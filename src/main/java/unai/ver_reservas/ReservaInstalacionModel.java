package unai.ver_reservas;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import giis.demo.util.Database;
import unai.lista_actividades.PeriodoDTO;

public class ReservaInstalacionModel {
	
	private Database db=new Database();




	
    // Obtener las instalaciones
    public List<InstalacionDTO> getInstalaciones() {
        String sql = "SELECT * FROM INSTALACION WHERE estado = 'DISPONIBLE'";
        return db.executeQueryPojo(InstalacionDTO.class, sql);
    }

    // Obtener las reservas de una instalación para los próximos 30 días
    public List<ReservaInstalacionDTO> getReservasForNext30Days(int idInstalacion) {
        LocalDate today = LocalDate.now();
        LocalDate en30 = today.plusDays(30);
     // Define el formato
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Convierte las fechas a String con el formato deseado
        String todayFormatted = today.format(formatter);
        String en30Formatted = en30.format(formatter);
        
        
        
        String sql = "SELECT fecha, "
        		+ "  hora_inicio AS horaInicio, "
        		+ "  hora_fin AS horaFin, "
        		+ "  pagado AS pagado, "
        		+ "  u.nombre AS nombreUsuario "
        		+ " FROM RESERVA_INSTALACION ri JOIN USUARIO u ON ri.usuario_id = u.id WHERE instalacion_id = " + idInstalacion + " AND fecha BETWEEN "+  "\"" + todayFormatted + "\" "  + " AND " +  "\"" + en30Formatted + "\" "  ;
     // Convertir LocalDate a LocalDateTime
       // LocalDateTime localDateTime = today.atStartOfDay();

        // Convertir LocalDateTime a Date
       // Instant ins1 = today.atStartOfDay(ZoneId.systemDefault()).toInstant();
       // Instant ins2 = en30.atStartOfDay(ZoneId.systemDefault()).toInstant();

       // Date date1 = Date.from(ins1);
       // Date date2 = Date.from(ins2);


        
   //     System.out.print(sql);
     //   System.out.println();
      //  System.out.print(idInstalacion);
      // System.out.println();

        return db.executeQueryPojo(ReservaInstalacionDTO.class, sql);
    }

    // Obtener todas las reservas de una instalación para una fecha determinada
    public List<ReservaInstalacionDTO> getReservasForDate(int idInstalacion, LocalDate date) {
        String sql = "SELECT * FROM RESERVA_INSTALACION WHERE instalacion_id = ? AND fecha = ?";
        return db.executeQueryPojo(ReservaInstalacionDTO.class, sql, idInstalacion, date);
    }

}
