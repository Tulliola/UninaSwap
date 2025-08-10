package utilities;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
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
	
	public MyJLabel(boolean isLabelDiErrore) {
		this();
		
		if(isLabelDiErrore) {
			this.setForeground(Color.red);
			this.setFont(new Font("Ubuntu Sans", Font.BOLD, 13));
			this.setVisible(false);
		}
	}
	
	public MyJLabel(String stringaDiDefault, Icon immagineLabel) {
		this(stringaDiDefault);
		this.setIcon(immagineLabel);
	}
	
	public MyJLabel(ImageIcon immagine, boolean isCliccabile) {
		this();
		this.setIcon(immagine);

		if(isCliccabile) {
			this.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent me) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
				
				public void mouseExited(MouseEvent me) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}				
			});
		}
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
	
	public void aggiungiImmagineScalata(String stringPath, int larghezza, int altezza) {
		ImageIcon img = new ImageIcon(stringPath);
		Image resizedImage = img.getImage().getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
		ImageIcon resizeResult = new ImageIcon(resizedImage);
		this.setIcon(resizeResult);
	}
}
