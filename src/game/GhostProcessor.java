package game;

import model.CellType;
import model.Ghost;
import model.Pacman;

import java.util.Random;

import static model.CellType.*;

public class GhostProcessor implements Runnable {
    private final Ghost ghost;
    private final Map map;
    private final Pacman pacman;

    public GhostProcessor(Ghost ghost, Map map, Pacman pacman) {
        this.ghost = ghost;
        this.map = map;
        this.pacman = pacman;
    }

    private synchronized void makeMove(int xPos, int yPos) {
        int currentxPos = ghost.getxPos();
        int currentyPos = ghost.getyPos();
        int futurexPos = currentxPos + xPos;
        int futureyPos = currentyPos + yPos;
        CellType futureCell = map.getPosition(futurexPos, futureyPos);
        CellType currentCell = map.getPosition(currentxPos, currentyPos);

        switch (futureCell) {
            case BLOCK, GHOST, GHOST_EDIBLE, PACMAN_INVINCIBLE -> {
                return;
            }
            case PACMAN -> {
                pacman.hit();
                map.setPosition(futurexPos, futureyPos, GHOST);
            }
            case EDIBLE -> map.setPosition(futurexPos, futureyPos, GHOST_EDIBLE);
            default -> map.setPosition(futurexPos, futureyPos, GHOST);
        }

        switch (currentCell) {
            case GHOST -> map.setPosition(currentxPos, currentyPos, PASSAGE);
            case GHOST_EDIBLE -> {
                if (ghost.getBoost() != null) {
                    pacman.givePoints(100);
                    map.setPosition(currentxPos, currentyPos, ghost.getBoost());
                    ghost.setBoost(null);
                } else
                    map.setPosition(currentxPos, currentyPos, EDIBLE);
            }
        }

        if (futureCell == EDIBLE || ifBoost(futureCell.name()) || futureCell == PASSAGE)
            ghost.setPos(futurexPos, futureyPos);
    }

    private boolean ifBoost(String name) {
        return "BOOST".equals(name.substring(name.length() - 5));
    }

    @Override
    public void run() {
        Random random = new Random();

        while (ghost.isRunning()) {
            int move = random.nextInt(4);

            switch (move) {
                case 0 -> makeMove(-1, 0);
                case 1 -> makeMove(1, 0);
                case 2 -> makeMove(0, -1);
                case 3 -> makeMove(0, 1);
            }

            try {
                Thread.sleep(ghost.getSpeed());//vopros
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
