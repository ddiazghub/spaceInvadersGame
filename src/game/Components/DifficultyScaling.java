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
import java.lang.Math;

public class DifficultyScaling {
	
	private boolean scaleByTime; // 0 makes this scale by level;
	private String scalingFunction;
	private long count = 0;
	
	public DifficultyScaling(boolean scaleByTime, String scalingFunction) {
		
		this.scalingFunction = scalingFunction;
		this.scaleByTime = scaleByTime;
		if (this.scaleByTime) this.count = System.currentTimeMillis();
		
	}
	
	public double newLevel() {
		this.count++;
		return this.applyScaling();
	}
	
	public double getCurrentMultiplier() {
		return this.applyScaling();
	}
	
	public double applyScaling() {
		double multiplier = 1;
		if (this.scaleByTime) {
			
		} else {
			switch (this.scalingFunction) {
				case "linear":
					multiplier = 1 + 0.3 * this.count;
					break;
				case "log":
					multiplier = 1 + 1 * Math.log(this.count);
					break;
				case "sigmoid":

					break;
			}
		}
		return multiplier;
	}
}
