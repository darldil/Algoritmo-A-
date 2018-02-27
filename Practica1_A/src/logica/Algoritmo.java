package logica;

import java.util.ArrayList;
import java.util.Collections;

import ui.Estado;

public class Algoritmo {

	private Tablero tab;
	private ArrayList<Nodo> listaCerrada;
	private ArrayList<Nodo> listaAbierta;
	private Ruta rutaMasCorta;
	private Estado state;
	
	public Algoritmo(Tablero tab) {
		this.tab = tab;
		this.listaAbierta = new ArrayList<Nodo>();
		this.listaCerrada = new ArrayList<Nodo>();
		this.rutaMasCorta = new Ruta();
		this.state = Estado.START;
	}
	
	public Ruta calcularCamino() {
		
		listaCerrada.clear();
		listaAbierta.clear();
		
		this.tab.getNodoInicio().setDistanciaDesdeInicio(0);
		listaAbierta.add(this.tab.getNodoInicio());
		
		while (listaAbierta.size() != 0) {
			Nodo nodoActual = listaAbierta.get(0);
			boolean vecinoMejor = false;
			float distanciaFilas, distanciaColumnas;
			
			if(nodoActual.getF() == tab.getNodoMeta().getF() && nodoActual.getC() == tab.getNodoMeta().getC())
				return recalcularRuta(nodoActual);
			else {
				nodoActual.setCamino(true);
				listaCerrada.add(nodoActual);
				listaAbierta.remove(nodoActual);
				
				for (int i = 0; i < nodoActual.getNodosVecinos().size(); i++) {
					Nodo nodoVecino = nodoActual.getNodosVecinos().get(i);
					
					if(!listaCerrada.contains(nodoVecino) || !nodoVecino.getTipoNodo().equals(TipoNodo.OBSTACULO)) {
						float distancia1 = tab.calcularDistancias(nodoActual, nodoVecino);
						
						if (!listaAbierta.contains(nodoVecino)) {
							listaAbierta.add(nodoVecino);
							Collections.sort(listaAbierta);
							vecinoMejor = true;
						} else if (distancia1 < nodoActual.getDistanciaDesdeInicio())
							vecinoMejor = true;
						if (vecinoMejor) {
							nodoVecino.setNodoAnterior(nodoActual);
							nodoVecino.setDistanciaDesdeInicio(distancia1);
							distanciaFilas = tab.getNodoMeta().getF() - nodoVecino.getF();
							distanciaColumnas = tab.getNodoMeta().getC() - nodoVecino.getC();
							float aux = (float) Math.sqrt((distanciaFilas * distanciaFilas) + (distanciaColumnas * distanciaColumnas));
							nodoVecino.setDistanciaHastaMeta(aux);
						}
					}
				}
			}
		}
	
		return null;
	}
	
	private Ruta recalcularRuta(Nodo nodo) {
		Ruta ruta = new Ruta();
		
		while (nodo.getNodoAnterior() != null) {
			ruta.anhadirCoordenadas(true, nodo);
			nodo = nodo.getNodoAnterior();
		}
		this.rutaMasCorta = ruta;
		return ruta;
	}
	
	public void setInicio(int f, int c) {
		this.tab.establecerInicio(f, c);
	}
	
	public void setMeta(int f, int c) {
		this.tab.establecerMeta(f, c);
	}
	
	public void setObstaculo(int f, int c) {
		if (this.tab.getNodo(f, c).getTipoNodo() == TipoNodo.OBSTACULO)
			this.tab.setObstaculo(f, c, false);
		else if (this.tab.getNodo(f, c).getTipoNodo() == TipoNodo.VACIO)
			this.tab.setObstaculo(f, c, true);
	}
	
	public Tablero getTab() {
		return this.tab;
	}
	
	public Ruta getRutaMasCorta() {
		return this.rutaMasCorta;
	}
	
	public void setEstado(Estado state) {
		this.state = state;
	}
	
	public Estado getEstado() {
		return this.state;
	}
	
}
