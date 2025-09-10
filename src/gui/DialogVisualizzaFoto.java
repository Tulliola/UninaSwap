package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controller.Controller;
import utilities.MyJDialog;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class DialogVisualizzaFoto extends MyJDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPane = new JPanel();
	
	private Color coloreTrasparente = new Color(0, 0, 0, 0);
	private Controller mainController;
	private Color coloreCasualePerBG;
	private MyJPanel panelFrecciaSx;
	private MyJPanel panelCentrale;
	private MyJPanel panelFrecciaDx;
	private MyJPanel[] panelPerFoto;
	
	private int panelCorrente;
	
	private int larghezza = 800;
	private int altezza = 800;
	
	public DialogVisualizzaFoto(Controller controller, ArrayList<byte[]> fotoDaMostrare) {
		mainController = controller;
		
		this.setSize(new Dimension(larghezza, altezza));
		this.setBackground(coloreCasualePerBG);
		this.setModal(true);
		this.setTitle("Dai un'occhiata alle foto dell'annuncio!");
		
		contentPane.setLayout(new BorderLayout());
		contentPane.setBackground(coloreTrasparente);
		contentPane.add(this.creaPanelFrecciaSx(fotoDaMostrare.size()), BorderLayout.WEST);
		contentPane.add(this.creaPanelCentrale(fotoDaMostrare), BorderLayout.CENTER);
		contentPane.add(this.creaPanelFrecciaDx(fotoDaMostrare.size()), BorderLayout.EAST);
		
		this.add(contentPane);
		this.setLocationRelativeTo(null);
	}
	
	private MyJPanel creaPanelFrecciaSx(int numFotoCaricate) {
		panelFrecciaSx = new MyJPanel();
		panelFrecciaSx.setOpaque(true);
		panelFrecciaSx.setBackground(coloreTrasparente);
		panelFrecciaSx.setLayout(new BoxLayout(panelFrecciaSx, BoxLayout.Y_AXIS));
		panelFrecciaSx.setPreferredSize(new Dimension(100, altezza));
		panelFrecciaSx.setMaximumSize(new Dimension(100, altezza));
		
		MyJLabel lblFrecciaSx = new MyJLabel();
		lblFrecciaSx.aggiungiImmagineScalata("images/iconaTornaIndietroFoto.png", 50, 50, true);
		if(numFotoCaricate == 1)
			lblFrecciaSx.setVisible(false);		
		lblFrecciaSx.setAlignmentX(CENTER_ALIGNMENT);
		
		lblFrecciaSx.rendiLabelInteragibile();
		lblFrecciaSx.setOnMouseEnteredAction(() -> {});
		lblFrecciaSx.setOnMouseExitedAction(() -> {});
		lblFrecciaSx.setOnMouseClickedAction(() -> {
			if(panelCorrente == 0)
				panelCorrente = numFotoCaricate - 1;
			else
				panelCorrente--;
			
			((CardLayout) panelCentrale.getLayout()).show(panelCentrale, "CardPanel"+panelCorrente);
		});
		
		panelFrecciaSx.add(Box.createVerticalGlue());
		panelFrecciaSx.add(lblFrecciaSx);
		panelFrecciaSx.add(Box.createVerticalGlue());
		
		return panelFrecciaSx;
	}

	private MyJPanel creaPanelCentrale(ArrayList<byte[]> fotoDaMostrare) {
		panelCentrale = new MyJPanel();
		panelCentrale.setBackground(coloreCasualePerBG);
		panelCentrale.setPreferredSize(new Dimension(larghezza - 200, altezza));
		panelCentrale.setMaximumSize(new Dimension(larghezza - 200, altezza));
		
		panelCentrale.setLayout(new CardLayout());
		
		panelCorrente = 0;
		panelPerFoto = new MyJPanel[fotoDaMostrare.size()];
		
		for(int i = 0; i < panelPerFoto.length; i++) {
			ImageIcon fotoAttuale = new ImageIcon(fotoDaMostrare.get(i));
			panelPerFoto[i] = new MyJPanel(fotoAttuale.getImage());
			
			panelCentrale.add(panelPerFoto[i], "CardPanel"+i);
		}
		
		return panelCentrale;
	}
	
	private MyJPanel creaPanelFrecciaDx(int numFotoCaricate) {
		panelFrecciaDx = new MyJPanel();
		panelFrecciaDx.setBackground(coloreTrasparente);
		panelFrecciaDx.setLayout(new BoxLayout(panelFrecciaDx, BoxLayout.Y_AXIS));
		panelFrecciaDx.setPreferredSize(new Dimension(100, altezza));
		panelFrecciaDx.setMaximumSize(new Dimension(100, altezza));
		
		MyJLabel lblFrecciaDx = new MyJLabel();
		lblFrecciaDx.aggiungiImmagineScalata("images/iconaVaiAvantiFoto.png", 50, 50, true);
		if(numFotoCaricate == 1)
			lblFrecciaDx.setVisible(false);
		lblFrecciaDx.setAlignmentX(CENTER_ALIGNMENT);
		
		lblFrecciaDx.rendiLabelInteragibile();
		lblFrecciaDx.setOnMouseEnteredAction(() -> {});
		lblFrecciaDx.setOnMouseExitedAction(() -> {});
		lblFrecciaDx.setOnMouseClickedAction(() -> {
			if(panelCorrente == numFotoCaricate-1)
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
