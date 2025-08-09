package utilities;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class MyJPasswordField extends JPasswordField {
	
	Border blackBorder = new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(0, 5, 0, 0));

	public MyJPasswordField() {
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
	
	public MyJPasswordField(String password) {
		this();
		this.setText(password);
	}
	
	public void settaBordiTextField(Color chosenColor) {
		Border chosenBorder;;
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
}
