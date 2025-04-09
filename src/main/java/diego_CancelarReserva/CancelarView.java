package diego_CancelarReserva;

import java.awt.*;
import javax.swing.*;

public class CancelarView {
    // Variables para el JFrame y sus paneles
    private JFrame frame;
    private JPanel panelPrincipal; // Panel que utiliza CardLayout para cambiar entre vistas

    // Componentes para la pantalla de Login
    private JPanel panelLogin;
    private JTextField txtUsuario;
    private JPasswordField pwdContrasena;
    private JButton btnLogin;

    // Componentes para la pantalla de Cancelación de Reserva
    private JPanel panelCancelar;
    private JTable tablaReservas;
    private JButton btnCancelar;
    
    // Constantes para identificar las tarjetas del CardLayout
    private final String LOGIN_PANEL = "loginPanel";
    private final String CANCELAR_PANEL = "cancelarPanel";

    public CancelarView() {
        initialize();
    }

    private void initialize() {
        // Configuración básica del frame
        frame = new JFrame("Cancelar Reserva");
        frame.setBounds(100, 100, 600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // Panel principal usando CardLayout para alternar entre vistas
        panelPrincipal = new JPanel(new CardLayout());
        frame.getContentPane().add(panelPrincipal, BorderLayout.CENTER);

        // --- Inicialización de la Vista de Login ---
        panelLogin = new JPanel();
        panelLogin.setLayout(new GridLayout(3, 2, 10, 10)); // Disposición en 3 filas x 2 columnas

        JLabel lblUsuario = new JLabel("Usuario:");
        txtUsuario = new JTextField();
        txtUsuario.setMaximumSize(new Dimension(2147483646, 2147483647));
        JLabel lblContrasena = new JLabel("Contraseña:");
        pwdContrasena = new JPasswordField();

        btnLogin = new JButton("Iniciar Sesión");

        panelLogin.add(lblUsuario);
        panelLogin.add(txtUsuario);
        panelLogin.add(lblContrasena);
        panelLogin.add(pwdContrasena);
        // Espacio vacío para mayor orden
        panelLogin.add(new JLabel(""));
        panelLogin.add(btnLogin);

        // Agregar panel de login al panel principal
        panelPrincipal.add(panelLogin, LOGIN_PANEL);

        // --- Inicialización de la Vista de Cancelación ---
        panelCancelar = new JPanel();
        panelCancelar.setLayout(new BorderLayout(10, 10));

        // Encabezado o mensaje de instrucciones
        JPanel panelEncabezado = new JPanel();
        JLabel lblEncabezado = new JLabel("Seleccione la reserva a cancelar (Con mínimo 1 día de antelación):");
        lblEncabezado.setFont(new Font("Arial", Font.BOLD, 14));
        panelEncabezado.add(lblEncabezado);
        panelCancelar.add(panelEncabezado, BorderLayout.NORTH);

        // Tabla para mostrar las reservas del usuario
        tablaReservas = new JTable();
        JScrollPane scrollTabla = new JScrollPane(tablaReservas);
        panelCancelar.add(scrollTabla, BorderLayout.CENTER);

        // Botón para cancelar la reserva seleccionada
        JPanel panelBotones = new JPanel();
        btnCancelar = new JButton("Cancelar Reserva");
        panelBotones.add(btnCancelar);
        panelCancelar.add(panelBotones, BorderLayout.SOUTH);

        // Agregar panel de cancelación al panel principal
        panelPrincipal.add(panelCancelar, CANCELAR_PANEL);
    }
    
    // Métodos para cambiar de vista en el CardLayout
    public void mostrarLogin() {
        CardLayout cl = (CardLayout)(panelPrincipal.getLayout());
        cl.show(panelPrincipal, LOGIN_PANEL);
    }
    
    public void mostrarCancelar() {
        CardLayout cl = (CardLayout)(panelPrincipal.getLayout());
        cl.show(panelPrincipal, CANCELAR_PANEL);
    }
    
    // Métodos para acceder a los componentes desde el controlador
    public JFrame getFrame() {
        return frame;
    }

    public JTextField getUsuarioField() {
        return txtUsuario;
    }

    public JPasswordField getPasswordField() {
        return pwdContrasena;
    }
    
    public JButton getBtnLogin() {
        return btnLogin;
    }
    
    public JTable getTablaReservas() {
        return tablaReservas;
    }
    
    public JButton getBtnCancelar() {
        return btnCancelar;
    }
    
    // Métodos para mostrar mensajes e informar al usuario
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
