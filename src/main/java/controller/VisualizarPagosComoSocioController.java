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
		
		this.cargarMesesConPagosEnComboBox(1);
		this.view.getBtnBuscar().addActionListener(e -> {
		    cargarPagosPorMesSeleccionado(idSocio);
		    calcularTotalPagos();
		});

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
	
	public void calcularTotalPagos() {
	    DefaultTableModel modelo = (DefaultTableModel) view.getTablaPagos().getModel();
	    double total = 0.0;

	    // Recorremos todas las filas y sumamos los valores de la quinta columna (índice 4)
	    for (int i = 0; i < modelo.getRowCount(); i++) {
	        Object valor = modelo.getValueAt(i, 4);
	        if (valor != null) {
	            try {
	                total += Double.parseDouble(valor.toString());
	            } catch (NumberFormatException e) {
	                System.err.println("Error al convertir el valor de la fila " + i + " a número: " + valor);
	            }
	        }
	    }

	    // Mostramos el total en la etiqueta correspondiente de la vista
	    view.getLblCosteMensual().setText("Total Mensual: " + String.format("%.2f", total) + " €");
	}



}
