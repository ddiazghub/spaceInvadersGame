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
public abstract class Enemy extends Character {

	protected String behavior;
	protected boolean lastMovedRight;
	protected boolean down;
	protected boolean allowMovement;
	protected long lastMoveTime;
	protected int lastMoveX;
	protected int score;
	protected int lastMoveY;

	public Enemy(int x, int y, int hp, int xSpeed, int ySpeed, int score, Weapon weapon, String behavior, String imagePath) {

		super(x, y, hp, xSpeed, ySpeed, weapon, imagePath);
		this.behavior = behavior;
		this.right = false;
		this.left = false;
		this.down = false;
		this.shooting = false;
		this.score = score;
		this.allowMovement = false;
		this.lastMoveTime = System.currentTimeMillis();

	}

	public abstract void tick();

	public abstract void move();

	public abstract void shoot();

	public void toggleMovement() {
		this.allowMovement = !this.allowMovement;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void collision(Player player) {
		if (player.getBounds().intersects(this.getBounds())) {
			player.hurt(this.weapon.getDamage());
			System.out.println("COLLISION DETECTED");
		}
	}

	public boolean canMove() {
		return this.allowMovement;
	}
}
