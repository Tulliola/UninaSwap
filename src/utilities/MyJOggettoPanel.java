package utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dto.Oggetto;

public class MyJOggettoPanel extends MyJPanel {

	private static final long serialVersionUID = 1L;
	protected int larghezza = 1200;
	protected int distanzaDalBordo = 10;
	protected int altezza = 680;	
	
	public MyJOggettoPanel(Oggetto oggettoDaMostrare) {
				
		this.setBackground(uninaLightColor);
		this.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.add(this.creaPanelOggetto(oggettoDaMostrare));
	}

	private MyJPanel creaPanelOggetto(Oggetto oggettoDaMostrare) {
		MyJPanel panelOggetto = new MyJPanel();
		panelOggetto.setLayout(new BoxLayout(panelOggetto, BoxLayout.Y_AXIS));	
		panelOggetto.setPreferredSize(new Dimension(larghezza, altezza));
		panelOggetto.setMaximumSize(new Dimension(larghezza, altezza));
		panelOggetto.setBackground(uninaColorClicked);
		
		panelOggetto.add(this.creaPanelFotoOggetto(oggettoDaMostrare.getImmagini()));
		panelOggetto.add(this.creaPanelCondizioniECategoria(oggettoDaMostrare.getCondizioniEnum(), oggettoDaMostrare.getCategoriaEnum()));
		panelOggetto.add(this.creaPanelDescrizioneOggetto(oggettoDaMostrare.getDescrizione()));
		
		return panelOggetto;
	}
	
	private MyJPanel creaPanelFotoOggetto(byte[][] immaginiOggetto) {
		MyJPanel panelFotoOggetto = new MyJPanel();
		panelFotoOggetto.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelFotoOggetto.setPreferredSize(new Dimension(larghezza, 510));
		panelFotoOggetto.setMaximumSize(new Dimension(larghezza, 510));
		panelFotoOggetto.setBackground(uninaColorClicked);
		
		for(int i = 0; i < 3; i++) {
			MyJPanel panelFoto;
			MyJLabel lblAggiungiFoto = new MyJLabel();
			lblAggiungiFoto.setAlignmentX(CENTER_ALIGNMENT);

			if(immaginiOggetto[i] == null)
				lblAggiungiFoto.aggiungiImmagineScalata("images/iconaAggiungiImmagine.png", 100, 100, false);
			else
				lblAggiungiFoto.aggiungiImmagineScalata(immaginiOggetto[i], 375, 500, false);
			
			panelFoto = new MyJPanel();
			panelFoto.setLayout(new BoxLayout(panelFoto, BoxLayout.Y_AXIS));
			panelFoto.setBackground(Color.white);
			panelFoto.setPreferredSize(new Dimension(375, 500));
			panelFoto.setMaximumSize(new Dimension(375, 500));
			panelFoto.setAlignmentX(CENTER_ALIGNMENT);
			panelFotoOggetto.add(panelFoto);
			
			panelFoto.add(Box.createVerticalGlue());
			panelFoto.add(Box.createHorizontalGlue());
			panelFoto.add(lblAggiungiFoto);
			panelFoto.add(Box.createHorizontalGlue());
			panelFoto.add(Box.createVerticalGlue());
		}	
		
		return panelFotoOggetto;
	}
	
