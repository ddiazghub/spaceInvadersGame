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
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author david
 */
public class Collectable {
	
	private Image sprite;
	private String type;
	private int xPos;
	private int yPos;
	private int width;
	private int height;
	
	public Collectable(String type) {
		
		System.out.println(type);
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
		}
		
		try {
			System.out.println(imagePath);
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
				player.getWeapon().setFireRateMultiplier(fireRateMultiplier + fireRateMultiplier * 0.25);
				break;
				
			case "damage":
				double multiplier = player.getWeapon().getDamageMultiplier();
				player.getWeapon().setDamageMultiplier(multiplier + multiplier * 0.3);
			}
		}
		return collision;
	}
}
