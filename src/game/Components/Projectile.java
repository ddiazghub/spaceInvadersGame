/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components;

import game.Core.GamePanel;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author david
 */


public class Projectile {
	
	private int xPos;
	private int yPos;
	private Image sprite;
	private int damage;
	private int projectileSpeed;
	private int width;
	private int height;
	private String direction;
	private String imagePath;
	
	public Projectile(int x, int y, int damage, int projectileSpeed, String direction, String imagePath) {
		
		this.xPos = x;
		this.yPos = y;
		this.damage = damage;
		this.projectileSpeed = projectileSpeed;
		this.imagePath = imagePath;
		this.direction = direction;
		try {
			this.sprite = ImageIO.read(new File(this.imagePath));
		} catch (Exception e) {
			e.getStackTrace();
		}
		this.width = this.sprite.getWidth(null);
		this.height = this.sprite.getHeight(null);
	}
	public int getX() {
		return this.xPos;
	}
	public int getY() {
		return this.yPos;
	}
	public int getDamage() {
		return this.damage;
	}
	public String getDirection() {
		return this.direction;
	}
	public String getImagePath() {
		return this.imagePath;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getProjectileSpeed() {
		return this.projectileSpeed;
	}
	public void travel() {
		switch (direction) {
			case "up":
				this.yPos -= this.projectileSpeed;
				break;
			case "down":
				this.yPos += this.projectileSpeed;
		}
	}
	public void tick() {
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

