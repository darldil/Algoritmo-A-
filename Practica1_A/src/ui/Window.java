package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import logica.Ruta;
import logica.Tablero;
import logica.Transfer;

public class Window extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tablero tab;
	private JPanel panel_tab;
	private JPanel panel_options;
	private JPanel panel_dimensiones;
	private JPanel panel_filas;
	private JPanel panel_columnas;
	private JPanel panel_botones;
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
		this.setResizable(false);
		this.setTitle("A*");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private void initializeUI() {
		this.panel_tab = new JPanel();
		this.panel_options = new JPanel();
		
		this.panel_options.setLayout(new BorderLayout());

		this.inicializarJTextFields();
		this.inicializarJPanels();
		
		this.panel_botones.add(closeButton, BorderLayout.SOUTH);
		this.panel_options.add(panel_botones, BorderLayout.SOUTH);
		this.panel_tab.add(ObtenerTableroBotones());
		
		add(panel_tab, BorderLayout.WEST);
		add(panel_options, BorderLayout.EAST);
	}
	
	private void inicializarJPanels() {
		this.panel_dimensiones = new JPanel();
		this.panel_filas = new JPanel();
		this.panel_columnas = new JPanel();
		this.panel_botones = new JPanel();
		
		this.panel_botones.setLayout(new BorderLayout());
		
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
		
		panel_dimensiones.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), 
				"Dimensiones"));
		panel_dimensiones.setLayout(new BoxLayout(panel_dimensiones, BoxLayout.Y_AXIS));
		panel_filas.add(new JLabel("          Filas"));
		panel_filas.add(filas);
		panel_columnas.add(new JLabel("Columnas"));
		panel_columnas.add(columnas);
		panel_dimensiones.add(panel_filas);
		panel_dimensiones.add(panel_columnas);
		panel_dimensiones.add(resetButton);
		this.panel_options.add(panel_dimensiones, BorderLayout.NORTH);
		
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
		this.panel_botones.add(finObstButton, BorderLayout.NORTH);
		this.panel_tab.add(ObtenerTableroBotones());
		
		this.closeButton = new JButton();
		this.closeButton.setText("Cerrar");
		this.closeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
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
		
		this.filas.setPreferredSize(new Dimension(100, 24));
		this.columnas.setPreferredSize(new Dimension(100, 24));
		
		this.filas.setHorizontalAlignment(SwingConstants.RIGHT);
		this.columnas.setHorizontalAlignment(SwingConstants.RIGHT);
		
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
	
	private void actualizarPanel(Object datos) {
		
		if (datos != null) {
			int n = 0;
			while (n < this.tab.getFilas() * this.tab.getColumnas()) {
				for (int f = 0; f < this.tab.getFilas(); f++) {
					for(int c = 0; c < this.tab.getColumnas(); c++) {
						botonera[n].updateButtons(tab.getNodo(f, c).getTipoNodo());
						n++;
					}
				}
			}
		}
		
		else if (!this.estado.equals(Estado.ERROR)){
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Ruta tmp = (Ruta) controlador.accion(Acciones.GETRUTE, null);
						int z = 0;
						while (z < tmp.getSize() - 1) {
							for (int n = 0; n < tab.getFilas() * tab.getColumnas(); n++) {
								if (botonera[n].getFila() == tmp.getF(z) && botonera[n].getColumna() == tmp.getC(z)) {
									Thread.sleep(100);
									botonera[n].updateButtons(tab.getNodo(tmp.getF(z), tmp.getC(z)).getTipoNodo());	
									z++;
								}
							}
						}
			    		
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		
		if (this.estado.equals(Estado.ERROR)) JOptionPane.showMessageDialog(null, "No hay camino");
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
					actualizarPanel(null);
				} else {
					actualizarPanel(null);
				}
				break;
			default: break;
		}
	}
}
