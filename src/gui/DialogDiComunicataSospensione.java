package gui;

import java.awt.*;
import java.sql.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DialogDiComunicataSospensione extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	
	public DialogDiComunicataSospensione(Frame framePadre, Date dataSospensione, String[] motiviSegnalazioni, boolean isModale) {
		super(framePadre, "Sei attualmente sospeso", isModale);
		settaDialog(framePadre, dataSospensione, motiviSegnalazioni);
	}

	private void settaDialog(Frame framePadre, Date dataSospensione, String[] motiviSegnalazioni) {
		this.setSize(200, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(framePadre);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBackground(Color.WHITE);
		
		this.setContentPane(contentPanel);
		
		JLabel motivoSegnalazione1 = new JLabel(motiviSegnalazioni[0]);
		motivoSegnalazione1.setAlignmentX(CENTER_ALIGNMENT);
		JLabel motivoSegnalazione2 = new JLabel(motiviSegnalazioni[1]);
		motivoSegnalazione2.setAlignmentX(CENTER_ALIGNMENT);
		JLabel motivoSegnalazione3 = new JLabel(motiviSegnalazioni[2]);
		motivoSegnalazione3.setAlignmentX(CENTER_ALIGNMENT);
		
		contentPanel.add(motivoSegnalazione1);
		contentPanel.add(motivoSegnalazione2);
		contentPanel.add(motivoSegnalazione3);
	}
}
