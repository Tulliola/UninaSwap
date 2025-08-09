package utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class MyJLabel extends JLabel {
	
	public MyJLabel() {
		this.setForeground(Color.black);
		this.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
	}
	
	public MyJLabel(String stringaDiDefault) {
		this();
		this.setText(stringaDiDefault);
	}
	
	public MyJLabel(String stringaDiDefault, Icon immagineLabel) {
		this(stringaDiDefault);
		this.setIcon(immagineLabel);
	}
	
	public void aggiungiEffettoCliccabilita() {
		Font oldFont = this.getFont();
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				setFont(new Font("Ubuntu Sans", Font.BOLD, oldFont.getSize()+1));
			}
			
			
			public void mouseExited(MouseEvent me) {
				setFont(oldFont);
			}
		});
	}
}
