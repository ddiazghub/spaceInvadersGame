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

/**
 *
 * @author david
 */
public class MainMenuState extends GameState {
	
	private String[] options = {"Jugar", "Configuración", "Acerca del juego", "Cerrar juego"};
	private int currentSelection = 0;
	
	public MainMenuState(GameStateManager stateManager) {
		super(stateManager);
	}
	
	public void init() {}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
		Image background = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/background.png");
		Image logo = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/logo.png");
	
		//Creando el fondo
		g.drawImage(background, 0, 0, null);
		
		
		g.drawImage(logo, GamePanel.WIDTH / 2 - logo.getWidth(null) / 2, -110, null);
	
		
		for(int i = 0; i < this.options.length; i++) {
			
			boolean selected = i == this.currentSelection;
			if (selected) {
				g.setColor(Color.GREEN);
			}	else {
				g.setColor(Color.WHITE);
			}
			
			g.setFont(new Font("Arial", Font.PLAIN, 40));
			g.drawString(this.options[i], GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth(this.options[i]) / 2, 390 + i * 50);
		}
	}
	
	public void keyPressed(int k) {
		boolean down = k == KeyEvent.VK_S;
		boolean up = k == KeyEvent.VK_W;
		boolean enter = k == KeyEvent.VK_ENTER;
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
			switch (this.currentSelection) {
				case 0:
					stateManager.pushState(new GMSelectState(stateManager));
					break;
				case 1:
					stateManager.pushState(new ConfigMenuState(stateManager));
					break;
				case 2:
					stateManager.pushState(new AboutState(stateManager));
					break;
				case 3:
					System.exit(0);
			}
		}
	}
	
	public void keyReleased(int k) {
		
	}
}
