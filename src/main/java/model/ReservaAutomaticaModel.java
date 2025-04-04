package model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import giis.demo.util.Database;

public class ReservaAutomaticaModel {
	private Database db = new Database();
	
	public ReservaAutomaticaModel (){
		
	}
	/**
	 * Método para sacar las instalaciones de las bases de datos
	 * @return una lista con las instalaciones
	 */
	public List<Object[]> getActividades() {
		String sql = "SELECT nombre FROM ACTIVIDAD";
		List<Object[]> ins = db.executeQueryArray(sql, null);
		System.out.println(ins);
		return ins;
	}
	
	/**
	 * Método para obtener parámetros de la actividad
	 * @param nombreActividad
	 * @return
	 */

	
	public List<Object[]> getActividadDetalles(String nombreActividad) {
	    // Modificar la consulta SQL para incluir el nombre de la instalación
	    String sql = "SELECT a.nombre, a.fecha_inicio, a.fecha_fin, i.nombre " +
	                 "FROM ACTIVIDAD a " +
	                 "JOIN INSTALACION i ON a.instalacion_id = i.id " +
	                 "WHERE a.nombre = ?";
	    return db.executeQueryArray(sql, new Object[]{nombreActividad});
	}
	
	public List<Object[]> getActividadDetalles2(String nombreActividad) {
	    String sql = "SELECT a.nombre, a.fecha_inicio, a.fecha_fin, i.id, a.dias, a.hora_inicio, a.hora_fin " +
	                 "FROM ACTIVIDAD a " +
	                 "JOIN INSTALACION i ON a.instalacion_id = i.id " +
	                 "WHERE a.nombre = ?";
	    return db.executeQueryArray(sql, new Object[]{nombreActividad});
	}
	
	// He incluido dos métodos para que en la visualización de la instalacion, aparezca el nombre y no el id numero
	// Actividad detalles -> Muestra el nombre 
	// Actividad detales 2 -> Muestra el numero



	/**
	 * Método para imprimir los días y las horas
	 * @param nombreActividad
	 * @return
	 */
	public List<Object[]> getActividadDetallesHoras(String nombreActividad) {
	    // Modificar la consulta SQL para obtener los días, hora de inicio y hora de fin
	    String sql = "SELECT dias, hora_inicio, hora_fin FROM ACTIVIDAD WHERE nombre = ?";
	    
	    // Ejecutar la consulta utilizando el método `executeQueryArray` de tu clase `db`
	    return db.executeQueryArray(sql, new Object[]{nombreActividad});
	}

