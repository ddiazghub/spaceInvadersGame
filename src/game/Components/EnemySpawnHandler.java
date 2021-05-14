/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components;

import java.util.HashMap;

/**
 *
 * @author david
 */
public class EnemySpawnHandler {
	
	private HashMap<String, Integer> enemySpawnRates;
	private HashMap<String, Integer> bossSpawnRates;
	
	public EnemySpawnHandler() {
		this.enemySpawnRates = new HashMap<>();
		this.bossSpawnRates = new HashMap<>();
	}
	
	public void addEnemies() {
		this.enemySpawnRates.put("Red1", 10);
		this.enemySpawnRates.put("Blue1", 7);
		
		this.bossSpawnRates.put("RedBoss", 25);
		this.bossSpawnRates.put("YellowBoss2", 25);
	}
}
