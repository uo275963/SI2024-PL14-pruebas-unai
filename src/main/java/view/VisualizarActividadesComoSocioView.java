package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VisualizarActividadesComoSocioView {

	private JFrame frame;
	private JLabel lblNombreSocio;
	private JTable tablaActividades;

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

	/**
	 * Create the application.
	 */
	public VisualizarActividadesComoSocioView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Visualizar actividades");
		frame.setBounds(100, 100, 613, 392);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblNombreSocio = new JLabel("");
		lblNombreSocio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNombreSocio.setBounds(10, 11, 46, 14);
		frame.getContentPane().add(lblNombreSocio);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 55, 577, 274);
		frame.getContentPane().add(scrollPane);

		tablaActividades = new JTable();
		scrollPane.setViewportView(tablaActividades);
	}
}
