package util;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public final class Preloader {
    public static void loadFont() {
        try {
            GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources/8-bit.ttf")).deriveFont(12f));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}