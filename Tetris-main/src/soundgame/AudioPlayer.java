package soundgame;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer extends Thread {
    private String soundsFolder = "Sounds Effect" + File.separator;
    private String clearLinePath = soundsFolder + "clear.wav";
    private String blockdownPath = soundsFolder + "blockdown.wav";
    private String rotateblockPath = soundsFolder + "rotateblock.wav";
    private String holdblockPath = soundsFolder + "holdblock.wav";
    

    private Clip clearLineSound, gameoverSound, blockdownSound, rotateblockSound, holdblockSound;

    public AudioPlayer() {
        try {
            // Khởi tạo clip cho từng âm thanh
            clearLineSound = AudioSystem.getClip();
            blockdownSound = AudioSystem.getClip();
            rotateblockSound = AudioSystem.getClip();
            holdblockSound = AudioSystem.getClip();

            // Mở các tệp âm thanh
            clearLineSound.open(AudioSystem.getAudioInputStream(new File(clearLinePath).getAbsoluteFile()));           
            blockdownSound.open(AudioSystem.getAudioInputStream(new File(blockdownPath).getAbsoluteFile()));
            rotateblockSound.open(AudioSystem.getAudioInputStream(new File(rotateblockPath).getAbsoluteFile()));
            holdblockSound.open(AudioSystem.getAudioInputStream(new File(holdblockPath).getAbsoluteFile()));
            
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Phương thức để phát âm thanh với đa luồng
    public void playSound(Clip sound) {
        new Thread(() -> {
            try {
                sound.setFramePosition(0); // Đặt lại từ đầu tệp âm thanh
                sound.start(); // Bắt đầu phát âm thanh
            } catch (Exception ex) {
                Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    // Phát âm thanh clear line
    public void playClearLine() {
        playSound(clearLineSound);
    }

    // Phát âm thanh block down
    public void playBlockDown() {
        playSound(blockdownSound);
    }

    // Phát âm thanh rotate block
    public void playRotateBlock() {
        playSound(rotateblockSound);
    }

    // Phát âm thanh hold block
    public void playHoldBlock() {
        playSound(rotateblockSound);
    }
}