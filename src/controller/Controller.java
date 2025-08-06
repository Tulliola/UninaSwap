package controller;

import java.sql.*;

//Import dal package GUI
import gui.FrameDiLogin;


public class Controller {
	private FrameDiLogin loginFrame;
	
	public Controller() {
		loginFrame = new FrameDiLogin(this);
		loginFrame.setVisible(true);
	}	
	
	public static void main(String[] args) {
		Controller mainController = new Controller();
	}
}

