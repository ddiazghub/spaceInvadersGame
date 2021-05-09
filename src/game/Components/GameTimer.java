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
public class GameTimer {
	
	private long duration;
	private long startTime;
	private long elapsedTime;

	public GameTimer() {
		duration = 0;
		startTime = 0;
		elapsedTime = 0;
	}
	
	public void newDelay(long duration) {
		this.duration = duration;
		this.startTime = System.currentTimeMillis();
	}
	
	public void newDelay(double duration) {
		newDelay((long) duration);
	}
	
	public void endDelay() {
		this.startTime -= duration;
	}
	
	public boolean delayFinished() {
		return System.currentTimeMillis() - this.startTime >= this.duration;
	}
	
	public void pause() {
		elapsedTime = System.currentTimeMillis() - startTime;
	}
	
	public void resume() {
		startTime = System.currentTimeMillis() - elapsedTime;
	}
	
	public void reset() {
		newDelay(this.duration);
	}
}
