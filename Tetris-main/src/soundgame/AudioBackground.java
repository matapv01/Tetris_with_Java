package soundgame; 
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioBackground {
    private Clip clip;
    private String filePath = "Sounds Effect/background.wav";

    // Constructor để khởi tạo trình phát âm thanh với đường dẫn tệp
    public AudioBackground() {
        try {
            // Tải tệp âm thanh
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Lấy tài nguyên clip âm thanh
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Phương thức để bật hoặc tắt nhạc nền
    public void start() {
        clip.setFramePosition(0);  // Đặt lại vị trí phát từ đầu
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);  // Lặp vô tận để phát nhạc nền
    }
    public void stop() {
        clip.stop();
    }
}