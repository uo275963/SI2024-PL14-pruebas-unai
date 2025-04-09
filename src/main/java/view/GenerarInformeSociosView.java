package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class GenerarInformeSociosView {

	private JFrame frame;
	private JRadioButton RBMes;
	private JRadioButton RBEstaciones;
	private JRadioButton RBAño;
	private JRadioButton RBPeriodoPersonalizado;
	private JComboBox CBEstaciones;
	private JComboBox CBEstacionesAño;
	private JComboBox CBAno;
	private JComboBox CBMesInicio;
	private JComboBox CBAñoInicio;
	private JComboBox CBMesFinal;
	private JComboBox CBAñoFinal;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JComboBox CBMes;
	private JComboBox CBMesAno;
	private JButton btnGenerarInforme;
	private JButton btnCerrar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenerarInformeSociosView window = new GenerarInformeSociosView();
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
	public GenerarInformeSociosView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	
	
	
	
	
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("InformeSocios");
		frame.setBounds(100, 100, 789, 547);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Escoga un periodo de tiempo:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 10, 192, 29);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Estaciones:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(10, 120, 103, 13);
		frame.getContentPane().add(lblNewLabel_1);
		
		CBEstaciones = new JComboBox();
		CBEstaciones.setFont(new Font("Tahoma", Font.PLAIN, 12));
		CBEstaciones.setModel(new DefaultComboBoxModel(new String[] {"Primavera", "Verano", "Otoño", "Invierno"}));
		CBEstaciones.setBounds(10, 140, 100, 30);
		frame.getContentPane().add(CBEstaciones);
		
		CBEstacionesAño = new JComboBox();
		CBEstacionesAño.setBounds(120, 140, 100, 30);
		frame.getContentPane().add(CBEstacionesAño);
		
		RBEstaciones = new JRadioButton("Estaciones");
		RBEstaciones.setFont(new Font("Tahoma", Font.PLAIN, 12));
		buttonGroup.add(RBEstaciones);
		RBEstaciones.setBounds(302, 15, 92, 21);
		frame.getContentPane().add(RBEstaciones);
		
		RBPeriodoPersonalizado = new JRadioButton("Periodo personalizado");
		RBPeriodoPersonalizado.setFont(new Font("Tahoma", Font.PLAIN, 12));
		buttonGroup.add(RBPeriodoPersonalizado);
		RBPeriodoPersonalizado.setBounds(494, 14, 163, 21);
		frame.getContentPane().add(RBPeriodoPersonalizado);
		
		RBMes = new JRadioButton("Mes");
		RBMes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		buttonGroup.add(RBMes);
		RBMes.setBounds(208, 15, 70, 21);
		frame.getContentPane().add(RBMes);
		
		RBAño = new JRadioButton("Año");
		RBAño.setFont(new Font("Tahoma", Font.PLAIN, 12));
		buttonGroup.add(RBAño);
		RBAño.setBounds(407, 15, 85, 21);
		frame.getContentPane().add(RBAño);
		
		JLabel lblNewLabel_2 = new JLabel("Mes");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(10, 50, 45, 13);
		frame.getContentPane().add(lblNewLabel_2);
		
		CBMes = new JComboBox();
		CBMes.setBounds(10, 70, 100, 30);
		frame.getContentPane().add(CBMes);
		
		JLabel lblNewLabel_3 = new JLabel("Año");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(10, 190, 45, 13);
		frame.getContentPane().add(lblNewLabel_3);
		
		CBAno = new JComboBox();
		CBAno.setBounds(10, 210, 100, 30);
		frame.getContentPane().add(CBAno);
		
		JLabel lblNewLabel_4 = new JLabel("Personalizado");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(10, 260, 85, 13);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Desde");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(10, 288, 45, 13);
		frame.getContentPane().add(lblNewLabel_5);
		
		CBMesInicio = new JComboBox();
		CBMesInicio.setBounds(66, 280, 100, 30);
		frame.getContentPane().add(CBMesInicio);
		
		CBAñoInicio = new JComboBox();
		CBAñoInicio.setBounds(176, 280, 100, 30);
		frame.getContentPane().add(CBAñoInicio);
		
		JLabel lblNewLabel_6 = new JLabel("hasta");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_6.setBounds(292, 288, 45, 13);
		frame.getContentPane().add(lblNewLabel_6);
		
		CBMesFinal = new JComboBox();
		CBMesFinal.setBounds(347, 280, 100, 30);
		frame.getContentPane().add(CBMesFinal);
		
		CBAñoFinal = new JComboBox();
		CBAñoFinal.setBounds(457, 280, 100, 30);
		frame.getContentPane().add(CBAñoFinal);
		
		btnGenerarInforme = new JButton("Generar informe");
		btnGenerarInforme.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnGenerarInforme.setBounds(146, 470, 150, 30);
		frame.getContentPane().add(btnGenerarInforme);
		
		btnCerrar = new JButton("Cerrar");
		btnCerrar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCerrar.setBounds(27, 470, 100, 30);
		frame.getContentPane().add(btnCerrar);
		
		CBMesAno = new JComboBox();
		CBMesAno.setBounds(120, 70, 100, 30);
		frame.getContentPane().add(CBMesAno);
	}
	
	
	
	


	public JComboBox getCBMesAno() {
		return CBMesAno;
	}

	public void setCBMesAno(JComboBox cBMesAno) {
		CBMesAno = cBMesAno;
	}

	public JButton getBtnGenerarInforme() {
		return btnGenerarInforme;
	}

	public void setBtnGenerarInforme(JButton btnGenerarInforme) {
		this.btnGenerarInforme = btnGenerarInforme;
	}

	public JButton getBtnCerrar() {
		return btnCerrar;
	}

	public void setBtnCerrar(JButton btnCerrar) {
		this.btnCerrar = btnCerrar;
	}

	public JComboBox getCBMes() {
		return CBMes;
	}

	public void setCBMes(JComboBox cBMes) {
		CBMes = cBMes;
	}

	public JRadioButton getRBMes() {
		return RBMes;
	}

	public void setRBMes(JRadioButton rBMes) {
		RBMes = rBMes;
	}

	public JRadioButton getRBEstaciones() {
		return RBEstaciones;
	}

	public void setRBEstaciones(JRadioButton rBEstaciones) {
		RBEstaciones = rBEstaciones;
	}

	public JRadioButton getRBAño() {
		return RBAño;
	}

	public void setRBAño(JRadioButton rBAño) {
		RBAño = rBAño;
	}

	public JRadioButton getRBPeriodoPersonalizado() {
		return RBPeriodoPersonalizado;
	}

	public void setRBPeriodoPersonalizado(JRadioButton rBPeriodoPersonalizado) {
		RBPeriodoPersonalizado = rBPeriodoPersonalizado;
	}

	public ButtonGroup getButtonGroup() {
		return buttonGroup;
	}

	public JComboBox getCBEstaciones() {
		return CBEstaciones;
	}

	public void setCBEstaciones(JComboBox cBEstaciones) {
		CBEstaciones = cBEstaciones;
	}

	public JComboBox getCBEstacionesAño() {
		return CBEstacionesAño;
	}

	public void setCBEstacionesAño(JComboBox cBEstacionesAño) {
		CBEstacionesAño = cBEstacionesAño;
	}

	public JComboBox getCBAno() {
		return CBAno;
	}

	public void setCBAño(JComboBox cBAno) {
		CBAno = cBAno;
	}

	public JComboBox getCBMesInicio() {
		return CBMesInicio;
	}

	public void setCBMesInicio(JComboBox cBMesInicio) {
		CBMesInicio = cBMesInicio;
	}

	public JComboBox getCBAñoInicio() {
		return CBAñoInicio;
	}

	public void setCBAñoInicio(JComboBox cBAñoInicio) {
		CBAñoInicio = cBAñoInicio;
	}

	public JComboBox getCBMesFinal() {
		return CBMesFinal;
	}

	public void setCBMesFinal(JComboBox cBMesFinal) {
		CBMesFinal = cBMesFinal;
	}

	public JComboBox getCBAñoFinal() {
		return CBAñoFinal;
	}

	public void setCBAñoFinal(JComboBox cBAñoFinal) {
		CBAñoFinal = cBAñoFinal;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrmInformesocios(JFrame frmInformesocios) {
		this.frame = frmInformesocios;
	}
}
