/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.GameStateLogic;

import game.Components.Sound;
import game.Core.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 *
 * @author david
 */
public class GMSelectState extends GameState {

	private HashMap<String, Sound> sounds;
	private String[] options = {"Clásico", "No se", "Multijugador"};
	private int currentSelection = 0;

	public GMSelectState(GameStateManager stateManager, Sound music) {
		super(stateManager);
		this.music = music;
		this.sounds = new HashMap<>();
		this.sounds.put("navigation", new Sound("./src/game/Sound/SoundEffects/menu_navigation.wav"));
		this.sounds.put("selection", new Sound("./src/game/Sound/SoundEffects/menu_selection.wav"));
		this.sounds.put("back", new Sound("./src/game/Sound/SoundEffects/menu_back.wav"));
	}

	public void init() {
	}

	public void tick() {

	}

	public void render(Graphics g) {

		Image background = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/background.png");
		Image alien = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/alienSelec.png");
		g.drawImage(background, 0, 0, null);

		g.setColor(new Color(51, 175, 255));
		g.fillRect(150, 300, 10, GamePanel.HEIGHT - 350);
		g.fillRect(150, 300, GamePanel.WIDTH - 300, 10);
		g.fillRect(GamePanel.WIDTH - 150, 300, 10, GamePanel.HEIGHT - 350);
		g.fillRect(150, GamePanel.HEIGHT - 60, GamePanel.WIDTH - 300, 10);
		g.drawRect(150, 300, GamePanel.WIDTH - 300, GamePanel.HEIGHT - 350);

		for (int i = 0; i < this.options.length; i++) {

			g.setFont(new Font("Arial", Font.PLAIN, 30));
			boolean selected = i == this.currentSelection;
			if (selected) {
				g.setColor(Color.YELLOW);
				g.drawImage(alien, GamePanel.WIDTH / 2 - alien.getWidth(null) / 2 - g.getFontMetrics().stringWidth(this.options[i]) / 2 - 40, 415 + i * 50 - alien.getHeight(null) / 2 , null);
			} else {
				g.setColor(Color.WHITE);
			}

			g.drawString(this.options[i], GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth(this.options[i]) / 2, 430 + i * (g.getFontMetrics().getHeight() + 15));
		}
		g.setFont(new Font("Arial", Font.BOLD, 45));
		g.setColor(Color.WHITE);
		g.drawString("MODOS DE JUEGO", GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth("MODOS DE JUEGO") / 2, 370);
		
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.setColor(Color.WHITE);
		g.drawString("Volver al menu principal (ESC)", 10, g.getFontMetrics().getHeight() + 10);
	}

	public void keyPressed(int k) {
		boolean down = k == KeyEvent.VK_S;
		boolean up = k == KeyEvent.VK_W;
		boolean enter = k == KeyEvent.VK_ENTER;
		boolean esc = k == KeyEvent.VK_ESCAPE;
		if (down || up) this.sounds.get("navigation").play(false);
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
			this.sounds.get("selection").play(false);
			this.music.stop();
			switch (this.currentSelection) {
				case 0:
					stateManager.pushState(new ClassicModeState(stateManager));
					break;
				case 1:
					stateManager.pushState(new SurvivalModeState(stateManager));
					break;
				case 2:
					stateManager.pushState(new MultiplayerModeState(stateManager));
			}
		} else if (esc) {
			this.sounds.get("back").play(false);
			stateManager.popState();
		}
	}

	public void keyReleased(int k) {

	}

}
