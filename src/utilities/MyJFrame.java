package utilities;

import java.awt.Color;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class MyJFrame extends JFrame {
	
	public Border blackBorder = new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(0, 5, 0, 0));
	public Border redBorder = new CompoundBorder(BorderFactory.createLineBorder(Color.RED, 2), new EmptyBorder(0, 5, 0, 0));
	
	public void settaLabelETextFieldDiErrore(MyJLabel labelInput, String messaggioDiErrore, JTextField textFieldInput) {
		labelInput.setText(messaggioDiErrore);
		textFieldInput.setBorder(redBorder);
		labelInput.setVisible(true);
	}
	
	public void settaLabelETextAreaDiErrore(MyJLabel labelInput, String messaggioDiErrore, JTextArea textAInput) {
		labelInput.setText(messaggioDiErrore);
		textAInput.setBorder(redBorder);
		labelInput.setVisible(true);
	}
	
	public void nascondiLabelErrore(JLabel... labelsDiErrore) {
		for(JLabel labelAttuale: labelsDiErrore)
			labelAttuale.setVisible(false);
	}
	
	public void nascondiPanelErrore(JPanel... panelsDiErrore) {
		for(JPanel panelAttuale: panelsDiErrore)
			panelAttuale.setVisible(false);
	}

	public void resettaBordiTextField(JTextField... textFields) {
		for(JTextField textFieldAttuale: textFields)
			textFieldAttuale.setBorder(blackBorder);
	}
	
	public void resettaBordiTextA(JTextArea... textAreas) {
		for(JTextArea textAreaAttuale: textAreas)
			textAreaAttuale.setBorder(blackBorder);
	}
	
}
