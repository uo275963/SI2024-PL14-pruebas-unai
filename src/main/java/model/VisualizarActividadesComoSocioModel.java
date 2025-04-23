package model;

import java.util.ArrayList;
import java.util.List;

import giis.demo.util.Database;

public class VisualizarActividadesComoSocioModel {

	private Database db = new Database();

	public String getNombreUsuarioPorId(int idUsuario) {
		String sql = "SELECT nombre FROM USUARIO WHERE id = ?";

		// Ejecutamos la consulta y obtenemos el resultado
		List<Object[]> resultado = db.executeQueryArray(sql, idUsuario);

		if (resultado != null && !resultado.isEmpty()) {
			// Retornamos el nombre del usuario
			return (String) resultado.get(0)[0];
		}

		// Si no se encuentra el usuario, retornamos null o puedes lanzar una excepción
		return null;
	}

	public List<Object[]> getNombreInstalaciones() {
		String sql = "SELECT nombre FROM INSTALACION";
		return db.executeQueryArray(sql, null);

	}

	public List<Object[]> getActividadesPorUsuarioId(int usuarioId, String instalacion, String fechaInicio, String fechaFin) {
        String sql = "SELECT a.nombre AS actividad_nombre, " +
                     "i.nombre AS instalacion_nombre, " +
                     "a.fecha_inicio AS fecha_actividad, " +
                     "a.fecha_fin AS fecha_fin_actividad, " +
                     "pi.fecha_inicio_socios AS fecha_inscripcion, " +
                     "pi.fecha_fin_socios AS fecha_fin_socios, " +
                     "a.hora_inicio AS hora_inicio, " +
                     "a.hora_fin AS hora_fin, " +
                     "a.coste_socio AS coste " +
                     "FROM ACTIVIDAD a " +
                     "JOIN INSCRIPCION_ACTIVIDAD ia ON a.id = ia.actividad_id " +
                     "JOIN INSTALACION i ON a.instalacion_id = i.id " +
                     "JOIN PERIODO_INSCRIPCION pi ON a.periodo_inscripcion_id = pi.id " +
                     "WHERE ia.usuario_id = ?";

        List<Object> params = new ArrayList<>();
        params.add(usuarioId);

        // Agregar filtros opcionales para instalación, fechaInicio y fechaFin
        if (instalacion != null && !instalacion.isEmpty()) {
            sql += " AND i.nombre = ?";
            params.add(instalacion);
        }

        if (fechaInicio != null && !fechaInicio.isEmpty() && fechaFin != null && !fechaFin.isEmpty()) {
            // Modificamos la consulta para obtener actividades que se encuentren entre las dos fechas
            sql += " AND (a.fecha_inicio BETWEEN ? AND ? " +
                   "OR a.fecha_fin BETWEEN ? AND ? " +
                   "OR (a.fecha_inicio <= ? AND a.fecha_fin >= ?))";
            params.add(fechaInicio);
            params.add(fechaFin);
            params.add(fechaInicio);
            params.add(fechaFin);
            params.add(fechaInicio);
            params.add(fechaFin);
        }

        sql += " ORDER BY a.fecha_inicio ASC"; // Ordenar por fecha de inicio

        // Ejecutar la consulta con los parámetros
        return db.executeQueryArray(sql, params.toArray());
    }


}
