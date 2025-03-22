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
                     "(SELECT SUM(i.precio_hora) FROM reserva_instalacion r " +
                     "JOIN instalacion i ON r.instalacion_id = i.id WHERE r.usuario_id = u.id AND r.pagado = TRUE) AS montante_reserva, " +
                     "(SELECT GROUP_CONCAT(a.nombre) FROM inscripcion_actividad ia " +
                     "JOIN actividad a ON ia.actividad_id = a.id WHERE ia.usuario_id = u.id) AS actividades, " +
                     "(SELECT SUM(p.monto) FROM pago p WHERE p.usuario_id = u.id) AS total " +
                     "FROM usuario u " +
                     "WHERE u.rol = 'SOCIO'";

        return db.executeQueryPojo(ContabilidadDTO.class, sql);
    }

    /**
     * Obtiene la información de un socio específico según su DNI.
     */
    public ContabilidadDTO obtenerSocioPorDni(String dni) {
        String sql = "SELECT u.nombre, u.dni, u.estado, " +
                     "(SELECT SUM(i.precio_hora) FROM reserva_instalacion r " +
                     "JOIN instalacion i ON r.instalacion_id = i.id WHERE r.usuario_id = u.id AND r.pagado = TRUE) AS montante_reserva, " +
                     "(SELECT GROUP_CONCAT(a.nombre) FROM inscripcion_actividad ia " +
                     "JOIN actividad a ON ia.actividad_id = a.id WHERE ia.usuario_id = u.id) AS actividades, " +
                     "(SELECT SUM(p.monto) FROM pago p WHERE p.usuario_id = u.id) AS total " +
                     "FROM usuario u WHERE u.dni = ? AND u.rol = 'SOCIO'";

        List<ContabilidadDTO> resultados = db.executeQueryPojo(ContabilidadDTO.class, sql, dni);
        if (resultados.isEmpty()) {
            throw new ApplicationException("No se encontró el socio con DNI: " + dni);
        }
        return resultados.get(0);
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

