package gamemenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class CustomButton extends JButton {

    private Timer out, in;
    private int min, max, borderThickness, step;
    private volatile boolean isAnimatingOut = false;
    private volatile boolean isAnimatingIn = false;
    private Color dark, bright;
    private CustomButton thisCLass;

    public CustomButton(String line1, String line2, ImageIcon icon, int x, int y, int width, int height, Color backgroundColor, Color charColorLine1, Color charColorLine2, int fontSize) {

        super("<html><p style='margin-top: 0mm; margin-right: 0mm; margin-bottom: 0mm; font-size: 11pt; font-family: \"Calibri\", sans-serif; line-height: 0.3;'><strong><span style=\"font-size: " + fontSize + "px; font-family: Helvetica; color: rgb(" + charColorLine1.getRed() + ", " + charColorLine1.getGreen() + ", " + charColorLine1.getBlue() + ");\">" + line1 + "</span></strong></p>\n"
                + "<p style='margin: 0mm; font-size: 11pt; font-family: \"Calibri\", sans-serif; line-height: 0.2;'><span style=\"font-size: " + (int) (fontSize * 0.3) + "px; font-family: Helvetica; color: rgb(" + charColorLine2.getRed() + ", " + charColorLine2.getGreen() + ", " + charColorLine2.getBlue() + ");\">" + line2 + "</span></p>\n"
                + "<div style=\"all: initial;\" class=\"notranslate\"><br></div></html>");

        double pro = height * 0.8 / icon.getIconHeight();
        this.setIcon(new ImageIcon(icon.getImage().getScaledInstance((int) (icon.getIconWidth() * pro), (int) (icon.getIconHeight() * pro), Image.SCALE_DEFAULT)));
        this.revalidate();

        this.setBounds(x, y, width, height);

        thisCLass = this;

        borderThickness = (int) (width * 0.0037);
        min = width;
        max = (int) (width * 1.045);
        step = (int) ((max - min) / 12);

        this.bright = backgroundColor.brighter();
        this.dark = backgroundColor;

        this.setFocusable(false);

        this.setBackground(dark);
        this.setBorder(new MatteBorder(borderThickness, 0, 0, 0, bright));
        this.setHorizontalAlignment(SwingConstants.LEFT);

        out = new Timer(13, (et) -> {
            Dimension size = this.getSize();
            Point p = this.getLocation();
            if (size.width < max) {
                this.setSize(size.width + step * step, size.height);
                this.setLocation(p.x - step * step, p.y);
                repaint();
            } else {
                out.stop();
                isAnimatingOut = false; // Reset trạng thái khi kết thúc
            }
        });

        in = new Timer(15, (et) -> {
            Dimension size = this.getSize();
            Point p = this.getLocation();
            if (size.width > min) {
                this.setSize(size.width - step*3, size.height);
                this.setLocation(p.x + step*3, p.y);
                repaint();
            } else {
                in.stop();
                isAnimatingIn = false; // Reset trạng thái khi kết thúc
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (in.isRunning()) {
                    in.stop();
                    isAnimatingIn = false;
                }
                if (!out.isRunning() && !isAnimatingOut) {
                    isAnimatingOut = true;
                    out.start();
                    thisCLass.setBackground(bright);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (out.isRunning()) {
                    out.stop();
                    isAnimatingOut = false;
                }
                if (!in.isRunning() && !isAnimatingIn) {
                    isAnimatingIn = true;
                    in.start();
                    thisCLass.setBackground(dark);
                }
            }

        });
    }
}
