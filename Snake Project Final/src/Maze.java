

import java.awt.*;
import java.util.Random;

public class Maze {
    public final int WIDTH, HEIGHT, UNIT_SIZE;
    public int[][] mazeLayout;
    private Random random;

    public Maze(int width, int height, int unitSize) {
        WIDTH = width;
        HEIGHT = height;
        UNIT_SIZE = unitSize;
        mazeLayout = new int[WIDTH / UNIT_SIZE][HEIGHT / UNIT_SIZE];
        random = new Random(); // Initialize Random
    }

    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        for (int i = 0; i < WIDTH; i += UNIT_SIZE) {
            g.drawLine(i, 0, i, HEIGHT);
        }
        for (int j = 0; j < HEIGHT; j += UNIT_SIZE) {
            g.drawLine(0, j, WIDTH, j);
        }
    }

    public void drawMaze(Graphics g, int mazetype) {
        for (int i = 0; i < WIDTH; i += UNIT_SIZE) {
            for (int j = 0; j < HEIGHT; j += UNIT_SIZE) {
                int x = i / UNIT_SIZE;
                int y = j / UNIT_SIZE;
                if (mazeLayout[x][y] == 1 ) {
                    if (mazetype == 1 || mazetype == 2) {
                    g.setColor(new Color(0, 50, 0));
                    g.fillRect(i, j, UNIT_SIZE, UNIT_SIZE);
                    }
                } else if (mazeLayout[x][y] == 2 && mazetype == 2) {
                    g.setColor(new Color(0, 50, 42));
                    g.fillRect(i, j, UNIT_SIZE, UNIT_SIZE);
                }
            }
        }
    }

    public void generator(int mazetype) {
        if (mazetype == 1) {
            generateWall();
        } else if (mazetype == 2) {
            generateWall();
            generateObstacles();
        }
    }

    public void generateWall() {
        for (int i = 0; i < WIDTH / UNIT_SIZE; i++) {
            for (int j = 0; j < HEIGHT / UNIT_SIZE; j++) {
                if (i == 0 || j == 0 || i == (WIDTH / UNIT_SIZE) - 1 || j == (HEIGHT / UNIT_SIZE) - 1) {
                    mazeLayout[i][j] = 1; // Wall
                } else {
                    mazeLayout[i][j] = 0;
                }
            }
        }
    }

    public void generateObstacles() {
        for (int i = 0; i < WIDTH / UNIT_SIZE; i+=10) {
            for (int j = 0; j < HEIGHT / UNIT_SIZE; j+=10) {
                mazeLayout[i][j] = 2;
                mazeLayout[i+1][j+1]=2;
                mazeLayout[i][j+1]=2;
                mazeLayout[i+1][j]=2;
            }
        }
    }
    public boolean checkCollision(Point head, int mazetype) {
        switch (mazetype) {
            case 1: // Medium Maze
                return checkCollisionMedium(head);
            case 2: // Hard Maze
                return checkCollisionHard(head);
            default:
                return false; // Easy has no walls
        }
    }


    public boolean checkCollisionMedium(Point p) {
        boolean choice=false;
        int x = p.x / UNIT_SIZE;
        int y = p.y / UNIT_SIZE;
        if (mazeLayout[x][y] == 1){
            choice=true;
        }
        return choice;
    }
    public boolean isWall(int x, int y) {
        int gridX = x / UNIT_SIZE;
        int gridY = y / UNIT_SIZE;
        if (gridX < 0 || gridY < 0 || gridX >= mazeLayout.length || gridY >= mazeLayout[0].length)
            return false;

        return mazeLayout[gridX][gridY] != 0;
    }


    public boolean checkCollisionHard(Point p) {

        boolean choice =false;
        int x = p.x / UNIT_SIZE;
        int y = p.y / UNIT_SIZE;
        if (mazeLayout[x][y] == 2||mazeLayout[x][y]==1){
            choice=true;
        }
        return choice;
    }
}
