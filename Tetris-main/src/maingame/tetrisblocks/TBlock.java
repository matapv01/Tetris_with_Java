package maingame.tetrisblocks;

import java.awt.Color;
import maingame.Block;
import maingame.BLockType;

public class TBlock extends Block {
    
    public static final Color BLOCK_COLOR=new Color(205 , 58, 191);
    public static final int[][] BLOCK_SHAPE = {{0,1,0},
                            {1,1,1},
                            {0,0,0}};

    public TBlock() {
        super(BLOCK_SHAPE,BLOCK_COLOR,4,-2,BLockType.NORMAL_BLOCK);
    }
}
