package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
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
import javax.swing.JScrollBar;
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
import net.miginfocom.swing.MigLayout;
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
	protected MyJPanel panelAnnunciVendita = new MyJPanel();
	protected MyJPanel panelAnnunciScambio = new MyJPanel();
	protected MyJPanel panelAnnunciRegalo = new MyJPanel();
	protected JScrollPane scrollPane;
	protected MyJPanel panelDefault = new MyJPanel();
	protected boolean isScrollPaneCentraleAnnunciVendita;
	protected boolean isScrollPaneCentraleAnnunciScambio;
	protected boolean isScrollPaneCentraleAnnunciRegalo;
	protected MyJLabel lblMessaggioUtente;
	protected MyJFrame parentFrame;
	
	public PanelVisualizzaAnnunciUtente(Controller mainController, ArrayList<Annuncio> annunciToDisplay, String messaggioAllUtente, MyJFrame parentFrame) {
		this.mainController = mainController;
		this.parentFrame = parentFrame;
		
		this.setLayout(new BorderLayout());
		
		scrollPane = new JScrollPane(panelCentrale);
		
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);	
//		scrollPane.getViewport().setViewPosition(new Point(0, 0));
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
		
		settaPanelCentrale(messaggioAllUtente, annunciToDisplay);
		settaPanelSuperiore();
		
		this.add(panelSuperiore, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
	}


	private void settaPanelSuperiore() {
		panelSuperiore.setLayout(new BoxLayout(panelSuperiore, BoxLayout.X_AXIS));
		
		isScrollPaneCentraleAnnunciVendita = panelAnnunciVendita.hasAnnunciPanels();
		isScrollPaneCentraleAnnunciScambio = panelAnnunciScambio.hasAnnunciPanels();
		isScrollPaneCentraleAnnunciRegalo = panelAnnunciRegalo.hasAnnunciPanels();
		
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
				
		lblVendita.setOnMouseClickedAction(() -> {
			panelVendita.setBackground(MyJPanel.uninaLightColor);
			panelScambio.setBackground(Color.WHITE);
			panelRegalo.setBackground(Color.WHITE);
			((CardLayout) panelCentrale.getLayout()).show(panelCentrale, "Annunci vendita");
			SwingUtilities.invokeLater(() -> {
				settaScrollabilita(isScrollPaneCentraleAnnunciVendita);
			});
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
			((CardLayout) panelCentrale.getLayout()).show(panelCentrale, "Annunci scambio");
			SwingUtilities.invokeLater(() -> {
				settaScrollabilita(isScrollPaneCentraleAnnunciScambio);
			});
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
			((CardLayout) panelCentrale.getLayout()).show(panelCentrale, "Annunci regalo");
			SwingUtilities.invokeLater(() -> {
				settaScrollabilita(isScrollPaneCentraleAnnunciRegalo);			
			});
			
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
	
	private void settaPanelCentrale(String messaggioAllUtente, ArrayList<Annuncio> annunciToDisplay) {
		panelCentrale.setLayout(new CardLayout());
		
		panelAnnunciVendita.setLayout(new MigLayout("wrap 2", "", ""));
		panelAnnunciVendita.setBackground(uninaLightColor);
		panelAnnunciScambio.setLayout(new MigLayout("wrap 2", "", ""));
		panelAnnunciScambio.setBackground(uninaLightColor);
		panelAnnunciRegalo.setLayout(new MigLayout("wrap 2", "", ""));
		panelAnnunciRegalo.setBackground(uninaLightColor);
		
		panelDefault.setLayout(new BoxLayout(panelDefault, BoxLayout.Y_AXIS));
		panelDefault.setPreferredSize(scrollPane.getViewport().getSize());
		panelDefault.setBackground(uninaLightColor);
		
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

		
		settaPanelAnnunciVendita(annunciVendita);
		settaPanelAnnunciScambio(annunciScambio);
		settaPanelAnnunciRegalo(annunciRegalo);
		
		panelCentrale.add(panelDefault, "Default");
		((CardLayout) panelCentrale.getLayout()).show(panelCentrale, "Default");
		
		panelCentrale.add(panelAnnunciVendita, "Annunci vendita");
		panelCentrale.add(panelAnnunciScambio, "Annunci scambio");
		panelCentrale.add(panelAnnunciRegalo, "Annunci regalo");
		
		lblMessaggioUtente = new MyJLabel(messaggioAllUtente, new Font("Ubuntu Sans", Font.ITALIC, 16));
		lblMessaggioUtente.setForeground(Color.BLACK);

		SwingUtilities.invokeLater(() -> {
			scrollPane.getViewport().setViewPosition(this.calcolaPosizioneCentralePerScrollPane());			
			scrollPane.setWheelScrollingEnabled(false);
		});
		
		panelCentrale.add(lblMessaggioUtente);
		panelCentrale.setBackground(MyJPanel.uninaLightColor);
	}
	
	
	protected abstract void settaPanelAnnunciVendita(ArrayList<Annuncio> annunciToDisplay);


	protected abstract void settaPanelAnnunciScambio(ArrayList<Annuncio> annunciToDisplay);


	protected abstract void settaPanelAnnunciRegalo(ArrayList<Annuncio> annunciToDisplay);
	
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
			Rectangle boundLabelCentrale = lblMessaggioUtente.getBounds();
			Dimension extent = scrollPane.getViewport().getExtentSize();
			
			int x = boundLabelCentrale.x - (extent.width - boundLabelCentrale.width) / 2;
			int y = boundLabelCentrale.y - (extent.height - boundLabelCentrale.height) / 2;
	
			x = Math.max(0, x);
			y = Math.max(0, y);
			
			return new Point(x,y);
	}
}