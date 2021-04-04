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
import java.awt.Image;
import java.awt.Toolkit;

public abstract class Character {
	
	protected int hp;
	protected int movementSpeed;
	protected int WIDTH;
	protected int HEIGHT;
	protected int xPos;
	protected int yPos;
	protected Weapon weapon;
	protected Image sprite;
	
	public Character(int x, int y, int hp, int movementSpeed, String imagePath) {
		this.xPos = x;
		this.yPos = y;
		this.hp = hp;
		this.movementSpeed = movementSpeed;
		this.sprite = Toolkit.getDefaultToolkit().getImage(imagePath);
	}
	
	public abstract void tick();

	public int getHp() {
		return this.hp;
	}

	public int getMovementSpeed() {
		return this.movementSpeed;
	}

	public int getWIDTH() {
		return this.WIDTH;
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
}
