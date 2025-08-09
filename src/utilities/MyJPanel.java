package utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MyJPanel extends JPanel {
	
	public void aggiungiTextFieldConLabel(MyJTextField textFieldInput, JLabel labelIn) {
		textFieldInput.modificaBGColorSeEnabled(Color.LIGHT_GRAY, Color.white);
		
		this.add(labelIn);
		this.add(textFieldInput);
	}
}
