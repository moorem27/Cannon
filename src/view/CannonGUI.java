package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The JFrame that holds the Panel.
 * @author Matthew Moore
 * @version 1.0
 * December 1, 2014
 */
public class CannonGUI extends JFrame{
	
	/** For serialization. */
	private static final long serialVersionUID = 1L;
	
	/** The Panel that all objects will be drawn on.*/
	private Panel panel;
	
	/**
	 * No argument constructor that creates the JFrame. Adds a JPanel() that all images will be drawn on. 
	 */
	public CannonGUI() {
		panel = new Panel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize(1000,750);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().add(panel, BorderLayout.CENTER);
		setVisible(true);
	}	
}

/**
 * The panel that all objects are drawn on.
 * @author Matthew Moore
 * @version 1.0
 */
class Panel extends JPanel implements ActionListener{

	/** For serialization.*/
	private static final long serialVersionUID = 1L;
	
	/** A JLabel that displays the launch angle in the JPanel.*/
	private JLabel thetaLabel;
	
	
	/** The message that will go in the JLabel*/
	private String text;
	
	/** The X and Y coordinates used to calculate theta2 for the cannon movement.*/
	private int moveX;
	private int moveY;
	
	private int velocity;
	/** Theta - the angle that will determine the ball's flight path. */
	private double theta;
	/** Theta that determines where cannon points*/
	private double theta2;

	/** The timer delay*/
	private final int TIME = 25;

	/** The mouse x and y coordinates used to determine the launch angle for the cannon ball. */
	private int mouseX;
	private int mouseY;
	
	/** The list of cannon balls that will be fired. */
	private List<CannonBall> ballList;
	
	//The timer used to 
	private Timer timer;
	
	
	/**
	 * No argument constructor. 
	 */
	public Panel(){
		velocity = 115;
		setLayout(new FlowLayout());
		thetaLabel = new JLabel();
		ballList = new ArrayList<CannonBall>();
		timer = new Timer(TIME, this);
		
		text = "Cannon angle = " + (int)theta + " degrees  ";
		thetaLabel.setText(text);
		thetaLabel.setSize(70,70);
		thetaLabel.setFocusable(false);
		thetaLabel.setLocation(10, 10);
		add(thetaLabel, BorderLayout.NORTH);
		setBackground(Color.WHITE);
		
		//Start timer
		timer.start();
		setVisible(true);
		
		//Capture the mouse motion X and Y coordinates to provide a continuous, accurate
		//display of the current cannon angle.
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {}
			public void mouseMoved(MouseEvent e) {
				moveX = e.getX();
				moveY = e.getY();
				theta2 = Math.atan2((getHeight() - moveY), moveX); 
				text = "Cannon angle = " + (int)Math.rint(theta2*(180/Math.PI)) + " degrees  ";
				thetaLabel.setText(text);
			}
		});
		
		//Capture the mouse click X and Y coordinates to determine initial firing angle
		addMouseListener(new MouseListener() {
			//Fire shot with these coordinates
			public void mouseClicked(MouseEvent e) { 
				mouseX = e.getX();
				mouseY = e.getY();	
				theta = Math.atan2((getHeight() - mouseY), mouseX);   
				thetaLabel.setText(text);
				ballList.add(new CannonBall(theta, TIME, getHeight(), velocity));
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});	
	}
	
	
	/**
	 * Check to see if the ball drops below Y = 0.
	 * Call repaint() to refresh the panel.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < ballList.size(); i++) {
			if(ballList.get(i).getY()< 0) {
				ballList.remove(i);
			} 
		}
		repaint();	
	}
	
	
	/**
	 * Paint the CannonBall objects here.
	 */
	public void paintComponent(Graphics g) {	
		Graphics2D g2d = (Graphics2D) g;
		
		//Draw each cannon ball in the list of cannon balls.
		for(int i = 0; i < ballList.size(); i++)
		{
			ballList.get(i).draw(g);
		}
		
		int x = 0; //Cannon starting x coordinate
		int y = getHeight(); //Cannon starting y coordinate
		
		//Set hypotenuse (cannon) to a set length
		int hypotenuse = 50; 
		
		//Change opposite and adjacent based on theta2
		int adjacent = (int) (Math.cos(theta2) * hypotenuse); //Use as ending x coordinate of cannon
		int opposite = (int) (Math.sin(theta2) * hypotenuse); //Use as ending y coordinate of cannon
		
		g2d.setStroke(new BasicStroke(20)); //Make the line thick so it looks like a cannon
		g2d.drawLine(x, y, adjacent, y - opposite ); //Draw a "cannon" (line) that is pointing towards the mouse.
	}	
}
