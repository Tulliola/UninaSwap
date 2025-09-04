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
import net.miginfocom.swing.MigLayout;
import utilities.MyJAnnuncioPanel;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class PanelVisualizzaAnnunciScadutiUtente extends PanelVisualizzaAnnunciUtente {
	
	public PanelVisualizzaAnnunciScadutiUtente(Controller controller, ArrayList<Annuncio> annunciToDisplay, String messaggioAllUtente, MyJFrame parentFrame) {
		super(controller, annunciToDisplay, messaggioAllUtente, parentFrame);
		
		MyJPanel panelInternoCentrale = new MyJPanel();
		panelInternoCentrale.setLayout(new BoxLayout(panelInternoCentrale, BoxLayout.Y_AXIS));
		panelInternoCentrale.setAlignmentX(CENTER_ALIGNMENT);
		panelInternoCentrale.setBackground(uninaLightColor);
		
		MyJLabel lblMessaggio = new MyJLabel("Qui troverai tutti i tuoi annunci scaduti"
				, new Font("Ubuntu Sans", Font.ITALIC, 20));
		lblMessaggio.setAlignmentX(CENTER_ALIGNMENT);
		
		MyJLabel lblIcona = new MyJLabel();
		lblIcona.setAlignmentX(CENTER_ALIGNMENT);
		lblIcona.aggiungiImmagineScalata("images/iconaScaduto.png", 100, 100, false);
		
		panelInternoCentrale.add(lblMessaggio);
		panelInternoCentrale.add(Box.createVerticalStrut(20));
		panelInternoCentrale.add(lblIcona);
		
		panelDefault.add(Box.createVerticalGlue());
		panelDefault.add(panelInternoCentrale);
		panelDefault.add(Box.createVerticalGlue());
	}


	private MyJPanel creaPanelRiepilogoScadenza(Annuncio annuncio) {
		MyJPanel panelRiepilogoScadenza = new MyJPanel();
		panelRiepilogoScadenza.setPreferredSize(new Dimension(425, 46));
		panelRiepilogoScadenza.setMaximumSize(new Dimension(425, 46));
		panelRiepilogoScadenza.setLayout(new BoxLayout(panelRiepilogoScadenza, BoxLayout.X_AXIS));
		panelRiepilogoScadenza.setAlignmentX(CENTER_ALIGNMENT);
		panelRiepilogoScadenza.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, MyJPanel.uninaColorClicked));
		panelRiepilogoScadenza.setBackground(new Color(220, 220, 220));
		
		MyJLabel lblDataScadenza = new MyJLabel();
		lblDataScadenza.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));

		if(annuncio.getDataScadenza() != null) {
			DateTimeFormatter formatterPerData = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			
			lblDataScadenza.setText("Questo annuncio è scaduto il " + annuncio.getDataScadenza().toLocalDate().format(formatterPerData));
		}
		else 
			lblDataScadenza.setText("Questo annuncio non prevedeva una data di scadenza");
		
		panelRiepilogoScadenza.add(Box.createHorizontalGlue());
		panelRiepilogoScadenza.add(lblDataScadenza);
		panelRiepilogoScadenza.add(Box.createHorizontalGlue());
		
		return panelRiepilogoScadenza;
	}
	
	private MyJPanel creaPanelScaduto() {
		MyJPanel panelDataScadenza = new MyJPanel();
		panelDataScadenza.setLayout(new BoxLayout(panelDataScadenza, BoxLayout.X_AXIS));
		panelDataScadenza.setBackground(new Color(220, 220, 220));
		panelDataScadenza.setAlignmentX(LEFT_ALIGNMENT);
		MyJLabel lblDataScadenza = new MyJLabel("Ci dispiace, ma questo annuncio è scaduto", new Font("Ubuntu Sans", Font.ITALIC, 18));
		lblDataScadenza.setAlignmentX(CENTER_ALIGNMENT);
		lblDataScadenza.setForeground(Color.black);

		panelDataScadenza.add(Box.createHorizontalGlue());
		panelDataScadenza.add(lblDataScadenza);
		panelDataScadenza.add(Box.createHorizontalGlue());
		
		return panelDataScadenza;
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
					return creaPanelScaduto();
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
					return creaPanelScaduto();
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
					return creaPanelRiepilogoScadenza(annuncio);
				}
				
				@Override
				public MyJPanel creaPanelDataScadenza(Annuncio annuncio) {
					return creaPanelScaduto();
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
