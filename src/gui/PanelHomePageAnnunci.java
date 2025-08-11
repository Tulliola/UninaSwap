package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import dto.Annuncio;
import dto.AnnuncioRegalo;
import dto.AnnuncioScambio;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;

public class PanelHomePageAnnunci extends JPanel{

	private static final long serialVersionUID = 1L;

	public PanelHomePageAnnunci(ArrayList<Annuncio> annunci) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Color.LIGHT_GRAY);
		this.setPreferredSize(new Dimension(300, 300));
		
		MyJPanel prova = new MyJPanel();
		prova.setLayout(new BoxLayout(prova, BoxLayout.Y_AXIS));
		
		MyJTextField prova2 = new MyJTextField();
		prova.add(prova2);
		
		for(int i = 0; i < annunci.size(); i++) {
			System.out.println(annunci.get(i));
			
			MyJLabel label = new MyJLabel(annunci.get(i).getNome());
			label.setAlignmentX(CENTER_ALIGNMENT);
			prova.add(label);
		}
		
		prova.setPreferredSize(new Dimension(500, 4000));
		
		
		JScrollPane scrollPanel = new JScrollPane(prova);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPanel.getVerticalScrollBar().setUnitIncrement(20);

		this.add(scrollPanel);
	}
	
}
