package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;

public class FramePubblicaAnnuncio extends MyJFrame {

	private static final long serialVersionUID = 1L;
	private Controller mainController;
	
	private MyJPanel contentPane = new MyJPanel();
	private MyJPanel panelAggiungiFoto = new MyJPanel();
	
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
		panelInserimentoDati.setPreferredSize(new Dimension(1225, 4000));
		panelInserimentoDati.setMaximumSize(new Dimension(1225, 4000));

		JTextArea inserisciDescrizioneTextA = new JTextArea();
		inserisciDescrizioneTextA.setLineWrap(true);
		inserisciDescrizioneTextA.setWrapStyleWord(true);
		inserisciDescrizioneTextA.setPreferredSize(new Dimension(800, 400));
		inserisciDescrizioneTextA.setMaximumSize(new Dimension(800, 400));
		inserisciDescrizioneTextA.setFont(inserisciDescrizioneTextA.getFont().deriveFont(25f));
		
		panelInserimentoDati.add(this.creaPanelGenerico("Descrivi il tuo articolo!", inserisciDescrizioneTextA.getPreferredSize(), inserisciDescrizioneTextA, "Errore 1"));
		
		return panelInserimentoDati;
	}
	
	private MyJPanel creaPanelGenerico(String stringaPerLabel, Dimension dimensionePrefissata, JComponent componentToAdd, String stringaPerLabelDiErrore) {
		MyJPanel panelGenerico = new MyJPanel();
		panelGenerico.setLayout(new BorderLayout());
//		panelGenerico.setAlignmentX(CENTER_ALIGNMENT);
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
		lblDiErrore.setText(stringaPerLabelDiErrore);
		lblDiErrore.setAlignmentX(LEFT_ALIGNMENT);
		
		componentToAdd.setAlignmentX(LEFT_ALIGNMENT);
			
		MyJPanel panelSuperiore = new MyJPanel(MyJPanel.uninaColorClicked);
		panelSuperiore.setLayout(new BoxLayout(panelSuperiore, BoxLayout.X_AXIS));
		panelSuperiore.add(lblGenerico);
		panelSuperiore.setAlignmentX(LEFT_ALIGNMENT);
		
		MyJPanel panelCentrale = new MyJPanel();
		panelCentrale.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelCentrale.setBackground(MyJPanel.uninaLightColor);
		panelCentrale.add(componentToAdd);
		
		MyJPanel panelInferiore = new MyJPanel(new Color(220, 220, 220));
		panelInferiore.add(lblDiErrore);
		
		panelGenerico.add(panelSuperiore, BorderLayout.NORTH);
		panelGenerico.add(panelCentrale, BorderLayout.CENTER);
		panelGenerico.add(panelInferiore, BorderLayout.SOUTH);
		
		return panelGenerico;
	}
}