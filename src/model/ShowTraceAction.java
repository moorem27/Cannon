package model;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import view.Panel;

/**
 * 
 * @author Matthew Moore
 * May 28, 2015
 */
public class ShowTraceAction extends AbstractAction {
	
	/** */
	private static final long serialVersionUID = 1L;
	private Panel panel;
	private int count = 0;
	public ShowTraceAction(final Panel panel) {
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		count++;
		if(count%2 != 0) {
			panel.traceButtonClicked(true);
		} else {
			panel.traceButtonClicked(false);
		}
		if(count == 2) {
			count = 0;
		}
		System.out.println(" trace count = " + count);
	}
}
