package util;

import game.GameProcessor;
import model.CellType;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

import static model.CellType.*;

public class GameRenderer extends DefaultTableCellRenderer {
    private final GameProcessor gameProcessor;

    public GameRenderer(GameProcessor gameProcessor) {
        this.gameProcessor = gameProcessor;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        CellType cell = (CellType) value;

        table.repaint();

        if (cell == PACMAN || cell == PACMAN_INVINCIBLE)
            return gameProcessor.getPacman();
        else if (cell == GHOST || cell == GHOST_EDIBLE)
            return gameProcessor.findGhostByPosition(column, row);

        String name = switch (cell) {
            case BLOCK -> "wall.png";
            case PASSAGE -> "hall.png";
            case EDIBLE -> "food.png";
            default -> "boost.png";
        };

        JLabel label = new JLabel();

        label.setIcon(new ImageIcon("resources/image/other/" + name));
        label.setOpaque(true);
        label.setBackground(Color.BLACK);

        return label;
    }
}
