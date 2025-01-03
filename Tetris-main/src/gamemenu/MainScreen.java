package gamemenu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import tetris.Tetris;

public class MainScreen extends JPanel {
    private int buttonX, buttonY, buttonW, buttonH, buttonGap, fontSize;
    private Leaderboard leaderboard;

    public MainScreen(Dimension d) {
        this.setBackground(Color.black);
        this.setLayout(null);

        buttonX = (int) (d.width * 0.2);
        buttonY = (int) (d.width * 0.02);
        buttonW = (int) (d.width * 0.8);
        buttonH = (int) (d.width * 0.1);
        buttonGap = (int) (d.width * 0.01);
        fontSize = (int) (d.width * 0.04);

        CustomButton gameModeButton = new CustomButton("GAME MODE", "START PLAYING AND SCORE POINTS", new ImageIcon("Icon\\bt1.png"), buttonX, buttonY, buttonW, buttonH, new Color(52, 34, 45), new Color(241, 188, 219), new Color(182, 138, 165), fontSize);
        CustomButton practiceModeButton = new CustomButton("PRACTICE MODE", "PRACTICE THE TETRIS SUPER ROTATION SYSTEM", new ImageIcon("Icon\\bt2.png"), buttonX, buttonY + buttonH + buttonGap, buttonW, buttonH, new Color(43, 41, 64), new Color(186, 184, 223), new Color(140, 136, 192), fontSize);
        CustomButton leaderboardButton = new CustomButton("LEADERBOARD", "VIEW SCORES FROM OTHER PLAYERS", new ImageIcon("Icon\\bt3.png"), buttonX, buttonY + buttonH * 2 + buttonGap * 2, buttonW, buttonH, new Color(32, 71, 48), new Color(103, 195, 119), new Color(80, 190, 98), fontSize);
        CustomButton guideButton = new CustomButton("GUIDE", "SHOW BUTTON INFORMATION", new ImageIcon("Icon\\bt4.png"), buttonX, buttonY + buttonH * 3 + buttonGap * 3, buttonW, buttonH, new Color(31, 42, 69), new Color(136, 175, 255), new Color(81, 105, 192), fontSize);

        this.add(gameModeButton);
        this.add(practiceModeButton);
        this.add(leaderboardButton);
        this.add(guideButton);

        GuideForm guideForm = new GuideForm();
        leaderboard = new Leaderboard();

        gameModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tetris.startGame(0);
            }
        });
        practiceModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tetris.startGame(1);
            }
        });
        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Chỉ mở bảng xếp hạng nếu nó chưa được hiển thị
                if (!leaderboard.isVisible()) {
                    leaderboard.clearPanel(); // Xóa dữ liệu cũ
                    leaderboard.loadAndDisplay();
                    leaderboard.setVisible(true);
                }
            }
        });
        guideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guideForm.setVisible(true);
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon backGround = new ImageIcon("Icon\\background.jpg");
        g.drawImage(backGround.getImage(), 0, 0, this);
    }
}
