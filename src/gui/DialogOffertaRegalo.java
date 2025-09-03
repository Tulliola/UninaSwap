package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.Annuncio;
import dto.AnnuncioRegalo;
import dto.AnnuncioVendita;
import dto.Offerta;
import dto.OffertaAcquisto;
import dto.OffertaRegalo;
import dto.SedeUniversita;
import dto.UfficioPostale;
import eccezioni.PrezzoOffertoException;
import eccezioni.ResidenzaException;
import eccezioni.SaldoException;
import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.MyJButton;
import utilities.MyJDialog;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;

public class DialogOffertaRegalo extends DialogOfferta {

	private static final long serialVersionUID = 1L;
	private MyJPanel panelMieProposte;
	private MyJTextField inserisciMessaggioTextField;
	public DialogOffertaRegalo(Annuncio annuncioPerOfferta, Controller controller) {
		super(annuncioPerOfferta, controller);
	}
	
	
	public DialogOffertaRegalo(Annuncio annuncioPerOfferta, Controller controller, Offerta offertaDaModificare) {
		super(annuncioPerOfferta, controller, offertaDaModificare);
	}

	@Override
	protected MyJPanel creaPanelDatiProposte(Annuncio annuncioPerOfferta) {
		panelDatiProposte = new MyJPanel();
		panelDatiProposte.setBackground(Color.white);
		panelDatiProposte.setPreferredSize(new Dimension(this.panelProposteVenditore.getPreferredSize().width, this.getHeight()));
		panelDatiProposte.setMaximumSize(new Dimension(this.panelProposteVenditore.getPreferredSize().width, this.getHeight()));
		panelDatiProposte.setAlignmentX(CENTER_ALIGNMENT);
		panelDatiProposte.setLayout(new BoxLayout(panelDatiProposte, BoxLayout.Y_AXIS));
	
		MyJPanel panelRegalo = new MyJPanel();
		panelRegalo.setBackground(Color.white);
		MyJLabel lblRegalo = new MyJLabel();
		lblRegalo.setAlignmentX(LEFT_ALIGNMENT);

		lblRegalo.setText(mainController.getUtenteLoggato().getUsername()+ ", questo articolo è in regalo!");
		lblRegalo.aggiungiImmagineScalata("images/iconaAnnuncioRegaloColored.png", 25, 25, false);
		
		panelRegalo.add(lblRegalo);

		panelDatiProposte.add(Box.createVerticalGlue());
		panelDatiProposte.add(panelRegalo);
		panelDatiProposte.add(this.creaPanelModalitaConsegnaProposte(annuncioPerOfferta));
		
		return panelDatiProposte;
	}

	@Override
	protected MyJPanel creaPanelMieProposte(Annuncio annuncioPerOfferta, Offerta offertaDaModificare) {
		panelMieProposte = new MyJPanel();
		panelMieProposte.setLayout(new BorderLayout());
		
		panelMieProposte.add(this.creaPanelMessaggioMotivazionale(offertaDaModificare), BorderLayout.NORTH);
		panelMieProposte.add(this.creaPanelModalitaConsegnaScelta(annuncioPerOfferta, offertaDaModificare), BorderLayout.CENTER);
		panelMieProposte.add(this.creaPanelNotaOfferta(annuncioPerOfferta, offertaDaModificare), BorderLayout.SOUTH);
		
		return panelMieProposte;
	}
	
