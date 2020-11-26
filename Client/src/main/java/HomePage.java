package main.java;

import javax.swing.*;

/**
 * @description: 首页
 * @author: liyue
 * @date: 2020/11/17 16:37
 */
public class HomePage {






    public static void main(String[] args) {
        new HomePage().init();
    }


    public void init(){
        JFrame jFrame = new JFrame("DBE");
        jFrame.setSize(350, 200);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        placeComponents(panel);
        jFrame.add(panel);

        // 显示窗口
        jFrame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);

        // 创建 JLabel
        JLabel userLabel = new JLabel("User:");
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);

        /*
         * 创建文本域用于用户输入
         */
        JTextField userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        // 输入密码的文本域
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);

        /*
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        // 创建按钮
        JButton upLoud = new JButton("上传Excle");
        JButton downLoud = new JButton("下载模板");
        JButton dataSet = new JButton("数据库设置");
        upLoud.setBounds(10, 80, 120, 35);
        downLoud.setBounds(150, 80, 120, 35);
        dataSet.setBounds(210, 120, 120, 25);

        panel.add(upLoud);
        panel.add(downLoud);
        panel.add(dataSet);
    }


}
