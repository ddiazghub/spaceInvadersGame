/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components.Enemies;

import game.Components.Animation;
import game.Components.Enemy;
import game.Components.Sound;
import game.Components.Weapon;
import game.Core.GamePanel;
import java.util.Random;

/**
 *
 * @author david
 */
public class Red3 extends Enemy {
	
	private int xDir;
	
	public Red3() {
		super(-1000, -1000, 300, 1, 2 + new Random().nextInt(2), 100, new Weapon(-1000, 1000, 30, 4, 1, 0.4, "down", "./src/game/Graphics/Projectiles/missile.png", new Sound("./src/game/Sound/SoundEffects/shot.wav")), "./src/game/Graphics/Enemies/red_enemy4.png");
		this.shooting = true;
		this.weapon.setExplosive(true);
		Random rng = new Random();
		this.yPos = 10 + rng.nextInt(GamePanel.HEIGHT - this.height - 100);
		this.xPos = 10 + rng.nextInt(GamePanel.WIDTH - this.width - 20);
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
	
	public void move() {
		if (this.getX() >= GamePanel.WIDTH - this.getWidth() || this.getX() <= 0) {
			this.xDir *= -1;
		}
		this.xPos += this.xSpeed * this.xDir * this.speedMultiplier;
	}
	
	public void stop(long duration) {
		stop();
		this.timer.newDelay(duration);
	}
	
	public void shoot() {
		this.weapon.shoot();
	}
}
