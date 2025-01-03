package gamemenu;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import loginandsignup.Player;
import tetris.Tetris;

public class Leaderboard extends JFrame {

    private JPanel panel;

    public Leaderboard() {
        super("TOP 20");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(0x24384d));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);

        setSize(400, 600);
    }

    public void loadAndDisplay() {
        setLocationRelativeTo(null);
        setSize(640, 720);
        setResizable(false);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 60, 5, 60);

        panel.setBackground(new Color(0x24384d));

        int maxLeaderboardPlayer = 20;

        // Tạo một danh sách Player với các dữ liệu mẫu
        ArrayList<Player> players = Tetris.getLeaderboard(maxLeaderboardPlayer);

        int rank = 0;

        for (Player player : players) {

            JLabel rankLabel = createLabel(String.valueOf(rank + 1), SwingConstants.LEFT, new Color(0xc8cdd0), new Font("Arial", Font.PLAIN, 14));
            JLabel nameLabel = createLabel(player.getUsername(), SwingConstants.LEFT, new Color(0xd9dadc), new Font("Arial", Font.BOLD, 14));

            JPanel scorePanel = new JPanel();
            scorePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
            scorePanel.setBackground(new Color(0x24384d));
            JLabel trophyLabel = new JLabel(new ImageIcon(new ImageIcon("Icon\\OIP.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
            trophyLabel.setHorizontalAlignment(SwingConstants.LEFT);
            scorePanel.add(trophyLabel);

            JLabel scoreLabel = createLabel(String.valueOf(player.getScore()), SwingConstants.LEFT, new Color(0xc8cdd0), new Font("Arial", Font.PLAIN, 14));
            scorePanel.add(scoreLabel);

            gbc.gridx = 0;
            gbc.gridy = rank;
            panel.add(rankLabel, gbc);

            gbc.gridx = 1;
            panel.add(nameLabel, gbc);

            gbc.gridx = 2;
            panel.add(scorePanel, gbc);

            rank++;
        }
    }

    public void clearPanel() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

    private JLabel createLabel(String text, int alignment, Color color, Font font) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(alignment);
        label.setForeground(color);
        label.setFont(font);
        return label;
    }
}
