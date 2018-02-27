package logica;

import java.util.ArrayList;

public class Nodo implements Comparable<Nodo>{
	private Tablero tab;
	private Nodo norte;
	private Nodo sur;
	private Nodo este;
	private Nodo oeste;
	private Nodo noreste;
	private Nodo noroeste;
	private Nodo sureste;
	private Nodo suroeste;
	private Nodo nodoAnterior;
	private ArrayList<Nodo> nodosVecinos;
	private TipoNodo tipo;
	private int f;
	private int c;
	private float distanciaDesdeInicio;
	private float distanciaHastaMeta;
	
	public Nodo(int x, int y) {
		this.nodosVecinos = new ArrayList<Nodo>();
		this.setF(x);
		this.setC(y);
		this.tipo = TipoNodo.VACIO;
		this.setDistanciaDesdeInicio(Integer.MAX_VALUE);
		this.setDistanciaHastaMeta(0);
	}
	
	public Nodo(int x, int y, TipoNodo tipo) {
		this.nodosVecinos = new ArrayList<Nodo>();
		this.f = x;
		this.c = y;
		this.tipo = tipo;
		this.setDistanciaDesdeInicio(0);
		this.setDistanciaHastaMeta(0);
	}
	
	public Tablero getTab() {
		return tab;
	}
	
	public void setTab(Tablero tab) {
		this.tab = tab;
	}
	
	public Nodo getNorte() {
		return norte;
	}
	
	public void setNorte(Nodo norte) {
		if (nodosVecinos.contains(norte))
			nodosVecinos.remove(norte);
		
		nodosVecinos.add(norte);
		this.norte = norte;
	}

	public Nodo getSur() {
		return sur;
	}

	public void setSur(Nodo sur) {
		if (nodosVecinos.contains(sur))
			nodosVecinos.remove(sur);
		
		nodosVecinos.add(sur);
		this.sur = sur;
	}

	public Nodo getEste() {
		return este;
	}

	public void setEste(Nodo este) {
		if (nodosVecinos.contains(este))
			nodosVecinos.remove(este);
		
		nodosVecinos.add(este);
		this.este = este;
	}

	public Nodo getOeste() {
		return oeste;
	}

	public void setOeste(Nodo oeste) {
		if (nodosVecinos.contains(oeste))
			nodosVecinos.remove(oeste);
		
		nodosVecinos.add(oeste);
		this.oeste = oeste;
	}

	public Nodo getNoreste() {
		return noreste;
	}

	public void setNoreste(Nodo noreste) {
		if (nodosVecinos.contains(noreste))
			nodosVecinos.remove(noreste);
		
		nodosVecinos.add(noreste);
		this.noreste = noreste;
	}

	public Nodo getNoroeste() {
		return noroeste;
	}

	public void setNoroeste(Nodo noroeste) {
		if (nodosVecinos.contains(noroeste))
			nodosVecinos.remove(noroeste);
		
		nodosVecinos.add(noroeste);
		this.noroeste = noroeste;
	}

	public Nodo getSureste() {
		return sureste;
	}

	public void setSureste(Nodo sureste) {
		if (nodosVecinos.contains(sureste))
			nodosVecinos.remove(sureste);
		
		nodosVecinos.add(sureste);
		this.sureste = sureste;
	}

	public Nodo getSuroeste() {
		return suroeste;
	}

	public void setSuroeste(Nodo suroeste) {
		if (nodosVecinos.contains(suroeste))
			nodosVecinos.remove(suroeste);
		
		nodosVecinos.add(suroeste);
		this.suroeste = suroeste;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}
	
	public float getDistanciaDesdeInicio() {
		return distanciaDesdeInicio;
	}

	public void setDistanciaDesdeInicio(float distanciaDesdeInicio) {
		this.distanciaDesdeInicio = distanciaDesdeInicio;
	}

	public float getDistanciaHastaMeta() {
		return distanciaHastaMeta;
	}

	public void setDistanciaHastaMeta(float distanciaHastaMeta) {
		this.distanciaHastaMeta = distanciaHastaMeta;
	}

	public Nodo getNodoAnterior() {
		return this.nodoAnterior;
	}
	
	public void setNodoAnterior(Nodo nodo) {
		this.nodoAnterior = nodo;
	}

	public void setCamino(boolean camino) {
		if (camino && this.tipo != TipoNodo.INICIO) this.tipo = TipoNodo.CAMINO;
	}
	
	public TipoNodo getTipoNodo() {
		return this.tipo;
	}

	public void setInicio(boolean inicio) {
		if (!inicio) this.tipo = TipoNodo.VACIO;
		else this.tipo = TipoNodo.INICIO;
	}

	public void setMeta(boolean meta) {
		if (!meta) this.tipo = TipoNodo.VACIO;
		else this.tipo = TipoNodo.META;
	}
	
	public void setObstaculo(boolean obstaculo) {
		if (!obstaculo) this.tipo = TipoNodo.VACIO;
		else this.tipo = TipoNodo.OBSTACULO;
	}

	public ArrayList<Nodo> getNodosVecinos() {
		return nodosVecinos;
	}

	@Override
	public int compareTo(Nodo nodo) {
		/*float distanciaHastaMeta = this.distanciaDesdeInicio + this.distanciaHastaMeta;
		float distanciaHastaMetaAux = nodo.getDistanciaDesdeInicio() + nodo.getDistanciaHastaMeta();
		
		if (distanciaHastaMeta < distanciaHastaMetaAux) return 1;
		else if (distanciaHastaMeta > distanciaHastaMetaAux) return -1;*/
		
		if (this.distanciaHastaMeta < nodo.getDistanciaHastaMeta()) return -1;
		else if (this.distanciaHastaMeta > nodo.getDistanciaHastaMeta()) return 1;
		
		return 0;
	}
	
}
