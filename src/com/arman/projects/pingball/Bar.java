/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arman.projects.pingball;

import com.arman.projects.pingball.enums.Collission;
import com.arman.projects.pingball.enums.Direction;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Arman
 */
public class Bar {

    int x;
    int y;
    int w;
    int h;
    int minX;
    int maxX;
    int speed;
    private Thread barMovingThread;
    private boolean barMovingThreadRunning;
    private Direction direction;
    private BufferedImage barImage;

    public interface BarMoveCallback {

        void barMoved();
    };
    BarMoveCallback callback;

    public Bar(int minX, int maxX, int y, BarMoveCallback callback) {
        this.callback = callback;
        Random r = new Random();
        this.minX = minX;
        this.maxX = maxX;
        w = 100;
        h = 10;
        this.x = minX + r.nextInt(maxX - w);
        this.y = y - h;
        this.speed = 5;

        try {
            barImage = ImageIO.read(Bar.class.getClassLoader().getResourceAsStream("resource/img/bamboo.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    void draw(Graphics g) {
//        g.setColor(Color.green);
//        g.fillRect(x, y, w, h);

        g.drawImage(barImage, x, y, w, h, null);
    }

    Collission check(Ball ball) {
        boolean upTouched = (ball.y < y && y < ball.y + ball.r) && (ball.x >= x && ball.x <= x + w);
        if (upTouched) {
            return Collission.UP;
        }

        boolean leftTouched = (ball.x <= x && x <= ball.x + ball.r) && (ball.y >= y && ball.y <= y + h);
        if (leftTouched) {
            return Collission.LEFT;
        }

        boolean rightTouched = (ball.x - ball.r <= x + w && x + w <= ball.x) && (ball.y >= y && ball.y <= y + h);
        if (rightTouched) {
            return Collission.RIGHT;
        }


        return null;
    }

    private void move(Direction direction) {
        switch (direction) {
            case LEFT:
                if (x > minX) {
                    x -= speed;
                }
                break;

            case RIGHT:
                if (x + w < maxX) {
                    x += speed;
                }
                break;
        }

//        W.prl("Bar moved to '" + direction + "'");
    }

    public void move(Direction direction, boolean start) {
        this.direction = direction;
        if (start) {
            if (barMovingThread == null) {
                barMovingThread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        while (barMovingThreadRunning) {
                            handleBarMovingLoop();
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                        barMovingThread = null;
                    }
                }, "Bar Moving Thread");
                barMovingThreadRunning = true;
                barMovingThread.start();
            }
        } else {
            barMovingThreadRunning = false;
        }
//        W.prl("Bar move = " + direction + ", start/stop = " + start);
    }

    private void handleBarMovingLoop() {
        move(direction);
        callback.barMoved();
//        W.prl("Bar MOVED");
    }
}
