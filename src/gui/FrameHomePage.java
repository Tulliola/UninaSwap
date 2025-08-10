package gui;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.PanelHomePageAnnunci;
import gui.PanelHomePageLateraleSx;
import gui.PanelHomePageSuperiore;
import utilities.MyJPanel;
import controller.Controller;
import dto.ProfiloUtente;

public class FrameHomePage extends JFrame {

	private static final long serialVersionUID = 1L;
	private MyJPanel contentPane;
	private PanelHomePageAnnunci panelAnnunci;
	private PanelHomePageLateraleSx panelLateraleSx;
	private PanelHomePageSuperiore panelSuperiore;
	private Controller mainController;

	public FrameHomePage(Controller controller, ProfiloUtente utenteLoggato) {
		mainController = controller;
		settaContentPane(utenteLoggato);
	}

	private void settaContentPane(ProfiloUtente utenteLoggato) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(new Rectangle(800, 800));
		this.setLocationRelativeTo(null);
		this.setTitle("Il mercatino digitale federiciano");
		
		contentPane = new MyJPanel();
		contentPane.setLayout(new BorderLayout());
		
		panelAnnunci = new PanelHomePageAnnunci();
		panelLateraleSx = new PanelHomePageLateraleSx();
		panelSuperiore = new PanelHomePageSuperiore(contentPane, utenteLoggato);
		
		contentPane.add(panelAnnunci, BorderLayout.CENTER);
		contentPane.add(panelLateraleSx, BorderLayout.WEST);
		contentPane.add(panelSuperiore, BorderLayout.NORTH);
		
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		setContentPane(contentPane);
	}
}
