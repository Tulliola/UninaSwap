package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.Annuncio;
import dto.AnnuncioRegalo;
import dto.AnnuncioVendita;
import dto.OffertaAcquisto;
import dto.SedeUniversita;
import dto.UfficioPostale;
import eccezioni.PrezzoOffertoException;
import eccezioni.ResidenzaException;
import eccezioni.SaldoException;
import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.MyJButton;
import utilities.MyJDialog;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;

public class DialogOffertaAcquisto extends MyJDialog {

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
	private MyJTextField inserisciPrezzoTextField;
	private MyJTextField inserisciIndirizzoTextField;
	private ButtonGroup modalitaSceltaBG;
	private JTextArea inserisciNotaTextArea;
	private MyJLabel lblErrorePrezzoOfferto;
	private MyJLabel lblErroreSpedizione;
	
	private JComboBox<UfficioPostale> ufficiPostaliCB;
	private ButtonGroup incontriBG;
	private MyJTextField inserisciMessaggioTextField;
	
	public DialogOffertaAcquisto(Annuncio annuncioPerOfferta, Controller controller) {
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
		MyJLabel lblLeProposte = new MyJLabel("Le proposte del venditore", new Font("Ubuntu Sans", Font.BOLD, 25));
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
	
		MyJPanel panelPrezzoIniziale = new MyJPanel();
		panelPrezzoIniziale.setBackground(Color.white);
		MyJLabel lblPrezzoIniziale = new MyJLabel();
		lblPrezzoIniziale.setAlignmentX(LEFT_ALIGNMENT);

		if(!(annuncioPerOfferta instanceof AnnuncioRegalo)) {
			lblPrezzoIniziale.setText(mainController.getUtenteLoggato().getUsername()+ ", il prezzo iniziale del mio articolo è " + annuncioPerOfferta.getPrezzoIniziale() + "€ ...");
			lblPrezzoIniziale.aggiungiImmagineScalata("images/iconaPrezzoIniziale.png", 25, 25, false);
			panelPrezzoIniziale.add(lblPrezzoIniziale);
			panelPrezzoIniziale.add(new MyJLabel("... ma sono disposto a trattare!"));
		}
		else {
			lblPrezzoIniziale.setText(mainController.getUtenteLoggato().getUsername()+ ", questo articolo è in regalo!");
			lblPrezzoIniziale.aggiungiImmagineScalata("images/iconaAnnuncioRegaloColored.png", 25, 25, false);
			panelPrezzoIniziale.add(lblPrezzoIniziale);
		}

		panelDatiProposte.add(Box.createVerticalGlue());
		panelDatiProposte.add(panelPrezzoIniziale);
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
		
		panelMieProposte.add(this.creaPanelPrezzoOfferto(annuncioPerOfferta), BorderLayout.NORTH);
		panelMieProposte.add(this.creaPanelModalitaConsegnaScelta(annuncioPerOfferta), BorderLayout.CENTER);
		panelMieProposte.add(this.creaPanelNotaOfferta(annuncioPerOfferta), BorderLayout.SOUTH);
		
		return panelMieProposte;
	}
	
