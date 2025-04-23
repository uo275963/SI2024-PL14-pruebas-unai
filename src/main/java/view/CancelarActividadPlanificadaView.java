package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class CancelarActividadPlanificadaView {

	private JFrame frame;
	private JTable TablaActividades;
	private JTable TablaCalendario;
	private JTable TablaInscritos;
	private JScrollPane scrollPane_1_1;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane;
	private JButton btnCerrar;
	private JButton btnCancelarActividad;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CancelarActividadPlanificadaView window = new CancelarActividadPlanificadaView();
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

	public void setFrame(JFrame frmCancelacinDeActividades) {
		this.frame = frmCancelacinDeActividades;
	}

	public JTable getTablaActividades() {
		return TablaActividades;
	}

	public void setTablaActividades(JTable tablaActividades) {
		TablaActividades = tablaActividades;
	}

	public JTable getTablaCalendario() {
		return TablaCalendario;
	}

	public void setTablaCalendario(JTable tablaCalendario) {
		TablaCalendario = tablaCalendario;
	}

	public JTable getTablaInscritos() {
		return TablaInscritos;
	}

	public void setTablaInscritos(JTable tablaInscritos) {
		TablaInscritos = tablaInscritos;
	}

	public JButton getBtnCerrar() {
		return btnCerrar;
	}

	public void setBtnCerrar(JButton btnCerrar) {
		this.btnCerrar = btnCerrar;
	}

	public JButton getBtnCancelarActividad() {
		return btnCancelarActividad;
	}

	public void setBtnCancelarActividad(JButton btnCancelarActividad) {
		this.btnCancelarActividad = btnCancelarActividad;
	}

	/**
	 * Create the application.
	 */
	public CancelarActividadPlanificadaView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Cancelación de actividades");
		frame.setBounds(100, 100, 932, 562);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Actividades planificadas");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 11, 169, 14);
		frame.getContentPane().add(lblNewLabel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 896, 163);
		frame.getContentPane().add(scrollPane);

		TablaActividades = new JTable();
		scrollPane.setViewportView(TablaActividades);

		JLabel lblNewLabel_1 = new JLabel("Calendario actividad");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(10, 210, 133, 14);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Inscritos");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(475, 210, 65, 14);
		frame.getContentPane().add(lblNewLabel_2);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 235, 367, 153);
		frame.getContentPane().add(scrollPane_1);

		TablaCalendario = new JTable();
		scrollPane_1.setViewportView(TablaCalendario);

		scrollPane_1_1 = new JScrollPane();
		scrollPane_1_1.setBounds(475, 235, 389, 153);
		frame.getContentPane().add(scrollPane_1_1);

		TablaInscritos = new JTable();
		scrollPane_1_1.setViewportView(TablaInscritos);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(570, 489, 89, 23);
		frame.getContentPane().add(btnCerrar);

		btnCancelarActividad = new JButton("Cancelar actividad");
		btnCancelarActividad.setBounds(669, 489, 195, 23);
		frame.getContentPane().add(btnCancelarActividad);
	}
}
