/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arman.projects.pingball;

import com.arman.projects.pingball.enums.BrickType;
import com.arman.projects.pingball.enums.Collission;

/**
 *
 * @author Arman
 */
public class BrickResponse {
    Collission collission;
    Brick touchedBrick;
    
    boolean allBricksCompleted;

    BrickResponse(Collission collission, Brick touchedBrick, boolean allBricksCompleted) {
        this.collission = collission;
        this.touchedBrick = touchedBrick;
        this.allBricksCompleted = allBricksCompleted;
    }
   
}