	private MyJPanel creaPanelCondizioniECategoria(CondizioneEnum condizioneOggetto, CategoriaEnum categoriaOggetto) {
		MyJPanel panelCondizioniECategoria = new MyJPanel();
		panelCondizioniECategoria.setLayout(new BorderLayout());
		panelCondizioniECategoria.setPreferredSize(new Dimension(larghezza - distanzaDalBordo, 35));
		panelCondizioniECategoria.setMaximumSize(new Dimension(larghezza - distanzaDalBordo, 35));
		panelCondizioniECategoria.setBackground(Color.white);
		
		MyJPanel panelCondizioni = new MyJPanel();
		panelCondizioni.setBackground(Color.white);
		panelCondizioni.setLayout(new BoxLayout(panelCondizioni, BoxLayout.X_AXIS));
		panelCondizioni.setPreferredSize(new Dimension(panelCondizioniECategoria.getPreferredSize().width/2, panelCondizioniECategoria.getPreferredSize().height));
		panelCondizioni.setMaximumSize(new Dimension(panelCondizioniECategoria.getMaximumSize().width/2, panelCondizioniECategoria.getMaximumSize().height));
		
		MyJLabel lblCondizioni = new MyJLabel(condizioneOggetto.toString(), condizioneOggetto.getColoreCondizione());

		if(!condizioneOggetto.equals(CondizioneEnum.Ricondizionato)){
			MyJPanel panelStelleCondizioni = new MyJPanel();
			panelStelleCondizioni.setBackground(Color.white);
			panelStelleCondizioni.setPreferredSize(new Dimension(90, 30));
			MyJLabel lblStella1 = new MyJLabel();
			lblStella1.aggiungiImmagineScalata(condizioneOggetto.getStella1(), 25, 25, false);
			MyJLabel lblStella2 = new MyJLabel();
			lblStella2.aggiungiImmagineScalata(condizioneOggetto.getStella2(), 25, 25, false);
			MyJLabel lblStella3 = new MyJLabel();
			lblStella3.aggiungiImmagineScalata(condizioneOggetto.getStella3(), 25, 25, false);
			panelStelleCondizioni.add(lblStella1);
			panelStelleCondizioni.add(lblStella2);
			panelStelleCondizioni.add(lblStella3);
			panelStelleCondizioni.add(lblCondizioni);
			
			panelCondizioni.add(panelStelleCondizioni);
		}
		else {
			lblCondizioni.aggiungiImmagineScalata(condizioneOggetto.getFixPerRicondizionato(), 25, 25, false);
			lblCondizioni.setHorizontalTextPosition(SwingConstants.RIGHT);
			panelCondizioni.add(lblCondizioni);
		}
		
		MyJPanel panelCategoria = new MyJPanel();
		panelCategoria.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelCategoria.setBackground(Color.white);
		panelCategoria.setPreferredSize(new Dimension(panelCondizioniECategoria.getPreferredSize().width/2, panelCondizioniECategoria.getPreferredSize().height));
		panelCategoria.setMaximumSize(new Dimension(panelCondizioniECategoria.getMaximumSize().width/2, panelCondizioniECategoria.getMaximumSize().height));
		
		MyJLabel lblCategoria = new MyJLabel(categoriaOggetto.toString());
		lblCategoria.aggiungiImmagineScalata(categoriaOggetto.getImmagineCategoria(), 25, 25, false);
		lblCategoria.setHorizontalTextPosition(SwingConstants.RIGHT);
		panelCategoria.add(lblCategoria);
		
		panelCondizioniECategoria.add(panelCondizioni, BorderLayout.WEST);
		panelCondizioniECategoria.add(panelCategoria, BorderLayout.CENTER);
		
		return panelCondizioniECategoria;
	}
	
	private MyJPanel creaPanelDescrizioneOggetto(String descrizioneOggetto) {
		MyJPanel panelDescrizioneOggetto = new MyJPanel();
		panelDescrizioneOggetto.setBackground(uninaLightColor);
		panelDescrizioneOggetto.setPreferredSize(new Dimension(larghezza - distanzaDalBordo, 100));
		panelDescrizioneOggetto.setMaximumSize(new Dimension(larghezza - distanzaDalBordo, 100));
		
		JTextArea descrizioneOggettoTextArea = new JTextArea(descrizioneOggetto);
		descrizioneOggettoTextArea.setBackground(uninaLightColor);
		descrizioneOggettoTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		descrizioneOggettoTextArea.setPreferredSize(new Dimension(panelDescrizioneOggetto.getPreferredSize().width, 100));
		descrizioneOggettoTextArea.setMaximumSize(new Dimension(panelDescrizioneOggetto.getPreferredSize().width, 100));
		descrizioneOggettoTextArea.setEditable(false);
		descrizioneOggettoTextArea.setFocusable(false);
		descrizioneOggettoTextArea.setOpaque(true);
		descrizioneOggettoTextArea.setLineWrap(true);
		descrizioneOggettoTextArea.setWrapStyleWord(true);
		descrizioneOggettoTextArea.setAlignmentX(LEFT_ALIGNMENT);
		descrizioneOggettoTextArea.setFont(new Font("Ubuntu Sans", Font.ITALIC, 16));
		
		panelDescrizioneOggetto.add(descrizioneOggettoTextArea);
		
		return panelDescrizioneOggetto;
	}

}
