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
	        case MONDAY: return "L";
	        case TUESDAY: return "M";
	        case WEDNESDAY: return "X";
	        case THURSDAY: return "J";
	        case FRIDAY: return "V";
	        case SATURDAY: return "S";
	        case SUNDAY: return "D";
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




}
