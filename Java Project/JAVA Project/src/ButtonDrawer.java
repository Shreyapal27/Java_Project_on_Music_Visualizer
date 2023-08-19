import processing.core.PApplet;
import processing.core.PImage;

public class ButtonDrawer {
    public final PApplet parent;
    public final int buttonColor;
    public final int buttonHoverAlpha;
    public final boolean isPaused;
    public final boolean isMuted;
    public final boolean isRepeating;
    public final PImage pause;
    public final PImage play;
    public final PImage mute;
    public final PImage unmute;
    public final PImage ff;
    public final PImage rr;
    public final PImage repeat;

    public ButtonDrawer(PApplet parent, int buttonColor, int buttonHoverAlpha,
                        boolean isPaused, boolean isMuted, boolean isRepeating,
                        PImage pause, PImage play, PImage mute, PImage unmute,
                        PImage ff, PImage rr, PImage repeat) {
        this.parent = parent;
        this.buttonColor = buttonColor;
        this.buttonHoverAlpha = buttonHoverAlpha;
        this.isPaused = isPaused;
        this.isMuted = isMuted;
        this.isRepeating = isRepeating;
        this.pause = pause;
        this.play = play;
        this.mute = mute;
        this.unmute = unmute;
        this.ff = ff;
        this.rr = rr;
        this.repeat = repeat;
    }
    // ===============================
    // Methods to Draw Various Buttons
    // ===============================
    // Method to draw the play/pause buttons
    public void drawPauseButtons(int[] pauseCoords) {
        int btnAlpha = mouseOver(pauseCoords[0], pauseCoords[1], pause.width, pause.height) ? buttonHoverAlpha : 255;
        tint(buttonColor, btnAlpha);
        PImage playPauseImageToDraw = isPaused ? play : pause;
        parent.image(playPauseImageToDraw, pauseCoords[0], pauseCoords[1]);
    }

    public void drawUnmuteButtons(int[] muteCoords) {
        int btnAlpha = mouseOver(muteCoords[0], muteCoords[1], mute.width, mute.height) ? buttonHoverAlpha : 255;
        tint(buttonColor, btnAlpha);
        mute.resize(50, 50);
        unmute.resize(50, 50);
        PImage imageToDraw = isMuted ? unmute : mute;
        parent.image(imageToDraw, muteCoords[0], muteCoords[1]);
    }

    public void drawFFButton(int[] ffCoords) {
        int btnAlpha = mouseOver(ffCoords[0], ffCoords[1], ff.width, ff.height) ? buttonHoverAlpha : 255;
        tint(buttonColor, btnAlpha);
        ff.resize(50, 50);
        parent.image(ff, ffCoords[0], ffCoords[1]);
    }

    public void drawRRButton(int[] rrCoords) {
        int btnAlpha = mouseOver(rrCoords[0], rrCoords[1], rr.width, rr.height) ? buttonHoverAlpha : 255;
        tint(buttonColor, btnAlpha);
        rr.resize(50, 50);
        parent.image(rr, rrCoords[0], rrCoords[1]);
    }

    public void drawRepeatButton(int[] repeatCoords) {
        int btnAlpha = mouseOver(repeatCoords[0], repeatCoords[1], repeat.width, repeat.height) ? buttonHoverAlpha : 255;
        int btnColor = isRepeating ? parent.color(156, 228, 255) : parent.color(255);
        tint(btnColor, btnAlpha);
        repeat.resize(50, 50);
        parent.image(repeat, repeatCoords[0], repeatCoords[1]);
    }

    public boolean mouseOver(int x, int y, int width, int height) {
        return parent.mouseX >= x && parent.mouseX <= x + width &&
                parent.mouseY >= y && parent.mouseY <= y + height;
    }

    public void tint(int color, int alpha) {
        parent.tint(parent.red(color), parent.green(color), parent.blue(color), alpha);
    }
}