	private MyJPanel creaPanelMessaggioMotivazionale(Offerta offertaDaModificare) {
		MyJPanel panelMessaggioMotivazionale = new MyJPanel();
		panelMessaggioMotivazionale.setLayout(new BoxLayout(panelMessaggioMotivazionale, BoxLayout.Y_AXIS));
		panelMessaggioMotivazionale.setPreferredSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width, 40));
		panelMessaggioMotivazionale.setMaximumSize(new Dimension(this.panelLaMiaOfferta.getMaximumSize().width, 40));
		panelMessaggioMotivazionale.setBackground(MyJPanel.uninaLightColor);
		panelMessaggioMotivazionale.setAlignmentX(CENTER_ALIGNMENT);
		
		MyJLabel lblMessaggio = new MyJLabel("Lascia un messaggio motivazionale");
		lblMessaggio.setAlignmentX(CENTER_ALIGNMENT);
		inserisciMessaggioTextField = new MyJTextField();
		inserisciMessaggioTextField.setPreferredSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width-20, 30));
		inserisciMessaggioTextField.setMaximumSize(new Dimension(this.panelLaMiaOfferta.getMaximumSize().width-20, 30));
		inserisciMessaggioTextField.setAlignmentX(CENTER_ALIGNMENT);
		inserisciMessaggioTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
		if(offertaDaModificare != null)
			inserisciMessaggioTextField.setText(offertaDaModificare.getMessaggioMotivazionale());
		inserisciMessaggioTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				if(inserisciMessaggioTextField.getText().length() > 100)
					ke.consume();
			}
		});
		
		panelMessaggioMotivazionale.add(lblMessaggio);
		panelMessaggioMotivazionale.add(inserisciMessaggioTextField);
		panelMessaggioMotivazionale.add(Box.createRigidArea(new Dimension(0, 20)));
		
		return panelMessaggioMotivazionale;
	}
	
	@Override
	protected MyJPanel creaPanelNotaOfferta(Annuncio annuncioPerOfferta, Offerta offertaDaModificare) {
		MyJPanel panelNotaOfferta = new MyJPanel();
		panelNotaOfferta.setLayout(new BorderLayout());
		
		MyJPanel panelSuperiore = new MyJPanel();
		panelSuperiore.setBackground(MyJPanel.uninaColorClicked);
		panelSuperiore.setLayout(new FlowLayout(FlowLayout.CENTER));
		MyJLabel lblTitolo = new MyJLabel("Hai qualcosa da indicare al venditore?");
		lblTitolo.setForeground(Color.white);
		panelSuperiore.add(lblTitolo);
		
		MyJPanel panelCentrale = new MyJPanel();
		panelCentrale.setBackground(MyJPanel.uninaLightColor);
		inserisciNotaTextArea = new JTextArea("Ciao " + annuncioPerOfferta.getUtenteProprietario().getUsername() + ", ho visto il tuo annuncio \"" + annuncioPerOfferta.getNome() + "\".");

		inserisciNotaTextArea.setFont(new Font("Ubuntu Sans", Font.PLAIN, 13));
		inserisciNotaTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		inserisciNotaTextArea.setLineWrap(true);
		inserisciNotaTextArea.setWrapStyleWord(true);
		inserisciNotaTextArea.setPreferredSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width-50, 200));
		inserisciNotaTextArea.setMaximumSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width-50, 200));
		if(offertaDaModificare != null)
			inserisciNotaTextArea.setText(offertaDaModificare.getNota());
		
		inserisciNotaTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				if(inserisciNotaTextArea.getText().length() > 300)
					ke.consume();
			}
		});
		
		panelCentrale.add(inserisciNotaTextArea);
		
		panelNotaOfferta.add(panelSuperiore, BorderLayout.NORTH);
		panelNotaOfferta.add(panelCentrale, BorderLayout.CENTER);
		
		return panelNotaOfferta;
	}	
	
	@Override
	protected MyJPanel creaPanelBottoni(Annuncio annuncioPerOfferta, Offerta offertaDaModificare) {
		MyJPanel panelBottoni = new MyJPanel();
		panelBottoni.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelBottoni.setBackground(Color.orange);
		panelBottoni.setPreferredSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width, 50));
		panelBottoni.setMaximumSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width, 50));
		panelBottoni.setBackground(new Color(220, 220, 220));
		
		MyJButton bottoneConfermaOfferta;
		
		if(offertaDaModificare == null) {
			bottoneConfermaOfferta = new MyJButton("Conferma la mia offerta!");
			bottoneConfermaOfferta.setDefaultAction(() -> {this.clickBottoneConfermaOfferta(annuncioPerOfferta, null);});
		}
		else {
			bottoneConfermaOfferta = new MyJButton("Modifica la mia offerta!");
			bottoneConfermaOfferta.setDefaultAction(() -> {this.clickBottoneConfermaOfferta(annuncioPerOfferta, offertaDaModificare);});
		}
		
		MyJButton bottoneCiHoRipensato = new MyJButton("Ci ho ripensato...");
		bottoneCiHoRipensato.setDefaultAction(() -> {mainController.passaAFrameHomePage(this);});
		
		panelBottoni.add(Box.createVerticalGlue());
		panelBottoni.add(bottoneCiHoRipensato);
		panelBottoni.add(bottoneConfermaOfferta);
		panelBottoni.add(Box.createVerticalGlue());
		
		return panelBottoni;
	}
	
	@Override
	protected void clickBottoneConfermaOfferta(Annuncio annuncioPerOfferta, Offerta offertaDaModificare) {
		try {
			this.nascondiLabelErrore(this.lblErroreSpedizione);
			this.resettaBordiTextField(new EmptyBorder(5, 5, 5, 5), this.inserisciIndirizzoTextField);
			
			if(this.modalitaSceltaBG.getSelection().getActionCommand().equals("Spedizione"))
				checkResidenza(this.inserisciIndirizzoTextField.getText());
			
			if(offertaDaModificare == null) {
				OffertaRegalo newOfferta = this.organizzaDatiDaPassareAlController(annuncioPerOfferta, null);
				mainController.onConfermaOffertaButtonClicked(newOfferta);
			}
			else {
				offertaDaModificare = organizzaDatiDaPassareAlController(annuncioPerOfferta, offertaDaModificare);
				mainController.onModificaOffertaRegaloButtonClicked(offertaDaModificare);
			}
		}
		catch(ResidenzaException exc2) {
			this.settaLabelETextFieldDiErrore(lblErroreSpedizione, exc2.getMessage(), this.inserisciIndirizzoTextField);
		}
		catch(SQLException exc3) {
			System.out.println(exc3.getErrorCode());
			System.out.println(exc3.getMessage());
			System.out.println(exc3.getSQLState());
		}
		
	}

	@Override
	protected void checkResidenza(String indirizzoDiSpedizione) throws ResidenzaException{
		if(indirizzoDiSpedizione == null || indirizzoDiSpedizione.isBlank())
			throw new ResidenzaException("Il campo residenza è obbligatorio.");
		
		if(indirizzoDiSpedizione.startsWith(" "))
			throw new ResidenzaException("La residenza non può iniziare con uno spazio vuoto.");
		
		if(indirizzoDiSpedizione.endsWith(" "))
			throw new ResidenzaException("La residenza non può terminare con uno spazio vuoto.");
		
		if(indirizzoDiSpedizione.length() > 75)
			throw new ResidenzaException("La residenza deve essere di massimo 75 caratteri.");

	}

	@Override
	protected OffertaRegalo organizzaDatiDaPassareAlController(Annuncio annuncioRiferito, Offerta offertaDaModificare) {
		ModConsegnaEnum modalitaConsegnaScelta = ModConsegnaEnum.confrontaConStringa(modalitaSceltaBG.getSelection().getActionCommand());
		
		OffertaRegalo offertaToAdd;
		if(offertaDaModificare == null)
			offertaToAdd = new OffertaRegalo(mainController.getUtenteLoggato(), modalitaConsegnaScelta, annuncioRiferito);
		else {
			offertaToAdd = (OffertaRegalo)offertaDaModificare;
			offertaToAdd.setModalitaConsegnaScelta(modalitaConsegnaScelta);
		}
			
		if(modalitaConsegnaScelta.toString().equals("Spedizione"))
			offertaToAdd.setIndirizzoSpedizione(this.inserisciIndirizzoTextField.getText());
		else if(modalitaConsegnaScelta.toString().equals("Ritiro in posta"))
			offertaToAdd.setUfficioRitiro((UfficioPostale)this.ufficiPostaliCB.getSelectedItem());
		else {
			ButtonModel selectedModel = this.incontriBG.getSelection();
			JRadioButton rbSelezionato = null;

			for (Enumeration<AbstractButton> buttons = this.incontriBG.getElements(); buttons.hasMoreElements();) {
			    AbstractButton button = buttons.nextElement();
			    if (button.getModel() == selectedModel) {
			        rbSelezionato = (JRadioButton) button;
			        break;
			    }
			}

			
			String oraInizio = (String)rbSelezionato.getClientProperty("Ora inizio");
			String oraFine = (String)rbSelezionato.getClientProperty("Ora fine");
			GiornoEnum giornoIncontro = (GiornoEnum)rbSelezionato.getClientProperty("Giorno");
			SedeUniversita sedeIncontro = (SedeUniversita)rbSelezionato.getClientProperty("Sede");

			offertaToAdd.setOraInizioIncontro(oraInizio);
			offertaToAdd.setOraFineIncontro(oraFine);
			offertaToAdd.setGiornoIncontro(giornoIncontro);
			offertaToAdd.setSedeDIncontroScelta(sedeIncontro);
		}		
		
		offertaToAdd.setNota(this.inserisciNotaTextArea.getText());
		
		offertaToAdd.setMessaggioMotivazionale(this.inserisciMessaggioTextField.getText());
		
		return offertaToAdd;
	}
}
