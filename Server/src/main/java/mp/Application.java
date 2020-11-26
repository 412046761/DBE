package mp;

import client.ClientComponent;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

/**
 * @description: 启动类
 * @author: liyue
 * @date: 2020/9/17 16:44
 */
@MapperScan("mp.dao")
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        //SwingUtilities.invokeLater的作用可以详细百度一下，我新手
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
        //spirng
        SpringApplication.run(Application.class,args);
    }

    private static void createAndShowGUI(){
        // 创建 JFrame 实例
        JFrame frame = new JFrame("DBEClient");
        // Setting the width and height of frame
        frame.setSize(350, 200);
        /**
         * 下边的这句话，如果这么写的话，窗口关闭，springboot项目就会关掉，使用
         * dispose则不会
         */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        new ClientComponent().placeComponents(panel);

        // 设置界面可见
        frame.setVisible(true);
    }

}
