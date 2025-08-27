package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.Annuncio;
import utilities.MyJAnnuncioPanel;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class PanelVisualizzaAnnunciDisponibiliUtente extends PanelVisualizzaAnnunciUtente {
	
	public PanelVisualizzaAnnunciDisponibiliUtente(Controller controller, ArrayList<Annuncio> annunciToDisplay, String messaggioAllUtente, MyJFrame parentFrame) {
		super(controller, annunciToDisplay, messaggioAllUtente, parentFrame);
		
		MyJLabel lblMessaggio = new MyJLabel("Qui troverai tutti i tuoi annunci ancora disponibili"
				, new Font("Ubuntu Sans", Font.ITALIC, 16));
		lblMessaggio.setAlignmentX(CENTER_ALIGNMENT);
		panelDefault.add(lblMessaggio);
	}

	private MyJPanel creaPanelVisualizzaOfferte(Annuncio annuncio) {
		MyJPanel panelVisualizzaOfferta = new MyJPanel();
		panelVisualizzaOfferta.setPreferredSize(new Dimension(425, 50));
		panelVisualizzaOfferta.setMaximumSize(new Dimension(425, 50));
		panelVisualizzaOfferta.setLayout(new BoxLayout(panelVisualizzaOfferta, BoxLayout.X_AXIS));
		panelVisualizzaOfferta.setAlignmentX(CENTER_ALIGNMENT);
		panelVisualizzaOfferta.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, MyJPanel.uninaColorClicked));
		panelVisualizzaOfferta.setBackground(Color.white);
		
		MyJButton visualizzaOfferteButton = new MyJButton("Visualizza offerte");
		visualizzaOfferteButton.rendiNotificabile(annuncio.getOfferteInAttesa().size());
		visualizzaOfferteButton.setAlignmentX(CENTER_ALIGNMENT);
		visualizzaOfferteButton.setDefaultAction(() -> {
			mainController.passaAFrameVisualizzaOfferte(annuncio.getOfferteInAttesa());
		});
		visualizzaOfferteButton.setUpAction(() ->{});
		visualizzaOfferteButton.setDownAction(() ->{});
		
		MyJButton rimuoviAnnuncioButton = new MyJButton("Rimuovi annuncio");
		rimuoviAnnuncioButton.setDefaultAction(() ->{
			mainController.passaADialogConfermaRimozioneAnnuncio(annuncio);
		});
		rimuoviAnnuncioButton.setUpAction(()->{});
		rimuoviAnnuncioButton.setDownAction(()->{});
		
		panelVisualizzaOfferta.add(Box.createHorizontalGlue());
		panelVisualizzaOfferta.add(visualizzaOfferteButton);
		panelVisualizzaOfferta.add(Box.createHorizontalStrut(20));
		panelVisualizzaOfferta.add(rimuoviAnnuncioButton);
		panelVisualizzaOfferta.add(Box.createHorizontalGlue());
		
		return panelVisualizzaOfferta;
	}

	@Override
	protected void settaPanelAnnunciVendita(ArrayList<Annuncio> annunciToDisplay) {
		for(int i = annunciToDisplay.size()-1; i >= 0; i--) {
			panelAnnunciVendita.add(new MyJAnnuncioPanel(mainController, annunciToDisplay.get(i)) {

				@Override
				public MyJPanel creaPanelSottoDescrizione(Annuncio annuncio) {
					return creaPanelVisualizzaOfferte(annuncio);
				}
		
			});
		}
		
		if(!panelAnnunciVendita.hasPanels()) {
			MyJLabel lblNonCiSonoAnnunci = new MyJLabel("Non ci sono annunci di regalo da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 16));
			lblNonCiSonoAnnunci.setForeground(Color.BLACK);
			
			panelAnnunciVendita.add(lblNonCiSonoAnnunci);
		}
	}

	@Override
	protected void settaPanelAnnunciScambio(ArrayList<Annuncio> annunciToDisplay) {
		for(int i = annunciToDisplay.size()-1; i >= 0; i--) {
			panelAnnunciScambio.add(new MyJAnnuncioPanel(mainController, annunciToDisplay.get(i)) {

				@Override
				public MyJPanel creaPanelSottoDescrizione(Annuncio annuncio) {
					return creaPanelVisualizzaOfferte(annuncio);
				}
		
			});
		}
		
		if(!panelAnnunciScambio.hasPanels()) {
			MyJLabel lblNonCiSonoAnnunci = new MyJLabel("Non ci sono annunci di regalo da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 16));
			lblNonCiSonoAnnunci.setForeground(Color.BLACK);
			
			panelAnnunciScambio.add(lblNonCiSonoAnnunci);
		}
	}

	@Override
	protected void settaPanelAnnunciRegalo(ArrayList<Annuncio> annunciToDisplay) {
		for(int i = annunciToDisplay.size()-1; i >= 0; i--) {
			panelAnnunciRegalo.add(new MyJAnnuncioPanel(mainController, annunciToDisplay.get(i)) {

				@Override
				public MyJPanel creaPanelSottoDescrizione(Annuncio annuncio) {
					return creaPanelVisualizzaOfferte(annuncio);
				}
		
			});
		}
		
		if(!panelAnnunciRegalo.hasPanels()) {
			MyJLabel lblNonCiSonoAnnunci = new MyJLabel("Non ci sono annunci di regalo da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 16));
			lblNonCiSonoAnnunci.setForeground(Color.BLACK);
			
			panelAnnunciRegalo.add(lblNonCiSonoAnnunci);
		}
	}
}
