/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.GameStateLogic;

import game.Core.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import game.Components.Enemies.InvaderGroup;
import game.Components.Enemies.Invader;
import game.Components.Player;
import game.Components.DifficultyScaling;
import game.Components.Weapon;
import game.Components.GameTimer;
import java.awt.event.KeyEvent;
import game.Components.ScoreCounter;

/**
 *
 * @author david
 */
public class ClassicModeState extends GameState {

	private Player player;
	private InvaderGroup invaders;
	private ArrayList<Invader> deadInvaders;
	private ScoreCounter score;
	private GameTimer startTimer;
	private GameTimer respawnTimer;
	private DifficultyScaling scaling;
	private boolean paused;

	public ClassicModeState(GameStateManager stateManager) {
		super(stateManager);
		init();
	}

	public void init() {
		Weapon base = new Weapon(-1000, 1000, 1, 12, 20, "up", "./src/game/Graphics/Projectiles/disparo.png");
		this.invaders = new InvaderGroup(10, 50, 6, 9);
		this.deadInvaders = new ArrayList<>();
		this.scaling = new DifficultyScaling(false, "log");
		this.player = new Player(1, 3, base, "./src/game/Graphics/Player/nave2.png");
		this.score = new ScoreCounter();
		this.startTimer = new GameTimer();
		this.respawnTimer = new GameTimer();
		this.respawnTimer.newDelay(2500);
		this.respawnTimer.endDelay();
		this.startTimer.newDelay(3000);
		this.paused = false;
	}

	public void tick() {
		if (paused) {
			this.startTimer.resume();
			this.respawnTimer.resume();
			this.paused = false;
		}
		
		if (!this.startTimer.delayFinished()) return;
		if (this.respawnTimer.delayFinished()) this.player.setVisible(true);
		
		if (this.player.isDead()) {
			if (this.player.getLives() < 1) {
				gameOver();
			} else {
				this.player.respawn();
				this.stopAllEntities(2500);
				this.player.setVisible(false);
				this.respawnTimer.reset();
				
				this.player.getWeapon().clear();
				for (Invader invader: this.invaders.getInvaders()) {
					invader.getWeapon().clear();
				}
			}
		}
		
		this.player.tick();
		this.invaders.tick();
		
		for (Invader invader: this.invaders.getInvaders()) {
			this.player.getWeapon().collision(invader);
			invader.getWeapon().collision(player);
			if (invader.isDead()) {
				this.score.addScore(invader.getScore());
				this.invaders.stop(200);
				this.deadInvaders.add(invader);
			}
			if (this.player.getBounds().intersects(invader.getBounds())) gameOver();
		}
		for (Invader invader: this.deadInvaders) {
			this.invaders.killInvader(invader);
		}
		
		if (this.invaders.getInvaders().isEmpty()) {
			newLevel();
		}
	}

	public void render(Graphics g) {
		Image background = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/background.png");
		Image life = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/vida.png");
		g.drawImage(background, 0, 0, null);

		this.player.render(g);
		this.invaders.render(g);
		
		for (int i = 0; i < player.getLives(); i++) {
			g.drawImage(life, 90 + i * 30, 13, null);
		}
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.setColor(new Color(51, 175, 255));
		g.drawString("Score: " + this.score.getScore(), 450, 10 + g.getFontMetrics().getHeight() - 10);
		g.drawString("Lives:", 10, 10 + g.getFontMetrics().getHeight() - 10);
		
		if (!this.startTimer.delayFinished()) {
			g.setFont(new Font("Arial", Font.PLAIN, 40));
			g.setColor(Color.WHITE);
			g.drawString("SPACE INVADERS", GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth("SPACE INVADERS") / 2, 200 + g.getFontMetrics().getHeight());
			g.drawString("MODO CLÁSICO", GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth("MODO CLÁSICO") / 2, 400 + g.getFontMetrics().getHeight());
		}
	}
	
	public void newLevel() {
		double multiplier = this.scaling.newLevel();
		this.player.resetPosition();
		this.invaders = new InvaderGroup(10, 50, 6, 9);
		this.invaders.setSpeedMultiplier(multiplier);
		this.clearProjectiles();
		this.stopAllEntities(2000);
	}
	
	public void clearProjectiles() {
		for (int i = 0; i < this.invaders.getInvaders().size(); i++) {
			this.invaders.getInvaders().get(i).getWeapon().clear();
		}
		this.player.getWeapon().clear();
	}
	
	public void stopAllEntities(long duration) {
		this.player.stop(duration);
		this.invaders.stop(duration);
	} 
	
	public void reset() {
		this.player.respawn();
	}
	
	public void gameOver() {
		this.stateManager.pushState(new GameOverState(this.stateManager));
	}
	
	public void moveEnemy() {

	}

	public void keyPressed(int k) {
		this.player.keyPressed(k);
		boolean esc = k == KeyEvent.VK_ESCAPE;
		if (esc) {
			this.paused = true;
			this.startTimer.pause();
			this.respawnTimer.pause();
			this.stateManager.pushState(new PauseMenuState(this.stateManager));
		}
	}

	public void keyReleased(int k) {
		this.player.keyReleased(k);
	}
}
