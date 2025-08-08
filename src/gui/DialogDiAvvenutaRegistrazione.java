package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class DialogDiAvvenutaRegistrazione extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int numeroDiPuntini = 0;
	private int secondiPassati = 0;
	private String stringaPerLabel;
	private Timer timerDiIndirizzamento;

	public DialogDiAvvenutaRegistrazione(Frame compPadre, String titolo, boolean isModale) {
		super(compPadre, titolo, isModale);
		
		this.impostaSettingsPerDialog(titolo, compPadre);
		this.impostaContentPane();
	}
	
	private void impostaSettingsPerDialog(String titolo, Frame compPadre) {
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setTitle(titolo);
		this.setSize(300, 100);
		this.setLocationRelativeTo(compPadre);
		this.setResizable(false);

		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setBackground(Color.white);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
	}
	
	private void impostaContentPane() {
		JLabel lblRegistrazioneEffettuata = new JLabel();
		lblRegistrazioneEffettuata.setText("Registrazione effettuata con successo!");
		lblRegistrazioneEffettuata.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		lblRegistrazioneEffettuata.setForeground(Color.black);
		lblRegistrazioneEffettuata.setAlignmentX(CENTER_ALIGNMENT);
		lblRegistrazioneEffettuata.setVisible(true);
		
		JLabel lblIndirizzamentoALogin = new JLabel();
		lblIndirizzamentoALogin.setFont(new Font("Ubuntu Sans", Font.ITALIC, 15));
		lblIndirizzamentoALogin.setForeground(Color.black);
		lblIndirizzamentoALogin.setAlignmentX(CENTER_ALIGNMENT);
		lblIndirizzamentoALogin.setVisible(true);
		
		contentPane.add(lblRegistrazioneEffettuata);
		contentPane.add(Box.createRigidArea(new Dimension(0, 15)));
		contentPane.add(lblIndirizzamentoALogin);

		timerDiIndirizzamento = new Timer(1000, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				numeroDiPuntini = (numeroDiPuntini % 3) + 1;
				
				stringaPerLabel = "Indirizzamento alla pagina di login";
				stringaPerLabel = stringaPerLabel+(".".repeat(numeroDiPuntini));
				
				lblIndirizzamentoALogin.setText(stringaPerLabel);
				
				secondiPassati++;
				
				if(secondiPassati >= 6) {
					timerDiIndirizzamento.stop();
					dispose();
				}
			}
		});
		
		timerDiIndirizzamento.start();
	}

}
