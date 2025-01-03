package maingame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ScoreAndTimeSystem extends JPanel {

    //Phông chữ
    private Font font;
    
    //Toạ độ chữ
    private int textX,textY;
    
    //Điểm số
    private int score;
    
    //Thời gian chơi game
    private int startMinutes,startSeconds;
    
    //Định dạng điểm (VD 1000 -> 1,000)
    private DecimalFormat scoreFormat;
    
    //Bộ đếm thời gian
    Timer gameTimer;
    
    
    public ScoreAndTimeSystem() {
        this.setOpaque(false);
        this.score = 0;
        this.startMinutes = 0;
        this.startSeconds = 0;
        
        this.font = new Font("Calibri", Font.BOLD, 16);
        
        //Bộ đếm thời gian chơi game
        gameTimer = new Timer(1000, e -> {
            this.startSeconds++;
            if (this.startSeconds==61) {
                this.startMinutes++;
                this.startSeconds=0;
            }
            repaint();
        });
        gameTimer.setRepeats(true);
        gameTimer.start();
        
        scoreFormat = new DecimalFormat("###,###");
    }
    
    public void stopTime() {
        gameTimer.stop();
    }
    
    //Cập nhật kích thước và vị trí khi cửa sổ chương trình thay đổi kích thước
    //Ý tưởng:****************
    //Tương tự với HoldingBlock
    public void updateAreaSize(Rectangle gameAreaSize) {

        //Xác định dài, rộng của khung
        int scoreAndTimeSystemHeight = (int) (gameAreaSize.height * 0.26);
        int scoreAndTimeSystemWidth = (int) (gameAreaSize.width * 0.5);
        
        //Xác định toạ độ của khung
        int scoreAndTimeSystemX = gameAreaSize.x + gameAreaSize.width + (int) (gameAreaSize.width * 0.02);
        int scoreAndTimeSystemY = gameAreaSize.y + (int)(gameAreaSize.height*0.72);

        //Cập nhật vị trí và kích thước của khung
        this.setBounds(scoreAndTimeSystemX, scoreAndTimeSystemY, scoreAndTimeSystemWidth, scoreAndTimeSystemHeight);

        //Cập nhật kích thước phông chữ và vị trí
        font = font.deriveFont((float)(scoreAndTimeSystemHeight*0.2));
        
        textX = (int)(scoreAndTimeSystemHeight*0.04);
        textY = (int)(scoreAndTimeSystemHeight*0.2);
        
    }
    
    //Tăng điểm khi có block mới xuất hiện
    //Tăng điểm tỉ lệ với level đạt đươc
    public void addPointsForNewBlock(int level) {
        score += 10*level;
        repaint();
    }
    
    //Tăng điểm theo số lượng hàng đầy (tối đa có thể có 4 hàng đầy cùng lúc)
    //Tăng điểm tỉ lệ với level đạt đươc
    public void addPointsForClearLines(int linesClearNum, int level) {
        switch (linesClearNum) {
            case 1:
                score += 100*level;
                break;
            case 2:
                score += 200*level;
                break;
            case 3:
                score += 300*level;
                break;
            case 4:
                score += 500*level;
                break;
            default:
                break;
        }
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setFont(font);
        g.drawString("TIME", textX, textY);
        g.drawString(String.format("%02d:%02d", startMinutes, startSeconds), textX, textY*2);
        g.drawString("SCORE", textX, (int)(textY*3.5));
        g.drawString(""+scoreFormat.format(score), textX, (int)(textY*4.5));
    }
    
    //làm mới thông số khi có yêu cầu làm mới game
    public void newGame() {
        this.score = 0;
        this.startMinutes = 0;
        this.startSeconds = 0;
        gameTimer.start();
        repaint();
    }

    public int getScore() {
        return score;
    }
}
