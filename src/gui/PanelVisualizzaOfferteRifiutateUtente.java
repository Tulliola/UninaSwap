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
import net.miginfocom.swing.MigLayout;
import utilities.MyJAnnuncioPanel;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class PanelVisualizzaOfferteRifiutateUtente extends PanelVisualizzaOfferteUtente {

	private static final long serialVersionUID = 1L;

	
	public PanelVisualizzaOfferteRifiutateUtente(ArrayList<Offerta> offerteToDisplay, String messaggioAllUtente, MyJFrame parentFrame, Controller controller) {
		super(offerteToDisplay, messaggioAllUtente, parentFrame, controller);
		
		MyJPanel panelInternoCentrale = new MyJPanel();
		panelInternoCentrale.setLayout(new BoxLayout(panelInternoCentrale, BoxLayout.Y_AXIS));
		panelInternoCentrale.setAlignmentX(CENTER_ALIGNMENT);
		panelInternoCentrale.setBackground(uninaLightColor);
		
		MyJLabel lblMessaggio = new MyJLabel("Qui troverai tutti le tue offerte che sono state rifiutate"
				, new Font("Ubuntu Sans", Font.ITALIC, 20));
		lblMessaggio.setAlignmentX(CENTER_ALIGNMENT);
		
		MyJLabel lblIcona = new MyJLabel();
		lblIcona.setAlignmentX(CENTER_ALIGNMENT);
		lblIcona.aggiungiImmagineScalata("images/iconaCross.png", 100, 100, false);
		
		panelInternoCentrale.add(lblMessaggio);
		panelInternoCentrale.add(Box.createVerticalStrut(20));
		panelInternoCentrale.add(lblIcona);
		
		panelDefault.add(Box.createVerticalGlue());
		panelDefault.add(panelInternoCentrale);
		panelDefault.add(Box.createVerticalGlue());
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
			panelOfferteAcquisto.setLayout(new MigLayout("fill, align center center"));
			
			MyJPanel panelInterno = new MyJPanel();
			panelInterno.setLayout(new BoxLayout(panelInterno, BoxLayout.Y_AXIS));
			panelInterno.setBackground(uninaLightColor);
			
			MyJLabel lblNonCiSonoAnnunci = new MyJLabel("Non ci sono offerte di acquisto da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 20));
			lblNonCiSonoAnnunci.setForeground(Color.BLACK);
			lblNonCiSonoAnnunci.setAlignmentX(CENTER_ALIGNMENT);
			MyJLabel lblNoResultsImage = new MyJLabel();
			lblNoResultsImage.aggiungiImmagineScalata("images/iconaNoResults.png", 100, 100, false);
			lblNoResultsImage.setAlignmentX(CENTER_ALIGNMENT);
			
			panelInterno.add(lblNonCiSonoAnnunci);
			panelInterno.add(Box.createVerticalStrut(20));
			panelInterno.add(lblNoResultsImage);

			panelOfferteAcquisto.add(panelInterno, "align center center");
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
			panelOfferteScambio.setLayout(new MigLayout("fill, align center center"));
			
			MyJPanel panelInterno = new MyJPanel();
			panelInterno.setLayout(new BoxLayout(panelInterno, BoxLayout.Y_AXIS));
			panelInterno.setBackground(uninaLightColor);
			
			MyJLabel lblNonCiSonoAnnunci = new MyJLabel("Non ci sono offerte di scambio da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 20));
			lblNonCiSonoAnnunci.setForeground(Color.BLACK);
			lblNonCiSonoAnnunci.setAlignmentX(CENTER_ALIGNMENT);
			MyJLabel lblNoResultsImage = new MyJLabel();
			lblNoResultsImage.aggiungiImmagineScalata("images/iconaNoResults.png", 100, 100, false);
			lblNoResultsImage.setAlignmentX(CENTER_ALIGNMENT);
			
			panelInterno.add(lblNonCiSonoAnnunci);
			panelInterno.add(Box.createVerticalStrut(20));
			panelInterno.add(lblNoResultsImage);

			panelOfferteScambio.add(panelInterno, "align center center");
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
			panelOfferteRegalo.setLayout(new MigLayout("fill, align center center"));
			
			MyJPanel panelInterno = new MyJPanel();
			panelInterno.setLayout(new BoxLayout(panelInterno, BoxLayout.Y_AXIS));
			panelInterno.setBackground(uninaLightColor);
			
			MyJLabel lblNonCiSonoAnnunci = new MyJLabel("Non ci sono offerte di regalo da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 20));
			lblNonCiSonoAnnunci.setForeground(Color.BLACK);
			lblNonCiSonoAnnunci.setAlignmentX(CENTER_ALIGNMENT);
			MyJLabel lblNoResultsImage = new MyJLabel();
			lblNoResultsImage.aggiungiImmagineScalata("images/iconaNoResults.png", 100, 100, false);
			lblNoResultsImage.setAlignmentX(CENTER_ALIGNMENT);
			
			panelInterno.add(lblNonCiSonoAnnunci);
			panelInterno.add(Box.createVerticalStrut(20));
			panelInterno.add(lblNoResultsImage);

			panelOfferteRegalo.add(panelInterno, "align center center");
		}
	}
}
