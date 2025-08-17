package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.util.ArrayList;

import controller.Controller;
import dto.AnnuncioRegalo;
import dto.AnnuncioScambio;
import dto.AnnuncioVendita;
import dto.Offerta;
import dto.OffertaAcquisto;
import dto.OffertaRegalo;
import dto.OffertaScambio;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class PanelVisualizzaOfferteUtente extends MyJPanel {

	private static final long serialVersionUID = 1L;
	
	
	MyJPanel panelSuperiore = new MyJPanel();
	MyJPanel panelCentrale = new MyJPanel();
	
	public PanelVisualizzaOfferteUtente(ArrayList<Offerta> offerteToDisplay, String messaggioAllUtente) {
		
		this.setLayout(new BorderLayout());
		
		settaPanelSuperiore(offerteToDisplay);
		settaPanelCentrale(messaggioAllUtente);
		
		this.add(panelSuperiore, BorderLayout.NORTH);
		this.add(panelCentrale, BorderLayout.CENTER);
	}

	private void settaPanelCentrale(String messaggioAllUtente) {
		panelCentrale.setLayout(new FlowLayout());
		MyJLabel messaggio = new MyJLabel(messaggioAllUtente, new Font("Ubuntu Sans", Font.ITALIC, 16));
		messaggio.setForeground(Color.BLACK);
		panelCentrale.add(messaggio);
		panelCentrale.setBackground(uninaLightColor);
	}

	private void settaPanelSuperiore(ArrayList<Offerta> offerteToDisplay) {
		panelSuperiore.setLayout(new BoxLayout(panelSuperiore, BoxLayout.X_AXIS));
		
		MyJPanel panelAcquisto = new MyJPanel();
		panelAcquisto.setLayout(new FlowLayout());
		panelAcquisto.setBackground(Color.WHITE);
		MyJPanel panelScambio = new MyJPanel();
		panelScambio.setLayout(new FlowLayout());
		panelScambio.setBackground(Color.WHITE);
		MyJPanel panelRegalo = new MyJPanel();
		panelRegalo.setLayout(new FlowLayout());
		panelRegalo.setBackground(Color.WHITE);

		MyJLabel lblOffertaAnnuncioVendita = new MyJLabel(new ImageIcon("images/iconaAnnuncioVenditaColored.png"), true);
		MyJLabel lblOffertaAnnuncioScambio = new MyJLabel(new ImageIcon("images/iconaAnnuncioScambioColored.png"), true);
		MyJLabel lblOffertaAnnuncioRegalo = new MyJLabel(new ImageIcon("images/iconaAnnuncioRegaloColored.png"), true);
		
		lblOffertaAnnuncioVendita.rendiLabelInteragibile();
		lblOffertaAnnuncioScambio.rendiLabelInteragibile();
		lblOffertaAnnuncioRegalo.rendiLabelInteragibile();
		
		lblOffertaAnnuncioVendita.setOnMouseClickedAction(()->{
			panelAcquisto.setBackground(MyJPanel.uninaLightColor);
			panelScambio.setBackground(Color.WHITE);
			panelRegalo.setBackground(Color.WHITE);
			panelCentrale.removeAll();
			panelCentrale.revalidate();
			panelCentrale.repaint();
			mostraOfferteDiAcquistoSulCentrale(offerteToDisplay);
		});
		lblOffertaAnnuncioVendita.setOnMouseEnteredAction(() -> {
			if(panelAcquisto.getBackground().equals(Color.WHITE)) {
				panelAcquisto.setBackground(Color.LIGHT_GRAY);
			}
		});
		lblOffertaAnnuncioVendita.setOnMouseExitedAction(() -> {
			if(panelAcquisto.getBackground().equals(Color.LIGHT_GRAY)) {
				panelAcquisto.setBackground(Color.WHITE);
			}
		});
		
		lblOffertaAnnuncioScambio.setOnMouseClickedAction(() -> {
			panelScambio.setBackground(MyJPanel.uninaLightColor);
			panelAcquisto.setBackground(Color.WHITE);
			panelRegalo.setBackground(Color.WHITE);
			panelCentrale.removeAll();
			panelCentrale.revalidate();
			panelCentrale.repaint();
			mostraAnnunciDiScambioSulCentrale(offerteToDisplay);
		});
		lblOffertaAnnuncioScambio.setOnMouseEnteredAction(() -> {
			if(panelScambio.getBackground().equals(Color.WHITE)) {
				panelScambio.setBackground(Color.LIGHT_GRAY);
			}
		});
		lblOffertaAnnuncioScambio.setOnMouseExitedAction(() -> {
			if(panelScambio.getBackground().equals(Color.LIGHT_GRAY)) {
				panelScambio.setBackground(Color.WHITE);
			}
		});
		
		lblOffertaAnnuncioRegalo.setOnMouseClickedAction(() -> {
			panelRegalo.setBackground(MyJPanel.uninaLightColor);
			panelAcquisto.setBackground(Color.WHITE);
			panelScambio.setBackground(Color.WHITE);
			panelCentrale.removeAll();
			panelCentrale.revalidate();
			panelCentrale.repaint();
			mostraAnnunciDiRegaloSulCentrale(offerteToDisplay);
		});
		lblOffertaAnnuncioRegalo.setOnMouseEnteredAction(() -> {
			if(panelRegalo.getBackground().equals(Color.WHITE)) {
				panelRegalo.setBackground(Color.LIGHT_GRAY);
			}
		});
		lblOffertaAnnuncioRegalo.setOnMouseExitedAction(() -> {
			if(panelRegalo.getBackground().equals(Color.LIGHT_GRAY)) {
				panelRegalo.setBackground(Color.WHITE);
			}
		});
		
		panelAcquisto.add(lblOffertaAnnuncioVendita);
		panelScambio.add(lblOffertaAnnuncioScambio);
		panelRegalo.add(lblOffertaAnnuncioRegalo);
		
		panelSuperiore.add(panelAcquisto);
		panelSuperiore.add(panelScambio);
		panelSuperiore.add(panelRegalo);
	}

	private void mostraAnnunciDiRegaloSulCentrale(ArrayList<Offerta> offerteToDisplay) {
		for(Offerta offerta: offerteToDisplay) {
			if(offerta instanceof OffertaRegalo) {
				panelCentrale.add(creaPanelOfferta(offerta));
			}
		}
	}

	private void mostraAnnunciDiScambioSulCentrale(ArrayList<Offerta> offerteToDisplay) {
		for(Offerta offerta: offerteToDisplay) {
			if(offerta instanceof OffertaScambio) {
				panelCentrale.add(creaPanelOfferta(offerta));
			}
		}
	}

	private void mostraOfferteDiAcquistoSulCentrale(ArrayList<Offerta> offerteToDisplay) {
		for(Offerta offerta: offerteToDisplay) {
			if(offerta instanceof OffertaAcquisto) {
				panelCentrale.add(creaPanelOfferta(offerta));
			}
		}
	}

	private MyJPanel creaPanelOfferta(Offerta offerta) {
		MyJPanel offertaPanel = new MyJPanel();
		offertaPanel.setLayout(new BorderLayout());
		offertaPanel.setPreferredSize(new Dimension(800, 600));
		offertaPanel.setMaximumSize(new Dimension(800, 600));
		offertaPanel.setBorder(BorderFactory.createLineBorder(MyJPanel.uninaColorClicked, 2));
		
		MyJPanel panelInfoEFotoOggetto = new MyJPanel();
		panelInfoEFotoOggetto.setLayout(new BorderLayout());
		panelInfoEFotoOggetto.add(this.creaPanelFotoOggetto(offerta), BorderLayout.NORTH);
		panelInfoEFotoOggetto.add(this.creaPanelInfoOggetto(offerta), BorderLayout.CENTER);
		
		offertaPanel.add(panelInfoEFotoOggetto, BorderLayout.WEST);
		
		MyJPanel panelInfoAnnuncio = new MyJPanel();
		panelInfoAnnuncio.setLayout(new BorderLayout());
		panelInfoAnnuncio.add(this.creaPanelUsernamePubblicante(offerta), BorderLayout.NORTH);
		panelInfoAnnuncio.add(this.creaPanelDescrizioneAnnuncio(offerta), BorderLayout.CENTER);
//		panelInfoAnnuncio.add(this.creaPanelFaiOfferta(offerta), BorderLayout.SOUTH);

		offertaPanel.add(panelInfoAnnuncio, BorderLayout.CENTER);
		
		return offertaPanel;
	}

	private MyJPanel creaPanelDescrizioneAnnuncio(Offerta offerta) {
		// TODO Auto-generated method stub
		return null;
	}

	private MyJPanel creaPanelUsernamePubblicante(Offerta offerta) {
		MyJPanel panelUsernamePubblicante = new MyJPanel();
		panelUsernamePubblicante.setLayout(new BoxLayout(panelUsernamePubblicante, BoxLayout.X_AXIS));
		panelUsernamePubblicante.setPreferredSize(new Dimension(425, 75));
		panelUsernamePubblicante.setMaximumSize(new Dimension(425, 75));
		panelUsernamePubblicante.setAlignmentX(CENTER_ALIGNMENT);		
		panelUsernamePubblicante.setBorder(BorderFactory.createLineBorder(MyJPanel.uninaColorClicked, 1));
		
		MyJLabel lblUsername = new MyJLabel(offerta.getAnnuncioRiferito().getUtenteProprietario().getUsername());
		lblUsername.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		lblUsername.setAlignmentX(RIGHT_ALIGNMENT);
		lblUsername.setForeground(Color.white);
		MyJLabel lblIconaTipoAnnuncio = new MyJLabel();
		
		if(offerta.getAnnuncioRiferito() instanceof AnnuncioVendita) {
			lblIconaTipoAnnuncio.aggiungiImmagineScalata("images/iconaAnnuncioVendita.png", 50, 50, false);
			lblIconaTipoAnnuncio.setToolTipText("Prezzo iniziale - "+offerta.getAnnuncioRiferito().getPrezzoIniziale()+"€");
		}
		else if(offerta.getAnnuncioRiferito() instanceof AnnuncioScambio) {
			lblIconaTipoAnnuncio.aggiungiImmagineScalata("images/iconaAnnuncioScambio.png", 50, 50, false);
			lblIconaTipoAnnuncio.setToolTipText(offerta.getAnnuncioRiferito().getNotaScambio());
		}
		else if(offerta.getAnnuncioRiferito() instanceof AnnuncioRegalo)
			lblIconaTipoAnnuncio.aggiungiImmagineScalata("images/iconaAnnuncioRegalo.png", 50, 50, false);

		panelUsernamePubblicante.add(Box.createVerticalGlue());
		panelUsernamePubblicante.add(Box.createRigidArea(new Dimension(10, 0)));
		panelUsernamePubblicante.add(lblUsername);
		panelUsernamePubblicante.add(Box.createHorizontalGlue());
		panelUsernamePubblicante.add(lblIconaTipoAnnuncio);
		panelUsernamePubblicante.add(Box.createRigidArea(new Dimension(10, 0)));
		panelUsernamePubblicante.add(Box.createVerticalGlue());
		
		panelUsernamePubblicante.setBackground(MyJPanel.uninaColorClicked);

		return panelUsernamePubblicante;
	}

	private Component creaPanelInfoOggetto(Offerta offerta) {
		MyJPanel panelInfoOggetto = new MyJPanel();
		panelInfoOggetto.setLayout(new BorderLayout());
		panelInfoOggetto.setPreferredSize(new Dimension(375, 100));
		panelInfoOggetto.setMaximumSize(new Dimension(375, 100));
		panelInfoOggetto.setAlignmentX(CENTER_ALIGNMENT);
		
		MyJPanel panelCategoria = new MyJPanel();
		panelCategoria.setPreferredSize(new Dimension(375, 50));
		panelCategoria.setMaximumSize(new Dimension(375, 50));
		panelCategoria.setLayout(new FlowLayout());
		panelCategoria.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 2, MyJPanel.uninaColorClicked));
		panelCategoria.setAlignmentX(CENTER_ALIGNMENT);
		panelCategoria.setBackground(Color.white);
		
		MyJPanel panelCondizioni = new MyJPanel();
		panelCondizioni.setPreferredSize(new Dimension(375, 50));
		panelCondizioni.setMaximumSize(new Dimension(375, 50));
		panelCondizioni.setLayout(new FlowLayout());
		panelCondizioni.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, MyJPanel.uninaColorClicked));
		panelCondizioni.setAlignmentX(CENTER_ALIGNMENT);
		panelCondizioni.setBackground(Color.white);

		MyJLabel lblCategoria = new MyJLabel();
		lblCategoria.setAlignmentX(CENTER_ALIGNMENT);
		lblCategoria.setBorder(new EmptyBorder(10, 0, 0, 0));
		lblCategoria.setText(lblCategoria.getText()+offerta.getAnnuncioRiferito().getOggettoInAnnuncio().getCategoria());
		lblCategoria.aggiungiImmagineScalata(offerta.getAnnuncioRiferito().getOggettoInAnnuncio().getCategoriaEnum().getImmagineCategoria(), 25, 25, false);
		lblCategoria.setHorizontalTextPosition(SwingConstants.LEFT);
		panelCategoria.add(Box.createVerticalGlue());
		panelCategoria.add(lblCategoria);
		panelCategoria.add(Box.createVerticalGlue());
		
		MyJLabel lblCondizioni = new MyJLabel();
		lblCondizioni.setAlignmentX(CENTER_ALIGNMENT);
		lblCondizioni.setBorder(new EmptyBorder(10, 0, 0, 0));
		lblCondizioni.setText(lblCondizioni.getText()+offerta.getAnnuncioRiferito().getOggettoInAnnuncio().getCondizioni());
		lblCondizioni.setForeground(offerta.getAnnuncioRiferito().getOggettoInAnnuncio().getCondizioniEnum().getColoreCondizione());
		
		if(!offerta.getAnnuncioRiferito().getOggettoInAnnuncio().getCondizioni().equals("Ricondizionato")) {
			MyJPanel panelStelleCondizioni = new MyJPanel();
			panelStelleCondizioni.setBackground(Color.white);
			panelStelleCondizioni.setPreferredSize(new Dimension(90, 30));
			MyJLabel lblStella1 = new MyJLabel();
			lblStella1.aggiungiImmagineScalata(offerta.getAnnuncioRiferito().getOggettoInAnnuncio().getCondizioniEnum().getStella1(), 25, 25, false);
			MyJLabel lblStella2 = new MyJLabel();
			lblStella2.aggiungiImmagineScalata(offerta.getAnnuncioRiferito().getOggettoInAnnuncio().getCondizioniEnum().getStella2(), 25, 25, false);
			MyJLabel lblStella3 = new MyJLabel();
			lblStella3.aggiungiImmagineScalata(offerta.getAnnuncioRiferito().getOggettoInAnnuncio().getCondizioniEnum().getStella3(), 25, 25, false);
			panelStelleCondizioni.add(lblStella1);
			panelStelleCondizioni.add(lblStella2);
			panelStelleCondizioni.add(lblStella3);
		
			panelCondizioni.add(Box.createVerticalGlue());
			panelCondizioni.add(lblCondizioni);
			panelCondizioni.add(panelStelleCondizioni);
			panelCondizioni.add(Box.createVerticalGlue());
		}
		else {
			lblCondizioni.aggiungiImmagineScalata(offerta.getAnnuncioRiferito().getOggettoInAnnuncio().getCondizioniEnum().getFixPerRicondizionato(), 25, 25, false);
			lblCondizioni.setHorizontalTextPosition(SwingConstants.LEFT);
			
			panelCondizioni.add(Box.createVerticalGlue());
			panelCondizioni.add(lblCondizioni);
			panelCondizioni.add(Box.createVerticalGlue());
		}
		
		panelInfoOggetto.add(panelCategoria, BorderLayout.NORTH);
		panelInfoOggetto.add(panelCondizioni, BorderLayout.CENTER);
		
		panelInfoOggetto.setBackground(Color.red);
		
		return panelInfoOggetto;
	}

	private MyJPanel creaPanelFaiOfferta(Offerta offerta) {
		// TODO Auto-generated method stub
		return null;
	}

	private MyJPanel creaPanelFotoOggetto(Offerta offerta) {
		MyJPanel panelFotoOggetto = new MyJPanel();
		panelFotoOggetto.setLayout(new BorderLayout());
		panelFotoOggetto.setPreferredSize(new Dimension(375, 500));
		panelFotoOggetto.setMaximumSize(new Dimension(375, 500));
		panelFotoOggetto.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, MyJPanel.uninaColorClicked));
				
		ImageIcon fotoToAdd;
		MyJPanel panelInterno;
		if(offerta.getAnnuncioRiferito().getOggettoInAnnuncio().getImmagine(0) != null) {
			fotoToAdd = new ImageIcon(offerta.getAnnuncioRiferito().getOggettoInAnnuncio().getImmagine(0));
			panelInterno = new MyJPanel(fotoToAdd.getImage());
		}
		else {
			fotoToAdd = new ImageIcon("images/logo_uninaswap.png");
			panelInterno = new MyJPanel(fotoToAdd.getImage());
		}
		
		panelFotoOggetto.add(panelInterno, BorderLayout.CENTER);
		
		return panelFotoOggetto;
	}
}
