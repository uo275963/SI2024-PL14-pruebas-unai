package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class CancelarReservaSocioView {

	private JFrame frame;
	private JTextField TFFecha;

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
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Fecha: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 68, 14);
		frame.getContentPane().add(lblNewLabel);
		
		TFFecha = new JTextField();
		TFFecha.setBounds(10, 36, 200, 20);
		frame.getContentPane().add(TFFecha);
		TFFecha.setColumns(10);
		
		JButton BMostrar = new JButton("Mostrar\r\n");
		BMostrar.setBounds(234, 35, 91, 23);
		frame.getContentPane().add(BMostrar);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
