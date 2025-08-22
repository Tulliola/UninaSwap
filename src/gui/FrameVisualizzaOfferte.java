package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.Controller;
import dto.Offerta;
import dto.OffertaAcquisto;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.StatoOffertaEnum;

public class FrameVisualizzaOfferte extends MyJFrame {

	private static final long serialVersionUID = 1L;
	
	private Controller mainController;
	
	private MyJPanel contentPane;
	private PanelHomePageSuperiore panelSuperiore = new PanelHomePageSuperiore(this);
	private MyJPanel panelCentrale = new MyJPanel();
	private PanelBarraLateraleSx panelLaterale;
	private JScrollPane scrollPane;
	private MyJPanel panelOfferte = new MyJPanel();
	private MyJLabel lblTornaAllaHomePage = new MyJLabel("   Torna alla home page");
	
	public FrameVisualizzaOfferte(ArrayList<Offerta> offerte, Controller mainController) {
		this.mainController = mainController;
		this.setSize(1100, 900);
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
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		contentPane.add(scrollPane);
		this.setContentPane(contentPane);
	}

	private void settaPanelCentrale(ArrayList<Offerta> offerte) {
		panelCentrale.setLayout(new BorderLayout());
		panelCentrale.setPreferredSize(new Dimension(1100, 900));
		panelLaterale = new PanelBarraLateraleSx(panelCentrale, mainController, this, "        Offerte accettate");
		panelLaterale.aggiungiRigaNelPanel(lblTornaAllaHomePage, true, "images/iconaHomePage.png");
		panelLaterale.add(lblTornaAllaHomePage, 0);
		
		lblTornaAllaHomePage.setOnMouseClickedAction(() -> 
		{
			mainController.passaAFrameHomePage(this);
		});
		
		panelLaterale.getLblIlMioProfilo().setOnMouseClickedAction(() -> {
			mainController.passaASezioneInFrameProfiloUtente("   Il mio profilo");
		});
		
		panelLaterale.getLblAnnunciDisponibili().setOnMouseClickedAction(() -> 
		{
			mainController.passaASezioneInFrameProfiloUtente("        Annunci disponibili");
		});
		
		panelLaterale.getLblAnnunciUltimati().setOnMouseClickedAction(() -> 
		{
			mainController.passaASezioneInFrameProfiloUtente("        Annunci andati a buon fine");
		});
		
		panelLaterale.getLblAnnunciScaduti().setOnMouseClickedAction(() -> 
		{
			mainController.passaASezioneInFrameProfiloUtente("        Annunci scaduti");
		});
		
		panelLaterale.getLblAnnunciRimossi().setOnMouseClickedAction(() -> 
		{        
			mainController.passaASezioneInFrameProfiloUtente("        Annunci rimossi");
		});
		
		panelLaterale.getLblOfferteAccettate().setOnMouseClickedAction(() -> {
			mainController.passaASezioneInFrameProfiloUtente("        Offerte accettate");
		});
		
		panelLaterale.getLblOfferteRifiutate().setOnMouseClickedAction(() -> {
			mainController.passaASezioneInFrameProfiloUtente("        Offerte rifiutate");
		});
		
		panelLaterale.getLblOfferteInAttesa().setOnMouseClickedAction(() -> {
			mainController.passaASezioneInFrameProfiloUtente("        Offerte in attesa");
		});
		
		panelLaterale.getLblOfferteRitirate().setOnMouseClickedAction(() -> {
			mainController.passaASezioneInFrameProfiloUtente("        Offerte ritirate");
		});
		
		contentPane.add(panelLaterale, BorderLayout.WEST);
		if(offerte.isEmpty()) {
			this.setTitle("Non ci sono offerte per questo annuncio");
			MyJLabel noOfferte = new MyJLabel();
			noOfferte.setText("Non ci sono offerte attive per questo annuncio");
			noOfferte.setFont(new Font("Ubuntu Sans", Font.ITALIC, 16));
			noOfferte.setAlignmentX(CENTER_ALIGNMENT);
			panelOfferte.add(noOfferte);
		}
		else
			panelCentrale.add(settaPanelOfferte(offerte), BorderLayout.CENTER);
	}

