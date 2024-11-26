package model;


import javax.swing.*;

import static java.awt.event.KeyEvent.*;

public class Pacman extends AbstractCharacter {
    private int key;
    private boolean isClosedMouth;
    private final String PATH = "resources/image/pacman/pacman_";
    private volatile long score = 0;
    private volatile int timer = 0;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public long getScore() {
        return score;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public synchronized int incrementTimer() {
        return timer += 1;
    }

    public synchronized void givePoints(int points) {
        score += points;
    }

    public Pacman(int xPosStart, int yPosStart) {
        this.xPosStart = xPosStart;
        this.yPosStart = yPosStart;

        this.xPos = xPosStart;
        this.yPos = yPosStart;

        health = 3;
        speed = 400;
        setIcon(new ImageIcon(PATH + "right_closed.png"));
    }

    @Override
    public void hit() {
        super.hit();
        xPos = xPosStart;
        yPos = yPosStart;
    }

    @Override
    public void run() {
        while (isRunning) {
            String mouth = isClosedMouth ? "closed" : "opened";

            isClosedMouth = !isClosedMouth;//vopros

            switch (key) {
                case VK_RIGHT -> setIcon(new ImageIcon(PATH + "right_" + mouth + ".png"));
                case VK_LEFT -> setIcon(new ImageIcon(PATH + "left_" + mouth + ".png"));
                case VK_UP -> setIcon(new ImageIcon(PATH + "up_" + mouth + ".png"));
                case VK_DOWN -> setIcon(new ImageIcon(PATH + "down_" + mouth + ".png"));
            }

            repaint();

            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
