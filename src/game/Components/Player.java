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
import com.sun.glass.events.KeyEvent;
import game.Core.GamePanel;

public class Player extends Character {

	private int lives = 3;

	public Player(int hp, double movementSpeed, Weapon weapon, String imagePath) {

		super(GamePanel.WIDTH / 2, GamePanel.HEIGHT - 60, hp, movementSpeed, 0, weapon, imagePath);
		this.setX(GamePanel.WIDTH / 2 - this.width / 2);
		this.weapon.setY(this.yPos);
	}

	public void tick() {
		this.weapon.tick();
		this.weapon.setX(this.xPos + this.width / 2);
		
		if (this.invulnerabilityTimer.delayFinished()) this.vulnerable = true;
		if (this.timer.delayFinished() && this.stopped) resume();
		if (this.stopped) return;
		
		boolean canMoveLeft = xPos > 0;
		boolean canMoveRight = xPos < GamePanel.WIDTH - this.sprite.getWidth(null);
		if (right && canMoveRight) {
			this.xPos += this.xSpeed * this.speedMultiplier;
		}
		if (left && canMoveLeft) {
			this.xPos -= this.xSpeed * this.speedMultiplier;
		}
		if (shooting) {
			this.weapon.shoot();
		}
	}
	
	@Override
	public void pauseTimers() {
		this.paused = true;
		this.timer.pause();
		this.invulnerabilityAnimTimer.pause();
		this.invulnerabilityTimer.pause();
	}
	
	@Override
	public void resumeTimers() {
		this.paused = false;
		this.timer.resume();
		this.invulnerabilityAnimTimer.resume();
		this.invulnerabilityTimer.resume();
	}
	
	public int getLives() {
		return this.lives;
	}
	
	public void addLife() {
		this.lives++;
	}
	
	public void resetPosition() {
		this.xPos = GamePanel.WIDTH / 2 - this.width / 2;
		this.yPos = GamePanel.HEIGHT - 60;
	}
	
	public void respawn() {
		this.lives--;
		this.resetPosition();
		this.dead = false;
	}
	
	public void stop(long duration) {
		stop();
		this.timer.newDelay(duration);
	}
	
	public void keyPressed(int k) {
		
		boolean right = k == KeyEvent.VK_D;
		boolean left = k == KeyEvent.VK_A;
		boolean enter = k == KeyEvent.VK_ENTER;

		if (right) {
			this.right = true;
		}
		if (left) {
			this.left = true;
		}
		if (enter) {
			this.shooting = true;
		}
	}

	public void keyReleased(int k) {
		
		boolean right = k == KeyEvent.VK_D;
		boolean left = k == KeyEvent.VK_A;
		boolean enter = k == KeyEvent.VK_ENTER;

		if (right) {
			this.right = false;
		}
		if (left) {
			this.left = false;
		}
		if (enter) {
			this.shooting = false;
		}
	}
}
