package gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.*;

import controller.Controller;

//Eccezioni
import eccezioni.UsernameException;
import eccezioni.EmailException;
import eccezioni.PasswordException;
import eccezioni.ResidenzaException;
import eccezioni.MatricolaNonTrovataException;

public class FrameDiRegistrazione extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//Panels
	private JPanel contentPane;
	private JPanel panelInserimentoDati;
	private JPanel panelTextELabel;

	//Buttons
	private JButton bottoneDiRegistrazione;
	private JButton bottoneTornaALogin;

	//Textfields
	private JTextField usernameTextField;
	private JTextField emailTextField;
	private JTextField residenzaTextField;
	private JPasswordField passwordTextField;
	
	//Label di errore
	private JLabel lblErroreUsername = new JLabel();
	private JLabel lblErroreEmail = new JLabel();
	private JLabel lblErrorePassword = new JLabel();
	private JLabel lblErroreResidenza = new JLabel();
	private JLabel lblErroreDalDB =  new JLabel();

	
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
		panelInserimentoDati.setBorder(BorderFactory.createLineBorder(Color.black, 2, false));
		panelInserimentoDati.setPreferredSize(new Dimension(400, 430));
		panelInserimentoDati.setMaximumSize(new Dimension(400, 430));
		panelInserimentoDati.setBackground(new Color(198, 210, 222));	
		
		//Crea spazio tra il motto e i campi di testo
		contentPane.add(Box.createRigidArea(new Dimension(0, 20)));
		
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 20)));
		
		this.aggiungiTextFields();
		
		//Crea spazio tra i textfields e il bottone
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 20)));
		
		lblErroreDalDB = new JLabel();
		lblErroreDalDB.setForeground(Color.red);
		lblErroreDalDB.setVisible(false);
		lblErroreDalDB.setAlignmentX(CENTER_ALIGNMENT);
		
		panelInserimentoDati.add(lblErroreDalDB);
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 5)));
		
		this.aggiungiBottoneDiRegistrazione();
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 10)));
		this.aggiungiBottoneTornaAlLogin();
	
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
		panelTextELabel = new JPanel();
		panelTextELabel.setLayout(new BoxLayout(panelTextELabel, BoxLayout.Y_AXIS));
		panelTextELabel.setOpaque(false);
		
		usernameTextField = new JTextField();
		this.aggiungiTextField(panelTextELabel, usernameTextField, lblErroreUsername, "Inserisci il tuo username");

		emailTextField = new JTextField();
		this.aggiungiTextField(panelTextELabel, emailTextField, lblErroreEmail, "Inserisci la tua email istituzionale");
		
		passwordTextField = new JPasswordField();
		this.aggiungiTextField(panelTextELabel, passwordTextField, lblErrorePassword, "Inserisci la tua password istituzionale");
	
		residenzaTextField = new JTextField();
		this.aggiungiTextField(panelTextELabel, residenzaTextField, lblErroreResidenza, "Inserisci la tua residenza");
		
		panelTextELabel.setAlignmentX(CENTER_ALIGNMENT);
		
		panelInserimentoDati.add(panelTextELabel);
	}
	
	private void aggiungiTextField(JPanel panelInput, JTextField textFieldInput, JLabel labelDiErrore, String stringaPerLabel) {
		JLabel label = new JLabel();
		label.setText(stringaPerLabel);
		label.setForeground(Color.black);
		label.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		label.setAlignmentX(LEFT_ALIGNMENT);
		
		textFieldInput.setMaximumSize(new Dimension(300, 30));
		textFieldInput.setFont(new Font("Ubuntu Sans", Font.PLAIN, 13));
		textFieldInput.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		textFieldInput.setAlignmentX(LEFT_ALIGNMENT);
		Border blackBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		Border spacedBorder = new EmptyBorder(0, 5, 0, 0);
		textFieldInput.setBorder(new CompoundBorder(blackBorder, spacedBorder));

		labelDiErrore.setForeground(Color.red); 
		labelDiErrore.setVisible(false);
		
		panelInput.add(label);
		panelInput.add(textFieldInput);
		panelInput.add(labelDiErrore);
		
		panelInput.add(Box.createRigidArea(new Dimension(0, 15)));
	}
	
	private void aggiungiBottoneDiRegistrazione() {
		bottoneDiRegistrazione = new JButton();
		this.impostaSettingsBottone(bottoneDiRegistrazione, "Conferma registrazione");
		
		//Logica del bottone
		bottoneDiRegistrazione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nascondiLabelErrore();
				resettaBordiTextField();
				clickConfermaRegistrazione();
			}
		});
		
		panelInserimentoDati.add(bottoneDiRegistrazione);
	}
	
	private void aggiungiBottoneTornaAlLogin() {
		bottoneTornaALogin = new JButton();
		this.impostaSettingsBottone(bottoneTornaALogin, "Torna al login");
		
		//Logica del bottone
		bottoneTornaALogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainController.tornaALogin();
			}

		});
		
		panelInserimentoDati.add(bottoneTornaALogin);
	}
	
	private void impostaSettingsBottone(JButton bottoneIn, String testoBottone) {

		bottoneIn.setText(testoBottone);
		bottoneIn.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		bottoneIn.setBackground(new Color(65, 106, 144));
		bottoneIn.setForeground(Color.white);
		bottoneIn.setFocusable(false);
		bottoneIn.setAlignmentX(CENTER_ALIGNMENT);
	}

	private void clickConfermaRegistrazione() {
		try {
			checkDatiRegistrazione();
			mainController.onConfermaRegistrazioneButtonClicked(usernameTextField.getText(), emailTextField.getText(), passwordTextField.getText(), residenzaTextField.getText());
		}
		catch(UsernameException exc1) {
			settaLabelETextFieldDiErrore(lblErroreUsername, exc1.getMessage(), usernameTextField);
		}
		catch(EmailException exc2) {
			settaLabelETextFieldDiErrore(lblErroreEmail, exc2.getMessage(), emailTextField);
		}
		catch(PasswordException exc3) {
			settaLabelETextFieldDiErrore(lblErrorePassword, exc3.getMessage(), passwordTextField);
		}
		catch(ResidenzaException exc4) {
			settaLabelETextFieldDiErrore(lblErroreResidenza, exc4.getMessage(), residenzaTextField);
		}
		catch(SQLException exc5) {
			String statoDiErrore = exc5.getSQLState();
			
			if(statoDiErrore.equals("23505")) {
				if(exc5.getMessage().contains("unic"))
					lblErroreDalDB.setText("Questo username non è disponibile.");
				else
					lblErroreDalDB.setText("Esiste già un account associato a questa email");
			}
			else if(statoDiErrore.equals("P0008")) {
				lblErroreDalDB.setText("La password o l'email sono sbagliate");
			}
			lblErroreDalDB.setVisible(true);
		}
		catch(MatricolaNonTrovataException exc6) {
			lblErroreDalDB.setText(exc6.getMessage());
			lblErroreDalDB.setVisible(true);
		}
		
	}
	private void checkDatiRegistrazione() throws UsernameException, EmailException, PasswordException, ResidenzaException{
	
		checkUsername(usernameTextField.getText());
		checkEmail(emailTextField.getText());
		checkPassword(passwordTextField.getText());
		checkResidenza(residenzaTextField.getText());
	}
	
	
	private void checkUsername(String usernameIn) {
		if(usernameIn == null || usernameIn.length() == 0)
			throw new UsernameException("Il campo username è obbligatorio.");
		
		if(usernameIn.length() > 20)
			throw new UsernameException("L'username deve essere di massimo 20 caratteri.");
		
		if(usernameIn.contains(" "))
			throw new UsernameException("L'username non deve contenere spazi vuoti.");
	}
	
	
	private void checkPassword(String passwordIn) {
		if(passwordIn == null || passwordIn.length() == 0)
			throw new PasswordException("Il campo password è obbligatorio.");
		
	}
	
	
	private void checkEmail(String emailIn) {
		if(emailIn == null || emailIn.length() == 0)
			throw new EmailException("Il campo email è obbligatorio.");
		
	}
	
	
	private void checkResidenza(String residenzaIn) {
		if(residenzaIn == null || residenzaIn.isBlank())
			throw new ResidenzaException("Il campo residenza è obbligatorio.");
		
		if(residenzaIn.startsWith(" "))
			throw new ResidenzaException("La residenza non può iniziare con uno spazio vuoto.");
		
		if(residenzaIn.endsWith(" "))
			throw new ResidenzaException("La residenza non può terminare con uno spazio vuoto.");

	}
	
	private void nascondiLabelErrore() {
		lblErroreUsername.setVisible(false);
		lblErroreEmail.setVisible(false);
		lblErrorePassword.setVisible(false);
		lblErroreResidenza.setVisible(false);
		lblErroreDalDB.setVisible(false);
	}
	
	private void resettaBordiTextField() {
		usernameTextField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		emailTextField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		passwordTextField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		residenzaTextField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
	}
	
	private void settaLabelETextFieldDiErrore(JLabel labelInput, String messaggioDiErrore, JTextField textFieldInput) {
		labelInput.setText(messaggioDiErrore);
		textFieldInput.setBorder(BorderFactory.createLineBorder(Color.red, 2));
		labelInput.setVisible(true);
	}
	
	private void vaiATextFieldVuoto() {
		if(usernameTextField.getText().length() == 0)
			usernameTextField.requestFocus();
		else if(emailTextField.getText().length() == 0)
			emailTextField.requestFocus();
		else if(passwordTextField.getText().length() == 0)
			passwordTextField.requestFocus();
		else if(residenzaTextField.getText().length() == 0)
			residenzaTextField.requestFocus();
	}
}
