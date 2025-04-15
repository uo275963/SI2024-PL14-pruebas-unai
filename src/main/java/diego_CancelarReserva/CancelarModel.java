package diego_CancelarReserva;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import giis.demo.util.Database;

public class CancelarModel {

    private Database db = new Database();

    // Verifica usuario y contraseña, devuelve el ID del usuario si es correcto, -1 si no existe
    public int obtenerIdUsuario(String nombre, String password) {
        String sql = "SELECT id FROM USUARIO WHERE nombre=? AND password=?";
        List<Object[]> rows = db.executeQueryArray(sql, nombre, password);

        if (rows.isEmpty()) {
            return -1;
        }

        return ((Number) rows.get(0)[0]).intValue();
    }
    
 // Método que obtiene las reservas del usuario logeado
    public List<CancelarDTO> obtenerReservasUsuario(int usuarioId) {
        List<CancelarDTO> reservas = new ArrayList<>();
        
        String sql = "SELECT i.nombre, r.fecha, r.hora_inicio " +
                     "FROM RESERVA_INSTALACION r " +
                     "JOIN INSTALACION i ON r.instalacion_id = i.id " +
                     "WHERE r.usuario_id = ?";

        List<Object[]> rows = db.executeQueryArray(sql, usuarioId);
        
        for (Object[] row : rows) {
            String nombreInstalacion = (String) row[0];
            String fecha = (String) row[1];
            String horaInicio = (String) row[2];
            
            // Crear un objeto DTO para la reserva
            CancelarDTO reserva = new CancelarDTO(nombreInstalacion, fecha, horaInicio);
            reservas.add(reserva);
        }
        
        return reservas;
    }

    // Método para cancelar una reserva, verificando el tiempo de antelación
    public boolean cancelarReserva(int usuarioId, String nombreInstalacion, String fecha, String hora) {
        // Convertir la fecha y hora a un LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String fechaHora = fecha + " " + hora; // La hora ya está en el formato adecuado
        LocalDateTime fechaReserva = LocalDateTime.parse(fechaHora, formatter);

        // Obtener la fecha y hora actual
        LocalDateTime fechaActual = LocalDateTime.now();

        // Verificar que haya al menos 24 horas de diferencia
        Duration duracion = Duration.between(fechaActual, fechaReserva);
        if (duracion.toHours() < 24) {
            return false; // No se puede cancelar si no hay 24 horas de antelación
        }
        // Verificar si el usuario ya ha pagado la reserva
        String sqlPago = "SELECT pagado, instalacion_id, fecha, hora_inicio FROM RESERVA_INSTALACION " +
                         "WHERE usuario_id = ? AND instalacion_id = (SELECT id FROM INSTALACION WHERE nombre = ?) " +
                         "AND fecha = ? AND hora_inicio = ?";

        List<Object[]> pagoResult = db.executeQueryArray(sqlPago, usuarioId, nombreInstalacion, fecha, hora);

        if (pagoResult.isEmpty()) {
            return false; // Si no existe la reserva, no se puede cancelar
        }

        // Extraer la información sobre el pago
        boolean pagado = (Boolean) pagoResult.get(0)[0];
        int instalacionId = (Integer) pagoResult.get(0)[1];
        String fechaReservaP = (String) pagoResult.get(0)[2];
        String horaReserva = (String) pagoResult.get(0)[3];

        if (pagado) {
            // Si el usuario ya ha pagado, reembolsar el importe en el siguiente recibo mensual
            String sqlImporte = "SELECT precio_hora FROM INSTALACION WHERE id = ?";
            List<Object[]> precioResult = db.executeQueryArray(sqlImporte, instalacionId);
            if (!precioResult.isEmpty()) {
                double precioHora = (Double) precioResult.get(0)[0];

                // Insertar un nuevo recibo con el importe de la reserva
                String sqlRecibo = "INSERT INTO PAGO (usuario_id, monto, concepto, fecha_pago) VALUES (?, ?, ?, NOW())";
                db.executeUpdate(sqlRecibo, usuarioId, precioHora, "Reembolso por cancelación de reserva");
            }
        } else {
            // Si el usuario no ha pagado, eliminar el pago pendiente
            String sqlEliminarPago = "DELETE FROM PAGO WHERE usuario_id = ? AND concepto = ?";
            db.executeUpdate(sqlEliminarPago, usuarioId);
        }

        // Subconsulta para obtener el id de la instalación basada en el nombre
        String sql = "DELETE FROM RESERVA_INSTALACION " +
                     "WHERE usuario_id = ? " +
                     "AND instalacion_id = (SELECT id FROM INSTALACION WHERE nombre = ?) " +
                     "AND fecha = ? " +
                     "AND hora_inicio = ?";
        
        db.executeUpdate(sql, usuarioId, nombreInstalacion, fecha, hora);
        return true;

        
    }
}
    
