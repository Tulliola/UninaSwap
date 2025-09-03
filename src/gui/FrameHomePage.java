package gui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import utilities.MyJFrame;
import utilities.MyJPanel;
import controller.Controller;
import dto.Annuncio;
import dto.ProfiloUtente;

public class FrameHomePage extends MyJFrame {

	private static final long serialVersionUID = 1L;
	private MyJPanel contentPane;
	private PanelHomePageAnnunci panelAnnunci;
	private PanelBarraLateraleSx panelLateraleSx;
	private PanelHomePageSuperiore panelSuperiore;
	private Controller mainController;
	
	public FrameHomePage(Controller controller, ProfiloUtente utenteLoggato, ArrayList<Annuncio> annunci) {
		mainController = controller;
		settaContentPane(utenteLoggato, annunci);
	}

	private void settaContentPane(ProfiloUtente utenteLoggato, ArrayList<Annuncio> annunci) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setLocationRelativeTo(null);
		this.setTitle("Il mercatino digitale federiciano");
		
		contentPane = new MyJPanel();
		contentPane.setLayout(new BorderLayout());
		
		panelAnnunci = new PanelHomePageAnnunci(mainController, annunci);
		panelLateraleSx = new PanelBarraLateraleSx(contentPane, mainController, this, null);
		panelSuperiore = new PanelHomePageSuperiore(this, "UninaSwap");
		
		contentPane.add(panelLateraleSx, BorderLayout.WEST);
		contentPane.add(panelAnnunci, BorderLayout.CENTER);
		contentPane.add(panelSuperiore, BorderLayout.NORTH);
		
		
		contentPane.setBackground(Color.black);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		setContentPane(contentPane);
	}
}
