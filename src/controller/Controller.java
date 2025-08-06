package controller;

import java.sql.*;

//Import dal package GUI
import gui.FrameDiLogin;


public class Controller {
	public Controller() {
		FrameDiLogin loginFrame = new FrameDiLogin(this);
	}	
	
	public static void main(String[] args) {
		Controller mainController = new Controller();
	}
}

