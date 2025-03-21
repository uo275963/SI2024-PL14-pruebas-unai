package model;

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
		String sql = "SELECT nombre FROM INSTALACION";
		List<Object[]> ins = db.executeQueryArray(sql, null);
		System.out.println(ins);
		return ins;
	}
}
