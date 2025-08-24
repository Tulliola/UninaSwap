package utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.Offerta;

public class DialogOffertaSpecificaUtente extends MyJDialog {

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
	
	public DialogOffertaSpecificaUtente(Offerta offertaToDisplay, Controller controller, Container container) {
		this.setModal(true);
		this.setSize(new Dimension(800, 400));
		this.setTitle("Dettagli offerta");
		this.mainController = controller;
		
		this.add(creaPanelOfferta(offertaToDisplay));
		this.setLocationRelativeTo(container);
	}



	private MyJPanel creaPanelOfferta(Offerta offertaToDisplay) {
		MyJPanel offertaPanel = new MyJPanel();
		offertaPanel.setLayout(new BorderLayout());
		offertaPanel.setPreferredSize(new Dimension(larghezza, altezza));
		offertaPanel.setMaximumSize(new Dimension(larghezza, altezza));
		offertaPanel.setBorder(BorderFactory.createLineBorder(MyJPanel.uninaColorClicked, 2));
		offertaPanel.setBackground(coloreCasualePerBG);

		offertaPanel.add(this.creaPanelSuperiore(offertaToDisplay), BorderLayout.NORTH);
		offertaPanel.add(this.creaPanelInfoOfferta(offertaToDisplay), BorderLayout.CENTER);
		
		return offertaPanel;
	}



	private MyJPanel creaPanelSuperiore(Offerta offertaToDisplay) {
		MyJPanel panelSuperiore = new MyJPanel();
		panelSuperiore.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelSuperiore.setBackground(MyJPanel.uninaColorClicked);
		panelSuperiore.setPreferredSize(new Dimension(larghezza, 35));
		panelSuperiore.setMaximumSize(new Dimension(larghezza, 35));
		
		MyJLabel lblOfferente = new MyJLabel("Tua offerta all'annuncio "+offertaToDisplay.getAnnuncioRiferito().getNome());
		lblOfferente.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		lblOfferente.setForeground(Color.white);
		
		panelSuperiore.add(lblOfferente);
		return panelSuperiore;
	}



	private MyJPanel creaPanelInfoOfferta(Offerta offertaToDisplay) {
		MyJPanel panelInfoOfferta = new MyJPanel();
		panelInfoOfferta.setLayout(new BoxLayout(panelInfoOfferta, BoxLayout.Y_AXIS));
		panelInfoOfferta.setBackground(coloreCasualePerBG);
		
		panelInfoOfferta.add(creaPanelModalitaConsegnaScelta(offertaToDisplay));
		panelInfoOfferta.add(creaPanelNota(offertaToDisplay));
		panelInfoOfferta.add(creaPanelSpecifico(offertaToDisplay));

		return panelInfoOfferta;
	}



	private MyJPanel creaPanelSpecifico(Offerta offertaToDisplay) {
		MyJPanel panelSpecifico = new MyJPanel();
		panelSpecifico.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelSpecifico.setPreferredSize(new Dimension(larghezza - distanzaDalBordo, 60));
		panelSpecifico.setMaximumSize(new Dimension(larghezza - distanzaDalBordo, 60));		
		panelSpecifico.setBackground(coloreCasualePerBG);
		
		if(offertaToDisplay.getPrezzoOfferto() != null) {
			MyJLabel lblPrezzoOfferto = new MyJLabel("Hai offerto " + offertaToDisplay.getPrezzoOfferto() + "€!");
			lblPrezzoOfferto.aggiungiImmagineScalata("images/iconaPrezzoIniziale.png", 25, 25, false);
			lblPrezzoOfferto.setHorizontalTextPosition(SwingConstants.RIGHT);
			
			panelSpecifico.add(lblPrezzoOfferto);
		}
		else if(offertaToDisplay.getOggettiOfferti() != null && offertaToDisplay.getOggettiOfferti().size() != 0)
			settaPanelSpecificoToOggettiInScambio(offertaToDisplay, panelSpecifico);
		
		else {
			MyJLabel lblRegalo = new MyJLabel("Hai chiesti l'oggetto è stato chiesto in regalo");
			lblRegalo.setAlignmentX(LEFT_ALIGNMENT);
			panelSpecifico.add(lblRegalo);
		}
			
		return panelSpecifico;
	}



	private void settaPanelSpecificoToOggettiInScambio(Offerta offertaToDisplay, MyJPanel panelSpecifico) {
		MyJLabel lblOggettiInCambio = new MyJLabel("Hai offerto ");
		lblOggettiInCambio.aggiungiImmagineScalata("images/iconaFrecceScambio.png", 25, 25, false);
		lblOggettiInCambio.setHorizontalAlignment(SwingConstants.RIGHT);
		
		MyJLabel lblCliccabile =  new MyJLabel(String.valueOf(offertaToDisplay.getOggettiOfferti().size()), Color.red);
		lblCliccabile.aggiungiEffettoCliccabilitaPerTesto();
		lblCliccabile.rendiLabelInteragibile();
		
		lblCliccabile.setOnMouseExitedAction(() -> {});
		lblCliccabile.setOnMouseEnteredAction(() -> {});
		lblCliccabile.setOnMouseClickedAction(() -> {mainController.passaADialogVisualizzaOggetti(offertaToDisplay.getOggettiOfferti());});
		
		MyJLabel lblOggetti = new MyJLabel(" oggetti!");
		
		panelSpecifico.add(lblOggettiInCambio);
		panelSpecifico.add(lblCliccabile);
		panelSpecifico.add(lblOggetti);
	}



	private MyJPanel creaPanelNota(Offerta offertaToDisplay) {
		String nota = offertaToDisplay.getNota();
		
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



	private MyJPanel creaPanelModalitaConsegnaScelta(Offerta offertaToDisplay) {
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
		
		MyJLabel lblModalita = new MyJLabel("Hai scelto " + offertaToDisplay.getModalitaConsegnaScelta() + " come modalità di consegna!",
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
		
		if(offertaToDisplay.getModalitaConsegnaScelta().equals("Spedizione")) {
			lblModalita.aggiungiImmagineScalata("images/iconaSpedizione.png", 25, 25, false);
			luogoTextArea.setText("\"L'indirizzo a cui spedire l'articolo è "+offertaToDisplay.getIndirizzoSpedizione() + "\"");
		}
		else if(offertaToDisplay.getModalitaConsegnaScelta().equals("Incontro")) {
			lblModalita.aggiungiImmagineScalata("images/iconaIncontro.png", 25, 25, false);
			luogoTextArea.setText("\"Preferirei un incontro presso "+offertaToDisplay.getSedeDIncontroScelta().getNome()+" alle "+offertaToDisplay.getOraInizioIncontro()
			+"-"+offertaToDisplay.getOraFineIncontro()+" il "+offertaToDisplay.getGiornoIncontro() + "\"");
		}
		else {
			lblModalita.aggiungiImmagineScalata("images/iconaRitiroInPosta.png", 25, 25, false);
			luogoTextArea.setText("\"L'ufficio postale in cui ritirerei l'articolo è "+offertaToDisplay.getUfficioRitiro()+"\"");
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
}
