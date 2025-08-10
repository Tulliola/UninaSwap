package utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MyJPanel extends JPanel {
	public static final Color uninaColor = new Color(65, 106, 144);
	public static final Color uninaLightColor = new Color(198, 210, 222);
	
	public void aggiungiTextFieldConLabel(MyJTextField textFieldInput, JLabel labelIn) {
		textFieldInput.modificaBGColorSeEnabled(Color.LIGHT_GRAY, Color.white);
		
		this.add(labelIn);
		this.add(textFieldInput);
	}
}
