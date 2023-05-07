package cz.cvut.fel.pjv.main;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public class Sound {
    URL[] url = new URL[5];
    Clip clip;
    public Sound() {
        url[0] = this.getClass().getClassLoader().getResource("sound/rammstein_reisereise.wav");
        url[1] = this.getClass().getClassLoader().getResource("sound/open_door.wav");
        url[2] = this.getClass().getClassLoader().getResource("sound/pickup_key.wav");
        url[3] = this.getClass().getClassLoader().getResource("sound/SabatonBismark.wav");
    }

    public void setMusic(int idx) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url[idx]);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void pauseTrack() {
        clip.stop();
    }
}
