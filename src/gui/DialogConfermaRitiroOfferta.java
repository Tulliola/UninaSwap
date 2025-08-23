package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.Offerta;
import utilities.MyJButton;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.StatoOffertaEnum;

public class DialogConfermaRitiroOfferta extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private static Controller mainController;
	private MyJPanel panelSx = new MyJPanel();
	private MyJPanel panelDx = new MyJPanel();
	private Window parentFrame;
	private PanelHomePageSuperiore panelSuperiore;
	private MyJPanel panelCentrale = new MyJPanel();
	private MyJPanel panelBottoni = new MyJPanel();
	
	public DialogConfermaRitiroOfferta(Controller mainController, JFrame parentFrame, Offerta offerta) {
		this.mainController = mainController;
		this.parentFrame = parentFrame;
		setDialog(parentFrame, offerta);
		
	}
	
	private void setDialog(JFrame parentFrame, Offerta offerta) {
		this.setSize(getPreferredSize());
		this.setSize(new Dimension(500, 300));
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setTitle("Conferma ritiro offerta");
		
		panelSx.setSize(new Dimension(50, this.getHeight()));
		panelSx.setBackground(MyJPanel.uninaColor);

		panelDx.setSize(new Dimension(50, this.getHeight()));
		panelDx.setBackground(MyJPanel.uninaColor);
		
		panelSuperiore = new PanelHomePageSuperiore(this, "Conferma ritiro offerta");
		
		panelCentrale.setLayout(new BoxLayout(panelCentrale, BoxLayout.Y_AXIS));
		MyJLabel lblConferma = new MyJLabel("Sei sicuro di voler ritirare l'offerta?");
		lblConferma.setAlignmentX(CENTER_ALIGNMENT);
		
		panelBottoni.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelBottoni.setAlignmentX(CENTER_ALIGNMENT);
		MyJButton annullaButton = new MyJButton("Annulla");
		annullaButton.setUpAction(()->{});
		annullaButton.setDownAction(()->{});
		annullaButton.setDefaultAction(()->{
			mainController.chiudiDialogConfermaRitiroOfferta();
		});
		MyJButton siButton = new MyJButton("Sì");
		siButton.setUpAction(()->{});
		siButton.setDownAction(()->{});
		siButton.setDefaultAction(()->{
			mainController.aggiornaStatoOfferta(offerta, StatoOffertaEnum.Ritirata);
		});
		
		panelBottoni.add(annullaButton);
		panelBottoni.add(siButton);
		
		panelCentrale.add(Box.createVerticalGlue());
		panelCentrale.add(lblConferma);
		panelCentrale.add(panelBottoni);
		panelCentrale.add(Box.createVerticalGlue());
		
		this.add(panelSx, BorderLayout.WEST);
		this.add(panelDx, BorderLayout.EAST);
		this.add(panelSuperiore, BorderLayout.NORTH);
		this.add(panelCentrale, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(500, 300));
		this.setLocationRelativeTo(parentFrame);
	}
}
