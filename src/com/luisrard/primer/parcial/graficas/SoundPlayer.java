package com.luisrard.primer.parcial.graficas;
import javax.sound.sampled.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SoundPlayer {
    private final boolean loop;
    private Clip clip;
    private final String soundFile;

    public SoundPlayer(String soundFile, boolean loop) {
        this.loop = loop;
        this.soundFile = soundFile;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(SoundPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playSound() {
        try {
            if (!clip.isOpen() || !clip.isRunning()) {
                AudioInputStream audioInputStream  = obtainAudioInputStream();
                if (audioInputStream != null) {
                    clip.open(audioInputStream);
                    if (loop) {
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                    }
                    clip.start();
                    clip.start();
                    clip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            clip.close();
                        }
                    });
                }
            }
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    private AudioInputStream obtainAudioInputStream() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            return AudioSystem.getAudioInputStream(classLoader.getResource("sound/" + this.soundFile));
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
