/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Core;

import game.GameStateLogic.GameStateManager;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;

/**
 *
 * @author david
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 650;
	
	private Thread mainThread;
	private boolean isRunning = false;
	
	private final int fps = 60;
	private final long targetTime = 1000 / this.fps;
	
	private long startingTime;
	private long elapsedTime;
	
	private GameStateManager stateManager;
			
	public GamePanel () {
		this.setPreferredSize(new Dimension(GamePanel.WIDTH, GamePanel.HEIGHT));
		addKeyListener(this);
		setFocusable(true);
		this.start();
	}
	
	private void start() {
		this.isRunning = true;
		this.startingTime = System.currentTimeMillis();
		this.elapsedTime = 0;
		this.mainThread = new Thread(this);
		this.mainThread.start();
	}
	
	public void run() {
		long lastRender, elapsed, wait;
		this.stateManager = new GameStateManager();
		while (isRunning) {
			lastRender = System.nanoTime();
			
			this.elapsedTime = System.currentTimeMillis() - this.startingTime;
			elapsed = System.nanoTime() - lastRender;
			wait = targetTime - elapsed / 1000000;
			
			tick(this.elapsedTime);
			repaint();
			
			if (wait < 0) {
				wait = 5;
			}
			
			try {
				Thread.sleep(wait);
			}	catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void tick(long elapsedTime) {
		// System.out.println("Running, Elapsed Time: " + elapsedTime);
		this.stateManager.tick();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.clearRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		this.stateManager.render(g);
	}
	
	public int[] getCenter() {
		int x = this.WIDTH / 2;
		int y = this.HEIGHT / 2;
		int[] center = {x, y};
		return center;
	}
	
	public void keyPressed (KeyEvent e) {
		this.stateManager.keyPressed(e.getKeyCode());
	}
	
	public void keyReleased (KeyEvent e) {
		this.stateManager.keyReleased(e.getKeyCode());
	}
	
	public void keyTyped (KeyEvent e) {
		
	}
}
