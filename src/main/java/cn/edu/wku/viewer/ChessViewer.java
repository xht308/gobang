package cn.edu.wku.viewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ChessViewer extends JFrame {

    public ChessViewer() {
        this(528, 528);
    }

    public ChessViewer(int width, int height) {
        //set the frame
        this.setTitle("five-in-a-row");
        this.setSize(528,528);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - 528) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - 528) / 2);
        this.setBackground(new Color(255, 181, 54));
//        //load a background image
//        try {
//            image = ImageIO.read(new File("D:\\CPS\\a CPS 2232\\background.jpg"));
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        this.setVisible(true);
    }
}
