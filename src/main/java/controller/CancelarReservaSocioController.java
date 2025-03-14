package controller;

import java.util.List;

import javax.swing.JOptionPane;

import giis.demo.util.SwingUtil;
import model.CancelarReservaSocioModel;
import view.CancelarReservaSocioView;

public class CancelarReservaSocioController {
	private CancelarReservaSocioModel model;
	private CancelarReservaSocioView view;
	
	public CancelarReservaSocioController(CancelarReservaSocioModel m, CancelarReservaSocioView v){
		this.model=m;
		this.view=v;
		initView();
	}
	
	public void initView() {
		view.getFrame().setVisible(true);
	}
	
	public void initController() {
		view.getbMostrar().addActionListener(e-> SwingUtil.exceptionWrapper(() -> {
			mostrarReservas();
		}));
		
		view.getBEliminar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> {
			eliminarReserva();
		}));
	}

	private void mostrarReservas() {
        String fecha = view.getTFFecha().getText();
        
        // Llamar al método del modelo para obtener las reservas
        List<Object[]> reservas = model.obtenerReservas(fecha);
        
        // Validar que la fecha esté en formato YYYY-MM-DD directamente en la condición del if
        if (!fecha.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            JOptionPane.showMessageDialog(view.getFrame(), "La fecha debe tener el formato YYYY-MM-DD", "Error de formato", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Si no se encuentran reservas, puedes manejar eso
        // Si no se encuentran reservas, mostramos un mensaje y vaciamos la tabla
        if (reservas.isEmpty()) {
            JOptionPane.showMessageDialog(view.getFrame(), "No hay reservas", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            vaciarTabla();
            return;
        }

        // Mostrar las reservas en la tabla
        String[] columnas = {"ID Reserva", "Instalación", "Fecha", "Hora Inicio", "Hora Fin", "Pagado","Usuario"};
        Object[][] datos = new Object[reservas.size()][columnas.length];

        // Llenar los datos de la tabla con la información obtenida
        for (int i = 0; i < reservas.size(); i++) {
            Object[] reserva = reservas.get(i);
            for (int j = 0; j < columnas.length; j++) {
                datos[i][j] = reserva[j];
            }
        }

        // Establecer el modelo de la tabla con los datos obtenidos
        view.getTReservas().setModel(new javax.swing.table.DefaultTableModel(datos, columnas));
    }
	
	// Método para vaciar la tabla
    private void vaciarTabla() {
        // Limpiar el modelo de la tabla
        view.getTReservas().setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[]{"ID Reserva", "Instalación", "Fecha", "Hora Inicio", "Hora Fin", "Pagado"}));
    }
    
    private void eliminarReserva() {
    	String fecha = view.getTFFecha().getText();
        String horaInicio = view.getTFHoraInicio().getText();
        String horaFin = view.getTFHoraFin().getText();
        String instalacion = view.getTFInstalacion().getText();
        String motivo = view.gettAMotivo().getText();

        // 1. Comprobar si las horas están en formato HH:00
        if (!horaInicio.matches("^\\d{2}:00$") || !horaFin.matches("^\\d{2}:00$")) {
            JOptionPane.showMessageDialog(view.getFrame(), "Las horas deben estar en el formato HH:00", "Error de formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Comprobar si la instalación existe en la base de datos
        if (!model.instalacionExiste(instalacion)) {
            JOptionPane.showMessageDialog(view.getFrame(), "La instalación no existe en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Comprobar si el campo motivo no está vacío
        if (motivo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view.getFrame(), "El campo motivo no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // 4. Comprobar si hay una reserva en la fecha indicada
        List<Object[]> reservas = model.obtenerReservas(fecha); // Obtener reservas para la fecha
        boolean reservaExistente = false;

        // Buscar si hay alguna reserva con la misma hora de inicio y fin, y la misma instalación
        for (Object[] reserva : reservas) {
            String reservaHoraInicio = reserva[3].toString();  // Suponiendo que la hora de inicio es la columna 3
            String reservaHoraFin = reserva[4].toString();     // Suponiendo que la hora de fin es la columna 4
            String reservaInstalacion = reserva[1].toString(); // Suponiendo que la instalación es la columna 1

            if (reservaHoraInicio.equals(horaInicio) && reservaHoraFin.equals(horaFin) && reservaInstalacion.equals(instalacion)) {
                reservaExistente = true;
                break;
            }
        }

        if (!reservaExistente) {
            JOptionPane.showMessageDialog(view.getFrame(), "No hay una reserva con la fecha, hora y instalación especificadas.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        // Si todas las validaciones son correctas, procedemos con la eliminación
        // Suponiendo que tienes un método en el modelo para eliminar la reserva
        boolean eliminada = model.eliminarReserva(horaInicio, horaFin, instalacion, motivo);

        if (eliminada) {
            JOptionPane.showMessageDialog(view.getFrame(), "Reserva eliminada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // Aquí podrías refrescar la lista de reservas o vaciar la tabla si lo deseas
            vaciarTabla();
        } else {
            JOptionPane.showMessageDialog(view.getFrame(), "Hubo un error al eliminar la reserva", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
