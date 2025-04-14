package diego_Informe;

import java.awt.*;
import javax.swing.*;

public class InformeView {
    private JFrame frame;
    private JComboBox<String> comboPeriodo;
    private JButton btnGenerarInforme;
    private JTable tablaInforme;

    public InformeView() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Informe de Actividades");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // Panel de encabezado
        JPanel panelEncabezado = new JPanel();
        JLabel lblEncabezado = new JLabel("Informe de Actividades");
        lblEncabezado.setFont(new Font("Arial", Font.BOLD, 16));
        panelEncabezado.add(lblEncabezado);
        frame.getContentPane().add(panelEncabezado, BorderLayout.NORTH);

        // Panel para selección de periodo y generación del informe
        JPanel panelControles = new JPanel();
        panelControles.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelControles.add(new JLabel("Seleccione el periodo:"));
        
        comboPeriodo = new JComboBox<>();
        // Se añaden los periodos de inscripción predefinidos (se pueden obtener dinámicamente)
        comboPeriodo.addItem("Cuatrimestre 1");
        comboPeriodo.addItem("Cuatrimestre 2");
        panelControles.add(comboPeriodo);
        
        btnGenerarInforme = new JButton("Generar Informe");
        panelControles.add(btnGenerarInforme);
        
        frame.getContentPane().add(panelControles, BorderLayout.CENTER);

        // Tabla para mostrar el informe de actividades
        tablaInforme = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaInforme);
        scrollPane.setPreferredSize(new Dimension(780, 300));
        frame.getContentPane().add(scrollPane, BorderLayout.SOUTH);
    }

    // Métodos para que el controlador pueda interactuar con los componentes de la vista
    public JFrame getFrame() {
        return frame;
    }

    public JComboBox<String> getComboPeriodo() {
        return comboPeriodo;
    }

    public JButton getBtnGenerarInforme() {
        return btnGenerarInforme;
    }

    public JTable getTablaInforme() {
        return tablaInforme;
    }

    // Métodos para mostrar mensajes a la hora de mostrar información o errores
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
