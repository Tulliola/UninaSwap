package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.Controller;

public class FrameDiLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel fieldPane;
	private JPanel buttonsPane;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JButton accediButton = new JButton("Accedi");
	private JButton registratiButton = new JButton("Registrati");
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

		//Aggiunta delle Lable e dei TextField necessari per l'accesso
		aggiungiFieldAccesso();
		
		contentPane.add(Box.createRigidArea(new Dimension(20, 20)));
	
		//Aggiunta dei bottoni per l'accesso
		aggiungiBottoniLogin();
		
	}
	
	private void impostaSettingsPerFrame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(600, 600));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Accesso");
		this.setContentPane(contentPane);
		
	}
	
	private void aggiungiFieldAccesso() {
		fieldPane = new JPanel();
		fieldPane.setBackground(Color.WHITE);
		fieldPane.setLayout(new BoxLayout(fieldPane, BoxLayout.Y_AXIS));
		
		JLabel campiObbligatori = new JLabel("I campi col simbolo * sono obbligatori");
		campiObbligatori.setAlignmentX(LEFT_ALIGNMENT);
		fieldPane.add(campiObbligatori);	
		
		fieldPane.add(Box.createRigidArea(new Dimension(25, 25)));
		
		JLabel email = new JLabel("Email istituzionale o username *");
		emailField = new JTextField();
		aggiungiFieldAccesso(email, emailField);
		
		fieldPane.add(email);
		fieldPane.add(emailField);
		
		fieldPane.add(Box.createRigidArea(new Dimension(15, 15)));
		
		JLabel password = new JLabel("Password *");
		passwordField = new JPasswordField();
		aggiungiFieldAccesso(password, passwordField);
		
		fieldPane.add(password);
		fieldPane.add(passwordField);
		
		
		fieldPane.setAlignmentX(CENTER_ALIGNMENT);
		
		
		contentPane.add(fieldPane);
	}
	
	private void aggiungiFieldAccesso(JLabel field, JTextField text) {
		text.setMaximumSize(new Dimension(300, 30));
		field.setAlignmentX(LEFT_ALIGNMENT);
		text.setAlignmentX(LEFT_ALIGNMENT);
	}
	
	private void aggiungiLogo(ImageIcon img) {
		JLabel etichettaImmagine = new JLabel();
		Image resizedImg = img.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon logo = new ImageIcon(resizedImg);
		etichettaImmagine.setIcon(logo);
		etichettaImmagine.setAlignmentX(CENTER_ALIGNMENT);
		
		contentPane.add(etichettaImmagine);
		
	}
	
	private void aggiungiBottoniLogin() {
		buttonsPane = new JPanel();
		buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.Y_AXIS));
		buttonsPane.setAlignmentX(CENTER_ALIGNMENT);
		buttonsPane.setBackground(Color.WHITE);
		
		aggiungiBottoniLogin(accediButton);
		
		JLabel oppureRegistrati = new JLabel ("Oppure, se non sei ancora registrato, fallo!");
		oppureRegistrati.setAlignmentX(CENTER_ALIGNMENT);
		
		aggiungiBottoniLogin(registratiButton);		
		
		buttonsPane.add(accediButton);
		buttonsPane.add(Box.createRigidArea(new Dimension(20, 20)));
		buttonsPane.add(oppureRegistrati);
		buttonsPane.add(Box.createRigidArea(new Dimension(10, 10)));
		buttonsPane.add(registratiButton);
		
		contentPane.add(buttonsPane);
	}
	
	private void aggiungiBottoniLogin(JButton button) {
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.setBackground(new Color(65, 106, 144));
		button.setForeground(Color.WHITE);
		button.setFocusable(false);
	}

	private void aggiungiMotto() {
		JLabel nome = new JLabel("UninaSwap");
		nome.setFont(new Font("UninaSwap", Font.BOLD, 25));
		nome.setForeground(new Color(65, 106, 144));
		nome.setAlignmentX(CENTER_ALIGNMENT);
		JLabel motto = new JLabel("Il mercatino digitale federiciano");
		motto.setFont(new Font("Motto", Font.ITALIC, 16));
		motto.setForeground(new Color(65, 106, 144));
		motto.setAlignmentX(CENTER_ALIGNMENT);
		
		contentPane.add(nome);
		contentPane.add(motto);
	}
}