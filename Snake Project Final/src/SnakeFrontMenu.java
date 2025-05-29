import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.List;

public class SnakeFrontMenu extends JFrame {
    private String playerName;
    private Color snakeColor = Color.GREEN;
    private int speed = 150;
    GamePanel gamePanel;

    public SnakeFrontMenu() {
        gamePanel = new GamePanel();
        setTitle("Venom Trails: Clash Mode");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(new BackgroundPanel());
        setIconImage(new ImageIcon("logo2.png").getImage());
        setLayout(null);

        JLabel title = new JLabel("Venom Trails");
        title.setFont(new Font("Impact", Font.BOLD, 48));
        title.setForeground(Color.yellow);
        title.setBounds(270, 50, 400, 50);
        add(title);

        String[] buttons = {"Start Game", "Rules", "Customize Snake", "High Scores", "Exit"};
        int y = 150;

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setBounds(300, y, 200, 40);
            button.setFocusPainted(false);
            button.setBackground(Color.decode("#2f4f4f"));
            button.setForeground(Color.decode("#00FF7F"));
            button.setFont(new Font("Cooper Black", Font.BOLD, 19));
            button.setBorder(new LineBorder(Color.decode("#00FF7F"), 1, true)); // last `true` makes corners rounded

            add(button);

            if (text.equals("Start Game")) {
                button.addActionListener(e -> startGame());
            } else if (text.equals("Rules")) {
                button.addActionListener(e -> showDifficulty());
            } else if (text.equals("Customize Snake")) {
                button.addActionListener(e -> customizeSnake());
            } else if (text.equals("High Scores")) {
                button.addActionListener(e -> showHighScores());
            } else if (text.equals("Exit")) {
                button.addActionListener(e -> System.exit(0));
            }
            y += 60;
        }

    }

    public void startGame() {
            gamePanel.initSettingsStart();
            startGame1();
    }
    public void startGame1(){
        JFrame frame = new JFrame("Snake Game");

        frame.add(gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();                        // Fit frame to content
        frame.setLocationRelativeTo(null);  // Center the frame
        frame.setVisible(true);             // Show the frame
        gamePanel.startGame();
    }

    private void customizeSnake() {
        gamePanel.initSettingsCustomize();
    }
    private void showDifficulty() {
        String gameRules = "Game Rules: \n1. Use arrow keys to control the snake.\n2. Eat food to grow.\n3. Avoid hitting walls or your own body.\n";
        JOptionPane.showMessageDialog(this, gameRules, "Game Difficulty", JOptionPane.INFORMATION_MESSAGE);
    }
    private void showHighScores() {
        List<String> scores = ScoreManager.loadScores();  // Load scores from the ScoreManager class
        StringBuilder highScores = new StringBuilder("High Scores:\n");
        for (String score : scores) {
            highScores.append(score).append("\n");
        }
        JOptionPane.showMessageDialog(this, highScores.toString(), "High Scores", JOptionPane.INFORMATION_MESSAGE);
    }
//     Keep other methods (showHighScores, showDifficulty) from second code

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                URL imageUrl = getClass().getResource("/logo3.jpeg");
                if (imageUrl != null) {
                    backgroundImage = new ImageIcon(imageUrl).getImage();
                    System.out.println("Background image loaded successfully.");
                } else {
                    System.out.println("Error: Background image not found!");
                }
            } catch (Exception e) {
                System.out.println("Error loading background image: " + e.getMessage());
            }
        }





        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // If image loaded successfully, draw it
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth()+5, getHeight()+5, this);
            } else {
                // If not, fill background with pink
                g.setColor(Color.pink);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(800, 600);
        }
        }

}