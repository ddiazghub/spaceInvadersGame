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

/**
 *
 * @author david
 */
public class GameOverState extends GameState {

	private String text = "FIN DEL JUEGO";
	private String[] options = {"Intentar nuevamente", "Volver al menu"};
	private int currentSelection = 0;

	public GameOverState(GameStateManager stateManager) {
		super(stateManager);
	}

	public void init() {
	}

	public void tick() {

	}

	public void render(Graphics g) {

		Image background = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/background.png");
		g.drawImage(background, 0, 0, null);

		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 70));
		g.drawString(this.text, GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth(this.text) / 2, 180);
		for (int i = 0; i < options.length; i++) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.PLAIN, 30));

			g.drawString(this.options[i],  GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth(this.options[i]) / 2, 400 + i * 50);
		}
	}

	public void keyPressed(int k) {
		boolean down = k == KeyEvent.VK_S;
		boolean up = k == KeyEvent.VK_W;
		boolean enter = k == KeyEvent.VK_ENTER;
		boolean escape = k == KeyEvent.VK_ESCAPE;
		if (down) {

		} else if (up) {

		} else if (enter) {

		} else if (escape) {
			this.stateManager.popState();
		}
	}

	public void keyReleased(int k) {

	}
}
