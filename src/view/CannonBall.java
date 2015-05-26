package view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * The Cannon ball object that will be fired by the Cannon.
 * @author Matthew Moore
 * @version 1.0
 * December 1, 2014
 */
public class CannonBall implements ActionListener {
	
	// The initial velocity in the x direction: v_x0 = v * cos(theta)
	private double v_x0; 
	
	// The initial velocity in the y direction: v_y0 = v * sin(theta)
	private double v_y0; 

	//The cannon ball X coordinate
	private double ballX;
	
	//The cannon ball Y coordinate
	private double ballY;
	
	//The initial velocity
	private double v_0;
	
	//Gravity as a constant 9.8 m/(s^2)
	private double g;
	
	//Theta - the angle based on X and Y coordinates grabbed from the mouse click 
	//and the lower left hand portion of the JFrame
	private double theTheta;
	
	//The timer that is started when a ball is created.
	private Timer timer;
	
	//The time, t, to be used to calculate position as a function of time.
	private double t;
	
	//The timer delay that the CannonGUI passes in.
	private int theTime;
	
	//The Y coordinate offset. Used to draw the ball right side up.
	private int yOffset;
	
	/**
	 * The CannonBall constructor. Takes in an angle theta (in radians) that will determine
	 * the flight path of the mushroom. Initializes variables and starts a timer for this
	 * CannonBall object.
	 * @param x is the X coordinate.
	 * @param y is the Y coordinate.
	 * @param theta is the angle that the ball will be fired.
	 */
	public CannonBall(double theta, int time, int yOffset, int velocity) {
		this.yOffset = yOffset;
		theTime = time;
		v_0 = velocity;
		g = 9.8;
		theTheta = theta;
		v_x0 = v_0*Math.cos(theTheta);
		v_y0 = v_0*Math.sin(theTheta);
		timer = new Timer(theTime, this);
		timer.start();
	}
	
	
	/**
	 * A method that is used to draw the Cannon Ball onto the JPanel.
	 * @param g is the Graphics object
	 */
	public void draw(Graphics g) {
		//Cannon ball
		g.fillOval((int)ballX - 5, (int)(yOffset - 10 - ballY), 10, 10);

//		Show ball velocity in x direction
//		g.fillOval((int)ballX,(int)yOffset - 10, 10, 10);
		
		//Show ball velocity in y direction
//		g.fillOval(0, (int)(yOffset - ballY), 10, 10);
	}
	
	
	/**
	 * A method that returns the Y coordinate of the ball.
	 * @return Current Y coordinate of the ball.
	 */
	public double getY() {
		return ballY;
	}
	
	
	/**
	 * A method that returns the X coordinate of the ball.
	 * @return Current X coordinate of the ball.
	 */
	public double getX() {
		return ballX;
	}
	
	
	/**
	 * The actionPerformed method is used to re-calculate the ball X and Y coordinates as time passes by.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//Increment t by a decimal amount to smooth the animation
		t = t + .3;
		
		//x = x_0 + vx0 * t. Here x_0 is implied to be 0 since we are firing from the ground.
		ballX = (v_x0 * t); 
		
		//y = y_0 + (vy0 * t) - (.5 * g * (t^2)). y_0 is also implied to be 0
		ballY = ((v_y0 * t) - (.5 * g * (t*t))); 
		
		//Stop the timer if the ball passes below this Y coordinate
		if(ballY < 0) {
			timer.stop();
		}
	}
} //End CannonBall.java
