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
	protected int maxHp;
	protected boolean hasWeapon;
	protected Weapon weapon;
	protected boolean shooting;
	protected boolean right;
	protected boolean left;
	protected boolean dead = false;
	protected boolean vulnerable;
	protected int score = 0;
	protected GameTimer invulnerabilityTimer;
	protected GameTimer invulnerabilityAnimTimer;
	protected Animation spawn;
	
	public Character(int x, int y, int hp, double xSpeed, double ySpeed, Weapon weapon, String imagePath) {
		super(x, y, imagePath);
		this.hp = hp;
		this.maxHp = hp;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.hasWeapon = weapon != null;
		this.weapon = weapon;
		this.vulnerable = true;
		this.invulnerabilityTimer = new GameTimer();
		this.invulnerabilityAnimTimer = new GameTimer();
		this.invulnerabilityAnimTimer.newDelay(400);
		this.spawn = new Animation(x + width / 2 - height, y + height / 2 - height, 2 * width, 2 * height, "./src/game/Graphics/portal.gif", 500, new Sound("./src/game/Sound/SoundEffects/teleportation.wav"));
		
	}
	
	public Character(int x, int y, int hp, double xSpeed, double ySpeed, int score, Weapon weapon, String imagePath) {
		super(x, y, imagePath);
		this.hp = hp;
		this.maxHp = hp;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.hasWeapon = weapon != null;
		this.weapon = weapon;
		this.vulnerable = true;
		this.score = score;
		this.invulnerabilityTimer = new GameTimer();
		this.invulnerabilityAnimTimer = new GameTimer();
		this.invulnerabilityAnimTimer.newDelay(400);
		this.spawn = new Animation(x + width / 2 - width, y + height / 2 - height, 2 * width, 2 * height, "./src/game/Graphics/portal.gif", 500, new Sound("./src/game/Sound/SoundEffects/teleportation.wav"));
	}
	
	public abstract void tick();
		
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
			if (hasWeapon) {
				this.weapon.render(g);
			}
		}
		if (this.spawn != null) {
			if (!this.spawn.ended() ) {
				spawn.render(g);
			}
		}
	}

	public int getHp() {
		return this.hp;
	}
	
	public int getMaxHp() {
		return this.maxHp;
	}

	public Weapon getWeapon() {
		return this.weapon;
	}
	
	public void setVulnerable(boolean vulnerable) {
		this.vulnerable = vulnerable;
	}
	
	public boolean isVulnerable() {
		return this.vulnerable;
	}
	
	public void invulnerable(long duration) {
		setVulnerable(false);
		this.invulnerabilityTimer.newDelay(duration);
	}
	
	public void hurt(int damage) {
		if (!this.vulnerable) return;
		
		this.hp -= damage;
		boolean dead = this.hp < 1;
		if (dead) {
			this.dead = true;
			this.hp = 0;
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
		if (this.hp > maxHp) this.hp = maxHp;
	}
	
	public void collision(CharacterEntity entity) {
		if (entity.getBounds().intersects(this.getBounds())) {
			entity.hurt(this.weapon.getDamage() * 100000);
		}
		if (entity.isDead()) {
			this.weapon.newExplosion(entity);
		}
	}
	
	public int getScore() {
		return this.score;
	}
}
