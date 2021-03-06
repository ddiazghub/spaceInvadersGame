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
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public interface CharacterEntity {
	
	public void tick();
	public void render(Graphics g);
	public int getX();
	public int getY();
	public int getWidth();
	public int getHeight();
	public double getSpeedX();
	public double getSpeedY();
	public Rectangle getBounds();
	public Image getSprite();
	public void setSprite(Image sprite);
	public void hurt(int damage);
	public void heal(int hp);
	public boolean isVulnerable();
	public boolean isDead();
	public void pauseTimers();
	public void resumeTimers();
	public Weapon getWeapon();
	public int getScore();
	public void stop(long durationm);
	public void stop();
	public void collision(CharacterEntity entity);
	public int getHp();
	public int getMaxHp();
	public void maxHpMultiplier(double multiplier);
	public void damageMultiplier(double multiplier);
	
}
