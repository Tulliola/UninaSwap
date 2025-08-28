package utilities;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.ui.FlatTextFieldUI;

public class MyJTextField extends JTextField implements ActionListener, KeyListener, MouseListener, FocusListener{

	public static final Border blackBorder = new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(0, 5, 0, 0));
	public static final Border redBorder = new CompoundBorder(BorderFactory.createLineBorder(Color.RED, 2), new EmptyBorder(0, 5, 0, 0));

	private Runnable defaultAction;
	private Runnable upAction;
	private Runnable downAction;
	private Runnable focusGainedAction;
	private Runnable focusLostAction;
	private Runnable keyTypedAction;
	
	public MyJTextField() {
		this.setBorder(blackBorder);
		this.setMaximumSize(new Dimension(300, 30));
		this.setFont(new Font("Ubuntu Sans", Font.PLAIN, 13));
	}

	public MyJTextField(String stringaDiDefault) {
		this();
		this.setText(stringaDiDefault);
	}
	
	public MyJTextField(String stringaDiDefault, int limiteDiColonne) {
		this(stringaDiDefault);
		this.setColumns(limiteDiColonne);
	}
	
	public static ImageIcon immageIconDaByteArray(byte[] image) {
		BufferedImage img;
		try {
			img = ImageIO.read(new ByteArrayInputStream(image));
		} catch (IOException e) {
			img = null;
			e.printStackTrace();
		}
		ImageIcon immagineDaScalare = new ImageIcon(img);
		
		Image immagineScalata = immagineDaScalare.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
		
		return new ImageIcon(immagineScalata);
	}
	
	public void settaBordiTextFieldStandard() {
		this.setBorder(blackBorder);		
	}
	
	public void settaBordiTextFieldErrore() {
		this.setBorder(redBorder);
	}

		public MyJTextField(Font font) {
		this();
		this.setFont(font);
	}
	
	public void modificaBGColorSeEnabled(Color coloreDaDisabilitato, Color coloreDaAbilitato) {
		if(!(this.isEnabled())) {
			this.setDisabledTextColor(Color.black);
			this.setBackground(coloreDaDisabilitato);
		}
		else
			this.setBackground(coloreDaAbilitato);

	}
	
	public void cambiaStatoEnabled() {
		if(!(this.isEnabled()))
			this.setEnabled(true);
		else
			this.setEnabled(false);
	}
	
	public void cambiaStatoVisible() {
		if(!(this.isVisible()))
			this.setVisible(true);
		else
			this.setVisible(false);
	}
	
	public void rendiTextFieldFocusable() {
		this.addFocusListener(this);
	}
	
	public void rendiTextFieldMouseListenable() {
		this.addMouseListener(this);
	}
	
	public void rendiTextFieldKeyListenable() {
		this.addKeyListener(this);
	}
	
	public void rendiTextFieldActionListenable() {
		this.addActionListener(this);
	}
	
	public void setDefaultAction(Runnable defaultAction) {
		this.defaultAction = defaultAction;
	}
	
	public void setUpAction(Runnable upAction) {
		this.upAction = upAction;
	}
	
	public void setDownAction(Runnable downAction) {
		this.downAction = downAction;
	}
	
	public void setFocusGainedAction(Runnable focusGainedAction) {
		this.focusGainedAction = focusGainedAction;
	}
	
	public void setFocusLostAction(Runnable focusLostAction) {
		this.focusLostAction = focusLostAction;
	}

	public void setKeyTypedAction(Runnable keyTypedAction) {
		this.keyTypedAction = keyTypedAction;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		keyTypedAction.run();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int tastoDigitato = e.getKeyCode();
		
		if(tastoDigitato == KeyEvent.VK_ENTER)
			defaultAction.run();
		else if(tastoDigitato == KeyEvent.VK_UP)
			upAction.run();
		else if(tastoDigitato == KeyEvent.VK_DOWN)
			downAction.run();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//Non fa nulla
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		defaultAction.run();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//Non fa nulla
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
	public void mouseEntered(MouseEvent e) {
		setCursor(new Cursor(Cursor.TEXT_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void focusGained(FocusEvent e) {
		focusGainedAction.run();
	}

	@Override
	public void focusLost(FocusEvent e) {
		focusLostAction.run();
	}
}
