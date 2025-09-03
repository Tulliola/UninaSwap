package gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;


import controller.Controller;
import utilities.MyJButton;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class DialogConfermaLogout extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	Controller mainController;
	
	public DialogConfermaLogout(Controller mainController, JFrame framePadre) {
		super(framePadre, "Logout", true);
		this.mainController = mainController;
		settaDialog(framePadre);
	}

	private void settaDialog(JFrame framePadre) {
		this.setLayout(new BorderLayout());
		MyJPanel contentPane = new MyJPanel();
		contentPane.setLayout(new BorderLayout());
		
		MyJPanel panelMessaggio = new MyJPanel();
		MyJLabel lblMessaggio = new MyJLabel("Sei sicuro di voler effettuare il logout?", new Font("Ubuntu Sans", Font.BOLD, 16));
		panelMessaggio.add(lblMessaggio);
		
		MyJPanel panelBottoni = new MyJPanel();

		MyJButton okButton = new MyJButton("OK");
		
		panelBottoni.add(okButton);
		MyJButton tornaIndietroButton = new MyJButton("Torna indietro");
		
		okButton.setDefaultAction(() -> {
			mainController.logout();
		});
		okButton.setUpAction(() -> {});
		okButton.setDownAction(() -> {
			tornaIndietroButton.requestFocus();
		});
		
		tornaIndietroButton.setDefaultAction(() -> {
			mainController.chiudiDialogConfermaLogout();
		});
		tornaIndietroButton.setUpAction(() -> {
			okButton.requestFocus();
		});
		tornaIndietroButton.setDownAction(() -> {});
		panelBottoni.add(tornaIndietroButton);
		
		contentPane.add(panelMessaggio, BorderLayout.CENTER);
		contentPane.add(panelBottoni, BorderLayout.SOUTH);
		
		this.setContentPane(contentPane);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(framePadre);
	}
}
