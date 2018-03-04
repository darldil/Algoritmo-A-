package main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ui.Controlador;
import ui.Window;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controlador controlador = new Controlador();
		
		 try {
	            // Set cross-platform Java L&F (also called "Metal")
	        UIManager.setLookAndFeel(
	            UIManager.getCrossPlatformLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
	    }
	    catch (ClassNotFoundException e) {
	       // handle exception
	    }
	    catch (InstantiationException e) {
	       // handle exception
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    }
		
		new Window(controlador);
		
	}

}
