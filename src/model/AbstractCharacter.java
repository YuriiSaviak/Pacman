package model;

import javax.swing.*;

public abstract class AbstractCharacter extends JLabel implements Runnable {
    protected int xPos, yPos;
    protected int xPosStart, yPosStart;
    protected int health;
    protected volatile int speed;
    protected volatile boolean isRunning = true;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setPos(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void hit() {
        health -= 1;
    }

    public boolean isDead() {
        return health == 0;
    }
}