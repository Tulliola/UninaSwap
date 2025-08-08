package utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MyJPanel extends JPanel {
	
	public void aggiungiTextField(JTextField textFieldInput, String stringaPerLabel) {
		JLabel label = new JLabel();
		label.setText(stringaPerLabel);
		label.setForeground(Color.black);
		label.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		label.setAlignmentX(LEFT_ALIGNMENT);
		
		textFieldInput.setMaximumSize(new Dimension(300, 30));
		textFieldInput.setFont(new Font("Ubuntu Sans", Font.PLAIN, 13));
		textFieldInput.setBorder(new EmptyBorder(0, 5, 0, 0));
		textFieldInput.setAlignmentX(LEFT_ALIGNMENT);
		
		if(!(textFieldInput.isEnabled())) { 
			textFieldInput.setDisabledTextColor(Color.black);
			textFieldInput.setBackground(Color.lightGray);
		}
		
		this.add(label);
		this.add(textFieldInput);
	}
	
	public void aggiungiTextField(JTextField textFieldInput, String stringaPerLabel, String stringPerTextField) {
		this.aggiungiTextField(textFieldInput, stringaPerLabel);
		
		textFieldInput.setText(stringPerTextField);
	}
}
