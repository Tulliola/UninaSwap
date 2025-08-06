package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.Controller;

public class FrameDiLogin extends JFrame {
	//TODO aggiungere variabili di istanza
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Controller mainController;
	
	public FrameDiLogin(Controller controller) {
		mainController = controller;
		
		impostaSettingsPerFrame();
		
		//aggiunta del logo dell'applicativo UninaSwap
		aggiungiLogo(new ImageIcon("images/logo_uninaswap.png"));
		
		//aggiunta dello slogan dell'applicativo UninaSwap
		aggiungiMotto();
		
		contentPane.add(Box.createRigidArea(new Dimension(30, 30)));

		//aggiunta delle Lable e dei TextField necessari per l'accesso
		aggiungiFieldAccesso();
	
		//aggiunta dei bottoni per l'accesso
		JButton accedi = new JButton("Accedi");
		JButton registrati = new JButton("Registrati");
		aggiungiBottoniAccesso(accedi, registrati);
		
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
		setContentPane(contentPane);
		
	}
	
	private void aggiungiFieldAccesso() {
		JPanel pannelloFieldDatiAccesso = new JPanel();
		pannelloFieldDatiAccesso.setLayout(new BoxLayout(pannelloFieldDatiAccesso, BoxLayout.Y_AXIS));
		
		pannelloFieldDatiAccesso.setBackground(Color.WHITE);
		
		JLabel email = new JLabel("Email istituzionale o username");
		JTextField inserisciEmail = new JTextField(10);
		inserisciEmail.setMaximumSize(new Dimension(300, 30));
		email.setAlignmentX(LEFT_ALIGNMENT);
		inserisciEmail.setAlignmentX(LEFT_ALIGNMENT);
		
		pannelloFieldDatiAccesso.add(email);
		pannelloFieldDatiAccesso.add(inserisciEmail);
		
		
		JLabel password = new JLabel("Password");
		JPasswordField inserisciPassword = new JPasswordField();
		inserisciPassword.setMaximumSize(new Dimension(300, 30));
		password.setAlignmentX(LEFT_ALIGNMENT);
		inserisciPassword.setAlignmentX(LEFT_ALIGNMENT);
		
		pannelloFieldDatiAccesso.add(password);
		pannelloFieldDatiAccesso.add(inserisciPassword);
		
		
		pannelloFieldDatiAccesso.setAlignmentX(CENTER_ALIGNMENT);
		pannelloFieldDatiAccesso.setAlignmentY(CENTER_ALIGNMENT);
		
		
		contentPane.add(pannelloFieldDatiAccesso);
	}
	
	private void aggiungiLogo(ImageIcon img) {
		JLabel etichettaImmagine = new JLabel();
		Image resizedImg = img.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon logo = new ImageIcon(resizedImg);
		etichettaImmagine.setIcon(logo);
		etichettaImmagine.setAlignmentX(CENTER_ALIGNMENT);
		etichettaImmagine.setAlignmentY(CENTER_ALIGNMENT);
		
		contentPane.add(etichettaImmagine);
		
	}
	
	private void aggiungiBottoniAccesso(JButton accedi, JButton registrati) {
		JPanel pannelloBottoni = new JPanel();
		pannelloBottoni.setLayout(new BoxLayout(pannelloBottoni, BoxLayout.Y_AXIS));
		pannelloBottoni.setBackground(Color.WHITE);
		
		accedi.setAlignmentX(CENTER_ALIGNMENT);
		accedi.setAlignmentY(BOTTOM_ALIGNMENT);
		accedi.setBackground(new Color(65, 106, 144));
		accedi.setForeground(Color.WHITE);
		accedi.setFocusable(false);
		
		registrati.setAlignmentX(CENTER_ALIGNMENT);
		registrati.setAlignmentY(BOTTOM_ALIGNMENT);
		registrati.setBackground(pannelloBottoni.getBackground());
		registrati.setFocusable(false);
		
		JLabel oppureRegistrati = new JLabel ("Oppure se non sei ancora registrato, fallo!");
		oppureRegistrati.setAlignmentX(CENTER_ALIGNMENT);
		JTextPane pane = new JTextPane();

		
		pannelloBottoni.add(accedi);
		pannelloBottoni.add(oppureRegistrati);
		pannelloBottoni.add(registrati);
		
		contentPane.add(pannelloBottoni);
	}

	private void aggiungiMotto() {
		JPanel pannelloMotto = new JPanel();
		pannelloMotto.setBackground(Color.WHITE);
		pannelloMotto.setLayout(new BoxLayout(pannelloMotto, BoxLayout.Y_AXIS));
		
		JLabel nome = new JLabel("UninaSwap");
		nome.setAlignmentX(CENTER_ALIGNMENT);
		nome.setAlignmentY(TOP_ALIGNMENT);
		nome.setFont(new Font("UninaSwap", Font.BOLD, 25));
		nome.setForeground(new Color(65, 106, 144));
		
		JLabel motto = new JLabel("Il mercatino digitale federiciano");
		motto.setAlignmentX(CENTER_ALIGNMENT);
		motto.setAlignmentY(BOTTOM_ALIGNMENT);
		motto.setFont(new Font("Motto", Font.ITALIC, 16));
		motto.setForeground(new Color(65, 106, 144));
		
		pannelloMotto.add(nome);
		pannelloMotto.add(motto);
		
		pannelloMotto.setAlignmentX(CENTER_ALIGNMENT);
		pannelloMotto.setAlignmentY(CENTER_ALIGNMENT);
		contentPane.add(pannelloMotto);
	}
}
