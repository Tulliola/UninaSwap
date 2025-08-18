package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.Annuncio;
import dto.OffertaScambio;
import dto.SedeUniversita;
import dto.UfficioPostale;
import dto.Oggetto;
import eccezioni.OffertaScambioException;
import eccezioni.PrezzoOffertoException;
import eccezioni.ResidenzaException;
import eccezioni.SaldoException;
import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.MyJButton;
import utilities.MyJDialog;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;

public class DialogOffertaScambio extends MyJDialog {
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPane = new MyJPanel();
	
	private MyJPanel panelProposteVenditore = new MyJPanel();
	private MyJPanel panelDatiProposte;
	private MyJPanel panelModalitaConsegnaProposte;
	
	private MyJPanel panelLaMiaOfferta = new MyJPanel();
	private MyJPanel panelMieProposte = new MyJPanel();
	private MyJPanel sottoPanelIncontro;
	private MyJPanel sottoPanelRitiroInPosta;
	private MyJPanel sottoPanelSpedizione;
	
	private MyJPanel sottoPanelAttivato = new MyJPanel();

	private MyJPanel panelBottoni = new MyJPanel();
	
	private Controller mainController;
	private MyJTextField inserisciIndirizzoTextField;
	private ButtonGroup modalitaSceltaBG;
	private JTextArea inserisciNotaTextArea;
	
	// Label di errore spedizione
	private MyJLabel lblErroreCaricamentoOggetti;
	private MyJLabel lblErroreSpedizione;
	
	private JComboBox<UfficioPostale> ufficiPostaliCB;
	private ButtonGroup incontriBG;
	
	private int numeroOggettiCaricati = 0;
	private MyJLabel[] lblCaricaOggetto = new MyJLabel[3];
	private String defaultStringPerCaricaOggettoLbl = "Carica il tuo oggetto!";
	
	private boolean isOggettoCaricato[] = new boolean[3];
	
	public DialogOffertaScambio(Annuncio annuncioPerOfferta, Controller controller) {
		mainController = controller;
		
		this.setSize(1200, 800);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle(mainController.getUtenteLoggato().getUsername() + ", crea ora la tua offerta!");
		this.setModal(true);
		this.settaContentPane(annuncioPerOfferta);
	}
	
	private void settaContentPane(Annuncio annuncioPerOfferta) {
		contentPane.setLayout(new BorderLayout());
		
		this.settaPanelProposteVenditore(annuncioPerOfferta);
		this.settaPanelLaMiaOfferta(annuncioPerOfferta);
		
		contentPane.add(panelProposteVenditore, BorderLayout.WEST);
		contentPane.add(panelLaMiaOfferta, BorderLayout.CENTER);
		
		this.setContentPane(contentPane);
	}
	
