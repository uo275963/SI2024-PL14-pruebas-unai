package diego_Informe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InformeView {
    // Componentes principales
    private JFrame frame;
    private JTextField txtFechaInicio;
    private JTextField txtFechaFin;
    private JButton btnBuscar;
    private JTable tablaInforme;
    private JButton btnGenerarInforme;

    public InformeView() {
        initialize();
    }

    private void initialize() {
        // Configuración básica del frame
        frame = new JFrame("Informe de Actividades");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // --- Panel Superior: Selección de Fechas y Botón Buscar ---
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel lblFechaInicio = new JLabel("Fecha Inicio (yyyy-MM-dd):");
        txtFechaInicio = new JTextField(10);
        JLabel lblFechaFin = new JLabel("Fecha Fin (yyyy-MM-dd):");
        txtFechaFin = new JTextField(10);
        btnBuscar = new JButton("Buscar");

        panelSuperior.add(lblFechaInicio);
        panelSuperior.add(txtFechaInicio);
        panelSuperior.add(lblFechaFin);
        panelSuperior.add(txtFechaFin);
        panelSuperior.add(btnBuscar);
        frame.add(panelSuperior, BorderLayout.NORTH);

        // --- Panel Central: Tabla con el Informe ---
        tablaInforme = new JTable();
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Actividad");
        modeloTabla.addColumn("Edición");
        modeloTabla.addColumn("Inscritos");
        modeloTabla.addColumn("Sin Plaza");
        modeloTabla.addColumn("% Socios");
        modeloTabla.addColumn("% No Socios");
        tablaInforme.setModel(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaInforme);
        frame.add(scrollTabla, BorderLayout.CENTER);

        // --- Panel Inferior: Botón para Generar Informe ---
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnGenerarInforme = new JButton("Generar Informe");
        panelInferior.add(btnGenerarInforme);
        frame.add(panelInferior, BorderLayout.SOUTH);

        // Hacer visible la interfaz
        frame.setVisible(true);
    }

    // Métodos para que el controlador acceda a los componentes
    public JFrame getFrame() {
        return frame;
    }
    
    public JTextField getTxtFechaInicio() {
        return txtFechaInicio;
    }
    
    public JTextField getTxtFechaFin() {
        return txtFechaFin;
    }
    
    public JButton getBtnBuscar() {
        return btnBuscar;
    }
    
    public JTable getTablaInforme() {
        return tablaInforme;
    }
    
    public JButton getBtnGenerarInforme() {
        return btnGenerarInforme;
    }

    // Métodos para mostrar mensajes al usuario
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
