package diego_InscripcionSocios;

import java.util.List;
import giis.demo.util.Database;
import giis.demo.util.ApplicationException;

public class InscripcionModel {
    private Database db = new Database();

    /**
     * Obtiene la lista de periodos de inscripción desde la base de datos.
     */
    public List<PeriodoDTO> getPeriodosInscripcion() {
        String sql = "SELECT id, nombre, fecha_inicio_socios AS fecha_inicio, fecha_fin_no_socios AS fecha_fin FROM PERIODO_INSCRIPCION";
        return db.executeQueryPojo(PeriodoDTO.class, sql);
    }

    /**
     * Obtiene las actividades disponibles para la inscripción de socios.
     */
    public List<ActividadDisplayDTO> getActividadesDisponibles() {
        String sql = "SELECT id, nombre, descripcion, instalacion_id, aforo_maximo, fecha_inicio, fecha_fin, dias, hora_inicio, hora_fin " +
                     "FROM ACTIVIDAD WHERE aforo_maximo > 0";
        return db.executeQueryPojo(ActividadDisplayDTO.class, sql);
    }

    /**
     * Guarda una inscripción de un usuario a una actividad.
     */
    public void guardarInscripcion(InscripcionDTO inscripcion) {
        if (inscripcion.getUsuarioId() <= 0 || inscripcion.getActividadId() <= 0) {
            throw new ApplicationException("Datos de inscripción incompletos.");
        }

        String sql = "INSERT INTO INSCRIPCION_ACTIVIDAD (usuario_id, actividad_id, pagado) VALUES (?, ?, ?)";
        db.executeUpdate(sql, inscripcion.getUsuarioId(), inscripcion.getActividadId(), inscripcion.isPagado());
    }
}

