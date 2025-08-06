package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.Controller;

public class FrameDiLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Controller mainController;
	
	public FrameDiLogin(Controller controller) {
		mainController = controller;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(800, 800));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Accesso");
		setContentPane(contentPane);
		
//		//Logo UninaSwap
//		JLabel logoUninaSwap = new JLabel();
//		ImageIcon logoDaScalare = new ImageIcon("images/logo_uninaswap.png");
//		Image resizedLogo = logoDaScalare.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
//		ImageIcon logoUS = new ImageIcon(resizedLogo);
//		logoUninaSwap.setIcon(logoUS);
//		logoUninaSwap.setAlignmentX(Box.LEFT_ALIGNMENT);
//		logoUninaSwap.setAlignmentY(Box.LEFT_ALIGNMENT);
//		contentPane.add(logoUninaSwap);
//		
//		creaFieldAccesso(new JLabel("Email o username"), new JTextField());
//		
//		//Label per indicare di inserire la mail
//		JLabel email = new JLabel("Email o username");
//		email.setBounds(EXIT_ON_CLOSE, ABORT, WIDTH, HEIGHT);
//		email.setAlignmentX(Box.LEFT_ALIGNMENT);
//		email.setAlignmentY(Box.CENTER_ALIGNMENT);
//		contentPane.add(email);
//
//		//TextField in cui inserire la mail
//		JTextField inserisciEmail = new JTextField();
//		inserisciEmail.setMaximumSize(new Dimension(275, 30));
//		inserisciEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
//		inserisciEmail.getHorizontalAlignment();
//		System.out.println(inserisciEmail.getHorizontalAlignment());
//		
//		contentPane.add(inserisciEmail);
		
	}
	
	
	private void creaFieldAccesso(JLabel label, JTextField text) {
		JPanel pannelloFieldCorrente = new JPanel();
		
		//TODO aggiustare questo metodo
		pannelloFieldCorrente.add(label);
		pannelloFieldCorrente.add(text);
		
		contentPane.add(pannelloFieldCorrente);
	}
	
	private void aggiungiImmagine(ImageIcon img) {
		//TODO creare questo metodo
	}

}
