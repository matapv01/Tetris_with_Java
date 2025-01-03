package loginandsignup;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LoginManager extends JFrame {
    private BackgroundPanel BGHome;
    private JButton Online, Offline, exit;
    private JLabel iconLb, title;
    private DataBaseManager cnt;

    // Phương thức khởi tạo
    public LoginManager(){
        this.init();

        this.add(BGHome);

        this.setTitle("Đăng Nhập");
        this.setSize(500, 474);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        cnt = new DataBaseManager();
    }

    private void init(){
        BGHome = new BackgroundPanel();
        BGHome.setLayout(null);

        iconLb = new JLabel(new ImageIcon("Icon\\tetris2.png"));
        iconLb.setBounds(175, 1, 150, 100);
        BGHome.add(iconLb);

        title = new JLabel("TETRIS");
        title.setFont(new Font("Impact", Font.BOLD, 48));
        title.setForeground(new Color(128, 0, 128));
        title.setBounds(180, 70, 400, 100);
        BGHome.add(title);

        //online
        Online = new JButton("PLAY ONLINE");
        Online.setBounds(130, 170, 240, 37);
        Online.setBackground(new Color(255, 165, 0));
        Online.setForeground(Color.BLACK);
        Online.setFocusable(false);
        Online.addActionListener(e -> handleLogin());
        BGHome.add(Online);

        //offline
        Offline = new JButton("PLAY OFFLINE");
        Offline.setBounds(130, 240, 240, 37);
        Offline.setBackground(new Color(255, 165, 0));
        Offline.setForeground(Color.BLACK);
        Offline.setFocusable(false);
        Offline.addActionListener(e -> handleOffline());
        BGHome.add(Offline);

        //rank
        exit = new JButton("EXIT");
        exit.setBounds(130, 310, 240, 37);
        exit.setBackground(new Color(255, 165, 0));
        exit.setForeground(Color.BLACK);
        exit.setFocusable(false);
        exit.addActionListener(e -> handleExit());
        BGHome.add(exit);
    }

    private void handleLogin(){
        new Login(cnt, this);
        this.dispose();
    }

    private void handleOffline(){
        new OfflineMode(this);
        this.dispose();
    }
    
    private void handleExit(){
        System.exit(0);
    }
    
    public void updateScore(Player player) {
        cnt.updatePlayerScore(player.getUsername(), player.getScore());
    }
    
    public ArrayList<Player> getLeaderboard(int limit) {
        return cnt.getLeaderboard(limit);
    }
}
