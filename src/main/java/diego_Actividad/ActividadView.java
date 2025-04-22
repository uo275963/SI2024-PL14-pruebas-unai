package diego_Actividad;

import java.awt.*;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;

public class ActividadView {
	private JFrame frame;
	private JTextField txtNombre;
	private JTextField txtDescripcion;
	private JTextField txtAforoMaximo;
	private JTextField txtCosteSocio;
	private JTextField txtCosteNoSocio;
	private JDateChooser dateFechaInicio;
	private JDateChooser dateFechaFin;
	private JTextField txtDias;
	private JTextField txtHoraInicio;
	private JTextField txtHoraFin;
	private JComboBox<Object> listaInstalaciones;
	private JComboBox<Object> listaPeriodosInscripcion;
	private JButton btnGuardar;
	private JTable tablaActividades;

	public ActividadView() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame("Gestión de Actividades");
		frame.setBounds(100, 100, 750, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		// Panel de encabezado con mensaje
		JPanel panelEncabezado = new JPanel();
		JLabel lblEncabezado = new JLabel("Introduzca los siguientes datos para añadir una actividad");
		lblEncabezado.setFont(new Font("Arial", Font.BOLD, 14));
		panelEncabezado.add(lblEncabezado);
		frame.getContentPane().add(panelEncabezado, BorderLayout.NORTH);

		// Panel de formulario
		JPanel panelFormulario = new JPanel();
		panelFormulario.setLayout(new GridLayout(7, 2, 10, 20)); // Mejor organización con más espacio

		// Nombre de la actividad
		panelFormulario.add(new JLabel("Nombre:"));
		txtNombre = new JTextField();
		panelFormulario.add(txtNombre);

		// Descripción
		panelFormulario.add(new JLabel("Descripción:"));
		txtDescripcion = new JTextField();
		panelFormulario.add(txtDescripcion);

		// Instalación
		panelFormulario.add(new JLabel("Instalación:"));
		listaInstalaciones = new JComboBox<>();
		panelFormulario.add(listaInstalaciones);

		// Aforo máximo
		panelFormulario.add(new JLabel("Aforo Máximo:"));
		txtAforoMaximo = new JTextField();
		panelFormulario.add(txtAforoMaximo);

		// Coste para socios
		panelFormulario.add(new JLabel("Coste Socio:"));
		txtCosteSocio = new JTextField();
		panelFormulario.add(txtCosteSocio);

		// Coste para no socios
		panelFormulario.add(new JLabel("Coste No Socio:"));
		txtCosteNoSocio = new JTextField();
		panelFormulario.add(txtCosteNoSocio);

		// Fecha de inicio
		panelFormulario.add(new JLabel("Fecha Inicio:"));
		dateFechaInicio = new JDateChooser();
		panelFormulario.add(dateFechaInicio);

		// Fecha de fin
		panelFormulario.add(new JLabel("Fecha Fin:"));
		dateFechaFin = new JDateChooser();
		panelFormulario.add(dateFechaFin);

		// Hora de inicio
		panelFormulario.add(new JLabel("Hora Inicio:"));
		txtHoraInicio = new JTextField();
		panelFormulario.add(txtHoraInicio);
		

		// Hora de fin
		JLabel label_1 = new JLabel("Hora Fin:");
		panelFormulario.add(label_1);
		txtHoraFin = new JTextField();
		panelFormulario.add(txtHoraFin);

		// Días
		JLabel label = new JLabel("Días:");
		panelFormulario.add(label);
		txtDias = new JTextField();
		panelFormulario.add(txtDias);
		

		// Periodo de inscripción
		panelFormulario.add(new JLabel("Periodo Inscripción:"));
		listaPeriodosInscripcion = new JComboBox<>();
		panelFormulario.add(listaPeriodosInscripcion);

		// Botón para guardar
		btnGuardar = new JButton("Guardar Actividad");
		panelFormulario.add(btnGuardar);

		frame.getContentPane().add(panelFormulario, BorderLayout.CENTER);

		// Tabla para mostrar actividades
		tablaActividades = new JTable();
		JScrollPane scrollTabla = new JScrollPane(tablaActividades);
		frame.getContentPane().add(scrollTabla, BorderLayout.SOUTH);
	}

	// Métodos para acceder a los componentes desde el controlador
	public JFrame getFrame() {
		return frame;
	}

	public JTextField getNombreField() {
		return txtNombre;
	}

	public JTextField getDescripcionField() {
		return txtDescripcion;
	}

	public JComboBox<Object> getListaInstalaciones() {
		return listaInstalaciones;
	}

	public JTextField getAforoMaximoField() {
		return txtAforoMaximo;
	}

	public JTextField getCosteSocioField() {
		return txtCosteSocio;
	}

	public JTextField getCosteNoSocioField() {
		return txtCosteNoSocio;
	}

	public JDateChooser getFechaInicioChooser() {
		return dateFechaInicio;
	}

	public JDateChooser getFechaFinChooser() {
		return dateFechaFin;
	}

	public JTextField getDiasField() {
		return txtDias;
	}

	public JTextField getHoraInicioField() {
		return txtHoraInicio;
	}

	public JTextField getHoraFinField() {
		return txtHoraFin;
	}

	public JComboBox<Object> getListaPeriodosInscripcion() {
		return listaPeriodosInscripcion;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public JTable getTablaActividades() {
		return tablaActividades;
	}

	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(frame, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
	}

	public void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
}