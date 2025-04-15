package diego_CancelarReserva;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import giis.demo.util.SwingUtil;

public class CancelarController {
    private CancelarModel model;
    private CancelarView view;

    private int usuarioIdLogeado; // Guarda el ID del usuario tras login

    public CancelarController(CancelarModel model, CancelarView view) {
        this.model = model;
        this.view = view;
        initView();
    }

    public void initView() {
        view.mostrarLogin();
        view.getFrame().setVisible(true);
    }

    public void initController() {
        view.getBtnLogin().addActionListener(e -> SwingUtil.exceptionWrapper(() -> login()));
        view.getBtnCancelar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> cancelarReserva()));
    }

    private void login() {
        String nombreUsuario = view.getUsuarioField().getText();
        String contrasena = new String(view.getPasswordField().getPassword());

        if (nombreUsuario.isEmpty() || contrasena.isEmpty()) {
            view.mostrarError("Por favor, complete todos los campos.");
            return;
        }

        try {
            usuarioIdLogeado = model.obtenerIdUsuario(nombreUsuario, contrasena);

            if (usuarioIdLogeado == -1) {
                view.mostrarError("Credenciales incorrectas.");
                return;
            }

            cargarReservasUsuario(usuarioIdLogeado);
            view.mostrarCancelar();
        } catch (Exception e) {
            view.mostrarError("Error durante el login: " + e.getMessage());
        }
    }

    private void cargarReservasUsuario(int usuarioId) {
        List<CancelarDTO> reservas = model.obtenerReservasUsuario(usuarioId);
        DefaultTableModel tableModel = new DefaultTableModel(
                new String[]{"Nombre Instalacion", "Fecha", "Hora"}, 0);

        for (CancelarDTO dto : reservas) {
            tableModel.addRow(new Object[]{
                    dto.getNombre_instalacion(),
                    dto.getFecha(),
                    dto.getHora(),
                
            });
        }

        view.getTablaReservas().setModel(tableModel);
    }

    private void cancelarReserva() {
        int filaSeleccionada = view.getTablaReservas().getSelectedRow();
        if (filaSeleccionada == -1) {
            view.mostrarError("Seleccione una reserva para cancelar.");
            return;
        }

        try {
            List<CancelarDTO> reservas = model.obtenerReservasUsuario(usuarioIdLogeado);
            CancelarDTO reserva = reservas.get(filaSeleccionada);

            // Obtener fecha y hora de la reserva como LocalDateTime para verificar restricción
            LocalDate fechaReserva = LocalDate.parse(reserva.getFecha()); // Asegúrate de que el formato sea YYYY-MM-DD

            LocalDate fechaHoy = LocalDate.now();

            // Si la reserva es para hoy o ya pasó, no se puede cancelar
            if (!fechaHoy.isBefore(fechaReserva)) {
                view.mostrarError("No se puede cancelar una reserva el mismo día o pasada.");
                return;
            }

            // Llamar al modelo para cancelar
            model.cancelarReserva(usuarioIdLogeado, reserva.getNombre_instalacion(), reserva.getFecha(), reserva.getHora());

            view.mostrarMensaje("Reserva cancelada exitosamente.");
            cargarReservasUsuario(usuarioIdLogeado); // Refrescar la tabla
        } catch (Exception e) {
            view.mostrarError("Error al cancelar la reserva: " + e.getMessage());
        }
    }

}
