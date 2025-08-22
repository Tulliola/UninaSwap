package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import controller.Controller;
import dto.Annuncio;
import dto.Offerta;
import dto.ProfiloUtente;
import eccezioni.EmailException;
import eccezioni.PasswordException;
import eccezioni.ResidenzaException;
import eccezioni.UsernameException;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJPasswordField;
import utilities.MyJTextField;
import utilities.StatoAnnuncioEnum;

public class FrameProfiloUtente extends MyJFrame {

	private static final long serialVersionUID = 1L;
	
	//Panels
	private MyJPanel contentPane;
	private JPanel panelProfilo;
	private PanelBarraLateraleSx panelLateraleSx;
	private MyJPanel panelRiepilogoInfoUtente;
	private MyJPanel panelBottoni;
	private MyJPanel panelAnnunciCard = new MyJPanel();
	private PanelVisualizzaAnnunciUtente panelAnnunciDisponibili;
	private PanelVisualizzaAnnunciUtente panelAnnunciUltimati;
	private PanelVisualizzaAnnunciUtente panelAnnunciScaduti;
	private PanelVisualizzaAnnunciUtente panelAnnunciRimossi;
	private MyJPanel panelOfferteCard = new MyJPanel();
	private PanelVisualizzaOfferteUtente panelOfferteAccettate;
	private PanelVisualizzaOfferteUtente panelOfferteRifiutate;
	private PanelVisualizzaOfferteUtente panelOfferteInAttesa;
	private PanelVisualizzaOfferteUtente panelOfferteRitirate;
	private PanelVisualizzaReport panelReport;
	
	//Buttons
	private MyJButton bottoneSalvaModifiche;
	
	//TextFields
	private MyJTextField residenzaTextField;
	private MyJTextField usernameTextField;
	private MyJTextField emailTextField;
	private MyJPasswordField passwordTextField;
	private MyJTextField cambiaPWDField;
	private MyJTextField saldoTextField;
	
	
	//Labels di errore
	MyJLabel lblErroreUsername = new MyJLabel(true);
	MyJLabel lblErrorePWD = new MyJLabel(true);
	MyJLabel lblErroreResidenza = new MyJLabel(true);
	
	//Labels generiche
	MyJLabel lblModificheEffettuate = new MyJLabel("Modifiche effettuate con successo!");
	MyJLabel lblTornaAHomePage = new MyJLabel("   Torna alla home page");
	MyJLabel lblLogout = new MyJLabel("   Logout");
	
	//RigidArea
	private Component rigidArea = Box.createRigidArea(new Dimension(0, 20));
	
	//Variabili per la manipolazione delle componenti
	boolean mostraCambiaPWDField = false;
	boolean isPasswordVisibile = false;

	private Controller mainController;

	public FrameProfiloUtente(Controller controller, String sezioneScelta, ProfiloUtente utenteLoggato) {
		mainController = controller;
		
		this.impostaSettingsPerFrame(sezioneScelta);
		this.impostaContentPane(utenteLoggato, sezioneScelta);

	}
	
