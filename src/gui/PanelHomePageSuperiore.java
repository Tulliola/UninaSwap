package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dto.ProfiloUtente;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class PanelHomePageSuperiore extends MyJPanel {

	private static final long serialVersionUID = 1L;
	private MyJPanel panelChiamante;
	
	public PanelHomePageSuperiore(MyJPanel parentPanel, ProfiloUtente utenteLoggato) {
		panelChiamante = parentPanel;
		
		this.setBackground(Color.blue);
		this.setPreferredSize(new Dimension(parentPanel.getWidth(), 150));
		this.setMaximumSize(new Dimension(parentPanel.getWidth(), 150));
		
		aggiungiImmagineProfilo(utenteLoggato.getImmagineProfilo());
	}

	private void aggiungiImmagineProfilo(byte[] immagineProfilo) {
		ImageIcon bioPic = new ImageIcon(immagineProfilo);
		Image resizedBioPic = bioPic.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon bioPicScalata = new ImageIcon(resizedBioPic);
		
		MyJLabel lblBioPic = new MyJLabel();
		
		this.add(lblBioPic);
	}
}
