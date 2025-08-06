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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);
//		contentPane.setLayout(new BoxLayout(contentPane, defaultCloseOperation));
		this.setResizable(false);
		this.setTitle("Accesso");
		setContentPane(contentPane);
		
		//Logo UninaSwap
		JLabel logoUninaSwap = new JLabel();
		ImageIcon logoDaScalare = new ImageIcon("images/logo_uninaswap.png");
		Image resizedLogo = logoDaScalare.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
		ImageIcon logoUS = new ImageIcon(resizedLogo);
		logoUninaSwap.setIcon(logoUS);
		logoUninaSwap.setVerticalAlignment(JLabel.CENTER);
		logoUninaSwap.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(logoUninaSwap);
		
		
		//Label per indicare di inserire la mail
		JLabel email = new JLabel("Email o username");
		contentPane.add(email);

		//TextField in cui inserire la mail
		JTextField inserisciEmail = new JTextField(20);
		inserisciEmail.setBounds(50, 50, 50, 50);
		contentPane.add(inserisciEmail);
		
		this.setVisible(true);
	}

}
