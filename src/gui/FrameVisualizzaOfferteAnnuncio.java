package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import controller.Controller;
import dto.Offerta;
import dto.OffertaAcquisto;
import dto.OffertaScambio;
import dto.Oggetto;
import net.miginfocom.swing.MigLayout;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJOffertaPanel;
import utilities.MyJPanel;
import utilities.StatoAnnuncioEnum;
import utilities.StatoOffertaEnum;

public class FrameVisualizzaOfferteAnnuncio extends MyJFrame {

	private static final long serialVersionUID = 1L;
	
	private Controller mainController;
	
	private MyJPanel contentPane;
	private PanelHomePageSuperiore panelSuperiore = new PanelHomePageSuperiore(this, "UninaSwap");
	private MyJPanel panelCentrale = new MyJPanel();
	private PanelBarraLateraleSx panelLaterale;
	private JScrollPane scrollPane;
	private MyJPanel panelOfferte = new MyJPanel();
	private MyJLabel lblTornaAllaHomePage = new MyJLabel("   Torna alla home page");
	
	public FrameVisualizzaOfferteAnnuncio(ArrayList<Offerta> offerte, Controller mainController) {
		this.mainController = mainController;
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		if(!offerte.isEmpty())
			this.setTitle("Le offerte al tuo annuncio - "+offerte.get(0).getAnnuncioRiferito().getNome());
		
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		settaContentPane(offerte);
	}

	private void settaContentPane(ArrayList<Offerta> offerte) {
		contentPane = new MyJPanel();
		contentPane.setLayout(new BorderLayout());
		settaPanelCentrale(offerte);
		contentPane.add(panelCentrale, BorderLayout.CENTER);
		contentPane.add(panelSuperiore, BorderLayout.NORTH);
		scrollPane = new JScrollPane(panelOfferte);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		SwingUtilities.invokeLater(() -> {
			scrollPane.getViewport().setViewPosition(new Point(0, 0));
		});
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		contentPane.add(scrollPane);
		this.setContentPane(contentPane);
	}

