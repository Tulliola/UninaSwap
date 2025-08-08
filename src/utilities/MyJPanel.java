package utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MyJPanel extends JPanel {
	
	public void aggiungiTextField(JTextField textFieldInput, JLabel labelIn, String stringaPerLabel) {
		labelIn.setText(stringaPerLabel);
		labelIn.setForeground(Color.black);
		labelIn.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		labelIn.setAlignmentX(LEFT_ALIGNMENT);
		
		textFieldInput.setMaximumSize(new Dimension(300, 30));
		textFieldInput.setFont(new Font("Ubuntu Sans", Font.PLAIN, 13));
		textFieldInput.setBorder(new EmptyBorder(0, 5, 0, 0));
		textFieldInput.setAlignmentX(LEFT_ALIGNMENT);
		
		if(!(textFieldInput.isEnabled())) { 
			textFieldInput.setDisabledTextColor(Color.black);
			textFieldInput.setBackground(Color.lightGray);
		}
		
		this.add(labelIn);
		this.add(textFieldInput);
	}
	
	public void aggiungiTextField(JTextField textFieldInput, JLabel labelIn, String stringaPerLabel, String stringPerTextField) {
		textFieldInput.setText(stringPerTextField);
		
		this.aggiungiTextField(textFieldInput, labelIn, stringaPerLabel);		
	}
	
	public void aggiungiTextField(JTextField textFieldInput, JLabel labelIn, String stringaPerLabel, String stringPerTextField, Icon immagineLabel) {
		labelIn.setIcon(immagineLabel);
		labelIn.setHorizontalTextPosition(SwingConstants.LEFT);
		
		this.aggiungiTextField(textFieldInput, labelIn, stringaPerLabel, stringPerTextField);		
	}
}
