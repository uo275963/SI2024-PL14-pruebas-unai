package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

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
	
	public List<Object[]> generarInformeActividadesPorInstalacion(String fechaInicio, String fechaFin) {
	    String sql = 
	        "SELECT i.nombre, COUNT(*) AS num_actividades " +
	        "FROM ACTIVIDAD a " +
	        "JOIN INSTALACION i ON a.instalacion_id = i.id " +
	        "WHERE a.fecha_inicio BETWEEN ? AND ? " +
	        "GROUP BY i.nombre " +
	        "ORDER BY num_actividades DESC";

	    return db.executeQueryArray(sql, fechaInicio, fechaFin);
	}

	
	public void generarInformeArchivo(String fechaInicio, String fechaFin, String nombreArchivo) {
	    // Llamar al método que genera el informe
	    List<Object[]> informe = generarInformeActividadesPorInstalacion(fechaInicio, fechaFin);

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
	             int numActividades = (Integer) fila[1];

	             writer.write("Instalación: " + nombreInstalacion);
	             writer.newLine();
	             writer.write("Número de Actividades: " + numActividades);
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


}
