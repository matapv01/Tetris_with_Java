
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HeLi
 */
public class NewClass extends JFrame {

    private JPanel start;
    
    public NewClass() throws HeadlessException {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(0, 0, 1000, 1000);

        start = new JPanel();
        start.setPreferredSize(new Dimension(200,200));
        start.setBackground(Color.red);
        
        this.getContentPane().add(BorderLayout.CENTER,start);
        start.add(new JLabel("SDFSD"));
        
        
        
    }
    
}
