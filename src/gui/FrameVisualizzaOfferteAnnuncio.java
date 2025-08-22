package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.Controller;
import dto.Offerta;
import dto.OffertaAcquisto;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJOffertaPanel;
import utilities.MyJPanel;
import utilities.StatoOffertaEnum;

public class FrameVisualizzaOfferteAnnuncio extends MyJFrame {

	private static final long serialVersionUID = 1L;
	
	private Controller mainController;
	
	private MyJPanel contentPane;
	private PanelHomePageSuperiore panelSuperiore = new PanelHomePageSuperiore(this);
	private MyJPanel panelCentrale = new MyJPanel();
	private PanelBarraLateraleSx panelLaterale;
	private JScrollPane scrollPane;
	private MyJPanel panelOfferte = new MyJPanel();
	private MyJLabel lblTornaAllaHomePage = new MyJLabel("   Torna alla home page");
	
	public FrameVisualizzaOfferteAnnuncio(ArrayList<Offerta> offerte, Controller mainController) {
		this.mainController = mainController;
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		if(!offerte.isEmpty())
			this.setTitle("Le offerte al tuo annuncio - "+offerte.get(0).getAnnuncioRiferito().getNome());
		
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		settaContentPane(offerte);
	}

	private void settaContentPane(ArrayList<Offerta> offerte) {
		contentPane = new MyJPanel();
		contentPane.setLayout(new BorderLayout());
		settaPanelCentrale(offerte);
		contentPane.add(panelCentrale, BorderLayout.CENTER);
		contentPane.add(panelSuperiore, BorderLayout.NORTH);
		scrollPane = new JScrollPane(panelOfferte);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		contentPane.add(scrollPane);
		this.setContentPane(contentPane);
	}

	private void settaPanelCentrale(ArrayList<Offerta> offerte) {
		panelCentrale.setLayout(new BorderLayout());
		panelCentrale.setPreferredSize(new Dimension(1100, 900));
		panelLaterale = new PanelBarraLateraleSx(panelCentrale, mainController, this, "        Annunci disponibili");
		panelLaterale.aggiungiRigaNelPanel(lblTornaAllaHomePage, true, "images/iconaHomePage.png");
		panelLaterale.add(lblTornaAllaHomePage, 0);
		
		lblTornaAllaHomePage.setOnMouseClickedAction(() -> 
		{
			mainController.passaAFrameHomePage(this);
		});
		
		
		contentPane.add(panelLaterale, BorderLayout.WEST);
		if(offerte.isEmpty()) {
			this.setTitle("Non ci sono offerte per questo annuncio");
			MyJLabel noOfferte = new MyJLabel();
			noOfferte.setText("Non ci sono offerte attive per questo annuncio");
			noOfferte.setFont(new Font("Ubuntu Sans", Font.ITALIC, 16));
			noOfferte.setAlignmentX(CENTER_ALIGNMENT);
			panelOfferte.add(noOfferte);
		}
		else
			panelCentrale.add(settaPanelOfferte(offerte), BorderLayout.CENTER);
	}

	private MyJPanel settaPanelOfferte(ArrayList<Offerta> offerte) {
		panelOfferte.setBackground(MyJPanel.uninaLightColor);
		panelOfferte.setPreferredSize(getDimension(offerte));
		for(Offerta offerta: offerte) {
			panelOfferte.add(new MyJOffertaPanel(offerta, mainController) {

				@Override
				public void settaAzioneRifiutaButton() {
					mainController.aggiornaStatoOffertaAcquisto((OffertaAcquisto)offerta, StatoOffertaEnum.Rifiutata);
				}

				@Override
				public void settaAzioneAccettaButton() {
					mainController.aggiornaStatoOffertaAcquisto((OffertaAcquisto)offerta, StatoOffertaEnum.Accettata);
				}

				@Override
				public MyJPanel creaPanelSpecifico() {
					MyJPanel panelSpecifico = new MyJPanel();
					panelSpecifico.setLayout(new BoxLayout(panelSpecifico, BoxLayout.X_AXIS));
					panelSpecifico.setPreferredSize(new Dimension(780, 40));
					panelSpecifico.setMaximumSize(new Dimension(780, 40));
					
					if(offerta.getPrezzoOfferto() != null) {
						MyJLabel lblPrezzo = new MyJLabel("Prezzo offerto: €"+offerta.getPrezzoOfferto());
						lblPrezzo.setAlignmentX(LEFT_ALIGNMENT);
						lblPrezzo.setFont(new Font("Ubuntu Sans", Font.PLAIN, 18));
						panelSpecifico.add(lblPrezzo);
					}
					else if(!offerta.getOggettiOfferti().isEmpty()) {
						
					}
					else {
						MyJLabel lblRegalo = new MyJLabel("L'oggetto è stato chiesto in regalo");
						lblRegalo.setAlignmentX(LEFT_ALIGNMENT);
						panelSpecifico.add(lblRegalo);
					}
					return panelSpecifico;
				}
				
			});
		}
		return this.panelOfferte;
	}

	private PanelBarraLateraleSx settaPanelLaterale() {
		panelLaterale = new PanelBarraLateraleSx(panelCentrale, mainController, this, "        Annunci disponibili");
		panelLaterale.aggiungiRigaNelPanel(lblTornaAllaHomePage, true, "images/iconaHomePage.png");
		panelLaterale.add(lblTornaAllaHomePage, 0);
		
		return this.panelLaterale;
	}

	private Dimension getDimension(ArrayList<Offerta> offerte) {
		return new Dimension(800, offerte.size()*400);
	}
}