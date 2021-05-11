/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components.Enemies;

import game.Components.Enemy;
import game.Components.Weapon;
import game.Core.GamePanel;

/**
 *
 * @author david
 */
public class RedBoss extends Enemy {
	
	private boolean hitEdge;
	private int xDir;
	
	public RedBoss(int x, int y) {
		super(x, y, 300, 3, 1, 100, new Weapon(-1000, 1000, 30, 4, 2, 0.3, 6, "down", "./src/game/Graphics/Projectiles/shock.png"), "base", "./src/game/Graphics/Enemies/red_boss.png");
		this.shooting = true;
		this.xDir = 1;
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
