package diego_periodoInscripcion;

import java.awt.*;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;

public class PeriodoView {
	private JFrame frame;
	private JTextField txtNombre;
	private JDateChooser dateInicio;
	private JDateChooser dateFin;
	private JDateChooser dateFinNoSocios;
	private JButton btnGuardar;
	private JTable tablaPeriodos;
	private JComboBox<Object> listaPeriodos;

	public PeriodoView() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame("Gestión de Períodos");
		frame.setBounds(100, 100, 760, 760);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		JPanel panelFormulario = new JPanel();
		panelFormulario.setLayout(new GridLayout(6, 2, 5, 5));

		panelFormulario.add(new JLabel("Nombre:"));
		txtNombre = new JTextField();
		panelFormulario.add(txtNombre);

		panelFormulario.add(new JLabel("Fecha Inicio para socios:"));
		dateInicio = new JDateChooser();
		panelFormulario.add(dateInicio);

		panelFormulario.add(new JLabel("Fecha Fin para socios:"));
		dateFin = new JDateChooser();
		panelFormulario.add(dateFin);

		panelFormulario.add(new JLabel("Fecha Fin No Socios:"));
		dateFinNoSocios = new JDateChooser();
		panelFormulario.add(dateFinNoSocios);

		btnGuardar = new JButton("Guardar Período");
		panelFormulario.add(btnGuardar);

		frame.getContentPane().add(panelFormulario, BorderLayout.NORTH);

		tablaPeriodos = new JTable();
		JScrollPane scrollTabla = new JScrollPane(tablaPeriodos);
		frame.getContentPane().add(scrollTabla, BorderLayout.CENTER);

		listaPeriodos = new JComboBox<>();
	}

	// Métodos para acceder a los componentes desde el controlador
	public JFrame getFrame() {
		return frame;
	}

	public JTextField getNombreField() {
		return txtNombre;
	}

	public JDateChooser getFechaInicioChooser() {
		return dateInicio;
	}

	public JDateChooser getFechaFinChooser() {
		return dateFin;
	}

	public JDateChooser getFechaFinNoSociosChooser() {
		return dateFinNoSocios;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public JTable getTablaPeriodos() {
		return tablaPeriodos;
	}


	public JComboBox<Object> getListaPeriodos() {
		return listaPeriodos;
	}

	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(frame, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
	}

	public void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
