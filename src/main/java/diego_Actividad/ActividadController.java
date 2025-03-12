package diego_Actividad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import diego_periodoInscripcion.PeriodoEntity;
import giis.demo.util.ApplicationException;
import giis.demo.util.SwingUtil;
import giis.demo.util.Util;
import unai.ver_reservas.InstalacionDTO;

public class ActividadController {
	private ActividadModel model;
	private ActividadView view;
	private String lastSelectedKey = ""; // Guarda la última clave seleccionada

	public ActividadController(ActividadModel model, ActividadView view) {
		this.model = model;
		this.view = view;
		this.initView();
	}

	public void initController() {
		view.getBtnGuardar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> guardarActividad()));
		view.getBtnMostrar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> getListaActividades()));

		view.getTablaActividades().addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				SwingUtil.exceptionWrapper(() -> updateDetail());
			}
		});

		// Agregar el ActionListener para el ComboBox de períodos de inscripción
		view.getListaPeriodosInscripcion().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtil.exceptionWrapper(() -> actualizarPeriodoInscripcion());
			}
		});
	}

	public void initView() {
		cargarInstalacionesEnComboBox();
		view.getFrame().setVisible(true);
	}

	/**
	 * Guarda una nueva actividad en la base de datos.
	 */
	public void guardarActividad() {
		try {
			String nombre = view.getNombreField().getText();
			String descripcion = view.getDescripcionField().getText();
			int aforoMaximo = Integer.parseInt(view.getAforoMaximoField().getText());
			double costeSocio = Double.parseDouble(view.getCosteSocioField().getText());
			double costeNoSocio = Double.parseDouble(view.getCosteNoSocioField().getText());
			String fechaInicio = Util.dateToIsoString(view.getFechaInicioChooser().getDate());
			String fechaFin = Util.dateToIsoString(view.getFechaFinChooser().getDate());
			String dias = view.getDiasField().getText();
			String horaInicio = view.getHoraInicioField().getText();
			String horaFin = view.getHoraFinField().getText();

			// Obtener id periodo
			Object selectedItem = view.getListaPeriodosInscripcion().getSelectedItem();
			int periodoInscripcionId = 0;
			Object[] selectedPeriodo = (Object[]) selectedItem;
			periodoInscripcionId = (int) selectedPeriodo[0];

			// Obtener id instalacion
			Object selectedItem2 = view.getListaInstalaciones().getSelectedItem();
			int instalacionId = 0;
			Object[] selectedInstal = (Object[]) selectedItem2;
			instalacionId = (int) selectedInstal[0];

			if (nombre.isEmpty() || descripcion.isEmpty() || fechaInicio == null || fechaFin == null || dias.isEmpty()
					|| horaInicio.isEmpty() || horaFin.isEmpty()) {
				throw new ApplicationException("Todos los campos deben estar completos.");
			}

			model.guardarActividad(nombre, descripcion, instalacionId, aforoMaximo, costeSocio, costeNoSocio,
					fechaInicio, fechaFin, dias, horaInicio, horaFin, periodoInscripcionId);
			view.mostrarMensaje("Actividad guardada correctamente.");
			getListaActividades();
		} catch (ApplicationException ex) {
			view.mostrarError(ex.getMessage());
		}
	}

	/**
	 * Obtiene la lista de actividades desde el modelo y la muestra en la vista.
	 */
	public void getListaActividades() {
		List<ActividadDisplayDTO> actividades = model.getListaActividades();
		TableModel tmodel = SwingUtil.getTableModelFromPojos(actividades,
				new String[] { "id", "nombre", "descripcion", "instalacion_id", "aforo_maximo", "coste_socio",
						"coste_no_socio", "fecha_inicio", "fecha_fin", "dias", "hora_inicio", "hora_fin",
						"periodo_inscripcion_id" });
		view.getTablaActividades().setModel(tmodel);
		SwingUtil.autoAdjustColumns(view.getTablaActividades());

		restoreDetail();

		List<Object[]> actividadesList = model.getListaActividadesArray();
		ComboBoxModel<Object> lmodel = SwingUtil.getComboModelFromList(actividadesList);
		view.getListaActividades().setModel(lmodel);
	}

	/**
	 * Restaura la selección de detalles en la tabla.
	 */
	public void restoreDetail() {
		this.lastSelectedKey = SwingUtil.selectAndGetSelectedKey(view.getTablaActividades(), this.lastSelectedKey);
		if ("".equals(this.lastSelectedKey)) {
			view.getDetalleActividad().setModel(new DefaultTableModel());
		} else {
			this.updateDetail();
		}
	}

	/**
	 * Actualiza la vista con los detalles de la actividad seleccionada.
	 */
	public void updateDetail() {
		this.lastSelectedKey = SwingUtil.getSelectedKey(view.getTablaActividades());
		int idActividad = Integer.parseInt(this.lastSelectedKey);

		ActividadEntity actividad = model.getActividad(idActividad);
		TableModel tmodel = SwingUtil.getRecordModelFromPojo(actividad,
				new String[] { "id", "nombre", "descripcion", "instalacion_id", "aforo_maximo", "coste_socio",
						"coste_no_socio", "fecha_inicio", "fecha_fin", "dias", "hora_inicio", "hora_fin",
						"periodo_inscripcion_id" });
		view.getDetalleActividad().setModel(tmodel);
		SwingUtil.autoAdjustColumns(view.getDetalleActividad());
	}

	/**
	 * Actualiza la información del periodo de inscripción basado en la selección
	 * del ComboBox.
	 */
	public void actualizarPeriodoInscripcion() {
		// Obtener el objeto seleccionado en el ComboBox
		Object selectedItem = view.getListaPeriodosInscripcion().getSelectedItem();

		// Verificar que el item seleccionado no sea nulo y sea un array (id, nombre)
		if (selectedItem != null && selectedItem instanceof Object[]) {
			Object[] selectedPeriodo = (Object[]) selectedItem;
			int idPeriodoInscripcion = (int) selectedPeriodo[0]; // El primer elemento es el ID del periodo

			// Usar el ID para obtener el periodo de inscripción completo
			PeriodoEntity periodoInscripcion = model.getPeriodoInscripcion(idPeriodoInscripcion);

		}
	}

	/**
	 * Obtiene la lista de períodos de inscripción y los muestra en el ComboBox
	 */
	public void cargarListaPeriodosInscripcion() {
		List<Object[]> periodosInscripcion = model.getListaPeriodosInscripcionArray(); // Método similar a
																						// getListaPeriodosArray
		ComboBoxModel<Object> lmodel = SwingUtil.getComboModelFromList(periodosInscripcion);
		view.getListaPeriodosInscripcion().setModel(lmodel);
	}

	/**
	 * Carga las instalaciones en el ComboBox
	 */
	private void cargarInstalacionesEnComboBox() {
		List<InstalacionDTO> instalaciones = model.getInstalaciones();
		view.getListaInstalaciones().removeAllItems();
		view.getListaInstalaciones().addItem(""); // Opción vacía
		for (InstalacionDTO instalacion : instalaciones) {
			view.getListaInstalaciones().addItem(instalacion.getNombre());
		}
	}

}
