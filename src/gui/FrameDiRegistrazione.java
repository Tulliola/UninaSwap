package gui;

import java.awt.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.*;

import controller.Controller;

//Eccezioni
import eccezioni.UsernameException;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJPasswordField;
import utilities.MyJTextField;
import eccezioni.EmailException;
import eccezioni.PasswordException;
import eccezioni.ResidenzaException;
import eccezioni.MatricolaNonTrovataException;

public class FrameDiRegistrazione extends MyJFrame {

	private static final long serialVersionUID = 1L;
	
	//Panels
	private MyJPanel contentPane;
	private MyJPanel panelInserimentoDati;
	private MyJPanel panelTextELabel;

	//Buttons
	private MyJButton bottoneDiRegistrazione = new MyJButton("Conferma registrazione");
	private MyJButton bottoneTornaALogin = new MyJButton("Torna al Login");

	//Textfields
	private MyJTextField usernameTextField;
	private MyJTextField emailTextField;
	private MyJTextField residenzaTextField;
	private MyJPasswordField passwordTextField;
	
	//Label di errore
	private MyJLabel lblErroreUsername = new MyJLabel(true);
	private MyJLabel lblErroreEmail = new MyJLabel(true);
	private MyJLabel lblErrorePassword = new MyJLabel(true);
	private MyJLabel lblErroreResidenza = new MyJLabel(true);
	private MyJLabel lblErroreDalDB =  new MyJLabel(true);
	
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
	
