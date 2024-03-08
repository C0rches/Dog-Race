/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isp.lab10.racedemo;

import java.io.File;
import javax.sound.sampled.*;

/**
 *
 * @author mihai
 */
public class PlaySound {

    private Clip clip,clip2;

    void playSound() {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(".\\mixkit-horde-of-barking-dogs-60.wav")));
            clip.start();

            clip2 = AudioSystem.getClip();
            clip2.open(AudioSystem.getAudioInputStream(new File(".\\mixkit-game-gun-shot-1662.wav")));
            clip2.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void stopSound(){
        if(clip!=null)
            clip.stop();
    }
    
    public static void main(String[] args) throws InterruptedException {
        PlaySound ps = new PlaySound();
        ps.playSound();
        Thread.sleep(15000);
        ps.stopSound();
    }
}
