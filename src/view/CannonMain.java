package view;

import javax.swing.SwingUtilities;

/**
 * A program that simulates two dimensional
 * projectile motion.
 * @author Matthew Moore
 * December 1, 2014
 */
public class CannonMain {
	public static void main(String[] args) {
		 SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                new CannonGUI();
	            }
	        });
	}
}
