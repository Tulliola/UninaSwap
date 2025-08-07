package controller;

import java.sql.*;

//Import dal package GUI
import gui.*;


public class Controller {
	private FrameDiLogin frameDiLogin;
	private FrameDiRegistrazione frameDiRegistrazione;
	
	public Controller() {
		frameDiLogin = new FrameDiLogin(this);
		frameDiLogin.setVisible(true);
		frameDiRegistrazione = new FrameDiRegistrazione(this);
		frameDiRegistrazione.setVisible(true);
		
	}	
	
	public static void main(String[] args) {
		Controller mainController = new Controller();
	}
}