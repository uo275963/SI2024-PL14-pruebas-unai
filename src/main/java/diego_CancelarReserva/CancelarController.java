package diego_CancelarReserva;

import java.awt.event.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class CancelarController {
	private CancelarView view;
	private CancelarModel model;

	public CancelarController(CancelarView model, CancelarModel view) {
		this.model = model;
		this.view = view;
		this.initView();
	}
	
	public void initView() {
		cargarUsuarios();
		view.getFrame().setVisible(true);;
	}

	public void initController() {
		view.getBtnBuscar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarReservas();
			}
		});

		view.getBtnCancelar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelarReserva();
			}
		});
	}

	// Cargar usuarios en el JComboBox
	private void cargarUsuarios() {
		try {
			List<String> usuarios = model.obtenerUsuarios();
			for (String usuario : usuarios) {
				view.getComboUsuarios().addItem(usuario);
			}
		} catch (Exception e) {
			view.mostrarError("Error al cargar usuarios: " + e.getMessage());
		}
	}

	// Buscar reservas del usuario seleccionado y mostrarlas en la tabla
	private void cargarReservas() {
		String usuario = (String) view.getComboUsuarios().getSelectedItem();
		if (usuario == null || usuario.isEmpty()) {
			view.mostrarError("Seleccione un usuario válido.");
			return;
		}

		try {
			List<String[]> reservas = model.obtenerReservas(usuario);

			// Preparar tabla
			String[] columnas = {"ID", "Actividad", "Fecha", "Hora"};
			DefaultTableModel tableModel = new DefaultTableModel(columnas, 0);

			for (String[] reserva : reservas) {
				tableModel.addRow(reserva);
			}

			view.getTablaReservas().setModel(tableModel);

		} catch (Exception e) {
			view.mostrarError("Error al cargar reservas: " + e.getMessage());
		}
	}

	// Cancelar reserva seleccionada
	private void cancelarReserva() {
		int filaSeleccionada = view.getTablaReservas().getSelectedRow();

		if (filaSeleccionada == -1) {
			view.mostrarError("Seleccione una reserva para cancelar.");
			return;
		}

		String idReserva = view.getTablaReservas().getValueAt(filaSeleccionada, 0).toString();

		try {
			model.cancelarReserva(idReserva);
			view.mostrarMensaje("Reserva cancelada correctamente.");
			cargarReservas(); // Refrescar tabla
		} catch (Exception e) {
			view.mostrarError("Error al cancelar reserva: " + e.getMessage());
		}
	}
}
