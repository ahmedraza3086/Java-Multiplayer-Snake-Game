import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {
    String choice;
    final int WIDTH = 600;
    final int HEIGHT = 600;
    final int UNIT_SIZE = 25;
    String playerName1 = null;
    String playersName[] = {null, null};
    public boolean isTwoPlayer = false, paused = false, darkMode = false;
    private String collisionMessage = null;
    private String eliminationMessage = null;
    private Timer messageTimer;

    String targetScore = null;
    Snake snake2;
    Timer timer;
    Snake snake;
    Food food;
    Maze maze;
    int mazetype = 0;
    ScoreManager highScores;
    SoundManager sound;
    int speed = 150;
    Color[] snakeColors = {Color.GREEN, Color.GREEN};
    int[] speeds = {150, 150};
    private String playerName;
    private Color snakeColor = Color.GREEN;

    public boolean isTwoPlayer() {
        return isTwoPlayer;
    }


    // Modify constructor
    public GamePanel() {

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
//        this.setBackground(Color.BLACK);

        this.setFocusable(true);
        addKeyListener(new MyKeyAdapter());

        // Initialize default values
        snakeColor = Color.GREEN;
        startGame();
        highScores = new ScoreManager();
        sound = new SoundManager();
        loadBackgroundImage();
        messageTimer = new Timer(2000, e -> {
            collisionMessage = null;
            eliminationMessage = null;
            repaint();
        });
        messageTimer.setRepeats(false);
//        initSettings();
    }


    public void startGame() {
        snake = new Snake(WIDTH, HEIGHT, UNIT_SIZE);

        if (isTwoPlayer) {
            snake2 = new Snake(WIDTH, HEIGHT, UNIT_SIZE);  // REMOVE local variable
            snake2.setStartingPosition(new Point(WIDTH - 100, HEIGHT - 100));
        }

        food = new Food(WIDTH, HEIGHT, UNIT_SIZE);
        maze = new Maze(WIDTH, HEIGHT, UNIT_SIZE);
        maze.generator(mazetype);
        food.newFood(new ArrayList<>(snake.getBody()), maze, mazetype);

        timer = new Timer(speed, this);
        timer.start();
    }


    // Add new methods for settings
    public void setPlayerName(String name) {
        this.playerName = name;
    }

    public void setSnakeColor(Color color) {
        this.snakeColor = color;
    }

    public void setGameSpeed(int speed) {
        this.speed = speed;
        if (timer != null) {
            timer.setDelay(speed);
        }
    }

    public void initSettingsCustomize() {

        if (!isTwoPlayer) {
            String[] speeds = {"Slow", "Normal", "Fast"};
            String selectedSpeed = (String) JOptionPane.showInputDialog(null, "Select Speed", "Speed",
                    JOptionPane.PLAIN_MESSAGE, null, speeds, speeds[1]);
            if (selectedSpeed != null) {
                switch (selectedSpeed) {
                    case "Slow":
                        speed = 200;
                        break;
                    case "Fast":
                        speed = 100;
                        break;
                    default:
                        speed = 150;
                }
            }
        }
    }

    public void initSettingsStart() {
        String[] modes = {"Single Player", "Two Players"};
        String selectedMode = (String) JOptionPane.showInputDialog(null, "Select Game Mode", "Mode",
                JOptionPane.PLAIN_MESSAGE, null, modes, modes[0]);
        if (selectedMode != null) {
            switch (selectedMode) {
                case "Two Players":
                    isTwoPlayer = true;
                    break;
                default:
                    isTwoPlayer = false;
            }
            if (!isTwoPlayer) {
                playerName1 = JOptionPane.showInputDialog(null, "Enter Your Name :", "Name",
                        JOptionPane.PLAIN_MESSAGE);
                this.playerName1 = playerName1;

            } else {
                for (int i = 0; i < 2; i++) {
                    String name = (String) JOptionPane.showInputDialog(null, "Enter your Name Player " + (i + 1), "Name",
                            JOptionPane.PLAIN_MESSAGE);
                    this.playersName[i] = name;

                }
            }
            if (!isTwoPlayer) {
                String[] colors = {"Green", "Red", "Blue"};
                String selectedColor = (String) JOptionPane.showInputDialog(null, "Select Snake Color", "Color",
                        JOptionPane.PLAIN_MESSAGE, null, colors, colors[0]);
                if (selectedColor != null) {
                    switch (selectedColor) {
                        case "Red":
                            snakeColor = Color.RED;
                            break;
                        case "Blue":
                            snakeColor = Color.BLUE;
                            break;
                        default:
                            snakeColor = Color.GREEN;
                    }
                }
            } else {
                for (int i = 0; i < 2; i++) {
                    String[] colors = {"Green", "Red", "Blue"};
                    String selectedColor = (String) JOptionPane.showInputDialog(null, "Select Snake Color Player " + (i + 1), "Color",
                            JOptionPane.PLAIN_MESSAGE, null, colors, colors[0]);
                    if (selectedColor != null) {
                        switch (selectedColor) {
                            case "Red":
                                snakeColors[i] = Color.RED;
                                break;
                            case "Blue":
                                snakeColors[i] = Color.BLUE;
                                break;
                            default:
                                snakeColors[i] = Color.GREEN;
                        }
                    }
                }
            }
            if (isTwoPlayer) {
                String targetInput = JOptionPane.showInputDialog(null, "Enter target score to win:", "Target Score",
                        JOptionPane.PLAIN_MESSAGE);
                this.targetScore = targetInput;

            } // Default value

//            else {
//                for (int i = 0; i < 2; i++) {
//                    String[] speeds = {"Slow", "Normal", "Fast"};
//                    String selectedSpeed = (String) JOptionPane.showInputDialog(null, "Select Speed Player " + (i + 1), "Speed",
//                            JOptionPane.PLAIN_MESSAGE, null, speeds, speeds[1]);
//                    if (selectedSpeed != null) {
//                        switch (selectedSpeed) {
//                            case "Slow":
//                                speeds[i] = String.valueOf(200);
//                                break;
//                            case "Fast":
//                                speeds[i] = String.valueOf(100);
//                                break;
//                            default:
//                                speeds[i] = String.valueOf(150);
//                        }
//                    }
//                }
//            }

            String[] mazes = {"Easy", "Medium", "Hard"};
            String selectedMaze = (String) JOptionPane.showInputDialog(null, "Select Maze Type", "Mazes",
                    JOptionPane.PLAIN_MESSAGE, null, mazes, mazes[0]);
            if (selectedMaze != null) {
                switch (selectedMaze) {

                    case "Medium":
                        mazetype = 1;
                        break;
                    case "Hard":
                        mazetype = 2;
                        break;
                    default:
                        mazetype = 0;
                }
            }
            maze = new Maze(WIDTH, HEIGHT, UNIT_SIZE);
        }
//    requestFocusInWindow();

    }

    // Modify paintComponent
    public void paintComponent(Graphics g) {
            super.paintComponent(g);
        // Set default text color based on dark mode
        g.setColor(darkMode ? Color.BLACK : Color.WHITE);

            // Consolidated background drawing logic
            if (darkMode) {
                g.setColor(Color.darkGray);
                g.fillRect(0, 0, getWidth(), getHeight());
            } else if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
            }

            // Draw player names and scores
            g.setFont(new Font("Arial", Font.BOLD, 20));
            if (!isTwoPlayer) {
                // Single player
                g.setColor(snakeColor);
                g.drawString((playerName1 != null ? playerName1 : "Player 1") + " : " + snake.getScore(), 20, 30);
            } else {
                // Two players
                g.setColor(snakeColors[0]);
                g.drawString((playersName[0] != null ? playersName[0] : "Player 1") + " : " + snake.getScore(), 20, 30);
                g.setColor(snakeColors[1]);
                g.drawString((playersName[1] != null ? playersName[1] : "Player 2") + " : " + snake2.getScore(),
                        WIDTH - 200, 30);
            }

            // Draw pause message
            if (paused) {
                g.setColor(Color.YELLOW);
                g.setFont(new Font("Arial", Font.BOLD, 50));
                String pauseText = "PAUSED";
                int textWidth = g.getFontMetrics().stringWidth(pauseText);
                g.drawString(pauseText, (WIDTH - textWidth)/2, HEIGHT/2);
            }
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.YELLOW);

        if (collisionMessage != null) {
            int textWidth = g.getFontMetrics().stringWidth(collisionMessage);
            g.drawString(collisionMessage, (WIDTH - textWidth)/2, HEIGHT/2 - 40);
        }

        if (eliminationMessage != null) {
            int textWidth = g.getFontMetrics().stringWidth(eliminationMessage);
            g.drawString(eliminationMessage, (WIDTH - textWidth)/2, HEIGHT/2 - 20);
        }
            // Rest of your drawing code
            maze.draw(g);
            maze.drawMaze(g, mazetype);
            food.draw(g);
            snake.draw(g, snakeColors[0]);
            if (isTwoPlayer) {
                snake2.draw(g, snakeColors[1]);
            }
        }


    public void actionPerformed(ActionEvent e) {
        if (!paused) {
            if (snake != null && snake.isAlive()) {
                snake.move();
                if (snake.checkFood(food)) {
                    food.newFood(new ArrayList<>(snake.getBody()), maze, mazetype);
                }
                if (maze.checkCollision(snake.getHead(), mazetype) || snake.checkSelfCollision()) {
                    snake.setAlive(false);
                    SoundManager.playSound("gameover.wav");
                    ScoreManager.saveScore(playerName1, snake.getScore());
                }
            }

            if (isTwoPlayer && snake2 != null && snake2.isAlive()) {
                snake2.move();
                if (snake2.checkFood(food)) {
                    food.newFood(new ArrayList<>(snake2.getBody()), maze, mazetype);
                }
                if (maze.checkCollision(snake2.getHead(), mazetype) || snake2.checkSelfCollision()) {
                    snake2.setAlive(false);
                    SoundManager.playSound("gameover.wav");
                    ScoreManager.saveScore(playersName[1], snake2.getScore());
                }
            }
        }
        if (isTwoPlayer && snake.isAlive() && snake2.isAlive()) {
            boolean collisionOccurred = false;
            for (Point p1 : snake.getBody()) {
                for (Point p2 : snake2.getBody()) {
                    if (p1.equals(p2)) {
                        collisionOccurred = true;
                        break;
                    }
                }
                if (collisionOccurred) break;
            }

            if (collisionOccurred) {
                collisionMessage = "Collision! Both players lose a block!";
                messageTimer.start();

                // Reduce body size
                if (!snake.getBody().isEmpty()) {
                    snake.getBody().removeLast();
                    snake.setScore(Math.max(0, snake.getScore() - 1));
                }
                if (!snake2.getBody().isEmpty()) {
                    snake2.getBody().removeLast();
                    snake2.setScore(Math.max(0, snake2.getScore() - 1));
                }

                // Check for elimination
                if (snake.getBody().size() < 3) {
                    snake.setAlive(false);
                    eliminationMessage = (playersName[0] != null ? playersName[0] : "Player 1") + " eliminated!";
                }
                if (snake2.getBody().size() < 3) {
                    snake2.setAlive(false);
                    eliminationMessage = (playersName[1] != null ? playersName[1] : "Player 2") + " eliminated!";
                }
            }
        }

        // Check for elimination victory
        if (isTwoPlayer) {
            if (!snake.isAlive() && snake2.isAlive()) {
                JOptionPane.showMessageDialog(this, eliminationMessage + "\n" +
                        (playersName[1] != null ? playersName[1] : "Player 2") + " wins!");
                System.exit(0);
            }
            if (snake.isAlive() && !snake2.isAlive()) {
                JOptionPane.showMessageDialog(this, eliminationMessage + "\n" +
                        (playersName[0] != null ? playersName[0] : "Player 1") + " wins!");
                System.exit(0);
            }
        }
//
//        if (isTwoPlayer) {
//            if (snake.isAlive() && snake2.isAlive()) {
//                for (Point p1 : snake.getBody()) {
//                    for (Point p2 : snake2.getBody()) {
//                        if (p1.equals(p2)) {
//                            // Collision found: remove last part of both snakes
//                            if (!snake.getBody().isEmpty()) {
//                                snake.getBody().removeLast();
//                                snake.setScore(snake.getScore() - 1);
//                            }
//                            if (!snake2.getBody().isEmpty()) {
//                                snake2.getBody().removeLast();
//                                snake2.setScore(snake2.getScore() - 1);
//                            }
//                            // Only break out of the loop once collision is handled
//                            break;
//                        }
//                    }
//                }
//            }
//        }

// Check if Player 1's snake is alive and its length
//        if (snake.isAlive()) {
//            if (snake.getLength() < 3) {
//                snake.setAlive(false);
//                JOptionPane.showMessageDialog(this, "Player 1 ELIMINATED!\nPlayer 2 wins!");
//            }
//        }
//
//// Check if Player 2's snake is alive and its length
//        if (snake2.isAlive()) {
//            if (snake2.getLength() < 3) {
//                snake2.setAlive(false);
//                JOptionPane.showMessageDialog(this, "Player 2 ELIMINATED!\nPlayer 1 wins!");
//            }
//        }

//        if (!snake.isAlive() && (!isTwoPlayer || !snake2.isAlive())) {
//            JOptionPane.showMessageDialog(this, "Game Over!");
//            String choice = JOptionPane.showInputDialog(null, "Press 'S' to see scoreboard", "Score", JOptionPane.PLAIN_MESSAGE);
//            if ("S".equalsIgnoreCase(choice)) {
//                ScoreManager.loadScores();
//            }
//            System.exit(0);
//        }
//
//        if (targetScore != null) {
//            try {
//                int target = Integer.parseInt(targetScore);
//                if (snake.getLength() - 3 >= target) {
//                    JOptionPane.showMessageDialog(this, "Player 1 wins!");
//                    System.exit(0);
//                }
//                if (isTwoPlayer && snake2.getLength() - 3 >= target) {
//                    JOptionPane.showMessageDialog(this, "Player 2 wins!");
//                    System.exit(0);
//                }
//            } catch (NumberFormatException ex) {
//                // Invalid targetScore, ignore
//            }
//        }

        repaint();
    }


    // Add dark mode toggle to key adapter
    class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_P) {
                paused = !paused;
                repaint();
            }
            // Control snake 1 with Arrow Keys
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT ||
                    key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                snake.changeDirection(key);
            }

            // Control snake 2 with WASD
            if (key == KeyEvent.VK_A || key == KeyEvent.VK_D ||
                    key == KeyEvent.VK_W || key == KeyEvent.VK_S) {
                if (snake2 != null) {
                    snake2.changeDirection(key);
                }
            }
            // Add dark mode toggle
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                darkMode = !darkMode;
                repaint();
            }
        }
    }
        private Image backgroundImage;

    private void loadBackgroundImage() {
        try {
            URL imageUrl = getClass().getResource("/logo4.jpeg");
            if (imageUrl != null) {
                backgroundImage = new ImageIcon(imageUrl).getImage();
                System.out.println("Successfully loaded background image");
            } else {
                System.out.println("Image not found! Check these locations:");
                System.out.println("1. Is logo3.jpg in your src/main/resources folder?");
                System.out.println("2. Is the file extension exactly .jpg?");
                System.out.println("3. Have you refreshed your project after adding the file?");
            }
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }







        @Override
        public Dimension getPreferredSize() {
            return new Dimension(WIDTH, HEIGHT);
        }

}