	private void settaPanelProposteVenditore(Annuncio annuncioPerOfferta) {
		panelProposteVenditore.setBackground(Color.red);
		panelProposteVenditore.setLayout(new BorderLayout());
		panelProposteVenditore.setPreferredSize(new Dimension(this.getWidth()/2, this.getHeight()));
		panelProposteVenditore.setMaximumSize(new Dimension(this.getWidth()/2, this.getHeight()));
		panelProposteVenditore.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));
		
		MyJPanel panelSuperiore = new MyJPanel();
		panelSuperiore.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelSuperiore.setBackground(MyJPanel.uninaColorClicked);
		MyJLabel lblLeProposte = new MyJLabel("Le proposte del venditore ", new Font("Ubuntu Sans", Font.BOLD, 25));
		lblLeProposte.setForeground(Color.white);
		lblLeProposte.setAlignmentX(CENTER_ALIGNMENT);
		panelSuperiore.add(lblLeProposte);
		
		panelProposteVenditore.add(panelSuperiore, BorderLayout.NORTH);
		panelProposteVenditore.add(this.creaPanelDatiProposte(annuncioPerOfferta), BorderLayout.CENTER);
	}

	private MyJPanel creaPanelDatiProposte(Annuncio annuncioPerOfferta) {
		panelDatiProposte = new MyJPanel();
		panelDatiProposte.setBackground(Color.white);
		panelDatiProposte.setPreferredSize(new Dimension(this.panelProposteVenditore.getPreferredSize().width, this.getHeight()));
		panelDatiProposte.setMaximumSize(new Dimension(this.panelProposteVenditore.getPreferredSize().width, this.getHeight()));
		panelDatiProposte.setAlignmentX(CENTER_ALIGNMENT);
		panelDatiProposte.setLayout(new BoxLayout(panelDatiProposte, BoxLayout.Y_AXIS));
		
		MyJPanel panelNotaScambio = new MyJPanel();
		panelNotaScambio.setLayout(new BoxLayout(panelNotaScambio, BoxLayout.Y_AXIS));
		panelNotaScambio.setBackground(Color.white);
		panelNotaScambio.setAlignmentX(CENTER_ALIGNMENT);
		panelNotaScambio.setPreferredSize(new Dimension(this.panelProposteVenditore.getPreferredSize().width-10, 200));
		panelNotaScambio.setMaximumSize(new Dimension(this.panelProposteVenditore.getPreferredSize().width-10, 200));
		MyJLabel lblNotaScambio = new MyJLabel(mainController.getUtenteLoggato().getUsername() + ", per questo articolo vorrei...");
		lblNotaScambio.aggiungiImmagineScalata("images/iconaFrecceScambio.png", 25, 25, false);
		lblNotaScambio.setAlignmentX(CENTER_ALIGNMENT);
		JTextArea notaScambioTextA = new JTextArea();
		notaScambioTextA.setText(annuncioPerOfferta.getNotaScambio());
		notaScambioTextA.setEditable(false);
		notaScambioTextA.setOpaque(false);
		notaScambioTextA.setLineWrap(true);
		notaScambioTextA.setPreferredSize(new Dimension(this.panelDatiProposte.getPreferredSize().width-10, 100));
		notaScambioTextA.setMaximumSize(new Dimension(this.panelDatiProposte.getPreferredSize().width-10, 100));
		notaScambioTextA.setBorder(new EmptyBorder(5, 5, 5, 5));
		notaScambioTextA.setEnabled(false);
		notaScambioTextA.setDisabledTextColor(Color.black);
		
		panelNotaScambio.add(lblNotaScambio);
		panelNotaScambio.add(notaScambioTextA);
		
		panelDatiProposte.add(Box.createVerticalGlue());
		panelDatiProposte.add(panelNotaScambio);
		panelDatiProposte.add(this.creaPanelModalitaConsegnaProposte(annuncioPerOfferta));
		
		return panelDatiProposte;
	}
	
	private MyJPanel creaPanelModalitaConsegnaProposte(Annuncio annuncioPerOfferta) {
		panelModalitaConsegnaProposte = new MyJPanel();
		panelModalitaConsegnaProposte.setBackground(Color.white);
		panelModalitaConsegnaProposte.setLayout(new BoxLayout(panelModalitaConsegnaProposte, BoxLayout.Y_AXIS));
		
		MyJPanel panelSpedizione = new MyJPanel();
		panelSpedizione.setLayout(new BoxLayout(panelSpedizione, BoxLayout.Y_AXIS));
		panelSpedizione.setBackground(Color.white);
		panelSpedizione.setAlignmentX(CENTER_ALIGNMENT);
		MyJLabel lblSpedizione = new MyJLabel();
		lblSpedizione.setAlignmentX(CENTER_ALIGNMENT);
		MyJLabel iconaSpedizione = new MyJLabel("Spedizione");
		iconaSpedizione.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		iconaSpedizione.setAlignmentX(CENTER_ALIGNMENT);
		iconaSpedizione.aggiungiImmagineScalata("images/iconaSpedizione.png", 25, 25, false);
		if(annuncioPerOfferta.isSpedizione()) {
			lblSpedizione.setText("Se ti è più comodo, posso spedirti l'articolo a casa!");
			lblSpedizione.aggiungiImmagineScalata("images/iconaCheckVerde.png", 25, 25, false);
		}
		else {
			lblSpedizione.setText("Mi dispiace, ma non spedisco questo articolo.");
			lblSpedizione.aggiungiImmagineScalata("images/iconaXRossa.png", 25, 25, false);
		}
		panelSpedizione.add(iconaSpedizione);
		panelSpedizione.add(lblSpedizione);
		
		MyJPanel panelRitiroInPosta = new MyJPanel();
		panelRitiroInPosta.setLayout(new BoxLayout(panelRitiroInPosta, BoxLayout.Y_AXIS));
		panelRitiroInPosta.setBackground(Color.white);
		MyJLabel lblRitiroInPosta = new MyJLabel();
		lblRitiroInPosta.setAlignmentX(CENTER_ALIGNMENT);
		MyJLabel iconaRitiroInPosta = new MyJLabel("Ritiro in posta");
		iconaRitiroInPosta.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		iconaRitiroInPosta.setAlignmentX(CENTER_ALIGNMENT);
		iconaRitiroInPosta.aggiungiImmagineScalata("images/iconaRitiroInPosta.png", 25, 25, false);
		if(annuncioPerOfferta.isRitiroInPosta()) {
			lblRitiroInPosta.setText("Se ti è più comodo, puoi indicare l'ufficio postale dove recapitare l'articolo!");
			lblRitiroInPosta.aggiungiImmagineScalata("images/iconaCheckVerde.png", 25, 25, false);
		}
		else {
			lblRitiroInPosta.setText("Mi dispiace, ma non posso portare l'articolo in un ufficio postale.");
			lblRitiroInPosta.aggiungiImmagineScalata("images/iconaXRossa.png", 25, 25, false);
		}
		panelRitiroInPosta.add(iconaRitiroInPosta);
		panelRitiroInPosta.add(lblRitiroInPosta);
		
		MyJPanel panelIncontro = new MyJPanel();
		panelIncontro.setLayout(new BoxLayout(panelIncontro, BoxLayout.Y_AXIS));
		panelIncontro.setBackground(Color.white);
		panelIncontro.setPreferredSize(new Dimension(this.panelDatiProposte.getPreferredSize().width-10, 250));
		panelIncontro.setMaximumSize(new Dimension(this.panelDatiProposte.getPreferredSize().width-10, 250));
		panelIncontro.setAlignmentX(CENTER_ALIGNMENT);
		MyJLabel lblIncontro = new MyJLabel();
		lblIncontro.setAlignmentX(CENTER_ALIGNMENT);
		MyJLabel iconaIncontro = new MyJLabel("Incontro");
		iconaIncontro.setAlignmentX(CENTER_ALIGNMENT);
		iconaIncontro.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		iconaIncontro.aggiungiImmagineScalata("images/iconaIncontro.png", 25, 25, false);
		if(annuncioPerOfferta.isIncontro()) {
			lblIncontro.setText("Se ti è più comodo, ci possiamo incontrare da vicino per ultimare l'accordo!");
			lblIncontro.aggiungiImmagineScalata("images/iconaCheckVerde.png", 25, 25, false);
		}
		else {
			lblIncontro.setText("Mi dispiace, ma non sono disponibile ad un incontro.");
			lblIncontro.aggiungiImmagineScalata("images/iconaXRossa.png", 25, 25, false);
		}
		panelIncontro.add(iconaIncontro);
		panelIncontro.add(lblIncontro);
		
		if(annuncioPerOfferta.isIncontro()) {
			MyJLabel lblDisponibilita = new MyJLabel("Ecco le mie disponibilità:");
			lblDisponibilita.setAlignmentX(CENTER_ALIGNMENT);
			
			panelIncontro.add(Box.createRigidArea(new Dimension(0, 10)));
			panelIncontro.add(lblDisponibilita);
			
			JTextArea disponibilitaTextArea = new JTextArea();
			disponibilitaTextArea.setEditable(false);
			disponibilitaTextArea.setOpaque(false);
			disponibilitaTextArea.setLineWrap(true);
			disponibilitaTextArea.setPreferredSize(new Dimension(this.panelDatiProposte.getPreferredSize().width-10, 100));
			disponibilitaTextArea.setMaximumSize(new Dimension(this.panelDatiProposte.getPreferredSize().width-10, 100));
			disponibilitaTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
			disponibilitaTextArea.setEnabled(false);
			disponibilitaTextArea.setDisabledTextColor(Color.black);
			String incontri = "";
			
			for(int i = 0; i < annuncioPerOfferta.getGiornoIncontro().size(); i++) {
				incontri += annuncioPerOfferta.getGiornoIncontro().get(i).toString();
				incontri += ", dalle " + annuncioPerOfferta.getOraInizioIncontro().get(i);
				incontri += " alle " + annuncioPerOfferta.getOraFineIncontro().get(i);
				incontri += " a " + annuncioPerOfferta.getSedeIncontroProposte().get(i);
				incontri += ";\n";
			}
						
			disponibilitaTextArea.setText(incontri);
			panelIncontro.add(disponibilitaTextArea);
		}
		
		panelModalitaConsegnaProposte.add(Box.createVerticalGlue());
		panelModalitaConsegnaProposte.add(panelSpedizione);
		panelModalitaConsegnaProposte.add(Box.createVerticalGlue());
		panelModalitaConsegnaProposte.add(panelRitiroInPosta);
		panelModalitaConsegnaProposte.add(Box.createVerticalGlue());
		panelModalitaConsegnaProposte.add(panelIncontro);
		panelModalitaConsegnaProposte.add(Box.createVerticalGlue());

		return panelModalitaConsegnaProposte;
	}
	
	private void settaPanelLaMiaOfferta(Annuncio annuncioPerOfferta) {
		panelLaMiaOfferta.setBackground(MyJPanel.uninaLightColor);
		panelLaMiaOfferta.setLayout(new BorderLayout());
		panelLaMiaOfferta.setPreferredSize(new Dimension(this.getWidth()/2, this.getHeight()));
		panelLaMiaOfferta.setMaximumSize(new Dimension(this.getWidth()/2, this.getHeight()));
		panelLaMiaOfferta.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.black));
		
		MyJPanel panelSuperiore = new MyJPanel();
		panelSuperiore.setBackground(MyJPanel.uninaColorClicked);
		panelSuperiore.setLayout(new FlowLayout(FlowLayout.CENTER));
		MyJLabel lblLaMiaOfferta = new MyJLabel("La mia offerta", new Font("Ubuntu Sans", Font.BOLD, 25));
		lblLaMiaOfferta.setForeground(Color.white);
		panelSuperiore.add(lblLaMiaOfferta);
		
		panelLaMiaOfferta.add(panelSuperiore, BorderLayout.NORTH);
		panelLaMiaOfferta.add(this.creaPanelMieProposte(annuncioPerOfferta), BorderLayout.CENTER);
		panelLaMiaOfferta.add(this.creaPanelBottoni(annuncioPerOfferta), BorderLayout.SOUTH);
	}
	

	private MyJPanel creaPanelMieProposte(Annuncio annuncioPerOfferta) {
		panelMieProposte = new MyJPanel();
		panelMieProposte.setLayout(new BorderLayout());
		
		panelMieProposte.add(this.creaPanelCaricamentoOggetti(annuncioPerOfferta), BorderLayout.NORTH);
		panelMieProposte.add(this.creaPanelModalitaConsegnaScelta(annuncioPerOfferta), BorderLayout.CENTER);
		panelMieProposte.add(this.creaPanelNotaOfferta(annuncioPerOfferta), BorderLayout.SOUTH);
		
		return panelMieProposte;
	}
	
	private MyJPanel creaPanelCaricamentoOggetti(Annuncio annuncioPerOfferta) {
		MyJPanel panelCaricamentoOggetti = new MyJPanel();
		panelCaricamentoOggetti.setLayout(new BoxLayout(panelCaricamentoOggetti, BoxLayout.Y_AXIS));
		panelCaricamentoOggetti.setAlignmentX(CENTER_ALIGNMENT);
		panelCaricamentoOggetti.setPreferredSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width, 200));
		panelCaricamentoOggetti.setMaximumSize(new Dimension(this.panelLaMiaOfferta.getMaximumSize().width, 200));
		
		MyJPanel panelWrapper = new MyJPanel();
		panelWrapper.setAlignmentX(CENTER_ALIGNMENT);
		panelWrapper.setLayout(new BoxLayout(panelWrapper, BoxLayout.Y_AXIS));
		panelWrapper.setBackground(MyJPanel.uninaLightColor);
		
		MyJLabel lblCaricaOggetti = new MyJLabel(annuncioPerOfferta.getUtenteProprietario().getUsername() + ", ecco gli oggetti che potrei scambiarti: ");
		lblCaricaOggetti.aggiungiImmagineScalata("images/iconaFrecceScambio.png", 25, 25, false);
		lblCaricaOggetto[0] = new MyJLabel(this.defaultStringPerCaricaOggettoLbl);
		lblCaricaOggetto[0].setAlignmentX(CENTER_ALIGNMENT);
		lblCaricaOggetto[0].aggiungiImmagineScalata("images/iconaAggiungiAnnuncio.png", 25, 25, true);
		lblCaricaOggetto[0].rendiLabelInteragibile();
		lblCaricaOggetto[0].setOnMouseEnteredAction(() -> {});
		lblCaricaOggetto[0].setOnMouseExitedAction(() -> {});
		lblCaricaOggetto[0].setOnMouseClickedAction(() -> {mainController.passaAFrameCaricaOggetto(0);});
		
		lblCaricaOggetto[1] = new MyJLabel(this.defaultStringPerCaricaOggettoLbl);
		lblCaricaOggetto[1].setAlignmentX(CENTER_ALIGNMENT);
		lblCaricaOggetto[1].aggiungiImmagineScalata("images/iconaAggiungiAnnuncio.png", 25, 25, true);
		lblCaricaOggetto[1].rendiLabelInteragibile();
		lblCaricaOggetto[1].setOnMouseEnteredAction(() -> {});
		lblCaricaOggetto[1].setOnMouseExitedAction(() -> {});
		lblCaricaOggetto[1].setOnMouseClickedAction(() -> {mainController.passaAFrameCaricaOggetto(1);});
		
		lblCaricaOggetto[2] = new MyJLabel(this.defaultStringPerCaricaOggettoLbl);
		lblCaricaOggetto[2].setAlignmentX(CENTER_ALIGNMENT);
		lblCaricaOggetto[2].aggiungiImmagineScalata("images/iconaAggiungiAnnuncio.png", 25, 25, true);
		lblCaricaOggetto[2].rendiLabelInteragibile();
		lblCaricaOggetto[2].setOnMouseEnteredAction(() -> {});
		lblCaricaOggetto[2].setOnMouseExitedAction(() -> {});
		lblCaricaOggetto[2].setOnMouseClickedAction(() -> {mainController.passaAFrameCaricaOggetto(2);});
		
		lblErroreCaricamentoOggetti = new MyJLabel(true);
		lblErroreCaricamentoOggetti.setAlignmentX(CENTER_ALIGNMENT);
		
		panelWrapper.add(lblCaricaOggetti);
		panelCaricamentoOggetti.add(Box.createVerticalGlue());
		panelCaricamentoOggetti.add(panelWrapper);
		panelCaricamentoOggetti.add(lblCaricaOggetto[0]);
		panelCaricamentoOggetti.add(lblCaricaOggetto[1]);
		panelCaricamentoOggetti.add(lblCaricaOggetto[2]);
		panelCaricamentoOggetti.add(lblErroreCaricamentoOggetti);
		panelCaricamentoOggetti.setBackground(MyJPanel.uninaLightColor);
		panelCaricamentoOggetti.add(Box.createVerticalGlue());
		
		return panelCaricamentoOggetti;
	}
	
	private MyJPanel creaPanelModalitaConsegnaScelta(Annuncio annuncioPerOfferta) {
		MyJPanel panelModalitaConsegnaScelta = new MyJPanel();
		panelModalitaConsegnaScelta.setBackground(MyJPanel.uninaLightColor);
		panelModalitaConsegnaScelta.setLayout(new BoxLayout(panelModalitaConsegnaScelta, BoxLayout.Y_AXIS));
		panelModalitaConsegnaScelta.setAlignmentX(CENTER_ALIGNMENT);
		panelModalitaConsegnaScelta.setPreferredSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width-50, 300));
		panelModalitaConsegnaScelta.setMaximumSize(new Dimension(this.panelLaMiaOfferta.getMaximumSize().width-50, 300));

		MyJPanel sottoPanelModalitaScelta = new MyJPanel();
		sottoPanelModalitaScelta.setLayout(new BoxLayout(sottoPanelModalitaScelta, BoxLayout.Y_AXIS));
		sottoPanelModalitaScelta.setAlignmentX(CENTER_ALIGNMENT);
		sottoPanelModalitaScelta.setBackground(MyJPanel.uninaLightColor);
		MyJLabel lblModalitaScelta = new MyJLabel("Tra le modalità di consegna che hai proposto, preferirei...");
		lblModalitaScelta.setAlignmentX(LEFT_ALIGNMENT);
		
		JRadioButton spedizioneRB = new JRadioButton("Spedizione");
		this.settaRadioButton(spedizioneRB, () -> {
			sottoPanelAttivato.setVisible(false);
			sottoPanelAttivato = sottoPanelSpedizione;
			sottoPanelSpedizione.setVisible(true);
		});
		spedizioneRB.setActionCommand("Spedizione");
		
		JRadioButton ritiroInPostaRB = new JRadioButton("Ritiro in posta");
		this.settaRadioButton(ritiroInPostaRB, () -> {
			sottoPanelAttivato.setVisible(false);
			sottoPanelAttivato = sottoPanelRitiroInPosta;
			sottoPanelRitiroInPosta.setVisible(true);
		});
		ritiroInPostaRB.setActionCommand("Ritiro in posta");

		JRadioButton incontroRB = new JRadioButton("Incontro");
		this.settaRadioButton(incontroRB, () -> {
			sottoPanelAttivato.setVisible(false);
			sottoPanelAttivato = sottoPanelIncontro;
			sottoPanelIncontro.setVisible(true);
		});
		incontroRB.setActionCommand("Incontro");
		
		sottoPanelModalitaScelta.add(lblModalitaScelta);

		modalitaSceltaBG = new ButtonGroup();
		JRadioButton primaModalitaInserita = null;;
		
		if(annuncioPerOfferta.isSpedizione()) {
			primaModalitaInserita = spedizioneRB;
			modalitaSceltaBG.add(spedizioneRB);
			sottoPanelModalitaScelta.add(spedizioneRB);
		}
	
		if(annuncioPerOfferta.isRitiroInPosta()) {
			if(primaModalitaInserita == null)
				primaModalitaInserita = ritiroInPostaRB;
			modalitaSceltaBG.add(ritiroInPostaRB);		
			sottoPanelModalitaScelta.add(ritiroInPostaRB);
		}
	
		if(annuncioPerOfferta.isIncontro()) {
			if(primaModalitaInserita == null)
				primaModalitaInserita = incontroRB;
			modalitaSceltaBG.add(incontroRB);
			sottoPanelModalitaScelta.add(incontroRB);
		}		
				
		sottoPanelSpedizione = new MyJPanel();
		sottoPanelSpedizione.setLayout(new BoxLayout(sottoPanelSpedizione, BoxLayout.Y_AXIS));
		sottoPanelSpedizione.setAlignmentX(CENTER_ALIGNMENT);
		sottoPanelSpedizione.setBackground(MyJPanel.uninaLightColor);
		MyJLabel lblSpedizione = new MyJLabel("Questo è l'indirizzo a cui spedire: ");
		lblSpedizione.aggiungiImmagineScalata("images/iconaCasa.png", 25, 25, false);
		lblSpedizione.setAlignmentX(LEFT_ALIGNMENT);
		inserisciIndirizzoTextField = new MyJTextField(mainController.getUtenteLoggato().getResidenza());
		inserisciIndirizzoTextField.setFont(new Font("Ubuntu Sans", Font.BOLD, 13));
		inserisciIndirizzoTextField.setAlignmentX(LEFT_ALIGNMENT);
		inserisciIndirizzoTextField.setPreferredSize(new Dimension (300, 25));		
		inserisciIndirizzoTextField.setMaximumSize(new Dimension (300, 25));
		inserisciIndirizzoTextField.setBorder(new EmptyBorder(5, 5, 5, 5));
		lblErroreSpedizione = new MyJLabel(true);
		lblErroreSpedizione.setAlignmentX(LEFT_ALIGNMENT);
		sottoPanelSpedizione.setVisible(false);
		
		sottoPanelSpedizione.add(lblSpedizione);
		sottoPanelSpedizione.add(inserisciIndirizzoTextField);
		sottoPanelSpedizione.add(lblErroreSpedizione);
		
		
		sottoPanelRitiroInPosta = new MyJPanel();
		sottoPanelRitiroInPosta.setLayout(new BoxLayout(sottoPanelRitiroInPosta, BoxLayout.Y_AXIS));
		sottoPanelRitiroInPosta.setAlignmentX(CENTER_ALIGNMENT);
		sottoPanelRitiroInPosta.setBackground(MyJPanel.uninaLightColor);
		MyJLabel lblRitiroInPosta = new MyJLabel("L'ufficio postale in cui preferirei ritirare l'articolo è: ");
		lblRitiroInPosta.aggiungiImmagineScalata("images/iconaUfficioPostale.png", 25, 25, false);	
		lblRitiroInPosta.setAlignmentX(LEFT_ALIGNMENT);
		
		ufficiPostaliCB = new JComboBox<UfficioPostale>();
		ufficiPostaliCB.setFont(new Font("Ubuntu Sans", Font.BOLD, 13));
		ufficiPostaliCB.setAlignmentX(LEFT_ALIGNMENT);
		ufficiPostaliCB.setPreferredSize(new Dimension(500, 30));
		ufficiPostaliCB.setMaximumSize(new Dimension(500, 30));
		
		for(UfficioPostale ufficioCorrente: mainController.getUfficiPostali())
			ufficiPostaliCB.addItem(ufficioCorrente);
		
		ufficiPostaliCB.setSelectedIndex(0);
				
		sottoPanelRitiroInPosta.add(lblRitiroInPosta);
		sottoPanelRitiroInPosta.add(ufficiPostaliCB);
		sottoPanelRitiroInPosta.setVisible(false);
		

		sottoPanelIncontro = new MyJPanel();
		sottoPanelIncontro.setLayout(new BoxLayout(sottoPanelIncontro, BoxLayout.Y_AXIS));
		sottoPanelIncontro.setAlignmentX(CENTER_ALIGNMENT);
		sottoPanelIncontro.setBackground(MyJPanel.uninaLightColor);
		MyJLabel lblIncontro = new MyJLabel("Va bene, incontriamoci ");
		lblIncontro.setAlignmentX(LEFT_ALIGNMENT);
		lblIncontro.aggiungiImmagineScalata("images/iconaStrettaDiMano.png", 25, 25, false);
		
		sottoPanelIncontro.add(lblIncontro);

		incontriBG = new ButtonGroup();
		JRadioButton primoIncontroInserito = null;
		
		for(int i = 0; i < annuncioPerOfferta.getSedeIncontroProposte().size(); i++) {
			JRadioButton incontro = new JRadioButton(annuncioPerOfferta.getIncontro(i));
			incontro.putClientProperty("Ora inizio", annuncioPerOfferta.getOraInizioIncontro().get(i).toString());
			incontro.putClientProperty("Ora fine", annuncioPerOfferta.getOraFineIncontro().get(i).toString());
			incontro.putClientProperty("Giorno", annuncioPerOfferta.getGiornoIncontro().get(i));
			incontro.putClientProperty("Sede", annuncioPerOfferta.getSedeIncontroProposte().get(i));
			
			incontro.setActionCommand("Opzione "+i);
			incontro.setFont(new Font("Ubuntu Sans", Font.BOLD, 13));
			incontro.setOpaque(false);
			incontriBG.add(incontro);
			
			if(primoIncontroInserito == null)
				primoIncontroInserito = incontro;
			sottoPanelIncontro.add(incontro);
		}
		sottoPanelIncontro.setVisible(false);
				
		primaModalitaInserita.doClick();
		
		if(annuncioPerOfferta.isIncontro())
			primoIncontroInserito.doClick();
		
		panelModalitaConsegnaScelta.add(sottoPanelModalitaScelta);
		panelModalitaConsegnaScelta.add(Box.createRigidArea(new Dimension(0, 10)));
		panelModalitaConsegnaScelta.add(sottoPanelSpedizione);
		panelModalitaConsegnaScelta.add(Box.createRigidArea(new Dimension(0, 10)));
		panelModalitaConsegnaScelta.add(sottoPanelRitiroInPosta);
		panelModalitaConsegnaScelta.add(Box.createRigidArea(new Dimension(0, 10)));
		panelModalitaConsegnaScelta.add(sottoPanelIncontro);
		panelModalitaConsegnaScelta.add(Box.createRigidArea(new Dimension(0, 10)));

		return panelModalitaConsegnaScelta;
	}
	
	private MyJPanel creaPanelNotaOfferta(Annuncio annuncioPerOfferta) {
		MyJPanel panelNotaOfferta = new MyJPanel();
		panelNotaOfferta.setLayout(new BorderLayout());
		
		MyJPanel panelSuperiore = new MyJPanel();
		panelSuperiore.setBackground(MyJPanel.uninaColorClicked);
		panelSuperiore.setLayout(new FlowLayout(FlowLayout.CENTER));
		MyJLabel lblTitolo = new MyJLabel("Hai qualcosa da indicare al venditore?");
		lblTitolo.setForeground(Color.white);
		panelSuperiore.add(lblTitolo);
		
		MyJPanel panelCentrale = new MyJPanel();
		panelCentrale.setBackground(MyJPanel.uninaLightColor);
		inserisciNotaTextArea = new JTextArea("Ciao " + annuncioPerOfferta.getUtenteProprietario().getUsername() + ", ho visto il tuo annuncio \"" + annuncioPerOfferta.getNome() + "\".");
		inserisciNotaTextArea.setFont(new Font("Ubuntu Sans", Font.PLAIN, 13));
		inserisciNotaTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		inserisciNotaTextArea.setLineWrap(true);
		inserisciNotaTextArea.setWrapStyleWord(true);
		inserisciNotaTextArea.setPreferredSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width-50, 200));
		inserisciNotaTextArea.setMaximumSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width-50, 200));
		
		inserisciNotaTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				if(inserisciNotaTextArea.getText().length() > 300)
					ke.consume();
			}
		});
		
		panelCentrale.add(inserisciNotaTextArea);
		
		panelNotaOfferta.add(panelSuperiore, BorderLayout.NORTH);
		panelNotaOfferta.add(panelCentrale, BorderLayout.CENTER);
		
		return panelNotaOfferta;
	}
	
	private void settaRadioButton(JRadioButton rbToSet, Runnable azioneSeCliccato) {
		Font fontPerRadioButton = new Font("Ubuntu Sans", Font.BOLD, 15);

		Image imageUnselectedResized = (new ImageIcon("images/iconaUnselectedRadioButton.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		ImageIcon imageIconUnSelectedResized = new ImageIcon(imageUnselectedResized);
		
		Image imageSelectedResized = (new ImageIcon("images/iconaSelectedRadioButton.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		ImageIcon imageIconSelectedResized = new ImageIcon(imageSelectedResized);

		rbToSet.setFont(fontPerRadioButton);
		rbToSet.setIcon(imageIconUnSelectedResized);
		rbToSet.setSelectedIcon(imageIconSelectedResized);
		
		rbToSet.setOpaque(false);
		
		rbToSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				azioneSeCliccato.run();
			}
		});
	}
	

	private MyJPanel creaPanelBottoni(Annuncio annuncioPerOfferta) {
		MyJPanel panelBottoni = new MyJPanel();
		panelBottoni.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelBottoni.setBackground(Color.orange);
		panelBottoni.setPreferredSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width, 50));
		panelBottoni.setMaximumSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width, 50));
		panelBottoni.setBackground(new Color(220, 220, 220));
		
		MyJButton bottoneConfermaOfferta = new MyJButton("Conferma la mia offerta!");
		bottoneConfermaOfferta.setDefaultAction(() -> {this.clickBottoneConfermaOfferta(annuncioPerOfferta);});
		
		MyJButton bottoneCiHoRipensato = new MyJButton("Ci ho ripensato...");
		bottoneCiHoRipensato.setDefaultAction(() -> {mainController.passaAFrameHomePage(this);});
		
		panelBottoni.add(Box.createVerticalGlue());
		panelBottoni.add(bottoneCiHoRipensato);
		panelBottoni.add(bottoneConfermaOfferta);
		panelBottoni.add(Box.createVerticalGlue());
		
		return panelBottoni;
	}
	
	private void clickBottoneConfermaOfferta(Annuncio annuncioPerOfferta) {
		try {
			this.nascondiLabelErrore(this.lblErroreSpedizione, this.lblErroreCaricamentoOggetti);
			this.resettaBordiTextField(new EmptyBorder(5, 5, 5, 5), this.inserisciIndirizzoTextField);
			
			checkNumeroOggettiCaricati();
			if(this.modalitaSceltaBG.getSelection().getActionCommand().equals("Spedizione"))
				this.checkResidenza(this.inserisciIndirizzoTextField.getText());
			
			OffertaScambio newOfferta = this.organizzaDatiDaPassareAlController(annuncioPerOfferta);
			mainController.onConfermaOffertaButtonClicked(newOfferta);
			
		}
		catch(OffertaScambioException exc1) {
			lblErroreCaricamentoOggetti.setText(exc1.getMessage());
			lblErroreCaricamentoOggetti.setVisible(true);			
		}
		catch(ResidenzaException exc2) {
			this.settaLabelETextFieldDiErrore(lblErroreSpedizione, exc2.getMessage(), this.inserisciIndirizzoTextField);
		}
		catch(SQLException exc3) {
			System.out.println(exc3.getErrorCode());
			System.out.println(exc3.getMessage());
			System.out.println(exc3.getSQLState());
		}
	}

	private void checkResidenza(String indirizzoDiSpedizione) throws ResidenzaException{
		if(indirizzoDiSpedizione == null || indirizzoDiSpedizione.isBlank())
			throw new ResidenzaException("Il campo residenza è obbligatorio.");
		
		if(indirizzoDiSpedizione.startsWith(" "))
			throw new ResidenzaException("La residenza non può iniziare con uno spazio vuoto.");
		
		if(indirizzoDiSpedizione.endsWith(" "))
			throw new ResidenzaException("La residenza non può terminare con uno spazio vuoto.");
		
		if(indirizzoDiSpedizione.length() > 75)
			throw new ResidenzaException("La residenza deve essere di massimo 75 caratteri.");

	}
	
	private void checkNumeroOggettiCaricati() throws OffertaScambioException{
		if(this.numeroOggettiCaricati == 0)
			throw new OffertaScambioException("Devi caricare almeno un oggetto.");
	}
	
	private OffertaScambio organizzaDatiDaPassareAlController(Annuncio annuncioRiferito) {
		ModConsegnaEnum modalitaConsegnaScelta = ModConsegnaEnum.confrontaConStringa(modalitaSceltaBG.getSelection().getActionCommand());
		ArrayList<Oggetto> oggettiCaricati = new ArrayList<Oggetto>();
		
		for(int i = 0; i < isOggettoCaricato.length; i++)
			if(isOggettoCaricato[i]) {
				Oggetto oggettoCaricato = mainController.recuperaOggettoDaFrame(i);
				oggettiCaricati.add(oggettoCaricato);
			}
		
		OffertaScambio offertaToAdd = new OffertaScambio(mainController.getUtenteLoggato(), modalitaConsegnaScelta, annuncioRiferito, oggettiCaricati);
				
		if(modalitaConsegnaScelta.toString().equals("Spedizione"))
			offertaToAdd.setIndirizzoSpedizione(this.inserisciIndirizzoTextField.getText());
		else if(modalitaConsegnaScelta.toString().equals("Ritiro in posta"))
			offertaToAdd.setUfficioRitiro((UfficioPostale)this.ufficiPostaliCB.getSelectedItem());
		else {
			JRadioButton rbSelezionato = (JRadioButton)this.incontriBG.getSelection();
			String oraInizio = (String)rbSelezionato.getClientProperty("Ora inizio");
			String oraFine = (String)rbSelezionato.getClientProperty("Ora fine");
			GiornoEnum giornoIncontro = (GiornoEnum)rbSelezionato.getClientProperty("Giorno");
			SedeUniversita sedeIncontro = (SedeUniversita)rbSelezionato.getClientProperty("Sede");

			offertaToAdd.setOraInizioIncontro(oraInizio);
			offertaToAdd.setOraFineIncontro(oraFine);
			offertaToAdd.setGiornoIncontro(giornoIncontro);
			offertaToAdd.setSedeDIncontroScelta(sedeIncontro);
		}		
		
		offertaToAdd.setNota(this.inserisciNotaTextArea.getText());
		
		return offertaToAdd;
	}

	public void eliminaOggettoCaricato(int indiceDelFrameDaCuiRicevi) {
		this.lblCaricaOggetto[indiceDelFrameDaCuiRicevi].setText(this.defaultStringPerCaricaOggettoLbl);
		this.lblCaricaOggetto[indiceDelFrameDaCuiRicevi].aggiungiImmagineScalata("images/iconaAggiungiAnnuncio.png", 25, 25, true);
		this.numeroOggettiCaricati--;
		this.isOggettoCaricato[indiceDelFrameDaCuiRicevi] = false;
	}
	
	public void aggiungiOggettoCaricato(int indiceDelFrameDaCuiRicevi, String nomeOggetto) {
		this.lblCaricaOggetto[indiceDelFrameDaCuiRicevi].setText(nomeOggetto);
		this.lblCaricaOggetto[indiceDelFrameDaCuiRicevi].aggiungiImmagineScalata("images/iconModify.png", 25, 25, true);
		this.numeroOggettiCaricati++;
		this.isOggettoCaricato[indiceDelFrameDaCuiRicevi] = true;
	}
}
