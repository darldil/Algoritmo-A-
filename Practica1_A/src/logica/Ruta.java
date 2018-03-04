package logica;

import java.util.ArrayList;

public class Ruta {
	private ArrayList<Nodo> rutaHaciaMeta;
	
	public Ruta() {
		this.rutaHaciaMeta = new ArrayList<Nodo>();
	}
	
	public int getSize() {
		return rutaHaciaMeta.size();
	}
	
	public Nodo getCoordenadasRuta(int indice) {
		return rutaHaciaMeta.get(indice);
	}
	
	public int getF(int indice) {
		return rutaHaciaMeta.get(indice).getF();
	}
	
	public int getC(int indice) {
		return rutaHaciaMeta.get(indice).getC();
	}
	
	public void anhadirCoordenadas(boolean inicio, Nodo nodo) {
		if (inicio == true)
			this.rutaHaciaMeta.add(0, nodo);
		else 
			this.rutaHaciaMeta.add(nodo);
	}
}
