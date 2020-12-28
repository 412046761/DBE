package client;

import client.commen.SwingUtils;
import mp.dao.PrimaryMapper;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.apache.poi.ss.usermodel.CellType.BLANK;

/**
 * @description: 界面展示
 * @author: liyue
 * @date: 2020/11/23 11:46
 */
public class ClientComponent {
    static String  FILL_PATH ="C:\\Users\\41204\\Desktop\\DBE.xls";


    public static void placeComponents(JPanel panel) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);
        // 创建上传按钮
        JButton uploudButton =  SwingUtils.buildJButton("上传Excle",90, 10, 125, 45);
        addActionListener2(uploudButton);
        panel.add(uploudButton);
        // 创建下载按钮
        JButton downlouButton =  SwingUtils.buildJButton("下载模板",90, 50, 125, 45);
        addActionListener1(downlouButton);
        panel.add(downlouButton);
        // 创建设置按钮
        JButton DBsetButton =  SwingUtils.buildJButton("数据库设置",90, 90, 125, 45);
        addActionListener(DBsetButton);
        panel.add(DBsetButton);
    }

    /**
     * 下载模板按钮事件
     * @param saveButton
     */
    private static void addActionListener1(JButton saveButton) {
        // 为按钮绑定监听器
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                {

                    // 创建一个excel对象
                    HSSFWorkbook wb = null;
                    String excelName = "DBE模板";

                    wb = new HSSFWorkbook();
                    HSSFSheet sheet = wb.createSheet(excelName);

                    HSSFRow row = sheet.createRow(0);
                    row.setHeightInPoints(20);

                    CellRangeAddressList regions = new CellRangeAddressList(0, 0, 0, 0);
                    //获取单元格的坐标
                    sheet.setDefaultColumnWidth(500);

                    OutputStream output;

                    try {
                        output = new FileOutputStream(FILL_PATH);
                        wb.write(output);
                        output.close();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
    }

    /**
     * 上传模板按钮事件
     * @param saveButton
     */
    private static void addActionListener2(JButton saveButton) {


        // 为按钮绑定监听器
        saveButton.addActionListener(new ActionListener() {
            @Autowired
            public PrimaryMapper primaryMapper;
            @Override
            public void actionPerformed(ActionEvent e) {

                {
                    MultipartFile file = null;
                    try{
                        File  file1 = new File(FILL_PATH);

                        //将File格式转换为MultipartFile格式
                        FileInputStream fileInputStream = new FileInputStream(file1);
                         file = new MockMultipartFile(file1.getName(), fileInputStream);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                    //判断文件是否是excel文件
                    if(!file.getOriginalFilename().endsWith("xls") && !file.getOriginalFilename().endsWith("xlsx")){
                        // 不是excel文件
                    }

                    // 获取SQL集合
                    List<String> sqlList = getDataFromExcel(file);

                    // 数据库执行SQL
                    List<List<Map<String, Object>>> rsList = getRSForDB(sqlList);

                    //  将返回值序列化为Ecsle
                    exportEcsle(rsList);

                }
            }


            private void exportEcsle(List<List<Map<String, Object>>> rsList) {
                if(rsList == null || rsList.size()==0) return;
            }

            private List<List<Map<String, Object>>> getRSForDB(List<String> sqlList) {
                List<List<Map<String, Object>>> rslist = new ArrayList<>();

                for (String sql :sqlList ) {
                    List<Map<String,Object>> rs = primaryMapper.getList(sql);
                    if(rs != null && rs.size()>0)
                    rslist.add(rs);
                }
                return rslist;
            }
        });
    }



    public static List<String> getDataFromExcel(MultipartFile file) {
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个ExcelParamVo，所有行作为一个集合返回
        List<String> list = new ArrayList<String>();
        if(workbook != null){
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum  = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行
                for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++){
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if(isRowEmpty(row)){
                        continue;
                    }

                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = row.getLastCellNum();
                    String[] cells = new String[row.getLastCellNum()];

                    String  sql = null;
                    //循环当前列
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                        if(cellNum == 0){
                            Cell cell = row.getCell(cellNum);
                            sql = getCellValue(cell);
                        }
                    }
                    list.add(sql);
                }
            }
        }
        return list;
    }




    /**
     * 数据库设置按钮事件
     * @param saveButton
     */
    private static void addActionListener(JButton saveButton) {
        // 为按钮绑定监听器
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 对话框
                //JOptionPane.showMessageDialog(null, "保存成功！");

                // 创建 JFrame 实例
                JFrame frame = new JFrame("DBEClient");
                // Setting the width and height of frame
                frame.setSize(700, 220);
                /**
                 * 下边的这句话，如果这么写的话，窗口关闭，springboot项目就会关掉，使用
                 * dispose则不会
                 */
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                //frame.dispose(); //如果写这句可实现窗口关闭，springboot项目仍运行
                /* 创建面板，这个类似于 HTML 的 div 标签
                 * 我们可以创建多个面板并在 JFrame 中指定位置
                 * 面板中我们可以添加文本字段，按钮及其他组件。
                 */
                JPanel panel = new JPanel();
                // 添加面板
                frame.add(panel);
                /*
                 * 调用用户定义的方法并添加组件到面板
                 */
                new DBsetForm().placeComponents(panel);

                // 设置界面可见
                frame.setVisible(true);
            }
        });
    }


    public static  Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getName();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {

            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith("xls")){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith("xlsx")){
                //2007 及2007以上
                SXSSFWorkbook workBooke = new SXSSFWorkbook(new XSSFWorkbook(is),-1);
                workbook = workBooke.getXSSFWorkbook();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return workbook;
    }


    public static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != BLANK)
                return false;
        }
        return true;

    }


    /**
     * 时间格式处理
     * @return
     * @data 2017年11月27日
     */
    public static String stringDateProcess(Cell cell){
        String result = new String();
        if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
            SimpleDateFormat sdf = null;
            if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                sdf = new SimpleDateFormat("HH:mm");
            } else {// 日期
                sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            }
            Date date = cell.getDateCellValue();
            result = sdf.format(date);
        } else if (cell.getCellStyle().getDataFormat() == 58) {
            // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            double value = cell.getNumericCellValue();
            Date date = org.apache.poi.ss.usermodel.DateUtil
                    .getJavaDate(value);
            result = sdf.format(date);
        } else {
            double value = cell.getNumericCellValue();
            CellStyle style = cell.getCellStyle();
            DecimalFormat format = new DecimalFormat();
            String temp = style.getDataFormatString();
            // 单元格设置成常规
            if (temp.equals("General")) {
                format.applyPattern("#");
            }
            result = format.format(value);
        }

        return result;
    }

    public static String getCellValue(Cell cell){
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC

                case NUMERIC : {
                    short format = cell.getCellStyle().getDataFormat();
                    if(format == 14 || format == 31 || format == 57 || format == 58){ 	//excel中的时间格式
                        cellvalue= stringDateProcess(cell);
                    }
                    // 判断当前的cell是否为Date
                    else if (HSSFDateUtil.isCellDateFormatted(cell)) {  //先注释日期类型的转换，在实际测试中发现HSSFDateUtil.isCellDateFormatted(cell)只识别2014/02/02这种格式。
                        cellvalue= stringDateProcess(cell);
                    } else { // 如果是纯数字
                        // 取得当前Cell的数值
                        cellvalue = NumberToTextConverter.toText(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getStringCellValue().replaceAll("'", "''");
                    break;
                case BLANK:
                    cellvalue = null;
                    break;
                // 默认的Cell值
                case BOOLEAN: // Boolean
                    cellvalue = cell.getBooleanCellValue() + "";
                    break;

                case FORMULA: // 公式
                    cellvalue = cell.getCellFormula() + "";
                    break;

                case ERROR:
                    cellvalue = cell.getErrorCellValue() + "";
                    break;
                default:{
                    cellvalue = " ";
                }
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

}
