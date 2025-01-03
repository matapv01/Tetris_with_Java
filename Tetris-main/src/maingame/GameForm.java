package maingame;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import tetris.Tetris;

//**************************************
//Đây là lớp được dùng để quản lý cửa sổ game
//Lấy luồng điều khiển từ bàn phím
//**************************************
public class GameForm extends javax.swing.JFrame implements KeyListener {

    private GameForm gameForm;

    private HoldingBlock storedBlock;
    private GameArea gameArea;
    private BlockGenerator blockGenerator;
    private SpeedAndLevelSystem speedAndLevelSystem;
    private ScoreAndTimeSystem scoreAndTimeSystem;

    //Luồng game
    private GameThread gameThread;

    //biến này sẽ chứa tốc độ điều khiển
    private final int keyPressedDelay = 80;

    //Cho phép di chuyển hoặc không
    private boolean canMoveLeft, canMoveRight, canMoveDown, canRotateClockWise, canRotateCounterClockWise, canRotate180, canDrop, canStore;

    //Phương thức khởi tạo
    public GameForm() {
        initComponents();

        //Thêm ảnh vào Background của cửa sổ game
        JLabel jl = null;
        try {
            jl = new JLabel(new ImageIcon(ImageIO.read(new File("Icon\\background.jpg"))));
        } catch (IOException ex) {
            System.out.println("Lỗi tải ảnh");
        }
        this.setContentPane(jl);    //phủ kín màn hình bằng ảnh nền

        //Thêm chức năng di chuyển từ bàn phím
        addKeyListener(this);

        gameForm = this;

        //Khởi tạo các lớp cần thiết
        gameArea = new GameArea();
        blockGenerator = new BlockGenerator();
        storedBlock = new HoldingBlock();
        speedAndLevelSystem = new SpeedAndLevelSystem();
        scoreAndTimeSystem = new ScoreAndTimeSystem();
        gameThread = new GameThread(gameArea, speedAndLevelSystem, scoreAndTimeSystem);

        //Thêm các lớp extends từ Jpanel vào khung cửa sổ Jfame
        this.add(gameArea);
        this.add(blockGenerator);
        this.add(storedBlock);
        this.add(speedAndLevelSystem);
        this.add(scoreAndTimeSystem);

        //Cho gameArea tham chiếu đến các lớp cần thiết
        gameArea.addBlockGenerator(blockGenerator);
        gameArea.addStoredBlock(storedBlock);
        gameArea.addGameThread(gameThread);

        //Khởi tạo điều kiện điều khiển
        this.canRotateClockWise = true;
        this.canRotateCounterClockWise = true;
        this.canRotate180 = true;
        this.canDrop = true;
        this.canStore = true;

        //Lắng nghe thay đổi cửa sổ
        //Nếu có thay đổi thì cập nhật lại thông số cho các cửa sổ con bên trong
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                gameArea.updateAreaSize(gameForm.getBounds());
                blockGenerator.updateAreaSize(gameArea.getBounds());
                storedBlock.updateAreaSize(gameArea.getBounds());
                speedAndLevelSystem.updateAreaSize(gameArea.getBounds());
                scoreAndTimeSystem.updateAreaSize(gameArea.getBounds());
            }

        });

        //***********
        //Phần này sẽ kiểm tra luồng từ bàn phím sau mỗi (keyPressedDelay) thời gian
        //Và được lặp lại sau mỗi (keyPressedDelay) thời gian
        //***********
        Timer timer = new Timer(keyPressedDelay, event -> {
            moveBlock();
        });
        timer.setRepeats(true);
        timer.start();  // Bắt đầu đếm ngược

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tetris Project");
        setMinimumSize(new java.awt.Dimension(440, 480));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 762, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Thiết lập game Level
    public void setGameLevel(int gameLevel) {
        gameArea.setGameLevel(gameLevel);
    }

    //bắt đầu luồng game
    public void startGameThread() {
        gameThread.setExitGame();
        gameThread.interrupt();
        gameThread = new GameThread(gameArea, speedAndLevelSystem, scoreAndTimeSystem);
        gameArea.addGameThread(gameThread);
        gameThread.start();
    }

    //********************************
    //Phía dưới liên quan đến sử lý luồng vào của bàn phím
    //Đối với xoay khối gạch và thả khối gạch thì chỉ được thực hiện 1 lần mỗi lần ấn
    //tức 1 lần ấn và thả chỉ tính 1 lần, còn sang trái,phải,xuống thì cho phép giữ
    //*******************************
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SHIFT) {
            if (canRotate180) {
                canRotate180 = false;
                gameArea.rotateBlock180();
            }
        }
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP || key == KeyEvent.VK_X) {
            if (canRotateClockWise) {
                canRotateClockWise = false;
                gameArea.rotateBlockClockWise();
            }
        }
        if (key == KeyEvent.VK_Z) {
            if (canRotateCounterClockWise) {
                canRotateCounterClockWise = false;
                gameArea.rotateBlockCounterClockWise();
            }
        }
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            canMoveLeft = true;
        }
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            canMoveRight = true;
        }
        if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            canMoveDown = true;
        }
        if (key == KeyEvent.VK_SPACE) {
            if (canDrop) {
                canDrop = false;
                gameArea.dropBlock();
            }
        }
        if (key == KeyEvent.VK_C) {
            if (canDrop) {
                canStore = false;
                if (gameArea.holdOrSwapBlock()) {
                    gameThread.interrupt();
                }
            }
        }

        //ESC để quay trở lại menu
        if (key == KeyEvent.VK_ESCAPE) {
            Tetris.showMenu();
            gameThread.setExitGame();
            gameThread.interrupt();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SHIFT) {
            canRotate180 = true;
        }
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP || key == KeyEvent.VK_X) {
            canRotateClockWise = true;
        }
        if (key == KeyEvent.VK_Z) {
            canRotateCounterClockWise = true;
        }
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            canMoveLeft = false;
        }
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            canMoveRight = false;
        }
        if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            canMoveDown = false;
        }
        if (key == KeyEvent.VK_SPACE) {
            canDrop = true;
        }
        if (key == KeyEvent.VK_C) {
            canStore = true;
        }
    }

    private void moveBlock() {
        if (canMoveLeft) {
            gameArea.moveBlockLeft();
        }
        if (canMoveRight) {
            gameArea.moveBlockRight();
        }
        if (canMoveDown) {
            gameArea.moveBlockDown();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void keyTyped(KeyEvent e) {
    }
}
