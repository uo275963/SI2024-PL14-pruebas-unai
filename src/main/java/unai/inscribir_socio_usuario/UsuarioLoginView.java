package unai.inscribir_socio_usuario;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;

/**
 * Clase que implementa la vista de login para el sistema de inscripción de socios
 */
public class UsuarioLoginView {

    private JFrame frame;
    private JTextField txtDNI;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnSalir;

    /**
     * Constructor de la vista de login
     */
    public UsuarioLoginView() {
        initialize();
    }

    /**
     * Inicializa los componentes de la interfaz gráfica
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Login de Socio");
        frame.setName("LoginSocio");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        frame.setContentPane(contentPane);
        
        JLabel lblTitulo = new JLabel("Acceso al Sistema de Inscripción");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBounds(100, 20, 300, 30);
        contentPane.add(lblTitulo);
        
        JLabel lblDNI = new JLabel("DNI:");
        lblDNI.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblDNI.setBounds(50, 80, 100, 25);
        contentPane.add(lblDNI);
        
        txtDNI = new JTextField();
        txtDNI.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtDNI.setBounds(160, 80, 200, 25);
        contentPane.add(txtDNI);
        txtDNI.setColumns(10);
        
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPassword.setBounds(50, 120, 100, 25);
        contentPane.add(lblPassword);
        
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtPassword.setBounds(160, 120, 200, 25);
        contentPane.add(txtPassword);
        
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnLogin.setBounds(230, 180, 140, 35);
        contentPane.add(btnLogin);
        
        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSalir.setBounds(80, 180, 100, 35);
        contentPane.add(btnSalir);
    }
    
    /**
     * Devuelve el campo de texto para el DNI
     * @return Campo de texto del DNI
     */
    public JTextField getCampoDNI() {
        return txtDNI;
    }
    
    /**
     * Devuelve el campo de contraseña
     * @return Campo de contraseña
     */
    public JPasswordField getCampoPassword() {
        return txtPassword;
    }
    
    /**
     * Devuelve el botón de login
     * @return Botón de login
     */
    public JButton getBotonLogin() {
        return btnLogin;
    }
    
    /**
     * Devuelve el botón de salir
     * @return Botón de salir
     */
    public JButton getBotonSalir() {
        return btnSalir;
    }
    
    /**
     * Devuelve el frame principal
     * @return Frame principal
     */
    public JFrame getFrame() {
        return frame;
    }
    
    /**
     * Limpia los campos del formulario
     */
    public void limpiarCampos() {
        txtDNI.setText("");
        txtPassword.setText("");
        txtDNI.requestFocus();
    }
}