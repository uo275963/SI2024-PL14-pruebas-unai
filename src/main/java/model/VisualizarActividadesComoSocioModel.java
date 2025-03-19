package model;

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
	
	public List<Object[]> getActividadesPorUsuarioId(int usuarioId) {
	    String sql = "SELECT a.nombre AS actividad_nombre, " +
	                 "i.nombre AS instalacion_nombre, " +
	                 "a.fecha_inicio AS fecha_actividad, " +
	                 "pi.fecha_inicio_socios AS fecha_inscripcion, " + // Asumiendo que tomamos la fecha de inicio de socios
	                 "a.coste_socio AS coste " +
	                 "FROM ACTIVIDAD a " +
	                 "JOIN INSCRIPCION_ACTIVIDAD ia ON a.id = ia.actividad_id " +
	                 "JOIN INSTALACION i ON a.instalacion_id = i.id " +
	                 "JOIN PERIODO_INSCRIPCION pi ON a.periodo_inscripcion_id = pi.id " +
	                 "WHERE ia.usuario_id = ?";

	    // Ejecutamos la consulta con el ID del usuario y devolvemos el resultado
	    return db.executeQueryArray(sql, usuarioId);
	}




}
