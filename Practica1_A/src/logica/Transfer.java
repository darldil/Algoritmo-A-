package logica;

public class Transfer {

	private int f;
	private int c;
	private Nodo nodo;
	
	public Transfer(int f, int c, Nodo nodo) {
		this.f = f;
		this.c = c;
		this.nodo = nodo;
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

	public Nodo getNodo() {
		return nodo;
	}

	public void setNodo(Nodo nodo) {
		this.nodo = nodo;
	}
	
	
	
}
