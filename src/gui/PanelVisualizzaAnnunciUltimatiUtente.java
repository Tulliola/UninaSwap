package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
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
import utilities.MyJAnnuncioSegnalabilePanel;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class PanelVisualizzaAnnunciUltimatiUtente extends PanelVisualizzaAnnunciUtente {
	
	public PanelVisualizzaAnnunciUltimatiUtente(Controller controller, ArrayList<Annuncio> annunciToDisplay, String messaggioAllUtente, MyJFrame parentFrame) {
		super(controller, annunciToDisplay, messaggioAllUtente, parentFrame);
		MyJLabel lblMessaggio = new MyJLabel("Qui troverai tutti i tuoi annunci andati a buon fine"
				, new Font("Ubuntu Sans", Font.ITALIC, 16));
		lblMessaggio.setAlignmentX(CENTER_ALIGNMENT);
		panelDefault.add(lblMessaggio);
	}

	private MyJPanel creaPanelVisualizzaOffertaAccettata(Annuncio annuncio) {
		MyJPanel panelVisualizzaOffertaAccettata = new MyJPanel();
		panelVisualizzaOffertaAccettata.setPreferredSize(new Dimension(425, 50));
		panelVisualizzaOffertaAccettata.setMaximumSize(new Dimension(425, 50));
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
		panelRiepilogoScadenza.setBackground(Color.white);
		
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

	@Override
	protected void settaPanelAnnunciVendita(ArrayList<Annuncio> annunciToDisplay) {

//		panelAnnunciVendita.setPreferredSize(new Dimension(panelCentrale.getWidth(), ((annunciToDisplay.size()/2 + 1) * 610)));
		
		for(int i = annunciToDisplay.size()-1; i >= 0; i--) {
			panelAnnunciVendita.add(new MyJAnnuncioSegnalabilePanel(mainController, annunciToDisplay.get(i)) {

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
			MyJLabel lblNonCiSonoAnnunci = new MyJLabel("Non ci sono annunci di vendita da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 16));
			lblNonCiSonoAnnunci.setForeground(Color.BLACK);
			panelAnnunciVendita.add(lblNonCiSonoAnnunci);
		}
	}

	@Override
	protected void settaPanelAnnunciScambio(ArrayList<Annuncio> annunciToDisplay) {

//		panelAnnunciScambio.setPreferredSize(new Dimension(panelCentrale.getWidth(), ((annunciToDisplay.size()/2 + 1) * 610)));
		for(int i = annunciToDisplay.size()-1; i >= 0; i--) {
			panelAnnunciScambio.add(new MyJAnnuncioSegnalabilePanel(mainController, annunciToDisplay.get(i)) {

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
			MyJLabel lblNonCiSonoAnnunci = new MyJLabel("Non ci sono annunci di scambio da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 16));
			lblNonCiSonoAnnunci.setForeground(Color.BLACK);
			panelAnnunciScambio.add(lblNonCiSonoAnnunci);
		}
	}

	@Override
	protected void settaPanelAnnunciRegalo(ArrayList<Annuncio> annunciToDisplay) {

//		panelAnnunciRegalo.setPreferredSize(new Dimension(panelCentrale.getWidth(), ((annunciToDisplay.size()/2 + 1) * 610)));
		for(int i = annunciToDisplay.size()-1; i >= 0; i--) {
			panelAnnunciRegalo.add(new MyJAnnuncioSegnalabilePanel(mainController, annunciToDisplay.get(i)) {

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
			MyJLabel lblNonCiSonoAnnunci = new MyJLabel("Non ci sono annunci di regalo da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 16));
			lblNonCiSonoAnnunci.setForeground(Color.BLACK);
			panelAnnunciRegalo.add(lblNonCiSonoAnnunci);
		}
	}
}
