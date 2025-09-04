package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.Oggetto;
import eccezioni.DescrizioneException;
import eccezioni.FotoException;
import eccezioni.OggettoException;
import utilities.CategoriaEnum;
import utilities.CondizioneEnum;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;

public class FrameCaricaOggettoScambio extends MyJFrame {

	private static final long serialVersionUID = 1L;
	private Controller mainController;
	
	private MyJPanel contentPane = new MyJPanel();
	private MyJPanel panelNomeOggetto;
	private MyJPanel panelAggiungiFoto;
	private MyJPanel panelCentrale;
	private MyJPanel panelInserimentoDati;
	private MyJPanel panelBottoni;
	
	//Panels di errore
	private MyJPanel panelErroreNomeOggetto = new MyJPanel();
	private MyJPanel panelErroreDescrizione = new MyJPanel();
	private MyJPanel panelErroreFoto = new MyJPanel();
	
	private MyJButton bottoneTornaIndietro;
	private MyJButton bottoneCaricaOModificaOggetto;
	private MyJButton bottoneEliminaOggetto;
	
	private JComboBox categorieOggettoComboBox;
	private JComboBox condizioniOggettoComboBox;

	//TextFields
	private MyJTextField nomeOggettoTextField;
	
	//TextAreas
	private JTextArea inserisciDescrizioneTextA;
	
	//Labels di errore
	private MyJLabel lblErroreNomeOggetto;
	private MyJLabel lblErroreDescrizione;
	private MyJLabel lblErroreFoto;
	
	//Flag per immagini
	private AtomicBoolean foto1Caricata = new AtomicBoolean(false);
	private AtomicBoolean foto2Caricata = new AtomicBoolean(false);
	private AtomicBoolean foto3Caricata = new AtomicBoolean(false);
	
	//Labels per le immagini
	private MyJLabel lblAggiungiFoto1;
	private MyJLabel lblAggiungiFoto2;
	private MyJLabel lblAggiungiFoto3;
		
	//Variabili per gestire la funzionalita di caricamento oggetti
	private int indiceNellArrayDeiFrame;
	private boolean isOggettoCaricato = false;
	
	public FrameCaricaOggettoScambio(Controller controller, int indiceNellArrayDeiFrame, Oggetto oggettoCaricato) {
		mainController = controller;
		this.indiceNellArrayDeiFrame = indiceNellArrayDeiFrame;
		
		if(oggettoCaricato != null)
			isOggettoCaricato = true;
		
		this.settaContentPane(oggettoCaricato);
	}

	private void settaContentPane(Oggetto oggettoCaricato) {
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setLocationRelativeTo(null);
		this.setTitle("Carica ora il tuo oggetto!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane.setLayout(new BorderLayout());
		
		MyJPanel scrittaUninaSwap = new MyJPanel(MyJPanel.uninaColorClicked, new Dimension(Integer.MAX_VALUE, 50));
		scrittaUninaSwap.add(new MyJLabel("UninaSwap", new Font("Ubuntu Sans", Font.BOLD, 25), Color.white));
		
		contentPane.add(scrittaUninaSwap, BorderLayout.NORTH);
		contentPane.add(new MyJPanel(MyJPanel.uninaLightColor, new Dimension(100, contentPane.getHeight())), BorderLayout.WEST);
		contentPane.add(this.creaPanelCentrale(oggettoCaricato), BorderLayout.CENTER);
		contentPane.add(new MyJPanel(MyJPanel.uninaLightColor, new Dimension(100, contentPane.getHeight())), BorderLayout.EAST);
		contentPane.add(new MyJPanel(MyJPanel.uninaColorClicked, new Dimension(Integer.MAX_VALUE, 25)), BorderLayout.SOUTH);

		this.setContentPane(contentPane);
	}
	
	private JScrollPane creaPanelCentrale(Oggetto oggettoCaricato) {
		panelCentrale = new MyJPanel();
		panelCentrale.setLayout(new BoxLayout(panelCentrale, BoxLayout.Y_AXIS));
		panelCentrale.setBackground(Color.white);
		panelCentrale.setPreferredSize(new Dimension(1225, 2000));
		panelCentrale.setMaximumSize(new Dimension(1225, 2000));
		panelCentrale.setAlignmentX(CENTER_ALIGNMENT);
		
		this.creaPanelAggiungiFoto(oggettoCaricato);
		this.settaPanelNomeOggetto();
		
		panelCentrale.add(Box.createRigidArea(new Dimension(0, 50)));
		panelCentrale.add(panelNomeOggetto);
		panelCentrale.add(Box.createRigidArea(new Dimension(0, 20)));
		panelCentrale.add(creaPanelInserimentoDati(oggettoCaricato));
		panelCentrale.add(Box.createRigidArea(new Dimension(0, 20)));
		
		
		JScrollPane scrollPane = new JScrollPane(panelCentrale);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);

		return scrollPane;
	}
	
