package model;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import giis.demo.util.Database;

public class ReservaAutomaticaModel {
	private Database db = new Database();
	
	public ReservaAutomaticaModel (){
		
	}
	/**
	 * Método para sacar las instalaciones de las bases de datos
	 * @return una lista con las instalaciones
	 */
	public List<Object[]> getActividades() {
		String sql = "SELECT nombre FROM ACTIVIDAD";
		List<Object[]> ins = db.executeQueryArray(sql, null);
		System.out.println(ins);
		return ins;
	}
	
	/**
	 * Método para obtener parámetros de la actividad
	 * @param nombreActividad
	 * @return
	 */
	public List<Object[]> getActividadDetalles(String nombreActividad) {
	    // Modificar la consulta SQL para incluir el nombre de la instalación
	    String sql = "SELECT a.nombre, a.fecha_inicio, a.fecha_fin, i.nombre " +
	                 "FROM ACTIVIDAD a " +
	                 "JOIN INSTALACION i ON a.instalacion_id = i.id " +
	                 "WHERE a.nombre = ?";
	    return db.executeQueryArray(sql, new Object[]{nombreActividad});
	}

	/**
	 * Método para imprimir los días y las horas
	 * @param nombreActividad
	 * @return
	 */
	public List<Object[]> getActividadDetallesHoras(String nombreActividad) {
	    // Modificar la consulta SQL para obtener los días, hora de inicio y hora de fin
	    String sql = "SELECT dias, hora_inicio, hora_fin FROM ACTIVIDAD WHERE nombre = ?";
	    
	    // Ejecutar la consulta utilizando el método `executeQueryArray` de tu clase `db`
	    return db.executeQueryArray(sql, new Object[]{nombreActividad});
	}




}
