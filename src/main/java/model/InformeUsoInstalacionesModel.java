package model;

import java.util.ArrayList;
import java.util.List;

import giis.demo.util.Database;

public class InformeUsoInstalacionesModel {
	private Database db = new Database();
	
	public List<Object[]> obtenerNombreCuatrimestres(int anio) {
	    String startDate = anio + "-01-01";  // Fecha de inicio del año
	    String endDate = anio + "-12-31";   // Fecha de fin del año

	    String sql = "SELECT nombre FROM PERIODO_INSCRIPCION WHERE fecha_inicio_socios BETWEEN ? AND ?";
	    
	    // Ejecutar la consulta con el rango de fechas
	    List<Object[]> result = db.executeQueryArray(sql, new Object[]{startDate, endDate});

	    
	    return result;
	}



}
