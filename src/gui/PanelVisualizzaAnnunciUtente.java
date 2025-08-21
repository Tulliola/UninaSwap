package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.Annuncio;
import dto.AnnuncioRegalo;
import dto.AnnuncioScambio;
import dto.AnnuncioVendita;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class PanelVisualizzaAnnunciUtente extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controller mainController;
	private MyJPanel panelSuperiore = new MyJPanel();
	private MyJPanel panelCentrale = new MyJPanel();
	private JScrollPane scrollPane;
	
	public PanelVisualizzaAnnunciUtente(Controller mainController, ArrayList<Annuncio> annunciToDisplay, String messaggioAllUtente, MyJFrame parentFrame) {
		this.mainController = mainController;
		
		this.setLayout(new BorderLayout());
		
		panelCentrale.setPreferredSize(new Dimension(800, 600));
		panelCentrale.setMaximumSize(new Dimension(800, 600));
		
		scrollPane = new JScrollPane(panelCentrale);
		
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);	
		parentFrame.add(scrollPane);
		
		settaPanelSuperiore(annunciToDisplay);
		settaPanelCentrale(messaggioAllUtente);
		
		this.add(panelSuperiore, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		
	}


	private void settaPanelSuperiore(ArrayList<Annuncio> annunciToDisplay) {
		panelSuperiore.setLayout(new BoxLayout(panelSuperiore, BoxLayout.X_AXIS));
		
		MyJPanel panelVendita = new MyJPanel();
		panelVendita.setLayout(new FlowLayout());
		panelVendita.setBackground(Color.WHITE);
		
		MyJPanel panelScambio = new MyJPanel();
		panelScambio.setLayout(new FlowLayout());
		panelScambio.setBackground(Color.WHITE);
		
		MyJPanel panelRegalo = new MyJPanel();
		panelRegalo.setLayout(new FlowLayout());
		panelRegalo.setBackground(Color.WHITE);
		
		MyJLabel lblVendita = new MyJLabel(new ImageIcon("images/iconaAnnuncioVenditaColored.png"), true);
		MyJLabel lblScambio = new MyJLabel(new ImageIcon("images/iconaAnnuncioScambioColored.png"), true);
		MyJLabel lblRegalo = new MyJLabel(new ImageIcon("images/iconaAnnuncioRegaloColored.png"), true);
		
		lblVendita.rendiLabelInteragibile();
		lblScambio.rendiLabelInteragibile();
		lblRegalo.rendiLabelInteragibile();
		
		ArrayList<Annuncio> annunciVendita = new ArrayList();
		ArrayList<Annuncio> annunciScambio = new ArrayList();
		ArrayList<Annuncio> annunciRegalo = new ArrayList();
		
		for(Annuncio annuncio: annunciToDisplay) {
			if(annuncio instanceof AnnuncioVendita)
				annunciVendita.add(annuncio);
			else if(annuncio instanceof AnnuncioScambio)
				annunciScambio.add(annuncio);
			else if(annuncio instanceof AnnuncioRegalo)
				annunciRegalo.add(annuncio);
		}
		
		lblVendita.setOnMouseClickedAction(() -> {
			panelVendita.setBackground(MyJPanel.uninaLightColor);
			panelScambio.setBackground(Color.WHITE);
			panelRegalo.setBackground(Color.WHITE);
			mostraAnnunciDiVenditaSulCentrale(annunciVendita);
		});
		lblVendita.setOnMouseEnteredAction(() -> {
			if(panelVendita.getBackground().equals(Color.WHITE)) {
				panelVendita.setBackground(Color.LIGHT_GRAY);
			}
		});
		lblVendita.setOnMouseExitedAction(() -> {
			if(panelVendita.getBackground().equals(Color.LIGHT_GRAY)) {
				panelVendita.setBackground(Color.WHITE);
			}
		});
		
		lblScambio.setOnMouseClickedAction(() -> {
			panelScambio.setBackground(MyJPanel.uninaLightColor);
			panelVendita.setBackground(Color.WHITE);
			panelRegalo.setBackground(Color.WHITE);
			mostraAnnunciDiScambioSulCentrale(annunciScambio);
		});
		lblScambio.setOnMouseEnteredAction(() -> {
			if(panelScambio.getBackground().equals(Color.WHITE)) {
				panelScambio.setBackground(Color.LIGHT_GRAY);
			}
		});
		lblScambio.setOnMouseExitedAction(() -> {
			if(panelScambio.getBackground().equals(Color.LIGHT_GRAY)) {
				panelScambio.setBackground(Color.WHITE);
			}
		});
		
		lblRegalo.setOnMouseClickedAction(() -> {
			panelRegalo.setBackground(MyJPanel.uninaLightColor);
			panelVendita.setBackground(Color.WHITE);
			panelScambio.setBackground(Color.WHITE);
			mostraAnnunciDiRegaloSulCentrale(annunciRegalo);
		});
		lblRegalo.setOnMouseEnteredAction(() -> {
			if(panelRegalo.getBackground().equals(Color.WHITE)) {
				panelRegalo.setBackground(Color.LIGHT_GRAY);
			}
		});
		lblRegalo.setOnMouseExitedAction(() -> {
			if(panelRegalo.getBackground().equals(Color.LIGHT_GRAY)) {
				panelRegalo.setBackground(Color.WHITE);
			}
		});
		
		panelVendita.add(lblVendita);
		panelScambio.add(lblScambio);
		panelRegalo.add(lblRegalo);
		
		panelSuperiore.add(panelVendita);
		panelSuperiore.add(panelScambio);
		panelSuperiore.add(panelRegalo);
	}

	private void settaPanelCentrale(String messaggioAllUtente) {
		panelCentrale.setLayout(new FlowLayout(FlowLayout.CENTER));
		MyJLabel messaggio = new MyJLabel(messaggioAllUtente, new Font("Ubuntu Sans", Font.ITALIC, 16));
		messaggio.setForeground(Color.BLACK);
		panelCentrale.add(messaggio);
		panelCentrale.setBackground(MyJPanel.uninaLightColor);
	}
	
	private void mostraAnnunciDiVenditaSulCentrale(ArrayList<Annuncio> annunciToDisplay) {
		panelCentrale.removeAll();

		ricalcolaAltezzaConAnnunci(annunciToDisplay);
		for(int i = annunciToDisplay.size()-1; i >= 0; i--) {
			panelCentrale.add(creaPanelAnnuncio(annunciToDisplay.get(i)));
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

	
	private void mostraAnnunciDiScambioSulCentrale(ArrayList<Annuncio> annunciToDisplay) {
		panelCentrale.removeAll();
		
		ricalcolaAltezzaConAnnunci(annunciToDisplay);
		for(int i = annunciToDisplay.size()-1; i >= 0; i--) {
			panelCentrale.add(creaPanelAnnuncio(annunciToDisplay.get(i)));
		}

		panelCentrale.revalidate();
		panelCentrale.repaint();
		
		SwingUtilities.invokeLater(() -> {
		    scrollPane.getVerticalScrollBar().setValue(0); // torna su
		});
		
		if(!panelCentrale.hasPanels()) {
			MyJLabel lblNonCiSonoAnnunci = new MyJLabel("Non ci sono annunci di scambio da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 16));
			lblNonCiSonoAnnunci.setForeground(Color.BLACK);
			panelCentrale.add(lblNonCiSonoAnnunci);
		}
	}
	
	private void mostraAnnunciDiRegaloSulCentrale(ArrayList<Annuncio> annunciToDisplay) {
		panelCentrale.removeAll();
		
		ricalcolaAltezzaConAnnunci(annunciToDisplay);
		for(int i = annunciToDisplay.size()-1; i >= 0; i--) {
			panelCentrale.add(creaPanelAnnuncio(annunciToDisplay.get(i)));
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


	//copied
	private MyJPanel creaPanelAnnuncio(Annuncio annuncioToAdd) {
		MyJPanel annuncioPanel = new MyJPanel();
		annuncioPanel.setLayout(new BorderLayout());
		annuncioPanel.setPreferredSize(new Dimension(800, 600));
		annuncioPanel.setMaximumSize(new Dimension(800, 600));
		annuncioPanel.setBorder(BorderFactory.createLineBorder(MyJPanel.uninaColorClicked, 2));
		
		MyJPanel panelInfoEFotoOggetto = new MyJPanel();
		panelInfoEFotoOggetto.setLayout(new BorderLayout());
		panelInfoEFotoOggetto.add(this.creaPanelFotoOggetto(annuncioToAdd), BorderLayout.NORTH);
		panelInfoEFotoOggetto.add(this.creaPanelInfoOggetto(annuncioToAdd), BorderLayout.CENTER);
		
		annuncioPanel.add(panelInfoEFotoOggetto, BorderLayout.WEST);
		
		MyJPanel panelInfoAnnuncio = new MyJPanel();
		panelInfoAnnuncio.setLayout(new BorderLayout());
		panelInfoAnnuncio.add(this.creaPanelUsernamePubblicante(annuncioToAdd), BorderLayout.NORTH);
		panelInfoAnnuncio.add(this.creaPanelDescrizioneAnnuncio(annuncioToAdd), BorderLayout.CENTER);
//		panelInfoAnnuncio.add(this.creaPanelFaiOfferta(annuncioToAdd), BorderLayout.SOUTH);

		annuncioPanel.add(panelInfoAnnuncio, BorderLayout.CENTER);
		
		return annuncioPanel;
	}
	
	private MyJPanel creaPanelFotoOggetto(Annuncio annuncioToAdd) {
		MyJPanel panelFotoOggetto = new MyJPanel();
		panelFotoOggetto.setLayout(new BorderLayout());
		panelFotoOggetto.setPreferredSize(new Dimension(375, 500));
		panelFotoOggetto.setMaximumSize(new Dimension(375, 500));
		panelFotoOggetto.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, MyJPanel.uninaColorClicked));
				
		ImageIcon fotoToAdd;
		MyJPanel panelInterno;
		if(annuncioToAdd.getOggettoInAnnuncio().getImmagine(0) != null) {
			fotoToAdd = new ImageIcon(annuncioToAdd.getOggettoInAnnuncio().getImmagine(0));
			panelInterno = new MyJPanel(fotoToAdd.getImage());
		}
		else {
			fotoToAdd = new ImageIcon("images/logo_uninaswap.png");
			panelInterno = new MyJPanel(fotoToAdd.getImage());
		}
		
		panelFotoOggetto.add(panelInterno, BorderLayout.CENTER);
		
		return panelFotoOggetto;
	}
	
	private MyJPanel creaPanelInfoOggetto(Annuncio annuncioToAdd) {
		
		MyJPanel panelInfoOggetto = new MyJPanel();
		panelInfoOggetto.setLayout(new BorderLayout());
		panelInfoOggetto.setPreferredSize(new Dimension(375, 100));
		panelInfoOggetto.setMaximumSize(new Dimension(375, 100));
		panelInfoOggetto.setAlignmentX(CENTER_ALIGNMENT);
		
		MyJPanel panelCategoria = new MyJPanel();
		panelCategoria.setPreferredSize(new Dimension(375, 50));
		panelCategoria.setMaximumSize(new Dimension(375, 50));
		panelCategoria.setLayout(new FlowLayout());
		panelCategoria.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 2, MyJPanel.uninaColorClicked));
		panelCategoria.setAlignmentX(CENTER_ALIGNMENT);
		panelCategoria.setBackground(Color.white);
		
		MyJPanel panelCondizioni = new MyJPanel();
		panelCondizioni.setPreferredSize(new Dimension(375, 50));
		panelCondizioni.setMaximumSize(new Dimension(375, 50));
		panelCondizioni.setLayout(new FlowLayout());
		panelCondizioni.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, MyJPanel.uninaColorClicked));
		panelCondizioni.setAlignmentX(CENTER_ALIGNMENT);
		panelCondizioni.setBackground(Color.white);

		MyJLabel lblCategoria = new MyJLabel();
		lblCategoria.setAlignmentX(CENTER_ALIGNMENT);
		lblCategoria.setBorder(new EmptyBorder(10, 0, 0, 0));
		lblCategoria.setText(lblCategoria.getText()+annuncioToAdd.getOggettoInAnnuncio().getCategoria());
		lblCategoria.aggiungiImmagineScalata(annuncioToAdd.getOggettoInAnnuncio().getCategoriaEnum().getImmagineCategoria(), 25, 25, false);
		lblCategoria.setHorizontalTextPosition(SwingConstants.LEFT);
		panelCategoria.add(Box.createVerticalGlue());
		panelCategoria.add(lblCategoria);
		panelCategoria.add(Box.createVerticalGlue());
		
		MyJLabel lblCondizioni = new MyJLabel();
		lblCondizioni.setAlignmentX(CENTER_ALIGNMENT);
		lblCondizioni.setBorder(new EmptyBorder(10, 0, 0, 0));
		lblCondizioni.setText(lblCondizioni.getText()+annuncioToAdd.getOggettoInAnnuncio().getCondizioni());
		lblCondizioni.setForeground(annuncioToAdd.getOggettoInAnnuncio().getCondizioniEnum().getColoreCondizione());
		
		if(!annuncioToAdd.getOggettoInAnnuncio().getCondizioni().equals("Ricondizionato")) {
			MyJPanel panelStelleCondizioni = new MyJPanel();
			panelStelleCondizioni.setBackground(Color.white);
			panelStelleCondizioni.setPreferredSize(new Dimension(90, 30));
			MyJLabel lblStella1 = new MyJLabel();
			lblStella1.aggiungiImmagineScalata(annuncioToAdd.getOggettoInAnnuncio().getCondizioniEnum().getStella1(), 25, 25, false);
			MyJLabel lblStella2 = new MyJLabel();
			lblStella2.aggiungiImmagineScalata(annuncioToAdd.getOggettoInAnnuncio().getCondizioniEnum().getStella2(), 25, 25, false);
			MyJLabel lblStella3 = new MyJLabel();
			lblStella3.aggiungiImmagineScalata(annuncioToAdd.getOggettoInAnnuncio().getCondizioniEnum().getStella3(), 25, 25, false);
			panelStelleCondizioni.add(lblStella1);
			panelStelleCondizioni.add(lblStella2);
			panelStelleCondizioni.add(lblStella3);
		
			panelCondizioni.add(Box.createVerticalGlue());
			panelCondizioni.add(lblCondizioni);
			panelCondizioni.add(panelStelleCondizioni);
			panelCondizioni.add(Box.createVerticalGlue());
		}
		else {
			lblCondizioni.aggiungiImmagineScalata(annuncioToAdd.getOggettoInAnnuncio().getCondizioniEnum().getFixPerRicondizionato(), 25, 25, false);
			lblCondizioni.setHorizontalTextPosition(SwingConstants.LEFT);
			
			panelCondizioni.add(Box.createVerticalGlue());
			panelCondizioni.add(lblCondizioni);
			panelCondizioni.add(Box.createVerticalGlue());
		}
		
		panelInfoOggetto.add(panelCategoria, BorderLayout.NORTH);
		panelInfoOggetto.add(panelCondizioni, BorderLayout.CENTER);
		
		panelInfoOggetto.setBackground(Color.red);
		
		return panelInfoOggetto;
	}
	
	private MyJPanel creaPanelUsernamePubblicante(Annuncio annuncio) {
		MyJPanel panelUsernamePubblicante = new MyJPanel();
		panelUsernamePubblicante.setLayout(new BoxLayout(panelUsernamePubblicante, BoxLayout.X_AXIS));
		panelUsernamePubblicante.setPreferredSize(new Dimension(425, 75));
		panelUsernamePubblicante.setMaximumSize(new Dimension(425, 75));
		panelUsernamePubblicante.setAlignmentX(CENTER_ALIGNMENT);		
		panelUsernamePubblicante.setBorder(BorderFactory.createLineBorder(MyJPanel.uninaColorClicked, 1));
		
		MyJLabel lblUsername = new MyJLabel(annuncio.getUtenteProprietario().getUsername());
		lblUsername.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		lblUsername.setAlignmentX(RIGHT_ALIGNMENT);
		lblUsername.setForeground(Color.white);
		MyJLabel lblIconaTipoAnnuncio = new MyJLabel();
		
		if(annuncio instanceof AnnuncioVendita) {
			lblIconaTipoAnnuncio.aggiungiImmagineScalata("images/iconaAnnuncioVendita.png", 50, 50, false);
			lblIconaTipoAnnuncio.setToolTipText("Prezzo iniziale - "+annuncio.getPrezzoIniziale()+"€");
		}
		else if(annuncio instanceof AnnuncioScambio) {
			lblIconaTipoAnnuncio.aggiungiImmagineScalata("images/iconaAnnuncioScambio.png", 50, 50, false);
			lblIconaTipoAnnuncio.setToolTipText(annuncio.getNotaScambio());
		}
		else if(annuncio instanceof AnnuncioRegalo)
			lblIconaTipoAnnuncio.aggiungiImmagineScalata("images/iconaAnnuncioRegalo.png", 50, 50, false);

		panelUsernamePubblicante.add(Box.createVerticalGlue());
		panelUsernamePubblicante.add(Box.createRigidArea(new Dimension(10, 0)));
		panelUsernamePubblicante.add(lblUsername);
		panelUsernamePubblicante.add(Box.createHorizontalGlue());
		panelUsernamePubblicante.add(lblIconaTipoAnnuncio);
		panelUsernamePubblicante.add(Box.createRigidArea(new Dimension(10, 0)));
		panelUsernamePubblicante.add(Box.createVerticalGlue());
		
		panelUsernamePubblicante.setBackground(MyJPanel.uninaColorClicked);

		return panelUsernamePubblicante;
	}
	
	private MyJPanel creaPanelDescrizioneAnnuncio(Annuncio annuncio) {
		MyJPanel panelDescrizioneAnnuncio = new MyJPanel();
		panelDescrizioneAnnuncio.setLayout(new BorderLayout());
		panelDescrizioneAnnuncio.setPreferredSize(new Dimension(425, 500));
		panelDescrizioneAnnuncio.setMaximumSize(new Dimension(425, 500));
		panelDescrizioneAnnuncio.setAlignmentX(CENTER_ALIGNMENT);
		
		MyJPanel panelNomeAnnuncio = new MyJPanel();
		panelNomeAnnuncio.setLayout(new BoxLayout(panelNomeAnnuncio, BoxLayout.PAGE_AXIS));
		panelNomeAnnuncio.setPreferredSize(new Dimension(425, 100));
		panelNomeAnnuncio.setMaximumSize(new Dimension(425, 100));
		panelNomeAnnuncio.setBackground(MyJPanel.uninaLightColor);
		JTextArea nomeAnnuncioTextArea = new JTextArea(annuncio.getNome());
		nomeAnnuncioTextArea.setBackground(new Color(98, 145, 188));
		nomeAnnuncioTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		nomeAnnuncioTextArea.setPreferredSize(new Dimension(425, 100));
		nomeAnnuncioTextArea.setMaximumSize(new Dimension(425, 100));
		nomeAnnuncioTextArea.setEditable(false);
		nomeAnnuncioTextArea.setOpaque(true);
		nomeAnnuncioTextArea.setLineWrap(true);
		nomeAnnuncioTextArea.setWrapStyleWord(true);
		nomeAnnuncioTextArea.setAlignmentX(LEFT_ALIGNMENT);
		nomeAnnuncioTextArea.setFont(new Font("Ubuntu Sans", Font.BOLD, 18));
		panelNomeAnnuncio.add(nomeAnnuncioTextArea);
		
		MyJPanel panelDescrizione = new MyJPanel();
		panelDescrizione.setLayout(new BorderLayout());
		panelDescrizione.setPreferredSize(new Dimension(425, 300));
		panelDescrizione.setMaximumSize(new Dimension(425, 300));
		JTextArea descrizioneAnnuncioTextArea = new JTextArea(annuncio.getOggettoInAnnuncio().getDescrizione());
		descrizioneAnnuncioTextArea.setBackground(new Color(110, 164, 213));
		descrizioneAnnuncioTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		descrizioneAnnuncioTextArea.setPreferredSize(new Dimension(425, 270));
		descrizioneAnnuncioTextArea.setMaximumSize(new Dimension(425, 270));
		descrizioneAnnuncioTextArea.setEditable(false);
		descrizioneAnnuncioTextArea.setOpaque(true);
		descrizioneAnnuncioTextArea.setLineWrap(true);
		descrizioneAnnuncioTextArea.setWrapStyleWord(true);
		descrizioneAnnuncioTextArea.setAlignmentX(LEFT_ALIGNMENT);
		descrizioneAnnuncioTextArea.setFont(new Font("Ubuntu Sans", Font.ITALIC, 18));
		panelDescrizione.add(descrizioneAnnuncioTextArea, BorderLayout.NORTH);
		
		MyJPanel panelDataScadenza = new MyJPanel();
		panelDataScadenza.setLayout(new BoxLayout(panelDataScadenza, BoxLayout.X_AXIS));
		panelDataScadenza.setBackground(new Color(123, 183, 237));
		panelDataScadenza.setAlignmentX(LEFT_ALIGNMENT);
		MyJLabel lblDataScadenza = new MyJLabel("Data scadenza - N/A");
		lblDataScadenza.setAlignmentX(LEFT_ALIGNMENT);
		lblDataScadenza.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		if(annuncio.getDataScadenza() != null)
			lblDataScadenza.setText("Affrettati, questo annuncio scade tra "+ 
								   (ChronoUnit.DAYS.between(LocalDate.now(), annuncio.getDataScadenza().toLocalDate())) + " giorni!");
		panelDataScadenza.add(lblDataScadenza);
		panelDescrizione.add(panelDataScadenza, BorderLayout.CENTER);
		
		MyJPanel panelModalitaConsegna = this.creaPanelModalitaConsegna(annuncio);
		
		panelDescrizioneAnnuncio.add(panelNomeAnnuncio, BorderLayout.NORTH);
		panelDescrizioneAnnuncio.add(panelDescrizione, BorderLayout.CENTER);
		panelDescrizioneAnnuncio.add(panelModalitaConsegna, BorderLayout.SOUTH);
		
		panelDescrizioneAnnuncio.setBackground(Color.white);
		
		return panelDescrizioneAnnuncio;
	}
	
	private MyJPanel creaPanelModalitaConsegna(Annuncio annuncio) {
		MyJPanel panelModalitaConsegna = new MyJPanel();
		panelModalitaConsegna.setPreferredSize(new Dimension(425, 50));
		panelModalitaConsegna.setMaximumSize(new Dimension(425, 50));
		panelModalitaConsegna.setLayout(new BoxLayout(panelModalitaConsegna, BoxLayout.X_AXIS));
		panelModalitaConsegna.setAlignmentX(CENTER_ALIGNMENT);
		panelModalitaConsegna.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, MyJPanel.uninaColorClicked));
		panelModalitaConsegna.setBackground(Color.white);
		
		MyJButton visualizzaOfferteButton = new MyJButton("Visualizza offerte");
		visualizzaOfferteButton.rendiNotificabile(annuncio.getOfferteRicevute().size());
		visualizzaOfferteButton.setAlignmentX(CENTER_ALIGNMENT);
		visualizzaOfferteButton.setDefaultAction(() -> {
			mainController.passaAFrameVisualizzaOfferte(annuncio.getOfferteRicevute());
		});
		visualizzaOfferteButton.setUpAction(() ->{});
		visualizzaOfferteButton.setDownAction(() ->{});
		
		MyJButton rimuoviAnnuncioButton = new MyJButton("Rimuovi Annuncio");
		rimuoviAnnuncioButton.setDefaultAction(() ->{
			mainController.passaADialogConfermaRimozioneAnnuncio(annuncio);
		});
		rimuoviAnnuncioButton.setUpAction(()->{});
		rimuoviAnnuncioButton.setDownAction(()->{});
		
		panelModalitaConsegna.add(Box.createHorizontalGlue());
		panelModalitaConsegna.add(visualizzaOfferteButton);
		panelModalitaConsegna.add(Box.createHorizontalStrut(20));
		panelModalitaConsegna.add(rimuoviAnnuncioButton);
		panelModalitaConsegna.add(Box.createHorizontalGlue());
