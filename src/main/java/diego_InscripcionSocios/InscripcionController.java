package diego_InscripcionSocios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import giis.demo.util.ApplicationException;
import giis.demo.util.SwingUtil;

public class InscripcionController {
	private InscripcionModel model;
	private InscripcionView view;

	public InscripcionController(InscripcionModel model, InscripcionView view) {
		this.model = model;
		this.view = view;
		initView();
	}

	/**
	 * Inicializa la vista, cargando los elementos necesarios.
	 */
	public void initView() {
		cargarListaActividades();
		cargarListaPeriodosInscripcion();
		view.getFrame().setVisible(true);
	}

	/**
	 * Inicializa los controladores de eventos, como los botones.
	 */
	public void initController() {
		view.getBtnInscribirse().addActionListener(e -> SwingUtil.exceptionWrapper(() -> guardarInscripcion()));
	}

	/**
	 * Carga las actividades disponibles en el ComboBox.
	 */
	private void cargarListaActividades() {
		try {
			List<ActividadDisplayDTO> actividades = model.getActividadesDisponibles();
			DefaultComboBoxModel<ActividadComboBoxItem> actividadesModel = new DefaultComboBoxModel<>();
			actividadesModel.addElement(new ActividadComboBoxItem(0, "")); // Elemento vacío por defecto

			// Llenar el ComboBox con ActividadComboBoxItem (ID y nombre)
			for (ActividadDisplayDTO actividad : actividades) {
				actividadesModel.addElement(new ActividadComboBoxItem(actividad.getId(), actividad.getNombre()));
			}

			view.getListaActividades().setModel(actividadesModel); // Establecer el modelo adecuado
		} catch (ApplicationException e) {
			view.mostrarError("Error al cargar actividades: " + e.getMessage());
		}
	}

	/**
	 * Carga los periodos de inscripción en el ComboBox.
	 */
	private void cargarListaPeriodosInscripcion() {
		try {
			List<PeriodoDTO> periodos = model.getPeriodosInscripcion();
			DefaultComboBoxModel<Object> periodosModel = new DefaultComboBoxModel<>();
			periodosModel.addElement(""); // Elemento vacío por defecto
			for (PeriodoDTO periodo : periodos) {
				periodosModel.addElement(periodo.getNombre());
			}
			view.getListaPeriodosInscripcion().setModel(periodosModel);
		} catch (ApplicationException e) {
			view.mostrarError("Error al cargar periodos: " + e.getMessage());
		}
	}

	/**
	 * Guarda la inscripción de un socio a una actividad. En este caso, es un
	 * proceso directo con la selección de actividad y periodo.
	 */
	private void guardarInscripcion() {
		  System.out.println("guardando inscripción..."); // Esto es solo para depuración
		try {
			// Obtener el objeto seleccionado en el ComboBox
			ActividadComboBoxItem actividadSeleccionada = (ActividadComboBoxItem) view.getListaActividades()
					.getSelectedItem();

			// Validar si se seleccionó una actividad
			if (actividadSeleccionada == null) {
				throw new ApplicationException("Debe seleccionar una actividad.");
			}

			// Obtener el ID de la actividad seleccionada
			int actividadId = actividadSeleccionada.getId(); // Ya tenemos el ID directamente

			// Obtener el ID del usuario desde el JTextField
			int usuarioId = Integer.parseInt(view.getUsuarioId().getText()); // Obtener el usuario ID desde el
																				// JTextField

			// Crear la inscripción directamente con la selección
			InscripcionDTO inscripcion = new InscripcionDTO();
			inscripcion.setUsuarioId(usuarioId); // Establecer el ID del usuario
			inscripcion.setPagado(false); // Suponemos que inicialmente la inscripción no está pagada
			inscripcion.setActividadId(actividadId); // Asignar el ID de la actividad seleccionada

			// Guardar la inscripción en la base de datos
			model.guardarInscripcion(inscripcion);

			view.mostrarMensaje("Inscripción guardada correctamente.");
		} catch (ApplicationException e) {
			view.mostrarError("Error al guardar la inscripción: " + e.getMessage());
		}
	}

}
