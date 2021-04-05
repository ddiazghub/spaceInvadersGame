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
import java.util.ArrayList;
import game.Core.GamePanel;
import java.awt.Graphics;

public class InvaderGroup {

	private int rows;
	private int columns;
	private ArrayList<Invader> invaders;
	private ArrayList<Invader> lastInvadersX;
	private ArrayList<Integer> bottomInvaders;

	public InvaderGroup(int x, int y, int rows, int columns) {

		this.invaders = new ArrayList<>();
		this.lastInvadersX = new ArrayList<>();
		this.bottomInvaders = new ArrayList<>();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				this.invaders.add(new Invader(100 + j * (GamePanel.WIDTH / 10 - 25), 10 + i * (GamePanel.HEIGHT / 10 - 10), 3, GamePanel.WIDTH / 30, 7));
			}
		}
		for (int i = this.invaders.size() - 1 - columns; i < this.invaders.size() - 1; i++) {
			this.invaders.get(i).toggleShooting();
			this.bottomInvaders.add(i);
		}

	}

	public void getLastRow() {
		for (int i = 0; i < this.invaders.size() - 1; i++) {
			
			this.bottomInvaders.add(i);
		}
	}
	
	public void killInvader() {
		
	}

	public void tick() {
		for (Invader invader : invaders) {
			invader.tick();
		}
	}

	public void render(Graphics g) {
		for (Invader invader : invaders) {
			invader.render(g);
		}
	}

	/*
	private int rows;
	private int columns;
	private Invader[][] invaders;
	private Invader[] lastInvadersX;
	private Invader[] lastInvadersY;
	
	public InvaderGroup(int x, int y, int rows, int columns) {
		
		this.invaders = new Invader[rows][columns];
		this.lastInvadersX = new Invader[rows];
		this.lastInvadersY = new Invader[columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				this.invaders[i][j] = new Invader(x + i * (GamePanel.WIDTH / 10 - 10), y + j * (GamePanel.HEIGHT / 10 - 10), 2, GamePanel.WIDTH / 40);
				if (j == columns - 1) {
					this.lastInvadersX[i] = this.invaders[i][j];
				}
				if (i == rows - 1) {
					this.lastInvadersY[j] = this.invaders[i][j];
				}
			}
		}
	}
	
	
	public void tick() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				this.invaders[i][j].tick();
			}
		}
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				this.invaders[i][j].render(g);
			}
		}
	}
}
	 */
}
