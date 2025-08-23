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
import utilities.StatoOffertaEnum;

public class PanelVisualizzaOfferteInAttesaUtente extends PanelVisualizzaOfferteUtente {

	private static final long serialVersionUID = 1L;

	
	public PanelVisualizzaOfferteInAttesaUtente(ArrayList<Offerta> offerteToDisplay, String messaggioAllUtente, MyJFrame parentFrame, Controller controller) {
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
			MyJLabel lblNoOfferte = new MyJLabel("Non ci sono offerte di regalo da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 16));
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
			MyJLabel lblNoOfferte = new MyJLabel("Non ci sono offerte di scambio da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 16));
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

				MyJButton modificaOffertaButton = new MyJButton("Modifica offerta");
				
				MyJButton ritiraOffertaButton = new MyJButton("Ritira offerta");
				ritiraOffertaButton.setUpAction(()->{});
				ritiraOffertaButton.setDownAction(()->{});
				ritiraOffertaButton.setDefaultAction(()->{
					mainController.passaADialogConfermaRitiroOfferta(offerta);
				});
				
				panelSottoDescrizione.add(Box.createHorizontalGlue());
				panelSottoDescrizione.add(modificaOffertaButton);
				panelSottoDescrizione.add(Box.createHorizontalGlue());
				panelSottoDescrizione.add(ritiraOffertaButton);
				panelSottoDescrizione.add(Box.createHorizontalGlue());
				
				return panelSottoDescrizione;
			}
		};
	}
}
