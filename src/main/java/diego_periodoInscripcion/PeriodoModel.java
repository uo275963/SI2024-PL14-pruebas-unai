package diego_periodoInscripcion;

import java.util.Date;
import java.util.List;
import giis.demo.util.Database;
import giis.demo.util.ApplicationException;
import giis.demo.util.Util;

public class PeriodoModel {
    private Database db = new Database();

    /**
     * Guarda un nuevo período en la base de datos.
     */
    public void guardarPeriodo(String nombre, String fechaInicio, String fechaFin, String fechaFinNoSocios) {
        if (nombre == null || nombre.isEmpty() || fechaInicio == null || fechaFin == null || fechaFinNoSocios == null) {
            throw new ApplicationException("Todos los campos deben estar completos.");
        }

        String sql = "INSERT INTO PERIODO_INSCRIPCION (nombre, fecha_inicio_socios, fecha_fin_socios, fecha_fin_no_socios) VALUES (?, ?, ?, ?)";
        db.executeUpdate(sql, nombre, fechaInicio, fechaFin, fechaFinNoSocios);
    }

    /**
     * Obtiene la lista de períodos desde la base de datos.
     */
    public List<PeriodoDisplayDTO> getListaPeriodos() {
        String sql = "SELECT id, nombre, fecha_inicio_socios, fecha_fin_socios, fecha_fin_no_socios FROM PERIODO_INSCRIPCION";
        return db.executeQueryPojo(PeriodoDisplayDTO.class, sql);
    }

    /**
     * Obtiene la lista de períodos en formato de array para usar en ComboBox.
     */
    public List<Object[]> getListaPeriodosArray() {
        String sql = "SELECT id, nombre FROM PERIODO_INSCRIPCION";
        return db.executeQueryArray(sql);
    }

}