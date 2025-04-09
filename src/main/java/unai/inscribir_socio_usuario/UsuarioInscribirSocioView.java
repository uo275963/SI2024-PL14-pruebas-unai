package unai.inscribir_socio_usuario;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Component;

public class UsuarioInscribirSocioView {

    private JFrame frame;
    private JTable tablaActividades;
    private JButton btnInscribir;
    private JButton btnSalir;
    private JLabel lblUsuarioInfo;

    /**
     * Create the application.
     */
    public UsuarioInscribirSocioView() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Inscripción a Actividades");
        frame.setName("InscripcionActividades");
        frame.setBounds(0, 0, 1292, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBounds(20, 11, 1252, 450);
        frame.getContentPane().add(contentPane);
        
        lblUsuarioInfo = new JLabel("Bienvenido, [nombre socio]. Selecciona una actividad para inscribirte:");
        lblUsuarioInfo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblUsuarioInfo.setBounds(20, 11, 600, 26);
        contentPane.add(lblUsuarioInfo);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 50, 1207, 300);
        contentPane.add(scrollPane);
        
        tablaActividades = new JTable();
        tablaActividades.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Nombre", "Descripción", "Instalación", "Precio socio", "Precio no socio", 
                "Periodo", "Fecha inicio", "Fecha fin", "Plazas totales", "Plazas disponibles", "Estado"
            }
        ));
        tablaActividades.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablaActividades.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablaActividades.getColumnModel().getColumn(5).setPreferredWidth(80);
        tablaActividades.getColumnModel().getColumn(10).setPreferredWidth(120);
        scrollPane.setViewportView(tablaActividades);
        
        btnInscribir = new JButton("Inscribirse");
        btnInscribir.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnInscribir.setBounds(250, 380, 150, 40);
        contentPane.add(btnInscribir);
        
        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSalir.setBounds(50, 380, 150, 40);
        contentPane.add(btnSalir);
    }
    
    // Getters y Setters para acceso desde el controlador
    public JFrame getFrame() { return this.frame; }
    public JTable getTablaActividades() { return this.tablaActividades; }
    public JButton getBotonInscribir() { return this.btnInscribir; }
    public JButton getBotonSalir() { return this.btnSalir; }
    public JLabel getLabelUsuarioInfo() { return this.lblUsuarioInfo; }
    
    // Método para actualizar el nombre del usuario en la interfaz
    public void setNombreUsuario(String nombre) {
        lblUsuarioInfo.setText("Bienvenido, " + nombre + ". Selecciona una actividad para inscribirte:");
    }
}
