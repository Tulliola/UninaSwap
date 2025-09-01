package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.ProfiloUtente;
import eccezioni.MotivoSegnalazioneException;
import utilities.MyJButton;
import utilities.MyJDialog;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;

public class DialogSegnalaUtente extends MyJDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPane = new JPanel();
	private Controller mainController;
	private JFrame framePadre;
	private JTextArea inserisciMotivoSegnalazione;
	private MyJLabel lblErroreSegnalazione;
	
	public DialogSegnalaUtente(Controller controller, JFrame framePadre, ProfiloUtente utenteSegnalante, ProfiloUtente utenteSegnalato) {
		mainController = controller;
		this.framePadre = framePadre;
		
		this.impostaSettingsPerDialog(utenteSegnalante, utenteSegnalato);
	}
	
	
	private void impostaSettingsPerDialog(ProfiloUtente utenteSegnalante, ProfiloUtente utenteSegnalato) {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(1000, 400);
		this.setResizable(false);
		this.setModal(true);
		this.setTitle("Segnala "+utenteSegnalato.getUsername());
		
		contentPane.setLayout(new BorderLayout());
		contentPane.setBackground(Color.white);
		
		contentPane.add(new MyJPanel(MyJPanel.uninaColorClicked, new Dimension(50, this.getHeight())), BorderLayout.WEST);
		contentPane.add(this.creaPanelCentrale(utenteSegnalante, utenteSegnalato), BorderLayout.CENTER);
		contentPane.add(new MyJPanel(MyJPanel.uninaColorClicked, new Dimension(50, this.getHeight())), BorderLayout.EAST);

		this.setContentPane(contentPane);
		this.setLocationRelativeTo(framePadre);
	}
	
	private MyJPanel creaPanelCentrale(ProfiloUtente utenteSegnalante, ProfiloUtente utenteSegnalato) {
		MyJPanel panelCentrale = new MyJPanel();
		panelCentrale.setLayout(new BoxLayout(panelCentrale, BoxLayout.Y_AXIS));
		panelCentrale.setAlignmentX(CENTER_ALIGNMENT);
		panelCentrale.setBackground(new Color(220, 220, 220));
		
		MyJLabel lblAiutaciACapire = new MyJLabel(utenteSegnalante.getUsername()+", aiutaci a capire cosa è successo e perché vuoi segnalare "+utenteSegnalato.getUsername());
		lblAiutaciACapire.setAlignmentX(CENTER_ALIGNMENT);
		
		inserisciMotivoSegnalazione = new JTextArea();
		inserisciMotivoSegnalazione.setPreferredSize(new Dimension(800, 200));
		inserisciMotivoSegnalazione.setMaximumSize(new Dimension(800, 200));
		inserisciMotivoSegnalazione.setFont(new Font("Ubuntu Sans", Font.PLAIN, 16));
		inserisciMotivoSegnalazione.setLineWrap(true);
		inserisciMotivoSegnalazione.setWrapStyleWord(true);
		inserisciMotivoSegnalazione.setAlignmentX(CENTER_ALIGNMENT);
		
		inserisciMotivoSegnalazione.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				if(inserisciMotivoSegnalazione.getText().length() > 200)
					ke.consume();
			}
		});
		
		MyJLabel iconaSegnalazione = new MyJLabel();
		iconaSegnalazione.aggiungiImmagineScalata("images/iconaSegnalazione.png", 50, 50, false);
		iconaSegnalazione.setAlignmentX(CENTER_ALIGNMENT);
		
		lblErroreSegnalazione = new MyJLabel(true);
		lblErroreSegnalazione.setAlignmentX(CENTER_ALIGNMENT);
		
		panelCentrale.add(Box.createVerticalGlue());
		panelCentrale.add(iconaSegnalazione);
		panelCentrale.add(Box.createVerticalGlue());
		panelCentrale.add(lblAiutaciACapire);
		panelCentrale.add(Box.createVerticalGlue());
		panelCentrale.add(inserisciMotivoSegnalazione);
		panelCentrale.add(lblErroreSegnalazione);
		panelCentrale.add(Box.createVerticalGlue());
		panelCentrale.add(this.creaPanelBottoni(utenteSegnalante, utenteSegnalato));
		
		return panelCentrale;
	}
	
	private MyJPanel creaPanelBottoni(ProfiloUtente utenteSegnalante, ProfiloUtente utenteSegnalato) {
		MyJPanel panelBottoni = new MyJPanel();
		panelBottoni.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelBottoni.setBackground(MyJPanel.uninaLightColor);
		
		MyJButton bottoneTornaIndietro = new MyJButton("Torna indietro");
		bottoneTornaIndietro.setDefaultAction(() -> {this.dispose();});
		bottoneTornaIndietro.setUpAction(() -> {});
		bottoneTornaIndietro.setDownAction(() -> {});
		
		MyJButton bottoneConfermaSegnalazione = new MyJButton("Conferma segnalazione");
		bottoneConfermaSegnalazione.setDefaultAction(() -> {
			try {
				this.nascondiLabelErrore(this.lblErroreSegnalazione);
				this.checkMotivoSegnalazione();
				this.mainController.onConfermaSegnalazioneButtonClicked(utenteSegnalante.getEmail(), utenteSegnalato.getEmail(), this.inserisciMotivoSegnalazione.getText());
			}
			catch(MotivoSegnalazioneException exc1) {
				this.settaLabelETextAreaDiErrore(this.lblErroreSegnalazione, exc1.getMessage(), this.inserisciMotivoSegnalazione);
			}
			catch(SQLException exc2) {
				JOptionPane.showMessageDialog(this, "Hai già segnalato questo utente.");
			}
		});
		bottoneConfermaSegnalazione.setUpAction(() -> {});
		bottoneConfermaSegnalazione.setDownAction(() -> {});
		
		panelBottoni.add(bottoneTornaIndietro);
		panelBottoni.add(bottoneConfermaSegnalazione);
		
		return panelBottoni;
	}
	
	private void checkMotivoSegnalazione() throws MotivoSegnalazioneException{
		if(this.inserisciMotivoSegnalazione == null || this.inserisciMotivoSegnalazione.getText().isBlank())
			throw new MotivoSegnalazioneException("Devi inserire una motivazione");
	}
}
