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
	}
	
	@Override
	protected void mostraAnnunciDiVenditaSulCentrale(ArrayList<Annuncio> annunciToDisplay) {
		panelCentrale.removeAll();

		ricalcolaAltezzaConAnnunci(annunciToDisplay);
		for(int i = annunciToDisplay.size()-1; i >= 0; i--) {
			panelCentrale.add(new MyJAnnuncioPanel(mainController, annunciToDisplay.get(i)) {

				@Override
				public MyJPanel creaPanelSottoDescrizione(Annuncio annuncio) {
					return creaPanelVisualizzaOfferte(annuncio);
				}
				
			});
		}
		
		panelCentrale.revalidate();
		panelCentrale.repaint();
		
		SwingUtilities.invokeLater(() -> {
		    scrollPane.getVerticalScrollBar().setValue(0);
		});
		
		if(!panelCentrale.hasPanels()) {
			MyJLabel lblNonCiSonoAnnunci = new MyJLabel("Non ci sono annunci di vendita da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 16));
			lblNonCiSonoAnnunci.setForeground(Color.BLACK);
			
			panelCentrale.add(lblNonCiSonoAnnunci);
			
		}
	}

	@Override
	protected void mostraAnnunciDiScambioSulCentrale(ArrayList<Annuncio> annunciToDisplay) {
		panelCentrale.removeAll();
		
		ricalcolaAltezzaConAnnunci(annunciToDisplay);
		for(int i = annunciToDisplay.size()-1; i >= 0; i--) {			
			panelCentrale.add(new MyJAnnuncioPanel(mainController, annunciToDisplay.get(i)) {

				@Override
				public MyJPanel creaPanelSottoDescrizione(Annuncio annuncio) {
					return creaPanelVisualizzaOfferte(annuncio);
				}
		
			});
		}

		panelCentrale.revalidate();
		panelCentrale.repaint();
		
		SwingUtilities.invokeLater(() -> {
		    scrollPane.getVerticalScrollBar().setValue(0); 
		});
		
		if(!panelCentrale.hasPanels()) {
			MyJLabel lblNonCiSonoAnnunci = new MyJLabel("Non ci sono annunci di scambio da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 16));
			lblNonCiSonoAnnunci.setForeground(Color.BLACK);
			panelCentrale.add(lblNonCiSonoAnnunci);
		}
	}

	@Override
	protected void mostraAnnunciDiRegaloSulCentrale(ArrayList<Annuncio> annunciToDisplay) {
		panelCentrale.removeAll();
		
		ricalcolaAltezzaConAnnunci(annunciToDisplay);
		for(int i = annunciToDisplay.size()-1; i >= 0; i--) {
			panelCentrale.add(new MyJAnnuncioPanel(mainController, annunciToDisplay.get(i)) {

				@Override
				public MyJPanel creaPanelSottoDescrizione(Annuncio annuncio) {
					return creaPanelVisualizzaOfferte(annuncio);
				}
		
			});
		}

		panelCentrale.revalidate();
		panelCentrale.repaint();
		
		SwingUtilities.invokeLater(() -> {
		    scrollPane.getVerticalScrollBar().setValue(0);
		});
		
		if(!panelCentrale.hasPanels()) {
			MyJLabel lblNonCiSonoAnnunci = new MyJLabel("Non ci sono annunci di regalo da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 16));
			lblNonCiSonoAnnunci.setForeground(Color.BLACK);
			
			panelCentrale.add(lblNonCiSonoAnnunci);
		}
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
}
