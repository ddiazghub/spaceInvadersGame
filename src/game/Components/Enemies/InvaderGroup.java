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
import game.Components.GameTimer;
import java.util.ArrayList;
import game.Core.GamePanel;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.Stack;

public class InvaderGroup {

	private double speedMultiplier;
	private boolean hitEdge = false;
	private ArrayList<Invader> invaders;
	private ArrayList<Stack<Invader>> invaderColumns;
	private Image[] sprites = new Image[7];
	protected boolean stopped;
	protected GameTimer timer = new GameTimer();

	public InvaderGroup(int x, int y, int rows, int columns) {

		this.invaders = new ArrayList<>();
		this.invaderColumns = new ArrayList<>();
		this.speedMultiplier = 1;
		
		for (int j = 0; j < columns; j++) {
			this.invaderColumns.add(new Stack<>());
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				// this.invaders.add(new Invader(x + j * (GamePanel.WIDTH / 10 - 25), y + i * (GamePanel.HEIGHT / 10 - 10), 1, GamePanel.WIDTH / 30, i));
				Invader invader = new Invader(x + j * (GamePanel.WIDTH / 13 - 28), y + i * (GamePanel.HEIGHT / 13 - 13), i);
				this.invaders.add(invader);
				this.invaderColumns.get(j).push(invader);
				this.changeColor(invader);
			}
		}
		for (int i = 0; i < this.invaderColumns.size() - 1; i++) {
			this.invaderColumns.get(i).peek().allowShooting();
		}
				
		File spritesDir = new File("./src/game/Graphics/Enemies/");
		File[] spritesList = spritesDir.listFiles();
		for (int i = 0; i < spritesList.length; i++) {
			try {
				this.sprites[i] = ImageIO.read(spritesList[i]);
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
			}
			System.out.println(spritesList[i].getPath());
			System.out.println(spritesList[i].getAbsolutePath());
		}
	}
	
	public void changeColor(Invader invader) {
		int row = invader.getRow();
		if (row > 13) row -= 14;
		if (row > 6) row -= 7;
		invader.setSprite(sprites[row]);
	}
	
	public ArrayList<Invader> getInvaders() {
		return this.invaders;
	}
	
	public void killInvader(Invader invader) {
		for (int i = 0; i < this.invaderColumns.size() - 1; i++) {
			this.invaderColumns.get(i).remove(invader);
		}
		this.invaders.remove(invader);
	}
	
	public void stop() {
		this.stopped = true;
	}
	
	public void resume() {
		this.stopped = false;
	}
	
	public void setSpeedMultiplier(double speedMultiplier) {
		this.speedMultiplier = speedMultiplier;
		this.speedMultiplier(speedMultiplier);
	}
	
	public void speedMultiplier(double speedMultiplier) {
		for (Invader invader: this.invaders) {
			invader.speedMultiplier(speedMultiplier);
		}
	}
	
	public void stop(long duration) {
		stop();
		this.timer.newDelay(duration);
	}

	public void tick() {
		if (this.timer.delayFinished()) resume();
		if (this.stopped) {
			for (Invader invader : invaders) {
			invader.tickWeapon();
			}
			return;
		}
		
		for (Invader invader : invaders) {
			invader.tick();
			if (invader.getX() >= GamePanel.WIDTH - invader.getWidth() || invader.getX() <= 0) this.hitEdge = true;
			this.changeColor(invader);
		}
		
		for (int i = 0; i < this.invaderColumns.size() - 10; i++) {
			if (this.invaderColumns.get(i).size() > 0) this.invaderColumns.get(i).peek().allowShooting();
		}
		
		if (this.hitEdge) {
			for (Invader invader : invaders) {
				invader.reverseDirection();
			}
			this.hitEdge = false;
		}
		
		if (this.invaders.size() < 5) {
			this.speedMultiplier(5 * this.speedMultiplier);
		} else if (this.invaders.size() < 10) {
			this.speedMultiplier(3 * this.speedMultiplier);
		} else if (this.invaders.size() < 20) {
			this.speedMultiplier(2 * this.speedMultiplier);
		} else if (this.invaders.size() < 30) {
			this.speedMultiplier(1.5 * this.speedMultiplier);
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
