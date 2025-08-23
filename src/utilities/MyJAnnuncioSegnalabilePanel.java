package utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

import controller.Controller;
import dto.Annuncio;
import dto.AnnuncioRegalo;
import dto.AnnuncioScambio;
import dto.AnnuncioVendita;

public abstract class MyJAnnuncioSegnalabilePanel extends MyJAnnuncioPanel{

	
	public MyJAnnuncioSegnalabilePanel(Controller controller, Annuncio annuncioToAdd) {
		super(controller, annuncioToAdd);
	}

	@Override 
	protected MyJPanel creaPanelUsernamePubblicante(Annuncio annuncio) {
		MyJPanel panelUsernamePubblicante = new MyJPanel();
		panelUsernamePubblicante.setLayout(new BoxLayout(panelUsernamePubblicante, BoxLayout.X_AXIS));
		panelUsernamePubblicante.setPreferredSize(new Dimension(425, 75));
		panelUsernamePubblicante.setMaximumSize(new Dimension(425, 75));
		panelUsernamePubblicante.setAlignmentX(CENTER_ALIGNMENT);		
		panelUsernamePubblicante.setBorder(BorderFactory.createLineBorder(MyJPanel.uninaColorClicked, 1));
		
		MyJLabel lblUsername = new MyJLabel(annuncio.getUtenteProprietario().getUsername());
		lblUsername.setFont(new Font("Ubuntu Sans", Font.BOLD, 20));
		lblUsername.setAlignmentX(RIGHT_ALIGNMENT);
		lblUsername.setForeground(Color.white);
		MyJLabel lblIconaTipoAnnuncio = new MyJLabel();
		
		MyJLabel lblSegnala = new MyJLabel();
		lblSegnala.rendiLabelInteragibile();
		lblSegnala.aggiungiImmagineScalata("images/iconaSegnalazione.png", 50, 50, true);
		lblSegnala.setOnMouseClickedAction(() -> {
			mainController.passaADialogSegnalaUtente(annuncio);
		});
		lblSegnala.setOnMouseEnteredAction(() -> {});
		lblSegnala.setOnMouseExitedAction(() -> {});
		
		if(annuncio instanceof AnnuncioVendita) {
			lblIconaTipoAnnuncio.aggiungiImmagineScalata("images/iconaAnnuncioVendita.png", 50, 50, false);
			lblIconaTipoAnnuncio.setToolTipText("Prezzo iniziale - "+annuncio.getPrezzoIniziale()+"€");
		}
		else if(annuncio instanceof AnnuncioScambio) {
			lblIconaTipoAnnuncio.aggiungiImmagineScalata("images/iconaAnnuncioScambio.png", 50, 50, false);
			lblIconaTipoAnnuncio.setToolTipText(annuncio.getNotaScambio());
		}
		else if(annuncio instanceof AnnuncioRegalo)
			lblIconaTipoAnnuncio.aggiungiImmagineScalata("images/iconaAnnuncioRegalo.png", 50, 50, false);

		panelUsernamePubblicante.add(Box.createVerticalGlue());
		panelUsernamePubblicante.add(Box.createRigidArea(new Dimension(10, 0)));
		panelUsernamePubblicante.add(lblUsername);
		panelUsernamePubblicante.add(Box.createHorizontalGlue());
		panelUsernamePubblicante.add(lblSegnala);
		panelUsernamePubblicante.add(lblIconaTipoAnnuncio);
		panelUsernamePubblicante.add(Box.createRigidArea(new Dimension(10, 0)));
		panelUsernamePubblicante.add(Box.createVerticalGlue());
		
		panelUsernamePubblicante.setBackground(MyJPanel.uninaColorClicked);

		return panelUsernamePubblicante;
	}

}
