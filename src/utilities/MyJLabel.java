package utilities;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class MyJLabel extends JLabel {
	
	public static final Border blackBorder = new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(0, 5, 0, 0));
	public static final Border redBorder = new CompoundBorder(BorderFactory.createLineBorder(Color.RED, 2), new EmptyBorder(0, 5, 0, 0));
	
	public static final Color uninaColor = new Color(65, 106, 144);
	public static final Color uninaLightColor = new Color(198, 210, 222);
	
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
	
	public MyJLabel(Font font) {
		this.setFont(font);
	}
	
	public MyJLabel(String stringaDiDefault, Font font) {
		this.setText(stringaDiDefault);
		this.setFont(font);
	}
	
	public MyJLabel(String stringaDiDefault, Font font, boolean isLabelDiErrore) {
		this.setText(stringaDiDefault);
		this.setFont(font);
		
		if(isLabelDiErrore) {
			this.setBorder(redBorder);
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
	
	public void aggiungiImmagineScalata(int larghezza, int altezza, String stringPath) {
		ImageIcon img = new ImageIcon(stringPath);	
		Image resizedImage = img.getImage().getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
		ImageIcon resizeResult = new ImageIcon(resizedImage);
		this.setIcon(resizeResult);
	}
	
	public void aggiungiImmagineScalataX(int larghezza, int altezza, String stringPath, float alignment) {
		aggiungiImmagineScalata(larghezza, altezza, stringPath);
		this.setAlignmentX(alignment);
	}
	
	public void aggiungiImmagineScalataY(int larghezza, int altezza, String stringPath, float alignment) {
		aggiungiImmagineScalata(larghezza, altezza, stringPath);
		this.setAlignmentY(alignment);
	}
}
