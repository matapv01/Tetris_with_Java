package maingame;

import maingame.effect.ClearLinesEffect;
import maingame.effect.DropBlockEffect;
import maingame.tetrisblocks.GachNo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;
import tetris.Tetris;

//**************************************
//Đây là lớp được dùng để vẽ màn hình game cũng như di chuyển gạch
//**************************************
public class GameArea extends JPanel {

    //biến này sẽ được sử dụng để lưu kích thước của một ô gạch (blockcell)
    private int blockCellsSize;

    //Khối gạch đang được điều khuyển
    private Block block;

    //Ma trận màu sắc để xác định các khối hiện có trong nền
    private Color[][] backgroundColorMatrix;

    //lớp sinh khối
    private BlockGenerator blockGenerator;

    //Lớp bảng giữ khối
    private HoldingBlock storedBlock;

    //lớp hiệu ứng thả khối
    private DropBlockEffect dropBlockEffect;

    //Luồng game
    private GameThread gameThread;

    //biến kiểm tra game kết thúc chưa
    private boolean gameOver;

    //biến lưu level game
    private int gameLevel;

    //Lớp quản lý màn chơi theo level
    private LevelMatrix levelMatrix;

    private GameOverPanel gameOverPanel;
    
    //Biến này sẽ lưu vị trí các hàng bị đầy
    //Tối đa ta có 4 hàng nên mảng sẽ có 4 giá trị
    //Nếu có ít hơn 4 hàng thì các giá trị trống để -1
    private int[] fullLinesList;

    //Hiệu ứng xoá hàng
    private ClearLinesEffect clearLinesEffect;

