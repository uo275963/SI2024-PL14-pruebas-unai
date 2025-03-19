package unai.inscribir_socio;

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
public class InscribirSocioView {

	private JFrame frame;
	private JTable tabActividades;
	private JDateChooser fecha1;
	private JDateChooser fecha2;
	private JComboBox<Object> cbPeriodo;
	private JTable tabSocios;
	private JButton btnInscribir;

	/**
	 * Create the application.
	 */
	public InscribirSocioView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Carreras");
		frame.setName("Carreras");
		frame.setBounds(0, 0, 1497, 644);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBounds(20, 11, 1451, 564);
		frame.getContentPane().add(contentPane);
		
		cbPeriodo = new JComboBox<>();
		cbPeriodo.setBounds(138, 48, 194, 22);
		contentPane.add(cbPeriodo);
		
		JLabel lblPeriodo = new JLabel("Periodo:");
		lblPeriodo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPeriodo.setBounds(20, 52, 108, 14);
		contentPane.add(lblPeriodo);
		
		fecha1 = new JDateChooser();
		fecha1.setBounds(138, 77, 103, 20);
		contentPane.add(fecha1);
		
		fecha2 = new JDateChooser();
		fecha2.setBounds(138, 108, 103, 20);
		contentPane.add(fecha2);
		
		JLabel lblFechaInicio = new JLabel("Fecha inicio:");
		lblFechaInicio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFechaInicio.setBounds(20, 83, 108, 14);
		contentPane.add(lblFechaInicio);
		
		JLabel lblFechaFin = new JLabel("Fecha fin:");
		lblFechaFin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFechaFin.setBounds(20, 114, 108, 14);
		contentPane.add(lblFechaFin);
		
		JScrollPane tablePanel = new JScrollPane((Component) null);
		tablePanel.setBounds(20, 164, 828, 312);
		contentPane.add(tablePanel);
		
		tabActividades = new JTable();
		tablePanel.setViewportView(tabActividades);
		
		JLabel lblSeleccionaFechasPara = new JLabel("Selecciona una actividad y el socio que deseas inscribir");
		lblSeleccionaFechasPara.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblSeleccionaFechasPara.setBounds(20, 11, 602, 26);
		contentPane.add(lblSeleccionaFechasPara);
		
		JScrollPane tablePanel_1 = new JScrollPane((Component) null);
		tablePanel_1.setBounds(886, 164, 521, 312);
		contentPane.add(tablePanel_1);
		
		tabSocios = new JTable();
		tablePanel_1.setViewportView(tabSocios);
		
		btnInscribir = new JButton("Realizar inscripción");
		btnInscribir.setBounds(20, 498, 167, 43);
		contentPane.add(btnInscribir);
	}

	//Getters y Setters anyadidos para acceso desde el controlador (repersentacion compacta)
	public JFrame getFrame() { return this.frame; }
	public JTable getTablaActividades() { return this.tabActividades; }
	public JDateChooser fechaInicio() { return this.fecha1;}
	public JDateChooser fechaFin() {return this.fecha2;}
	public void setFechaInicio(Date fecha) { this.fecha1.setDate(fecha);}
	public void setFechaFin(Date fecha) { this.fecha2.setDate(fecha);}
	public JTable getTabSocios() { return this.tabSocios; }
	public JButton getBoton() { return this.btnInscribir; }


	public JComboBox<Object> getListaPeriodo() { return this.cbPeriodo; }
}
