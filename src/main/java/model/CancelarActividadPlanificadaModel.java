package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import giis.demo.util.Database;

public class CancelarActividadPlanificadaModel {
	
	private Database db = new Database();

	
	
	
	// Método que obtiene el detalle completo de las actividades con su instalación
	public List<Object[]> getDetalleActividades() {
	    String sql = "SELECT A.nombre AS actividad, " +
	                 "I.nombre AS instalacion, " +
	                 "A.fecha_inicio, " +
	                 "A.fecha_fin, " +
	                 "A.dias, " +
	                 "A.hora_inicio, " +
	                 "A.hora_fin, " +
	                 "A.coste_socio, " +
	                 "A.coste_no_socio " +
	                 "FROM ACTIVIDAD A " +
	                 "JOIN INSTALACION I ON A.instalacion_id = I.id";

	    List<Object[]> actividades = db.executeQueryArray(sql, null);

	    if (actividades == null || actividades.isEmpty()) {
	        System.out.println("No se encontraron actividades.");
	    } else {
	        for (Object[] row : actividades) {
	            System.out.println("Actividad: " + row[0] + ", Instalación: " + row[1] +
	                               ", Fecha Inicio: " + row[2] + ", Fecha Fin: " + row[3] +
	                               ", Días: " + row[4] + ", Hora Inicio: " + row[5] +
	                               ", Hora Fin: " + row[6] + ", Socio: " + row[7] +
	                               ", No Socio: " + row[8]);
	        }
	    }

	    return actividades;
	}
	
	
	public List<Object[]> getDetalleActividadPorDias(int actividadId) {
	    String sql = "SELECT I.nombre AS instalacion, RI.fecha, RI.hora_inicio, RI.hora_fin " +
	                 "FROM RESERVA_INSTALACION RI " +
	                 "JOIN INSTALACION I ON RI.instalacion_id = I.id " +
	                 "WHERE RI.usuario_id = 3 AND RI.instalacion_id = ( " +
	                 "    SELECT instalacion_id FROM ACTIVIDAD WHERE id = ? " +
	                 ")";

	    List<Object[]> resultado = db.executeQueryArray(sql, actividadId);
	    List<Object[]> filas = new ArrayList<>();

	    for (Object[] row : resultado) {
	        String instalacion = (String) row[0];
	        LocalDate fecha = LocalDate.parse(row[1].toString());
	        LocalTime horaInicio = LocalTime.parse(row[2].toString());
	        LocalTime horaFin = LocalTime.parse(row[3].toString());

	        String dia = letraDia(fecha.getDayOfWeek());

	        filas.add(new Object[] {
	            instalacion,
	            dia,
	            fecha.toString(),
	            horaInicio.toString(),
	            horaFin.toString()
	        });
	    }

	    return filas;
	}



	// Método auxiliar para convertir DayOfWeek a letra del sistema ("L", "M", "X", "J", "V", "S", "D")
	private String letraDia(DayOfWeek day) {
	    switch (day) {
	        case MONDAY: return "Lunes";
	        case TUESDAY: return "Martes";
	        case WEDNESDAY: return "Miercoles";
	        case THURSDAY: return "Jueves";
	        case FRIDAY: return "Viernes";
	        case SATURDAY: return "Sabado";
	        case SUNDAY: return "Domingo";
	        default: return "";
	    }
	}
	
	
	public int getActividadIdPorNombre(String nombreActividad) {
	    String sql = "SELECT id FROM ACTIVIDAD WHERE nombre = ?";
	    List<Object[]> resultado = db.executeQueryArray(sql, nombreActividad);

	    if (!resultado.isEmpty()) {
	        return (int) resultado.get(0)[0];
	    }

	    return -1;
	}
	
	public List<Object[]> getInscritosActividad(int actividadId) {
	    String sql = "SELECT U.nombre, U.dni, U.rol, IA.pagado " +
	                 "FROM INSCRIPCION_ACTIVIDAD IA " +
	                 "JOIN USUARIO U ON IA.usuario_id = U.id " +
	                 "WHERE IA.actividad_id = ?";
	    List<Object[]> resultado = db.executeQueryArray(sql, actividadId);

	    // Si quieres mostrar "SOCIO" o "NO SOCIO" más explícitamente (opcional)
	    List<Object[]> datosProcesados = new ArrayList<>();
	    for (Object[] row : resultado) {
	        String rol = row[2].toString().equalsIgnoreCase("SOCIO") ? "Socio" : "No Socio";
	        datosProcesados.add(new Object[] { row[0], row[1], rol, row[3] });
	    }

	    return datosProcesados;
	}

	
	
	public int getTotalHorasReservadas(int actividadId) {
	    String sql = "SELECT hora_inicio, hora_fin FROM RESERVA_INSTALACION " +
	                 "WHERE instalacion_id = (SELECT instalacion_id FROM ACTIVIDAD WHERE id = ?) " +
	                 "AND usuario_id = 3"; // Asegúrate de usar el mismo filtro que usas para identificar reservas "de actividad"

	    List<Object[]> reservas = db.executeQueryArray(sql, actividadId);
	    int totalMinutos = 0;

	    for (Object[] row : reservas) {
	        LocalTime inicio = LocalTime.parse(row[0].toString());
	        LocalTime fin = LocalTime.parse(row[1].toString());
	        totalMinutos += java.time.Duration.between(inicio, fin).toMinutes();
	    }

	    return totalMinutos / 60; // horas completas
	}

	public void eliminarReservasActividad(int actividadId) {
	    String sql = "DELETE FROM RESERVA_INSTALACION " +
	                 "WHERE instalacion_id = (SELECT instalacion_id FROM ACTIVIDAD WHERE id = ?) " +
	                 "AND usuario_id = 3"; // filtra solo las reservas que se hicieron por actividad planificada
	    db.executeUpdate(sql, actividadId);
	}

	public void eliminarInscripcionesActividad(int actividadId) {
	    String sql = "DELETE FROM INSCRIPCION_ACTIVIDAD WHERE actividad_id = ?";
	    db.executeUpdate(sql, actividadId);
	}






}
