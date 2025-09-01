package utilities;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class MyJButton extends JButton implements ActionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	
	public static final Color uninaColor = new Color(65, 106, 144);
	public static final Color uninaLightColor = new Color(198, 210, 222);
	
	private Runnable defaultAction;
	private Runnable upAction;
	private Runnable downAction;
	
	private Integer badgeText;
	
	public MyJButton() {
		this.setBackground(new Color(65, 106, 144));
		this.setForeground(Color.WHITE);
		this.setFont(new Font("Ubuntu Sans", Font.BOLD, 15));

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
	
	public void rendiNotificabile(Integer numeroOfferte) {
		this.badgeText = numeroOfferte;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (badgeText != null) {
            Graphics2D graficaConBadgeRosso = (Graphics2D) g.create();
            graficaConBadgeRosso.setColor(Color.RED);

            graficaConBadgeRosso.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            graficaConBadgeRosso.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            int size = 20;
            int padding = 4;
            int x = getWidth() - size + padding;
            int y = 0;

            graficaConBadgeRosso.fillOval(x, y, size, size);

            graficaConBadgeRosso.setColor(Color.WHITE);
            graficaConBadgeRosso.setFont(graficaConBadgeRosso.getFont().deriveFont(Font.BOLD, 11));
            FontMetrics fm = graficaConBadgeRosso.getFontMetrics();
            int textWidth = fm.stringWidth(badgeText.toString());
            int textHeight = fm.getAscent();
            graficaConBadgeRosso.drawString(badgeText.toString(), x + (size - textWidth) / 2, y + (size + textHeight) / 2 - 2);

            graficaConBadgeRosso.dispose();
        }
    }
	
	public void aggiungiImmagineScalata(String stringPath, int larghezza, int altezza) {
		ImageIcon img = new ImageIcon(stringPath);	
		Image resizedImage = img.getImage().getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);
		ImageIcon resizeResult = new ImageIcon(resizedImage);
		
		this.setIcon(resizeResult);
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
	
}
