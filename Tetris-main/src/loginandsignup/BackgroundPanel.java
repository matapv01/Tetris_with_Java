package loginandsignup;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public  class BackgroundPanel extends JPanel {
    private ImageIcon backgroundImage;

    public BackgroundPanel() {
        backgroundImage = new ImageIcon("Icon\\background.jpg"); // Đường dẫn đến file hình ảnh
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage.getImage(), 0, 0, this);
        }
    }
}
