package diego_CancelarReserva;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            + "SELECT u.id AS usuario_id, "
            + "       a.id AS actividad_id, "
            + "       u.nombre AS nombre_usuario, "
            + "       a.nombre AS nombre_actividad, "
            + "       i.nombre AS nombre_instalacion, "
            + "       a.fecha_inicio, "
            + "       a.hora_inicio "
            + "FROM INSCRIPCION_ACTIVIDAD ia "
            + "JOIN USUARIO u ON ia.usuario_id = u.id "
            + "JOIN ACTIVIDAD a ON ia.actividad_id = a.id "
            + "JOIN INSTALACION i ON a.instalacion_id = i.id "
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
            dto.setFecha_actividad(row[5].toString());  // puede ser Date → String
            dto.setHora_actividad((String) row[6]);
            reservas.add(dto);
        }

        return reservas;
    }


    // Elimina la inscripción del usuario en la actividad
    public boolean cancelarReserva(int usuarioId, int actividadId) {
        // Obtener fecha y hora de inicio de la actividad
        String sql = "SELECT fecha_inicio, hora_inicio FROM ACTIVIDAD WHERE id = ?";
        List<Object[]> rows = db.executeQueryArray(sql, actividadId);

        if (rows.isEmpty()) {
            throw new RuntimeException("No se encontró la actividad con ID: " + actividadId);
        }

        String fechaStr = (String) rows.get(0)[0];
        String horaStr = (String) rows.get(0)[1];

        LocalDateTime fechaActividad = LocalDateTime.parse(fechaStr + "T" + horaStr);
        LocalDateTime ahora = LocalDateTime.now();
        int horasMinimas = getHorasMinimasCancelacion();

        Duration diferencia = Duration.between(ahora, fechaActividad);

        if (diferencia.toHours() < horasMinimas) {
            return false;
        }

        String deleteSql = "DELETE FROM INSCRIPCION_ACTIVIDAD WHERE usuario_id = ? AND actividad_id = ?";
        db.executeUpdate(deleteSql, usuarioId, actividadId);
        return true;
    }


    // Devuelve la fecha de la actividad
    public LocalDate obtenerFechaActividad(int actividadId) {
        String sql = "SELECT fecha_inicio FROM ACTIVIDAD WHERE id = ?";
        List<Object[]> rows = db.executeQueryArray(sql, actividadId);

        if (rows.isEmpty()) {
            throw new RuntimeException("No se encontró la actividad con ID: " + actividadId);
        }

     // Obtener la fecha como String desde la base de datos
        String fechaStr = (String) rows.get(0)[0];

        // Definir el formato de fecha esperado en la base de datos (Asegúrate de que coincida con el formato de tu base de datos)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Convertir el String a LocalDate
        LocalDate fechaActividad = LocalDate.parse(fechaStr, formatter);

        return fechaActividad;
    }
    
    public int getHorasMinimasCancelacion() {
        String sql = "SELECT valor FROM CONFIGURACION WHERE clave = 'min_horas_cancelacion'";
        List<Object[]> rows = db.executeQueryArray(sql);

        if (rows.isEmpty()) {
            return 24; // Valor por defecto si no está definido en la base de datos
        }

        return Integer.parseInt((String) rows.get(0)[0]);
    }

}
