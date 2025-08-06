package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;


import controller.Controller;

public class FrameDiRegistrazione extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Controller mainController;
	
	public FrameDiRegistrazione(Controller controller) {
		mainController = controller;
		
		this.impostaSettingsPerFrame();
		contentPane = new JPanel();
		contentPane.setBackground(new Color(160, 181, 200));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		
		this.prova();
	}
	
	private void impostaSettingsPerFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Registrati ora!");
		this.setSize(800, 800);
		this.setLocationRelativeTo(null);

	}
}
