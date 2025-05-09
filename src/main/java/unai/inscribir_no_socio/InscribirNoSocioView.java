package unai.inscribir_no_socio;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Dimension;
import java.awt.SystemColor;
import java.util.Date;

import javax.swing.UIManager;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Font;

/**
 * Vista de la pantalla que muestra las carreras activas y permite interactuar con ellas.
 * <br/>Se ha generado con WindowBulder y modificado para ser conforme a MVC teniendo en cuenta:
 * - Se elimina main (es invocada desde CarrerasMain) y se incluye Title en el frame
 * - No se incluye ningun handler de eventos pues estos van en el controlador
 * - Las tablas se encierran en JOptionPane para que se puedan visualizar las cabeceras
 * - Se asinga nombre a las tablas si se van a automatizar la ejecucion de pruebas
 * - Incluye al final los metodos adicionales necesarios para acceder al UI desde el controlador
 */
public class InscribirNoSocioView {

	private JFrame frmInscribirUnNo;
	private JTable tabActividades;
	private JButton btnInscribir;
	private JButton btnVolver;
	private JTextField tfDNI;
	private JTextField tfNombre;

	/**
	 * Create the application.
	 */
	public InscribirNoSocioView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInscribirUnNo = new JFrame();
		frmInscribirUnNo.setTitle("Inscribir un no socio");
		frmInscribirUnNo.setName("Inscribir un no socio");
		frmInscribirUnNo.setBounds(0, 0, 1197, 525);
		frmInscribirUnNo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmInscribirUnNo.getContentPane().setLayout(null);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBounds(20, 11, 1451, 564);
		frmInscribirUnNo.getContentPane().add(contentPane);
		
		JScrollPane tablePanel = new JScrollPane((Component) null);
		tablePanel.setBounds(20, 66, 828, 312);
		contentPane.add(tablePanel);
		
		tabActividades = new JTable();
		tablePanel.setViewportView(tabActividades);
		
		JLabel lblSeleccionaFechasPara = new JLabel("Selecciona una actividad e introduce los datos del usuario que deseas inscbirir");
		lblSeleccionaFechasPara.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblSeleccionaFechasPara.setBounds(20, 11, 995, 26);
		contentPane.add(lblSeleccionaFechasPara);
		
		btnInscribir = new JButton("Realizar inscripción");
		btnInscribir.setBounds(206, 412, 167, 43);
		contentPane.add(btnInscribir);
		
		btnVolver = new JButton("Volver");
		btnVolver.setBounds(29, 412, 167, 43);
		contentPane.add(btnVolver);
		
		tfDNI = new JTextField();
		tfDNI.setBounds(964, 66, 156, 32);
		contentPane.add(tfDNI);
		tfDNI.setColumns(10);
		
		tfNombre = new JTextField();
		tfNombre.setColumns(10);
		tfNombre.setBounds(964, 109, 156, 32);
		contentPane.add(tfNombre);
		
		JLabel lblNewLabel = new JLabel("DNI: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(891, 70, 101, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNombre.setBounds(891, 113, 101, 23);
		contentPane.add(lblNombre);
	}

	//Getters y Setters anyadidos para acceso desde el controlador (repersentacion compacta)
	public JFrame getFrame() { return this.frmInscribirUnNo; }
	public JTable getTablaActividades() { return this.tabActividades; }

	public JButton getBotonInsc() { return this.btnInscribir; }
	public JButton getBotonVolver() { return this.btnVolver; }
	public JTextField getNombreField() { return this.tfNombre; }
	public JTextField getDniField() { return this.tfDNI; }

}