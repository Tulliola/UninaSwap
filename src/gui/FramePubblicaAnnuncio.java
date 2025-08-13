package gui;

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
import java.sql.SQLException;

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
import dto.ProfiloUtente;
import eccezioni.DescrizioneException;
import eccezioni.FotoException;
import eccezioni.ModalitaDiConsegnaException;
import eccezioni.NomeAnnuncioException;
import eccezioni.NotaScambioException;
import utilities.CategoriaEnum;
import utilities.CondizioneEnum;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;
import utilities.StatoAnnuncioEnum;
import dto.Oggetto;
import dto.Annuncio;
import dto.AnnuncioRegalo;
import dto.AnnuncioScambio;
import dto.AnnuncioVendita;

public class FramePubblicaAnnuncio extends MyJFrame {

	private static final long serialVersionUID = 1L;
	private Controller mainController;
	private String tipoAnnuncio;
	
	private MyJPanel contentPane = new MyJPanel();
	private MyJPanel panelAggiungiFoto = new MyJPanel();
	private MyJPanel panelDettagliIncontri;
	private MyJPanel panelNomeAnnuncio;
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
	private JComboBox sediUniversita = new JComboBox();
	private JComboBox categorieOggetto;
	private JComboBox condizioniOggetto;
	
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
	
	//Flag per immagini
	private boolean foto1Caricata;
	private boolean foto2Caricata;
	private boolean foto3Caricata;
	
	//Labels per le immagini
	private MyJLabel aggiungiFoto1;
	private MyJLabel aggiungiFoto2;
	private MyJLabel aggiungiFoto3;
	
	public FramePubblicaAnnuncio(Controller controller, String tipoAnnuncioDaPubblicare) {
		tipoAnnuncio = tipoAnnuncioDaPubblicare;
		mainController = controller;
		
		this.settaContentPane();
	}

