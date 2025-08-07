package controller;

import java.sql.*;

//Import dal package GUI
import gui.*;


public class Controller {
//	private FrameDiLogin frameDiLogin;
	private FrameDiRegistrazione frameDiRegistrazione;
	
	public Controller() {
		frameDiRegistrazione = new FrameDiRegistrazione(this);
		frameDiRegistrazione.setVisible(true);
		
		//FrameDiLogin loginFrame = new FrameDiLogin(this);
	}	
	
	public static void main(String[] args) {
		Controller mainController = new Controller();
	}
}

