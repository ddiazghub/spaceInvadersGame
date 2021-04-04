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
import java.awt.event.KeyEvent;
import game.Components.Player;

/**
 *
 * @author david
 */
public class ClassicModeState extends GameState{

	private String[] options = {"Clásico", "Campaña", "Survival", "Multijugador"};
	private int currentSelection = 0;
	private Player player;

	public ClassicModeState(GameStateManager stateManager) {
		super(stateManager);
		init();
	}

	public void init() {
		this.player = new Player(GamePanel.WIDTH / 2, GamePanel.HEIGHT - 100, 1, 1, "./src/game/Graphics/Player/nave2.png");
	}

	public void tick() {
		this.player.tick();
	}

	public void render(Graphics g) {

		Image background = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/background.png");
		g.drawImage(background, 0, 0, null);

		this.player.render(g);
	}

	public void keyPressed(int k) {
		this.player.keyPressed(k);
	}

	public void keyReleased(int k) {
		this.player.keyReleased(k);
	}
}
