/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components;

import game.Core.GamePanel;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author david
 */


public class Projectile extends Entity {
	
	private int damage;
	private int width;
	private int height;
	private String direction;
	
	public Projectile(int x, int y, int damage, int projectileSpeed, String direction, String imagePath) {
		super(x, y, imagePath);
		this.damage = damage;
		this.xSpeed = 0;
		this.ySpeed = projectileSpeed;
		this.direction = direction;
	}
	public Projectile(int x, int y, int damage, int projectileSpeed, String direction, Image sprite) {
		super(x, y, sprite);
		this.damage = damage;
		this.xSpeed = 0;
		this.ySpeed = projectileSpeed;
		this.direction = direction;
	}
	public int getDamage() {
		return this.damage;
	}
	public String getDirection() {
		return this.direction;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public void travel() {
		switch (direction) {
			case "up":
				this.yPos -= this.ySpeed;
				break;
			case "down":
				this.yPos += this.ySpeed;
		}
	}
	public void tick() {
		if (this.stopped) return;
		this.travel();
	}
	public void render(Graphics g) {
		g.drawImage(sprite, xPos, yPos, null);
	}
	public boolean isOutOfBounds() {
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

