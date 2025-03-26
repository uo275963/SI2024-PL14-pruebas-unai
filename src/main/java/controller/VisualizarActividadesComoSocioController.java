package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import giis.demo.util.SwingUtil;
import model.VisualizarActividadesComoSocioModel;
import view.VisualizarActividadesComoSocioView;

public class VisualizarActividadesComoSocioController {

    private VisualizarActividadesComoSocioModel model;
    private VisualizarActividadesComoSocioView view;
    private int idSocio = 1;

    public VisualizarActividadesComoSocioController(VisualizarActividadesComoSocioModel m,
            VisualizarActividadesComoSocioView v) {
        this.model = m;
        this.view = v;
    }

    private void initview() {
        // Crear un modelo de tabla vacío con las columnas "Actividad", "Instalación", "Fecha inicio actividad", etc.
        this.view.getFrame().setVisible(true);
    }

    public void initController() {

        // Crear un modelo de tabla con las nuevas columnas
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Actividad");
        modelo.addColumn("Instalación");
        modelo.addColumn("Fecha inicio actividad");
        modelo.addColumn("Fecha fin actividad");
        modelo.addColumn("Fecha inicio inscripción");
        modelo.addColumn("Fecha fin inscripción");
        modelo.addColumn("Hora inicio");
        modelo.addColumn("Hora fin");
        this.view.setTablaActividadesModel(modelo);

        // Actualizar el nombre del socio
        actualizarNombreSocio(idSocio);

        // Llenar el combo de instalaciones
        llenarComboBoxInstalaciones();

        // Mostrar la vista
        this.initview();

        // Acción para filtrar actividades
        this.view.getBtnFiltrar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> {
            // Llamar al método para actualizar las actividades del socio con el idSocio
            actualizarActividadesUsuario(idSocio);
        }));

        // Acción para cerrar la vista
        this.view.getBtnCerrar().addActionListener(e -> this.view.getFrame().setVisible(false));
    }

    private boolean esFechaValida(String fecha) {
        if (fecha == null || fecha.isEmpty())
            return true; // Permitir campos vacíos
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void actualizarNombreSocio(int idUsuario) {
        // Usar el método del modelo para obtener el nombre del socio
        String nombreSocio = this.model.getNombreUsuarioPorId(idUsuario);

        // Asignar el nombre del socio a la etiqueta en la vista
        if (nombreSocio != null) {
            this.view.getLblNombreSocio().setText("Socio: " + nombreSocio);
        } else {
            this.view.getLblNombreSocio().setText("Socio: No encontrado");
        }
    }

    private void llenarComboBoxInstalaciones() {
        // Obtener los nombres de las instalaciones desde el modelo
        List<Object[]> instalaciones = this.model.getNombreInstalaciones();

        // Limpiar el ComboBox antes de llenarlo
        this.view.getCbInstalaciones().removeAllItems();

        // Llenar el ComboBox con los nombres de las instalaciones
        if (instalaciones != null && !instalaciones.isEmpty()) {
            for (Object[] instalacion : instalaciones) {
                // Cada instalacion[0] contiene el nombre de la instalación
                String nombreInstalacion = (String) instalacion[0];
                this.view.getCbInstalaciones().addItem(nombreInstalacion);
            }
        } else {
            this.view.getCbInstalaciones().addItem("No hay instalaciones disponibles");
        }
    }

    public void actualizarActividadesUsuario(int usuarioId) {
        // Obtener la instalación seleccionada desde el JComboBox
        String instalacionSeleccionada = (String) this.view.getCbInstalaciones().getSelectedItem();
        String fechaInicio = this.view.getTFFechaInicio().getText();
        String fechaFin = this.view.getTFFechaFin().getText();

        // Validar formato de fechas
        if (!esFechaValida(fechaInicio) || !esFechaValida(fechaFin)) {
            JOptionPane.showMessageDialog(null, "Las fechas deben tener el formato YYYY-MM-DD.", "Error de formato",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que la fecha de inicio no sea posterior a la de fin
        if (isFechaInicioDespuesDeFechaFin(fechaInicio, fechaFin)) {
            JOptionPane.showMessageDialog(null, "La fecha de inicio no puede ser posterior a la fecha de fin.",
                    "Error de fechas", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener las actividades del usuario filtradas desde el modelo
        List<Object[]> actividades = this.model.getActividadesPorUsuarioId(usuarioId, instalacionSeleccionada,
                fechaInicio, fechaFin);

        // Obtener el modelo de la tabla
        DefaultTableModel modelo = (DefaultTableModel) this.view.getTablaActividades().getModel();

        // Limpiar la tabla antes de agregar nuevas filas
        modelo.setRowCount(0);

        // Verificamos si hay actividades
        if (actividades != null && !actividades.isEmpty()) {
            // Agregar las actividades a la tabla
            for (Object[] actividad : actividades) {
                try {
                    modelo.addRow(new Object[]{
                        actividad[0], // Actividad
                        actividad[1], // Instalación
                        actividad[2], // Fecha inicio actividad
                        actividad[3], // Fecha fin actividad
                        actividad[4], // Fecha inicio inscripción
                        actividad[5], // Fecha fin inscripción
                        actividad[6], // Hora inicio
                        actividad[7]  // Hora fin
                    });
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error: La actividad no tiene suficientes datos - " + Arrays.toString(actividad));
                    JOptionPane.showMessageDialog(null, "Error al cargar una actividad: Datos incompletos.", "Error de datos",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            // Si no hay actividades, mostrar un mensaje
            JOptionPane.showMessageDialog(null,
                    "No hay actividades registradas para este usuario con los filtros aplicados.", "Sin actividades",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Método para comprobar si la fecha de inicio es posterior a la fecha de fin
    private boolean isFechaInicioDespuesDeFechaFin(String fechaInicio, String fechaFin) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // Convertir las fechas a objetos Date
            java.util.Date fechaInicioDate = sdf.parse(fechaInicio);
            java.util.Date fechaFinDate = sdf.parse(fechaFin);

            // Comparar las fechas
            return fechaInicioDate.after(fechaFinDate); // Retorna true si fechaInicio es posterior a fechaFin
        } catch (ParseException e) {
            // Si hay un error al parsear las fechas, se asume que no son válidas
            return false;
        }
    }

}
