/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components;

import game.Core.GamePanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.util.HashMap;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author david
 */
public class Collectable {
	
	private HashMap<String, Projectile> projectiles;
	private Image sprite;
	private String type;
	private int xPos;
	private int yPos;
	private int width;
	private int height;
	
	public Collectable(String type) {
		
		this.type = type;
		String imagePath = "";
		switch (type) {
			case "hp":
				imagePath = "./src/game/Graphics/Collectables/hp.png";
				break;
				
			case "maxHp":
				imagePath = "./src/game/Graphics/Collectables/maxHp.png";
				break;
				
			case "speed":
				imagePath = "./src/game/Graphics/Collectables/speed.png";
				break;
				
			case "firerate":
				imagePath = "./src/game/Graphics/Collectables/firerate.png";
				break;
				
			case "damage":
				imagePath = "./src/game/Graphics/Collectables/damage.png";
				break;
				
			case "red_laser":
				imagePath = "./src/game/Graphics/Collectables/red_laser.png";
				break;
				
			case "blue_laser":
				imagePath = "./src/game/Graphics/Collectables/blue_laser.png";
				break;
				
			case "missile":
				imagePath = "./src/game/Graphics/Collectables/missile.png";
				break;
				
			case "blue_plasma":
				imagePath = "./src/game/Graphics/Collectables/blue_plasma.png";
		}
		
		try {
			this.sprite = ImageIO.read(new File(imagePath));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		this.width = this.sprite.getWidth(null);
		this.height = this.sprite.getHeight(null);
		this.xPos = new Random().nextInt(GamePanel.WIDTH - this.width);
		this.yPos = -this.height - new Random().nextInt(100);
	}
	
	public void tick() {
		this.yPos += 2;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, xPos, yPos, null);
	}
	
	public int getX() {
		return this.xPos;
	}
	
	public int getY() {
		return this.yPos;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(xPos, yPos, width, height);
	}
	
	public boolean collision(Player player) {
		boolean collision = player.getBounds().intersects(this.getBounds());
		if (collision) {
			new Sound("./src/game/Sound/SoundEffects/collectable.wav").play(false);
			Weapon playerWeapon = player.getWeapon();
			switch (type) {
			case "hp":
				player.heal(player.getMaxHp() / 3);
				break;
			
			case "maxHp":
				player.maxHp += 30;
				player.heal(30);
				break;
				
			case "speed":
				player.speedMultiplier(player.getSpeedMultiplier() + 0.2);
				break;
				
			case "firerate":
				double fireRateMultiplier = player.getWeapon().getFireRateMultiplier();
				playerWeapon.setFireRateMultiplier(fireRateMultiplier + fireRateMultiplier * 0.25);
				break;
				
			case "damage":
				double multiplier = playerWeapon.getDamageMultiplier();
				playerWeapon.setDamageMultiplier(multiplier + multiplier * 0.3);
				break;
				
			case "red_laser":
				if (playerWeapon.getType().equals("red_laser")) {
					if (playerWeapon.getMultishot() < 5) playerWeapon.addShot();
				} else {
					playerWeapon.resetMultishot();
					playerWeapon.newProjectile(-1000, -1000, 25, 10, "up", "./src/game/Graphics/Projectiles/red_laser.png");
					playerWeapon.setFireRate(2);
					playerWeapon.setSoundEffect(new Sound("./src/game/Sound/SoundEffects/laser.wav"));
					playerWeapon.addShot();
					playerWeapon.setType("red_laser");
					playerWeapon.setExplosive(false);
				}
				break;
				
			case "blue_laser":
				if (playerWeapon.getType().equals("blue_laser")) {
					if (playerWeapon.getMultishot() < 4) playerWeapon.addShot();
				} else {
					playerWeapon.resetMultishot();
					playerWeapon.newProjectile(-1000, -1000, 10, 10, "up", "./src/game/Graphics/Projectiles/blue_laser.png");
					playerWeapon.setFireRate(7);
					playerWeapon.setSoundEffect(new Sound("./src/game/Sound/SoundEffects/laser.wav"));
					playerWeapon.setType("blue_laser");
					playerWeapon.setExplosive(false);
				}
				break;
				
			case "missile":
				if (playerWeapon.getType().equals("missile")) {
					if (playerWeapon.getMultishot() < 3) playerWeapon.addShot();
				} else {
					playerWeapon.resetMultishot();
					playerWeapon.newProjectile(-1000, -1000, 45, 10, "up", "./src/game/Graphics/Projectiles/missile.png");
					playerWeapon.setFireRate(1);
					playerWeapon.setSoundEffect(new Sound("./src/game/Sound/SoundEffects/shot.wav"));
					playerWeapon.setExplosive(true);
					playerWeapon.setType("missile");
				}
				break;
				
			case "blue_plasma":
				if (playerWeapon.getType().equals("blue_plasma")) {
					if (playerWeapon.getMultishot() < 3) playerWeapon.addShot();
				} else {
					playerWeapon.resetMultishot();
					playerWeapon.newProjectile(-1000, -1000, 15, 10, "up", "./src/game/Graphics/Projectiles/blue_plasma.png");
					playerWeapon.setFireRate(4);
					playerWeapon.setSoundEffect(new Sound("./src/game/Sound/SoundEffects/laser.wav"));
					playerWeapon.setExplosive(true);
					playerWeapon.setType("blue_plasma");
				}
				break;
			}
		}
		return collision;
	}
}
