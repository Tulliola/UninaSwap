package gui;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import gui.PanelHomePageAnnunci;
import gui.PanelHomePageLateraleSx;
import gui.PanelHomePageSuperiore;
import utilities.MyJFrame;
import utilities.MyJPanel;
import controller.Controller;
import dto.ProfiloUtente;

public class FrameHomePage extends MyJFrame {

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
	
	public Controller getController() {
		return mainController;
	}

	private void settaContentPane(ProfiloUtente utenteLoggato) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(new Rectangle(1200, 1000));
		this.setLocationRelativeTo(null);
		this.setTitle("Il mercatino digitale federiciano");
		
		contentPane = new MyJPanel();
		contentPane.setLayout(new BorderLayout());
		
		panelAnnunci = new PanelHomePageAnnunci();
		panelLateraleSx = new PanelHomePageLateraleSx(contentPane, mainController);
		panelSuperiore = new PanelHomePageSuperiore(this, utenteLoggato);
		
		contentPane.add(panelLateraleSx, BorderLayout.WEST);
		contentPane.add(panelAnnunci, BorderLayout.CENTER);
		contentPane.add(panelSuperiore, BorderLayout.NORTH);
		
		
		contentPane.setBackground(Color.black);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		setContentPane(contentPane);
	}
	
	
}
