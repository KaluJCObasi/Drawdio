import java.awt.Color;

public class DrawingMaker {

    // Draws a series of concentric circles, with each consecutively drawn
    // circle differing in color, size, and boldness.
    public static void drawCircles(Draw drawing, Color[] colors, double posx, double posy,
                                   double growrt, double penGrowth) {

        double radius = 0.05; // starting radius of the circle
        double penSize = 0.002; // starting pen size for drawing

        drawing.enableDoubleBuffering();
        drawing.setPenRadius(penSize);

        // Draws 12 concentric circles. After 4 circles are drawn, the color of
        // the pen is changed, and the radius and penSize are adjusted such that
        // the next 4 circles are larger and bolder. (The color of each set of 4
        // circles is saved such that the next 4 circles are a different color
        // than the previous four.)

        // inner circles
        Color save = new Color(0, 0, 0); // "saved" color starts out as black
        Color penColor = colors[StdRandom.uniformInt(0, 12)];
        while (save == penColor) {
            penColor = colors[StdRandom.uniformInt(0, 12)];

        }
        save = penColor;

        drawing.setPenColor(penColor);
        for (int i = 0; i < 4; i++) {
            drawing.circle(posx, posy, radius);
            drawing.show();
            drawing.pause(100);
            radius += growrt;
            penSize += penGrowth;
            drawing.setPenRadius(penSize);
        }

        // middle circles
        while (save == penColor) {
            penColor = colors[StdRandom.uniformInt(0, 12)];

        }
        save = penColor;

        drawing.setPenColor(penColor);
        for (int i = 0; i < 4; i++) {
            drawing.circle(posx, posy, radius);
            drawing.show();
            drawing.pause(100);
            radius += growrt;
            penSize += penGrowth;
            drawing.setPenRadius(penSize);
        }

        // outer circles
        while (save == penColor) {
            penColor = colors[StdRandom.uniformInt(0, 12)];
        }

        drawing.setPenColor(penColor);
        for (int i = 0; i < 4; i++) {
            drawing.circle(posx, posy, radius);
            drawing.show();
            drawing.pause(100);
            radius += growrt;
            penSize += penGrowth;
            drawing.setPenRadius(penSize);
        }

        // Shows all circles for a brief moment,
        // then resets the drawing to completely black.
        drawing.pause(200);
        drawing.setPenColor(Color.BLACK);
        drawing.filledSquare(5, 5, 5);
        drawing.show();
        radius = 0.05;

    }

    // Precisely the same method as drawCircles,
    // but with concentric squares instead.
    public static void drawSquares(Draw drawing, Color[] colors, double posx,
                                   double posy, double growrt, double penGrowth) {

        double halflength = 0.05; // starting half-length of the square
        double penSize = 0.002; // starting pen size for drawing

        drawing.enableDoubleBuffering();
        drawing.setPenRadius();

        Color save = new Color(0, 0, 0);
        Color penColor = colors[StdRandom.uniformInt(0, 12)];
        while (save == penColor) {
            penColor = colors[StdRandom.uniformInt(0, 12)];

        }
        save = penColor;

        drawing.setPenColor(penColor);
        for (int i = 0; i < 4; i++) {
            drawing.square(posx, posy, halflength);
            drawing.show();
            drawing.pause(100);
            halflength += growrt;
            penSize += penGrowth;
            drawing.setPenRadius(penSize);
        }

        while (save == penColor) {
            penColor = colors[StdRandom.uniformInt(0, 12)];

        }
        save = penColor;

        drawing.setPenColor(penColor);
        for (int i = 0; i < 4; i++) {
            drawing.square(posx, posy, halflength);
            drawing.show();
            drawing.pause(100);
            halflength += growrt;
            penSize += penGrowth;
            drawing.setPenRadius(penSize);
        }

        while (save == penColor) {
            penColor = colors[StdRandom.uniformInt(0, 12)];

        }

        drawing.setPenColor(penColor);
        for (int i = 0; i < 4; i++) {
            drawing.square(posx, posy, halflength);
            drawing.show();
            drawing.pause(100);
            halflength += growrt;
            penSize += penGrowth;
            drawing.setPenRadius(penSize);
        }

        drawing.pause(200);
        drawing.setPenColor(Color.BLACK);
        drawing.filledSquare(5, 5, 5);
        drawing.show();
        halflength = 0.05;

    }


    // "clamp" function for audio files,
    // borrowed from the AudioCollage assignment
    public static double[] clamp(double[] h) {
        // Create an array identical to h[].
        double[] clamped = new double[h.length];
        for (int x = 0; x < h.length; x++) {
            clamped[x] = h[x];
        }
        // "Clamp" all the values in the array.
        for (int y = 0; y < h.length; y++) {
            if (clamped[y] > 1) {
                clamped[y] = 1;
            }
            else if (clamped[y] < -1) {
                clamped[y] = -1;
            }
        }
        return clamped;
    }

    // Tests DrawingMaker by creating an animated drawing
    // with the colors gathered in ColorMaker from the appropriate audio file.
    public static void main(String[] args) {

        Draw drawing = new Draw(); // new drawing; starts out as completely black
        drawing.setPenColor(Color.BLACK);
        drawing.setXscale(-20, 20);
        drawing.setYscale(-20, 20);
        drawing.setCanvasSize(1000, 1000);
        drawing.filledSquare(0, 0, 10);
        drawing.show();
        drawing.setPenRadius(0.01);
        drawing.enableDoubleBuffering();

        // Creates a color palette from the audio file
        // given by command-line input.
        String sample = "sm64lvl1.wav";
        double[] test = clamp(StdAudio.read(sample));
        Color[] palette = ColorMaker.palette(test);

        // Plays background music.
        StdAudio.playInBackground(sample);

        // Calls either drawCircles or drawSquares, a total of five times.
        for (int i = 0; i < 5; i++) {

            // x-coordinate of the shapes
            double posx = StdRandom.uniformDouble(0.25, 0.75);
            // y-coordinate of the shapes
            double posy = StdRandom.uniformDouble(0.25, 0.75);
            // growth rate of the size for each concentric shape
            double growrt = 0.02 * StdRandom.uniformDouble(0, 1)
                    + 0.005;
            // growth rate of the pen boldness for each concentric shape
            double penGrowth = 0.001;

            double chance = StdRandom.uniformDouble(0, 2);
            if (chance < 1) drawCircles(drawing, palette, posx,
                                        posy, growrt, penGrowth);
            else drawSquares(drawing, palette, posx, posy, growrt, penGrowth);
        }

        System.exit(0);
    }
}
