package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

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

import utilities.MyJButton;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;

public class PanelHomePageAnnunci extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private MyJPanel bordoSuperiore = new MyJPanel();
	private MyJPanel barraDiRicerca = new MyJPanel();
	private MyJPanel bordoInferiore = new MyJPanel();
	
	public PanelHomePageAnnunci() {
		this.setLayout(new BorderLayout());
		
		this.settaBordoSuperiore();
		this.settaBordoInferiore();
		
		MyJPanel prova = new MyJPanel();
//		prova.setLayout(new BoxLayout(prova, BoxLayout.Y_AXIS));
		prova.setLayout(new FlowLayout());
		
		for(int i = 0; i < 10; i++) {
//			MyJLabel label = new MyJLabel("Annuncio "+i);
//			label.setAlignmentX(LEFT_ALIGNMENT);
//			label.setPreferredSize(new Dimension(100, 300));
//			label.setMaximumSize(new Dimension(100, 300));
			MyJPanel annuncioCorrente = creaPanelAnnuncio();
			
			prova.add(annuncioCorrente);
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
		bordoInferiore.setLayout(new BoxLayout(bordoInferiore, BoxLayout.Y_AXIS));
		bordoInferiore.setAlignmentX(CENTER_ALIGNMENT);
		bordoInferiore.setPreferredSize(new Dimension(500, 100));
		bordoInferiore.setBackground(new Color(220, 220, 220));	
		
		MyJButton bottonePubblicaAnnuncio = new MyJButton("Pubblica un nuovo annuncio");
		bottonePubblicaAnnuncio.setAlignmentX(CENTER_ALIGNMENT);
		bottonePubblicaAnnuncio.setPreferredSize(new Dimension(300, 50));
		
		bottonePubblicaAnnuncio.setDefaultAction(() -> {});
		bottonePubblicaAnnuncio.setUpAction(() -> {});
		bottonePubblicaAnnuncio.setDownAction(() -> {});
		
		bordoInferiore.add(Box.createVerticalGlue());
		bordoInferiore.add(bottonePubblicaAnnuncio);
		bordoInferiore.add(Box.createVerticalGlue());
	}
	
	private MyJPanel creaPanelAnnuncio() {
		MyJPanel annuncio = new MyJPanel();
		annuncio.setLayout(new BorderLayout());
		annuncio.setPreferredSize(new Dimension(800, 600));
		annuncio.setMaximumSize(new Dimension(800, 600));
		
		MyJPanel panelInfoEFotoOggetto = new MyJPanel();
		panelInfoEFotoOggetto.setLayout(new BorderLayout());
		panelInfoEFotoOggetto.add(this.creaPanelFotoOggetto(), BorderLayout.NORTH);
		panelInfoEFotoOggetto.add(this.creaPanelInfoOggetto(), BorderLayout.CENTER);
		
		annuncio.add(panelInfoEFotoOggetto, BorderLayout.WEST);
		
		MyJPanel panelInfoAnnuncio = new MyJPanel();
		panelInfoAnnuncio.setLayout(new BorderLayout());
		panelInfoAnnuncio.add(this.creaPanelUsernamePubblicante(), BorderLayout.NORTH);
		panelInfoAnnuncio.add(this.creaPanelTipoAnnuncio(), BorderLayout.CENTER);

		annuncio.add(panelInfoAnnuncio, BorderLayout.CENTER);
		
		return annuncio;
	}
	
	private MyJPanel creaPanelFotoOggetto() {
		MyJPanel panelFotoOggetto = new MyJPanel();
		panelFotoOggetto.setLayout(new BorderLayout());
		panelFotoOggetto.setPreferredSize(new Dimension(375, 500));
		panelFotoOggetto.setMaximumSize(new Dimension(375, 500));
				
		MyJLabel lblVaiIndietro = new MyJLabel();
		lblVaiIndietro.aggiungiImmagineScalata("images/iconaTornaIndietroFoto.png", 50, 50, true);
		
		MyJLabel lblVaiAvanti = new MyJLabel();
		lblVaiAvanti.aggiungiImmagineScalata("images/iconaVaiAvantiFoto.png", 50, 50, true);
		
		ImageIcon foto = new ImageIcon("images/logo_uninaswap.png");
		MyJPanel panelInterno = new MyJPanel(foto.getImage());
		
		panelFotoOggetto.add(panelInterno, BorderLayout.CENTER);
		
		return panelFotoOggetto;
	}
	
	private MyJPanel creaPanelInfoOggetto() {
		MyJPanel panelInfoOggetto = new MyJPanel();
		panelInfoOggetto.setLayout(new BoxLayout(panelInfoOggetto, BoxLayout.X_AXIS));
		panelInfoOggetto.setPreferredSize(new Dimension(350, 200));
		panelInfoOggetto.setMaximumSize(new Dimension(350, 200));
		panelInfoOggetto.setAlignmentX(CENTER_ALIGNMENT);
		
		panelInfoOggetto.setBackground(Color.green);
		
		return panelInfoOggetto;
	}
	
	private MyJPanel creaPanelUsernamePubblicante() {
		MyJPanel panelUsernamePubblicante = new MyJPanel();
		panelUsernamePubblicante.setLayout(new BoxLayout(panelUsernamePubblicante, BoxLayout.Y_AXIS));
		panelUsernamePubblicante.setPreferredSize(new Dimension(550, 75));
		panelUsernamePubblicante.setMaximumSize(new Dimension(550, 75));
		panelUsernamePubblicante.setAlignmentX(CENTER_ALIGNMENT);
		
		
		MyJLabel lblUsername = new MyJLabel("Username");
		lblUsername.setAlignmentX(RIGHT_ALIGNMENT);
		
		panelUsernamePubblicante.add(Box.createVerticalGlue());
		panelUsernamePubblicante.add(lblUsername);
		panelUsernamePubblicante.add(Box.createVerticalGlue());
		
		panelUsernamePubblicante.setBackground(Color.yellow);

		return panelUsernamePubblicante;
	}
	
	private MyJPanel creaPanelTipoAnnuncio() {
		MyJPanel panelTipoAnnuncio = new MyJPanel();
		panelTipoAnnuncio.setLayout(new BoxLayout(panelTipoAnnuncio, BoxLayout.Y_AXIS));
		panelTipoAnnuncio.setPreferredSize(new Dimension(550, 570));
		panelTipoAnnuncio.setMaximumSize(new Dimension(550, 570));
		panelTipoAnnuncio.setAlignmentX(CENTER_ALIGNMENT);
		
		MyJLabel lblNomeAnnuncio = new MyJLabel("Nome annuncio");
		lblNomeAnnuncio.setAlignmentX(LEFT_ALIGNMENT);
		
		MyJLabel lblTipoAnnuncio = new MyJLabel("Tipo annuncio");
		lblTipoAnnuncio.setAlignmentX(LEFT_ALIGNMENT);
		
		MyJLabel lblNotaScambio = new MyJLabel("Nota scambio");
		lblNotaScambio.setAlignmentX(LEFT_ALIGNMENT);
		
		MyJLabel lblDescrizione = new MyJLabel("Descrizione oggetto");
		lblDescrizione.setAlignmentX(LEFT_ALIGNMENT);
		
		panelTipoAnnuncio.add(lblNomeAnnuncio);
		panelTipoAnnuncio.add(lblTipoAnnuncio);
		panelTipoAnnuncio.add(lblDescrizione);
		panelTipoAnnuncio.add(lblNotaScambio);
		
		panelTipoAnnuncio.setBackground(Color.orange);
		
		return panelTipoAnnuncio;
	}
}
