package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dto.ProfiloUtente;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class PanelHomePageSuperiore extends MyJPanel {

	private static final long serialVersionUID = 1L;
	private FrameHomePage frameChiamante;
	
	public PanelHomePageSuperiore(FrameHomePage parentFrame, ProfiloUtente utenteLoggato) {
		frameChiamante = parentFrame;
		
		this.setBackground(Color.blue);
		this.setPreferredSize(new Dimension(parentFrame.getWidth(), 150));
		this.setMaximumSize(new Dimension(parentFrame.getWidth(), 150));
		
		aggiungiImmagineProfilo(utenteLoggato.getImmagineProfilo());
	}

	private void aggiungiImmagineProfilo(byte[] immagineProfilo) {
		MyJLabel lblBioPic = new MyJLabel();
		lblBioPic.aggiungiImmagineScalata(immagineProfilo, 50, 50, true);
		lblBioPic.setAlignmentX(CENTER_ALIGNMENT);
		lblBioPic.rendiLabelInteragibile();
		
		lblBioPic.setOnMouseEnteredAction(() -> {
			lblBioPic.setCursor(new Cursor(Cursor.HAND_CURSOR));
		});
		
		lblBioPic.setOnMouseExitedAction(() -> {
			lblBioPic.setCursor(new Cursor(Cursor.HAND_CURSOR));
		});
		
		lblBioPic.setOnMouseClickedAction(() -> {
			frameChiamante.getController().passaAFrameProfiloUtente();
		});
		
		this.add(lblBioPic);
	}
}
