package giis.demo.tkrun.ut;


import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
import java.util.List;

import javax.swing.JOptionPane;

import controller.ReservarInstalacionParaSocioComoAdminController;
import giis.demo.util.Database;
import model.ReservarInstalacionParaSocioComoAdminModel;
import view.ReservarInstalacionParaSocioComoAdminView;
import unai.inscribir_no_socio.*;


public class InscribirNoSocioEnActividadTest {
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
			"('reserva_antelacion_max_dias', '16')," +
			"('min_horas_cancelacion', '24')," +
			"('max_horas_por_dia', '4')," +
			"('max_horas_seguidas', '2')," +
			"('max_horas_totales_reservadas', '10')," +
			"('max_recibos_pendientes_para_moroso', '2');", 

			// Usuarios de prueba
			"DELETE FROM USUARIO;",
			"INSERT INTO USUARIO (id, nombre, dni, password, rol, estado, recibos_pendientes) VALUES "
			+ "(1, 'Sara Luna', '12345678M', 'password123', 'SOCIO', 'ACTIVO', 0),"
			+ "(2, 'Jonay García', '99999999H', 'password123', 'NO_SOCIO', 'ACTIVO', 0);",

			// Instalaciones disponibles
			"DELETE FROM INSTALACION;",
			"INSERT INTO INSTALACION (id, nombre, tipo, aforo_maximo, estado, precio_hora) VALUES "
			+ "(1, 'Pista de Tenis 1', 'tenis', 4, 'DISPONIBLE', 4.50);",
			
			// Periodos disponibles
			"DELETE FROM PERIODO_INSCRIPCION; ",
			"INSERT INTO PERIODO_INSCRIPCION (id, nombre, fecha_inicio_socios, fecha_fin_socios, fecha_fin_no_socios) VALUES "
			+ "(1, 'Periodo abierto', '2025-04-01', '2025-05-01', '2025-06-01'),"
			+ "(2, 'Periodo cerrado', '2025-05-01', '2025-06-01', '2025-07-01');",

			// Actividades disponibles
			"DELETE FROM ACTIVIDAD; ",
			"INSERT INTO ACTIVIDAD (id, nombre, descripcion, instalacion_id, aforo_maximo, coste_socio, coste_no_socio, fecha_inicio, fecha_fin, dias, hora_inicio, hora_fin, periodo_inscripcion_id) VALUES "
			+ "(1, 'Torneo abierto de tenis 1', 'Competencia amateur', 1, 1, 5.00, 10.00, '2025-07-01', '2025-07-01', 'Sábado,Domingo', '09:00', '14:00', 1),"
			+ "(2, 'Torneo abierto de tenis 2', 'Competencia amateur', 1, 10, 5.00, 10.00, '2025-07-01', '2025-07-01', 'Sábado,Domingo', '09:00', '14:00', 1),"
			+ "(3, 'Torneo cerrado de tenis 1', 'Competencia amateur', 1, 10, 6.00, 12.00, '2025-07-01', '2025-07-01', 'Lunes,Martes', '09:00', '11:00', 2);",
			
			// Inscripciones previas
			"DELETE FROM INSCRIPCION_ACTIVIDAD; ",
			"INSERT INTO INSCRIPCION_ACTIVIDAD (usuario_id, actividad_id, pagado) VALUES "
			+ "(2, 1, TRUE);",
					
