package utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.Oggetto;

public class MyJOggettoPanel extends MyJPanel {

	private static final long serialVersionUID = 1L;
	private static final Color[] tonalitaDiUninaLightColor = new Color[5]; 
	protected Color coloreCasualePerBG;
	protected int larghezza = 1200;
	protected int distanzaDalBordo = 10;
	protected int altezza = 700;
	
	static {
		tonalitaDiUninaLightColor[0] = new Color(118, 146, 175);
		tonalitaDiUninaLightColor[1] = new Color(158, 178, 198);
		tonalitaDiUninaLightColor[2] = new Color(198, 209, 221);
	}	
	
	public MyJOggettoPanel(Oggetto oggettoDaMostrare) {
		
		Random generatore = new Random();
		coloreCasualePerBG = tonalitaDiUninaLightColor[generatore.nextInt(5)];
		
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
		panelFotoOggetto.setBackground(coloreCasualePerBG);
		
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
		MyJPanel panelWrapper = new MyJPanel();
		panelWrapper.setLayout(new BoxLayout(panelWrapper, BoxLayout.X_AXIS));
		panelWrapper.setPreferredSize(new Dimension(larghezza, 35));
		panelWrapper.setMaximumSize(new Dimension(larghezza, 35));
		panelWrapper.setBackground(Color.white);
		
		MyJPanel panelCondizioni = new MyJPanel();
		panelCondizioni.setBackground(Color.white);
		panelCondizioni.setLayout(new BoxLayout(panelCondizioni, BoxLayout.X_AXIS));
		panelCondizioni.setPreferredSize(new Dimension(250, 30));
		panelCondizioni.setMaximumSize(new Dimension(250, 30));
		
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
			
			panelCondizioni.add(panelStelleCondizioni);
		}
		else {
			lblCondizioni.aggiungiImmagineScalata(condizioneOggetto.getFixPerRicondizionato(), 25, 25, false);
			lblCondizioni.setHorizontalTextPosition(SwingConstants.RIGHT);
		}
		panelCondizioni.add(lblCondizioni);
		
		MyJLabel lblCategoria = new MyJLabel(categoriaOggetto.toString());
		lblCategoria.aggiungiImmagineScalata(categoriaOggetto.getImmagineCategoria(), 25, 25, false);
		lblCategoria.setHorizontalTextPosition(SwingConstants.RIGHT);
		
		panelWrapper.add(Box.createVerticalGlue());
		panelWrapper.add(Box.createHorizontalGlue());
		panelWrapper.add(panelCondizioni);
		panelWrapper.add(Box.createHorizontalGlue());
		panelWrapper.add(lblCategoria);
		panelWrapper.add(Box.createHorizontalGlue());
		panelWrapper.add(Box.createVerticalGlue());
		
		return panelWrapper;
	}
	
	private MyJPanel creaPanelDescrizioneOggetto(String descrizioneOggetto) {
		MyJPanel panelDescrizioneOggetto = new MyJPanel();
		panelDescrizioneOggetto.setPreferredSize(new Dimension(larghezza - distanzaDalBordo, 100));
		panelDescrizioneOggetto.setMaximumSize(new Dimension(larghezza - distanzaDalBordo, 100));
		
		JTextArea descrizioneOggettoTextArea = new JTextArea(descrizioneOggetto);
		descrizioneOggettoTextArea.setBackground(coloreCasualePerBG);
		descrizioneOggettoTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		descrizioneOggettoTextArea.setPreferredSize(new Dimension(panelDescrizioneOggetto.getPreferredSize().width, 100));
		descrizioneOggettoTextArea.setMaximumSize(new Dimension(panelDescrizioneOggetto.getPreferredSize().width, 100));
		descrizioneOggettoTextArea.setEditable(false);
		descrizioneOggettoTextArea.setOpaque(true);
		descrizioneOggettoTextArea.setLineWrap(true);
		descrizioneOggettoTextArea.setWrapStyleWord(true);
		descrizioneOggettoTextArea.setAlignmentX(LEFT_ALIGNMENT);
		descrizioneOggettoTextArea.setFont(new Font("Ubuntu Sans", Font.ITALIC, 16));
		
		panelDescrizioneOggetto.add(descrizioneOggettoTextArea);
		
		return panelDescrizioneOggetto;
	}

}
