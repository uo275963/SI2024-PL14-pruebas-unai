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
		
		JRadioButton RBMes = new JRadioButton("Mes");
		buttonGroup.add(RBMes);
		RBMes.setFont(new Font("Tahoma", Font.PLAIN, 13));
		RBMes.setBounds(218, 42, 74, 26);
		frame.getContentPane().add(RBMes);
		
		JRadioButton RBCuatrimestre = new JRadioButton("Cuatrimestre");
		buttonGroup.add(RBCuatrimestre);
		RBCuatrimestre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		RBCuatrimestre.setBounds(305, 42, 127, 26);
		frame.getContentPane().add(RBCuatrimestre);
		
		JRadioButton RBAño = new JRadioButton("Año");
		buttonGroup.add(RBAño);
		RBAño.setFont(new Font("Tahoma", Font.PLAIN, 13));
		RBAño.setBounds(455, 41, 90, 26);
		frame.getContentPane().add(RBAño);
		
		JRadioButton RBPersonalizado = new JRadioButton("Personalizado");
		buttonGroup.add(RBPersonalizado);
		RBPersonalizado.setFont(new Font("Tahoma", Font.PLAIN, 13));
		RBPersonalizado.setBounds(547, 39, 138, 33);
		frame.getContentPane().add(RBPersonalizado);
		
		JLabel lblNewLabel_1 = new JLabel("Mes: ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(34, 98, 74, 26);
		frame.getContentPane().add(lblNewLabel_1);
		
		JComboBox CBMesMes = new JComboBox();
		CBMesMes.setBounds(150, 101, 90, 22);
		frame.getContentPane().add(CBMesMes);
		
		JComboBox CBAñoMes = new JComboBox();
		CBAñoMes.setBounds(276, 101, 90, 22);
		frame.getContentPane().add(CBAñoMes);
		
		JLabel lblNewLabel_2 = new JLabel("Cuatrimestre:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_2.setBounds(34, 135, 120, 22);
		frame.getContentPane().add(lblNewLabel_2);
		
		JComboBox CBCuatrimestreMes = new JComboBox();
		CBCuatrimestreMes.setBounds(150, 136, 90, 22);
		frame.getContentPane().add(CBCuatrimestreMes);
		
		JComboBox CBCuatrimestreAño = new JComboBox();
		CBCuatrimestreAño.setBounds(276, 136, 90, 22);
		frame.getContentPane().add(CBCuatrimestreAño);
		
		JLabel lblNewLabel_3 = new JLabel("Año:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3.setBounds(34, 174, 48, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JComboBox CBAño = new JComboBox();
		CBAño.setBounds(150, 171, 90, 22);
		frame.getContentPane().add(CBAño);
		
		JLabel lblNewLabel_4 = new JLabel("Personalizado:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_4.setBounds(34, 206, 112, 22);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("De:");
		lblNewLabel_5.setBounds(150, 211, 30, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		JComboBox CBPersonalizadoMesInicio = new JComboBox();
		CBPersonalizadoMesInicio.setBounds(181, 207, 74, 22);
		frame.getContentPane().add(CBPersonalizadoMesInicio);
		
		JComboBox CBPersonalizadoAñoInicio = new JComboBox();
		CBPersonalizadoAñoInicio.setBounds(271, 207, 74, 22);
		frame.getContentPane().add(CBPersonalizadoAñoInicio);
		
		JLabel lblNewLabel_6 = new JLabel("Hasta: ");
		lblNewLabel_6.setBounds(377, 211, 48, 14);
		frame.getContentPane().add(lblNewLabel_6);
		
		JComboBox CBPersonalizadoMesFin = new JComboBox();
		CBPersonalizadoMesFin.setBounds(435, 207, 74, 22);
		frame.getContentPane().add(CBPersonalizadoMesFin);
		
		JComboBox CBPersonalizadoAñoFin = new JComboBox();
		CBPersonalizadoAñoFin.setBounds(519, 207, 90, 22);
		frame.getContentPane().add(CBPersonalizadoAñoFin);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 256, 754, 2);
		frame.getContentPane().add(separator);
		
		JButton BCerrar = new JButton("Cerrar");
		BCerrar.setBounds(643, 397, 91, 23);
		frame.getContentPane().add(BCerrar);
		
		JButton BInforme = new JButton("Generar Informe");
		BInforme.setBounds(487, 397, 146, 23);
		frame.getContentPane().add(BInforme);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
