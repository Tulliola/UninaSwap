package gui;

import java.util.*;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class PanelHomePageLateraleSx extends MyJPanel {

private static final long serialVersionUID = 1L;
	
	private MyJPanel panelChiamante;
	private Controller mainController;
	
	public PanelHomePageLateraleSx(MyJPanel parentPanel, Controller controller) {
		mainController = controller;
		panelChiamante = parentPanel;
	
		this.setPreferredSize(new Dimension(300, panelChiamante.getHeight()));
		this.setBackground(new Color(85, 126, 164));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(LEFT_ALIGNMENT);
		
		MyJLabel lblIlTuoProfiloUtente = new MyJLabel("    Il mio profilo");
		
		aggiungiRigaNelPanel(lblIlTuoProfiloUtente, true, "images/iconaVediProfiloUtente.png", () -> {});
		
		aggiungiRigheAnnunciNelPanel();
		aggiungiRigheOfferteNelPanel();
	}
	
	private void aggiungiRigheAnnunciNelPanel() {
		MyJLabel lblIMieiAnnunci = new MyJLabel("   I miei annunci");
		MyJLabel lblAnnunciDisponibili = new MyJLabel("        Annunci disponibili");
		MyJLabel lblAnnunciUltimati = new MyJLabel("        Annunci andati a buon fine");
		MyJLabel lblAnnunciScaduti = new MyJLabel("        Annunci scaduti");
		MyJLabel lblAnnunciRimossi = new MyJLabel("        Annunci rimossi");
		
		aggiungiRigaNelPanel(lblIMieiAnnunci, false, null, null);
		aggiungiRigaNelPanel(lblAnnunciDisponibili, true, "images/iconaDisponibile.png", () -> {mainController.passaASezioneInFrameProfiloUtente(lblAnnunciDisponibili.getText());});
		aggiungiRigaNelPanel(lblAnnunciUltimati, true, "images/iconaCheck.png", () -> {mainController.passaASezioneInFrameProfiloUtente(lblAnnunciUltimati.getText());});
		aggiungiRigaNelPanel(lblAnnunciScaduti, true, "images/iconaCronometro.png", () -> {mainController.passaASezioneInFrameProfiloUtente(lblAnnunciScaduti.getText());});
		aggiungiRigaNelPanel(lblAnnunciRimossi, true, "images/iconaCross.png", () -> {mainController.passaASezioneInFrameProfiloUtente(lblAnnunciRimossi.getText());});
		
	}
	
	private void aggiungiRigheOfferteNelPanel() {
		MyJLabel lblLeMieOfferte = new MyJLabel("   Le mie offerte");
		MyJLabel lblOfferteAccettate = new MyJLabel("        Offerte accettate");
		MyJLabel lblOfferteInAttesa = new MyJLabel("        Offerte in attesa");
		MyJLabel lblOfferteRifiutate = new MyJLabel("        Offerte rifiutate");
		MyJLabel lblOfferteRitirate = new MyJLabel("        Offerte ritirate");
		MyJLabel lblReportOfferte = new MyJLabel("        Report offerte");
			
		aggiungiRigaNelPanel(lblLeMieOfferte, false, null, null);
		aggiungiRigaNelPanel(lblOfferteAccettate, true, "images/iconaCheck.png", () -> {mainController.passaASezioneInFrameProfiloUtente(lblOfferteAccettate.getText());});
		aggiungiRigaNelPanel(lblOfferteInAttesa, true, "images/iconInAttesa.png", () -> {mainController.passaASezioneInFrameProfiloUtente(lblOfferteInAttesa.getText());});
		aggiungiRigaNelPanel(lblOfferteRifiutate, true, "images/iconaCross.png", () -> {mainController.passaASezioneInFrameProfiloUtente(lblOfferteRifiutate.getText());});
		aggiungiRigaNelPanel(lblOfferteRitirate, true, "images/iconaRitirata.png", () -> {mainController.passaASezioneInFrameProfiloUtente(lblOfferteRitirate.getText());});
		aggiungiRigaNelPanel(lblReportOfferte, true, "images/iconaGrafico.png", () -> {mainController.passaASezioneInFrameProfiloUtente(lblReportOfferte.getText());});
	}
	
	private void aggiungiRigaNelPanel(MyJLabel labelIn, boolean isInteragibile, String pathImage, Runnable azioneSeCliccato) {
		labelIn.setBackground(new Color(85, 126, 164));
		labelIn.setPreferredSize(new Dimension(300, 50));
		labelIn.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		labelIn.setMaximumSize(new Dimension(300, 50));
		labelIn.setAlignmentX(CENTER_ALIGNMENT);
		labelIn.setOpaque(true);
		labelIn.setBorder(new EmptyBorder(0, 5, 0, 0));
		
		if(pathImage != null)
			labelIn.aggiungiImmagineScalata(pathImage, 35, 35, false);
		
		if(isInteragibile)
			labelIn.rendiLabelInteragibile();
		else
			labelIn.setBackground(uninaColor);
			
		
		labelIn.setOnMouseEnteredAction(() -> {
			labelIn.setBackground(uninaColor);
			labelIn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		});
		
		labelIn.setOnMouseExitedAction(() -> {
			labelIn.setBackground(new Color(85, 126, 164));
			labelIn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		});
		
		if(azioneSeCliccato != null)
			labelIn.setOnMouseClickedAction(azioneSeCliccato);
		else
			labelIn.setOnMouseClickedAction(() -> {});
			
		
		this.add(labelIn);
	}
	
}
