package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import giis.demo.util.Database;

public class InformeUsoInstalacionesModel {
	private Database db = new Database();
	
	/**
	 * Metodo que devuelve el nombre de un cuatrimestre 
	 * @param Un año en específico
	 * @return una lista de objetos con los nombres de los cuatrimestres
	 */
	public List<Object[]> obtenerNombreCuatrimestres(int anio) {
	    String startDate = anio + "-01-01";  // Fecha de inicio del año
	    String endDate = anio + "-12-31";   // Fecha de fin del año

	    String sql = "SELECT nombre FROM PERIODO_INSCRIPCION WHERE fecha_inicio_socios BETWEEN ? AND ?";
	    
	    // Ejecutar la consulta con el rango de fechas
	    List<Object[]> result = db.executeQueryArray(sql, new Object[]{startDate, endDate});

	    
	    return result;
	}
	
	/**
	 * Método para generar el informe de uso de instalaciones 
	 * @param La fecha de inicio 
	 * @param La fecha final
	 * @return El informe de uso de insalaciones
	 */
	public List<Object[]> generarInformeUsoInstalaciones(String fechaInicio, String fechaFin) {
	    String sql = 
	        "SELECT i.nombre, " +
	        "       COUNT(DISTINCT a.id) AS num_actividades, " +
	        "       COUNT(DISTINCT ri.id) AS num_reservas, " +
	        "       COALESCE(SUM(strftime('%H', a.hora_fin) - strftime('%H', a.hora_inicio)), 0) * COUNT(DISTINCT a.id) AS horas_actividades, " +
	        "       COALESCE(SUM(strftime('%H', ri.hora_fin) - strftime('%H', ri.hora_inicio)), 0) AS horas_reservas, " +
	        "       i.id " + // añadimos el ID para poder usarlo luego si se desea
	        "FROM INSTALACION i " +
	        "LEFT JOIN ACTIVIDAD a ON a.instalacion_id = i.id AND a.fecha_inicio BETWEEN ? AND ? " +
	        "LEFT JOIN RESERVA_INSTALACION ri ON ri.instalacion_id = i.id AND ri.fecha BETWEEN ? AND ? " +
	        "GROUP BY i.id";

	    return db.executeQueryArray(sql, fechaInicio, fechaFin, fechaInicio, fechaFin);
	}


	/**
	 * Transfora ese informe en un archivo.txt
	 * @param fechaInicio
	 * @param fechaFin
	 * @param nombreArchivo
	 */
	public void generarInformeArchivo(String fechaInicio, String fechaFin, String nombreArchivo) {
	    // Llamar al método que genera el informe
	    List<Object[]> informe = generarInformeUsoInstalaciones(fechaInicio, fechaFin);

	    // Calcular días del periodo
	    LocalDate inicio = LocalDate.parse(fechaInicio);
	    LocalDate fin = LocalDate.parse(fechaFin);
	    long diasPeriodo = ChronoUnit.DAYS.between(inicio, fin) + 1; // Incluye el último día
	    int horasPorDia = 12; // Suponemos apertura 09:00 y cierre 21:00

	    // Creamos el archivo donde se guardará el informe
	    File archivo = new File(nombreArchivo);
	    
	    // Usamos BufferedWriter para escribir en el archivo
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {

	    	writer.write("Informe de Actividades por Instalación");
	        writer.newLine();
	        writer.write("Periodo: " + fechaInicio + " - " + fechaFin);
	        writer.newLine();
	        writer.write("----------------------------------------------------------");

	        
	        // Agregar un espacio adicional antes de los datos de los socios
	        writer.newLine(); // Esta es la línea adicional de espacio
	        writer.newLine(); // Otra línea de espacio

	        // Escribimos los datos de cada socio
	        for (Object[] fila : informe) {
	        	 String nombreInstalacion = (String) fila[0];
	             int numActividades = (fila[1] != null) ? ((Number) fila[1]).intValue() : 0;
	             int numReservas = (fila[2] != null) ? ((Number) fila[2]).intValue() : 0;
	             int horasActividades = (fila[3] != null) ? ((Number) fila[3]).intValue() : 0;
	             int horasReservas = (fila[4] != null) ? ((Number) fila[4]).intValue() : 0;

	             int totalHorasDisponibles = (int) diasPeriodo * horasPorDia;
	             double porcentajeActividades = totalHorasDisponibles > 0 ? (horasActividades * 100.0 / totalHorasDisponibles) : 0;
	             double porcentajeReservas = totalHorasDisponibles > 0 ? (horasReservas * 100.0 / totalHorasDisponibles) : 0;
	             double porcentajeGeneral = porcentajeActividades + porcentajeReservas;

	             writer.write("Instalación: " + nombreInstalacion);
	             writer.newLine();
	             writer.write("Número de Actividades: " + numActividades);
	             writer.newLine();
	             writer.write("Número de Reservas: " + numReservas);
	             writer.newLine();
	             writer.write(String.format("%% Uso por Actividades: %.2f%%", porcentajeActividades));
	             writer.newLine();
	             writer.write(String.format("%% Uso por Reservas: %.2f%%", porcentajeReservas));
	             writer.newLine();
	             writer.write(String.format("%% Uso Total: %.2f%%", porcentajeGeneral));
	             writer.newLine();
	             writer.write("----------------------------------------------------------");
	             writer.newLine();
	         }

	        System.out.println("Informe generado correctamente en el archivo: " + nombreArchivo);

	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Error al escribir el archivo.");
	    }
	}

	/**
	 * Obtener las fechas de los cuatrimestres
	 * @param nombre
	 * @param anio
	 * @return Una lista con las fechas de los cuatrimestres
	 */
	public List<Object[]> obtenerFechasCuatrimestre(String nombre, int anio) {
	    String startDate = anio + "-01-01";
	    String endDate = anio + "-12-31";

	    String sql = "SELECT fecha_inicio_socios, fecha_fin_no_socios " +
	                 "FROM PERIODO_INSCRIPCION " +
	                 "WHERE nombre = ? AND fecha_inicio_socios BETWEEN ? AND ?";

	    return db.executeQueryArray(sql, new Object[]{nombre, startDate, endDate});
	}


}