	//Settaggio del frame di registrazione
	private void impostaSettingsPerFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Registrazione - UninaSwap");
		this.setSize(500, 800);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		contentPane = new MyJPanel(Color.WHITE);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
	}
	
	//Settaggio del panel per compilare i field
	private void impostaPanelInserimentoDati() {
		panelInserimentoDati = new MyJPanel(MyJPanel.uninaLightColor);
		panelInserimentoDati.setLayout(new BoxLayout(panelInserimentoDati, BoxLayout.Y_AXIS));
		panelInserimentoDati.setBorder(MyJPanel.blackBorder);
		panelInserimentoDati.setPreferredSize(new Dimension(400, 430));
		panelInserimentoDati.setMaximumSize(new Dimension(400, 430));	
		
		//Crea spazio tra il motto e i campi di testo
		contentPane.add(Box.createRigidArea(new Dimension(20, 20)));
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(20, 20)));
		
		this.aggiungiTextFields();
		
		//Crea spazio tra i textfields e il bottone
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 20)));
		
		lblErroreDalDB.setAlignmentX(CENTER_ALIGNMENT);
		
		panelInserimentoDati.add(lblErroreDalDB);
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 5)));
		
		this.aggiungiBottoneDiRegistrazione();
		panelInserimentoDati.add(Box.createRigidArea(new Dimension(0, 10)));
		this.aggiungiBottoneTornaAlLogin();
	
		panelInserimentoDati.setAlignmentX(CENTER_ALIGNMENT);
		
		contentPane.add(panelInserimentoDati);
	}
	
	//Aggiungo il logo dell'applicativo
	private void aggiungiLogoCentrale() {
		//Crea spazio tra il bordo superiore e il logo
		contentPane.add(Box.createRigidArea(new Dimension(0, 25)));
		
		MyJLabel lblLogo = new MyJLabel();
		lblLogo.aggiungiImmagineScalata("images/logo_uninaswap.png", 200, 200, false);
		
		lblLogo.setAlignmentX(CENTER_ALIGNMENT);
		
		contentPane.add(lblLogo);
	}
	
	//Aggiungo lo slogan dell'applicativo
	private void aggiungiMottoSottoAlLogo() {
		
		MyJLabel nomeApplicativo = new MyJLabel("UninaSwap", new Font("Ubuntu Sans", Font.BOLD, 24));
		nomeApplicativo.setForeground(MyJLabel.uninaColor);
		nomeApplicativo.setAlignmentX(CENTER_ALIGNMENT);
		
		MyJLabel mottoApplicativo = new MyJLabel("Il mercatino digitale federiciano", new Font("Ubuntu Sans", Font.ITALIC, 16));
		mottoApplicativo.setForeground(MyJLabel.uninaColor);
		mottoApplicativo.setAlignmentX(CENTER_ALIGNMENT);

		contentPane.add(nomeApplicativo);
		contentPane.add(mottoApplicativo);
	}
	
	//Aggiungo i textfield al panel dei textfield e dei bottoni
	private void aggiungiTextFields() {
		panelTextELabel = new MyJPanel();
		panelTextELabel.setLayout(new BoxLayout(panelTextELabel, BoxLayout.Y_AXIS));
		panelTextELabel.setOpaque(false);
		
		usernameTextField = new MyJTextField();
		emailTextField = new MyJTextField();
		passwordTextField = new MyJPasswordField();
		residenzaTextField = new MyJTextField();
		
		this.aggiungiTextField(panelTextELabel, usernameTextField, lblErroreUsername, "Inserisci il tuo username", emailTextField, null);
		
		this.aggiungiTextField(panelTextELabel, emailTextField, lblErroreEmail, "Inserisci la tua email istituzionale", passwordTextField, usernameTextField);
		
		
		passwordTextField.setDefaultAction(() -> {
			residenzaTextField.requestFocus();
		});
		
		passwordTextField.setDownAction(() -> {
			residenzaTextField.requestFocus();
		});
		
		passwordTextField.setUpAction(() -> {
			emailTextField.requestFocus();
		});
		
		MyJLabel lblPassword = new MyJLabel("Inserisci la tua password istituzionale");
		passwordTextField.setAlignmentX(LEFT_ALIGNMENT);
		
		panelTextELabel.add(lblPassword);
		panelTextELabel.add(passwordTextField);
		panelTextELabel.add(lblErrorePassword);
		panelTextELabel.add(Box.createRigidArea(new Dimension(0, 15)));
		
		this.aggiungiTextField(panelTextELabel, residenzaTextField, lblErroreResidenza, "Inserisci la tua residenza", bottoneDiRegistrazione, passwordTextField);
		
		panelTextELabel.setAlignmentX(CENTER_ALIGNMENT);
		
		panelInserimentoDati.add(panelTextELabel);
	}
	
	private void aggiungiTextField(MyJPanel panelInput, MyJTextField textFieldInput, MyJLabel labelDiErrore, String stringaPerLabel, JComponent nextComponent, JComponent previousComponent) {
		MyJLabel label = new MyJLabel(stringaPerLabel, new Font("Ubuntu Sans", Font.BOLD, 15));
		label.setForeground(Color.black);
		label.setAlignmentX(LEFT_ALIGNMENT);
		textFieldInput.setAlignmentX(LEFT_ALIGNMENT);
		textFieldInput.setBorder(MyJTextField.blackBorder);
		textFieldInput.rendiTextFieldActionListenable();
		textFieldInput.rendiTextFieldKeyListenable();
		textFieldInput.rendiTextFieldMouseListenable();
		
		textFieldInput.setDefaultAction(() -> {
			if(nextComponent != null) {
				nextComponent.setFocusable(true);
				nextComponent.requestFocus();
			}
		});
		
		textFieldInput.setDownAction(() -> {
			if(nextComponent != null) {
				nextComponent.setFocusable(true);
				nextComponent.requestFocus();
			}
		});
		
		textFieldInput.setUpAction(() -> {
			if(previousComponent != null) {
				previousComponent.setFocusable(true);
				previousComponent.requestFocus();
			}
		});

		textFieldInput.setKeyTypedAction(()->{});
		
		labelDiErrore.setForeground(Color.red); 
		labelDiErrore.setVisible(false);
		
		panelInput.add(label);
		panelInput.add(textFieldInput);
		panelInput.add(labelDiErrore);
		
		panelInput.add(Box.createRigidArea(new Dimension(0, 15)));
	}
	
	//Aggiungo il bottone al panel di inserimento dei textfield e dei bottoni
	private void aggiungiBottoneDiRegistrazione() {
		this.impostaSettingsBottone(bottoneDiRegistrazione);

		bottoneDiRegistrazione.setDefaultAction(() -> {
			nascondiLabelErrore(lblErroreUsername, lblErroreEmail, lblErrorePassword, lblErroreResidenza, lblErroreDalDB);
			resettaBordiTextField(blackBorder, usernameTextField, emailTextField, passwordTextField,residenzaTextField);
			clickConfermaRegistrazione();
		});
		
		bottoneDiRegistrazione.setUpAction(() -> {
			residenzaTextField.requestFocus();
		});
		
		bottoneDiRegistrazione.setDownAction(()->{
			bottoneTornaALogin.setFocusable(true);
			bottoneTornaALogin.requestFocus();
		});
		
		
		panelInserimentoDati.add(bottoneDiRegistrazione);
	}
	
	//Aggiungo il bottone al panel di inserimento dei textfield e dei bottoni
	private void aggiungiBottoneTornaAlLogin() {
		this.impostaSettingsBottone(bottoneTornaALogin);
		
		bottoneTornaALogin.setDefaultAction(() -> {
			mainController.tornaALogin();
		});
		
		bottoneTornaALogin.setUpAction(() -> {
			bottoneDiRegistrazione.setFocusable(true);
			bottoneDiRegistrazione.requestFocus();
		});
		
		bottoneTornaALogin.setDownAction(()->{});
		
		panelInserimentoDati.add(bottoneTornaALogin);
	}
	
	//Setting dei bottoni
	private void impostaSettingsBottone(JButton bottoneIn) {
		bottoneIn.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		bottoneIn.setBackground(MyJButton.uninaColor);
		bottoneIn.setForeground(Color.white);
		bottoneIn.setAlignmentX(CENTER_ALIGNMENT);
	}

	//Istruzioni da eseguire quando si clicca il bottone conferma registrazione
	@SuppressWarnings("deprecation")
	private void clickConfermaRegistrazione() {
		try {
			checkDatiRegistrazione();
			mainController.onConfermaRegistrazioneButtonClicked(usernameTextField.getText(), emailTextField.getText(), passwordTextField.getText(), residenzaTextField.getText());
		}
		catch(UsernameException exc1) {
			usernameTextField.requestFocus();
			settaLabelETextFieldDiErrore(lblErroreUsername, exc1.getMessage(), usernameTextField);
		}
		catch(EmailException exc2) {
			emailTextField.requestFocus();
			settaLabelETextFieldDiErrore(lblErroreEmail, exc2.getMessage(), emailTextField);
		}
		catch(PasswordException exc3) {
			passwordTextField.requestFocus();
			settaLabelETextFieldDiErrore(lblErrorePassword, exc3.getMessage(), passwordTextField);
		}
		catch(ResidenzaException exc4) {
			residenzaTextField.requestFocus();
			settaLabelETextFieldDiErrore(lblErroreResidenza, exc4.getMessage(), residenzaTextField);
		}
		catch(SQLException exc5) {
			String statoDiErrore = exc5.getSQLState();
			
			if(statoDiErrore.equals("23505")) {
				if(exc5.getMessage().contains("unic"))
					lblErroreDalDB.setText("Questo username non è disponibile.");
				else
					lblErroreDalDB.setText("Esiste già un account associato a questa email.");
			}
			else if(statoDiErrore.equals("P0008")) {
				lblErroreDalDB.setText("La password o l'email sono sbagliate.");
			}
			lblErroreDalDB.setVisible(true);
		}
		catch(MatricolaNonTrovataException exc6) {
			lblErroreDalDB.setText(exc6.getMessage());
			lblErroreDalDB.setVisible(true);
		}
		
	}
	
	//Controllo dei valori inseriti nei field
	@SuppressWarnings("deprecation")
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
		
		if(residenzaIn.length() > 75)
			throw new ResidenzaException("La residenza deve essere di massimo 75 caratteri.");

	}
}