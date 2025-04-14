package diego_Informe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import giis.demo.util.SwingUtil;

public class InformeController {
    private InformeModel model;
    private InformeView view;

    public InformeController(InformeModel model, InformeView view) {
        this.model = model;
        this.view = view;
        initView();
        initController();
    }

    private void initView() {
        // Se carga inicialmente una tabla vacía
        cargarTablaInforme();
        view.getFrame().setVisible(true);
    }

    private void initController() {
        view.getBtnGenerarInforme().addActionListener(e -> SwingUtil.exceptionWrapper(() -> generarInforme()));
    }

    /**
     * Al pulsar el botón, se obtiene el periodo seleccionado y se solicita al modelo la lista de 
     * informe correspondiente a ese periodo, para posteriormente actualizar la tabla.
     */
    private void generarInforme() {
        try {
            // Recuperar el periodo seleccionado en el combo box
            String periodo = (String) view.getComboPeriodo().getSelectedItem();
            
            // Obtener la lista de InformeDTO para ese periodo (este método debe implementarse en el modelo)
            List<InformeDTO> informes = model.getInformePorPeriodo(periodo);
            
            // Actualizar la tabla de la vista con la información recuperada
            cargarTablaInforme(informes);
        } catch (Exception ex) {
            view.mostrarError("Error generando informe: " + ex.getMessage());
        }
    }

    /**
     * Actualiza la tabla del informe utilizando la lista de InformeDTO proporcionada
     * @param informes Lista de objetos InformeDTO que contienen la información del informe.
     */
    private void cargarTablaInforme(List<InformeDTO> informes) {
        // Definir las columnas de la tabla
        DefaultTableModel tableModel = new DefaultTableModel(
                new String[] { "Actividad", "N° Edición", "Inscripciones", "Socios", "No Socios" }, 0);

        // Recorrer la lista y añadir una fila por cada InformeDTO
        for (InformeDTO dto : informes) {
            tableModel.addRow(new Object[] {
                dto.getNombreActividad(),
                dto.getNumeroEdicion(),
                dto.getNumeroInscripciones(),
                dto.getCantidadSocios(),
                dto.getCantidadNoSocios()
            });
        }
        view.getTablaInforme().setModel(tableModel);
    }

    /**
     * Inicializa la tabla en blanco (sin datos) para que la vista se inicie sin registros.
     */
    private void cargarTablaInforme() {
        DefaultTableModel tableModel = new DefaultTableModel(
                new String[] { "Actividad", "N° Edición", "Inscripciones", "Socios", "No Socios" }, 0);
        view.getTablaInforme().setModel(tableModel);
    }
}
