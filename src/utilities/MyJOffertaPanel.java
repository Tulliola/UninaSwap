package utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Random;

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
	private static final Color[] tonalitaDiUninaLightColor = new Color[5]; 
	protected Color coloreCasualePerBG;
	protected Controller mainController;
	protected int larghezza = 800;
	protected int distanzaDalBordo = 10;
	protected int altezza = 400;
	
	static {
		tonalitaDiUninaLightColor[0] = new Color(118, 146, 175);
		tonalitaDiUninaLightColor[1] = new Color(158, 178, 198);
		tonalitaDiUninaLightColor[2] = new Color(198, 209, 221);
		tonalitaDiUninaLightColor[3] = new Color(237, 241, 245);
		tonalitaDiUninaLightColor[4] = new Color(255, 255, 255);
	}	
		
	
	public MyJOffertaPanel(Offerta offertaToAdd, Controller controller) {
		mainController = controller;

		Random generatore = new Random();
		coloreCasualePerBG = tonalitaDiUninaLightColor[generatore.nextInt(5)];

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
		offertaPanel.setBackground(coloreCasualePerBG);

		offertaPanel.add(this.creaPanelSuperiore(offertaToAdd), BorderLayout.NORTH);
		offertaPanel.add(this.creaPanelInfoOfferta(offertaToAdd), BorderLayout.CENTER);
		offertaPanel.add(this.creaPanelAccettaRifiuta(offertaToAdd), BorderLayout.SOUTH);
		
		return offertaPanel;
	}

	private MyJPanel creaPanelSuperiore(Offerta offertaToAdd) {
		MyJPanel panelSuperiore = new MyJPanel();
		panelSuperiore.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelSuperiore.setBackground(uninaColorClicked);
		panelSuperiore.setPreferredSize(new Dimension(larghezza, 35));
		panelSuperiore.setMaximumSize(new Dimension(larghezza, 35));
		
		MyJLabel lblOfferente = new MyJLabel("Offerta di "+offertaToAdd.getUtenteProprietario().getUsername());
		lblOfferente.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		lblOfferente.setForeground(Color.white);
		
		panelSuperiore.add(lblOfferente);
		return panelSuperiore;
	}


	private MyJPanel creaPanelInfoOfferta(Offerta offertaToAdd) {
		MyJPanel panelInfoOfferta = new MyJPanel();
		panelInfoOfferta.setLayout(new BoxLayout(panelInfoOfferta, BoxLayout.Y_AXIS));
		panelInfoOfferta.setBackground(coloreCasualePerBG);
		
		if(offertaToAdd.getMessaggioMotivazionale() != null) {
			JTextArea messaggioMotivazionaleTextArea = new JTextArea(offertaToAdd.getMessaggioMotivazionale());
			messaggioMotivazionaleTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
			messaggioMotivazionaleTextArea.setPreferredSize(new Dimension(larghezza - distanzaDalBordo, 100));
			messaggioMotivazionaleTextArea.setMaximumSize(new Dimension(larghezza - distanzaDalBordo, 100));
			messaggioMotivazionaleTextArea.setEditable(false);
			messaggioMotivazionaleTextArea.setEnabled(false);
			messaggioMotivazionaleTextArea.setDisabledTextColor(Color.black);
			messaggioMotivazionaleTextArea.setOpaque(false);
			messaggioMotivazionaleTextArea.setLineWrap(true);
			messaggioMotivazionaleTextArea.setWrapStyleWord(true);
			messaggioMotivazionaleTextArea.setAlignmentX(LEFT_ALIGNMENT);
			
			messaggioMotivazionaleTextArea.setFont(messaggioMotivazionaleTextArea.getFont().deriveFont(Font.ITALIC));
			panelInfoOfferta.add(messaggioMotivazionaleTextArea);
		}
		
		panelInfoOfferta.add(creaPanelModalitaConsegnaScelta(offertaToAdd));
		panelInfoOfferta.add(creaPanelNota(offertaToAdd));
		panelInfoOfferta.add(creaPanelSpecifico(offertaToAdd));

		
		return panelInfoOfferta;
	}


	private MyJPanel creaPanelModalitaConsegnaScelta(Offerta offertaToAdd) {
		MyJPanel panelWrapper = new MyJPanel();
		panelWrapper.setLayout(new BoxLayout(panelWrapper, BoxLayout.Y_AXIS));
		panelWrapper.setPreferredSize(new Dimension(larghezza-distanzaDalBordo, 120));
		panelWrapper.setMaximumSize(new Dimension(larghezza-distanzaDalBordo, 120));
		panelWrapper.setBackground(coloreCasualePerBG);
		
		MyJPanel panelModalitaConsegnaScelta = new MyJPanel();
		panelModalitaConsegnaScelta.setBackground(coloreCasualePerBG);
		panelModalitaConsegnaScelta.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelModalitaConsegnaScelta.setPreferredSize(new Dimension(larghezza-distanzaDalBordo, 50));
		panelModalitaConsegnaScelta.setMaximumSize(new Dimension(larghezza-distanzaDalBordo, 50));
		
		MyJLabel lblModalita = new MyJLabel(offertaToAdd.getUtenteProprietario().getUsername()+" ha scelto " + offertaToAdd.getModalitaConsegnaScelta() + " come modalità di consegna!",
				new Font("Ubuntu Sans", Font.PLAIN, 18));

		
		MyJPanel panelLuogoRitiro = new MyJPanel();
		panelLuogoRitiro.setBackground(coloreCasualePerBG);
		panelLuogoRitiro.setLayout(new BoxLayout(panelLuogoRitiro, BoxLayout.X_AXIS));
		panelLuogoRitiro.setPreferredSize(new Dimension(larghezza-distanzaDalBordo, 50));
		panelLuogoRitiro.setMaximumSize(new Dimension(larghezza-distanzaDalBordo, 50));
		
		MyJLabel lblIconaUomoParlante = new MyJLabel();
		lblIconaUomoParlante.aggiungiImmagineScalata("images/iconaUomoParlante.png", 25, 25, false);
		
		JTextArea luogoTextArea = new JTextArea();
		luogoTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		luogoTextArea.setPreferredSize(new Dimension(larghezza - distanzaDalBordo, 100));
		luogoTextArea.setMaximumSize(new Dimension(larghezza - distanzaDalBordo, 100));
		luogoTextArea.setFont(new Font("Ubuntu Sans", Font.ITALIC, 18));
		luogoTextArea.setEditable(false);
		luogoTextArea.setEnabled(false);
		luogoTextArea.setDisabledTextColor(Color.black);
		luogoTextArea.setOpaque(false);
		luogoTextArea.setLineWrap(true);
		luogoTextArea.setWrapStyleWord(true);
		luogoTextArea.setAlignmentX(LEFT_ALIGNMENT);
		luogoTextArea.setFont(new Font("Ubuntu Sans", Font.PLAIN, 16));
		
		if(offertaToAdd.getModalitaConsegnaScelta().equals("Spedizione")) {
			lblModalita.aggiungiImmagineScalata("images/iconaSpedizione.png", 25, 25, false);
			luogoTextArea.setText("\"L'indirizzo a cui spedire l'articolo è "+offertaToAdd.getIndirizzoSpedizione() + "\"");
		}
		else if(offertaToAdd.getModalitaConsegnaScelta().equals("Incontro")) {
			lblModalita.aggiungiImmagineScalata("images/iconaIncontro.png", 25, 25, false);
			luogoTextArea.setText("\"Preferirei un incontro presso "+offertaToAdd.getSedeDIncontroScelta().getNome()+" alle "+offertaToAdd.getOraInizioIncontro()
			+"-"+offertaToAdd.getOraFineIncontro()+" il "+offertaToAdd.getGiornoIncontro() + "\"");
		}
		else {
			lblModalita.aggiungiImmagineScalata("images/iconaRitiroInPosta.png", 25, 25, false);
			luogoTextArea.setText("\"L'ufficio postale in cui ritirerei l'articolo è "+offertaToAdd.getUfficioRitiro().getNome() + "\"");
		}
		
		lblModalita.setHorizontalTextPosition(SwingConstants.RIGHT);
		
		panelModalitaConsegnaScelta.add(Box.createVerticalGlue());
		panelModalitaConsegnaScelta.add(lblModalita);
		panelModalitaConsegnaScelta.add(Box.createVerticalGlue());
		panelLuogoRitiro.add(lblIconaUomoParlante);
		panelLuogoRitiro.add(luogoTextArea);
		
		panelWrapper.add(panelModalitaConsegnaScelta);
		panelWrapper.add(panelLuogoRitiro);
		
		return panelWrapper;
	}

	private MyJPanel creaPanelNota(Offerta offertaToAdd) {
		String nota = offertaToAdd.getNota();
		
		MyJPanel panelNota = new MyJPanel();
		panelNota.setLayout(new BoxLayout(panelNota, BoxLayout.X_AXIS));
		panelNota.setPreferredSize(new Dimension(larghezza - distanzaDalBordo, 150));
		panelNota.setMaximumSize(new Dimension(larghezza - distanzaDalBordo, 150));
		panelNota.setBackground(coloreCasualePerBG);
		
		JTextArea notaTextArea = new JTextArea();
		notaTextArea.setText("L'utente non ha niente da specificare");
		notaTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		notaTextArea.setPreferredSize(new Dimension(larghezza - distanzaDalBordo, 150));
		notaTextArea.setMaximumSize(new Dimension(larghezza - distanzaDalBordo, 150));
		notaTextArea.setEditable(false);
		notaTextArea.setEnabled(false);
		notaTextArea.setDisabledTextColor(Color.black);;
		notaTextArea.setOpaque(false);
		notaTextArea.setLineWrap(true);
		notaTextArea.setWrapStyleWord(true);
		notaTextArea.setAlignmentX(LEFT_ALIGNMENT);
		notaTextArea.setFont(new Font("Ubuntu Sans", Font.PLAIN, 16));
		if(nota != null)
			notaTextArea.setText(nota);
		
		
		panelNota.add(notaTextArea);
		return panelNota;
	}
	
	public abstract MyJPanel creaPanelSpecifico(Offerta offertaToAdd);
	
	private MyJPanel creaPanelAccettaRifiuta(Offerta offertaToAdd) {
		MyJPanel panelAccettaRifiuta = new MyJPanel();
		panelAccettaRifiuta.setBackground(new Color(220, 220, 220));
		panelAccettaRifiuta.setBackground(uninaColorClicked);
		panelAccettaRifiuta.setLayout(new BoxLayout(panelAccettaRifiuta, BoxLayout.X_AXIS));
		panelAccettaRifiuta.setPreferredSize(new Dimension(larghezza, 60));
		panelAccettaRifiuta.setMaximumSize(new Dimension(larghezza, 60));
		
		MyJButton rifiutaButton = new MyJButton("Rifiuta");

		rifiutaButton.setFocusable(false);
		rifiutaButton.setPreferredSize(new Dimension(100, 50));
		rifiutaButton.setMaximumSize(new Dimension(100, 50));
		rifiutaButton.setBackground(new Color(178, 34, 34));

		rifiutaButton.setDefaultAction(() -> settaAzioneRifiutaButton());
		rifiutaButton.setUpAction(()->{});
		rifiutaButton.setDownAction(()->{});
		
		MyJButton accettaButton = new MyJButton("Accetta");

		accettaButton.setFocusable(false);
		accettaButton.setPreferredSize(new Dimension(100, 50));
		accettaButton.setMaximumSize(new Dimension(100, 50));
		accettaButton.setBackground(new Color(46, 139, 87));

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
	
	public abstract void settaAzioneRifiutaButton();
	
	public abstract void settaAzioneAccettaButton();
	
	public abstract MyJPanel creaPanelSpecifico();
}
