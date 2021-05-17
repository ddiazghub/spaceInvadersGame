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

import game.Core.GamePanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Weapon {
	
	
	private int xPos;
	private int yPos;
	private int fireRate;
	private int multishot = 1;
	private final GameTimer timer;
	private double chanceToShoot; 
	private double fireRateMultiplier;
	private double damageMultiplier = 1;
	private final ArrayList<Projectile> projectileStream;
	private Projectile projectile;
	private ArrayList<Animation> explosions;
	private ArrayList<Animation> finishedExplosions = new ArrayList<>();
	private Sound soundEffect;
	private String type = "base";
	private boolean explosive = false;
	private boolean star = false;
	private boolean spread = false;

	
	public Weapon(int x, int y, int damage, double projectileSpeed, int fireRate, String shootingDirection, String imagePath, Sound soundEffect) {
		
		this.xPos = x;
		this.yPos = y;
		this.fireRate = fireRate;
		this.fireRateMultiplier = 1;
		this.damageMultiplier = 1;
		this.projectileStream = new ArrayList<>();
		this.projectile = new Projectile(x, y, damage, projectileSpeed, shootingDirection, imagePath);
		this.timer = new GameTimer();
		this.timer.newDelay(1000 / (this.fireRate * this.fireRateMultiplier));
		this.chanceToShoot = 1;
		this.explosions = new ArrayList<>();
		this.soundEffect = soundEffect;
		
	}
	
	public Weapon(int x, int y, int damage, double projectileSpeed, int fireRate, int multishot, String shootingDirection, String imagePath, Sound soundEffect) {
		
		this.xPos = x;
		this.yPos = y;
		this.fireRate = fireRate;
		this.fireRateMultiplier = 1;
		this.damageMultiplier = 1;
		this.projectileStream = new ArrayList<>();
		this.projectile = new Projectile(x, y, damage, projectileSpeed, shootingDirection, imagePath);
		this.timer = new GameTimer();
		this.timer.newDelay(1000 / (this.fireRate * this.fireRateMultiplier));
		this.chanceToShoot = 1;
		this.explosions = new ArrayList<>();
		this.multishot = multishot;
		this.soundEffect = soundEffect;
	}
	
	public Weapon(int x, int y, int damage, double projectileSpeed, int fireRate, double chanceToShoot, String shootingDirection, String imagePath, Sound soundEffect) {
		
		this.xPos = x;
		this.yPos = y;
		this.fireRate = fireRate;
		this.damageMultiplier = 1;
		this.fireRateMultiplier = 1;
		this.projectileStream = new ArrayList<>();
		this.projectile = new Projectile(x, y, damage, projectileSpeed, shootingDirection, imagePath);
		this.timer = new GameTimer();
		this.timer.newDelay(1000 / (this.fireRate * fireRateMultiplier));
		this.chanceToShoot = chanceToShoot;
		this.explosions = new ArrayList<>();
		this.soundEffect = soundEffect;
	}
	
	public Weapon(int x, int y, int damage, double projectileSpeed, double fireRatePercent, String shootingDirection, String imagePath, Sound soundEffect) {
		
		this.xPos = x;
		this.yPos = y;
		this.fireRate = 1;
		this.damageMultiplier = 1;
		this.fireRateMultiplier = fireRatePercent;
		this.projectileStream = new ArrayList<>();
		this.projectile = new Projectile(-1000, 1000, damage, projectileSpeed, shootingDirection, imagePath);
		this.timer = new GameTimer();
		this.timer.newDelay(1000 / (this.fireRate * this.fireRateMultiplier));
		this.chanceToShoot = 1;
		this.explosions = new ArrayList<>();
		this.soundEffect = soundEffect;
	}
	
	public Weapon(int x, int y, int damage, double projectileSpeed, double fireRatePercent, double chanceToShoot, String shootingDirection, String imagePath, Sound soundEffect) {
		
		this.xPos = x;
		this.yPos = y;
		this.fireRate = 1;
		this.damageMultiplier = 1;
		this.fireRateMultiplier = fireRatePercent;
		this.projectileStream = new ArrayList<>();
		this.projectile = new Projectile(-1000, 1000, damage, projectileSpeed, shootingDirection, imagePath);
		this.timer = new GameTimer();
		this.timer.newDelay(1000 / (this.fireRate * this.fireRateMultiplier));
		this.chanceToShoot = chanceToShoot;
		this.explosions = new ArrayList<>();
		this.soundEffect = soundEffect;
	}
	
	public Weapon(int x, int y, int damage, double projectileSpeed, double fireRatePercent, double chanceToShoot, int multishot, String shootingDirection, String imagePath, Sound soundEffect) {
		
		this.xPos = x;
		this.yPos = y;
		this.fireRate = 1;
		this.damageMultiplier = 1;
		this.fireRateMultiplier = fireRatePercent;
		this.projectileStream = new ArrayList<>();
		this.projectile = new Projectile(-1000, 1000, damage, projectileSpeed, shootingDirection, imagePath);
		this.timer = new GameTimer();
		this.timer.newDelay(1000 / (this.fireRate * this.fireRateMultiplier));
		this.chanceToShoot = chanceToShoot;
		this.explosions = new ArrayList<>();
		this.multishot = multishot;
		this.soundEffect = soundEffect;
	}
	
	public int getX() {
		return this.xPos;
	}
	public int getY() {
		return this.yPos;
	}
	public void setX(int x) {
		this.xPos = x;
	}
	public void setY(int y) {
		this.yPos = y;
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void tick() {
		
		for (int i = 0; i < this.projectileStream.size(); i++) {
			Projectile thisProjectile = this.projectileStream.get(i);
			thisProjectile.tick();
			if (thisProjectile.isOutOfBounds()) {
				this.deleteProjectile(i);
			}
		}
		for (Animation finishedExplosion: finishedExplosions) {
			this.explosions.remove(finishedExplosion);
		}
		
	}
	
	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
		this.timer.newDelay(1000 / (this.fireRate * this.fireRateMultiplier));
	}
	
	public void star(Boolean star) {
		this.star = star;
	}
	
	public void spread(Boolean spread) {
		this.spread = spread;
	}
	
	public void resetMultishot() {
		this.multishot = 1;
	}
	
	public int getMultishot() {
		return this.multishot;
	}
	
	public void newProjectile(int x, int y, int damage, int projectileSpeed, String shootingDirection, String imagePath) {
		this.projectile = new Projectile(x, y, damage, projectileSpeed, shootingDirection, imagePath);
	}
	public void forceToShoot() {
		this.soundEffect.play(false);
		if (star) {
			this.projectileStream.add(new Projectile(this.getX(), this.getY(), this.projectile.getDamage(), this.projectile.getSpeedY(), this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
			this.projectileStream.add(new Projectile(this.getX(), this.getY(), this.projectile.getDamage(), -this.projectile.getSpeedY(), this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
			this.projectileStream.add(new Projectile(this.getX(), this.getY(), this.projectile.getDamage(), this.projectile.getSpeedY() / 2, this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
			this.projectileStream.add(new Projectile(this.getX(), this.getY(), this.projectile.getDamage(), -this.projectile.getSpeedY() / 2, this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
			this.projectileStream.add(new Projectile(this.getX(), this.getY(), this.projectile.getDamage(), 0, this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
		} else if(spread) {
			Random rng = new Random();
			if (multishot % 2 != 0) {
				this.projectileStream.add(new Projectile(this.getX(), this.getY(), this.projectile.getDamage(), this.projectile.getSpeedY(), (double) rng.nextInt(5), this.projectile.getDirection(), this.projectile.getSprite()));
				for (int i = 0; i < (multishot - 1) / 2; i++) {
					this.projectileStream.add(new Projectile(this.getX() - this.projectile.width * (i + 1), this.getY(), this.projectile.getDamage(), (double) rng.nextInt(5), this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
					this.projectileStream.add(new Projectile(this.getX() + this.projectile.width * (i + 1), this.getY(), this.projectile.getDamage(), (double) rng.nextInt(5), this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
				}
			} else {
				for (int i = 0; i < multishot / 2; i++) {
					this.projectileStream.add(new Projectile(this.getX() - this.projectile.width * (i + 1), this.getY(), this.projectile.getDamage(), (double) rng.nextInt(5), this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
					this.projectileStream.add(new Projectile(this.getX() + this.projectile.width * (i + 1), this.getY(), this.projectile.getDamage(), (double) rng.nextInt(5), this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
				}
			}
		} else {
			if (multishot % 2 != 0) {
				this.projectileStream.add(new Projectile(this.getX(), this.getY(), this.projectile.getDamage(), this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
				for (int i = 0; i < (multishot - 1) / 2; i++) {
					this.projectileStream.add(new Projectile(this.getX() - this.projectile.width * (i + 1), this.getY(), this.projectile.getDamage(), this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
					this.projectileStream.add(new Projectile(this.getX() + this.projectile.width * (i + 1), this.getY(), this.projectile.getDamage(), this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
				}
			} else {
				for (int i = 0; i < multishot / 2; i++) {
					this.projectileStream.add(new Projectile(this.getX() - this.projectile.width * (i + 1), this.getY(), this.projectile.getDamage(), this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
					this.projectileStream.add(new Projectile(this.getX() + this.projectile.width * (i + 1), this.getY(), this.projectile.getDamage(), this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
				}
			}
		}
	}
	public void render(Graphics g) {
		for (Projectile projectile: this.projectileStream) {
			projectile.render(g);
		}
		for (Animation explosion: this.explosions) {
			explosion.render(g);
			if (explosion.ended()) {
				this.finishedExplosions.add(explosion);
			}
		}
	}
	public void shoot() {
		if (this.timer.delayFinished()) {
			Random random = new Random();
			double rand = random.nextFloat();
			if (rand > 1 - this.chanceToShoot) {
				this.soundEffect.play(false);
				if (star) {
					this.projectileStream.add(new Projectile(this.getX(), this.getY(), this.projectile.getDamage(), this.projectile.getSpeedY(), this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
					this.projectileStream.add(new Projectile(this.getX(), this.getY(), this.projectile.getDamage(), -this.projectile.getSpeedY(), this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
					this.projectileStream.add(new Projectile(this.getX(), this.getY(), this.projectile.getDamage(), this.projectile.getSpeedY() / 2, this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
					this.projectileStream.add(new Projectile(this.getX(), this.getY(), this.projectile.getDamage(), -this.projectile.getSpeedY() / 2, this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
					this.projectileStream.add(new Projectile(this.getX(), this.getY(), this.projectile.getDamage(), 0, this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
				} else if (spread) {
					Random rng = new Random();
					if (multishot % 2 != 0) {
						this.projectileStream.add(new Projectile(this.getX(), this.getY(), this.projectile.getDamage(), this.projectile.getSpeedY(), (double) rng.nextInt(3) - 1, this.projectile.getDirection(), this.projectile.getSprite()));
						for (int i = 0; i < (multishot - 1) / 2; i++) {
							this.projectileStream.add(new Projectile(this.getX() - this.projectile.width * (i + 1), this.getY(), this.projectile.getDamage(), (double) rng.nextInt(3) - 1, this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
							this.projectileStream.add(new Projectile(this.getX() + this.projectile.width * (i + 1), this.getY(), this.projectile.getDamage(), (double) rng.nextInt(3) - 1, this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
						}
					} else {
						for (int i = 0; i < multishot / 2; i++) {
							this.projectileStream.add(new Projectile(this.getX() - this.projectile.width * (i + 1), this.getY(), this.projectile.getDamage(), (double) rng.nextInt(3) - 1, this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
							this.projectileStream.add(new Projectile(this.getX() + this.projectile.width * (i + 1), this.getY(), this.projectile.getDamage(), (double) rng.nextInt(3) - 1, this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
						}
					}
				} else {
					if (multishot % 2 != 0) {
						this.projectileStream.add(new Projectile(this.getX(), this.getY(), this.projectile.getDamage(), this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
						for (int i = 0; i < (multishot - 1) / 2; i++) {
							this.projectileStream.add(new Projectile(this.getX() - this.projectile.width * (i + 1), this.getY(), this.projectile.getDamage(), this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
							this.projectileStream.add(new Projectile(this.getX() + this.projectile.width * (i + 1), this.getY(), this.projectile.getDamage(), this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
						}
					} else {
						for (int i = 0; i < multishot / 2; i++) {
							this.projectileStream.add(new Projectile(this.getX() - this.projectile.width * (i + 1), this.getY(), this.projectile.getDamage(), this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
							this.projectileStream.add(new Projectile(this.getX() + this.projectile.width * (i + 1), this.getY(), this.projectile.getDamage(), this.projectile.getSpeedY(), this.projectile.getDirection(), this.projectile.getSprite()));
						}
					}
				}
			}
			this.timer.reset();
		}
	}
	public void setYSpeed(int ySpeed) {
		this.projectile.setSpeedY(ySpeed);
	}
	public ArrayList<Animation> explosions() {
		return this.explosions;
	}
	
	public double getDamageMultiplier() {
		return this.damageMultiplier;
	}
	
	public void setDamageMultiplier(double multiplier) {
		this.damageMultiplier = multiplier;
	}
	public void setFireRateMultiplier(double multiplier) {
		this.fireRateMultiplier = multiplier;
		this.timer.newDelay(1000 / (this.fireRate * this.fireRateMultiplier));
	}
	
	public double getFireRateMultiplier() {
		return this.fireRateMultiplier;
	}
	
	public boolean hasNoProjectiles() {
		return this.projectileStream.size() < 1;
	}
	
	public void deleteProjectile(int i) {
		this.projectileStream.remove(i);
	}
	
	public void clear() {
		this.projectileStream.clear();
	}
	
	public int getDamage() {
		return this.projectile.getDamage();
	}
	
	public void addShot() {
		this.multishot++;
	}
	
	public void setSoundEffect(Sound soundEffect) {
		this.soundEffect = soundEffect;
	}
	
	public ArrayList<Projectile> getProjectileStream() {
		return this.projectileStream;
	}
	
	public void collision(CharacterEntity entity) {
		if (entity.isVulnerable()) {
			for (int i = 0; i < this.projectileStream.size(); i++) {
				Projectile projectile = this.projectileStream.get(i);
				if (explosive) {
					if (projectile.getY() > GamePanel.HEIGHT - projectile.height) {
						Rectangle aoe = new Rectangle(projectile.getX() - 100, projectile.getY() - 115, 200, 200);
						Animation explosion = new Animation(projectile.getX() - 110, projectile.getY() - 140, 220, 220, "./src/game/Graphics/explosion.gif", 1000, new Sound("./src/game/Sound/SoundEffects/explosion.wav"));
						this.explosions.add(explosion);
						if (entity.getBounds().intersects(aoe)) entity.hurt((int) Math.round(this.projectileStream.get(i).getDamage() * this.damageMultiplier));
						this.deleteProjectile(i);
					}
				} else {
					if (entity.getBounds().intersects(projectile.getBounds())) {
						entity.hurt((int) Math.round(projectile.getDamage() * this.damageMultiplier));
						this.deleteProjectile(i);
					}
				}
			}
		}
		if (entity.isDead()) {
			newExplosion(entity);
		}
	}
	
	public void setExplosive(boolean explosive) {
		this.explosive = explosive;
	}
	
	public void collision(ArrayList<CharacterEntity> entities) {
		for (CharacterEntity entity: entities) {
			if (entity.isVulnerable()) {
				for (int i = 0; i < this.projectileStream.size(); i++) {
					Projectile projectile = this.projectileStream.get(i);
					if (entity.getBounds().intersects(projectile.getBounds())) {
						if (explosive) {
							Rectangle aoe;
							Animation explosion;
							if (type.equals("blue_plasma")) {
								aoe = new Rectangle(projectile.getX() - 50, projectile.getY() - 50, 100, 100);
								explosion = new Animation(projectile.getX() - 80, projectile.getY() - 80, 160, 160, "./src/game/Graphics/explosion.gif", 1000, new Sound("./src/game/Sound/SoundEffects/explosion.wav"));
							}
							else {
								aoe = new Rectangle(projectile.getX() - 100, projectile.getY() - 100, 200, 200);
								explosion = new Animation(projectile.getX() - 110, projectile.getY() - 110, 220, 220, "./src/game/Graphics/explosion.gif", 1000, new Sound("./src/game/Sound/SoundEffects/explosion.wav"));
							}
							this.explosions.add(explosion);
							for (CharacterEntity eachEntity: entities) {
								if (eachEntity.getBounds().intersects(aoe)) eachEntity.hurt((int) Math.round(this.projectileStream.get(i).getDamage() * this.damageMultiplier));
							}
							this.deleteProjectile(i);
						} else {
							entity.hurt((int) Math.round(this.projectileStream.get(i).getDamage() * this.damageMultiplier));
							this.deleteProjectile(i);
						}
					}
				}
			}
			if (entity.isDead()) {
				newExplosion(entity);
			}
		}
	}
	
	public void newExplosion(CharacterEntity entity) {
		this.explosions.add(new Animation(entity.getX() - entity.getWidth() / 2, entity.getY() - entity.getHeight() / 2, 2 * entity.getWidth(), 2 * entity.getHeight(), "./src/game/Graphics/explosion.gif", 1000, new Sound("./src/game/Sound/SoundEffects/explosion.wav")));
	}
}

/*
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Weapon {

	private String name;
	private int damage;
	private int projectileSpeed;
	private int fireRate;
	private int projectileWidth;
	private int projectileHeight;
	private int xPos;
	private int yPos;
	private long lastShot = 0;
	private boolean active;
	private boolean canShoot = true;
	private Image sprite;
	private ArrayList<Projectile> projectileStream;
	private Projectile projectile;

	public Weapon(String name, int x, int y, int damage, int projectileSpeed, int fireRate, String imagePath) {
		this.name = name;

		this.projectileSpeed = projectileSpeed;
		this.fireRate = fireRate;
		this.damage = damage;
		this.xPos = x;
		this.yPos = y;
		this.sprite = Toolkit.getDefaultToolkit().getImage(imagePath);
		this.projectileStream = new ArrayList<>();
		this.projectileWidth = this.sprite.getWidth(null);
		this.projectileHeight = this.sprite.getHeight(null);
		this.projectile = new Projectile(x, y, damage, projectileSpeed, sprite);
	}

	public Weapon(String name, int x, int y, String imagePath) {
		this.name = name;
		this.xPos = x;
		this.yPos = y;
		this.sprite = Toolkit.getDefaultToolkit().getImage(imagePath);
	}

	public int getWidth() {
		return this.projectileWidth;
	}

	public int getHeight() {
		return this.projectileHeight;
	}

	public int getX() {
		return this.xPos;
	}

	public int getY() {
		return this.yPos;
	}

	public void setX(int x) {
		this.xPos = x;
	}

	public void setInitialY(int y) {
		this.yPos = y;
	}

	public int getDamage() {
		return this.damage;
	}

	public void shoot() {
		//this.projectileStream.add(projectile);

		if (!this.canShoot) {
			long elapsed = System.currentTimeMillis() - this.lastShot;
			if (elapsed >= 1000 / this.fireRate) {
				this.canShoot = true;
			}
		}
		this.projectileStream.add(new Projectile(this.xPos, this.yPos, this.damage, this.projectileSpeed, this.sprite));
		this.lastShot = System.currentTimeMillis();
		this.canShoot = false;
	}

	public void tick() {
		if (!this.projectileStream.isEmpty()) {
			for (int i = 0; i < this.projectileStream.size(); i++) {
				Projectile currentProjectile = this.projectileStream.get(i);
				currentProjectile.tick();
			}
		}
	}

	public void render(Graphics g) {
		if (!this.projectileStream.isEmpty()) {
			for (int i = 0; i < this.projectileStream.size(); i++) {
				Projectile currentProjectile = this.projectileStream.get(i);
				currentProjectile.render(g);
			}
		}
	}

	public void deleteProjectile(Projectile projectile) {

	}
}
*/
