package utilities;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class MyJButton extends JButton {
	public MyJButton() {
		this.setBackground(new Color(65, 106, 144));
		this.setForeground(Color.WHITE);
		this.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		this.setFocusable(false);
	}
	
	public MyJButton(String stringaDiDefault) {
		this();
		this.setText(stringaDiDefault);
	}
}
