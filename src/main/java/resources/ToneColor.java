package resources;

import java.awt.*;
import java.util.Random;

public enum ToneColor {
    GREY(204, 204, 204),
    RED(232, 135, 131),
    GREEN(161, 228, 148), //
    PURPLE(196, 189, 217),
    LIGHTBLUE(126, 190, 222),
    YELLOW(247, 246, 192),
    ORANGE(240, 206, 136),
    LIGHT_ORANGE(241, 209, 179), //
    BURGUNDY(210, 158, 233), //
    MUTED_RED(217, 138, 108), //
    YELLOW_ROSE(238, 214, 81),
    HOT_PINK(236, 177, 213),
    LIGHT_PURPLE(222, 204, 243), //
    BLOOD_RED(189, 51, 50), //
    WHITE(255, 255, 255),
    PINK(240, 209, 226);


    private final int r;
    private final int g;
    private final int b;
    private final String rgb;

    ToneColor(final int r, final int g, final int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.rgb = r + ", " + g + ", " + b;
    }

    public static ToneColor getRandomColor() {
        return ToneColor.values()[new Random().nextInt(ToneColor.values().length)];
    }

    public String getRGB() {
        return rgb;
    }

    public int getRed() {
        return r;
    }

    public int getGreen() {
        return g;
    }

    public int getBlue() {
        return r;
    }

    public Color getColor() {
        return new Color(r, g, b);
    }
}


