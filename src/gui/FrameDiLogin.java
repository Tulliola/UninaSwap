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
import utilities.MyJPasswordField;
import utilities.MyJTextField;

public class FrameDiLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	//Panel del frame
	private JPanel contentPane;
	private JPanel bluePane;
	private JPanel fieldPane;
	private JPanel buttonsPane;
	
	//Field per l'accesso
	private JTextField emailField;
	private JTextField passwordField;
	
	//Bottoni per accedere
	private JButton accediButton = new JButton("Accedi");
	private JButton registratiButton = new JButton("Registrati");
	
	//Label di errore
	private JLabel erroreEmail = new JLabel();
	private JLabel errorePassword = new JLabel();
	private JLabel erroreComunicazioneDatabase = new JLabel();
	
	//Border dei TextField
	private Border blackBorder = new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(0, 5, 0, 0));
	private Border redBorder = new CompoundBorder(BorderFactory.createLineBorder(Color.RED, 2), new EmptyBorder(0, 5, 0, 0));
	
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
		
		JLabel password = new JLabel("Password");
		passwordField = new JPasswordField();
		
		aggiungiFieldAccesso(email, emailField, erroreEmail, passwordField, null);

		
		fieldPane.add(email);
		fieldPane.add((JTextField)emailField);
		fieldPane.add(erroreEmail);
		
		fieldPane.add(Box.createRigidArea(new Dimension(15, 15)));
		
		aggiungiFieldAccesso(password, passwordField, errorePassword, accediButton, emailField);

		
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
	
	private void aggiungiFieldAccesso(JLabel field, JTextField text, JLabel errore, JComponent nextComponent, JComponent previousComponent) {
		errore.setForeground(Color.RED);
		errore.setVisible(false);
		
		field.setForeground(Color.BLACK);
		field.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		field.setAlignmentX(LEFT_ALIGNMENT);
	
		text.setMaximumSize(new Dimension(300, 30));
		text.setFont(new Font("Ubuntu Sans", Font.PLAIN, 13));
		text.setAlignmentX(LEFT_ALIGNMENT);
		text.setBorder(blackBorder);
		text.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				nextComponent.requestFocus();
			}
			
		});
		
		text.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				//Non fa niente
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_UP) {
					if(previousComponent != null)
						previousComponent.requestFocus();
				}
				else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(nextComponent != null)
						nextComponent.requestFocus();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				//Non fa niente
			}
			
		});
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
		
		accediButton.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					nascondiErrori();
					clickAccedi(); 
				}
				else if(e.getKeyCode() == KeyEvent.VK_UP) {
					passwordField.requestFocus();
				}
				else if(e.getKeyCode() == KeyEvent.VK_DOWN)
					registratiButton.requestFocus();
			}

			@Override
			public void keyReleased(KeyEvent e) {}
			
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
		
		registratiButton.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				//Non fa nulla
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					mainController.passaAFrameDiRegistrazione();
				else if(e.getKeyCode() == KeyEvent.VK_UP)
					accediButton.requestFocus();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				//Non fa nulla
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
		
		emailField.setBorder(blackBorder);
		passwordField.setBorder(blackBorder);
	}
	
	private void aggiungiBottoniLogin(JButton button) {
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.setBackground(new Color(65, 106, 144));
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
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
			mainController.onAccessoButtonClicked(emailField.getText(), passwordField.getText());
			nascondiErrori();
			mainController.passaAHomePage();
		}
		catch(EmailException e) {
			erroreEmail.setText(e.getMessage());
			erroreEmail.setVisible(true);
			emailField.requestFocus();
			emailField.setBorder(redBorder);
		}
		catch(PasswordException e) {
			errorePassword.setText(e.getMessage());
			errorePassword.setVisible(true);
			passwordField.requestFocus();
			passwordField.setBorder(redBorder);
		}
		catch(UtenteNonTrovatoException e) {
			erroreComunicazioneDatabase.setText(e.getMessage());
			erroreComunicazioneDatabase.setVisible(true);
		}
		catch(UtentePasswordMismatchException e) {
			erroreComunicazioneDatabase.setText(e.getMessage());
			erroreComunicazioneDatabase.setVisible(true);
		}
		catch(UtenteSospesoException e) {
			try {
				System.out.println("Sto qui");
				mainController.passaADialogDiComunicataSospensione(emailField.getText());
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
				//TODO poi si vede con fulio
				System.out.println(e1.getSQLState());
				System.out.println(e1.getMessage());
				System.out.println("Sto nell'sql exc");
			}
		}
		catch(SQLException e) {
			erroreComunicazioneDatabase.setText("Errore nella comunicazione col database");
			erroreComunicazioneDatabase.setVisible(true);
		}
	}
}