	private void settaPanelCentrale(ArrayList<Offerta> offerte) {
		panelCentrale.setLayout(new MigLayout("wrap 2", "[]", ""));
		panelCentrale.setBackground(MyJPanel.uninaLightColor);
		panelLaterale = new PanelBarraLateraleSx(panelCentrale, mainController, this, "        Annunci disponibili");
		panelLaterale.aggiungiRigaNelPanel(lblTornaAllaHomePage, true, "images/iconaHomePage.png");
		panelLaterale.add(lblTornaAllaHomePage, 0);
		
		lblTornaAllaHomePage.setOnMouseClickedAction(() -> 
		{
			mainController.passaAFrameHomePage(this);
		});
		
		
		contentPane.add(panelLaterale, BorderLayout.WEST);
		if(offerte.isEmpty()) {
			this.setTitle("Non ci sono offerte per questo annuncio");
			panelOfferte.setLayout(new MigLayout("fill, align center center"));
			panelOfferte.setBackground(MyJPanel.uninaLightColor);
			
			MyJPanel panelInterno = new MyJPanel();
			panelInterno.setLayout(new BoxLayout(panelInterno, BoxLayout.Y_AXIS));
			panelInterno.setAlignmentX(CENTER_ALIGNMENT);
			panelInterno.setBackground(MyJPanel.uninaLightColor);
			
			MyJLabel lblNonCiSonoAnnunci = new MyJLabel("Non ci sono offerte a questo annuncio da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 20));
			lblNonCiSonoAnnunci.setForeground(Color.BLACK);
			lblNonCiSonoAnnunci.setAlignmentX(CENTER_ALIGNMENT);
			MyJLabel lblNoResultsImage = new MyJLabel();
			lblNoResultsImage.aggiungiImmagineScalata("images/iconaNoResults.png", 100, 100, false);
			lblNoResultsImage.setAlignmentX(CENTER_ALIGNMENT);
			
			panelInterno.add(lblNonCiSonoAnnunci);
			panelInterno.add(Box.createVerticalStrut(20));
			panelInterno.add(lblNoResultsImage);
			
			panelOfferte.add(Box.createVerticalGlue());
			panelOfferte.add(panelInterno, "align center center");
			panelOfferte.add(Box.createVerticalGlue());
		}
		else
			panelCentrale.add(settaPanelOfferte(offerte), BorderLayout.CENTER);
	}

	private MyJPanel settaPanelOfferte(ArrayList<Offerta> offerte) {
		panelOfferte.setBackground(MyJPanel.uninaLightColor);
		panelOfferte.setLayout(new MigLayout("wrap 2", "[]", ""));
		for(Offerta offerta: offerte) {
			panelOfferte.add(new MyJOffertaPanel(offerta, mainController) {

				@Override
				public void settaAzioneRifiutaButton() {
					mainController.aggiornaStatoOfferta(offerta, StatoOffertaEnum.Rifiutata);
				}

				@Override
				public void settaAzioneAccettaButton() {
					mainController.aggiornaStatoOfferta(offerta, StatoOffertaEnum.Accettata);
					if(offerta instanceof OffertaAcquisto)
						offerta.getAnnuncioRiferito().setStatoEnum(StatoAnnuncioEnum.Venduto);
					else if(offerta instanceof OffertaScambio)
						offerta.getAnnuncioRiferito().setStatoEnum(StatoAnnuncioEnum.Scambiato);
					else
						offerta.getAnnuncioRiferito().setStatoEnum(StatoAnnuncioEnum.Regalato);
				}
				
				@Override
				public MyJPanel creaPanelSpecifico(Offerta offerta) {
					MyJPanel panelSpecifico = new MyJPanel();
					panelSpecifico.setLayout(new FlowLayout(FlowLayout.CENTER));
					panelSpecifico.setPreferredSize(new Dimension(larghezza - distanzaDalBordo, 60));
					panelSpecifico.setMaximumSize(new Dimension(larghezza - distanzaDalBordo, 60));		
					panelSpecifico.setBackground(coloreCasualePerBG);
					
					if(offerta.getPrezzoOfferto() != null) {
						MyJLabel lblPrezzoOfferto = new MyJLabel(offerta.getUtenteProprietario().getUsername() + " ti ha offerto " + offerta.getPrezzoOfferto() + " €!");
						lblPrezzoOfferto.aggiungiImmagineScalata("images/iconaPrezzoIniziale.png", 25, 25, false);
						lblPrezzoOfferto.setHorizontalTextPosition(SwingConstants.RIGHT);
						
						panelSpecifico.add(lblPrezzoOfferto);
					}
					else if(offerta.getOggettiOfferti() != null && offerta.getOggettiOfferti().size() != 0)
						settaPanelSpecificoToOggettiInScambio(offerta, panelSpecifico);
						
					else {
						MyJLabel lblRegalo = new MyJLabel(offerta.getUtenteProprietario().getUsername()+ " ha chiesto l'oggetto in regalo");
						lblRegalo.aggiungiImmagineScalata("images/iconaAnnuncioRegaloColored.png", 25, 25, false);
						lblRegalo.setHorizontalTextPosition(SwingConstants.RIGHT);
						
						panelSpecifico.add(lblRegalo);
					}
					return panelSpecifico;
				}

				@Override
				public MyJPanel creaPanelSpecifico() {
					MyJPanel panelSpecifico = new MyJPanel();
					panelSpecifico.setLayout(new BoxLayout(panelSpecifico, BoxLayout.X_AXIS));
					panelSpecifico.setPreferredSize(new Dimension(780, 40));
					panelSpecifico.setMaximumSize(new Dimension(780, 40));
					
					if(offerta.getPrezzoOfferto() != null) {
						MyJLabel lblPrezzo = new MyJLabel("Prezzo offerto: €"+offerta.getPrezzoOfferto());
						lblPrezzo.setAlignmentX(LEFT_ALIGNMENT);
						lblPrezzo.setFont(new Font("Ubuntu Sans", Font.PLAIN, 18));
						panelSpecifico.add(lblPrezzo);
					}
					else if(!offerta.getOggettiOfferti().isEmpty()) {
						
					}
					else {
						MyJLabel lblRegalo = new MyJLabel("L'oggetto è stato chiesto in regalo");
						lblRegalo.setAlignmentX(LEFT_ALIGNMENT);
						panelSpecifico.add(lblRegalo);
					}
					return panelSpecifico;
				}
				
			});
		}
		return this.panelOfferte;
	}

	private void settaPanelSpecificoToOggettiInScambio(Offerta offerta, MyJPanel panelSpecifico) {
		MyJLabel lblOggettiInCambio = new MyJLabel(offerta.getUtenteProprietario().getUsername() + " ti ha offerto ");
		lblOggettiInCambio.aggiungiImmagineScalata("images/iconaFrecceScambio.png", 25, 25, false);
		lblOggettiInCambio.setHorizontalAlignment(SwingConstants.RIGHT);
		
		MyJLabel lblCliccabile =  new MyJLabel(String.valueOf(offerta.getOggettiOfferti().size()), Color.red);
		lblCliccabile.aggiungiEffettoCliccabilitaPerTesto();
		lblCliccabile.rendiLabelInteragibile();
		
		lblCliccabile.setOnMouseExitedAction(() -> {});
		lblCliccabile.setOnMouseEnteredAction(() -> {});
		lblCliccabile.setOnMouseClickedAction(() -> {mainController.passaADialogVisualizzaOggetti(offerta.getOggettiOfferti());});
		
		MyJLabel lblOggetti = new MyJLabel(" oggetti!");
		
		panelSpecifico.add(lblOggettiInCambio);
		panelSpecifico.add(lblCliccabile);
		panelSpecifico.add(lblOggetti);
	}
	
	private PanelBarraLateraleSx settaPanelLaterale() {
		panelLaterale = new PanelBarraLateraleSx(panelCentrale, mainController, this, "        Annunci disponibili");
		panelLaterale.aggiungiRigaNelPanel(lblTornaAllaHomePage, true, "images/iconaHomePage.png");
		panelLaterale.add(lblTornaAllaHomePage, 0);
		
		return this.panelLaterale;
	}

	private Dimension getDimension(ArrayList<Offerta> offerte) {
		return new Dimension(800, offerte.size()*400);
	}
}
