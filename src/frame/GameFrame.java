package frame;

import game.GameProcessor;
import game.Map;
import util.GameRenderer;
import util.GameTableModel;
import util.JComponentCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

public class GameFrame extends JFrame {
    private final GameProcessor gameProcessor;

    public GameFrame(int size) {
        JFrame frame = JComponentCreator.createFrame(DISPOSE_ON_CLOSE);
        frame.addWindowListener(JComponentCreator.closeWindow());
        frame.requestFocusInWindow();//vopros

        Map map = new Map(size);
        gameProcessor = new GameProcessor(map, frame);
        new Thread(gameProcessor).start();

        JPanel bar = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        JComponentCreator.createView(8, bar);

        JLabel health = new JLabel();
        JLabel timer = new JLabel();
        JLabel score = new JLabel();
        JLabel heart = new JLabel(new ImageIcon("resources/image/other/heart.png"));

        JComponentCreator.createView(14, score, health, timer);
        JComponentCreator.addComponents(bar, score, health, heart, timer);

        gameProcessor.health(health);
        gameProcessor.timer(timer);
        gameProcessor.score(score);

        JTable table = new JTable(new GameTableModel(map));//vopros
        table.setDefaultRenderer(Object.class, new GameRenderer(gameProcessor));//vopros
        table.setBackground(Color.BLACK);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                return;
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == VK_RIGHT || key == VK_LEFT || key == VK_UP || key == VK_DOWN)
                    gameProcessor.getPacman().setKey(key);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                return;
            }
        });

        // inspired by https://stackoverflow.com/questions/17627431/auto-resizing-the-jtable-column-widths
        table.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                for (int column = 0; column < table.getColumnCount(); column++)
                    table.getColumnModel().getColumn(column).setPreferredWidth(table.getWidth() / table.getColumnCount());

                table.setRowHeight(table.getHeight() / table.getRowCount());
            }
        });

        frame.add(bar, BorderLayout.NORTH);
        frame.add(table, BorderLayout.CENTER);
    }
}
