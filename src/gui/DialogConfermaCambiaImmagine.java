package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import utilities.MyJButton;
import utilities.MyJDialog;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class DialogConfermaCambiaImmagine extends MyJDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPane = new JPanel();
	private Controller mainController;
	
	public DialogConfermaCambiaImmagine(JFrame framePadre, Controller controller, byte[] immagineProfilo) {
		mainController = controller;
		
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		contentPane.setLayout(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		contentPane.add(this.creaPanelMessaggio(), BorderLayout.CENTER);
		contentPane.add(this.creaPanelBottoni(immagineProfilo), BorderLayout.SOUTH);
		
		this.setContentPane(contentPane);
		
		this.pack();
	}

	private MyJPanel creaPanelMessaggio() {
		MyJPanel panelMessaggio = new MyJPanel();
		MyJLabel lblMessaggio = new MyJLabel("Sei sicuro di voler cambiare l'immagine?", new Font("Ubuntu Sans", Font.BOLD, 16));
		
		panelMessaggio.add(lblMessaggio);
		
		return panelMessaggio;
	}
	
	private MyJPanel creaPanelBottoni(byte[] immagineProfilo) {
		MyJPanel panelBottone = new MyJPanel();
		panelBottone.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		MyJButton bottoneConferma = new MyJButton("Conferma");
		bottoneConferma.setDefaultAction(() -> {
			try {
				mainController.onConfermaCambiaImmagineButton(immagineProfilo);
			}
			catch(SQLException exc) {
				exc.printStackTrace();
			}
		});
		bottoneConferma.setFocusable(false);
		
		MyJButton bottoneCiHoRipensato = new MyJButton("Ci ho ripensato...");
		bottoneCiHoRipensato.setDefaultAction(() -> {
			mainController.tornaAFrameCambiaImmagine(this);
		});
		bottoneCiHoRipensato.setFocusable(false);
		
		panelBottone.add(bottoneCiHoRipensato);
		panelBottone.add(bottoneConferma);
		
		return panelBottone;
	}
}