	private void impostaSettingsPerFrame(String sezioneScelta) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(settaTitolo(sezioneScelta));
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		
	}
	
	private String settaTitolo(String sezioneScelta) {
		String toReturn = "Il tuo profilo";
		
		String sezione = sezioneScelta.trim();
		
		if(!sezioneScelta.contains("profilo"))
			toReturn+= " - "+sezione;
		
		return toReturn;
	}

	private void impostaContentPane(ProfiloUtente utenteLoggato, String sezioneScelta) {
		contentPane = new MyJPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.setBackground(Color.white);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		panelLateraleSx = new PanelBarraLateraleSx(contentPane, mainController, this, sezioneScelta);
		panelLateraleSx.aggiungiRigaNelPanel(lblTornaAHomePage, true, "images/iconaHomePage.png");
		panelLateraleSx.add(lblTornaAHomePage, 0);
		panelLateraleSx.aggiungiRigaNelPanel(lblLogout, true, "images/iconaLogout.png");
		panelLateraleSx.add(lblLogout);
		
		panelAnnunciCard.setLayout(new CardLayout());
		panelAnnunciDisponibili = new PanelVisualizzaAnnunciUtente(mainController, utenteLoggato.getAnnunciDisponibili(), "Qui troverai tutti i tuoi annunci ancora attivi", this);
		panelAnnunciUltimati = new PanelVisualizzaAnnunciUtente(mainController, utenteLoggato.getAnnunciUltimati(), "Qui troverai tutti i tuoi annunci che sei riuscito ad ultimare", this);
		panelAnnunciScaduti = new PanelVisualizzaAnnunciUtente(mainController, utenteLoggato.getAnnunciScaduti(), "Qui troverai tutti i tuoi annunci che sono scaduti", this);
		panelAnnunciRimossi = new PanelVisualizzaAnnunciUtente(mainController, utenteLoggato.getAnnunciRimossi(), "Qui troverai tutti i tuoi annunci rimossi da te o a causa di una sospensione passata", this);
		
		panelAnnunciCard.add(panelAnnunciDisponibili, "        Annunci disponibili");
		panelAnnunciCard.add(panelAnnunciUltimati, "        Annunci andati a buon fine");
		panelAnnunciCard.add(panelAnnunciScaduti, "        Annunci scaduti");
		panelAnnunciCard.add(panelAnnunciRimossi, "        Annunci rimossi");
		((CardLayout) panelAnnunciCard.getLayout()).show(panelAnnunciCard, sezioneScelta);
	
		panelOfferteCard.setLayout(new CardLayout());
		
		panelOfferteAccettate = new PanelVisualizzaOfferteUtente(utenteLoggato.getOfferteAccettate(), "Qui troverai tutte le tue offerte accettate", this);
		panelOfferteRifiutate = new PanelVisualizzaOfferteUtente(utenteLoggato.getOfferteRifiutate(), "Qui troverai tutte le tue offerte rifiutate", this);
		panelOfferteInAttesa = new PanelVisualizzaOfferteUtente(utenteLoggato.getOfferteInAttesa(), "Qui troverai tutte le tue offerte ancora in attesa di essere valutate", this);
		panelOfferteRitirate = new PanelVisualizzaOfferteUtente(utenteLoggato.getOfferteRitirate(), "Qui troverai tutte le tue offerte che hai ritirato", this);
		panelOfferteCard.add(panelOfferteAccettate, "        Offerte accettate");
		panelOfferteCard.add(panelOfferteRifiutate, "        Offerte rifiutate");
		panelOfferteCard.add(panelOfferteInAttesa, "        Offerte in attesa");
		panelOfferteCard.add(panelOfferteRitirate, "        Offerte ritirate");
		((CardLayout) panelOfferteCard.getLayout()).show(panelOfferteCard, sezioneScelta);
		
		panelReport = new PanelVisualizzaReport(utenteLoggato);
		
		lblTornaAHomePage.setOnMouseClickedAction(() -> 
		{
			mainController.passaAFrameHomePage(this);
		});
		
		lblLogout.setOnMouseClickedAction(() -> {
			mainController.passaADialogConfermaLogout();
		});
		
		panelLateraleSx.getLblIlMioProfilo().setOnMouseClickedAction(() -> {
			this.setTitle("Il tuo profilo");
			setClickedActions(panelLateraleSx.getLblIlMioProfilo(), null);
		});
		
		panelLateraleSx.getLblAnnunciDisponibili().setOnMouseClickedAction(() -> 
		{
			this.setTitle("Il tuo profilo - Annunci disponibili");
			setClickedActions(panelLateraleSx.getLblAnnunciDisponibili(), "        Annunci disponibili");
		});
		
		panelLateraleSx.getLblAnnunciUltimati().setOnMouseClickedAction(() -> 
		{
			this.setTitle("Il tuo profilo - Annunci ultimati");
			setClickedActions(panelLateraleSx.getLblAnnunciUltimati(), "        Annunci andati a buon fine");
		});
		
		panelLateraleSx.getLblAnnunciScaduti().setOnMouseClickedAction(() -> 
		{
			this.setTitle("Il tuo profilo - Annunci scaduti");
			setClickedActions(panelLateraleSx.getLblAnnunciScaduti(), "        Annunci scaduti");
		});
		
		panelLateraleSx.getLblAnnunciRimossi().setOnMouseClickedAction(() -> 
		{        
			this.setTitle("Il tuo profilo - Annunci rimossi");
			setClickedActions(panelLateraleSx.getLblAnnunciRimossi(), "        Annunci rimossi");
		});
		
		panelLateraleSx.getLblOfferteAccettate().setOnMouseClickedAction(() -> {
			this.setTitle("Il tuo profilo - Offerte accettate");
			setClickedActions(panelLateraleSx.getLblOfferteAccettate(),"        Offerte accettate");
		});
		
		panelLateraleSx.getLblOfferteRifiutate().setOnMouseClickedAction(() -> {
			this.setTitle("Il tuo profilo - Offerte rifiutate");
			setClickedActions(panelLateraleSx.getLblOfferteRifiutate(),"        Offerte rifiutate");
		});
		
		panelLateraleSx.getLblOfferteInAttesa().setOnMouseClickedAction(() -> {
			this.setTitle("Il tuo profilo - Offerte in attesa");
			setClickedActions(panelLateraleSx.getLblOfferteInAttesa(),"        Offerte in attesa");
		});
		
		panelLateraleSx.getLblOfferteRitirate().setOnMouseClickedAction(() -> {
			this.setTitle("Il tuo profilo - Offerte ritirate");
			setClickedActions(panelLateraleSx.getLblOfferteRitirate(),"        Offerte ritirate");
		});
		
		panelLateraleSx.getLblReportOfferte().setOnMouseClickedAction(() -> {
			this.setTitle("Il tuo profilo - Report offerte");
			setClickedActions(panelLateraleSx.getLblReportOfferte(), "        Report");
		});
		
		panelProfilo = new JPanel();
		panelProfilo.setPreferredSize(new Dimension(600, this.getHeight()));
		panelProfilo.setBackground(Color.white);
		
		panelProfilo.setMaximumSize(new Dimension(600, this.getHeight()));
		panelProfilo.setLayout(new BoxLayout(panelProfilo, BoxLayout.Y_AXIS));
		panelProfilo.setBorder(new EmptyBorder(20, 0, 0, 0));
		impostaPanelProfilo(utenteLoggato);
		
		panelProfilo.setAlignmentX(CENTER_ALIGNMENT);
		panelProfilo.setAlignmentY(CENTER_ALIGNMENT);

		if(sezioneScelta.equals("   Il mio profilo"))
			contentPane.add(panelProfilo, BorderLayout.CENTER);
		else if(sezioneScelta.startsWith("        Annunci"))
			contentPane.add(panelAnnunciCard, BorderLayout.CENTER);
		else if(sezioneScelta.startsWith("        Offerte"))
			contentPane.add(panelOfferteCard, BorderLayout.CENTER);
		else if(sezioneScelta.startsWith(panelLateraleSx.getLblReportOfferte().getText())) {
			contentPane.add(panelReport, BorderLayout.CENTER);
		}
		
		contentPane.add(panelLateraleSx, BorderLayout.WEST);
		
		this.setContentPane(contentPane);
	}
	
	private void setClickedActions(MyJLabel label, String sezioneCard) {
		contentPane.removeAll();
		contentPane.revalidate();
		contentPane.repaint();
		label.setBackground(MyJPanel.uninaColor);
		panelLateraleSx.setSelectedLabel(label);
		panelLateraleSx.resettaFocusLabelsNonCliccate();
		contentPane.add(panelLateraleSx, BorderLayout.WEST);
		if(sezioneCard == null) {
			contentPane.add(panelProfilo, BorderLayout.CENTER);
		}
		else if(sezioneCard.startsWith("        Annunci")) {
			contentPane.add(panelAnnunciCard, BorderLayout.CENTER);
			((CardLayout) panelAnnunciCard.getLayout()).show(panelAnnunciCard, sezioneCard);
		}
		else if(sezioneCard.startsWith("        Offerte")) {
			contentPane.add(panelOfferteCard, BorderLayout.CENTER);
			((CardLayout) panelOfferteCard.getLayout()).show(panelOfferteCard, sezioneCard);
		}
		else
			contentPane.add(panelReport, BorderLayout.CENTER);
		
	}

	private void settaBandaLaterale(JPanel bandaLaterale) {
		bandaLaterale.setPreferredSize(new Dimension(30, contentPane.getHeight()));
		bandaLaterale.setBackground(new Color(198, 210, 222));
	}
	
	private void impostaPanelProfilo(ProfiloUtente utenteLoggato) {		
		this.aggiungiImmagineProfilo(utenteLoggato.getImmagineProfilo());
		
		panelProfilo.add(Box.createRigidArea(new Dimension(0, 10)));
		
		this.aggiungiOpzioneCambiaImmagine();
		
		panelProfilo.add(Box.createRigidArea(new Dimension(0, 25)));
		
		this.aggiungiPanelRiepilogoInformazioni(utenteLoggato);
		
		panelProfilo.add(Box.createRigidArea(new Dimension(0, 20)));

		this.aggiungiPanelBottoni(utenteLoggato);
	}
	
	private void aggiungiImmagineProfilo(byte[] immagineProfilo) {
		ImageIcon bioPic = new ImageIcon(immagineProfilo);
		Image resizedBioPic = bioPic.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon bioPicScalata = new ImageIcon(resizedBioPic);
		
		JLabel lblBioPic = new JLabel();
		lblBioPic.setIcon(bioPicScalata);
		lblBioPic.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		lblBioPic.setAlignmentX(CENTER_ALIGNMENT);
		lblBioPic.setAlignmentY(CENTER_ALIGNMENT);
		
		panelProfilo.add(lblBioPic);
	}
	
	private void aggiungiOpzioneCambiaImmagine() {
		
		MyJLabel lblCambiaImmagine = new MyJLabel("CAMBIA IMMAGINE DI PROFILO");

		lblCambiaImmagine.setAlignmentX(CENTER_ALIGNMENT);
		
		lblCambiaImmagine.aggiungiEffettoCliccabilitaPerTesto();
		lblCambiaImmagine.rendiLabelInteragibile();
		
		lblCambiaImmagine.setOnMouseClickedAction(() -> {
			if(lblModificheEffettuate.isVisible())
				lblModificheEffettuate.setVisible(false);
			
			mainController.passaAFrameCambiaImmagine();
		});
		lblCambiaImmagine.setOnMouseEnteredAction(() -> {});
		lblCambiaImmagine.setOnMouseExitedAction(() -> {});	
		panelProfilo.add(lblCambiaImmagine);
	}
	
	private void aggiungiPanelRiepilogoInformazioni(ProfiloUtente utenteLoggato) {
		rigidArea.setVisible(false);
		lblModificheEffettuate.setVisible(false);
		lblModificheEffettuate.setForeground(new Color(76, 175, 80));
		lblModificheEffettuate.setFont(new Font("Ubuntu Sans", Font.BOLD, 12));
		lblModificheEffettuate.setAlignmentX(CENTER_ALIGNMENT);
		
		panelRiepilogoInfoUtente = new MyJPanel();
		
		panelRiepilogoInfoUtente.setLayout(new BoxLayout(panelRiepilogoInfoUtente, BoxLayout.Y_AXIS));
		panelRiepilogoInfoUtente.setAlignmentX(CENTER_ALIGNMENT);
		
		MyJLabel modificaUsername = new MyJLabel();
		modificaUsername.aggiungiImmagineScalata("images/iconModify.png", 25, 25, true);
		modificaUsername.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				usernameTextField.cambiaStatoEnabled();
				mostraBottoneSalvaModifiche();
				
				if(!usernameTextField.isEnabled()) {
					usernameTextField.setText(utenteLoggato.getUsername());
					lblErroreUsername.setVisible(false);
					usernameTextField.settaBordiTextFieldStandard();

					if(!cambiaPWDField.isVisible() && !residenzaTextField.isEnabled())
						bottoneSalvaModifiche.setVisible(false);
				}					
				
				if(lblModificheEffettuate.isVisible())
					lblModificheEffettuate.setVisible(false);
				
				usernameTextField.modificaBGColorSeEnabled(Color.LIGHT_GRAY, Color.WHITE);
			}
		});
				
		MyJLabel modificaPassword = new MyJLabel();
		modificaPassword.aggiungiImmagineScalata("images/iconModify.png", 25, 25, true);
		modificaPassword.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				cambiaPWDField.cambiaStatoVisible();
				mostraBottoneSalvaModifiche();
				
				if(!cambiaPWDField.isVisible()) {
					passwordTextField.setText(utenteLoggato.getPassword());
					lblErrorePWD.setVisible(false);
					cambiaPWDField.settaBordiTextFieldStandard();
					cambiaPWDField.setText("Inserisci la nuova password");
					
					if(!residenzaTextField.isEnabled() && !usernameTextField.isEnabled())
						bottoneSalvaModifiche.setVisible(false);

				}
				
				if(lblModificheEffettuate.isVisible())
					lblModificheEffettuate.setVisible(false);
				
				mostraCambiaPWDField = !mostraCambiaPWDField;
				mostraONascondiCambiaPWDField();
			}
		});
		
		MyJLabel modificaResidenza = new MyJLabel();
		modificaResidenza.aggiungiImmagineScalata("images/iconModify.png", 25, 25, true);
		modificaResidenza.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				residenzaTextField.cambiaStatoEnabled();
				mostraBottoneSalvaModifiche();
				
				if(!residenzaTextField.isEnabled()) {
					residenzaTextField.setText(utenteLoggato.getResidenza());
					lblErroreResidenza.setVisible(false);
					residenzaTextField.settaBordiTextFieldStandard();
					
					if(!usernameTextField.isEnabled() && !cambiaPWDField.isVisible())
						bottoneSalvaModifiche.setVisible(false);
				}
				
				if(lblModificheEffettuate.isVisible())
					lblModificheEffettuate.setVisible(false);
				
				residenzaTextField.modificaBGColorSeEnabled(Color.LIGHT_GRAY, Color.white);
			}
		});
		emailTextField = new MyJTextField(String.valueOf(utenteLoggato.getEmail()));
		emailTextField.setAlignmentX(LEFT_ALIGNMENT);
		emailTextField.setEnabled(false);
		MyJLabel lblEmail = new MyJLabel("La tua email istituzionale");
		lblEmail.aggiungiImmagineScalata("images/iconaEmail.png", 30, 30, false);
		lblEmail.setAlignmentX(LEFT_ALIGNMENT);
		
		usernameTextField = new MyJTextField(utenteLoggato.getUsername());
		usernameTextField.setAlignmentX(LEFT_ALIGNMENT);
		usernameTextField.setEnabled(false);
		usernameTextField.modificaBGColorSeEnabled(Color.LIGHT_GRAY, Color.white);

		saldoTextField = new MyJTextField(String.valueOf(utenteLoggato.getSaldo())+" $");
		saldoTextField.setAlignmentX(LEFT_ALIGNMENT);
		saldoTextField.setDisabledTextColor(Color.BLACK);
		saldoTextField.setEnabled(false);
		saldoTextField.setBackground(Color.LIGHT_GRAY);
		MyJLabel lblSaldo = new MyJLabel("Il tuo saldo attuale");
		lblSaldo.setAlignmentX(LEFT_ALIGNMENT);
		lblSaldo.aggiungiImmagineScalata("images/iconaPortafoglio.png", 30, 30, false);
		
		passwordTextField = new MyJPasswordField(utenteLoggato.getPassword());
		passwordTextField.setAlignmentX(LEFT_ALIGNMENT);
		passwordTextField.setEnabled(false);
		passwordTextField.modificaBGColorSeEnabled(Color.LIGHT_GRAY, Color.white);
		
		cambiaPWDField = new MyJTextField("Inserisci la nuova password");
		cambiaPWDField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent fe) {
				if(cambiaPWDField.getText().equals("Inserisci la nuova password"))
					cambiaPWDField.setText("");
			}
			
			@Override
			public void focusLost(FocusEvent fe) {
				if(cambiaPWDField.getText().isEmpty())
					cambiaPWDField.setText("Inserisci la nuova password");
			}
		});
		
		cambiaPWDField.setAlignmentX(LEFT_ALIGNMENT);
		cambiaPWDField.setForeground(Color.gray);
		cambiaPWDField.setVisible(false);
		
		residenzaTextField = new MyJTextField(utenteLoggato.getResidenza());
		residenzaTextField.setAlignmentX(LEFT_ALIGNMENT);
		residenzaTextField.setEnabled(false);
		residenzaTextField.modificaBGColorSeEnabled(Color.LIGHT_GRAY,Color.white);
		
		//Settaggio di un panel orizzontale per mettere l'icona di modifica
		MyJPanel panelUsername = new MyJPanel();
		panelUsername.setLayout(new BoxLayout(panelUsername, BoxLayout.X_AXIS));
		panelUsername.setAlignmentX(LEFT_ALIGNMENT);
		
		MyJLabel lblUsername = new MyJLabel("Il tuo username");
		lblUsername.aggiungiImmagineScalata("images/iconaNomeUtente.png", 30, 30, false);
		panelUsername.add(lblUsername);
		panelUsername.add(Box.createRigidArea(new Dimension(15, 0)));
		panelUsername.add(modificaUsername);
		
		//Settaggio di un panel orizzontale per mettere l'icona di modifica e di nascondi/mostra password
		MyJPanel panelPassword = new MyJPanel();
		panelPassword.setLayout(new BoxLayout(panelPassword, BoxLayout.X_AXIS));
		panelPassword.setAlignmentX(LEFT_ALIGNMENT);
		
		MyJLabel lblPassword = new MyJLabel("La tua password");
		lblPassword.aggiungiImmagineScalata("images/iconaPassword.png", 30, 30, false);
		panelPassword.add(lblPassword);
		panelPassword.add(Box.createRigidArea(new Dimension(15, 0)));
		panelPassword.add(modificaPassword);
		
		MyJPanel panelSaldo = new MyJPanel();
		panelSaldo.setLayout(new BoxLayout(panelSaldo, BoxLayout.X_AXIS));
		panelSaldo.setAlignmentX(LEFT_ALIGNMENT);
		panelSaldo.add(lblSaldo);
		MyJButton versaButton = new MyJButton("Versa");
		versaButton.setDefaultAction(() -> {
			mainController.passaADialogVersamento();
		});
		MyJButton cashoutButton = new MyJButton("Cashout");
		cashoutButton.setDefaultAction(()->{
			mainController.passaADialogCashout();
		});
		
		MyJPanel panelVersaCashout = new MyJPanel();
		panelVersaCashout.setLayout(new BoxLayout(panelVersaCashout, BoxLayout.X_AXIS));
		panelVersaCashout.setAlignmentX(LEFT_ALIGNMENT);
		panelVersaCashout.setPreferredSize(new Dimension(200, 30));
		panelVersaCashout.setMaximumSize(new Dimension(200, 30));
		panelVersaCashout.add(versaButton);
		panelVersaCashout.add(Box.createHorizontalStrut(20));
		panelVersaCashout.add(cashoutButton);
		
