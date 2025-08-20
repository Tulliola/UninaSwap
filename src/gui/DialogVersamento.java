package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import utilities.MyJButton;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;

public class DialogVersamento extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Controller mainController;
	
	MyJPanel panelSx = new MyJPanel();
	MyJPanel panelDx = new MyJPanel();
	PanelHomePageSuperiore panelSuperiore;
	MyJPanel panelCentrale = new MyJPanel();
	MyJPanel panelInferiore = new MyJPanel();
	
	MyJTextField textFieldImporto;
	
	MyJLabel lblErroreImporto;
	
	public DialogVersamento(Controller controller, JFrame parentFrame) {
		this.mainController = controller;
		
		this.setModal(true);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.setSize(700, 400);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("UninaSwap - Versa");
		
		panelSx.setSize(new Dimension(50, this.getHeight()));
		panelSx.setBackground(MyJPanel.uninaColor);

		panelDx.setSize(new Dimension(50, this.getHeight()));
		panelDx.setBackground(MyJPanel.uninaColor);
		
		panelSuperiore = new PanelHomePageSuperiore(this);
		
		settaPanelInferiore();
		settaPanelCentrale();
		
		
		this.add(panelInferiore, BorderLayout.SOUTH);
		this.add(panelSx, BorderLayout.WEST);
		this.add(panelDx, BorderLayout.EAST);
		this.add(panelSuperiore, BorderLayout.NORTH);
		this.add(panelCentrale, BorderLayout.CENTER);
		this.setLocationRelativeTo(parentFrame);
	}

	private void settaPanelInferiore() {
		panelInferiore.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelInferiore.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		panelInferiore.setPreferredSize(new Dimension(600, 50));
		panelInferiore.setMaximumSize(new Dimension(600, 50));
		
		lblErroreImporto = new MyJLabel("Inserire un importo valido");
		lblErroreImporto.setText("Inserire un importo valido");
		lblErroreImporto.setForeground(Color.red);
		
		MyJButton tornaIndietroButton = new MyJButton("Torna indietro");
		MyJButton versaButton = new MyJButton("Versa");
		versaButton.setDefaultAction(() -> {
			try{
				textFieldImporto.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				lblErroreImporto.setVisible(false);
				
				checkImporto(textFieldImporto.getText());
				Double importo = Double.parseDouble(textFieldImporto.getText());
				
				mainController.aggiornaSaldoUtente(importo);
				mainController.chiudiDialogVersamento();
			}
			catch(NumberFormatException e) {
				textFieldImporto.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				lblErroreImporto.setVisible(true);
			}
		});
		
		tornaIndietroButton.setDefaultAction(() ->{
			mainController.chiudiDialogVersamento();
		});
		
		panelInferiore.add(tornaIndietroButton);
		panelInferiore.add(versaButton);
	}
	
	private void checkImporto(String importo) {
		int decimalIndex = importo.indexOf('.');
		
		String sottoStringaOltreDueDecimali = importo.substring(decimalIndex+3);
		for(Character carattere: sottoStringaOltreDueDecimali.toCharArray()) {
			if(!carattere.equals('0'))
				throw new NumberFormatException();
		}
	}

	private void settaPanelCentrale() {
		panelCentrale.setLayout(new BoxLayout(panelCentrale, BoxLayout.Y_AXIS));
		
		MyJLabel lblImporto = new MyJLabel("Importo da versare");
		lblImporto.setFont(new Font("Ubuntu Sans", Font.BOLD, 16));
		lblImporto.setAlignmentX(CENTER_ALIGNMENT);
		textFieldImporto = new MyJTextField();
		textFieldImporto.setAlignmentX(CENTER_ALIGNMENT);
		lblErroreImporto.setVisible(false);
		lblErroreImporto.setAlignmentX(CENTER_ALIGNMENT);
		
		panelCentrale.add(Box.createVerticalGlue());
		panelCentrale.add(lblImporto);
		panelCentrale.add(textFieldImporto);
		panelCentrale.add(lblErroreImporto);
		panelCentrale.add(Box.createVerticalGlue());
	}
}
