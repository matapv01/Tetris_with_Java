package maingame.tetrisblocks;

import java.awt.Color;
import maingame.Block;
import maingame.BLockType;

public class GachNo extends Block {
       
    private static final int[][] HINH_DANG = {{1,1},
                                              {1,1}};
    public GachNo() {
        super(HINH_DANG ,Color.RED,4,-1,BLockType.TNT_BLOCK);
    }
    
    public static void noGach(Color[][] maTranNen, int x, int y) {
        int[][] khuVucNo = {{0,1,1,1,1,0},
                            {1,1,1,1,1,1},
                            {1,1,1,1,1,1},
                            {0,1,1,1,1,0},
                            {0,0,1,1,0,0}};
        x-=2;
        for (int i=0;i<5;i++) {
            for (int j=0;j<6;j++) {
                if (x+j>=0 && y+i>=0 && khuVucNo[i][j]!=0) {
                    maTranNen[y+i][x+j]=null;
                }
            }
        }
    }
}