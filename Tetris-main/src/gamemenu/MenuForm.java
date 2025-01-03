package gamemenu;

import java.awt.*;
import loginandsignup.Player;

public class MenuForm extends javax.swing.JFrame {
    private MainScreen mainScreen;
    private UserPanel userPanel;
    
    public MenuForm(Rectangle bounds, Player player) {
        initComponents();
        this.setBounds(bounds);
        mainScreen = new MainScreen(new Dimension(bounds.width, bounds.width / 2));  //Menu chính
        userPanel = new UserPanel(new Dimension(bounds.width, bounds.height - bounds.width / 2), player);   //Thanh người chươi
        this.setLayout(new BorderLayout());
        this.add(BorderLayout.CENTER, mainScreen);
        this.add(BorderLayout.NORTH, userPanel);
        this.setResizable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void refreshBestScore() {
        userPanel.updatePlayerInfo();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
