package gui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import gui.PanelHomePageAnnunci;
import gui.PanelHomePageSuperiore;
import utilities.MyJFrame;
import utilities.MyJPanel;
import controller.Controller;
import dto.Annuncio;
//import dto.AnnuncioRegalo;
//import dto.AnnuncioScambio;
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
	
	public Controller getController() {
		return mainController;
	}

	private void settaContentPane(ProfiloUtente utenteLoggato, ArrayList<Annuncio> annunci) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(new Rectangle(1200, 1000));
		this.setLocationRelativeTo(null);
		this.setTitle("Il mercatino digitale federiciano");
		
		contentPane = new MyJPanel();
		contentPane.setLayout(new BorderLayout());
		
		panelAnnunci = new PanelHomePageAnnunci(mainController, annunci);
		panelLateraleSx = new PanelBarraLateraleSx(contentPane, mainController, this);
		
		panelLateraleSx.getLblIlMioProfilo().setOnMouseClickedAction(() ->{
			mainController.passaASezioneInFrameProfiloUtente(panelLateraleSx.getLblIlMioProfilo().getText());
		});
		
		panelLateraleSx.getLblAnnunciDisponibili().setOnMouseClickedAction(() -> 
		{
			mainController.passaASezioneInFrameProfiloUtente(panelLateraleSx.getLblAnnunciDisponibili().getText());
		});
		
		panelLateraleSx.getLblAnnunciUltimati().setOnMouseClickedAction(() -> 
		{
			mainController.passaASezioneInFrameProfiloUtente(panelLateraleSx.getLblAnnunciUltimati().getText());
		});
		
		panelLateraleSx.getLblAnnunciScaduti().setOnMouseClickedAction(() -> 
		{
			mainController.passaASezioneInFrameProfiloUtente(panelLateraleSx.getLblAnnunciScaduti().getText());
		});
		
		panelLateraleSx.getLblAnnunciRimossi().setOnMouseClickedAction(() -> 
		{
			mainController.passaASezioneInFrameProfiloUtente(panelLateraleSx.getLblAnnunciRimossi().getText());
		});
		
		panelSuperiore = new PanelHomePageSuperiore(this, utenteLoggato);
		
		contentPane.add(panelLateraleSx, BorderLayout.WEST);
		contentPane.add(panelAnnunci, BorderLayout.CENTER);
		contentPane.add(panelSuperiore, BorderLayout.NORTH);
		
		
		contentPane.setBackground(Color.black);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		setContentPane(contentPane);
	}
	
	
}
