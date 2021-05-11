/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.GameStateLogic;

import game.Components.CharacterEntity;
import game.Components.DifficultyScaling;
import game.Components.Enemies.Red1;
import game.Components.Enemies.YellowBoss2;
import game.Components.Enemies.RedBoss;
import game.Components.GameTimer;
import game.Components.Player;
import game.Components.ScoreCounter;
import game.Components.Weapon;
import game.Core.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author david
 */
public class SurvivalModeState extends GameState{
	
	
	private Player player;
	private ArrayList<CharacterEntity> enemies;
	private ArrayList<CharacterEntity> deadEnemies;
	private ScoreCounter score;
	private GameTimer startTimer;
	private GameTimer respawnTimer;
	private DifficultyScaling scaling;
	private boolean paused;

	public SurvivalModeState(GameStateManager stateManager) {
		super(stateManager);
		init();
	}

	public void init() {
		Weapon base = new Weapon(-1000, 1000, 15, 12, 20, "up", "./src/game/Graphics/Projectiles/disparo.png");
		this.enemies = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			this.enemies.add(new YellowBoss2());
		}
		
		this.enemies.add(new RedBoss(400, 200));
		this.deadEnemies = new ArrayList<>();
		this.scaling = new DifficultyScaling(false, "log");
		this.player = new Player(100, 3, base, "./src/game/Graphics/Player/nave2.png");
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
			this.player.resumeTimers();
			for (CharacterEntity enemy: enemies) {
				enemy.resumeTimers();
			}
			this.paused = false;
		}
		
		if (!this.startTimer.delayFinished()) return;
		if (this.respawnTimer.delayFinished()) {
			if (!this.player.isVisible()) {
				this.player.invulnerable(2000);
				this.player.setVisible(true);
			}
		}
	
		if (this.player.isDead()) {
			if (this.player.getLives() < 1) {
				gameOver();
			} else {
				this.player.respawn(100);
				this.stopAllEntities(2500);
				this.player.setVisible(false);
				this.respawnTimer.reset();
				
				this.player.getWeapon().clear();
				for (CharacterEntity enemy: enemies) {
					enemy.getWeapon().clear();
				}
			}
		}
		
		this.player.tick();
		
		for (CharacterEntity enemy: enemies) {
			enemy.tick();
			this.player.getWeapon().collision(enemy);
			enemy.getWeapon().collision(player);
			if (enemy.isDead()) {
				this.score.addScore(enemy.getScore());
				this.deadEnemies.add(enemy);
			}
			if (this.player.getBounds().intersects(enemy.getBounds())) gameOver();
		}
		for (CharacterEntity enemy: deadEnemies) {
			enemies.remove(enemy);
		}
		
		/*
		if (this.invaders.getInvaders().isEmpty()) {
			newLevel();
		}
		*/
	}

	public void render(Graphics g) {
		Image background = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/background.png");
		Image life = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/vida.png");
		g.drawImage(background, 0, 0, null);

		this.player.render(g);
		for (CharacterEntity enemy: enemies) {
			enemy.render(g);
		}
		
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
			g.drawString("MODO SUPERVIVENCIA", GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth("MODO SUPERVIVENCIA") / 2, 400 + g.getFontMetrics().getHeight());
		}
	}
	
	public void newLevel() {
		int[] levelsFor1Up = {4, 8, 12, 16, 20, 24, 28, 32, 36, 40};
		double multiplier = this.scaling.newLevel();
		if (Arrays.binarySearch(levelsFor1Up, (int) this.scaling.getCount()) > -1) {
			this.player.addLife();
		}
		this.player.resetPosition();
		this.deadEnemies.clear();
		/*
		this.enemies = new InvaderGroup(10, 50, (int) Math.floor(6 + multiplier), (int) Math.floor(9 + multiplier));
		this.invaders.setSpeedMultiplier(multiplier);
		*/
		this.clearProjectiles();
		this.stopAllEntities(2000);
	}
	
	public void clearProjectiles() {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).getWeapon().clear();
		}
		this.player.getWeapon().clear();
	}
	
	public void stopAllEntities(long duration) {
		this.player.stop(duration);
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).stop(duration);
		}
	} 
	
	public void gameOver() {
		this.stateManager.pushState(new GameOverState(this.stateManager, "survival"));
	}

	public void keyPressed(int k) {
		this.player.keyPressed(k);
		boolean esc = k == KeyEvent.VK_ESCAPE;
		if (esc) {
			this.paused = true;
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).pauseTimers();
			}
			this.startTimer.pause();
			this.respawnTimer.pause();
			this.player.pauseTimers();
			this.stateManager.pushState(new PauseMenuState(this.stateManager));
		}
	}

	public void keyReleased(int k) {
		this.player.keyReleased(k);
	}
}

