package gui;

//Import dalle librerie standard
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Controller;

//Import da eccezioni
import eccezioni.DescrizioneException;
import eccezioni.FotoException;
import eccezioni.ModalitaDiConsegnaException;
import eccezioni.NomeAnnuncioException;
import eccezioni.NotaScambioException;
import eccezioni.PrezzoInizialeException;

//Import da utilities
import utilities.CategoriaEnum;
import utilities.CondizioneEnum;
import utilities.GiornoEnum;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;
import utilities.StatoAnnuncioEnum;
import utilities.RigaIncontro;

//Import da DTO
import dto.Oggetto;
import dto.Annuncio;
import dto.AnnuncioRegalo;
import dto.AnnuncioScambio;
import dto.AnnuncioVendita;
import dto.ProfiloUtente;
import dto.SedeUniversita;

public class FramePubblicaAnnuncio extends MyJFrame {

	private static final long serialVersionUID = 1L;
	private Controller mainController;
	private String tipoAnnuncio;
	
	private MyJPanel contentPane = new MyJPanel();
	private MyJPanel panelNomeAnnuncio;
	private MyJPanel panelAggiungiFoto;
	private MyJPanel panelDettagliIncontri;
	private MyJPanel panelCentrale;
	private MyJPanel panelInserimentoDati;
	
	//Panels di errore
	private MyJPanel panelErroreNomeAnnuncio = new MyJPanel();
	private MyJPanel panelErroreDescrizione = new MyJPanel();
	private MyJPanel panelErroreNotaScambio = new MyJPanel();
	private MyJPanel panelErrorePrezzoIniziale = new MyJPanel();
	private MyJPanel panelErroreModalitaDiConsegna = new MyJPanel();
	private MyJPanel panelErroreFoto = new MyJPanel();
	
	private int numeroIncontri = 1;
	private MyJButton bottoneTornaIndietro;
	private MyJButton bottonePubblicaAnnuncio;
	
	//CheckBoxes
	private JCheckBox spedizioneCheckBox;
	private JCheckBox ritiroInPostaCheckBox;
	private JCheckBox incontroCheckBox;
	
	//ComboBoxes
	private JComboBox sediUniversitaComboBox = new JComboBox();
	private JComboBox categorieOggettoComboBox;
	private JComboBox condizioniOggettoComboBox;
	private JComboBox dataScadenzaComboBox;
	
	//TextFields
	private MyJTextField nomeAnnuncioTextField;
	private MyJTextField prezzoInizialeTextField;
	
	//TextAreas
	private JTextArea inserisciDescrizioneTextA;
	private JTextArea inserisciNotaScambioTextA;
	
	//Labels di errore
	private MyJLabel lblErroreDescrizione;
	private MyJLabel lblErroreNotaScambio;
	private MyJLabel lblErroreNomeAnnuncio;
	private MyJLabel lblErroreModalitaConsegna;
	private MyJLabel lblErroreFoto;
	private MyJLabel lblErrorePrezzoIniziale;
	
	//Flag per immagini
	private AtomicBoolean foto1Caricata = new AtomicBoolean(false);
	private AtomicBoolean foto2Caricata = new AtomicBoolean(false);
	private AtomicBoolean foto3Caricata = new AtomicBoolean(false);
	
	//Labels per le immagini
	private MyJLabel lblAggiungiFoto1;
	private MyJLabel lblAggiungiFoto2;
	private MyJLabel lblAggiungiFoto3;
	
	//ArrayList per tenere traccia di tutti gli eventuali incontri specificati
	private ArrayList<RigaIncontro> incontriSpecificati = new ArrayList<>();
	
	
	public FramePubblicaAnnuncio(Controller controller, String tipoAnnuncioDaPubblicare, ArrayList<SedeUniversita> sediPresenti) {
		tipoAnnuncio = tipoAnnuncioDaPubblicare;
		mainController = controller;
		
		this.settaContentPane(sediPresenti);
	}

	private void settaContentPane(ArrayList<SedeUniversita> sediPresenti) {
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setLocationRelativeTo(null);
		this.setTitle("Pubblica ora il tuo nuovo annuncio di "+tipoAnnuncio.toLowerCase()+"!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane.setLayout(new BorderLayout());
		
		MyJPanel scrittaUninaSwap = new MyJPanel(MyJPanel.uninaColorClicked, new Dimension(Integer.MAX_VALUE, 50));
		scrittaUninaSwap.add(new MyJLabel("UninaSwap", new Font("Ubuntu Sans", Font.BOLD, 25), Color.white));
		
		contentPane.add(scrittaUninaSwap, BorderLayout.NORTH);
		contentPane.add(new MyJPanel(MyJPanel.uninaLightColor, new Dimension(100, contentPane.getHeight())), BorderLayout.WEST);
		contentPane.add(this.creaPanelCentrale(sediPresenti), BorderLayout.CENTER);
		contentPane.add(new MyJPanel(MyJPanel.uninaLightColor, new Dimension(100, contentPane.getHeight())), BorderLayout.EAST);
		contentPane.add(new MyJPanel(MyJPanel.uninaColorClicked, new Dimension(Integer.MAX_VALUE, 25)), BorderLayout.SOUTH);

		this.setContentPane(contentPane);
	}
	
	private JScrollPane creaPanelCentrale(ArrayList<SedeUniversita> sediPresenti) {
		panelCentrale = new MyJPanel();
		panelCentrale.setLayout(new BoxLayout(panelCentrale, BoxLayout.Y_AXIS));
		panelCentrale.setBackground(Color.white);
		
		if(tipoAnnuncio.equals("Scambio")) {
			panelCentrale.setPreferredSize(new Dimension(1225, 3200));
			panelCentrale.setMaximumSize(new Dimension(1225, 3200));
		}
		else if (tipoAnnuncio.equals("Vendita")){
			panelCentrale.setPreferredSize(new Dimension(1225, 2900));
			panelCentrale.setMaximumSize(new Dimension(1225, 2900));
		}
		else {
			panelCentrale.setPreferredSize(new Dimension(1225, 2800));
			panelCentrale.setMaximumSize(new Dimension(1225, 2800));
		}
		
		panelCentrale.setAlignmentX(CENTER_ALIGNMENT);
		
		this.creaPanelAggiungiFoto();
		this.creaPanelNomeAnnuncio();
		
		panelCentrale.add(Box.createRigidArea(new Dimension(0, 20)));
		panelCentrale.add(panelNomeAnnuncio);
		panelCentrale.add(Box.createRigidArea(new Dimension(0, 20)));
		panelCentrale.add(creaPanelInserimentoDati(sediPresenti));
		panelCentrale.add(Box.createRigidArea(new Dimension(0, 20)));
		
		
		JScrollPane scrollPane = new JScrollPane(panelCentrale);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);

		return scrollPane;
	}
	
