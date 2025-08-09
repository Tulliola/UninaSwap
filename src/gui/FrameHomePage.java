package gui;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;

public class FrameHomePage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Controller mainController;

	public FrameHomePage(Controller contr) {
		mainController = contr;
		settaContentPane();
	}

	private void settaContentPane() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(new Rectangle(800, 800));
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}
}
