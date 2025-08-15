package utilities;

import javax.swing.ImageIcon;

public enum CategoriaEnum {
	Libri_di_testo("Libri di testo"),
	Appunti("Appunti"),
	Elettronica_e_Informatica("Elettronica e Informatica"),
	Libri("Libri"),
	Per_la_casa("Per la casa"),
	Cura_della_persona("Cura della persona"),
	Abbigliamento("Abbigliamento"),
	Sport_e_Tempo_libero("Sport e Tempo libero"),
	Musica("Musica"),
	Film("Film"),
	Collezionismo("Collezionismo");
	
	private String categoria;
	private ImageIcon immaginePerCategoria;
	
	private CategoriaEnum(String categoria, ImageIcon immaginePerCategoria) {
		this.categoria = categoria;
		this.immaginePerCategoria = immaginePerCategoria;
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
}
