package diego_periodoInscripcion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import giis.demo.util.ApplicationException;
import giis.demo.util.SwingUtil;
import giis.demo.util.Util;

public class PeriodoController {
    private PeriodoModel model;
    private PeriodoView view;

    public PeriodoController(PeriodoModel model, PeriodoView view) {
        this.model = model;
        this.view = view;
        this.initView();
    }

    public void initController() {
        view.getBtnGuardar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> guardarPeriodo()));
    }

    public void initView() {
        getListaPeriodos();
        view.getFrame().setVisible(true);
    }

    /**
     * Guarda un nuevo período en la base de datos.
     */
    public void guardarPeriodo() {
        try {
            String nombre = view.getNombreField().getText();
            String fecha_inicio_socios = Util.dateToIsoString(view.getFechaInicioChooser().getDate());
            String fecha_fin_socios = Util.dateToIsoString(view.getFechaFinChooser().getDate());
            String fecha_fin_no_socios = Util.dateToIsoString(view.getFechaFinNoSociosChooser().getDate());

            if (nombre.isEmpty() || fecha_inicio_socios == null || fecha_fin_socios == null || fecha_fin_no_socios == null) {
                throw new ApplicationException("Todos los campos deben estar completos.");
            }

            model.guardarPeriodo(nombre, fecha_inicio_socios, fecha_fin_socios, fecha_fin_no_socios);
            view.mostrarMensaje("Período guardado correctamente.");
            getListaPeriodos();
        } catch (ApplicationException ex) {
            view.mostrarError(ex.getMessage());
        }
    }

    /**
     * Obtiene la lista de períodos desde el modelo y la muestra en la vista.
     */
    public void getListaPeriodos() {
        List<PeriodoDisplayDTO> periodos = model.getListaPeriodos();

        // Crear un modelo de tabla vacío con las cabeceras
        DefaultTableModel tmodel = new DefaultTableModel(
                new String[] {"id", "nombre", "fecha_inicio_socios", "fecha_fin_socios", "fecha_fin_no_socios" },
                0);

        // Formateador de fechas
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Iterar sobre los períodos y agregar filas al modelo de la tabla
        for (PeriodoDisplayDTO periodo : periodos) {
            Object[] row = new Object[5];
            
            row[0] = periodo.getId();
            row[1] = periodo.getNombre(); // nombre
            row[2] = periodo.getFecha_inicio_socios();
            row[3] = periodo.getFecha_fin_socios();
            row[4] = periodo.getFecha_fin_no_socios();

            // Agregar la fila al modelo de la tabla
            tmodel.addRow(row);
        }

        // Establecer el modelo de la tabla en la vista
        view.getTablaPeriodos().setModel(tmodel);

        // Ajustar las columnas automáticamente
        SwingUtil.autoAdjustColumns(view.getTablaPeriodos());
    }





}
