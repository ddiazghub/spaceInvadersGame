/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components.Enemies;

import game.Components.Animation;
import game.Components.CharacterEntity;
import game.Components.Enemy;
import game.Components.GameTimer;
import game.Components.Sound;
import game.Components.Weapon;
import game.Core.GamePanel;
import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author david
 */
public class RedBoss extends Enemy {
	
	private int xDir;
	private GameTimer shootingTimer;
	private GameTimer shootingTimer2;
	
	public RedBoss() {
		super(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2 - 150, 800, 3, 1, 100, 
			new Weapon(-1000, 1000, 6, 6, 4, 4, "down", "./src/game/Graphics/Projectiles/missile.png", 
			new Sound("./src/game/Sound/SoundEffects/shot.wav")), "./src/game/Graphics/Enemies/red_boss.png");
		this.shooting = true;
		this.weapon.setExplosive(true);
		this.shootingTimer = new GameTimer();
		this.shootingTimer.newDelay(1000);
		this.shootingTimer2 = new GameTimer();
		this.shootingTimer2.newDelay(5000);
		this.xDir = 1;
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
			this.shootingTimer.newDelay(1000);
		}
		
		if (this.shootingTimer.delayFinished() && this.shooting) {
			this.shooting = false;
			this.shootingTimer.newDelay(3000);
		}
	}
	
	public void tickWeapon() {
		if (this.hasWeapon) this.weapon.tick();
	}
	
	public void move() {
		if (this.getX() >= GamePanel.WIDTH - this.getWidth() || this.getX() <= 0) {
			if (this.hp < this.maxHp / 2) {
				this.xSpeed = 3 + new Random().nextInt(3);
			}
			this.xDir *= -1;
		}
		this.xPos += this.xSpeed * this.xDir * this.speedMultiplier;
	}
	
	@Override
	public void render(Graphics g) {

		if (this.visible) {
			if (!this.vulnerable) {
				if (this.invulnerabilityAnimTimer.remainingDuration() < 200) {
					g.drawImage(this.sprite, this.xPos, this.yPos, null);
				}
				if (this.invulnerabilityAnimTimer.delayFinished()) this.invulnerabilityAnimTimer.reset();
			} else {
				g.drawImage(this.sprite, this.xPos, this.yPos, null);
			}
			if (hasWeapon) this.weapon.render(g);
		}
		if (this.spawn != null && !this.spawn.ended()) spawn.render(g);
	}
	
	public void stop(long duration) {
		stop();
		this.timer.newDelay(duration);
	}
	
	public void shoot() {
		this.weapon.shoot();
		if (this.hp < this.maxHp / 2 && this.shootingTimer2.delayFinished()) {
			if (new Random().nextFloat() < 1) {
				for (int i = 0; i < 4; i++) {
					this.weapon.setYSpeed(8);
					this.weapon.setY(-50);
					this.weapon.setX(new Random().nextInt(GamePanel.WIDTH));
					this.weapon.forceToShoot();
					this.weapon.setYSpeed(6);
				}
			}
			this.shootingTimer2.reset();
		}
	}
}
