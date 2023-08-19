public class ButtonEventHandler {
    public final MusicVisualizer visualizer;

    public ButtonEventHandler(MusicVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    // Method to handle the mouse hovering over buttons
    public boolean mouseOver(int x, int y, int width, int height) {
        // Return if the mouse is inside the given borders
        return (visualizer.mouseX > x && visualizer.mouseX < (x + width)) && (visualizer.mouseY > y && visualizer.mouseY < (y + height));
    }

    // Method to handle the mouseClicked event
    public void handleMouseClicked() {
        // If the mouse is over the button, toggle play/pause
        if (mouseOver(visualizer.pauseCoords[0], visualizer.pauseCoords[1], visualizer.pause.width, visualizer.pause.height)) {
            visualizer.buttonFunctions.togglePause();
        }

        // If the mouse is over the mute/unmute buttons, toggle volume on/off
        if (mouseOver(visualizer.muteCoords[0], visualizer.muteCoords[1], visualizer.mute.width, visualizer.mute.height)) {
            visualizer.buttonFunctions.toggleMute();
        }

        // If the mouse is over the fast-forward button, move to the next song
        if (mouseOver(visualizer.ffCoords[0], visualizer.ffCoords[1], visualizer.ff.width, visualizer.ff.height)) {
            visualizer.buttonFunctions.fastForward();
        }

        // If the mouse is over the rewind button, move to the previous song
        if (mouseOver(visualizer.rrCoords[0], visualizer.rrCoords[1], visualizer.rr.width, visualizer.rr.height)) {
            visualizer.buttonFunctions.rewind();
        }

        // If the mouse is over the repeat button, make the current song repeat
        if (mouseOver(visualizer.repeatCoords[0], visualizer.repeatCoords[1], visualizer.repeat.width, visualizer.repeat.height)) {
            visualizer.buttonFunctions.toggleRepeat();
        }
    }
}
