package gamemenu;

import javax.swing.*;

public class GuideForm extends JFrame {

    public GuideForm() {
        setTitle("Guide");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel guidePhoto = new JLabel();
        ImageIcon photo = new ImageIcon("Icon\\guide.png");
        guidePhoto.setIcon(photo);

        setContentPane(guidePhoto);
        setBounds(0, 0, photo.getIconWidth(), photo.getIconHeight());
        this.setResizable(false);
    }
}
