public class ButtonFunctions {
    public final MusicVisualizer visualizer;

    public ButtonFunctions(MusicVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    // Method to toggle the music on/off
    public void togglePause() {
        // If the music is being played, stop it and vice versa
        visualizer.isPaused = !visualizer.isPaused;

        if (visualizer.isPaused)
            visualizer.audio[visualizer.counter].pause();
        else
            visualizer.audio[visualizer.counter].play();
    }

    // Method to toggle the volume on/off
    public void toggleMute() {
        // If the volume is on, mute it and vice versa
        visualizer.isMuted = !visualizer.isMuted;

        if (visualizer.isMuted)
            visualizer.audio[visualizer.counter].mute();
        else
            visualizer.audio[visualizer.counter].unmute();
    }

    // Method to fast-forward to the next song
    public void fastForward() {
        // Stop the current song
        visualizer.audio[visualizer.counter].pause();

        // Increment count, thereby moving to the next song
        visualizer.counter++;

        if (visualizer.counter > visualizer.songCount - 1) {
            // Return to the first song
            visualizer.counter = 0;
        }

        // Play the next song, as long as the player isn't paused
        if (!visualizer.isPaused)
            visualizer.audio[visualizer.counter].play();
        else
            visualizer.audio[visualizer.counter].pause();

        // Check if the music's volume should be on
        if (visualizer.isMuted)
            visualizer.audio[visualizer.counter].mute();
        else
            visualizer.audio[visualizer.counter].unmute();

        // Rewind the previous audio track
        int idxToRewind = (visualizer.counter - 1) < 0 ? visualizer.songCount - 1 : (visualizer.counter - 1);
        visualizer.audio[idxToRewind].rewind();
    }

    // Method to move to the previous song
    public void rewind() {
        // Stop the current song
        visualizer.audio[visualizer.counter].pause();

        // Decrement count, thereby moving to the previous song
        visualizer.counter--;

        if (visualizer.counter < 0) {
            // Return to the first song
            visualizer.counter = visualizer.songCount - 1;
        }

        // Play the next song, as long as the player isn't paused
        if (!visualizer.isPaused)
            visualizer.audio[visualizer.counter].play();
        else
            visualizer.audio[visualizer.counter].pause();

        // Check if the music's volume should be on
        if (visualizer.isMuted)
            visualizer.audio[visualizer.counter].mute();
        else
            visualizer.audio[visualizer.counter].unmute();

        // Rewind the previously played audio track
        int idxToRewind = (visualizer.counter + 1) > visualizer.songCount - 1 ? 0 : (visualizer.counter + 1);
        visualizer.audio[idxToRewind].rewind();
    }

    // Method to toggle repeat on a song on/off
    public void toggleRepeat() {
        // If the song is not repeating, make it do so, and vice versa
        visualizer.isRepeating = !visualizer.isRepeating;
    }
}
