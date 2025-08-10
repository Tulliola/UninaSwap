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
import utilities.MyJButton;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJPasswordField;
import utilities.MyJTextField;

public class FrameDiLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//Panel del frame
	private MyJPanel contentPane;
	private MyJPanel bluePane;
	private MyJPanel fieldPane;
	private MyJPanel buttonsPane;
	
	//Field per l'accesso
	private MyJTextField emailField;
	private MyJPasswordField passwordField;
	
	//Bottoni per il frame Accesso
	private MyJButton accediButton = new MyJButton("Accedi");
	private MyJButton registratiButton = new MyJButton("Registrati");
	
	//Label di errore per il frame Accesso
	private MyJLabel erroreEmail = new MyJLabel(true);
	private MyJLabel errorePassword = new MyJLabel(true);
	private MyJLabel erroreComunicazioneDatabase = new MyJLabel(true);

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
		etichettaImmagine.aggiungiImmagineScalataX(200, 200, "images/logo_uninaswap.png", CENTER_ALIGNMENT);
		
		contentPane.add(etichettaImmagine);
	}
	
	//Aggiunta dello slogan dell'applicativo
	private void aggiungiMotto() {
		MyJLabel nome = new MyJLabel("UninaSwap", new Font("Ubuntu Sans", Font.BOLD, 24));
		nome.setForeground(MyJLabel.uninaColor);
		nome.setAlignmentX(CENTER_ALIGNMENT);
		
		MyJLabel motto = new MyJLabel("Il mercatino digitale federiciano", new Font("Ubuntu Sans", Font.ITALIC, 16));
		motto.setForeground(MyJLabel.uninaColor);
		motto.setAlignmentX(CENTER_ALIGNMENT);
		
		contentPane.add(nome);
		contentPane.add(motto);
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
		
		MyJLabel email = new MyJLabel("Email istituzionale o Username", new Font("Ubuntu Sans", Font.BOLD, 15));
		emailField = new MyJTextField(new Font("Ubuntu Sans", Font.PLAIN, 13));
		emailField.setAlignmentX(LEFT_ALIGNMENT);
		
		MyJLabel password = new MyJLabel("Password", new Font("Ubuntu Sans", Font.BOLD, 15));
		passwordField = new MyJPasswordField();
		passwordField.setAlignmentX(LEFT_ALIGNMENT);

		emailField.setDefaultAction(() -> {
			passwordField.requestFocus();
		});
		
		emailField.setDownAction(() -> {
			passwordField.requestFocus();
		});
		
		emailField.setUpAction(()->{});
		
		passwordField.setDefaultAction(() -> {
			accediButton.setFocusable(true);
			accediButton.requestFocus();
		});
		
		passwordField.setDownAction(() -> {
			accediButton.setFocusable(true);
			accediButton.requestFocus();
		});
		
		passwordField.setUpAction(()->{
			emailField.requestFocus();
		});
		
		fieldPane.add(email);
		fieldPane.add((JTextField)emailField);
		fieldPane.add(erroreEmail);
		
		fieldPane.add(Box.createRigidArea(new Dimension(15, 15)));

		fieldPane.add(password);
		fieldPane.add(passwordField);
		fieldPane.add(errorePassword);
		
		fieldPane.setAlignmentX(CENTER_ALIGNMENT);
		
		bluePane.add(fieldPane);
		bluePane.add(Box.createVerticalStrut(20));
		
		erroreComunicazioneDatabase.setForeground(Color.RED);
		erroreComunicazioneDatabase.setAlignmentX(CENTER_ALIGNMENT);
		erroreComunicazioneDatabase.setVisible(false);
		bluePane.add(erroreComunicazioneDatabase);
		bluePane.add(Box.createVerticalStrut(10));
		
		contentPane.add(bluePane);
	}
	
	private void settingButtonsPane() {
		buttonsPane = new MyJPanel();
		buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.Y_AXIS));
		buttonsPane.setAlignmentX(CENTER_ALIGNMENT);
		buttonsPane.setBackground(new Color(198, 210, 222));
	}
	
	private void aggiungiBottoniLogin() {
		accediButton.setAlignmentX(CENTER_ALIGNMENT);
		accediButton.setForeground(Color.WHITE);
		accediButton.setBackground(MyJButton.uninaColor);
		
		accediButton.setDefaultAction(() -> {
			nascondiErrori();
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
		
		JLabel oppureRegistrati = new JLabel ("Oppure, se non sei ancora registrato, fallo!");
		oppureRegistrati.setFont(new Font("Ubuntu Sans", Font.ITALIC, 15));
		oppureRegistrati.setAlignmentX(CENTER_ALIGNMENT);
		
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
		buttonsPane.add(oppureRegistrati);
		buttonsPane.add(Box.createRigidArea(new Dimension(10, 10)));
		buttonsPane.add(registratiButton);
		
		bluePane.add(buttonsPane);
	}
	
	
	
	public void nascondiErrori() {
		erroreEmail.setVisible(false);
		errorePassword.setVisible(false);
		erroreComunicazioneDatabase.setVisible(false);
		
		emailField.setBorder(MyJTextField.blackBorder);
		passwordField.setBorder(MyJTextField.blackBorder);
	}
		
	public void checkDatiAccesso() {
		checkEmail(emailField.getText());
		checkPassword(passwordField.getText());
	}
	
	public void checkEmail(String email) {
		if(email == null || email.length() == 0)
			throw new EmailException("Il campo email è obbligatorio.");
	}
	
	public void checkPassword(String password) {
		if(password == null || password.length() == 0)
			throw new PasswordException("Il campo password è obbligatorio.");
	}
	
	private void clickAccedi() {
		try {
			checkDatiAccesso();
			nascondiErrori();
			mainController.onAccessoButtonClicked(emailField.getText(), passwordField.getText());
		}
		catch(EmailException e) {
			erroreEmail.setText(e.getMessage());
			erroreEmail.setVisible(true);
			emailField.requestFocus();
			emailField.setBorder(MyJTextField.redBorder);
		}
		catch(PasswordException e) {
			errorePassword.setText(e.getMessage());
			errorePassword.setVisible(true);
			passwordField.requestFocus();
			passwordField.setBorder(MyJPasswordField.redBorder);
		}
		catch(UtenteNonTrovatoException e) {
			erroreComunicazioneDatabase.setText(e.getMessage());
			erroreComunicazioneDatabase.setVisible(true);
		}
		catch(UtentePasswordMismatchException e) {
			erroreComunicazioneDatabase.setText(e.getMessage());
			erroreComunicazioneDatabase.setVisible(true);
		}
		catch(SQLException e) {
			erroreComunicazioneDatabase.setText("Errore nella comunicazione col database");
			erroreComunicazioneDatabase.setVisible(true);
		}
	}
}