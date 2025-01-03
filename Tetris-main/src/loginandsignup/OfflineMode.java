package loginandsignup;

import tetris.Tetris;

import javax.swing.*;
import java.awt.*;

public class OfflineMode extends JFrame {
    private BackgroundPanel BGOffline;
    private JTextField jfUser;
    private JButton jbOk, jbBack;
    private JLabel jLabel;
    private LoginManager loginManager;

    public OfflineMode(LoginManager loginManager){
        this.init();

        this.add(BGOffline);
        this.loginManager = loginManager;
        
        this.setTitle("Offline");
        this.setSize(500, 474);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void init(){
        BGOffline = new BackgroundPanel();
        BGOffline.setLayout(null);

        jLabel = new JLabel("Nhập UserName!!!");
        jLabel.setFont(new Font("Arial", Font.BOLD, 28));
        jLabel.setForeground(Color.RED);
        jLabel.setBounds(135, 70, 400, 70);
        BGOffline.add(jLabel);

        jfUser = new JTextField();
        jfUser.setBounds(100,175,300,70);
        jfUser.setBackground(Color.DARK_GRAY);
        jfUser.setForeground(Color.WHITE);
        jfUser.setFont(new Font("Arial", Font.PLAIN, 28));
        BGOffline.add(jfUser);

        jbOk = new JButton("OK");
        jbOk.setBackground(Color.GREEN);
        jbOk.setForeground(Color.BLACK);
        jbOk.setBounds(200,275,100,30);
        jbOk.setFocusable(false);
        jbOk.addActionListener(e -> handleOK());
        BGOffline.add(jbOk);

        jbBack = new JButton("Back");
        jbBack.setBounds(20, 30, 70, 30);
        jbBack.setFocusable(false);
        jbBack.addActionListener(e -> handleBack());
        BGOffline.add(jbBack);
    }

    private void handleBack(){
        loginManager.setVisible(true);
        this.dispose();
    }

    private void handleOK(){
        Tetris.onLoginSuccess(new Player(jfUser.getText(), 0), false);
        this.dispose();
    }
}
