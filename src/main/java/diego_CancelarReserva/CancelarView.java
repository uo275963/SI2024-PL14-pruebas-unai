package diego_CancelarReserva;

import java.awt.*;
import javax.swing.*;

public class CancelarView {
	private JFrame frame;
	private JTable tablaReservas;
	private JButton btnCancelar;
	private JButton btnBuscar;
	private JComboBox<Object> comboUsuarios;

	public CancelarView() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame("Cancelar Reserva");
		frame.setBounds(100, 100, 700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		// Panel superior con instrucciones
		JPanel panelEncabezado = new JPanel();
		JLabel lblEncabezado = new JLabel("Seleccione un usuario para ver y cancelar reservas");
		lblEncabezado.setFont(new Font("Arial", Font.BOLD, 14));
		panelEncabezado.add(lblEncabezado);
		frame.getContentPane().add(panelEncabezado, BorderLayout.NORTH);

		// Panel central con controles
		JPanel panelCentro = new JPanel(new GridLayout(2, 2, 10, 20));
		panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// ComboBox de usuarios
		panelCentro.add(new JLabel("Usuario:"));
		comboUsuarios = new JComboBox<>();
		panelCentro.add(comboUsuarios);

		// Botón buscar
		btnBuscar = new JButton("Buscar Reservas");
		panelCentro.add(btnBuscar);

		// Botón cancelar
		btnCancelar = new JButton("Cancelar Reserva");
		panelCentro.add(btnCancelar);

		frame.getContentPane().add(panelCentro, BorderLayout.CENTER);

		// Tabla de reservas en la parte inferior
		tablaReservas = new JTable();
		JScrollPane scrollTabla = new JScrollPane(tablaReservas);
		frame.getContentPane().add(scrollTabla, BorderLayout.SOUTH);
	}

	// Métodos getter para el controlador
	public JFrame getFrame() {
		return frame;
	}

	public JTable getTablaReservas() {
		return tablaReservas;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public JComboBox<Object> getComboUsuarios() {
		return comboUsuarios;
	}

	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(frame, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
	}

	public void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
