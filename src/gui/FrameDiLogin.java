package gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;

import controller.Controller;
import eccezioni.*;

import utilities.MyJFrame;
import utilities.MyJButton;
import utilities.MyJLabel;

import utilities.MyJPanel;
import utilities.MyJPasswordField;
import utilities.MyJTextField;

public class FrameDiLogin extends MyJFrame {

	private static final long serialVersionUID = 1L;
	
	//Panel del frame
	private MyJPanel contentPane;
	private MyJPanel bluePane;
	private MyJPanel fieldPane;
	private MyJPanel buttonsPane;
	
	//Field per l'accesso
	private MyJTextField emailTextField;
	private MyJPasswordField passwordField;

	//Bottoni per il frame Accesso
	private MyJButton accediButton = new MyJButton("Accedi");
	private MyJButton registratiButton = new MyJButton("Registrati");
	
	//Label di errore per il frame Accesso
	private MyJLabel lblErroreEmail = new MyJLabel(true);
	private MyJLabel lblErrorePassword = new MyJLabel(true);
	private MyJLabel lblErroreComunicazioneColDB = new MyJLabel(true);

	//Controller con cui comunicare
	private Controller mainController;
	
	public FrameDiLogin(Controller controller) {
		mainController = controller;
		
		//Impostazione del frame di base
		impostaSettingsPerFrame();
		
		//Aggiunta del logo dell'applicativo UninaSwap
		aggiungiLogo(new ImageIcon("images/logo_uninaswap.png"));
		
		//Aggiunta dello slogan dell'applicativo UninaSwap
		aggiungiMotto();
		
		contentPane.add(Box.createRigidArea(new Dimension(25, 25)));
		
		//Settaggio del riquadro blu
		contentPane.add(Box.createRigidArea(new Dimension(0, 30)));
		settingBluePane();
		
		//Aggiunta delle Label e dei TextField necessari per l'accesso
		settingFieldPane();
		aggiungiFieldAccesso();
		
		//Aggiunta dei bottoni per l'accesso
		settingButtonsPane();
		aggiungiBottoniLogin();

		contentPane.add(Box.createRigidArea(new Dimension(20, 20)));
		
	}

	//Settaggio di base del frame
	private void impostaSettingsPerFrame() {
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(500, 800));
		
		ImageIcon iconaFinestraPartenza = new ImageIcon("images/logo_uninaswap.png");
		Image iconaFinestra = iconaFinestraPartenza.getImage();
		this.setIconImage(iconaFinestra);
		
