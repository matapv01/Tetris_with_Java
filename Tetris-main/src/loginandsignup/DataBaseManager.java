package loginandsignup;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseManager {
    private String url = "jdbc:mysql://sql.freedb.tech:3306/freedb_Tetris";
    private String username = "freedb_user1";
    private String password = "!WfADPhc7&?Dm8Q";
    private Connection connection;

    public DataBaseManager(){
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            // Nếu xảy ra lỗi, in thông báo lỗi
            System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<Player> getLeaderboard(int limit) {
        ArrayList<Player> leaderboard = new ArrayList<>();
        String query = "SELECT username, score FROM Account ORDER BY score DESC LIMIT ?"; // DESC la giam dan

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String user = rs.getString("username");
                int score = rs.getInt("score");
                leaderboard.add(new Player(user, score));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }

    public boolean loginn(String username, String password){
        String query = "SELECT * FROM Account WHERE username = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    String storedHash = rs.getString("password");

                    if (UserService.checkPassword(password, storedHash)) {
                        return true;  // Đăng nhập thành công
                    }
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean signUp(String username, String password) {
        // Hash mật khẩu trước khi lưu vào cơ sở dữ liệu
        String hashedPassword = UserService.hashPassword(password);

        String query = "INSERT INTO Account (username, password, score) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setInt(3, 0);

            // Thực thi câu lệnh SQL để lưu người dùng vào cơ sở dữ liệu
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Kiểm tra xem username đã tồn tại trong cơ sở dữ liệu chưa
    public boolean isUserExist(String username) {
        String query = "SELECT COUNT(*) FROM Account WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // Nếu kết quả > 0, nghĩa là user đã tồn tại
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updatePlayerScore(String username, int score) {
        String query = "UPDATE Account SET score = GREATEST(score, ?) WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query);) {
            stmt.setInt(1, score);
            stmt.setString(2, username);

            // Thực thi câu lệnh UPDATE
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Điểm số đã được cập nhật thành công.");
            } else {
                System.out.println("Không có dữ liệu nào được cập nhật.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
    public Integer getPlayerScore(String username) {
        String query = "SELECT score FROM Account WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("score");  // Lấy điểm của người chơi nếu tồn tại
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi truy vấn điểm của người chơi: " + e.getMessage());
        }
        return null;  // Trả về null nếu không tìm thấy hoặc có lỗi
    }
}
