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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.CannonBall;


/**
 * 
 * @author Matthew Moore
 * May 28, 2015
 */
public class Panel extends JPanel implements ActionListener{

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
	
	private boolean impact;
	
	private boolean fired;
	
	private boolean traceClicked;
	private boolean pathClicked;

	private BufferedImage in;
	private BufferedImage fire;
	
	
	
	/**
	 * No argument constructor. 
	 */
	public Panel(){
		impact = false;
		fired = false;
		velocity = 115;
		setLayout(new FlowLayout());
		thetaLabel = new JLabel();
		ballList = new ArrayList<CannonBall>();
		timer = new Timer(TIME, this);
		File blast = new File("img/fire.png");
		File img = new File("img/flame.png");
		try {
			in = ImageIO.read(Panel.class.getResourceAsStream("/img/flame.png"));
			fire = ImageIO.read(Panel.class.getResourceAsStream("/img/fire.png"));
//			in = ImageIO.read(img);
//			fire = ImageIO.read(blast);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("You fucked up, Matt");
		}
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
				fired = true;
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
	 * 
	 * @param clicked
	 * @return
	 */
	public boolean traceButtonClicked(boolean clicked) {
		if(clicked) {
			traceClicked = true;
		} else {
			traceClicked = false;
		}
		return traceClicked;
	}
	
	
	/**
	 * 
	 * @param clicked
	 * @return
	 */
	public boolean pathButtonClicked(boolean clicked) {
		if(clicked) {
			pathClicked = true;
		} else {
			pathClicked = false;
		}
		return pathClicked;
	}
	
	/**
	 * Check to see if the ball drops below Y = 0.
	 * Call repaint() to refresh the panel.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		for(int i = 0; i < ballList.size(); i++) {
			if(impact && ballList.get(i).getY() < 0) {
				ballList.remove(i);
				impact = false;
			} 
		}	
	}
	
	
	/**
	 * Paint the CannonBall objects here.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		//Draw each cannon ball in the list of cannon balls.
		for(int i = 0; i < ballList.size(); i++) {
			if(ballList.get(i).getTime() > 0 && ballList.get(i).getY() < 0) {
//				ballList.get(i).drawBoom(g);
				ballList.get(i).boom(g2d, in);
				impact = true;
			}
			ballList.get(i).draw(g);
			if(pathClicked) {
				ballList.get(i).showPath(g);
			}
			if(traceClicked) {
				ballList.get(i).showXAndY(g);
			}
		}
		
		int x = 0; //Cannon starting x coordinate
		int y = getHeight(); //Cannon starting y coordinate
		
		//Set hypotenuse (cannon) to a set length
		int hypotenuse = 50; 
		
		//Change opposite and adjacent based on theta2
		int adjacent = (int) (Math.cos(theta2) * hypotenuse); //Use as ending x coordinate of cannon
		int opposite = (int) (Math.sin(theta2) * hypotenuse); //Use as ending y coordinate of cannon
		
		if(fired) {
			g2d.drawImage(fire, (adjacent + 10 - fire.getWidth()/2), ((y - opposite - 15) - fire.getHeight()/2), null);
			fired = false;
		}
		g2d.setStroke(new BasicStroke(20)); //Make the line thick so it looks like a cannon
		g2d.drawLine(x, y, adjacent, y - opposite ); //Draw a "cannon" (line) that is pointing towards the mouse.
	}	
}

