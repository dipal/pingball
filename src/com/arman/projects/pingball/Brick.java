/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arman.projects.pingball;

import com.arman.projects.pingball.enums.BrickType;
import com.arman.projects.pingball.enums.Collission;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Arman
 */
public class Brick {

    int x;
    int y;
    public static int w = 60;
    public static int h = 15;
    BrickType type;
    int point;

    public Brick(int x, int y, int point, BrickType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.point = point;

    }

    void draw(Graphics g) {
//        g.setColor(Color.BLUE);
//        g.fillRect(x, y, w, h);

        g.drawImage(BrickFactory.brickImage, x, y, w, h, null);

        g.setColor(Color.WHITE);
        g.drawString("      " + point, x, y + 12);
    }

    Collission check(Ball ball) {
        boolean upTouched = (ball.y <= y && y <= ball.y + ball.r) && (ball.x >= x && ball.x <= x + w);
        if (upTouched) {
            return Collission.UP;
        }

        boolean leftTouched = (ball.x <= x && x <= ball.x + ball.r) && (ball.y >= y && ball.y <= y + h);
        if (leftTouched) {
            return Collission.LEFT;
        }

        boolean downTouched = (ball.y - ball.r <= y + h && y + h <= ball.y) && (ball.x >= x && ball.x <= x + w);
        if (downTouched) {
            return Collission.DOWN;
        }

        boolean rightTouched = (ball.x - ball.r <= x + w && x + w <= ball.x) && (ball.y >= y && ball.y <= y + h);
        if (rightTouched) {
            return Collission.RIGHT;
        }

        return null;
    }
}
