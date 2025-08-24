package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.Annuncio;
import dto.AnnuncioRegalo;
import dto.AnnuncioScambio;
import dto.AnnuncioVendita;
import dto.Offerta;
import dto.OffertaAcquisto;
import dto.OffertaScambio;
import eccezioni.OffertaAcquistoException;
import eccezioni.OffertaRegaloException;
import eccezioni.OffertaScambioException;
import net.miginfocom.swing.MigLayout;
import utilities.CategoriaEnum;
import utilities.MyJAnnuncioPanel;
import utilities.MyJAnnuncioSegnalabilePanel;
//import dto.AnnuncioRegalo;
//import dto.AnnuncioScambio;
import utilities.MyJButton;

import utilities.MyJLabel;
import utilities.MyJPanel;
import utilities.MyJTextField;
import utilities.StatoAnnuncioEnum;
import utilities.StatoOffertaEnum;

public class PanelHomePageAnnunci extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private MyJPanel bordoSuperiore = new MyJPanel();
	private MyJPanel barraDiRicerca = new MyJPanel();
	private MyJPanel bordoInferiore = new MyJPanel();
	private MyJPanel panelAnnunciInBacheca = new MyJPanel();
	private MyJPanel panelAnnunci = new MyJPanel();
	private MyJPanel panelRisultatiDiRicerca = new MyJPanel();
	
	private MyJLabel lblRisultatiDiRicerca = new MyJLabel();
	
	private JScrollPane scrollPanelAnnunci;
	
	
	private Controller mainController;
	
	public PanelHomePageAnnunci(Controller controller, ArrayList<Annuncio> annunci) {
		mainController = controller;
		
		this.setLayout(new BorderLayout());
		
		this.settaBordoSuperiore(annunci);
		this.settaBordoInferiore();

//		panelAnnunci.setLayout(new FlowLayout(FlowLayout.CENTER));
		MigLayout layoutPanelAnnunci = new MigLayout("wrap 2", "[]", "");
		panelAnnunci.setLayout(layoutPanelAnnunci);
		
		scrollPanelAnnunci = new JScrollPane(panelAnnunci);
		scrollPanelAnnunci.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPanelAnnunci.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanelAnnunci.getVerticalScrollBar().setUnitIncrement(20);
		scrollPanelAnnunci.getHorizontalScrollBar().setUnitIncrement(20);
		
		scrollPanelAnnunci.addMouseWheelListener(e -> {
			JScrollBar barraVerticale = scrollPanelAnnunci.getVerticalScrollBar();
			JScrollBar barraOrizzontale = scrollPanelAnnunci.getHorizontalScrollBar();
			
		    if (e.isShiftDown()) {
		        barraOrizzontale.setValue(barraOrizzontale.getValue() + e.getUnitsToScroll() * barraOrizzontale.getUnitIncrement());
		    } else {
		        barraVerticale.setValue(barraVerticale.getValue() + e.getUnitsToScroll() * barraVerticale.getUnitIncrement());
		    }
//		    e.consume();
		});


		
		this.settaPanelAnnunciEPanelRisultatiRicerca(annunci, scrollPanelAnnunci);
		
		mostraAnnunciInBacheca(annunci);		
		

		this.add(bordoSuperiore, BorderLayout.NORTH);
		this.add(panelAnnunciInBacheca, BorderLayout.CENTER);
		this.add(bordoInferiore, BorderLayout.SOUTH);
	}
	
	private void settaBordoSuperiore(ArrayList<Annuncio> annunci) {
		bordoSuperiore.setLayout(new BoxLayout(bordoSuperiore, BoxLayout.X_AXIS));
		bordoSuperiore.setAlignmentX(CENTER_ALIGNMENT);
		bordoSuperiore.setPreferredSize(new Dimension(500, 50));
		bordoSuperiore.setBackground(MyJPanel.uninaColorClicked);
		
		barraDiRicerca.setLayout(new BorderLayout());
		barraDiRicerca.setAlignmentX(CENTER_ALIGNMENT);
		barraDiRicerca.setMaximumSize(new Dimension(450, 35));
		barraDiRicerca.setPreferredSize(new Dimension(450, 35));
		barraDiRicerca.setMaximumSize(new Dimension(450, 35));

		JTextField campoDiTestoTextField = new JTextField("Cerca ora!");
		campoDiTestoTextField.setBorder(new EmptyBorder(0, 10, 0, 0));
		campoDiTestoTextField.setFont(new Font("Ubuntu Sans", Font.PLAIN, 15));
		campoDiTestoTextField.setMinimumSize(new Dimension(450, 35));
		campoDiTestoTextField.setPreferredSize(new Dimension(450, 35));
		campoDiTestoTextField.setMaximumSize(new Dimension(450, 35));
		campoDiTestoTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent fe) {
				if(campoDiTestoTextField.getText().equals("Cerca ora!"))
					campoDiTestoTextField.setText("");
			}
			
			@Override
			public void focusLost(FocusEvent fe) {
				if(campoDiTestoTextField.getText().isEmpty())
					campoDiTestoTextField.setText("Cerca ora!");
			}
		});
		campoDiTestoTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				filtraAnnunciPerRicerca(annunci, campoDiTestoTextField.getText());
			}
		});
		
		MyJLabel lblIconaDiRicerca = new MyJLabel();
		lblIconaDiRicerca.aggiungiImmagineScalata("images/iconaDiRicerca.png", 30, 30, false);
		lblIconaDiRicerca.setOpaque(true);
		lblIconaDiRicerca.setBackground(Color.white);
		
		MyJLabel lblIconaDiEliminaTesto = new MyJLabel();
		lblIconaDiEliminaTesto.aggiungiImmagineScalata("images/iconaRimuovi.png", 30, 30, true);
		lblIconaDiEliminaTesto.setOpaque(true);
		lblIconaDiEliminaTesto.setBackground(Color.white);
		
		lblIconaDiEliminaTesto.rendiLabelInteragibile();
		
		lblIconaDiEliminaTesto.setOnMouseClickedAction(() -> {
			campoDiTestoTextField.setText("");
			campoDiTestoTextField.requestFocus();
		});
		
		lblIconaDiEliminaTesto.setOnMouseEnteredAction(() -> {
			lblIconaDiEliminaTesto.setBackground(new Color(220, 220, 220));
			lblIconaDiEliminaTesto.setCursor(new Cursor(Cursor.HAND_CURSOR));
		});
		
		lblIconaDiEliminaTesto.setOnMouseExitedAction(() -> {
			lblIconaDiEliminaTesto.setBackground(Color.white);
			lblIconaDiEliminaTesto.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		});
		
		barraDiRicerca.add(lblIconaDiRicerca, BorderLayout.WEST);
		barraDiRicerca.add(campoDiTestoTextField, BorderLayout.CENTER);
		barraDiRicerca.add(lblIconaDiEliminaTesto, BorderLayout.EAST);
	
		
		JComboBox<String> tipologiaAnnunciCB = new JComboBox();
		tipologiaAnnunciCB.setBackground(Color.white);
		tipologiaAnnunciCB.setMinimumSize(new Dimension(450, 35));
		tipologiaAnnunciCB.setPreferredSize(new Dimension(300, 35));
		tipologiaAnnunciCB.setMaximumSize(new Dimension(300, 35));
		tipologiaAnnunciCB.setFont(new Font("Ubuntu Sans", Font.PLAIN, 20));
		
		tipologiaAnnunciCB.addItem("Tutti gli annunci");
		tipologiaAnnunciCB.addItem("Vendita");
		tipologiaAnnunciCB.addItem("Scambio");
		tipologiaAnnunciCB.addItem("Regalo");
		tipologiaAnnunciCB.setSelectedIndex(0);
		
		JComboBox<String> categoriaOggettoInAnnuncioCB = new JComboBox();
		categoriaOggettoInAnnuncioCB.setBackground(Color.white);
		categoriaOggettoInAnnuncioCB.setMinimumSize(new Dimension(450, 35));
		categoriaOggettoInAnnuncioCB.setPreferredSize(new Dimension(300, 35));
		categoriaOggettoInAnnuncioCB.setMaximumSize(new Dimension(300, 35));
		categoriaOggettoInAnnuncioCB.setFont(new Font("Ubuntu Sans", Font.PLAIN, 20));
		
		categoriaOggettoInAnnuncioCB.addItem("Tutte le categorie");
		categoriaOggettoInAnnuncioCB.addItem(CategoriaEnum.Abbigliamento.toString());
		categoriaOggettoInAnnuncioCB.addItem(CategoriaEnum.Appunti.toString());
		categoriaOggettoInAnnuncioCB.addItem(CategoriaEnum.Collezionismo.toString());
		categoriaOggettoInAnnuncioCB.addItem(CategoriaEnum.Cura_della_persona.toString());
		categoriaOggettoInAnnuncioCB.addItem(CategoriaEnum.Elettronica_e_Informatica.toString());
		categoriaOggettoInAnnuncioCB.addItem(CategoriaEnum.Film.toString());
		categoriaOggettoInAnnuncioCB.addItem(CategoriaEnum.Libri.toString());
		categoriaOggettoInAnnuncioCB.addItem(CategoriaEnum.Libri_di_testo.toString());
		categoriaOggettoInAnnuncioCB.addItem(CategoriaEnum.Musica.toString());
		categoriaOggettoInAnnuncioCB.addItem(CategoriaEnum.Per_la_casa.toString());
		categoriaOggettoInAnnuncioCB.addItem(CategoriaEnum.Sport_e_Tempo_libero.toString());
		categoriaOggettoInAnnuncioCB.setSelectedIndex(0);

		categoriaOggettoInAnnuncioCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				aggiornaFiltri(annunci, tipologiaAnnunciCB, categoriaOggettoInAnnuncioCB);
			}
		});
		
		tipologiaAnnunciCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				aggiornaFiltri(annunci, tipologiaAnnunciCB, categoriaOggettoInAnnuncioCB);
			}
		});
		
		bordoSuperiore.add(Box.createVerticalGlue());
		bordoSuperiore.add(tipologiaAnnunciCB);
		bordoSuperiore.add(Box.createRigidArea(new Dimension(10, 0)));
		bordoSuperiore.add(barraDiRicerca);
		bordoSuperiore.add(Box.createRigidArea(new Dimension(10, 0)));
		bordoSuperiore.add(categoriaOggettoInAnnuncioCB);
		bordoSuperiore.add(Box.createVerticalGlue());
	}
	
	private void settaBordoInferiore() {
		bordoInferiore.setLayout(new BoxLayout(bordoInferiore, BoxLayout.X_AXIS));
		bordoInferiore.setAlignmentX(CENTER_ALIGNMENT);
		bordoInferiore.setPreferredSize(new Dimension(500, 100));
		bordoInferiore.setBackground(new Color(220, 220, 220));	
		
		MyJButton bottonePubblicaAnnuncioVendita = new MyJButton("Pubblica un nuovo annuncio di vendita!");
		bottonePubblicaAnnuncioVendita.setAlignmentX(CENTER_ALIGNMENT);
		bottonePubblicaAnnuncioVendita.setPreferredSize(new Dimension(400, 75));
		bottonePubblicaAnnuncioVendita.setMaximumSize(new Dimension(400, 75));
		bottonePubblicaAnnuncioVendita.setDefaultAction(() -> {mainController.passaAFramePubblicaAnnuncio("Vendita");});
		bottonePubblicaAnnuncioVendita.setUpAction(() -> {});
		bottonePubblicaAnnuncioVendita.setDownAction(() -> {});
		
		MyJButton bottonePubblicaAnnuncioScambio = new MyJButton("Pubblica un nuovo annuncio di scambio!");
		bottonePubblicaAnnuncioScambio.setAlignmentX(CENTER_ALIGNMENT);
		bottonePubblicaAnnuncioScambio.setPreferredSize(new Dimension(400, 75));
		bottonePubblicaAnnuncioScambio.setMaximumSize(new Dimension(400, 75));
		bottonePubblicaAnnuncioScambio.setDefaultAction(() -> {mainController.passaAFramePubblicaAnnuncio("Scambio");});
		bottonePubblicaAnnuncioScambio.setUpAction(() -> {});
		bottonePubblicaAnnuncioScambio.setDownAction(() -> {});
		
		MyJButton bottonePubblicaAnnuncioRegalo = new MyJButton("Pubblica un nuovo annuncio di regalo!");
		bottonePubblicaAnnuncioRegalo.setAlignmentX(CENTER_ALIGNMENT);
		bottonePubblicaAnnuncioRegalo.setPreferredSize(new Dimension(400, 75));
		bottonePubblicaAnnuncioRegalo.setMaximumSize(new Dimension(400, 75));

		bottonePubblicaAnnuncioRegalo.setDefaultAction(() -> {mainController.passaAFramePubblicaAnnuncio("Regalo");});
		bottonePubblicaAnnuncioRegalo.setUpAction(() -> {});
		bottonePubblicaAnnuncioRegalo.setDownAction(() -> {});
		
		bordoInferiore.add(Box.createVerticalGlue());
		bordoInferiore.add(Box.createHorizontalGlue());
		bordoInferiore.add(bottonePubblicaAnnuncioVendita);
		bordoInferiore.add(Box.createHorizontalGlue());
		bordoInferiore.add(bottonePubblicaAnnuncioScambio);
		bordoInferiore.add(Box.createHorizontalGlue());
		bordoInferiore.add(bottonePubblicaAnnuncioRegalo);
		bordoInferiore.add(Box.createHorizontalGlue());
		bordoInferiore.add(Box.createVerticalGlue());
	}
	
	private void settaPanelAnnunciEPanelRisultatiRicerca(ArrayList<Annuncio> annunciInBacheca, JScrollPane scrollPanel) {
		this.panelAnnunciInBacheca.setLayout(new BorderLayout());
		
		panelRisultatiDiRicerca.add(lblRisultatiDiRicerca);
		panelRisultatiDiRicerca.setVisible(false);
			
		panelAnnunciInBacheca.add(panelRisultatiDiRicerca, BorderLayout.NORTH);
		panelAnnunciInBacheca.add(scrollPanel, BorderLayout.CENTER);
	}
	
	private MyJPanel creaPanelFaiOfferta(Annuncio annuncio) {
		MyJPanel panelFaiOfferta = new MyJPanel();
		panelFaiOfferta.setLayout(new BoxLayout(panelFaiOfferta, BoxLayout.X_AXIS));
		panelFaiOfferta.setAlignmentX(CENTER_ALIGNMENT);
		panelFaiOfferta.setPreferredSize(new Dimension(425, 50));
		panelFaiOfferta.setMaximumSize(new Dimension(425, 50));
		panelFaiOfferta.setBackground(Color.white);
		
		MyJButton bottoneFaiOfferta = new MyJButton("Fai un'offerta!");
		bottoneFaiOfferta.setBackground(new Color(65, 106, 144));
		bottoneFaiOfferta.setForeground(Color.white);
		bottoneFaiOfferta.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		bottoneFaiOfferta.setAlignmentX(CENTER_ALIGNMENT);
		
		if(annuncio instanceof AnnuncioVendita)
			bottoneFaiOfferta.setDefaultAction(() -> {
				try {
					this.checkOffertaAcquistoGiaEsistentePerUtente(annuncio.getIdAnnuncio());
					mainController.passaADialogOffertaAcquisto(annuncio);
				}
				catch(OffertaAcquistoException exc) {
					JOptionPane.showMessageDialog(this, exc.getMessage());
				}
			});
		else if(annuncio instanceof AnnuncioScambio)
			bottoneFaiOfferta.setDefaultAction(() -> {
				try {
					this.checkOffertaScambioGiaEsistentePerUtente(annuncio.getIdAnnuncio());
					mainController.passaADialogOffertaScambio(annuncio);
				}
				catch(OffertaScambioException exc) {
					JOptionPane.showMessageDialog(this, exc.getMessage());
				}
			});
		else
			bottoneFaiOfferta.setDefaultAction(() -> {
				try {
					mainController.passaADialogScegliOffertaRegalo(annuncio);
				}
				catch(OffertaRegaloException exc) {
					JOptionPane.showMessageDialog(this, exc.getMessage());
				}
			});
		
		MyJLabel lblInterazioni = new MyJLabel(String.valueOf(annuncio.getNumeroInterazioni()));
		lblInterazioni.aggiungiImmagineScalata("images/iconaMiPiace.png", 25, 25, false);
		
		lblInterazioni.setForeground(Color.red);
		lblInterazioni.setHorizontalTextPosition(SwingConstants.RIGHT);
		
		panelFaiOfferta.add(Box.createVerticalGlue());
		panelFaiOfferta.add(Box.createHorizontalGlue());
		panelFaiOfferta.add(bottoneFaiOfferta);
		panelFaiOfferta.add(Box.createHorizontalGlue());
		panelFaiOfferta.add(lblInterazioni);
		panelFaiOfferta.add(Box.createHorizontalGlue());
		panelFaiOfferta.add(Box.createVerticalGlue());
		
		return panelFaiOfferta;
	}
	
