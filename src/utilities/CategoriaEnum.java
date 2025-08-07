package utilities;

public enum CategoriaEnum {
	Libri_di_testo("Libri di testo"),
	Appunti("Appunti"),
	Elettronica_e_informatica("Elettronica e informatica"),
	Libri("Libri"),
	Per_la_casa("Per la casa"),
	Cura_della_persona("Cura della persona"),
	Abbigliamento("Abbigliamento"),
	Sport_e_tempo_libero("Sport e tempo libero"),
	Musica("Musica"),
	Film("Film"),
	Collezionismo("Collezionismo");
	
	private String categoria;
	
	private CategoriaEnum(String categoria) {
		this.categoria = categoria;
	}
	
	 @Override
	public String toString() {
		return categoria;
	}
}
