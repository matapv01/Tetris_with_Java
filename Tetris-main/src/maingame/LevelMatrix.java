package maingame;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.Scanner;

//******
//Lớp này được sử dụng để quản lý màn chơi theo từng level
//******

public class LevelMatrix {
    
    private String blockName;
    private int[] correctBlockPosition;
    private Color[][] levelBackgroundColorMatrix;
    
    public LevelMatrix(int gameLevel) {
        correctBlockPosition = new int[4];
        levelBackgroundColorMatrix = new Color[30][20];
        readFile(gameLevel);
    }
    
    //Đọc giữ liệu level từ file
    private void readFile(int level) {
        File levelFile = null;
        Scanner sc = null;
        try {
            
            String levelFilePatch = String.format("Tetris Level Map/Level_%d.txt", level);
            System.out.println(level+".txt | now ="+LocalTime.now());
            levelFile = new File(levelFilePatch);
            sc = new Scanner(levelFile);
            
        } catch (FileNotFoundException ex) {
            System.out.println("KHONG DOC DUOC FILE"+level+".txt | now ="+LocalTime.now());
        }
        blockName = sc.next();
        for (int i=0;i<4;i++) {
            correctBlockPosition[i] = sc.nextInt();
        }
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 10; x++) {
                if (sc.hasNext()==false) {
                    System.out.println("SC rong");
                }
                if (sc.nextInt() == 0) {
                    levelBackgroundColorMatrix[y][x] = null;
                } else {
                    levelBackgroundColorMatrix[y][x] = Color.GRAY;
                }
            }
        }
    }
    
    //Thiết lập các block trong bảng sinh khối toàn khối được chọn
    public void setBlockGenerator(BlockGenerator blockGenerator) {
        if (blockName.equals("TBlock")) {
            blockGenerator.fillQueueWithTBlock();
        }
        else if (blockName.equals("IBlock")) {
            blockGenerator.fillQueueWithIBlock();
        }
        else if (blockName.equals("JBlock")) {
            blockGenerator.fillQueueWithJBlock();
        }
        else if (blockName.equals("LBlock")) {
            blockGenerator.fillQueueWithLBlock();
        }
        else if (blockName.equals("OBlock")) {
            blockGenerator.fillQueueWithOBlock();
        }
        else if (blockName.equals("SBlock")) {
            blockGenerator.fillQueueWithSBlock();
        }
        else if (blockName.equals("ZBlock")) {
            blockGenerator.fillQueueWithZBlock();
        }
        
    }
    
    //Thiết lập giá trị cho ma trận theo level
    public void setLevelMatrix(Color[][] backgroundColorMatrix) {
        for (int y = 0; y < 30; y++) {
            for (int x = 0; x < 10; x++) {
                backgroundColorMatrix[y][x] = levelBackgroundColorMatrix[y][x];
            }
        }
        
    }
    
    //Kiểm tra xem block có đúng chỗ không
    public boolean isBlockPositionCorrect(int[] fullLinesList) {
        for (int i=0;i<4;i++) {
            if (fullLinesList[i]!=correctBlockPosition[i]) {
                return false;
            }
        }
        return true;
    }


}
