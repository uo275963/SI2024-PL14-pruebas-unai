package giis.demo.tkrun.ut;


import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import controller.ReservarInstalacionParaSocioComoAdminController;
import giis.demo.util.Database;
import model.ReservarInstalacionParaSocioComoAdminModel;
import view.ReservarInstalacionParaSocioComoAdminView;


public class ReservarInstalacionParaSocioComoAdminControllerTest {
	private static Database db = new Database();
	
	@BeforeEach
	public void setUp() {
		db.createDatabase(true);
		loadCleanDatabase(db);
	}
	@AfterEach
	public void tearDown() {
		
	}
	public static void loadCleanDatabase(Database db) {
		db.executeBatch(new String[] {
			// Configuración de límites
			"DELETE FROM CONFIGURACION;",
			"INSERT INTO CONFIGURACION (clave, valor) VALUES " +
			"('hora_apertura', '09:00')," +
			"('hora_cierre', '21:00')," +
			"('reserva_antelacion_max_dias', '15')," +
			"('min_horas_cancelacion', '24')," +
			"('max_horas_por_dia', '4')," +
			"('max_horas_seguidas', '2')," +
			"('max_horas_totales_reservadas', '10')," +
			"('max_recibos_pendientes_para_moroso', '2');",

			// Usuarios de prueba
			"DELETE FROM USUARIO;",
			"INSERT INTO USUARIO (nombre, dni, password, rol, estado, recibos_pendientes) VALUES " +
			"('Enrique García', '12345689X', 'password123', 'SOCIO', 'ACTIVO', 0)," +
			"('Carlos Sánchez', '11223344C', 'adminpass', 'ADMIN', 'ACTIVO', 0);",

			// Instalaciones disponibles
			"DELETE FROM INSTALACION;",
			"INSERT INTO INSTALACION (nombre, tipo, aforo_maximo, estado, precio_hora) VALUES " +
			"('Piscina Olímpica', 'piscina', 50, 'DISPONIBLE', 3.00);",

			// Reservas previas
			"DELETE FROM RESERVA_INSTALACION;",
			"INSERT INTO RESERVA_INSTALACION (usuario_id, instalacion_id, fecha, hora_inicio, hora_fin, pagado) VALUES " +
			"(1, 1, '2025-05-07', '10:00', '11:00', TRUE);"
		});
	}

	/*Aquí los diferentes test*/
	@Test
	public void testReservaExitosaPorAdmin() {
		ReservarInstalacionParaSocioComoAdminModel model = new ReservarInstalacionParaSocioComoAdminModel();
		ReservarInstalacionParaSocioComoAdminView view = new ReservarInstalacionParaSocioComoAdminView();
	    ReservarInstalacionParaSocioComoAdminController controller = new ReservarInstalacionParaSocioComoAdminController(model ,view);

	    // Datos del socio, instalación y franja horaria sin conflicto
	    int idSocio = 1; // Enrique García
	    int idInstalacion = 1; // Piscina Olímpica
	    String fecha = "2025-05-07"; // ya hay una reserva de 10:00-11:00
	    String horaInicio = "11:00";
	    String horaFin = "12:00";
	    Boolean pagado = true;

	    // Llamamos al método y comprobamos que devuelve true
	    boolean resultado = model.insertarReserva(idSocio, idInstalacion, fecha, horaInicio, horaFin, pagado);

	    assertEquals("La reserva debería ser exitosa", resultado);
	}
	@Test
	public void testReservaNoExitosaPorAdmin1() {
		ReservarInstalacionParaSocioComoAdminModel model = new ReservarInstalacionParaSocioComoAdminModel();
		ReservarInstalacionParaSocioComoAdminView view = new ReservarInstalacionParaSocioComoAdminView();
	    ReservarInstalacionParaSocioComoAdminController controller = new ReservarInstalacionParaSocioComoAdminController(model ,view);

	    // Datos del socio, instalación y franja horaria sin conflicto
	    int idSocio = 1; // Enrique García
	    int idInstalacion = 1; // Piscina Olímpica
	    String fecha = "2025-07-10"; // > 15 días
	    String horaInicio = "12:00";
	    String horaFin = "13:00";
	    Boolean pagado = true;

	    // Llamamos al método y comprobamos que devuelve true
	    boolean resultado = model.insertarReserva(idSocio, idInstalacion, fecha, horaInicio, horaFin, pagado);

	    assertEquals("La reserva no debería ser exitosa", resultado);
	}
	
