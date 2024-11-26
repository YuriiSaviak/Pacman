package frame;

import model.Record;
import util.JComponentCreator;
import util.LeaderboardProcessor;

import javax.swing.*;
import java.util.Comparator;

public class LeaderboardFrame extends JFrame {
    public LeaderboardFrame() {
        JFrame frame = JComponentCreator.createFrame(DISPOSE_ON_CLOSE);
        frame.addWindowListener(JComponentCreator.closeWindow());

        frame.add(new JScrollPane(createList(LeaderboardProcessor.readRecords()),
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
    }
    private JList<Record> createList(java.util.List<Record> recordList) {
        recordList.sort(Comparator.comparingLong(Record::score).reversed());
        JList<Record> list = new JList<>(recordList.toArray(new Record[0]));

        JComponentCreator.createView(10, list);

        return list;
    }
}
