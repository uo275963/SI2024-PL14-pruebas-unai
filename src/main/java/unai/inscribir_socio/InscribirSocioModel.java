package unai.inscribir_socio;

import java.util.Date;
import java.util.List;

import giis.demo.util.ApplicationException;
import giis.demo.util.Database;
import giis.demo.util.Util;

public class InscribirSocioModel {
	private static final String MSG_PERIODO_NO_NULO = "Se deben introducir dos fechas";
	
	private Database db=new Database();


	public List<ListaActividadesDisplayDTO> getListaActividades(String fechaInicio, String fechaFin) {
		validateNotNull(fechaInicio,MSG_PERIODO_NO_NULO);
		validateNotNull(fechaFin,MSG_PERIODO_NO_NULO);
		String sql = 
		        "SELECT a.nombre AS nombre, " 
		        + "a.descripcion AS desc, "
		        + "i.nombre AS inst, "
		        + "a.coste_socio AS precio_s, "
		        + "a.coste_no_socio AS precio_n, "
		        + "p.nombre AS periodo, "
		        + "a.fecha_inicio AS finicio, "
		        + "a.fecha_fin AS ffin "
		        + "FROM ACTIVIDAD a "
		        + "JOIN INSTALACION i ON a.instalacion_id = i.id "
		        + "LEFT JOIN PERIODO_INSCRIPCION p ON a.periodo_inscripcion_id = p.id "
		        + "WHERE finicio >= " + "\"" + fechaInicio + "\" " 
		        + "AND ffin <= " + "\"" + fechaFin + "\"" ;
		// Debug
		//System.out.println(sql);
		//System.out.println(fechaInicio);
		//System.out.println(fechaFin);

		
		return db.executeQueryPojo(ListaActividadesDisplayDTO.class, sql);
		

		
		
	}
	
	public List<PeriodoDTO> getPeriodos() {
	    String sql = "SELECT id, nombre, fecha_inicio_socios AS fecha_inicio, fecha_fin_no_socios AS fecha_fin FROM PERIODO_INSCRIPCION";
	    return db.executeQueryPojo(PeriodoDTO.class, sql);
	}


	
	private void validateNotNull(Object obj, String message) {
		if (obj==null)
			throw new ApplicationException(message);
	}
}
