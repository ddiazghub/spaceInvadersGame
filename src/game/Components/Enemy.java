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
	protected int lastMoveY;

	public Enemy(int x, int y, int hp, double xSpeed, double ySpeed, int score, Weapon weapon, String imagePath) {

		super(x, y, hp, xSpeed, ySpeed, score, weapon, imagePath);
		this.behavior = behavior;
		this.right = false;
		this.left = false;
		this.down = false;
		this.shooting = false;
		this.allowMovement = false;
		this.lastMoveTime = System.currentTimeMillis();

	}

	public abstract void tick();

	public abstract void move();

	public abstract void shoot();

	public void toggleMovement() {
		this.allowMovement = !this.allowMovement;
	}

	public boolean canMove() {
		return this.allowMovement;
	}
	
}
