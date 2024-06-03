import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

// An extended TimerTask which calls functions
// from DrawingMaker at a fixed rate specified by a Timer.
class DrawThings extends TimerTask {

    private Color[] palette; // color palette created by audio file in ColorMaker
    private double positionX; // x-coordinate of the shapes
    private double positionY; // y-coordinate of the shapes
    private double growthrate; // growth rate of each concentric shape
    private Draw drawing; // initial Draw object
    private int count; // stops the timer when 20 shapes have been drawn

    // constructor
    public DrawThings(Draw drawing2, String musicFile) {
        this.drawing = drawing2;
        // instance variables
        // audio file
        double[] music = StdAudio.read(musicFile);
        palette = ColorMaker.palette(music);
        positionX = StdRandom.uniformDouble(0.25, 0.75);
        positionY = StdRandom.uniformDouble(0.25, 0.75);
        growthrate = 0.02 * StdRandom.uniformDouble(0, 1) + 0.005;

    }

    // This run() method is the "heart" of the TimerTask; that is, it is the task
    // which is performed in each time interval.
    public void run() {

        // "Flips a coin" which determines whether to draw a series of concentric
        // circles or a series of concentric squares.
        double chance = StdRandom.uniformDouble();
        double penGrowth = 0.001;
        if (chance > 0.5)
            DrawingMaker.drawCircles(drawing, palette, positionX,
                                     positionY, growthrate, penGrowth);
        else
            DrawingMaker.drawSquares(drawing, palette, positionX,
                                     positionY, growthrate, penGrowth);


        // Randomizes the position and growth rate for the next series of shapes.
        positionX = StdRandom.uniformDouble(0.25, 0.75);
        positionY = StdRandom.uniformDouble(0.25, 0.75);
        growthrate = 0.02 * StdRandom.uniformDouble(0, 1) + 0.005;

        // Resets the drawing back to a completely black background.
        drawing.setPenColor(Color.BLACK);
        drawing.filledSquare(0, 0, 10);
        drawing.show();
        drawing.setPenRadius(0.01);

        // After 20 iterations, the program stops drawing.
        count++;
        if (count == 20) {
            System.exit(0);
        }
    }
}

// The main event!! Takes in an audio file, uses its data to create colors in
// ColorMaker, and incorporates those colors to create DrawingMaker animations
// at a fixed rate. Also prints each color in the palette to standard output,
// and lists the audio file and its color values at the top of the drawing
// window.
public class Drawdio {

    public static void main(String[] args) {

        Draw drawdio = new Draw();
        Timer timer = new Timer();
        TimerTask drawIt = new DrawThings(drawdio, args[0]);

        // initializes the drawing to a completely black screen
        drawdio.setPenColor(Color.BLACK);
        drawdio.setXscale(-20, 20);
        drawdio.setYscale(-20, 20);
        drawdio.setCanvasSize(1000, 1000);
        drawdio.filledSquare(0, 0, 10);
        drawdio.show();
        drawdio.setPenRadius(0.01);
        drawdio.enableDoubleBuffering();

        String audio = args[0]; // audio file from command line
        double[] test = DrawingMaker.clamp(StdAudio.read(audio)); // audio array

        // Sets the drawing title with the audio file name.
        drawdio.setTitle(
                "Drawdio with " + audio);

        // Prints the audio file name and the color palette to standard output.
        StdOut.println("Welcome to Drawdio!!");
        StdOut.println("You chose this audio: " + audio);
        StdOut.println("That audio created these colors:");
        for (int i = 0; i < ColorMaker.palette(test).length; i++) {
            StdOut.println("Color " + (i + 1) + ": " + ColorMaker.palette(test)[i]);
        }
        StdOut.println("Thanks for playing! - Kalu and Yuyu");

        // Plays audio in the background.
        StdAudio.playInBackground(audio);

        // Creates animated drawings every two seconds.
        timer.schedule(drawIt, 0, 2000);
    }
}

