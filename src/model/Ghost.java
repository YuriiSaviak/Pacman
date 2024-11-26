package model;

import javax.swing.*;
import java.util.Random;

import static model.CellType.*;

public class Ghost extends AbstractCharacter {
    private volatile CellType boost;

    public CellType getBoost() {
        return boost;
    }

    public void setBoost(CellType boost) {
        this.boost = boost;
    }

    public Ghost(int xPosStart, int yPosStart) {
        this.xPosStart = xPosStart;
        this.yPosStart = yPosStart;

        this.xPos = xPosStart;
        this.yPos = yPosStart;

        health = 1;
        speed = 800;
        setIcon(new ImageIcon("resources/image/other/ghost.png"));
    }

    @Override
    public void run() {
        Random random = new Random();
        CellType[] array = {SCORE_BOOST, HEALTH_BOOST, INVINCIBILITY_BOOST, TIME_BOOST, SPEED_BOOST};

        while (isRunning) {
            if (random.nextInt(100) < 25)
                boost = array[random.nextInt(5)];

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}