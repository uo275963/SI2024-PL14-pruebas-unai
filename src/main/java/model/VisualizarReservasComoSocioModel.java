package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import giis.demo.util.Database;

public class VisualizarReservasComoSocioModel {

	private Database db = new Database();

	public List<Object[]> getReservarInstalaciones(String fechaInscripcion, String Instalacion) {

		String sql = "SELECT RESERVA_INSTALACION.hora_inicio, " + "RESERVA_INSTALACION.hora_fin, "
				+ "INSTALACION.nombre, "
				+ "CASE WHEN RESERVA_INSTALACION.id IS NOT NULL THEN 'Reservada' ELSE 'Disponible' END AS estado_reserva, "
				+ "USUARIO.dni AS dni_socio " + "FROM INSTALACION "
				+ "LEFT JOIN RESERVA_INSTALACION ON INSTALACION.id = RESERVA_INSTALACION.instalacion_id "
				+ "AND RESERVA_INSTALACION.fecha = ? "
				+ "LEFT JOIN USUARIO ON RESERVA_INSTALACION.usuario_id = USUARIO.id " + "WHERE INSTALACION.nombre = ? "
				+ "ORDER BY RESERVA_INSTALACION.hora_inicio";

		return db.executeQueryArray(sql, fechaInscripcion, Instalacion);

	}

	public List<Object[]> getNombreInstalaciones() {
		String sql = "SELECT nombre FROM INSTALACION";
		return db.executeQueryArray(sql, null);

	}
	
	
	
	public void reservarHora(int idSocio, String fecha, String instalacion, String horaInicio, String horaFin,boolean pagado) {
	    String sql = "INSERT INTO RESERVA_INSTALACION (usuario_id, instalacion_id, fecha, hora_inicio, hora_fin, pagado) " +
	                 "VALUES (?, (SELECT id FROM INSTALACION WHERE nombre = ?), ?, ?, ?,?)";
	    db.executeUpdate(sql, idSocio, instalacion, fecha, horaInicio, horaFin,pagado);
	}
	
	

	

	public Integer getAforoInstalacion(String nombreInstalacion) {
		String sql = "SELECT aforo_maximo FROM INSTALACION WHERE nombre = ?";
		List<Object[]> result = db.executeQueryArray(sql, new Object[] { nombreInstalacion });

		if (result != null && !result.isEmpty()) {
			return (Integer) result.get(0)[0];
		}

		return null;
	}
	
	
	

	public Double getPrecioHoraInstalacion(String nombreInstalacion) {

		String sql = "SELECT precio_hora " + "FROM INSTALACION " + "WHERE nombre = ? ";

		List<Object[]> resultado = db.executeQueryArray(sql, nombreInstalacion);

		return ((Number) resultado.get(0)[0]).doubleValue();

	}
	
	
	public boolean masdexhorasreservadas(int idSocio) {
	    String sql = "SELECT hora_inicio, hora_fin FROM RESERVA_INSTALACION " +
	                 "WHERE usuario_id = ? AND fecha >= DATE('now', '-15 days')";
	    
	    List<Object[]> resultado = db.executeQueryArray(sql, idSocio);
	    int horasReservadas = 0;

	    for (Object[] reserva : resultado) {
	        String horaInicio = (String) reserva[0];
	        String horaFin = (String) reserva[1];

	        double horaInicioDecimal = convertirHoraAEntero(horaInicio);
	        double horaFinDecimal = convertirHoraAEntero(horaFin);

	        horasReservadas += (horaFinDecimal - horaInicioDecimal);
	        
	        if (horasReservadas > 15) {
	            return true;
	        }
	    }

	    return false;
	}


	
	/*
	public String getNombreSocio(String dni) {
		String nombre = null;
		String sql = "SELECT nombre FROM USUARIO WHERE dni = ?";

		List<Object[]> resultado = db.executeQueryArray(sql, dni);
		return ((String) resultado.get(0)[0]);

	}
	
	*/
	
	
	public Integer obtenerIdReserva(int idSocio, String fecha, String nombreInstalacion, String horaInicio) {
	    String sql = "SELECT id FROM RESERVA_INSTALACION " +
	                 "WHERE usuario_id = ? AND fecha = ? AND hora_inicio = ? " +
	                 "AND instalacion_id = (SELECT id FROM INSTALACION WHERE nombre = ?)";
	    
	    System.out.println("MODEL");
	    System.out.println("Fecha: " + fecha);
	    System.out.println("Hora Inicio: " + horaInicio);
	    System.out.println("Instalación: " + nombreInstalacion);


	    List<Object[]> resultado = db.executeQueryArray(sql, idSocio, fecha, horaInicio, nombreInstalacion);

	    if (resultado != null && !resultado.isEmpty()) {
	        // Suponemos que el id está en la primera columna
	        return (Integer) resultado.get(0)[0];
	    }

	    return null; // Retorna null si no se encuentra la reserva
	}

	
	

