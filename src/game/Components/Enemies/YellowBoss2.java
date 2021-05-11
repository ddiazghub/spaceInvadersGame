/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components.Enemies;

import game.Components.Enemy;
import game.Components.Weapon;
import game.Core.GamePanel;
import java.util.Random;

/**
 *
 * @author david
 */
public class YellowBoss2 extends Enemy {
	public YellowBoss2() {
		super(new Random().nextInt(GamePanel.WIDTH - 50), -500, 1000, 1, 4, 100, new Weapon(-1000, 1000, 7, 4, 1, 0.4, "down", "./src/game/Graphics/Projectiles/shock.png"), "base", "./src/game/Graphics/Enemies/yellow_boss2.png");
		this.shooting = true;
		this.xPos = new Random().nextInt(GamePanel.WIDTH - this.width);
		this.yPos = -this.height;
	}
	
	public void tick() {
		if (this.hasWeapon) this.weapon.tick();
		
		if (this.timer.delayFinished() && this.stopped) resume();
		if (this.stopped) return;
		
		if (this.hp < 100) {
			this.speedMultiplier = 3;
		}
		if (this.hp < 250) {
			this.speedMultiplier = 2;
		}
		
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
			this.yPos = -this.height;
			this.xPos = new Random().nextInt(GamePanel.WIDTH - this.width);
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
