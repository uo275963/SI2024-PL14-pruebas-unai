package unai.inscribir_no_socio;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import giis.demo.util.ApplicationException;
import giis.demo.util.Database;
import giis.demo.util.Util;

public class InscribirNoSocioModel {
	private static final String MSG_PERIODO_NO_NULO = "Se deben introducir dos fechas";
	
	private Database db=new Database();

	
	public void registrarNuevoNoSocio(String dni, String nombre) {
	    String sql = "INSERT INTO USUARIO (dni, nombre, rol, estado, password) VALUES (?, ?, 'NO_SOCIO', 'ACTIVO', 'default')";
	    
	    // Ejecuta la consulta para insertar al no socio
	    db.executeUpdate(sql, dni, nombre);
	   
	}
	
	public int getAforoMaximoDeActividad(int actividadId) {
	    String sql = "SELECT aforo_maximo FROM ACTIVIDAD WHERE id = ?";
	    List<Object[]> resultado = db.executeQueryArray(sql, actividadId);

	    if (resultado != null && !resultado.isEmpty()) {
	        Object[] fila = resultado.get(0);
	        if (fila != null && fila.length > 0) {
	            Object aforo = fila[0];
	            if (aforo != null) {
	                try {
	                    return Integer.parseInt(aforo.toString());
	                } catch (NumberFormatException e) {
	                    e.printStackTrace();
	                    return -1;  // Devolver -1 en caso de error
	                }
	            }
	        }
	    }
	    return -1;  // Si no hay resultados o el aforo es nulo
	}
	
	  public boolean estaNoSocioInscritoEnActividad(int socioId, int actividadId) {
		    String sql = "SELECT COUNT(*) FROM INSCRIPCION_ACTIVIDAD WHERE usuario_id = ? AND actividad_id = ?";
		    List<Object[]> resultado = db.executeQueryArray(sql, socioId, actividadId);

		    if (resultado != null && !resultado.isEmpty()) {
		        Object[] fila = resultado.get(0);
		        if (fila != null && fila.length > 0) {
		            try {
		                int count = Integer.parseInt(fila[0].toString());
		                return count > 0; // Retorna true si ya está inscrito
		            } catch (NumberFormatException e) {
		                e.printStackTrace();
		            }
		        }
		    }
		    return false; // Si no hay resultados o hay un error
		    
	  }
	
	public int getNumeroDeInscritos(int actividadId) {
	    String sql = "SELECT COUNT(*) FROM INSCRIPCION_ACTIVIDAD WHERE actividad_id = ?";
	    List<Object[]> resultado = db.executeQueryArray(sql, actividadId);

	    if (resultado != null && !resultado.isEmpty()) {
	        Object[] fila = resultado.get(0);
	        if (fila != null && fila.length > 0) {
	            Object count = fila[0];
	            if (count != null) {
	                try {
	                    return Integer.parseInt(count.toString());
	                } catch (NumberFormatException e) {
	                    e.printStackTrace();
	                    return -1;  // Devolver -1 en caso de error
	                }
	            }
	        }
	    }
	    return -1;  // Si no hay resultados o el conteo es nulo
	}
	
	
	public String obtenerNombreInstalacion(int actividadId) {
	    String sql = "SELECT i.nombre FROM ACTIVIDAD a JOIN INSTALACION i ON a.instalacion_id = i.id WHERE a.id = ?";
	    List<Object[]> resultado = db.executeQueryArray(sql, actividadId);

	    if (resultado != null && !resultado.isEmpty()) {
	        Object[] fila = resultado.get(0);
	        if (fila != null && fila.length > 0) {
	            Object nombre = fila[0];
	            if (nombre != null) {
	                return nombre.toString();
	            }
	        }
	    }
	    return null;  // Si no se encuentra la instalación
	}

	
	
	
	
	
	
	public Double getCosteNoSocio(int actividadId) {
	    String sql = "SELECT coste_no_socio FROM ACTIVIDAD WHERE id = ?";
	    List<Object[]> resultado = db.executeQueryArray(sql, actividadId);

	    if (resultado != null && !resultado.isEmpty()) {
	        Object[] fila = resultado.get(0);
	        if (fila != null && fila.length > 0) {
	            Object coste = fila[0];
	            if (coste != null) {
	                try {
	                    return Double.parseDouble(coste.toString());
	                } catch (NumberFormatException e) {
	                    e.printStackTrace();
	                    return 0.0;  // Devolver 0.0 si hay error
	                }
	            }
	        }
	    }
	    return 0.0;  // Si no hay resultados
	}
	
	
	public void registrarPago(int usuarioId, int actividadId, Double monto) {
	    String concepto = obtenerNombreActividad(actividadId);  // Recuperamos el nombre de la actividad
	    String sql = "INSERT INTO PAGO (usuario_id, inscripcion_actividad_id, monto, concepto) VALUES (?, ?, ?, ?)";
	    
	    // Primero, insertar en la tabla de pagos
	    db.executeUpdate(sql, usuarioId, actividadId, monto, concepto);
	}
	
