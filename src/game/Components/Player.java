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

	public Player(int x, int y, int hp, int movementSpeed, String imagePath) {

		super(x, y, hp, movementSpeed, imagePath);

	}

	public void tick() {
		boolean canMoveLeft = xPos > 0;
		boolean canMoveRight = xPos < GamePanel.WIDTH - this.sprite.getWidth(null);
			if (right && canMoveRight) {
				this.xPos += 5;
			}
			if (left && canMoveLeft) {
				this.xPos -= 5;
			}
	}

	public void render(Graphics g) {

		g.drawImage(this.sprite, this.xPos, this.yPos, null);

	}

	public void keyPressed(int k) {

		boolean right = k == KeyEvent.VK_D;
		boolean left = k == KeyEvent.VK_A;

		if (right) {
			this.right = true;
		}
		if (left) {
			this.left = true;
		}
	}

	public void keyReleased(int k) {

		boolean right = k == KeyEvent.VK_D;
		boolean left = k == KeyEvent.VK_A;

		if (right) {
			this.right = false;
		} else if (left) {
			this.left = false;
		}
	}
}
