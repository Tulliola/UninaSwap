package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.text.Caret;

import controller.Controller;
import dto.Offerta;
import dto.OffertaAcquisto;
import dto.OffertaRegalo;
import dto.OffertaScambio;
import dto.ProfiloUtente;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;

public class FrameReport extends MyJFrame {

	private static final long serialVersionUID = 1L;
	private Controller mainController;
	private MyJPanel contentPane = new MyJPanel();
	private PanelBarraLateraleSx panelBarraLaterale;
	private MyJPanel panelCentrale;
	private JScrollPane scrollPane;
	
	public FrameReport(Controller controller, ProfiloUtente utente) {
		mainController = controller;
		settaFrame();
		settaContentPane(utente);
		settaBarraLaterale();
		
		scrollPane = new JScrollPane(panelCentrale);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);	
		this.add(scrollPane);
	}


	private void settaFrame(){
		setTitle("Report dello storico delle tue offerte");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		this.setPreferredSize(new Dimension(1000, 700));
	}
	
	private void settaContentPane(ProfiloUtente utente) {
		contentPane.setLayout(new BorderLayout());
		settaPanelCentrale(utente);
		setContentPane(contentPane);
	}
	
	private void settaBarraLaterale() {
		panelBarraLaterale = new PanelBarraLateraleSx(contentPane, mainController, this, "        Report offerte");
		contentPane.add(panelBarraLaterale, BorderLayout.WEST);
	}
	
	private void settaPanelCentrale(ProfiloUtente utente) {
		panelCentrale = new MyJPanel();
		panelCentrale.setLayout(new BorderLayout());
		panelCentrale.setBackground(MyJPanel.uninaLightColor);
		
		contentPane.add(panelCentrale, BorderLayout.CENTER);
	}
}
