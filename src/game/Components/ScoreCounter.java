/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components;

/**
 *
 * @author david
 */
public class ScoreCounter {
	
	private int score;
	private int multiplier;
	
	public ScoreCounter() {
		this.score = 0;
		this.multiplier = 1;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int getMultiplier() {
		return this.multiplier;
	}
	
	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}
	
	public void addScore(int score) {
		this.score += score;
	}
}
