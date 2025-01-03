package maingame.tetrisblocks;

import java.awt.Color;
import maingame.Block;
import maingame.BLockType;

public class SBlock extends Block {

    public static final Color BLOCK_COLOR=new Color(130 , 179, 49);
    public static final int[][] BLOCK_SHAPE = {{0,1,1},
                            {1,1,0},
                            {0,0,0}};

    public SBlock() {
        super(BLOCK_SHAPE,BLOCK_COLOR,4,-2,BLockType.NORMAL_BLOCK);
    }
    
}
