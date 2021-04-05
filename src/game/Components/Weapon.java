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
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Weapon {
	
	
	private int xPos;
	private int yPos;
	private Image sprite;
	private int fireRate;
	private long lastShot = 0;
	private boolean canShoot = true;
	private ArrayList<Projectile> projectileStream;
	private Projectile projectile;

	
	public Weapon(int x, int y, int fireRate, String imagePath) {
		
		this.xPos = x;
		this.yPos = y;
		this.fireRate = fireRate;
		this.sprite = Toolkit.getDefaultToolkit().getImage(imagePath);
		this.projectileStream = new ArrayList<>();
		this.projectile = new Projectile(x, y, this.sprite);
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
	public void tick() {
		
		for (int i = 0; i < this.projectileStream.size(); i++) {
			Projectile thisProjectile = this.projectileStream.get(i);
			thisProjectile.tick();
			if (thisProjectile.outOfBounds()) {
				this.deleteProjectile(i);
			}
		}
	}
	public void render(Graphics g) {
		for (Projectile projectile: this.projectileStream) {
			projectile.render(g);
		}
	}
	
	public void shoot() {
		
		if (!this.canShoot) {
			long elapsed = System.currentTimeMillis() - this.lastShot;
			if (elapsed >= 1000 / this.fireRate) {
				this.canShoot = true;
			}
			return;
		}
		this.projectileStream.add(new Projectile(this.getX(), this.getY(), this.sprite));
		this.lastShot = System.currentTimeMillis();
		this.canShoot = false;
	}
	
	public void deleteProjectile(int i) {
		this.projectileStream.remove(i);
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
