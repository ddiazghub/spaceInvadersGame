/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.GameStateLogic;

/**
 *
 * @author david
 */
import game.Components.Sound;
import java.awt.Graphics;

public abstract class GameState {
	
	protected Sound music;
	protected GameStateManager stateManager;
	
	public GameState(GameStateManager stateManager) {
		this.stateManager = stateManager;
		this.init();
	}
	
	public abstract void init();
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
}
