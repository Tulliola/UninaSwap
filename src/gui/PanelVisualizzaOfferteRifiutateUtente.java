package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.Controller;
import dto.Annuncio;
import dto.Offerta;
import dto.OffertaAcquisto;
import dto.OffertaRegalo;
import dto.OffertaScambio;
import utilities.MyJAnnuncioPanel;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class PanelVisualizzaOfferteRifiutateUtente extends PanelVisualizzaOfferteUtente {

	private static final long serialVersionUID = 1L;

	
	public PanelVisualizzaOfferteRifiutateUtente(ArrayList<Offerta> offerteToDisplay, String messaggioAllUtente, MyJFrame parentFrame, Controller controller) {
		super(offerteToDisplay, messaggioAllUtente, parentFrame, controller);
	}


	private MyJAnnuncioPanel settaPanel(Controller mainController, Offerta offerta) {
		return new MyJAnnuncioPanel(mainController, offerta.getAnnuncioRiferito()) {

			@Override
			public MyJPanel creaPanelSottoDescrizione(Annuncio annuncio) {
				MyJPanel panelSottoDescrizione = new MyJPanel();
				panelSottoDescrizione.setLayout(new BoxLayout(panelSottoDescrizione, BoxLayout.X_AXIS));
				panelSottoDescrizione.setPreferredSize(new Dimension(425, 50));
				panelSottoDescrizione.setMaximumSize(new Dimension(425, 50));
				panelSottoDescrizione.setBackground(Color.white);
				
				MyJButton visualizzaOffertaButton = new MyJButton("Visualizza i dettagli dell'offerta rifiutata");
				visualizzaOffertaButton.setDefaultAction(() -> {
					mainController.passaADialogVisualizzaOffertaSpecificaUtente(offerta, mainController, this.getTopLevelAncestor());
				});
				
				panelSottoDescrizione.add(Box.createHorizontalGlue());
				panelSottoDescrizione.add(visualizzaOffertaButton);
				panelSottoDescrizione.add(Box.createHorizontalGlue());
				
				return panelSottoDescrizione;
				
			}
			
			
			@Override
			protected MyJPanel creaPanelDataScadenza(Annuncio annuncio){
				MyJPanel panelDataScadenza = new MyJPanel();
				panelDataScadenza.setBackground(new Color(123, 183, 237));
				panelDataScadenza.setLayout(new BoxLayout(panelDataScadenza, BoxLayout.Y_AXIS));
				
				MyJLabel lblOffertaRifiutata = new MyJLabel("Ci dispiace! Questa tua offerta è stata rifiutata", new Font("Ubuntu Sans", Font.BOLD, 13));
				lblOffertaRifiutata.setAlignmentX(LEFT_ALIGNMENT);
				
				panelDataScadenza.add(Box.createVerticalGlue());
				panelDataScadenza.add(lblOffertaRifiutata);
				panelDataScadenza.add(Box.createVerticalGlue());
				return panelDataScadenza;
			}
		};
	}


	@Override
	protected void settaPanelOfferteAcquisto(ArrayList<Offerta> offerteVendita) {
		for(int i = offerteVendita.size() - 1; i >= 0; i--) {
			if(offerteVendita.get(i) instanceof OffertaAcquisto) {
				panelOfferteAcquisto.add(settaPanel(mainController, offerteVendita.get(i)));
			}
		}
	
		
		if(!panelOfferteAcquisto.hasPanels()) {
			MyJLabel lblNoOfferte = new MyJLabel("Non ci sono offerte di acquisto da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 16));
			panelOfferteAcquisto.add(lblNoOfferte);
		}
	}


	@Override
	protected void settaPanelOfferteScambio(ArrayList<Offerta> offerteScambio) {
		for(int i = offerteScambio.size() - 1; i >= 0; i--) {
			if(offerteScambio.get(i) instanceof OffertaScambio) {
				panelOfferteScambio.add(settaPanel(mainController, offerteScambio.get(i)));
			}
		}
	
		if(!panelOfferteScambio.hasPanels()) {
			MyJLabel lblNoOfferte = new MyJLabel("Non ci sono offerte di scambio da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 16));
			panelOfferteScambio.add(lblNoOfferte);
		}
	}


	@Override
	protected void settaPanelOfferteRegalo(ArrayList<Offerta> offerteRegalo) {
		for(int i = offerteRegalo.size() - 1; i >= 0; i--) {
			if(offerteRegalo.get(i) instanceof OffertaRegalo) {
				panelOfferteRegalo.add(settaPanel(mainController, offerteRegalo.get(i)));
			}
		}

		
		if(!panelOfferteRegalo.hasPanels()) {
			MyJLabel lblNoOfferte = new MyJLabel("Non ci sono offerte di regalo da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 16));
			panelOfferteRegalo.add(lblNoOfferte);
		}
	}
}
