package utilities;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class MyJTextField extends JTextField implements ActionListener, KeyListener, MouseListener{

	public static final Border blackBorder = new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), new EmptyBorder(0, 5, 0, 0));
	public static final Border redBorder = new CompoundBorder(BorderFactory.createLineBorder(Color.RED, 2), new EmptyBorder(0, 5, 0, 0));
	
	private Runnable defaultAction;
	private Runnable upAction;
	private Runnable downAction;
	
	public MyJTextField() {
		this.setBorder(blackBorder);
		this.setMaximumSize(new Dimension(300, 30));
		this.setFont(new Font("Ubuntu Sans", Font.PLAIN, 13));
		
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.addActionListener(this);
	}

	public MyJTextField(String stringaDiDefault) {
		this();
		this.setText(stringaDiDefault);
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
	
	public void setDefaultAction(Runnable defaultAction) {
		this.defaultAction = defaultAction;
	}
	
	public void setUpAction(Runnable upAction) {
		this.upAction = upAction;
	}
	
	public void setDownAction(Runnable downAction) {
		this.downAction = downAction;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//Non fa nulla
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			defaultAction.run();
		if(e.getKeyCode() == KeyEvent.VK_UP)
			upAction.run();
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
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
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

}
