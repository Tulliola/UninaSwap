package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dto.ProfiloUtente;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;

public class PanelHomePageSuperiore extends MyJPanel {

	private static final long serialVersionUID = 1L;
	
	private FrameHomePage frameChiamante;
	
	private	MyJPanel barraDiRicerca = new MyJPanel();
	private MyJPanel barraBluChiaro = new MyJPanel();
	
	public PanelHomePageSuperiore(FrameHomePage parentFrame, ProfiloUtente utenteLoggato) {
		frameChiamante = parentFrame;
		
		this.setPreferredSize(new Dimension(parentFrame.getWidth(), 105));
		this.setMaximumSize(new Dimension(parentFrame.getWidth(), 150));
		this.setLayout(new BorderLayout());
		
		MyJPanel barraBlu = new MyJPanel();
		barraBlu.setBackground(uninaColor);
		barraBlu.setPreferredSize(new Dimension(parentFrame.getWidth(), 45));
		barraBlu.add(new MyJLabel("UninaSwap", new Font("Ubuntu Sans", Font.BOLD, 25), Color.white));
		
		this.impostaBarraDiRicerca();
		
		this.add(barraBlu, BorderLayout.NORTH);
		this.add(barraBluChiaro, BorderLayout.CENTER);
	}
	
	private void impostaBarraDiRicerca() {
		barraBluChiaro.setLayout(new BorderLayout());
		barraBluChiaro.setPreferredSize(new Dimension(100, 30));
		barraBluChiaro.setBackground(uninaColorClicked);
		
		barraDiRicerca.setLayout(new BorderLayout());
		barraDiRicerca.setBackground(uninaColorClicked);
		barraDiRicerca.setMaximumSize(new Dimension(500, 30));
		
		JTextField campoDiTestoTextField = new JTextField("Cerca ora!");
		campoDiTestoTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
		campoDiTestoTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent fe) {
				if(campoDiTestoTextField.getText().equals("Cerca ora!"))
					campoDiTestoTextField.setText("");
			}
			
			@Override
			public void focusLost(FocusEvent fe) {
				if(campoDiTestoTextField.getText().isEmpty())
					campoDiTestoTextField.setText("Cerca ora!");
			}
		});
		
		MyJLabel lblIconaDiRicerca = new MyJLabel();
		lblIconaDiRicerca.aggiungiImmagineScalata("images/iconaDiRicerca.png", 25, 25, false);
		lblIconaDiRicerca.setOpaque(true);
		lblIconaDiRicerca.setBackground(Color.white);
		
		MyJLabel lblIconaDiEliminaTesto = new MyJLabel();
		lblIconaDiEliminaTesto.aggiungiImmagineScalata("images/iconaRimuovi.png", 25, 25, true);
		lblIconaDiEliminaTesto.setOpaque(true);
		lblIconaDiEliminaTesto.setBackground(Color.white);
		
		lblIconaDiEliminaTesto.rendiLabelInteragibile();
		
		lblIconaDiEliminaTesto.setOnMouseClickedAction(() -> {
			campoDiTestoTextField.setText("");
			campoDiTestoTextField.requestFocus();
		});
		
		lblIconaDiEliminaTesto.setOnMouseEnteredAction(() -> {
			lblIconaDiEliminaTesto.setBackground(new Color(220, 220, 220));
			lblIconaDiEliminaTesto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		});
		
		lblIconaDiEliminaTesto.setOnMouseExitedAction(() -> {
			lblIconaDiEliminaTesto.setBackground(Color.white);
			lblIconaDiEliminaTesto.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		});
		
		barraDiRicerca.add(lblIconaDiRicerca, BorderLayout.WEST);
		barraDiRicerca.add(campoDiTestoTextField, BorderLayout.CENTER);
		barraDiRicerca.add(lblIconaDiEliminaTesto, BorderLayout.EAST);
		
		barraBluChiaro.add(new MyJPanel(uninaColorClicked), BorderLayout.NORTH);
		barraBluChiaro.add(new MyJPanel(uninaColorClicked), BorderLayout.WEST);
		barraBluChiaro.add(barraDiRicerca, BorderLayout.CENTER);
		barraBluChiaro.add(new MyJPanel(uninaColorClicked), BorderLayout.EAST);
		barraBluChiaro.add(new MyJPanel(uninaColorClicked), BorderLayout.SOUTH);
		
	}
	
}
