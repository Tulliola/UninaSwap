package utilities;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class MyJTextField extends JPasswordField {
	
	public void settaBordiTextField(Color chosenColor) {
		Border chosenBorder;;
		Border spacedBorder;
		
		if(chosenColor == Color.RED)
			chosenBorder = BorderFactory.createLineBorder(chosenColor, 2);
		
		else	
			chosenBorder = BorderFactory.createLineBorder(chosenColor, 1);
		
		spacedBorder = new EmptyBorder(0, 5, 0, 0);
		this.setBorder(new CompoundBorder(chosenBorder, spacedBorder));		
	}

}
