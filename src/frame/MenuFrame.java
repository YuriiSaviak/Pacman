package frame;

import util.JComponentCreator;

import javax.swing.*;
import java.awt.*;


public class MenuFrame extends JFrame {
    public MenuFrame() throws HeadlessException {
        JFrame frame = JComponentCreator.createFrame(EXIT_ON_CLOSE);

        JLabel name = new JLabel("Pacman", JLabel.CENTER);

        JButton newGameButton = JComponentCreator.createButton("New Game");
        JButton scoresButton = JComponentCreator.createButton("High Scores");
        JButton exitButton = JComponentCreator.createButton("Exit");

        newGameButton.addActionListener(a -> {
            String sizeString = JOptionPane.showInputDialog(null, "Write size number from 1 to 5", "Write board size", JOptionPane.PLAIN_MESSAGE);
            int size;

            try {
                size = Integer.parseInt(sizeString);
                if (size < 1 || size > 5)
                    return;
            } catch (NumberFormatException exception) {
                return;
            }

            int finalSize = size;
            SwingUtilities.invokeLater(() -> new GameFrame(finalSize));
            frame.dispose();
        });
        scoresButton.addActionListener(a -> {
            SwingUtilities.invokeLater(LeaderboardFrame::new);
            frame.dispose();
        });
        exitButton.addActionListener(a -> frame.dispose());

        JPanel options = new JPanel(new FlowLayout());//vopros
        JComponentCreator.addComponents(options, newGameButton, scoresButton, exitButton);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JComponentCreator.createView(12, options, centerPanel);
        JComponentCreator.createView(35, name);

        JComponentCreator.addComponents(centerPanel, name, Box.createRigidArea(new Dimension(300, 175)), options,
                new JLabel(new ImageIcon("resources/image/other/menu_pacman.gif")), Box.createVerticalGlue());

        frame.add(centerPanel, BorderLayout.CENTER);
    }
}