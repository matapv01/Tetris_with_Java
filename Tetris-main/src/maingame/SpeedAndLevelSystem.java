package maingame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class SpeedAndLevelSystem extends JPanel {
    
    //cấp độ, và số hàng đã xoá
    private int level,LinesCleared;
    
    //Phông chữ
    private Font font;
    
    //Toạ độ chữ
    private int textX,textY;

    //Tốc độ theo từng level
    private final int[] speedLevel = {1000,793,618,473,355,262,190,135,94,64,43,28,18,11,7};
    
    //Số hàng cần xoá để chuyển level
    private final int linesToNextLevel = 12;
    
    public SpeedAndLevelSystem() {
        this.setOpaque(false);
        
        this.font = new Font("Calibri", Font.BOLD, 16);
        this.level = 0;
        this.LinesCleared = 0;
    }
    
    //Cập nhật kích thước và vị trí khi cửa sổ chương trình thay đổi kích thước
    //Ý tưởng:****************
    //Tương tự với HoldingBlock
    void updateAreaSize(Rectangle gameAreaSize) {

        //Xác định dài, rộng của khung
        int speedAndLevelSystemHeight = (int) (gameAreaSize.height * 0.26);
        int speedAndLevelSystemWidth = (int) (gameAreaSize.width * 0.5);
        
        //Xác định toạ độ của khung
        int speedAndLevelSystemX = gameAreaSize.x - speedAndLevelSystemWidth-(int)(gameAreaSize.width*0.02);
        int speedAndLevelSystemY = gameAreaSize.y + (int)(gameAreaSize.height*0.72);

        //Cập nhật vị trí và kích thước của khung
        this.setBounds(speedAndLevelSystemX, speedAndLevelSystemY, speedAndLevelSystemWidth, speedAndLevelSystemHeight);

        //Cập nhật kích thước phông chữ và vị trí
        font = font.deriveFont((float)(speedAndLevelSystemHeight*0.2));
        
        textX = (int)(speedAndLevelSystemHeight*0.2);
        textY = (int)(speedAndLevelSystemHeight*0.2);
        
    }
    
    //Tăng số hàng xoá được đồng thời kiểm tra xem có tăng được level không
    //đồng thời trả tốc độ tương ứng với level
    public int addLinesAndCheckNextLevel(int LinesClear) {
        LinesCleared+=LinesClear;
        if (LinesCleared>=linesToNextLevel) {
            LinesCleared-=linesToNextLevel;
            level++;
        }
        repaint();
        return speedLevel[level];
    }
    
    public int getLevel() {
        return level+1; 
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setFont(font);
        g.drawString("SPEED LV", textX, textY);
        if (level>=10) {
            g.drawString(String.valueOf(level+1), (int)(textX*3.85), textY*2);
        }
        else {
            g.drawString(String.valueOf(level+1), (int)(textX*4.36), textY*2);
        }
        g.drawString("LINES", (int)(textX*2.6), (int)(textY*3.5));
        g.drawString(String.format("%02d/%02d", LinesCleared,linesToNextLevel), (int)(textX*2.4), (int)(textY*4.5));
    }
    
    //Làm mới thông số khi có yêu cầu
    public void newGame() {
        this.level = 0;
        this.LinesCleared = 0;
        repaint();
    }
}
