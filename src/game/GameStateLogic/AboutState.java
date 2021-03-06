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
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 *
 * @author david
 */
public class AboutState extends GameState {

	private HashMap<String, Sound> sounds;
	private String[] text = {"Bienvenido al juego space invaders. Destruye", "la mayor cantidad de aliens que puedas para", "obtener la mejor puntuación. Desarrollado por:", "- David Díaz.", "- Katy Díaz.", "----------------"};
	private int currentSelection = 0;

	public AboutState(GameStateManager stateManager, Sound music) {
		super(stateManager);
		this.music = music;
		this.sounds = new HashMap<>();
		this.sounds.put("back", new Sound("./src/game/Sound/SoundEffects/menu_back.wav"));
	}

	public void init() {
	}

	public void tick() {

	}

	public void render(Graphics g) {

		Image background = Toolkit.getDefaultToolkit().getImage("./src/game/Graphics/background.png");
		g.drawImage(background, 0, 0, null);

		for (int i = 0; i < text.length; i++) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.PLAIN, 30));
			if (i < 3) {
				g.drawString(this.text[i], GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth(this.text[i]) / 2, 180 + i * 60);
			} else {
				g.drawString(this.text[i], GamePanel.WIDTH / 2 - 3 * g.getFontMetrics().stringWidth(this.text[5]) / 2, 180 + i * 60);
			}
		}
		
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.setColor(Color.WHITE);
		g.drawString("Volver al menu principal (ESC)", 10, g.getFontMetrics().getHeight() + 10);
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
			this.sounds.get("back").play(false);
			this.stateManager.popState();
		}
	}

	public void keyReleased(int k) {

	}
}
