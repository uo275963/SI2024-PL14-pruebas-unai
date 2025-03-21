package diego_Actividad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
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

	public ActividadController(ActividadModel model, ActividadView view) {
		this.model = model;
		this.view = view;
		this.initView();
	}

	public void initView() {
		cargarListaPeriodosInscripcion();
		cargarInstalacionesEnComboBox();
		cargarTablaActividades();
		view.getFrame().setVisible(true);	}

	public void initController() {
		view.getBtnGuardar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> guardarActividad()));
	}

	private void cargarTablaActividades() {
		List<ActividadDisplayDTO> actividades = model.getListaActividades();
		DefaultTableModel tableModel = new DefaultTableModel(
				new String[] { "ID", "Nombre", "Descripción", "Instalación", "Aforo", "Coste Socio", "Coste No Socio",
						"Fecha Inicio", "Fecha Fin", "Días", "Hora Inicio", "Hora Fin", "Periodo Inscripción" },
				0);

		for (ActividadDisplayDTO actividad : actividades) {
			tableModel.addRow(new Object[] { actividad.getId(), actividad.getNombre(), actividad.getDescripcion(),
					actividad.getInstalacion_id(), actividad.getAforo_maximo(), actividad.getCoste_socio(),
					actividad.getCoste_no_socio(), actividad.getFecha_inicio(), actividad.getFecha_fin(),
					actividad.getDias(), actividad.getHora_inicio(), actividad.getHora_fin(),
					actividad.getPeriodo_inscripcion_id() });
		}
		view.getTablaActividades().setModel(tableModel);
	}

	private void guardarActividad() {
		try {
			String nombre = view.getNombreField().getText();
			String descripcion = view.getDescripcionField().getText();
			int instalacionId = view.getListaInstalaciones().getSelectedIndex();
			int aforoMaximo = Integer.parseInt(view.getAforoMaximoField().getText());
			double costeSocio = Double.parseDouble(view.getCosteSocioField().getText());
			double costeNoSocio = Double.parseDouble(view.getCosteNoSocioField().getText());
			String fechaInicio = view.getFechaInicioChooser().getDate().toString();
			String fechaFin = view.getFechaFinChooser().getDate().toString();
			String dias = view.getDiasField().getText();
			String horaInicio = view.getHoraInicioField().getText();
			String horaFin = view.getHoraFinField().getText();
			int periodoInscripcionId = view.getListaPeriodosInscripcion().getSelectedIndex();

			model.guardarActividad(nombre, descripcion, instalacionId, aforoMaximo, costeSocio, costeNoSocio,
					fechaInicio, fechaFin, dias, horaInicio, horaFin, periodoInscripcionId);

			view.mostrarMensaje("Actividad guardada exitosamente.");
			cargarTablaActividades();
		} catch (Exception ex) {
			view.mostrarError("Error al guardar actividad: " + ex.getMessage());
		}
	}

	// Cargar los Periodos en el ComboBox
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