package tool;

import org.apache.ibatis.io.Resources;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BarTool extends JFrame {
    //设置导航栏的按钮
    public JButton getButton (String text,String img){
        String absolutePath = null;
        try {
            File resourceAsFile = Resources.getResourceAsFile(img);
            absolutePath = resourceAsFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon imageIcon = new ImageIcon(absolutePath);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(28,28, Image.SCALE_DEFAULT));
        Font font = new Font("行书",Font.LAYOUT_LEFT_TO_RIGHT,16);
        JButton jbt1 = new JButton(text,imageIcon);
        jbt1.setFont(font);
        //无边框
        jbt1.setBorderPainted(false);
        //文字与图片垂直对齐
        jbt1.setVerticalTextPosition(SwingConstants.BOTTOM);
        //点击变成手型
        jbt1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return jbt1;
    }
}
