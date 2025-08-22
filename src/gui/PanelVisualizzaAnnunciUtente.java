package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.Annuncio;
import dto.AnnuncioRegalo;
import dto.AnnuncioScambio;
import dto.AnnuncioVendita;
import utilities.MyJAnnuncioPanel;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;

abstract public class PanelVisualizzaAnnunciUtente extends MyJPanel {

	private static final long serialVersionUID = 1L;
	protected Controller mainController;
	protected MyJPanel panelSuperiore = new MyJPanel();
	protected MyJPanel panelCentrale = new MyJPanel();
	protected JScrollPane scrollPane;
	
	public PanelVisualizzaAnnunciUtente(Controller mainController, ArrayList<Annuncio> annunciToDisplay, String messaggioAllUtente, MyJFrame parentFrame) {
		this.mainController = mainController;
		
		this.setLayout(new BorderLayout());
		
		panelCentrale.setPreferredSize(new Dimension(800, 600));
		panelCentrale.setMaximumSize(new Dimension(800, 600));
		
		scrollPane = new JScrollPane(panelCentrale);
		
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);	
		parentFrame.add(scrollPane);
		
		settaPanelSuperiore(annunciToDisplay);
		settaPanelCentrale(messaggioAllUtente);
		
		this.add(panelSuperiore, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		
	}


	private void settaPanelSuperiore(ArrayList<Annuncio> annunciToDisplay) {
		panelSuperiore.setLayout(new BoxLayout(panelSuperiore, BoxLayout.X_AXIS));
		
		MyJPanel panelVendita = new MyJPanel();
		panelVendita.setLayout(new FlowLayout());
		panelVendita.setBackground(Color.WHITE);
		
		MyJPanel panelScambio = new MyJPanel();
		panelScambio.setLayout(new FlowLayout());
		panelScambio.setBackground(Color.WHITE);
		
		MyJPanel panelRegalo = new MyJPanel();
		panelRegalo.setLayout(new FlowLayout());
		panelRegalo.setBackground(Color.WHITE);
		
		MyJLabel lblVendita = new MyJLabel(new ImageIcon("images/iconaAnnuncioVenditaColored.png"), true);
		MyJLabel lblScambio = new MyJLabel(new ImageIcon("images/iconaAnnuncioScambioColored.png"), true);
		MyJLabel lblRegalo = new MyJLabel(new ImageIcon("images/iconaAnnuncioRegaloColored.png"), true);
		
		lblVendita.rendiLabelInteragibile();
		lblScambio.rendiLabelInteragibile();
		lblRegalo.rendiLabelInteragibile();
		
		ArrayList<Annuncio> annunciVendita = new ArrayList();
		ArrayList<Annuncio> annunciScambio = new ArrayList();
		ArrayList<Annuncio> annunciRegalo = new ArrayList();
		
		for(Annuncio annuncio: annunciToDisplay) {
			if(annuncio instanceof AnnuncioVendita)
				annunciVendita.add(annuncio);
			else if(annuncio instanceof AnnuncioScambio)
				annunciScambio.add(annuncio);
			else if(annuncio instanceof AnnuncioRegalo)
				annunciRegalo.add(annuncio);
		}
		
		lblVendita.setOnMouseClickedAction(() -> {
			panelVendita.setBackground(MyJPanel.uninaLightColor);
			panelScambio.setBackground(Color.WHITE);
			panelRegalo.setBackground(Color.WHITE);
			mostraAnnunciDiVenditaSulCentrale(annunciVendita);
		});
		lblVendita.setOnMouseEnteredAction(() -> {
			if(panelVendita.getBackground().equals(Color.WHITE)) {
				panelVendita.setBackground(Color.LIGHT_GRAY);
			}
		});
		lblVendita.setOnMouseExitedAction(() -> {
			if(panelVendita.getBackground().equals(Color.LIGHT_GRAY)) {
				panelVendita.setBackground(Color.WHITE);
			}
		});
		
		lblScambio.setOnMouseClickedAction(() -> {
			panelScambio.setBackground(MyJPanel.uninaLightColor);
			panelVendita.setBackground(Color.WHITE);
			panelRegalo.setBackground(Color.WHITE);
			mostraAnnunciDiScambioSulCentrale(annunciScambio);
		});
		lblScambio.setOnMouseEnteredAction(() -> {
			if(panelScambio.getBackground().equals(Color.WHITE)) {
				panelScambio.setBackground(Color.LIGHT_GRAY);
			}
		});
		lblScambio.setOnMouseExitedAction(() -> {
			if(panelScambio.getBackground().equals(Color.LIGHT_GRAY)) {
				panelScambio.setBackground(Color.WHITE);
			}
		});
		
		lblRegalo.setOnMouseClickedAction(() -> {
			panelRegalo.setBackground(MyJPanel.uninaLightColor);
			panelVendita.setBackground(Color.WHITE);
			panelScambio.setBackground(Color.WHITE);
			mostraAnnunciDiRegaloSulCentrale(annunciRegalo);
		});
		lblRegalo.setOnMouseEnteredAction(() -> {
			if(panelRegalo.getBackground().equals(Color.WHITE)) {
				panelRegalo.setBackground(Color.LIGHT_GRAY);
			}
		});
		lblRegalo.setOnMouseExitedAction(() -> {
			if(panelRegalo.getBackground().equals(Color.LIGHT_GRAY)) {
				panelRegalo.setBackground(Color.WHITE);
			}
		});
		
		panelVendita.add(lblVendita);
		panelScambio.add(lblScambio);
		panelRegalo.add(lblRegalo);
		
		panelSuperiore.add(panelVendita);
		panelSuperiore.add(panelScambio);
		panelSuperiore.add(panelRegalo);
	}
	
	private void settaPanelCentrale(String messaggioAllUtente) {
		panelCentrale.setLayout(new FlowLayout(FlowLayout.CENTER));
		MyJLabel messaggio = new MyJLabel(messaggioAllUtente, new Font("Ubuntu Sans", Font.ITALIC, 16));
		messaggio.setForeground(Color.BLACK);
		panelCentrale.add(messaggio);
		panelCentrale.setBackground(MyJPanel.uninaLightColor);
	}
	
	protected abstract void mostraAnnunciDiVenditaSulCentrale(ArrayList<Annuncio> annunciToDisplay);
	
	protected abstract void mostraAnnunciDiScambioSulCentrale(ArrayList<Annuncio> annunciToDisplay);

	protected abstract void mostraAnnunciDiRegaloSulCentrale(ArrayList<Annuncio> annunciToDisplay);
	
	protected void ricalcolaAltezzaConAnnunci(ArrayList<Annuncio> annunciMostrati) {
		int larghezza = panelCentrale.getWidth();
		//600 è l'altezza di un singolo panel dell'annuncio. 10 sono dei pixel aggiuntivi
		int altezza = (annunciMostrati.size() / 2 == 0) ? (annunciMostrati.size()/2 * 610) : ((annunciMostrati.size()/2+1) * 610);
		
		panelCentrale.setPreferredSize(new Dimension(larghezza, altezza));
		panelCentrale.setMaximumSize(new Dimension(larghezza, altezza));
	}

}