	@Test
	public void testReservaNoExitosaPorAdmin2() {
		ReservarInstalacionParaSocioComoAdminModel model = new ReservarInstalacionParaSocioComoAdminModel();
		ReservarInstalacionParaSocioComoAdminView view = new ReservarInstalacionParaSocioComoAdminView();
	    ReservarInstalacionParaSocioComoAdminController controller = new ReservarInstalacionParaSocioComoAdminController(model ,view);

	    // Datos del socio, instalación y franja horaria sin conflicto
	    int idSocio = 1; // Enrique García
	    int idInstalacion = 1; // Piscina Olímpica
	    String fecha = "2025-02-25"; 
	    String horaInicio = "10:30";
	    String horaFin = "11:30";
	    Boolean pagado = true;

	    // Llamamos al método y comprobamos que devuelve true
	    boolean resultado = model.insertarReserva(idSocio, idInstalacion, fecha, horaInicio, horaFin, pagado);

	    assertEquals("La reserva no debería ser exitosa", resultado);
	}
	
	@Test
	public void testReservaNoExitosaPorAdmin3() {
		ReservarInstalacionParaSocioComoAdminModel model = new ReservarInstalacionParaSocioComoAdminModel();
		ReservarInstalacionParaSocioComoAdminView view = new ReservarInstalacionParaSocioComoAdminView();
	    ReservarInstalacionParaSocioComoAdminController controller = new ReservarInstalacionParaSocioComoAdminController(model ,view);

	    // Datos del socio, instalación y franja horaria sin conflicto
	    int idSocio = 3; // Enrique García
	    int idInstalacion = 1; // Piscina Olímpica
	    String fecha = "2025-02-28"; 
	    String horaInicio = "12:00";
	    String horaFin = "13:00";
	    Boolean pagado = true;

	    // Llamamos al método y comprobamos que devuelve true
	    boolean resultado = model.insertarReserva(idSocio, idInstalacion, fecha, horaInicio, horaFin, pagado);

	    assertEquals("La reserva no debería ser exitosa", resultado);
	}
	
	@Test
	public void testReservaNoExitosaPorAdmin4() {
		ReservarInstalacionParaSocioComoAdminModel model = new ReservarInstalacionParaSocioComoAdminModel();
		ReservarInstalacionParaSocioComoAdminView view = new ReservarInstalacionParaSocioComoAdminView();
	    ReservarInstalacionParaSocioComoAdminController controller = new ReservarInstalacionParaSocioComoAdminController(model ,view);

	    // Datos del socio, instalación y franja horaria sin conflicto
	    int idSocio = 5; // Enrique García
	    int idInstalacion = 1; // Piscina Olímpica
	    String fecha = "2025-02-28"; 
	    String horaInicio = "12:00";
	    String horaFin = "13:00";
	    Boolean pagado = true;

	    // Llamamos al método y comprobamos que devuelve true
	    boolean resultado = model.insertarReserva(idSocio, idInstalacion, fecha, horaInicio, horaFin, pagado);

	    assertEquals("La reserva no debería ser exitosa", resultado);
	}
	
	@Test
	public void testReservaNoExitosaPorAdmin5() {
		ReservarInstalacionParaSocioComoAdminModel model = new ReservarInstalacionParaSocioComoAdminModel();
		ReservarInstalacionParaSocioComoAdminView view = new ReservarInstalacionParaSocioComoAdminView();
	    ReservarInstalacionParaSocioComoAdminController controller = new ReservarInstalacionParaSocioComoAdminController(model ,view);

	    // Datos del socio, instalación y franja horaria sin conflicto
	    int idSocio = 1; // Enrique García
	    int idInstalacion = 1; // Piscina Olímpica
	    String fecha = "2025-02-28"; 
	    String horaInicio = "08:00";
	    String horaFin = "09:00";
	    Boolean pagado = true;

	    // Llamamos al método y comprobamos que devuelve true
	    boolean resultado = model.insertarReserva(idSocio, idInstalacion, fecha, horaInicio, horaFin, pagado);

	    assertEquals("La reserva no debería ser exitosa", resultado);
	}
	
	@Test
	public void testReservaNoExitosaPorAdmin6() {
		ReservarInstalacionParaSocioComoAdminModel model = new ReservarInstalacionParaSocioComoAdminModel();
		ReservarInstalacionParaSocioComoAdminView view = new ReservarInstalacionParaSocioComoAdminView();
	    ReservarInstalacionParaSocioComoAdminController controller = new ReservarInstalacionParaSocioComoAdminController(model ,view);

	    // Datos del socio, instalación y franja horaria sin conflicto
	    int idSocio = 1; // Enrique García
	    int idInstalacion = 1; // Piscina Olímpica
	    String fecha = "2025-02-28"; 
	    String horaInicio = "20:00";
	    String horaFin = "22:00";
	    Boolean pagado = true;

	    // Llamamos al método y comprobamos que devuelve true
	    boolean resultado = model.insertarReserva(idSocio, idInstalacion, fecha, horaInicio, horaFin, pagado);

	    assertEquals("La reserva no debería ser exitosa", resultado);
	}
	
