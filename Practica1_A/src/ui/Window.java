package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import logica.Tablero;
import logica.TipoNodo;
import logica.Transfer;

public class Window extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tablero tab;
	private JPanel panel_tab;
	private JPanel panel_options;
	private JPanel panel_filas;
	private JPanel panel_columnas;
	//private JPanel panel_exit;
	private JFormattedTextField filas;
	private JFormattedTextField columnas;
	private TableroDeBotones[] botonera;
	private JButton closeButton;
	private JButton resetButton;
	private JButton finObstButton;
	private Controlador controlador;
	private Estado estado;
	
	public Window(Controlador contr) {
		this.controlador = contr;
		this.tab = controlador.getTablero();
		this.estado = Estado.START;
		crearBotones();
		initializeUI();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		this.setResizable(true);
		this.setTitle("A*");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private void initializeUI() {
		this.panel_tab = new JPanel();
		this.panel_options = new JPanel();
		this.panel_filas = new JPanel();
		this.panel_columnas = new JPanel();
	
		this.panel_options.setLayout(new BoxLayout(panel_options, BoxLayout.Y_AXIS));
		this.panel_columnas.setAlignmentY(0f);
		
		this.inicializarJTextFields();
		this.resetButton = new JButton();
		this.resetButton.setText("Reiniciar");
		this.resetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Transfer trans = new Transfer(Integer.parseInt(filas.getText()),
						Integer.parseInt(columnas.getText()), null);
				estado = (Estado) controlador.accion(Acciones.RESET, trans);
				reset();
			}
		});
		this.panel_filas.add(new JLabel("Filas"));
		this.panel_filas.add(filas);
		this.panel_columnas.add(new JLabel("Columnas"));
		this.panel_columnas.add(columnas);
		this.panel_options.add(panel_filas);
		this.panel_options.add(panel_columnas);
		this.panel_options.add(resetButton);
		
		this.finObstButton = new JButton();
		this.finObstButton.setText("Fin Obstaculos");
		this.finObstButton.setEnabled(false);
		this.finObstButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Transfer datos = new Transfer(-1, -1, null);
				estado = (Estado) controlador.accion(Acciones.PUTOBSTACLES, datos);
				finObstButton.setEnabled(false);
			}
		});
		this.panel_options.add(finObstButton);
		this.panel_tab.add(ObtenerTableroBotones());
		
		this.closeButton = new JButton();
		this.closeButton.setText("Cerrar");
		this.closeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		this.panel_options.add(closeButton);
		this.panel_tab.add(ObtenerTableroBotones());
		
		add(panel_tab, BorderLayout.WEST);
		add(panel_options, BorderLayout.EAST);
	}
	
	private void inicializarJTextFields() {
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(50);
		formatter.setAllowsInvalid(false);
		
		this.filas = new JFormattedTextField(formatter);
		this.columnas = new JFormattedTextField(formatter);
		
		this.filas.setText("10");
		this.columnas.setText("10");
	}
	
	private void crearBotones() {
		int dimension = this.tab.getFilas() * this.tab.getColumnas();
		this.botonera = new TableroDeBotones[dimension];
		int n = 0;
		
		while (n < dimension) {
			for (int f = 0; f < this.tab.getFilas(); f++) {
				for(int c = 0; c < this.tab.getColumnas(); c++) {
					this.botonera[n] = new TableroDeBotones(f, c);
					this.botonera[n].addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							if (!estado.equals(Estado.WORKING)) {
								boolean parar = false;
								int n = 0;
								while (n < botonera.length && !parar) {
									if (e.getSource() == botonera[n]) {
										int fila = botonera[n].getFila();
										int columna = botonera[n].getColumna();
										accionesBotonera(fila, columna);
										parar = true;
									}
									else n++;
								}
							}
						}
					});
					this.botonera[n].updateButtons(this.tab.getNodo(f, c).getTipoNodo());
					n++;
				}
			}
		}
	}
	
	private JPanel ObtenerTableroBotones() {
		int dimension = this.tab.getFilas() * this.tab.getColumnas();
		JPanel aux = new JPanel();
		aux.setLayout(new GridLayout(this.tab.getFilas(), this.tab.getColumnas()));
		for (int n = 0; n < dimension; n++) {
			JButton botonAux = this.botonera[n].getBoton();
			aux.add(botonAux);
		}
		return aux;
	}
	
	private void actualizarPanel(Object datos) { //OPTIMIZAR
		Transfer coordenada = null;
		
		if (datos != null) {
			coordenada = (Transfer) datos;
			int filas = coordenada.getF();
			int columnas = coordenada.getC();
			int n = 0;
			
			if (filas == -1 || columnas == -1) {
				JOptionPane.showMessageDialog(null, "No hay camino");
			}
			else {
				while (n < this.tab.getFilas() * this.tab.getColumnas()) {
					for (int f = 0; f < this.tab.getFilas(); f++) {
						for(int c = 0; c < this.tab.getColumnas(); c++) {
							botonera[n].updateButtons(tab.getNodo(f, c).getTipoNodo());
							n++;
						}
					}
				}
			}
	    	
		}
		
		else {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						//ArrayList<Nodo> tmp = controlador.getListaNodosModificados();
						int n = 0;
						while (n < tab.getFilas() * tab.getColumnas()) {
							for (int f = 0; f < tab.getFilas(); f++) {
								for(int c = 0; c < tab.getColumnas(); c++) {
									if (!tab.getNodo(f, c).getTipoNodo().equals(TipoNodo.VACIO)) {
										Thread.sleep(100);
										botonera[n].updateButtons(tab.getNodo(f, c).getTipoNodo());
										
									}
									n++;
								}
							}
						}
			    		/*for (int n = 0; n < tmp.size(); n++) {
			    			Integer f = tmp.get(n).getF();
			    			Integer c = tmp.get(n).getC();
			    			String aux = f.toString() + c.toString();
			    			Thread.sleep(100);
			    			botonera[Integer.parseInt(aux)].updateButtons(tab.getNodo(f, c).getTipoNodo());
			    		}*/
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		}
		
		
		if (estado.equals(Estado.OBSTACLES)) finObstButton.setEnabled(true);
		else finObstButton.setEnabled(false);
	}
	
	private void reset() {
		this.tab = controlador.getTablero();
		this.finObstButton.setEnabled(false);
		crearBotones();
		
		panel_tab.removeAll();
		this.panel_tab.add(ObtenerTableroBotones());
		
		panel_tab.revalidate();
		panel_tab.repaint();
		getContentPane().revalidate();
		getContentPane().repaint();
		pack();
	}

	private void accionesBotonera(int f, int c) {
		Transfer datos = new Transfer(f, c, null);
		switch(estado) {
			case START:
				this.estado = (Estado) controlador.accion(Acciones.START, datos);
				actualizarPanel(datos);
				break;
			case OBSTACLES:
				this.estado = (Estado) controlador.accion(Acciones.PUTOBSTACLES, datos);
				actualizarPanel(datos);
				break;
			case END:
				this.estado = (Estado) controlador.accion(Acciones.PUTEND, datos);
				actualizarPanel(datos);
				this.estado = (Estado) controlador.accion(Acciones.CALCULATE, null);
				if (this.estado.equals(Estado.ERROR)) {
					datos.setC(-1);
					datos.setF(-1);
					actualizarPanel(datos);
				} else {
					actualizarPanel(null);
				}
				break;
			default: break;
		}
	}
}
