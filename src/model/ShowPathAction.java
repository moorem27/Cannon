package model;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import view.Panel;

public class ShowPathAction extends AbstractAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int count = 0;
	private Panel panel;
	
	public ShowPathAction(Panel panel) {
		this.panel = panel;
	}

	/**
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		count++;
		if(count%2 != 0) {
			panel.pathButtonClicked(true);
		} else {
			panel.pathButtonClicked(false);
		}
		if(count == 2) {
			count = 0;
		}
		System.out.println(" path count = " + count);
	}

}