    //Phương thức khởi tạo
    public GameArea() {
        
        this.setLayout(null);
        
        this.setBackground(Color.black);

        //Lấy kích thước của ô vuông
        blockCellsSize = this.getHeight() / 20;

        //Khởi tạo các ô trong khu vực game là rỗng và ở ngoài là đã tồn tại
        this.backgroundColorMatrix = new Color[30][20];
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 20; j++) {
                if (i >= 20 || j >= 10) {
                    backgroundColorMatrix[i][j] = Color.black;
                }
            }
        }

        this.gameOver = false;
        this.dropBlockEffect = new DropBlockEffect(this);
        this.dropBlockEffect.start();
        clearLinesEffect = new ClearLinesEffect(blockCellsSize);
        fullLinesList = new int[4];
        fullLinesList[0] = fullLinesList[1] = fullLinesList[2] = fullLinesList[3] = -1;
        
        this.gameOverPanel = new GameOverPanel() {
            @Override
            public void onClose() {
                gameThread.interrupt();
            }
        };
        this.add(gameOverPanel);
        gameOverPanel.setVisible(false);
    }

    //Thay đổi kích thước màn hình game khi cửa sổ chương trình thay đổi kích thước
    public void updateAreaSize(Rectangle gameFormSize) {
        int gameAreaHeight = (int) (gameFormSize.height * 0.8);

        blockCellsSize = gameAreaHeight / 20;

        gameAreaHeight = blockCellsSize * 20;
        int gameAreaWidth = blockCellsSize * 10;

        int GameAreaX = gameFormSize.width / 2 - blockCellsSize * 5;
        int GameAreaY = (gameFormSize.height - gameAreaHeight) / 2;

        this.setBounds(GameAreaX, GameAreaY, gameAreaWidth, gameAreaHeight);

        clearLinesEffect.setBlockCellsSize(blockCellsSize);
    }

    //Tham chiếu đến lớp sinh khối
    public void addBlockGenerator(BlockGenerator blockGenerator) {
        this.blockGenerator = blockGenerator;
    }

    //Tham chiếu đến bảng lưu khối
    public void addStoredBlock(HoldingBlock storedBlock) {
        this.storedBlock = storedBlock;
    }

    //Tham chiếu đến luồng game để thực hiện ngắt chờ khi có khối gạch chạm đáy
    public void addGameThread(GameThread gameThread) {
        this.gameThread = gameThread;
    }

    //thêm level
    public void setGameLevel(int gameLevel) {
        this.gameLevel = gameLevel;
        levelMatrix = new LevelMatrix(gameLevel);
        
    }
    
    public int getGameLevel() {
        return gameLevel;
    }
    
    //Hiển thị gameOver
    public void showGameOverPanel(int score) {
        gameOverPanel.setBounds((int)(this.getWidth()*0.05), (int)(this.getHeight()*0.3), (int)(this.getWidth()*0.9), (int)(this.getHeight()*0.25));
        gameOverPanel.setVisible(true);
        gameOverPanel.setScore(score);
    }
    
    //Thực hiện lưu hoặc swap block
    //Ý tưởng:*********************
    //+Đầu tiên ta cần kiểm tra xem khối đã được swap lần nào hay chưa,
    //nếu rồi thì trả false
    //+Nếu chưa thì ta gọi phương thức .holdOrSwapBlock() của lớp StoredBlock,
    //nếu trả về null tức từ trước chưa giữ viên gạch nào và mới cho vào, khi đó
    //ta sẽ tạo viên gạch mới
    //nếu khác null tức đã swap gạch, ta reset viên gạch mới swap đó về vị trí thả gạch
    //*****************************
    public boolean holdOrSwapBlock() {
        if (gameOver)   return false;
        
        if (storedBlock.hasSwappedHoldingBlock()) {
            return false;
        }
        block = storedBlock.holdOrSwapBlock(block);
        Tetris.playHoldblockSoundEffect();
        
        if (block == null) {
            createNewBlock();
        } else {
            block.resetPosition();
        }
        return true;
    }

    //Tạo khối gạch mới
    public void createNewBlock() {
        if (gameLevel > 0) {
            levelMatrix.setBlockGenerator(blockGenerator);
        }

        block = blockGenerator.getNextBlock();

        //Cho block tham chiếu đến ma trận màu sắc
        block.setBackgroundColorMatrix(backgroundColorMatrix);

        storedBlock.resetHasSwappedHoldingBlock();

    }

    //Khởi tạo game mới
    public void newGame() {
        levelMatrix.setLevelMatrix(backgroundColorMatrix);
        gameOver = false;
        blockGenerator.newGame();
        gameOverPanel.setVisible(false);
    }

    //Kiểm tra xem có hoàn thành phòng tập chưa
    //Phải tạo phương thức mới mà không tích hợp với newGame là bởi:
    //Newgame là khi có khối chạm trần mà ở trong phòng tập thì không
    //có trường hợp đấy vì luôn rest lại phòng mỗi khi block không đúng vị trí
    private void checkTrainingCompletion() {
        checkAndCountFullLines();
        if (levelMatrix.isBlockPositionCorrect(fullLinesList)) {
            Tetris.nextLevel(gameLevel);
        }
        gameOver = true;
    }

    //Di chuyển gạch xuống dưới
    public boolean moveBlockDown() {
        if (!gameOver && block.moveDown()) {
            repaint();  //Sau khi di chuyển xuống thì "vẽ" lại màn hình
            return true;
        }
        //Nếu gạch không xuống được dưới nữa thì chuyển vào nền
        embedBlockIntoMatrix();
        return false;
    }

    //Di chuyển gạch sang trái
    public boolean moveBlockLeft() {
        if (!gameOver && block.moveLeft()) {
            repaint();  //Sau khi di chuyển trái thì "vẽ" lại màn hình
            return true;
        }
        return false;
    }

    //Di chuyển gạch sang phải
    public boolean moveBlockRight() {
        if (!gameOver && block.moveRight()) {
            repaint();  //Sau khi di chuyển phải thì "vẽ" lại màn hình
            return true;
        }
        return false;
    }

    //Thả khối gạch xuống
    public boolean dropBlock() {
        if (!gameOver && block.drop()) {
            dropBlockEffect.setDrop();  //Hiệu ứng thả gạch
            embedBlockIntoMatrix(); //Cho khối gạch vào ma trận nền
            repaint(); 
            Tetris.playBlockdownSoundEffect();
            return true;
        }
        return false;
        
    }

    //Xoay khối gạch theo chiều kim đồng hồ
    public void rotateBlockClockWise() {
        if (gameThread.checkBlockAlive() == false) {
            return;
        }
        if (!gameOver && block.rotateClockWise()) {
            repaint();
            Tetris.playRotateblockSoundEffect();
        }
    }

    //Xoay khối gạch ngược chiều kim đồng hồ
    public void rotateBlockCounterClockWise() {
        if (gameThread.checkBlockAlive() == false) {
            return;
        }
        if (!gameOver && block.rotateCounterClockWise()) {
            repaint();
            Tetris.playRotateblockSoundEffect();
        }
    }
    
    //Xoay khối gạch 180 độ
    public void rotateBlock180() {
        if (gameThread.checkBlockAlive() == false) {
            return;
        }
        if (!gameOver && block.rotate180()) {
            repaint();
            Tetris.playRotateblockSoundEffect();
        }
    }

    //Kiểm tra có hàng nào đầy không
    //Đồng thời trả về số lượng hàng đầy
    public int checkAndCountFullLines() {
        int t = 0;
        for (int i = 0; i < 20; i++) {
            boolean check = true;
            for (int j = 0; j < 10; j++) {
                if (backgroundColorMatrix[i][j] == null) {
                    check = false;
                    break;
                }
            }
            if (check) {
                fullLinesList[t++] = i;
            }
        }

        if (t == 4) {
            clearLinesEffect.setIs4Line(true);
        }
        if (t!=0) {
            Tetris.playClearSoundEffect();
        }
        return t;
    }

    //Kiểm tra game kết thúc
    public boolean checkGameOver() {
        return gameOver;
    }

    //Đẩy các hàng ở phía trên hàng bị xoá xuống
    public void dropBlocksAboveClearedLines() {
        for (int rowToRemove : fullLinesList) {

            if (rowToRemove == -1) {
                break;
            }

            for (int i = rowToRemove; i > 0; i--) {
                for (int j = 0; j < 10; j++) {
                    backgroundColorMatrix[i][j] = backgroundColorMatrix[i - 1][j];
                }
            }
            for (int j = 0; j < 10; j++) {
                backgroundColorMatrix[0][j] = null;
            }
        }
        fullLinesList[0] = fullLinesList[1] = fullLinesList[2] = fullLinesList[3] = -1;
        repaint();
        clearLinesEffect.setIs4Line(false);

        //Thêm hiệu ứng khối rơi khi đầy hàng để cho đẹp hơn =))
        dropBlockEffect.setDrop();
    }

    //Chuyển khối gạch vào nền, đồng thời thực hiện ngắt chờ của luồng game.
    private void embedBlockIntoMatrix() {
        if (block.getBlockType() == BLockType.TNT_BLOCK) {
            GachNo.noGach(backgroundColorMatrix, block.getX(), block.getY());
            repaint();
            return;
        }

        //Lấy các tham số của khối gạch
        int blockMatrixSize = block.getBlockMatrixSize();
        int[][] blockShape = block.getCurrentBlockShape();
        int blockX = block.getX();
        int blockY = block.getY();

        //Lặp qua ma trận hình dạng của khối gạch
        //Nếu là 1 thì cho màu của khối gạch vào ma trận nền tại ô đó
        for (int i = 0; i < blockMatrixSize; i++) {
            for (int j = 0; j < blockMatrixSize; j++) {
                if (blockShape[i][j] == 1) {
                    if (blockY + i < 0 || blockX + j < 0) {
                        gameOver = true;
                        return;
                    }
                    backgroundColorMatrix[blockY + i][blockX + j] = block.getBlockColor();
                }
            }
        }

        //Game level=0 sẽ là chế độ thường còn từ 1->.. là chế độ luyện tập
        if (gameLevel > 0) {
            checkTrainingCompletion();
        }

        //Sau khi cho khối gạch vào ma trận nền thì ngắt sleep của gameThread
        //để sinh ra khối mới ngay lập.
        gameThread.interrupt();
    }

    //Vẽ khối gạch hiện tại đang được điều khuyển
    private void drawActiveBlock(Graphics g) {

        int BlockMatrixSize = block.getBlockMatrixSize();
        int[][] BlockShape = block.getCurrentBlockShape();
        int blockX = block.getX();
        int blockY = block.getY();
        int ghostBlockY = block.getGhostBlockY();
        Color ghostBlockColor = new Color(255, 225, 225, 50);

        for (int i = 0; i < BlockMatrixSize; i++) {
            for (int j = 0; j < BlockMatrixSize; j++) {
                if (BlockShape[i][j] != 0) {

                    //Vẽ khối gạch ảo
                    Block.drawBlockCells(g, (blockX + j) * blockCellsSize, (ghostBlockY + i) * blockCellsSize, blockCellsSize, ghostBlockColor);

                    //Vẽ khối gạch thật
                    Block.drawBlockCells(g, (blockX + j) * blockCellsSize, (blockY + i) * blockCellsSize, blockCellsSize, block.getBlockColor());

                }
            }
        }
    }

    //Phương thức kế thừa từ lớp Jpanel dùng để vẽ
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fullLinesList[0] == -1 && fullLinesList[1] == -1 && fullLinesList[2] == -1 && fullLinesList[3] == -1) {
            drawActiveBlock(g);
        }

        for (int Y = 0; Y < 20; Y++) {
            for (int X = 0; X < 10; X++) {
                if (Y == fullLinesList[0] || Y == fullLinesList[1] || Y == fullLinesList[2] || Y == fullLinesList[3]) {
                    clearLinesEffect.drawFrame(g, Y);
                } else {
                    //Vẽ khung nền
                    g.setColor(new Color(32, 33, 30));
                    g.drawRect(X * blockCellsSize, Y * blockCellsSize, blockCellsSize, blockCellsSize);

                    if (backgroundColorMatrix[Y][X] != null) {
                        //Vẽ khối
                        Block.drawBlockCells(g, X * blockCellsSize, Y * blockCellsSize, blockCellsSize, backgroundColorMatrix[Y][X]);

                    }
                }

            }
        }
    }

    //Hiệu ứng xoá hàng
    //Phương thức này sẽ được gọi liên tục trong GameThread khi có hàng đầy
    //Mỗi lần gọi phương thức sẽ sinh ra một frame mới
    //Do gọi phương thức liên tục nên sẽ tạo ra hiệu ứng nháy trên màn hình
    public void startClearLinesEffect() {
        repaint();
        clearLinesEffect.nextFrameColorIndex();
    }
}
