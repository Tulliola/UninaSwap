package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Controller;
import dto.ProfiloUtente;
import eccezioni.SaldoException;
import utilities.MyJButton;
import utilities.MyJDialog;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;

public class DialogCashout extends MyJDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	Controller mainController;
	
	private MyJPanel panelSx = new MyJPanel();
	private MyJPanel panelDx = new MyJPanel();
	private MyJPanel panelCentrale = new MyJPanel();
	private MyJPanel panelInferiore = new MyJPanel();
	private PanelHomePageSuperiore panelSuperiore;
	
	MyJLabel lblErroreImporto = new MyJLabel("Inserire un importo valido");
	
	MyJTextField textFieldImporto = new MyJTextField();
	
	public DialogCashout(Controller controller, ProfiloUtente utenteLoggato, JFrame parentFrame) {
		this.mainController = controller;
		
		this.setModal(true);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.setSize(500, 300);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("UninaSwap - Cashout");
		
		panelSx.setSize(new Dimension(50, this.getHeight()));
		panelSx.setBackground(MyJPanel.uninaColor);

		panelDx.setSize(new Dimension(50, this.getHeight()));
		panelDx.setBackground(MyJPanel.uninaColor);
		
		panelSuperiore = new PanelHomePageSuperiore(this, "ATM UninaSwap");
		
		settaPanelInferiore(utenteLoggato);
		settaPanelCentrale();
		
		
		this.add(panelInferiore, BorderLayout.SOUTH);
		this.add(panelSx, BorderLayout.WEST);
		this.add(panelDx, BorderLayout.EAST);
		this.add(panelSuperiore, BorderLayout.NORTH);
		this.add(panelCentrale, BorderLayout.CENTER);
		this.setLocationRelativeTo(parentFrame);
	}

	private void settaPanelInferiore(ProfiloUtente utente) {
		panelInferiore.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelInferiore.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		panelInferiore.setPreferredSize(new Dimension(600, 50));
		panelInferiore.setMaximumSize(new Dimension(600, 50));
		
		lblErroreImporto = new MyJLabel("Inserire un importo valido");
		lblErroreImporto.setForeground(Color.red);
		
		MyJButton tornaIndietroButton = new MyJButton("Torna indietro");
		MyJButton prelevaButton = new MyJButton("Preleva");
		prelevaButton.setDefaultAction(() -> {
			try{
				checkImporto(textFieldImporto.getText(), utente);
				Double importo = Double.parseDouble(textFieldImporto.getText());
				
				mainController.aggiornaSaldoUtente(-importo);
				mainController.chiudiDialogCashout(true);
			}
			catch(NumberFormatException | SaldoException throwables) {
				this.settaLabelETextFieldDiErrore(lblErroreImporto, throwables.getMessage(), textFieldImporto);
			}
		});
		
		tornaIndietroButton.setDefaultAction(() -> {
			mainController.chiudiDialogCashout(false);
		});
		
		panelInferiore.add(tornaIndietroButton);
		panelInferiore.add(prelevaButton);
	}
	
	private void checkImporto(String importo, ProfiloUtente utente) {
		
		if(importo.contains(".")) {
			int decimalIndex = importo.indexOf('.');
			if(importo.endsWith(".") || importo.startsWith(".")) {
				throw new NumberFormatException("Importo non valido.");
			}
			try {
				String sottoStringaOltreDueDecimali = importo.substring(decimalIndex+3);
				for(Character carattere: sottoStringaOltreDueDecimali.toCharArray()) {
					if(!carattere.equals('0'))
						throw new NumberFormatException("Importo non valido.");
				}
			}
			
			catch(StringIndexOutOfBoundsException e) {}
		}	
		try {
			if(Double.parseDouble(importo) > utente.getSaldo()) 
				throw new SaldoException("Non puoi fare prelevare più di quanto possiedi.");
			else if(Double.parseDouble(importo) < 0)
				throw new SaldoException("L'importo non può essere negativo.");
		}catch(NumberFormatException e) {
			throw new NumberFormatException("Importo non valido.");
		}
	}

	private void settaPanelCentrale() {
		panelCentrale.setLayout(new BoxLayout(panelCentrale, BoxLayout.Y_AXIS));
		
		MyJLabel lblImporto = new MyJLabel("Importo da prelevare");
		lblImporto.setFont(new Font("Ubuntu Sans", Font.BOLD, 16));
		lblImporto.setAlignmentX(CENTER_ALIGNMENT);
		textFieldImporto = new MyJTextField();
		textFieldImporto.setAlignmentX(CENTER_ALIGNMENT);
		lblErroreImporto.setVisible(false);
		lblErroreImporto.setAlignmentX(CENTER_ALIGNMENT);
		
		MyJLabel lblATM = new MyJLabel();
		lblATM.aggiungiImmagineScalata("images/iconaVersaCashout.png", 30, 30, false);
		lblATM.setAlignmentX(CENTER_ALIGNMENT);
		
		panelCentrale.add(Box.createVerticalGlue());
		panelCentrale.add(lblImporto);
		panelCentrale.add(textFieldImporto);
		panelCentrale.add(lblErroreImporto);
		panelCentrale.add(lblATM);
		panelCentrale.add(Box.createVerticalGlue());
	}
}
