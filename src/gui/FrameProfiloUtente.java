package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import controller.Controller;

import dto.ProfiloUtente;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class FrameProfiloUtente extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel panelProfilo;

	private Controller mainController;
	
	public FrameProfiloUtente(Controller controller, ProfiloUtente utenteLoggato) {
		mainController = controller;
		
		this.impostaSettingsPerFrame();
		this.impostaContentPane(utenteLoggato);

	}
	
	private void impostaSettingsPerFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Il tuo profilo utente");
		this.setSize(500, 800);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
	}
	
	private void impostaContentPane(ProfiloUtente utenteLoggato) {
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.setBackground(Color.white);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		JPanel bandaLateraleSx = new JPanel();
		settaBandaLaterale(bandaLateraleSx);
		
		JPanel bandaLateraleDx = new JPanel();
		settaBandaLaterale(bandaLateraleDx);
		
		panelProfilo = new JPanel();
		panelProfilo.setLayout(new BoxLayout(panelProfilo, BoxLayout.Y_AXIS));
		panelProfilo.setBorder(new EmptyBorder(20, 0, 0, 0));
		impostaPanelProfilo(utenteLoggato);
		
		panelProfilo.setAlignmentX(CENTER_ALIGNMENT);

		contentPane.add(panelProfilo, BorderLayout.CENTER);
		contentPane.add(bandaLateraleDx, BorderLayout.EAST);
		contentPane.add(bandaLateraleSx, BorderLayout.WEST);
		
		this.setContentPane(contentPane);
	}
	
	private void settaBandaLaterale(JPanel bandaLaterale) {
		bandaLaterale.setPreferredSize(new Dimension(75, contentPane.getHeight()));
		bandaLaterale.setBackground(new Color(198, 210, 222));
	}
	
	private void impostaPanelProfilo(ProfiloUtente utenteLoggato) {
		this.aggiungiImmagineProfilo(utenteLoggato.getImmagineProfilo());
		
		panelProfilo.add(Box.createRigidArea(new Dimension(0, 10)));
		
		this.aggiungiOpzioneCambiaImmagine();
		
		panelProfilo.add(Box.createRigidArea(new Dimension(0, 20)));
		
		this.aggiungiPanelRiepilogoInformazioni(utenteLoggato);
	}
	
	private void aggiungiImmagineProfilo(byte[] immagineProfilo) {
		ImageIcon bioPic = new ImageIcon(immagineProfilo);
		Image resizedBioPic = bioPic.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon bioPicScalata = new ImageIcon(resizedBioPic);
		
		JLabel lblBioPic = new JLabel();
		lblBioPic.setIcon(bioPicScalata);
		lblBioPic.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		lblBioPic.setAlignmentX(CENTER_ALIGNMENT);
		
		panelProfilo.add(lblBioPic);
	}
	
	private void aggiungiOpzioneCambiaImmagine() {
		
		MyJLabel lblCambiaImmagine = new MyJLabel();
		lblCambiaImmagine.setText("Cambia immagine di profilo");
		lblCambiaImmagine.setFont(new Font("Ubuntu Sans", Font.PLAIN, 15));
		lblCambiaImmagine.setAlignmentX(CENTER_ALIGNMENT);
		
		lblCambiaImmagine.aggiungiEffettoCliccabilita();
		lblCambiaImmagine.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseClicked(MouseEvent me) {
				mainController.passaAFrameCambiaImmagine();
			}
		});
		
		panelProfilo.add(lblCambiaImmagine);
	}
	
	private void aggiungiPanelRiepilogoInformazioni(ProfiloUtente utenteLoggato) {
		MyJPanel panelRiepilogoInfoUtente = new MyJPanel();
		
		panelRiepilogoInfoUtente.setLayout(new BoxLayout(panelRiepilogoInfoUtente, BoxLayout.Y_AXIS));
		panelRiepilogoInfoUtente.setAlignmentX(CENTER_ALIGNMENT);
		
		ImageIcon modifyIcon = new ImageIcon("images/iconModify.png");
		Image resizedModifyIcon = modifyIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon iconaModifyScalata = new ImageIcon(resizedModifyIcon);
		
		
		JTextField saldoTextField = new JTextField();
		JLabel lblSaldo = new JLabel();
		saldoTextField.setEnabled(false);
		
		JTextField residenzaTextField = new JTextField();
		MyJLabel lblResidenza = new MyJLabel();
		residenzaTextField.setEnabled(false);
		
		
		
		panelRiepilogoInfoUtente.aggiungiTextField(saldoTextField, lblSaldo, "Il tuo saldo attuale", String.valueOf(utenteLoggato.getSaldo()));
		panelRiepilogoInfoUtente.add(Box.createRigidArea(new Dimension(0, 20)));
		panelRiepilogoInfoUtente.aggiungiTextField(residenzaTextField, lblResidenza, "La tua residenza", utenteLoggato.getResidenza(), iconaModifyScalata);
		
		lblResidenza.aggiungiEffettoCliccabilita();

		panelProfilo.add(panelRiepilogoInfoUtente);
	}
}
