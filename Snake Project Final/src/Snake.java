

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class Snake {
    private final int UNIT_SIZE;
    private final int WIDTH, HEIGHT;
    private LinkedList<Point> body;
    private char direction = 'L';
    private boolean alive = true;
    private char direction1='R';
    private int score=0;

    public Snake(int width, int height, int unitSize) {
        WIDTH = width;
        HEIGHT = height;
        UNIT_SIZE = unitSize;
        body = new LinkedList<>();
        body.add(new Point(UNIT_SIZE * 9, UNIT_SIZE * 9));
        for (int i = 1; i < 3; i++) {
            body.add(new Point(UNIT_SIZE * (5 + i), UNIT_SIZE * 5));
        }
    }

    public void move() {
        Point head = body.getFirst();
        Point newHead = new Point(head);
        switch (direction) {
            case 'U': newHead.y -= UNIT_SIZE; break;
            case 'D': newHead.y += UNIT_SIZE; break;
            case 'L': newHead.x -= UNIT_SIZE; break;
            case 'R': newHead.x += UNIT_SIZE; break;
        }
//        switch (direction1) {
//            case 'U': newHead.y -= UNIT_SIZE; break;
//            case 'D': newHead.y += UNIT_SIZE; break;
//            case 'L': newHead.x -= UNIT_SIZE; break;
//            case 'R': newHead.x += UNIT_SIZE;break;
//        }
        if (newHead.x < 0) newHead.x = WIDTH - UNIT_SIZE;
        if (newHead.x >= WIDTH) newHead.x = 0;
        if (newHead.y < 0) newHead.y = HEIGHT - UNIT_SIZE;
        if (newHead.y >= HEIGHT) newHead.y = 0;
        body.addFirst(newHead);
        body.removeLast();
    }

    public void grow() {
        body.addLast(new Point(body.getLast()));
    }

    public boolean checkFood(Food food) {
        if (body.getFirst().equals(food.getPosition())) {
            grow();
            score++;
            SoundManager.playSound("sound.wav");
            return true;
        }
        return false;
    }

    public boolean checkSelfCollision() {
        Point head = body.getFirst();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) return true;
        }
        return false;
    }
    public void incrementScore() {
        score++;
    }

    public void changeDirection(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                if (direction != 'R') direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') direction = 'R';
                break;
            case KeyEvent.VK_UP:
                if (direction != 'D') direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') direction = 'D';
                break;

            case KeyEvent.VK_A:
                if (direction != 'R') direction = 'L';
                break;
            case KeyEvent.VK_D:
                if (direction != 'L') direction = 'R';
                break;
            case KeyEvent.VK_W:
                if (direction != 'D') direction = 'U';
                break;
            case KeyEvent.VK_S:
                if (direction != 'U') direction = 'D';
                break;
        }
    }


    public void draw(Graphics g, Color color) {
        for (int i = 0; i < body.size(); i++) {
            if (i == 0) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(color);
            }
            g.fillRect(body.get(i).x, body.get(i).y, UNIT_SIZE, UNIT_SIZE);
        }
    }
    public void setStartingPosition(Point start) {
        body.clear();
        body.add(start);
        for (int i = 1; i < 3; i++) {
            body.add(new Point(start.x + i * UNIT_SIZE, start.y));
        }
    }


    public Point getHead() {
        return body.getFirst();
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean a) {
        alive = a;
    }

    public int getLength() {
        return body.size();
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
       this.score = score;
    }
}
