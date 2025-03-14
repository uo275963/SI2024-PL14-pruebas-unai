package controller;

import javax.swing.table.DefaultTableModel;

import model.VisualizarPagosComoSocioModel;
import view.VisualizarPagosComoSocioView;

public class VisualizarPagosComoSocioController {

	private VisualizarPagosComoSocioModel model;
	private VisualizarPagosComoSocioView view;
	public int idSocio = 1;

	public VisualizarPagosComoSocioController(VisualizarPagosComoSocioModel m, VisualizarPagosComoSocioView v) {

		this.model = m;
		this.view = v;
		this.initview();
	}

	private void initview() {

		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("Tipo");
		modelo.addColumn("Instalación");
		modelo.addColumn("Fecha de reserva");
		modelo.addColumn("Fecha de pago");
		modelo.addColumn("Coste");
		modelo.addColumn("Estado del pago");
		modelo.addColumn("Forma de pago");

		// Asignar el modelo vacío a la tabla en la vista
		this.view.setTablaPagosModel(modelo);

		this.view.getFrame().setVisible(true);

	}

	public void mostrarNombreSocio(int idSocio) {
		String nombreSocio = model.getNombreSocioPorId(idSocio);
		if (nombreSocio != null) {
			this.view.getLblNombreSocio().setText(nombreSocio);

		}
	}

	public void initController() {
		mostrarNombreSocio(idSocio);

	}

}
