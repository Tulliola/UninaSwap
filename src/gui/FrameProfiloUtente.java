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
import eccezioni.EmailException;
import eccezioni.PasswordException;
import eccezioni.ResidenzaException;
import eccezioni.UsernameException;
import utilities.MyJButton;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;

public class FrameProfiloUtente extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//Panels
	private JPanel contentPane;
	private JPanel panelProfilo;
	private MyJPanel panelRiepilogoInfoUtente;
	private MyJPanel panelBottoni;

	//Buttons
	private MyJButton bottoneTornaIndietro;
	private MyJButton bottoneSalvaModifiche;
	
	//TextFields
	private MyJTextField residenzaTextField;
	private MyJTextField usernameTextField;
	private MyJTextField emailTextField;
	private MyJTextField passwordTextField;
	private MyJTextField saldoTextField;
	
	private Controller mainController;
	
	public FrameProfiloUtente(Controller controller, ProfiloUtente utenteLoggato) {
		mainController = controller;
		
		this.impostaSettingsPerFrame();
		this.impostaContentPane(utenteLoggato);

	}
	
	private void impostaSettingsPerFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Il tuo profilo utente");
		this.setSize(500, 900);
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
		
		panelProfilo.add(Box.createRigidArea(new Dimension(0, 25)));
		
		this.aggiungiPanelRiepilogoInformazioni(utenteLoggato);
		
		panelProfilo.add(Box.createRigidArea(new Dimension(0, 20)));

		this.aggiungiPanelBottoni();
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
		
		MyJLabel lblCambiaImmagine = new MyJLabel("CAMBIA IMMAGINE DI PROFILO");

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
		panelRiepilogoInfoUtente = new MyJPanel();
		
		panelRiepilogoInfoUtente.setLayout(new BoxLayout(panelRiepilogoInfoUtente, BoxLayout.Y_AXIS));
		panelRiepilogoInfoUtente.setAlignmentX(CENTER_ALIGNMENT);
		
		ImageIcon modifyIcon = new ImageIcon("images/iconModify.png");
		Image resizedModifyIcon = modifyIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		ImageIcon iconaModifyScalata = new ImageIcon(resizedModifyIcon);
		
		//Creazione delle icone di modifica con relativa logica
		MyJLabel lblUsername = new MyJLabel(iconaModifyScalata, true);
		lblUsername.setText("Il tuo username");
		lblUsername.setHorizontalTextPosition(SwingConstants.LEFT);
		lblUsername.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				usernameTextField.cambiaStatoEnabled();
				usernameTextField.modificaBGColorSeEnabled(Color.LIGHT_GRAY, Color.WHITE);
				mostraBottoneSalvaModifiche();
			}
		});
				
		MyJLabel lblPassword = new MyJLabel(iconaModifyScalata, true);
		lblPassword.setText("La tua password");
		lblPassword.setHorizontalTextPosition(SwingConstants.LEFT);
		lblPassword.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				passwordTextField.cambiaStatoEnabled();
				passwordTextField.modificaBGColorSeEnabled(Color.LIGHT_GRAY, Color.WHITE);
				mostraBottoneSalvaModifiche();
			}
		});
		
		MyJLabel lblResidenza = new MyJLabel(iconaModifyScalata, true);
		lblResidenza.setText("La tua residenza");
		lblResidenza.setHorizontalTextPosition(SwingConstants.LEFT);
		lblResidenza.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				residenzaTextField.cambiaStatoEnabled();
				residenzaTextField.modificaBGColorSeEnabled(Color.LIGHT_GRAY, Color.WHITE);
				mostraBottoneSalvaModifiche();
			}
		});
		emailTextField = new MyJTextField(String.valueOf(utenteLoggato.getEmail()));
		emailTextField.setAlignmentX(LEFT_ALIGNMENT);
		emailTextField.setEnabled(false);
		MyJLabel lblEmail = new MyJLabel("La tua email istituzionale");
		lblEmail.setAlignmentX(LEFT_ALIGNMENT);
		
		usernameTextField = new MyJTextField(utenteLoggato.getUsername());
		usernameTextField.setAlignmentX(LEFT_ALIGNMENT);
		usernameTextField.setEnabled(false);

		saldoTextField = new MyJTextField(String.valueOf(utenteLoggato.getSaldo()));
		saldoTextField.setAlignmentX(LEFT_ALIGNMENT);
		saldoTextField.setEnabled(false);
		MyJLabel lblSaldo = new MyJLabel("Il tuo saldo attuale");
		lblSaldo.setAlignmentX(LEFT_ALIGNMENT);
		
		//TODO aggiungere come MyJPasswordTextField
		passwordTextField = new MyJTextField(utenteLoggato.getPassword());
		passwordTextField.setAlignmentX(LEFT_ALIGNMENT);
		passwordTextField.setEnabled(false);
		
		residenzaTextField = new MyJTextField(utenteLoggato.getResidenza());
		residenzaTextField.setAlignmentX(LEFT_ALIGNMENT);
		residenzaTextField.setEnabled(false);
		
		panelRiepilogoInfoUtente.aggiungiTextFieldConLabel(emailTextField, lblEmail);
		panelRiepilogoInfoUtente.add(Box.createRigidArea(new Dimension(0, 20)));
		panelRiepilogoInfoUtente.aggiungiTextFieldConLabel(usernameTextField, lblUsername);
		panelRiepilogoInfoUtente.add(Box.createRigidArea(new Dimension(0, 20)));
		panelRiepilogoInfoUtente.aggiungiTextFieldConLabel(saldoTextField, lblSaldo);
		panelRiepilogoInfoUtente.add(Box.createRigidArea(new Dimension(0, 20)));
		panelRiepilogoInfoUtente.aggiungiTextFieldConLabel(passwordTextField, lblPassword);
		panelRiepilogoInfoUtente.add(Box.createRigidArea(new Dimension(0, 20)));
		panelRiepilogoInfoUtente.aggiungiTextFieldConLabel(residenzaTextField, lblResidenza);

		panelProfilo.add(panelRiepilogoInfoUtente);

		panelProfilo.add(Box.createRigidArea(new Dimension(0, 20)));
		
	}
	
	private void mostraBottoneSalvaModifiche() {
		if(!(bottoneSalvaModifiche.isVisible()))
			bottoneSalvaModifiche.setVisible(true);
	}
	
	private void aggiungiPanelBottoni() {
		panelBottoni = new MyJPanel();
		
		panelBottoni.setLayout(new BoxLayout(panelBottoni, BoxLayout.X_AXIS));
		panelBottoni.setAlignmentX(CENTER_ALIGNMENT);
		
		bottoneTornaIndietro = new MyJButton("Torna indietro");
		
		bottoneSalvaModifiche = new MyJButton("Salva modifiche");
		bottoneSalvaModifiche.setVisible(false);
		
		bottoneSalvaModifiche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainController.onSalvaModificheProfiloClicked();
			}
		});
		
		panelBottoni.add(bottoneSalvaModifiche);
		panelBottoni.add(Box.createRigidArea(new Dimension(10, 0)));
		panelBottoni.add(bottoneTornaIndietro);
		
		panelProfilo.add(panelBottoni);
	}
	
	//TODO aggiungere la logica di errore
//	private void clickSalvaModificheButton() {
//		try {
//			
//		}
//	}
//	
	private void checkUsername(String usernameIn) {
		if(usernameIn == null || usernameIn.length() == 0)
			throw new UsernameException("Inserire un username.");
		
		if(usernameIn.length() > 20)
			throw new UsernameException("L'username deve essere di massimo 20 caratteri.");
		
		if(usernameIn.contains(" "))
			throw new UsernameException("L'username non deve contenere spazi vuoti.");
	}
	
	
	private void checkPassword(String passwordIn) {
		if(passwordIn == null || passwordIn.length() == 0)
			throw new PasswordException("Inserire una password.");
	}
	
	
	private void checkResidenza(String residenzaIn) {
		if(residenzaIn == null || residenzaIn.isBlank())
			throw new ResidenzaException("Inserire una residenza.");
		
		if(residenzaIn.startsWith(" "))
			throw new ResidenzaException("La residenza non può iniziare con uno spazio vuoto.");
		
		if(residenzaIn.endsWith(" "))
			throw new ResidenzaException("La residenza non può terminare con uno spazio vuoto.");

	}
}
