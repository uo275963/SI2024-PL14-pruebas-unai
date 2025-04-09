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
                new String[]{"Nombre Usuario", "Nombre Actividad", "Instalación"}, 0);

        for (CancelarDTO dto : reservas) {
            tableModel.addRow(new Object[]{
                    dto.getNombre_usuario(),
                    dto.getNombre_actividad(),
                    dto.getNombre_instalacion()
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

            // Verificar restricción: mínimo 1 día de antelación
            LocalDate fechaHoy = LocalDate.now();
            LocalDate fechaActividad = model.obtenerFechaActividad(reserva.getActividad_id());

            if (fechaHoy.plusDays(1).isAfter(fechaActividad)) {
                view.mostrarError("Solo puede cancelar con al menos 1 día de antelación.");
                return;
            }

            model.cancelarReserva(usuarioIdLogeado, reserva.getActividad_id());
            view.mostrarMensaje("Reserva cancelada exitosamente.");
            cargarReservasUsuario(usuarioIdLogeado); // Refrescar tabla

        } catch (Exception e) {
            view.mostrarError("Error al cancelar la reserva: " + e.getMessage());
        }
    }
}
