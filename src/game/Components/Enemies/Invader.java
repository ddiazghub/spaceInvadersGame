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

public class Invader extends Enemy {

	private int row;
	private int xDir;
	public Invader(int x, int y, int initialRow) {
		super(x, y, 1, 1, 3, 100, new Weapon(-1000, 1000, 1, 4, 0.5, 0.2, "down", "./src/game/Graphics/Projectiles/shock.png"), "base", "./src/game/Graphics/Enemies/alienB.png");
		this.row = initialRow;
		this.xDir = 1;
	}
	
	public void tick() {
		if (this.hasWeapon) this.weapon.tick();
		
		if (this.timer.delayFinished()) resume();
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
		this.xPos += this.xSpeed * this.xDir * this.speedMultiplier;
		if (this.xPos > GamePanel.WIDTH - this.width) {
			this.xPos = GamePanel.WIDTH - this.width;
		} else if (this.xPos < 0) {
			this.xPos = 0;
		}
	}
	
	public void stop(long duration) {
		stop();
		this.timer.newDelay(duration);
	}
	
	public void reverseDirection() {
		this.yPos += GamePanel.HEIGHT / 16;
		this.xDir *= -1;
		this.row++;
	}
	
	public void shoot() {
		this.weapon.shoot();
	}
	
	public int getRow() {
		return this.row;
	}
	
	public void allowShooting() {
		this.shooting = true;
	}
}
	
	/*
	private int moveFrecuency;
	private int moveDelta;
	private boolean canMoveLeft;
	private boolean canMoveRight;
	private boolean fixedMovementX;
	private int movementRepetitions;
	private int movementCounter;
	private boolean changedDirection = false;
	private int row;

	public Invader(int x, int y, int moveFrecuency, int moveDelta, int initialRow) {

		super(x, y, 1, 5, 3, 10, new Weapon(-1000, 1000, 1, 4, 0.5, "down", "./src/game/Graphics/Player/disparo.png"), "base", "./src/game/Graphics/Enemies/alienB.png");

		this.moveDelta = moveDelta;
		this.right = true;
		this.shooting = true;
		this.moveFrecuency = moveFrecuency;
		this.canMoveLeft = this.xPos > 0;
		this.canMoveRight = this.xPos < GamePanel.WIDTH - this.sprite.getWidth(null);
		this.fixedMovementX = false;
		this.row = initialRow;
	}

	public Invader(int x, int y, int moveFrecuency, int moveDelta, int movementRepetitions, int initialRow) {
		
		super(x, y, 1, 3, 3, 10, new Weapon(-1000, 1000, 1, 5, 3, "down", "./src/game/Graphics/Player/disparo.png"), "base", "./src/game/Graphics/Enemies/alienB.png");

		this.moveDelta = moveDelta;
		this.right = true;
		this.shooting = false;
		this.moveFrecuency = moveFrecuency;
		this.canMoveLeft = this.xPos > 0;
		this.canMoveRight = this.xPos < GamePanel.WIDTH - this.sprite.getWidth(null);
		this.fixedMovementX = true;
		this.movementRepetitions = movementRepetitions;
		this.movementCounter = 0;
		this.row = initialRow;
	}

	public void tick() {

		this.move();
		if (this.hasWeapon) {
			this.weapon.tick();
			this.weapon.setX(this.xPos + this.width / 2);
			this.weapon.setY(this.yPos + this.height);
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
				this.xPos += this.xSpeed * this.speedPercentX;
			} else {
				this.changedDirection = true;
				changeDirection();
			}
			if (this.xPos - this.lastMoveX >= this.moveDelta) {
				this.allowMovement = false;
				this.lastMoveTime = System.currentTimeMillis();
			}
		} else {
			if (this.movementCounter <= this.movementRepetitions) {
				this.xPos += this.xSpeed * this.speedPercentX;
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
				this.xPos -= this.xSpeed * this.speedPercentX;
			} else {
				this.changedDirection = true;
				changeDirection();
			}
			if (this.lastMoveX - this.xPos >= this.moveDelta) {
				this.allowMovement = false;
				this.lastMoveTime = System.currentTimeMillis();
			}
		} else {
			if (this.movementCounter <= this.movementRepetitions) {
				this.xPos -= this.xSpeed * this.speedPercentX;;
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
		this.yPos += this.ySpeed * this.speedPercentY;
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
			this.row++;
		}
	}

	public void changeDirection() {
		this.left = !this.left;
		this.right = !this.right;
		this.yPos += this.moveDelta;
		this.allowMovement = false;
		this.lastMoveTime = System.currentTimeMillis();
		this.row++;
	}
	
	public boolean changedDirection() {
		boolean answer = this.changedDirection;
		this.changedDirection = false;
		return answer;
	}

	public boolean movedDown() {
		return this.down;
	}
	
	public void setDown() {
		this.down = true;
	}

	public void shoot() {
		Random random = new Random();
		double rand = random.nextFloat();
		double chance = 0.1;
		if (rand > 1 - chance) {
			this.weapon.shoot();
		}
	}
	
	public int getRow() {
		return this.row;
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
*/
