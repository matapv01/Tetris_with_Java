package maingame;

//**************************************

import java.util.logging.Level;
import java.util.logging.Logger;
import tetris.Tetris;

//Đây là lớp được dùng để di chuyển khối gạch xuống sau khoản thời gian nhất định
//**************************************
public class GameThread extends Thread {

    private GameArea gameArea;
    private SpeedAndLevelSystem speedAndLevelSystem;
    private ScoreAndTimeSystem scoreAndTimeSystem;
    private volatile boolean exited;

    private volatile int gameSpeed;
    private volatile boolean isBlockAlive;

    public GameThread(GameArea gameArea, SpeedAndLevelSystem speedAndLevelSystem, ScoreAndTimeSystem scoreAndTimeSystem) {
        this.gameArea = gameArea;
        this.speedAndLevelSystem = speedAndLevelSystem;
        this.scoreAndTimeSystem = scoreAndTimeSystem;
        exited = false;
    }

    //Kiểm tra xem Block có còn 'sống' không
    //Tránh trường hợp xảy ra các lỗi không mong muốn khi Thread đang
    //trong quá trình chạy hiệu ứng nháy thì người chơi nhấn xoay khối
    public boolean checkBlockAlive() {
        return isBlockAlive;
    }

    //Khởi tạo game mới
    public void newGame() {
        Tetris.updatePlayerScore(scoreAndTimeSystem.getScore());
        gameArea.newGame();
        speedAndLevelSystem.newGame();
        scoreAndTimeSystem.newGame();
        this.gameSpeed = 1000;

        //Nếu là chế độ luyện tập thì để gameSpeed là 100000ms
        if (this.gameArea.getGameLevel()>0) {
            this.gameSpeed = 100000;
        }
    }

    public void setExitGame() {
        Tetris.updatePlayerScore(scoreAndTimeSystem.getScore());
        exited = true;
    }

    @Override
    public void run() {
        this.newGame();
        while (true) {

            isBlockAlive = false;
            //Đếm xem có hàng nào full không
            int FullLinesNum = gameArea.checkAndCountFullLines();

            //Nếu có
            if (FullLinesNum != 0) {

                //Tạo hiệu ứng nhấp nháy xoá hàng
                for (int i = 0; i < 50; i++) {
                    gameArea.startClearLinesEffect();
                    try {
                        Thread.sleep(8);
                    } catch (InterruptedException ex) {
                    }
                }

                //Sau khi nháy xong thì đẩy các khối bên trên xuống
                gameArea.dropBlocksAboveClearedLines();

                //TÍnh điểm và cập nhật tốc độ game nếu có
                gameSpeed = speedAndLevelSystem.addLinesAndCheckNextLevel(FullLinesNum);
                scoreAndTimeSystem.addPointsForClearLines(FullLinesNum, speedAndLevelSystem.getLevel());
            }
            
            //Kiểm tra xem game đã kết thúc chưa
            if (gameArea.checkGameOver()) {
                //Nếu là chơi thường thì sẽ hiện gameOver nếu không thì thôi
                if (gameArea.getGameLevel()==0) {
                    gameArea.showGameOverPanel(scoreAndTimeSystem.getScore());
                    try {
                        scoreAndTimeSystem.stopTime();
                        Thread.sleep(3600000);
                    } catch (InterruptedException ex) {
                    }
                }
                
                //Tạo mới game
                this.newGame();
            }

            //Tạo khối mới
            gameArea.createNewBlock();
            isBlockAlive = true;

            //Tăng điểm khi khối mới được tạo
            scoreAndTimeSystem.addPointsForNewBlock(speedAndLevelSystem.getLevel());

            //Kiểm tra game kết thúc, nếu có thì làm mới game
            //Cho khối game đi xuống 1 ô nếu có thể.
            //Sau mỗi lần di chuyển 1 ô thì nghỉ gameSpeed thời gian
            while (gameArea.moveBlockDown()) {
                try {
                    Thread.sleep(gameSpeed);
                } catch (InterruptedException ex) {
                    if (exited) {
                        return;
                    }
                }
            }
        }
    }
}