	private void settaContentPane() {
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setLocationRelativeTo(null);
		this.setTitle("Pubblica ora il tuo nuovo annuncio di "+tipoAnnuncio.toLowerCase()+"!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane.setLayout(new BorderLayout());
		
		MyJPanel scrittaUninaSwap = new MyJPanel(MyJPanel.uninaColorClicked, new Dimension(Integer.MAX_VALUE, 50));
		scrittaUninaSwap.add(new MyJLabel("UninaSwap", new Font("Ubuntu Sans", Font.BOLD, 25), Color.white));
		
		contentPane.add(scrittaUninaSwap, BorderLayout.NORTH);
		contentPane.add(new MyJPanel(MyJPanel.uninaLightColor, new Dimension(100, contentPane.getHeight())), BorderLayout.WEST);
		contentPane.add(this.creaPanelCentrale(), BorderLayout.CENTER);
		contentPane.add(new MyJPanel(MyJPanel.uninaLightColor, new Dimension(100, contentPane.getHeight())), BorderLayout.EAST);
		contentPane.add(new MyJPanel(MyJPanel.uninaColorClicked, new Dimension(Integer.MAX_VALUE, 25)), BorderLayout.SOUTH);

		this.setContentPane(contentPane);
	}
	
	private JScrollPane creaPanelCentrale() {
		panelCentrale = new MyJPanel();
		panelCentrale.setLayout(new BoxLayout(panelCentrale, BoxLayout.Y_AXIS));
		panelCentrale.setBackground(Color.white);
		panelCentrale.setPreferredSize(new Dimension(375, 4000));
		panelCentrale.setAlignmentX(CENTER_ALIGNMENT);
		
		this.creaPanelAggiungiFoto();
		
		nomeAnnuncioTextField = new MyJTextField("Dai un nome al tuo annuncio!");
		nomeAnnuncioTextField.rendiTextFieldFocusable();
		
		nomeAnnuncioTextField.setFocusGainedAction(() -> {
			if(nomeAnnuncioTextField.getText().equals("Dai un nome al tuo annuncio!"))
				nomeAnnuncioTextField.setText("");
		});
		
		nomeAnnuncioTextField.setFocusLostAction(() -> {
			if(nomeAnnuncioTextField.getText().isEmpty())
				nomeAnnuncioTextField.setText("Dai un nome al tuo annuncio!");
		});
		
		nomeAnnuncioTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(nomeAnnuncioTextField.getText().length() >= 100)
					e.consume();
			}
		});
		
		nomeAnnuncioTextField.setFont(new Font("Ubuntu Sans", Font.BOLD, 30));
		nomeAnnuncioTextField.setForeground(Color.black);
		nomeAnnuncioTextField.setPreferredSize(new Dimension(1225, 40));
		nomeAnnuncioTextField.setOpaque(true);
		nomeAnnuncioTextField.setBackground(Color.white);
		nomeAnnuncioTextField.setMaximumSize(new Dimension(1225, 40));
		nomeAnnuncioTextField.setBorder(blackBorder);
		
		lblErroreNomeAnnuncio = new MyJLabel(true);
		lblErroreNomeAnnuncio.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		lblErroreNomeAnnuncio.setText("VAMOOOOOOOS");
		lblErroreNomeAnnuncio.setBackground(MyJPanel.uninaLightColor);
		lblErroreNomeAnnuncio.setVisible(true);
		lblErroreNomeAnnuncio.setAlignmentX(LEFT_ALIGNMENT);
		
		MyJPanel panelNomeAnnuncio = new MyJPanel();
		panelNomeAnnuncio.setPreferredSize(new Dimension(1225, 60));
		panelNomeAnnuncio.setMaximumSize(new Dimension(1225, 60));
		panelNomeAnnuncio.setLayout(new BorderLayout());
		panelNomeAnnuncio.setBackground(Color.white);
		
		MyJPanel panelSuperiore = new MyJPanel();
		panelSuperiore.setLayout(new BoxLayout(panelSuperiore, BoxLayout.X_AXIS));
		panelSuperiore.setBackground(Color.white);
		panelSuperiore.add(nomeAnnuncioTextField);
		
		panelErroreNomeAnnuncio = new MyJPanel();
		panelErroreNomeAnnuncio.setLayout(new BoxLayout(panelErroreNomeAnnuncio, BoxLayout.X_AXIS));
		panelErroreNomeAnnuncio.add(lblErroreNomeAnnuncio);
		panelErroreNomeAnnuncio.setBackground(Color.white);
		panelErroreNomeAnnuncio.setVisible(false);
		
		panelNomeAnnuncio.add(panelSuperiore, BorderLayout.NORTH);
		panelNomeAnnuncio.add(panelErroreNomeAnnuncio, BorderLayout.CENTER);
		
		panelCentrale.add(Box.createRigidArea(new Dimension(0, 20)));
		panelCentrale.add(panelNomeAnnuncio);
		panelCentrale.add(Box.createRigidArea(new Dimension(0, 20)));
		panelCentrale.add(panelAggiungiFoto);
		panelCentrale.add(Box.createRigidArea(new Dimension(0, 20)));
		panelCentrale.add(creaPanelInserimentoDati());
		panelCentrale.add(Box.createRigidArea(new Dimension(0, 20)));
		
		
		JScrollPane scrollPane = new JScrollPane(panelCentrale);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);

		return scrollPane;
	}

	private MyJPanel creaPanelAggiungiFoto() {
		panelAggiungiFoto = new MyJPanel();
		panelAggiungiFoto.setLayout(new BorderLayout());
		panelAggiungiFoto.setPreferredSize(new Dimension(1225, 600));
		panelAggiungiFoto.setMaximumSize(new Dimension(1225, 600));
		
		MyJPanel panelSuperiore = new MyJPanel(MyJPanel.uninaColorClicked);
		panelSuperiore.setLayout(new BoxLayout(panelSuperiore, BoxLayout.X_AXIS));
		MyJLabel lblAggiungiFoto = new MyJLabel("Carica fino ad un massimo di 3 foto! (Si consiglia la risoluzione 3:4)");
		lblAggiungiFoto.setFont(new Font("Ubuntu Sans", Font.BOLD, 30));
		lblAggiungiFoto.setForeground(Color.white);
		lblAggiungiFoto.setAlignmentX(LEFT_ALIGNMENT);
		lblAggiungiFoto.setBorder(new EmptyBorder(0, 10, 0, 0));
		panelSuperiore.add(lblAggiungiFoto);
		panelSuperiore.setAlignmentX(LEFT_ALIGNMENT);
		
		MyJPanel panelContieniFoto = new MyJPanel();
		panelContieniFoto.setLayout(new FlowLayout());
		panelContieniFoto.setBackground(MyJPanel.uninaLightColor);
		
		
		MyJPanel panelFoto1 = new MyJPanel(Color.white, new Dimension(375, 500));
		panelFoto1.setMaximumSize(new Dimension(375, 500));
		panelFoto1.setLayout(new BoxLayout(panelFoto1, BoxLayout.Y_AXIS));
		panelFoto1.setAlignmentX(CENTER_ALIGNMENT);
		aggiungiFoto1 = new MyJLabel();
		aggiungiFoto1.aggiungiImmagineScalata("images/iconaAggiungiImmagine.png", 100, 100, true);
		aggiungiFoto1.setAlignmentX(CENTER_ALIGNMENT);
		panelFoto1.add(Box.createVerticalGlue());
		panelFoto1.add(Box.createHorizontalGlue());
		panelFoto1.add(aggiungiFoto1);
		panelFoto1.add(Box.createHorizontalGlue());
		panelFoto1.add(Box.createVerticalGlue());
		
		aggiungiFoto1.rendiLabelInteragibile();
		aggiungiFoto1.setOnMouseClickedAction(() -> {foto1Caricata = aggiungiFoto1.aggiungiImmagineDaFileSystem();});
		aggiungiFoto1.setOnMouseEnteredAction(() -> {});
		aggiungiFoto1.setOnMouseExitedAction(() -> {});

		MyJPanel panelFoto2 = new MyJPanel(Color.white, new Dimension(375, 500));
		panelFoto2.setLayout(new BoxLayout(panelFoto2, BoxLayout.Y_AXIS));
		panelFoto2.setAlignmentX(CENTER_ALIGNMENT);
		aggiungiFoto2 = new MyJLabel();
		aggiungiFoto2.aggiungiImmagineScalata("images/iconaAggiungiImmagine.png", 100, 100, true);
		aggiungiFoto2.setAlignmentX(CENTER_ALIGNMENT);
		panelFoto2.add(Box.createVerticalGlue());
		panelFoto2.add(Box.createHorizontalGlue());
		panelFoto2.add(aggiungiFoto2);
		panelFoto2.add(Box.createHorizontalGlue());
		panelFoto2.add(Box.createVerticalGlue());
		panelFoto2.setMaximumSize(new Dimension(375, 500));
		
		aggiungiFoto2.rendiLabelInteragibile();
		aggiungiFoto2.setOnMouseClickedAction(() -> {foto2Caricata = aggiungiFoto2.aggiungiImmagineDaFileSystem();});
		aggiungiFoto2.setOnMouseEnteredAction(() -> {});
		aggiungiFoto2.setOnMouseExitedAction(() -> {});
		
		MyJPanel panelFoto3 = new MyJPanel(Color.white, new Dimension(375, 500));
		panelFoto3.setLayout(new BoxLayout(panelFoto3, BoxLayout.Y_AXIS));
		panelFoto3.setAlignmentX(CENTER_ALIGNMENT);
		aggiungiFoto3 = new MyJLabel();
		aggiungiFoto3.aggiungiImmagineScalata("images/iconaAggiungiImmagine.png", 100, 100, true);
		aggiungiFoto3.setAlignmentX(CENTER_ALIGNMENT);
		panelFoto3.add(Box.createVerticalGlue());
		panelFoto3.add(Box.createHorizontalGlue());
		panelFoto3.add(aggiungiFoto3);
		panelFoto3.add(Box.createHorizontalGlue());
		panelFoto3.add(Box.createVerticalGlue());
		panelFoto3.setMaximumSize(new Dimension(375, 500));
		
		aggiungiFoto3.rendiLabelInteragibile();
		aggiungiFoto3.setOnMouseClickedAction(() -> {foto3Caricata = aggiungiFoto3.aggiungiImmagineDaFileSystem();});
		aggiungiFoto3.setOnMouseEnteredAction(() -> {});
		aggiungiFoto3.setOnMouseExitedAction(() -> {});
		
		panelContieniFoto.add(panelFoto1);
		panelContieniFoto.add(panelFoto2);
		panelContieniFoto.add(panelFoto3);
		
		panelErroreFoto.setPreferredSize(new Dimension(1225, 30));
		panelErroreFoto.setMaximumSize(new Dimension(1225, 30));
		panelErroreFoto.setBackground(MyJPanel.uninaLightColor);
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
	
	private MyJPanel creaPanelInserimentoDati() {
		panelInserimentoDati = new MyJPanel();
		panelInserimentoDati.setBackground(Color.white);
		panelInserimentoDati.setLayout(new BoxLayout(panelInserimentoDati, BoxLayout.Y_AXIS));
		panelInserimentoDati.setPreferredSize(new Dimension(1225, 4000));
		panelInserimentoDati.setMaximumSize(new Dimension(1225, 4000));

		inserisciDescrizioneTextA = new JTextArea();
		this.settaTextArea(inserisciDescrizioneTextA);
		String stringaDiDefaultPerDescrizione = "Dai una descrizione accurata dell'oggetto che stai vendendo. L'acquirente deve avere ben chiaro cosa starà acquistando!";
		lblErroreDescrizione = new MyJLabel(true);
		
		inserisciNotaScambioTextA = new JTextArea();
		this.settaTextArea(inserisciNotaScambioTextA);
		String stringaDiDefaultPerNotaScambio = "Sii preciso in quello che richiedi!";
		lblErroreNotaScambio = new MyJLabel(true);
		
		prezzoInizialeTextField = new MyJTextField();
		prezzoInizialeTextField.setPreferredSize(new Dimension(300, 30));
		prezzoInizialeTextField.setMaximumSize(new Dimension(300, 30));
		
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
		panelInserimentoDati.add(this.creaPanelDettagliIncontri());
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
		
		JScrollPane scrollPaneTesto = new JScrollPane(componentToAdd);
		scrollPaneTesto.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPaneTesto.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelCentrale.add(scrollPaneTesto, BorderLayout.CENTER);
		
		panelDiErrore.setPreferredSize(new Dimension(1225, 30));
		panelDiErrore.setMaximumSize(new Dimension(1225, 30));
		panelDiErrore.setBackground(MyJPanel.uninaLightColor);
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
		categorieOggetto = this.creaCBCategorieOggetto();
		categorieOggetto.setSelectedIndex(0);
		categorieOggetto.setAlignmentX(LEFT_ALIGNMENT);
		panelPerComboBox.add(categorieOggetto);
		
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
		condizioniOggetto = this.creaCBCondizioniOggetto();
		condizioniOggetto.setSelectedIndex(0);
		condizioniOggetto.setAlignmentX(LEFT_ALIGNMENT);
		panelPerComboBox.add(condizioniOggetto);
		
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
		
		MyJPanel panelInferiore = new MyJPanel(new Color(220, 220, 220));
		panelInferiore.setLayout(new BoxLayout(panelInferiore, BoxLayout.X_AXIS));
		panelInferiore.setAlignmentX(LEFT_ALIGNMENT);
		panelInferiore.setBackground(MyJPanel.uninaLightColor);
		panelInferiore.setVisible(false);
		MyJLabel lblDiErrore = new MyJLabel(true);
		lblDiErrore.setVisible(true);
		lblDiErrore.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		lblDiErrore.setBorder(new EmptyBorder(0, 10, 0, 0));
		lblDiErrore.setAlignmentX(LEFT_ALIGNMENT);
		
		textFieldIn.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char carattereDigitato = e.getKeyChar();
				
				if(Character.isDigit(carattereDigitato)) {
					int posizioneDiUnPunto = textFieldIn.getText().indexOf('.');
					
					if(posizioneDiUnPunto != -1 && textFieldIn.getText().length() - posizioneDiUnPunto > 2)
						e.consume();
					else {
						panelInferiore.setVisible(false);
						textFieldIn.settaBordiTextFieldStandard();
					}
				}
				else if(carattereDigitato != '.'){
					e.consume();
					textFieldIn.settaBordiTextFieldErrore();
					panelInferiore.setVisible(true);
					lblDiErrore.setText("Formato non valido.");
				}
				else if(textFieldIn.getText().contains(".") || textFieldIn.getText().startsWith(".")) {
					e.consume();
					textFieldIn.settaBordiTextFieldErrore();
					panelInferiore.setVisible(true);
					lblDiErrore.setText("Formato non valido.");
				}
				else {
					textFieldIn.settaBordiTextFieldStandard();
					panelInferiore.setVisible(false);
				}
			}
		});
		panelPerTextField.add(textFieldIn);
		panelInferiore.add(lblDiErrore);
		
		panelPrezzoIniziale.add(panelSuperiore, BorderLayout.NORTH);
		panelPrezzoIniziale.add(panelCentrale, BorderLayout.CENTER);
		panelPrezzoIniziale.add(panelInferiore, BorderLayout.SOUTH);
		
		
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
		JComboBox periodiDiScadenza = new JComboBox();
		periodiDiScadenza.setBackground(Color.white);
		periodiDiScadenza.setPreferredSize(new Dimension(300, 30));
		periodiDiScadenza.setMaximumSize(new Dimension(300, 30));
		periodiDiScadenza.setFont(new Font("Ubuntu Sans", Font.PLAIN, 20));

		periodiDiScadenza.addItem("Non far scadere l'annuncio");
		periodiDiScadenza.addItem("7 giorni");
		periodiDiScadenza.addItem("15 giorni");
		periodiDiScadenza.addItem("31 giorni");
		periodiDiScadenza.setSelectedIndex(0);
		periodiDiScadenza.setAlignmentX(LEFT_ALIGNMENT);
		panelPerComboBox.add(periodiDiScadenza);
		
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
		panelErroreModalitaDiConsegna.setBackground(MyJPanel.uninaLightColor);
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
	
	private MyJPanel creaPanelDettagliIncontri() {
		panelDettagliIncontri = new MyJPanel();
		panelDettagliIncontri.setVisible(false);
		panelDettagliIncontri.setLayout(new BorderLayout());
		panelDettagliIncontri.setBackground(MyJPanel.uninaColorClicked);
		panelDettagliIncontri.setPreferredSize(new Dimension(1225, 135));
		panelDettagliIncontri.setMaximumSize(new Dimension(1225, 135));
		
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
		panelCentrale.setPreferredSize(new Dimension(1225, 100));
		panelCentrale.setMaximumSize(new Dimension(1225, 100));
		
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
		rigaIncontro.setLayout(new BoxLayout(rigaIncontro, BoxLayout.X_AXIS));
		rigaIncontro.setPreferredSize(new Dimension(1225, 100));
		rigaIncontro.setMaximumSize(new Dimension(1225, 100));
		
		rigaIncontro.add(Box.createRigidArea(new Dimension(50, 0)));
		rigaIncontro.add(Box.createHorizontalGlue());
		rigaIncontro.add(new MyJLabel("Incontriamoci a ", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(Box.createHorizontalGlue());
		rigaIncontro.add(this.creaCBSediUniversita());
		rigaIncontro.add(Box.createHorizontalGlue());
		rigaIncontro.add(new MyJLabel(" dalle ", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(Box.createHorizontalGlue());
		rigaIncontro.add(this.creaCBOraIncontro());
		rigaIncontro.add(new MyJLabel(":", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(this.creaCBMinutoIncontro());
		rigaIncontro.add(Box.createHorizontalGlue());
		rigaIncontro.add(new MyJLabel(" alle ", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(Box.createHorizontalGlue());
		rigaIncontro.add(this.creaCBOraIncontro());
		rigaIncontro.add(new MyJLabel(":", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(this.creaCBMinutoIncontro());
		rigaIncontro.add(Box.createHorizontalGlue());
		rigaIncontro.add(new MyJLabel(" di ", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(Box.createHorizontalGlue());
		rigaIncontro.add(this.creaCBGiornoIncontro());
		rigaIncontro.add(Box.createHorizontalGlue());
		
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
				
				panelPerIncontri.add(aggiungiNuovaRigaIncontro(panelPerIncontri));
				
				numeroIncontri++;
			}
		});
		
		lblAggiungiIncontro.setOnMouseEnteredAction(() -> {});
		lblAggiungiIncontro.setOnMouseExitedAction(() -> {});
		
		rigaIncontro.add(lblAggiungiIncontro);
		panelPerIncontri.add(rigaIncontro);
		
		panelCentrale.add(panelPerIncontri, BorderLayout.CENTER);
		
		panelDettagliIncontri.add(panelSuperiore, BorderLayout.NORTH);
		panelDettagliIncontri.add(panelCentrale, BorderLayout.CENTER);
		
		
		return panelDettagliIncontri;
	}
	
	private MyJPanel aggiungiNuovaRigaIncontro(MyJPanel panelPerIncontri) {
		MyJPanel rigaIncontro = new MyJPanel();
		rigaIncontro.setBackground(MyJPanel.uninaLightColor);
		rigaIncontro.setLayout(new BoxLayout(rigaIncontro, BoxLayout.X_AXIS));
		rigaIncontro.setPreferredSize(new Dimension(1225, 100));
		rigaIncontro.setMaximumSize(new Dimension(1225, 100));
		
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
			
			numeroIncontri--;
		});
		
		lblRimuoviIncontro.setOnMouseEnteredAction(() -> {});
		lblRimuoviIncontro.setOnMouseExitedAction(() -> {});
		
		rigaIncontro.add(lblRimuoviIncontro);
		
		rigaIncontro.add(Box.createHorizontalGlue());
		rigaIncontro.add(new MyJLabel("Incontriamoci a ", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(Box.createHorizontalGlue());
		rigaIncontro.add(this.creaCBSediUniversita());
		rigaIncontro.add(Box.createHorizontalGlue());
		rigaIncontro.add(new MyJLabel(" dalle ", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(Box.createHorizontalGlue());
		rigaIncontro.add(this.creaCBOraIncontro());
		rigaIncontro.add(new MyJLabel(":", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(this.creaCBMinutoIncontro());
		rigaIncontro.add(Box.createHorizontalGlue());
		rigaIncontro.add(new MyJLabel(" alle ", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(Box.createHorizontalGlue());
		rigaIncontro.add(this.creaCBOraIncontro());
		rigaIncontro.add(new MyJLabel(":", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(this.creaCBMinutoIncontro());
		rigaIncontro.add(Box.createHorizontalGlue());
		rigaIncontro.add(new MyJLabel(" di ", new Font("Ubuntu Sans", Font.PLAIN, 25)));
		rigaIncontro.add(Box.createHorizontalGlue());
		rigaIncontro.add(this.creaCBGiornoIncontro());
		rigaIncontro.add(Box.createHorizontalGlue());
		
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
					
				panelPerIncontri.add(aggiungiNuovaRigaIncontro(panelPerIncontri));
				
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
		JComboBox oraInizioIncontro = new JComboBox();
		oraInizioIncontro.setPreferredSize(new Dimension(100, 30));
		oraInizioIncontro.setMaximumSize(new Dimension(100, 30));
		oraInizioIncontro.setFont(new Font("Ubuntu Sans", Font.PLAIN, 20));
		oraInizioIncontro.setBackground(Color.white);
		
		oraInizioIncontro.addItem("09");
		oraInizioIncontro.addItem("10");
		oraInizioIncontro.addItem("11");
		oraInizioIncontro.addItem("12");
		oraInizioIncontro.addItem("13");
		oraInizioIncontro.addItem("14");
		oraInizioIncontro.addItem("15");
		oraInizioIncontro.addItem("16");
		oraInizioIncontro.addItem("17");
		oraInizioIncontro.addItem("18");
		oraInizioIncontro.addItem("19");
		oraInizioIncontro.addItem("20");
		
		return oraInizioIncontro;
	}
	
	private JComboBox creaCBMinutoIncontro() {
		JComboBox oraFineIncontro = new JComboBox();
		oraFineIncontro.setBackground(Color.white);
		oraFineIncontro.setPreferredSize(new Dimension(100, 30));
		oraFineIncontro.setMaximumSize(new Dimension(100, 30));
		oraFineIncontro.setFont(new Font("Ubuntu Sans", Font.PLAIN, 20));
		
		oraFineIncontro.addItem("00");
		oraFineIncontro.addItem("15");
		oraFineIncontro.addItem("30");
		oraFineIncontro.addItem("45");
		
		return oraFineIncontro;
	}
	
	private JComboBox creaCBGiornoIncontro() {
		JComboBox giornoIncontro = new JComboBox();
		giornoIncontro.setBackground(Color.white);
		giornoIncontro.setPreferredSize(new Dimension(100, 30));
		giornoIncontro.setMaximumSize(new Dimension(100, 30));
		giornoIncontro.setFont(new Font("Ubuntu Sans", Font.PLAIN, 20));
		
		giornoIncontro.addItem("Lunedì");
		giornoIncontro.addItem("Martedì");
		giornoIncontro.addItem("Mercoledì");
		giornoIncontro.addItem("Giovedì");
		giornoIncontro.addItem("Venerdì");

		return giornoIncontro;
	}
	
	private JComboBox creaCBSediUniversita(/*SedeUniversita sedi*/) {
		JComboBox sediUniversita = new JComboBox();
		sediUniversita.setBackground(Color.white);
		sediUniversita.setPreferredSize(new Dimension(100, 30));
		sediUniversita.setMaximumSize(new Dimension(100, 30));
		sediUniversita.setFont(new Font("Ubuntu Sans", Font.PLAIN, 20));
		
//		for(SedeUniversita sedeCorrente : sedi)
//			sediUniversita.addItem(sedeCorrente.getNome());
//		
		
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
		
		bottoneTornaIndietro = new MyJButton("Torna alla home page");
		bottoneTornaIndietro.setPreferredSize(new Dimension(300, 100));
		bottoneTornaIndietro.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		bottoneTornaIndietro.setDefaultAction(() -> {mainController.passaAHomePage(this);});
		
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
			resettaBordiTextField(this.nomeAnnuncioTextField);
			resettaBordiTextA(this.inserisciDescrizioneTextA);	
			nascondiPanelErrore(this.panelErroreNomeAnnuncio, this.panelErroreFoto, this.panelErroreDescrizione, this.panelErroreModalitaDiConsegna);
			
			if(tipoAnnuncio == "Vendita") {
				resettaBordiTextField(this.prezzoInizialeTextField);
				nascondiPanelErrore(this.panelErrorePrezzoIniziale);
			}
			else if(tipoAnnuncio == "Scambio") {
				resettaBordiTextA(this.inserisciNotaScambioTextA);
				nascondiPanelErrore(this.panelErroreNotaScambio);
			}

			checkDatiInseriti();
			
			CategoriaEnum categoriaSelezionata = CategoriaEnum.confrontaConStringa(this.categorieOggetto.getSelectedItem().toString());
			CondizioneEnum condizioneSelezionata = CondizioneEnum.confrontaConStringa(this.condizioniOggetto.getSelectedItem().toString());
			
			Oggetto oggettoDaPassare = new Oggetto(categoriaSelezionata, condizioneSelezionata, 
					this.aggiungiFoto1.getImmagineInByte(), true);
			oggettoDaPassare.aggiungiImmagine(1, this.aggiungiFoto2.getImmagineInByte());
			oggettoDaPassare.aggiungiImmagine(2, this.aggiungiFoto3.getImmagineInByte());

			Annuncio annuncioDaPassare;
			if(tipoAnnuncio == "Vendita") {	
				double prezzoIniziale = Double.valueOf(this.prezzoInizialeTextField.getText());
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
			
			mainController.onPubblicaAnnuncioButtonClicked(annuncioDaPassare);
		}
		catch(NomeAnnuncioException exc1) {
			this.settaLabelETextFieldDiErrore(lblErroreNomeAnnuncio, exc1.getMessage(), this.nomeAnnuncioTextField);
			SwingUtilities.invokeLater(() -> {
				this.nomeAnnuncioTextField.requestFocusInWindow();
				this.panelCentrale.scrollRectToVisible(nomeAnnuncioTextField.getBounds());				
			});
			panelErroreNomeAnnuncio.setVisible(true);
			exc1.printStackTrace();
		}
		catch(FotoException exc2) {
			SwingUtilities.invokeLater(() -> {
				this.nomeAnnuncioTextField.requestFocusInWindow();
				this.panelCentrale.scrollRectToVisible(nomeAnnuncioTextField.getBounds());				
			});
			lblErroreFoto.setText(exc2.getMessage());
			panelErroreFoto.setVisible(true);
			exc2.printStackTrace();

		}
		catch(DescrizioneException exc3) {
			this.settaLabelETextAreaDiErrore(lblErroreDescrizione, exc3.getMessage(), this.inserisciDescrizioneTextA);
			SwingUtilities.invokeLater(() -> {
				this.panelInserimentoDati.requestFocusInWindow();
				this.panelCentrale.scrollRectToVisible(inserisciDescrizioneTextA.getBounds());				
			});
			panelErroreDescrizione.setVisible(true);
			exc3.printStackTrace();

		}
		catch(NotaScambioException exc4) {
			this.settaLabelETextAreaDiErrore(lblErroreNotaScambio, exc4.getMessage(), this.inserisciNotaScambioTextA);
			SwingUtilities.invokeLater(() -> {
				this.panelInserimentoDati.requestFocusInWindow();
				this.panelCentrale.scrollRectToVisible(inserisciNotaScambioTextA.getBounds());				
			});	
			panelErroreNotaScambio.setVisible(true);
			exc4.printStackTrace();

		}
		catch(ModalitaDiConsegnaException exc5) {
			SwingUtilities.invokeLater(() -> {
				this.panelInserimentoDati.requestFocusInWindow();
				this.panelCentrale.scrollRectToVisible(inserisciNotaScambioTextA.getBounds());				
			});	
			lblErroreModalitaConsegna.setText(exc5.getMessage());
			panelErroreModalitaDiConsegna.setVisible(true);
			exc5.printStackTrace();

		}
		catch(SQLException exc6) {
			exc6.printStackTrace();
		}
	}
	
	private void checkDatiInseriti() {
		checkNomeAnnuncio();
		checkFoto();
		checkDescrizioneAnnuncio();
		
		if(tipoAnnuncio == "Scambio")
			checkNotaScambio();
		
		checkModalitaConsegna();
	}
	
	private void checkNomeAnnuncio() throws NomeAnnuncioException{
		if(this.nomeAnnuncioTextField.getText().isEmpty() || 
		   this.nomeAnnuncioTextField.getText().equals("Dai un nome al tuo annuncio!"))
		{
			throw new NomeAnnuncioException("Inserisci un nome per il tuo annuncio.");
		}
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
	
	private void checkModalitaConsegna() throws ModalitaDiConsegnaException{
		if(!this.spedizioneCheckBox.isSelected() && !this.ritiroInPostaCheckBox.isSelected() && !this.incontroCheckBox.isSelected())
			throw new ModalitaDiConsegnaException("Seleziona almeno una modalità di consegna.");
	}
	
	private void checkFoto() throws FotoException{
		if(!foto1Caricata && !foto2Caricata && !foto3Caricata)
			throw new FotoException("Devi caricare almeno una foto.");
	}
}