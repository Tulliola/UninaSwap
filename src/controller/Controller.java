package controller;

import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

//Import dal package GUI
import gui.*;


public class Controller {
	private FrameDiLogin frameDiLogin;
	private FrameDiRegistrazione frameDiRegistrazione;
	
	public Controller() {
		frameDiLogin = new FrameDiLogin(this);
		frameDiLogin.setVisible(true);
		frameDiRegistrazione = new FrameDiRegistrazione(this);
	}	
	
	public static void main(String[] args) {
		Controller mainController = new Controller();
	}

	public void passaAFrameDiRegistrazione() {
		frameDiLogin.dispose();
		frameDiRegistrazione.setVisible(true);
	}
}