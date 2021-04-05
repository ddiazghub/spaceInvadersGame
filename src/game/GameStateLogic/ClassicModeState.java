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
import game.Components.Enemy;
import game.Components.Weapon;
import java.awt.event.KeyEvent;


/**
 *
 * @author david
 */
public class ClassicModeState extends GameState{

	private String[] options = {"Clásico", "Campaña", "Survival", "Multijugador"};
	private int currentSelection = 0;
	private Player player;
	private InvaderGroup invaders;

	public ClassicModeState(GameStateManager stateManager) {
		super(stateManager);
		init();
	}

	public void init() {
		Weapon base = new Weapon(-1000, 1000, 1, 6, 5, "up", "./src/game/Graphics/Player/disparo.png");
		this.invaders = new InvaderGroup(10, 60, 5, 8);
		this.player = new Player(GamePanel.WIDTH / 2, GamePanel.HEIGHT - 100, 1, 1, base, "./src/game/Graphics/Player/nave2.png");
	}

	public void tick() {
		this.player.tick();
		this.invaders.tick();
	}

	public void render(Graphics g) {

		Image background = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/background.png");
		g.drawImage(background, 0, 0, null);

		this.player.render(g);
		this.invaders.render(g);
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
