/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components.Enemies;

import game.Components.Animation;
import game.Components.CharacterEntity;
import game.Components.Enemy;
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
public class Blue2 extends Enemy {
	private int xDir;
	private Animation portal2;
	
	public Blue2() {
		super(-1000, -1000, 30, 1, 4 + new Random().nextInt(3), 100, 
				new Weapon(-1000, 1000, 7, 4, 1, 0.4, 3, "down", "./src/game/Graphics/Projectiles/blue_laser.png", 
						new Sound("./src/game/Sound/SoundEffects/laser.wav")), "./src/game/Graphics/Enemies/blue_enemy2.png");
		this.shooting = true;
		Random rng = new Random();
		this.yPos = 10 + rng.nextInt(GamePanel.HEIGHT - this.height - 100);
		this.xPos = 50 + rng.nextInt(GamePanel.WIDTH - this.width - 100);
		if (this.xPos - this.width < GamePanel.WIDTH) xDir = 1;
		else xDir = -1; 
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
		if (this.getX() >= GamePanel.WIDTH - this.getWidth() - 50 || this.getX() <= 50) {
			this.xDir *= -1;
			resetPortal2();
			Random rng = new Random();
			Rectangle initialBounds = new Rectangle(xPos - 50, yPos - 25, width + 100, height + 50);
			while (new Rectangle(xPos, yPos, width, height).intersects(initialBounds)) {
				this.yPos = 10 + rng.nextInt(GamePanel.HEIGHT - this.height - 100);
			}
			resetPortal();
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
		if (this.portal2 != null && !this.portal2.ended()) portal2.render(g);
	}
	
	public void stop(long duration) {
		stop();
		this.timer.newDelay(duration);
	}
	
	public void shoot() {
		this.weapon.shoot();
	}
}
