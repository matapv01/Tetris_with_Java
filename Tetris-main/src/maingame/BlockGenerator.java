package maingame;

import maingame.tetrisblocks.*;
import java.awt.Color;
import java.awt.Font;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import javax.swing.JPanel;

//**************************************
//Lớp này sẽ được sử dụng để sinh ra các khối gạch khác nhau.
//Đồng thời sẽ vẽ các khối gạch tiếp theo lên trên bảng xem trước (BlockGeneratorPanel)
//**************************************
public class BlockGenerator extends JPanel {

    //Để lấy khối gạch ở đầu đồng thời cho khối gạch khác vào cuối thì ta sử dụng hàng đợi
    private Queue<Block> nextBlocksQueue;
    
    private static final char[] blockSpawnRates = {'I','J','L','O','S','T','T','Z'};

    private Font font;
    
    //Biến này sẽ lưu độ dày của viền
    private int innerMargin;
    
    private int textX,textY;

    private int blockCellSize;

    //Phương thức khởi tạo
    public BlockGenerator() {

        this.setBackground(Color.WHITE);
        nextBlocksQueue = new LinkedList<>();

        font = new Font("Calibri", Font.PLAIN, 16);

        blockCellSize = (int) (this.getWidth() * 0.8) / 4;

    }

    //Trả về khối đầu tiên trong hàng đợi đồng thời sinh ra khối khác để thay thế
    public Block getNextBlock() {
        if (nextBlocksQueue.isEmpty()) {
            nextBlocksQueue.add(this.generateRandomBlock());
            nextBlocksQueue.add(this.generateRandomBlock());
            nextBlocksQueue.add(this.generateRandomBlock());
            nextBlocksQueue.add(this.generateRandomBlock());
            nextBlocksQueue.add(this.generateRandomBlock());
        }
        nextBlocksQueue.add(this.generateRandomBlock());
        Block newBlock = nextBlocksQueue.poll();

        //Sau khi lấy ra thì ta vẽ lại màn hình các khối mới
        repaint();
        return newBlock;
    }

    //Phương thức này sẽ vẽ các khối gạch có trong hàng đợi lên màn hình
    private void drawQueueBlocks(Graphics g) {

        //Chuyển các khối gạch trong hàng đợi sang dạng mảng
        Block[] queueBlocks = nextBlocksQueue.toArray(new Block[0]);

        //****************************
        //Tính toán kích thước khối dựa trên kích thước của sổ vẽ hiện tại
        //Sau đó lặp qua danh sách các khối ở trên rồi vẽ từng khối
        //Mỗi khối gạch các nhau một khối gạch con (blockCell)
        //****************************
        int drawingAreaWidth = this.getWidth() - innerMargin * 2;
        int drawingAreaHeight = this.getHeight() - innerMargin - blockCellSize;

        int drawingAreaY = blockCellSize * 2;

        g.setColor(Color.BLACK);
        g.fillRect(innerMargin, blockCellSize, drawingAreaWidth, drawingAreaHeight);

        for (int t = 0; t < 5; t++) {

            int blockMatrixSize = queueBlocks[t].getBlockMatrixSize();
            int[][] blockShape = queueBlocks[t].getCurrentBlockShape();
            int drawingAreaX = (drawingAreaWidth - blockCellSize * blockMatrixSize) / 2;

            //Do khối I có kích cỡ ma trận là 4 và 2 ô trên trống nên ta lùi lại 1 ô để các ô cách nhau đúng 1 khối gạch con
            if (blockMatrixSize == 4) {
                drawingAreaY -= blockCellSize;
            }

            for (int i = 0; i < blockMatrixSize; i++) {
                for (int j = 0; j < blockMatrixSize; j++) {
                    if (blockShape[i][j] != 0) {

                        //Gọi phương thức trong lớp Block để vẽ ô gạch con (blockcell)
                        Block.drawBlockCells(g, drawingAreaX + j * blockCellSize, drawingAreaY + i * blockCellSize, blockCellSize, queueBlocks[t].getBlockColor());
                    }
                }
            }
            drawingAreaY += blockCellSize * blockMatrixSize;
        }
    }

    public void dayKhoiGachNoVaoHangDoi() {
        nextBlocksQueue.poll();
        nextBlocksQueue.add(this.taoKhoiGachNo());
    }

    //Phương thức sinh ra một khối gạch bất kì
    public Block generateRandomBlock() {
        Random randomBlock = new Random();
        switch (blockSpawnRates[randomBlock.nextInt(blockSpawnRates.length)]) {
            case 'I':
                return this.generateIBlock();
            case 'L':
                return this.generateLBlock();
            case 'J':
                return this.generateJBlock();
            case 'T':
                return this.generateTBlock();
            case 'O':
                return this.generateOBlock();
            case 'Z':
                return this.generateZBlock();
            case 'S':
                return this.generateSBlock();

            default:
                throw new AssertionError();
        }
    }
    
