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
	private String gamemode;

	public GameOverState(GameStateManager stateManager, String gamemode) {
		super(stateManager);
		this.gamemode = gamemode;
	}

	public void init() {
	}

	public void tick() {

	}

	public void render(Graphics g) {

		Image background = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/background.png");
		Image alien = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/alienSelec.png");
		g.drawImage(background, 0, 0, null);

		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 70));
		g.drawString(this.text, GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth(this.text) / 2, 180);
		
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		for (int i = 0; i < options.length; i++) {
			boolean selected = i == this.currentSelection;
			if (selected) {
				g.setColor(Color.YELLOW);
				g.drawImage(alien, GamePanel.WIDTH / 2 - alien.getWidth(null) / 2 - g.getFontMetrics().stringWidth(this.options[i]) / 2 - 40, 385 - alien.getHeight(null) / 2 + i * 50, null);
			}	else {
				g.setColor(Color.WHITE);
			}

			g.drawString(this.options[i],  GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth(this.options[i]) / 2, 400 + i * 50);
		}
	}

	public void keyPressed(int k) {
		boolean down = k == KeyEvent.VK_S;
		boolean up = k == KeyEvent.VK_W;
		boolean enter = k == KeyEvent.VK_ENTER;
		boolean escape = k == KeyEvent.VK_ESCAPE;
		if (down) {
			this.currentSelection++;
			if (this.currentSelection >= options.length) {
				this.currentSelection -= options.length;
			}
		} else if (up) {
			this.currentSelection--;
			if (this.currentSelection < 0) {
				this.currentSelection += options.length;
			}
		} else if (enter) {
			if (currentSelection == 0) {
				this.stateManager.popState();
				this.stateManager.popState();
				if (this.gamemode.equals("classic")) this.stateManager.pushState(new ClassicModeState(stateManager));
				if (this.gamemode.equals("survival")) this.stateManager.pushState(new SurvivalModeState(stateManager));
			}
			if (currentSelection == 1) {
				while (this.stateManager.size() > 1) {
					this.stateManager.popState();
				}
			}
		} else if (escape) {
			
		}
	}

	public void keyReleased(int k) {

	}
}
