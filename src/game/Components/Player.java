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
import java.awt.Graphics;

public class Player extends Character {

	private boolean right = false;
	private boolean left = false;
	private boolean shooting = false;

	public Player(int x, int y, int hp, int movementSpeed, Weapon weapon, String imagePath) {

		super(x, y, hp, movementSpeed, weapon, imagePath);
		this.weapon.setY(this.yPos);
	}

	public void tick() {
		boolean canMoveLeft = xPos > 0;
		boolean canMoveRight = xPos < GamePanel.WIDTH - this.sprite.getWidth(null);
		this.weapon.tick();
		this.weapon.setX(this.xPos + this.WIDTH / 2);
			if (right && canMoveRight) {
				this.xPos += 5;
			}
			if (left && canMoveLeft) {
				this.xPos -= 5;
			}
			if (shooting) {
				this.weapon.shoot();
			}
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
