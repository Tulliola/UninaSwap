package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.Annuncio;
import dto.AnnuncioRegalo;
import dto.Offerta;
import dto.OffertaScambio;
import dto.SedeUniversita;
import dto.UfficioPostale;
import dto.Oggetto;
import eccezioni.OffertaScambioException;
import eccezioni.ResidenzaException;
import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.MyJButton;
import utilities.MyJLabel;
import utilities.MyJPanel;

public class DialogOffertaScambio extends DialogOfferta {
	
	private static final long serialVersionUID = 1L;
		
	public DialogOffertaScambio(Annuncio annuncioPerOfferta, Controller controller) {
		super(annuncioPerOfferta, controller);
	}
	
	public DialogOffertaScambio(Annuncio annuncioPerOfferta, Controller controller, OffertaScambio offertaDaModificare) {
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
		
		MyJPanel panelNotaScambio = new MyJPanel();
		panelNotaScambio.setLayout(new BoxLayout(panelNotaScambio, BoxLayout.Y_AXIS));
		panelNotaScambio.setBackground(Color.white);
		panelNotaScambio.setAlignmentX(CENTER_ALIGNMENT);
		panelNotaScambio.setPreferredSize(new Dimension(this.panelProposteVenditore.getPreferredSize().width-10, 200));
		panelNotaScambio.setMaximumSize(new Dimension(this.panelProposteVenditore.getPreferredSize().width-10, 200));
		
		MyJLabel lblNotaScambio = new MyJLabel();
		lblNotaScambio.setAlignmentX(CENTER_ALIGNMENT);

		if(!(annuncioPerOfferta instanceof AnnuncioRegalo)) {
			lblNotaScambio.setText(mainController.getUtenteLoggato().getUsername() + ", per questo articolo vorrei...");
			lblNotaScambio.aggiungiImmagineScalata("images/iconaFrecceScambio.png", 25, 25, false);

			JTextArea notaScambioTextA = new JTextArea();
			notaScambioTextA.setText(annuncioPerOfferta.getNotaScambio());
			notaScambioTextA.setEditable(false);
			notaScambioTextA.setOpaque(false);
			notaScambioTextA.setLineWrap(true);
			notaScambioTextA.setPreferredSize(new Dimension(this.panelDatiProposte.getPreferredSize().width-10, 100));
			notaScambioTextA.setMaximumSize(new Dimension(this.panelDatiProposte.getPreferredSize().width-10, 100));
			notaScambioTextA.setBorder(new EmptyBorder(5, 5, 5, 5));
			notaScambioTextA.setEnabled(false);
			notaScambioTextA.setDisabledTextColor(Color.black);
			
			panelNotaScambio.add(lblNotaScambio);
			panelNotaScambio.add(notaScambioTextA);
		}
		else {
			lblNotaScambio.setText(mainController.getUtenteLoggato().getUsername() + ", questo articolo è in regalo!");
			lblNotaScambio.aggiungiImmagineScalata("images/iconaAnnuncioRegaloColored.png", 25, 25, false);
			
			panelNotaScambio.add(lblNotaScambio);
		}
		
		
		panelDatiProposte.add(Box.createVerticalGlue());
		panelDatiProposte.add(panelNotaScambio);
		panelDatiProposte.add(this.creaPanelModalitaConsegnaProposte(annuncioPerOfferta));
		
		return panelDatiProposte;
	}

	@Override
	protected MyJPanel creaPanelMieProposte(Annuncio annuncioPerOfferta, Offerta offertaDaModificare) {
		panelMieProposte = new MyJPanel();
		panelMieProposte.setLayout(new BorderLayout());
		
		panelMieProposte.add(this.creaPanelCaricamentoOggetti(annuncioPerOfferta, offertaDaModificare), BorderLayout.NORTH);
		panelMieProposte.add(this.creaPanelModalitaConsegnaScelta(annuncioPerOfferta, offertaDaModificare), BorderLayout.CENTER);
		panelMieProposte.add(this.creaPanelNotaOfferta(annuncioPerOfferta, offertaDaModificare), BorderLayout.SOUTH);
		
		return panelMieProposte;
	}
	
