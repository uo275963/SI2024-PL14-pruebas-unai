package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;

public class VisualizarPagosComoSocioView {

	private JFrame frame;
	private JTable TablaPagos;
	private JLabel lblNewLabel;
	private JLabel lblNombreSocio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisualizarPagosComoSocioView window = new VisualizarPagosComoSocioView();
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
	public VisualizarPagosComoSocioView() {
		initialize();
	}

	public JTable getTablaPagos() {
		return TablaPagos;
	}

	public void setTablaPagos(JTable tablaPagos) {
		TablaPagos = tablaPagos;
	}

	public JLabel getLblNewLabel() {
		return lblNewLabel;
	}

	public void setLblNewLabel(JLabel lblNewLabel) {
		this.lblNewLabel = lblNewLabel;
	}

	public JLabel getLblNombreSocio() {
		return lblNombreSocio;
	}

	public void setLblNombreSocio(JLabel lblNombreSocio) {
		this.lblNombreSocio = lblNombreSocio;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Visualización de pagos");
		frame.setBounds(100, 100, 889, 682);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblNewLabel = new JLabel("Socio:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 32, 39, 13);
		frame.getContentPane().add(lblNewLabel);

		lblNombreSocio = new JLabel("");
		lblNombreSocio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNombreSocio.setBounds(46, 32, 78, 13);
		frame.getContentPane().add(lblNombreSocio);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 115, 837, 450);
		frame.getContentPane().add(scrollPane);

		TablaPagos = new JTable();
		scrollPane.setViewportView(TablaPagos);

		JLabel lblNewLabel_1 = new JLabel("Periodo a visualizar:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(283, 33, 149, 13);
		frame.getContentPane().add(lblNewLabel_1);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(400, 29, 166, 21);
		frame.getContentPane().add(comboBox);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public void setTablaPagosModel(DefaultTableModel modelo) {
		TablaPagos.setModel(modelo); // Reemplaza el modelo de la tabla existente
	}

	public DefaultTableModel getTablaReservasModel() {
		return (DefaultTableModel) this.TablaPagos.getModel(); // Obtener y devolver el modelo de la tabla
	}
}
