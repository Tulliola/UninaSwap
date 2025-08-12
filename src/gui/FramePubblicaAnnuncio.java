package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;

public class FramePubblicaAnnuncio extends MyJFrame {

	private static final long serialVersionUID = 1L;
	private Controller mainController;
	
	private MyJPanel contentPane = new MyJPanel();
	private MyJPanel panelAggiungiFoto = new MyJPanel();
	private MyJPanel panelDettagliIncontri;
	
	private JComboBox sediUniversita = new JComboBox();
	
	private int numeroIncontri = 1;
	
	public FramePubblicaAnnuncio(Controller controller) {
		mainController = controller;
		
		this.settaContentPane();
	}

	private void settaContentPane() {
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setLocationRelativeTo(null);
		this.setTitle("Pubblica il tuo annuncio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane.setLayout(new BorderLayout());
		contentPane.add(new MyJPanel(MyJPanel.uninaLightColor, new Dimension(100, contentPane.getHeight())), BorderLayout.WEST);
		contentPane.add(this.creaPanelCentrale(), BorderLayout.CENTER);
		contentPane.add(new MyJPanel(MyJPanel.uninaLightColor, new Dimension(100, contentPane.getHeight())), BorderLayout.EAST);

		this.setContentPane(contentPane);
	}
	
	private JScrollPane creaPanelCentrale() {
		MyJPanel panelCentrale = new MyJPanel();
		panelCentrale.setLayout(new BoxLayout(panelCentrale, BoxLayout.Y_AXIS));
		panelCentrale.setBackground(Color.white);
		panelCentrale.setPreferredSize(new Dimension(375, 4000));
		panelCentrale.setAlignmentX(CENTER_ALIGNMENT);
		
		this.creaPanelAggiungiFoto();
		
		MyJTextField nomeAnnuncioTextField = new MyJTextField("Dai un nome al tuo annuncio!");
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
		nomeAnnuncioTextField.setBackground(Color.LIGHT_GRAY);
		nomeAnnuncioTextField.setMaximumSize(new Dimension(1225, 40));
		nomeAnnuncioTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		panelCentrale.add(Box.createRigidArea(new Dimension(0, 20)));
		panelCentrale.add(nomeAnnuncioTextField);
		panelCentrale.add(Box.createRigidArea(new Dimension(0, 20)));
		panelCentrale.add(panelAggiungiFoto);
		panelCentrale.add(Box.createRigidArea(new Dimension(0, 20)));
		panelCentrale.add(creaPanelInserimentoDati());
		panelCentrale.add(Box.createRigidArea(new Dimension(0, 20)));
		
		
		JScrollPane scrollPane = new JScrollPane(panelCentrale);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);

		return scrollPane;
	}
	
	private MyJPanel creaPanelAggiungiFoto() {
		panelAggiungiFoto = new MyJPanel();
		panelAggiungiFoto.setLayout(new FlowLayout());
		panelAggiungiFoto.setPreferredSize(new Dimension(1225, 500));
		panelAggiungiFoto.setMaximumSize(new Dimension(1225, 500));
		
		MyJPanel panelFoto1 = new MyJPanel(Color.red, new Dimension(375, 500));
		panelFoto1.setMaximumSize(new Dimension(375, 500));
		
		MyJPanel panelFoto2 = new MyJPanel(Color.green, new Dimension(375, 500));
		panelFoto2.setMaximumSize(new Dimension(375, 500));
		
		MyJPanel panelFoto3 = new MyJPanel(Color.blue, new Dimension(375, 500));
		panelFoto3.setMaximumSize(new Dimension(375, 500));
		
		panelAggiungiFoto.add(panelFoto1);
		panelAggiungiFoto.add(panelFoto2);
		panelAggiungiFoto.add(panelFoto3);
		
		return panelAggiungiFoto;
	}
	
