package com.project.RabbitRun.main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

/**
 * Manages the sound effects in the "Rabbit Run" game, including loading,
 * playing, looping, and stopping audio clips.
 */
public class Sound {

    /** The audio clip used for playing sounds. */
    Clip clip;

    /** Array storing URLs of the sound files used in the game. */
    URL[] soundURL = new URL[15];

    /**
     * Initializes sound resources by loading audio files into an array.
     */
    public Sound() {

        soundURL[0] = getClass().getResource("/sound/Game_Sound.wav");
        soundURL[1] = getClass().getResource("/sound/Reward_Sound.wav");
        soundURL[2] = getClass().getResource("/sound/BonusReward_Sound.wav");
        soundURL[3] = getClass().getResource("/sound/Punishment_Sound.wav");
        soundURL[4] = getClass().getResource("/sound/Door_sound.wav");
        soundURL[5] = getClass().getResource("/sound/Button_Click.wav");
        soundURL[6] = getClass().getResource("/sound/Win_Sound.wav");
        soundURL[7] = getClass().getResource("/sound/GameOver_Sound.wav");
    }

    /**
     * Loads the audio file at the specified index into the {@code clip} for playback.
     *
     * @param index the index of the sound file in {@code soundURL} to load
     */
    public void setFile (int index) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[index]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            //throw new RuntimeException(e);
            // Exception handling for audio file loading errors
        }
    }

    /**
     * Plays the loaded audio clip from the beginning.
     */
    public void play() {
        clip.start();
    }

    /**
     * Continuously loops the loaded audio clip.
     */
    public void loop () {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops the audio clip if it is currently playing.
     */
    public void stop () {
        clip.stop();
    }
}