			"DELETE FROM LISTA_ESPERA",
		});
	}


	@Test
	public void testInscripcionNoSocio1() {
		InscribirNoSocioModel model = new InscribirNoSocioModel();
		String dni = "23859123T";
		String nombre = "María Ruiz";
		
        
		model.registrarNuevoNoSocio(dni, nombre);
   
		int usuarioId = model.getIdSocioPorDNI(dni);
		int actividadId= 1;
		
		model.inscribirNoSocioEnActividad(usuarioId, 1);
		
		
		// Comprobar que la inscripción existe en la base de datos
        String sqlVerificacion = "SELECT COUNT(*) FROM INSCRIPCION_ACTIVIDAD " +
                               "WHERE usuario_id = ? AND actividad_id = ? AND pagado = FALSE";
        
        List<Object[]> resultado = db.executeQueryArray(sqlVerificacion, usuarioId, actividadId);

        // Verificaciones
        assertNotNull(resultado, "El resultado no debería ser null");
        assertFalse(resultado.isEmpty(), "El resultado no debería estar vacío");
        
        Object[] fila = resultado.get(0);
        assertNotNull(fila, "La fila no debería ser null");
        assertTrue(fila.length > 0, "La fila debería tener al menos una columna");
        
        int count = Integer.parseInt(fila[0].toString());
        assertEquals(1, count, "Debería existir exactamente una inscripción");
        
     // Comprobar que el usuario está en la base de datos y no está duplicado
        sqlVerificacion = "SELECT COUNT(*) FROM USUARIO " +
        		"WHERE dni = ?";
        resultado = db.executeQueryArray(sqlVerificacion, dni);
        
     // Verificaciones
        assertNotNull(resultado, "El resultado no debería ser null");
        assertFalse(resultado.isEmpty(), "El resultado no debería estar vacío");
        
        fila = resultado.get(0);
        assertNotNull(fila, "La fila no debería ser null");
        assertTrue(fila.length > 0, "La fila debería tener al menos una columna");
       
        count = Integer.parseInt(fila[0].toString());
        assertEquals(1, count, "Debería existir exactamente un usuario con ese DNI");
		
	}
	
	@Test
	public void testInscripcionNoSocio2_1() {
		InscribirNoSocioModel model = new InscribirNoSocioModel();
		int usuarioId = 2; // Usuario ya existente, y ya inscrito en la actividad
		int actividadId= 1;
		String dni = "99999999H";
		String nombre = "Jonay García";
		

        // Registrar nuevo usuario
        model.registrarNuevoNoSocio(dni, nombre);  
		// Inscribirlo en la actividad
		model.inscribirNoSocioEnActividad(usuarioId, 1);
		
		
		
        
        // Comprobar que el usuario está en la base de datos y no está duplicado
        String sqlVerificacion = "SELECT COUNT(*) FROM USUARIO " +
        		"WHERE dni = ?";
        List<Object[]>resultado = db.executeQueryArray(sqlVerificacion, dni);
        
     // Verificaciones
        assertNotNull(resultado, "El resultado no debería ser null");
        assertFalse(resultado.isEmpty(), "El resultado no debería estar vacío");
        
        Object[] fila = resultado.get(0);
        assertNotNull(fila, "La fila no debería ser null");
        assertTrue(fila.length > 0, "La fila debería tener al menos una columna");
       
        int count = Integer.parseInt(fila[0].toString());
        assertEquals(1, count, "Debería existir exactamente un usuario con ese DNI");
        
        
		
	}
	
	@Test
	public void testInscripcionNoSocio2_2() {
		InscribirNoSocioModel model = new InscribirNoSocioModel();
		int usuarioId = 2; // Usuario ya existente, y ya inscrito en la actividad
		int actividadId= 1;
		String dni = "99999999H";
		String nombre = "Jonay García";
		

        // Registrar nuevo usuario
        model.registrarNuevoNoSocio(dni, nombre);  
		// Inscribirlo en la actividad
		model.inscribirNoSocioEnActividad(usuarioId, 1);
		
		
		// Comprobar que la inscripción está en la base de datos y no está duplicada
        String sqlVerificacion = "SELECT COUNT(*) FROM INSCRIPCION_ACTIVIDAD " +
                               "WHERE usuario_id = ? AND actividad_id = ?";
        
        List<Object[]> resultado = db.executeQueryArray(sqlVerificacion, usuarioId, actividadId);

        // Verificaciones
        assertNotNull(resultado, "El resultado no debería ser null");
        assertFalse(resultado.isEmpty(), "El resultado no debería estar vacío");
        
        Object[] fila = resultado.get(0);
        assertNotNull(fila, "La fila no debería ser null");
        assertTrue(fila.length > 0, "La fila debería tener al menos una columna");
        
        int count = Integer.parseInt(fila[0].toString());
        assertEquals(1, count, "Debería existir exactamente una inscripción");
        
        
        // Comprobar que el usuario está en la base de datos y no está duplicado
        sqlVerificacion = "SELECT COUNT(*) FROM USUARIO " +
        		"WHERE dni = ?";
        resultado = db.executeQueryArray(sqlVerificacion, dni);
        
     // Verificaciones
        assertNotNull(resultado, "El resultado no debería ser null");
        assertFalse(resultado.isEmpty(), "El resultado no debería estar vacío");
        
        fila = resultado.get(0);
        assertNotNull(fila, "La fila no debería ser null");
        assertTrue(fila.length > 0, "La fila debería tener al menos una columna");
       
        count = Integer.parseInt(fila[0].toString());
        assertEquals(1, count, "Debería existir exactamente un usuario con ese DNI");
        
        
		
	}
	
	@Test
	public void testInscripcionNoSocio3() {
		InscribirNoSocioModel model = new InscribirNoSocioModel();
		String dni = "58440181X";
		String nombre = "Unai San Juan";
		
        model.registrarNuevoNoSocio(dni, nombre);
          
		int usuarioId = model.getIdSocioPorDNI(dni);
		int actividadId= 1;
		
		model.agregarAListaEspera(usuarioId, actividadId);
		
		
		// Comprobar que la entrada en la lista de espera existe en la base de datos
        String sqlVerificacion = "SELECT COUNT(*) FROM LISTA_ESPERA " +
                               "WHERE usuario_id = ? AND actividad_id = ?";
        
        List<Object[]> resultado = db.executeQueryArray(sqlVerificacion, usuarioId, actividadId);

        // Verificaciones
        assertNotNull(resultado, "El resultado no debería ser null");
        assertFalse(resultado.isEmpty(), "El resultado no debería estar vacío");
        
        Object[] fila = resultado.get(0);
        assertNotNull(fila, "La fila no debería ser null");
        assertTrue(fila.length > 0, "La fila debería tener al menos una columna");
        
        int count = Integer.parseInt(fila[0].toString());
        assertEquals(1, count, "Debería existir exactamente una inscripción");
        
     // Comprobar que el usuario está en la base de datos y no está duplicado
        sqlVerificacion = "SELECT COUNT(*) FROM USUARIO " +
        		"WHERE dni = ?";
        resultado = db.executeQueryArray(sqlVerificacion, dni);
        
     // Verificaciones
        assertNotNull(resultado, "El resultado no debería ser null");
        assertFalse(resultado.isEmpty(), "El resultado no debería estar vacío");
        
        fila = resultado.get(0);
        assertNotNull(fila, "La fila no debería ser null");
        assertTrue(fila.length > 0, "La fila debería tener al menos una columna");
       
        count = Integer.parseInt(fila[0].toString());
        assertEquals(1, count, "Debería existir exactamente un usuario con ese DNI");
        

		
	}
	
	
	@Test
	public void testInscripcionNoSocio4_1() {
		InscribirNoSocioModel model = new InscribirNoSocioModel();
		int usuarioId = 2; // Usuario ya existente, y ya inscrito en la actividad
		int actividadId= 2;
		String dni = "99999999H";
		String nombre = "Jonay García";
		

        // Registrar nuevo usuario
        model.registrarNuevoNoSocio(dni, nombre);  
		// Inscribirlo en la actividad
		model.inscribirNoSocioEnActividad(usuarioId, actividadId);
		
		
		
        
        // Comprobar que el usuario está en la base de datos y no está duplicado
        String sqlVerificacion = "SELECT COUNT(*) FROM USUARIO " +
        		"WHERE dni = ?";
        List<Object[]>resultado = db.executeQueryArray(sqlVerificacion, dni);
        
     // Verificaciones
        assertNotNull(resultado, "El resultado no debería ser null");
        assertFalse(resultado.isEmpty(), "El resultado no debería estar vacío");
        
        Object[] fila = resultado.get(0);
        assertNotNull(fila, "La fila no debería ser null");
        assertTrue(fila.length > 0, "La fila debería tener al menos una columna");
       
        int count = Integer.parseInt(fila[0].toString());
        assertEquals(1, count, "Debería existir exactamente un usuario con ese DNI");
        
        
		
	}
	
	@Test
	public void testInscripcionNoSocio4_2() {
		InscribirNoSocioModel model = new InscribirNoSocioModel();
		int usuarioId = 2; // Usuario ya existente, y ya inscrito en la actividad
		int actividadId= 2;
		String dni = "99999999H";
		String nombre = "Jonay García";
		

        // Registrar nuevo usuario
        model.registrarNuevoNoSocio(dni, nombre);  
		// Inscribirlo en la actividad
		model.inscribirNoSocioEnActividad(usuarioId, actividadId);
		
		
        
		// Comprobar que la entrada en la lista de espera existe en la base de datos
        String sqlVerificacion = "SELECT COUNT(*) FROM INSCRIPCION_ACTIVIDAD " +
                               "WHERE usuario_id = ? AND actividad_id = ?";
        
        List<Object[]> resultado = db.executeQueryArray(sqlVerificacion, usuarioId, actividadId);

        // Verificaciones
        assertNotNull(resultado, "El resultado no debería ser null");
        assertFalse(resultado.isEmpty(), "El resultado no debería estar vacío");
        
        Object[] fila = resultado.get(0);
        assertNotNull(fila, "La fila no debería ser null");
        assertTrue(fila.length > 0, "La fila debería tener al menos una columna");
        
        int count = Integer.parseInt(fila[0].toString());
        assertEquals(1, count, "Debería existir exactamente una inscripción");
        
        
        
		
	}
	
	@Test
	public void testInscripcionNoSocio5_1() {
		InscribirNoSocioModel model = new InscribirNoSocioModel();
		int actividadId= 2;
		String dni = "99999999H";
		String nombre = "Unai San Juan";
		

        // Registrar nuevo usuario
        model.registrarNuevoNoSocio(dni, nombre);  
        
		int usuarioId = model.getIdSocioPorDNI(dni);

		// Inscribirlo en la actividad
		model.inscribirNoSocioEnActividad(usuarioId, actividadId);
		
		
		
        
        // Comprobar que el usuario está en la base de datos y no está duplicado
        String sqlVerificacion = "SELECT COUNT(*) FROM USUARIO " +
        		"WHERE dni = ?";
        List<Object[]>resultado = db.executeQueryArray(sqlVerificacion, dni);
        
     // Verificaciones
        assertNotNull(resultado, "El resultado no debería ser null");
        assertFalse(resultado.isEmpty(), "El resultado no debería estar vacío");
        
        Object[] fila = resultado.get(0);
        assertNotNull(fila, "La fila no debería ser null");
        assertTrue(fila.length > 0, "La fila debería tener al menos una columna");
       
        int count = Integer.parseInt(fila[0].toString());
        assertEquals(1, count, "Debería existir exactamente un usuario con ese DNI");
        
        
		
	}
	
	@Test
	public void testInscripcionNoSocio5_2() {
		InscribirNoSocioModel model = new InscribirNoSocioModel();
		int actividadId= 2;
		String dni = "99999999H";
		String nombre = "Unai San Juan";
		

		// 2. Registrar nuevo usuario no socio
        model.registrarNuevoNoSocio(dni, nombre);
        int usuarioId = model.getIdSocioPorDNI(dni);

        // 3. Inscribir en la actividad
        model.inscribirNoSocioEnActividad(usuarioId, actividadId);

     // 4. Obtener el usuario_id almacenado en INSCRIPCION_ACTIVIDAD
        List<Object[]> resultado = db.executeQueryArray(
            "SELECT usuario_id FROM INSCRIPCION_ACTIVIDAD " +
            "WHERE actividad_id = ?", 
            actividadId
        );

        // 5. Verificaciones
        assertFalse(resultado.isEmpty(), "Debería existir la inscripción");
        int usuarioIdEnInscripcion = (int) resultado.get(0)[0]; // Primer registro, primera columna
        assertEquals(usuarioId, usuarioIdEnInscripcion, 
            "El usuario_id en la inscripción debe coincidir con el ID del usuario registrado");
    }
        
	
	@Test
	public void testInscripcionNoSocio6() {
		InscribirNoSocioModel model = new InscribirNoSocioModel();
		String dni = "23859123T";
		String nombre = "María Ruiz";
		
        
		model.registrarNuevoNoSocio(dni, nombre);
   
		int usuarioId = model.getIdSocioPorDNI(dni);
		int actividadId= 3;
		
		model.inscribirNoSocioEnActividad(usuarioId, actividadId);
		
		
		// Comprobar que la inscripción existe en la base de datos
        String sqlVerificacion = "SELECT COUNT(*) FROM INSCRIPCION_ACTIVIDAD " +
                               "WHERE usuario_id = ? AND actividad_id = ?";
        
        List<Object[]> resultado = db.executeQueryArray(sqlVerificacion, usuarioId, actividadId);

        // Verificaciones
        assertNotNull(resultado, "El resultado no debería ser null");
        assertFalse(resultado.isEmpty(), "El resultado no debería estar vacío");
        
        Object[] fila = resultado.get(0);
        assertNotNull(fila, "La fila no debería ser null");
        assertTrue(fila.length > 0, "La fila debería tener al menos una columna");
        
        int count = Integer.parseInt(fila[0].toString());
        assertEquals(0, count, "No deberían existir inscripciones");
        
     // Comprobar que el usuario está en la base de datos y no está duplicado
        sqlVerificacion = "SELECT COUNT(*) FROM USUARIO " +
        		"WHERE dni = ?";
        resultado = db.executeQueryArray(sqlVerificacion, dni);
        
     // Verificaciones
        assertNotNull(resultado, "El resultado no debería ser null");
        assertFalse(resultado.isEmpty(), "El resultado no debería estar vacío");
        
        fila = resultado.get(0);
        assertNotNull(fila, "La fila no debería ser null");
        assertTrue(fila.length > 0, "La fila debería tener al menos una columna");
       
        count = Integer.parseInt(fila[0].toString());
        assertEquals(1, count, "Debería existir exactamente un usuario con ese DNI");
		
	}
	
	@Test
	public void testInscripcionNoSocio7_1() {
		InscribirNoSocioModel model = new InscribirNoSocioModel();
		String dni = "12345678M";
		String nombre = "Sara Luna";
		
        
		model.registrarNuevoNoSocio(dni, nombre);
   
		int usuarioId = model.getIdSocioPorDNI(dni);
		int actividadId= 2;
		
		model.inscribirNoSocioEnActividad(usuarioId, actividadId);
		
		
		// Comprobar que la inscripción existe en la base de datos
        String sqlVerificacion = "SELECT COUNT(*) FROM INSCRIPCION_ACTIVIDAD " +
                               "WHERE usuario_id = ? AND actividad_id = ?";
        
        List<Object[]> resultado = db.executeQueryArray(sqlVerificacion, usuarioId, actividadId);

        // Verificaciones
        assertNotNull(resultado, "El resultado no debería ser null");
        assertFalse(resultado.isEmpty(), "El resultado no debería estar vacío");
        
        Object[] fila = resultado.get(0);
        assertNotNull(fila, "La fila no debería ser null");
        assertTrue(fila.length > 0, "La fila debería tener al menos una columna");
        
        int count = Integer.parseInt(fila[0].toString());
        assertEquals(0, count, "No deberían existir inscripciones");
        
        
        
     // Comprobar que el usuario está en la base de datos y no está duplicado
        sqlVerificacion = "SELECT COUNT(*) FROM USUARIO " +
        		"WHERE dni = ?";
        resultado = db.executeQueryArray(sqlVerificacion, dni);
        
     // Verificaciones
        assertNotNull(resultado, "El resultado no debería ser null");
        assertFalse(resultado.isEmpty(), "El resultado no debería estar vacío");
        
        fila = resultado.get(0);
        assertNotNull(fila, "La fila no debería ser null");
        assertTrue(fila.length > 0, "La fila debería tener al menos una columna");
       
        count = Integer.parseInt(fila[0].toString());
        assertEquals(1, count, "Debería existir exactamente un usuario con ese DNI");
		
	}
	
	@Test
	public void testInscripcionNoSocio7_2() {
		InscribirNoSocioModel model = new InscribirNoSocioModel();
		String dni = "12345678M";
		String nombre = "Sara Luna";
		
        
		model.registrarNuevoNoSocio(dni, nombre);
   
		int usuarioId = model.getIdSocioPorDNI(dni);
		int actividadId= 2;
		
		model.inscribirNoSocioEnActividad(usuarioId, actividadId);
		
		
	
        
        
     // Comprobar que el usuario está en la base de datos y no está duplicado
        String sqlVerificacion = "SELECT COUNT(*) FROM USUARIO " +
        		"WHERE dni = ?";
        List<Object[]> resultado = db.executeQueryArray(sqlVerificacion, dni);
        
     // Verificaciones
        assertNotNull(resultado, "El resultado no debería ser null");
        assertFalse(resultado.isEmpty(), "El resultado no debería estar vacío");
        
        Object[] fila = resultado.get(0);
        assertNotNull(fila, "La fila no debería ser null");
        assertTrue(fila.length > 0, "La fila debería tener al menos una columna");
       
        int count = Integer.parseInt(fila[0].toString());
        assertEquals(1, count, "Debería existir exactamente un usuario con ese DNI");
		
	}
        
		
	}
	

