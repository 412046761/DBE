package client;

import client.commen.JdbcSetDO;
import client.commen.SwingUtils;
import org.ho.yaml.Yaml;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: DBsetForm
 * @author: liyue
 * @date: 2020/11/23 11:52
 */
public class DBsetForm {

    JTextField driverText;
    JTextField jdbcText;
    JTextField userText;
    JTextField passwordText;
    JdbcSetDO jdbcSetDO;
    HashMap datasource_father;
    File dumpFile ;
    DBsetForm  (){
        jdbcSetDO = new JdbcSetDO();
        dumpFile = new File(System.getProperty("user.dir") + "/src/main/resources/application.yml");
    }

    public  void placeComponents(JPanel panel){

        // 初始化数据
        try{
            datasource_father = read2();
        }catch (Exception ignored){
            System.out.println("数据初始化失败。");
        }
        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);
        // 创建 JLabel
        /*
         * 创建文本域用于JDBCURL输入
         */
        JLabel driverLabel =  SwingUtils.buildJLabel("jdbcDriver:",10,10,165,25);
        panel.add(driverLabel);
        /*
         * jdbcurl
         */
        driverText = new JTextField(20);
        driverText.setBounds(100,10,550,25);
        driverText.setText(jdbcSetDO.getDriver());
        panel.add(driverText);
        /*
         * jdbcurl
         */
        /*
         * 创建文本域用于JDBCURL输入
         */
        JLabel jdbcLabel =  SwingUtils.buildJLabel("jdbcURL:",10,40,165,25);
        panel.add(jdbcLabel);
        jdbcText = new JTextField(20);
        jdbcText.setBounds(100,40,550,25);
        jdbcText.setText(jdbcSetDO.getUrl());
        panel.add(jdbcText);

        /*
         * 创建文本域用于用户输入
         */
        JLabel userLabel = SwingUtils.buildJLabel("DBUser:",10,70,80,25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100,70,550,25);
        userText.setText(jdbcSetDO.getUsername());
        panel.add(userText);



        // 输入密码的文本域
        JLabel passwordLabel = SwingUtils.buildJLabel("DBPassword:",10,100,80,25);
        panel.add(passwordLabel);
        /*
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */
        //JPasswordField passwordText = new JPasswordField(20);
        passwordText = new JTextField(20);
        passwordText.setBounds(100,100,550,25);
        passwordText.setText(jdbcSetDO.getPassword());
        panel.add(passwordText);

        // 创建登录按钮
        JButton saveButton = SwingUtils.buildJButton("save",450, 130, 80, 25);
        addActionListener(saveButton);
        panel.add(saveButton);
    }

    /**
     * 读取yml
     * @throws FileNotFoundException
     */
    public  HashMap  read2() throws FileNotFoundException {

        HashMap father = Yaml.loadType(dumpFile, HashMap.class);
        HashMap springMap = (HashMap)father.get("spring");
        HashMap datasource = (HashMap) springMap.get("datasource");
        // 数据库数据初始化
        for(Object key:datasource.keySet()){

            switch (key.toString()) {
                case "driver-class-name":
                    jdbcSetDO.setDriver(datasource.get(key).toString());
                    break;
                case "url":
                    jdbcSetDO.setUrl(datasource.get(key).toString());
                    break;
                case "username":
                    jdbcSetDO.setUsername(datasource.get(key).toString());
                    break;
                case "password":
                    jdbcSetDO.setPassword(datasource.get(key).toString());
                    break;
            }

        }
        return  father;
    }

    public void write(){

        datasource_father.get("spring");
        HashMap springMap = (HashMap)datasource_father.get("spring");
        HashMap datasource = (HashMap) springMap.get("datasource");
        datasource.put("driver-class-name",driverText.getText());
        datasource.put("url", jdbcText.getText());
        datasource.put("username",userText.getText());
        datasource.put("password",passwordText.getText());
        springMap.put("datasource",datasource);
        datasource_father.put("spring",springMap);
        try {
            Yaml.dump(datasource_father, dumpFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private  void addActionListener(JButton saveButton) {
        // 为按钮绑定监听器
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                write();
                // 对话框
                JOptionPane.showMessageDialog(null, "保存成功！");
            }
        });
    }
}
