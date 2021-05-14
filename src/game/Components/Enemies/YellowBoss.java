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
import game.Components.Player;
import game.Components.Sound;
import game.Components.Weapon;
import game.Core.GamePanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class YellowBoss extends Enemy {
	
	private ArrayList<YellowBoss2> helpers;
	private ArrayList<YellowBoss2> deadHelpers;
	private Player player;
	private boolean spawnedHelpers = false;
	private boolean teleported = false;
	private GameTimer teleportTimer;
	private Animation portal2;
	private int xDir;
	
	
	public YellowBoss(Player player) {
		super(250, 200, 1000, 1, 4 + new Random().nextInt(3), 100, new Weapon(-1000, 1000, 7, 4, 1, 0.4, "down", "./src/game/Graphics/Projectiles/shock.png", new Sound("./src/game/Sound/SoundEffects/shot.wav")), "./src/game/Graphics/Enemies/yellow_boss.png");
		this.shooting = true;
		this.helpers = new ArrayList<>();
		this.deadHelpers = new ArrayList<>();
		this.teleportTimer = new GameTimer();
		this.teleportTimer.newDelay(2000);
	}
	
	public void tick() {
		
		if (this.helpers.size() > 0) {
			this.setVisible(false);
			this.invulnerable(10000000);
			for (YellowBoss2 helper: helpers) {
				helper.tick();
				if (helper.isDead()) deadHelpers.add(helper);
			}
			for (YellowBoss2 helper: deadHelpers) helpers.remove(helper);
			return;
		} else {
			this.setVisible(true);
			this.setVulnerable(true);
		}
		
		if (this.spawnedHelpers && !this.teleported) {
			resetPortal();
			this.teleported = true;
		}
		if (this.hasWeapon) this.weapon.tick();
		
		if (this.timer.delayFinished() && this.stopped) resume();
		if (this.stopped) return;
		
		if (this.hp < this.maxHp * 1 / 2 && !this.spawnedHelpers) {
			this.helpers.add(new YellowBoss2());
			this.helpers.add(new YellowBoss2());
			this.helpers.add(new YellowBoss2());
			this.spawnedHelpers = true;
			this.teleportTimer.newDelay(1000);
			resetPortal();
		}
		
		this.move();
		if (this.hasWeapon) {
			this.weapon.setX(this.xPos + this.width / 2);
			this.weapon.setY(this.yPos + this.height);
			if (this.shooting) shoot();
		}
	}
	
	@Override
	public void render(Graphics g) {
		
		for (YellowBoss2 helper: helpers) helper.render(g);

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
		if (!this.spawn.ended()) spawn.render(g);
		if (this.portal2 != null && !this.portal2.ended()) portal2.render(g);
	}
	
	@Override
	public void collision(CharacterEntity entity) {
		for (YellowBoss2 helper: helpers) {
			helper.collision(entity);
			entity.getWeapon().collision(helper);
		}
		if (entity.getBounds().intersects(this.getBounds())) {
			entity.hurt(this.weapon.getDamage() * 100000);
		}
		if (entity.isDead()) {
			this.weapon.newExplosion(entity);
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
		if (this.getX() >= GamePanel.WIDTH - this.getWidth() || this.getX() <= 0) {
			this.xDir *= -1;
		}
		if (this.teleportTimer.delayFinished()) {
			resetPortal2();
			Random rng = new Random();
			Rectangle initialBounds = new Rectangle(xPos - 50, yPos - 25, width + 100, height + 50);
			while (new Rectangle(xPos, yPos, width, height).intersects(initialBounds)) {
				this.yPos = 25 + rng.nextInt(GamePanel.HEIGHT - this.height - 200);
				this.xPos = 50 + rng.nextInt(GamePanel.WIDTH - this.width - 100);
			}
			this.teleportTimer.reset();
			resetPortal();
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