    public void fillQueueWithTBlock() {
        nextBlocksQueue.clear();
        nextBlocksQueue.add(this.generateTBlock());
        nextBlocksQueue.add(this.generateTBlock());
        nextBlocksQueue.add(this.generateTBlock());
        nextBlocksQueue.add(this.generateTBlock());
        nextBlocksQueue.add(this.generateTBlock());
    }

    public void fillQueueWithIBlock() {
        nextBlocksQueue.clear();
        nextBlocksQueue.add(this.generateIBlock());
        nextBlocksQueue.add(this.generateIBlock());
        nextBlocksQueue.add(this.generateIBlock());
        nextBlocksQueue.add(this.generateIBlock());
        nextBlocksQueue.add(this.generateIBlock());
    }

    public void fillQueueWithLBlock() {
        nextBlocksQueue.clear();
        nextBlocksQueue.add(this.generateLBlock());
        nextBlocksQueue.add(this.generateLBlock());
        nextBlocksQueue.add(this.generateLBlock());
        nextBlocksQueue.add(this.generateLBlock());
        nextBlocksQueue.add(this.generateLBlock());
    }

    public void fillQueueWithJBlock() {
        nextBlocksQueue.clear();
        nextBlocksQueue.add(this.generateJBlock());
        nextBlocksQueue.add(this.generateJBlock());
        nextBlocksQueue.add(this.generateJBlock());
        nextBlocksQueue.add(this.generateJBlock());
        nextBlocksQueue.add(this.generateJBlock());
    }

    public void fillQueueWithZBlock() {
        nextBlocksQueue.clear();
        nextBlocksQueue.add(this.generateZBlock());
        nextBlocksQueue.add(this.generateZBlock());
        nextBlocksQueue.add(this.generateZBlock());
        nextBlocksQueue.add(this.generateZBlock());
        nextBlocksQueue.add(this.generateZBlock());
    }

    public void fillQueueWithSBlock() {
        nextBlocksQueue.clear();
        nextBlocksQueue.add(this.generateSBlock());
        nextBlocksQueue.add(this.generateSBlock());
        nextBlocksQueue.add(this.generateSBlock());
        nextBlocksQueue.add(this.generateSBlock());
        nextBlocksQueue.add(this.generateSBlock());
    }

    public void fillQueueWithOBlock() {
        nextBlocksQueue.clear();
        nextBlocksQueue.add(this.generateOBlock());
        nextBlocksQueue.add(this.generateOBlock());
        nextBlocksQueue.add(this.generateOBlock());
        nextBlocksQueue.add(this.generateOBlock());
        nextBlocksQueue.add(this.generateOBlock());
    }


    public Block generateTBlock() {
        return new TBlock();
    }

    public Block generateIBlock() {
        return new IBlock();
    }

    public Block generateLBlock() {
        return new LBlock();
    }

    public Block generateJBlock() {
        return new JBlock();
    }

    public Block generateZBlock() {
        return new ZBlock();
    }

    public Block generateSBlock() {
        return new SBlock();
    }

    public Block generateOBlock() {
        return new OBlock();
    }

    public Block taoKhoiGachNo() {
        return new GachNo();
    }

    //Phương thức kế thừa từ lớp Jpanel dùng để vẽ
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawQueueBlocks(g);

        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("NEXT", textX, textY);
    }

    //Cập nhật kích thước và vị trí khi cửa sổ chương trình thay đổi kích thước
    //Ý tưởng:****************
    //Tương tự với HoldingBlock
    void updateAreaSize(Rectangle gameAreaSize) {

        int blockGeneratorHeight = (int) (gameAreaSize.height * 0.68);
        int blockGeneratorWidth = (int) (gameAreaSize.width * 0.4);

        innerMargin = (int) (blockGeneratorWidth * 0.03);

        int blockGeneratorX = gameAreaSize.x + gameAreaSize.width + (int) (gameAreaSize.width * 0.02);
        int blockGeneratorY = gameAreaSize.y;

        this.setBounds(blockGeneratorX, blockGeneratorY, blockGeneratorWidth, blockGeneratorHeight);

        blockCellSize = (int) (blockGeneratorWidth * 0.8) / 4;
        
        //Xác định kích vị trí, kích thước của chữ "NEXT"
        textX=(int) (blockGeneratorWidth * 0.1);
        textY=(int) (blockGeneratorWidth * 0.15);
        font = font.deriveFont((float)(blockCellSize));
    }
    
    //Làm mới hàng đợi khối khi có yêu cầu
    public void newGame() {
        nextBlocksQueue.clear();
        this.getNextBlock();
    }
}
