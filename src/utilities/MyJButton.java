package utilities;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class MyJButton extends JButton {
	public MyJButton() {
		this.setBackground(new Color(65, 106, 144));
		this.setForeground(Color.WHITE);
		this.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		this.setFocusable(false);
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent me) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			public void mouseExited(MouseEvent me) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}
	
	public MyJButton(String stringaDiDefault) {
		this();
		this.setText(stringaDiDefault);
	}
}
