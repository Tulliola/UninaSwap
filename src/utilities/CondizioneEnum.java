package utilities;

import java.awt.Color;
import javax.swing.ImageIcon;

public enum CondizioneEnum {
	Nuovo("Nuovo", new ImageIcon("images/iconaStellaPiena.png"), new ImageIcon("images/iconaStellaPiena.png"), new ImageIcon("images/iconaStellaPiena.png"), new Color(0, 180, 0)),
	Usato_Come_nuovo("Usato - come nuovo", new ImageIcon("images/iconaStellaPiena.png"), new ImageIcon("images/iconaStellaPiena.png"), new ImageIcon("images/iconaStellaMezzaVuota.png"), new Color(90, 190, 0)),
	Ottime_condizioni("Usato - ottime condizioni", new ImageIcon("images/iconaStellaPiena.png"), new ImageIcon("images/iconaStellaPiena.png"), new ImageIcon("images/iconaStellaVuota.png"), new Color(150, 180, 0)),
	Buone_condizioni("Usato - buone condizioni", new ImageIcon("images/iconaStellaPiena.png"), new ImageIcon("images/iconaStellaMezzaVuota.png"), new ImageIcon("images/iconaStellaVuota.png"), new Color(200, 170, 0)),
	Condizioni_accettabili("Usato - condizioni accettabili", new ImageIcon("images/iconaStellaPiena.png"), new ImageIcon("images/iconaStellaVuota.png"), new ImageIcon("images/iconaStellaVuota.png"), new Color(210, 130, 0)),
	Usurato("Usato - usurato", new ImageIcon("images/iconaStellaMezzaVuota.png"), new ImageIcon("images/iconaStellaVuota.png"), new ImageIcon("images/iconaStellaVuota.png"), new Color(200, 80, 0)),
	Ricondizionato("Ricondizionato", new ImageIcon("images/iconaFix.png"), new Color(170, 0, 0));
		
	private String condizioni;
	private ImageIcon stella1;
	private ImageIcon stella2;
	private ImageIcon stella3;
	private ImageIcon fixPerRicondizionato;
	private Color coloreCondizione;	
	
	CondizioneEnum(String condizioni, ImageIcon stella1, ImageIcon stella2, ImageIcon stella3, Color coloreCondizione) {
		this.condizioni = condizioni;
		
		this.stella1 = stella1;
		this.stella2 = stella2;
		this.stella3 = stella3;
		
		this.coloreCondizione = coloreCondizione;
	}
	
	CondizioneEnum(String condizioni, ImageIcon fixPerRicondizionato, Color colorePerCondizioni){
		this.condizioni = condizioni;
		
		this.fixPerRicondizionato = fixPerRicondizionato;
		
		this.coloreCondizione = colorePerCondizioni;
	}
	
	public static CondizioneEnum confrontaConStringa(String condizioni) {
		for(CondizioneEnum c : values()) {
			if(c.condizioni.equals(condizioni)) {
				return c;
			}
		}
		throw new IllegalArgumentException("Valore non presente nel database");
	}
		
	@Override
	public String toString() {
		return condizioni;
	}
	
	public ImageIcon getStella1() {
		if(stella1 != null)
			return stella1;
		else
			throw new RuntimeException("Icona della stella non prevista");
	}
	
	public ImageIcon getStella2() {
		if(stella2 != null)
			return stella2;
		else
			throw new RuntimeException("Icona della stella non prevista");
	}
	
	public ImageIcon getStella3() {
		if(stella3 != null)
			return stella3;
		else
			throw new RuntimeException("Icona della stella non prevista");
	}
	
	public ImageIcon getFixPerRicondizionato() {
		if(fixPerRicondizionato != null)
			return fixPerRicondizionato;
		else
			throw new RuntimeException("Icona del fix non prevista");
	}
	
	public Color getColoreCondizione() {
		return coloreCondizione;
	}
};
