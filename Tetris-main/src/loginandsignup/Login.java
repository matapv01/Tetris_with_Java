package loginandsignup;

import tetris.Tetris;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    private BackgroundPanel panel; // Thay đổi từ JPanel sang BackgroundPanel
    private JPanel welcomePanel;
    private JLabel Username, Password, Signup, iconLabel;
    private JTextField textUsername;
    private JPasswordField textPassword;
    private JButton btnOK, btnSignup, btnCancel;
    private DataBaseManager cnt;
    private LoginManager loginManager;

    public Login(DataBaseManager cnt, LoginManager loginManager) {
        this.init();

        this.add(panel);
        this.setTitle("Login");
        this.setSize(500, 474);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.cnt = cnt;
        this.loginManager = loginManager;
    }

    private void init() {
        panel = new BackgroundPanel(); // Sử dụng BackgroundPanel
        panel.setLayout(null);

        iconLabel = new JLabel(new ImageIcon("Icon\\login.png"));
        iconLabel.setBounds(200, 4, 150, 150);
        panel.add(iconLabel);

        // Username
        Username = new JLabel("Username");
        Username.setBounds(80, 140, 100, 30);
        Username.setFont(new Font("Segoe Print", Font.BOLD, 14));
        Username.setForeground(Color.BLACK);
        panel.add(Username);

        textUsername = new JTextField();
        textUsername.setBounds(180, 140, 200, 30);
        panel.add(textUsername);

        // Password
        Password = new JLabel("Password");
        Password.setBounds(80, 190, 100, 30);
        Password.setFont(new Font("Segoe Print", Font.BOLD, 14));
        Password.setForeground(Color.BLACK);
        panel.add(Password);

        textPassword = new JPasswordField();
        textPassword.setBounds(180, 190, 200, 30);
        panel.add(textPassword);

        // Nút OK
        btnOK = new JButton("OK");
        btnOK.setBounds(190, 240, 180, 30);
        btnOK.setBackground(Color.GREEN);
        btnOK.setFocusable(false);
        btnOK.addActionListener(evt -> handleLogin());
        panel.add(btnOK);

        // Nút Back
        btnCancel = new JButton("Back");
        btnCancel.setBounds(20, 30, 70, 30);
        btnCancel.setFocusable(false);
        btnCancel.addActionListener(e -> handleBack());
        panel.add(btnCancel);

        // Đăng ký
        Signup = new JLabel("Don't have an account?");
        Signup.setBounds(80, 290, 200, 30);
        Signup.setForeground(Color.RED);
        panel.add(Signup);

        btnSignup = new JButton("Sign Up");
        btnSignup.setBounds(240, 290, 80, 30);
        btnSignup.setForeground(Color.WHITE);
        btnSignup.setBackground(new Color(70, 130, 180));
        btnSignup.setFocusable(false);
        btnSignup.addActionListener(e -> handleSignup());
        panel.add(btnSignup);
    }

    private void handleLogin() {
        String username = textUsername.getText();
        String password = new String(textPassword.getPassword());

        boolean checklogin = cnt.loginn(username, password);
        if (checklogin) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            Tetris.onLoginSuccess(new Player(username, cnt.getPlayerScore(username)), true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Tên người dùng hoặc mật khẩu không chính xác.", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleBack() {
        loginManager.setVisible(true);
        this.dispose();
    }

    private void handleSignup() {
        SignUp signUp = new SignUp(cnt, this);
        this.dispose();
    }
}
