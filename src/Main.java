import javax.swing.*;
import frame.MenuFrame;
import util.Preloader;

public class Main {
    public static void main(String[] args) {
        Preloader.loadFont();
        SwingUtilities.invokeLater(MenuFrame::new);
    }
}
