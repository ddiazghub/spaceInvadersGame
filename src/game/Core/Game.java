package game.Core;

import java.awt.BorderLayout;
import java.awt.Canvas;
import javax.swing.JFrame;

public class Game extends Canvas{
	
	
	public static void main(String[] args) {
		
		JFrame gameWindow = new JFrame("Space invaders");

		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setResizable(false);
		gameWindow.setLayout(new BorderLayout());
		gameWindow.add(new GamePanel(), BorderLayout.CENTER);
		gameWindow.pack();
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setVisible(true);
	}
}
