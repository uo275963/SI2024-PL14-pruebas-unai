package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class ReservaAutomaticaView {

	private JFrame frame;
	private JTextField tfInstalacion;
	private JTextField tfFechaInicio;
	private JTextField tfFechaFin;
	private JTable tDias;
	private JComboBox cbActividades;
	private JLabel lActividad;
	private JButton bMostrar;
	private JTextArea taConflictos;
	private JButton bReserva;
	private JButton bCerrar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReservaAutomaticaView window = new ReservaAutomaticaView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ReservaAutomaticaView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 567, 608);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Seleccionar actividades: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 215, 26);
		frame.getContentPane().add(lblNewLabel);
		
		cbActividades = new JComboBox();
		cbActividades.setBounds(10, 46, 169, 22);
		frame.getContentPane().add(cbActividades);
		
		lActividad = new JLabel("\r\n");
		lActividad.setBackground(Color.WHITE);
		lActividad.setBounds(214, 48, 189, 20);
		frame.getContentPane().add(lActividad);
		
		bMostrar = new JButton("Mostrar\r\n");
		bMostrar.setBounds(439, 46, 91, 23);
		frame.getContentPane().add(bMostrar);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 96, 555, 2);
		frame.getContentPane().add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("Datos de la actividad:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 109, 215, 26);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Instalacion seleccionada: ");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(10, 146, 152, 22);
		frame.getContentPane().add(lblNewLabel_2);
		
		tfInstalacion = new JTextField();
		tfInstalacion.setEditable(false);
		tfInstalacion.setBounds(10, 179, 132, 20);
		frame.getContentPane().add(tfInstalacion);
		tfInstalacion.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Fecha de Inicio:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(177, 146, 113, 22);
		frame.getContentPane().add(lblNewLabel_3);
		
		tfFechaInicio = new JTextField();
		tfFechaInicio.setEditable(false);
		tfFechaInicio.setBounds(177, 179, 113, 20);
		frame.getContentPane().add(tfFechaInicio);
		tfFechaInicio.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Fecha de Fin: ");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(316, 146, 132, 22);
		frame.getContentPane().add(lblNewLabel_4);
		
		tfFechaFin = new JTextField();
		tfFechaFin.setEditable(false);
		tfFechaFin.setBounds(316, 179, 106, 20);
		frame.getContentPane().add(tfFechaFin);
		tfFechaFin.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 228, 555, 8);
		frame.getContentPane().add(separator_1);
		
		JLabel lblNewLabel_5 = new JLabel("Datos de los dias: ");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_5.setBounds(10, 241, 157, 26);
		frame.getContentPane().add(lblNewLabel_5);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 278, 510, 59);
		frame.getContentPane().add(scrollPane);
		
		tDias = new JTable();
		scrollPane.setViewportView(tDias);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(0, 366, 555, 2);
		frame.getContentPane().add(separator_2);
		
		JLabel lblNewLabel_6 = new JLabel("Conflictos: ");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_6.setBounds(10, 379, 152, 22);
		frame.getContentPane().add(lblNewLabel_6);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(0, 516, 555, 2);
		frame.getContentPane().add(separator_3);
		
		bCerrar = new JButton("Cerrar\r\n");
		bCerrar.setBounds(439, 539, 91, 23);
		frame.getContentPane().add(bCerrar);
		
		bReserva = new JButton("Hacer Reserva");
		bReserva.setBounds(298, 539, 124, 23);
		frame.getContentPane().add(bReserva);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 416, 520, 78);
		frame.getContentPane().add(scrollPane_1);
		
		taConflictos = new JTextArea();
		scrollPane_1.setViewportView(taConflictos);
		taConflictos.setEditable(false);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JTextField getTfInstalacion() {
		return tfInstalacion;
	}

	public void setTfInstalacion(JTextField tfInstalacion) {
		this.tfInstalacion = tfInstalacion;
	}

	public JTextField getTfFechaInicio() {
		return tfFechaInicio;
	}

	public void setTfFechaInicio(JTextField tfFechaInicio) {
		this.tfFechaInicio = tfFechaInicio;
	}

	public JTextField getTfFechaFin() {
		return tfFechaFin;
	}

	public void setTfFechaFin(JTextField tfFechaFin) {
		this.tfFechaFin = tfFechaFin;
	}

	public JTable gettDias() {
		return tDias;
	}

	public void settDias(JTable tDias) {
		this.tDias = tDias;
	}
	
	public void setTablaDiasModel(DefaultTableModel modelo) {
		tDias.setModel(modelo); // Reemplaza el modelo de la tabla existente
	}

	public DefaultTableModel getTablaDiasModel() {
		return (DefaultTableModel) this.tDias.getModel(); // Obtener y devolver el modelo de la tabla
	}

	public JComboBox<Integer> getCbActividades() {
		return cbActividades;
	}

	public void setCbActividades(JComboBox<Integer> cbActividades) {
		this.cbActividades = cbActividades;
	}
	
	public void setCbActividadesModel(DefaultComboBoxModel cBActividades) {
		cbActividades.setModel(cBActividades);
	}

	public JLabel getlActividad() {
		return lActividad;
	}

	public void setlActividad(JLabel lActividad) {
		this.lActividad = lActividad;
	}

	public JButton getbMostrar() {
		return bMostrar;
	}

	public void setbMostrar(JButton bMostrar) {
		this.bMostrar = bMostrar;
	}

	public JTextArea getTaConflictos() {
		return taConflictos;
	}

	public void setTaConflictos(JTextArea taConflictos) {
		this.taConflictos = taConflictos;
	}

	public JButton getbReserva() {
		return bReserva;
	}

	public void setbReserva(JButton bReserva) {
		this.bReserva = bReserva;
	}

	public JButton getbCerrar() {
		return bCerrar;
	}

	public void setbCerrar(JButton bCerrar) {
		this.bCerrar = bCerrar;
	}
}
