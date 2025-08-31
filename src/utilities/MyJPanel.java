package utilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;

public class MyJPanel extends JPanel implements MouseListener {
	public static final Color uninaColor = new Color(65, 106, 144);
	public static final Color uninaLightColor = new Color(198, 210, 222);
	public static final Color uninaColorClicked = new Color(85, 126, 164);
	
	public static final Border blackBorder = BorderFactory.createLineBorder(Color.BLACK,  1);
	public static final Border redBorder = BorderFactory.createLineBorder(Color.RED,  2);

	private Image immagine;
	
	private Runnable onMouseClickedAction;
	private Runnable onMouseEnteredAction;
	private Runnable onMouseExitedAction;
	
	public MyJPanel() {
		super();
	}

	public MyJPanel(Color backgroundColor) {
		this();
		this.setBackground(backgroundColor);
	}
	
	public MyJPanel(Image immagine) {
		this.immagine = immagine;
	}
	
	public MyJPanel(Color backgroundColor, Dimension dimensionePanel) {
		this(backgroundColor);
		this.setPreferredSize(dimensionePanel);
	}
	
	public void aggiungiTextFieldConLabel(MyJTextField textFieldInput, JLabel labelIn) {
		textFieldInput.modificaBGColorSeEnabled(Color.LIGHT_GRAY, Color.white);
		
		this.add(labelIn);
		this.add(textFieldInput);
	}
	
	public void nascondiLabelErrore(JLabel... labelsDiErrore) {
		for(JLabel labelAttuale: labelsDiErrore)
			labelAttuale.setVisible(false);
	}
	
	public void nascondiPanelErrore(JPanel... panelsDiErrore) {
		for(JPanel panelAttuale: panelsDiErrore)
			panelAttuale.setVisible(false);
	}

	public void resettaBordiTextField(Border borderDaSettare, JTextField... textFields) {
		for(JTextField textFieldAttuale: textFields)
			textFieldAttuale.setBorder(borderDaSettare);
	}
	
	public void resettaBordiTextA(Border borderDaSettare, JTextArea... textAreas) {
		for(JTextArea textAreaAttuale: textAreas)
			textAreaAttuale.setBorder(borderDaSettare);
	}
	
	public void settaLabelETextFieldDiErrore(MyJLabel labelInput, String messaggioDiErrore, JTextField textFieldInput) {
		labelInput.setText(messaggioDiErrore);
		textFieldInput.setBorder(redBorder);
		labelInput.setVisible(true);
	}
	
	public void settaLabelETextAreaDiErrore(MyJLabel labelInput, String messaggioDiErrore, JTextArea textAInput) {
		labelInput.setText(messaggioDiErrore);
		textAInput.setBorder(redBorder);
		labelInput.setVisible(true);
	}
	
	
	@Override
	protected void paintComponent(Graphics immagineDaAdattare) {
		super.paintComponent(immagineDaAdattare);
		
		if(immagineDaAdattare != null)
			immagineDaAdattare.drawImage(immagine, 0, 0, getWidth(), getHeight(), this);
	}
	
	public boolean hasPanels() {
	    for (Component component : this.getComponents()) {
	        if (component instanceof JPanel) {
	            return true; 
	        }
	    }
	    return false; 
	}
	
	public void rendiPanelInteragibile() {
		this.addMouseListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//Non fa nulla
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//Non fa nulla
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		onMouseClickedAction.run();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setCursor(new Cursor(Cursor.HAND_CURSOR));

		onMouseEnteredAction.run();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		onMouseExitedAction.run();
	}
	
	public void setOnMouseEnteredAction(Runnable onMouseEntered) {
		this.onMouseEnteredAction = onMouseEntered;
	}
	
	public void setOnMouseExitedAction(Runnable onMouseExited) {
		this.onMouseExitedAction = onMouseExited;
	}
	
	public void setOnMouseClickedAction(Runnable onMouseClicked) {
		this.onMouseClickedAction = onMouseClicked;
	}
}
