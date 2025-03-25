package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	// Método para obtener el rol de un usuario basado en la reserva
	public String obtenerRolUsuarioReserva(int reservaId) {
	    String query = "SELECT u.rol FROM USUARIO u JOIN RESERVA_INSTALACION r ON u.id = r.usuario_id WHERE r.id = ?";
	    String rol = null;
	    try (PreparedStatement stmt = db.getConnection().prepareStatement(query)) {
	        stmt.setInt(1, reservaId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                rol = rs.getString("rol");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return rol;
	}
	
	public Object[] obtenerDetallesReserva(int reservaId) {
	    // Definir la consulta SQL para obtener los detalles de la reserva
	    String query = "SELECT u.nombre, r.fecha, r.hora_inicio, r.hora_fin, r.pagado " +
	                   "FROM RESERVA_INSTALACION r " +
	                   "JOIN USUARIO u ON r.usuario_id = u.id " +
	                   "WHERE r.id = ?";

	    // Ejecutar la consulta y devolver el resultado
	    List<Object[]> result = db.executeQueryArray(query, reservaId);

	    // Si no se encuentra el resultado, devolvemos null
	    if (result.isEmpty()) {
	        return null;
	    }

	    // Devuelve los detalles de la reserva como un array de objetos
	    return result.get(0); // El primer (y único) resultado
	}


	
}
