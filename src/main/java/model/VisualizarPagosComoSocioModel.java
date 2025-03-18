package model;

import java.util.List;

import giis.demo.util.Database;

public class VisualizarPagosComoSocioModel {

	private Database db = new Database();

	public VisualizarPagosComoSocioModel() {

	}
	
	
	public List<Object[]> getMesesConPagos(int usuarioId) {
	    String sql = "SELECT DISTINCT strftime('%Y-%m', fecha_pago) AS mes, COUNT(*) AS cantidad_pagos "
	               + "FROM PAGO WHERE usuario_id = ? "
	               + "GROUP BY mes ORDER BY mes DESC";

	    return db.executeQueryArray(sql, usuarioId);
	}

	public String getNombreSocioPorId(int idSocio) {
		String sql = "SELECT nombre FROM USUARIO WHERE id = ?";
		List<Object[]> resultado = db.executeQueryArray(sql, idSocio);

		if (resultado != null && !resultado.isEmpty()) {
			return (String) resultado.get(0)[0];
		}
		return null; // Retorna null si no se encuentra el socio
	}
	
	public List<Object[]> getPagosPorMesAno(int usuarioId, String mesAnio) {
	    String sql = "SELECT \r\n"
	                + "    CASE \r\n"
	                + "        WHEN PAGO.concepto = 'Cuota mensual' THEN 'Cuota'\r\n"
	                + "        ELSE 'Reserva'\r\n"
	                + "    END AS tipo,\r\n"
	                + "    IFNULL(INSTALACION.nombre, 'N/A') AS instalacion,\r\n"
	                + "    IFNULL(RESERVA_INSTALACION.fecha, 'N/A') AS fecha_reserva,\r\n"
	                + "    PAGO.fecha_pago,\r\n"
	                + "    PAGO.monto AS coste,\r\n"
	                + "    CASE WHEN PAGO.monto > 0 THEN 'Pagado' ELSE 'Pendiente' END AS estado_pago,\r\n"
	                + "    CASE \r\n"
	                + "        WHEN RESERVA_INSTALACION.pagado = 0 THEN 'Pasado a cuota'\r\n"
	                + "        ELSE 'Momento'\r\n"
	                + "    END AS forma_pago\r\n"
	                + "FROM PAGO \r\n"
	                + "LEFT JOIN RESERVA_INSTALACION ON PAGO.reserva_instalacion_id = RESERVA_INSTALACION.id \r\n"
	                + "LEFT JOIN INSTALACION ON RESERVA_INSTALACION.instalacion_id = INSTALACION.id \r\n"
	                + "WHERE PAGO.usuario_id = ? AND strftime('%Y-%m', PAGO.fecha_pago) = ?";

	    return db.executeQueryArray(sql, usuarioId, mesAnio);
	}

}
