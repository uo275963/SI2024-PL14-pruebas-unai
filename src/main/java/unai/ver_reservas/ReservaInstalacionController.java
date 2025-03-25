package unai.ver_reservas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class ReservaInstalacionController {
    private ReservaInstalacionModel model;
    private ReservaInstalacionView view;

    public ReservaInstalacionController(ReservaInstalacionModel model, ReservaInstalacionView view) {
        this.model = model;
        this.view = view;
        initView();       // Inicializa la vista
        initController(); // Inicializa los eventos del controlador
    }

    // Inicializa la vista: carga el ComboBox y muestra la ventana
    public void initView() {
        cargarInstalacionesEnComboBox();
        view.getFrame().setVisible(true);
    }

    // Inicializa los eventos: por ejemplo, el ActionListener del ComboBox
    public void initController() {
        view.getCbInstalacion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedInstalacion = (String) view.getCbInstalacion().getSelectedItem();
                if (selectedInstalacion != null && !selectedInstalacion.isEmpty()) {
                    InstalacionDTO instalacion = obtenerInstalacionPorNombre(selectedInstalacion);
                    if (instalacion != null) {
                        // Obtiene las reservas para los próximos 30 días
                        List<ReservaInstalacionDTO> reservas = model.getReservasForNext30Days(instalacion.getId());
                        actualizarTablaHorario(reservas);
                    }
                }
            }
        });
    }
    
    


    // Carga las instalaciones en el ComboBox
    private void cargarInstalacionesEnComboBox() {
        List<InstalacionDTO> instalaciones = model.getInstalaciones();
        view.getCbInstalacion().removeAllItems();
        view.getCbInstalacion().addItem(""); // Opción vacía
        for (InstalacionDTO instalacion : instalaciones) {
            view.getCbInstalacion().addItem(instalacion.getNombre());
        }
    }

    // Obtiene la instalación según su nombre (se asume que los nombres son únicos)
    private InstalacionDTO obtenerInstalacionPorNombre(String nombreInstalacion) {
        List<InstalacionDTO> instalaciones = model.getInstalaciones();
        for (InstalacionDTO instalacion : instalaciones) {
            if (instalacion.getNombre().equals(nombreInstalacion)) {
                return instalacion;
            }
        }
        return null;
    }

    private void actualizarTablaHorario(List<ReservaInstalacionDTO> reservas) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Hora/Fecha");

        LocalDate today = LocalDate.now();
        for (int i = 0; i < 30; i++) {
            model.addColumn(today.plusDays(i));
        }

        // Crear las filas con los horarios (ejemplo: 9:00 - 21:00)
        for (int hora = 9; hora < 21; hora++) {
            String horaStr = String.format("%02d:00-%02d:00", hora, hora + 1);
            Object[] fila = new Object[31];  
            fila[0] = horaStr; 

            for (int i = 0; i < 30; i++) {
                LocalDate fecha = today.plusDays(i);
                String contenidoCelda = "Libre";

                for (ReservaInstalacionDTO reserva : reservas) {
                    LocalDate reservaFecha = LocalDate.parse(reserva.getFecha());
                    int horaInicioReserva = Integer.parseInt(reserva.getHoraInicio().split(":")[0]);
                    int horaFinReserva = Integer.parseInt(reserva.getHoraFin().split(":")[0]);

                    if (reservaFecha.equals(fecha) && hora >= horaInicioReserva && hora < horaFinReserva) {
                        if (reserva.getNombreUsuario() != null) {
                            contenidoCelda = "Reservado por " + reserva.getNombreUsuario();
                        }
                        if (reserva.getNombreActividad() != null) {
                            if (!contenidoCelda.equals("Libre")) {
                                contenidoCelda += " / ";
                            }
                            contenidoCelda += "Actividad: " + reserva.getNombreActividad();
                        }
                    }
                }

                fila[i + 1] = contenidoCelda;
            }

            model.addRow(fila);
        }

        view.getTabHorario().setModel(model);

        // Ajuste del ancho de columnas
        for (int column = 0; column < view.getTabHorario().getColumnCount(); column++) {
            int width = 0;
            for (int row = 0; row < view.getTabHorario().getRowCount(); row++) {
                TableCellRenderer renderer = view.getTabHorario().getCellRenderer(row, column);
                Component comp = view.getTabHorario().prepareRenderer(renderer, row, column);
                width = Math.max(width, comp.getPreferredSize().width);
            }

            TableColumn tableColumn = view.getTabHorario().getColumnModel().getColumn(column);
            int headerWidth = view.getTabHorario().getTableHeader()
                .getDefaultRenderer().getTableCellRendererComponent(view.getTabHorario(),
                    tableColumn.getHeaderValue(), false, false, -1, column).getPreferredSize().width;
            tableColumn.setPreferredWidth(Math.max(width, headerWidth) + 10);
        }

        view.getTabHorario().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Aplicar el renderizador para cambiar el color de las celdas ocupadas
        for (int i = 1; i < model.getColumnCount(); i++) {
            view.getTabHorario().getColumnModel().getColumn(i).setCellRenderer(new CustomTableCellRenderer());
        }
        


        
    }


}