//		ImageIcon showPWDIcon = new ImageIcon("images/iconShowPWD.png");
//		ImageIcon hidePWDIcon = new ImageIcon("images/iconHidePWD.png");
		
		Image resizedShowPWD = new ImageIcon("images/iconShowPWD.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		Image resizedHidePWD = new ImageIcon("images/iconHidePWD.png").getImage().getScaledInstance(25,  25, Image.SCALE_SMOOTH);
		
		ImageIcon showPWDIcon = new ImageIcon(resizedShowPWD);
		ImageIcon hidePWDIcon = new ImageIcon(resizedHidePWD);

		MyJLabel mostraNascondiPassword = new MyJLabel(hidePWDIcon, true);
		
		mostraNascondiPassword.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				isPasswordVisibile = !isPasswordVisibile;
				
				if(!isPasswordVisibile) {
					passwordTextField.setEchoChar('*');
					mostraNascondiPassword.setIcon(hidePWDIcon);
				}
				else {
					passwordTextField.setEchoChar((char) 0);
					mostraNascondiPassword.setIcon(showPWDIcon);
				}
			}
		});
		
		panelPassword.add(Box.createRigidArea(new Dimension(15, 0)));
		panelPassword.add(mostraNascondiPassword);
		
		
		//Settaggio di un panel orizzontale per mettere l'icona di modifica
		MyJPanel panelResidenza = new MyJPanel();
		panelResidenza.setLayout(new BoxLayout(panelResidenza, BoxLayout.X_AXIS));
		panelResidenza.setAlignmentX(LEFT_ALIGNMENT);
		
		MyJLabel lblResidenza = new MyJLabel("La tua residenza");
		lblResidenza.aggiungiImmagineScalata("images/iconaResidenza.png", 30, 30, false);
		panelResidenza.add(lblResidenza);
		panelResidenza.add(Box.createRigidArea(new Dimension(15, 0)));
		panelResidenza.add(modificaResidenza);
		
		//Organizzazione del panel
		panelRiepilogoInfoUtente.aggiungiTextFieldConLabel(emailTextField, lblEmail);
		panelRiepilogoInfoUtente.add(Box.createRigidArea(new Dimension(0, 20)));
		panelRiepilogoInfoUtente.add(panelUsername);
		panelRiepilogoInfoUtente.add(usernameTextField);
		panelRiepilogoInfoUtente.add(lblErroreUsername);
		panelRiepilogoInfoUtente.add(Box.createRigidArea(new Dimension(0, 20)));
		panelRiepilogoInfoUtente.add(panelPassword);
		panelRiepilogoInfoUtente.add(passwordTextField);
		panelRiepilogoInfoUtente.add(rigidArea);
		panelRiepilogoInfoUtente.add(cambiaPWDField);
		panelRiepilogoInfoUtente.add(lblErrorePWD);
		panelRiepilogoInfoUtente.add(Box.createRigidArea(new Dimension(0, 20)));
		panelRiepilogoInfoUtente.add(panelSaldo);
		panelRiepilogoInfoUtente.add(saldoTextField);
		panelRiepilogoInfoUtente.add(Box.createRigidArea(new Dimension(0, 20)));
		panelRiepilogoInfoUtente.add(panelVersaCashout);
