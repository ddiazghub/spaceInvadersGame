/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.GameStateLogic;

import game.Components.Enemies.*;
import game.Components.*;
import game.Core.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author david
 */
public class SurvivalModeState extends GameState{
	
	
	private Player player;
	private ArrayList<CharacterEntity> enemies;
	private ArrayList<CharacterEntity> deadEnemies;
	private HashMap<String, Integer> enemySpawnRates;
	private HashMap<String, Integer> bossSpawnRates;
	private HashMap<String, Sound> sounds;
	private ScoreCounter score;
	private GameTimer startTimer;
	private GameTimer respawnTimer;
	private DifficultyScaling scaling;
	private GameTimer enemySpawnTimer;
	private GameTimer collectableSpawnTimer;
	private ArrayList<Collectable> collectables;
	private ArrayList<Collectable> collectablesToRemove;
	private HashMap<String, Integer> collectablesSpawnRates;
	private int enemiesToSpawn;
	private int spawnedEnemies;
	private int maxNumberOfEnemies;
	private boolean boss;
	private boolean paused;
	private Sound bossMusic;
	private double enemyHpMultiplier = 1;
	private double enemyDamageMultiplier = 1;

	public SurvivalModeState(GameStateManager stateManager) {
		super(stateManager);
	}

	public void init() {
		Weapon base = new Weapon(-1000, 1000, 15, 12, 20, "up", "./src/game/Graphics/Projectiles/disparo.png", new Sound("./src/game/Sound/SoundEffects/shot.wav"));
		this.enemies = new ArrayList<>();
		//this.enemies.add(new YellowBoss(this.player));
		this.deadEnemies = new ArrayList<>();
		this.scaling = new DifficultyScaling(false, "log");
		this.player = new Player(100, 3, base, "./src/game/Graphics/Player/nave2.png");
		this.score = new ScoreCounter();
		this.startTimer = new GameTimer();
		this.respawnTimer = new GameTimer();
		this.enemySpawnTimer = new GameTimer();
		this.respawnTimer.newDelay(2500);
		this.respawnTimer.endDelay();
		this.startTimer.newDelay(3000);
		this.enemySpawnTimer.newDelay(1000);
		this.paused = false;
		this.music = new Sound("./src/game/Sound/Music/game.wav");
		this.music.play(true);
		this.bossMusic = new Sound("./src/game/Sound/Music/boss.wav");
		this.enemiesToSpawn = 80;
		this.spawnedEnemies = 0;
		this.maxNumberOfEnemies = 8;
		this.sounds = new HashMap<>();
		this.sounds.put("pause", new Sound("./src/game/Sound/SoundEffects/menu_back.wav"));
		this.boss = false;
		
		this.collectableSpawnTimer = new GameTimer();
		this.collectableSpawnTimer.newDelay(5000);
		this.collectables = new ArrayList<>();
		this.collectablesToRemove = new ArrayList<>();
		this.collectablesSpawnRates = new HashMap<>();
		this.collectablesSpawnRates.put("hp", 17);
		this.collectablesSpawnRates.put("maxHp", 14);
		this.collectablesSpawnRates.put("speed", 14);
		this.collectablesSpawnRates.put("damage", 12);
		this.collectablesSpawnRates.put("firerate", 10);
		this.collectablesSpawnRates.put("red_laser", 10);
		this.collectablesSpawnRates.put("blue_laser", 10);
		this.collectablesSpawnRates.put("missile", 8);
		this.collectablesSpawnRates.put("blue_plasma", 5);
		
		this.enemySpawnRates = new HashMap<>();
		this.enemySpawnRates.put("Red1", 14);
		this.enemySpawnRates.put("Blue1", 14);
		this.enemySpawnRates.put("Yellow1", 14);
		this.enemySpawnRates.put("Red2", 14);
		this.enemySpawnRates.put("Brown1", 14);
		this.enemySpawnRates.put("Yellow2", 10);
		this.enemySpawnRates.put("Blue2", 8);
		this.enemySpawnRates.put("Red3", 4);
		this.enemySpawnRates.put("Brown3", 4);
		this.enemySpawnRates.put("Blue3", 4);
		
		this.bossSpawnRates = new HashMap<>();
		this.bossSpawnRates.put("RedBoss", 25);
		this.bossSpawnRates.put("BlueBoss", 25);
		this.bossSpawnRates.put("YellowBoss", 25);
		this.bossSpawnRates.put("BrownBoss", 25);
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
				this.player.setVisible(true);
			}
		}
		
		if (enemies.size() == 0 && this.boss) {
			this.boss = false;
			this.bossMusic.stop();
			this.music.play(true);
			newLevel();
		}
	
		if (this.player.isDead()) {
			if (this.player.getLives() < 1) {
				gameOver();
			} else {
				this.player.invulnerable(4500);
				this.player.respawn();
				this.player.setVisible(false);
				this.player.stop(2500);
				this.respawnTimer.reset();
				
				this.player.getWeapon().clear();
				for (CharacterEntity enemy: enemies) {
					if (enemy.getWeapon() != null) enemy.getWeapon().clear();
				}
			}
		}
		
		this.player.tick();
		
		this.player.getWeapon().collision(enemies);
		for (CharacterEntity enemy: enemies) {
			enemy.tick();
			if (enemy.getWeapon() != null) enemy.getWeapon().collision(player);
			enemy.collision(player);
			if (enemy.isDead()) {
				this.score.addScore(enemy.getScore());
				this.deadEnemies.add(enemy);
			}
		}
		for (CharacterEntity enemy: deadEnemies) {
			enemies.remove(enemy);
		}
		
		if (this.enemySpawnTimer.delayFinished() && this.spawnedEnemies < this.enemiesToSpawn && enemies.size() < this.maxNumberOfEnemies) {
			double enemiesPerSecond = 1.5 * this.enemiesToSpawn / 60;
			while (enemiesPerSecond >= 0) {
				if (new Random().nextDouble() <= enemiesPerSecond) {
					try {
						Class enemyClass = Class.forName("game.Components.Enemies." + pickRandomWithRates(enemySpawnRates));
						CharacterEntity enemy = (CharacterEntity) enemyClass.newInstance();
						enemy.maxHpMultiplier(enemyHpMultiplier);
						enemy.damageMultiplier(enemyDamageMultiplier);
						enemies.add(enemy);
						spawnedEnemies++;
					} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
						System.out.println(e.getMessage());
					}
				}
				enemiesPerSecond--;
			}
			this.enemySpawnTimer.reset();
		}
		
		for (Collectable collectable: this.collectables) {
			collectable.tick();
			if (collectable.collision(player) || collectable.getY() > GamePanel.WIDTH) {
				this.collectablesToRemove.add(collectable);
			}
		}
		
		for (Collectable collectable: this.collectablesToRemove) {
			this.collectables.remove(collectable);
		}
		
		this.collectablesToRemove.clear();
		
		if (this.collectableSpawnTimer.delayFinished()) {
			if (new Random().nextFloat() >= 0.6) this.collectables.add(new Collectable(pickRandomWithRates(this.collectablesSpawnRates)));
			this.collectableSpawnTimer.reset();
		}
		
		if (this.spawnedEnemies >= this.enemiesToSpawn && enemies.size() == 0 && !boss) {
			try {
				Class bossClass = Class.forName("game.Components.Enemies." + pickRandomWithRates(bossSpawnRates));
				CharacterEntity boss = (CharacterEntity) bossClass.newInstance();
				boss.maxHpMultiplier(enemyHpMultiplier);
				boss.damageMultiplier(enemyDamageMultiplier);
				enemies.add(boss);
				spawnedEnemies++;
			} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
				System.out.println(e.getMessage());
			}
			this.music.stop();
			boss = true;
			this.bossMusic.play(true);
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
		for (Collectable collectable: this.collectables) collectable.render(g);
		
		for (int i = 0; i < player.getLives(); i++) {
			g.drawImage(life, 570 + i * 30, 13, null);
		}
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.setColor(new Color(51, 175, 255));
		g.drawString("Score: " + this.score.getScore(), 800, g.getFontMetrics().getHeight());
		g.drawString("Lives: ", 485, g.getFontMetrics().getHeight());
		
		g.setColor(Color.WHITE);
		if (!this.startTimer.delayFinished()) {
			g.setFont(new Font("Arial", Font.PLAIN, 30));
			g.drawString("SPACE INVADERS", GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth("SPACE INVADERS") / 2, 200 + g.getFontMetrics().getHeight());
			g.drawString("MODO SUPERVIVENCIA", GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth("MODO SUPERVIVENCIA") / 2, 400 + g.getFontMetrics().getHeight());
		}
		
		g.fillRect(5, 5, 250, 40);
		int hpBar = (this.player.getHp() * 240) / this.player.getMaxHp();
		if (hpBar < 60) g.setColor(Color.RED);
		else if (hpBar < 120) g.setColor(Color.YELLOW);
		else g.setColor(Color.GREEN);
		if (this.respawnTimer.delayFinished()) {
			g.fillRect(10, 10, hpBar, 30);
			g.drawString("" + this.player.getHp() + "/" + this.player.getMaxHp(), 265, 10 + g.getFontMetrics().getHeight() - 10);
		} else {
			g.setColor(Color.RED);
			g.drawString("" + 0 + "/" + this.player.getMaxHp(), 265, 10 + g.getFontMetrics().getHeight() - 10);
		}
		
		if (boss && enemies.size() > 0) {
			g.setColor(Color.GRAY);
			g.fillRect(GamePanel.WIDTH / 2 - 330, 60, 660, 60);
			g.setColor(Color.RED);
			int bossHpBar = (this.enemies.get(0).getHp() * 650) / this.enemies.get(0).getMaxHp();
			g.fillRect(GamePanel.WIDTH / 2 - 325, 65, bossHpBar, 50);
		} 
	}
	
	public void newLevel() {
		int[] levelsFor1Up = {4, 8, 12, 16, 20, 24, 28, 32, 36, 40};
		double multiplier = this.scaling.newLevel();
		if (Arrays.binarySearch(levelsFor1Up, (int) this.scaling.getCount()) > -1) {
			this.player.addLife();
		}
		this.deadEnemies.clear();
		this.enemiesToSpawn += 15;
		this.spawnedEnemies = 0;
		this.maxNumberOfEnemies += 4;
		this.enemyHpMultiplier *= 1.15;
		this.enemyDamageMultiplier *= 1.15;
		/*
		this.enemies = new InvaderGroup(10, 50, (int) Math.floor(6 + multiplier), (int) Math.floor(9 + multiplier));
		this.invaders.setSpeedMultiplier(multiplier);
		*/
		this.clearProjectiles();
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
	
	public void addEnemy(CharacterEntity enemy) {
		this.enemies.add(enemy);
	}
	
	public String pickRandomWithRates(HashMap<String, Integer> data) {
		ArrayList<String> shuffler = new ArrayList<>();
		for (String element : data.keySet()) {
			for (int i = 0; i < data.get(element); i++) shuffler.add(element);
		}
		return shuffler.get(new Random().nextInt(shuffler.size()));
	}
	
	public void gameOver() {
		this.stateManager.pushState(new GameOverState(this.stateManager, "survival"));
	}

	public void keyPressed(int k) {
		this.player.keyPressed(k);
		boolean esc = k == KeyEvent.VK_ESCAPE;
		if (esc) {
			this.sounds.get("pause").play(false);
			this.paused = true;
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).pauseTimers();
			}
			this.startTimer.pause();
			this.respawnTimer.pause();
			this.player.pauseTimers();
			if (boss) {
				this.stateManager.pushState(new PauseMenuState(this.stateManager, this.bossMusic));
			} else {
				this.stateManager.pushState(new PauseMenuState(this.stateManager, this.music));
			}
		}
	}

	public void keyReleased(int k) {
		this.player.keyReleased(k);
	}
}

