/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components;

/**
 *
 * @author david
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.imageio.ImageIO;
import java.io.File;

public abstract class Character {
	
	protected int hp;
	protected int movementSpeed;
	protected int WIDTH;
	protected int HEIGHT;
	protected int xPos;
	protected int yPos;
	protected boolean hasWeapon;
	protected Weapon weapon;
	protected Image sprite;
	protected double movementSpeedPercent;
	
	public Character(int x, int y, int hp, int movementSpeed, Weapon weapon, String imagePath) {
		this.xPos = x;
		this.yPos = y;
		this.hp = hp;
		this.movementSpeed = movementSpeed;
		this.hasWeapon = weapon != null;
		this.weapon = weapon;
		try {
			this.sprite = ImageIO.read(new File(imagePath));
		} catch (Exception e) {
			e.getStackTrace();
		}
		this.WIDTH = this.sprite.getWidth(null);
		this.HEIGHT = this.sprite.getHeight(null);
		this.movementSpeedPercent = 1;
	}
	
	public Character(int x, int y, int hp, double movementSpeedPercent, Weapon weapon, String imagePath) {
		this.xPos = x;
		this.yPos = y;
		this.hp = hp;
		this.movementSpeed = movementSpeed;
		this.movementSpeedPercent = movementSpeedPercent;
		this.hasWeapon = weapon != null;
		this.weapon = weapon;
		try {
			this.sprite = ImageIO.read(new File(imagePath));
		} catch (Exception e) {
			e.getStackTrace();
		}
		this.WIDTH = this.sprite.getWidth(null);
		this.HEIGHT = this.sprite.getHeight(null);
	}
	
	public abstract void tick();
		
	public void render(Graphics g) {
		g.drawImage(this.sprite, this.xPos, this.yPos, null);
		if (hasWeapon) {
			this.weapon.render(g);
		}
	}

	public int getHp() {
		return this.hp;
	}

	public int getMovementSpeed() {
		return this.movementSpeed;
	}

	public int getWIDTH() {
		return this.WIDTH;
	}
	
	public int getX() {
		return this.xPos;
	}
	
	public int getY() {
		return this.yPos;
	}

	public int getHEIGHT() {
		return this.HEIGHT;
	}

	public Image getSprite() {
		return sprite;
	}

	public void setSprite(String imagePath) {
		this.sprite = Toolkit.getDefaultToolkit().getImage(imagePath);
	}
	
	public void hurt(int damage) {
		this.hp -= damage;
	}
	
	public void heal(int hp) {
		this.hp += hp;
	}
	
	public void kill() {
	
	}
}
