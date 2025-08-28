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
	
	private MyJLabel lblIMieiAnnunci = new MyJLabel("   Annunci", Color.white);
	private MyJLabel lblAnnunciDisponibili = new MyJLabel("        Disponibili");
	private MyJLabel lblAnnunciUltimati = new MyJLabel("        Andati a buon fine");
	private MyJLabel lblAnnunciScaduti = new MyJLabel("        Scaduti");
	private MyJLabel lblAnnunciRimossi = new MyJLabel("        Rimossi");
	private MyJLabel lblIlTuoProfiloUtente = new MyJLabel("   Il mio profilo");
	
	
	private MyJLabel lblLeMieOfferte = new MyJLabel("   Offerte", Color.white);
	private MyJLabel lblOfferteAccettate = new MyJLabel("        Accettate");
	private MyJLabel lblOfferteInAttesa = new MyJLabel("        In attesa");
	private MyJLabel lblOfferteRifiutate = new MyJLabel("        Rifiutate");
	private MyJLabel lblOfferteRitirate = new MyJLabel("        Ritirate");
	private MyJLabel lblReportOfferte = new MyJLabel("        Report offerte");
	
	private MyJLabel lblSelezionata = null;

	public PanelBarraLateraleSx(MyJPanel parentPanel, Controller controller, MyJFrame parentFrame, String sezioneScelta) {
		mainController = controller;
		panelChiamante = parentPanel;
	
		this.setPreferredSize(new Dimension(300, panelChiamante.getHeight()));
		this.setBackground(new Color(85, 126, 164));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(LEFT_ALIGNMENT);
		
		
		aggiungiRigaNelPanel(lblIlTuoProfiloUtente, true, "images/iconaProfiloUtente.png");
		lblIlTuoProfiloUtente.setOnMouseClickedAction(() -> {
			mainController.passaASezioneInFrameProfiloUtente("   Il mio profilo");
		});
		
		aggiungiRigheAnnunciNelPanel();
		aggiungiRigheOfferteNelPanel();
		
		comparaConSezioneScelta(sezioneScelta);
	}
	
	private void aggiungiRigheAnnunciNelPanel() {
		
		lblAnnunciDisponibili.setOnMouseClickedAction(() ->{
			mainController.passaASezioneInFrameProfiloUtente("        Annunci disponibili");
		});
		
		lblAnnunciUltimati.setOnMouseClickedAction(() ->{
			mainController.passaASezioneInFrameProfiloUtente("        Annunci andati a buon fine");
		});
		
		lblAnnunciScaduti.setOnMouseClickedAction(() ->{
			mainController.passaASezioneInFrameProfiloUtente("        Annunci scaduti");
		});
		
		lblAnnunciRimossi.setOnMouseClickedAction(() ->{
			mainController.passaASezioneInFrameProfiloUtente("        Annunci rimossi");
		});
		aggiungiRigaNelPanel(lblIMieiAnnunci, false, null);
		aggiungiRigaNelPanel(lblAnnunciUltimati, true, "images/iconaAnnunciUltimati.png");
		aggiungiRigaNelPanel(lblAnnunciDisponibili, true, "images/iconaAnnuncio.png");
		aggiungiRigaNelPanel(lblAnnunciScaduti, true, "images/iconaScaduto.png");
		aggiungiRigaNelPanel(lblAnnunciRimossi, true, "images/iconaCestino.png");
		
	}
	
	private void aggiungiRigheOfferteNelPanel() {
		
		lblOfferteAccettate.setOnMouseClickedAction(()->{
			mainController.passaASezioneInFrameProfiloUtente("        Offerte accettate");
		});
		
		lblOfferteInAttesa.setOnMouseClickedAction(()->{
			mainController.passaASezioneInFrameProfiloUtente("        Offerte in attesa");
			
		});
		
		lblOfferteRifiutate.setOnMouseClickedAction(()->{
			mainController.passaASezioneInFrameProfiloUtente("        Offerte rifiutate");
			
		});
		
		lblOfferteRitirate.setOnMouseClickedAction(()->{
			mainController.passaASezioneInFrameProfiloUtente("        Offerte ritirate");
			
		});
		
		lblReportOfferte.setOnMouseClickedAction(()->{
			mainController.passaASezioneInFrameProfiloUtente("        Report offerte");
			
		});
		
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
			if(labelIn != lblSelezionata)
				labelIn.setBackground(new Color(85, 126, 164));
			labelIn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		});
			
		
		this.add(labelIn);
	}
	
	
	public void resettaFocusLabelsNonCliccate() {
		ArrayList<MyJLabel> jlabels = getLabelsFocusabili();
		
		for(MyJLabel label: jlabels) {
			if(label != lblSelezionata)
				label.setBackground(new Color(85, 126, 164));
		}
	}
	
	private void comparaConSezioneScelta(String sezioneScelta) {
		ArrayList<MyJLabel> jlabels = getLabelsFocusabili();
		
		for(MyJLabel label: jlabels) {
			if(label.getText().equals(sezioneScelta)) {
				label.setBackground(uninaColor);
				lblSelezionata = label;
			}
		}
	}
	
	private ArrayList <MyJLabel> getLabelsFocusabili(){
		ArrayList<MyJLabel> labelsFocusabili = new ArrayList();
		
		labelsFocusabili.add(lblIlTuoProfiloUtente);
		labelsFocusabili.add(lblAnnunciDisponibili);
		labelsFocusabili.add(lblAnnunciUltimati);
		labelsFocusabili.add(lblAnnunciRimossi);
		labelsFocusabili.add(lblAnnunciScaduti);
		labelsFocusabili.add(lblOfferteAccettate);
		labelsFocusabili.add(lblOfferteRifiutate);
		labelsFocusabili.add(lblOfferteInAttesa);
		labelsFocusabili.add(lblOfferteRitirate);
		labelsFocusabili.add(lblReportOfferte);
		
		return labelsFocusabili;
		
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

	public MyJLabel getLblOfferteAccettate() {
		return lblOfferteAccettate;
	}

	public void setLblOfferteAccettate(MyJLabel lblOfferteAccettate) {
		this.lblOfferteAccettate = lblOfferteAccettate;
	}

	public MyJLabel getLblOfferteInAttesa() {
		return lblOfferteInAttesa;
	}

	public void setLblOfferteInAttesa(MyJLabel lblOfferteInAttesa) {
		this.lblOfferteInAttesa = lblOfferteInAttesa;
	}

	public MyJLabel getLblOfferteRifiutate() {
		return lblOfferteRifiutate;
	}

	public void setLblOfferteRifiutate(MyJLabel lblOfferteRifiutate) {
		this.lblOfferteRifiutate = lblOfferteRifiutate;
	}

	public MyJLabel getLblOfferteRitirate() {
		return lblOfferteRitirate;
	}

	public void setLblOfferteRitirate(MyJLabel lblOfferteRitirate) {
		this.lblOfferteRitirate = lblOfferteRitirate;
	}

	public MyJLabel getLblReportOfferte() {
		return lblReportOfferte;
	}

	public void setLblReportOfferte(MyJLabel lblReportOfferte) {
		this.lblReportOfferte = lblReportOfferte;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public MyJPanel getPanelChiamante() {
		return panelChiamante;
	}

	public Controller getMainController() {
		return mainController;
	}


	public MyJLabel getLblIMieiAnnunci() {
		return lblIMieiAnnunci;
	}

	public MyJLabel getLblIlTuoProfiloUtente() {
		return lblIlTuoProfiloUtente;
	}

	public MyJLabel getLblLeMieOfferte() {
		return lblLeMieOfferte;
	}
	
	public MyJLabel getSelectedLabel() {
		return lblSelezionata;
	}

	public void setSelectedLabel(MyJLabel selectedLabel) {
		this.lblSelezionata = selectedLabel;
	}
}
