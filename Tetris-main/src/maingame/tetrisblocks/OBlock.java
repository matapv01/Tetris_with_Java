package maingame.tetrisblocks;

import java.awt.Color;
import maingame.Block;
import maingame.BLockType;

public class OBlock extends Block {

    public static final Color BLOCK_COLOR=new Color(178, 152, 49);
    public static final int[][] BLOCK_SHAPE = {{0,0,0,0},
                                            {0,1,1,0},
                                            {0,1,1,0},
                                            {0,0,0,0}};

    public OBlock() {
        super(BLOCK_SHAPE,BLOCK_COLOR,4,-1,BLockType.NORMAL_BLOCK);
    }
    
}