	private MyJPanel settaPanelOfferte(ArrayList<Offerta> offerte) {
		panelOfferte.setBackground(MyJPanel.uninaLightColor);
		panelOfferte.setPreferredSize(getDimension(offerte));
		for(Offerta offerta: offerte) {
			panelOfferte.add(aggiungiPanelOfferta(offerta));
			panelOfferte.add(new JSeparator(JSeparator.HORIZONTAL));
		}
		return this.panelOfferte;
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

	private MyJPanel aggiungiPanelOfferta(Offerta offertaToAdd) {
		MyJPanel panelOfferta = new MyJPanel();
		panelOfferta.setSize(800, 400);
		panelOfferta.setPreferredSize(new Dimension(800, 400));
		panelOfferta.setLayout(new BoxLayout(panelOfferta, BoxLayout.X_AXIS));
		panelOfferta.add(creaPanelImmagine(offertaToAdd));
		panelOfferta.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		panelOfferta.add(creaPanelInfoAnnuncio(offertaToAdd));
		panelOfferta.add(creaPanelInfoOfferta(offertaToAdd));
		panelOfferta.add(creaPanelAccettaRifiutaOfferta(offertaToAdd));
		panelOfferta.setBackground(Color.WHITE);
		return panelOfferta;
	}

	private MyJPanel creaPanelAccettaRifiutaOfferta(Offerta offerta) {
		MyJPanel panelAccettaRifiutaOfferta = new MyJPanel();
		panelAccettaRifiutaOfferta.setPreferredSize(new Dimension(250, 400));
		panelAccettaRifiutaOfferta.setMaximumSize(new Dimension(150, 400));
		panelAccettaRifiutaOfferta.setLayout(new BoxLayout(panelAccettaRifiutaOfferta, BoxLayout.Y_AXIS));
		panelAccettaRifiutaOfferta.setBackground(Color.WHITE);
		
		MyJButton accettaButton = new MyJButton("Accetta");
		accettaButton.setAlignmentX(CENTER_ALIGNMENT);
		accettaButton.setDefaultAction(() -> {
			mainController.aggiornaStatoOffertaAcquisto((OffertaAcquisto)offerta, StatoOffertaEnum.Accettata);
		});
		
		MyJButton rifiutaButton = new MyJButton("Rifiuta");
		rifiutaButton.setAlignmentX(CENTER_ALIGNMENT);
		rifiutaButton.setBackground(Color.RED);
		rifiutaButton.setDefaultAction(() -> {
			mainController.aggiornaStatoOffertaAcquisto((OffertaAcquisto)offerta, StatoOffertaEnum.Rifiutata);
		});
		
		panelAccettaRifiutaOfferta.add(Box.createVerticalGlue());
		panelAccettaRifiutaOfferta.add(accettaButton);
		panelAccettaRifiutaOfferta.add(Box.createVerticalStrut(30));
		panelAccettaRifiutaOfferta.add(rifiutaButton);
		panelAccettaRifiutaOfferta.add(Box.createVerticalGlue());
		return panelAccettaRifiutaOfferta;
	}

	private MyJPanel creaPanelInfoOfferta(Offerta offertaToAdd) {
		MyJPanel panelInfoOfferta = new MyJPanel();
		panelInfoOfferta.setMaximumSize(new Dimension(300, 400));
		panelInfoOfferta.setLayout(new BoxLayout(panelInfoOfferta, BoxLayout.Y_AXIS));
		panelInfoOfferta.setBackground(Color.WHITE);
		panelInfoOfferta.setPreferredSize(new Dimension(300, 400));
		panelInfoOfferta.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
		
		JTextArea textNomeOfferente = new JTextArea();
		textNomeOfferente.setText("Offerta di: "+offertaToAdd.getUtenteProprietario().getUsername());
		textNomeOfferente.setFont(new Font("Ubuntu Sans", Font.BOLD, 14));
		textNomeOfferente.setFocusable(false);
		textNomeOfferente.setEditable(false);
		
		JTextArea textPrezzoOfferto = new JTextArea();
		textPrezzoOfferto.setText("Prezzo offerto: €"+offertaToAdd.getPrezzoOfferto());
		textPrezzoOfferto.setFont(new Font("Ubuntu Sans", Font.BOLD, 14));
		textPrezzoOfferto.setFocusable(false);
		textPrezzoOfferto.setEditable(false);
		
		JTextArea textModConsegna = new JTextArea();
		String consegna = "Modalità di consegna scelta: ";
		if(offertaToAdd.getModalitaConsegnaScelta().equals("Spedizione")) {
			consegna += "spedizione presso "+offertaToAdd.getIndirizzoSpedizione();
		}
		else if(offertaToAdd.getModalitaConsegnaScelta().equals("Incontro")) {
			consegna += "incontro presso "+offertaToAdd.getSedeDIncontroScelta().getNome()+ " dalle "+
			offertaToAdd.getOraInizioIncontro()+" alle "+offertaToAdd.getOraFineIncontro()+" di "+offertaToAdd.getGiornoIncontro();
		}
		else {
			consegna += "ritiro presso la posta "+offertaToAdd.getUfficioRitiro().getNome();
		}
		textModConsegna.setText(consegna);
		textModConsegna.setFont(new Font("Ubuntu Sans", Font.BOLD, 14));
		textModConsegna.setFocusable(false);
		textModConsegna.setEditable(false);
		textModConsegna.setLineWrap(true);
		textModConsegna.setWrapStyleWord(true);
		
		panelInfoOfferta.add(textNomeOfferente);
		panelInfoOfferta.add(textPrezzoOfferto);
		panelInfoOfferta.add(textModConsegna);
		return panelInfoOfferta;
	}

	private MyJPanel creaPanelInfoAnnuncio(Offerta offertaToAdd) {
		MyJPanel panelInfoAnnunci = new MyJPanel();
		panelInfoAnnunci.setPreferredSize(new Dimension(300, 400));
		panelInfoAnnunci.setMaximumSize(new Dimension(300, 400));
		panelInfoAnnunci.setLayout(new BoxLayout(panelInfoAnnunci, BoxLayout.Y_AXIS));
		panelInfoAnnunci.setBackground(Color.WHITE);
		panelInfoAnnunci.setBorder(BorderFactory.createMatteBorder(0,  1, 0, 1, Color.BLACK));
		
		JTextArea textPrezzoAnnuncio = new JTextArea();
		textPrezzoAnnuncio.setText("Prezzo iniziale: €"+offertaToAdd.getAnnuncioRiferito().getPrezzoIniziale());
		textPrezzoAnnuncio.setFont(new Font("Ubuntu Sans", Font.BOLD, 14));
		textPrezzoAnnuncio.setFocusable(false);
		textPrezzoAnnuncio.setEditable(false);
		textPrezzoAnnuncio.setLineWrap(true);
		textPrezzoAnnuncio.setWrapStyleWord(true);
		textPrezzoAnnuncio.setAlignmentX(CENTER_ALIGNMENT);
	
		MyJPanel panelModalitaMesseADisposizione = new MyJPanel();
		panelModalitaMesseADisposizione.setBackground(Color.white);
		panelModalitaMesseADisposizione.setLayout(new BoxLayout(panelModalitaMesseADisposizione, BoxLayout.X_AXIS));
		MyJLabel lblSpedizione = new MyJLabel();
		lblSpedizione.aggiungiImmagineScalata("images/iconaSpedizione.png", 25, 25, false);
		panelModalitaMesseADisposizione.add(lblSpedizione);
		if(offertaToAdd.getAnnuncioRiferito().isSpedizione()) {
			MyJLabel lblNonOfferta = new MyJLabel();
			lblNonOfferta.aggiungiImmagineScalata("images/iconaXRossa.png", 25, 25, false);
			panelModalitaMesseADisposizione.add(lblNonOfferta);
		}
		else {
			MyJLabel lblOfferta = new MyJLabel();
			lblOfferta.aggiungiImmagineScalata("images/iconaCheckVerde.png", 25, 25, false);
			panelModalitaMesseADisposizione.add(lblOfferta);
			panelModalitaMesseADisposizione.add(lblOfferta);
		}
		
		panelModalitaMesseADisposizione.add(Box.createHorizontalStrut(10));
		
		MyJLabel lblRitiroInPosta = new MyJLabel();
		lblRitiroInPosta.aggiungiImmagineScalata("images/iconaRitiroInPosta.png", 25, 25, false);
		panelModalitaMesseADisposizione.add(lblRitiroInPosta);
		if(offertaToAdd.getAnnuncioRiferito().isRitiroInPosta()) {
			MyJLabel lblNonOfferta = new MyJLabel();
			lblNonOfferta.aggiungiImmagineScalata("images/iconaXRossa.png", 25, 25, false);
			panelModalitaMesseADisposizione.add(lblNonOfferta);
		}
		else {
			MyJLabel lblOfferta = new MyJLabel();
			lblOfferta.aggiungiImmagineScalata("images/iconaCheckVerde.png", 25, 25, false);
			panelModalitaMesseADisposizione.add(lblOfferta);
			panelModalitaMesseADisposizione.add(lblOfferta);
		}
		
		panelModalitaMesseADisposizione.add(Box.createHorizontalStrut(10));
		
		MyJLabel lblIncontro = new MyJLabel();
		lblIncontro.aggiungiImmagineScalata("images/iconaIncontro.png", 25, 25, false);
		panelModalitaMesseADisposizione.add(lblIncontro);
		if(offertaToAdd.getAnnuncioRiferito().isIncontro()) {
			MyJLabel lblNonOfferta = new MyJLabel();
			lblNonOfferta.aggiungiImmagineScalata("images/iconaXRossa.png", 25, 25, false);
			panelModalitaMesseADisposizione.add(lblNonOfferta);
		}
		else {
			MyJLabel lblOfferta = new MyJLabel();
			lblOfferta.aggiungiImmagineScalata("images/iconaCheckVerde.png", 25, 25, false);
			panelModalitaMesseADisposizione.add(lblOfferta);
			panelModalitaMesseADisposizione.add(lblOfferta);
		}

//		panelInfoAnnunci.add(Box.createVerticalGlue());
		panelInfoAnnunci.add(textPrezzoAnnuncio);
//		panelInfoAnnunci.add(Box.createVerticalGlue());
		panelInfoAnnunci.add(panelModalitaMesseADisposizione);
		panelInfoAnnunci.add(Box.createVerticalGlue());
		return panelInfoAnnunci;
	}

	private MyJPanel creaPanelImmagine(Offerta offertaToAdd) {
		MyJPanel panelImmagine = new MyJPanel();
		panelImmagine.setLayout(new BoxLayout(panelImmagine, BoxLayout.Y_AXIS));
		panelImmagine.setPreferredSize(new Dimension(300, 400));
		panelImmagine.setBackground(Color.WHITE);
		MyJLabel lblPrimaImmagineAnnuncio = new MyJLabel();
		lblPrimaImmagineAnnuncio.aggiungiImmagineScalata(offertaToAdd.getAnnuncioRiferito().getOggettoInAnnuncio().getImmagine(0),
				150, 200, false);

		lblPrimaImmagineAnnuncio.setAlignmentX(CENTER_ALIGNMENT);
		lblPrimaImmagineAnnuncio.setBorder(BorderFactory.createLineBorder(MyJPanel.uninaColor, 3));
		
		panelImmagine.add(Box.createVerticalGlue());
		panelImmagine.add(lblPrimaImmagineAnnuncio);
		panelImmagine.add(Box.createVerticalGlue());
		return panelImmagine;
	}
}
