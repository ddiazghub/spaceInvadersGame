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

import java.awt.Graphics;
import java.util.Stack;

public class GameStateManager {
	
	private Stack<GameState> states;
	
	public GameStateManager() {
		this.states = new Stack<GameState>();
		this.pushState(new MainMenuState(this));
	}
	
	public void tick() {
		this.states.peek().tick();
	}
	
	public void render(Graphics g) {
		this.states.peek().render(g);
	}
	
	public void keyPressed(int k) {
		this.states.peek().keyPressed(k);
	}
	
	public void keyReleased(int k) {
		this.states.peek().keyReleased(k);
	}
	
	public void pushState(GameState state) {
		this.states.push(state);
	}
	
	public GameState currentState(GameState state) {
		return this.states.peek();
	}
	
	public void popState() {
		this.states.pop();
	}
}
