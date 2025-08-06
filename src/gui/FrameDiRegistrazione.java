package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import controller.Controller;

public class FrameDiRegistrazione extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JPanel panelCentrale;

	private JTextField usernameTextField;
	private JTextField emailTextField;
	private JPasswordField passwordTextField;
	
    private Controller mainController;
	
	public FrameDiRegistrazione(Controller controller) {
		mainController = controller;
		
		this.impostaSettingsPerFrame();
		this.impostaPanelCentrale();
	}
	
	private void impostaSettingsPerFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Registrati ora!");
		this.setSize(800, 800);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		//contentPane.setBackground(new Color(198, 210, 222));
		contentPane.setBackground(Color.gray);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
	}
	
	private void impostaPanelCentrale() {
		panelCentrale = new JPanel();
		panelCentrale.setLayout(new BoxLayout(panelCentrale, BoxLayout.Y_AXIS));
		panelCentrale.setBackground(Color.white);
		
		this.aggiungiLogoCentrale();
		this.aggiungiMottoSottoAlLogo();
		
		panelCentrale.add(Box.createRigidArea(new Dimension(20, 20)));
		
		this.aggiungiTextFields();
		
		contentPane.add(panelCentrale);
	}
	
	private void aggiungiLogoCentrale() {
		ImageIcon logo = new ImageIcon("images/logo_uninaswap.png");
		Image resizedLogo = logo.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon logoScalato = new ImageIcon(resizedLogo);
		
		JLabel lblLogo = new JLabel();
		lblLogo.setIcon(logoScalato);
		lblLogo.setAlignmentX(CENTER_ALIGNMENT);
		
		panelCentrale.add(lblLogo);
	}
	
	private void aggiungiMottoSottoAlLogo() {
		JPanel panelNomeEMotto = new JPanel();
		panelNomeEMotto.setLayout(new BoxLayout(panelNomeEMotto, BoxLayout.Y_AXIS));
		panelNomeEMotto.setBackground(Color.white);
		
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
		
		panelNomeEMotto.add(nomeApplicativo);
		panelNomeEMotto.add(mottoApplicativo);
		
		panelNomeEMotto.setAlignmentX(CENTER_ALIGNMENT);
		
		panelCentrale.add(panelNomeEMotto);
	}
	
	private void aggiungiTextFields() {
		JPanel panelUsername = new JPanel();
		usernameTextField = new JTextField();
		this.aggiungiTextField(panelUsername, usernameTextField, "Username");

		JPanel panelEmail = new JPanel();
		emailTextField = new JTextField();
		this.aggiungiTextField(panelEmail, emailTextField, "Inserisci la tua email istituzionale");
		
		JPanel panelPassword = new JPanel();
		passwordTextField = new JPasswordField();
		this.aggiungiTextField(panelPassword, passwordTextField, "Inserisci la tua password istituzionale");
		
		JPanel panelResidenza = new JPanel();
		
	}
	
	private void aggiungiTextField(JPanel panelInput, JTextField textFieldInput, String stringaPerLabel) {
		panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
		panelInput.setBackground(Color.white);
		
		labelInput.setText(stringaPerLabel);
		labelInput.setForeground(Color.black);
		labelInput.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		labelInput.setAlignmentX(CENTER_ALIGNMENT);
		
		textFieldInput.setMaximumSize(new Dimension(300, 30));
		textFieldInput.setAlignmentX(CENTER_ALIGNMENT);
		
		panelInput.add(labelInput);
		panelInput.add(textFieldInput);
	}
}
