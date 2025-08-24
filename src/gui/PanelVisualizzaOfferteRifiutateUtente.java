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


	@Override
	protected void mostraOfferteDiRegaloSulCentrale(ArrayList<Offerta> offerteToDisplay) {
		ricalcolaAltezzaConOfferte(offerteToDisplay);
		for(int i = offerteToDisplay.size() - 1; i >= 0; i--) {
			if(offerteToDisplay.get(i) instanceof OffertaRegalo) {
				panelCentrale.add(settaPanel(mainController, offerteToDisplay.get(i)));
			}
		}
		
		panelCentrale.revalidate();
		panelCentrale.repaint();

		SwingUtilities.invokeLater(() -> {
		    scrollPane.getVerticalScrollBar().setValue(0);
		});
		
		if(!panelCentrale.hasPanels()) {
			MyJLabel lblNoOfferte = new MyJLabel("Non ci sono offerte di acquisto da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 16));
			panelCentrale.add(lblNoOfferte);
		}
	}


	@Override
	protected void mostraOfferteDiScambioSulCentrale(ArrayList<Offerta> offerteToDisplay) {
		ricalcolaAltezzaConOfferte(offerteToDisplay);
		for(int i = offerteToDisplay.size() - 1; i >= 0; i--) {
			if(offerteToDisplay.get(i) instanceof OffertaScambio) {
				panelCentrale.add(settaPanel(mainController, offerteToDisplay.get(i)));
			}
		}
		
		panelCentrale.revalidate();
		panelCentrale.repaint();

		SwingUtilities.invokeLater(() -> {
		    scrollPane.getVerticalScrollBar().setValue(0);
		});
		
		if(!panelCentrale.hasPanels()) {
			MyJLabel lblNoOfferte = new MyJLabel("Non ci sono offerte di acquisto da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 16));
			panelCentrale.add(lblNoOfferte);
		}
	}


	@Override
	protected void mostraOfferteDiAcquistoSulCentrale(ArrayList<Offerta> offerteToDisplay) {
		ricalcolaAltezzaConOfferte(offerteToDisplay);
		for(int i = offerteToDisplay.size() - 1; i >= 0; i--) {
			if(offerteToDisplay.get(i) instanceof OffertaAcquisto) {
				panelCentrale.add(settaPanel(mainController, offerteToDisplay.get(i)));
			}
		}
		
		panelCentrale.revalidate();
		panelCentrale.repaint();

		SwingUtilities.invokeLater(() -> {
		    scrollPane.getVerticalScrollBar().setValue(0);
		});
		
		if(!panelCentrale.hasPanels()) {
			MyJLabel lblNoOfferte = new MyJLabel("Non ci sono offerte di acquisto da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 16));
			panelCentrale.add(lblNoOfferte);
		}
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
}
