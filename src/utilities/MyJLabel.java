package utilities;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class MyJLabel extends JLabel {
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
