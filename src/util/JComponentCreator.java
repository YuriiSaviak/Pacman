package util;

import frame.MenuFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class JComponentCreator {
    public static JFrame createFrame(int exitMode) {
        JFrame frame = new JFrame("Pacman");

        frame.pack();
        frame.setSize(1024, 768);
        frame.setDefaultCloseOperation(exitMode);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setIconImage(new ImageIcon("resources/image/other/game_icon.jpg").getImage());
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setVisible(true);

        return frame;
    }

    public static JButton createButton(String text) {
        JButton button = new JButton(text);

        button.setFont(new Font("QuinqueFive", Font.PLAIN, 8));
        button.setBackground(new Color(255, 255, 0));
        button.setForeground(Color.BLACK);
        button.setPreferredSize(new Dimension(150, 50));

        return button;
    }

    public static WindowAdapter closeWindow() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SwingUtilities.invokeLater(MenuFrame::new);
            }
        };
    }

    public static void createView(int size, JComponent... components) {//vopros
        for (JComponent component : components) {
            component.setFont(new Font("QuinqueFive", Font.PLAIN, size));
            component.setBackground(Color.BLACK);
            component.setForeground(new Color(255, 255, 0));
        }
    }

    public static void addComponents(JPanel panel, Component... components) {
        for(Component component : components)
            panel.add(component);
    }
}
