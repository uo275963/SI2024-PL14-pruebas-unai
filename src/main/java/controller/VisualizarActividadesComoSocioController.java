package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import giis.demo.util.SwingUtil;
import model.VisualizarActividadesComoSocioModel;
import view.VisualizarActividadesComoSocioView;

public class VisualizarActividadesComoSocioController {

	private VisualizarActividadesComoSocioModel model;
	private VisualizarActividadesComoSocioView view;
	private int idSocio = 1;

	public VisualizarActividadesComoSocioController(VisualizarActividadesComoSocioModel m,
			VisualizarActividadesComoSocioView v) {
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

		actualizarNombreSocio(idSocio);
		llenarComboBoxInstalaciones();

		this.initview();

		this.view.getBtnFiltrar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> {
			// Llamar al método para actualizar las actividades del usuario con el idSocio
			actualizarActividadesUsuario(idSocio);
		}));

		this.view.getBtnCerrar().addActionListener(e -> this.view.getFrame().setVisible(false));

	}

	private boolean esFechaValida(String fecha) {
		if (fecha == null || fecha.isEmpty())
			return true; // Permitir campos vacíos
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			sdf.parse(fecha);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	private void actualizarNombreSocio(int idUsuario) {
		// Usamos el método del modelo para obtener el nombre del socio
		String nombreSocio = this.model.getNombreUsuarioPorId(idUsuario);

		// Asumimos que tienes una etiqueta en la vista llamada 'lblSocioNombre'
		if (nombreSocio != null) {
			this.view.getLblNombreSocio().setText("Socio: " + nombreSocio);
		} else {
			this.view.getLblNombreSocio().setText("Socio: No encontrado");
		}
	}

	private void llenarComboBoxInstalaciones() {
		// Obtener los nombres de las instalaciones desde el modelo
		List<Object[]> instalaciones = this.model.getNombreInstalaciones();

		// Limpiar la ComboBox antes de llenarla
		this.view.getCbInstalaciones().removeAllItems();

		// Llenar la ComboBox con los nombres de las instalaciones
		if (instalaciones != null && !instalaciones.isEmpty()) {
			for (Object[] instalacion : instalaciones) {
				// Cada instalacion[0] contiene el nombre de la instalación
				String nombreInstalacion = (String) instalacion[0];
				this.view.getCbInstalaciones().addItem(nombreInstalacion);
			}
		} else {
			this.view.getCbInstalaciones().addItem("No hay instalaciones disponibles");
		}
	}

	public void actualizarActividadesUsuario(int usuarioId) {
		// Obtener la instalación seleccionada desde el JComboBox
		String instalacionSeleccionada = (String) this.view.getCbInstalaciones().getSelectedItem();
		String fechaInicio = this.view.getTFFechaInicio().getText();
		String fechaFin = this.view.getTFFechaFin().getText();

		// Validar formato de fechas
		if (!esFechaValida(fechaInicio) || !esFechaValida(fechaFin)) {
			JOptionPane.showMessageDialog(null, "Las fechas deben tener el formato YYYY-MM-DD.", "Error de formato",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Obtener las actividades del usuario filtradas desde el modelo
		List<Object[]> actividades = this.model.getActividadesPorUsuarioId(usuarioId, instalacionSeleccionada,
				fechaInicio, fechaFin);

		// Obtener el modelo de la tabla
		DefaultTableModel modelo = (DefaultTableModel) this.view.getTablaActividades().getModel();

		// Limpiar la tabla antes de agregar nuevas filas
		modelo.setRowCount(0);

		// Verificamos si hay actividades
		if (actividades != null && !actividades.isEmpty()) {
			// Agregar las actividades a la tabla
			for (Object[] actividad : actividades) {
				modelo.addRow(actividad);
			}
		} else {
			// Si no hay actividades, mostrar un mensaje
			JOptionPane.showMessageDialog(null,
					"No hay actividades registradas para este usuario con los filtros aplicados.", "Sin actividades",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
