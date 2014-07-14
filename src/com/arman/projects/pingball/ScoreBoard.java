/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arman.projects.pingball;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Arman
 */
public class ScoreBoard {

    private static final int MAX_LIFE = 3;
    int x;
    int y;
    int life;
    int score;
    int level;
    Font f = new Font("monospaced", Font.BOLD, 13);

    public ScoreBoard(int x, int y, int score, int level) {
        this.x = x;
        this.y = y;
        this.score = score;
        this.level = level;
        this.life = MAX_LIFE;
    }

    public void resetLife() {
        life = Math.max(MAX_LIFE, Math.min(MAX_LIFE + 2, life));
    }

    void draw(Graphics g) {
        g.setFont(f);
        g.setColor(Color.red);
        g.drawString("Life  = " + life, x, y);
        g.drawString("Score = " + score, x, y + 15);
        g.drawString("Level = " + (level + 1), x, y + 30);
    }

    void resetScore() {
        score = 0;
    }

    void repaint(Component c) {
        c.repaint(x, y, 100, 50);
    }
}