	public String obtenerNombreActividad(int actividadId) {
	    String sql = "SELECT nombre FROM ACTIVIDAD WHERE id = ?";
	    List<Object[]> resultado = db.executeQueryArray(sql, actividadId);

	    if (resultado != null && !resultado.isEmpty()) {
	        Object[] fila = resultado.get(0);
	        if (fila != null && fila.length > 0) {
	            Object nombre = fila[0];
	            if (nombre != null) {
	                return nombre.toString();
	            }
	        }
	    }
	    return null;  // Si no se encuentra la actividad
	}

	
	
	
	  public List<SocioDTO> getListaSocios() {
	        String sql = "SELECT nombre, dni FROM USUARIO WHERE rol = 'SOCIO' AND estado = 'ACTIVO'";
	        return db.executeQueryPojo(SocioDTO.class, sql);
	    }
	  
	  public void inscribirNoSocioEnActividad(int socioId, int actividadId) {
		    // Construir la consulta SQL para insertar la inscripción
		    String sql = "INSERT INTO INSCRIPCION_ACTIVIDAD (usuario_id, actividad_id, pagado) " +
		                 "VALUES (" + socioId + ", " + actividadId + ", FALSE)";

		    // Imprimir la consulta para depuración
		    System.out.println(sql);

		    // Ejecutar la consulta
		    db.executeUpdate(sql);
		}
	  
	  public Integer getIdSocioPorDNI(String dni) {
		  String sql = "SELECT id FROM USUARIO WHERE dni = ? AND rol = 'NO_SOCIO'";

		  System.out.println();
		  System.out.println("model----");
		  System.out.println(sql);
		  System.out.println(dni);
		// Ejecuta la consulta y obtiene el resultado como una lista de arrays
		List<Object[]> resultado = db.executeQueryArray(sql, dni);

		if (resultado != null && !resultado.isEmpty()) {
		    // Accede al primer array dentro de la lista
		    Object[] fila = resultado.get(0); // El primer array en la lista
		    if (fila != null && fila.length > 0) {
		        Object id = fila[0]; // El valor de 'id' estará en la primera posición del array
		        if (id != null) {
		            try {
		                return Integer.parseInt(id.toString()); // Convierte a Integer de manera segura
		            } catch (NumberFormatException e) {
		                e.printStackTrace();
		                return null;
		            }
		        }
		    }
		}
		return null; // Si no hay resultados o si el id es nulo


		}

	  public Integer getIdActividadPorNombre(String nombreActividad) {
		  String sql = "SELECT id FROM ACTIVIDAD WHERE nombre = ?";  // Asegúrate de que el nombre de la columna sea correcto

		    // Ejecuta la consulta y obtiene el resultado como una lista de arrays
		    List<Object[]> resultado = db.executeQueryArray(sql, nombreActividad);

		    if (resultado != null && !resultado.isEmpty()) {
		        // Accede al primer array dentro de la lista
		        Object[] fila = resultado.get(0); // El primer array en la lista
		        if (fila != null && fila.length > 0) {
		            Object id = fila[0]; // El valor de 'id' estará en la primera posición del array
		            if (id != null) {
		                try {
		                    return Integer.parseInt(id.toString()); // Convierte a Integer de manera segura
		                } catch (NumberFormatException e) {
		                    e.printStackTrace();
		                    return null;
		                }
		            }
		        }
		    }
		    return null; // Si no hay resultados o si el id es nulo
		}

	 
	  public List<ListaActividadesDisplayDTO> getListaActividadesValidas() {
	        // Obtener la fecha actual en formato YYYY-MM-DD
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        String fechaHoy = sdf.format(new Date());

	        String sql = 
	            "SELECT a.nombre AS nombre, " +
	            "       a.descripcion AS desc, " +
	            "       i.nombre AS inst, " +
	            "       a.coste_socio AS precio_s, " +
	            "       a.coste_no_socio AS precio_n, " +
	            "       p.nombre AS periodo, " +
	            "       a.fecha_inicio AS finicio, " +
	            "       a.fecha_fin AS ffin " +
	            "FROM ACTIVIDAD a " +
	            "JOIN INSTALACION i ON a.instalacion_id = i.id " +
	            "LEFT JOIN PERIODO_INSCRIPCION p ON a.periodo_inscripcion_id = p.id " +
	            "WHERE p.fecha_inicio_socios <= " + "\"" + "2025-03-19" + "\" "
	            + "AND p.fecha_fin_no_socios >= " + "\"" + "2025-03-19" + "\" ";
	        
	        System.out.println(sql);

	        return db.executeQueryPojo(ListaActividadesDisplayDTO.class, sql);
	    }
	  
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
