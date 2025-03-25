package unai.inscribir_no_socio;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ReciboView extends JFrame {

    public ReciboView(String dni, String nombre, double cantidad, String nombreActividad, String nombreInstalacion, String fechaInscripcion) {
        setTitle("Recibo de Inscripción");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));

        panel.add(new JLabel("DNI:"));
        panel.add(new JLabel(dni));

        panel.add(new JLabel("Nombre:"));
        panel.add(new JLabel(nombre));

        panel.add(new JLabel("Cantidad:"));
        panel.add(new JLabel(String.format("%.2f €", cantidad)));

        panel.add(new JLabel("Actividad:"));
        panel.add(new JLabel(nombreActividad));

        panel.add(new JLabel("Instalación:"));
        panel.add(new JLabel(nombreInstalacion));

        panel.add(new JLabel("Fecha de inscripción:"));
        panel.add(new JLabel(fechaInscripcion));

        add(panel, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar, BorderLayout.SOUTH);
    }

    public void mostrar() {
        setVisible(true);
    }
} 

