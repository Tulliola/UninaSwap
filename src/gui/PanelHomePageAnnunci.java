package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.Annuncio;
import dto.AnnuncioRegalo;
import dto.AnnuncioScambio;
import dto.AnnuncioVendita;
//import dto.AnnuncioRegalo;
//import dto.AnnuncioScambio;
import utilities.MyJButton;

import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;
import utilities.StatoAnnuncioEnum;

public class PanelHomePageAnnunci extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private MyJPanel bordoSuperiore = new MyJPanel();
	private MyJPanel barraDiRicerca = new MyJPanel();
	private MyJPanel bordoInferiore = new MyJPanel();
	
	private Controller mainController;
	
	public PanelHomePageAnnunci(Controller controller, ArrayList<Annuncio> annunci) {
		mainController = controller;
		
		this.setLayout(new BorderLayout());
		
		this.settaBordoSuperiore();
		this.settaBordoInferiore();

		MyJPanel prova = new MyJPanel();
		prova.setLayout(new FlowLayout());
		
		for(int i = 0; i < annunci.size(); i++) {
			MyJPanel annuncioCorrente = creaPanelAnnuncio(annunci.get(i));
			prova.add(annuncioCorrente);
		}
		
		prova.setPreferredSize(new Dimension(500, 4000));
		
		
		JScrollPane scrollPanel = new JScrollPane(prova);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.getVerticalScrollBar().setValue(0);
		scrollPanel.getVerticalScrollBar().setUnitIncrement(20);

		this.add(bordoSuperiore, BorderLayout.NORTH);
		this.add(scrollPanel, BorderLayout.CENTER);
		this.add(bordoInferiore, BorderLayout.SOUTH);
	}
	
	private void settaBordoSuperiore() {
		bordoSuperiore.setLayout(new BoxLayout(bordoSuperiore, BoxLayout.Y_AXIS));
		bordoSuperiore.setAlignmentX(CENTER_ALIGNMENT);
		bordoSuperiore.setPreferredSize(new Dimension(500, 50));
		bordoSuperiore.setBackground(MyJPanel.uninaColorClicked);
		
		barraDiRicerca.setLayout(new BorderLayout());
		barraDiRicerca.setAlignmentX(CENTER_ALIGNMENT);
		barraDiRicerca.setPreferredSize(new Dimension(450, 35));
		barraDiRicerca.setMaximumSize(new Dimension(450, 35));

		JTextField campoDiTestoTextField = new JTextField("Cerca ora!");
		campoDiTestoTextField.setBorder(new EmptyBorder(0, 10, 0, 0));
		campoDiTestoTextField.setFont(new Font("Ubuntu Sans", Font.PLAIN, 15));
		campoDiTestoTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent fe) {
				if(campoDiTestoTextField.getText().equals("Cerca ora!"))
					campoDiTestoTextField.setText("");
			}
			
			@Override
			public void focusLost(FocusEvent fe) {
				if(campoDiTestoTextField.getText().isEmpty())
					campoDiTestoTextField.setText("Cerca ora!");
			}
		});
		
		MyJLabel lblIconaDiRicerca = new MyJLabel();
		lblIconaDiRicerca.aggiungiImmagineScalata("images/iconaDiRicerca.png", 30, 30, false);
		lblIconaDiRicerca.setOpaque(true);
		lblIconaDiRicerca.setBackground(Color.white);
		
		MyJLabel lblIconaDiEliminaTesto = new MyJLabel();
		lblIconaDiEliminaTesto.aggiungiImmagineScalata("images/iconaRimuovi.png", 30, 30, true);
		lblIconaDiEliminaTesto.setOpaque(true);
		lblIconaDiEliminaTesto.setBackground(Color.white);
		
		lblIconaDiEliminaTesto.rendiLabelInteragibile();
		
		lblIconaDiEliminaTesto.setOnMouseClickedAction(() -> {
			campoDiTestoTextField.setText("");
			campoDiTestoTextField.requestFocus();
		});
		
		lblIconaDiEliminaTesto.setOnMouseEnteredAction(() -> {
			lblIconaDiEliminaTesto.setBackground(new Color(220, 220, 220));
			lblIconaDiEliminaTesto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		});
		
		lblIconaDiEliminaTesto.setOnMouseExitedAction(() -> {
			lblIconaDiEliminaTesto.setBackground(Color.white);
			lblIconaDiEliminaTesto.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		});
		
		barraDiRicerca.add(lblIconaDiRicerca, BorderLayout.WEST);
		barraDiRicerca.add(campoDiTestoTextField, BorderLayout.CENTER);
		barraDiRicerca.add(lblIconaDiEliminaTesto, BorderLayout.EAST);
	
		bordoSuperiore.add(Box.createVerticalGlue());
		bordoSuperiore.add(barraDiRicerca);
		bordoSuperiore.add(Box.createVerticalGlue());
	}
	
	private void settaBordoInferiore() {
		bordoInferiore.setLayout(new BoxLayout(bordoInferiore, BoxLayout.X_AXIS));
		bordoInferiore.setAlignmentX(CENTER_ALIGNMENT);
		bordoInferiore.setPreferredSize(new Dimension(500, 100));
		bordoInferiore.setBackground(new Color(220, 220, 220));	
		
		MyJButton bottonePubblicaAnnuncioVendita = new MyJButton("Pubblica un nuovo annuncio di vendita");
		bottonePubblicaAnnuncioVendita.setAlignmentX(CENTER_ALIGNMENT);
		bottonePubblicaAnnuncioVendita.setPreferredSize(new Dimension(400, 75));
		bottonePubblicaAnnuncioVendita.setMaximumSize(new Dimension(400, 75));
		bottonePubblicaAnnuncioVendita.setDefaultAction(() -> {mainController.passaAFramePubblicaAnnuncio("Vendita");});
		bottonePubblicaAnnuncioVendita.setUpAction(() -> {});
		bottonePubblicaAnnuncioVendita.setDownAction(() -> {});
		
		MyJButton bottonePubblicaAnnuncioScambio = new MyJButton("Pubblica un nuovo annuncio di scambio");
		bottonePubblicaAnnuncioScambio.setAlignmentX(CENTER_ALIGNMENT);
		bottonePubblicaAnnuncioScambio.setPreferredSize(new Dimension(400, 75));
		bottonePubblicaAnnuncioScambio.setMaximumSize(new Dimension(400, 75));
		bottonePubblicaAnnuncioScambio.setDefaultAction(() -> {mainController.passaAFramePubblicaAnnuncio("Scambio");});
		bottonePubblicaAnnuncioScambio.setUpAction(() -> {});
		bottonePubblicaAnnuncioScambio.setDownAction(() -> {});
		
		MyJButton bottonePubblicaAnnuncioRegalo = new MyJButton("Pubblica un nuovo annuncio di regalo");
		bottonePubblicaAnnuncioRegalo.setAlignmentX(CENTER_ALIGNMENT);
		bottonePubblicaAnnuncioRegalo.setPreferredSize(new Dimension(400, 75));
		bottonePubblicaAnnuncioRegalo.setMaximumSize(new Dimension(400, 75));

		bottonePubblicaAnnuncioRegalo.setDefaultAction(() -> {mainController.passaAFramePubblicaAnnuncio("Regalo");});
		bottonePubblicaAnnuncioRegalo.setUpAction(() -> {});
		bottonePubblicaAnnuncioRegalo.setDownAction(() -> {});
		
		bordoInferiore.add(Box.createVerticalGlue());
		bordoInferiore.add(Box.createHorizontalGlue());
		bordoInferiore.add(bottonePubblicaAnnuncioVendita);
		bordoInferiore.add(Box.createHorizontalGlue());
		bordoInferiore.add(bottonePubblicaAnnuncioScambio);
		bordoInferiore.add(Box.createHorizontalGlue());
		bordoInferiore.add(bottonePubblicaAnnuncioRegalo);
		bordoInferiore.add(Box.createHorizontalGlue());
		bordoInferiore.add(Box.createVerticalGlue());
	}
	
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
		panelInfoAnnuncio.add(this.creaPanelFaiOfferta(annuncioToAdd), BorderLayout.SOUTH);

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
		panelCategoria.setBackground(new Color(220, 220, 220));
		
		MyJPanel panelCondizioni = new MyJPanel();
		panelCondizioni.setPreferredSize(new Dimension(375, 50));
		panelCondizioni.setMaximumSize(new Dimension(375, 50));
		panelCondizioni.setLayout(new FlowLayout());
		panelCondizioni.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, MyJPanel.uninaColorClicked));
		panelCondizioni.setAlignmentX(CENTER_ALIGNMENT);
		panelCondizioni.setBackground(new Color(220, 220, 220));

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
			panelStelleCondizioni.setBackground(new Color(220, 220, 220));
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
		nomeAnnuncioTextArea.setBackground(Color.orange);
		nomeAnnuncioTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		nomeAnnuncioTextArea.setPreferredSize(new Dimension(425, 100));
		nomeAnnuncioTextArea.setMaximumSize(new Dimension(425, 100));
		nomeAnnuncioTextArea.setEditable(false);
		nomeAnnuncioTextArea.setOpaque(true);
		nomeAnnuncioTextArea.setLineWrap(true);
		nomeAnnuncioTextArea.setWrapStyleWord(true);
		nomeAnnuncioTextArea.setAlignmentX(LEFT_ALIGNMENT);
		nomeAnnuncioTextArea.setFont(new Font("Ubuntu Sans", Font.BOLD, 21));
		panelNomeAnnuncio.add(nomeAnnuncioTextArea);
		
		MyJPanel panelDescrizione = new MyJPanel();
		panelDescrizione.setLayout(new BoxLayout(panelDescrizione, BoxLayout.PAGE_AXIS));
		panelDescrizione.setPreferredSize(new Dimension(425, 300));
		panelDescrizione.setMaximumSize(new Dimension(425, 300));
		JTextArea descrizioneAnnuncioTextArea = new JTextArea(annuncio.getOggettoInAnnuncio().getDescrizione());
		descrizioneAnnuncioTextArea.setBackground(Color.orange);
		descrizioneAnnuncioTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		descrizioneAnnuncioTextArea.setPreferredSize(new Dimension(425, 100));
		descrizioneAnnuncioTextArea.setMaximumSize(new Dimension(425, 100));
		descrizioneAnnuncioTextArea.setEditable(false);
		descrizioneAnnuncioTextArea.setOpaque(true);
		descrizioneAnnuncioTextArea.setLineWrap(true);
		descrizioneAnnuncioTextArea.setWrapStyleWord(true);
		descrizioneAnnuncioTextArea.setAlignmentX(LEFT_ALIGNMENT);
		descrizioneAnnuncioTextArea.setFont(new Font("Ubuntu Sans", Font.BOLD, 21));
		panelDescrizione.add(descrizioneAnnuncioTextArea);
		
		MyJPanel panelModalitaConsegna = this.creaPanelModalitaConsegna(annuncio);
		
		panelDescrizioneAnnuncio.add(panelNomeAnnuncio, BorderLayout.NORTH);
		panelDescrizioneAnnuncio.add(panelDescrizione, BorderLayout.CENTER);
		panelDescrizioneAnnuncio.add(panelModalitaConsegna, BorderLayout.SOUTH);
		
		panelDescrizioneAnnuncio.setBackground(Color.white);
		
		return panelDescrizioneAnnuncio;
	}
	
	private MyJPanel creaPanelFaiOfferta(Annuncio annuncio) {
		MyJPanel panelFaiOfferta = new MyJPanel();
		panelFaiOfferta.setLayout(new BoxLayout(panelFaiOfferta, BoxLayout.X_AXIS));
		panelFaiOfferta.setAlignmentX(CENTER_ALIGNMENT);
		panelFaiOfferta.setPreferredSize(new Dimension(425, 50));
		panelFaiOfferta.setMaximumSize(new Dimension(425, 50));
		panelFaiOfferta.setBackground(Color.white);
		
		JButton bottoneFaiOfferta = new JButton("Fai un'offerta!");
		bottoneFaiOfferta.setBackground(new Color(65, 106, 144));
		bottoneFaiOfferta.setForeground(Color.white);
		bottoneFaiOfferta.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		bottoneFaiOfferta.setAlignmentX(CENTER_ALIGNMENT);
		
		MyJLabel lblInterazioni = new MyJLabel(String.valueOf(annuncio.getNumeroInterazioni()));
		lblInterazioni.aggiungiImmagineScalata("images/iconaMiPiace.png", 25, 25, false);
		
		lblInterazioni.setForeground(Color.red);
		lblInterazioni.setHorizontalTextPosition(SwingConstants.RIGHT);
		
		panelFaiOfferta.add(Box.createVerticalGlue());
		panelFaiOfferta.add(Box.createHorizontalGlue());
		panelFaiOfferta.add(bottoneFaiOfferta);
		panelFaiOfferta.add(Box.createHorizontalGlue());
		panelFaiOfferta.add(lblInterazioni);
		panelFaiOfferta.add(Box.createHorizontalGlue());
		panelFaiOfferta.add(Box.createVerticalGlue());
		
		return panelFaiOfferta;
	}
	
	private MyJPanel creaPanelModalitaConsegna(Annuncio annuncio) {
		MyJPanel panelModalitaConsegna = new MyJPanel();
		panelModalitaConsegna.setPreferredSize(new Dimension(425, 50));
		panelModalitaConsegna.setMaximumSize(new Dimension(425, 50));
		panelModalitaConsegna.setLayout(new BoxLayout(panelModalitaConsegna, BoxLayout.X_AXIS));
		panelModalitaConsegna.setAlignmentX(CENTER_ALIGNMENT);
		panelModalitaConsegna.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, MyJPanel.uninaColorClicked));
		panelModalitaConsegna.setBackground(Color.white);
		
		MyJPanel panelSpedizione = new MyJPanel();
		panelSpedizione.setPreferredSize(new Dimension(60, 30));
		panelSpedizione.setMaximumSize(new Dimension(60, 30));
		panelSpedizione.setBackground(Color.white);
		MyJLabel lblSpedizione = new MyJLabel();
		lblSpedizione.aggiungiImmagineScalata("images/iconaSpedizione.png", 25, 25, false);
		panelSpedizione.add(lblSpedizione);
		if(annuncio.isSpedizione()) {
			MyJLabel lblPrevista = new MyJLabel();
			lblPrevista.aggiungiImmagineScalata("images/iconaCheckVerde.png", 25, 25, false);
			panelSpedizione.add(lblPrevista);
		}
		else {
			MyJLabel lblNonPrevista = new MyJLabel();
			lblNonPrevista.aggiungiImmagineScalata("images/iconaXRossa.png", 25, 25, false);
			panelSpedizione.add(lblNonPrevista);
		}
		
		MyJPanel panelRitiroInPosta = new MyJPanel();
		panelRitiroInPosta.setPreferredSize(new Dimension(60, 30));
		panelRitiroInPosta.setMaximumSize(new Dimension(60, 30));
		panelRitiroInPosta.setBackground(Color.white);
		MyJLabel lblRitiroInPosta = new MyJLabel();
		lblRitiroInPosta.aggiungiImmagineScalata("images/iconaRitiroInPosta.png", 25, 25, false);
		panelRitiroInPosta.add(lblRitiroInPosta);
		if(annuncio.isRitiroInPosta()) {
			MyJLabel lblPrevista = new MyJLabel();
			lblPrevista.aggiungiImmagineScalata("images/iconaCheckVerde.png", 25, 25, false);
			panelRitiroInPosta.add(lblPrevista);
		}
		else {
			MyJLabel lblNonPrevista = new MyJLabel();
			lblNonPrevista.aggiungiImmagineScalata("images/iconaXRossa.png", 25, 25, false);
			panelRitiroInPosta.add(lblNonPrevista);
		}
		
		MyJPanel panelIncontro = new MyJPanel();
		panelIncontro.setPreferredSize(new Dimension(60, 30));
		panelIncontro.setMaximumSize(new Dimension(60, 30));
		panelIncontro.setBackground(Color.white);
		MyJLabel lblIncontro = new MyJLabel();
		lblIncontro.aggiungiImmagineScalata("images/iconaIncontro.png", 25, 25, false);
		panelIncontro.add(lblIncontro);
		if(annuncio.isIncontro()) {
			String stringaPerToolTip = "<html>Sono disposto ad un incontro di persona secondo le seguenti disponibilità: <br>";
			
			for(int i = 0; i < annuncio.getGiornoIncontro().size(); i++) {
				stringaPerToolTip += " - ";
				stringaPerToolTip += annuncio.getGiornoIncontro().get(i) + ", dalle ";
				stringaPerToolTip += annuncio.getOraInizioIncontro().get(i) + " alle ";
				stringaPerToolTip += annuncio.getOraFineIncontro().get(i) + ", a ";
				stringaPerToolTip += annuncio.getSedeIncontroProposte().get(i).getNome() + "; <br>";
			}
						
			stringaPerToolTip += " </html>";
			lblIncontro.setToolTipText(stringaPerToolTip);
			
			MyJLabel lblPrevista = new MyJLabel();
			lblPrevista.aggiungiImmagineScalata("images/iconaCheckVerde.png", 25, 25, false);
			panelIncontro.add(lblPrevista);
		}
		else {
			MyJLabel lblNonPrevista = new MyJLabel();
			lblNonPrevista.aggiungiImmagineScalata("images/iconaXRossa.png", 25, 25, false);			
			panelIncontro.add(lblNonPrevista);
		}
		
		panelModalitaConsegna.add(Box.createVerticalGlue());
		panelModalitaConsegna.add(Box.createHorizontalGlue());
		panelModalitaConsegna.add(panelSpedizione);
		panelModalitaConsegna.add(Box.createHorizontalGlue());
		panelModalitaConsegna.add(panelRitiroInPosta);
		panelModalitaConsegna.add(Box.createHorizontalGlue());
		panelModalitaConsegna.add(panelIncontro);
		panelModalitaConsegna.add(Box.createHorizontalGlue());
		panelModalitaConsegna.add(Box.createVerticalGlue());
		
		return panelModalitaConsegna;
	}
}
