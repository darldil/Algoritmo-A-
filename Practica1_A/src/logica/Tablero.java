package logica;

import java.util.ArrayList;

public class Tablero {
	private static final int FILAS = 10;
	private static final int COLUMNAS = 10;
	private int filas;
	private int columnas;
	private int inicioF;
	private int inicioC;
	private int metaF;
	private int metaC;
	private ArrayList<ArrayList<Nodo>> tablero;
	
	public Tablero() {
		this.tablero = new ArrayList<ArrayList<Nodo>>();
		this.filas = FILAS;
		this.columnas = COLUMNAS;
		this.inicioF = 0;
		this.inicioC = 0;
		this.metaF = 0;
		this.metaC = 0;
		this.inicializarTablero();
	}
	
	public Tablero(int f, int c) {
		this.tablero = new ArrayList<ArrayList<Nodo>>();
		this.filas = f;
		this.columnas = c;this.inicioF = 0;
		this.inicioC = 0;
		this.metaF = 0;
		this.metaC = 0;
		this.inicializarTablero();
	}
	
	private void inicializarTablero() {
		for (int f = 0; f < this.filas; f++) {
			tablero.add(new ArrayList<Nodo>());
			for (int c = 0; c < this.columnas; c++)
				tablero.get(f).add(new Nodo(f, c));
		}
		
		for (int f = 0; f < filas; f++) {
			for (int c = 0; c < columnas; c++) {
				Nodo nodo = tablero.get(f).get(c);
				if (f < filas - 1)
					nodo.setNorte(tablero.get(f + 1).get(c));
				if (f > 0) 
					nodo.setSur(tablero.get(f - 1).get(c));
				if (c < columnas - 1)
					nodo.setEste(tablero.get(f).get(c + 1));
				if (c > 0)
					nodo.setOeste(tablero.get(f).get(c - 1));
				if (f > 0 && c < 0)
					nodo.setSuroeste(tablero.get(f - 1).get(c - 1));
				if (f > 0 && c < columnas - 1)
					nodo.setSureste(tablero.get(f - 1).get(c + 1));
				if (f < filas - 1 && c < columnas - 1)
					nodo.setNoreste(tablero.get(f + 1).get(c + 1));
				if (f < filas - 1 && c > 0)
					nodo.setNoroeste(tablero.get(f + 1).get(c - 1));
			}
		}
	}
	
	public Nodo getNodo(int f, int c) {
		return this.tablero.get(f).get(c);
	}
	
	public void setNodo(int f, int c, Nodo data) {
		this.tablero.get(f).set(c, data);
	}
	
	public void setObstaculo (int f, int c, boolean esObstaculo) {
		this.tablero.get(f).get(c).setObstaculo(esObstaculo);
	}
	
	public void establecerInicio(int f, int c) {
		this.setNodoInicio(this.inicioF, this.inicioC, false);
		this.inicioF = f;
		this.inicioC = c;
		this.setNodoInicio(f, c, true);
	}
	
	public void establecerMeta(int f, int c) {
		this.setNodoMeta(this.metaF, this.metaC, false);
		this.metaF = f;
		this.metaC = c;
		this.setNodoMeta(f, c, true);
	}
	
	private void setNodoInicio (int f, int c, boolean esInicio) {
		this.tablero.get(f).get(c).setInicio(esInicio);
	}
	
	private void setNodoMeta (int f, int c, boolean esMeta) {
		this.tablero.get(f).get(c).setMeta(esMeta);
	}
	
	public Nodo getNodoInicio() {
		return this.getNodo(this.inicioF, this.inicioC);
	}
	
	public Nodo getNodoMeta() {
		return this.getNodo(this.metaF, this.metaC);
	}
	
	public int getFilas() {
		return filas;
	}
	
	public int getColumnas() {
		return columnas;
	}
	
	public float calcularDistancias(Nodo nodo1, Nodo nodo2) {
		if (nodo1.getF() == nodo2.getF() || nodo1.getC() == nodo2.getC()) 
			return filas + columnas;
		return (float) 1.7 * (filas + columnas);
	}
}
