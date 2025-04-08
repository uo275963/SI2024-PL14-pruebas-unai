package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class GenerarInformeSociosView {

	private JFrame frame;

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
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrmInformesocios(JFrame frmInformesocios) {
		this.frame = frmInformesocios;
	}

}