	private MyJPanel creaPanelInserimentoDati() {
		MyJPanel panelInserimentoDati = new MyJPanel();
		panelInserimentoDati.setBackground(Color.white);
		panelInserimentoDati.setLayout(new BoxLayout(panelInserimentoDati, BoxLayout.Y_AXIS));
		panelInserimentoDati.setPreferredSize(new Dimension(1225, 4000));
		panelInserimentoDati.setMaximumSize(new Dimension(1225, 4000));

		JTextArea inserisciDescrizioneTextA = new JTextArea();
		this.settaTextArea(inserisciDescrizioneTextA);
		String stringaDiDefaultPerDescrizione = "Dai una descrizione accurata dell'oggetto che stai vendendo. L'acquirente deve avere ben chiaro cosa starà acquistando!";
		
		JTextArea inserisciNotaScambioTextA = new JTextArea();
		this.settaTextArea(inserisciNotaScambioTextA);
		String stringaDiDefaultPerNotaScambio = "Sii preciso in quello che richiedi!";
		
		MyJTextField prezzoInizialeTextField = new MyJTextField();
		prezzoInizialeTextField.setPreferredSize(new Dimension(300, 30));
		prezzoInizialeTextField.setMaximumSize(new Dimension(300, 30));
		
		panelInserimentoDati.add(this.creaPanelTextArea("Descrivi il tuo articolo!", inserisciDescrizioneTextA.getPreferredSize(), inserisciDescrizioneTextA, stringaDiDefaultPerDescrizione));
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 50)));
		panelInserimentoDati.add(this.creaPanelCategoria());
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 50)));
		panelInserimentoDati.add(this.creaPanelCondizioni());
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 50)));
		panelInserimentoDati.add(this.creaPanelTextArea("Indica gli oggetti con cui scambieresti il tuo articolo!", inserisciNotaScambioTextA.getPreferredSize(), 
				inserisciNotaScambioTextA, stringaDiDefaultPerNotaScambio));
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 50)));
		panelInserimentoDati.add(this.creaPanelPrezzoIniziale("Indica un prezzo iniziale per il tuo articolo in vendita!", prezzoInizialeTextField));
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 50)));
		panelInserimentoDati.add(this.creaPanelDataScadenza());
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 50)));
		panelInserimentoDati.add(this.creaPanelModalitaConsegna());
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 50)));
		panelInserimentoDati.add(this.creaPanelDettagliIncontri());
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 50)));
		panelInserimentoDati.add(this.creaPanelBottoni());
		
		return panelInserimentoDati;
	}
	
	private MyJPanel creaPanelTextArea(String stringaPerLabel, Dimension dimensionePrefissata, JTextArea componentToAdd, String stringaDiDefaultPerTextA) {
		MyJPanel panelGenerico = new MyJPanel();
		panelGenerico.setLayout(new BorderLayout());
		panelGenerico.setPreferredSize(new Dimension(1225, 400));
		panelGenerico.setMaximumSize(new Dimension(1225, 400));
		
		MyJLabel lblGenerico = new MyJLabel(stringaPerLabel);
		lblGenerico.setFont(new Font("Ubuntu Sans", Font.BOLD, 30));
		lblGenerico.setForeground(Color.white);
		lblGenerico.setAlignmentX(LEFT_ALIGNMENT);
		lblGenerico.setBorder(new EmptyBorder(0, 10, 0, 0));
		
		MyJLabel lblDiErrore = new MyJLabel(true);
		lblDiErrore.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		lblDiErrore.setVisible(true);
		lblDiErrore.setBorder(new EmptyBorder(0, 10, 0, 0));
		lblDiErrore.setText("Prova");
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
		
		MyJPanel panelInferiore = new MyJPanel(new Color(220, 220, 220));
		panelInferiore.setLayout(new BoxLayout(panelInferiore, BoxLayout.X_AXIS));
		panelInferiore.add(lblDiErrore);
		panelInferiore.setAlignmentX(LEFT_ALIGNMENT);
		
		panelGenerico.add(panelSuperiore, BorderLayout.NORTH);
		panelGenerico.add(panelCentrale, BorderLayout.CENTER);
		panelGenerico.add(panelInferiore, BorderLayout.SOUTH);
		
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
		JComboBox categorieOggetto = this.creaCBCategorieOggetto();
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
		JComboBox condizioniOggetto = this.creaCBCondizioniOggetto();
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
		panelPerTextField.add(textFieldIn);
		
		panelCentrale.add(panelPerTextField);
		
		MyJPanel panelInferiore = new MyJPanel(new Color(220, 220, 220));
		panelInferiore.setLayout(new BoxLayout(panelInferiore, BoxLayout.X_AXIS));
		panelInferiore.setAlignmentX(LEFT_ALIGNMENT);
		MyJLabel lblDiErrore = new MyJLabel(true);
		lblDiErrore.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		lblDiErrore.setVisible(true);
		lblDiErrore.setBorder(new EmptyBorder(0, 10, 0, 0));
		lblDiErrore.setText("Errore 3");
		lblDiErrore.setAlignmentX(LEFT_ALIGNMENT);
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
	
		JCheckBox spedizioneCheckBox = new JCheckBox("Spedizione");
		this.settaCheckBox(spedizioneCheckBox);
		
		JCheckBox ritiroInPostaCheckBox = new JCheckBox("Ritiro in posta");
		this.settaCheckBox(ritiroInPostaCheckBox);
		
		JCheckBox incontroCheckBox = new JCheckBox("Incontro");
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
		
		MyJPanel panelInferiore = new MyJPanel(new Color(220, 220, 220));
		panelInferiore.setLayout(new BoxLayout(panelInferiore, BoxLayout.X_AXIS));
		panelInferiore.setAlignmentX(LEFT_ALIGNMENT);
		MyJLabel lblDiErrore = new MyJLabel(true);
		lblDiErrore.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		lblDiErrore.setVisible(true);
		lblDiErrore.setBorder(new EmptyBorder(0, 10, 0, 0));
		lblDiErrore.setText("Errore 3");
		lblDiErrore.setAlignmentX(LEFT_ALIGNMENT);
		panelInferiore.add(lblDiErrore);
			
		panelModalitaConsegna.add(panelSuperiore, BorderLayout.NORTH);
		panelModalitaConsegna.add(panelCentrale, BorderLayout.CENTER);
		panelModalitaConsegna.add(panelInferiore, BorderLayout.SOUTH);
		
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
		
		MyJButton bottoneTornaIndietro = new MyJButton("Torna alla home page");
		bottoneTornaIndietro.setPreferredSize(new Dimension(300, 100));
		bottoneTornaIndietro.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		bottoneTornaIndietro.setDefaultAction(() -> {mainController.passaAHomePage(this);});
		
		MyJButton bottonePubblicaAnnuncio = new MyJButton("Pubblica annuncio");
		bottonePubblicaAnnuncio.setPreferredSize(new Dimension(300, 100));
		bottonePubblicaAnnuncio.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		bottonePubblicaAnnuncio.setDefaultAction(() -> {});

		panelBottoni.add(bottoneTornaIndietro);
		panelBottoni.add(bottonePubblicaAnnuncio);
		
		return panelBottoni;
	}
}