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
import game.Components.Enemies.InvaderGroup;
import game.Components.Player;
import game.Components.Weapon;
import java.awt.event.KeyEvent;
import game.Components.ScoreCounter;

/**
 *
 * @author david
 */
public class ClassicModeState extends GameState {

	private Player player;
	private InvaderGroup invaders;
	private ScoreCounter score;

	public ClassicModeState(GameStateManager stateManager) {
		super(stateManager);
		init();
	}

	public void init() {
		Weapon base = new Weapon(-1000, 1000, 1, 6, 2, "up", "./src/game/Graphics/Player/disparo.png");
		this.invaders = new InvaderGroup(10, 100, 6, 9);
		this.player = new Player(GamePanel.WIDTH / 2, GamePanel.HEIGHT - 100, 1, 1, base, "./src/game/Graphics/Player/nave2.png");
		this.score = new ScoreCounter();
	}

	public void tick() {
		this.player.tick();
		this.invaders.tick();
	}

	public void render(Graphics g) {

		Image background = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/background.png");
		Image vida = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/vida.png");
		g.drawImage(background, 0, 0, null);

		this.player.render(g);
		this.invaders.render(g);
		
		g.drawImage(vida, 90, 13, null);
		g.drawImage(vida, 120, 13, null);
		g.drawImage(vida, 150, 13, null);
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.setColor(new Color(51, 175, 255));
		g.drawString("Score: " + this.score.getScore(), 450, 10 + g.getFontMetrics().getHeight() - 10);
		g.drawString("Lives:", 10, 10 + g.getFontMetrics().getHeight() - 10);
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
