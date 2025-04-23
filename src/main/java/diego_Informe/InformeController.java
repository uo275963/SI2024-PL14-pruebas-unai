package diego_Informe;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class InformeController {
    private InformeModel model;
    private InformeView view;

    public InformeController(InformeModel model, InformeView view) {
        this.model = model;
        this.view = view;
        initView();
        initController();
    }

    public void initView() {
        view.getFrame().setVisible(true);
    }

    public void initController() {
        view.getBtnBuscar().addActionListener(e -> cargarInforme());
        view.getBtnGenerarInforme().addActionListener(e -> generarInforme());
    }

    private void cargarInforme() {
        String fechaInicio = view.getTxtFechaInicio().getText();
        String fechaFin = view.getTxtFechaFin().getText();

        if (fechaInicio.isEmpty() || fechaFin.isEmpty()) {
            view.mostrarError("Debe introducir ambas fechas.");
            return;
        }

        List<InformeDTO> informes = model.obtenerInformesPorPeriodo(fechaInicio, fechaFin);

        DefaultTableModel dtm = (DefaultTableModel) view.getTablaInforme().getModel();
        dtm.setRowCount(0);

        for (InformeDTO dto : informes) {
            Object[] fila = new Object[]{
                dto.getNombreActividad(),
                dto.getNumeroEdicion(),
                dto.getNumeroInscritos(),
                dto.getNumeroSinPlaza(),
                String.format("%.2f", dto.getPorcentajeSocios()),
                String.format("%.2f", dto.getPorcentajeNoSocios())
            };
            dtm.addRow(fila);
        }
    }

    private void generarInforme() {
        String fechaInicio = view.getTxtFechaInicio().getText();
        String fechaFin = view.getTxtFechaFin().getText();

        if (fechaInicio.isEmpty() || fechaFin.isEmpty()) {
            view.mostrarError("Debe introducir ambas fechas para generar el informe.");
            return;
        }

        List<InformeDTO> informes = model.obtenerInformesPorPeriodo(fechaInicio, fechaFin);

        if (informes.isEmpty()) {
            view.mostrarMensaje("No hay datos para generar el informe.");
            return;
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("informe.txt"))) {
            writer.write("INFORME DE ACTIVIDADES\n");
            writer.write("Periodo: " + fechaInicio + " - " + fechaFin + "\n\n");

            for (InformeDTO dto : informes) {
                writer.write("Actividad: " + dto.getNombreActividad() + "\n");
                writer.write("Número de edición: " + dto.getNumeroEdicion() + "\n");
                writer.write("Inscritos: " + dto.getNumeroInscritos() + "\n");
                writer.write("Sin plaza: " + dto.getNumeroSinPlaza() + "\n");
                writer.write("Socios (%): " + String.format("%.2f", dto.getPorcentajeSocios()) + "\n");
                writer.write("No socios (%): " + String.format("%.2f", dto.getPorcentajeNoSocios()) + "\n");
                writer.write("-------------------------------\n");
            }

            view.mostrarMensaje("Informe generado exitosamente como 'informe.txt'.");
        } catch (IOException e) {
            view.mostrarError("Error al generar el informe: " + e.getMessage());
        }
    }
}
