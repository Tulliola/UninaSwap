package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.Oggetto;
import utilities.MyJDialog;
import utilities.MyJLabel;
import utilities.MyJOggettoPanel;
import utilities.MyJPanel;

public class DialogVisualizzaOggetti extends MyJDialog {

	private static final long serialVersionUID = 1L;
	private final MyJPanel contentPane = new MyJPanel();
	private Controller mainController;
	private Color coloreCasualePerBG;
	private MyJPanel panelFrecciaSx;
	private MyJPanel panelCentrale;
	private MyJPanel panelFrecciaDx;
	private MyJPanel[] panelPerOggetti;
	
	private int panelCorrente;
	
	private int larghezza = 1400;
	private int altezza = 680;
	
	public DialogVisualizzaOggetti(Controller controller, ArrayList<Oggetto> oggettiDaMostrare) {
		mainController = controller;
		
		this.setSize(new Dimension(larghezza, altezza));
		this.setBackground(coloreCasualePerBG);
		this.setModal(true);
		this.setTitle("Questi sono gli oggetti che ti sono stati offerti. Prenditi il tempo per valutare e decidere se accettare o rifiutare l'offerta!");
		
		contentPane.setLayout(new BorderLayout());
		contentPane.setBackground(coloreCasualePerBG);
		contentPane.add(this.creaPanelFrecciaSx(oggettiDaMostrare.size()), BorderLayout.WEST);
		contentPane.add(this.creaPanelCentrale(oggettiDaMostrare), BorderLayout.CENTER);
		contentPane.add(this.creaPanelFrecciaDx(oggettiDaMostrare.size()), BorderLayout.EAST);
		
		this.add(contentPane);
		this.setLocationRelativeTo(null);
	}
	
	private MyJPanel creaPanelFrecciaSx(int numOggettiCaricati) {
		panelFrecciaSx = new MyJPanel();
		panelFrecciaSx.setBackground(Color.white);
		panelFrecciaSx.setLayout(new BoxLayout(panelFrecciaSx, BoxLayout.Y_AXIS));
		panelFrecciaSx.setPreferredSize(new Dimension(100, altezza));
		panelFrecciaSx.setMaximumSize(new Dimension(100, altezza));
		
		MyJLabel lblFrecciaSx = new MyJLabel();
		lblFrecciaSx.aggiungiImmagineScalata("images/iconaTornaIndietroFoto.png", 50, 50, true);
		if(numOggettiCaricati == 1)
			lblFrecciaSx.setVisible(false);		
		lblFrecciaSx.setAlignmentX(CENTER_ALIGNMENT);
		
		lblFrecciaSx.rendiLabelInteragibile();
		lblFrecciaSx.setOnMouseEnteredAction(() -> {});
		lblFrecciaSx.setOnMouseExitedAction(() -> {});
		lblFrecciaSx.setOnMouseClickedAction(() -> {
			if(panelCorrente == 0)
				panelCorrente = numOggettiCaricati - 1;
			else
				panelCorrente--;
			
			((CardLayout) panelCentrale.getLayout()).show(panelCentrale, "CardPanel"+panelCorrente);
		});
		
		panelFrecciaSx.add(Box.createVerticalGlue());
		panelFrecciaSx.add(lblFrecciaSx);
		panelFrecciaSx.add(Box.createVerticalGlue());
		
		return panelFrecciaSx;
	}

	private MyJPanel creaPanelCentrale(ArrayList<Oggetto> oggettiDaMostrare) {
		panelCentrale = new MyJPanel();
		panelCentrale.setBackground(coloreCasualePerBG);
		panelCentrale.setPreferredSize(new Dimension(larghezza - 200, altezza));
		panelCentrale.setMaximumSize(new Dimension(larghezza - 200, altezza));
		
		panelCentrale.setLayout(new CardLayout());
		
		panelCorrente = 0;
		panelPerOggetti = new MyJPanel[oggettiDaMostrare.size()];
		
		for(int i = 0; i < panelPerOggetti.length; i++) {
			panelPerOggetti[i] = new MyJPanel();
			panelPerOggetti[i].add(new MyJOggettoPanel(oggettiDaMostrare.get(i)));
			
			panelCentrale.add(panelPerOggetti[i], "CardPanel"+i);
		}
		
		return panelCentrale;
	}
	
	private MyJPanel creaPanelFrecciaDx(int numOggettiCaricati) {
		panelFrecciaDx = new MyJPanel();
		panelFrecciaDx.setBackground(Color.white);
		panelFrecciaDx.setLayout(new BoxLayout(panelFrecciaDx, BoxLayout.Y_AXIS));
		panelFrecciaDx.setPreferredSize(new Dimension(100, altezza));
		panelFrecciaDx.setMaximumSize(new Dimension(100, altezza));
		
		MyJLabel lblFrecciaDx = new MyJLabel();
		lblFrecciaDx.aggiungiImmagineScalata("images/iconaVaiAvantiFoto.png", 50, 50, true);
		if(numOggettiCaricati == 1)
			lblFrecciaDx.setVisible(false);
		lblFrecciaDx.setAlignmentX(CENTER_ALIGNMENT);
		
		lblFrecciaDx.rendiLabelInteragibile();
		lblFrecciaDx.setOnMouseEnteredAction(() -> {});
		lblFrecciaDx.setOnMouseExitedAction(() -> {});
		lblFrecciaDx.setOnMouseClickedAction(() -> {
			if(panelCorrente == numOggettiCaricati-1)
				panelCorrente = 0;
			else
				panelCorrente++;	
			
			((CardLayout) panelCentrale.getLayout()).show(panelCentrale, "CardPanel"+panelCorrente);
		});
		
		panelFrecciaDx.add(Box.createVerticalGlue());
		panelFrecciaDx.add(lblFrecciaDx);
		panelFrecciaDx.add(Box.createVerticalGlue());
		
		return panelFrecciaDx;
	}
}
