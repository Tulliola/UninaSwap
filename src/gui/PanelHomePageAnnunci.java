package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.Annuncio;
import dto.AnnuncioRegalo;
import dto.AnnuncioScambio;
import dto.AnnuncioVendita;
//import dto.AnnuncioRegalo;
//import dto.AnnuncioScambio;
import utilities.MyJButton;

import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;
import utilities.StatoAnnuncioEnum;

public class PanelHomePageAnnunci extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private MyJPanel bordoSuperiore = new MyJPanel();
	private MyJPanel barraDiRicerca = new MyJPanel();
	private MyJPanel bordoInferiore = new MyJPanel();
	
	private Controller mainController;
	
	public PanelHomePageAnnunci(Controller controller, ArrayList<Annuncio> annunci) {
		mainController = controller;
		
		this.setLayout(new BorderLayout());
		
		this.settaBordoSuperiore();
		this.settaBordoInferiore();

		MyJPanel prova = new MyJPanel();
		prova.setLayout(new FlowLayout());
		
		for(int i = 0; i < annunci.size(); i++) {
			if(annunci.get(i).getStato() == StatoAnnuncioEnum.Disponibile) {
				MyJPanel annuncioCorrente = creaPanelAnnuncio(annunci.get(i));
				prova.add(annuncioCorrente);
			}

		}
		
		prova.setPreferredSize(new Dimension(500, 4000));
		
		
		JScrollPane scrollPanel = new JScrollPane(prova);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.getVerticalScrollBar().setUnitIncrement(20);

		this.add(bordoSuperiore, BorderLayout.NORTH);
		this.add(scrollPanel, BorderLayout.CENTER);
		this.add(bordoInferiore, BorderLayout.SOUTH);
	}
	
	private void settaBordoSuperiore() {
		bordoSuperiore.setLayout(new BoxLayout(bordoSuperiore, BoxLayout.Y_AXIS));
		bordoSuperiore.setAlignmentX(CENTER_ALIGNMENT);
		bordoSuperiore.setPreferredSize(new Dimension(500, 50));
		bordoSuperiore.setBackground(MyJPanel.uninaColorClicked);
		
		barraDiRicerca.setLayout(new BorderLayout());
		barraDiRicerca.setAlignmentX(CENTER_ALIGNMENT);
		barraDiRicerca.setPreferredSize(new Dimension(450, 35));
		barraDiRicerca.setMaximumSize(new Dimension(450, 35));

		JTextField campoDiTestoTextField = new JTextField("Cerca ora!");
		campoDiTestoTextField.setBorder(new EmptyBorder(0, 10, 0, 0));
		campoDiTestoTextField.setFont(new Font("Ubuntu Sans", Font.PLAIN, 15));
		campoDiTestoTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent fe) {
				if(campoDiTestoTextField.getText().equals("Cerca ora!"))
					campoDiTestoTextField.setText("");
			}
			
			@Override
			public void focusLost(FocusEvent fe) {
				if(campoDiTestoTextField.getText().isEmpty())
					campoDiTestoTextField.setText("Cerca ora!");
			}
		});
		
		MyJLabel lblIconaDiRicerca = new MyJLabel();
		lblIconaDiRicerca.aggiungiImmagineScalata("images/iconaDiRicerca.png", 30, 30, false);
		lblIconaDiRicerca.setOpaque(true);
		lblIconaDiRicerca.setBackground(Color.white);
		
		MyJLabel lblIconaDiEliminaTesto = new MyJLabel();
		lblIconaDiEliminaTesto.aggiungiImmagineScalata("images/iconaRimuovi.png", 30, 30, true);
		lblIconaDiEliminaTesto.setOpaque(true);
		lblIconaDiEliminaTesto.setBackground(Color.white);
		
		lblIconaDiEliminaTesto.rendiLabelInteragibile();
		
		lblIconaDiEliminaTesto.setOnMouseClickedAction(() -> {
			campoDiTestoTextField.setText("");
			campoDiTestoTextField.requestFocus();
		});
		
		lblIconaDiEliminaTesto.setOnMouseEnteredAction(() -> {
			lblIconaDiEliminaTesto.setBackground(new Color(220, 220, 220));
			lblIconaDiEliminaTesto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		});
		
		lblIconaDiEliminaTesto.setOnMouseExitedAction(() -> {
			lblIconaDiEliminaTesto.setBackground(Color.white);
			lblIconaDiEliminaTesto.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		});
		
		barraDiRicerca.add(lblIconaDiRicerca, BorderLayout.WEST);
		barraDiRicerca.add(campoDiTestoTextField, BorderLayout.CENTER);
		barraDiRicerca.add(lblIconaDiEliminaTesto, BorderLayout.EAST);
	
		bordoSuperiore.add(Box.createVerticalGlue());
		bordoSuperiore.add(barraDiRicerca);
		bordoSuperiore.add(Box.createVerticalGlue());
	}
	
	private void settaBordoInferiore() {
		bordoInferiore.setLayout(new BoxLayout(bordoInferiore, BoxLayout.X_AXIS));
		bordoInferiore.setAlignmentX(CENTER_ALIGNMENT);
		bordoInferiore.setPreferredSize(new Dimension(500, 100));
		bordoInferiore.setBackground(new Color(220, 220, 220));	
		
		MyJButton bottonePubblicaAnnuncioVendita = new MyJButton("Pubblica un nuovo annuncio di vendita");
		bottonePubblicaAnnuncioVendita.setAlignmentX(CENTER_ALIGNMENT);
		bottonePubblicaAnnuncioVendita.setPreferredSize(new Dimension(400, 75));
		bottonePubblicaAnnuncioVendita.setMaximumSize(new Dimension(400, 75));
		bottonePubblicaAnnuncioVendita.setDefaultAction(() -> {mainController.passaAFramePubblicaAnnuncio("Vendita");});
		bottonePubblicaAnnuncioVendita.setUpAction(() -> {});
		bottonePubblicaAnnuncioVendita.setDownAction(() -> {});
		
		MyJButton bottonePubblicaAnnuncioScambio = new MyJButton("Pubblica un nuovo annuncio di scambio");
		bottonePubblicaAnnuncioScambio.setAlignmentX(CENTER_ALIGNMENT);
		bottonePubblicaAnnuncioScambio.setPreferredSize(new Dimension(400, 75));
		bottonePubblicaAnnuncioScambio.setMaximumSize(new Dimension(400, 75));
		bottonePubblicaAnnuncioScambio.setDefaultAction(() -> {mainController.passaAFramePubblicaAnnuncio("Scambio");});
		bottonePubblicaAnnuncioScambio.setUpAction(() -> {});
		bottonePubblicaAnnuncioScambio.setDownAction(() -> {});
		
		MyJButton bottonePubblicaAnnuncioRegalo = new MyJButton("Pubblica un nuovo annuncio di regalo");
		bottonePubblicaAnnuncioRegalo.setAlignmentX(CENTER_ALIGNMENT);
		bottonePubblicaAnnuncioRegalo.setPreferredSize(new Dimension(400, 75));
		bottonePubblicaAnnuncioRegalo.setMaximumSize(new Dimension(400, 75));

		bottonePubblicaAnnuncioRegalo.setDefaultAction(() -> {mainController.passaAFramePubblicaAnnuncio("Regalo");});
		bottonePubblicaAnnuncioRegalo.setUpAction(() -> {});
		bottonePubblicaAnnuncioRegalo.setDownAction(() -> {});
		
		bordoInferiore.add(Box.createVerticalGlue());
		bordoInferiore.add(Box.createHorizontalGlue());
		bordoInferiore.add(bottonePubblicaAnnuncioVendita);
		bordoInferiore.add(Box.createHorizontalGlue());
		bordoInferiore.add(bottonePubblicaAnnuncioScambio);
		bordoInferiore.add(Box.createHorizontalGlue());
		bordoInferiore.add(bottonePubblicaAnnuncioRegalo);
		bordoInferiore.add(Box.createHorizontalGlue());
		bordoInferiore.add(Box.createVerticalGlue());
	}
	
	private MyJPanel creaPanelAnnuncio(Annuncio annuncioToAdd) {
		MyJPanel annuncioPanel = new MyJPanel();
		annuncioPanel.setLayout(new BorderLayout());
		annuncioPanel.setPreferredSize(new Dimension(800, 600));
		annuncioPanel.setMaximumSize(new Dimension(800, 600));
		annuncioPanel.setBorder(BorderFactory.createLineBorder(MyJPanel.uninaColorClicked, 1));
		
		MyJPanel panelInfoEFotoOggetto = new MyJPanel();
		panelInfoEFotoOggetto.setLayout(new BorderLayout());
		panelInfoEFotoOggetto.add(this.creaPanelFotoOggetto(annuncioToAdd), BorderLayout.NORTH);
		panelInfoEFotoOggetto.add(this.creaPanelInfoOggetto(annuncioToAdd), BorderLayout.CENTER);
		
		annuncioPanel.add(panelInfoEFotoOggetto, BorderLayout.WEST);
		
		MyJPanel panelInfoAnnuncio = new MyJPanel();
		panelInfoAnnuncio.setLayout(new BorderLayout());
		panelInfoAnnuncio.add(this.creaPanelUsernamePubblicante(annuncioToAdd), BorderLayout.NORTH);
		panelInfoAnnuncio.add(this.creaPanelTipoAnnuncio(annuncioToAdd), BorderLayout.CENTER);

		annuncioPanel.add(panelInfoAnnuncio, BorderLayout.CENTER);
		
		return annuncioPanel;
	}
	
	private MyJPanel creaPanelFotoOggetto(Annuncio annuncioToAdd) {
		MyJPanel panelFotoOggetto = new MyJPanel();
		panelFotoOggetto.setLayout(new BorderLayout());
		panelFotoOggetto.setPreferredSize(new Dimension(375, 500));
		panelFotoOggetto.setMaximumSize(new Dimension(375, 500));
		panelFotoOggetto.setBorder(BorderFactory.createLineBorder(MyJPanel.uninaColorClicked, 1));
				
		ImageIcon fotoToAdd;
		MyJPanel panelInterno;
		if(annuncioToAdd.getOggettoInAnnuncio().getImmagine(0) != null) {
			fotoToAdd = new ImageIcon(annuncioToAdd.getOggettoInAnnuncio().getImmagine(0));
			panelInterno = new MyJPanel(fotoToAdd.getImage());
		}
		else {
			fotoToAdd = new ImageIcon("images/logo_uninaswap.png");
			panelInterno = new MyJPanel(fotoToAdd.getImage());
		}
		
		panelFotoOggetto.add(panelInterno, BorderLayout.CENTER);
		
		return panelFotoOggetto;
	}
	
	private MyJPanel creaPanelInfoOggetto(Annuncio annuncioToAdd) {
		MyJPanel panelInfoOggetto = new MyJPanel();
		panelInfoOggetto.setLayout(new BorderLayout());
		panelInfoOggetto.setPreferredSize(new Dimension(375, 100));
		panelInfoOggetto.setMaximumSize(new Dimension(375, 100));
		panelInfoOggetto.setAlignmentX(CENTER_ALIGNMENT);
		
		MyJPanel panelCategoria = new MyJPanel();
		panelCategoria.setPreferredSize(new Dimension(375, 50));
		panelCategoria.setMaximumSize(new Dimension(375, 50));
		panelCategoria.setBackground(Color.white);
		panelCategoria.setBorder(BorderFactory.createLineBorder(MyJPanel.uninaColorClicked, 1));
		panelCategoria.setLayout(new FlowLayout());
		panelCategoria.setAlignmentX(CENTER_ALIGNMENT);
		
		MyJPanel panelCondizioni = new MyJPanel();
		panelCondizioni.setPreferredSize(new Dimension(375, 50));
		panelCondizioni.setMaximumSize(new Dimension(375, 50));
		panelCondizioni.setBackground(Color.white);
		panelCondizioni.setLayout(new FlowLayout());
		panelCondizioni.setAlignmentX(CENTER_ALIGNMENT);
		panelCondizioni.setBorder(BorderFactory.createLineBorder(MyJPanel.uninaColorClicked, 1));

		MyJLabel lblCategoria = new MyJLabel();
		lblCategoria.setAlignmentX(CENTER_ALIGNMENT);
		lblCategoria.setBorder(new EmptyBorder(10, 0, 0, 0));
		lblCategoria.setText(lblCategoria.getText()+annuncioToAdd.getOggettoInAnnuncio().getCategoria());
		panelCategoria.add(Box.createVerticalGlue());
		panelCategoria.add(lblCategoria);
		panelCategoria.add(Box.createVerticalGlue());
		
		MyJLabel lblCondizioni = new MyJLabel();
		lblCondizioni.setAlignmentX(CENTER_ALIGNMENT);
		lblCondizioni.setBorder(new EmptyBorder(10, 0, 0, 0));
		lblCondizioni.setText(lblCondizioni.getText()+annuncioToAdd.getOggettoInAnnuncio().getCondizioni());
		panelCondizioni.add(Box.createVerticalGlue());
		panelCondizioni.add(lblCondizioni);
		panelCondizioni.add(Box.createVerticalGlue());
		
		panelInfoOggetto.add(panelCategoria, BorderLayout.NORTH);
		panelInfoOggetto.add(panelCondizioni, BorderLayout.CENTER);
		
		panelInfoOggetto.setBackground(Color.red);
		
		return panelInfoOggetto;
	}
	
	private MyJPanel creaPanelUsernamePubblicante(Annuncio annuncio) {
		MyJPanel panelUsernamePubblicante = new MyJPanel();
		panelUsernamePubblicante.setLayout(new BoxLayout(panelUsernamePubblicante, BoxLayout.X_AXIS));
		panelUsernamePubblicante.setPreferredSize(new Dimension(425, 75));
		panelUsernamePubblicante.setMaximumSize(new Dimension(425, 75));
		panelUsernamePubblicante.setAlignmentX(CENTER_ALIGNMENT);		
		panelUsernamePubblicante.setBorder(BorderFactory.createLineBorder(MyJPanel.uninaColorClicked, 1));
		
		MyJLabel lblUsername = new MyJLabel(annuncio.getUtenteProprietario().getUsername());
		lblUsername.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		lblUsername.setAlignmentX(RIGHT_ALIGNMENT);
		MyJLabel lblIconaTipoAnnuncio = new MyJLabel();
		
		if(annuncio instanceof AnnuncioVendita) {
			lblIconaTipoAnnuncio.aggiungiImmagineScalata("images/iconaAnnuncioVendita.png", 50, 50, false);
			lblIconaTipoAnnuncio.setToolTipText("Prezzo iniziale - "+annuncio.getPrezzoIniziale()+"€");
		}
		else if(annuncio instanceof AnnuncioScambio) {
			lblIconaTipoAnnuncio.aggiungiImmagineScalata("images/iconaAnnuncioScambio.png", 50, 50, false);
			lblIconaTipoAnnuncio.setToolTipText(annuncio.getNotaScambio());
		}
		else if(annuncio instanceof AnnuncioRegalo)
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
	
	private MyJPanel creaPanelTipoAnnuncio(Annuncio annuncio) {
		MyJPanel panelTipoAnnuncio = new MyJPanel();
		panelTipoAnnuncio.setLayout(new BoxLayout(panelTipoAnnuncio, BoxLayout.Y_AXIS));
		panelTipoAnnuncio.setPreferredSize(new Dimension(550, 570));
		panelTipoAnnuncio.setMaximumSize(new Dimension(550, 570));
		panelTipoAnnuncio.setAlignmentX(CENTER_ALIGNMENT);
		panelTipoAnnuncio.setBorder(BorderFactory.createLineBorder(MyJPanel.uninaColorClicked, 1));
		
		MyJLabel lblNomeAnnuncio = new MyJLabel(annuncio.getNome());
		lblNomeAnnuncio.setAlignmentX(LEFT_ALIGNMENT);
		
		MyJLabel lblNotaScambio = new MyJLabel(annuncio.getNotaScambio());
		if(annuncio.getNotaScambio() != null) {
			lblNotaScambio.setAlignmentX(LEFT_ALIGNMENT);
		}
		
		MyJLabel lblDescrizione = new MyJLabel(annuncio.getOggettoInAnnuncio().getDescrizione());
		lblDescrizione.setAlignmentX(LEFT_ALIGNMENT);
		
		panelTipoAnnuncio.add(lblNomeAnnuncio);
		panelTipoAnnuncio.add(lblDescrizione);
		panelTipoAnnuncio.add(lblNotaScambio);
		
		panelTipoAnnuncio.setBackground(Color.white);
		
		return panelTipoAnnuncio;
	}
}
