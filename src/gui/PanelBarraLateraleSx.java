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
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class PanelBarraLateraleSx extends MyJPanel {

private static final long serialVersionUID = 1L;
	
	private MyJPanel panelChiamante;
	private Controller mainController;
	
	MyJLabel lblTornaAHomePage = new MyJLabel("   Torna alla home page");
	MyJLabel lblIMieiAnnunci = new MyJLabel("   I miei annunci");
	MyJLabel lblAnnunciDisponibili = new MyJLabel("        Annunci disponibili");
	MyJLabel lblAnnunciUltimati = new MyJLabel("        Annunci andati a buon fine");
	MyJLabel lblAnnunciScaduti = new MyJLabel("        Annunci scaduti");
	MyJLabel lblAnnunciRimossi = new MyJLabel("        Annunci rimossi");
	MyJLabel lblIlTuoProfiloUtente = new MyJLabel("   Il mio profilo");
	
	public PanelBarraLateraleSx(MyJPanel parentPanel, Controller controller, MyJFrame parentFrame) {
		mainController = controller;
		panelChiamante = parentPanel;
	
		this.setPreferredSize(new Dimension(300, panelChiamante.getHeight()));
		this.setBackground(new Color(85, 126, 164));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(LEFT_ALIGNMENT);
		
		
		aggiungiRigaNelPanel(lblIlTuoProfiloUtente, true, "images/iconaProfiloUtente.png");
		
		aggiungiRigheAnnunciNelPanel();
		aggiungiRigheOfferteNelPanel();
	}
	
	private void aggiungiRigheAnnunciNelPanel() {
		
		aggiungiRigaNelPanel(lblIMieiAnnunci, false, null);
		aggiungiRigaNelPanel(lblAnnunciDisponibili, true, "images/iconaAnnuncio.png");
		aggiungiRigaNelPanel(lblAnnunciUltimati, true, "images/iconaAnnunciUltimati.png");
		aggiungiRigaNelPanel(lblAnnunciScaduti, true, "images/iconaScaduto.png");
		aggiungiRigaNelPanel(lblAnnunciRimossi, true, "images/iconaCestino.png");
		
	}
	
	//TODO Una volta fatti i panel per le offerte
	private void aggiungiRigheOfferteNelPanel() {
		MyJLabel lblLeMieOfferte = new MyJLabel("   Le mie offerte");
		MyJLabel lblOfferteAccettate = new MyJLabel("        Offerte accettate");
		MyJLabel lblOfferteInAttesa = new MyJLabel("        Offerte in attesa");
		MyJLabel lblOfferteRifiutate = new MyJLabel("        Offerte rifiutate");
		MyJLabel lblOfferteRitirate = new MyJLabel("        Offerte ritirate");
		MyJLabel lblReportOfferte = new MyJLabel("        Report offerte");
			
		aggiungiRigaNelPanel(lblLeMieOfferte, false, null);
		aggiungiRigaNelPanel(lblOfferteAccettate, true, "images/iconaOffertaAccettata.png");
		aggiungiRigaNelPanel(lblOfferteInAttesa, true, "images/iconaInAttesa.png");
		aggiungiRigaNelPanel(lblOfferteRifiutate, true, "images/iconaCross.png");
		aggiungiRigaNelPanel(lblOfferteRitirate, true, "images/iconaCancellato.png");
		aggiungiRigaNelPanel(lblReportOfferte, true, "images/iconaReport.png");
	}
	
	public void aggiungiRigaNelPanel(MyJLabel labelIn, boolean isInteragibile, String pathImage) {
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
		
//		if(azioneSeCliccato != null)
//			labelIn.setOnMouseClickedAction(azioneSeCliccato);
//		else
//			labelIn.setOnMouseClickedAction(() -> {});
			
		
		this.add(labelIn);
	}
	
	public MyJLabel getLblAnnunciDisponibili() {
		return lblAnnunciDisponibili;
	}

	public void setLblAnnunciDisponibili(MyJLabel lblAnnunciDisponibili) {
		this.lblAnnunciDisponibili = lblAnnunciDisponibili;
	}

	public MyJLabel getLblAnnunciUltimati() {
		return lblAnnunciUltimati;
	}

	public void setLblAnnunciUltimati(MyJLabel lblAnnunciUltimati) {
		this.lblAnnunciUltimati = lblAnnunciUltimati;
	}

	public MyJLabel getLblAnnunciScaduti() {
		return lblAnnunciScaduti;
	}

	public void setLblAnnunciScaduti(MyJLabel lblAnnunciScaduti) {
		this.lblAnnunciScaduti = lblAnnunciScaduti;
	}

	public MyJLabel getLblAnnunciRimossi() {
		return lblAnnunciRimossi;
	}

	public void setLblAnnunciRimossi(MyJLabel lblAnnunciRimossi) {
		this.lblAnnunciRimossi = lblAnnunciRimossi;
	}
	
	public MyJLabel getLblIlMioProfilo() {
		return lblIlTuoProfiloUtente;
	}
}
