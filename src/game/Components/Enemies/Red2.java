/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components.Enemies;

import game.Components.Animation;
import game.Components.CharacterEntity;
import game.Components.Enemy;
import game.Components.Sound;
import game.Components.Weapon;
import game.Core.GamePanel;
import java.awt.Rectangle;
import java.util.Random;

/**
 *
 * @author david
 */
public class Red2 extends Enemy {
	private int xDir;
	
	public Red2() {
		super(-1000, -1000, 30, 0, 2, 100, null, "./src/game/Graphics/Enemies/red_enemy2.png");
		Random rng = new Random();
		this.yPos = -this.height - new Random().nextInt(200);
		this.xPos = 10 + rng.nextInt(GamePanel.WIDTH - this.width - 20);
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
	
	public void move() {
		this.xPos += this.xSpeed * this.xDir * this.speedMultiplier;
		this.yPos += this.ySpeed * this.speedMultiplier;
		
		if (this.getY() > GamePanel.HEIGHT) {
			Random rng = new Random();
			this.yPos = -this.height - rng.nextInt(100);
			this.xPos = rng.nextInt(GamePanel.WIDTH - this.width);
			this.xSpeed = 0;
		}
	}
	
	public void collision(CharacterEntity entity) {
		if (this.yPos > GamePanel.HEIGHT / 2 - this.height / 2) {
			if (entity.getX() - this.xPos > GamePanel.WIDTH / 4 || this.xPos - entity.getX() > GamePanel.WIDTH / 4) this.xSpeed = 4;
			else this.xSpeed = 2;
			if (entity.getX() > this.xPos) this.xDir = 1;
			else this.xDir = -1;
		}
		
		Rectangle triggerArea = new Rectangle(this.xPos  - 50, this.yPos - 50, this.width + 100, this.height + 100);
		if (entity.getBounds().intersects(triggerArea)) {
			entity.hurt(maxHp);
			this.hurt(maxHp);
			if (!entity.isDead()) entity.getWeapon().explosions().add(new Animation(this.xPos  - 80, this.yPos - 80, this.width + 160, this.height + 160, "./src/game/Graphics/explosion.gif", 1000, new Sound("./src/game/Sound/SoundEffects/explosion.wav")));
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
