package maingame.effect;

import java.awt.Point;
import maingame.GameArea;

//**************************************
//Đây là lớp được dùng để tạo hiệu ứng khi khối gạch được thả
//**************************************
public class DropBlockEffect extends Thread {

    private GameArea gameArea;
    private volatile boolean isDrop;

    public DropBlockEffect(GameArea gameArea) {
        this.gameArea = gameArea;
        this.isDrop = false;
    }

    @Override
    public void run() {
        while (true) {

            if (!isDrop) {
                continue;
            }

            isDrop = false;

            Point gameAreaLocation = gameArea.getLocation();

            int numPixel = 4;
            while (numPixel-- > 0) {
                gameAreaLocation.y++;
                gameArea.setLocation(gameAreaLocation);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                }
            }
            numPixel = 4;
            while (numPixel-- > 0) {
                gameAreaLocation.y--;
                gameArea.setLocation(gameAreaLocation);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                }
            }
            try {
                Thread.sleep(120);
            } catch (InterruptedException ex) {
            }
        }
    }

    public void setDrop() {
        isDrop = true;
    }
}
