package diego_periodoInscripcion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private String lastSelectedKey = ""; // Guarda la última clave seleccionada

    public PeriodoController(PeriodoModel model, PeriodoView view) {
        this.model = model;
        this.view = view;
        this.initView();
    }

    public void initController() {
        view.getBtnGuardar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> guardarPeriodo()));
        view.getTablaPeriodos().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                SwingUtil.exceptionWrapper(() -> updateDetail());
            }
        });
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
            String fechaInicio = Util.dateToIsoString(view.getFechaInicioChooser().getDate());
            String fechaFin = Util.dateToIsoString(view.getFechaFinChooser().getDate());
            String fechaFinNoSocios = Util.dateToIsoString(view.getFechaFinNoSociosChooser().getDate());

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
        TableModel tmodel = SwingUtil.getTableModelFromPojos(periodos, new String[]{"id", "nombre", "descripcion", "fechaInicio", "fechaFin", "fechaFinNoSocios"});
        view.getTablaPeriodos().setModel(tmodel);
        SwingUtil.autoAdjustColumns(view.getTablaPeriodos());

        restoreDetail();

        List<Object[]> periodosList = model.getListaPeriodosArray();
        ComboBoxModel<Object> lmodel = SwingUtil.getComboModelFromList(periodosList);
        view.getListaPeriodos().setModel(lmodel);
    }

    /**
     * Restaura la selección de detalles en la tabla.
     */
    public void restoreDetail() {
        this.lastSelectedKey = SwingUtil.selectAndGetSelectedKey(view.getTablaPeriodos(), this.lastSelectedKey);
        if ("".equals(this.lastSelectedKey)) {
            view.getDetallePeriodo().setModel(new DefaultTableModel());
        } else {
            this.updateDetail();
        }
    }

    /**
     * Actualiza la vista con los detalles del período seleccionado.
     */
    public void updateDetail() {
        this.lastSelectedKey = SwingUtil.getSelectedKey(view.getTablaPeriodos());
        int idPeriodo = Integer.parseInt(this.lastSelectedKey);

        PeriodoEntity periodo = model.getPeriodo(idPeriodo);
        TableModel tmodel = SwingUtil.getRecordModelFromPojo(periodo, new String[]{"id", "nombre", "descripcion", "fechaInicio", "fechaFin", "fechaFinNoSocios"});
        view.getDetallePeriodo().setModel(tmodel);
        SwingUtil.autoAdjustColumns(view.getDetallePeriodo());
    }
}
