package utilities;

import javax.swing.JComboBox;

import dto.SedeUniversita;

public class RigaIncontro {
	private JComboBox<SedeUniversita> sedeDiIncontro;
	private JComboBox oraInizioIncontro;
	private JComboBox minutoInizioIncontro;
	private JComboBox oraFineIncontro;
	private JComboBox minutoFineIncontro;
	private JComboBox giornoIncontro;
	
	public RigaIncontro(JComboBox<SedeUniversita> sedeDiIncontro, JComboBox oraInizioIncontro, JComboBox minutoInizioIncontro, JComboBox oraFineIncontro, 
					    JComboBox minutoFineIncontro, JComboBox giornoIncontro) 
	{
		this.sedeDiIncontro = sedeDiIncontro;
		this.oraInizioIncontro = oraInizioIncontro;
		this.minutoInizioIncontro = minutoInizioIncontro;
		this.oraFineIncontro = oraFineIncontro;
		this.minutoFineIncontro = minutoFineIncontro;
		this.giornoIncontro = giornoIncontro;
	}
	
	public SedeUniversita getSedeDiIncontro() {
		return ((SedeUniversita)sedeDiIncontro.getSelectedItem());
	}

	public String getOraInizioIncontro() {
		return oraInizioIncontro.getSelectedItem().toString();
	}

	public String getMinutoInizioIncontro() {
		return minutoInizioIncontro.getSelectedItem().toString();
	}

	public String getOraFineIncontro() {
		return oraFineIncontro.getSelectedItem().toString();
	}

	public String getMinutoFineIncontro() {
		return minutoFineIncontro.getSelectedItem().toString();
	}

	public String getGiornoIncontro() {
		return giornoIncontro.getSelectedItem().toString();
	}
}
