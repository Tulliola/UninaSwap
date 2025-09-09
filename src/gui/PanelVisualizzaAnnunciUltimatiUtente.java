package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;

import controller.Controller;
import dto.Annuncio;
import net.miginfocom.swing.MigLayout;
import utilities.MyJAnnuncioPanel;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class PanelVisualizzaAnnunciUltimatiUtente extends PanelVisualizzaAnnunciUtente {
	
	private static final long serialVersionUID = 1L;

	public PanelVisualizzaAnnunciUltimatiUtente(Controller controller, ArrayList<Annuncio> annunciToDisplay, String messaggioAllUtente, MyJFrame parentFrame) {
		super(controller, annunciToDisplay, messaggioAllUtente, parentFrame);
		
		MyJPanel panelInternoCentrale = new MyJPanel();
		panelInternoCentrale.setLayout(new BoxLayout(panelInternoCentrale, BoxLayout.Y_AXIS));
		panelInternoCentrale.setAlignmentX(CENTER_ALIGNMENT);
		panelInternoCentrale.setBackground(uninaLightColor);
		
		MyJLabel lblMessaggio = new MyJLabel("Qui troverai tutti i tuoi annunci andati a buon fine"
				, new Font("Ubuntu Sans", Font.ITALIC, 20));
		lblMessaggio.setAlignmentX(CENTER_ALIGNMENT);
		
		MyJLabel lblIcona = new MyJLabel();
		lblIcona.setAlignmentX(CENTER_ALIGNMENT);
		lblIcona.aggiungiImmagineScalata("images/iconaAnnunciUltimati.png", 100, 100, false);
		
		panelInternoCentrale.add(lblMessaggio);
		panelInternoCentrale.add(Box.createVerticalStrut(20));
		panelInternoCentrale.add(lblIcona);
		
		panelDefault.add(Box.createVerticalGlue());
		panelDefault.add(panelInternoCentrale);
		panelDefault.add(Box.createVerticalGlue());

	}

	private MyJPanel creaPanelVisualizzaOffertaAccettata(Annuncio annuncio) {
		MyJPanel panelVisualizzaOffertaAccettata = new MyJPanel();
		panelVisualizzaOffertaAccettata.setPreferredSize(new Dimension(425, 46));
		panelVisualizzaOffertaAccettata.setMaximumSize(new Dimension(425, 46));
		panelVisualizzaOffertaAccettata.setLayout(new BoxLayout(panelVisualizzaOffertaAccettata, BoxLayout.X_AXIS));
		panelVisualizzaOffertaAccettata.setAlignmentX(CENTER_ALIGNMENT);
		panelVisualizzaOffertaAccettata.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, MyJPanel.uninaColorClicked));
		panelVisualizzaOffertaAccettata.setBackground(Color.white);
		
		MyJButton visualizzaOffertaAccettata = new MyJButton("Visualizza offerta accettata");
		visualizzaOffertaAccettata.setAlignmentX(CENTER_ALIGNMENT);
		visualizzaOffertaAccettata.setDefaultAction(() -> {
			mainController.passaADialogOffertaAccettataAnnuncio(annuncio.getOffertaAccettata());
		});
		visualizzaOffertaAccettata.setUpAction(() ->{});
		visualizzaOffertaAccettata.setDownAction(() ->{});
		
			
		panelVisualizzaOffertaAccettata.add(Box.createHorizontalGlue());
		panelVisualizzaOffertaAccettata.add(visualizzaOffertaAccettata);
		panelVisualizzaOffertaAccettata.add(Box.createHorizontalGlue());
		
		return panelVisualizzaOffertaAccettata;
	}
	
	private MyJPanel creaPanelRiepilogoScadenza(Annuncio annuncio) {
		MyJPanel panelRiepilogoScadenza = new MyJPanel();
		panelRiepilogoScadenza.setPreferredSize(new Dimension(425, 50));
		panelRiepilogoScadenza.setMaximumSize(new Dimension(425, 50));
		panelRiepilogoScadenza.setLayout(new BoxLayout(panelRiepilogoScadenza, BoxLayout.X_AXIS));
		panelRiepilogoScadenza.setAlignmentX(CENTER_ALIGNMENT);
		panelRiepilogoScadenza.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, MyJPanel.uninaColorClicked));
		panelRiepilogoScadenza.setBackground(new Color(220, 220, 220));
		
		MyJLabel lblDataScadenza = new MyJLabel();
		lblDataScadenza.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));

		if(annuncio.getDataScadenza() != null) {
			DateTimeFormatter formatterPerData = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			
			lblDataScadenza.setText("Questo annuncio sarebbe dovuto scadere il " + annuncio.getDataScadenza().toLocalDate().format(formatterPerData));
		}
		else 
			lblDataScadenza.setText("Questo annuncio non prevedeva una data di scadenza");
		
		panelRiepilogoScadenza.add(Box.createHorizontalGlue());
		panelRiepilogoScadenza.add(lblDataScadenza);
		panelRiepilogoScadenza.add(Box.createHorizontalGlue());
		
		return panelRiepilogoScadenza;
	}

	@SuppressWarnings("serial")
	@Override
	protected void settaPanelAnnunciVendita(ArrayList<Annuncio> annunciToDisplay) {

		
		for(int i = annunciToDisplay.size()-1; i >= 0; i--) {
			panelAnnunciVendita.add(new MyJAnnuncioPanel(mainController, annunciToDisplay.get(i)) {

				@Override
				public MyJPanel creaPanelSottoDescrizione(Annuncio annuncio) {
					return creaPanelVisualizzaOffertaAccettata(annuncio);
				}
				
				@Override
				public MyJPanel creaPanelDataScadenza(Annuncio annuncio) {
					return creaPanelRiepilogoScadenza(annuncio);
				}
		
			});
		}

		
		if(!panelAnnunciVendita.hasPanels()) {
			panelAnnunciVendita.setLayout(new MigLayout("fill, align center center"));
			
			MyJPanel panelInterno = new MyJPanel();
			panelInterno.setLayout(new BoxLayout(panelInterno, BoxLayout.Y_AXIS));
			panelInterno.setAlignmentX(CENTER_ALIGNMENT);
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

	@SuppressWarnings("serial")
	@Override
	protected void settaPanelAnnunciScambio(ArrayList<Annuncio> annunciToDisplay) {

		for(int i = annunciToDisplay.size()-1; i >= 0; i--) {
			panelAnnunciScambio.add(new MyJAnnuncioPanel(mainController, annunciToDisplay.get(i)) {

				@Override
				public MyJPanel creaPanelSottoDescrizione(Annuncio annuncio) {
					return creaPanelVisualizzaOffertaAccettata(annuncio);
				}
				
				@Override
				public MyJPanel creaPanelDataScadenza(Annuncio annuncio) {
					return creaPanelRiepilogoScadenza(annuncio);
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

	@SuppressWarnings("serial")
	@Override
	protected void settaPanelAnnunciRegalo(ArrayList<Annuncio> annunciToDisplay) {

		for(int i = annunciToDisplay.size()-1; i >= 0; i--) {
			panelAnnunciRegalo.add(new MyJAnnuncioPanel(mainController, annunciToDisplay.get(i)) {

				@Override
				public MyJPanel creaPanelSottoDescrizione(Annuncio annuncio) {
					return creaPanelVisualizzaOffertaAccettata(annuncio);
				}
				
				@Override
				public MyJPanel creaPanelDataScadenza(Annuncio annuncio) {
					return creaPanelRiepilogoScadenza(annuncio);
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
