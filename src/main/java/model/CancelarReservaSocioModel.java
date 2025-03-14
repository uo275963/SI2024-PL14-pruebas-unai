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


}
