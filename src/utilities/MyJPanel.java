package utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

	
	public MyJPanel() {}

	public MyJPanel(Color backgroundColor) {
		this.setBackground(backgroundColor);
	}
	
	public void aggiungiTextFieldConLabel(MyJTextField textFieldInput, JLabel labelIn) {
		textFieldInput.modificaBGColorSeEnabled(Color.LIGHT_GRAY, Color.white);
		
		this.add(labelIn);
		this.add(textFieldInput);
	}
	
}
