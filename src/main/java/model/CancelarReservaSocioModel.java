package model;

import java.util.List;

import giis.demo.util.Database;

public class CancelarReservaSocioModel {
	
	private Database db = new Database();
	
	public List<Object[]> obtenerReservas(String fecha) {
	    String query = "SELECT r.id, i.nombre, r.fecha, r.hora_inicio, r.hora_fin, r.pagado, u.nombre " +
	                   "FROM RESERVA_INSTALACION r " +
	                   "JOIN INSTALACION i ON r.instalacion_id = i.id " +
	                   "JOIN USUARIO u ON r.usuario_id = u.id " +
	                   "WHERE r.fecha = ?";
	    return db.executeQueryArray(query, fecha);
	}

	public boolean instalacionExiste(String nombreInstalacion) {
	    String query = "SELECT COUNT(*) FROM INSTALACION WHERE nombre = ?";
	    List<Object[]> result = db.executeQueryArray(query, nombreInstalacion);
	    
	    // Si el contador es mayor que 0, la instalación existe
	    return result.size() > 0 && Integer.parseInt(result.get(0)[0].toString()) > 0;
	}

	public boolean eliminarReserva(String horaInicio, String horaFin, String instalacion, String motivo) {
	    String queryDelete = "DELETE FROM RESERVA_INSTALACION WHERE hora_inicio = ? AND hora_fin = ? " +
	                         "AND instalacion_id = (SELECT id FROM INSTALACION WHERE nombre = ?)";
	    db.executeUpdate(queryDelete, horaInicio, horaFin, instalacion);

	    String queryCheck = "SELECT COUNT(*) FROM RESERVA_INSTALACION WHERE hora_inicio = ? AND hora_fin = ? " +
	                        "AND instalacion_id = (SELECT id FROM INSTALACION WHERE nombre = ?)";
	    // Ejecutamos la consulta y obtenemos el resultado
	    List<Object[]> result = db.executeQueryArray(queryCheck, horaInicio, horaFin, instalacion);

	    // El primer elemento de la lista tendrá el conteo de filas
	    int count = result.isEmpty() ? 0 : Integer.parseInt(result.get(0)[0].toString());

	    return count == 0;  // Si count es 0, la reserva fue eliminada correctamente
	}




}
