/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
/**
 *
 * @author david
 */
public class Sound {
	
	public Clip clip;
	public float volume = 1;
	
	public Sound(String path) {
		try {
			// Set up an audio input stream piped from the sound file  found at the given path.
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path));
			// Get a clip resource.
			this.clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			this.clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	// Play or Re-play the sound effect from the beginning, by rewinding.
	public void play(Boolean loop) {
		if (clip.isRunning()) stop();
		clip.setFramePosition(0);
		clip.start();
		if(loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	   	   
	public void stop() {
		clip.stop();
		clip.setFramePosition(0);
	}

	public boolean isPlaying() {
		return this.clip.isRunning();
	}
	
	public float getVolume() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
		return (float) Math.pow(10f, gainControl.getValue() / 20f);
	}

	public void setVolume(float volume) {
		if (volume < 0f || volume > 1f) throw new IllegalArgumentException("Volume not valid: " + volume);
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
		gainControl.setValue(20f * (float) Math.log10(volume));
	}
}