	private void creaPanelNomeAnnuncio() {		
		panelNomeAnnuncio = new MyJPanel();
		panelNomeAnnuncio.setPreferredSize(new Dimension(1225, 115));
		panelNomeAnnuncio.setMaximumSize(new Dimension(1225, 115));
		panelNomeAnnuncio.setLayout(new BorderLayout());
		panelNomeAnnuncio.setBackground(Color.white);
		
		nomeAnnuncioTextField = new MyJTextField("Titolo");
		nomeAnnuncioTextField.setBorder(new EmptyBorder(0, 10, 0, 0));
		nomeAnnuncioTextField.setPreferredSize(new Dimension(1225, 30));
		nomeAnnuncioTextField.setMaximumSize(new Dimension(1225, 30));
		nomeAnnuncioTextField.rendiTextFieldFocusable();
		
		nomeAnnuncioTextField.setFocusGainedAction(() -> {
			if(nomeAnnuncioTextField.getText().equals("Titolo"))
				nomeAnnuncioTextField.setText("");
		});
		
		nomeAnnuncioTextField.setFocusLostAction(() -> {
			if(nomeAnnuncioTextField.getText().isEmpty())
				nomeAnnuncioTextField.setText("Titolo");
		});
		
		nomeAnnuncioTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(nomeAnnuncioTextField.getText().length() >= 100)
					e.consume();
			}
		});
		
		lblErroreNomeAnnuncio = new MyJLabel(true);
		lblErroreNomeAnnuncio.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		lblErroreNomeAnnuncio.setText("prova");
		lblErroreNomeAnnuncio.setVisible(true);
		lblErroreNomeAnnuncio.setBorder(new EmptyBorder(0, 10, 0, 0));
		lblErroreNomeAnnuncio.setAlignmentX(LEFT_ALIGNMENT);
		panelErroreNomeAnnuncio.add(lblErroreNomeAnnuncio);
		
		MyJPanel panelSuperiore = new MyJPanel(MyJPanel.uninaColorClicked);
		panelSuperiore.setLayout(new BoxLayout(panelSuperiore, BoxLayout.X_AXIS));
		panelSuperiore.setAlignmentX(LEFT_ALIGNMENT);
		MyJLabel lblNomeAnnuncio = new MyJLabel("Dai un nome al tuo annuncio!");
		lblNomeAnnuncio.setFont(new Font("Ubuntu Sans", Font.BOLD, 30));
		lblNomeAnnuncio.setForeground(Color.white);
		lblNomeAnnuncio.setAlignmentX(LEFT_ALIGNMENT);
		lblNomeAnnuncio.setBorder(new EmptyBorder(0, 10, 0, 0));
		panelSuperiore.add(lblNomeAnnuncio);
		
		MyJPanel panelCentrale = new MyJPanel();
		panelCentrale.setLayout(new BorderLayout());
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.NORTH);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.WEST);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.SOUTH);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.EAST);
		
		MyJPanel panelPerTextField = new MyJPanel();
		panelPerTextField.setLayout(new BoxLayout(panelPerTextField, BoxLayout.PAGE_AXIS));
		panelPerTextField.setAlignmentX(LEFT_ALIGNMENT);
		panelPerTextField.setBackground(MyJPanel.uninaLightColor);
		this.nomeAnnuncioTextField.setAlignmentX(LEFT_ALIGNMENT);
		this.nomeAnnuncioTextField.setFont(new Font("Ubuntu Sans", Font.PLAIN, 25));
		
		panelCentrale.add(panelPerTextField);
		
		panelPerTextField.add(nomeAnnuncioTextField);
		
		panelErroreNomeAnnuncio.setBackground(Color.white);
		panelErroreNomeAnnuncio.setLayout(new BoxLayout(panelErroreNomeAnnuncio, BoxLayout.X_AXIS));
		panelErroreNomeAnnuncio.setAlignmentX(LEFT_ALIGNMENT);
		panelErroreNomeAnnuncio.setVisible(false);
		panelErroreNomeAnnuncio.add(lblErroreNomeAnnuncio);
		
		panelNomeAnnuncio.add(panelSuperiore, BorderLayout.NORTH);
		panelNomeAnnuncio.add(panelCentrale, BorderLayout.CENTER);
		panelNomeAnnuncio.add(panelErroreNomeAnnuncio, BorderLayout.SOUTH);
		
		this.panelCentrale.add(panelNomeAnnuncio);
		
	}

	private MyJPanel creaPanelAggiungiFoto() {
		panelAggiungiFoto = new MyJPanel();
		panelAggiungiFoto.setLayout(new BorderLayout());
		panelAggiungiFoto.setPreferredSize(new Dimension(1225, 620));
		panelAggiungiFoto.setMaximumSize(new Dimension(1225, 620));
		
		MyJPanel panelSuperiore = new MyJPanel(MyJPanel.uninaColorClicked);
		panelSuperiore.setLayout(new BoxLayout(panelSuperiore, BoxLayout.X_AXIS));
		panelSuperiore.setPreferredSize(new Dimension(1225, 35));
		panelSuperiore.setMaximumSize(new Dimension(1225, 35));
		MyJLabel lblAggiungiFoto = new MyJLabel("Carica fino ad un massimo di 3 foto! (Si consiglia la risoluzione 3:4)");
		lblAggiungiFoto.setFont(new Font("Ubuntu Sans", Font.BOLD, 30));
		lblAggiungiFoto.setForeground(Color.white);
		lblAggiungiFoto.setAlignmentX(LEFT_ALIGNMENT);
		lblAggiungiFoto.setBorder(new EmptyBorder(0, 10, 0, 0));
		panelSuperiore.add(lblAggiungiFoto);
		panelSuperiore.setAlignmentX(LEFT_ALIGNMENT);
		
		MyJPanel panelContieniFoto = new MyJPanel();
		panelContieniFoto.setLayout(new BorderLayout());
		panelContieniFoto.setPreferredSize(new Dimension(1225, 570));
		panelContieniFoto.setMaximumSize(new Dimension(1225, 570));
		panelContieniFoto.setBackground(MyJPanel.uninaLightColor);
		
		MyJPanel panelDelleFoto = new MyJPanel();
		panelDelleFoto.setLayout(new FlowLayout());
		panelDelleFoto.setPreferredSize(new Dimension(1225, 550));
		panelDelleFoto.setMaximumSize(new Dimension(1225, 550));
		panelDelleFoto.setBackground(MyJPanel.uninaLightColor);
		
		MyJPanel panelFoto1 = new MyJPanel();
		lblAggiungiFoto1 = new MyJLabel();
		this.settaPanelFoto(panelFoto1, lblAggiungiFoto1, foto1Caricata, () -> {foto1Caricata.set(lblAggiungiFoto1.aggiungiImmagineDaFileSystem());});
		
		MyJPanel panelFoto2 = new MyJPanel();
		lblAggiungiFoto2 = new MyJLabel();
		this.settaPanelFoto(panelFoto2, lblAggiungiFoto2, foto2Caricata, () -> {foto2Caricata.set(lblAggiungiFoto2.aggiungiImmagineDaFileSystem());});
		
		MyJPanel panelFoto3 = new MyJPanel();
		lblAggiungiFoto3 = new MyJLabel();
		this.settaPanelFoto(panelFoto3, lblAggiungiFoto3, foto3Caricata, () -> {foto3Caricata.set(lblAggiungiFoto3.aggiungiImmagineDaFileSystem());});
		
		panelDelleFoto.add(panelFoto1);
		panelDelleFoto.add(panelFoto2);
		panelDelleFoto.add(panelFoto3);
		
		panelContieniFoto.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.NORTH);
		panelContieniFoto.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.EAST);
		panelContieniFoto.add(panelDelleFoto, BorderLayout.NORTH);
		panelContieniFoto.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.WEST);
		panelContieniFoto.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.SOUTH);
		
		panelErroreFoto.setBackground(Color.white);
		panelErroreFoto.setPreferredSize(new Dimension(1225, 35));
		panelErroreFoto.setMaximumSize(new Dimension(1225, 35));
		panelErroreFoto.setLayout(new BoxLayout(panelErroreFoto, BoxLayout.X_AXIS));
		panelErroreFoto.setAlignmentX(LEFT_ALIGNMENT);
		panelErroreFoto.setVisible(false);

		lblErroreFoto = new MyJLabel(true);
		lblErroreFoto.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		lblErroreFoto.setVisible(true);
		lblErroreFoto.setBorder(new EmptyBorder(0, 10, 0, 0));
		lblErroreFoto.setAlignmentX(LEFT_ALIGNMENT);
		panelErroreFoto.add(lblErroreFoto);
		
		panelAggiungiFoto.add(panelSuperiore, BorderLayout.NORTH);
		panelAggiungiFoto.add(panelContieniFoto, BorderLayout.CENTER);
		panelAggiungiFoto.add(panelErroreFoto, BorderLayout.SOUTH);
		
		return panelAggiungiFoto;
	}
	
	private MyJPanel creaPanelInserimentoDati(ArrayList<SedeUniversita> sediPresenti) {
		panelInserimentoDati = new MyJPanel();
		panelInserimentoDati.setBackground(Color.white);
		panelInserimentoDati.setLayout(new BoxLayout(panelInserimentoDati, BoxLayout.Y_AXIS));
		panelInserimentoDati.setPreferredSize(new Dimension(1225, 3000));
		panelInserimentoDati.setMaximumSize(new Dimension(1225, 3000));

		inserisciDescrizioneTextA = new JTextArea();
		this.settaTextArea(inserisciDescrizioneTextA);
		String stringaDiDefaultPerDescrizione = "Dai una descrizione accurata dell'oggetto che stai vendendo. L'acquirente deve avere ben chiaro cosa starà acquistando!";
		lblErroreDescrizione = new MyJLabel(true);
		
		inserisciNotaScambioTextA = new JTextArea();
		this.settaTextArea(inserisciNotaScambioTextA);
		String stringaDiDefaultPerNotaScambio = "Sii preciso in quello che richiedi!";
		lblErroreNotaScambio = new MyJLabel(true);
		
		prezzoInizialeTextField = new MyJTextField();
		prezzoInizialeTextField.setBorder(new EmptyBorder(0, 10, 0, 0));
		prezzoInizialeTextField.setPreferredSize(new Dimension(300, 30));
		prezzoInizialeTextField.setMaximumSize(new Dimension(300, 30));
		
		panelInserimentoDati.add(this.creaPanelAggiungiFoto());
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 50)));
		panelInserimentoDati.add(this.creaPanelTextArea("Descrivi il tuo articolo!", stringaDiDefaultPerDescrizione, inserisciDescrizioneTextA,
				lblErroreDescrizione, panelErroreDescrizione));
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 50)));
		panelInserimentoDati.add(this.creaPanelCategoria());
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 50)));
		panelInserimentoDati.add(this.creaPanelCondizioni());
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 50)));
		
		if(this.tipoAnnuncio.equals("Scambio")) {
			panelInserimentoDati.add(this.creaPanelTextArea("Indica gli oggetti con cui scambieresti il tuo articolo!", stringaDiDefaultPerNotaScambio, 
				inserisciNotaScambioTextA, lblErroreNotaScambio, panelErroreNotaScambio));
			panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 50)));
		}
		
		
		if(this.tipoAnnuncio.equals("Vendita")) {
			panelInserimentoDati.add(this.creaPanelPrezzoIniziale("Indica un prezzo iniziale per il tuo articolo in vendita!", prezzoInizialeTextField));
			panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 50)));
		}
		
		panelInserimentoDati.add(this.creaPanelDataScadenza());
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 50)));
		panelInserimentoDati.add(this.creaPanelModalitaConsegna());
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 50)));
		panelInserimentoDati.add(this.creaPanelDettagliIncontri(sediPresenti));
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 50)));
		panelInserimentoDati.add(this.creaPanelBottoni());
		
		return panelInserimentoDati;
	}
	
	private MyJPanel creaPanelTextArea(String stringaPerLabel, String stringaDiDefaultPerTextA, JTextArea componentToAdd, MyJLabel lblDiErrore, MyJPanel panelDiErrore) {
		MyJPanel panelGenerico = new MyJPanel();
		panelGenerico.setLayout(new BorderLayout());
		panelGenerico.setPreferredSize(new Dimension(1225, 400));
		panelGenerico.setMaximumSize(new Dimension(1225, 400));
		
		MyJLabel lblGenerico = new MyJLabel(stringaPerLabel);
		lblGenerico.setFont(new Font("Ubuntu Sans", Font.BOLD, 30));
		lblGenerico.setForeground(Color.white);
		lblGenerico.setAlignmentX(LEFT_ALIGNMENT);
		lblGenerico.setBorder(new EmptyBorder(0, 10, 0, 0));
		
		lblDiErrore.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		lblDiErrore.setVisible(true);
		lblDiErrore.setBorder(new EmptyBorder(0, 10, 0, 0));
		lblDiErrore.setAlignmentX(LEFT_ALIGNMENT);
	
		MyJPanel panelSuperiore = new MyJPanel(MyJPanel.uninaColorClicked);
		panelSuperiore.setLayout(new BoxLayout(panelSuperiore, BoxLayout.X_AXIS));
		panelSuperiore.add(lblGenerico);
		panelSuperiore.setAlignmentX(LEFT_ALIGNMENT);
		
		MyJPanel panelCentrale = new MyJPanel();
		panelCentrale.setLayout(new BorderLayout());
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.NORTH);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.WEST);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.SOUTH);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.EAST);
		
		componentToAdd.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent fe) {
				if(componentToAdd.getText().equals(stringaDiDefaultPerTextA))
					componentToAdd.setText("");
			}
			
			@Override
			public void focusLost(FocusEvent fe) {
				if(componentToAdd.getText().isEmpty())
					componentToAdd.setText(stringaDiDefaultPerTextA);
			}
		});
		componentToAdd.setText(stringaDiDefaultPerTextA);
		
