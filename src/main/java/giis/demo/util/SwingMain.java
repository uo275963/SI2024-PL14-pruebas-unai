package giis.demo.util;

import java.awt.EventQueue;
import javax.swing.JFrame;

import controller.ReservarInstalacionParaActividadComoAdminController;
import controller.ReservarInstalacionParaSocioComoAdminController;
import controller.VisualizarPagosComoSocioController;
import controller.VisualizarReservasComoSocioController;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import giis.demo.tkrun.*;
import model.ReservarInstalacionParaActividadComoAdminModel;
import model.ReservarInstalacionParaSocioComoAdminModel;
import model.VisualizarPagosComoSocioModel;
import model.VisualizarReservasComoSocioModel;
import view.ReservarInstalacionParaActividadComoAdminView;
import view.ReservarInstalacionParaSocioComoAdminView;
import view.VisualizarPagosComoSocioView;
import view.VisualizarReservasComoSocioView;
import diego_Actividad.*;
import diego_periodoInscripcion.*;
import unai.lista_actividades.*;
import unai.ver_reservas.*;

import model.VisualizarReservasComoSocioModel;
import view.VisualizarReservasComoSocioView;



/**
 * Punto de entrada principal que incluye botones para la ejecucion de las pantallas 
 * de las aplicaciones de ejemplo
 * y acciones de inicializacion de la base de datos.
 * No sigue MVC pues es solamente temporal para que durante el desarrollo se tenga posibilidad
 * de realizar acciones de inicializacion
 */
public class SwingMain {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() { //NOSONAR codigo autogenerado
			public void run() {
				try {
					SwingMain window = new SwingMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace(); //NOSONAR codigo autogenerado
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SwingMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Main");
		frame.setBounds(0, 0, 327, 324);
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		/*
		JButton btnEjecutarTkrun = new JButton("Ejecutar giis.demo.tkrun");
		btnEjecutarTkrun.addActionListener(new ActionListener() { //NOSONAR codigo autogenerado
			public void actionPerformed(ActionEvent e) {
				CarrerasController controller=new CarrerasController(new CarrerasModel(), new CarrerasView());
				controller.initController();
			}
		});
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.getContentPane().add(btnEjecutarTkrun);
*/		
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));


		
			
		JButton btnInicializarBaseDeDatos = new JButton("Inicializar Base de Datos en Blanco");
		btnInicializarBaseDeDatos.addActionListener(new ActionListener() { //NOSONAR codigo autogenerado
			public void actionPerformed(ActionEvent e) {
				Database db=new Database();
				db.createDatabase(false);
			}
		});
		frame.getContentPane().add(btnInicializarBaseDeDatos);
			
		JButton btnCargarDatosIniciales = new JButton("Cargar Datos Iniciales para Pruebas");
		btnCargarDatosIniciales.addActionListener(new ActionListener() { //NOSONAR codigo autogenerado
			public void actionPerformed(ActionEvent e) {
				Database db=new Database();
				db.createDatabase(false);
				db.loadDatabase();
			}
		});
		frame.getContentPane().add(btnCargarDatosIniciales);
		

		JButton btnVisualizarReservasComoSocio = new JButton("Visualizar/Reservar instalaciones como socio");
		btnVisualizarReservasComoSocio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				VisualizarReservasComoSocioController controller = new VisualizarReservasComoSocioController(new VisualizarReservasComoSocioModel(),new VisualizarReservasComoSocioView());
				controller.initController();
			}
		});
		frame.getContentPane().add(btnVisualizarReservasComoSocio);
		
		
		

		JButton btnActividades = new JButton("Añadir actividades");
		btnActividades.addActionListener(new ActionListener() { //NOSONAR codigo autogenerado
			public void actionPerformed(ActionEvent e) {
				ActividadController controller=new ActividadController(new ActividadModel(), new ActividadView());
				controller.initController();
			}
		});
		frame.getContentPane().add(btnActividades);
		
		JButton btnPeriodoInscripcion = new JButton("Añadir periodo de inscripcion");
		btnPeriodoInscripcion.addActionListener(new ActionListener() { //NOSONAR codigo autogenerado
			public void actionPerformed(ActionEvent e) {
				PeriodoController controller=new PeriodoController(new PeriodoModel(), new PeriodoView());
				controller.initController();
			}
		});
		frame.getContentPane().add(btnPeriodoInscripcion);
		
		JButton btnVerListaActividades = new JButton("Ver lista de actividades");
		btnVerListaActividades.addActionListener(new ActionListener() { //NOSONAR codigo autogenerado
			public void actionPerformed(ActionEvent e) {
				ListaActividadesController controller=new ListaActividadesController(new ListaActividadesModel(), new ListaActividadesView());
				controller.initController();
			}
		});
		frame.getContentPane().add(btnVerListaActividades);
		
		JButton btnReservasInstalacion = new JButton("Ver reservas de una instalacion");
		btnReservasInstalacion.addActionListener(new ActionListener() { //NOSONAR codigo autogenerado
			public void actionPerformed(ActionEvent e) {
				ReservaInstalacionController controller=new ReservaInstalacionController(new ReservaInstalacionModel(), new ReservaInstalacionView());
				controller.initController();
			}
		});
		frame.getContentPane().add(btnReservasInstalacion);
		
		JButton btnReservarInstalacionAdmin = new JButton("Reservar instalacion para actividad");
		btnReservarInstalacionAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReservarInstalacionParaActividadComoAdminController controller = new ReservarInstalacionParaActividadComoAdminController(new ReservarInstalacionParaActividadComoAdminModel(), new ReservarInstalacionParaActividadComoAdminView());
				controller.initController();
			}
		});
		frame.getContentPane().add(btnReservarInstalacionAdmin);
		
		JButton btnReservarSocioComoAdmin = new JButton("Reservar para socio como admin");
		btnReservarSocioComoAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReservarInstalacionParaSocioComoAdminController controller = new ReservarInstalacionParaSocioComoAdminController(new ReservarInstalacionParaSocioComoAdminModel(), new ReservarInstalacionParaSocioComoAdminView());
				controller.initController();
			}
		});
		frame.getContentPane().add(btnReservarSocioComoAdmin);
		
		JButton btnVerPagosComoSocio = new JButton("Ver pagos como socio");
		btnVerPagosComoSocio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualizarPagosComoSocioController controller = new VisualizarPagosComoSocioController(new VisualizarPagosComoSocioModel(), new VisualizarPagosComoSocioView());
				controller.initController();
			}
		});
		frame.getContentPane().add(btnVerPagosComoSocio);

		/*
		JButton btnVisualizarReservasComoSocio = new JButton("Ver reservas como socio");
		btnVisualizarReservasComoSocio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				VisualizarReservasComoSocioController controller = new VisualizarReservasComoSocioController(new VisualizarReservasComoSocioModel(),new VisualizarReservasComoSocioView());
				controller.initController();
			}
		});
		frame.getContentPane().add(btnVisualizarReservasComoSocio);
		*/
		
		


	}

	public JFrame getFrame() { return this.frame; }
	
}
