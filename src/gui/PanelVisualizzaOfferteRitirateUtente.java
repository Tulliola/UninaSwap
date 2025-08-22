package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

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

public class PanelVisualizzaOfferteRitirateUtente extends PanelVisualizzaOfferteUtente {

	private static final long serialVersionUID = 1L;


	public PanelVisualizzaOfferteRitirateUtente(ArrayList<Offerta> offerteToDisplay, String messaggioAllUtente, MyJFrame parentFrame, Controller controller) {
		super(offerteToDisplay, messaggioAllUtente, parentFrame, controller);
	}


	@Override
	protected void mostraOfferteDiRegaloSulCentrale(ArrayList<Offerta> offerteToDisplay) {
		ricalcolaAltezzaConOfferte(offerteToDisplay);
		for(int i = offerteToDisplay.size() - 1; i >= 0; i--) {
			if(offerteToDisplay.get(i) instanceof OffertaRegalo) {
				panelCentrale.add(new MyJAnnuncioPanel(mainController, offerteToDisplay.get(i).getAnnuncioRiferito()) {

					@Override
					public MyJPanel creaPanelSottoDescrizione(Annuncio annuncio) {
						MyJPanel panelSottoDescrizione = new MyJPanel();
						panelSottoDescrizione.setLayout(new BoxLayout(panelSottoDescrizione, BoxLayout.X_AXIS));
						panelSottoDescrizione.setPreferredSize(new Dimension(425, 50));
						panelSottoDescrizione.setMaximumSize(new Dimension(425, 50));
						panelSottoDescrizione.setBackground(Color.white);
						
						MyJButton visualizzaOffertaButton = new MyJButton("Visualizza i dettagli dell'offerta ritirata");
						
						
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
						
						MyJLabel lblOffertaRitirata = new MyJLabel("Hai ritirato quest'offerta", new Font("Ubuntu Sans", Font.BOLD, 13));
						lblOffertaRitirata.setAlignmentX(LEFT_ALIGNMENT);
						
						panelDataScadenza.add(Box.createVerticalGlue());
						panelDataScadenza.add(lblOffertaRitirata);
						panelDataScadenza.add(Box.createVerticalGlue());
						return panelDataScadenza;
					}
				});
			}
		}
	}


	@Override
	protected void mostraOfferteDiScambioSulCentrale(ArrayList<Offerta> offerteToDisplay) {
		ricalcolaAltezzaConOfferte(offerteToDisplay);
		for(int i = offerteToDisplay.size() - 1; i >= 0; i--) {
			if(offerteToDisplay.get(i) instanceof OffertaScambio) {
				panelCentrale.add(new MyJAnnuncioPanel(mainController, offerteToDisplay.get(i).getAnnuncioRiferito()) {

					@Override
					public MyJPanel creaPanelSottoDescrizione(Annuncio annuncio) {
						MyJPanel panelSottoDescrizione = new MyJPanel();
						panelSottoDescrizione.setLayout(new BoxLayout(panelSottoDescrizione, BoxLayout.X_AXIS));
						panelSottoDescrizione.setPreferredSize(new Dimension(425, 50));
						panelSottoDescrizione.setMaximumSize(new Dimension(425, 50));
						panelSottoDescrizione.setBackground(Color.white);
						
						MyJButton visualizzaOffertaButton = new MyJButton("Visualizza i dettagli dell'offerta ritirata");
						
						
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
						
						MyJLabel lblOffertaRitirata = new MyJLabel("Hai ritirato quest'offerta", new Font("Ubuntu Sans", Font.BOLD, 13));
						lblOffertaRitirata.setAlignmentX(LEFT_ALIGNMENT);
						
						panelDataScadenza.add(Box.createVerticalGlue());
						panelDataScadenza.add(lblOffertaRitirata);
						panelDataScadenza.add(Box.createVerticalGlue());
						return panelDataScadenza;
					}
				});
			}
		}
	}


	@Override
	protected void mostraOfferteDiAcquistoSulCentrale(ArrayList<Offerta> offerteToDisplay) {
		ricalcolaAltezzaConOfferte(offerteToDisplay);
		for(int i = offerteToDisplay.size() - 1; i >= 0; i--) {
			if(offerteToDisplay.get(i) instanceof OffertaAcquisto) {
				panelCentrale.add(new MyJAnnuncioPanel(mainController, offerteToDisplay.get(i).getAnnuncioRiferito()) {

					@Override
					public MyJPanel creaPanelSottoDescrizione(Annuncio annuncio) {
						MyJPanel panelSottoDescrizione = new MyJPanel();
						panelSottoDescrizione.setLayout(new BoxLayout(panelSottoDescrizione, BoxLayout.X_AXIS));
						panelSottoDescrizione.setPreferredSize(new Dimension(425, 50));
						panelSottoDescrizione.setMaximumSize(new Dimension(425, 50));
						panelSottoDescrizione.setBackground(Color.white);
						
						MyJButton visualizzaOffertaButton = new MyJButton("Visualizza i dettagli dell'offerta ritirata");
						
						
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
						
						MyJLabel lblOffertaRitirata = new MyJLabel("Hai ritirato quest'offerta", new Font("Ubuntu Sans", Font.BOLD, 13));
						lblOffertaRitirata.setAlignmentX(LEFT_ALIGNMENT);
						
						panelDataScadenza.add(Box.createVerticalGlue());
						panelDataScadenza.add(lblOffertaRitirata);
						panelDataScadenza.add(Box.createVerticalGlue());
						return panelDataScadenza;
					}
				});
			}
		}
	}
}
