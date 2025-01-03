package tetris;

import soundgame.AudioPlayer;
import soundgame.AudioBackground;
import gamemenu.MenuForm;
import java.awt.Rectangle;
import java.util.ArrayList;
import loginandsignup.LoginManager;
import loginandsignup.Player;
import maingame.GameForm;

public class Tetris {

    private static GameForm gameForm;
    private static MenuForm menuForm;
    private static Rectangle currentBonds;
    private static AudioPlayer audio;
    private static AudioBackground audioBackground;
    private static LoginManager loginManager;
    private static Player player;
    private static boolean isOnline;

    //Khởi tạo khung cửa sổ game
    public static void startGame(int gameLevel) {
        currentBonds = menuForm.getBounds();
        gameForm.setGameLevel(gameLevel);
        gameForm.setBounds(currentBonds);
        gameForm.setVisible(true);
        gameForm.startGameThread();
        menuForm.setVisible(false);
    }

    //Chuyển cửa sổ game đến level kế tiếp
    public static void nextLevel(int currentLevel) {
        gameForm.setGameLevel(currentLevel + 1);
    }

    public static void showMenu() {
        if (gameForm.isVisible()) {
            currentBonds = gameForm.getBounds();
            menuForm.setBounds(currentBonds);
        }
        menuForm.setVisible(true);
        gameForm.dispose();
    }

    public static void onLoginSuccess(Player playerx, boolean isOnlnex) {
        player = playerx;
        menuForm = new MenuForm(currentBonds, player);
        isOnline = isOnlnex;
        showMenu();
    }

    public static void updatePlayerScore(int score) {
        if (player.getScore() < score) {
            player.setScore(score);
            if (isOnline) {
                loginManager.updateScore(player);
            }
        }
        menuForm.refreshBestScore();
    }

    public static ArrayList<Player> getLeaderboard(int limit) {
        if (isOnline) {
            return loginManager.getLeaderboard(limit);
        }
        return new ArrayList<>();
    }

    public static void playClearSoundEffect() {
        audio.playClearLine();
    }

    public static void playBlockdownSoundEffect() {
        audio.playBlockDown();
    }

    public static void playRotateblockSoundEffect() {
        audio.playRotateBlock();
    }

    public static void playHoldblockSoundEffect() {
        audio.playHoldBlock();
    }

    public static void startBackGroundMusic() {
        audioBackground.start();
    }

    public static void stopBackGroundMusic() {
        audioBackground.stop();
    }
    
    public static void logout() {
        menuForm.dispose();
        loginManager = new LoginManager();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                audioBackground = new AudioBackground();
                currentBonds = new Rectangle(0, 0, 1280, 720);
                gameForm = new GameForm();
                audio = new AudioPlayer();
                startBackGroundMusic();
                loginManager = new LoginManager();
                gameForm.setResizable(false);
            }
        });
    }

}
