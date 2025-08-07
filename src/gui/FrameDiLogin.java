package gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import eccezioni.*;

public class FrameDiLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	//Panel del frame
	private JPanel contentPane;
	private JPanel bluePane;
	private JPanel fieldPane;
	private JPanel buttonsPane;
	
	//Field per l'accesso
	private JTextField emailField;
	private JPasswordField passwordField;
	
	//Bottoni per accedere
	private JButton accediButton = new JButton("Accedi");
	private JButton registratiButton = new JButton("Registrati");
	
	//Label di errore
	JLabel erroreEmail = new JLabel();
	JLabel errorePassword = new JLabel();
	JLabel erroreComunicazioneDatabase = new JLabel();
	
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
		
		//Aggiunta delle Lable e dei TextField necessari per l'accesso
		settingFieldPane();
		aggiungiFieldAccesso();
		
		//Aggiunta dei bottoni per l'accesso
		settingButtonsPane();
		aggiungiBottoniLogin();

		contentPane.add(Box.createRigidArea(new Dimension(20, 20)));
		
	}

	private void impostaSettingsPerFrame() {
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(500, 800));
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(Box.createRigidArea(new Dimension(25, 25)));
		
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Accesso - UninaSwap");
		this.setContentPane(contentPane);
	
	}

	private void aggiungiLogo(ImageIcon img) {
		JLabel etichettaImmagine = new JLabel();
		Image resizedImg = img.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon logo = new ImageIcon(resizedImg);
		etichettaImmagine.setIcon(logo);
		etichettaImmagine.setAlignmentX(CENTER_ALIGNMENT);
		
		contentPane.add(etichettaImmagine);
	}
	
	private void aggiungiMotto() {
		JLabel nome = new JLabel("UninaSwap");
		nome.setFont(new Font("Ubuntu Sans", Font.BOLD, 24));
		settingMotto(nome);
		
		JLabel motto = new JLabel("Il mercatino digitale federiciano");
		motto.setFont(new Font("Ubuntu Sans", Font.ITALIC, 16));
		settingMotto(motto);
		
		contentPane.add(nome);
		contentPane.add(motto);
	}
	
	private void settingMotto(JLabel currLabel) {
		currLabel.setForeground(new Color(65, 106, 144));
		currLabel.setAlignmentX(CENTER_ALIGNMENT);
	}
	
	private void settingBluePane() {
		bluePane = new JPanel();
		bluePane.setLayout(new BoxLayout(bluePane, BoxLayout.Y_AXIS));
		bluePane.setBackground(new Color(198, 210, 222));
		bluePane.setPreferredSize(new Dimension(400, 300));
		bluePane.setMaximumSize(new Dimension(400, 300));
		bluePane.setAlignmentX(CENTER_ALIGNMENT);
		bluePane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	}
	
	private void settingFieldPane() {
		fieldPane = new JPanel();
		fieldPane.setBackground(new Color(198, 210, 222));
		fieldPane.setLayout(new BoxLayout(fieldPane, BoxLayout.Y_AXIS));
		fieldPane.add(Box.createRigidArea(new Dimension(25, 25)));
	}
	
	private void aggiungiFieldAccesso() {
		JLabel email = new JLabel("Email istituzionale o Username");
		emailField = new JTextField();
		aggiungiFieldAccesso(email, emailField, erroreEmail);
		
		fieldPane.add(email);
		fieldPane.add(emailField);
		fieldPane.add(erroreEmail);
		
		fieldPane.add(Box.createRigidArea(new Dimension(15, 15)));
		
		JLabel password = new JLabel("Password");
		passwordField = new JPasswordField();
		aggiungiFieldAccesso(password, passwordField, errorePassword);
		
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
	
	private void aggiungiFieldAccesso(JLabel field, JTextField text, JLabel errore) {
		errore.setForeground(Color.RED);
		errore.setVisible(false);
		
		field.setForeground(Color.black);
		field.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		field.setAlignmentX(LEFT_ALIGNMENT);
	
		text.setMaximumSize(new Dimension(300, 30));
		text.setFont(new Font("Ubuntu Sans", Font.PLAIN, 13));
		text.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		text.setAlignmentX(LEFT_ALIGNMENT);
	}
	
	private void settingButtonsPane() {
		buttonsPane = new JPanel();
		buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.Y_AXIS));
		buttonsPane.setAlignmentX(CENTER_ALIGNMENT);
		buttonsPane.setBackground(new Color(198, 210, 222));
	}
	
	private void aggiungiBottoniLogin() {
		accediButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				nascondiErrori();
				clickAccedi();
			}
			
		});
		
		aggiungiBottoniLogin(accediButton);
		
		JLabel oppureRegistrati = new JLabel ("Oppure, se non sei ancora registrato, fallo!");
		oppureRegistrati.setFont(new Font("Ubuntu Sans", Font.ITALIC, 15));
		oppureRegistrati.setAlignmentX(CENTER_ALIGNMENT);
		
		registratiButton.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.passaAFrameDiRegistrazione();
			}
			
		});
		
		aggiungiBottoniLogin(registratiButton);		

		
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
			
		emailField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		passwordField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}
	
	private void aggiungiBottoniLogin(JButton button) {
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.setBackground(new Color(65, 106, 144));
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		button.setFocusable(false);
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
			mainController.onAccessoClicked(emailField.getText(), passwordField.getText());
			nascondiErrori();
		}
		catch(EmailException e) {
			erroreEmail.setText(e.getMessage());
			erroreEmail.setVisible(true);

			emailField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		}
		catch(PasswordException e) {
			errorePassword.setText(e.getMessage());
			errorePassword.setVisible(true);
			
			passwordField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
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