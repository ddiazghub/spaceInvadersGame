/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components;

import game.Core.GamePanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author david
 */


public class Projectile {
	
	private int xPos;
	private int yPos;
	private Image sprite;
	
	public Projectile(int x, int y, Image sprite) {
		
		this.xPos = x;
		this.yPos = y;
		this.sprite = sprite;
	}
	public int getX() {
		return this.xPos;
	}
	public int getY() {
		return this.yPos;
	}
	public void tick() {
		this.yPos -= 10;
	}
	public void render(Graphics g) {
		g.drawImage(sprite, xPos, yPos, null);
	}
	public boolean outOfBounds() {
		return this.xPos < -300 || this.xPos > 300 + GamePanel.WIDTH || this.yPos < -300 || this.xPos > 300 + GamePanel.HEIGHT;
	}
}


/*
public class Projectile {
	private int damage;
	private int speed;
	private int width;
	private int height;
	private int initialXPos;
	private int initialYPos;
	private int xPos;
	private int yPos;
	private boolean active;
	private Image sprite;
	
	public Projectile(int x, int y, int damage, int speed, Image sprite) {
		this.initialXPos = x;
		this.initialYPos = y;
		this.speed = speed;
		this.damage = damage;
		this.xPos = this.initialXPos;
		this.yPos = this.initialYPos;
		this.sprite = sprite;
		this.width = this.sprite.getWidth(null);
		this.height = this.sprite.getHeight(null);
	}
	
	public Projectile(int x, int y, Image sprite) {
		this.initialXPos = x;
		this.initialYPos = y;
		this.xPos = this.initialXPos;
		this.yPos = this.initialYPos;
		this.sprite = sprite;
		this.width = this.sprite.getWidth(null);
		this.height = this.sprite.getHeight(null);
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
	public void setInitialX(int x) {
		this.initialXPos = x;
	}
	public void setInitialY(int y) {
		this.initialYPos = y;
	}
	public int getDamage() {
		return this.damage;
	}
	public void tick() {
		this.yPos -= this.speed;
	}
	public void render(Graphics g) {
		g.drawImage(sprite, xPos, yPos, null);
	}
}
*/

