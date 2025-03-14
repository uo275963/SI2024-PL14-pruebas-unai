package model;

import java.util.List;

import giis.demo.util.Database;

public class VisualizarPagosComoSocioModel {

	private Database db = new Database();

	public VisualizarPagosComoSocioModel() {

	}

	public String getNombreSocioPorId(int idSocio) {
		String sql = "SELECT nombre FROM USUARIO WHERE id = ?";
		List<Object[]> resultado = db.executeQueryArray(sql, idSocio);

		if (resultado != null && !resultado.isEmpty()) {
			return (String) resultado.get(0)[0];
		}
		return null; // Retorna null si no se encuentra el socio
	}

}
