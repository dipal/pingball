/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arman.projects.pingball;

import com.arman.projects.pingball.enums.Collission;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Arman
 */
public class Boundary {

    int x;
    int y;
    int w;
    int h;

    Boundary(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    Collission check(Ball ball) {
        boolean leftFailed = ball.x - ball.r < x;
        if (leftFailed) {
            return Collission.LEFT;
        }

        boolean rightFailed = ball.x + ball.r > x+w;
        if (rightFailed) {
            return Collission.RIGHT;
        }

        boolean upFailed = ball.y - ball.r < y;
        if (upFailed) {
            return Collission.UP;
        }

        boolean downFailed = ball.y + ball.r >y+h;
        if (downFailed) {
            return Collission.DOWN;
        }

        return null;
    }
    
    public void draw(Graphics g){
        g.setColor(Color.GRAY);
        g.drawRect(x, y, w, h);
    }
}