	@Test
	public void testReservaNoExitosaPorAdmin7() {
		ReservarInstalacionParaSocioComoAdminModel model = new ReservarInstalacionParaSocioComoAdminModel();
		ReservarInstalacionParaSocioComoAdminView view = new ReservarInstalacionParaSocioComoAdminView();
	    ReservarInstalacionParaSocioComoAdminController controller = new ReservarInstalacionParaSocioComoAdminController(model ,view);

	    // Datos del socio, instalación y franja horaria sin conflicto
	    int idSocio = 1; // Enrique García
	    int idInstalacion = 1; // Piscina Olímpica
	    String fecha = "2025-02-28"; 
	    String horaInicio = "12:00";
	    String horaFin = "15:00";
	    Boolean pagado = true;

	    // Llamamos al método y comprobamos que devuelve true
	    boolean resultado = model.insertarReserva(idSocio, idInstalacion, fecha, horaInicio, horaFin, pagado);

	    assertEquals("La reserva no debería ser exitosa", resultado);
	}
	
	@Test
	public void testReservaNoExitosaPorAdmin8() {
		ReservarInstalacionParaSocioComoAdminModel model = new ReservarInstalacionParaSocioComoAdminModel();
		ReservarInstalacionParaSocioComoAdminView view = new ReservarInstalacionParaSocioComoAdminView();
	    ReservarInstalacionParaSocioComoAdminController controller = new ReservarInstalacionParaSocioComoAdminController(model ,view);

	    // Datos del socio, instalación y franja horaria sin conflicto
	    int idSocio = 1; // Enrique García
	    int idInstalacion = 1; // Piscina Olímpica
	    String fecha = "2025-02-28"; 
	    String horaInicio = "09:00";
	    String horaFin = "12:00";
	    Boolean pagado = true;

	    // Llamamos al método y comprobamos que devuelve true
	    boolean resultado = model.insertarReserva(idSocio, idInstalacion, fecha, horaInicio, horaFin, pagado);

	    assertEquals("La reserva no debería ser exitosa", resultado);
	}

	@Test
	public void testReservaNoExitosaPorAdmin9() {
		ReservarInstalacionParaSocioComoAdminModel model = new ReservarInstalacionParaSocioComoAdminModel();
		ReservarInstalacionParaSocioComoAdminView view = new ReservarInstalacionParaSocioComoAdminView();
	    ReservarInstalacionParaSocioComoAdminController controller = new ReservarInstalacionParaSocioComoAdminController(model ,view);

	    // Datos del socio, instalación y franja horaria sin conflicto
	    int idSocio = 2; // Enrique García
	    int idInstalacion = 2; // Piscina Olímpica
	    String fecha = "2025-02-26"; 
	    String horaInicio = "11:00";
	    String horaFin = "12:00";
	    Boolean pagado = true;

	    // Llamamos al método y comprobamos que devuelve true
	    boolean resultado = model.insertarReserva(idSocio, idInstalacion, fecha, horaInicio, horaFin, pagado);

	    assertEquals("La reserva no debería ser exitosa", resultado);
	}
	
	@Test
	public void testReservaNoExitosaPorAdmin10() {
		ReservarInstalacionParaSocioComoAdminModel model = new ReservarInstalacionParaSocioComoAdminModel();
		ReservarInstalacionParaSocioComoAdminView view = new ReservarInstalacionParaSocioComoAdminView();
	    ReservarInstalacionParaSocioComoAdminController controller = new ReservarInstalacionParaSocioComoAdminController(model ,view);

	    // Datos del socio, instalación y franja horaria sin conflicto
	    int idSocio = 4; // Enrique García
	    int idInstalacion = 1; // Piscina Olímpica
	    String fecha = "2025-02-28"; 
	    String horaInicio = "12:00";
	    String horaFin = "13:00";
	    Boolean pagado = true;

	    // Llamamos al método y comprobamos que devuelve true
	    boolean resultado = model.insertarReserva(idSocio, idInstalacion, fecha, horaInicio, horaFin, pagado);

	    assertEquals("La reserva no debería ser exitosa", resultado);
	}
	
}
