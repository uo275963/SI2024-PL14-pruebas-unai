package controller;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import giis.demo.util.SwingUtil;
import model.ReservaAutomaticaModel;
import view.ReservaAutomaticaView;

public class ReservaAutomaticaController {
	private ReservaAutomaticaModel model;
	private ReservaAutomaticaView view;
	
	public ReservaAutomaticaController (ReservaAutomaticaModel m, ReservaAutomaticaView v) {
		this.model=m;
		this.view=v;
		this.initView();
	}
	
	public void initView() {
		view.getFrame().setVisible(true);
		this.meterActividades();
		
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("Día");
		modelo.addColumn("Hora Inicio");
		modelo.addColumn("Hora fin");
		this.view.setTablaDiasModel(modelo);
	}
	public void initController() {
		/**
		 * Accion en el controller para imprimir el texto del cb en la etiqueta
		 */
		view.getCbActividades().addActionListener(e -> SwingUtil.exceptionWrapper(() -> {
			String seleccion = (String) view.getCbActividades().getSelectedItem();
			view.getlActividad().setText(seleccion);
		}));
		
		view.getbMostrar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> {
			datosActividades();
			// Obtener el nombre de la actividad seleccionada
	        String actividadSeleccionada = (String) view.getCbActividades().getSelectedItem();
	        obtenerYRellenarActividad(actividadSeleccionada);

		}));
	}
	
	/**
	 *Método para meter las actividades en el combobox
	 */
	public void meterActividades() {
		List<Object[]> list = this.model.getActividades();
		
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>();
		
		for(Object[] fila: list) {
			if(fila.length > 0 && fila[0] !=null) {
				String nombreActividad = fila[0].toString();
				modelo.addElement(nombreActividad);
			}
		}
		this.view.setCbActividadesModel(modelo);
	}
	
	/**
	 * Imprime el nombre de la instalacion, la fecha inicio y la fecha fin
	 */
	public void datosActividades() {
		// Obtener el nombre de la actividad seleccionada
        String actividadSeleccionada = (String) view.getCbActividades().getSelectedItem();
        
        // Consultar la base de datos para obtener los detalles de la actividad seleccionada
        List<Object[]> actividadDetalles = model.getActividadDetalles(actividadSeleccionada);
        
        if (actividadDetalles != null && !actividadDetalles.isEmpty()) {
            // Asumiendo que la consulta devuelve la actividad con su fecha de inicio y fecha de fin
            Object[] detalles = actividadDetalles.get(0);
            String fechaInicio = detalles[1].toString(); // Fecha de inicio
            String fechaFin = detalles[2].toString();    // Fecha de fin
            String instalacion = detalles[3].toString();
            
            // Actualizar los campos de texto con los valores obtenidos
            view.getTfFechaInicio().setText(fechaInicio);
            view.getTfFechaFin().setText(fechaFin);
            view.getTfInstalacion().setText(instalacion);
        }
	}
	
	public void obtenerYRellenarActividad(String nombreActividad) {
	    // Obtener los detalles de la actividad
	    List<Object[]> detalles = model.getActividadDetallesHoras(nombreActividad);

	    // Rellenar la tabla con los datos obtenidos
	    for (Object[] detalle : detalles) {
	        String dias = (String) detalle[0];        // Obtener los días (por ejemplo, "Lunes,Miércoles,Viernes")
	        String horaInicio = (String) detalle[1];  // Obtener la hora de inicio
	        String horaFin = (String) detalle[2];     // Obtener la hora de fin

	        // Rellenar la tabla (suponiendo que tienes un método para hacerlo)
	        rellenarTablaConDias(dias, horaInicio, horaFin);
	    }
	}


	public void rellenarTablaConDias(String dias, String horaInicio, String horaFin) {
		// Limpiar la tabla antes de añadir las nuevas filas
	    DefaultTableModel modelo = (DefaultTableModel) view.gettDias().getModel();
	    modelo.setRowCount(0);  // Eliminar todas las filas de la tabla

	    // Dividir los días por coma
	    String[] diasArray = dias.split(",");

	    // Iterar sobre cada día y agregar una fila a la tabla
	    for (String dia : diasArray) {
	        // Crear un arreglo con los datos de cada fila
	        Object[] fila = new Object[3];
	        fila[0] = dia;          // Día (Lunes, Miércoles, Viernes)
	        fila[1] = horaInicio;   // Hora de inicio (18:00)
	        fila[2] = horaFin;      // Hora de fin (19:00)

	        // Agregar la fila al modelo de la tabla
	        modelo.addRow(fila);
	    }
	}

	
}