//		protected void ricalcolaAltezzaConAnnunci(ArrayList<Annuncio> annunciInBacheca) {
//		int larghezza = panelAnnunci.getWidth();
//		//600 è l'altezza di un singolo panel dell'annuncio. 10 sono dei pixel aggiuntivi
//		int altezza = (annunciInBacheca.size() / 2 == 0) ? (annunciInBacheca.size()/2 * 610) : ((annunciInBacheca.size()/2+1) * 610);
//		
//		panelAnnunci.setPreferredSize(new Dimension(larghezza, altezza));
//		panelAnnunci.setMaximumSize(new Dimension(larghezza, altezza));
//	}

	private void mostraAnnunciInBacheca(ArrayList<Annuncio> tuttiGliAnnunci) {
//		this.ricalcolaAltezzaConAnnunci(tuttiGliAnnunci);
		panelRisultatiDiRicerca.setVisible(false);
		
		panelAnnunci.removeAll();
		
		for(int i = tuttiGliAnnunci.size()-1; i >= 0; i--) {
			this.panelAnnunci.add(new MyJAnnuncioSegnalabilePanel(mainController, tuttiGliAnnunci.get(i)) {

				@Override
				public MyJPanel creaPanelSottoDescrizione(Annuncio annuncio) {
					return creaPanelFaiOfferta(annuncio);
				}
			});
		}
		
		panelAnnunci.revalidate();
		panelAnnunci.repaint();
		
		SwingUtilities.invokeLater(() -> {
			JScrollBar scrollBarDelPanel = scrollPanelAnnunci.getVerticalScrollBar();
			scrollBarDelPanel.setValue(scrollBarDelPanel.getMinimum());
		});
	}
	
	private void filtraAnnunciPerRicerca(ArrayList<Annuncio> tuttiGliAnnunci, String stringaIn) {
		ArrayList<Annuncio> annunciFiltratiPerRicerca = new ArrayList();
		
		for(Annuncio annuncioCorrente: tuttiGliAnnunci)
			if((annuncioCorrente.getNome().toLowerCase()).contains(stringaIn.toLowerCase()))
				annunciFiltratiPerRicerca.add(annuncioCorrente);

		this.mostraAnnunciInBacheca(annunciFiltratiPerRicerca);
		
		panelRisultatiDiRicerca.setVisible(true);
		
		if(annunciFiltratiPerRicerca.size() == 0)
			lblRisultatiDiRicerca.setText("Siamo spiacenti, ma a quanto pare l'articolo che stai cercando non è presente.");
		else 
			lblRisultatiDiRicerca.setText("Risultati: " + annunciFiltratiPerRicerca.size() + " di " + tuttiGliAnnunci.size());
	}
	
	private void filtraAnnunciPerTipoECategoria(ArrayList<Annuncio> tuttiGliAnnunci, String tipoAnnuncio, String categoriaOggetto) {
		ArrayList<Annuncio> annunciFiltratiPerTipo = new ArrayList();
		
		if(tipoAnnuncio.equals("Tutti gli annunci") && !categoriaOggetto.equals("Tutte le categorie")) {
			for(Annuncio annuncioCorrente: tuttiGliAnnunci)
				if(annuncioCorrente.getOggettoInAnnuncio().getCategoria().equals(categoriaOggetto))
					annunciFiltratiPerTipo.add(annuncioCorrente);
		}
		else if(!tipoAnnuncio.equals("Tutti gli annunci") && categoriaOggetto.equals("Tutte le categorie")) {
			if(tipoAnnuncio.equals("Vendita")) {
				for(Annuncio annuncioCorrente: tuttiGliAnnunci)
					if(annuncioCorrente instanceof AnnuncioVendita)
						annunciFiltratiPerTipo.add(annuncioCorrente);
			}
			else if(tipoAnnuncio.equals("Scambio")) {
				for(Annuncio annuncioCorrente: tuttiGliAnnunci )
					if(annuncioCorrente instanceof AnnuncioScambio)
						annunciFiltratiPerTipo.add(annuncioCorrente);
			}
			else {
				for(Annuncio annuncioCorrente: tuttiGliAnnunci)
					if(annuncioCorrente instanceof AnnuncioRegalo)
						annunciFiltratiPerTipo.add(annuncioCorrente);
			}
		}
		else {
			if(tipoAnnuncio.equals("Vendita")) {
				for(Annuncio annuncioCorrente: tuttiGliAnnunci)
					if((annuncioCorrente instanceof AnnuncioVendita) && (annuncioCorrente.getOggettoInAnnuncio().getCategoria().equals(categoriaOggetto)))
						annunciFiltratiPerTipo.add(annuncioCorrente);
			}
			else if(tipoAnnuncio.equals("Scambio")) {
				for(Annuncio annuncioCorrente: tuttiGliAnnunci )
					if((annuncioCorrente instanceof AnnuncioScambio) && (annuncioCorrente.getOggettoInAnnuncio().getCategoria().equals(categoriaOggetto)))
						annunciFiltratiPerTipo.add(annuncioCorrente);
			}
			else {
				for(Annuncio annuncioCorrente: tuttiGliAnnunci)
					if((annuncioCorrente instanceof AnnuncioRegalo) && (annuncioCorrente.getOggettoInAnnuncio().getCategoria().equals(categoriaOggetto)))
						annunciFiltratiPerTipo.add(annuncioCorrente);
			}
		}
		
		this.mostraAnnunciInBacheca(annunciFiltratiPerTipo);
	
		panelRisultatiDiRicerca.setVisible(true);
		
		if(annunciFiltratiPerTipo.size() == 0)
			lblRisultatiDiRicerca.setText("Siamo spiacenti, ma in questo momento non sono presenti annunci di " + categoriaOggetto + " (" + tipoAnnuncio + " ).");
		else
			lblRisultatiDiRicerca.setText("Risultati: " + annunciFiltratiPerTipo.size() + " di " + tuttiGliAnnunci.size());
	}
	
	private void aggiornaFiltri(ArrayList<Annuncio> annunci, JComboBox tipologiaAnnunciCB, JComboBox categoriaOggettoInAnnuncio) {
		String tipologiaSelezionata = tipologiaAnnunciCB.getSelectedItem().toString();
		String categoriaSelezionata = categoriaOggettoInAnnuncio.getSelectedItem().toString();
		if(tipologiaSelezionata.equals("Tutti gli annunci") && categoriaSelezionata.equals("Tutte le categorie"))
			mostraAnnunciInBacheca(annunci);
		else
			filtraAnnunciPerTipoECategoria(annunci, tipologiaSelezionata, categoriaSelezionata);
		
	}

	private void checkOffertaAcquistoGiaEsistentePerUtente(int idAnnuncioRiferito) throws OffertaAcquistoException{
		
		for(Offerta offertaCorrente : mainController.getUtenteLoggato().getOfferteInAttesa()) {		
			if(offertaCorrente instanceof OffertaAcquisto) {
				if(offertaCorrente.getAnnuncioRiferito().getIdAnnuncio() == idAnnuncioRiferito)
					throw new OffertaAcquistoException("Hai già un'offerta di acquisto attiva per questo annuncio.");
			}
		}
	}
	
	private void checkOffertaScambioGiaEsistentePerUtente(int idAnnuncioRiferito) throws OffertaScambioException{
		
		for(Offerta offertaCorrente : mainController.getUtenteLoggato().getOfferteInAttesa()) {		
			if(offertaCorrente instanceof OffertaScambio) {
				if(offertaCorrente.getAnnuncioRiferito().getIdAnnuncio() == idAnnuncioRiferito)
					throw new OffertaScambioException("Hai già un'offerta di scambio attiva per questo annuncio.");
			}
		}
	}
}
