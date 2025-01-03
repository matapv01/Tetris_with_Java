package maingame.tetrisblocks;

import java.awt.Color;
import maingame.Block;
import maingame.BLockType;

public class ZBlock extends Block {

    public static final Color BLOCK_COLOR=new Color(179 , 51, 58);
    public static final int[][] BLOCK_SHAPE = {{1,1,0},
                                                {0,1,1},
                                                {0,0,0}};

    public ZBlock() {
        super(BLOCK_SHAPE,BLOCK_COLOR,4,-2,BLockType.NORMAL_BLOCK);
    }
    
}
