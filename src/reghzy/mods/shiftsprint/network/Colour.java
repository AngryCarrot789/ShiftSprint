package reghzy.mods.shiftsprint.network;

import logisticspipes.utils.string.ChatColor;

/**
 * Float (0 to 1) based colour, holding methods for converting back to RGB values and such
 */
public class Colour {
    public static Colour black = fromRGB(5, 5, 5);
    public static Colour darkBlue = fromRGB(5, 5, 160);
    public static Colour darkGreen = fromRGB(5, 160, 5);
    public static Colour darkAqua = fromRGB(5, 160, 60);
    public static Colour darkRed = fromRGB(160, 5, 5);
    public static Colour darkPurple = fromRGB(160, 5, 160);
    public static Colour gold = fromRGB(250, 165, 5);
    public static Colour grey = fromRGB(160, 160, 160);
    public static Colour darkGrey = fromRGB(60, 60, 60);
    public static Colour blue = fromRGB(75, 75, 240);
    public static Colour green = fromRGB(75, 240, 75);
    public static Colour aqua = fromRGB(75, 240, 240);
    public static Colour red = fromRGB(240, 75, 75);
    public static Colour lightPurple = fromRGB(240, 75, 250);
    public static Colour yellow = fromRGB(255, 240, 75);
    public static Colour white = fromRGB(240, 240, 240);
    public float r;
    public float g;
    public float b;
    public float opacity;

    public Colour(float r, float g, float b) {
        this.r = Maths.clamp(r, 0, 1);
        this.g = Maths.clamp(g, 0, 1);
        this.b = Maths.clamp(b, 0, 1);
        this.opacity = 1.0f;
    }

    public Colour(float r, float g, float b, float opacity) {
        this.r = Maths.clamp(r, 0, 1);
        this.g = Maths.clamp(g, 0, 1);
        this.b = Maths.clamp(b, 0, 1);
        this.opacity = Maths.clamp(opacity, 0.0f, 1.0f);
    }

    public static Colour fromRGB(int r, int g, int b) {
        return new Colour(((float) r) / 255.0f, ((float) g) / 255.0f, ((float) b) / 255.0f);
    }

    public static Colour fromRGB(int rgb) {
        return fromRGB((rgb >> 16) & 255, (rgb >> 8) & 255, rgb & 255);
    }

    public static Colour fromRGBA(int r, int g, int b, int a) {
        return new Colour(((float) r) / 255.0f, ((float) g) / 255.0f, ((float) b) / 255.0f, ((float) a) / 255.0f);
    }

    public static Colour fromChatColour(ChatColor tpsColour) {
        switch (tpsColour) {
            case BLACK:
                return black;
            case DARK_BLUE:
                return darkBlue;
            case DARK_GREEN:
                return darkGreen;
            case DARK_AQUA:
                return darkAqua;
            case DARK_RED:
                return darkRed;
            case DARK_PURPLE:
                return darkPurple;
            case GOLD:
                return gold;
            case GRAY:
                return grey;
            case DARK_GRAY:
                return darkGrey;
            case BLUE:
                return blue;
            case GREEN:
                return green;
            case AQUA:
                return aqua;
            case RED:
                return red;
            case LIGHT_PURPLE:
                return lightPurple;
            case YELLOW:
                return yellow;
            case WHITE:
            default:
                return white;
        }
    }

    public int toRGB() {
        return ((int) (this.r * 255.0f)) << 16 | ((int) (this.g * 255.0f)) << 8 | ((int) (this.b * 255.0f));
    }

    public int toRGBA() {
        return ((int) (this.r * 255.0f)) << 24 |
               ((int) (this.g * 255.0f)) << 16 |
               ((int) (this.b * 255.0f)) << 8 |
               ((int) (this.opacity * 255.0f));
    }

    public void fromRGBA(int rgba) {
        this.r = ((rgba >> 24) & 255) / 255.0f;
        this.g = ((rgba >> 16) & 255) / 255.0f;
        this.b = ((rgba >> 8) & 255) / 255.0f;
        this.opacity = (rgba & 255) / 255.0f;
    }

    public int getRed() {
        return Maths.clamp((int) (this.r * 255.0f), 0, 255);
    }

    public int getGreen() {
        return Maths.clamp((int) (this.g * 255.0f), 0, 255);
    }

    public int getBlue() {
        return Maths.clamp((int) (this.b * 255.0f), 0, 255);
    }

    public int getOpacity() {
        return Maths.clamp((int) (this.opacity * 255.0f), 0, 255);
    }
}
