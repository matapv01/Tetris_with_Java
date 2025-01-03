package loginandsignup;

import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    public static String hashPassword(String password) {
        // Tạo salt và hash mật khẩu
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Phương thức kiểm tra mật khẩu khi đăng nhập
    public static boolean checkPassword(String inputPassword, String storedHash) {
        // So sánh mật khẩu người dùng nhập với mật khẩu đã hash
        return BCrypt.checkpw(inputPassword, storedHash);
    }
}