	public List<Object[]> getReservarActividades(String fecha, String instalacion) {
		List<Object[]> resultado = new ArrayList<>();

		String sql = "SELECT a.hora_inicio, a.nombre " + "FROM ACTIVIDAD a "
				+ "JOIN INSTALACION i ON a.instalacion_id = i.id " + "WHERE i.nombre = ?\r\n"
				+ "AND ? BETWEEN a.fecha_inicio AND a.fecha_fin";

		resultado = db.executeQueryArray(sql, instalacion, fecha);

		

		return resultado;
	}
	
	
	
	public boolean esUsuarioMoroso(int idUsuario) {
	    // Consulta SQL para verificar si el usuario está marcado como moroso
	    String sql = "SELECT estado FROM USUARIO WHERE id = ?";
	    
	    List<Object[]> resultado = db.executeQueryArray(sql, idUsuario);
	    
	    if (resultado != null && !resultado.isEmpty()) {
	        // Suponemos que 'estado' es un campo de texto que puede tener valores como 'moroso' o 'activo'.
	        String estado = (String) resultado.get(0)[0];
	        
	        // Si el estado del usuario es 'moroso', retornamos true
	        return estado.equalsIgnoreCase("moroso");
	    }
	    
	    // Si no hay resultado o el usuario no tiene estado de moroso, retornamos false
	    return false;
	}


	
	
	
	public int obtenerHorasReservadas(int idSocio, String fecha) {

	    String sql = "SELECT hora_inicio, hora_fin FROM RESERVA_INSTALACION WHERE usuario_id = ? AND fecha = ?";
	    List<Object[]> resultado = db.executeQueryArray(sql, idSocio, fecha);

	    int horasReservadas = 0;

	 // Iteramos sobre las reservas para contar las horas
	    for (Object[] reserva : resultado) {
	        String horaInicio = (String) reserva[0];
	        String horaFin = (String) reserva[1];

	        // Convertimos las horas a formato decimal
	        double horaInicioDecimal = convertirHoraAEntero(horaInicio);
	        double horaFinDecimal = convertirHoraAEntero(horaFin);

	        // Calculamos las horas reservadas sumando la diferencia entre hora de fin y hora de inicio
	        horasReservadas += (horaFinDecimal - horaInicioDecimal);
	    }


	    return horasReservadas;
	}

	// Función para convertir la hora a formato entero (hora de 24 horas como número entero)
	private double convertirHoraAEntero(String hora) {
	    String[] partes = hora.split(":");
	    int horaInt = Integer.parseInt(partes[0]);
	    int minutos = Integer.parseInt(partes[1]);

	    // Convertimos los minutos a una fracción de hora (por ejemplo: 30 minutos = 0.5 horas)
	    return horaInt + (minutos / 60.0);
	}

	
	public void registrarPagoAhora(int idSocio, Double monto) {
	    String sql = "INSERT INTO PAGOS (usuario_id, monto, fecha_pago, tipo_pago) VALUES (?, ?, NOW(), 'PAGO_INMEDIATO')";
	    db.executeUpdate(sql, idSocio, monto);
	}

