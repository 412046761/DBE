package client.commen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @description: SwingUtils
 * @author: liyue
 * @date: 2020/11/23 13:48
 */
public class SwingUtils {

    /**
     * 初始化按钮名称及位置
     * @param name
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public static JButton buildJButton(String name, int x, int y, int width, int height) {
        JButton button = new JButton(name);
        button.setBounds(x, y, width, height);
        return button;
    }

    /**
     * 初始化JLabel名称及位置
     * @param name
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public static JLabel buildJLabel(String name, int x, int y, int width, int height) {
        JLabel laber = new JLabel(name);
        laber.setBounds(x, y, width, height);
        return laber;
    }
}
