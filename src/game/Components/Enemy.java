/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Components;

import java.awt.Toolkit;

/**
 *
 * @author david
 */
public class Enemy extends Character {
	
	public Enemy(int x, int y, int hp, int movementSpeed, String imagePath) {
		
		super(x, y, hp, movementSpeed, imagePath);
		
	} 
	
	public void tick() {
		
	}
}
