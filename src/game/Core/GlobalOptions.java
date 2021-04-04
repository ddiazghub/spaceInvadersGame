/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Core;

/**
 *
 * @author david
 */
import java.awt.event.KeyEvent;

public class GlobalOptions {
	
	private boolean volume;
	private int globalVolumeLevel;
	private int[] KeyBindings = { KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE };;
	
	public GlobalOptions () {
		
		this.volume = true;
		this.globalVolumeLevel = 100;
		
	}
	
	public void toggleVolume() {
		this.volume = !this.volume;
	}
	
	public void raiseGlobalVolume() {
		if (this.globalVolumeLevel < 100) {
			this.globalVolumeLevel++;
		}
	}
	
	public void lowerGlobalVolume() {
		if (this.globalVolumeLevel > 0) {
			this.globalVolumeLevel--;
		}
	}
	
	public boolean volumeIsActive() {
		return this.volume;
	}
	
	public int getVolumeLevel() {
		return this.globalVolumeLevel;
	}
	
	public int[] getKeyBindings() {
		return this.KeyBindings;
		
	}
}
