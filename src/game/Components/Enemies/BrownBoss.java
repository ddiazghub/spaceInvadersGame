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
import java.awt.Rectangle;
import java.util.Random;

/**
 *
 * @author david
 */
public class BrownBoss extends Enemy {
	private int xDir;
	private int yDir;
	private GameTimer speedChangeTimer;
	private GameTimer shootingTimer;
	private GameTimer teleportTimer;
	private Animation portal2;
	private int minimunMovementSpeed = 1;
	private int speedMargin = 4;
	
	public BrownBoss() {
		super(-1000, -1000, 800, 1 + new Random().nextInt(4), 1 + new Random().nextInt(4), 100, new Weapon(-1000, 1000, 4, 4, 5, 1, "down", "./src/game/Graphics/Projectiles/green_orb.png", new Sound("./src/game/Sound/SoundEffects/laser.wav")), "./src/game/Graphics/Enemies/brown_boss.png");
		this.shooting = true;
		Random rng = new Random();
		this.yPos = 10 + rng.nextInt(GamePanel.HEIGHT - this.height - 100);
		this.xPos = 10 + rng.nextInt(GamePanel.WIDTH - this.width - 20);
		if (this.xPos < GamePanel.WIDTH / 2 - this.width / 2) xDir = 1;
		else xDir = -1; 
		
		if (this.yPos < GamePanel.HEIGHT / 2 - 50  - this.height / 2) yDir = 1;
		else yDir = 1;
		this.weapon.star(true);
		this.speedChangeTimer = new GameTimer();
		this.speedChangeTimer.newDelay(1500);
		this.teleportTimer = new GameTimer();
		this.teleportTimer.newDelay(4000);
		this.shootingTimer = new GameTimer();
		this.shootingTimer.newDelay(3000);
		this.spawn.stop();
		this.spawn = null;
		this.spawn = new Animation(xPos - width / 2, yPos - height / 2, 2 * width, 2 * height, "./src/game/Graphics/portal.gif", 500, new Sound("./src/game/Sound/SoundEffects/teleportation.wav"));
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
		
		if (this.hp < this.maxHp) {
			this.minimunMovementSpeed = 3;
			this.speedMargin = 5;
		}
		Random rng = new Random();
		
		if (this.shootingTimer.delayFinished() && !this.shooting) {
			this.shooting = true;
			this.shootingTimer.newDelay(rng.nextInt(3000));
		}
		
		if (this.shootingTimer.delayFinished() && this.shooting) {
			this.shooting = false;
			this.shootingTimer.newDelay(rng.nextInt(4000));
		}
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
		if (this.portal2 != null && !this.portal2.ended()) portal2.render(g);
	}
	
	public void changeMovement() {
		Random rng = new Random();
		if (rng.nextFloat() > 0.5) xDir *= -1;
		
		if (rng.nextFloat() > 0.5) yDir *= -1;
		
		xSpeed = this.minimunMovementSpeed + rng.nextInt(this.speedMargin);
		ySpeed = this.minimunMovementSpeed + rng.nextInt(this.speedMargin);
	}
	
	public void tickWeapon() {
		if (this.hasWeapon) this.weapon.tick();
	}
	
	public void resetPortal() {
		this.spawn = new Animation(xPos - width / 2, yPos - height / 2, 2 * width, 2 * height, "./src/game/Graphics/portal.gif", 500, new Sound("./src/game/Sound/SoundEffects/teleportation.wav"));
	}
	
	public void resetPortal2() {
		this.portal2 = new Animation(xPos - width / 2, yPos - height / 2, 2 * width, 2 * height, "./src/game/Graphics/portal.gif", 500, new Sound("./src/game/Sound/SoundEffects/teleportation.wav"));
	}
	
	public void move() {
		if (this.teleportTimer.delayFinished()) {
			resetPortal2();
			Random rng = new Random();
			Rectangle initialBounds = new Rectangle(xPos - 50, yPos - 25, width + 100, height + 50);
			while (new Rectangle(xPos, yPos, width, height).intersects(initialBounds)) {
				this.yPos = 10 + rng.nextInt(GamePanel.HEIGHT - this.height - 100);
				this.xPos = 50 + rng.nextInt(GamePanel.WIDTH - this.width - 100);
			}
			if (this.hp < this.maxHp / 2) {
				this.teleportTimer.newDelay(rng.nextInt(4000));
			} else this.teleportTimer.reset();
			resetPortal();
		}
		
		if (this.speedChangeTimer.delayFinished()) {
			changeMovement();
			if (this.hp < this.maxHp / 2) {
				this.speedChangeTimer.newDelay(new Random().nextInt(1500));
			} else {
				this.speedChangeTimer.reset();
			}
		}
		if (this.getX() >= GamePanel.WIDTH - this.getWidth() || this.getX() <= 0) {
			this.xDir *= -1;
		}
		if (this.getY() >= GamePanel.HEIGHT - this.getHeight() - 100 || this.getY() <= 0) {
			this.yDir *= -1;
		}
		this.xPos += this.xSpeed * this.xDir * this.speedMultiplier;
		this.yPos += this.ySpeed * this.yDir * this.speedMultiplier;
	}
	
	public void stop(long duration) {
		stop();
		this.timer.newDelay(duration);
	}
	
	public void shoot() {
		this.weapon.shoot();
	}
}
