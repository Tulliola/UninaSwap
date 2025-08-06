package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import controller.Controller;

public class FrameDiRegistrazione extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//Panels
	private JPanel contentPane;
	private JPanel panelInserimentoDati;

	//Textfields
	private JTextField usernameTextField;
	private JTextField emailTextField;
	private JTextField residenzaTextField;
	private JPasswordField passwordTextField;
	
	//Buttons
	private JButton bottoneDiRegistrazione;
	
	//Controller
    private Controller mainController;

    
	//Costruttore
	public FrameDiRegistrazione(Controller controller) {
		mainController = controller;
		
		this.impostaSettingsPerFrame();
		this.aggiungiLogoCentrale();
		this.aggiungiMottoSottoAlLogo();
		this.impostaPanelInserimentoDati();
		
	}
	
	private void impostaSettingsPerFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Registrati ora!");
		this.setSize(500, 800);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setBackground(Color.white);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
	}
	
	private void impostaPanelInserimentoDati() {
		panelInserimentoDati = new JPanel();
		panelInserimentoDati.setLayout(new BoxLayout(panelInserimentoDati, BoxLayout.Y_AXIS));
		panelInserimentoDati.setPreferredSize(new Dimension(400, 400));
		panelInserimentoDati.setMaximumSize(new Dimension(400, 400));
		panelInserimentoDati.setBackground(new Color(198, 210, 222));	
		
		//Crea spazio tra il motto e i campi di testo
		contentPane.add(Box.createRigidArea(new Dimension(0, 50)));
		
		JLabel campiObbligatori = new JLabel();
		campiObbligatori.setText("I campi con * sono obbligatori.");
		campiObbligatori.setAlignmentX(CENTER_ALIGNMENT);
		
		panelInserimentoDati.add(campiObbligatori);
		
		this.aggiungiTextFields();
		this.aggiungiBottoneDiRegistrazione();
	
		panelInserimentoDati.setAlignmentX(CENTER_ALIGNMENT);
		
		contentPane.add(panelInserimentoDati);
	}
	
	private void aggiungiLogoCentrale() {
		//Crea spazio tra il bordo superiore e il logo
		contentPane.add(Box.createRigidArea(new Dimension(0, 25)));
		
		ImageIcon logo = new ImageIcon("images/logo_uninaswap.png");
		Image resizedLogo = logo.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon logoScalato = new ImageIcon(resizedLogo);
		
		JLabel lblLogo = new JLabel();
		lblLogo.setIcon(logoScalato);
		lblLogo.setAlignmentX(CENTER_ALIGNMENT);
		
		contentPane.add(lblLogo);
	}
	
	private void aggiungiMottoSottoAlLogo() {
		
		JLabel nomeApplicativo = new JLabel();
		nomeApplicativo.setText("UninaSwap");
		nomeApplicativo.setFont(new Font("Ubuntu Sans", Font.BOLD, 24));
		nomeApplicativo.setForeground(new Color(65, 106, 144));
		nomeApplicativo.setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel mottoApplicativo = new JLabel();
		mottoApplicativo.setText("Il mercatino digitale federiciano");
		mottoApplicativo.setFont(new Font("Ubuntu Sans", Font.ITALIC, 16));
		mottoApplicativo.setForeground(new Color(65, 106, 144));
		mottoApplicativo.setAlignmentX(CENTER_ALIGNMENT);
		
		contentPane.add(nomeApplicativo);
		contentPane.add(mottoApplicativo);
	}
	
	private void aggiungiTextFields() {
		usernameTextField = new JTextField();
		this.aggiungiTextField(usernameTextField, "Inserisci il tuo username");

		emailTextField = new JTextField();
		this.aggiungiTextField(emailTextField, "Inserisci la tua email istituzionale");
		
		passwordTextField = new JPasswordField();
		this.aggiungiTextField(passwordTextField, "Inserisci la tua password istituzionale");
	
		residenzaTextField = new JTextField();
		this.aggiungiTextField(residenzaTextField, "Inserisci la tua residenza");
		
	}
	
	private void aggiungiTextField(JTextField textFieldInput, String stringaPerLabel) {
		JLabel label = new JLabel();
		label.setText(stringaPerLabel);
		label.setForeground(Color.black);
		label.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		label.setAlignmentX(CENTER_ALIGNMENT);
		
		textFieldInput.setMaximumSize(new Dimension(300, 30));
		textFieldInput.setAlignmentX(CENTER_ALIGNMENT);
		
		panelInserimentoDati.add(label);
		panelInserimentoDati.add(textFieldInput);
		
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 15)));
	}
	
	private void aggiungiBottoneDiRegistrazione() {
		
		bottoneDiRegistrazione = new JButton();
		bottoneDiRegistrazione.setText("Registrati");
		bottoneDiRegistrazione.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		bottoneDiRegistrazione.setBackground(new Color(65, 106, 144));
		bottoneDiRegistrazione.setForeground(Color.white);
		bottoneDiRegistrazione.setFocusable(false);
		bottoneDiRegistrazione.setAlignmentX(CENTER_ALIGNMENT);
		
		panelInserimentoDati.add(bottoneDiRegistrazione);
		
	}
}
