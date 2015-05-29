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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.CannonBall;
import model.ShowPathAction;
import model.ShowTraceAction;

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
	
	private JMenuBar menuBar;
	
	private JMenu file;
	private JMenu options;
	private JMenuItem showCoordinates;
	private JMenuItem showPath;
	
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
		menuBar = new JMenuBar();
		file = new JMenu("File");
		options = new JMenu("Options");
		showCoordinates = new JMenuItem("Show Coordinates");
		showPath = new JMenuItem("Show Path");
		showCoordinates.addActionListener(new ShowTraceAction(panel));
		showPath.addActionListener(new ShowPathAction(panel));
		options.add(showCoordinates);
		options.add(showPath);
		menuBar.add(file);
		menuBar.add(options);
		setJMenuBar(menuBar);
		setVisible(true);
	}	
}
