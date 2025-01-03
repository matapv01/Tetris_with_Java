package maingame;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;

//**************************************
//Lớp này sẽ được sử dụng để hiện OverGame cùng điểm số
//**************************************
public abstract class GameOverPanel extends JTextPane {

    //Định dạng số có ',' ngăn cách
    private DecimalFormat df;

    public GameOverPanel() {
        this.setContentType("text/html");
        this.setBackground(Color.BLACK);

        this.setBorder(new LineBorder(Color.WHITE, 2, true));

        df = new DecimalFormat("###,###");
        
        //Lắng nghe chuột ấn để ẩn thông báo
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GameOverPanel.this.setVisible(false);
                onClose();
            }
        });

        this.setFocusable(false);
    }
    
    public void setScore(int score) {
        this.setText("<p style='margin-right: 0mm; margin-bottom: 0mm; font-size: 11pt; font-family: \"Calibri\", sans-serif; line-height: 0.3; text-align: center;'><span style=\"font-size: 24px; font-family: Helvetica; color: rgb(239, 239, 239);\">GAME OVER</span></p>\n"
                + "<p style='margin-right: 0mm; margin-bottom: 0mm; font-size: 11pt; font-family: \"Calibri\", sans-serif; line-height: 0.3; text-align: center;'><span style=\"font-size: 24px; font-family: Helvetica; color: rgb(239, 239, 239);\">SCORE: " + df.format(score) + "</span><span style=\"font-size: 15px; font-family: Helvetica; color: rgb(239, 239, 239);\"><br></span></p>\n"
                + "<div style=\"all: initial;\" class=\"notranslate\"><br></div>");
    }

    public abstract void onClose();
}
