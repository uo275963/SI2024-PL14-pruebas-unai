package diego_ContabilidadReservas;

import giis.demo.util.Database;
import giis.demo.util.ApplicationException;
import java.util.List;

public class ContabilidadModel {
    private Database db = new Database();

    /**
     * Obtiene la lista de socios con su información financiera.
     */
    public List<ContabilidadDTO> obtenerSocios() {
        String sql = "SELECT u.nombre, u.dni, u.estado, " +
                "COALESCE(SUM(p.monto), 0) AS montanteReserva, " +  // Suma de los pagos realizados
                "COALESCE(SUM(a.coste_socio), 0) AS actividades, " + // Suma de los costos de las actividades
                "COALESCE(SUM(p.monto), 0) + COALESCE(SUM(a.coste_socio), 0) AS total " +  // Suma total
                "FROM USUARIO u " +
                "LEFT JOIN PAGO p ON u.id = p.usuario_id " +
                "LEFT JOIN INSCRIPCION_ACTIVIDAD ia ON u.id = ia.usuario_id " +
                "LEFT JOIN ACTIVIDAD a ON ia.actividad_id = a.id " +
                "WHERE u.rol = 'SOCIO' " +  // Filtra solo a los socios
                "GROUP BY u.id";

        return db.executeQueryPojo(ContabilidadDTO.class, sql);
    }

    /**
     * Obtiene la lista de pagos realizados por un socio según su ID.
     */
    public List<ContabilidadDTO> obtenerPagosPorSocio(int usuarioId) {
        String sql = "SELECT p.monto, p.concepto, p.fecha_pago FROM pago p WHERE p.usuario_id = ?";
        return db.executeQueryPojo(ContabilidadDTO.class, sql, usuarioId);
    }

    /**
     * Obtiene los detalles de las reservas de instalaciones de un socio según su ID.
     */
    public List<ContabilidadDTO> obtenerReservasPorSocio(int usuarioId) {
        String sql = "SELECT i.nombre AS instalacion, r.fecha, r.hora_inicio, r.hora_fin, r.pagado FROM reserva_instalacion r " +
                     "JOIN instalacion i ON r.instalacion_id = i.id WHERE r.usuario_id = ?";
        return db.executeQueryPojo(ContabilidadDTO.class, sql, usuarioId);
    }

    /**
     * Obtener los socios en formato de array para usar en ComboBox u otros controles.
     */
    public List<Object[]> obtenerSociosArray() {
        String sql = "SELECT u.dni, u.nombre FROM usuario u WHERE u.rol = 'SOCIO'";
        return db.executeQueryArray(sql);
    }
}

