package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dto.Offerta;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class FrameVisualizzaOfferte extends MyJFrame {

	private static final long serialVersionUID = 1L;
	
	private MyJPanel contentPane;
	
	public FrameVisualizzaOfferte(ArrayList<Offerta> offerte) {
		this.setSize(800, 600);
		this.setTitle("Le tue offerte");
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		settaContentPane(offerte);
	}

	private void settaContentPane(ArrayList<Offerta> offerte) {
		contentPane = new MyJPanel();
		contentPane.setBackground(MyJPanel.uninaLightColor);
		contentPane.setLayout(new BorderLayout());
		contentPane.add(aggiungiPanelOfferta(offerte.get(0)));
		contentPane.add(new JSeparator(JSeparator.HORIZONTAL));
		this.setContentPane(contentPane);
	}

	private MyJPanel aggiungiPanelOfferta(Offerta offertaToAdd) {
		MyJPanel panelOfferta = new MyJPanel();
		panelOfferta.setSize(800, 200);
		panelOfferta.setLayout(new BoxLayout(panelOfferta, BoxLayout.X_AXIS));
		panelOfferta.add(creaPanelImmagine(offertaToAdd));
		panelOfferta.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		panelOfferta.add(creaPanelInfoAnnuncio(offertaToAdd));
		panelOfferta.add(creaPanelInfoOfferta(offertaToAdd));
		panelOfferta.add(creaPanelAccettaRifiutaOfferta());
		panelOfferta.setBackground(Color.WHITE);
		return panelOfferta;
	}

	private MyJPanel creaPanelAccettaRifiutaOfferta() {
		MyJPanel panelAccettaRifiutaOfferta = new MyJPanel();
		panelAccettaRifiutaOfferta.setPreferredSize(new Dimension(150, 200));
		panelAccettaRifiutaOfferta.setMaximumSize(new Dimension(150, 200));
		panelAccettaRifiutaOfferta.setLayout(new BoxLayout(panelAccettaRifiutaOfferta, BoxLayout.Y_AXIS));
		panelAccettaRifiutaOfferta.setBackground(Color.WHITE);
		
		MyJButton accettaButton = new MyJButton("Accetta");
		accettaButton.setAlignmentX(CENTER_ALIGNMENT);
		MyJButton rifiutaButton = new MyJButton("Rifiuta");
		rifiutaButton.setBackground(Color.RED);
		rifiutaButton.setAlignmentX(CENTER_ALIGNMENT);
		
		panelAccettaRifiutaOfferta.add(accettaButton);
		panelAccettaRifiutaOfferta.add(Box.createVerticalStrut(10));
		panelAccettaRifiutaOfferta.add(rifiutaButton);
		return panelAccettaRifiutaOfferta;
	}

	private MyJPanel creaPanelInfoOfferta(Offerta offertaToAdd) {
		MyJPanel panelInfoOfferta = new MyJPanel();
		panelInfoOfferta.setSize(250, 200);
		panelInfoOfferta.setMaximumSize(new Dimension(300, 200));
		panelInfoOfferta.setLayout(new BoxLayout(panelInfoOfferta, BoxLayout.Y_AXIS));
		panelInfoOfferta.setBackground(Color.WHITE);
		panelInfoOfferta.setPreferredSize(new Dimension(300, 200));
		panelInfoOfferta.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
		
		JTextArea textNomeOfferente = new JTextArea();
		textNomeOfferente.setText("Offerta di: "+offertaToAdd.getUtenteProprietario().getUsername());
		textNomeOfferente.setFont(new Font("Ubuntu Sans", Font.BOLD, 14));
		textNomeOfferente.setFocusable(false);
		textNomeOfferente.setEditable(false);
		
		JTextArea textPrezzoOfferto = new JTextArea();
		textPrezzoOfferto.setText("Prezzo offerto: "+offertaToAdd.getPrezzoOfferto());
		textPrezzoOfferto.setFont(new Font("Ubuntu Sans", Font.BOLD, 14));
		textPrezzoOfferto.setFocusable(false);
		textPrezzoOfferto.setEditable(false);
		
		JTextArea textModConsegna = new JTextArea();
		String consegna = "Modalità di consegna scelta: ";
		if(offertaToAdd.getModalitaConsegnaScelta().equals("Spedizione")) {
			consegna += "spedizione presso "+offertaToAdd.getIndirizzoSpedizione();
		}
		else if(offertaToAdd.getModalitaConsegnaScelta().equals("Incontro")) {
			consegna += "incontro presso "+offertaToAdd.getSedeDIncontroScelta().getNome()+ " dalle "+
			offertaToAdd.getOraInizioIncontro()+" alle "+offertaToAdd.getOraFineIncontro()+" di "+offertaToAdd.getGiornoIncontro();
		}
		else {
			consegna += "ritiro presso la posta "+offertaToAdd.getUfficioRitiro().getNome();
		}
		textModConsegna.setText(consegna);
		textModConsegna.setFont(new Font("Ubuntu Sans", Font.BOLD, 14));
		textModConsegna.setFocusable(false);
		textModConsegna.setEditable(false);
		textModConsegna.setLineWrap(true);
		textModConsegna.setWrapStyleWord(true);
		
		panelInfoOfferta.add(textNomeOfferente);
		panelInfoOfferta.add(textPrezzoOfferto);
		panelInfoOfferta.add(textModConsegna);
		return panelInfoOfferta;
	}

	private MyJPanel creaPanelInfoAnnuncio(Offerta offertaToAdd) {
		MyJPanel panelInfoAnnunci = new MyJPanel();
		panelInfoAnnunci.setSize(250, 200);
		panelInfoAnnunci.setPreferredSize(new Dimension(300, 200));
		panelInfoAnnunci.setMaximumSize(new Dimension(300, 200));
		panelInfoAnnunci.setLayout(new BoxLayout(panelInfoAnnunci, BoxLayout.Y_AXIS));
		panelInfoAnnunci.setBackground(Color.WHITE);
		panelInfoAnnunci.setBorder(BorderFactory.createMatteBorder(0,  1, 0, 1, Color.BLACK));
		
		JTextArea textNomeAnnuncio = new JTextArea();
		textNomeAnnuncio.setText("Offerta al tuo annuncio "+offertaToAdd.getAnnuncioRiferito().getNome());
		textNomeAnnuncio.setFont(new Font("Ubuntu Sans", Font.BOLD, 14));
		textNomeAnnuncio.setFocusable(false);
		textNomeAnnuncio.setEditable(false);
		textNomeAnnuncio.setLineWrap(true);
		textNomeAnnuncio.setWrapStyleWord(true);
		
		JTextArea textPrezzoAnnuncio = new JTextArea();
		textPrezzoAnnuncio.setText("Prezzo iniziale: "+offertaToAdd.getAnnuncioRiferito().getPrezzoIniziale());
		textPrezzoAnnuncio.setFont(new Font("Ubuntu Sans", Font.BOLD, 14));
		textPrezzoAnnuncio.setFocusable(false);
		textPrezzoAnnuncio.setEditable(false);
		textPrezzoAnnuncio.setLineWrap(true);
		textPrezzoAnnuncio.setWrapStyleWord(true);
		
		panelInfoAnnunci.add(textNomeAnnuncio);
		panelInfoAnnunci.add(textPrezzoAnnuncio);
		return panelInfoAnnunci;
	}

	private MyJPanel creaPanelImmagine(Offerta offertaToAdd) {
		MyJPanel panelImmagine = new MyJPanel();
		panelImmagine.setSize(150, 200);
		panelImmagine.setPreferredSize(new Dimension(300, 200));
		MyJLabel lblPrimaImmagineAnnuncio = new MyJLabel();
		lblPrimaImmagineAnnuncio.aggiungiImmagineScalata(offertaToAdd.getAnnuncioRiferito().getOggettoInAnnuncio().getImmagine(0),
				150, 200, false);
		
		panelImmagine.add(lblPrimaImmagineAnnuncio);
		return panelImmagine;
	}
}
