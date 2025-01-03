package maingame.effect;

import java.awt.Color;
import java.awt.Graphics;
import maingame.Block;

public class ClearLinesEffect {

    private int blockCellsSize;
    private Color[] alphaColor;
    private volatile int currentAlphaIndex;
    private boolean is4Line;

    public ClearLinesEffect(int blockCellsSize) {
        //Xác định kích thước pixcel của khối
        this.blockCellsSize = blockCellsSize;
        
        //Cập nhật bảng màu hiệu ứng
        alphaColor = new Color[50];
        currentAlphaIndex = 0;
        for (int i = 0; i < 20; i++) {
            alphaColor[i] = new Color(255, 255, 255, i * 5);
        }
        for (int i = 20; i < 40; i++) {
            alphaColor[i] = alphaColor[39 - i];
        }
        for (int i = 40; i < 50; i++) {
            alphaColor[i] = Color.BLACK;
        }
        
        is4Line = false;
    }

    //Cập nhât lại kích thước khối (dùng khi kích thước của sổ bị thay đổi)
    public void setBlockCellsSize(int blockCellsSize) {
        this.blockCellsSize = blockCellsSize;
    }

    //Vẽ frame hiệu ứng tương ứng với màu tại currentAlphaIndex
    public void drawFrame(Graphics g, int linePos) {
        for (int i = 0; i < 15; i++) {
            Block.drawBlockCells(g, i * blockCellsSize, linePos * blockCellsSize, blockCellsSize, alphaColor[currentAlphaIndex]);
        }
    }

    //Chuyển sang màu kế tiếp
    public void nextFrameColorIndex() {
        currentAlphaIndex = (currentAlphaIndex+1)%50;
    }

    public void setIs4Line(boolean set) {
        this.is4Line = set;
    }
}
