package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.Annuncio;
import dto.AnnuncioRegalo;
import dto.AnnuncioVendita;
import dto.Offerta;
import dto.OffertaAcquisto;
import dto.SedeUniversita;
import dto.UfficioPostale;
import eccezioni.PrezzoOffertoException;
import eccezioni.ResidenzaException;
import eccezioni.SaldoException;
import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.MyJButton;
import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;

public class DialogOffertaAcquisto extends DialogOfferta {

	private static final long serialVersionUID = 1L;
	private MyJPanel panelMieProposte;
	private MyJTextField inserisciPrezzoTextField;
	private MyJLabel lblErrorePrezzoOfferto;
	
	
	public DialogOffertaAcquisto(Annuncio annuncioPerOfferta, Controller controller) {
		super(annuncioPerOfferta, controller);
	}
	
	public DialogOffertaAcquisto(Annuncio annuncioPerOfferta, Controller controller, Offerta offertaDaModificare) {
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
	
		MyJPanel panelPrezzoIniziale = new MyJPanel();
		panelPrezzoIniziale.setBackground(Color.white);
		MyJLabel lblPrezzoIniziale = new MyJLabel();
		lblPrezzoIniziale.setAlignmentX(LEFT_ALIGNMENT);

		if(!(annuncioPerOfferta instanceof AnnuncioRegalo)) {
			lblPrezzoIniziale.setText(mainController.getUtenteLoggato().getUsername()+ ", il prezzo iniziale del mio articolo è €" + annuncioPerOfferta.getPrezzoIniziale() + "...");
			lblPrezzoIniziale.aggiungiImmagineScalata("images/iconaPrezzoIniziale.png", 25, 25, false);
			panelPrezzoIniziale.add(lblPrezzoIniziale);
			panelPrezzoIniziale.add(new MyJLabel(" ma sono disposto a trattare!"));
		}
		else {
			lblPrezzoIniziale.setText(mainController.getUtenteLoggato().getUsername()+ ", questo articolo è in regalo!");
			lblPrezzoIniziale.aggiungiImmagineScalata("images/iconaAnnuncioRegaloColored.png", 25, 25, false);
			panelPrezzoIniziale.add(lblPrezzoIniziale);
		}

		panelDatiProposte.add(Box.createVerticalGlue());
		panelDatiProposte.add(panelPrezzoIniziale);
		panelDatiProposte.add(this.creaPanelModalitaConsegnaProposte(annuncioPerOfferta));
		
		return panelDatiProposte;
	}
	
	@Override
	protected MyJPanel creaPanelMieProposte(Annuncio annuncioPerOfferta, Offerta offertaDaModificare) {
		panelMieProposte = new MyJPanel();
		panelMieProposte.setLayout(new BorderLayout());
		
		panelMieProposte.add(this.creaPanelPrezzoOfferto(annuncioPerOfferta, offertaDaModificare), BorderLayout.NORTH);
		panelMieProposte.add(this.creaPanelModalitaConsegnaScelta(annuncioPerOfferta, offertaDaModificare), BorderLayout.CENTER);
		panelMieProposte.add(this.creaPanelNotaOfferta(annuncioPerOfferta, offertaDaModificare), BorderLayout.SOUTH);
		
		return panelMieProposte;
	}
	
