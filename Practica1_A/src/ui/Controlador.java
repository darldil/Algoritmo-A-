package ui;

import logica.Algoritmo;
import logica.Tablero;
import logica.Transfer;

public class Controlador {

	private Tablero tablero;
	private Algoritmo alg;
	
	public Controlador() {
		this.tablero = new Tablero();
		this.alg = new Algoritmo(this.tablero);
	}
	
	public Tablero getTablero() {
		return this.tablero;
	}
	
	/*public ArrayList<Nodo> getListaNodosModificados() {
		return alg.getListaCerrada();
	}*/
	
	private Estado reset(Object datos) {
		this.tablero = new Tablero(((Transfer) datos).getF(), ((Transfer)datos).getC());
		this.alg = new Algoritmo(this.tablero);
		
		return Estado.START;
	}
	
	public Object accion(Acciones evento, Object datos) {
		int x = 0, y = 0;
		Estado estado = null;
		switch(evento) {
			case RESET: 
				estado = this.reset(datos);
				break;
			case START:
				x = ((Transfer) datos).getF();
				y = ((Transfer) datos).getC();
				alg.setInicio(x, y);
				alg.setEstado(Estado.OBSTACLES);
				estado = Estado.OBSTACLES;
				break;
				
			case PUTOBSTACLES:
				x = ((Transfer) datos).getF();
				y = ((Transfer) datos).getC();
				if (x == -1 || y == -1) {
					alg.setEstado(Estado.END);
					estado = Estado.END;
				}
				else {
					alg.setObstaculo(x, y);
					estado = Estado.OBSTACLES;
				}
				break;
				
			case PUTEND:
				x = ((Transfer) datos).getF();
				y = ((Transfer) datos).getC();
				alg.setMeta(x, y);
				alg.setEstado(Estado.WORKING);
				estado = Estado.WORKING;
				break;
				
			case CALCULATE:
				if (alg.calcularCamino() == null) estado = Estado.ERROR;
				else estado = Estado.STOP;
				
			default: break;
		}
		
		return estado;
	}
}