//		JScrollPane scrollPaneTesto = new JScrollPane(componentToAdd);
//		scrollPaneTesto.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
//		scrollPaneTesto.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		panelCentrale.add(scrollPaneTesto, BorderLayout.CENTER);
		panelCentrale.add(componentToAdd, BorderLayout.CENTER);	
		
		panelDiErrore.setPreferredSize(new Dimension(1225, 30));
		panelDiErrore.setMaximumSize(new Dimension(1225, 30));
		panelDiErrore.setBackground(Color.white);
		panelDiErrore.setLayout(new BoxLayout(panelDiErrore, BoxLayout.X_AXIS));
		panelDiErrore.add(lblDiErrore);
		panelDiErrore.setAlignmentX(LEFT_ALIGNMENT);
		panelDiErrore.setVisible(false);
		
		panelGenerico.add(panelSuperiore, BorderLayout.NORTH);
		panelGenerico.add(panelCentrale, BorderLayout.CENTER);
		panelGenerico.add(panelDiErrore, BorderLayout.SOUTH);
		
		return panelGenerico;
	}
	
	private void settaTextArea(JTextArea textArea) {
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setPreferredSize(new Dimension(1225, 400));
		textArea.setMaximumSize(new Dimension(1225, 400));
		textArea.setFont(textArea.getFont().deriveFont(25f));
		textArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				if(textArea.getText().length() > 300)
					ke.consume();
			}
		});
	}
	
	private MyJPanel creaPanelCategoria() {
		MyJPanel panelCategoria = new MyJPanel();
		panelCategoria.setLayout(new BorderLayout());
		panelCategoria.setPreferredSize(new Dimension(1225, 115));
		panelCategoria.setMaximumSize(new Dimension(1225, 115));
		
		MyJPanel panelSuperiore = new MyJPanel(MyJPanel.uninaColorClicked);
		panelSuperiore.setLayout(new BoxLayout(panelSuperiore, BoxLayout.X_AXIS));
		panelSuperiore.setAlignmentX(LEFT_ALIGNMENT);
		MyJLabel lblCategoria = new MyJLabel("Indica la categoria di afferenza del tuo articolo!");
		lblCategoria.setFont(new Font("Ubuntu Sans", Font.BOLD, 30));
		lblCategoria.setForeground(Color.white);
		lblCategoria.setAlignmentX(LEFT_ALIGNMENT);
		lblCategoria.setBorder(new EmptyBorder(0, 10, 0, 0));
		panelSuperiore.add(lblCategoria);
		
		MyJPanel panelCentrale = new MyJPanel();
		panelCentrale.setLayout(new BorderLayout());
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.NORTH);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.WEST);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.SOUTH);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.EAST);
		
		MyJPanel panelPerComboBox = new MyJPanel();
		panelPerComboBox.setLayout(new BoxLayout(panelPerComboBox, BoxLayout.X_AXIS));
		panelPerComboBox.setAlignmentX(LEFT_ALIGNMENT);
		panelPerComboBox.setBackground(MyJPanel.uninaLightColor);
		categorieOggettoComboBox = this.creaCBCategorieOggetto();
		categorieOggettoComboBox.setSelectedIndex(0);
		categorieOggettoComboBox.setAlignmentX(LEFT_ALIGNMENT);
		panelPerComboBox.add(categorieOggettoComboBox);
		
		panelCentrale.add(panelPerComboBox, BorderLayout.CENTER);
			
		panelCategoria.add(panelSuperiore, BorderLayout.NORTH);
		panelCategoria.add(panelCentrale, BorderLayout.CENTER);
		
		return panelCategoria;
	}
	
	private MyJPanel creaPanelCondizioni() {
		MyJPanel panelCondizioni = new MyJPanel();
		panelCondizioni.setLayout(new BorderLayout());
		panelCondizioni.setPreferredSize(new Dimension(1225, 115));
		panelCondizioni.setMaximumSize(new Dimension(1225, 115));
		
		MyJPanel panelSuperiore = new MyJPanel(MyJPanel.uninaColorClicked);
		panelSuperiore.setLayout(new BoxLayout(panelSuperiore, BoxLayout.X_AXIS));
		panelSuperiore.setAlignmentX(LEFT_ALIGNMENT);
		MyJLabel lblCondizioni = new MyJLabel("Indica le condizioni del tuo oggetto!");
		lblCondizioni.setFont(new Font("Ubuntu Sans", Font.BOLD, 30));
		lblCondizioni.setForeground(Color.white);
		lblCondizioni.setAlignmentX(LEFT_ALIGNMENT);
		lblCondizioni.setBorder(new EmptyBorder(0, 10, 0, 0));
		panelSuperiore.add(lblCondizioni);
		
		MyJPanel panelCentrale = new MyJPanel();
		panelCentrale.setLayout(new BorderLayout());
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.NORTH);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.WEST);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.SOUTH);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.EAST);
		
		MyJPanel panelPerComboBox = new MyJPanel();
		panelPerComboBox.setLayout(new BoxLayout(panelPerComboBox, BoxLayout.X_AXIS));
		panelPerComboBox.setAlignmentX(LEFT_ALIGNMENT);
		panelPerComboBox.setBackground(MyJPanel.uninaLightColor);
		condizioniOggettoComboBox = this.creaCBCondizioniOggetto();
		condizioniOggettoComboBox.setSelectedIndex(0);
		condizioniOggettoComboBox.setAlignmentX(LEFT_ALIGNMENT);
		panelPerComboBox.add(condizioniOggettoComboBox);
		
		panelCentrale.add(panelPerComboBox, BorderLayout.CENTER);
			
		panelCondizioni.add(panelSuperiore, BorderLayout.NORTH);
		panelCondizioni.add(panelCentrale, BorderLayout.CENTER);
		
		return panelCondizioni;
	}
	
	private MyJPanel creaPanelPrezzoIniziale(String stringaPerLabel, MyJTextField textFieldIn) {
		MyJPanel panelPrezzoIniziale = new MyJPanel();
		panelPrezzoIniziale.setLayout(new BorderLayout());
		panelPrezzoIniziale.setPreferredSize(new Dimension(1225, 115));
		panelPrezzoIniziale.setMaximumSize(new Dimension(1225, 115));
		
		MyJPanel panelSuperiore = new MyJPanel(MyJPanel.uninaColorClicked);
		panelSuperiore.setLayout(new BoxLayout(panelSuperiore, BoxLayout.X_AXIS));
		panelSuperiore.setAlignmentX(LEFT_ALIGNMENT);
		MyJLabel lblPrezzoIniziale = new MyJLabel(stringaPerLabel);
		lblPrezzoIniziale.setFont(new Font("Ubuntu Sans", Font.BOLD, 30));
		lblPrezzoIniziale.setForeground(Color.white);
		lblPrezzoIniziale.setAlignmentX(LEFT_ALIGNMENT);
		lblPrezzoIniziale.setBorder(new EmptyBorder(0, 10, 0, 0));
		panelSuperiore.add(lblPrezzoIniziale);
		
		MyJPanel panelCentrale = new MyJPanel();
		panelCentrale.setLayout(new BorderLayout());
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.NORTH);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.WEST);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.SOUTH);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.EAST);
		
		MyJPanel panelPerTextField = new MyJPanel();
		panelPerTextField.setLayout(new BoxLayout(panelPerTextField, BoxLayout.PAGE_AXIS));
		panelPerTextField.setAlignmentX(LEFT_ALIGNMENT);
		panelPerTextField.setBackground(MyJPanel.uninaLightColor);
		textFieldIn.setAlignmentX(LEFT_ALIGNMENT);
		textFieldIn.setFont(new Font("Ubuntu Sans", Font.BOLD, 25));
		
		panelCentrale.add(panelPerTextField);
		
		panelErrorePrezzoIniziale = new MyJPanel(new Color(220, 220, 220));
		panelErrorePrezzoIniziale.setLayout(new BoxLayout(panelErrorePrezzoIniziale, BoxLayout.X_AXIS));
		panelErrorePrezzoIniziale.setAlignmentX(LEFT_ALIGNMENT);
		panelErrorePrezzoIniziale.setBackground(Color.white);
		panelErrorePrezzoIniziale.setVisible(false);
		lblErrorePrezzoIniziale = new MyJLabel(true);
		lblErrorePrezzoIniziale.setVisible(true);
		lblErrorePrezzoIniziale.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		lblErrorePrezzoIniziale.setBorder(new EmptyBorder(0, 10, 0, 0));
		lblErrorePrezzoIniziale.setAlignmentX(LEFT_ALIGNMENT);
		
		textFieldIn.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char carattereDigitato = e.getKeyChar();
				
				if(Character.isDigit(carattereDigitato)) {
					int posizioneDiUnPunto = textFieldIn.getText().indexOf('.');
					
					if(posizioneDiUnPunto != -1 && textFieldIn.getText().length() - posizioneDiUnPunto > 2)
						e.consume();
					else {
						panelErrorePrezzoIniziale.setVisible(false);
						textFieldIn.settaBordiTextFieldStandard();
					}
				}
				else if(carattereDigitato != '.'){
					e.consume();
					textFieldIn.settaBordiTextFieldErrore();
					panelErrorePrezzoIniziale.setVisible(true);
					lblErrorePrezzoIniziale.setText("Formato non valido.");
				}
				else if(textFieldIn.getText().contains(".") || textFieldIn.getText().length() == 0) {
					e.consume();
					textFieldIn.settaBordiTextFieldErrore();
					panelErrorePrezzoIniziale.setVisible(true);
					lblErrorePrezzoIniziale.setText("Formato non valido.");
				}
				else {
					textFieldIn.settaBordiTextFieldStandard();
					panelErrorePrezzoIniziale.setVisible(false);
				}
			}
		});
		panelPerTextField.add(textFieldIn);
		panelErrorePrezzoIniziale.add(lblErrorePrezzoIniziale);
		
		panelPrezzoIniziale.add(panelSuperiore, BorderLayout.NORTH);
		panelPrezzoIniziale.add(panelCentrale, BorderLayout.CENTER);
		panelPrezzoIniziale.add(panelErrorePrezzoIniziale, BorderLayout.SOUTH);
		
		
		return panelPrezzoIniziale;
	}
	
	private MyJPanel creaPanelDataScadenza() {
		MyJPanel panelDataScadenza = new MyJPanel();
		panelDataScadenza.setLayout(new BorderLayout());
		panelDataScadenza.setPreferredSize(new Dimension(1225, 85));
		panelDataScadenza.setMaximumSize(new Dimension(1225, 85));
		
		MyJPanel panelSuperiore = new MyJPanel(MyJPanel.uninaColorClicked);
		panelSuperiore.setLayout(new BoxLayout(panelSuperiore, BoxLayout.X_AXIS));
		panelSuperiore.setAlignmentX(LEFT_ALIGNMENT);
		MyJLabel lblDataScadenza = new MyJLabel("Dopo quanto vuoi far scadere il tuo annuncio?");
		lblDataScadenza.setFont(new Font("Ubuntu Sans", Font.BOLD, 30));
		lblDataScadenza.setForeground(Color.white);
		lblDataScadenza.setAlignmentX(LEFT_ALIGNMENT);
		lblDataScadenza.setBorder(new EmptyBorder(0, 10, 0, 0));
		panelSuperiore.add(lblDataScadenza);
		
		MyJPanel panelCentrale = new MyJPanel();
		panelCentrale.setLayout(new BorderLayout());
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.NORTH);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.WEST);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.SOUTH);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.EAST);
		
		MyJPanel panelPerComboBox = new MyJPanel();
		panelPerComboBox.setLayout(new BoxLayout(panelPerComboBox, BoxLayout.X_AXIS));
		panelPerComboBox.setAlignmentX(LEFT_ALIGNMENT);
		panelPerComboBox.setBackground(MyJPanel.uninaLightColor);
		dataScadenzaComboBox = new JComboBox();
		dataScadenzaComboBox.setBackground(Color.white);
		dataScadenzaComboBox.setPreferredSize(new Dimension(300, 30));
		dataScadenzaComboBox.setMaximumSize(new Dimension(300, 30));
		dataScadenzaComboBox.setFont(new Font("Ubuntu Sans", Font.PLAIN, 20));

		dataScadenzaComboBox.addItem("Non far scadere l'annuncio");
		dataScadenzaComboBox.addItem("7 giorni");
		dataScadenzaComboBox.addItem("15 giorni");
		dataScadenzaComboBox.addItem("31 giorni");
		dataScadenzaComboBox.setSelectedIndex(0);
		dataScadenzaComboBox.setAlignmentX(LEFT_ALIGNMENT);
		panelPerComboBox.add(dataScadenzaComboBox);
		
		panelCentrale.add(panelPerComboBox, BorderLayout.CENTER);
			
		panelDataScadenza.add(panelSuperiore, BorderLayout.NORTH);
		panelDataScadenza.add(panelCentrale, BorderLayout.CENTER);
		
		return panelDataScadenza;
	}
	
	private MyJPanel creaPanelModalitaConsegna() {
		MyJPanel panelModalitaConsegna = new MyJPanel();
		panelModalitaConsegna.setLayout(new BorderLayout());
		panelModalitaConsegna.setPreferredSize(new Dimension(1225, 135));
		panelModalitaConsegna.setMaximumSize(new Dimension(1225, 135));
		
		MyJPanel panelSuperiore = new MyJPanel(MyJPanel.uninaColorClicked);
		panelSuperiore.setLayout(new BoxLayout(panelSuperiore, BoxLayout.X_AXIS));
		panelSuperiore.setAlignmentX(LEFT_ALIGNMENT);
		MyJLabel lblModalitaConsegna = new MyJLabel("Seleziona una (o più) modalità di consegna!");
		lblModalitaConsegna.setFont(new Font("Ubuntu Sans", Font.BOLD, 30));
		lblModalitaConsegna.setForeground(Color.white);
		lblModalitaConsegna.setAlignmentX(LEFT_ALIGNMENT);
		lblModalitaConsegna.setBorder(new EmptyBorder(0, 10, 0, 0));
		panelSuperiore.add(lblModalitaConsegna);
		
		MyJPanel panelCentrale = new MyJPanel();
		panelCentrale.setLayout(new BorderLayout());
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.NORTH);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.WEST);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.SOUTH);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.EAST);
		
		MyJPanel panelPerCheckBoxes = new MyJPanel();
		panelPerCheckBoxes.setLayout(new BoxLayout(panelPerCheckBoxes, BoxLayout.X_AXIS));
		panelPerCheckBoxes.setAlignmentX(LEFT_ALIGNMENT);
		panelPerCheckBoxes.setBackground(MyJPanel.uninaLightColor);
	
		spedizioneCheckBox = new JCheckBox("Spedizione");
		this.settaCheckBox(spedizioneCheckBox);
		
		ritiroInPostaCheckBox = new JCheckBox("Ritiro in posta");
		this.settaCheckBox(ritiroInPostaCheckBox);
		
		incontroCheckBox = new JCheckBox("Incontro");
		this.settaCheckBox(incontroCheckBox);
		incontroCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				panelDettagliIncontri.setVisible(!panelDettagliIncontri.isVisible());
			}
		});
		
		panelPerCheckBoxes.add(Box.createHorizontalGlue());
		panelPerCheckBoxes.add(spedizioneCheckBox);
		panelPerCheckBoxes.add(Box.createHorizontalGlue());
		panelPerCheckBoxes.add(ritiroInPostaCheckBox);
		panelPerCheckBoxes.add(Box.createHorizontalGlue());
		panelPerCheckBoxes.add(incontroCheckBox);
		panelPerCheckBoxes.add(Box.createHorizontalGlue());
	
		panelCentrale.add(panelPerCheckBoxes, BorderLayout.CENTER);
		
		panelErroreModalitaDiConsegna.setLayout(new BoxLayout(panelErroreModalitaDiConsegna, BoxLayout.X_AXIS));
		panelErroreModalitaDiConsegna.setBackground(Color.white);
		panelErroreModalitaDiConsegna.setAlignmentX(LEFT_ALIGNMENT);
		lblErroreModalitaConsegna = new MyJLabel(true);
		lblErroreModalitaConsegna.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		lblErroreModalitaConsegna.setVisible(true);
		lblErroreModalitaConsegna.setBorder(new EmptyBorder(0, 10, 0, 0));
		lblErroreModalitaConsegna.setText("Errore 3");
		lblErroreModalitaConsegna.setAlignmentX(LEFT_ALIGNMENT);
		panelErroreModalitaDiConsegna.add(lblErroreModalitaConsegna);
		panelErroreModalitaDiConsegna.setVisible(false);
			
		panelModalitaConsegna.add(panelSuperiore, BorderLayout.NORTH);
		panelModalitaConsegna.add(panelCentrale, BorderLayout.CENTER);
		panelModalitaConsegna.add(panelErroreModalitaDiConsegna, BorderLayout.SOUTH);
		
		return panelModalitaConsegna;
	}
	
	private void settaCheckBox(JCheckBox checkBoxIn) {
		checkBoxIn.setFont(new Font("Ubuntu Sans", Font.BOLD, 25));
		checkBoxIn.setBackground(MyJPanel.uninaLightColor);
		checkBoxIn.setSelectedIcon(new ImageIcon("images/iconaCheckedCheckBox.png"));
		checkBoxIn.setIcon(new ImageIcon("images/iconaUncheckedCheckBox.png"));
		checkBoxIn.setFocusPainted(false);
		checkBoxIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				checkBoxIn.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseExited(MouseEvent me) {
				checkBoxIn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent me) {
				panelErroreModalitaDiConsegna.setVisible(false);
			}
		});
	}
	
	private MyJPanel creaPanelDettagliIncontri(ArrayList<SedeUniversita> sediPresenti) {
		panelDettagliIncontri = new MyJPanel();
		panelDettagliIncontri.setVisible(false);
		panelDettagliIncontri.setLayout(new BorderLayout());
		panelDettagliIncontri.setBackground(MyJPanel.uninaColorClicked);
		panelDettagliIncontri.setPreferredSize(new Dimension(1225, 150));
		panelDettagliIncontri.setMaximumSize(new Dimension(1225, 150));
		
		MyJPanel panelSuperiore = new MyJPanel(MyJPanel.uninaColorClicked);
		panelSuperiore.setPreferredSize(new Dimension(1225, 35));
		panelSuperiore.setMaximumSize(new Dimension(1225, 35));

		panelSuperiore.setLayout(new BoxLayout(panelSuperiore, BoxLayout.X_AXIS));
		panelSuperiore.setAlignmentX(LEFT_ALIGNMENT);
		MyJLabel lblDettagliIncontri = new MyJLabel("Dettagli incontri");
		lblDettagliIncontri.setFont(new Font("Ubuntu Sans", Font.BOLD, 30));
		lblDettagliIncontri.setForeground(Color.white);
		lblDettagliIncontri.setAlignmentX(LEFT_ALIGNMENT);
		lblDettagliIncontri.setBorder(new EmptyBorder(0, 10, 0, 0));
		panelSuperiore.add(lblDettagliIncontri);
		
		MyJPanel panelCentrale = new MyJPanel();
		panelCentrale.setPreferredSize(new Dimension(1225, 150));
		panelCentrale.setMaximumSize(new Dimension(1225, 250));
		panelCentrale.setLayout(new BorderLayout());
		panelCentrale.setBackground(MyJPanel.uninaLightColor);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.NORTH);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.WEST);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.SOUTH);
		panelCentrale.add(new MyJPanel(MyJPanel.uninaLightColor), BorderLayout.EAST);
		
		MyJPanel panelPerIncontri = new MyJPanel();
		panelPerIncontri.setLayout(new BoxLayout(panelPerIncontri, BoxLayout.Y_AXIS));
		panelPerIncontri.setBackground(MyJPanel.uninaLightColor);
		
		
		MyJPanel rigaIncontro = new MyJPanel();
		rigaIncontro.setBackground(MyJPanel.uninaLightColor);
