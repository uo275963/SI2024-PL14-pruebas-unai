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
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

public class CancelarReservaSocioView {

	private JFrame frame;
	private JTextField TFFecha;
	private JTable TReservas;
	private JButton bMostrar;
	private JTextField TFHoraInicio;
	private JTextField TFHoraFin;
	private JTextField TFInstalacion;
	private JTextArea tAMotivo;
	private JButton BEliminar;
	private JButton bCerrar;

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
		frame.setBounds(100, 100, 702, 338);
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
		scrollPane.setBounds(10, 67, 670, 82);
		frame.getContentPane().add(scrollPane);
		
		TReservas = new JTable();
		scrollPane.setViewportView(TReservas);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(20, 160, 670, 2);
		frame.getContentPane().add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("Eliminar reserva:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 168, 122, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Hora inicio: ");
		lblNewLabel_2.setBounds(10, 193, 91, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		TFHoraInicio = new JTextField();
		TFHoraInicio.setBounds(122, 190, 106, 20);
		frame.getContentPane().add(TFHoraInicio);
		TFHoraInicio.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Hora fin:");
		lblNewLabel_3.setBounds(10, 218, 91, 23);
		frame.getContentPane().add(lblNewLabel_3);
		
		TFHoraFin = new JTextField();
		TFHoraFin.setBounds(122, 221, 106, 20);
		frame.getContentPane().add(TFHoraFin);
		TFHoraFin.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Instalacion: ");
		lblNewLabel_4.setBounds(10, 252, 91, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		TFInstalacion = new JTextField();
		TFInstalacion.setBounds(122, 249, 106, 20);
		frame.getContentPane().add(TFInstalacion);
		TFInstalacion.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Motivo:");
		lblNewLabel_5.setBounds(264, 173, 48, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(253, 160, 1, 132);
		frame.getContentPane().add(separator_1);
		
		tAMotivo = new JTextArea();
		tAMotivo.setBounds(264, 188, 416, 44);
		frame.getContentPane().add(tAMotivo);
		
		bCerrar = new JButton("Cerrar\r\n");
		bCerrar.setBounds(589, 269, 91, 23);
		frame.getContentPane().add(bCerrar);
		
		BEliminar = new JButton("Eliminar Reserva");
		BEliminar.setBounds(406, 269, 173, 23);
		frame.getContentPane().add(BEliminar);
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

	public JTextField getTFHoraInicio() {
		return TFHoraInicio;
	}

	public void setTFHoraInicio(JTextField tFHoraInicio) {
		TFHoraInicio = tFHoraInicio;
	}

	public JTextField getTFHoraFin() {
		return TFHoraFin;
	}

	public void setTFHoraFin(JTextField tFHoraFin) {
		TFHoraFin = tFHoraFin;
	}

	public JTextField getTFInstalacion() {
		return TFInstalacion;
	}

	public void setTFInstalacion(JTextField tFInstalacion) {
		TFInstalacion = tFInstalacion;
	}

	public JTextArea gettAMotivo() {
		return tAMotivo;
	}

	public void settAMotivo(JTextArea tAMotivo) {
		this.tAMotivo = tAMotivo;
	}

	public JButton getBtnNewButton() {
		return BEliminar;
	}

	public void setBtnNewButton(JButton btnNewButton) {
		this.BEliminar = btnNewButton;
	}

	public JButton getbCerrar() {
		return bCerrar;
	}

	public void setbCerrar(JButton bCerrar) {
		this.bCerrar = bCerrar;
	}

	public JButton getBEliminar() {
		return BEliminar;
	}

	public void setBEliminar(JButton bEliminar) {
		BEliminar = bEliminar;
	}
	
	
}
