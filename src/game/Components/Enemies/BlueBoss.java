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
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author david
 */
public class BlueBoss extends Enemy {
	private int xDir;
	private GameTimer shootingTimer;
	private GameTimer helperSpawnTimer;
	private Animation portal2;
	private ArrayList<CharacterEntity> helpers;
	private ArrayList<CharacterEntity> deadHelpers;
	private boolean spawnedHelpers = false;
	
	public BlueBoss() {
		super(-1000, -1000, 800, 3, 0, 100, 
				new Weapon(-1000, 1000, 4, 6, 10, 4, "down", "./src/game/Graphics/Projectiles/blue_plasma.png", 
						new Sound("./src/game/Sound/SoundEffects/laser.wav")), "./src/game/Graphics/Enemies/blue_boss.png");
		this.shooting = false;
		Random rng = new Random();
		this.yPos = 10 + rng.nextInt(GamePanel.HEIGHT - this.height - 100);
		this.xPos = 50 + rng.nextInt(GamePanel.WIDTH - this.width - 100);
		if (this.xPos - this.width < GamePanel.WIDTH) xDir = 1;
		else xDir = -1; 
		this.spawn.stop();
		this.spawn = null;
		this.spawn = new Animation(xPos - width / 2, yPos - height / 2, 2 * width, 2 * height, "./src/game/Graphics/portal.gif", 500, new Sound("./src/game/Sound/SoundEffects/teleportation.wav"));
		this.shootingTimer = new GameTimer();
		this.shootingTimer.newDelay(3000);
		this.helperSpawnTimer = new GameTimer();
		this.helperSpawnTimer.newDelay(5000);
		this.helpers = new ArrayList<>();
		this.deadHelpers = new ArrayList<>();
		this.weapon.spread(true);
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
			this.shootingTimer.newDelay(2000);
		}
		
		if (this.shootingTimer.delayFinished() && this.shooting) {
			this.shooting = false;
			this.shootingTimer.newDelay(3000);
		}
		
		if (helpers.isEmpty()) this.spawnedHelpers = false;
		for (CharacterEntity helper: helpers) {
			helper.tick();
			if (helper.isDead()) deadHelpers.add(helper);
		}
		for (CharacterEntity helper: deadHelpers) helpers.remove(helper);
		
		if (this.hp < this.maxHp * 1 / 2 && this.helperSpawnTimer.delayFinished() && !this.spawnedHelpers) {
			if (new Random().nextFloat() > 0.5) {
				for (int i = 0; i < 4; i++) {
					this.helpers.add(new Blue3());
				}
				for (int i = 0; i < 5; i++) {
					this.helpers.add(new Blue2());
				}
				for (int i = 0; i < 6; i++) {
					this.helpers.add(new Blue1());
				}
				this.spawnedHelpers = true;
				for (CharacterEntity helper: helpers) {
					helper.damageMultiplier(this.weapon.getDamageMultiplier());
				}
			}
			this.helperSpawnTimer.reset();
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

		for (CharacterEntity helper: helpers) helper.render(g);
		
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
	
	@Override
	public void collision(CharacterEntity entity) {
		for (CharacterEntity helper: helpers) {
			helper.collision(entity);
			helper.getWeapon().collision(entity);
		}
		entity.getWeapon().collision(helpers);
		if (entity.getBounds().intersects(this.getBounds())) {
			entity.hurt(this.weapon.getDamage() * 100000);
		}
		if (entity.isDead()) {
			this.weapon.newExplosion(entity);
		}
	}
	
	public void stop(long duration) {
		stop();
		this.timer.newDelay(duration);
	}
	
	public void shoot() {
		this.weapon.shoot();
	}
}
