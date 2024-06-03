import java.awt.Color;

public class ColorMaker {
    public static double beginning(double[] music) {

        // Averages the values in the 1st third of the audio file.
        double total = 0;
        for (int i = 0; i < (music.length / 3); i++) {
            total += music[i];
        }
        double r = Math.abs(total / (music.length / 3.0));

        // Turns this average into a value with at least
        // three digits before the decimal point.
        while (r / 100 < 1) {
            r *= 10;
        }

        // Caps the value at 255 to make it a legal color argument.
        if (r > 255) r = 255;
        return r;
    }

    public static double middle(double[] music) {

        // Averages the values in the 2nd third of the audio file.
        double total = 0;
        for (int i = (music.length / 3); i < (2 * (music.length / 3)); i++) {
            total += music[i];
        }
        double g = Math.abs(total / (music.length / 3.0));

        // Turns this average into a value with at least
        // three digits before the decimal point.
        while (g / 100 < 1) {
            g *= 10;
        }

        // Caps the value at 255 to make it a legal color argument.
        if (g > 255) g = 255;
        return g;
    }

    public static double end(double[] music) {

        // Averages the values in the last third of the audio file.
        double total = 0;
        for (int i = 2 * (music.length / 3); i < (music.length); i++) {
            total += music[i];
        }
        double b = Math.abs(total / (music.length / 3.0));

        // Turns this average into a value with at least
        // three digits before the decimal point.
        while (b / 100 < 1) {
            b *= 10;
        }

        // Caps the value at 255 to make it a legal color argument.
        if (b > 255) b = 255;
        return b;
    }

    // Creates an array of distinct colors using the integers gathered
    // from the previous three methods.
    public static Color[] palette(double[] music) {

        int r = (int) beginning(music); // 1st third
        int g = (int) middle(music); // 2nd third
        int b = (int) end(music); // 3rd third

        // Each value only appears once, in the same order.
        Color rgb = new Color(r, g, b);
        Color gbr = new Color(g, b, r);
        Color brg = new Color(b, r, g);

        // One value in each color is (roughly) halved.
        Color hgb = new Color(r / 2, g, b);
        Color rhb = new Color(r, g / 2, b);
        Color rgh = new Color(r, g, b / 2);

        // Each value "overwrites" the one to its right.
        Color rrb = new Color(r, r, b);
        Color rgg = new Color(r, g, g);
        Color bgb = new Color(b, g, b);

        // Each value "overwrites" the one to its left.
        Color rbb = new Color(r, b, b);
        Color ggb = new Color(g, g, b);
        Color rgr = new Color(r, g, r);

        // array of colors, referred to in the future as the "palette"
        Color[] colors = {
                rgb, gbr, brg, hgb, rhb, rgh,
                rbb, ggb, rgr, rrb, rgg, bgb
        };
        return colors;

    }

    // Tests ColorMaker by printing the appropriate values
    // of a predetermined array to standard output.
    public static void main(String[] args) {
        double[] test = {
                0.00120, 0.00240, 0.00360, 0.00100, 0.00100, 0.00100, 0.00100, 0.00200, 0.00300
        };
        StdOut.println(beginning(test) + " " + middle(test) + " " + end(test));
        for (int i = 0; i < palette(test).length; i++) {
            StdOut.println(palette(test)[i]);
        }
    }
}
