package controller;

import java.util.List;

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
		
		this.view.getBtnBuscar().addActionListener(e -> mostrarNombreSocio(1));
		this.cargarMesesConPagosEnComboBox(1);
		this.view.getBtnBuscar().addActionListener(e -> cargarPagosPorMesSeleccionado(idSocio));

	}
	
	public void cargarMesesConPagosEnComboBox(int usuarioId) {
	    List<Object[]> mesesConPagos = model.getMesesConPagos(usuarioId);
	    view.getCBMeses().removeAllItems();

	    if (mesesConPagos.isEmpty()) {
	        view.getCBMeses().addItem("No hay meses disponibles");
	    } else {
	        for (Object[] mes : mesesConPagos) {
	            String mesFormateado = mes[0].toString(); // Formato "YYYY-MM"
	            view.getCBMeses().addItem(mesFormateado);
	        }
	    }
	}
	
	public void cargarPagosPorMesSeleccionado(int usuarioId) {
	    String mesSeleccionado = (String) view.getCBMeses().getSelectedItem();
	    if (mesSeleccionado != null && !mesSeleccionado.equals("No hay meses disponibles")) {
	        List<Object[]> pagos = model.getPagosPorMesAno(usuarioId, mesSeleccionado);
	        DefaultTableModel modelo = (DefaultTableModel) view.getTablaPagos().getModel();
	        modelo.setRowCount(0);

	        for (Object[] pago : pagos) {
	            modelo.addRow(pago);
	        }
	    }
	}



}
