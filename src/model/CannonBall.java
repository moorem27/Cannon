package model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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
	private int time;
	
	//The Y coordinate offset. Used to draw the ball right side up.
	private int yOffset;
	
	private int ballDimension;
	
	private double tempX;
	private double tempY;
	private double prevX;
	private double prevY;
	private List<Shape> shapes;
	
	
	/**
	 * The CannonBall constructor. Takes in an angle theta (in radians) that will determine
	 * the flight path of the CannonBall. Initializes variables and starts a timer for this
	 * CannonBall object.
	 * @param x is the X coordinate.
	 * @param y is the Y coordinate.
	 * @param theta is the angle that the ball will be fired.
	 */
	public CannonBall(double theta, int time, int yOffset, int velocity) {
		this.yOffset = yOffset;
		this.time = time;
		ballDimension = 10;
		v_0 = velocity;
		g = 9.8;
		theTheta = theta;
		v_x0 = v_0*Math.cos(theTheta);
		v_y0 = v_0*Math.sin(theTheta);
		timer = new Timer(this.time, this);
		shapes = new ArrayList<Shape>();
		timer.start();
	}
	
	
	/**
	 * A method that is used to draw the Cannon Ball onto the JPanel.
	 * @param g is the Graphics object
	 */
	public void draw(Graphics g) {
		//Cannon ball
		g.fillOval((int)ballX - 5, (int)(yOffset - 10 - ballY), ballDimension, ballDimension);

//		Show ball velocity in x direction
//		g.fillOval((int)ballX,(int)yOffset - 10, 10, 10);
		
		//Show ball velocity in y direction
//		g.fillOval(0, (int)(yOffset - ballY), 10, 10);
	}

	/**
	 * Draws an explosion when the current CannonBall hits the ground (y < 0, timer > 0).
	 * @param g is the Graphics object. 
	 * @param in is the BufferedImage (explosion) to be drawn. 
	 */
	public void boom(Graphics g, BufferedImage in) {
		g.drawImage(in, (int)ballX - in.getWidth()/2, (int)(ballY + 650), null);
	}
	
	/**
	 * 
	 * @param g
	 */
	public void showXAndY(Graphics g) {
		
		//Parallel to x plane
		g.drawLine(0,(int) (yOffset - ballY - ballDimension/2), 
				(int) ballX, (int)(yOffset - ballY - ballDimension/2));
		
		//Parallel to y plane
		g.drawLine((int) ballX, yOffset, (int) ballX, (int) (yOffset - ballY));
	}
	
	/**
	 * 
	 * @param g
	 */
	public void showPath(Graphics g) {
		 //Create a GeneralPath object.
        final GeneralPath penPath = new GeneralPath();
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON); 
        //Create a shape that will be set equal to the object so I can
        //add the shape to the array of shapes.
        final Shape penShape = penPath; 
        penPath.moveTo(tempX, yOffset - tempY - ballDimension/2); //Start line at initial x and y
        penPath.lineTo(ballX, yOffset - ballY - ballDimension/2); //Move line to new x and y
        tempX = ballX; //Reset initial x
        tempY = ballY; //Reset initial y
        shapes.add(penShape);
        if(ballY < 0 && time > 0) {
        	penPath.closePath();
        }
        
        //Loop through list of shapes and draw each one to create the path
        for(int i = 0; i < shapes.size(); i++) {
        	((Graphics2D) g).draw(shapes.get(i));
        } 
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
	 * Returns t
	 * @return The current value of t
	 */
	public double getTime() {
		return t;
	}
	
	
	/**
	 * The actionPerformed method is used to re-calculate the ball X and Y coordinates as time passes by.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//Increment t by a decimal amount to smooth the animation
		t = t + .15;
		
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
