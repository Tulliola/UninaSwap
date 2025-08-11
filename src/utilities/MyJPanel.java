package utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class MyJPanel extends JPanel {
	public static final Color uninaColor = new Color(65, 106, 144);
	public static final Color uninaLightColor = new Color(198, 210, 222);
	public static final Color uninaColorClicked = new Color(85, 126, 164);
	
	public static final Border blackBorder = BorderFactory.createLineBorder(Color.BLACK,  1);
	public static final Border redBorder = BorderFactory.createLineBorder(Color.RED,  2);

	private Image immagine;
	
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
	
	
	@Override
	protected void paintComponent(Graphics immagineDaAdattare) {
		super.paintComponent(immagineDaAdattare);
		
		if(immagineDaAdattare != null)
			immagineDaAdattare.drawImage(immagine, 0, 0, getWidth(), getHeight(), this);
	}
}