	/**
	 * Método para reservar la instalacion los días asociados a esa actividad
	 * @param usuarioId
	 * @param instalacionId
	 * @param fechaInicio
	 * @param fechaFin
	 * @param dias
	 * @param horaInicio
	 * @param horaFin
	 */
	public void reservarInstalacion(int usuarioId, int instalacionId, String fechaInicio, String fechaFin, String dias, String horaInicio, String horaFin) {
	    String sql = "INSERT INTO RESERVA_INSTALACION (usuario_id, instalacion_id, fecha, hora_inicio, hora_fin, pagado) VALUES (?, ?, ?, ?, ?, ?)";

	    // Convertir fechas de inicio y fin en formato LocalDate
	    LocalDate inicio = LocalDate.parse(fechaInicio);
	    LocalDate fin = LocalDate.parse(fechaFin);

	    // Separar los días de la actividad en un array
	    String[] diasArray = dias.split(",");

	    // Iterar sobre el rango de fechas
	    for (LocalDate fecha = inicio; !fecha.isAfter(fin); fecha = fecha.plusDays(1)) {
	        // Obtener el día de la semana de la fecha actual
	        String diaSemana = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());

	        // Comprobar si el día está en los días especificados de la actividad
	        for (String dia : diasArray) {
	            if (dia.trim().equalsIgnoreCase(diaSemana)) {
	                // Insertar la reserva en la base de datos
	                db.executeUpdate(sql, new Object[]{usuarioId, instalacionId, fecha.toString(), horaInicio, horaFin, true});
	            }
	        }
	    }
	}
	
	public List<Object[]> verificarConflictos(int instalacionId, String fechaInicio, String fechaFin, String dias, String horaInicio, String horaFin) {
	    // Consulta SQL para verificar si ya existe una reserva en la instalación para esa fecha y hora
	    String sql = "SELECT r.fecha, r.hora_inicio, r.hora_fin, u.nombre " +  // Aquí se selecciona 'u.nombre' (nombre del usuario)
	                 "FROM RESERVA_INSTALACION r " +
	                 "JOIN USUARIO u ON r.usuario_id = u.id " +  // Nos aseguramos de hacer JOIN con la tabla 'USUARIO'
	                 "WHERE r.instalacion_id = ? AND r.fecha BETWEEN ? AND ? " +
	                 "AND ((r.hora_inicio <= ? AND r.hora_fin > ?) OR (r.hora_inicio < ? AND r.hora_fin >= ?))";

	    return db.executeQueryArray(sql, new Object[]{instalacionId, fechaInicio, fechaFin, horaInicio, horaInicio, horaFin, horaFin});
	}
	

    /**
     * Método para obtener las reservas de la instalación que no son de un admin y que coinciden con la fecha, hora e instalación de la actividad.
     * @param instalacionId El ID de la instalación.
     * @param fechaInicio La fecha de inicio de la actividad.
     * @param fechaFin La fecha de fin de la actividad.
     * @param dias Los días de la actividad.
     * @param horaInicio La hora de inicio de la actividad.
     * @param horaFin La hora de fin de la actividad.
     * @return Una lista con las reservas que cumplen los criterios.
     */
    public List<Object[]> obtenerReservasNoAdmin(int instalacionId, String fechaInicio, String fechaFin, String dias, String horaInicio, String horaFin) {
        // Convertir las fechas de inicio y fin a LocalDate
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);
        
        // Convertir las horas de inicio y fin a LocalTime
        LocalTime horaInicioTime = LocalTime.parse(horaInicio);
        LocalTime horaFinTime = LocalTime.parse(horaFin);

        // Separar los días de la actividad en un array
        String[] diasArray = dias.split(",");

        // Lista para almacenar las reservas que coincidan
        List<Object[]> reservasAEliminar = new ArrayList<>();

        // Iterar sobre el rango de fechas
        for (LocalDate fecha = inicio; !fecha.isAfter(fin); fecha = fecha.plusDays(1)) {
            // Obtener el día de la semana de la fecha actual
            String diaSemana = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());

            // Comprobar si el día está en los días especificados de la actividad
            for (String dia : diasArray) {
                if (dia.trim().equalsIgnoreCase(diaSemana)) {
                    // Consulta SQL para obtener las reservas que coinciden con la instalación, fecha, hora, etc.
                    String sql = "SELECT r.id, u.nombre, r.fecha, r.hora_inicio, r.hora_fin, r.pagado " +
                                 "FROM RESERVA_INSTALACION r " +
                                 "JOIN USUARIO u ON r.usuario_id = u.id " +
                                 "WHERE r.instalacion_id = ? " +
                                 "AND r.fecha = ? " +
                                 "AND ((r.hora_inicio <= ? AND r.hora_fin > ?) OR (r.hora_inicio < ? AND r.hora_fin >= ?)) " +
                                 "AND u.rol <> 'ADMIN'"; // Evitar admins

                    // Ejecutamos la consulta
                    List<Object[]> reservas = db.executeQueryArray(sql, new Object[]{instalacionId, fecha.toString(), horaInicioTime.toString(), horaInicioTime.toString(), horaFinTime.toString(), horaFinTime.toString()});

                    // Agregar las reservas que coinciden con los criterios
                    reservasAEliminar.addAll(reservas);
                }
            }
        }

        // Devolvemos las reservas que coinciden con el criterio
        return reservasAEliminar;
    }

    public void eliminarReservasNoAdmin(int instalacionId, String fechaInicio, String fechaFin, String dias, String horaInicio, String horaFin) {
        // Convertir las fechas a LocalDate para compararlas más fácilmente
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);

        // Separar los días de la actividad
        String[] diasArray = dias.split(",");

        // Preparamos la consulta SQL para eliminar las reservas coincidentes
        String sql = "DELETE FROM RESERVA_INSTALACION " +
                     "WHERE usuario_id IN (SELECT id FROM USUARIO WHERE rol <> 'ADMIN') " +
                     "AND instalacion_id = ? " +
                     "AND fecha BETWEEN ? AND ? " +
                     "AND ((hora_inicio <= ? AND hora_fin > ?) OR (hora_inicio < ? AND hora_fin >= ?))";

        // Iteramos sobre las fechas de la actividad
        for (LocalDate fecha = inicio; !fecha.isAfter(fin); fecha = fecha.plusDays(1)) {
            // Obtener el día de la semana de la fecha actual
            String diaSemana = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());

            // Comprobamos si el día está en los días especificados
            for (String dia : diasArray) {
                if (dia.trim().equalsIgnoreCase(diaSemana)) {
                    // Si el día coincide, ejecutar la eliminación
                    db.executeUpdate(sql, new Object[]{instalacionId, fecha.toString(), fecha.toString(), horaInicio, horaInicio, horaFin, horaFin});
                }
            }
        }
    }

	
}
