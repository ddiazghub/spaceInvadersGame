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
	private ArrayList<Invader> deadInvaders = new ArrayList<>();
	private ScoreCounter score;
	private GameTimer timer;

	public ClassicModeState(GameStateManager stateManager) {
		super(stateManager);
		init();
	}

	public void init() {
		Weapon base = new Weapon(-1000, 1000, 1, 6, 2, "up", "./src/game/Graphics/Player/disparo.png");
		this.invaders = new InvaderGroup(10, 100, 6, 9);
		this.player = new Player(1, 1, base, "./src/game/Graphics/Player/nave2.png");
		this.score = new ScoreCounter();
		this.timer = new GameTimer();
		this.timer.newDelay(3000);
	}

	public void tick() {
		if (!this.timer.delayFinished()) return;
		
		if (this.player.isDead()) {
			if (this.player.getLives() < 1) gameOver();
			this.player.respawn();
		}
		
		this.player.tick();
		this.invaders.tick();
		for (Invader invader: this.invaders.getInvaders()) {
			this.player.getWeapon().collision(invader);
			invader.getWeapon().collision(player);
			if (invader.isDead()) {
				this.score.addScore(invader.getScore());
				this.deadInvaders.add(invader);
			}
			if (this.player.getBounds().intersects(invader.getBounds())) gameOver();
		}
		for (Invader invader: this.deadInvaders) {
			this.invaders.killInvader(invader);
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
		
		if (!this.timer.delayFinished()) {
			g.setFont(new Font("Arial", Font.PLAIN, 40));
			g.setColor(Color.WHITE);
			g.drawString("SPACE INVADERS", GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth("SPACE INVADERS") / 2, 200 + g.getFontMetrics().getHeight());
			g.drawString("MODO CLÁSICO", GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth("MODO CLÁSICO") / 2, 400 + g.getFontMetrics().getHeight());
		}
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
			this.stateManager.pushState(new PauseMenuState(this.stateManager));
		}
	}

	public void keyReleased(int k) {
		this.player.keyReleased(k);
	}
}
