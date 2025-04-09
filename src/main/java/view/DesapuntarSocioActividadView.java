package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class DesapuntarSocioActividadView {

	private JFrame frame;
	private JComboBox cbActividad;
	private JLabel lactividad;
	private JTable tSocios;
	private JComboBox cbSocio;
	private JTextArea taDNI;
	private JTextArea taPago;
	private JButton bDesapuntar;
	private JButton bCerrar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DesapuntarSocioActividadView window = new DesapuntarSocioActividadView();
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
	public DesapuntarSocioActividadView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 593, 329);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Seleccionar actividad: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 27, 153, 22);
		frame.getContentPane().add(lblNewLabel);
		
		cbActividad = new JComboBox();
		cbActividad.setBounds(10, 60, 153, 22);
		frame.getContentPane().add(cbActividad);
		
		lactividad = new JLabel("");
		lactividad.setBounds(10, 103, 153, 22);
		frame.getContentPane().add(lactividad);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(246, 34, 265, 74);
		frame.getContentPane().add(scrollPane);
		
		tSocios = new JTable();
		scrollPane.setViewportView(tSocios);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 149, 551, 2);
		frame.getContentPane().add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("Seleccionar socio: ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 162, 153, 22);
		frame.getContentPane().add(lblNewLabel_1);
		
		cbSocio = new JComboBox();
		cbSocio.setBounds(10, 195, 153, 22);
		frame.getContentPane().add(cbSocio);
		
		JLabel lblNewLabel_2 = new JLabel("DNI Socio: ");
		lblNewLabel_2.setBounds(207, 179, 83, 22);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Estado del pago:\r\n");
		lblNewLabel_3.setBounds(207, 219, 123, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		taDNI = new JTextArea();
		taDNI.setEditable(false);
		taDNI.setBounds(340, 178, 211, 22);
		frame.getContentPane().add(taDNI);
		
		taPago = new JTextArea();
		taPago.setEditable(false);
		taPago.setBounds(340, 214, 211, 22);
		frame.getContentPane().add(taPago);
		
		bCerrar = new JButton("Cerrar\r\n");
		bCerrar.setBounds(480, 260, 91, 23);
		frame.getContentPane().add(bCerrar);
		
		bDesapuntar = new JButton("Desapuntar al socio\r\n");
		bDesapuntar.setBounds(317, 260, 153, 23);
		frame.getContentPane().add(bDesapuntar);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JComboBox<Integer> getCbActividad() {
		return cbActividad;
	}

	public void setCbActividad(JComboBox<Integer> cbActividad) {
		this.cbActividad = cbActividad;
	}
	
	public void setCbActividadesModel(DefaultComboBoxModel cBActividades) {
		cbActividad.setModel(cBActividades);
	}

	public JLabel getLactividad() {
		return lactividad;
	}

	public void setLactividad(JLabel lactividad) {
		this.lactividad = lactividad;
	}

	public JTable gettSocios() {
		return tSocios;
	}

	public void settSocios(JTable tSocios) {
		this.tSocios = tSocios;
	}
	
	public void setTablaSociosModel(DefaultTableModel modelo) {
		tSocios.setModel(modelo); // Reemplaza el modelo de la tabla existente
	}

	public DefaultTableModel getTablaSociosModel() {
		return (DefaultTableModel) this.tSocios.getModel(); // Obtener y devolver el modelo de la tabla
	}

	public JComboBox<Integer> getCbSocio() {
		return cbSocio;
	}

	public void setCbSocio(JComboBox<Integer> cbSocio) {
		this.cbSocio = cbSocio;
	}
	public void setCbSociosModel(DefaultComboBoxModel cBActividades) {
		cbSocio.setModel(cBActividades);
	}

	public JTextArea getTaDNI() {
		return taDNI;
	}

	public void setTaDNI(JTextArea taDNI) {
		this.taDNI = taDNI;
	}

	public JTextArea getTaPago() {
		return taPago;
	}

	public void setTaPago(JTextArea taPago) {
		this.taPago = taPago;
	}

	public JButton getbDesapuntar() {
		return bDesapuntar;
	}

	public void setbDesapuntar(JButton bDesapuntar) {
		this.bDesapuntar = bDesapuntar;
	}

	public JButton getbCerrar() {
		return bCerrar;
	}

	public void setbCerrar(JButton bCerrar) {
		this.bCerrar = bCerrar;
	}	
}
