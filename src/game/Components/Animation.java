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
	private int x;
	private int y;
	private int width;
	private int height;
	
	public Animation(int x, int y, int width, int height, String path, long duration) {
				
		this.animation = Toolkit.getDefaultToolkit().createImage(path);
		this.timer = new GameTimer();
		this.timer.newDelay(duration);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}
	
	public void render(Graphics g) {
		g.drawImage(animation, x, y, width, height, null);
	}
	
	public boolean ended() {
		return this.timer.delayFinished();
	}
}
