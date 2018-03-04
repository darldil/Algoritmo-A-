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
		int f = 0, c = 0;
		Estado estado = null;
		switch(evento) {
			case RESET: 
				estado = this.reset(datos);
				break;
			case START:
				f = ((Transfer) datos).getF();
				c = ((Transfer) datos).getC();
				alg.setInicio(f, c);
				alg.setEstado(Estado.OBSTACLES);
				estado = Estado.OBSTACLES;
				break;
				
			case PUTOBSTACLES:
				f = ((Transfer) datos).getF();
				c = ((Transfer) datos).getC();
				if (f == -1 || c == -1) {
					alg.setEstado(Estado.END);
					estado = Estado.END;
				}
				else {
					alg.setObstaculo(f, c);
					estado = Estado.OBSTACLES;
				}
				break;
				
			case PUTEND:
				f = ((Transfer) datos).getF();
				c = ((Transfer) datos).getC();
				alg.setMeta(f, c);
				alg.setEstado(Estado.WORKING);
				estado = Estado.WORKING;
				break;
				
			case CALCULATE:
				if (alg.calcularCamino() == null) estado = Estado.ERROR;
				else {
					estado = Estado.STOP;
				}
				break;
				
			case GETRUTE:
				return alg.getRutaMasCorta();
				
			default: break;
		}
		
		return estado;
	}
}
