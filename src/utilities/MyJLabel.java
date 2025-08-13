package utilities;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MyJLabel extends JLabel implements ActionListener, MouseListener{
	
	private Runnable defaultAction;
	private Runnable onMouseEnteredAction;
	private Runnable onMouseExitedAction;
	private Runnable onMouseClickedAction;
	
	private byte[] immagineInByte;
	
	public static final Border blackBorder = new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(0, 5, 0, 0));
	public static final Border redBorder = new CompoundBorder(BorderFactory.createLineBorder(Color.RED, 2), new EmptyBorder(0, 5, 0, 0));
	
	public static final Color uninaColor = new Color(65, 106, 144);
	public static final Color uninaLightColor = new Color(198, 210, 222);
	
	public MyJLabel() {
		this.setForeground(Color.black);
		this.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
	}
	
	public MyJLabel(String stringaDiDefault) {
		this();
		this.setText(stringaDiDefault);
		
	}
	
	public MyJLabel(boolean isLabelDiErrore) {
		this();
		
		if(isLabelDiErrore) {
			this.setForeground(Color.red);
			this.setFont(new Font("Ubuntu Sans", Font.BOLD, 13));
			this.setVisible(false);
		}
	}
	
	public MyJLabel(int larghezza, int altezza) {
		this();
		this.setPreferredSize(new Dimension(larghezza, altezza));
	}
	
	public MyJLabel(String stringaDiDefault, Icon immagineLabel) {
		this(stringaDiDefault);
		this.setIcon(immagineLabel);
	}

	
	public MyJLabel(ImageIcon immagine, boolean isCliccabile) {
		this();
		this.setIcon(immagine);

		if(isCliccabile) {
			this.aggiungiEffettoCliccabilitaPerImmagine();
		}
	}
	
	
	public MyJLabel(Font font) {
		this.setFont(font);
	}
	
	public MyJLabel(String stringaDiDefault, Font font) {
		this();
		this.setText(stringaDiDefault);
		this.setFont(font);
	}
	
	public MyJLabel(String stringaDiDefault, Font font, Color color) {
		this.setText(stringaDiDefault);
		this.setFont(font);
		this.setForeground(color);
	}
	
	public MyJLabel(String stringaDiDefault, Font font, boolean isLabelDiErrore) {
		this.setText(stringaDiDefault);
		this.setFont(font);
		
		if(isLabelDiErrore) {
			this.setBorder(redBorder);
		}
	}
	
	public void aggiungiEffettoCliccabilitaPerTesto() {
		Font oldFont = this.getFont();
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				setFont(new Font("Ubuntu Sans", Font.BOLD, oldFont.getSize()+1));
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}	
			
			@Override
			public void mouseExited(MouseEvent me) {
				setFont(oldFont);
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}
	
	public void aggiungiEffettoCliccabilitaPerImmagine() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseExited(MouseEvent me) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}				
		});
	}
	
	public void aggiungiImmagineScalata(String stringPath, int larghezza, int altezza, boolean isCliccabile) {
		ImageIcon img = new ImageIcon(stringPath);	
		Image resizedImage = img.getImage().getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
		ImageIcon resizeResult = new ImageIcon(resizedImage);
		
		if(isCliccabile)
			this.aggiungiEffettoCliccabilitaPerImmagine();
		
		this.setIcon(resizeResult);
	}
	
	public void aggiungiImmagineScalata(byte[] immagine, int larghezza, int altezza, boolean isCliccabile) {
		ImageIcon img = new ImageIcon(immagine);
		Image resizedImage = img.getImage().getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
		ImageIcon resizeResult = new ImageIcon(resizedImage);
		
		if(isCliccabile)
			this.aggiungiEffettoCliccabilitaPerImmagine();
		
		this.setIcon(resizeResult);
	}
	
	public void aggiungiImmagineScalata(ImageIcon immagine, int larghezza, int altezza, boolean isCliccabile) {
		Image resizedImage = immagine.getImage().getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
		ImageIcon resizeResult = new ImageIcon(resizedImage);
		
		if(isCliccabile)
			this.aggiungiEffettoCliccabilitaPerImmagine();
		
		this.setIcon(resizeResult);
	}

	public boolean aggiungiImmagineDaFileSystem() {
		JFileChooser scelta = new JFileChooser();
	    FileNameExtensionFilter filtro = new FileNameExtensionFilter(
	        "JPG & PNG", "jpg", "png");
	    scelta.setFileFilter(filtro);
	    int returnVal = scelta.showOpenDialog(this.getParent());
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	File fileSelezionato = scelta.getSelectedFile();
	    	
	    	try {
	    		byte[] fileInByte = this.trasformaImmagineInArrayDiByte(fileSelezionato.getAbsolutePath());
	    		this.aggiungiImmagineScalata(fileInByte, 375, 500, false);
	    		immagineInByte = fileInByte;
	    		return true;
	    	}
	    	catch(IOException exc) {
	    		exc.printStackTrace();
	    	}
	    }
	    return false;
	}
	
	public byte[] trasformaImmagineInArrayDiByte(String filePath) throws IOException {
        return Files.readAllBytes(new File(filePath).toPath());
    }
	
	public byte[] getImmagineInByte() {
		return immagineInByte;
	}
	
	public void rendiLabelInteragibile() {
		this.addMouseListener(this);
	}
	
	public void setDefaultAction(Runnable defaultAction) {
		this.defaultAction = defaultAction;
	}
	
	public void setOnMouseEnteredAction(Runnable onMouseEntered) {
		this.onMouseEnteredAction = onMouseEntered;
	}
	
	public void setOnMouseExitedAction(Runnable onMouseExited) {
		this.onMouseExitedAction = onMouseExited;
	}
	
	public void setOnMouseClickedAction(Runnable onMouseClicked) {
		this.onMouseClickedAction = onMouseClicked;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		defaultAction.run();
	}


	@Override
	public void mousePressed(MouseEvent e) {
		//Non fa nulla
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//Non fa nulla
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		onMouseClickedAction.run();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		onMouseEnteredAction.run();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		onMouseExitedAction.run();
	}
}
