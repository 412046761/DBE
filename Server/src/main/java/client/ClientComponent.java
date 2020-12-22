package client;

import client.commen.SwingUtils;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

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
            @Override
            public void actionPerformed(ActionEvent e) {

                {
                    File file = null;
                    try{
                        file = new File(FILL_PATH);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }


                    if(file == null){
                        return ;
                    }

//                    //判断文件是否是excel文件
//                    if(!file..endsWith("xls") && !file.getOriginalFilename().endsWith("xlsx")){
//                        return Response.error(ResultEnum.EXCEL_TYPE_ERROR);
//                    }
//



                }
            }
        });
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

}
