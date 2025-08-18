package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import utilities.MyJButton;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class FrameCambiaImmagine extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private MyJPanel panelContenenteFoto;
	private Controller mainController;

	private byte[] immagineSelezionata;
	
	public FrameCambiaImmagine(Controller controller, byte[][] immaginiDiSistema) {
		mainController = controller;
		
		this.setSize(new Dimension(450, (immaginiDiSistema.length/2) * 250));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Cambia la tua immagine di profilo!");
		
		settaContentPane(immaginiDiSistema);
		
	}
	
	private void settaContentPane(byte[][] immaginiDiSistema) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		panelContenenteFoto = new MyJPanel();
		panelContenenteFoto.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()-100));
		
		for(int i = 0; i < immaginiDiSistema.length; i++)
			aggiungiImmagineProfilo(immaginiDiSistema[i]);
		
		MyJPanel panelBottone = new MyJPanel();
		MyJButton bottoneTornaIndietro = new MyJButton("Torna indietro");
		bottoneTornaIndietro.setFocusable(false);
		bottoneTornaIndietro.setDefaultAction(() -> {mainController.tornaAFrameProfiloUtente();});
		bottoneTornaIndietro.setUpAction(() -> {});
		bottoneTornaIndietro.setDownAction(() -> {});
		panelBottone.add(bottoneTornaIndietro);
		
		contentPane.add(panelContenenteFoto, BorderLayout.NORTH);
		contentPane.add(panelBottone, BorderLayout.SOUTH);
		
		setContentPane(contentPane);

	}

	private void aggiungiImmagineProfilo(byte[] immagineProfilo) {
		MyJLabel lblBioPic = new MyJLabel();
		lblBioPic.aggiungiImmagineScalata(immagineProfilo, 200, 200, true);
		lblBioPic.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		lblBioPic.setAlignmentX(CENTER_ALIGNMENT);
		lblBioPic.setAlignmentY(CENTER_ALIGNMENT);
		
		lblBioPic.rendiLabelInteragibile();
		
		lblBioPic.setOnMouseEnteredAction(() -> {});
		lblBioPic.setOnMouseExitedAction(() -> {});
		lblBioPic.setOnMouseClickedAction(() -> {
			mainController.passaADialogConfermaCambioImmagine(immagineProfilo);
		});

		panelContenenteFoto.add(lblBioPic);
	}
}
