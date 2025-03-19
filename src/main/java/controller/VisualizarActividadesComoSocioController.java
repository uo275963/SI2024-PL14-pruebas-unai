package controller;

import javax.swing.table.DefaultTableModel;

import model.VisualizarActividadesComoSocioModel;
import view.VisualizarActividadesComoSocioView;

public class VisualizarActividadesComoSocioController {
	
	private VisualizarActividadesComoSocioModel model;
	private VisualizarActividadesComoSocioView view;
	
	public VisualizarActividadesComoSocioController(VisualizarActividadesComoSocioModel m,VisualizarActividadesComoSocioView v) {
		this.model = m;
		this.view = v;
		
	}
	
	private void initview() {
		// Crear un modelo de tabla vacío con las columnas "Horario" y "Estado"
		

		this.view.getFrame().setVisible(true);

	}

	public void initController() {
		
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("Actividad");
		modelo.addColumn("Instalación");
		modelo.addColumn("Fecha de actividad");
		modelo.addColumn("Fecha de inscripción");
		modelo.addColumn("Coste");
		this.view.setTablaActividadesModel(modelo);
		
		
		
		this.initview();
		
	}
	
	

}
