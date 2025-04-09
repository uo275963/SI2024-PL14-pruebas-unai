package model;

import java.util.List;

import giis.demo.util.Database;

public class DesapuntarSocioModel {
	
	private Database db = new Database();
	

	/**
	 * Método para sacar las actividades de las bases de datos
	 * @return una lista con las actividades
	 */
	public List<Object[]> getActividades() {
		String sql = "SELECT nombre FROM ACTIVIDAD";
		List<Object[]> ins = db.executeQueryArray(sql, null);
		System.out.println(ins);
		return ins;
	}
	/**
	 * Método para sacar de la BD los socios apuntados a una actividad
	 * @param El nombre de la actividad
	 * @return Una lista de objetos, que serán esos socios
	 */
	public List<Object[]> getSociosPorActividad(String nombreActividad) {
		String sql = "SELECT u.nombre "
				+ "FROM INSCRIPCION_ACTIVIDAD ia "
				+ "JOIN USUARIO u ON ia.usuario_id = u.id "
				+ "JOIN ACTIVIDAD a ON ia.actividad_id = a.id "
				+ "WHERE a.nombre = ?";
		
		return db.executeQueryArray(sql, new Object[] { nombreActividad });
	}
	
	public Object[] getDatosSocio(String nombreSocio, String nombreActividad) {
		String sql = "SELECT u.dni, ia.pagado "
				+ "FROM USUARIO u "
				+ "JOIN INSCRIPCION_ACTIVIDAD ia ON ia.usuario_id = u.id "
				+ "JOIN ACTIVIDAD a ON ia.actividad_id = a.id "
				+ "WHERE u.nombre = ? AND a.nombre = ?";
		
		List<Object[]> result = db.executeQueryArray(sql, new Object[] { nombreSocio, nombreActividad });
		return result.isEmpty() ? null : result.get(0);
	}
	
	public void eliminarInscripcion(String nombreSocio, String nombreActividad) {
	    String sql = "DELETE FROM INSCRIPCION_ACTIVIDAD " +
	                 "WHERE usuario_id = (SELECT id FROM USUARIO WHERE nombre = ?) " +
	                 "AND actividad_id = (SELECT id FROM ACTIVIDAD WHERE nombre = ?)";
	    db.executeUpdate(sql, new Object[]{nombreSocio, nombreActividad});
	}

}
