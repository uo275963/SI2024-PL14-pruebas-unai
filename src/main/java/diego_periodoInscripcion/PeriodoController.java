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
            Date fechaInicio = view.getFechaInicioChooser().getDate();
            Date fechaFin = view.getFechaFinChooser().getDate();
            Date fechaFinNoSocios = view.getFechaFinNoSociosChooser().getDate();

            if (nombre.isEmpty() || fechaInicio == null || fechaFin == null || fechaFinNoSocios == null) {
                throw new ApplicationException("Todos los campos deben estar completos.");
            }

            model.guardarPeriodo(nombre, fechaInicio, fechaFin, fechaFinNoSocios);
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

        // Definir el formato de las fechas
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Crear un modelo de tabla con los datos formateados
        String[] columnNames = {"id", "nombre", "fechaInicio", "fechaFin", "fechaFinNoSocios"};
        Object[][] data = new Object[periodos.size()][columnNames.length];

        for (int i = 0; i < periodos.size(); i++) {
            PeriodoDisplayDTO periodo = periodos.get(i);
            data[i][0] = periodo.getId();  // ID
            data[i][1] = periodo.getNombre();  // Nombre
            data[i][2] = periodo.getFechaInicio() != null ? dateFormat.format(periodo.getFechaInicio()) : "";
            data[i][3] = periodo.getFechaFin() != null ? dateFormat.format(periodo.getFechaFin()) : "";
            data[i][4] = periodo.getFechaFinNoSocios() != null ? dateFormat.format(periodo.getFechaFinNoSocios()) : "";
        }

        // Crear el modelo de tabla con los datos formateados
        TableModel tmodel = new DefaultTableModel(data, columnNames);
        view.getTablaPeriodos().setModel(tmodel);
        SwingUtil.autoAdjustColumns(view.getTablaPeriodos());
    }




}