	private MyJPanel creaPanelPrezzoOfferto(Annuncio annuncioPerOfferta) {
		MyJPanel panelPrezzoOfferto = new MyJPanel();
		panelPrezzoOfferto.setLayout(new BoxLayout(panelPrezzoOfferto, BoxLayout.Y_AXIS));
		panelPrezzoOfferto.setPreferredSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width, 100));
		panelPrezzoOfferto.setMaximumSize(new Dimension(this.panelLaMiaOfferta.getMaximumSize().width, 100));
		panelPrezzoOfferto.setBackground(MyJPanel.uninaLightColor);

		MyJPanel panelWrapper = new MyJPanel();
		panelWrapper.setLayout(new BoxLayout(panelWrapper, BoxLayout.X_AXIS));
		panelWrapper.setBackground(MyJPanel.uninaLightColor);
		
		MyJLabel lblPrezzoOfferto = new MyJLabel(annuncioPerOfferta.getUtenteProprietario().getUsername() + ", sono disposto ad offrire ");
		lblPrezzoOfferto.aggiungiImmagineScalata("images/iconaPrezzoIniziale.png", 25, 25, false);
		inserisciPrezzoTextField = new MyJTextField();
		
		if(annuncioPerOfferta instanceof AnnuncioVendita) {
			Double prezzoOffertaMinimo = annuncioPerOfferta.getPrezzoIniziale() * 0.4;
			inserisciPrezzoTextField.setText(prezzoOffertaMinimo.toString());
		}
		inserisciPrezzoTextField.setPreferredSize(new Dimension (100, 25));		
		inserisciPrezzoTextField.setMaximumSize(new Dimension (100, 25));
		inserisciPrezzoTextField.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		MyJLabel lblEuro = new MyJLabel(" €.");
		
		panelWrapper.add(lblPrezzoOfferto);
		panelWrapper.add(inserisciPrezzoTextField);
		panelWrapper.add(lblEuro);
		
		lblErrorePrezzoOfferto = new MyJLabel(true);
		lblErrorePrezzoOfferto.setAlignmentX(CENTER_ALIGNMENT);
		
		inserisciPrezzoTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char carattereDigitato = e.getKeyChar();
				
				if(Character.isDigit(carattereDigitato)) {
					int posizioneDiUnPunto = inserisciPrezzoTextField.getText().indexOf('.');
					
					if(posizioneDiUnPunto != -1 && inserisciPrezzoTextField.getText().length() - posizioneDiUnPunto > 2)
						e.consume();
					else {
						inserisciPrezzoTextField.setBorder(new EmptyBorder(5, 5, 5, 5));
						lblErrorePrezzoOfferto.setVisible(false);
					}
				}
				else if(carattereDigitato != '.'){
					e.consume();
					inserisciPrezzoTextField.settaBordiTextFieldErrore();
					lblErrorePrezzoOfferto.setText("Formato non valido.");
					lblErrorePrezzoOfferto.setVisible(true);
				}
				else if(inserisciPrezzoTextField.getText().contains(".") || inserisciPrezzoTextField.getText().length() == 0) {
					e.consume();
					inserisciPrezzoTextField.settaBordiTextFieldErrore();
					lblErrorePrezzoOfferto.setText("Formato non valido.");
					lblErrorePrezzoOfferto.setVisible(true);
				}
				else {
					inserisciPrezzoTextField.setBorder(new EmptyBorder(5, 5, 5, 5));
					lblErrorePrezzoOfferto.setVisible(false);
				}
			}
		});
		
		panelPrezzoOfferto.add(Box.createVerticalGlue());
		panelPrezzoOfferto.add(Box.createHorizontalGlue());
		
		if(annuncioPerOfferta instanceof AnnuncioRegalo) {
			panelPrezzoOfferto.add(this.creaPanelMessaggioMotivazionale());
			panelPrezzoOfferto.add(Box.createVerticalGlue());
		}
		
		panelPrezzoOfferto.add(panelWrapper);
		panelPrezzoOfferto.add(Box.createVerticalGlue());
		panelPrezzoOfferto.add(lblErrorePrezzoOfferto);
		panelPrezzoOfferto.add(Box.createHorizontalGlue());
		panelPrezzoOfferto.add(Box.createVerticalGlue());
		
		return panelPrezzoOfferto;

	}
	
	private MyJPanel creaPanelMessaggioMotivazionale() {
		MyJPanel panelMessaggioMotivazionale = new MyJPanel();
		panelMessaggioMotivazionale.setLayout(new BoxLayout(panelMessaggioMotivazionale, BoxLayout.Y_AXIS));
		panelMessaggioMotivazionale.setPreferredSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width, 40));
		panelMessaggioMotivazionale.setMaximumSize(new Dimension(this.panelLaMiaOfferta.getMaximumSize().width, 40));
		panelMessaggioMotivazionale.setBackground(MyJPanel.uninaLightColor);
		panelMessaggioMotivazionale.setAlignmentX(CENTER_ALIGNMENT);
		
		MyJLabel lblMessaggio = new MyJLabel("Lascia un messaggio motivazionale");
		lblMessaggio.setAlignmentX(CENTER_ALIGNMENT);
		inserisciMessaggioTextField = new MyJTextField();
		inserisciMessaggioTextField.setPreferredSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width-20, 30));
		inserisciMessaggioTextField.setMaximumSize(new Dimension(this.panelLaMiaOfferta.getMaximumSize().width-20, 30));
		inserisciMessaggioTextField.setAlignmentX(CENTER_ALIGNMENT);
		inserisciMessaggioTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
		inserisciMessaggioTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				if(inserisciMessaggioTextField.getText().length() > 100)
					ke.consume();
			}
		});
		
		panelMessaggioMotivazionale.add(lblMessaggio);
		panelMessaggioMotivazionale.add(inserisciMessaggioTextField);
		panelMessaggioMotivazionale.add(Box.createRigidArea(new Dimension(0, 20)));

		return panelMessaggioMotivazionale;
	}
	
	private MyJPanel creaPanelModalitaConsegnaScelta(Annuncio annuncioPerOfferta) {
		MyJPanel panelModalitaConsegnaScelta = new MyJPanel();
		panelModalitaConsegnaScelta.setBackground(MyJPanel.uninaLightColor);
		panelModalitaConsegnaScelta.setLayout(new BoxLayout(panelModalitaConsegnaScelta, BoxLayout.Y_AXIS));
		panelModalitaConsegnaScelta.setAlignmentX(CENTER_ALIGNMENT);
		panelModalitaConsegnaScelta.setPreferredSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width-50, 300));
		panelModalitaConsegnaScelta.setMaximumSize(new Dimension(this.panelLaMiaOfferta.getMaximumSize().width-50, 300));

		MyJPanel sottoPanelModalitaScelta = new MyJPanel();
		sottoPanelModalitaScelta.setLayout(new BoxLayout(sottoPanelModalitaScelta, BoxLayout.X_AXIS));
		sottoPanelModalitaScelta.setAlignmentX(CENTER_ALIGNMENT);
		sottoPanelModalitaScelta.setBackground(MyJPanel.uninaLightColor);
		MyJLabel lblModalitaScelta = new MyJLabel("Tra le modalità di consegna che hai proposto, preferirei...");
		lblModalitaScelta.setAlignmentX(CENTER_ALIGNMENT);
		
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
		
		panelModalitaConsegnaScelta.add(lblModalitaScelta);
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
			this.nascondiLabelErrore(this.lblErrorePrezzoOfferto, this.lblErroreSpedizione);
			this.resettaBordiTextField(new EmptyBorder(5, 5, 5, 5), this.inserisciIndirizzoTextField, this.inserisciPrezzoTextField);
			
			if(annuncioPerOfferta instanceof AnnuncioVendita)
				checkPrezzoOfferto(this.inserisciPrezzoTextField.getText(), annuncioPerOfferta.getPrezzoIniziale() * 0.4, annuncioPerOfferta.getPrezzoIniziale());
			else if(annuncioPerOfferta instanceof AnnuncioRegalo)
				checkPrezzoOfferto(this.inserisciPrezzoTextField.getText(), 0.01, 0);

			if(this.modalitaSceltaBG.getSelection().getActionCommand().equals("Spedizione"))
				checkResidenza(this.inserisciIndirizzoTextField.getText());
			
			OffertaAcquisto newOfferta = this.organizzaDatiDaPassareAlController(annuncioPerOfferta);
			mainController.onConfermaOffertaButtonClicked(newOfferta);
		}
		catch(PrezzoOffertoException | SaldoException throwables) {
			this.settaLabelETextFieldDiErrore(lblErrorePrezzoOfferto, throwables.getMessage(), this.inserisciPrezzoTextField);
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

	private void checkPrezzoOfferto(String prezzoOfferto, double prezzoMinimo, double prezzoIniziale) throws SaldoException, PrezzoOffertoException {
		
		if(this.inserisciPrezzoTextField.getText().isEmpty() || this.inserisciPrezzoTextField.getText() == null)
			throw new PrezzoOffertoException("Inserisci un'offerta monetaria.");
		
		double prezzoOffertoDouble = Double.valueOf(prezzoOfferto);
		
		if(mainController.getUtenteLoggato().getSaldo() <= prezzoOffertoDouble)
			throw new SaldoException("Saldo insufficiente.");
		
		if(prezzoIniziale > 0) {
			if(prezzoOffertoDouble < prezzoMinimo)
				throw new PrezzoOffertoException("Il prezzo offerto deve essere almeno pari a " + prezzoMinimo + "€ (il 40%).");
			
			if(prezzoOffertoDouble > prezzoIniziale)
				throw new PrezzoOffertoException("Il prezzo offerto deve essere al più pari al prezzo iniziale.");
		}
	}
	
	private OffertaAcquisto organizzaDatiDaPassareAlController(Annuncio annuncioRiferito) {
		ModConsegnaEnum modalitaConsegnaScelta = ModConsegnaEnum.confrontaConStringa(modalitaSceltaBG.getSelection().getActionCommand());
		
		OffertaAcquisto offertaToAdd = new OffertaAcquisto(mainController.getUtenteLoggato(), modalitaConsegnaScelta, annuncioRiferito, Double.valueOf(this.inserisciPrezzoTextField.getText()));
				
		if(modalitaConsegnaScelta.toString().equals("Spedizione"))
			offertaToAdd.setIndirizzoSpedizione(this.inserisciIndirizzoTextField.getText());
		else if(modalitaConsegnaScelta.toString().equals("Ritiro in posta"))
			offertaToAdd.setUfficioRitiro((UfficioPostale)this.ufficiPostaliCB.getSelectedItem());
		else {
			ButtonModel selectedModel = this.incontriBG.getSelection();
			JRadioButton rbSelezionato = null;

			for (Enumeration<AbstractButton> buttons = this.incontriBG.getElements(); buttons.hasMoreElements();) {
			    AbstractButton button = buttons.nextElement();
			    if (button.getModel() == selectedModel) {
			        rbSelezionato = (JRadioButton) button;
			        break;
			    }
			}

			
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
		
		if(annuncioRiferito instanceof AnnuncioRegalo)
			offertaToAdd.setMessaggioMotivazionale(this.inserisciMessaggioTextField.getText());
		
		return offertaToAdd;
	}

}
