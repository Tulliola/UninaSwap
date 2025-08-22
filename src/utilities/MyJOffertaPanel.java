package utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.Offerta;

public abstract class MyJOffertaPanel extends MyJPanel {

	private static final long serialVersionUID = 1L;
	
	protected Controller mainController;
	private int larghezza = 800;
	private int distanzaDalBordo = 10;
	private int altezza = 600;
	
	public MyJOffertaPanel(Offerta offertaToAdd, Controller controller) {
		mainController = controller;

		this.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.add(this.creaPanelOfferta(offertaToAdd));
	}

	protected MyJPanel creaPanelOfferta(Offerta offertaToAdd) {
		MyJPanel offertaPanel = new MyJPanel();
		offertaPanel.setLayout(new BorderLayout());
		offertaPanel.setPreferredSize(new Dimension(larghezza, altezza));
		offertaPanel.setMaximumSize(new Dimension(larghezza, altezza));
		offertaPanel.setBorder(BorderFactory.createLineBorder(MyJPanel.uninaColorClicked, 2));

		offertaPanel.add(this.creaPanelSuperiore(offertaToAdd), BorderLayout.NORTH);
		offertaPanel.add(this.creaPanelInfoOfferta(offertaToAdd), BorderLayout.CENTER);
		
		offertaPanel.add(this.creaPanelAccettaRifiuta(offertaToAdd), BorderLayout.SOUTH);

		
		return offertaPanel;

	}


	private MyJPanel creaPanelInfoOfferta(Offerta offertaToAdd) {
		MyJPanel panelInfoOfferta = new MyJPanel();
		panelInfoOfferta.setLayout(new BoxLayout(panelInfoOfferta, BoxLayout.Y_AXIS));
		
		if(offertaToAdd.getMessaggioMotivazionale() != null) {
			JTextArea messaggioMotivazionaleTextArea = new JTextArea(offertaToAdd.getMessaggioMotivazionale());
			messaggioMotivazionaleTextArea.setBackground(new Color(98, 145, 188));
			messaggioMotivazionaleTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
			messaggioMotivazionaleTextArea.setPreferredSize(new Dimension(larghezza - distanzaDalBordo, 100));
			messaggioMotivazionaleTextArea.setMaximumSize(new Dimension(larghezza - distanzaDalBordo, 100));
			messaggioMotivazionaleTextArea.setEditable(false);
			messaggioMotivazionaleTextArea.setEnabled(false);
			messaggioMotivazionaleTextArea.setDisabledTextColor(Color.black);
			messaggioMotivazionaleTextArea.setOpaque(true);
			messaggioMotivazionaleTextArea.setLineWrap(true);
			messaggioMotivazionaleTextArea.setWrapStyleWord(true);
			messaggioMotivazionaleTextArea.setAlignmentX(LEFT_ALIGNMENT);
			
			messaggioMotivazionaleTextArea.setFont(messaggioMotivazionaleTextArea.getFont().deriveFont(Font.ITALIC));
		}
		
		panelInfoOfferta.add(creaPanelModalitaConsegnaScelta(offertaToAdd));
		panelInfoOfferta.add(creaPanelNota(offertaToAdd));
		
		
		return panelInfoOfferta;
	}


	private MyJPanel creaPanelModalitaConsegnaScelta(Offerta offertaToAdd) {
		MyJPanel panelWrapper = new MyJPanel();
		panelWrapper.setLayout(new BoxLayout(panelWrapper, BoxLayout.Y_AXIS));
		panelWrapper.setPreferredSize(new Dimension(larghezza-distanzaDalBordo, 120));
		panelWrapper.setMaximumSize(new Dimension(larghezza-distanzaDalBordo, 120));
		
		MyJPanel panelModalitaConsegnaScelta = new MyJPanel();
		panelModalitaConsegnaScelta.setBackground(Color.red);
		panelModalitaConsegnaScelta.setLayout(new BoxLayout(panelModalitaConsegnaScelta, BoxLayout.X_AXIS));
		panelModalitaConsegnaScelta.setPreferredSize(new Dimension(larghezza-distanzaDalBordo, 50));
		panelModalitaConsegnaScelta.setMaximumSize(new Dimension(larghezza-distanzaDalBordo, 50));
		
		
		
		MyJLabel lblModalita = new MyJLabel(offertaToAdd.getUtenteProprietario().getUsername()+" ha scelto come modalità di consegna: ",
				new Font("Ubuntu Sans", Font.PLAIN, 14));
		
			
		
		
		MyJPanel panelLuogoRitiro = new MyJPanel();
		panelLuogoRitiro.setBackground(Color.yellow);
		panelLuogoRitiro.setLayout(new BoxLayout(panelLuogoRitiro, BoxLayout.X_AXIS));
		panelLuogoRitiro.setPreferredSize(new Dimension(larghezza-distanzaDalBordo, 50));
		panelLuogoRitiro.setMaximumSize(new Dimension(larghezza-distanzaDalBordo, 50));
		
		JTextArea luogoTextArea = new JTextArea();
		luogoTextArea.setBackground(new Color(98, 145, 188));
		luogoTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		luogoTextArea.setPreferredSize(new Dimension(larghezza - distanzaDalBordo, 100));
		luogoTextArea.setMaximumSize(new Dimension(larghezza - distanzaDalBordo, 100));
		luogoTextArea.setEditable(false);
		luogoTextArea.setEnabled(false);
		luogoTextArea.setDisabledTextColor(Color.black);
		luogoTextArea.setOpaque(true);
		luogoTextArea.setLineWrap(true);
		luogoTextArea.setWrapStyleWord(true);
		luogoTextArea.setAlignmentX(LEFT_ALIGNMENT);
		
		if(offertaToAdd.getModalitaConsegnaScelta().equals("Spedizione")) {
			lblModalita.aggiungiImmagineScalata("images/iconaSpedizione.png", 25, 25, false);
			luogoTextArea.setText("Spedizione presso: "+offertaToAdd.getIndirizzoSpedizione());
		}
		else if(offertaToAdd.getModalitaConsegnaScelta().equals("Incontro")) {
			lblModalita.aggiungiImmagineScalata("images/iconaIncontro.png", 25, 25, false);
			luogoTextArea.setText("Incontro presso: "+offertaToAdd.getSedeDIncontroScelta().getNome()+" alle "+offertaToAdd.getOraInizioIncontro()
			+"-"+offertaToAdd.getOraFineIncontro()+" il "+offertaToAdd.getGiornoIncontro());
		}
		else {
			lblModalita.aggiungiImmagineScalata("images/iconaRitiroInPosta.png", 25, 25, false);
			luogoTextArea.setText("Ritiro presso: "+offertaToAdd.getUfficioRitiro().getNome());
		}
		
		lblModalita.setHorizontalTextPosition(SwingConstants.LEFT);
		panelModalitaConsegnaScelta.add(lblModalita);

		panelLuogoRitiro.add(luogoTextArea);
		
		panelWrapper.add(panelModalitaConsegnaScelta);
		panelWrapper.add(panelLuogoRitiro);
		return panelWrapper;
	}