	private MyJPanel creaPanelCaricamentoOggetti(Annuncio annuncioPerOfferta, Offerta offertaDaModificare) {
		MyJPanel panelCaricamentoOggetti = new MyJPanel();
		panelCaricamentoOggetti.setLayout(new BoxLayout(panelCaricamentoOggetti, BoxLayout.Y_AXIS));
		panelCaricamentoOggetti.setAlignmentX(CENTER_ALIGNMENT);
		panelCaricamentoOggetti.setPreferredSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width, 200));
		panelCaricamentoOggetti.setMaximumSize(new Dimension(this.panelLaMiaOfferta.getMaximumSize().width, 200));
		
		MyJPanel panelWrapper = new MyJPanel();
		panelWrapper.setAlignmentX(CENTER_ALIGNMENT);
		panelWrapper.setLayout(new BoxLayout(panelWrapper, BoxLayout.Y_AXIS));
		panelWrapper.setBackground(MyJPanel.uninaLightColor);
		
		MyJLabel lblCaricaOggetti = new MyJLabel(annuncioPerOfferta.getUtenteProprietario().getUsername() + ", ecco gli oggetti che potrei scambiarti: ");
		lblCaricaOggetti.aggiungiImmagineScalata("images/iconaFrecceScambio.png", 25, 25, false);
		lblCaricaOggetto[0] = new MyJLabel(this.defaultStringPerCaricaOggettoLbl);
		lblCaricaOggetto[0].setAlignmentX(CENTER_ALIGNMENT);
		lblCaricaOggetto[0].aggiungiImmagineScalata("images/iconaAggiungiAnnuncio.png", 25, 25, true);
		lblCaricaOggetto[0].rendiLabelInteragibile();
		lblCaricaOggetto[0].setOnMouseEnteredAction(() -> {});
		lblCaricaOggetto[0].setOnMouseExitedAction(() -> {});
		lblCaricaOggetto[0].setOnMouseClickedAction(() -> {
			mainController.passaAFrameCaricaOggetto(0, null);
		});
		
		lblCaricaOggetto[1] = new MyJLabel(this.defaultStringPerCaricaOggettoLbl);
		lblCaricaOggetto[1].setAlignmentX(CENTER_ALIGNMENT);
		lblCaricaOggetto[1].aggiungiImmagineScalata("images/iconaAggiungiAnnuncio.png", 25, 25, true);
		lblCaricaOggetto[1].rendiLabelInteragibile();
		lblCaricaOggetto[1].setOnMouseEnteredAction(() -> {});
		lblCaricaOggetto[1].setOnMouseExitedAction(() -> {});
		lblCaricaOggetto[1].setOnMouseClickedAction(() -> {
			mainController.passaAFrameCaricaOggetto(1, null);
		});

		lblCaricaOggetto[2] = new MyJLabel(this.defaultStringPerCaricaOggettoLbl);
		lblCaricaOggetto[2].setAlignmentX(CENTER_ALIGNMENT);
		lblCaricaOggetto[2].aggiungiImmagineScalata("images/iconaAggiungiAnnuncio.png", 25, 25, true);
		lblCaricaOggetto[2].rendiLabelInteragibile();
		lblCaricaOggetto[2].setOnMouseEnteredAction(() -> {});
		lblCaricaOggetto[2].setOnMouseExitedAction(() -> {});
		lblCaricaOggetto[2].setOnMouseClickedAction(() -> {
			mainController.passaAFrameCaricaOggetto(2, null);
		});

		
		this.settaLabelEFlagSeOffertaDaModificare(offertaDaModificare);
		
		lblErroreCaricamentoOggetti = new MyJLabel(true);
		lblErroreCaricamentoOggetti.setAlignmentX(CENTER_ALIGNMENT);
		
		panelWrapper.add(lblCaricaOggetti);
		
		panelCaricamentoOggetti.add(Box.createVerticalGlue());
		
		if(annuncioPerOfferta instanceof AnnuncioRegalo) {
			panelCaricamentoOggetti.add(this.creaPanelMessaggioMotivazionale(offertaDaModificare));
			panelCaricamentoOggetti.add(Box.createVerticalGlue());
		}
		
		panelCaricamentoOggetti.add(panelWrapper);
		panelCaricamentoOggetti.add(lblCaricaOggetto[0]);
		panelCaricamentoOggetti.add(lblCaricaOggetto[1]);
		panelCaricamentoOggetti.add(lblCaricaOggetto[2]);
		panelCaricamentoOggetti.add(lblErroreCaricamentoOggetti);
		panelCaricamentoOggetti.setBackground(MyJPanel.uninaLightColor);
		panelCaricamentoOggetti.add(Box.createVerticalGlue());
		
		return panelCaricamentoOggetti;
	}

	@Override
	protected MyJPanel creaPanelBottoni(Annuncio annuncioPerOfferta, Offerta offertaDaModificare) {
		MyJPanel panelBottoni = new MyJPanel();
		panelBottoni.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelBottoni.setBackground(Color.orange);
		panelBottoni.setPreferredSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width, 50));
		panelBottoni.setMaximumSize(new Dimension(this.panelLaMiaOfferta.getPreferredSize().width, 50));
		panelBottoni.setBackground(new Color(220, 220, 220));
		
		MyJButton bottoneConfermaModificaOfferta;
	
		if(offertaDaModificare == null) {
			bottoneConfermaModificaOfferta = new MyJButton("Conferma la mia offerta!");
			bottoneConfermaModificaOfferta.setDefaultAction(() -> {
				this.clickBottoneConfermaOfferta(annuncioPerOfferta, null);
			});
		}
		else {
			bottoneConfermaModificaOfferta = new MyJButton("Modifica la mia offerta!");
			bottoneConfermaModificaOfferta.setDefaultAction(() -> {
				this.gestisciAttributiComuniDaModificare(annuncioPerOfferta, offertaDaModificare);
			});
		}
		MyJButton bottoneCiHoRipensato = new MyJButton("Ci ho ripensato...");
		bottoneCiHoRipensato.setDefaultAction(() -> {
			mainController.passaAFrameHomePage(this);
		});
		
		panelBottoni.add(Box.createVerticalGlue());
		panelBottoni.add(bottoneCiHoRipensato);
		panelBottoni.add(bottoneConfermaModificaOfferta);
		panelBottoni.add(Box.createVerticalGlue());
		
		return panelBottoni;
	}
	
	@Override
	protected void clickBottoneConfermaOfferta(Annuncio annuncioPerOfferta, Offerta offertaDaModificare) {
		try {
			this.nascondiLabelErrore(this.lblErroreSpedizione, this.lblErroreCaricamentoOggetti);
			this.resettaBordiTextField(new EmptyBorder(5, 5, 5, 5), this.inserisciIndirizzoTextField);
			
			checkNumeroOggettiCaricati();
			if(this.modalitaSceltaBG.getSelection().getActionCommand().equals("Spedizione"))
				this.checkResidenza();
			
			if(offertaDaModificare == null) {
				OffertaScambio newOfferta = this.organizzaDatiDaPassareAlController(annuncioPerOfferta, null);
				mainController.onConfermaOffertaButtonClicked(newOfferta);
			}
			else {
				offertaDaModificare = this.organizzaDatiDaPassareAlController(annuncioPerOfferta, offertaDaModificare);
				mainController.onModificaOffertaScambioButtonClicked(offertaDaModificare, operazioniDaEseguire);
			}
			
		}
		catch(OffertaScambioException exc1) {
			lblErroreCaricamentoOggetti.setText(exc1.getMessage());
			lblErroreCaricamentoOggetti.setVisible(true);			
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
	
	private void checkNumeroOggettiCaricati() throws OffertaScambioException{
		if(this.numeroOggettiCaricati == 0)
			throw new OffertaScambioException("Devi caricare almeno un oggetto.");
	}
	
	@Override
	protected OffertaScambio organizzaDatiDaPassareAlController(Annuncio annuncioRiferito, Offerta offerta) {
		
		ModConsegnaEnum modalitaConsegnaScelta = ModConsegnaEnum.confrontaConStringa(modalitaSceltaBG.getSelection().getActionCommand());

		ArrayList<Oggetto> oggettiCaricati = new ArrayList<Oggetto>();
		
		OffertaScambio offertaToAdd;
		
		if(offerta == null) {
			for(int i = 0; i < isOggettoCaricato.length; i++)
				if(isOggettoCaricato[i]) {
					Oggetto oggettoCaricato;
					oggettoCaricato = mainController.recuperaOggettoDaFrameCaricaOggetto(i);
					
					oggettiCaricati.add(oggettoCaricato);
				}
			offertaToAdd = new OffertaScambio(mainController.getUtenteLoggato(), modalitaConsegnaScelta, annuncioRiferito, oggettiCaricati);
		}
		else {
			operazioniDaEseguire = new ArrayList<String>();

			for(int i = 0; i < isOggettoCaricato.length; i++) {
				if(isOggettoCaricato[i]) {

					Oggetto oggettoToAdd = mainController.recuperaOggettoDaFrameCaricaOggetto(i);
					oggettiCaricati.add(oggettoToAdd);
					
					if(isOggettoCaricatoPrimaDellaModifica[i]) {
						oggettoToAdd.setIdOggetto(offerta.getOggettiOfferti().get(i).getIdOggetto());
						operazioniDaEseguire.add("UPDATE");
					}
					else
						operazioniDaEseguire.add("INSERT");
				}
				else if(isOggettoCaricatoPrimaDellaModifica[i]){
					oggettiCaricati.add(offerta.getOggettiOfferti().get(i));
					operazioniDaEseguire.add("DELETE");
				}
				
			}
			((OffertaScambio)offerta).setOggettiOfferti(oggettiCaricati);
			offertaToAdd = (OffertaScambio) offerta;
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

	public void eliminaOggettoCaricato(int indiceDelFrameDaCuiRicevi) {
		this.lblCaricaOggetto[indiceDelFrameDaCuiRicevi].setText(this.defaultStringPerCaricaOggettoLbl);
		this.lblCaricaOggetto[indiceDelFrameDaCuiRicevi].aggiungiImmagineScalata("images/iconaAggiungiAnnuncio.png", 25, 25, true);
		this.numeroOggettiCaricati--;
		this.isOggettoCaricato[indiceDelFrameDaCuiRicevi] = false;
	}
	
	public void aggiungiOggettoCaricato(int indiceDelFrameDaCuiRicevi, String nomeOggetto) {
		this.lblCaricaOggetto[indiceDelFrameDaCuiRicevi].setText(nomeOggetto);
		this.lblCaricaOggetto[indiceDelFrameDaCuiRicevi].aggiungiImmagineScalata("images/iconModify.png", 25, 25, true);
		this.numeroOggettiCaricati++;
		this.isOggettoCaricato[indiceDelFrameDaCuiRicevi] = true;
	}
	
	public void modificaOggettoCaricato(int indiceDelFrameDaCuiRicevi, String nomeOggetto) {
		this.lblCaricaOggetto[indiceDelFrameDaCuiRicevi].setText(nomeOggetto);
	}
	
	private void settaLabelEFlagSeOffertaDaModificare(Offerta offertaDaModificare) {
		if(offertaDaModificare != null) {
			numeroOggettiCaricati = offertaDaModificare.getOggettiOfferti().size();

			for(int i = 0; i < numeroOggettiCaricati; i++) {
				lblCaricaOggetto[i].setText("Oggetto " + (i+1) + " offerto");
				lblCaricaOggetto[i].aggiungiImmagineScalata("images/iconModify.png", 25, 25, false);
				isOggettoCaricato[i] = true;
				isOggettoCaricatoPrimaDellaModifica[i] = true;
				
				final int indiceAttuale = i;

				lblCaricaOggetto[i].setOnMouseClickedAction(() -> {
					if(isOggettoCaricato[indiceAttuale])
						mainController.passaAFrameCaricaOggetto(indiceAttuale, offertaDaModificare.getOggettiOfferti().get(indiceAttuale));
					else
						mainController.passaAFrameCaricaOggetto(indiceAttuale, null);
				});
			}
			
		}
	}
}