	private MyJPanel creaPanelPrezzoOfferto(Annuncio annuncioPerOfferta, Offerta offertaDaModificare) {
		MyJPanel panelPrezzoOfferto = new MyJPanel();
		panelPrezzoOfferto.setLayout(new BoxLayout(panelPrezzoOfferto, BoxLayout.Y_AXIS));
		panelPrezzoOfferto.setPreferredSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width, 100));
		panelPrezzoOfferto.setMaximumSize(new Dimension(this.panelLaMiaOfferta.getMaximumSize().width, 100));
		panelPrezzoOfferto.setBackground(MyJPanel.uninaLightColor);

		MyJPanel panelWrapper = new MyJPanel();
		panelWrapper.setLayout(new BoxLayout(panelWrapper, BoxLayout.X_AXIS));
		panelWrapper.setBackground(MyJPanel.uninaLightColor);
		
		MyJLabel lblPrezzoOfferto = new MyJLabel(annuncioPerOfferta.getUtenteProprietario().getUsername() + ", sono disposto ad offrire ");
		lblPrezzoOfferto.aggiungiImmagineScalata("images/iconaPrezzoIniziale.png", 25, 25, false);
		inserisciPrezzoTextField = new MyJTextField();
		
		if(annuncioPerOfferta instanceof AnnuncioVendita) {
			Double prezzoOffertaMinimo = (annuncioPerOfferta.getPrezzoIniziale() * 0.4) * 100;
			prezzoOffertaMinimo = Math.ceil(prezzoOffertaMinimo);
			prezzoOffertaMinimo /= 100;
			inserisciPrezzoTextField.setText(prezzoOffertaMinimo.toString());
		}
		inserisciPrezzoTextField.setPreferredSize(new Dimension (100, 25));		
		inserisciPrezzoTextField.setMaximumSize(new Dimension (100, 25));
		inserisciPrezzoTextField.setBorder(new EmptyBorder(5, 5, 5, 5));
		if(offertaDaModificare != null) {
			Double prezzoOffertoRound = offertaDaModificare.getPrezzoOfferto() * 100;
			prezzoOffertoRound = Math.floor(prezzoOffertoRound);
			prezzoOffertoRound /= 100;
			inserisciPrezzoTextField.setText(prezzoOffertoRound.toString());
		}
		
		MyJLabel lblEuro = new MyJLabel(" € ");
		
		panelWrapper.add(lblPrezzoOfferto);
		panelWrapper.add(lblEuro);
		panelWrapper.add(inserisciPrezzoTextField);
		
		lblErrorePrezzoOfferto = new MyJLabel(true);
		lblErrorePrezzoOfferto.setAlignmentX(CENTER_ALIGNMENT);
		
		inserisciPrezzoTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char carattereDigitato = e.getKeyChar();
				
				if(Character.isDigit(carattereDigitato)) {
					int posizioneDiUnPunto = inserisciPrezzoTextField.getText().indexOf('.');
					
					if(posizioneDiUnPunto != -1 && inserisciPrezzoTextField.getText().length() - posizioneDiUnPunto > 2)
						e.consume();
					else {
						inserisciPrezzoTextField.setBorder(new EmptyBorder(5, 5, 5, 5));
						lblErrorePrezzoOfferto.setVisible(false);
					}
				}
				else if(carattereDigitato != '.'){
					e.consume();
					inserisciPrezzoTextField.settaBordiTextFieldErrore();
					lblErrorePrezzoOfferto.setText("Formato non valido.");
					lblErrorePrezzoOfferto.setVisible(true);
				}
				else if(inserisciPrezzoTextField.getText().contains(".") || inserisciPrezzoTextField.getText().length() == 0) {
					e.consume();
					inserisciPrezzoTextField.settaBordiTextFieldErrore();
					lblErrorePrezzoOfferto.setText("Formato non valido.");
					lblErrorePrezzoOfferto.setVisible(true);
				}
				else {
					inserisciPrezzoTextField.setBorder(new EmptyBorder(5, 5, 5, 5));
					lblErrorePrezzoOfferto.setVisible(false);
				}
			}
		});
		
		panelPrezzoOfferto.add(Box.createVerticalGlue());
		panelPrezzoOfferto.add(Box.createHorizontalGlue());
		
		if(annuncioPerOfferta instanceof AnnuncioRegalo) {
			panelPrezzoOfferto.add(this.creaPanelMessaggioMotivazionale(offertaDaModificare));
			panelPrezzoOfferto.add(Box.createVerticalGlue());
		}
		
		panelPrezzoOfferto.add(panelWrapper);
		panelPrezzoOfferto.add(Box.createVerticalGlue());
		panelPrezzoOfferto.add(lblErrorePrezzoOfferto);
		panelPrezzoOfferto.add(Box.createHorizontalGlue());
		panelPrezzoOfferto.add(Box.createVerticalGlue());
		
		return panelPrezzoOfferto;

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
			bottoneConfermaOfferta.setDefaultAction(() -> {
				double prezzoOfferto = Double.parseDouble(inserisciPrezzoTextField.getText()) * 100;
				prezzoOfferto = Math.ceil(prezzoOfferto);
				prezzoOfferto /= 100;
				((OffertaAcquisto)offertaDaModificare).setPrezzoOfferto(prezzoOfferto);
				
				this.gestisciAttributiComuniDaModificare(annuncioPerOfferta, offertaDaModificare);
			});		
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
			this.nascondiLabelErrore(this.lblErrorePrezzoOfferto, this.lblErroreSpedizione);
			this.resettaBordiTextField(new EmptyBorder(5, 5, 5, 5), this.inserisciIndirizzoTextField, this.inserisciPrezzoTextField);
			
			if(annuncioPerOfferta instanceof AnnuncioVendita) {
				double offertaMinima = annuncioPerOfferta.getPrezzoIniziale() * 100;
				offertaMinima = Math.ceil(offertaMinima);
				offertaMinima *= 0.4;
				offertaMinima /= 100;
				checkPrezzoOfferto(this.inserisciPrezzoTextField.getText(), offertaMinima, annuncioPerOfferta.getPrezzoIniziale());
			}
			else if(annuncioPerOfferta instanceof AnnuncioRegalo)
				checkPrezzoOfferto(this.inserisciPrezzoTextField.getText(), 0.01, 0);

			if(this.modalitaSceltaBG.getSelection().getActionCommand().equals("Spedizione"))
				checkResidenza();
			
			if(offertaDaModificare == null) {
				OffertaAcquisto newOfferta = (OffertaAcquisto) this.organizzaDatiDaPassareAlController(annuncioPerOfferta, offertaDaModificare);
				mainController.onConfermaOffertaButtonClicked(newOfferta);
			}
			else {
				mainController.onModificaOffertaAcquistoButtonClicked(offertaDaModificare);
			}
			
		}
		catch(PrezzoOffertoException | SaldoException throwables) {
			this.settaLabelETextFieldDiErrore(lblErrorePrezzoOfferto, throwables.getMessage(), this.inserisciPrezzoTextField);
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
	
	private void checkPrezzoOfferto(String prezzoOfferto, double prezzoMinimo, double prezzoIniziale) throws SaldoException, PrezzoOffertoException {
		
		if(this.inserisciPrezzoTextField.getText().isEmpty() || this.inserisciPrezzoTextField.getText() == null)
			throw new PrezzoOffertoException("Inserisci un'offerta monetaria.");
		
		double prezzoOffertoDouble = Double.valueOf(prezzoOfferto);
		
		if(mainController.getUtenteLoggato().getSaldo() <= prezzoOffertoDouble)
			throw new SaldoException("Saldo insufficiente.");
		
		if(prezzoIniziale > 0.0) {
			if(prezzoOffertoDouble < prezzoMinimo)
				throw new PrezzoOffertoException("Il prezzo offerto deve essere almeno pari a " + prezzoMinimo + "€ (il 40%).");
			
			if(prezzoOffertoDouble > prezzoIniziale)
				throw new PrezzoOffertoException("Il prezzo offerto deve essere al più pari al prezzo iniziale.");
		}
		else if(prezzoIniziale == 0.0) {
			if(prezzoOffertoDouble < prezzoMinimo)
				throw new PrezzoOffertoException("Il prezzo offerto deve essere almeno pari a € 0.01. Se vuoi accettare il regalo, ritira questa offerta.");
			
		}
	}
	
	@Override
	protected Offerta organizzaDatiDaPassareAlController(Annuncio annuncioRiferito, Offerta offertaDaModificare) {
		ModConsegnaEnum modalitaConsegnaScelta = ModConsegnaEnum.confrontaConStringa(modalitaSceltaBG.getSelection().getActionCommand());
		double prezzoOfferto = Double.parseDouble(inserisciPrezzoTextField.getText()) * 100;
		prezzoOfferto = Math.ceil(prezzoOfferto);
		prezzoOfferto /= 100;
		
		
		OffertaAcquisto offertaToAdd;
		if(offertaDaModificare == null) {
			offertaToAdd = new OffertaAcquisto(mainController.getUtenteLoggato(), modalitaConsegnaScelta, annuncioRiferito, prezzoOfferto);
		}
		else {
			offertaToAdd = (OffertaAcquisto)offertaDaModificare;
			offertaToAdd.setModalitaConsegnaScelta(modalitaConsegnaScelta);
			offertaToAdd.setPrezzoOfferto(prezzoOfferto);
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
		
		if(annuncioRiferito instanceof AnnuncioRegalo)
			offertaToAdd.setMessaggioMotivazionale(this.inserisciMessaggioTextField.getText());
		
		return offertaToAdd;
	}
}
