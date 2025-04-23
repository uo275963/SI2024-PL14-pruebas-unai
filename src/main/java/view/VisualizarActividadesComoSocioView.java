package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class VisualizarActividadesComoSocioView {

	private JFrame frame;
	private JLabel lblNombreSocio;
	private JTable tablaActividades;
	private JComboBox cbInstalaciones;
	private JLabel lblNewLabel_1;
	private JTextField TFFechaInicio;
	private JTextField TFFechaFin;
	private JButton btnFiltrar;
	private JButton btnCerrar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisualizarActividadesComoSocioView window = new VisualizarActividadesComoSocioView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JLabel getLblNombreSocio() {
		return lblNombreSocio;
	}

	public void setLblNombreSocio(JLabel lblNombreSocio) {
		this.lblNombreSocio = lblNombreSocio;
	}

	public JTable getTablaActividades() {
		return tablaActividades;
	}

	public void setTablaActividades(JTable tablaActividades) {
		this.tablaActividades = tablaActividades;
	}

	public void setTablaActividadesModel(DefaultTableModel modelo) {
		tablaActividades.setModel(modelo); // Reemplaza el modelo de la tabla existente
	}

	public DefaultTableModel getTablaActividadesModel() {
		return (DefaultTableModel) this.tablaActividades.getModel(); // Obtener y devolver el modelo de la tabla
	}

	public JComboBox getCbInstalaciones() {
		return cbInstalaciones;
	}

	public void setCbInstalaciones(JComboBox cbInstalaciones) {
		this.cbInstalaciones = cbInstalaciones;
	}

	public JButton getBtnFiltrar() {
		return btnFiltrar;
	}

	public void setBtnFiltrar(JButton btnFiltrar) {
		this.btnFiltrar = btnFiltrar;
	}

	/**
	 * Create the application.
	 */
	public VisualizarActividadesComoSocioView() {
		initialize();
	}

	public JTextField getTFFechaInicio() {
		return TFFechaInicio;
	}

	public void setTFFechaInicio(JTextField tFFechaInicio) {
		TFFechaInicio = tFFechaInicio;
	}

	public JTextField getTFFechaFin() {
		return TFFechaFin;
	}

	public void setTFFechaFin(JTextField tFFechaFin) {
		TFFechaFin = tFFechaFin;
	}

	public JButton getBtnCerrar() {
		return btnCerrar;
	}

	public void setBtnCerrar(JButton btnCerrar) {
		this.btnCerrar = btnCerrar;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Visualizar actividades");
		frame.setBounds(100, 100, 964, 469);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblNombreSocio = new JLabel("");
		lblNombreSocio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNombreSocio.setBounds(10, 11, 126, 14);
		frame.getContentPane().add(lblNombreSocio);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 86, 930, 303);
		frame.getContentPane().add(scrollPane);

		tablaActividades = new JTable();
		scrollPane.setViewportView(tablaActividades);

		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnFiltrar.setBounds(10, 52, 89, 23);
		frame.getContentPane().add(btnFiltrar);

		JLabel lblNewLabel = new JLabel("Instalaciones:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(203, 11, 89, 14);
		frame.getContentPane().add(lblNewLabel);

		cbInstalaciones = new JComboBox();
		cbInstalaciones.setBounds(285, 8, 126, 22);
		frame.getContentPane().add(cbInstalaciones);

		lblNewLabel_1 = new JLabel("De");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(203, 57, 28, 14);
		frame.getContentPane().add(lblNewLabel_1);

		TFFechaInicio = new JTextField();
		TFFechaInicio.setBounds(228, 54, 118, 20);
		frame.getContentPane().add(TFFechaInicio);
		TFFechaInicio.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("a");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(350, 57, 19, 14);
		frame.getContentPane().add(lblNewLabel_1_1);

		TFFechaFin = new JTextField();
		TFFechaFin.setColumns(10);
		TFFechaFin.setBounds(375, 54, 118, 20);
		frame.getContentPane().add(TFFechaFin);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(855, 400, 85, 21);
		frame.getContentPane().add(btnCerrar);
	}
}
