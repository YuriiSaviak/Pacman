package util;

import game.Map;

import javax.swing.table.AbstractTableModel;

public class GameTableModel extends AbstractTableModel {
    private final Map map;

    public GameTableModel(Map map) {
        this.map = map;
    }

    @Override
    public int getRowCount() {
        return map.getMap().length;
    }

    @Override
    public int getColumnCount() {
        return map.getMap()[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return map.getPosition(columnIndex, rowIndex);
    }
}
