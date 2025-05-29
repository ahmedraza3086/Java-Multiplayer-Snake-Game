

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Food {
    private final int WIDTH, HEIGHT, UNIT_SIZE;
    private Point position;
    private Random random = new Random();

    public Food(int width, int height, int unitSize) {
        WIDTH = width;
        HEIGHT = height;
        UNIT_SIZE = unitSize;
        position = new Point(-UNIT_SIZE, -UNIT_SIZE); // Temporary position until actual food is placed

    }
    public void newFood(ArrayList<Point> snakeBody, Maze maze, int mazetype) {
        Random rand = new Random();
        int x, y;
        Point newPoint;

        do {
            x = rand.nextInt(WIDTH / UNIT_SIZE) * UNIT_SIZE;
            y = rand.nextInt(HEIGHT / UNIT_SIZE) * UNIT_SIZE;
            newPoint = new Point(x, y);
        } while (snakeBody.contains(newPoint) || maze.isWall(x, y));

        position = newPoint;
    }


    public void newFood(List<Point> snakeBody) {
        int x, y;
        do {
            x = random.nextInt(WIDTH / UNIT_SIZE) * UNIT_SIZE;
            y = random.nextInt(HEIGHT / UNIT_SIZE) * UNIT_SIZE;
        } while (snakeBody != null && snakeBody.contains(new Point(x, y)));
        position = new Point(x, y);
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(position.x, position.y, UNIT_SIZE, UNIT_SIZE);
    }

    public Point getPosition() {
        return position;
    }
}
