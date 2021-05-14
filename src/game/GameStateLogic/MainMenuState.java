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
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.event.KeyEvent;
import game.Components.Sound;
import java.util.HashMap;

/**
 *
 * @author david
 */
public class MainMenuState extends GameState {
	
	private HashMap<String, Sound> sounds;
	private String[] options = {"Jugar", "Configuración", "Acerca del juego", "Cerrar juego"};
	private int currentSelection;
	
	public MainMenuState(GameStateManager stateManager) {
		super(stateManager);
		this.currentSelection = 0;
		music = new Sound("./src/game/Sound/Music/menu.wav");
		music.play(true);
		this.sounds = new HashMap<>();
		this.sounds.put("navigation", new Sound("./src/game/Sound/SoundEffects/menu_navigation.wav"));
		this.sounds.put("selection", new Sound("./src/game/Sound/SoundEffects/menu_selection.wav"));
	}
	
	public void init() {
	}
	
	public void tick() {
		if (!this.music.isPlaying()) this.music.play(true);
	}
	
	public void render(Graphics g) {
		
		Image background = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/background.png");
		Image logo = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/logo.png");
		Image alien = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/alienSelec.png");
		
	
		//Creando el fondo
		g.drawImage(background, 0, 0, null);
		
		
		g.drawImage(logo, GamePanel.WIDTH / 2 - logo.getWidth(null) / 2, -110, null);
	
		g.setFont(new Font("Arial", Font.PLAIN, 40));
		
		for(int i = 0; i < this.options.length; i++) {
			boolean selected = i == this.currentSelection;
			if (selected) {
				g.setColor(Color.YELLOW);
				g.drawImage(alien, GamePanel.WIDTH / 2 - alien.getWidth(null) / 2 - g.getFontMetrics().stringWidth(this.options[i]) / 2 - 35, 375 + i * 50 - alien.getHeight(null) / 2 , null);
			}	else {
				g.setColor(Color.WHITE);
			}
			g.drawString(this.options[i], GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth(this.options[i]) / 2, 390 + i * 50);
		}
	}
	
	public void keyPressed(int k) {
		boolean down = k == KeyEvent.VK_S;
		boolean up = k == KeyEvent.VK_W;
		boolean enter = k == KeyEvent.VK_ENTER;
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
			switch (this.currentSelection) {
				case 0:
					stateManager.pushState(new GMSelectState(stateManager, music));
					break;
				case 1:
					stateManager.pushState(new ConfigMenuState(stateManager, music));
					break;
				case 2:
					stateManager.pushState(new AboutState(stateManager, music));
					break;
				case 3:
					System.exit(0);
			}
		}
	}
	
	public void keyReleased(int k) {
		
	}
}
