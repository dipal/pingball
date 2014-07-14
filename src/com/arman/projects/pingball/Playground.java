/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arman.projects.pingball;

import com.arman.projects.pingball.enums.Collission;
import com.arman.projects.pingball.enums.Direction;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Arman
 */
public class Playground extends JPanel implements KeyListener {

    int w;
    int h;
    private Boundary boundary;
    private Bar bar;
    private Ball ball;
    private BrickArea brickArea;
    private ScoreBoard scoreBoard;
    //
    State state;
    Thread gamePlayingThread;
    //
    Storage storage;
    //
    Font messageDrawingFont;

    Playground(int w, int h, int level) {

        this.w = w;
        this.h = h;

        this.storage = Storage.getInstance();

        scoreBoard = new ScoreBoard(w - 110, 25, 0, level);

        setState(State.INITIAL);
        initialize();

    }

    void resetPlay() {
        bar = new Bar(boundary.x, boundary.x + boundary.w, boundary.y + boundary.h, new Bar.BarMoveCallback() {

            @Override
            public void barMoved() {
                repaint(0, bar.y, w, bar.y + bar.w);
            }
        });
        ball = new Ball(bar.x + bar.w / 2, bar.y - 10, 10);
    }

    void initialize() {

        BrickFactory.refreshBrick();
        boundary = new Boundary(10, 10, w - 20, h - 20);
        brickArea = new BrickArea(boundary.x + 30, boundary.y + 50, boundary.w - 60, boundary.h - 400, scoreBoard.level);

//        W.prl("bar.x = " + bar.x + ", bar.y = " + bar.y + ", bar.w = " + bar.w + ", bar.h = " + bar.h);
//        W.prl("ball.x = " + ball.x + ", ball.y = " + ball.y);

        messageDrawingFont = new Font("comic sans", Font.BOLD, 20);

        gamePlayingThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    boolean isBreak = state == State.GAME_COMPLETE
                            || state == State.GAME_OVER
                            || state == State.LEVEL_COMPLETE;
                    if (isBreak) {
                        break;
                    }


                    try {
                        gamePlayingThread.sleep(5);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    if (state == State.LEVEL_DIED) {
                        continue;
                    }

                    executeGameLoop();
                    scoreBoard.repaint(Playground.this);
                    brickArea.repaint(Playground.this);
                    ball.repaint(Playground.this);
                }
            }
        });

        resetPlay();
        repaint();
    }

    private void drawMessage(Graphics g, String message) {
        g.setColor(Color.GRAY);
        int left = w / 2 - 250;
        int top = h / 2 - 50;
        g.fillRect(left, top, 500, 100);
        g.setColor(Color.red);
        g.setFont(messageDrawingFont);
        g.drawString(message, left + 20, top + 50);
    }

    private void handleSpacePress() {
        switch (state) {
            case INITIAL:
                setState(State.GAME_RUNNING);
                break;

            case GAME_RUNNING:
                setState(State.GAME_PAUSED);
                break;

            case GAME_PAUSED:
                setState(State.GAME_RUNNING);
                break;

            case GAME_OVER:
                setState(State.GAME_RUNNING);
                start();
                break;

            case LEVEL_COMPLETE:
                start();
                break;

            case LEVEL_DIED:
                setState(State.GAME_RUNNING);
                break;
        }

        repaint();
    }

    void increaseLevel() {
        if (scoreBoard.level < 9) {
            scoreBoard.level++;
        }
    }

    private void handleBarCollission(Collission collission) {
        switch (collission) {
            case UP:
                ball.swapYDirection();
                break;

            case LEFT:
            case RIGHT:
                ball.swapXDirection();
                break;
        }
    }

    private void handleBoundaryCollission(Collission boundaryCollission) {
        switch (boundaryCollission) {
            case LEFT:
            case RIGHT:
                ball.swapXDirection();
                break;

            case UP:
                ball.swapYDirection();
                break;

            case DOWN:
                scoreBoard.life--;
                if (scoreBoard.life <= 0) {
                    scoreBoard.resetScore();
                    scoreBoard.resetLife();
                    setState(State.GAME_OVER, 1000);
                    initialize();
                    setState(State.INITIAL);
                } else {
                    setState(State.LEVEL_DIED);
                    resetPlay();
                }
                break;
        }
    }

    private void handleBrickResponse(BrickResponse brickResponse) {
        if (brickResponse.collission == null) {
            return;
        }

        scoreBoard.score += brickResponse.touchedBrick.point;
        if (brickResponse.allBricksCompleted) {
            setState(State.LEVEL_COMPLETE, 1000);

            setState(State.INITIAL);
            scoreBoard.resetLife();
            increaseLevel();
            initialize();
            return;
        }

        switch (brickResponse.collission) {
            case LEFT:
            case RIGHT:
                ball.swapXDirection();
                break;

            case UP:
            case DOWN:
                ball.swapYDirection();
                break;

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        W.prl("Key Typed - keycode = " + e.getKeyCode() + ", keyChar = " + e.getKeyChar() + ", " + KeyEvent.VK_A);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                handleSpacePress();
                break;

            case KeyEvent.VK_LEFT:
                bar.move(Direction.LEFT, true);
                break;

            case KeyEvent.VK_RIGHT:
                bar.move(Direction.RIGHT, true);
                break;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                bar.move(Direction.LEFT, false);
                break;

            case KeyEvent.VK_RIGHT:
                bar.move(Direction.RIGHT, false);
                break;

        }
    }

    enum State {

        INITIAL, GAME_RUNNING, GAME_PAUSED, GAME_OVER, LEVEL_COMPLETE, GAME_COMPLETE, LEVEL_DIED
    };

    private void executeGameLoop() {
        switch (state) {
            case GAME_RUNNING:

                Collission barCollission = bar.check(ball);
                if (barCollission != null) {
                    handleBarCollission(barCollission);
                }

                Collission boundaryCollission = boundary.check(ball);
                if (boundaryCollission != null) {
                    handleBoundaryCollission(boundaryCollission);
                }

                BrickResponse brickResponse = brickArea.check(ball);
                if (brickResponse != null) {
                    handleBrickResponse(brickResponse);
                }


                ball.move();
                break;
        }
    }

    void start() {
        if (state == State.INITIAL) {
            gamePlayingThread.start();
        }
    }

    void pause() {
        if (state == State.GAME_RUNNING) {
            setState(State.GAME_PAUSED);
        }
    }

    void resume() {
        if (state == State.GAME_PAUSED) {
            setState(State.GAME_RUNNING);
        }
    }

    @Override
    public void paint(Graphics g) {
        if (state == null) {
            return;
        }

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, w, h);
        g.setColor(Color.red);
        g.drawRect(0, 0, w, h);
//        g.clearRect(0, 0, dimension.width, dimension.height);


        boundary.draw(g);
        brickArea.draw(g);
        bar.draw(g);
        ball.draw(g);
        scoreBoard.draw(g);

        switch (state) {
            case INITIAL:
                drawMessage(g, "        Hit space to play");
                break;

            case GAME_RUNNING:
                //TODO

                break;

            case GAME_PAUSED:
                drawMessage(g, "  Paused, hit space to resume");
                break;


            case GAME_OVER:
                drawMessage(g, "           Game is over.");
                break;

            case LEVEL_COMPLETE:
                drawMessage(g, "         Level complete !!!");
                break;
        }

    }

    private void setState(State state) {
        this.state = state;
        W.prl("State changed to '" + state + "'");
        repaint();
    }

    private void setState(State state, long ms) {
        setState(state);
        try {
            gamePlayingThread.sleep(ms);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
