package cz.cvut.fel.pjv.main;
import java.net.URL;
import javax.sound.sampled.*;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 A class for playing sound effects and music tracks.
 */
public class Sound {
    URL[] url = new URL[10];  // An array of URLs representing the locations of the sound files.
    Clip clip;
    public Sound() {
        url[0] = this.getClass().getClassLoader().getResource("sound/rammstein_reisereise.wav");
        url[1] = this.getClass().getClassLoader().getResource("sound/open_door.wav");
        url[2] = this.getClass().getClassLoader().getResource("sound/pickup_key.wav");
        url[3] = this.getClass().getClassLoader().getResource("sound/SabatonBismark.wav");
        url[4] = this.getClass().getClassLoader().getResource("sound/pick_up_heart.wav");
        url[5] = this.getClass().getClassLoader().getResource("sound/levelUp.wav");
        url[6] = this.getClass().getClassLoader().getResource("sound/sword_attack.wav");
        url[7] = this.getClass().getClassLoader().getResource("sound/sword_miss.wav");
        url[8] = this.getClass().getClassLoader().getResource("sound/oup.wav");
    }

    /**
     Sets the music to play based on the index provided.
     @param idx The index of the music file to play
     @throws RuntimeException If there is an error in opening or playing the music file
     */
    public void setMusic(int idx) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url[idx]);
            clip = AudioSystem.getClip();
            clip.open(audioIn);  // Opens the audio input stream and starts playing the audio.
            clip.start();
        } catch (Exception e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     Pauses the currently playing music track.
     */
    public void pauseTrack() {
        if (clip != null)
            clip.stop();
    }
}
