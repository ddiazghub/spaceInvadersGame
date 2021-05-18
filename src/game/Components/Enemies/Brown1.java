/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components.Enemies;

import game.Components.Enemy;
import game.Components.Sound;
import game.Components.Weapon;
import game.Core.GamePanel;
import java.util.Random;

/**
 *
 * @author david
 */
public class Brown1 extends Enemy {
	
	private int xDir;
	
	public Brown1() {
		super(100, -500, 70, 2, 0, 100, 
				new Weapon(-1000, 1000, 7, 4, 1, 0.4, "down", "./src/game/Graphics/Projectiles/green_plasma.png", 
			    new Sound("./src/game/Sound/SoundEffects/laser.wav")), "./src/game/Graphics/Enemies/brown_enemy1.png");
		this.shooting = true;
		Random rng = new Random();
		if (rng.nextFloat() >= 0.5) {
			this.xPos = -this.width - rng.nextInt(200);
			this.xDir = 1;
		} else {
			this.xPos = GamePanel.WIDTH + rng.nextInt(200);
			this.xDir = -1;
		}
		this.yPos = 10 + rng.nextInt(GamePanel.HEIGHT - this.height - 100);
		this.spawn.stop();
		this.spawn = null;
	}
	
	public void tick() {
		if (this.hasWeapon) this.weapon.tick();
		
		if (this.timer.delayFinished() && this.stopped) resume();
		if (this.stopped) return;
		
		this.move();
		if (this.hasWeapon) {
			this.weapon.setX(this.xPos + this.width / 2);
			this.weapon.setY(this.yPos + this.height);
			if (this.shooting) shoot();
		}
	}
	
	public void tickWeapon() {
		if (this.hasWeapon) this.weapon.tick();
	}
	
	public void move() {
		if (this.getX() > GamePanel.WIDTH || this.getX() < -this.width) {
			Random rng = new Random();
			this.xSpeed = 1 + rng.nextInt(2);
			if (rng.nextFloat() >= 0.5) {
				this.xPos = -this.width - rng.nextInt(200);
				this.xDir = 1;
			} else {
				this.xPos = GamePanel.WIDTH + rng.nextInt(200);
				this.xDir = -1;
			}
			this.yPos = 10 + rng.nextInt(GamePanel.HEIGHT - this.height - 100);
		}
		this.xPos += this.xSpeed * this.xDir * this.speedMultiplier;
	}
	
	public void stop(long duration) {
		stop();
		this.timer.newDelay(duration);
	}
	
	public void shoot() {
		this.weapon.shoot();
	}
}
