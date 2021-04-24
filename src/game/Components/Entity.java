/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author david
 */
public abstract class Entity {
	
	protected int width;
	protected int height;
	protected int xPos;
	protected int yPos;
	protected boolean stopped;
	
	protected int xSpeed;
	protected int ySpeed;
	protected Image sprite;
	
	public Entity(int x, int y, String imagePath) {
		this.xPos = x;
		this.yPos = y;
		try {
			this.sprite = ImageIO.read(new File(imagePath));
		} catch (Exception e) {
			e.getStackTrace();
		}
		this.width = this.sprite.getWidth(null);
		this.height = this.sprite.getHeight(null);
		this.stopped = false;
	}
	
	public Entity(int x, int y, Image sprite) {
		this.xPos = x;
		this.yPos = y;
		this.sprite = sprite;
		this.width = this.sprite.getWidth(null);
		this.height = this.sprite.getHeight(null);
	}
	
	public abstract void tick();
		
	public abstract void render(Graphics g);
	
	public void stop() {
		this.stopped = true;
	}
	
	public void resume() {
		this.stopped = false;
	}

	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getX() {
		return this.xPos;
	}
	
	public int getY() {
		return this.yPos;
	}
	
	public void setX(int x) {
		this.xPos = x;
	}
	
	public void setY(int y ) {
		this.yPos = y;
	}
	
	public int getSpeedX() {
		return this.xSpeed;
	}
	
	public int getSpeedY() {
		return this.ySpeed;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(xPos, yPos, width, height);
	}

	public Image getSprite() {
		return sprite;
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}
}
