package gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import utilities.MyJPanel;

public class PanelHomePageAnnunci extends JPanel{

	private static final long serialVersionUID = 1L;

	public PanelHomePageAnnunci() {
		this.setBackground(Color.LIGHT_GRAY);
		this.setPreferredSize(new Dimension(300, 300));
		JScrollPane scrollPanel = new JScrollPane(new JScrollBar());
		
		this.add(scrollPanel);
		
	}

}
