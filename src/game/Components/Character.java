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

public abstract class Character extends Entity implements CharacterEntity {
	
	protected int hp;
	protected boolean hasWeapon;
	protected Weapon weapon;
	protected boolean shooting;
	protected boolean right;
	protected boolean left;
	protected boolean dead = false;
	protected boolean vulnerable;
	
	public Character(int x, int y, int hp, double xSpeed, double ySpeed, Weapon weapon, String imagePath) {
		super(x, y, imagePath);
		this.hp = hp;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.hasWeapon = weapon != null;
		this.weapon = weapon;
		this.vulnerable = true;
	}
	
	public abstract void tick();
		
	public void render(Graphics g) {
		if (this.visible) {
			g.drawImage(this.sprite, this.xPos, this.yPos, null);
			if (hasWeapon) {
				this.weapon.render(g);
			}
		}
	}

	public int getHp() {
		return this.hp;
	}

	public Weapon getWeapon() {
		return this.weapon;
	}
	
	public void setVulnerable(boolean vulnerable) {
		this.vulnerable = vulnerable;
	}
	
	public void hurt(int damage) {
		if (!this.vulnerable) return;
		
		this.hp -= damage;
		boolean dead = this.hp < 1;
		if (dead) {
			this.kill();
		}
	}
	
	public boolean isDead() {
		return this.dead;
	}
	
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	public void heal(int hp) {
		this.hp += hp;
	}
	
	public void kill() {
		this.dead = true;
	}
}
