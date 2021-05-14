/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components.Enemies;

import game.Components.Enemy;
import game.Components.Sound;
import game.Components.Weapon;
import game.Core.GamePanel;
import java.util.Random;

/**
 *
 * @author david
 */
public class Red1 extends Enemy {
	
	public Red1() {
		super(100, -500, 30, 1, 1 + new Random().nextInt(1), 100, 
				new Weapon(-1000, 1000, 7, 4, 1, 0.4, "down", "./src/game/Graphics/Projectiles/shock.png", 
			    new Sound("./src/game/Sound/SoundEffects/shot.wav")), "./src/game/Graphics/Enemies/red_enemy1.png");
		this.shooting = true;
		this.xPos = new Random().nextInt(GamePanel.WIDTH - this.width);
		this.yPos = -this.height - new Random().nextInt(200);
		this.spawn.stop();
		this.spawn = null;
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
		if (this.getY() > GamePanel.HEIGHT) {
			Random rng = new Random();
			this.ySpeed = 1 + rng.nextInt(1);
			this.yPos = -this.height - rng.nextInt(100);
			this.xPos = rng.nextInt(GamePanel.WIDTH - this.width);
		}
		this.yPos += this.ySpeed * this.speedMultiplier;
	}
	
	public void stop(long duration) {
		stop();
		this.timer.newDelay(duration);
	}
	
	public void shoot() {
		this.weapon.shoot();
	}
}
