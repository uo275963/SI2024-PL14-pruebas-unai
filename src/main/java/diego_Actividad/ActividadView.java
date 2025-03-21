package diego_Actividad;

import java.awt.*;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;

public class ActividadView {
    private JFrame frame;
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtAforoMaximo;
    private JTextField txtCosteSocio;
    private JTextField txtCosteNoSocio;
    private JDateChooser dateFechaInicio;
    private JDateChooser dateFechaFin;
    private JTextField txtDias;
    private JTextField txtHoraInicio;
    private JTextField txtHoraFin;
    private JComboBox<Object> listaInstalaciones;
    private JComboBox<Object> listaPeriodosInscripcion;
    private JComboBox<Object> listaActividades;
    private JButton btnGuardar;
    private JTable tablaActividades;
    private JTable detalleActividad;

    public ActividadView() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Gestión de Actividades");
        frame.setBounds(100, 100, 600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel de formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(7, 2, 5, 5));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        panelFormulario.add(txtDescripcion);

        panelFormulario.add(new JLabel("Instalación:"));
        listaInstalaciones = new JComboBox<>();
        panelFormulario.add(listaInstalaciones);

        panelFormulario.add(new JLabel("Aforo Máximo:"));
        txtAforoMaximo = new JTextField();
        panelFormulario.add(txtAforoMaximo);

        panelFormulario.add(new JLabel("Coste Socio:"));
        txtCosteSocio = new JTextField();
        panelFormulario.add(txtCosteSocio);

        panelFormulario.add(new JLabel("Coste No Socio:"));
        txtCosteNoSocio = new JTextField();
        panelFormulario.add(txtCosteNoSocio);

        panelFormulario.add(new JLabel("Fecha Inicio:"));
        dateFechaInicio = new JDateChooser();
        panelFormulario.add(dateFechaInicio);

        panelFormulario.add(new JLabel("Fecha Fin:"));
        dateFechaFin = new JDateChooser();
        panelFormulario.add(dateFechaFin);

        panelFormulario.add(new JLabel("Días:"));
        txtDias = new JTextField();
        panelFormulario.add(txtDias);

        panelFormulario.add(new JLabel("Hora Inicio:"));
        txtHoraInicio = new JTextField();
        panelFormulario.add(txtHoraInicio);

        panelFormulario.add(new JLabel("Hora Fin:"));
        txtHoraFin = new JTextField();
        panelFormulario.add(txtHoraFin);

        panelFormulario.add(new JLabel("Periodo Inscripción:"));
        listaPeriodosInscripcion = new JComboBox<>();
        panelFormulario.add(listaPeriodosInscripcion);

        btnGuardar = new JButton("Guardar Actividad");
        panelFormulario.add(btnGuardar);

        frame.add(panelFormulario, BorderLayout.NORTH);

        // Tabla para mostrar actividades
        tablaActividades = new JTable();
        JScrollPane scrollTabla = new JScrollPane(tablaActividades);
        frame.add(scrollTabla, BorderLayout.CENTER);

        // Panel de detalles de actividad
        JPanel panelDetalles = new JPanel(new BorderLayout());
        detalleActividad = new JTable();
        panelDetalles.add(new JLabel("Detalles de la Actividad"), BorderLayout.NORTH);
        panelDetalles.add(new JScrollPane(detalleActividad), BorderLayout.CENTER);

        frame.add(panelDetalles, BorderLayout.SOUTH);
    }

    // Métodos para acceder a los componentes desde el controlador
    public JFrame getFrame() {
        return frame;
    }

    public JTextField getNombreField() {
        return txtNombre;
    }

    public JTextField getDescripcionField() {
        return txtDescripcion;
    }

    public JComboBox<Object> getListaInstalaciones() {
        return listaInstalaciones;
    }

    public JTextField getAforoMaximoField() {
        return txtAforoMaximo;
    }

    public JTextField getCosteSocioField() {
        return txtCosteSocio;
    }

    public JTextField getCosteNoSocioField() {
        return txtCosteNoSocio;
    }

    public JDateChooser getFechaInicioChooser() {
        return dateFechaInicio;
    }

    public JDateChooser getFechaFinChooser() {
        return dateFechaFin;
    }

    public JTextField getDiasField() {
        return txtDias;
    }

    public JTextField getHoraInicioField() {
        return txtHoraInicio;
    }

    public JTextField getHoraFinField() {
        return txtHoraFin;
    }

    public JComboBox<Object> getListaPeriodosInscripcion() {
        return listaPeriodosInscripcion;
    }
    
    public JComboBox<Object> getListaActividades() {
        return listaActividades;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JTable getTablaActividades() {
        return tablaActividades;
    }

    public JTable getDetalleActividad() {
        return detalleActividad;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

