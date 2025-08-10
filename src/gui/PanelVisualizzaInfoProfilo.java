package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import utilities.*;

public class PanelVisualizzaInfoProfilo extends MyJPanel {

	private static final long serialVersionUID = 1L;
	
	private MyJPanel panelChiamante;
	
	private MyJLabel sezioneAttuale;
	private MyJLabel sezionePrecedente;
	
	public PanelVisualizzaInfoProfilo(MyJPanel parentPanel) {
		panelChiamante = parentPanel;
	
		this.setPreferredSize(new Dimension(300, panelChiamante.getHeight()));
		this.setBackground(new Color(85, 126, 164));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(LEFT_ALIGNMENT);
		
		MyJLabel lblTornaAllaHome = new MyJLabel("   Torna alla home page");
		MyJLabel lblIlTuoProfiloUtente = new MyJLabel("    Il tuo profilo utente");
		
		aggiungiRigaNelPanel(lblTornaAllaHome, true, "images/iconaHome.png");
		aggiungiRigaNelPanel(lblIlTuoProfiloUtente, true, "images/iconaVediProfiloUtente.png");
		lblIlTuoProfiloUtente.setBackground(uninaColor);
		sezioneAttuale = lblIlTuoProfiloUtente;
		sezionePrecedente = null;
		
		aggiungiRigheAnnunciNelPanel();
		aggiungiRigheOfferteNelPanel();
	}
	
	private void aggiungiRigheAnnunciNelPanel() {
		MyJLabel lblIMieiAnnunci = new MyJLabel("   I miei annunci");
		MyJLabel lblAnnunciDisponibili = new MyJLabel("        Annunci disponibili");
		MyJLabel lblAnnunciUltimati = new MyJLabel("        Annunci andati a buon fine");
		MyJLabel lblAnnunciScaduti = new MyJLabel("        Annunci scaduti");
		MyJLabel lblAnnunciRimossi = new MyJLabel("        Annunci rimossi");
		
		aggiungiRigaNelPanel(lblIMieiAnnunci, false, null);
		aggiungiRigaNelPanel(lblAnnunciDisponibili, true, "images/iconaDisponibile.png");
		aggiungiRigaNelPanel(lblAnnunciUltimati, true, "images/iconaCheck.png");
		aggiungiRigaNelPanel(lblAnnunciScaduti, true, "images/iconaCronometro.png");
		aggiungiRigaNelPanel(lblAnnunciRimossi, true, "images/iconaCross.png");
	}
	
	private void aggiungiRigheOfferteNelPanel() {
		MyJLabel lblLeMieOfferte = new MyJLabel("   Le mie offerte");
		MyJLabel lblOfferteAccettate = new MyJLabel("        Offerte accettate");
		MyJLabel lblOfferteInAttesa = new MyJLabel("        Offerte in attesa");
		MyJLabel lblOfferteRifiutate = new MyJLabel("        Offerte rifiutate");
		MyJLabel lblOfferteRitirate = new MyJLabel("        Offerte ritirate");
		MyJLabel lblReportOfferte = new MyJLabel("        Report offerte");
			
		aggiungiRigaNelPanel(lblLeMieOfferte, false, null);
		aggiungiRigaNelPanel(lblOfferteAccettate, true, "images/iconaCheck.png");
		aggiungiRigaNelPanel(lblOfferteInAttesa, true, "images/iconInAttesa.png");
		aggiungiRigaNelPanel(lblOfferteRifiutate, true, "images/iconaCross.png");
		aggiungiRigaNelPanel(lblOfferteRitirate, true, "images/iconaRitirata.png");
		aggiungiRigaNelPanel(lblReportOfferte, true, "images/iconaGrafico.png");
	}
	
	private void aggiungiRigaNelPanel(MyJLabel labelIn, boolean isInteragibile, String pathImage) {
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
			if(labelIn != sezioneAttuale) {
				labelIn.setBackground(new Color(85, 126, 164));
				labelIn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		
		labelIn.setOnMouseClickedAction(() -> {
			sezionePrecedente = sezioneAttuale;
			sezioneAttuale = labelIn;
			cambiaSezioneGraficamente(sezionePrecedente, sezioneAttuale);
		});
		
		this.add(labelIn);
	}
	
	private void cambiaSezioneGraficamente(MyJLabel prevSezione, MyJLabel currSezione) {
		prevSezione.setBackground(new Color(85, 126, 164));
		currSezione.setBackground(uninaColor);
	}

}
