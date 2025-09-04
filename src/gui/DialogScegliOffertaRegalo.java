package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;

import controller.Controller;
import dto.Annuncio;
import dto.Offerta;
import dto.OffertaAcquisto;
import dto.OffertaRegalo;
import dto.OffertaScambio;
import eccezioni.OffertaAcquistoException;
import eccezioni.OffertaException;
import eccezioni.OffertaRegaloException;
import eccezioni.OffertaScambioException;
import utilities.MyJButton;
import utilities.MyJDialog;
import utilities.MyJLabel;
import utilities.MyJPanel;
import gui.PanelHomePageAnnunci;

public class DialogScegliOffertaRegalo extends MyJDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPane = new JPanel();

	private Controller mainController;
	
	public DialogScegliOffertaRegalo(Controller controller, JFrame framePadre, Annuncio annuncio) {
		mainController = controller;
		
		this.settaContentPane(framePadre, annuncio);
	}

	private void settaContentPane(JFrame framePadre, Annuncio annuncio) {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(600, 300);
		this.setLocationRelativeTo(framePadre);
		this.setResizable(false);
		
		MyJPanel contentPane = new MyJPanel();
		contentPane.setLayout(new BorderLayout());
		
		contentPane.add(this.creaPanelMessaggio(annuncio.getUtenteProprietario().getUsername()), BorderLayout.CENTER);
		contentPane.add(this.creaPanelInferiore(annuncio), BorderLayout.SOUTH);
		
		this.setContentPane(contentPane);
		this.setResizable(false);
		this.setLocationRelativeTo(framePadre);
	}
	
	private MyJPanel creaPanelMessaggio(String utenteProprietario) {
		MyJPanel panelMessaggio = new MyJPanel();
		panelMessaggio.setLayout(new BoxLayout(panelMessaggio, BoxLayout.Y_AXIS));
		panelMessaggio.setAlignmentX(CENTER_ALIGNMENT);
		panelMessaggio.setBackground(Color.white);

		MyJLabel lblNomeUtente = new MyJLabel(utenteProprietario, new Font("Ubuntu Sans", Font.BOLD, 16), Color.red);
		lblNomeUtente.setAlignmentX(CENTER_ALIGNMENT);
		MyJLabel lblMessaggio1 = new MyJLabel(" vuole regalare questo articolo!", new Font("Ubuntu Sans", Font.BOLD, 16));
		lblMessaggio1.setAlignmentX(CENTER_ALIGNMENT);
		MyJPanel panelWrapper = new MyJPanel();
		panelWrapper.setBackground(Color.white);
		panelWrapper.add(lblNomeUtente);
		panelWrapper.add(lblMessaggio1);
		
		MyJLabel lblMessaggio2 = new MyJLabel("Scegli tu se accettare il regalo o fare comunque un'offerta!", new Font("Ubuntu Sans", Font.BOLD, 16));
		lblMessaggio2.setAlignmentX(CENTER_ALIGNMENT);
		MyJLabel gifIcon = new MyJLabel();
		gifIcon.setIcon(new ImageIcon("images/gifIconaRegalo.gif"));
		gifIcon.setAlignmentX(CENTER_ALIGNMENT);
		
		panelMessaggio.add(Box.createVerticalGlue());
		panelMessaggio.add(gifIcon);
		panelMessaggio.add(Box.createVerticalGlue());
		panelMessaggio.add(panelWrapper);
		panelMessaggio.add(lblMessaggio2);
		panelMessaggio.add(Box.createVerticalGlue());
		
		return panelMessaggio;
	}
	
	private MyJPanel creaPanelInferiore(Annuncio annuncio) {
		MyJPanel panelInferiore = new MyJPanel();
		panelInferiore.setLayout(new BoxLayout(panelInferiore, BoxLayout.Y_AXIS));
		panelInferiore.setBackground(MyJPanel.uninaLightColor);
		panelInferiore.setPreferredSize(new Dimension(600, 80));
		panelInferiore.setMaximumSize(new Dimension(600, 80));
		
		MyJPanel panelBottoniOfferte = new MyJPanel();
		panelBottoniOfferte.setBackground(MyJPanel.uninaLightColor);
		panelBottoniOfferte.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		MyJButton bottoneAccettaRegalo = new MyJButton("Accetta regalo");
		bottoneAccettaRegalo.setFocusable(false);
		bottoneAccettaRegalo.setDefaultAction(() -> {
			try {
				mainController.getUtenteLoggato().checkOffertaGiaEsistentePerUtente(annuncio.getIdAnnuncio());
				mainController.passaADialogOffertaRegalo(annuncio);
			}
			catch(OffertaException exc) {
				JOptionPane.showMessageDialog(this, exc.getMessage());
			}
		});
		
		MyJButton bottoneFaiOffertaScambio = new MyJButton("Fai un'offerta di scambio");
		bottoneFaiOffertaScambio.setFocusable(false);
		bottoneFaiOffertaScambio.setDefaultAction(() -> {
			try {
				mainController.getUtenteLoggato().checkOffertaGiaEsistentePerUtente(annuncio.getIdAnnuncio());
				mainController.passaADialogOffertaScambio(annuncio);
			}
			catch(OffertaException exc) {
				JOptionPane.showMessageDialog(this, exc.getMessage());
			}
		});
		
		MyJButton bottoneFaiOffertaAcquisto = new MyJButton("Fai un'offerta di acquisto");
		bottoneFaiOffertaAcquisto.setFocusable(false);
		bottoneFaiOffertaAcquisto.setDefaultAction(() -> {			
			try {
				mainController.getUtenteLoggato().checkOffertaGiaEsistentePerUtente(annuncio.getIdAnnuncio());
				mainController.passaADialogOffertaAcquisto(annuncio);
			}
			catch(OffertaException exc) {
				JOptionPane.showMessageDialog(this, exc.getMessage());
			}
		});
		
		panelBottoniOfferte.add(bottoneFaiOffertaScambio);
		panelBottoniOfferte.add(bottoneAccettaRegalo);
		panelBottoniOfferte.add(bottoneFaiOffertaAcquisto);
		panelBottoniOfferte.setAlignmentX(CENTER_ALIGNMENT);

		MyJButton bottoneTornaIndietro = new MyJButton("Torna indietro");
		bottoneTornaIndietro.setFocusable(false);
		bottoneTornaIndietro.setAlignmentX(CENTER_ALIGNMENT);
		bottoneTornaIndietro.setDefaultAction(() -> {
			mainController.passaAFrameHomePage(this);
		});
		

		panelInferiore.add(Box.createVerticalGlue());
		panelInferiore.add(panelBottoniOfferte);
		panelInferiore.add(Box.createVerticalGlue());
		panelInferiore.add(bottoneTornaIndietro);
		panelInferiore.add(Box.createVerticalGlue());
		
		return panelInferiore;
	}
	
}
