package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JButton;

public class InformeUsoInstalacionesView {

	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rBMes;
	private JRadioButton rBCuatrimestre;
	private JRadioButton rBAño;
	private JRadioButton rBPersonalizado;
	private JComboBox cBMesMes;
	private JComboBox cBAñoMes;
	private JComboBox cBCuatrimestreMes;
	private JComboBox cBCuatrimestreAño;
	private JComboBox cBAño;
	private JComboBox cBPersonalizadoMesInicio;
	private JComboBox cBPersonalizadoAñoInicio;
	private JComboBox cBPersonalizadoMesFin;
	private JComboBox cBPersonalizadoAñoFin;
	private JButton bCerrar;
	private JButton bInforme;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InformeUsoInstalacionesView window = new InformeUsoInstalacionesView();
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
	public InformeUsoInstalacionesView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 756, 466);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Seleccionar periodo:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(34, 37, 178, 33);
		frame.getContentPane().add(lblNewLabel);
		
		rBMes = new JRadioButton("Mes");
		buttonGroup.add(rBMes);
		rBMes.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rBMes.setBounds(218, 42, 74, 26);
		frame.getContentPane().add(rBMes);
		
		rBCuatrimestre = new JRadioButton("Cuatrimestre");
		buttonGroup.add(rBCuatrimestre);
		rBCuatrimestre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rBCuatrimestre.setBounds(305, 42, 127, 26);
		frame.getContentPane().add(rBCuatrimestre);
		
		rBAño = new JRadioButton("Año");
		buttonGroup.add(rBAño);
		rBAño.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rBAño.setBounds(455, 41, 90, 26);
		frame.getContentPane().add(rBAño);
		
		rBPersonalizado = new JRadioButton("Personalizado");
		buttonGroup.add(rBPersonalizado);
		rBPersonalizado.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rBPersonalizado.setBounds(547, 39, 138, 33);
		frame.getContentPane().add(rBPersonalizado);
		
		JLabel lblNewLabel_1 = new JLabel("Mes: ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(34, 98, 74, 26);
		frame.getContentPane().add(lblNewLabel_1);
		
		cBMesMes = new JComboBox();
		cBMesMes.setBounds(150, 101, 90, 22);
		frame.getContentPane().add(cBMesMes);
		
		cBAñoMes = new JComboBox();
		cBAñoMes.setBounds(276, 101, 90, 22);
		frame.getContentPane().add(cBAñoMes);
		
		JLabel lblNewLabel_2 = new JLabel("Cuatrimestre:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_2.setBounds(34, 135, 120, 22);
		frame.getContentPane().add(lblNewLabel_2);
		
		cBCuatrimestreMes = new JComboBox();
		cBCuatrimestreMes.setBounds(150, 136, 90, 22);
		frame.getContentPane().add(cBCuatrimestreMes);
		
		cBCuatrimestreAño = new JComboBox();
		cBCuatrimestreAño.setBounds(276, 136, 90, 22);
		frame.getContentPane().add(cBCuatrimestreAño);
		
		JLabel lblNewLabel_3 = new JLabel("Año:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3.setBounds(34, 174, 48, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		cBAño = new JComboBox();
		cBAño.setBounds(150, 171, 90, 22);
		frame.getContentPane().add(cBAño);
		
		JLabel lblNewLabel_4 = new JLabel("Personalizado:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_4.setBounds(34, 206, 112, 22);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("De:");
		lblNewLabel_5.setBounds(150, 211, 30, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		cBPersonalizadoMesInicio = new JComboBox();
		cBPersonalizadoMesInicio.setBounds(181, 207, 74, 22);
		frame.getContentPane().add(cBPersonalizadoMesInicio);
		
		cBPersonalizadoAñoInicio = new JComboBox();
		cBPersonalizadoAñoInicio.setBounds(271, 207, 74, 22);
		frame.getContentPane().add(cBPersonalizadoAñoInicio);
		
		JLabel lblNewLabel_6 = new JLabel("Hasta: ");
		lblNewLabel_6.setBounds(377, 211, 48, 14);
		frame.getContentPane().add(lblNewLabel_6);
		
		cBPersonalizadoMesFin = new JComboBox();
		cBPersonalizadoMesFin.setBounds(435, 207, 74, 22);
		frame.getContentPane().add(cBPersonalizadoMesFin);
		
		cBPersonalizadoAñoFin = new JComboBox();
		cBPersonalizadoAñoFin.setBounds(519, 207, 90, 22);
		frame.getContentPane().add(cBPersonalizadoAñoFin);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 256, 754, 2);
		frame.getContentPane().add(separator);
		
		bCerrar = new JButton("Cerrar");
		bCerrar.setBounds(643, 397, 91, 23);
		frame.getContentPane().add(bCerrar);
		
		bInforme = new JButton("Generar Informe");
		bInforme.setBounds(487, 397, 146, 23);
		frame.getContentPane().add(bInforme);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JRadioButton getrBMes() {
		return rBMes;
	}

	public void setrBMes(JRadioButton rBMes) {
		this.rBMes = rBMes;
	}

	public JRadioButton getrBCuatrimestre() {
		return rBCuatrimestre;
	}

	public void setrBCuatrimestre(JRadioButton rBCuatrimestre) {
		this.rBCuatrimestre = rBCuatrimestre;
	}

	public JRadioButton getrBAño() {
		return rBAño;
	}

	public void setrBAño(JRadioButton rBAño) {
		this.rBAño = rBAño;
	}

	public JRadioButton getrBPersonalizado() {
		return rBPersonalizado;
	}

	public void setrBPersonalizado(JRadioButton rBPersonalizado) {
		this.rBPersonalizado = rBPersonalizado;
	}

	public JComboBox getcBMesMes() {
		return cBMesMes;
	}

	public void setcBMesMes(JComboBox cBMesMes) {
		this.cBMesMes = cBMesMes;
	}

	public JComboBox getcBAñoMes() {
		return cBAñoMes;
	}

	public void setcBAñoMes(JComboBox cBAñoMes) {
		this.cBAñoMes = cBAñoMes;
	}

	public JComboBox getcBCuatrimestreMes() {
		return cBCuatrimestreMes;
	}

	public void setcBCuatrimestreMes(JComboBox cBCuatrimestreMes) {
		this.cBCuatrimestreMes = cBCuatrimestreMes;
	}

	public JComboBox getcBCuatrimestreAño() {
		return cBCuatrimestreAño;
	}

	public void setcBCuatrimestreAño(JComboBox cBCuatrimestreAño) {
		this.cBCuatrimestreAño = cBCuatrimestreAño;
	}

	public JComboBox getcBAño() {
		return cBAño;
	}

	public void setcBAño(JComboBox cBAño) {
		this.cBAño = cBAño;
	}

	public JComboBox getcBPersonalizadoMesInicio() {
		return cBPersonalizadoMesInicio;
	}

	public void setcBPersonalizadoMesInicio(JComboBox cBPersonalizadoMesInicio) {
		this.cBPersonalizadoMesInicio = cBPersonalizadoMesInicio;
	}

	public JComboBox getcBPersonalizadoAñoInicio() {
		return cBPersonalizadoAñoInicio;
	}

	public void setcBPersonalizadoAñoInicio(JComboBox cBPersonalizadoAñoInicio) {
		this.cBPersonalizadoAñoInicio = cBPersonalizadoAñoInicio;
	}

	public JComboBox getcBPersonalizadoMesFin() {
		return cBPersonalizadoMesFin;
	}

	public void setcBPersonalizadoMesFin(JComboBox cBPersonalizadoMesFin) {
		this.cBPersonalizadoMesFin = cBPersonalizadoMesFin;
	}

	public JComboBox getcBPersonalizadoAñoFin() {
		return cBPersonalizadoAñoFin;
	}

	public void setcBPersonalizadoAñoFin(JComboBox cBPersonalizadoAñoFin) {
		this.cBPersonalizadoAñoFin = cBPersonalizadoAñoFin;
	}

	public JButton getbCerrar() {
		return bCerrar;
	}

	public void setbCerrar(JButton bCerrar) {
		this.bCerrar = bCerrar;
	}

	public JButton getbInforme() {
		return bInforme;
	}

	public void setbInforme(JButton bInforme) {
		this.bInforme = bInforme;
	}
	
	
}