	private void settaPanelNomeOggetto() {		
		panelNomeOggetto = new MyJPanel();
		panelNomeOggetto.setPreferredSize(new Dimension(1225, 115));
		panelNomeOggetto.setMaximumSize(new Dimension(1225, 115));
		panelNomeOggetto.setLayout(new BorderLayout());
		panelNomeOggetto.setBackground(Color.white);
		
		nomeOggettoTextField = new MyJTextField("Nome");
		nomeOggettoTextField.setBorder(new EmptyBorder(0, 10, 0, 0));
		nomeOggettoTextField.setPreferredSize(new Dimension(1225, 30));
		nomeOggettoTextField.setMaximumSize(new Dimension(1225, 30));
		nomeOggettoTextField.rendiTextFieldFocusable();
		
		if(isOggettoCaricato)
			nomeOggettoTextField.setText("Oggetto " + (indiceNellArrayDeiFrame+1) + " caricato");
		
		nomeOggettoTextField.setFocusGainedAction(() -> {
			if(nomeOggettoTextField.getText().equals("Nome"))
				nomeOggettoTextField.setText("");
		});
		
		nomeOggettoTextField.setFocusLostAction(() -> {
			if(nomeOggettoTextField.getText().isEmpty())
				nomeOggettoTextField.setText("Nome");
		});
		
		nomeOggettoTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(nomeOggettoTextField.getText().length() >= 100)
					e.consume();
			}
		});
		
		lblErroreNomeOggetto = new MyJLabel(true);
		lblErroreNomeOggetto.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		lblErroreNomeOggetto.setVisible(true);
		lblErroreNomeOggetto.setBorder(new EmptyBorder(0, 10, 0, 0));
		lblErroreNomeOggetto.setAlignmentX(LEFT_ALIGNMENT);
		panelErroreNomeOggetto.add(lblErroreNomeOggetto);
		
		MyJPanel panelSuperiore = new MyJPanel(MyJPanel.uninaColorClicked);
		panelSuperiore.setLayout(new BoxLayout(panelSuperiore, BoxLayout.X_AXIS));
		panelSuperiore.setAlignmentX(LEFT_ALIGNMENT);
		MyJLabel lblNomeOggetto = new MyJLabel("Che oggetto stai caricando?");
		lblNomeOggetto.setFont(new Font("Ubuntu Sans", Font.BOLD, 30));
		lblNomeOggetto.setForeground(Color.white);
		lblNomeOggetto.setAlignmentX(LEFT_ALIGNMENT);
		lblNomeOggetto.setBorder(new EmptyBorder(0, 10, 0, 0));
		panelSuperiore.add(lblNomeOggetto);
		
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
		this.nomeOggettoTextField.setAlignmentX(LEFT_ALIGNMENT);
		this.nomeOggettoTextField.setFont(new Font("Ubuntu Sans", Font.PLAIN, 25));
		
		panelCentrale.add(panelPerTextField);
		
		panelPerTextField.add(nomeOggettoTextField);
		
		panelErroreNomeOggetto.setBackground(Color.white);
		panelErroreNomeOggetto.setLayout(new BoxLayout(panelErroreNomeOggetto, BoxLayout.X_AXIS));
		panelErroreNomeOggetto.setAlignmentX(LEFT_ALIGNMENT);
		panelErroreNomeOggetto.setVisible(false);
		panelErroreNomeOggetto.add(lblErroreNomeOggetto);
		
		panelNomeOggetto.add(panelSuperiore, BorderLayout.NORTH);
		panelNomeOggetto.add(panelCentrale, BorderLayout.CENTER);
		panelNomeOggetto.add(panelErroreNomeOggetto, BorderLayout.SOUTH);
		
		this.panelCentrale.add(panelNomeOggetto);
		
	}

	private MyJPanel creaPanelAggiungiFoto(Oggetto oggettoCaricato) {
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
		this.settaPanelFoto(panelFoto1, lblAggiungiFoto1, foto1Caricata, oggettoCaricato, 0, () -> {foto1Caricata.set(lblAggiungiFoto1.aggiungiImmagineDaFileSystem());});
		
		MyJPanel panelFoto2 = new MyJPanel();
		lblAggiungiFoto2 = new MyJLabel();
		this.settaPanelFoto(panelFoto2, lblAggiungiFoto2, foto2Caricata, oggettoCaricato, 1, () -> {foto2Caricata.set(lblAggiungiFoto2.aggiungiImmagineDaFileSystem());});
		
		MyJPanel panelFoto3 = new MyJPanel();
		lblAggiungiFoto3 = new MyJLabel();
		this.settaPanelFoto(panelFoto3, lblAggiungiFoto3, foto3Caricata, oggettoCaricato, 2, () -> {foto3Caricata.set(lblAggiungiFoto3.aggiungiImmagineDaFileSystem());});
		
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
	
	private MyJPanel creaPanelInserimentoDati(Oggetto oggettoOfferto) {
		panelInserimentoDati = new MyJPanel();
		panelInserimentoDati.setBackground(Color.white);
		panelInserimentoDati.setLayout(new BoxLayout(panelInserimentoDati, BoxLayout.Y_AXIS));
		panelInserimentoDati.setPreferredSize(new Dimension(1225, 2000));
		panelInserimentoDati.setMaximumSize(new Dimension(1225, 2000));

		inserisciDescrizioneTextA = new JTextArea();
		this.settaTextArea(inserisciDescrizioneTextA);
		String stringaDiDefaultPerDescrizione = "Dai una descrizione accurata dell'oggetto che stai proponendo!";
		lblErroreDescrizione = new MyJLabel(true);
		
		
		panelInserimentoDati.add(Box.createVerticalGlue());
		panelInserimentoDati.add(this.creaPanelAggiungiFoto(oggettoOfferto));
		panelInserimentoDati.add(Box.createVerticalGlue());
		if(oggettoOfferto == null)
			panelInserimentoDati.add(this.creaPanelTextArea("Descrivi il tuo articolo!", stringaDiDefaultPerDescrizione, inserisciDescrizioneTextA,
				lblErroreDescrizione, panelErroreDescrizione));
		else
			panelInserimentoDati.add(this.creaPanelTextArea("Descrivi il tuo articolo!", oggettoOfferto.getDescrizione(), inserisciDescrizioneTextA, 
					lblErroreDescrizione, panelErroreDescrizione));
			
		panelInserimentoDati.add(Box.createVerticalGlue());
		panelInserimentoDati.add(this.creaPanelCategoria(oggettoOfferto));
		panelInserimentoDati.add(Box.createVerticalGlue());
		panelInserimentoDati.add(this.creaPanelCondizioni(oggettoOfferto));
		panelInserimentoDati.add(Box.createVerticalGlue());
		panelInserimentoDati.add(this.creaPanelBottoni());
		panelInserimentoDati.add(Box.createVerticalGlue());

		
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
		
		panelCentrale.add(componentToAdd);
		
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
	
	private MyJPanel creaPanelCategoria(Oggetto oggettoCaricato) {
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
		categorieOggettoComboBox = this.creaCBCategorieOggetto(oggettoCaricato);
		categorieOggettoComboBox.setAlignmentX(LEFT_ALIGNMENT);
		panelPerComboBox.add(categorieOggettoComboBox);
		
		panelCentrale.add(panelPerComboBox, BorderLayout.CENTER);
			
		panelCategoria.add(panelSuperiore, BorderLayout.NORTH);
		panelCategoria.add(panelCentrale, BorderLayout.CENTER);
		
		return panelCategoria;
	}
	
	private MyJPanel creaPanelCondizioni(Oggetto oggettoCaricato) {
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
		condizioniOggettoComboBox = this.creaCBCondizioniOggetto(oggettoCaricato);
		condizioniOggettoComboBox.setAlignmentX(LEFT_ALIGNMENT);
		panelPerComboBox.add(condizioniOggettoComboBox);
		
		panelCentrale.add(panelPerComboBox, BorderLayout.CENTER);
			
		panelCondizioni.add(panelSuperiore, BorderLayout.NORTH);
		panelCondizioni.add(panelCentrale, BorderLayout.CENTER);
		
		return panelCondizioni;
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
			}
		});
	}
		
	private JComboBox creaCBCategorieOggetto(Oggetto oggettoCaricato) {
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
		
		ComboBoxModel<String> model = categorieOggetto.getModel();
		if(isOggettoCaricato) {
			for (int i = 0; i < model.getSize(); i++) {
			    if(model.getElementAt(i).equals(oggettoCaricato.getCategoria())) {
			    	categorieOggetto.setSelectedIndex(i);
			    	break;
			    }
			}
		}
		else
			categorieOggetto.setSelectedIndex(0);
		
		return categorieOggetto;
	}
	

	private JComboBox creaCBCondizioniOggetto(Oggetto oggettoCaricato) {
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
		
		ComboBoxModel<String> model = condizioniOggetto.getModel();
		if(isOggettoCaricato) {
			for (int i = 0; i < model.getSize(); i++) {
			    if(model.getElementAt(i).equals(oggettoCaricato.getCondizioni())) {
			    	condizioniOggetto.setSelectedIndex(i);
			    	break;
			    }
			}
		}
		else
			condizioniOggetto.setSelectedIndex(0);
		return condizioniOggetto;
	}
	
	private MyJPanel creaPanelBottoni() {
		panelBottoni = new MyJPanel();
		panelBottoni.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelBottoni.setBackground(Color.white);
		panelBottoni.setPreferredSize(new Dimension(1225, 100));
		panelBottoni.setMaximumSize(new Dimension(1225, 100));
		
		bottoneCaricaOModificaOggetto = new MyJButton();
		bottoneCaricaOModificaOggetto.setPreferredSize(new Dimension(300, 100));
		bottoneCaricaOModificaOggetto.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		bottoneCaricaOModificaOggetto.setDefaultAction(() -> {this.clickBottoneCaricaOModificaOggetto();});

		bottoneTornaIndietro = new MyJButton("Torna all'offerta");
		bottoneTornaIndietro.setPreferredSize(new Dimension(300, 100));
		bottoneTornaIndietro.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		bottoneTornaIndietro.setDefaultAction(() -> {mainController.tornaADialogOffertaScambio(this);});

		bottoneCaricaOModificaOggetto.setText("Carica oggetto");
		
		if(this.isOggettoCaricato) {
			bottoneEliminaOggetto = new MyJButton("Elimina oggetto");
			bottoneEliminaOggetto.setPreferredSize(new Dimension(300, 100));
			bottoneEliminaOggetto.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
			bottoneEliminaOggetto.setDefaultAction(() -> {
				mainController.tornaADialogOffertaScambioEliminandoFrame(this.indiceNellArrayDeiFrame);
			});
			
			bottoneCaricaOModificaOggetto.setText("Modifica oggetto");
			
			panelBottoni.add(bottoneEliminaOggetto);
		}
		else
			panelBottoni.add(bottoneTornaIndietro);
		
		panelBottoni.add(bottoneCaricaOModificaOggetto);
		
		return panelBottoni;
	}
	
	private void clickBottoneCaricaOModificaOggetto() {
		try {
			resettaBordiTextField(new EmptyBorder(0, 10, 0, 0), this.nomeOggettoTextField);
			resettaBordiTextA(new EmptyBorder(0, 10, 0, 0), this.inserisciDescrizioneTextA);	
			nascondiPanelErrore(this.panelErroreNomeOggetto, this.panelErroreFoto, this.panelErroreDescrizione);
			
			checkDatiInseriti();
			
			if(!this.isOggettoCaricato) {
				bottoneEliminaOggetto = new MyJButton("Elimina oggetto");
				bottoneEliminaOggetto.setPreferredSize(new Dimension(300, 100));
				bottoneEliminaOggetto.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
				bottoneEliminaOggetto.setDefaultAction(() -> {
					mainController.tornaADialogOffertaScambioEliminandoFrame(this.indiceNellArrayDeiFrame);
				});
				
				bottoneCaricaOModificaOggetto.setText("Modifica oggetto");
				
				panelBottoni.remove(bottoneTornaIndietro);
				panelBottoni.add(bottoneEliminaOggetto, 0);
			}
			
			isOggettoCaricato = true;
			
			mainController.onCaricaOModificaOggettoButtonClicked(this.indiceNellArrayDeiFrame, this.nomeOggettoTextField.getText());
		}
		catch(OggettoException exc1) {
			this.settaLabelETextFieldDiErrore(lblErroreNomeOggetto, exc1.getMessage(), this.nomeOggettoTextField);
			SwingUtilities.invokeLater(() -> {
				this.nomeOggettoTextField.requestFocusInWindow();
				this.panelCentrale.scrollRectToVisible(nomeOggettoTextField.getBounds());				
			});
			panelErroreNomeOggetto.setVisible(true);
		}
		catch(FotoException exc2) {
			SwingUtilities.invokeLater(() -> {
				this.nomeOggettoTextField.requestFocusInWindow();
				this.panelCentrale.scrollRectToVisible(nomeOggettoTextField.getBounds());				
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
		
	}
	
	private void checkDatiInseriti() {
		checkNomeOggetto();
		checkFoto();
		checkDescrizioneOggetto();
		
	}
	
	private void checkNomeOggetto() throws OggettoException{
		if(this.nomeOggettoTextField.getText().isEmpty() || 
		   this.nomeOggettoTextField.getText().equals("Nome"))
		{
			throw new OggettoException("Inserisci un nome per il tuo oggetto.");
		}
	}
	
	private void checkFoto() throws FotoException{
		if(!foto1Caricata.get() && !foto2Caricata.get() && !foto3Caricata.get())
			throw new FotoException("Devi caricare almeno una foto.");
		
	}

	private void checkDescrizioneOggetto() throws DescrizioneException{
		if(this.inserisciDescrizioneTextA.getText().isEmpty() ||
		   this.inserisciDescrizioneTextA.getText().equals("Dai una descrizione accurata dell'oggetto che stai proponendo!")) 
		{
			throw new DescrizioneException("Inserisci una descrizione per il tuo articolo.");
		}
	}

	public Oggetto passaOggettoAlController() {
		CategoriaEnum categoriaSelezionata = CategoriaEnum.confrontaConStringa(this.categorieOggettoComboBox.getSelectedItem().toString());
		CondizioneEnum condizioneSelezionata = CondizioneEnum.confrontaConStringa(this.condizioniOggettoComboBox.getSelectedItem().toString());
		
		Oggetto oggettoDaPassare;
		oggettoDaPassare = new Oggetto(categoriaSelezionata, condizioneSelezionata, 
					this.lblAggiungiFoto1.getImmagineInByte());

		oggettoDaPassare.aggiungiImmagine(1, this.lblAggiungiFoto2.getImmagineInByte());	
		oggettoDaPassare.aggiungiImmagine(2, this.lblAggiungiFoto3.getImmagineInByte());
		oggettoDaPassare.setDescrizione(this.inserisciDescrizioneTextA.getText());
		
		return oggettoDaPassare;
	}
	
	private void settaPanelFoto(MyJPanel panelContenenteFoto, MyJLabel lblAggiungiFoto, AtomicBoolean isFotoCaricata, Oggetto oggettoCaricato, int indiceDellaFoto, Runnable onMouseClickedAction) {
		panelContenenteFoto.setBackground(MyJPanel.uninaLightColor);
		panelContenenteFoto.setLayout(new BoxLayout(panelContenenteFoto, BoxLayout.Y_AXIS));
		panelContenenteFoto.setPreferredSize(new Dimension(375, 550));
		panelContenenteFoto.setMaximumSize(new Dimension(375, 550));
		
		MyJPanel panelFoto = new MyJPanel(Color.white, new Dimension(375, 500));
		panelFoto.setMaximumSize(new Dimension(375, 500));
		panelFoto.setLayout(new BoxLayout(panelFoto, BoxLayout.Y_AXIS));
		panelFoto.setAlignmentX(CENTER_ALIGNMENT);

		lblAggiungiFoto.setAlignmentX(CENTER_ALIGNMENT);
		lblAggiungiFoto.rendiLabelInteragibile();
		lblAggiungiFoto.setOnMouseClickedAction(() -> {onMouseClickedAction.run();});
		lblAggiungiFoto.setOnMouseEnteredAction(() -> {});
		lblAggiungiFoto.setOnMouseExitedAction(() -> {});
		
		if(oggettoCaricato == null || oggettoCaricato.getImmagine(indiceDellaFoto) == null)
			lblAggiungiFoto.aggiungiImmagineScalata("images/iconaAggiungiImmagine.png", 100, 100, true);
		else {
			lblAggiungiFoto.aggiungiImmagineScalata(oggettoCaricato.getImmagine(indiceDellaFoto), 375, 500, true);
			isFotoCaricata.set(true);
		}
		
		panelFoto.add(Box.createVerticalGlue());
		panelFoto.add(Box.createHorizontalGlue());
		panelFoto.add(lblAggiungiFoto);
		panelFoto.add(Box.createHorizontalGlue());
		panelFoto.add(Box.createVerticalGlue());
				
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