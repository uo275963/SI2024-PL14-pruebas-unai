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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VisualizarPagosComoSocioView {

	private JFrame frame;
	private JTable TablaPagos;
	private JLabel lblNewLabel;
	private JLabel lblNombreSocio;
	private JButton btnBuscar;
	private JComboBox CBMeses;
	private JLabel lblCosteMensual;
	private JButton btnCerrar;

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
	

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(JButton btnBuscar) {
		this.btnBuscar = btnBuscar;
	}
	
	
	
	

	public JComboBox getCBMeses() {
		return CBMeses;
	}

	public void setCBMeses(JComboBox cBMeses) {
		this.CBMeses = cBMeses;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Visualización de pagos");
		frame.setBounds(100, 100, 889, 682);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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

		CBMeses = new JComboBox();
		CBMeses.setBounds(400, 29, 166, 21);
		frame.getContentPane().add(CBMeses);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBuscar.setBounds(10, 84, 139, 21);
		frame.getContentPane().add(btnBuscar);
		
		lblCosteMensual = new JLabel("");
		lblCosteMensual.setBounds(10, 595, 158, 13);
		frame.getContentPane().add(lblCosteMensual);
		
		btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(762, 595, 85, 21);
		frame.getContentPane().add(btnCerrar);
	}

	public JButton getBtnCerrar() {
		return btnCerrar;
	}

	public void setBtnCerrar(JButton btnCerrar) {
		this.btnCerrar = btnCerrar;
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

	public JLabel getLblCosteMensual() {
		return lblCosteMensual;
	}

	public void setLblCosteMensual(JLabel lblCosteMensual) {
		this.lblCosteMensual = lblCosteMensual;
	}
}
