/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arman.projects.pingball;

import com.arman.projects.pingball.enums.BrickType;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Arman
 */
public class BrickFactory {

    private int x;
    private int y;
    private int w;
    private int h;
    public static Image brickImage;

    ArrayList<Brick> getBricks(int level) {
        return selectBricksByLevel(new Random().nextInt(5));
    }

    static public void refreshBrick() {

        try {
            int imgIndex = new Random().nextInt(7) + 1;
            brickImage = ImageIO.read(BrickFactory.class.getClassLoader().getResourceAsStream("resource/img/brick" + imgIndex + ".jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    ArrayList<Brick> selectBricksByLevel(int level) {
        switch (level) {
            case 0:
                return getDesignedBricks0();

            case 1:
                return getDesignedBricks1();

            case 2:
                return getDesignedBricks2();

            case 3:
                return getDesignedBricks3();

            case 4:
                return getDesignedBricks4();

            default:
                return getRectangleRandom();
        }
    }

    ArrayList<Brick> getRectangleRandom() {
        Random r = new Random();
        String[] brickMusk = new String[5];
        int point = 25;
        for (int i = 0; i < brickMusk.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < (w - x) / (Brick.w + 10); j++) {
                if (r != null) {
                    sb.append(r.nextInt(5) < 2 ? "." : "*");
                } else {
                    sb.append("*");
                }
            }
            brickMusk[i] = sb.toString();
            point -= 5;
        }
        return getBricksFromMusk(brickMusk);
    }

    void setSize(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

    }

    private ArrayList<Brick> getDesignedBricks0() {
        String[] brickMusk = {
            "**********",
            "**********",
            "**********",
            "**********"
        };
        return getBricksFromMusk(brickMusk);
    }

    private ArrayList<Brick> getDesignedBricks1() {
        String[] brickMusk = {
            "...****...",
            "..*....*..",
            "**......**",
            "**......**",
            "**......**",
            "..*....*..",
            "...****..."
        };
        return getBricksFromMusk(brickMusk);
    }

    private ArrayList<Brick> getDesignedBricks2() {
        String[] brickMusk = {
            "...****...",
            "..*....*..",
            "**......**",
            "**..**..**",
            "**......**",
            "..*....*..",
            "...****..."
        };
        return getBricksFromMusk(brickMusk);
    }

    private ArrayList<Brick> getDesignedBricks3() {
        String[] brickMusk = {
            "**.****.**",
            "..*....*..",
            "**......**",
            "..........",
            "**......**",
            "..*....*..",
            "**.****.**"
        };
        return getBricksFromMusk(brickMusk);
    }

    private ArrayList<Brick> getDesignedBricks4() {
        String[] brickMusk = {
            "...*****...",
            ".....*.....",
            ".....*.....",
            ".....*.....",
            ".....*.....",
            ".....*.....",
            "...*****..."
        };
        return getBricksFromMusk(brickMusk);
    }

    private ArrayList<Brick> getBricksFromMusk(String[] brickMusk) {
        ArrayList<Brick> bricks = new ArrayList<Brick>();
        int point = 25;
        int row = brickMusk.length;
        int col = brickMusk[0].length();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (brickMusk[i].charAt(j) == '.') {
                    continue;
                }
                bricks.add(new Brick(x + j * (Brick.w + 10), y + i * (Brick.h + 5), Math.max(1, point), BrickType.REGULAR));
            }
            point -= 5;
        }

        return bricks;
    }
}
