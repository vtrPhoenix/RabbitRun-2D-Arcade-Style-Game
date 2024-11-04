package com.project.RabbitRun.main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound() {

        soundURL[0] = getClass().getResource("/sound/Game_Sound.wav");
        soundURL[1] = getClass().getResource("/sound/Reward_Sound");
        soundURL[2] = getClass().getResource("/sound/BonusReward_Sound.wav");
        soundURL[3] = getClass().getResource("/sound/Punishment_Sound.wav");
        soundURL[4] = getClass().getResource("/sound/Door_sound.wav");
        soundURL[5] = getClass().getResource("/sound/Button_Click.wav");
        soundURL[6] = getClass().getResource("/sound/Win_Sound.wav");
        soundURL[7] = getClass().getResource("/sound/GameOver_Sound.wav");
    }

    public void setFile (int index) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[index]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            //throw new RuntimeException(e);
        }
    }

    public void play() {
        clip.start();
    }

    public void loop () {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop () {
        clip.stop();
    }
}
