package main;

import ui.Controlador;
import ui.Window;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controlador controlador = new Controlador();
		
		new Window(controlador);
		
	}

}
