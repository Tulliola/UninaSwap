package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

import controller.Controller;
import dto.Annuncio;
import net.miginfocom.swing.MigLayout;
import utilities.MyJAnnuncioPanel;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class PanelVisualizzaAnnunciDisponibiliUtente extends PanelVisualizzaAnnunciUtente {
	
	public PanelVisualizzaAnnunciDisponibiliUtente(Controller controller, ArrayList<Annuncio> annunciToDisplay, String messaggioAllUtente, MyJFrame parentFrame) {
		super(controller, annunciToDisplay, messaggioAllUtente, parentFrame);
		
		MyJPanel panelInternoCentrale = new MyJPanel();
		panelInternoCentrale.setLayout(new BoxLayout(panelInternoCentrale, BoxLayout.Y_AXIS));
		panelInternoCentrale.setAlignmentX(CENTER_ALIGNMENT);
		panelInternoCentrale.setBackground(uninaLightColor);
		
		MyJLabel lblMessaggio = new MyJLabel("Qui troverai tutti i tuoi annunci ancora disponibili"
				, new Font("Ubuntu Sans", Font.ITALIC, 20));
		lblMessaggio.setAlignmentX(CENTER_ALIGNMENT);
		
		MyJLabel lblIcona = new MyJLabel();
		lblIcona.setAlignmentX(CENTER_ALIGNMENT);
		lblIcona.aggiungiImmagineScalata("images/iconaAnnuncio.png", 100, 100, false);
		
		panelInternoCentrale.add(lblMessaggio);
		panelInternoCentrale.add(Box.createVerticalStrut(20));
		panelInternoCentrale.add(lblIcona);
		
		panelDefault.add(Box.createVerticalGlue());
		panelDefault.add(panelInternoCentrale);
		panelDefault.add(Box.createVerticalGlue());
	}

	private MyJPanel creaPanelVisualizzaOfferte(Annuncio annuncio) {
		MyJPanel panelVisualizzaOfferta = new MyJPanel();
		panelVisualizzaOfferta.setPreferredSize(new Dimension(425, 46));
		panelVisualizzaOfferta.setMaximumSize(new Dimension(425, 46));
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
			panelAnnunciVendita.setLayout(new MigLayout("fill, align center center"));
			
			MyJPanel panelInterno = new MyJPanel();
			panelInterno.setLayout(new BoxLayout(panelInterno, BoxLayout.Y_AXIS));
			panelInterno.setBackground(uninaLightColor);
			
			MyJLabel lblNonCiSonoAnnunci = new MyJLabel("Non ci sono annunci di vendita da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 20));
			lblNonCiSonoAnnunci.setForeground(Color.BLACK);
			lblNonCiSonoAnnunci.setAlignmentX(CENTER_ALIGNMENT);
			MyJLabel lblNoResultsImage = new MyJLabel();
			lblNoResultsImage.aggiungiImmagineScalata("images/iconaNoResults.png", 100, 100, false);
			lblNoResultsImage.setAlignmentX(CENTER_ALIGNMENT);
			
			panelInterno.add(lblNonCiSonoAnnunci);
			panelInterno.add(Box.createVerticalStrut(20));
			panelInterno.add(lblNoResultsImage);

			panelAnnunciVendita.add(panelInterno, "align center center");
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
			panelAnnunciScambio.setLayout(new MigLayout("fill, align center center"));
			
			MyJPanel panelInterno = new MyJPanel();
			panelInterno.setLayout(new BoxLayout(panelInterno, BoxLayout.Y_AXIS));
			panelInterno.setAlignmentX(CENTER_ALIGNMENT);
			panelInterno.setBackground(uninaLightColor);
			
			MyJLabel lblNonCiSonoAnnunci = new MyJLabel("Non ci sono annunci di scambio da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 20));
			lblNonCiSonoAnnunci.setForeground(Color.BLACK);
			lblNonCiSonoAnnunci.setAlignmentX(CENTER_ALIGNMENT);
			MyJLabel lblNoResultsImage = new MyJLabel();
			lblNoResultsImage.aggiungiImmagineScalata("images/iconaNoResults.png", 100, 100, false);
			lblNoResultsImage.setAlignmentX(CENTER_ALIGNMENT);
			
			panelInterno.add(lblNonCiSonoAnnunci);
			panelInterno.add(Box.createVerticalStrut(20));
			panelInterno.add(lblNoResultsImage);

			panelAnnunciScambio.add(panelInterno, "align center center");
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
			panelAnnunciRegalo.setLayout(new MigLayout("fill, align center center"));
			
			MyJPanel panelInterno = new MyJPanel();
			panelInterno.setLayout(new BoxLayout(panelInterno, BoxLayout.Y_AXIS));
			panelInterno.setAlignmentX(CENTER_ALIGNMENT);
			panelInterno.setBackground(uninaLightColor);
			
			MyJLabel lblNonCiSonoAnnunci = new MyJLabel("Non ci sono annunci di regalo da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 20));
			lblNonCiSonoAnnunci.setForeground(Color.BLACK);
			lblNonCiSonoAnnunci.setAlignmentX(CENTER_ALIGNMENT);
			MyJLabel lblNoResultsImage = new MyJLabel();
			lblNoResultsImage.aggiungiImmagineScalata("images/iconaNoResults.png", 100, 100, false);
			lblNoResultsImage.setAlignmentX(CENTER_ALIGNMENT);
			
			panelInterno.add(lblNonCiSonoAnnunci);
			panelInterno.add(Box.createVerticalStrut(20));
			panelInterno.add(lblNoResultsImage);

			panelAnnunciRegalo.add(panelInterno, "align center center");
		}
	}
}