	public void registrarPagoFinalMes(int idSocio, Double monto) {
	    String sql = "INSERT INTO PAGOS (usuario_id, monto, fecha_pago, tipo_pago) VALUES (?, ?, NOW(), 'PAGO_AL_FINAL_DEL_MES')";
	    db.executeUpdate(sql, idSocio, monto);
	}

	
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

	
	/*
	public void procesarPagoCuotaMensual(int idSocio, Double monto) {
	    // Verificamos que el monto sea positivo
	    if (monto <= 0) {
	        JOptionPane.showMessageDialog(null, "El monto debe ser mayor a cero.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    // Insertamos el pago en la tabla PAGO, registrando el concepto como "cuota"
	    String sql = "INSERT INTO PAGO (usuario_id, monto, concepto, fecha_pago) " +
	                 "VALUES (?, ?, 'Cuota Mensual', CURRENT_DATE)";

	    // Ejecutamos la inserción en la base de datos
	    db.executeUpdate(sql, idSocio, monto);
	    
	    // Notificación al usuario de que el pago fue procesado correctamente
	    JOptionPane.showMessageDialog(null, "Pago de cuota mensual procesado exitosamente.", "Pago Exitoso", JOptionPane.INFORMATION_MESSAGE);
	}
	
	*/
	public void registrarPagoReserva(int usuarioId, int reservaInstalacionId, double monto) {
	   

	    String sql = "INSERT INTO PAGO (usuario_id, reserva_instalacion_id, monto, concepto, fecha_pago) " +
	                 "VALUES (?, ?, ?, 'reserva', CURRENT_DATE)";

	    try {
	        // Ejecutamos la consulta para insertar el pago en la tabla PAGO
	        db.executeUpdate(sql, usuarioId, reservaInstalacionId, monto);
	        

	        // Mostrar mensaje de éxito
	        JOptionPane.showMessageDialog(null, "Pago registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	        
	    } catch (Exception e) {
	        // Manejo de errores en caso de que algo falle
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al registrar el pago. Inténtelo de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	
	
	public String getDniSocioReserva(String fecha, String instalacion, String horaInicio) {
        String sql = "SELECT u.dni FROM USUARIO u " +
                     "JOIN RESERVA_INSTALACION r ON u.id = r.usuario_id " +
                     "JOIN INSTALACION i ON r.instalacion_id = i.id " +
                     "WHERE r.fecha = ? AND i.nombre = ? AND r.hora_inicio = ?";
        List<Object[]> resultado = db.executeQueryArray(sql, fecha, instalacion, horaInicio);

        if (resultado != null && !resultado.isEmpty()) {
            return (String) resultado.get(0)[0];
        }
        return null;
    }
	
	
	public String getDniUsuarioPorId(int idsocio) {
        String sql = "SELECT dni FROM USUARIO WHERE id = ?";
        List<Object[]> resultado = db.executeQueryArray(sql, idsocio);

        if (resultado != null && !resultado.isEmpty()) {
            return (String) resultado.get(0)[0];
        }
        return null;
    }
	
	public String getNombreUsuarioPorDni(String dni) {
	    String sql = "SELECT nombre FROM USUARIO WHERE dni = ?";
	    List<Object[]> resultado = db.executeQueryArray(sql, dni);

	    if (resultado != null && !resultado.isEmpty()) {
	        return (String) resultado.get(0)[0];
	    }
	    return null;
	}

	
	

	public void insertarPago(int usuarioId, int reservaInstalacionId, double monto, String concepto) {
	    String sql = "INSERT INTO PAGO (usuario_id, reserva_instalacion_id, monto, concepto) VALUES (?, ?, ?, ?)";
	    // Ejecutamos el SQL con los parámetros dados
	    db.executeUpdate(sql, usuarioId, reservaInstalacionId, monto, concepto);
	}

}
