/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arman.projects.pingball;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Arman
 */
public class Ball {

    int x;
    int y;
    int r;
    int velocityX;
    int velocityY;
    Image ballImage;

    public Ball(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
        velocityX = 1;
        velocityY = -1;

        try {
            int ballIndex = new Random().nextInt(19) + 1;
//            ballIndex = 12;
            ballImage = ImageIO.read(Ball.class.getClassLoader().getResourceAsStream("resource/img/ball" + ballIndex + ".png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    void repaint(Component c) {
        c.repaint(x - 2 * r, y - 2 * r, x + 2 * r, y + 2 * r);
    }

    public enum Direction {

        TOP_LEFT, TOP_RIGHT, DOWN_LET, DOWN_RIGHT
    };

    public void draw(Graphics g) {
//        g.setColor(Color.RED);
//        g.fillOval(x - r, y - r, r + r, r + r);

        g.drawImage(ballImage, x - r, y - r, r + r, r + r, null);
    }

    public void swapXDirection() {
        velocityX *= -1;
    }

    public void swapYDirection() {
        velocityY *= -1;
    }

    void move() {
        x += velocityX;
        y += velocityY;
    }
}
