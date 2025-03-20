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
import unai.lista_actividades.PeriodoDTO;
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

	}

	public void initView() {
		cargarListaPeriodosInscripcion();
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
			int aforo_maximo = Integer.parseInt(view.getAforoMaximoField().getText());
			double coste_socio = Double.parseDouble(view.getCosteSocioField().getText());
			double coste_no_socio = Double.parseDouble(view.getCosteNoSocioField().getText());
			String fecha_inicio = Util.dateToIsoString(view.getFechaInicioChooser().getDate());
			String fecha_fin = Util.dateToIsoString(view.getFechaFinChooser().getDate());
			String dias = view.getDiasField().getText();
			String hora_inicio = view.getHoraInicioField().getText();
			String hora_fin = view.getHoraFinField().getText();

			// Obtener id periodo
			Object selectedItem = view.getListaPeriodosInscripcion().getSelectedItem();
			int periodo_inscripcion_id = 0;
			if (selectedItem instanceof Object[]) {
			    Object[] selectedPeriodo = (Object[]) selectedItem;
			    periodo_inscripcion_id = (int) selectedPeriodo[0];  // Aseguramos que sea el ID del periodo
			} else if (selectedItem instanceof Integer) {
			    periodo_inscripcion_id = (int) selectedItem;  // Si es un Integer directamente, lo usamos
			}

			// Obtener id instalacion
			Object selectedItem2 = view.getListaInstalaciones().getSelectedItem();
			int instalacion_id = 0;
			if (selectedItem2 instanceof Object[]) {
			    Object[] selectedInstal = (Object[]) selectedItem2;
			    instalacion_id = (int) selectedInstal[0];  // Aseguramos que sea el ID de la instalación
			} else if (selectedItem2 instanceof Integer) {
			    instalacion_id = (int) selectedItem2;  // Si es un Integer directamente, lo usamos
			}
			if (nombre.isEmpty() || descripcion.isEmpty() || fecha_inicio == null || fecha_fin == null || dias.isEmpty()
					|| hora_inicio.isEmpty() || hora_fin.isEmpty()) {
				throw new ApplicationException("Todos los campos deben estar completos.");
			}

			model.guardarActividad(nombre, descripcion, instalacion_id, aforo_maximo, coste_socio, coste_no_socio,
					fecha_inicio, fecha_fin, dias, hora_inicio, hora_fin, periodo_inscripcion_id);
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

	 private void cargarListaPeriodosInscripcion() {
	    	List<PeriodoDTO> periodos = model.getPeriodosInscripcion();
	    	view.getListaPeriodosInscripcion().removeAllItems();
	    	
	    	// Agregar un valor por defecto que indique que no hay periodo seleccionado
	        view.getListaPeriodosInscripcion().addItem(""); // Esto agregará un elemento vacío al combo box
	    	for (PeriodoDTO periodo : periodos) {
	    		view.getListaPeriodosInscripcion().addItem(periodo.getNombre());
	    }
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