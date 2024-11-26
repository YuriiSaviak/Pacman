package game;

import model.CellType;
import model.Pacman;

import static java.awt.event.KeyEvent.*;
import static model.CellType.*;


public class PacmanProcessor implements Runnable {
    private final Pacman pacman;
    private final Map map;

    public PacmanProcessor(Pacman pacman, Map map) {
        this.pacman = pacman;
        this.map = map;
    }

    private synchronized void makeMove(int xPos, int yPos) {
        int currentxPos = pacman.getxPos();
        int currentyPos = pacman.getyPos();
        int futurexPos = currentxPos + xPos;
        int futureyPos = currentyPos + yPos;
        CellType currentCell = map.getPosition(currentxPos, currentyPos);
        CellType futureCell = map.getPosition(futurexPos, futureyPos);

        switch (futureCell) {
            case BLOCK -> {
                return;
            }
            case EDIBLE -> pacman.givePoints(100);
            case GHOST, GHOST_EDIBLE -> {
                if (map.getPosition(currentxPos, currentyPos) == PACMAN_INVINCIBLE)
                    return;

                pacman.hit();
                map.setPosition(currentxPos, currentyPos, PASSAGE);
                map.setPosition(futurexPos, futureyPos, GHOST);
            }
            case SCORE_BOOST -> giveScoreBoost();
            case HEALTH_BOOST -> giveHealthBoost();
            case INVINCIBILITY_BOOST -> giveInvincibility();
            case TIME_BOOST -> giveTimeBoost();
            case SPEED_BOOST -> giveSpeedBoost();
        }

        if (futureCell == EDIBLE || "BOOST".equals(futureCell.name().substring(futureCell.name().length() - 5)) || futureCell == PASSAGE) {
            if (currentCell == PACMAN_INVINCIBLE)
                map.setPosition(futurexPos, futureyPos, PACMAN_INVINCIBLE);
            else
                map.setPosition(futurexPos, futureyPos, PACMAN);

            map.setPosition(currentxPos, currentyPos, PASSAGE);
                     pacman.setPos(futurexPos, futureyPos);
        }
    }

    public synchronized void giveScoreBoost() {
        pacman.givePoints(1250);
    }

    public synchronized void giveHealthBoost() {
        pacman.setHealth(pacman.getHealth() + 1);
    }

    public synchronized void giveTimeBoost() {
        pacman.setTimer(Math.max(0, pacman.getTimer() - 10));
    }

    public synchronized void giveSpeedBoost() {
        new Thread(() -> {
            pacman.setSpeed(pacman.getSpeed() / 2);//vopros

            try {
                Thread.sleep(7500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            pacman.setSpeed(pacman.getSpeed() * 2);
        }).start();
    }

    public synchronized void giveInvincibility() {
        new Thread(() -> { //vopros
            map.setPosition(pacman.getxPos(), pacman.getyPos(), PACMAN_INVINCIBLE);

            try {
                Thread.sleep(7500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            map.setPosition(pacman.getxPos(), pacman.getyPos(), PACMAN);
        }).start();
    }

    @Override
    public void run() {
        while (pacman.isRunning()) {
            switch (pacman.getKey()) {
                case VK_LEFT -> makeMove(-1, 0);
                case VK_RIGHT -> makeMove(1, 0);
                case VK_UP -> makeMove(0, -1);
                case VK_DOWN -> makeMove(0, 1);
            }

            try {
                Thread.sleep(pacman.getSpeed());
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}