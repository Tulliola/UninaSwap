package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
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
import utilities.MyJAnnuncioSegnalabilePanel;
import utilities.MyJButton;

import utilities.MyJLabel;
import utilities.MyJPanel;

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

	private JComboBox<String> tipologiaAnnunciCB;

	private JComboBox<String> categoriaOggettoInAnnuncioCB;
	
	public PanelHomePageAnnunci(Controller controller, ArrayList<Annuncio> annunci) {
		mainController = controller;
		
		this.setLayout(new BorderLayout());
		
		this.settaBordoSuperiore(annunci);
		this.settaBordoInferiore();

		panelAnnunci.setBackground(MyJPanel.uninaLightColor);
		
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
		    } 
		    else {
		        barraVerticale.setValue(barraVerticale.getValue() + e.getUnitsToScroll() * barraVerticale.getUnitIncrement());
		    }
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
		bordoSuperiore.setMinimumSize(new Dimension(1050, 50));
		bordoSuperiore.setMaximumSize(new Dimension(1050, 50));
		bordoSuperiore.setPreferredSize(new Dimension(1050, 50));
		bordoSuperiore.setBackground(Color.white);
		
		barraDiRicerca.setLayout(new BorderLayout());
		barraDiRicerca.setAlignmentX(CENTER_ALIGNMENT);
		barraDiRicerca.setMaximumSize(new Dimension(450, 35));
		barraDiRicerca.setPreferredSize(new Dimension(450, 35));
		barraDiRicerca.setMaximumSize(new Dimension(450, 35));
		barraDiRicerca.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		JTextField campoDiTestoTextField = new JTextField() {
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        if (getText().isEmpty() && !isFocusOwner()) {
		            Graphics2D graficaCampoDiTestoTextField = (Graphics2D) g.create();
		            graficaCampoDiTestoTextField.setColor(Color.GRAY);
		            graficaCampoDiTestoTextField.setFont(new Font("Ubuntu Sans", Font.ITALIC, 15));
		            Insets dimensioni = getInsets();
		            graficaCampoDiTestoTextField.drawString("Cerca ora!", dimensioni.left + 2,
		                          getHeight() / 2 + graficaCampoDiTestoTextField.getFontMetrics().getAscent() / 2 - 2);
		            graficaCampoDiTestoTextField.dispose();
		        }
		    }
		};

		
		campoDiTestoTextField.setBorder(new EmptyBorder(0, 10, 0, 0));
		campoDiTestoTextField.setFont(new Font("Ubuntu Sans", Font.PLAIN, 15));
		campoDiTestoTextField.setMinimumSize(new Dimension(390, 35));
		campoDiTestoTextField.setPreferredSize(new Dimension(390, 35));
		campoDiTestoTextField.setMaximumSize(new Dimension(390, 35));
		campoDiTestoTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String tipologiaSelezionata = tipologiaAnnunciCB.getSelectedItem().toString();
				String categoriaSelezionata = categoriaOggettoInAnnuncioCB.getSelectedItem().toString();
				filtraAnnunci(annunci, tipologiaSelezionata, categoriaSelezionata, campoDiTestoTextField.getText());
			}
		});
		
		MyJLabel lblIconaDiRicerca = new MyJLabel();
		lblIconaDiRicerca.aggiungiImmagineScalata("images/iconaDiRicerca.png", 30, 30, false);
		lblIconaDiRicerca.setOpaque(true);
		lblIconaDiRicerca.setBackground(Color.white);
		lblIconaDiRicerca.rendiLabelInteragibile();
		
		lblIconaDiRicerca.setOnMouseClickedAction(() -> {
			String tipologiaSelezionata = tipologiaAnnunciCB.getSelectedItem().toString();
			String categoriaSelezionata = categoriaOggettoInAnnuncioCB.getSelectedItem().toString();
			filtraAnnunci(annunci, tipologiaSelezionata, categoriaSelezionata, campoDiTestoTextField.getText());
		});
		
		lblIconaDiRicerca.setOnMouseEnteredAction(() -> {
			lblIconaDiRicerca.setBackground(new Color(220, 220, 220));
			lblIconaDiRicerca.setCursor(new Cursor(Cursor.HAND_CURSOR));
		});
		
		lblIconaDiRicerca.setOnMouseExitedAction(() -> {
			lblIconaDiRicerca.setBackground(Color.white);
			lblIconaDiRicerca.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		});
		
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
	
		
		tipologiaAnnunciCB = new JComboBox();
		tipologiaAnnunciCB.setBackground(Color.white);
		tipologiaAnnunciCB.setMinimumSize(new Dimension(300, 35));
		tipologiaAnnunciCB.setPreferredSize(new Dimension(300, 35));
		tipologiaAnnunciCB.setMaximumSize(new Dimension(300, 35));
		tipologiaAnnunciCB.setFont(new Font("Ubuntu Sans", Font.PLAIN, 20));
		
		tipologiaAnnunciCB.addItem("Tutti gli annunci");
		tipologiaAnnunciCB.addItem("Vendita");
		tipologiaAnnunciCB.addItem("Scambio");
		tipologiaAnnunciCB.addItem("Regalo");
		tipologiaAnnunciCB.setSelectedIndex(0);
		
		categoriaOggettoInAnnuncioCB = new JComboBox();
		categoriaOggettoInAnnuncioCB.setBackground(Color.white);
		categoriaOggettoInAnnuncioCB.setMinimumSize(new Dimension(300, 35));
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
				filtraAnnunci(annunci, tipologiaAnnunciCB.getSelectedItem().toString(), categoriaOggettoInAnnuncioCB.getSelectedItem().toString(), campoDiTestoTextField.getText());
			}
		});
		
		tipologiaAnnunciCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				filtraAnnunci(annunci, tipologiaAnnunciCB.getSelectedItem().toString(), categoriaOggettoInAnnuncioCB.getSelectedItem().toString(), campoDiTestoTextField.getText());
			}
		});
		
		bordoSuperiore.add(Box.createVerticalGlue());
		bordoSuperiore.add(Box.createHorizontalGlue());
		bordoSuperiore.add(tipologiaAnnunciCB);
		bordoSuperiore.add(Box.createRigidArea(new Dimension(10, 0)));
		bordoSuperiore.add(barraDiRicerca);
		bordoSuperiore.add(Box.createRigidArea(new Dimension(10, 0)));
		bordoSuperiore.add(categoriaOggettoInAnnuncioCB);
		bordoSuperiore.add(Box.createHorizontalGlue());
		bordoSuperiore.add(Box.createVerticalGlue());
	}
	
	private void settaBordoInferiore() {
		bordoInferiore.setLayout(new BoxLayout(bordoInferiore, BoxLayout.X_AXIS));
		bordoInferiore.setAlignmentX(CENTER_ALIGNMENT);
		bordoInferiore.setPreferredSize(new Dimension(500, 100));
		bordoInferiore.setBackground(Color.white);
		bordoInferiore.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		
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
		panelFaiOfferta.setPreferredSize(new Dimension(425, 46));
		panelFaiOfferta.setMaximumSize(new Dimension(425, 46));
		panelFaiOfferta.setBackground(Color.white);
		
		MyJButton bottoneFaiOfferta = new MyJButton("Fai un'offerta!");
		bottoneFaiOfferta.setBackground(new Color(65, 106, 144));
		bottoneFaiOfferta.setForeground(Color.white);
		bottoneFaiOfferta.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		bottoneFaiOfferta.setAlignmentX(CENTER_ALIGNMENT);
		
		if(annuncio instanceof AnnuncioVendita)
			bottoneFaiOfferta.setDefaultAction(() -> {
				try {
					mainController.getUtenteLoggato().checkOffertaAcquistoGiaEsistentePerUtente(annuncio.getIdAnnuncio());
					mainController.passaADialogOffertaAcquisto(annuncio);
				}
				catch(OffertaAcquistoException exc) {
					JOptionPane.showMessageDialog(this, exc.getMessage());
				}
			});
		else if(annuncio instanceof AnnuncioScambio)
			bottoneFaiOfferta.setDefaultAction(() -> {
				try {
					mainController.getUtenteLoggato().checkOffertaScambioGiaEsistentePerUtente(annuncio.getIdAnnuncio());
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

	private void mostraAnnunciInBacheca(ArrayList<Annuncio> tuttiGliAnnunci) {
		panelRisultatiDiRicerca.setVisible(false);
		
		panelAnnunci.removeAll();
		
		panelAnnunci.setLayout(new MigLayout("wrap 2", "[]", ""));

		if(tuttiGliAnnunci.size() == 0) {
			this.panelAnnunci.setLayout(new MigLayout("fill, align center center"));
			
			MyJPanel panelInterno = new MyJPanel();
			panelInterno.setLayout(new BoxLayout(panelInterno, BoxLayout.Y_AXIS));
			panelInterno.setBackground(MyJPanel.uninaLightColor);
			
			MyJLabel lblNonCiSonoAnnunci = new MyJLabel("Al momento non ci sono annunci da mostrare", new Font("Ubuntu Sans", Font.ITALIC, 20));
			lblNonCiSonoAnnunci.setForeground(Color.BLACK);
			lblNonCiSonoAnnunci.setAlignmentX(CENTER_ALIGNMENT);
			MyJLabel lblNoResultsImage = new MyJLabel();
			lblNoResultsImage.aggiungiImmagineScalata("images/iconaNoResults.png", 100, 100, false);
			lblNoResultsImage.setAlignmentX(CENTER_ALIGNMENT);
			
			panelInterno.add(lblNonCiSonoAnnunci);
			panelInterno.add(Box.createVerticalStrut(20));
			panelInterno.add(lblNoResultsImage);

			this.panelAnnunci.add(panelInterno, "align center center");
		}
		
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
	
	private void filtraAnnunci(ArrayList<Annuncio> tuttiGliAnnunci, String tipoAnnuncio, String categoriaOggetto, String ricerca) {
		ArrayList<Annuncio> annunciFiltrati = new ArrayList();
		
		if(tipoAnnuncio.equals("Tutti gli annunci") && !categoriaOggetto.equals("Tutte le categorie")) {
			for(Annuncio annuncioCorrente: tuttiGliAnnunci)
				if(annuncioCorrente.getOggettoInAnnuncio().getCategoria().equals(categoriaOggetto) && (
						annuncioCorrente.getOggettoInAnnuncio().getDescrizione().toLowerCase().contains(ricerca.toLowerCase()) || annuncioCorrente.getNome().toLowerCase().contains(ricerca.toLowerCase())))
					annunciFiltrati.add(annuncioCorrente);
		}
		else if(!tipoAnnuncio.equals("Tutti gli annunci") && categoriaOggetto.equals("Tutte le categorie")) {
			if(tipoAnnuncio.equals("Vendita")) {
				for(Annuncio annuncioCorrente: tuttiGliAnnunci)
					if(annuncioCorrente instanceof AnnuncioVendita && (
							annuncioCorrente.getOggettoInAnnuncio().getDescrizione().toLowerCase().contains(ricerca.toLowerCase()) || annuncioCorrente.getNome().toLowerCase().contains(ricerca.toLowerCase())))
						annunciFiltrati.add(annuncioCorrente);
			}
			else if(tipoAnnuncio.equals("Scambio")) {
				for(Annuncio annuncioCorrente: tuttiGliAnnunci )
					if(annuncioCorrente instanceof AnnuncioScambio && (
							annuncioCorrente.getOggettoInAnnuncio().getDescrizione().toLowerCase().contains(ricerca.toLowerCase()) || annuncioCorrente.getNome().toLowerCase().contains(ricerca.toLowerCase())))
						annunciFiltrati.add(annuncioCorrente);
			}
			else {
				for(Annuncio annuncioCorrente: tuttiGliAnnunci)
					if(annuncioCorrente instanceof AnnuncioRegalo && (
							annuncioCorrente.getOggettoInAnnuncio().getDescrizione().toLowerCase().contains(ricerca.toLowerCase()) || annuncioCorrente.getNome().toLowerCase().contains(ricerca.toLowerCase())))
						annunciFiltrati.add(annuncioCorrente);
			}
		}
		else if(tipoAnnuncio.equals("Tutti gli annunci") && categoriaOggetto.equals("Tutte le categorie")) {
			for(Annuncio annuncioCorrente: tuttiGliAnnunci)
				if(annuncioCorrente.getOggettoInAnnuncio().getDescrizione().toLowerCase().contains(ricerca.toLowerCase()) || annuncioCorrente.getNome().toLowerCase().contains(ricerca.toLowerCase()))
					annunciFiltrati.add(annuncioCorrente);
		}
		else {
			if(tipoAnnuncio.equals("Vendita")) {
				for(Annuncio annuncioCorrente: tuttiGliAnnunci)
					if((annuncioCorrente instanceof AnnuncioVendita) && (annuncioCorrente.getOggettoInAnnuncio().getCategoria().equals(categoriaOggetto) 
							&& (annuncioCorrente.getOggettoInAnnuncio().getDescrizione().toLowerCase().contains(ricerca.toLowerCase()) || annuncioCorrente.getNome().toLowerCase().contains(ricerca.toLowerCase()))))
						annunciFiltrati.add(annuncioCorrente);
			}
			else if(tipoAnnuncio.equals("Scambio")) {
				for(Annuncio annuncioCorrente: tuttiGliAnnunci )
					if((annuncioCorrente instanceof AnnuncioScambio) && (annuncioCorrente.getOggettoInAnnuncio().getCategoria().equals(categoriaOggetto)
							&& (annuncioCorrente.getOggettoInAnnuncio().getDescrizione().toLowerCase().contains(ricerca.toLowerCase()) || annuncioCorrente.getNome().toLowerCase().contains(ricerca.toLowerCase()))))
						annunciFiltrati.add(annuncioCorrente);
			}
			else {
				for(Annuncio annuncioCorrente: tuttiGliAnnunci)
					if((annuncioCorrente instanceof AnnuncioRegalo) && (annuncioCorrente.getOggettoInAnnuncio().getCategoria().equals(categoriaOggetto)
							&& (annuncioCorrente.getOggettoInAnnuncio().getDescrizione().toLowerCase().contains(ricerca.toLowerCase()) || annuncioCorrente.getNome().toLowerCase().contains(ricerca.toLowerCase()))))
						annunciFiltrati.add(annuncioCorrente);
			}
		}
		
		this.mostraAnnunciInBacheca(annunciFiltrati);
	
		panelRisultatiDiRicerca.setVisible(true);
		
		if(annunciFiltrati.size() == 0)
			lblRisultatiDiRicerca.setText("Siamo spiacenti, ma in questo momento non sono presenti annunci per " + ricerca + " di " + categoriaOggetto + " (" + tipoAnnuncio + ").");
		else
			lblRisultatiDiRicerca.setText("Risultati: " + annunciFiltrati.size() + " di " + tuttiGliAnnunci.size());
	}
}
