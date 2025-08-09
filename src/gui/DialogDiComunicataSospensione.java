package gui;

import java.awt.*;
import java.time.*;
import java.sql.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class DialogDiComunicataSospensione extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	
	public DialogDiComunicataSospensione(Frame framePadre, Date dataSospensione, String[] motiviSegnalazioni, boolean isModale, String[] utentiSegnalanti) {
		super(framePadre, "Sei attualmente sospeso", isModale);
		settaDialog(framePadre, dataSospensione, motiviSegnalazioni, utentiSegnalanti);
	}

	private void settaDialog(Frame framePadre, Date dataSospensione, String[] motiviSegnalazioni, String utentiSegnalanti[]) {
		this.setSize(400, 200);
		this.setResizable(false);
		this.setLocationRelativeTo(framePadre);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBackground(Color.WHITE);
		
		this.setContentPane(contentPanel);
		
		JTextArea comunicaSegnalazione = new JTextArea();
		comunicaSegnalazione.setText("Sembra che tu sia sospeso fino alla data: "+ calcolaDataDesospensione(dataSospensione)+ ", a causa delle segeunti segnalazioni:");
		comunicaSegnalazione.setFont(new Font("Ubuntu Sans", Font.BOLD, 16));
		settaJTextArea(comunicaSegnalazione);
		
		JTextArea motivoSegnalazione1 = new JTextArea();
		motivoSegnalazione1.setText(utentiSegnalanti[0]+": "+motiviSegnalazioni[0]);
		JTextArea motivoSegnalazione2 = new JTextArea();
		motivoSegnalazione2.setText(utentiSegnalanti[1]+": "+motiviSegnalazioni[1]);
		JTextArea motivoSegnalazione3 = new JTextArea();
		motivoSegnalazione3.setText(utentiSegnalanti[2]+": "+motiviSegnalazioni[2]);
		
		settaJTextArea(motivoSegnalazione1);
		settaJTextArea(motivoSegnalazione2);
		settaJTextArea(motivoSegnalazione3);
		
		contentPanel.add(comunicaSegnalazione);
		contentPanel.add(motivoSegnalazione1);
		contentPanel.add(motivoSegnalazione2);
		contentPanel.add(motivoSegnalazione3);
	}
	
	private void settaJTextArea(JTextArea textIn) {
		textIn.setAlignmentX(CENTER_ALIGNMENT);
		textIn.setLineWrap(true);
		textIn.setWrapStyleWord(true);
		textIn.getCaret().setVisible(false);
		textIn.setEditable(false);
		textIn.setOpaque(false);
	}
	
	private Date calcolaDataDesospensione(Date dataSospensione) {
		LocalDate dataDiDesospensione = dataSospensione.toLocalDate().plusMonths(1);
		 return Date.valueOf(dataDiDesospensione);
	}
}
