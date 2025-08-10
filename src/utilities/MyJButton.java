package utilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class MyJButton extends JButton implements ActionListener, KeyListener{
	public static final Color uninaColor = new Color(65, 106, 144);
	public static final Color uninaLightColor = new Color(198, 210, 222);
	
	private Runnable defaultAction;
	private Runnable upAction;
	private Runnable downAction;
	
	private Component previousComponent;
	private Component nextComponent;
	
	public MyJButton() {
		this.setBackground(new Color(65, 106, 144));
		this.setForeground(Color.WHITE);
		this.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));
		this.setFocusable(false);

		this.addActionListener(this);
		this.addKeyListener(this);
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent me) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			public void mouseExited(MouseEvent me) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}
	
	public MyJButton(String text) {
		this();
		this.setText(text);
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
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			defaultAction.run();
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_UP) {
			upAction.run();
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			downAction.run();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//Non fa nulla
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		defaultAction.run();
	}
	
	
	public void setPreviousComponent(Component previousComponent) {
		this.previousComponent = previousComponent;
	}
	
	public Component getPreviousComponent() {
		return this.previousComponent;
	}
	
	public void setNextComponent(Component nextComponent) {
		this.nextComponent = nextComponent;
	}
	
	public Component getNextComponent() {
		return this.nextComponent;
	}
}
