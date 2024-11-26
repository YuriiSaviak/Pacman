package game;

import frame.LeaderboardFrame;
import frame.MenuFrame;
import model.Ghost;
import model.Pacman;
import model.Record;
import util.LeaderboardProcessor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameProcessor implements Runnable {
    private final List<GhostProcessor> ghostProcessors = new ArrayList<>();
    private final PacmanProcessor pacmanProcessor;
    private final List<Ghost> ghostList = new ArrayList<>();
    private final List<Thread> threadList = new ArrayList<>();
    private final Pacman pacman = new Pacman(1, 1);
    private final Map map;
    private volatile boolean isRunning = true;
    private final JFrame frame;

    public Pacman getPacman() {
        return pacman;
    }

    public GameProcessor(Map map, JFrame frame) {
        this.map = map;
        pacmanProcessor = new PacmanProcessor(pacman, map);
        this.frame = frame;
        createGhosts();
    }

    private void createGhosts() {
        List<List<Integer>> offsets = map.getGhostPositions();

        for (List<Integer> offset : offsets)
            ghostList.add(new Ghost(offset.get(1), offset.get(0)));

        for (Ghost ghost : ghostList)
            ghostProcessors.add(new GhostProcessor(ghost, map, pacman));
    }

    public synchronized void timer(JLabel label) {
        new Thread(() -> {
            while (isRunning) {
                label.setText("Time: " + pacman.incrementTimer() + " seconds");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();
    }

    public synchronized void health(JLabel label) {
        new Thread(() -> {
            while (isRunning) {
                label.setText("Health: " + pacman.getHealth());

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();
    }

    public synchronized void score(JLabel label) {
        new Thread(() -> {
            while (isRunning) {
                label.setText("Score: " + pacman.getScore());

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();
    }

    private void startThreads() {
        for (Ghost ghost : ghostList)
            threadList.add(new Thread(ghost));

        for (GhostProcessor ghostProcessor : ghostProcessors)
            threadList.add(new Thread(ghostProcessor));

        threadList.addAll(new ArrayList<>() {
            {
                add(new Thread(pacmanProcessor));
                add(new Thread(pacman));
            }
        });

        for (Thread thread : threadList)
            thread.start();
    }

    private void stopThreads() {
        for (Thread thread : threadList)
            thread.interrupt();
    }

    public Ghost findGhostByPosition(int xPos, int yPos) {
        for (Ghost ghost : ghostList)
            if (ghost.getxPos() == xPos && ghost.getyPos() == yPos)
                return ghost;

        return null;
    }

    private void endGame() {
        String nickname = JOptionPane.showInputDialog(null, "Write your nickname", "Write nickname", JOptionPane.PLAIN_MESSAGE);

        if (nickname == null || nickname.isEmpty()) {
            SwingUtilities.invokeLater(MenuFrame::new);
            frame.dispose();
            return;
        }

        List<Record> recordList = LeaderboardProcessor.readRecords();
        recordList.add(new Record(nickname, pacman.getScore() / pacman.getTimer()));
        LeaderboardProcessor.writeRecords(recordList);

        SwingUtilities.invokeLater(LeaderboardFrame::new);
        frame.dispose();
    }


    @Override
    public void run() {
        startThreads();

        while (isRunning) {
            if (pacman.isDead()  || !map.hasFood()) {
                isRunning = false;
                stopThreads();
                endGame();
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

}