		contentPane = new MyJPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(Box.createRigidArea(new Dimension(25, 25)));
		
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Accesso - UninaSwap");
		this.setContentPane(contentPane);
	
	}

	//Aggiunta del logo dell'applicazione
	private void aggiungiLogo(ImageIcon img) {
		MyJLabel etichettaImmagine = new MyJLabel();
		etichettaImmagine.aggiungiImmagineScalata("images/logo_uninaswap.png", 200, 200, false);
		etichettaImmagine.setAlignmentX(CENTER_ALIGNMENT);
		
		contentPane.add(etichettaImmagine);
	}
	
	//Aggiunta dello slogan dell'applicativo
	private void aggiungiMotto() {
		MyJLabel lblNomeApplicativo = new MyJLabel("UninaSwap", new Font("Ubuntu Sans", Font.BOLD, 24));
		lblNomeApplicativo.setForeground(MyJLabel.uninaColor);
		lblNomeApplicativo.setAlignmentX(CENTER_ALIGNMENT);
		
		MyJLabel lblMottoApplicativo = new MyJLabel("Il mercatino digitale federiciano", new Font("Ubuntu Sans", Font.ITALIC, 16));
		lblMottoApplicativo.setForeground(MyJLabel.uninaColor);
		lblMottoApplicativo.setAlignmentX(CENTER_ALIGNMENT);
		
		contentPane.add(lblNomeApplicativo);
		contentPane.add(lblMottoApplicativo);
	}
	
	//Settaggio del panel esterno a quello dei campi
	private void settingBluePane() {
		bluePane = new MyJPanel();
		bluePane.setLayout(new BoxLayout(bluePane, BoxLayout.Y_AXIS));
		bluePane.setBackground(MyJPanel.uninaLightColor);
		bluePane.setPreferredSize(new Dimension(400, 300));
		bluePane.setMaximumSize(new Dimension(400, 300));
		bluePane.setAlignmentX(CENTER_ALIGNMENT);
		bluePane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	}
	
	//Settaggio del panel dei campi
	private void settingFieldPane() {
		fieldPane = new MyJPanel();
		fieldPane.setBackground(new Color(198, 210, 222));
		fieldPane.setLayout(new BoxLayout(fieldPane, BoxLayout.Y_AXIS));
		fieldPane.add(Box.createRigidArea(new Dimension(25, 25)));
	}
	
	//Aggiunta dei field di accesso e dei relativi comportamenti tramite lambda expressions
	private void aggiungiFieldAccesso() {
		
		MyJLabel lblEmail = new MyJLabel("Email istituzionale o Username", new Font("Ubuntu Sans", Font.BOLD, 15));
		emailTextField = new MyJTextField(new Font("Ubuntu Sans", Font.PLAIN, 13));
		emailTextField.setAlignmentX(LEFT_ALIGNMENT);
		
		MyJLabel lblPassword = new MyJLabel("Password", new Font("Ubuntu Sans", Font.BOLD, 15));
		passwordField = new MyJPasswordField();
		passwordField.setAlignmentX(LEFT_ALIGNMENT);

		emailTextField.setDefaultAction(() -> {
			passwordField.requestFocus();
		});
		
		emailTextField.setDownAction(() -> {
			passwordField.requestFocus();
		});
		
		emailTextField.setUpAction(()->{});
		
		passwordField.setDefaultAction(() -> {
			accediButton.setFocusable(true);
			accediButton.requestFocus();
		});
		
		passwordField.setDownAction(() -> {
			accediButton.setFocusable(true);
			accediButton.requestFocus();
		});
		
		passwordField.setUpAction(()->{
			emailTextField.requestFocus();
		});
		
		fieldPane.add(lblEmail);
		fieldPane.add((JTextField)emailTextField);
		fieldPane.add(lblErroreEmail);
		
		fieldPane.add(Box.createRigidArea(new Dimension(15, 15)));

		fieldPane.add(lblPassword);
		fieldPane.add(passwordField);
		fieldPane.add(lblErrorePassword);
		
		fieldPane.setAlignmentX(CENTER_ALIGNMENT);
		
		bluePane.add(fieldPane);
		bluePane.add(Box.createVerticalStrut(20));
		
		lblErroreComunicazioneColDB.setAlignmentX(CENTER_ALIGNMENT);

		bluePane.add(lblErroreComunicazioneColDB);
		bluePane.add(Box.createVerticalStrut(10));
		
		contentPane.add(bluePane);
	}
	
	//Settaggio del panel dei bottoni
	private void settingButtonsPane() {
		buttonsPane = new MyJPanel();
		buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.Y_AXIS));
		buttonsPane.setAlignmentX(CENTER_ALIGNMENT);
		buttonsPane.setBackground(new Color(198, 210, 222));
	}
	
	//Aggiunta dei bottoni al relativo panel e con relativi comportamenti tramite lambda expressions
	private void aggiungiBottoniLogin() {
		accediButton.setAlignmentX(CENTER_ALIGNMENT);
		accediButton.setForeground(Color.WHITE);
		accediButton.setBackground(MyJButton.uninaColor);
		
		accediButton.setDefaultAction(() -> {
			registratiButton.setFocusable(true);
			nascondiLabelErrore(lblErroreEmail, lblErrorePassword, lblErroreComunicazioneColDB);
			resettaBordiTextField(emailTextField, passwordField);
			clickAccedi();
		});
		
		accediButton.setPreviousComponent(passwordField);
		
		accediButton.setUpAction(() -> {
			accediButton.getPreviousComponent().setFocusable(true);
			accediButton.getPreviousComponent().requestFocus();
		});
		
		accediButton.setNextComponent(registratiButton);
		
		accediButton.setDownAction(() -> {
			accediButton.getNextComponent().setFocusable(true);
			accediButton.getNextComponent().requestFocus();
		});
		
		JLabel lblOppureRegistrati = new JLabel ("Oppure, se non sei ancora registrato, fallo!");
		lblOppureRegistrati.setFont(new Font("Ubuntu Sans", Font.ITALIC, 15));
		lblOppureRegistrati.setAlignmentX(CENTER_ALIGNMENT);
		
		registratiButton.setAlignmentX(CENTER_ALIGNMENT);
		registratiButton.setForeground(Color.WHITE);
		registratiButton.setBackground(MyJButton.uninaColor);
		
		registratiButton.setDefaultAction(() -> {
			mainController.passaAFrameDiRegistrazione();
		});
		
		registratiButton.setUpAction(() -> {
			accediButton.setFocusable(true);
			accediButton.requestFocus();
		});
		
		registratiButton.setDownAction(()->{});	
		
		buttonsPane.add(accediButton);
		buttonsPane.add(Box.createRigidArea(new Dimension(20, 20)));
		buttonsPane.add(lblOppureRegistrati);
		buttonsPane.add(Box.createRigidArea(new Dimension(10, 10)));
		buttonsPane.add(registratiButton);
		
		bluePane.add(buttonsPane);
	}

	//Verifica che l'utente riempia i campi
	private void checkDatiAccesso() {
		checkEmail(emailTextField.getText());
		checkPassword(passwordField.getText());
	}
	
	private void checkEmail(String email) {
		if(email == null || email.length() == 0)
			throw new EmailException("Il campo email è obbligatorio.");
	}
	
	private void checkPassword(String password) {
		if(password == null || password.length() == 0)
			throw new PasswordException("Il campo password è obbligatorio.");
	}
	
	//Istruzioni da eseguire quando l'utente clicca "Accedi"
	private void clickAccedi() {
		try {
			checkDatiAccesso();
			nascondiLabelErrore(lblErroreEmail, lblErrorePassword, lblErroreComunicazioneColDB);
			resettaBordiTextField(emailTextField, passwordField);
			mainController.onAccessoButtonClicked(emailTextField.getText(), passwordField.getText());
		}
		catch(EmailException e) {
			lblErroreEmail.setText(e.getMessage());
			lblErroreEmail.setVisible(true);
			emailTextField.requestFocus();
			emailTextField.setBorder(MyJTextField.redBorder);
		}
		catch(PasswordException e) {
			lblErrorePassword.setText(e.getMessage());
			lblErrorePassword.setVisible(true);
			passwordField.requestFocus();
			passwordField.setBorder(MyJPasswordField.redBorder);
		}
		catch(UtenteNonTrovatoException e) {
			lblErroreComunicazioneColDB.setText(e.getMessage());
			lblErroreComunicazioneColDB.setVisible(true);
		}
		catch(UtentePasswordMismatchException e) {
			lblErroreComunicazioneColDB.setText(e.getMessage());
			lblErroreComunicazioneColDB.setVisible(true);
		}
		catch(SQLException e) {
			lblErroreComunicazioneColDB.setText("Errore nella comunicazione col database");
			lblErroreComunicazioneColDB.setVisible(true);
		}
	}
}