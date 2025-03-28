package diego_InscripcionSocios;

import javax.swing.*;
import java.awt.*;
import com.toedter.calendar.JDateChooser;

public class InscripcionView {
    private JFrame frame;
    private JComboBox<Object> listaPeriodosInscripcion; // ComboBox para seleccionar el período
    private JComboBox<ActividadComboBoxItem> listaActividades; // ComboBox para seleccionar la actividad
    private JTextField UsuarioId; // Campo para ingresar el ID del usuario (puede ser autogenerado en otro escenario)
    private JButton btnInscribirse; // Botón para realizar la inscripción
    private JTable tablaInscripciones; // Tabla para mostrar inscripciones previas

    public InscripcionView() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Inscripción a Actividad");
        frame.setBounds(100, 100, 750, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // Panel de encabezado con mensaje
        JPanel panelEncabezado = new JPanel();
        JLabel lblEncabezado = new JLabel("Seleccione un período y una actividad para inscribirse");
        lblEncabezado.setFont(new Font("Arial", Font.BOLD, 14));
        panelEncabezado.add(lblEncabezado);
        frame.getContentPane().add(panelEncabezado, BorderLayout.NORTH);

        // Panel de formulario para la inscripción
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(4, 2, 10, 20)); // Mejor organización con más espacio

        // Usuario ID (Este campo puede no ser necesario si lo gestionas desde el sistema de sesiones)
        panelFormulario.add(new JLabel("ID de Usuario:"));
        UsuarioId = new JTextField();
        panelFormulario.add(UsuarioId);

        // Selección de período
        panelFormulario.add(new JLabel("Periodo de Inscripción:"));
        listaPeriodosInscripcion = new JComboBox<>();
        panelFormulario.add(listaPeriodosInscripcion);

        // Selección de actividad
        panelFormulario.add(new JLabel("Actividad:"));
        listaActividades = new JComboBox<>();
        panelFormulario.add(listaActividades);

        // Botón para inscribirse
        btnInscribirse = new JButton("Inscribirse");
        panelFormulario.add(btnInscribirse);

        frame.getContentPane().add(panelFormulario, BorderLayout.CENTER);

        // Tabla para mostrar inscripciones previas
        tablaInscripciones = new JTable();
        JScrollPane scrollTabla = new JScrollPane(tablaInscripciones);
        frame.getContentPane().add(scrollTabla, BorderLayout.SOUTH);
    }

    // Métodos para acceder a los componentes desde el controlador
    public JFrame getFrame() {
        return frame;
    }

    public JComboBox<Object> getListaPeriodosInscripcion() {
        return listaPeriodosInscripcion;
    }

    public JComboBox<ActividadComboBoxItem> getListaActividades() {
        return listaActividades;
    }

    public JTextField getUsuarioId() {
        return UsuarioId;
    }

    public JButton getBtnInscribirse() {
        return btnInscribirse;
    }

    public JTable getTablaInscripciones() {
        return tablaInscripciones;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
