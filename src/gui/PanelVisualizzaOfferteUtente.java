package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import java.util.ArrayList;

import controller.Controller;

import dto.Offerta;
import dto.OffertaAcquisto;
import dto.OffertaRegalo;
import dto.OffertaScambio;
import net.miginfocom.swing.MigLayout;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;

public abstract class PanelVisualizzaOfferteUtente extends MyJPanel {

	private static final long serialVersionUID = 1L;
	
	protected JScrollPane scrollPane;
	protected Controller mainController;
	protected MyJPanel panelSuperiore = new MyJPanel();
	protected MyJPanel panelCentrale = new MyJPanel();
	protected MyJPanel panelOfferteAcquisto = new MyJPanel();
	protected MyJPanel panelOfferteScambio = new MyJPanel();
	protected MyJPanel panelOfferteRegalo = new MyJPanel();
	protected MyJPanel panelDefault = new MyJPanel();

	private MyJLabel messaggio;

	private boolean isScrollPaneCentraleOfferteAcquisto;

	private boolean isScrollPaneCentraleOfferteScambio;

	private boolean isScrollPaneCentraleOfferteRegalo;
	
	public PanelVisualizzaOfferteUtente(ArrayList<Offerta> offerteToDisplay, String messaggioAllUtente, MyJFrame parentFrame, Controller controller) {
		this.mainController = controller;
		this.setLayout(new BorderLayout());
		
		scrollPane = new JScrollPane(panelCentrale);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
		scrollPane.addMouseWheelListener(e -> {
			if (!scrollPane.isWheelScrollingEnabled()) {
		        return; 
		    }
			
			JScrollBar barraVerticale = scrollPane.getVerticalScrollBar();
			JScrollBar barraOrizzontale = scrollPane.getHorizontalScrollBar();
			
		    if (e.isShiftDown()) {
		        barraOrizzontale.setValue(barraOrizzontale.getValue() + e.getUnitsToScroll() * barraOrizzontale.getUnitIncrement());
		    } else {
		        barraVerticale.setValue(barraVerticale.getValue() + e.getUnitsToScroll() * barraVerticale.getUnitIncrement());
		    }
		});
		parentFrame.add(scrollPane);
		
		settaPanelCentrale(messaggioAllUtente, offerteToDisplay);
		settaPanelSuperiore();
		
		this.add(panelSuperiore, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
	}
	
	private void settaPanelSuperiore() {
		panelSuperiore.setLayout(new BoxLayout(panelSuperiore, BoxLayout.X_AXIS));
		
		isScrollPaneCentraleOfferteAcquisto = panelOfferteAcquisto.hasAnnunciPanels();
		isScrollPaneCentraleOfferteScambio = panelOfferteScambio.hasAnnunciPanels();
		isScrollPaneCentraleOfferteRegalo = panelOfferteRegalo.hasAnnunciPanels();
			
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
			((CardLayout)panelCentrale.getLayout()).show(panelCentrale, "Offerte acquisto");
			SwingUtilities.invokeLater(() -> {
				settaScrollabilita(isScrollPaneCentraleOfferteAcquisto);			
			});		
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
			((CardLayout)panelCentrale.getLayout()).show(panelCentrale, "Offerte scambio");
			SwingUtilities.invokeLater(() -> {
				settaScrollabilita(isScrollPaneCentraleOfferteScambio);			
			});
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
			((CardLayout)panelCentrale.getLayout()).show(panelCentrale, "Offerte regalo");
			SwingUtilities.invokeLater(() -> {
				settaScrollabilita(isScrollPaneCentraleOfferteRegalo);			
			});
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

	private void settaPanelCentrale(String messaggioAllUtente, ArrayList<Offerta> offerteToDisplay) {
		panelCentrale.setLayout(new CardLayout());
		
		panelOfferteAcquisto = new MyJPanel();
		panelOfferteAcquisto.setBackground(uninaLightColor);
		panelOfferteScambio = new MyJPanel();
		panelOfferteScambio.setBackground(uninaLightColor);
		panelOfferteRegalo = new MyJPanel();
		panelOfferteRegalo.setBackground(uninaLightColor);
		
		panelOfferteAcquisto.setLayout(new MigLayout("wrap 2", "[]", ""));
		panelOfferteScambio.setLayout(new MigLayout("wrap 2", "[]", ""));
		panelOfferteRegalo.setLayout(new MigLayout("wrap 2", "[]", ""));
		
		panelDefault.setLayout(new BoxLayout(panelDefault, BoxLayout.Y_AXIS));
		panelDefault.setPreferredSize(scrollPane.getViewport().getSize());
		panelDefault.setBackground(uninaLightColor);			
		
		ArrayList<Offerta> offerteAcquisto = new ArrayList<Offerta>();
		ArrayList<Offerta> offerteScambio = new ArrayList<Offerta>();
		ArrayList<Offerta> offerteRegalo = new ArrayList<Offerta>();
		
		for(Offerta offerta: offerteToDisplay) {
			if(offerta instanceof OffertaAcquisto)
				offerteAcquisto.add(offerta);
			else if(offerta instanceof OffertaScambio)
				offerteScambio.add(offerta);
			else if(offerta instanceof OffertaRegalo)
				offerteRegalo.add(offerta);
		}
		
		settaPanelOfferteAcquisto(offerteAcquisto);
		settaPanelOfferteScambio(offerteScambio);
		settaPanelOfferteRegalo(offerteRegalo);
		
		panelCentrale.add(panelDefault, "Default");
		((CardLayout) panelCentrale.getLayout()).show(panelCentrale, "Default");
		panelCentrale.add(panelOfferteAcquisto, "Offerte acquisto");
		panelCentrale.add(panelOfferteScambio, "Offerte scambio");
		panelCentrale.add(panelOfferteRegalo, "Offerte regalo");
		
		messaggio = new MyJLabel(messaggioAllUtente, new Font("Ubuntu Sans", Font.ITALIC, 16));
		messaggio.setForeground(Color.BLACK);
		
		SwingUtilities.invokeLater(() -> {
			scrollPane.getViewport().setViewPosition(this.calcolaPosizioneCentralePerScrollPane());			
			scrollPane.setWheelScrollingEnabled(false);
		});
		
		panelCentrale.add(messaggio);
		panelCentrale.setBackground(uninaLightColor);
	}


	protected abstract void settaPanelOfferteAcquisto(ArrayList<Offerta> offerteToDisplay);
	protected abstract void settaPanelOfferteScambio(ArrayList<Offerta> offerteToDisplay);
	protected abstract void settaPanelOfferteRegalo(ArrayList<Offerta> offerteToDisplay);
	
	private void settaScrollabilita(boolean isScrollPaneCentrale) {
		if(isScrollPaneCentrale) {
			scrollPane.getViewport().setViewPosition(new Point(0, 0));
			scrollPane.setWheelScrollingEnabled(true);
		}
		else {

			scrollPane.setWheelScrollingEnabled(false);
			scrollPane.getViewport().setViewPosition(this.calcolaPosizioneCentralePerScrollPane());			
		}
	}
	
	private Point calcolaPosizioneCentralePerScrollPane() {
			Rectangle boundLabelCentrale = messaggio.getBounds();
			Dimension extent = scrollPane.getViewport().getExtentSize();
			
			int x = boundLabelCentrale.x - (extent.width - boundLabelCentrale.width) / 2;
			int y = boundLabelCentrale.y - (extent.height - boundLabelCentrale.height) / 2;
	
			x = Math.max(0, x);
			y = Math.max(0, y);
			
			return new Point(x,y);
	}
}
