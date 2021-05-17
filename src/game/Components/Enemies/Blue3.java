/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components.Enemies;

import game.Components.Enemy;
import game.Components.GameTimer;
import game.Components.Sound;
import game.Components.Weapon;
import game.Core.GamePanel;
import java.util.Random;

/**
 *
 * @author david
 */
public class Blue3 extends Enemy {
	private int xDir;
	private GameTimer shootingTimer;
	
	public Blue3() {
		super(100, -500, 30, 2, 0, 100, 
				new Weapon(-1000, 1000, 7, 6, 4, 1, "down", "./src/game/Graphics/Projectiles/blue_laser.png", 
			    new Sound("./src/game/Sound/SoundEffects/laser.wav")), "./src/game/Graphics/Enemies/blue_enemy3.png");
		this.shooting = false;
		this.weapon.addShot();
		this.spawn.stop();
		this.spawn = null;
		this.shootingTimer = new GameTimer();
		this.shootingTimer.newDelay(3000);
		changeMovement();
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
		
		if (this.shootingTimer.delayFinished() && !this.shooting) {
			this.shooting = true;
			this.shootingTimer.newDelay(1500);
		}
		
		if (this.shootingTimer.delayFinished() && this.shooting) {
			this.shooting = false;
			this.shootingTimer.newDelay(3000);
		}
	}
	
	public void changeMovement() {
		Random rng = new Random();
		float random = rng.nextFloat();
		if (random < 0.33) {
			this.xPos = -this.width;
			this.xDir = 1;
			this.yPos = 10 + rng.nextInt(GamePanel.HEIGHT - this.height - 100);
			this.ySpeed = 0;
			this.xSpeed = 2 + rng.nextInt(2);
		} else if (random < 0.66) {
			this.xPos = GamePanel.WIDTH;
			this.xDir = -1;
			this.yPos = 10 + rng.nextInt(GamePanel.HEIGHT - this.height - 100);
			this.ySpeed = 0;
			this.xSpeed = 2 + rng.nextInt(2);
		} else {
			this.yPos = -this.height - 50;
			this.xPos = 25 + rng.nextInt(GamePanel.WIDTH - this.width - 50);
			this.xSpeed = 0;
			this.ySpeed = 2 + rng.nextInt(2);
		}
	}
	
	public void tickWeapon() {
		if (this.hasWeapon) this.weapon.tick();
	}
	
	public void move() {
		if (this.getX() > GamePanel.WIDTH || this.getX() < -this.width || this.getY() > GamePanel.HEIGHT) {
			changeMovement();
		}
		this.xPos += this.xSpeed * this.xDir * this.speedMultiplier;
		this.yPos += this.ySpeed * this.speedMultiplier;
	}
	
	public void stop(long duration) {
		stop();
		this.timer.newDelay(duration);
	}
	
	public void shoot() {
		this.weapon.shoot();
	}
}
