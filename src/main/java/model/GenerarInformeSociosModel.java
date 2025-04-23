package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import giis.demo.util.Database;

public class GenerarInformeSociosModel {
	
	private Database db = new Database();
	String nombreArchivo = "InformeSocios.txt";

	public List<Object[]> generarInformeSocios(String fechaInicio, String fechaFin) {
	    String sql = 
	        "SELECT u.id, u.nombre, " +
	        "       (SELECT COUNT(*) FROM RESERVA_INSTALACION ri WHERE ri.usuario_id = u.id AND ri.fecha BETWEEN ? AND ?) AS num_reservas, " +
	        "       (SELECT COUNT(*) FROM INSCRIPCION_ACTIVIDAD ia JOIN ACTIVIDAD a ON ia.actividad_id = a.id " +
	        "        WHERE ia.usuario_id = u.id AND a.fecha_inicio BETWEEN ? AND ?) AS num_actividades, " +
	        "       (SELECT i.nombre FROM INSTALACION i " +
	        "        WHERE i.id = (SELECT ri.instalacion_id FROM RESERVA_INSTALACION ri " +
	        "                       WHERE ri.usuario_id = u.id AND ri.fecha BETWEEN ? AND ? " +
	        "                       GROUP BY ri.instalacion_id ORDER BY COUNT(*) DESC LIMIT 1)) AS instalacion_mas_usada, " +
	        "       (SELECT COALESCE(SUM(p.monto), 0) FROM PAGO p " +
	        "        WHERE p.usuario_id = u.id AND p.fecha_pago BETWEEN ? AND ?) AS dinero_gastado " + // Cambio de p.fecha a p.fecha_pago
	        "FROM USUARIO u " +
	        "WHERE u.rol = 'SOCIO'";

	    // Ejecutar la consulta con las fechas de inicio y fin como parámetros
	    return db.executeQueryArray(sql, fechaInicio, fechaFin, fechaInicio, fechaFin, fechaInicio, fechaFin, fechaInicio, fechaFin);
	}




	public void generarInformeArchivo(String fechaInicio, String fechaFin, String nombreArchivo) {
	    // Llamar al método que genera el informe
	    List<Object[]> informe = generarInformeSocios(fechaInicio, fechaFin);

	    // Creamos el archivo donde se guardará el informe
	    File archivo = new File(nombreArchivo);
	    
	    // Usamos BufferedWriter para escribir en el archivo
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {

	        // Escribimos la cabecera del informe
	        writer.write("Informe de Socios");
	        writer.newLine();
	        
	        // Añadir el periodo de tiempo al principio de la cabecera
	        writer.write("Periodo: " + fechaInicio + " - " + fechaFin); // El periodo de tiempo
	        writer.newLine();

	        writer.write("Fecha de Inicio: " + fechaInicio);
	        writer.newLine();
	        writer.write("Fecha de Fin: " + fechaFin);
	        writer.newLine();
	        writer.write("----------------------------------------------------------");
	        writer.newLine();
	        
	        // Agregar un espacio adicional antes de los datos de los socios
	        writer.newLine(); // Esta es la línea adicional de espacio
	        writer.newLine(); // Otra línea de espacio

	        // Escribimos los datos de cada socio
	        for (Object[] fila : informe) {
	            int idSocio = (Integer) fila[0];
	            String nombre = (String) fila[1];
	            int numReservas = (Integer) fila[2];
	            int numActividades = (Integer) fila[3];
	            String instalacionMasUsada = (String) fila[4];
	            int dineroGastado = (int) fila[5];  // Obtener el dinero gastado

	            writer.write("Socio ID: " + idSocio);
	            writer.newLine();
	            writer.write("Nombre: " + nombre);
	            writer.newLine();
	            writer.write("Número de Reservas: " + numReservas);
	            writer.newLine();
	            writer.write("Número de Actividades: " + numActividades);
	            writer.newLine();
	            writer.write("Instalación Más Usada: " + (instalacionMasUsada != null ? instalacionMasUsada : "No ha reservado instalaciones"));
	            writer.newLine();
	            writer.write("Dinero Gastado: " + dineroGastado + "€");
	            writer.newLine();  // Imprimir el dinero gastado
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




