/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components.Enemies;

/**
 *
 * @author david
 */
import game.Components.Enemy;
import game.Components.Weapon;
import game.Core.GamePanel;
import java.util.Random;

public class Invader extends Enemy {

	private int moveFrecuency;
	private int moveDelta;
	private boolean canMoveLeft;
	private boolean canMoveRight;
	private boolean fixedMovementX;
	private int movementRepetitions;
	private int movementCounter;

	public Invader(int x, int y, int moveFrecuency, int moveDelta) {

		super(x, y, 1, 3, new Weapon(-1000, 1000, 1, 4, 0.5, "down", "./src/game/Graphics/Player/disparo.png"), "base", "./src/game/Graphics/Enemies/alienB.png");

		this.moveDelta = moveDelta;
		this.right = true;
		this.shooting = true;
		this.moveFrecuency = moveFrecuency;
		this.canMoveLeft = this.xPos > 0;
		this.canMoveRight = this.xPos < GamePanel.WIDTH - this.sprite.getWidth(null);
		this.fixedMovementX = false;
	}

	public Invader(int x, int y, int moveFrecuency, int moveDelta, int movementRepetitions) {

		super(x, y, 1, 3, new Weapon(-1000, 1000, 1, 5, 1, "down", "./src/game/Graphics/Player/disparo.png"), "base", "./src/game/Graphics/Enemies/alienB.png");

		this.moveDelta = moveDelta;
		this.right = true;
		this.shooting = false;
		this.moveFrecuency = moveFrecuency;
		this.canMoveLeft = this.xPos > 0;
		this.canMoveRight = this.xPos < GamePanel.WIDTH - this.sprite.getWidth(null);
		this.fixedMovementX = true;
		this.movementRepetitions = movementRepetitions;
		this.movementCounter = 0;
	}

	public void tick() {

		this.move();
		if (this.hasWeapon) {
			this.weapon.tick();
			this.weapon.setX(this.xPos + this.WIDTH / 2);
			this.weapon.setY(this.yPos + this.HEIGHT);
		}

	}

	public void move() {
		switch (this.behavior) {
			case "base":

				this.canMoveLeft = this.xPos > 0;
				this.canMoveRight = this.xPos < GamePanel.WIDTH - this.sprite.getWidth(null);

				if (!this.allowMovement) {
					this.waitForMovement();
					return;
				}
				if (this.right) {
					this.moveRight();
				}
				if (this.left) {
					this.moveLeft();
				}
				if (this.down) {
					this.moveDown();
				}
				if (shooting && this.hasWeapon) {
					this.shoot();
				}

				break;
		}
	}

	public void waitForMovement() {
		long elapsed = System.currentTimeMillis() - this.lastMoveTime;
		if (elapsed >= 1000 / this.moveFrecuency) {
			this.allowMovement();
		}
	}

	public void allowMovement() {
		this.allowMovement = true;
		this.lastMoveX = this.xPos;
		this.lastMoveY = this.yPos;
		if (this.fixedMovementX) {
			this.movementCounter++;
		}
	}

	public void moveRight() {
		if (!this.fixedMovementX) {
			if (canMoveRight) {
				this.xPos += this.movementSpeed * this.movementSpeedPercent;
				this.lastMovedRight = true;
			} else {
				this.right = false;
				this.down = true;
				this.allowMovement = false;
				this.lastMoveTime = System.currentTimeMillis();
			}
			if (this.xPos - this.lastMoveX >= this.moveDelta) {
				this.allowMovement = false;
				this.lastMoveTime = System.currentTimeMillis();
			}
		} else {
			if (this.movementCounter <= this.movementRepetitions) {
				this.xPos += this.movementSpeed * this.movementSpeedPercent;
				this.lastMovedRight = true;
			} else {
				this.right = false;
				this.down = true;
				this.allowMovement = false;
				this.lastMoveTime = System.currentTimeMillis();
				this.movementCounter = 0;
			}
			if (this.xPos - this.lastMoveX >= this.moveDelta) {
				this.allowMovement = false;
				this.lastMoveTime = System.currentTimeMillis();
			}
		}
	}

	public void moveLeft() {
		if (!this.fixedMovementX) {
			if (this.canMoveLeft) {
				this.xPos -= this.movementSpeed * this.movementSpeedPercent;
				this.lastMovedRight = false;
			} else {
				this.left = false;
				this.down = true;
				this.allowMovement = false;
				this.lastMoveTime = System.currentTimeMillis();
			}
			if (this.lastMoveX - this.xPos >= this.moveDelta) {
				this.allowMovement = false;
				this.lastMoveTime = System.currentTimeMillis();
			}
		} else {
			if (this.movementCounter <= this.movementRepetitions) {
				this.xPos -= this.movementSpeed * this.movementSpeedPercent;
				this.lastMovedRight = false;
			} else {
				this.left = false;
				this.down = true;
				this.allowMovement = false;
				this.lastMoveTime = System.currentTimeMillis();
				this.movementCounter = 0;
			}
			if (this.lastMoveX - this.xPos >= this.moveDelta) {
				this.allowMovement = false;
				this.lastMoveTime = System.currentTimeMillis();
			}
		}
	}

	public void moveDown() {
		this.yPos += this.movementSpeed * this.movementSpeedPercent;
		if (this.yPos - this.lastMoveY >= this.moveDelta) {
			if (this.lastMovedRight) {
				this.allowMovement = false;
				this.lastMoveTime = System.currentTimeMillis();
				this.left = true;
				this.down = false;
			} else {
				this.allowMovement = false;
				this.lastMoveTime = System.currentTimeMillis();
				this.right = true;
				this.down = false;
			}
			if (this.fixedMovementX) {
				this.movementCounter = 0;
			}
		}
	}

	public void followX(Invader invader) {
		this.yPos = invader.getY();
	}

	public void followY(Invader invader) {
		this.xPos = invader.getX();
	}

	public boolean movedDown() {
		return this.down;
	}

	public void shoot() {
		Random random = new Random();
		double rand = random.nextFloat();
		double chance = 0.01;
		if (rand > 1 - chance) {
			this.weapon.shoot();
		}
	}
	
	public void toggleShooting() {
		this.shooting = !this.shooting;
	}
	
	public boolean canShoot() {
		return this.shooting;
	}

	public void toggleMovement() {
		this.allowMovement = !this.allowMovement;
	}

	public boolean canMove() {
		return this.allowMovement;
	}
}
