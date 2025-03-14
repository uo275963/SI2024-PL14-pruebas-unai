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
}
