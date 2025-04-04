package diego_CancelarReserva;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import giis.demo.util.Database;

public class CancelarModel {
	 private Database db = new Database();

	/**
	 * Obtiene todos los usuarios que tienen reservas activas.
	 * @return Lista de nombres de usuario
	 */
	public List<String> obtenerUsuarios() {
		List<String> usuarios = new ArrayList<>();
		String sql = "SELECT DISTINCT u.nombre FROM usuario u " +
					 "JOIN inscripcion_actividad ia ON u.usuario_id = ia.usuario_id " +
					 "WHERE ia.pagado = 1";

		

		return usuarios;
	}

	/**
	 * Obtiene las reservas pagadas de un usuario específico.
	 * @param nombreUsuario Nombre del usuario
	 * @return Lista de arrays con los datos de cada reserva
	 */
	public List<String[]> obtenerReservas(String nombreUsuario) {
		List<String[]> reservas = new ArrayList<>();

		String sql = "SELECT ia.inscripcion_id AS id, a.nombre AS actividad, p.fecha_inicio AS fecha, a.hora_inicio AS hora " +
					 "FROM inscripcion_actividad ia " +
					 "JOIN usuario u ON ia.usuario_id = u.usuario_id " +
					 "JOIN actividad a ON ia.actividad_id = a.actividad_id " +
					 "JOIN periodo p ON a.periodo_id = p.periodo_id " +
					 "WHERE u.nombre = ? AND ia.pagado = 1";

		try (PreparedStatement stmt = db.getConnection().prepareStatement(sql)) {
			stmt.setString(1, nombreUsuario);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					String[] reserva = {
						rs.getString("id"),
						rs.getString("actividad"),
						rs.getString("fecha"),
						rs.getString("hora")
					};
					reservas.add(reserva);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al obtener reservas: " + e.getMessage(), e);
		}

		return reservas;
	}

	/**
	 * Marca una reserva como cancelada (pagado = 0).
	 * @param idReserva ID de la reserva
	 */
	public void cancelarReserva(String idReserva) {
		String sql = "UPDATE inscripcion_actividad SET pagado = 0 WHERE inscripcion_id = ?";

		try (PreparedStatement stmt = db.getConnection().prepareStatement(sql)) {
			stmt.setString(1, idReserva);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Error al cancelar reserva: " + e.getMessage(), e);
		}
	}
}