	private MyJPanel creaPanelNota(Offerta offertaToAdd) {
		String nota = offertaToAdd.getNota();
		
		MyJPanel panelNota = new MyJPanel();
		panelNota.setLayout(new BoxLayout(panelNota, BoxLayout.X_AXIS));
		panelNota.setPreferredSize(new Dimension(larghezza - distanzaDalBordo, 100));
		panelNota.setMaximumSize(new Dimension(larghezza - distanzaDalBordo, 100));
		
		
		JTextArea notaTextArea = new JTextArea();
		notaTextArea.setText("L'utente non ha niente da specificare");
		notaTextArea.setBackground(new Color(98, 145, 188));
		notaTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		notaTextArea.setPreferredSize(new Dimension(larghezza - distanzaDalBordo, 100));
		notaTextArea.setMaximumSize(new Dimension(larghezza - distanzaDalBordo, 100));
		notaTextArea.setEditable(false);
		notaTextArea.setEnabled(false);
		notaTextArea.setDisabledTextColor(Color.black);;
		notaTextArea.setOpaque(true);
		notaTextArea.setLineWrap(true);
		notaTextArea.setWrapStyleWord(true);
		notaTextArea.setAlignmentX(LEFT_ALIGNMENT);
		notaTextArea.setFont(new Font("Ubuntu Sans", Font.BOLD, 18));
		if(nota != null)
			notaTextArea.setText(nota);
		
		
		panelNota.add(notaTextArea);
		return panelNota;
	}
	
	private MyJPanel creaPanelAccettaRifiuta(Offerta offertaToAdd) {
		MyJPanel panelAccettaRifiuta = new MyJPanel();
		panelAccettaRifiuta.setLayout(new BoxLayout(panelAccettaRifiuta, BoxLayout.X_AXIS));
		panelAccettaRifiuta.setPreferredSize(new Dimension(larghezza, 100));
		panelAccettaRifiuta.setMaximumSize(new Dimension(larghezza, 100));
		
		MyJButton rifiutaButton = new MyJButton("Rifiuta");
//		rifiutaButton.setBackground(Color.red);
		rifiutaButton.setDefaultAction(() -> settaAzioneRifiutaButton());
		rifiutaButton.setUpAction(()->{});
		rifiutaButton.setDownAction(()->{});
		
		MyJButton accettaButton = new MyJButton("Accetta");
//		accettaButton.setBackground(Color.green);
		accettaButton.setDefaultAction(() -> settaAzioneAccettaButton());
		accettaButton.setUpAction(()->{});
		accettaButton.setDownAction(()->{});
		
		panelAccettaRifiuta.add(Box.createHorizontalGlue());
		panelAccettaRifiuta.add(rifiutaButton);
		panelAccettaRifiuta.add(Box.createHorizontalGlue());
		panelAccettaRifiuta.add(accettaButton);
		panelAccettaRifiuta.add(Box.createHorizontalGlue());
		
		return panelAccettaRifiuta;
	}
	
	private MyJPanel creaPanelSuperiore(Offerta offertaToAdd) {
		MyJPanel panelSuperiore = new MyJPanel();
		panelSuperiore.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelSuperiore.setBackground(uninaColorClicked);
		panelSuperiore.setPreferredSize(new Dimension(larghezza, 75));
		panelSuperiore.setMaximumSize(new Dimension(larghezza, 75));
		
		MyJLabel lblOfferente = new MyJLabel("Offerta di "+offertaToAdd.getUtenteProprietario().getUsername());
		lblOfferente.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		lblOfferente.setForeground(Color.white);
		
		panelSuperiore.add(lblOfferente);
		return panelSuperiore;
	}

	public abstract void settaAzioneRifiutaButton();
	
	public abstract void settaAzioneAccettaButton();
}
