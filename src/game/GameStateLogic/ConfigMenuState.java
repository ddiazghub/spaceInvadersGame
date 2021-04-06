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
public class ConfigMenuState extends GameState {
	
	private String[] options = {"Habilitar sonido", "Volumen global", "", "", "Arriba", "Abajo", "Izquierda", "Derecha", "Seleccionar/Disparar", "Pausa/Volver", "Guardar configuración", "Restaurar predeterminados"};
	private int currentSelection = 0;
	
	public ConfigMenuState(GameStateManager stateManager) {
		super(stateManager);
	}
	
	public void init() {}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
	
	
		Image background = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/background.png");
		Image alien = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/Enemies/alienY.png");
		g.drawImage(background, 0, 0, null);
		
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		for(int i = 0; i < this.options.length; i++) {
			
			boolean selected = i == this.currentSelection;
			if (selected) {
				g.setColor(Color.YELLOW);
				g.drawImage(alien, 110, 165 + i * (g.getFontMetrics().getHeight() + 10) - alien.getHeight(null) / 2, null);
			}	else {
				g.setColor(Color.WHITE);
			}
			g.drawString(this.options[i], 280, 165 + i * (g.getFontMetrics().getHeight() + 10));
		}
		g.setColor(Color.WHITE);
		g.fillRect(200, 100, 20, GamePanel.HEIGHT - 200);
		
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.setColor(new Color(51, 175, 255));
		
		g.drawString("OPCIONES DE AUDIO:", 250, 100 + g.getFontMetrics().getHeight() - 10);
		
		g.drawString("CONTROLES:", 250, 220 + g.getFontMetrics().getHeight());
		
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.setColor(Color.WHITE);
		g.drawString("Volver al menu principal (ESC)", 10, g.getFontMetrics().getHeight() + 10);
	}
	
	public void keyPressed(int k) {
		boolean down = k == KeyEvent.VK_S;
		boolean up = k == KeyEvent.VK_W;
		boolean enter = k == KeyEvent.VK_ENTER;
		boolean esc = k == KeyEvent.VK_ESCAPE;
		if (down) {
			this.currentSelection++;
			if (this.currentSelection >= options.length) {
				this.currentSelection -= options.length;
			} else if (this.currentSelection == 2) {
				this.currentSelection = 4;
			}
		} else if (up) {
			this.currentSelection--;
			if (this.currentSelection < 0) {
				this.currentSelection += options.length;
			} else if (this.currentSelection == 3) {
				this.currentSelection = 1;
			}
		} else if (enter) {
			switch (this.currentSelection) {
				case 0:
					
					break;
				case 1:
					
					break;
				case 2:
					System.exit(0);
			}
		} else if (esc) {
			stateManager.popState();
		}
	}
	
	public void keyReleased(int k) {
		
	}
}
