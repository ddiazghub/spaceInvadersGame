/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Toolkit;

/**
 *
 * @author david
 */
public class Animation {
	
	private Image animation;
	private GameTimer timer;
	private Sound soundEffect;
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean stopped = false;
	
	public Animation(int x, int y, int width, int height, String path, long duration, Sound soundEffect) {
				
		this.animation = Toolkit.getDefaultToolkit().createImage(path);
		this.timer = new GameTimer();
		this.timer.newDelay(duration);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.soundEffect = soundEffect;
		this.soundEffect.play(false);
		
	}
	
	public void render(Graphics g) {
		if (this.stopped) return;
		g.drawImage(animation, x, y, width, height, null);
	}
	
	public boolean ended() {
		return this.timer.delayFinished();
	}
	
	public void stop() {
		this.stopped = true;
		this.soundEffect.stop();
	}
}
