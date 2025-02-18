package maingame.tetrisblocks;

import java.awt.Color;
import maingame.Block;
import maingame.BLockType;

public class JBlock extends Block {
    
    public static final Color BLOCK_COLOR=new Color(78, 61, 164);
    public static final int[][] BLOCK_SHAPE = {{1,0,0},
                                            {1,1,1},
                                            {0,0,0}};

    public JBlock() {
        super(BLOCK_SHAPE,BLOCK_COLOR,4,-2,BLockType.NORMAL_BLOCK);
    }
    
}
