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
	
	public PanelVisualizzaInfoProfilo(MyJPanel parentPanel) {
		panelChiamante = parentPanel;
	
		this.setPreferredSize(new Dimension(300, panelChiamante.getHeight()));
		this.setBackground(new Color(85, 126, 164));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(LEFT_ALIGNMENT);
		
		aggiungiRigheAnnunciNelPanel();
		aggiungiRigheOfferteNelPanel();
	}
	
	public void aggiungiRigheAnnunciNelPanel() {
		MyJLabel lblIMieiAnnunci = new MyJLabel("   I miei annunci");

		MyJLabel lblAnnunciDisponibili = new MyJLabel("        Annunci disponibili");
		lblAnnunciDisponibili.aggiungiImmagineScalata("images/iconaSearchBar.png", 25, 25, false);
		lblAnnunciDisponibili.setHorizontalTextPosition(SwingConstants.RIGHT);

		MyJLabel lblAnnunciUltimati = new MyJLabel("        Annunci andati a buon fine");
		lblAnnunciUltimati.aggiungiImmagineScalata("images/iconaCheck.png", 25, 25, false);
		lblAnnunciUltimati.setHorizontalTextPosition(SwingConstants.RIGHT);
		
		MyJLabel lblAnnunciScaduti = new MyJLabel("        Annunci scaduti");
		lblAnnunciScaduti.aggiungiImmagineScalata("images/iconaCronometro.png", 25, 25, false);
		lblAnnunciScaduti.setHorizontalTextPosition(SwingConstants.RIGHT);
		
		MyJLabel lblAnnunciRimossi = new MyJLabel("        Annunci rimossi");
		lblAnnunciRimossi.aggiungiImmagineScalata("images/iconaCross.png", 25, 25, false);
		lblAnnunciRimossi.setHorizontalTextPosition(SwingConstants.RIGHT);
		
		aggiungiRigaNelPanel(lblIMieiAnnunci, false);
		aggiungiRigaNelPanel(lblAnnunciDisponibili, true);
		aggiungiRigaNelPanel(lblAnnunciUltimati, true);
		aggiungiRigaNelPanel(lblAnnunciScaduti, true);
		aggiungiRigaNelPanel(lblAnnunciRimossi, true);
	}
	
	public void aggiungiRigheOfferteNelPanel() {
		MyJLabel lblLeMieOfferte = new MyJLabel("   Le mie offerte");
		
		MyJLabel lblOfferteAccettate = new MyJLabel("        Offerte accettate");
		
		MyJLabel lblOfferteInAttesa = new MyJLabel("        Offerte in attesa");
		
		MyJLabel lblOfferteRifiutate = new MyJLabel("        Offerte rifiutate");
		
		MyJLabel lblOfferteRitirate = new MyJLabel("        Offerte ritirate");
		
		MyJLabel lblReportOfferte = new MyJLabel("        Report offerte");
				
		aggiungiRigaNelPanel(lblLeMieOfferte, false);
		aggiungiRigaNelPanel(lblOfferteAccettate, true);
		aggiungiRigaNelPanel(lblOfferteInAttesa, true);
		aggiungiRigaNelPanel(lblOfferteRifiutate, true);
		aggiungiRigaNelPanel(lblOfferteRitirate, true);
		aggiungiRigaNelPanel(lblReportOfferte, true);
	}
	
	public void aggiungiRigaNelPanel(MyJLabel labelIn, boolean isInteragibile) {
		labelIn.setBackground(new Color(85, 126, 164));
		labelIn.setPreferredSize(new Dimension(300, 50));
		labelIn.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		labelIn.setMaximumSize(new Dimension(300, 50));
		labelIn.setAlignmentX(CENTER_ALIGNMENT);
		labelIn.setOpaque(true);
		labelIn.setBorder(new EmptyBorder(0, 5, 0, 0));
		
		if(isInteragibile)
			labelIn.rendiLabelInteragibile();
		else
			labelIn.setBackground(new Color(65, 106, 144));
			
		
		labelIn.setOnMouseEnteredAction(() -> {
			labelIn.setBackground(new Color(65, 106, 144));
			labelIn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		});
		
		labelIn.setOnMouseExitedAction(() -> {
			labelIn.setBackground(new Color(85, 126, 164));
			labelIn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		});
		
		labelIn.setOnMouseClickedAction(() -> {
			
		});
		
		this.add(labelIn);
	}

}
