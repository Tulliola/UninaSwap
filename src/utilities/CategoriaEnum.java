package utilities;

import javax.swing.ImageIcon;

public enum CategoriaEnum {
	Libri_di_testo("Libri di testo", new ImageIcon("images/iconaLibriDitesto.png")),
	Appunti("Appunti", new ImageIcon("images/iconaAppunti.png")),
	Elettronica_e_Informatica("Elettronica e Informatica", new ImageIcon("images/iconaElettronicaEInformatica.png")),
	Libri("Libri", new ImageIcon("images/iconaLibri.png")),
	Per_la_casa("Per la casa", new ImageIcon("images/iconaPerLaCasa.png")),
	Cura_della_persona("Cura della persona", new ImageIcon("images/iconaCuraDellaPersona.png")),
	Abbigliamento("Abbigliamento", new ImageIcon("images/iconaAbbigliamento.png")),
	Sport_e_Tempo_libero("Sport e Tempo libero", new ImageIcon("images/iconaSportETempoLibero.png")),
	Musica("Musica", new ImageIcon("images/iconaMusica.png")),
	Film("Film", new ImageIcon("images/iconaFilm.png")),
	Collezionismo("Collezionismo", new ImageIcon("images/iconaCollezionismo.png"));
	
	private String categoria;
	private ImageIcon immagineCategoria;
		
	private CategoriaEnum(String categoria, ImageIcon immagine) {
		this.categoria = categoria;
		this.immagineCategoria = immagine;
	}
	
	public static CategoriaEnum confrontaConStringa(String valoreCategoria){
		for(CategoriaEnum c : values()) {
			if(c.categoria.equals(valoreCategoria))
				return c;
		}
		throw new IllegalArgumentException("Valore non presente nel database");
	}
	
	 @Override
	public String toString() {
		return categoria;
	}
	 
	 public ImageIcon getImmagineCategoria() {
		 return immagineCategoria;
	 }
}
