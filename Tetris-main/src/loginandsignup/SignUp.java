package loginandsignup;

import javax.swing.*;
import java.awt.*;

public class SignUp extends JFrame {

    private BackgroundPanel BGPanel;
    private JTextField fname;
    private JPasswordField pass, confirmPass;
    private JButton signUpBtn, loginBtn;
    private JLabel LBicon, labelUserName, labelPassword, labelConfirmPassword;
    private DataBaseManager cnt;
    private Login login;

    public SignUp(DataBaseManager cnt, Login login) {
        this.init();

        this.add(BGPanel);
        this.setTitle("Sign Up");
        this.setSize(500, 474);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.cnt = cnt;
        this.login = login;
    }

    private void init() {
        BGPanel = new BackgroundPanel();
        BGPanel.setLayout(null);

        LBicon = new JLabel(new ImageIcon("Icon\\signup.png"));
        LBicon.setBounds(230, 4, 140, 140);
        BGPanel.add(LBicon);

        // Tên đầy đủ
        labelUserName = new JLabel("Username");
        labelUserName.setFont(new Font("Segoe Print", Font.BOLD, 13));
        labelUserName.setForeground(Color.BLACK);
        labelUserName.setBounds(80, 125, 100, 30);
        BGPanel.add(labelUserName);

        fname = new JTextField();
        fname.setBounds(200, 125, 200, 30); // Căn giữa với label
        BGPanel.add(fname);

        // Mật khẩu
        labelPassword = new JLabel("Password");
        labelPassword.setBounds(80, 175, 100, 30);
        labelPassword.setFont(new Font("Segoe Print", Font.BOLD, 13));
        labelPassword.setForeground(Color.BLACK);
        BGPanel.add(labelPassword);

        pass = new JPasswordField();
        pass.setBounds(200, 175, 200, 30); // Căn giữa với label
        BGPanel.add(pass);

        // Xác nhận mật khẩu
        labelConfirmPassword = new JLabel("Confirm Password");
        labelConfirmPassword.setBounds(80, 225, 150, 30);
        labelConfirmPassword.setFont(new Font("Segoe Print", Font.BOLD, 13));
        labelConfirmPassword.setForeground(Color.BLACK);
        BGPanel.add(labelConfirmPassword);

        confirmPass = new JPasswordField();
        confirmPass.setBounds(200, 225, 200, 30); // Căn giữa với label
        BGPanel.add(confirmPass);

        // Nút Đăng ký
        signUpBtn = new JButton("Sign Up");
        signUpBtn.setBounds(200, 280, 90, 30);
        signUpBtn.setBackground(Color.GREEN);
        signUpBtn.setFocusable(false);
        signUpBtn.addActionListener(evt -> handleSignUp());
        BGPanel.add(signUpBtn);

        // Nút Đăng nhập
        loginBtn = new JButton("Login");
        loginBtn.setBounds(310, 280, 90, 30);
        loginBtn.setBackground(Color.RED);
        loginBtn.setFocusable(false);
        loginBtn.addActionListener(evt -> handleLogin());
        BGPanel.add(loginBtn);
    }


    private void handleSignUp() {
        String username = fname.getText();
        String password = new String(pass.getPassword());
        String confirmPassword = new String(confirmPass.getPassword());
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean userExists = cnt.isUserExist(username);  // Kiểm tra nếu username đã tồn tại
        if (userExists) {
            JOptionPane.showMessageDialog(this, "Tên người dùng đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean isRegistered = cnt.signUp(username, password);

        // Thông báo kết quả đăng ký
        if (isRegistered) {
            JOptionPane.showMessageDialog(this, "Đăng ký thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Đăng ký thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleLogin() {
        login.setVisible(true);
        this.dispose();
    }
}