//		rigaIncontro.setLayout(new BoxLayout(rigaIncontro, BoxLayout.X_AXIS));
		rigaIncontro.setLayout(new FlowLayout());
		rigaIncontro.setPreferredSize(new Dimension(1225, 250));
		rigaIncontro.setMaximumSize(new Dimension(1225, 300));
		
		JComboBox sediUniversitaCB = this.creaCBSediUniversita(sediPresenti);
		JComboBox oraInizioIncontroCB = this.creaCBOraIncontro();
		JComboBox minutoInizioIncontroCB = this.creaCBMinutoIncontro();
		JComboBox oraFineIncontroCB = this.creaCBOraIncontro();
		JComboBox minutoFineIncontroCB = this.creaCBMinutoIncontro();
		JComboBox giornoIncontroCB = this.creaCBGiornoIncontro();
		
		rigaIncontro.add(Box.createRigidArea(new Dimension(50, 0)));
		rigaIncontro.add(Box.createHorizontalGlue());
		rigaIncontro.add(new MyJLabel("Incontriamoci a ", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(sediUniversitaCB);
		rigaIncontro.add(new MyJLabel(" dalle ", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(oraInizioIncontroCB);
		rigaIncontro.add(new MyJLabel(":", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(minutoInizioIncontroCB);
		rigaIncontro.add(new MyJLabel(" alle ", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(oraFineIncontroCB);
		rigaIncontro.add(new MyJLabel(":", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(minutoFineIncontroCB);
		rigaIncontro.add(new MyJLabel(" di ", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(giornoIncontroCB);
		
		MyJLabel lblAggiungiIncontro = new MyJLabel();
		lblAggiungiIncontro.aggiungiImmagineScalata("images/iconaAggiungiIncontro.png", 50, 50, true);
		lblAggiungiIncontro.rendiLabelInteragibile();
		
		lblAggiungiIncontro.setOnMouseClickedAction(() -> {
			if(numeroIncontri < 5) {
				panelDettagliIncontri.setPreferredSize(new Dimension(1225, panelDettagliIncontri.getHeight()+120));
				panelDettagliIncontri.setMaximumSize(new Dimension(1225, panelDettagliIncontri.getHeight()+120));

				panelDettagliIncontri.revalidate();
				panelDettagliIncontri.repaint();

				panelPerIncontri.setPreferredSize(new Dimension(1225, panelPerIncontri.getHeight()+120));
				panelPerIncontri.setMaximumSize(new Dimension(1225, panelPerIncontri.getHeight()+120));
				
				panelPerIncontri.revalidate();
				panelPerIncontri.repaint();
				
				panelPerIncontri.add(aggiungiNuovaRigaIncontro(panelPerIncontri, sediPresenti));
				
				numeroIncontri++;
			}
		});
		
		lblAggiungiIncontro.setOnMouseEnteredAction(() -> {});
		lblAggiungiIncontro.setOnMouseExitedAction(() -> {});
		
		rigaIncontro.add(lblAggiungiIncontro);
		panelPerIncontri.add(rigaIncontro);
		
		RigaIncontro nuovoIncontro = new RigaIncontro(sediUniversitaCB, oraInizioIncontroCB, minutoInizioIncontroCB, oraFineIncontroCB, minutoFineIncontroCB,
													  giornoIncontroCB);
		this.incontriSpecificati.add(nuovoIncontro);
		
		panelCentrale.add(panelPerIncontri, BorderLayout.CENTER);
		
		panelDettagliIncontri.add(panelSuperiore, BorderLayout.NORTH);
		panelDettagliIncontri.add(panelCentrale, BorderLayout.CENTER);
		
		
		return panelDettagliIncontri;
	}
	
	private MyJPanel aggiungiNuovaRigaIncontro(MyJPanel panelPerIncontri, ArrayList<SedeUniversita> sediPresenti) {
		MyJPanel rigaIncontro = new MyJPanel();
		rigaIncontro.setBackground(MyJPanel.uninaLightColor);
//		rigaIncontro.setLayout(new BoxLayout(rigaIncontro, BoxLayout.X_AXIS));
		rigaIncontro.setLayout(new FlowLayout());
		rigaIncontro.setPreferredSize(new Dimension(1225, 250));
		rigaIncontro.setMaximumSize(new Dimension(1225, 250));
		
		JComboBox<SedeUniversita> sediUniversitaCB = this.creaCBSediUniversita(sediPresenti);

		JComboBox oraInizioIncontroCB = this.creaCBOraIncontro();
		JComboBox minutoInizioIncontroCB = this.creaCBMinutoIncontro();
		JComboBox oraFineIncontroCB = this.creaCBOraIncontro();
		JComboBox minutoFineIncontroCB = this.creaCBMinutoIncontro();
		JComboBox giornoIncontroCB = this.creaCBGiornoIncontro();
		
		rigaIncontro.add(Box.createRigidArea(new Dimension(50, 0)));
		rigaIncontro.add(new MyJLabel("Incontriamoci a ", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(sediUniversitaCB);
		rigaIncontro.add(new MyJLabel(" dalle ", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(oraInizioIncontroCB);
		rigaIncontro.add(new MyJLabel(":", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(minutoInizioIncontroCB);
		rigaIncontro.add(new MyJLabel(" alle ", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(oraFineIncontroCB);
		rigaIncontro.add(new MyJLabel(":", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(minutoFineIncontroCB);
		rigaIncontro.add(new MyJLabel(" di ", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(giornoIncontroCB);
		
		RigaIncontro nuovoIncontro = new RigaIncontro(
				sediUniversitaCB, 
				oraInizioIncontroCB, 
				minutoInizioIncontroCB, 
				oraFineIncontroCB, 
				minutoFineIncontroCB,
				giornoIncontroCB
		);
		
		this.incontriSpecificati.add(nuovoIncontro);
		
		MyJLabel lblRimuoviIncontro = new MyJLabel();
		lblRimuoviIncontro.aggiungiImmagineScalata("images/iconaRimuoviIncontro.png" , 50, 50, true);
		lblRimuoviIncontro.rendiLabelInteragibile();
		
		lblRimuoviIncontro.setOnMouseClickedAction(() -> {
			panelDettagliIncontri.setPreferredSize(new Dimension(1225, panelDettagliIncontri.getHeight()-120));
			panelDettagliIncontri.setMaximumSize(new Dimension(1225, panelDettagliIncontri.getHeight()-120));

			panelDettagliIncontri.revalidate();
			panelDettagliIncontri.repaint();

			panelPerIncontri.setPreferredSize(new Dimension(1225, panelPerIncontri.getHeight()-120));
			panelPerIncontri.setMaximumSize(new Dimension(1225, panelPerIncontri.getHeight()-120));
			
			panelPerIncontri.revalidate();
			panelPerIncontri.repaint();
				
			panelPerIncontri.remove(rigaIncontro);
			this.incontriSpecificati.remove(nuovoIncontro);
			
			numeroIncontri--;
		});
		
		lblRimuoviIncontro.setOnMouseEnteredAction(() -> {});
		lblRimuoviIncontro.setOnMouseExitedAction(() -> {});
		
		rigaIncontro.add(lblRimuoviIncontro);
		
		MyJLabel lblAggiungiIncontro = new MyJLabel();
		lblAggiungiIncontro.aggiungiImmagineScalata("images/iconaAggiungiIncontro.png", 50, 50, true);
		lblAggiungiIncontro.rendiLabelInteragibile();
		
		lblAggiungiIncontro.setOnMouseClickedAction(() -> {
			if(numeroIncontri < 5) {
				panelDettagliIncontri.setPreferredSize(new Dimension(1225, panelDettagliIncontri.getHeight()+120));
				panelDettagliIncontri.setMaximumSize(new Dimension(1225, panelDettagliIncontri.getHeight()+120));

				panelDettagliIncontri.revalidate();
				panelDettagliIncontri.repaint();

				panelPerIncontri.setPreferredSize(new Dimension(1225, panelPerIncontri.getHeight()+120));
				panelPerIncontri.setMaximumSize(new Dimension(1225, panelPerIncontri.getHeight()+120));
				
				panelPerIncontri.revalidate();
				panelPerIncontri.repaint();
					
				panelPerIncontri.add(aggiungiNuovaRigaIncontro(panelPerIncontri, sediPresenti));
				
				numeroIncontri++;
			}
		});
		
		lblAggiungiIncontro.setOnMouseEnteredAction(() -> {});
		lblAggiungiIncontro.setOnMouseExitedAction(() -> {});
		
		rigaIncontro.add(lblAggiungiIncontro);
		rigaIncontro.add(Box.createRigidArea(new Dimension(0, 10)));
		
		return rigaIncontro;
	}
	
	private JComboBox creaCBOraIncontro() {
		JComboBox oraIncontro = new JComboBox();
		oraIncontro.setPreferredSize(new Dimension(65, 30));
		oraIncontro.setMaximumSize(new Dimension(65, 30));
		oraIncontro.setFont(new Font("Ubuntu Sans", Font.PLAIN, 20));
		oraIncontro.setBackground(Color.white);
		
		oraIncontro.addItem("08");
		oraIncontro.addItem("09");
		oraIncontro.addItem("10");
		oraIncontro.addItem("11");
		oraIncontro.addItem("12");
		oraIncontro.addItem("13");
		oraIncontro.addItem("14");
		oraIncontro.addItem("15");
		oraIncontro.addItem("16");
		oraIncontro.addItem("17");
		oraIncontro.addItem("18");
		oraIncontro.addItem("19");
		oraIncontro.addItem("20");
		
		return oraIncontro;
	}
	
	private JComboBox creaCBMinutoIncontro() {
		JComboBox minutoIncontro = new JComboBox();
		minutoIncontro.setBackground(Color.white);
		minutoIncontro.setPreferredSize(new Dimension(65, 30));
		minutoIncontro.setMaximumSize(new Dimension(65, 30));
		minutoIncontro.setFont(new Font("Ubuntu Sans", Font.PLAIN, 20));
		
		minutoIncontro.addItem("00");
		minutoIncontro.addItem("15");
		minutoIncontro.addItem("30");
		minutoIncontro.addItem("45");
		
		return minutoIncontro;
	}
	
	private JComboBox creaCBGiornoIncontro() {
		JComboBox giornoIncontro = new JComboBox();
		giornoIncontro.setBackground(Color.white);
		giornoIncontro.setPreferredSize(new Dimension(125, 30));
		giornoIncontro.setMaximumSize(new Dimension(125, 30));
		giornoIncontro.setFont(new Font("Ubuntu Sans", Font.PLAIN, 20));
		
		giornoIncontro.addItem("Lunedì");
		giornoIncontro.addItem("Martedì");
		giornoIncontro.addItem("Mercoledì");
		giornoIncontro.addItem("Giovedì");
		giornoIncontro.addItem("Venerdì");

		return giornoIncontro;
	}
	
	private JComboBox creaCBSediUniversita(ArrayList<SedeUniversita> sediPresenti) {
		JComboBox<SedeUniversita> sediUniversita = new JComboBox();
		sediUniversita.setBackground(Color.white);
		sediUniversita.setPreferredSize(new Dimension(950, 30));
		sediUniversita.setMaximumSize(new Dimension(950, 30));
		sediUniversita.setFont(new Font("Ubuntu Sans", Font.PLAIN, 20));
		
		for(SedeUniversita sedeCorrente : sediPresenti)
			sediUniversita.addItem(sedeCorrente);
		
		return sediUniversita;
	}
	
	private JComboBox creaCBCategorieOggetto() {
		JComboBox categorieOggetto = new JComboBox();
		categorieOggetto.setBackground(Color.white);
		categorieOggetto.setPreferredSize(new Dimension(300, 30));
		categorieOggetto.setMaximumSize(new Dimension(300, 30));
		categorieOggetto.setFont(new Font("Ubuntu Sans", Font.PLAIN, 20));
	
		categorieOggetto.addItem("Libri di testo");
		categorieOggetto.addItem("Appunti");
		categorieOggetto.addItem("Elettronica e Informatica");
		categorieOggetto.addItem("Libri");
		categorieOggetto.addItem("Per la casa");
		categorieOggetto.addItem("Cura della persona");
		categorieOggetto.addItem("Abbigliamento");
		categorieOggetto.addItem("Sport e Tempo libero");
		categorieOggetto.addItem("Musica");
		categorieOggetto.addItem("Film");
		categorieOggetto.addItem("Collezionismo");
		
		return categorieOggetto;
	}
	

	private JComboBox creaCBCondizioniOggetto() {
		JComboBox condizioniOggetto = new JComboBox();
		condizioniOggetto.setBackground(Color.white);
		condizioniOggetto.setPreferredSize(new Dimension(300, 30));
		condizioniOggetto.setMaximumSize(new Dimension(300, 30));
		condizioniOggetto.setFont(new Font("Ubuntu Sans", Font.PLAIN, 20));
	
		condizioniOggetto.addItem("Nuovo");
		condizioniOggetto.addItem("Usato - come nuovo");
		condizioniOggetto.addItem("Usato - ottime condizioni");
		condizioniOggetto.addItem("Usato - buone condizioni");
		condizioniOggetto.addItem("Usato - condizioni accettabili");
		condizioniOggetto.addItem("Usato - usurato");
		condizioniOggetto.addItem("Ricondizionato");
	
		return condizioniOggetto;
	}
	
	private MyJPanel creaPanelBottoni() {
		MyJPanel panelBottoni = new MyJPanel();
		panelBottoni.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelBottoni.setBackground(Color.white);
		panelBottoni.setPreferredSize(new Dimension(1225, 100));
		panelBottoni.setMaximumSize(new Dimension(1225, 100));
		
		bottoneTornaIndietro = new MyJButton("Torna alla home page");
		bottoneTornaIndietro.setPreferredSize(new Dimension(300, 100));
		bottoneTornaIndietro.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		bottoneTornaIndietro.setDefaultAction(() -> {mainController.passaAFrameHomePage(this);});
		
		bottonePubblicaAnnuncio = new MyJButton("Pubblica annuncio");
		bottonePubblicaAnnuncio.setPreferredSize(new Dimension(300, 100));
		bottonePubblicaAnnuncio.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		bottonePubblicaAnnuncio.setDefaultAction(() -> {this.clickBottonePubblicaAnnuncio();});

		panelBottoni.add(bottoneTornaIndietro);
		panelBottoni.add(bottonePubblicaAnnuncio);
		
		return panelBottoni;
	}
	
	private void clickBottonePubblicaAnnuncio() {
		try {
			resettaBordiTextField(new EmptyBorder(0, 10, 0, 0), this.nomeAnnuncioTextField);
			resettaBordiTextA(new EmptyBorder(0, 10, 0, 0), this.inserisciDescrizioneTextA);	
			nascondiPanelErrore(this.panelErroreNomeAnnuncio, this.panelErroreFoto, this.panelErroreDescrizione, this.panelErroreModalitaDiConsegna);
			
			if(tipoAnnuncio == "Vendita") {
				resettaBordiTextField(new EmptyBorder(0, 10, 0, 0), this.prezzoInizialeTextField);
				nascondiPanelErrore(this.panelErrorePrezzoIniziale);
			}
			else if(tipoAnnuncio == "Scambio") {
				resettaBordiTextA(new EmptyBorder(0, 10, 0, 0), this.inserisciNotaScambioTextA);
				nascondiPanelErrore(this.panelErroreNotaScambio);
			}
			
			System.out.println(foto1Caricata);
			System.out.println(foto2Caricata);
			System.out.println(foto3Caricata);

			checkDatiInseriti();
			Annuncio annuncioDaPassare = organizzaDatiDaPassareAlController();

			mainController.onPubblicaAnnuncioButtonClicked(annuncioDaPassare);
		}
		catch(NomeAnnuncioException exc1) {
			this.settaLabelETextFieldDiErrore(lblErroreNomeAnnuncio, exc1.getMessage(), this.nomeAnnuncioTextField);
			SwingUtilities.invokeLater(() -> {
				this.nomeAnnuncioTextField.requestFocusInWindow();
				this.panelCentrale.scrollRectToVisible(nomeAnnuncioTextField.getBounds());				
			});
			panelErroreNomeAnnuncio.setVisible(true);
		}
		catch(FotoException exc2) {
			SwingUtilities.invokeLater(() -> {
				this.nomeAnnuncioTextField.requestFocusInWindow();
				this.panelCentrale.scrollRectToVisible(nomeAnnuncioTextField.getBounds());				
			});
			lblErroreFoto.setText(exc2.getMessage());
			panelErroreFoto.setVisible(true);
		}
		catch(DescrizioneException exc3) {
			this.settaLabelETextAreaDiErrore(lblErroreDescrizione, exc3.getMessage(), this.inserisciDescrizioneTextA);
			SwingUtilities.invokeLater(() -> {
				this.panelInserimentoDati.requestFocusInWindow();
				this.panelCentrale.scrollRectToVisible(inserisciDescrizioneTextA.getBounds());				
			});
			panelErroreDescrizione.setVisible(true);
		}
		catch(NotaScambioException exc4) {
			this.settaLabelETextAreaDiErrore(lblErroreNotaScambio, exc4.getMessage(), this.inserisciNotaScambioTextA);
			SwingUtilities.invokeLater(() -> {
				this.panelInserimentoDati.requestFocusInWindow();
				this.panelCentrale.scrollRectToVisible(inserisciNotaScambioTextA.getBounds());				
			});	
			panelErroreNotaScambio.setVisible(true);
		}
		catch(PrezzoInizialeException exc5) {
			this.settaLabelETextFieldDiErrore(lblErrorePrezzoIniziale, exc5.getMessage(), this.prezzoInizialeTextField);
			SwingUtilities.invokeLater(() -> {
				this.panelInserimentoDati.requestFocusInWindow();
				this.panelCentrale.scrollRectToVisible(inserisciNotaScambioTextA.getBounds());				
			});	
			panelErrorePrezzoIniziale.setVisible(true);
		}
		catch(ModalitaDiConsegnaException exc6) {
			SwingUtilities.invokeLater(() -> {
				this.panelInserimentoDati.requestFocusInWindow();
				this.panelCentrale.scrollRectToVisible(inserisciNotaScambioTextA.getBounds());				
			});	
			lblErroreModalitaConsegna.setText(exc6.getMessage());
			panelErroreModalitaDiConsegna.setVisible(true);
		}
		catch(SQLException exc7) {
			String statoDiErrore = exc7.getSQLState();
			String messaggioDiErrore = exc7.getMessage();
			System.out.println(statoDiErrore);
			System.out.println(messaggioDiErrore);
			System.out.println(exc7.getErrorCode());	
			
			if(statoDiErrore.equals("23505"))
				lblErroreModalitaConsegna.setText("Non puoi inserire due incontri identici.");
			else if(exc7.getMessage().contains("check_inizio_minore_fine"))
				lblErroreModalitaConsegna.setText("L'ora di inizio dell'incontro deve essere minore dell'ora di fine incontro");
			
			SwingUtilities.invokeLater(() -> {
				this.panelInserimentoDati.requestFocusInWindow();
				this.panelCentrale.scrollRectToVisible(inserisciNotaScambioTextA.getBounds());				
			});
			
			panelErroreModalitaDiConsegna.setVisible(true);
		}
	}
	
	private void checkDatiInseriti() {
		checkNomeAnnuncio();
		checkFoto();
		checkDescrizioneAnnuncio();
		
		if(tipoAnnuncio == "Scambio")
			checkNotaScambio();
		
		if(tipoAnnuncio == "Vendita")
			checkPrezzoIniziale();
		
		checkModalitaConsegna();
	}
	
	private void checkNomeAnnuncio() throws NomeAnnuncioException{
		if(this.nomeAnnuncioTextField.getText().isEmpty() || 
		   this.nomeAnnuncioTextField.getText().equals("Titolo"))
		{
			throw new NomeAnnuncioException("Inserisci un nome per il tuo annuncio.");
		}
	}
	
	private void checkFoto() throws FotoException{
		if(!foto1Caricata.get() && !foto2Caricata.get() && !foto3Caricata.get())
			throw new FotoException("Devi caricare almeno una foto.");
		
	}

	private void checkDescrizioneAnnuncio() throws DescrizioneException{
		if(this.inserisciDescrizioneTextA.getText().isEmpty() ||
		   this.inserisciDescrizioneTextA.getText().equals("Dai una descrizione accurata dell'oggetto che stai vendendo. L'acquirente deve avere ben chiaro cosa starà acquistando!")) 
		{
			throw new DescrizioneException("Inserisci una descrizione per il tuo articolo.");
		}
	}
	
	private void checkNotaScambio() throws NotaScambioException{
		if(this.inserisciNotaScambioTextA.getText().isEmpty() ||
		   this.inserisciNotaScambioTextA.getText().equals("Sii preciso in quello che richiedi!")) 
		{
			throw new NotaScambioException("Inserisci una lista di oggetti con cui scambieresti il tuo articolo.");
		}
	}
	
	private void checkPrezzoIniziale() throws PrezzoInizialeException{
		if(this.prezzoInizialeTextField.getText().isEmpty())
			throw new PrezzoInizialeException("Inserisci il prezzo iniziale per il tuo articolo.");
		
		double prezzoIniziale = Double.valueOf(prezzoInizialeTextField.getText()) * 100;
		prezzoIniziale = Math.ceil(prezzoIniziale);
		prezzoIniziale /= 100;
		
		if(prezzoIniziale <= 0.50)
			throw new PrezzoInizialeException("Il prezzo iniziale deve essere di almeno 0.50€");
	}
	
	private void checkModalitaConsegna() throws ModalitaDiConsegnaException{
		if(!this.spedizioneCheckBox.isSelected() && !this.ritiroInPostaCheckBox.isSelected() && !this.incontroCheckBox.isSelected())
			throw new ModalitaDiConsegnaException("Seleziona almeno una modalità di consegna.");
	}
	
	private Annuncio organizzaDatiDaPassareAlController() {
		CategoriaEnum categoriaSelezionata = CategoriaEnum.confrontaConStringa(this.categorieOggettoComboBox.getSelectedItem().toString());
		CondizioneEnum condizioneSelezionata = CondizioneEnum.confrontaConStringa(this.condizioniOggettoComboBox.getSelectedItem().toString());
		
		Oggetto oggettoDaPassare = new Oggetto(categoriaSelezionata, condizioneSelezionata, 
				this.lblAggiungiFoto1.getImmagineInByte(), true);
		
		oggettoDaPassare.aggiungiImmagine(1, this.lblAggiungiFoto2.getImmagineInByte());
		oggettoDaPassare.aggiungiImmagine(2, this.lblAggiungiFoto3.getImmagineInByte());
		oggettoDaPassare.setDescrizione(this.inserisciDescrizioneTextA.getText());

		Annuncio annuncioDaPassare;
		if(tipoAnnuncio == "Vendita") {	
			double prezzoIniziale = Double.valueOf(this.prezzoInizialeTextField.getText()) * 100;
			prezzoIniziale = Math.ceil(prezzoIniziale);
			prezzoIniziale /= 100;
			
			annuncioDaPassare = new AnnuncioVendita(this.spedizioneCheckBox.isSelected(), this.ritiroInPostaCheckBox.isSelected(), this.incontroCheckBox.isSelected(),
				StatoAnnuncioEnum.Disponibile, this.nomeAnnuncioTextField.getText(), mainController.getUtenteLoggato(), oggettoDaPassare, prezzoIniziale);
		}
		else if(tipoAnnuncio == "Scambio") {
			annuncioDaPassare = new AnnuncioScambio(this.spedizioneCheckBox.isSelected(), this.ritiroInPostaCheckBox.isSelected(), this.incontroCheckBox.isSelected(),
					StatoAnnuncioEnum.Disponibile, this.nomeAnnuncioTextField.getText(), mainController.getUtenteLoggato(), oggettoDaPassare, this.inserisciNotaScambioTextA.getText());
		}
		else {
			annuncioDaPassare = new AnnuncioRegalo(this.spedizioneCheckBox.isSelected(), this.ritiroInPostaCheckBox.isSelected(), this.incontroCheckBox.isSelected(),
					StatoAnnuncioEnum.Disponibile, this.nomeAnnuncioTextField.getText(), mainController.getUtenteLoggato(), oggettoDaPassare);					
		}
		
		if(this.dataScadenzaComboBox.getSelectedIndex() == 1)
			annuncioDaPassare.setDataScadenza(Date.valueOf(LocalDate.now().plusDays(7)));
		else if(this.dataScadenzaComboBox.getSelectedIndex() == 2)
			annuncioDaPassare.setDataScadenza(Date.valueOf(LocalDate.now().plusDays(15)));
		else if(this.dataScadenzaComboBox.getSelectedIndex() == 3)
			annuncioDaPassare.setDataScadenza(Date.valueOf(LocalDate.now().plusDays(31)));
		
		if(this.incontroCheckBox.isSelected())
			this.aggiungiIncontriAdAnnuncio(annuncioDaPassare);
		
		return annuncioDaPassare;
	}
	
	private void aggiungiIncontriAdAnnuncio(Annuncio annuncioDaPassare) {
		for(RigaIncontro incontroAttuale : this.incontriSpecificati) {
			SedeUniversita sedeScelta = incontroAttuale.getSedeDiIncontro();
			String inizioIncontro = incontroAttuale.getOraInizioIncontro()+":"+incontroAttuale.getMinutoInizioIncontro();
			String fineIncontro = incontroAttuale.getOraFineIncontro()+":"+incontroAttuale.getMinutoFineIncontro();
			String giornoIncontro = incontroAttuale.getGiornoIncontro();

			annuncioDaPassare.aggiungiPropostaIncontro(sedeScelta, inizioIncontro, 
													   fineIncontro, GiornoEnum.confrontaConStringa(giornoIncontro));
		}
	}
	
	private void settaPanelFoto(MyJPanel panelContenenteFoto, MyJLabel lblAggiungiFoto, AtomicBoolean isFotoCaricata, Runnable onMouseClickedAction) {
		panelContenenteFoto.setBackground(MyJPanel.uninaLightColor);
		panelContenenteFoto.setLayout(new BoxLayout(panelContenenteFoto, BoxLayout.Y_AXIS));
		panelContenenteFoto.setPreferredSize(new Dimension(375, 550));
		panelContenenteFoto.setMaximumSize(new Dimension(375, 550));
		
		MyJPanel panelFoto = new MyJPanel(Color.white, new Dimension(375, 500));
		panelFoto.setMaximumSize(new Dimension(375, 500));
		panelFoto.setLayout(new BoxLayout(panelFoto, BoxLayout.Y_AXIS));
		panelFoto.setAlignmentX(CENTER_ALIGNMENT);
		lblAggiungiFoto.aggiungiImmagineScalata("images/iconaAggiungiImmagine.png", 100, 100, true);
		lblAggiungiFoto.setAlignmentX(CENTER_ALIGNMENT);
		panelFoto.add(Box.createVerticalGlue());
		panelFoto.add(Box.createHorizontalGlue());
		panelFoto.add(lblAggiungiFoto);
		panelFoto.add(Box.createHorizontalGlue());
		panelFoto.add(Box.createVerticalGlue());
				
		lblAggiungiFoto.rendiLabelInteragibile();
		lblAggiungiFoto.setOnMouseClickedAction(() -> {onMouseClickedAction.run();});
		lblAggiungiFoto.setOnMouseEnteredAction(() -> {});
		lblAggiungiFoto.setOnMouseExitedAction(() -> {});
		
		MyJLabel lblRimuoviFoto = new MyJLabel();
		lblRimuoviFoto.setAlignmentX(CENTER_ALIGNMENT);
		lblRimuoviFoto.aggiungiImmagineScalata("images/iconaCestino.png", 30, 30, true);
		
		lblRimuoviFoto.rendiLabelInteragibile();
		lblRimuoviFoto.setOnMouseExitedAction(() -> {});
		lblRimuoviFoto.setOnMouseEnteredAction(() -> {});
		lblRimuoviFoto.setOnMouseClickedAction(() -> {
			if(isFotoCaricata.get()) {
				lblAggiungiFoto.setImmagineInByte(null);
				lblAggiungiFoto.aggiungiImmagineScalata("images/iconaAggiungiImmagine.png", 100, 100, true);
				isFotoCaricata.set(false);
			}
		});
		
		panelContenenteFoto.add(panelFoto);
		panelContenenteFoto.add(lblRimuoviFoto);
	}
	
}