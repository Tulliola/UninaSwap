package gui;

import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class DialogDiComunicataSospensione extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel panelInterno = new JPanel();
	
	
	public DialogDiComunicataSospensione(Frame framePadre, Date dataSospensione, String[] motiviSegnalazioni, boolean isModale, String[] utentiSegnalanti) {
		super(framePadre, "Sei attualmente sospeso", isModale);
		settaDialog(framePadre, dataSospensione, motiviSegnalazioni, utentiSegnalanti);
	}

	private void settaDialog(Frame framePadre, Date dataSospensione, String[] motiviSegnalazioni, String utentiSegnalanti[]) {
		this.setSize(800, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(framePadre);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBackground(Color.WHITE);
		
		this.setContentPane(contentPanel);
		
		panelInterno.setPreferredSize(new Dimension(700, 400));
		panelInterno.setMaximumSize(new Dimension(700, 400));
		panelInterno.setLayout(new BoxLayout(panelInterno, BoxLayout.Y_AXIS));
		panelInterno.setBackground(Color.WHITE);
		
		JTextArea comunicaSegnalazione = new JTextArea();
		comunicaSegnalazione.setText("Sembra che tu sia sospeso fino alla data "+ settaFormatoData(calcolaDataDesospensione(dataSospensione))+ ", a causa delle segeunti segnalazioni:");
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
		
		JLabel immagine = new JLabel();
		ImageIcon logo = new ImageIcon("images/logo_uninaswap.png");
		
		panelInterno.setAlignmentX(CENTER_ALIGNMENT);
		panelInterno.add(comunicaSegnalazione);
		panelInterno.add(motivoSegnalazione1);
		panelInterno.add(motivoSegnalazione2);
		panelInterno.add(motivoSegnalazione3);
		
		settaIcona(panelInterno, immagine, logo);

		contentPanel.add(panelInterno);
	}
	
	private void settaJTextArea(JTextArea textIn) {
		textIn.setAlignmentX(CENTER_ALIGNMENT);
		textIn.setLineWrap(true);
		textIn.setWrapStyleWord(true);
		textIn.getCaret().setVisible(false);
		textIn.setFocusable(false);
		textIn.setEditable(false);
		textIn.setOpaque(false);
	}
	
	private Date calcolaDataDesospensione(Date dataSospensione) {
		return Date.valueOf(dataSospensione.toLocalDate().plusMonths(1));
	}
	
	private void settaIcona(JPanel panel, JLabel labelImmagine, ImageIcon img) {
		ImageIcon iconaDaAggiungere = ridimensionaIcona(img);
		labelImmagine.setIcon(iconaDaAggiungere);
		labelImmagine.setAlignmentX(CENTER_ALIGNMENT);
		
		panelInterno.add(Box.createVerticalStrut(20));	
		panelInterno.add(labelImmagine);
	}
	
	private ImageIcon ridimensionaIcona(ImageIcon img) {
		Image resizedImg = img.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		return new ImageIcon(resizedImg);
	}
	
	private String settaFormatoData(Date data) {
		LocalDate localD = data.toLocalDate();
		return localD.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}
}
