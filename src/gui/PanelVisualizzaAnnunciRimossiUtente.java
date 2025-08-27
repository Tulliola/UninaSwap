package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;

import controller.Controller;
import dto.Annuncio;
import utilities.MyJAnnuncioPanel;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class PanelVisualizzaAnnunciRimossiUtente extends PanelVisualizzaAnnunciUtente {
	
	public PanelVisualizzaAnnunciRimossiUtente(Controller controller, ArrayList<Annuncio> annunciToDisplay, String messaggioAllUtente, MyJFrame parentFrame) {
		super(controller, annunciToDisplay, messaggioAllUtente, parentFrame);
		MyJLabel lblMessaggio = new MyJLabel("Qui troverai tutti i tuoi annunci rimossi da te"
				, new Font("Ubuntu Sans", Font.ITALIC, 16));
		lblMessaggio.setAlignmentX(CENTER_ALIGNMENT);
		panelDefault.add(lblMessaggio);
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
	
	private MyJPanel creaPanelRimosso() {
		MyJPanel panelDataRimosso = new MyJPanel();
		panelDataRimosso.setLayout(new BoxLayout(panelDataRimosso, BoxLayout.X_AXIS));
		panelDataRimosso.setBackground(new Color(220, 220, 220));
		panelDataRimosso.setAlignmentX(LEFT_ALIGNMENT);
		MyJLabel lblDataRimosso = new MyJLabel("A quanto pare hai rimosso questo annuncio...", new Font("Ubuntu Sans", Font.ITALIC, 18));
		lblDataRimosso.setAlignmentX(CENTER_ALIGNMENT);
		lblDataRimosso.setForeground(Color.black);

		panelDataRimosso.add(Box.createHorizontalGlue());
		panelDataRimosso.add(lblDataRimosso);
		panelDataRimosso.add(Box.createHorizontalGlue());
		
		return panelDataRimosso;
	}

	@Override
	protected void settaPanelAnnunciVendita(ArrayList<Annuncio> annunciToDisplay) {
		for(int i = annunciToDisplay.size()-1; i >= 0; i--) {
			panelAnnunciVendita.add(new MyJAnnuncioPanel(mainController, annunciToDisplay.get(i)) {

				@Override
				public MyJPanel creaPanelSottoDescrizione(Annuncio annuncio) {
					return creaPanelRiepilogoScadenza(annuncio);
				}
				
				@Override
				public MyJPanel creaPanelDataScadenza(Annuncio annuncio) {
					return creaPanelRimosso();
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
		for(int i = annunciToDisplay.size()-1; i >= 0; i--) {
			panelAnnunciScambio.add(new MyJAnnuncioPanel(mainController, annunciToDisplay.get(i)) {

				@Override
				public MyJPanel creaPanelSottoDescrizione(Annuncio annuncio) {
					return creaPanelRiepilogoScadenza(annuncio);
				}
				
				@Override
				public MyJPanel creaPanelDataScadenza(Annuncio annuncio) {
					return creaPanelRimosso();
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
		for(int i = annunciToDisplay.size()-1; i >= 0; i--) {
			panelAnnunciRegalo.add(new MyJAnnuncioPanel(mainController, annunciToDisplay.get(i)) {

				@Override
				public MyJPanel creaPanelSottoDescrizione(Annuncio annuncio) {
					return creaPanelRiepilogoScadenza(annuncio);
				}
				
				@Override
				public MyJPanel creaPanelDataScadenza(Annuncio annuncio) {
					return creaPanelRimosso();
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
