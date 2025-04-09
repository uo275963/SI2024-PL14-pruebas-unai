package diego_CancelarReserva;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import giis.demo.util.Database;

public class CancelarModel {

    private Database db = new Database();

    // Verifica usuario y contraseña, devuelve el ID del usuario si es correcto, -1 si no existe
    public int obtenerIdUsuario(String nombre, String password) {
        String sql = "SELECT id FROM USUARIO WHERE nombre=? AND password=?";
        List<Object[]> rows = db.executeQueryArray(sql, nombre, password);

        if (rows.isEmpty()) {
            return -1;
        }

        return ((Number) rows.get(0)[0]).intValue();
    }

    // Devuelve las reservas activas de un usuario
    public List<CancelarDTO> obtenerReservasUsuario(int usuarioId) {
    	 String sql = ""
    		        + "SELECT u.id AS usuario_id, a.id AS actividad_id, u.nombre AS nombre_usuario, "
    		        + "a.nombre AS nombre_actividad, i.nombre AS nombre_instalacion "
    		        + "FROM INSCRIPCION_ACTIVIDAD ia "
    		        + "JOIN USUARIO u ON ia.usuario_id = u.id "
    		        + "JOIN ACTIVIDAD a ON ia.actividad_id = a.id "
    		        + "JOIN INSTALACION i ON a.instalacion_id = i.rowid "
    		        + "WHERE u.id = ?";

        List<Object[]> rows = db.executeQueryArray(sql, usuarioId);
        List<CancelarDTO> reservas = new ArrayList<>();

        for (Object[] row : rows) {
            CancelarDTO dto = new CancelarDTO();
            dto.setUsuario_id(((Number) row[0]).intValue());
            dto.setActividad_id(((Number) row[1]).intValue());
            dto.setNombre_usuario((String) row[2]);
            dto.setNombre_actividad((String) row[3]);
            dto.setNombre_instalacion((String) row[4]);
            reservas.add(dto);
        }

        return reservas;
    }


    // Elimina la inscripción del usuario en la actividad
    public void cancelarReserva(int usuarioId, int actividadId) {
        String sql = "DELETE FROM INSCRIPCION_ACTIVIDAD WHERE usuario_id = ? AND actividad_id = ?";
        db.executeUpdate(sql, usuarioId, actividadId);
    }

    // Devuelve la fecha de la actividad
    public LocalDate obtenerFechaActividad(int actividadId) {
        String sql = "SELECT fecha_inicio FROM ACTIVIDAD WHERE id = ?";
        List<Object[]> rows = db.executeQueryArray(sql, actividadId);

        if (rows.isEmpty()) {
            throw new RuntimeException("No se encontró la actividad con ID: " + actividadId);
        }

        Date fechaSql = (Date) rows.get(0)[0];
        return fechaSql.toLocalDate();
    }
}
