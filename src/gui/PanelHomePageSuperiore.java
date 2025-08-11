package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dto.ProfiloUtente;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;

public class PanelHomePageSuperiore extends MyJPanel {

	private static final long serialVersionUID = 1L;
	
	private FrameHomePage frameChiamante;
	
	public PanelHomePageSuperiore(FrameHomePage parentFrame, ProfiloUtente utenteLoggato) {
		frameChiamante = parentFrame;
		
		this.setPreferredSize(new Dimension(parentFrame.getWidth(), 45));
		this.setMaximumSize(new Dimension(parentFrame.getWidth(), 45));
		this.setLayout(new BorderLayout());
		
		MyJPanel barraBlu = new MyJPanel();
		barraBlu.setBackground(uninaColor);
		barraBlu.setPreferredSize(new Dimension(parentFrame.getWidth(), 45));
		barraBlu.add(new MyJLabel("UninaSwap", new Font("Ubuntu Sans", Font.BOLD, 25), Color.white));
			
		this.add(barraBlu, BorderLayout.NORTH);
		
	}
	
	
}
