package diego_InscripcionSocios;

import java.util.List;
import giis.demo.util.Database;
import giis.demo.util.ApplicationException;
import diego_InscripcionSocios.ActividadDisplayDTO;

public class InscripcionModel {
    private Database db = new Database();

    /**
     * Obtiene las actividades disponibles para la inscripción.
     * Se filtran por el periodo activo y se verifica si hay plazas disponibles.
     */
    public List<ActividadDisplayDTO> obtenerActividadesDisponibles() {
        String sql = "SELECT a.id, a.nombre, a.descripcion, a.instalacion_id, a.aforo_maximo, a.coste_socio, a.coste_no_socio, " +
                     "a.fecha_inicio, a.fecha_fin, a.dias, a.hora_inicio, a.hora_fin, a.periodo_inscripcion_id " +
                     "FROM ACTIVIDAD a " +
                     "JOIN PERIODO_INSCRIPCION p ON a.periodo_inscripcion_id = p.id " +
                     "WHERE p.fecha_inicio_socios <= CURDATE() AND p.fecha_fin_socios >= CURDATE() " +
                     "AND a.aforo_maximo > (SELECT COUNT(*) FROM INSCRIPCION_ACTIVIDAD i WHERE i.actividad_id = a.id)";
        
        // Obtener las actividades desde la base de datos
        List<ActividadDisplayDTO> actividades = db.executeQueryPojo(ActividadDisplayDTO.class, sql);
        if (actividades.isEmpty()) {
            throw new ApplicationException("No hay actividades disponibles en este periodo.");
        }

        // Agregar más lógica si es necesario (por ejemplo, aforo restante, fechas formateadas, etc.)
        return actividades;
    }

    /**
     * Verifica si hay plazas disponibles para una actividad en particular.
     */
    public boolean verificarDisponibilidad(int actividadId) {
        String sql = "SELECT aforo_maximo - (SELECT COUNT(*) FROM INSCRIPCION_ACTIVIDAD WHERE actividad_id = ?) " +
                     "FROM ACTIVIDAD WHERE id = ?";
        List<Integer> resultados = db.executeQueryPojo(Integer.class, sql, actividadId, actividadId);
        if (resultados.isEmpty()) {
            throw new ApplicationException("Actividad no encontrada.");
        }
        return resultados.get(0) > 0;
    }

    /**
     * Realiza la inscripción de un usuario a una actividad.
     */
    public void inscribirUsuario(int usuarioId, int actividadId) {
        String sql = "INSERT INTO INSCRIPCION_ACTIVIDAD (usuario_id, actividad_id, pagado) VALUES (?, ?, FALSE)";
        db.executeUpdate(sql, usuarioId, actividadId);
    }
}

