package maingame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class HoldingBlock extends JPanel {

    private int blockCellSize;
    
    private Block block;

    //Biến này sẽ lưu độ dày của viền
    private int innerMargin;
    
    private int textX,textY;
    
    private boolean hasSwappedHoldingBlock;

    private Font font;

    public HoldingBlock() {
        this.setBackground(Color.WHITE);
        this.hasSwappedHoldingBlock = false;

        block = null;

        font = new Font("Calibri", Font.PLAIN, 16);
    }

    //Phương thức lưu hoặc tráo đổi khối hiện đang điều khiển
    //Nếu chưa có khối nào lưu thì ta lưu khối hiện tai và trả true
    //Nếu không thì ta swap hai khối và trả false
    public Block holdOrSwapBlock(Block block) {
        if (this.block == null) {
            this.block = block;
            repaint();
            return null;
        } else {
            hasSwappedHoldingBlock = true;
            Block temp = this.block;
            this.block = block;
            repaint();
            return temp;
        }
    }

    public boolean hasSwappedHoldingBlock() {
        return hasSwappedHoldingBlock;
    }

    public void resetHasSwappedHoldingBlock() {
        hasSwappedHoldingBlock = false;
        repaint();
    }

    //Phương thức này sẽ vẽ khối gạch đang cầm lên màn hình
    //Ý tưởng tương tự với vẽ các khối gạch kế tiếp ở lớp BlockGenerator
    private void drawHoldingBlock(Graphics g) {
        
        int drawingAreaWidth = this.getWidth()-innerMargin*2;
        int drawingAreaHeight = this.getHeight()-innerMargin - blockCellSize;
        g.setColor(Color.BLACK);
        g.fillRect(innerMargin, blockCellSize, drawingAreaWidth, drawingAreaHeight);
        
        if (block == null) {
            return;
        }
        
        int blockMatrixSize = block.getBlockMatrixSize();
        int[][] blockShape = block.getInitialBlockShape();
        
        int drawingAreaX = (drawingAreaWidth - blockCellSize * blockMatrixSize) / 2;
        int drawingAreaY = blockCellSize * 2;

        if (blockMatrixSize == 4) {
            drawingAreaY -= blockCellSize;
        }

        Color ghostBlockColor = new Color(255, 225, 225, 50);

        for (int i = 0; i < blockMatrixSize; i++) {
            for (int j = 0; j < blockMatrixSize; j++) {
                if (blockShape[i][j] != 0) {

                    if (hasSwappedHoldingBlock) {
                        Block.drawBlockCells(g, drawingAreaX + j * blockCellSize, drawingAreaY + i * blockCellSize, blockCellSize, ghostBlockColor);
                    } else {
                        Block.drawBlockCells(g, drawingAreaX + j * blockCellSize, drawingAreaY + i * blockCellSize, blockCellSize, block.getBlockColor());
                    }
                }
            }
        }
    }

    //Cập nhật kích thước và vị trí khi cửa sổ chương trình thay đổi kích thước
    //Ý tưởng:***********************
    //Ta sẽ các định kích thước các thành phần bằng cách lấy tỷ lệ theo khung game (GameArea)
    void updateAreaSize(Rectangle gameAreaSize) {

        //Xác định dài, rộng của khung
        int holdingBlockHeight = (int) (gameAreaSize.height * 0.19);
        int holdingBlockWidth = (int) (gameAreaSize.width * 0.4);

        //Cập nhật độ dày của viền
        innerMargin = (int)(gameAreaSize.width*0.01);
        
        //Xác định toạ độ của khung
        int holdingBlockX = gameAreaSize.x - holdingBlockWidth-(int)(gameAreaSize.width*0.02);
        int holdingBlockY = gameAreaSize.y;

        //Cập nhật vị trí và kích thước của khung
        this.setBounds(holdingBlockX, holdingBlockY, holdingBlockWidth, holdingBlockHeight);

        //Xác định kích thước khối
        blockCellSize = (int) (holdingBlockWidth * 0.8) / 4;
        
        //Xác định kích vị trí, kích thước của chữ "HOLD"
        textX=(int) (holdingBlockWidth * 0.1);
        textY=(int) (holdingBlockWidth * 0.15);
        font = font.deriveFont((float)(blockCellSize));
    }

    //Phương thức kế thừa từ lớp Jpanel dùng để vẽ
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawHoldingBlock(g);

        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("HOLD", textX, textY);
    }
    
    void newGame() {
        
    }
}
