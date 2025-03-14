package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JSeparator;

public class CancelarReservaSocioView {

	private JFrame frame;
	private JTextField TFFecha;
	private JTable TReservas;
	private JButton bMostrar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CancelarReservaSocioView window = new CancelarReservaSocioView();
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
	public CancelarReservaSocioView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 632, 338);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Fecha: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 68, 14);
		frame.getContentPane().add(lblNewLabel);
		
		TFFecha = new JTextField();
		TFFecha.setBounds(10, 36, 200, 20);
		frame.getContentPane().add(TFFecha);
		TFFecha.setColumns(10);
		
		bMostrar = new JButton("Mostrar\r\n");
		bMostrar.setBounds(234, 35, 91, 23);
		frame.getContentPane().add(bMostrar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 67, 600, 82);
		frame.getContentPane().add(scrollPane);
		
		TReservas = new JTable();
		scrollPane.setViewportView(TReservas);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(20, 160, 590, 2);
		frame.getContentPane().add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("Eliminar reserva:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 168, 122, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Hora inicio: ");
		lblNewLabel_2.setBounds(10, 193, 91, 14);
		frame.getContentPane().add(lblNewLabel_2);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JTextField getTFFecha() {
		return TFFecha;
	}

	public void setTFFecha(JTextField tFFecha) {
		TFFecha = tFFecha;
	}

	public JTable getTReservas() {
		return TReservas;
	}

	public void setTReservas(JTable tReservas) {
		TReservas = tReservas;
	}

	public JButton getbMostrar() {
		return bMostrar;
	}

	public void setbMostrar(JButton bMostrar) {
		this.bMostrar = bMostrar;
	}
}
