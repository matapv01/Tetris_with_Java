package maingame.tetrisblocks;

import java.awt.Color;
import maingame.Block;
import maingame.BLockType;

public class IBlock extends Block {
    
    public static final Color BLOCK_COLOR=new Color(49, 178, 130);
    public static final int[][] BLOCK_SHAPE = {{0,0,0,0},
                                            {1,1,1,1},
                                            {0,0,0,0},
                                            {0,0,0,0}};
    
    public IBlock() {
        super(BLOCK_SHAPE,BLOCK_COLOR,3,-2,BLockType.I_BLOCK);
    }
    
}
