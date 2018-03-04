package ui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;

import logica.TipoNodo;

public class TableroDeBotones extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int fila;
	private int columna;
	
	/**
	 * Constructora.
	 * @param fila Filas
	 * @param columna	Columnas
	 */
	public TableroDeBotones(int fila, int columna) {
		super();
		this.setPreferredSize(new Dimension(40,40));
		this.fila = fila;
		this.columna = columna;
	}
	
	/**
	 * Obtiene el bot�n.
	 * @return boton
	 */
	public JButton getBoton() {
		return this;
	}
	
	/**
	 * Obtiene la fila.
	 * @return fila
	 */
	public int getFila() {
		return this.fila;
	}
	
	/**
	 * Obtiene la columna.
	 * @return columna
	 */
	public int getColumna() {
		return this.columna;
	}
	
	public void updateButtons(TipoNodo tipo) {
		switch(tipo) {
			//case VACIO: this.setBackground(Color.GRAY); break;
			case INICIO: this.setBackground(Color.GREEN); break;
			case OBSTACULO: this.setBackground(Color.RED); break;
			case META: this.setBackground(Color.BLACK); break;
			case CAMINO: this.setBackground(Color.BLUE); break;
			default: break;
		}
		//this.setIcon(tab.getCasilla(f, c).icono());
	}
}