//		MyJPanel panelSpedizione = new MyJPanel();
//		panelSpedizione.setPreferredSize(new Dimension(60, 30));
//		panelSpedizione.setMaximumSize(new Dimension(60, 30));
//		panelSpedizione.setBackground(Color.white);
//		MyJLabel lblSpedizione = new MyJLabel();
//		lblSpedizione.aggiungiImmagineScalata("images/iconaSpedizione.png", 25, 25, false);
//		panelSpedizione.add(lblSpedizione);
//		if(annuncio.isSpedizione()) {
//			MyJLabel lblPrevista = new MyJLabel();
//			lblPrevista.aggiungiImmagineScalata("images/iconaCheckVerde.png", 25, 25, false);
//			panelSpedizione.add(lblPrevista);
//		}
//		else {
//			MyJLabel lblNonPrevista = new MyJLabel();
//			lblNonPrevista.aggiungiImmagineScalata("images/iconaXRossa.png", 25, 25, false);
//			panelSpedizione.add(lblNonPrevista);
//		}
//		
//		MyJPanel panelRitiroInPosta = new MyJPanel();
//		panelRitiroInPosta.setPreferredSize(new Dimension(60, 30));
//		panelRitiroInPosta.setMaximumSize(new Dimension(60, 30));
//		panelRitiroInPosta.setBackground(Color.white);
//		MyJLabel lblRitiroInPosta = new MyJLabel();
//		lblRitiroInPosta.aggiungiImmagineScalata("images/iconaRitiroInPosta.png", 25, 25, false);
//		panelRitiroInPosta.add(lblRitiroInPosta);
//		if(annuncio.isRitiroInPosta()) {
//			MyJLabel lblPrevista = new MyJLabel();
//			lblPrevista.aggiungiImmagineScalata("images/iconaCheckVerde.png", 25, 25, false);
//			panelRitiroInPosta.add(lblPrevista);
//		}
//		else {
//			MyJLabel lblNonPrevista = new MyJLabel();
//			lblNonPrevista.aggiungiImmagineScalata("images/iconaXRossa.png", 25, 25, false);
//			panelRitiroInPosta.add(lblNonPrevista);
//		}
//		
//		MyJPanel panelIncontro = new MyJPanel();
//		panelIncontro.setPreferredSize(new Dimension(60, 30));
//		panelIncontro.setMaximumSize(new Dimension(60, 30));
//		panelIncontro.setBackground(Color.white);
//		MyJLabel lblIncontro = new MyJLabel();
//		lblIncontro.aggiungiImmagineScalata("images/iconaIncontro.png", 25, 25, false);
//		panelIncontro.add(lblIncontro);
//		if(annuncio.isIncontro()) {
//			String stringaPerToolTip = "<html>Sono disposto ad un incontro di persona secondo le seguenti disponibilità: <br>";
//			
//			for(int i = 0; i < annuncio.getGiornoIncontro().size(); i++) {
//				stringaPerToolTip += " - ";
//				stringaPerToolTip += annuncio.getGiornoIncontro().get(i) + ", dalle ";
//				stringaPerToolTip += annuncio.getOraInizioIncontro().get(i) + " alle ";
//				stringaPerToolTip += annuncio.getOraFineIncontro().get(i) + ", a ";
//				stringaPerToolTip += annuncio.getSedeIncontroProposte().get(i).getNome() + "; <br>";
//			}
//						
//			stringaPerToolTip += " </html>";
//			lblIncontro.setToolTipText(stringaPerToolTip);
//			
//			MyJLabel lblPrevista = new MyJLabel();
//			lblPrevista.aggiungiImmagineScalata("images/iconaCheckVerde.png", 25, 25, false);
//			panelIncontro.add(lblPrevista);
//		}
//		else {
//			MyJLabel lblNonPrevista = new MyJLabel();
//			lblNonPrevista.aggiungiImmagineScalata("images/iconaXRossa.png", 25, 25, false);			
//			panelIncontro.add(lblNonPrevista);
//		}
//		
//		panelModalitaConsegna.add(Box.createVerticalGlue());
//		panelModalitaConsegna.add(Box.createHorizontalGlue());
//		panelModalitaConsegna.add(panelSpedizione);
//		panelModalitaConsegna.add(Box.createHorizontalGlue());
//		panelModalitaConsegna.add(panelRitiroInPosta);
//		panelModalitaConsegna.add(Box.createHorizontalGlue());
//		panelModalitaConsegna.add(panelIncontro);
//		panelModalitaConsegna.add(Box.createHorizontalGlue());
//		panelModalitaConsegna.add(Box.createVerticalGlue());
//		
		return panelModalitaConsegna;
	}
	
	private void ricalcolaAltezzaConAnnunci(ArrayList<Annuncio> annunciMostrati) {
		int larghezza = panelCentrale.getWidth();
		//600 è l'altezza di un singolo panel dell'annuncio. 10 sono dei pixel aggiuntivi
		int altezza = (annunciMostrati.size() / 2 == 0) ? (annunciMostrati.size()/2 * 610) : ((annunciMostrati.size()/2+1) * 610);
		
		panelCentrale.setPreferredSize(new Dimension(larghezza, altezza));
		panelCentrale.setMaximumSize(new Dimension(larghezza, altezza));
	}
}