/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arman.projects.pingball;

/**
 *
 * @author Arman
 */
public class Storage {

    private static Storage instance;

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    boolean lastGameExist() {
        //TODO
        return false;
    }
    int highscore;
    int lastLevel;
}
