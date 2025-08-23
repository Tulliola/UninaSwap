package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;

import controller.Controller;
import dto.Annuncio;
import utilities.MyJButton;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.StatoAnnuncioEnum;

public class DialogConfermaRimozioneAnnuncio extends JDialog {

	private static final long serialVersionUID = 1L;
	private Controller mainController;
	
	MyJLabel lblMessaggio = new MyJLabel("Sei sicuro di voler rimuovere l'annuncio?");
	
	PanelHomePageSuperiore panelSuperiore = new PanelHomePageSuperiore(this, "UninaSwap");
	MyJPanel panelDx = new MyJPanel();
	MyJPanel panelSx = new MyJPanel();
	MyJPanel panelCentrale = new MyJPanel();
	MyJPanel panelInferiore = new MyJPanel();
	
	MyJButton annullaButton = new MyJButton("Annulla");
	MyJButton siButton = new MyJButton("Sì");
	
	public DialogConfermaRimozioneAnnuncio(Controller controller, MyJFrame parent, Annuncio annuncio) {
		this.mainController = controller;
		
		this.setModal(true);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.setSize(400, 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Rimuovere annuncio?");

		this.add(panelSuperiore, BorderLayout.NORTH);
		this.add(settaPanelSx(), BorderLayout.WEST);
		this.add(settaPanelDx(), BorderLayout.EAST);
		settaPanelInferiore(annuncio);
		this.add(settaPanelCentrale(), BorderLayout.CENTER);
		
		
		this.setLocationRelativeTo(parent);
	}

	private MyJPanel settaPanelInferiore(Annuncio annuncioToRemove) {
		panelInferiore.setLayout(new BoxLayout(panelInferiore, BoxLayout.X_AXIS));
		panelInferiore.setSize(this.WIDTH, 100);
		
		annullaButton.setDefaultAction(() -> {
			mainController.chiudDialogConfermaRimozioneAnnuncio(false);
		});
		annullaButton.setUpAction(()->{});
		annullaButton.setDownAction(()->{});
		
		siButton.setDefaultAction(() -> {
			mainController.aggiornaStatoAnnuncio(annuncioToRemove, StatoAnnuncioEnum.Rimosso);
			mainController.chiudDialogConfermaRimozioneAnnuncio(true);
		});
		
		panelInferiore.add(Box.createHorizontalGlue());
		panelInferiore.add(annullaButton);
		panelInferiore.add(Box.createHorizontalStrut(20));
		panelInferiore.add(siButton);
		panelInferiore.add(Box.createHorizontalGlue());
		
		return this.panelInferiore;
	}

	private MyJPanel settaPanelCentrale() {
		panelCentrale.setLayout(new BoxLayout(panelCentrale, BoxLayout.Y_AXIS));
		
		panelCentrale.add(Box.createVerticalGlue());
		panelCentrale.add(lblMessaggio);
		lblMessaggio.setAlignmentX(CENTER_ALIGNMENT);
		panelCentrale.add(Box.createVerticalGlue());
		panelCentrale.add(panelInferiore);
		return this.panelCentrale;
	}

	private MyJPanel settaPanelDx() {
		panelDx.setSize(50, this.getHeight()-100);
		panelDx.setBackground(MyJPanel.uninaColor);
		
		return this.panelDx;
	}

	private MyJPanel settaPanelSx() {
		panelSx.setSize(50, this.getHeight()-100);
		panelSx.setBackground(MyJPanel.uninaColor);
	
		return this.panelSx;
	}
}
