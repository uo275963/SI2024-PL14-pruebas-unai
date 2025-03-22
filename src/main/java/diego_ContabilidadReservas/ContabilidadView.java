package diego_ContabilidadReservas;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ContabilidadView {

    private JFrame frame;
    private JTable tablaSocios;
    private JButton btnCerrar;
    private JButton btnGenerarDocumento;

    public ContabilidadView() {
        initialize();
    }

    private void initialize() {
        // Crear ventana principal
        frame = new JFrame("Contabilidad de Socios");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // Etiqueta principal
        JLabel lblTitulo = new JLabel("Contabilidad Socios");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblTitulo, BorderLayout.NORTH);

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
        btnCerrar = new JButton("Cerrar");
        btnGenerarDocumento = new JButton("Generar Documento");
        panelBotones.add(btnGenerarDocumento);
        panelBotones.add(btnCerrar);
        frame.getContentPane().add(panelBotones, BorderLayout.SOUTH);

        // Tabla para mostrar los datos de los socios
        tablaSocios = new JTable();
        JScrollPane scrollTabla = new JScrollPane(tablaSocios);
        frame.getContentPane().add(scrollTabla, BorderLayout.CENTER);
    }

    // Métodos para acceder a los componentes desde el controlador
    public JFrame getFrame() {
        return frame;
    }

    public JTable getTablaSocios() {
        return tablaSocios;
    }

    public JButton getBtnCerrar() {
        return btnCerrar;
    }

    public JButton getBtnGenerarDocumento() {
        return btnGenerarDocumento;
    }

    // Método para cargar los datos en la tabla
    public void cargarDatosTabla(Object[][] datos, String[] columnas) {
        DefaultTableModel model = new DefaultTableModel(datos, columnas);
        tablaSocios.setModel(model);
    }

    // Método para mostrar mensaje de éxito
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método para mostrar mensaje de error
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
