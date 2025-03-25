package controller;


import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
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
		
		view.getbReserva().addActionListener(e -> SwingUtil.exceptionWrapper(() -> {
		    String actividadSeleccionada = view.getCbActividades().getSelectedItem().toString();
		    reservarActividadSeleccionada(actividadSeleccionada);
		}));
		
		view.getbEliminar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> {
			String actividadSeleccionada = view.getCbActividades().getSelectedItem().toString();
		    eliminarReservasNoAdmin(actividadSeleccionada);
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
	
	/**
	 * Rellenar la tabla con los dias
	 * @param nombreActividad
	 */
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

	/**
	 * Sacar los parametros para la tabla
	 * @param dias
	 * @param horaInicio
	 * @param horaFin
	 */
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

	/**
	 * Reservar los dias de la actividad
	 * @param nombreActividad
	 */
	public void reservarActividadSeleccionada(String nombreActividad) {
	    List<Object[]> detalles = model.getActividadDetalles2(nombreActividad);

	    if (!detalles.isEmpty()) {
	        Object[] detalle = detalles.get(0);
	        
	        String nombre = detalle[0].toString();  // Nombre de la actividad
	        String fechaInicio = detalle[1].toString();  // Fecha de inicio (String)
	        String fechaFin = detalle[2].toString();  // Fecha de fin (String)
	        int instalacionId = (int) detalle[3]; // Ahora detalle[3] es un entero (ID)
	        String dias = detalle[4].toString();  // Días de la actividad
	        String horaInicio = detalle[5].toString(); // Hora de inicio
	        String horaFin = detalle[6].toString(); // Hora de fin

	        int usuarioId = 3; // Usuario fijo según lo solicitado

	        // Verificamos si ya existe alguna reserva en esos días y horarios
	        List<Object[]> conflictos = model.verificarConflictos(instalacionId, fechaInicio, fechaFin, dias, horaInicio, horaFin);

	        if (!conflictos.isEmpty()) {
	            // Si existen conflictos, mostramos el mensaje de conflictos y actualizamos el TextArea con los detalles de los conflictos
	            String mensajeConflictos = "Conflictos existentes";
	            StringBuilder conflictosDetalles = new StringBuilder("Conflictos existentes:\n");

	            for (Object[] conflicto : conflictos) {
	                // Formato: Nombre del usuario, Día, Hora inicio, Hora fin
	                String usuario = conflicto[3].toString(); // Usuario que tiene la reserva
	                String dia = conflicto[0].toString(); // Día
	                String horaIni = conflicto[1].toString(); // Hora de inicio
	                String horaFi = conflicto[2].toString(); // Hora de fin

	                // Construir el texto para el TextArea
	                conflictosDetalles.append("- ").append(usuario).append(", ").append(dia)
	                                   .append(", ").append(horaIni).append(" - ").append(horaFi).append("\n");
	            }

	            // Actualizamos el TextArea con los conflictos
	            view.getTaConflictos().setText(conflictosDetalles.toString());

	            // Mostrar un mensaje en el JOptionPane
	            JOptionPane.showMessageDialog(null, 
	                mensajeConflictos, 
	                "Conflictos en la reserva", 
	                JOptionPane.WARNING_MESSAGE);
	        } else {
	            // Si no hay conflictos, realizamos la reserva
	            model.reservarInstalacion(usuarioId, instalacionId, fechaInicio, fechaFin, dias, horaInicio, horaFin);
	            
	            JOptionPane.showMessageDialog(null, 
	                "Reserva realizada correctamente", 
	                "Información", 
	                JOptionPane.INFORMATION_MESSAGE);
	        }
	    }
	}


public void eliminarReservasNoAdmin(String nombreActividad) {
	
		List<Object[]> detalles = model.getActividadDetalles2(nombreActividad);


        Object[] detalle = detalles.get(0);
        
        String nombre = detalle[0].toString();  // Nombre de la actividad
        String fechaInicio = detalle[1].toString();  // Fecha de inicio (String)
        String fechaFin = detalle[2].toString();  // Fecha de fin (String)
        int instalacionId = (int) detalle[3]; // Ahora detalle[3] es un entero (ID)
        String dias = detalle[4].toString();  // Días de la actividad
        String horaInicio = detalle[5].toString(); // Hora de inicio
        String horaFin = detalle[6].toString(); // Hora de fin
		
		
	    List<Object[]> reservasAEliminar = model.obtenerReservasNoAdmin(instalacionId, fechaInicio, fechaFin, dias, horaInicio, horaFin);
	    
	    // Comprobamos si hay conflictos en taConflictos
	    if (view.getTaConflictos().getText().trim().isEmpty()) {
	        JOptionPane.showMessageDialog(null, 
	            "No hay conflictos existentes.", 
	            "Información", 
	            JOptionPane.INFORMATION_MESSAGE);
	        return; // No hacemos nada si no hay conflictos
	    }

	    if (reservasAEliminar.isEmpty()) {
	        JOptionPane.showMessageDialog(null, 
	            "No hay reservas de usuarios no administradores para eliminar.", 
	            "Información", 
	            JOptionPane.INFORMATION_MESSAGE);
	        return;
	    }

	    StringBuilder mensajeReservas = new StringBuilder("Se eliminarán las siguientes reservas:\n");
	    for (Object[] reserva : reservasAEliminar) {
	        String usuario = reserva[1].toString();  // Nombre del usuario
	        String fecha = reserva[2].toString();   // Fecha de la reserva
	        String horaIni = reserva[3].toString(); // Hora de inicio
	        String horaFi = reserva[4].toString(); // Hora de fin
	        // Obtener el valor de "pagado" como Integer
	        Integer pagadoInt = (Integer) reserva[5];

	        // Convertir el valor a booleano: 1 -> true, 0 -> false
	        boolean pagado = (pagadoInt != null && pagadoInt == 1); // Si es 1, es verdadero; si es 0, es falso


	        mensajeReservas.append("- ").append(usuario).append(", ")
	                       .append(fecha).append(", ")
	                       .append(horaIni).append(" - ")
	                       .append(horaFi).append(", Pagado: ")
	                       .append(pagado ? "Sí" : "No").append("\n");
	    }

	    // Mostrar la lista de reservas con dos botones: OK y Cancelar
	    int opcion = JOptionPane.showOptionDialog(
	        null, 
	        mensajeReservas.toString(), 
	        "Confirmar eliminación", 
	        JOptionPane.OK_CANCEL_OPTION, 
	        JOptionPane.WARNING_MESSAGE, 
	        null, 
	        new Object[]{"OK", "Cancelar"}, 
	        "Cancelar"
	    );

	    // Si el usuario presiona "OK", eliminar las reservas
	    if (opcion == JOptionPane.OK_OPTION) {
	        model.eliminarReservasNoAdmin(instalacionId, fechaInicio, fechaFin, dias, horaInicio, horaFin);; // Llamamos al modelo para eliminar las reservas
	        
	        JOptionPane.showMessageDialog(null, 
	            "Reservas eliminadas correctamente.", 
	            "Éxito", 
	            JOptionPane.INFORMATION_MESSAGE);
	    } else {
	        JOptionPane.showMessageDialog(null, 
	            "Eliminación cancelada. No se ha eliminado ninguna reserva.", 
	            "Cancelado", 
	            JOptionPane.INFORMATION_MESSAGE);
	    }
	}

}
