// Import libraries
import ddf.minim.*;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;
import processing.core.PImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MusicVisualizer extends PApplet {
    // ========================
    // Declare global variables
    // ========================
    // Minim objects
    public Minim minim;
    public AudioPlayer[] audio;
    public FFT fft;

    // List of songs and counter
    public int counter = 0;
    public String[] mp3Files;
    public int songCount = 0;

    // Vertical center of canvas
    public int yCenter;

    // Horizontal center of canvas
    public int xCenter;

    // Keep track of whether or not the music is playing
    public boolean isPaused;
    public boolean isMuted;

    // Keep track of whether or not a song is repeating
    public boolean isRepeating;

    // Images for play/pause icons
    PImage play;
    PImage pause;

    // Images for mute/unmute icons
    PImage mute;
    PImage unmute;

    // Image for fast-forward icon
    PImage ff;

    // Image for rewind icon
    PImage rr;

    // Image for repeat button
    PImage repeat;

    public ButtonDrawer buttonDrawer;  // Declare buttonDrawer as an instance variable
    public ButtonEventHandler buttonEventHandler; // Declare buttonEventHandler as an instance variable
    public ButtonFunctions buttonFunctions; // Instance of ButtonFunctions
    // Coordinates for play/pause button
    public final int[] pauseCoords = {90, 20};
    public final int[] muteCoords = {1850, 20};
    public final int[] ffCoords = {160, 20};
    public final int[] rrCoords = {20, 20};
    public final int[] repeatCoords = {2000, 20};

    // Colors for channels
    public final int leftChannelColor = color(37, 152, 230);
    public final int rightChannelColor = color(26, 111, 214);


    // ===========
    // Main method
    // ===========
    public static void main(String[] args) {
        // Processing configuration in main class
        PApplet.main("MusicVisualizer");
    }

    // =================
    // Processing Config
    // =================
    public void settings() {
        // Choose the size for the canvas
//        size(1000, 500);
        fullScreen();
    }

    public void setup() {

        // Determine the vertical & horizontal center of the canvas
        yCenter = height / 2;
        xCenter = width / 2;

        // Load images
        play = loadImage("./icons/play.png");
        pause = loadImage("./icons/pause.png");
        mute = loadImage("./icons/mute.png");
        unmute = loadImage("./icons/unmute.png");
        ff = loadImage("./icons/ff.png");
        rr = loadImage("./icons/rr.png");
        repeat = loadImage("./icons/repeat.png");
        // Create the ButtonDrawer instance
        buttonDrawer = new ButtonDrawer(this, color(255), 200, isPaused, isMuted, isRepeating,
                pause, play, mute, unmute, ff, rr, repeat);

        // Create the ButtonEventHandler instance
        buttonEventHandler = new ButtonEventHandler(this);

        // Create an instance of ButtonFunctions
        buttonFunctions = new ButtonFunctions(this);

        // Fill mp3Files array with song paths from a text file
        try {
            // Open the text file
            File txtFile = new File("./resources/songPaths.txt");
            Scanner reader1 = new Scanner(txtFile);
            Scanner reader2 = new Scanner(txtFile);

            // Find the number of songs in the file
            while (reader1.hasNextLine()) {
                reader1.nextLine();
                // Increment the number of songs
                songCount++;
            }

            // Create space in the mp3Files array
            mp3Files = new String[songCount];
            audio = new AudioPlayer[songCount];

            // Get each line of the file and add it to the mp3Files array
            int i = 0;
            while (reader2.hasNextLine()) {
                String data = reader2.nextLine();
                mp3Files[i] = data;
                i++;
            }

            // Close the file reader
            reader1.close();
            reader2.close();

        } catch (FileNotFoundException e) {
            // Print that an error occurred
            System.out.println("An error occurred when trying to open the text file.");
            e.printStackTrace();
        }

        // Minim configuration
        // Load the mp3 file
        minim = new Minim(this);

        // Create the audio players for each song
        for (int i = 0; i < songCount; i++) {
            //String filePath = "C:/Users/Piyush Chauhan/Downloads/Something.mp3";
//            audio[i] = minim.loadFile(filePath);
            String filePath = mp3Files[i] + ".mp3";
            audio[i] = minim.loadFile(filePath);
            System.out.println(mp3Files[i]);
        }

        // Play the first song
        if (!isPaused) audio[counter].play();
        else audio[counter].pause();

        // Initialize FFT
        fft = new FFT(audio[counter].bufferSize(), audio[counter].sampleRate());

        // Initially, the music will be playing and not muted
        isPaused = false;
        isMuted = false;
        isRepeating = false;
    }

    // =====================
    // Drawing on the canvas
    // =====================
    public void draw() {
        // Re-draw the background to hide previous frames
        background(0);

        // Check if the next song should be played
        if (!audio[counter].isPlaying() && !isPaused) {
            // Check if the current song should be repeated
            if (!isRepeating) {
                // Increment count, thereby moving to the next song
                counter++;

                if (counter > songCount - 1) {
                    // Return to the first song
                    counter = 0;
                }

                // Play the next song
                audio[counter].play();

                // Check if the music's volume should be on
                if (isMuted) audio[counter].mute();
                else audio[counter].unmute();

                // Rewind the previous audio track
                int idxToRewind = (counter - 1) < 0 ? songCount - 1 : (counter - 1);
                audio[idxToRewind].rewind();
            } else {
                // Rewind the current song
                audio[counter].rewind();

                // Play the current song again
                audio[counter].play();

                // Check if the music's volume should be on
                if (isMuted) audio[counter].mute();
                else audio[counter].unmute();
                // Call the drawPauseButtons method on the buttonDrawer instance
                buttonDrawer.drawPauseButtons(pauseCoords);
            }
        }

        // Grab the audio buffers for the left and right channels of the music
        float[] leftChannel = audio[counter].left.toArray();
        float[] rightChannel = audio[counter].right.toArray();

        // Use FFT to process new sample of music
        fft.forward(audio[counter].mix);

        // Draw buttons using the ButtonDrawer instance
        buttonDrawer.drawPauseButtons(pauseCoords);
        buttonDrawer.drawUnmuteButtons(muteCoords);
        buttonDrawer.drawFFButton(ffCoords);
        buttonDrawer.drawRRButton(rrCoords);
        buttonDrawer.drawRepeatButton(repeatCoords);

        // Iterate over every sample in the music
        for (int i = 0; i < leftChannel.length - 1; i++) {
            // Draw the left channel
            drawChannel(leftChannel, i, -1, leftChannelColor);

            // Draw the right channel
            drawChannel(rightChannel, i, 1, rightChannelColor);
        }

        // Iterate over the frequencies
        for (int i = 0; i < fft.specSize(); i++) {
            // Draw the current frequency
            drawFrequency(i);
        }
    }

    public void mouseClicked() {
        buttonEventHandler.handleMouseClicked();
    }

    // =======================================================
    // Methods to Draw the Frequency and Channels of the Music
    // =======================================================
    // Method to draw the channels of music
    public void drawChannel(float[] channel, int index, int direction, int color) {
        // Make the lines have a thickness of 2
        strokeWeight(2);
//        float x = map(index, 0, channel.length - 1, 0, width);
//        float y = yCenter + channel[index] * yCenter * direction;
//        float nextX = map(index + 1, 0, channel.length - 1, 0, width);
//        float nextY = yCenter + channel[index + 1] * yCenter * direction;
//        stroke(color);
//        line(x, y, nextX, nextY);

        // Calculate the x and y coordinates based on the canvas dimensions
        float x1 = map(index, 0, channel.length, 0, width);
        float y1 = height / 2;
        float x2 = map(index + 1, 0, channel.length, 0, width);

        // Draw 2 lines representing the channel of music at index
        for (int i = 1; i <= 2; i++) {
            // Set the color and transparency of the line
            stroke(color, (float) 100 / sq(i));

            // Draw the line
            line(x1, y1, x2, yCenter + (direction * abs(channel[index + 1] * (200 * sq(i)))));
        }
    }

    // Method to draw circles that illustrate the frequency of the music
    public void drawFrequency(int index) {
        // Draw 2 circles representing the music's frequency
        for (int i = 1; i < 2; i++) {
            // Determine the color and transparency of the circle
            fill(255, (float) 100 / sq(i));
            stroke(255, (float) 100 / sq(i));

            // Draw the circle
            circle(xCenter, yCenter, fft.getBand(index) * (3 * sq(i)));
        }
    }
}