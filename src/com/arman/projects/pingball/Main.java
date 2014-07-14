/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arman.projects.pingball;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

/**
 *
 * @author Arman
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {


        Storage storage = Storage.getInstance();

        int level = 0;
        if (storage.lastGameExist()) {
            level = storage.lastLevel;
        }

        JFrame mainFrame = new JFrame("PingBall made by Armaan");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.setResizable(false);

        Dimension dimension = new Dimension(800, 650);

        mainFrame.getContentPane().setPreferredSize(dimension);
        mainFrame.pack();

        W.prl("w: " + mainFrame.getContentPane().getWidth() + ", h = " + mainFrame.getContentPane().getHeight());

        final Playground playground = new Playground(mainFrame.getContentPane().getWidth(), mainFrame.getContentPane().getHeight(), level);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(playground, BorderLayout.CENTER);
        playground.start();

        mainFrame.addKeyListener(playground);
        mainFrame.setVisible(true);

    }
}
