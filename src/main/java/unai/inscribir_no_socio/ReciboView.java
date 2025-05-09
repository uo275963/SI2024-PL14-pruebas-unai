package unai.inscribir_no_socio;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ReciboView extends JFrame {
    private String dni;
    private String nombre;
    private double cantidad;
    private String nombreActividad;
    private String nombreInstalacion;
    private String fechaInscripcion;

    public ReciboView(String dni, String nombre, double cantidad, String nombreActividad, String nombreInstalacion, String fechaInscripcion) {
        this.dni = dni;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.nombreActividad = nombreActividad;
        this.nombreInstalacion = nombreInstalacion;
        this.fechaInscripcion = fechaInscripcion;
        
        setTitle("Recibo de Inscripción");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel principal con la información del recibo
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

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnGuardar = new JButton("Guardar como TXT");
        btnGuardar.addActionListener(e -> guardarComoTxt());
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCerrar);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Guardar automáticamente al mostrar el recibo
        guardarAutomaticamente();
    }

    private void guardarAutomaticamente() {
        // Crear una carpeta "recibos" si no existe
        File carpetaRecibos = new File("recibos");
        if (!carpetaRecibos.exists()) {
            carpetaRecibos.mkdir();
        }
        
        // Generar un nombre de archivo único basado en DNI y fecha/hora
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fechaHora = formatoFecha.format(new Date());
        String nombreArchivo = "recibos/recibo_" + dni.replace(" ", "_") + "_" + fechaHora + ".txt";
        
        // Guardar el archivo
        try {
            FileWriter writer = new FileWriter(nombreArchivo);
            writer.write(generarContenidoRecibo());
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al guardar el recibo automáticamente: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void guardarComoTxt() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Recibo");
        
        // Configurar para guardar solo archivos .txt
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto (.txt)", "txt");
        fileChooser.setFileFilter(filter);
        
        // Sugerir un nombre de archivo predeterminado
        fileChooser.setSelectedFile(new File("recibo_" + dni.replace(" ", "_") + ".txt"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            
            // Asegurar que el archivo tenga la extensión .txt
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".txt")) {
                filePath += ".txt";
                fileToSave = new File(filePath);
            }
            
            try {
                FileWriter writer = new FileWriter(fileToSave);
                writer.write(generarContenidoRecibo());
                writer.close();
                JOptionPane.showMessageDialog(this, 
                    "Recibo guardado correctamente en: " + fileToSave.getAbsolutePath(), 
                    "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al guardar el recibo: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private String generarContenidoRecibo() {
        StringBuilder contenido = new StringBuilder();
        contenido.append("=======================================\n");
        contenido.append("         RECIBO DE INSCRIPCIÓN         \n");
        contenido.append("=======================================\n\n");
        contenido.append("DNI: ").append(dni).append("\n");
        contenido.append("Nombre: ").append(nombre).append("\n");
        contenido.append("Cantidad: ").append(String.format("%.2f €", cantidad)).append("\n");
        contenido.append("Actividad: ").append(nombreActividad).append("\n");
        contenido.append("Instalación: ").append(nombreInstalacion).append("\n");
        contenido.append("Fecha de inscripción: ").append(fechaInscripcion).append("\n\n");
        contenido.append("=======================================\n");
        contenido.append("Gracias por su inscripción. Este recibo sirve como comprobante de pago.\n");
        
        return contenido.toString();
    }

    public void mostrar() {
        setVisible(true);
    }
}