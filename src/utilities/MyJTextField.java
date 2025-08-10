package utilities;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class MyJTextField extends JTextField {

	Border blackBorder = new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(0, 5, 0, 0));
	
	public MyJTextField() {
		this.setBorder(blackBorder);
		this.setMaximumSize(new Dimension(300, 30));
		this.setFont(new Font("Ubuntu Sans", Font.PLAIN, 13));
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent me) {
				setCursor(new Cursor(Cursor.TEXT_CURSOR));
			}
			
			public void mouseExited(MouseEvent me) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	public MyJTextField(String stringaDiDefault) {
		this();
		this.setText(stringaDiDefault);
	}
	
	public void settaBordiTextField(Color chosenColor) {
		Border chosenBorder;
		Border spacedBorder;
		
		if(chosenColor == Color.RED)
			chosenBorder = BorderFactory.createLineBorder(chosenColor, 2);
		
		else	
			chosenBorder = BorderFactory.createLineBorder(chosenColor, 1);
		
		spacedBorder = new EmptyBorder(0, 5, 0, 0);
		this.setBorder(new CompoundBorder(chosenBorder, spacedBorder));		
	}
	
	public void modificaBGColorSeEnabled(Color coloreDaDisabilitato, Color coloreDaAbilitato) {
		if(!(this.isEnabled())) {
			this.setDisabledTextColor(Color.black);
			this.setBackground(coloreDaDisabilitato);
		}
		else
			this.setBackground(coloreDaAbilitato);

	}
	
	public void cambiaStatoEnabled() {
		if(!(this.isEnabled()))
			this.setEnabled(true);
		else
			this.setEnabled(false);
	}
	
	public void cambiaStatoVisible() {
		if(!(this.isVisible()))
			this.setVisible(true);
		else
			this.setVisible(false);
	}

}
