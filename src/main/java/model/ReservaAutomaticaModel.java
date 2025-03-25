package model;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
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
	
	public List<Object[]> obtenerReservasNoAdmin() {
	    String sql = "SELECT r.id, u.nombre, r.fecha, r.hora_inicio, r.hora_fin, r.pagado " +
	                 "FROM RESERVA_INSTALACION r " +
	                 "JOIN USUARIO u ON r.usuario_id = u.id " +
	                 "WHERE u.rol <> 'ADMIN'";

	    List<Object[]> resultados = db.executeQueryArray(sql, new Object[]{});

	    // Convertimos la columna pagado a boolean manualmente
	    for (Object[] fila : resultados) {
	        int pagadoInt = (int) fila[5]; // Obtenemos el valor entero
	        fila[5] = pagadoInt == 1; // Convertimos 1 -> true, 0 -> false
	    }

	    return resultados;
	}



	public void eliminarReservasNoAdmin() {
	    String sql = "DELETE FROM RESERVA_INSTALACION WHERE usuario_id IN " +
	                 "(SELECT id FROM USUARIO WHERE rol <> 'ADMIN')";
	    db.executeUpdate(sql, new Object[]{});
	}



}