//		panelRiepilogoInfoUtente.aggiungiTextFieldConLabel(saldoTextField, lblSaldo);
		panelRiepilogoInfoUtente.add(Box.createRigidArea(new Dimension(0, 20)));
		panelRiepilogoInfoUtente.add(panelResidenza);
		panelRiepilogoInfoUtente.add(residenzaTextField);
		panelRiepilogoInfoUtente.add(lblErroreResidenza);
		
		panelProfilo.add(panelRiepilogoInfoUtente);
		
		panelProfilo.add(Box.createRigidArea(new Dimension(0, 20)));
		panelProfilo.add(lblModificheEffettuate);
		
	}
	
	private void mostraBottoneSalvaModifiche() {
		if(!(bottoneSalvaModifiche.isVisible()))
			bottoneSalvaModifiche.setVisible(true);
	}
	
	private void aggiungiPanelBottoni(ProfiloUtente utenteLoggato) {
		panelBottoni = new MyJPanel();
		
		panelBottoni.setLayout(new BoxLayout(panelBottoni, BoxLayout.X_AXIS));
		panelBottoni.setAlignmentX(CENTER_ALIGNMENT);
		
		
		bottoneSalvaModifiche = new MyJButton("Salva modifiche");
		bottoneSalvaModifiche.setVisible(false);
		bottoneSalvaModifiche.setDefaultAction(() -> {
			nascondiLabelErrore(lblErroreUsername, lblErrorePWD, lblErroreResidenza);
			resettaBordiTextField(blackBorder, usernameTextField, passwordTextField, residenzaTextField);
			clickSalvaModificheButton(utenteLoggato.getUsername(), utenteLoggato.getPassword(), utenteLoggato.getResidenza(), utenteLoggato);
		});
		
		panelBottoni.add(bottoneSalvaModifiche);
		
		panelProfilo.add(panelBottoni);
	}
	
	private void clickSalvaModificheButton(String oldUsername, String oldPWD, String oldResidenza, ProfiloUtente utenteLoggato) {
		try {
			boolean almenoUnaModifica = false;
			if(usernameTextField.isEnabled()) {
				checkNewUsername(usernameTextField.getText(), oldUsername);
				mainController.onSalvaModificheButtonClickedAggiornaUsername(usernameTextField.getText());
				almenoUnaModifica = true;
				
				usernameTextField.settaBordiTextFieldStandard();
				usernameTextField.setText(utenteLoggato.getUsername());
				usernameTextField.setEnabled(false);
				usernameTextField.modificaBGColorSeEnabled(Color.LIGHT_GRAY, Color.white);
			}
			if(cambiaPWDField.isVisible()) {
				checkNewPassword(cambiaPWDField.getText(), oldPWD);
				mainController.onSalvaModificheButtonClickedAggiornaPWD(cambiaPWDField.getText());
				almenoUnaModifica = true;
				
				passwordTextField.settaBordiTextFieldStandard();
				passwordTextField.setText(utenteLoggato.getPassword());
				passwordTextField.setEnabled(false);
				mostraCambiaPWDField = !mostraCambiaPWDField;
				mostraONascondiCambiaPWDField();
				rigidArea.setVisible(false);
				cambiaPWDField.setText("Inserisci la nuova password");
			}
			if(residenzaTextField.isEnabled()) {
				checkNewResidenza(residenzaTextField.getText(), oldResidenza);
				mainController.onSalvaModificheButtonClickedAggiornaResidenza(residenzaTextField.getText());
				almenoUnaModifica = true;
				
				residenzaTextField.settaBordiTextFieldStandard();
				residenzaTextField.setText(utenteLoggato.getResidenza());
				residenzaTextField.setEnabled(false);
				residenzaTextField.modificaBGColorSeEnabled(Color.LIGHT_GRAY, Color.white);
			}
			if(almenoUnaModifica) {
				lblModificheEffettuate.setVisible(true);
				bottoneSalvaModifiche.setVisible(false);
			}
		}
		catch(UsernameException exc1) {
			lblErroreUsername.setFont(new Font("Ubuntu Sans", Font.BOLD, 10));
			this.settaLabelETextFieldDiErrore(lblErroreUsername, exc1.getMessage(), usernameTextField);
		}
		catch(PasswordException exc2) {
			lblErrorePWD.setFont(new Font("Ubuntu Sans", Font.BOLD, 10));
			this.settaLabelETextFieldDiErrore(lblErrorePWD, exc2.getMessage(), cambiaPWDField);
		}
		catch(ResidenzaException exc3) {
			lblErroreResidenza.setFont(new Font("Ubuntu Sans", Font.BOLD, 10));
			this.settaLabelETextFieldDiErrore(lblErroreResidenza, exc3.getMessage(), residenzaTextField);
		}
		catch(SQLException exc4) {
			System.out.println(exc4.getSQLState());
			System.out.println(exc4.getMessage());
			
			this.settaLabelETextFieldDiErrore(lblErroreUsername, "Questo username non è disponibile.", usernameTextField);
		}
	}
	
	public void resettaTextFieldsDopoModifiche(MyJTextField textFieldIn, String nuovoText) {
		textFieldIn.settaBordiTextFieldStandard();
		textFieldIn.setText(nuovoText);
		textFieldIn.setEnabled(false);
		textFieldIn.modificaBGColorSeEnabled(Color.LIGHT_GRAY, Color.white);
	}
	
	public void mostraONascondiCambiaPWDField() {
		cambiaPWDField.setVisible(mostraCambiaPWDField);
		rigidArea.setVisible(mostraCambiaPWDField);
	}
	
	private void checkNewUsername(String usernameIn, String oldUsername) {
		if(usernameIn == null || usernameIn.length() == 0)
			throw new UsernameException("Inserire un username.");
		
		if(usernameIn.length() > 20)
			throw new UsernameException("L'username deve essere di massimo 20 caratteri.");
		
		if(usernameIn.contains(" "))
			throw new UsernameException("L'username non deve contenere spazi vuoti.");
		
		if(usernameIn.equals(oldUsername))
			throw new UsernameException("Il nuovo username deve essere diverso da quello vecchio.");
	}
	
	
	private void checkNewPassword(String passwordIn, String oldPassword) {
		if(passwordIn == null || passwordIn.length() == 0)
			throw new PasswordException("Inserire una password.");
		
		if(passwordIn.length() < 8)
			throw new PasswordException("La nuova password deve essere di almeno 8 caratteri.");
		
		if(passwordIn.length() > 16)
			throw new PasswordException("La nuova password deve essere di al massimo 16 caratteri.");
		
		if(passwordIn.equals(oldPassword))
			throw new PasswordException("La nuova password deve essere diversa dalla vecchia.");
	}
	
	
	private void checkNewResidenza(String residenzaIn, String oldResidenza) {
		if(residenzaIn == null || residenzaIn.isBlank())
			throw new ResidenzaException("Inserire una residenza.");
		
		if(residenzaIn.startsWith(" "))
			throw new ResidenzaException("La residenza non può iniziare con uno spazio.");
		
		if(residenzaIn.endsWith(" "))
			throw new ResidenzaException("La residenza non può terminare con uno spazio.");
		
		if(residenzaIn.equals(oldResidenza))
			throw new ResidenzaException("La nuova residenza deve essere diversa dalla vecchia.");

	}
	
	
}
