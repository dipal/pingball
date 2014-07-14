/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arman.projects.pingball;

import com.arman.projects.pingball.enums.Collission;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Arman
 */
public class BrickArea {

    static BrickFactory factory = new BrickFactory();
    private ArrayList<Brick> bricks;
    int x;
    int y;
    int w;
    int h;

    public BrickArea(int x, int y, int w, int h, int levelIndex) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        factory.setSize(x, y, w, h);
        bricks = factory.getBricks(levelIndex);
    }

    public BrickResponse check(Ball ball) {
        BrickResponse brickResponse = null;
        Brick touchedBrick = null;
        for (Brick b : bricks) {
            Collission c = b.check(ball);
            if (c != null) {
                boolean allBricksTouched = bricks.size() == 1; // if last one
                brickResponse = new BrickResponse(c, b, allBricksTouched);
                touchedBrick = b;
                break;
            }
        }

        if (brickResponse != null) {
            synchronized (bricks) {
                bricks.remove(touchedBrick);
            }
        }

        return brickResponse;
    }

    void draw(Graphics g) {
//        g.setColor(new Color(0xFF333333));
//        g.drawRect(x, y, w, h);

        synchronized (bricks) {
            for (Brick b : bricks) {
                b.draw(g);
            }
        }
    }

    void repaint(Component c) {
        c.repaint(x, y, x + w, y + h);
    }
}
