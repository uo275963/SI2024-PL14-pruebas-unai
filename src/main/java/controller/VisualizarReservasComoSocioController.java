package controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import giis.demo.util.SwingUtil;
import model.VisualizarReservasComoSocioModel;
import view.VisualizarReservasComoSocioView;

public class VisualizarReservasComoSocioController {
	
	private int horasMaximasConsecutivas;

	private VisualizarReservasComoSocioModel model;
	private VisualizarReservasComoSocioView view;
	private int idsocio = 1;

	public VisualizarReservasComoSocioController(VisualizarReservasComoSocioModel m,
			VisualizarReservasComoSocioView v) {

		this.model = m;
		this.view = v;
		this.initview();
	}
	
	private void initview() {
		// Crear un modelo de tabla vacío con las columnas "Horario" y "Estado"
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("Horario");
		modelo.addColumn("Estado");

		// Asignar el modelo vacío a la tabla en la vista
		this.view.setTablaReservasModel(modelo);

		this.obtenerNombreInstalaciones();

		this.view.getFrame().setVisible(true);

	}
	

	public void initController() {
		
		
		this.view.getTablaReservas().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);


		this.view.getBtnBuscar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> {
			actualizarTabla(view.getFTFFecha().getText(),
					view.getCBInstalaciones().getSelectedItem().toString());
			actualizarAforo(view.getCBInstalaciones().getSelectedItem().toString());
		}));

		this.view.getTablaReservas().getSelectionModel().addListSelectionListener(e -> {
		    if (!e.getValueIsAdjusting()) {
		        int[] selectedRows = this.view.getTablaReservas().getSelectedRows();
		        if (selectedRows.length > 0) {
		            int selectedRow = selectedRows[0];
		            String reservadoPor = (String) this.view.getTablaReservas().getValueAt(selectedRow, 2);
		            String horaSeleccionada = (String) this.view.getTablaReservas().getValueAt(selectedRow, 0);
		            String mensaje;

		            if (reservadoPor.equalsIgnoreCase("N/A")) {
		                mensaje = "Esta hora está disponible";
		            } else if (reservadoPor.equals("Socio")) {
		                // Obtener el DNI del socio que reservó la hora
		                String dniSocioReserva = this.model.getDniSocioReserva(this.view.getFTFFecha().getText(),
		                                                                     this.view.getCBInstalaciones().getSelectedItem().toString(),
		                                                                     horaSeleccionada);
		                // Obtener el nombre del socio que reservó la hora
		                String nombreSocioReserva = this.model.getNombreUsuarioPorDni(dniSocioReserva);

		                if (dniSocioReserva != null && dniSocioReserva.equals(this.model.getDniUsuarioPorId(idsocio))) {
		                    mensaje = "Reservado por " + this.model.getNombreUsuarioPorId(idsocio);
		                } else {
		                    mensaje = "Reservado por " + nombreSocioReserva;
		                }
		            } else {
		                mensaje = "Reservado para '" + reservadoPor + "'";
		            }
		            this.view.getTFDescripcion().setText(mensaje);
		        } else {
		            this.view.getTFDescripcion().setText("No se ha seleccionado ninguna hora.");
		        }
		    }
		});
		
		
		this.view.getBtnReservar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> realizarReserva()));


	

	}
	
	
	private boolean esReservaValida(String fechaSeleccionadaStr) {
	    // Obtener la fecha actual
	    LocalDate fechaActual = LocalDate.now();

	    // Convertir la fecha seleccionada por el usuario (formato: yyyy-MM-dd)
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate fechaSeleccionada = LocalDate.parse(fechaSeleccionadaStr, formatter);

	    if (ChronoUnit.DAYS.between(fechaActual, fechaSeleccionada) > 15) {
	        return false; // La fecha de la reserva es inválida
	    }

	    return true; // La fecha de la reserva es válida
	}

	
	
	private boolean esVisualizacionValida(String fechaSeleccionadaStr) {
	    // Obtener la fecha actual
	    LocalDate fechaActual = LocalDate.now();

	    // Convertir la fecha seleccionada por el usuario (formato: yyyy-MM-dd)
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate fechaSeleccionada = LocalDate.parse(fechaSeleccionadaStr, formatter);

	    // Comprobar si la fecha seleccionada está dentro de los 30 días
	    if (ChronoUnit.DAYS.between(fechaActual, fechaSeleccionada) > 30) {
	        return false; // La fecha de visualización es inválida
	    }

	    return true; // La fecha de visualización es válida
	}
	
	
	// Función para verificar si la fecha seleccionada no es anterior a la fecha actual
	private boolean esFechaValida(String fechaSeleccionadaStr) {
	    // Obtener la fecha actual
	    LocalDate fechaActual = LocalDate.now();

	    // Convertir la fecha seleccionada por el usuario (formato: yyyy-MM-dd)
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate fechaSeleccionada = LocalDate.parse(fechaSeleccionadaStr, formatter);

	    // Verificar si la fecha seleccionada es anterior a la fecha actual
	    return !fechaSeleccionada.isBefore(fechaActual);
	}
	
	
	private void realizarReserva() {
	    String fechaSeleccionadaStr = this.view.getFTFFecha().getText();

	    // Comprobar si la fecha de la reserva es válida
	    if (!esReservaValida(fechaSeleccionadaStr)) {
	        JOptionPane.showMessageDialog(null, "Las reservas se hacen con un tiempo máximo de 15 dias.", "Error", JOptionPane.ERROR_MESSAGE);
	        return; // Detener el proceso si la reserva no es válida
	    }

	    if (this.model.masdexhorasreservadas(idsocio)) {
	        JOptionPane.showMessageDialog(null, "No puede realizar la reserva porque ya tiene más de 15 horas reservadas en los últimos 15 días.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    // Comprobaciones previas
	    if (this.model.esUsuarioMoroso(idsocio)) {
	        JOptionPane.showMessageDialog(null, "No puede realizar reservas debido a impagos de la cuota.", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    int[] selectedRows = this.view.getTablaReservas().getSelectedRows();
	    if (selectedRows.length == 0) {
	        JOptionPane.showMessageDialog(null, "Por favor, seleccione al menos una hora para reservar.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    List<Integer> horasSeleccionadas = new ArrayList<>();
	    for (int row : selectedRows) {
	        String horaTexto = this.view.getTablaReservas().getValueAt(row, 0).toString();
	        int hora = Integer.parseInt(horaTexto.split(":")[0]); // Extrae solo la hora en formato entero
	        horasSeleccionadas.add(hora);
	    }

	    horasSeleccionadas.sort(Integer::compareTo);

	    for (int row : selectedRows) {
	        String estado = (String) this.view.getTablaReservas().getValueAt(row, 1);
	        if (!estado.equals("Disponible")) {
	            JOptionPane.showMessageDialog(null, "Una o más horas seleccionadas ya están reservadas.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	    }

	    for (int i = 0; i < horasSeleccionadas.size() - 2; i++) {
	        if (horasSeleccionadas.get(i) + 1 == horasSeleccionadas.get(i + 1) &&
	            horasSeleccionadas.get(i + 1) + 1 == horasSeleccionadas.get(i + 2)) {
	            JOptionPane.showMessageDialog(null, "No puede reservar más de dos horas consecutivas.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	    }

	    // Obtención de precios
	    String fecha = this.view.getFTFFecha().getText();
	    String instalacion = this.view.getCBInstalaciones().getSelectedItem().toString();
	    Double precioTotal = obtenerPrecioTotalReserva(horasSeleccionadas, instalacion); // Ahora calculamos con las horas seleccionadas

	    // Proceder con la reserva
	    int horasActuales = this.model.obtenerHorasReservadas(idsocio, fecha);
	    int horasNuevas = horasSeleccionadas.size();

	    if (horasActuales + horasNuevas > 4) {
	        JOptionPane.showMessageDialog(null, "No puede reservar más de 4 horas en un solo día.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    // Ventanas de métodos de pago
	    Object[] options = {"Confirmar reserva y pagar ahora", "Confirmar reserva y pagar al final del mes", "Cancelar"};
	    String mensaje = String.format("¿Está seguro de que desea confirmar la reserva?\n" +
	                                   "Horas seleccionadas: %d\n" +
	                                   "Precio total: %.2f €\n" +
	                                   "Seleccione el método de pago:", horasSeleccionadas.size(), precioTotal);
	    int opcionSeleccionada = JOptionPane.showOptionDialog(
	            null,
	            mensaje,
	            "Confirmar Reserva y Pago",
	            JOptionPane.DEFAULT_OPTION,
	            JOptionPane.QUESTION_MESSAGE,
	            null,
	            options,
	            options[0]); // Poner la opción predeterminada como "Confirmar reserva y pagar ahora"

	    if (opcionSeleccionada == 2) {
	        // Si el usuario selecciona "Cancelar"
	        JOptionPane.showMessageDialog(null, "La reserva ha sido cancelada.", "Reserva Cancelada", JOptionPane.INFORMATION_MESSAGE);
	        return; // Salir de la función
	    }

	    boolean pagado = false;
	    String fechaPago = null;

	    // Dependiendo de la opción seleccionada, procesamos el pago
	    if (opcionSeleccionada == 0) { // Pagar ahora
	        pagado = true;  // Marcar la reserva como pagada
	    } else if (opcionSeleccionada == 1) { // Pagar al final del mes
	        pagado = false;
	        fechaPago = obtenerFechaPago(); // Establecer la fecha de pago al 25 del mes
	    }

	    // Mostrar ventana con los datos de la reserva (Recibo)
	    StringBuilder fechaHora = new StringBuilder(fecha);
	    for (Integer hora : horasSeleccionadas) {
	        fechaHora.append(" " + String.format("%02d:00", hora)); // Formatear la hora
	    }
	    String nombreUsuario = this.model.getNombreUsuarioPorId(idsocio);  // Obtener el nombre del usuario con id = 1

	    String recibo = String.format(
	            "**RESERVA**\n\n" +
	            "Nombre: %s\n" +
	            "Instalación: %s\n" +
	            "Fecha y hora: %s\n" +
	            "Horas reservadas: %d\n" +
	            "Precio total: %.2f €\n" +
	            "Método de pago: %s\n\n" +
	            "¡Gracias por su reserva!",
	            nombreUsuario, 
	            instalacion, 
	            fechaHora, 
	            horasSeleccionadas.size(), 
	            precioTotal, 
	            (opcionSeleccionada == 0) ? "Pago inmediato" : "Pago a fin de mes"
	    );

	    JOptionPane.showMessageDialog(null, recibo, "Recibo de Reserva", JOptionPane.INFORMATION_MESSAGE);

	    // Realizamos las reservas de las horas
	    int i = 0;
	    while (i < horasSeleccionadas.size()) {
	        int horaInicio = horasSeleccionadas.get(i);
	        int horaFin = horaInicio + 1;

	        if (i + 1 < horasSeleccionadas.size() && horasSeleccionadas.get(i + 1) == horaFin) {
	            horaFin++;
	            i++;
	        }

	        String horaInicioStr = String.format("%02d:00", horaInicio);
	        String horaFinStr = String.format("%02d:00", horaFin);

	        this.model.reservarHora(idsocio, fecha, instalacion, horaInicioStr, horaFinStr, pagado);

	        i++;

	        Integer idReserva = this.model.obtenerIdReserva(idsocio, fecha, instalacion, horaInicioStr);
	        if (idReserva != null) {
	            if (pagado) {
	                registrarPago(idReserva, idsocio, precioTotal, pagado,null);
	            } else {
	                registrarPago(idReserva, idsocio, precioTotal, pagado, fechaPago); // Pasar la fecha de pago si es a cuota
	            }
	        }
	    }

	    // Actualizar la tabla con la nueva reserva
	    actualizarTabla(fecha, instalacion);

	    JOptionPane.showMessageDialog(null, "Reserva realizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	}

	// Método para obtener la fecha del próximo 25 del mes
	private String obtenerFechaPago() {
	    Calendar calendar = Calendar.getInstance();
	    int currentMonth = calendar.get(Calendar.MONTH);
	    int currentYear = calendar.get(Calendar.YEAR);
	    calendar.set(currentYear, currentMonth, 25); // Establecer al día 25 del mes actual

	    // Si la fecha ya pasó, se establece el 25 del mes siguiente
	    if (calendar.getTime().before(new Date())) {
	        calendar.add(Calendar.MONTH, 1);
	    }

	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    return dateFormat.format(calendar.getTime());
	}

	
	private boolean esFormatoFechaValido(String fecha) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    try {
	        LocalDate.parse(fecha, formatter);
	        return true; // La fecha es válida
	    } catch (DateTimeParseException e) {
	        return false; // La fecha tiene un formato incorrecto
	    }
	}
	
	
	
	
	
	private void registrarPago(int idReserva, int idsocio, double monto, boolean pagado, String fechaPago) {
	    // Crear una nueva entrada en la tabla de pagos
	    // Puedes usar tu modelo para insertar un nuevo pago, o un código específico para tu base de datos
	    String estadoPago = pagado ? "Pagado" : "Pendiente";

	    if (pagado) {
	        fechaPago = LocalDate.now().toString(); // Fecha actual en formato "yyyy-MM-dd"
	    }
	    
	    
	    // Llamamos al método para registrar el pago en la base de datos, pasando la fecha de pago
	    this.model.registrarPagoReserva(idsocio, idReserva, monto, fechaPago);
	}


	
	
	
    
    
  

	// Cambiar el método de obtener precio total para usar las horas seleccionadas
	public Double obtenerPrecioTotalReserva(List<Integer> horasSeleccionadas, String nombreInstalacion) {
	    // Paso 1: Obtener el precio por hora de la instalación
	    Double precioPorHora = this.model.getPrecioHoraInstalacion(nombreInstalacion);

	    // Paso 2: Calcular el precio total multiplicando el precio por hora por el número de horas seleccionadas
	    Double precioTotal = precioPorHora * horasSeleccionadas.size();

	    // Retornar el precio total
	    return precioTotal;
	}


	//Funcion para mostrar el aforo de una determinada instalación
	private void actualizarAforo(String instalacionSeleccionada) {
		// Validar que la instalación seleccionada no esté vacía
		if (instalacionSeleccionada != null && !instalacionSeleccionada.trim().isEmpty()) {
			// Obtener el aforo máximo de la instalación seleccionada
			Integer aforo = this.model.getAforoInstalacion(instalacionSeleccionada);

			// Actualizar la etiqueta con el aforo máximo
			if (aforo != null) {
				this.view.getLblAforo().setText(aforo.toString());
			} else {
				this.view.getLblAforo().setText("");
			}
		}
	}

	
	

	//Funcion para obtener los nombres de las instalaciones y poder insertarlos en el combobox
	public void obtenerNombreInstalaciones() {

		// Obtener los resultados de la consulta (nombre y precio por hora)
		List<Object[]> l = this.model.getNombreInstalaciones();

		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>();

		// Iterar sobre los resultados
		for (Object[] fila : l) {
			if (fila.length > 0 && fila[0] != null) {
				// Aquí estamos tomando solo el nombre para mostrar en el ComboBox
				String nombreInstalacion = fila[0].toString();
				modelo.addElement(nombreInstalacion);
			}
		}

		this.view.setCBReservasModel(modelo);

	}

	
	//Funcion para obtener el precio por hora de las instalaciones
	public void obtenerPrecioHora(String nombreInstalacion) {
		// Validar que el nombre de la instalación no esté vacío
		if (nombreInstalacion == null || nombreInstalacion.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por favor, seleccione una instalación.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Consultar el precio por hora de la instalación seleccionada
		Double precioHora = this.model.getPrecioHoraInstalacion(nombreInstalacion);

		// Verificar si se obtuvo el precio
		if (precioHora != null) {

			this.view.getTFPrecioHora().setText(String.format("%.2f", precioHora) + "€");
		} else {
			// Si no se encuentra el precio, mostrar un mensaje de error
			JOptionPane.showMessageDialog(null, "No se pudo obtener el precio de la instalación seleccionada.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	


	
	
	//Funcion principal de la clase , que se invoca al pulsar el boton de busqueda y que rellena la tabla con todos los parametros pasados
	//y muestra las reservas a partir de horas en los siguientes 30 dias
	
	public void actualizarTabla(String fecha, String instalacion) {
	    // Limpiar la tabla antes de mostrar nuevos datos
	    DefaultTableModel modelo = (DefaultTableModel) this.view.getTablaReservasModel();
	    modelo.setRowCount(0);
	    
	    String fechaSeleccionadaStr = this.view.getFTFFecha().getText();
	    
	 // Comprobación de formato correcto de fecha
	    if (!esFormatoFechaValido(fechaSeleccionadaStr)) {
	        JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use el formato correcto: YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
	        return; // Detener el proceso si la fecha es incorrecta
	    }

	    // Comprobar si la fecha de la visualización es válida (dentro de los próximos 30 días)
	    if (!esVisualizacionValida(fechaSeleccionadaStr)) {
	        JOptionPane.showMessageDialog(null, "No puedes visualizar o actualizar la reserva a una fecha más allá de 30 días desde la fecha actual.", "Error", JOptionPane.ERROR_MESSAGE);
	        return; // Detener el proceso si la fecha no es válida
	    }

	    
	 // Comprobar si la fecha seleccionada no es anterior a la fecha actual
	    if (!esFechaValida(fechaSeleccionadaStr)) {
	        JOptionPane.showMessageDialog(null, "La fecha seleccionada no puede ser anterior a la fecha actual.", "Error", JOptionPane.ERROR_MESSAGE);
	        return; // Detener el proceso si la fecha es anterior
	    }
	 
	    
	    //Mostrar el precio de la instalación seleccionada
	    Double precioHora = this.model.getPrecioHoraInstalacion(instalacion);
	    this.view.getTFPrecioHora().setText(precioHora.toString() + "€");

	    // Mostrar la fecha seleccionada en la cabecera de la tabla
	    this.view.getLblFechaTabla().setText(fecha);

	    // Obtener las reservas y actividades
	    List<Object[]> reservas = this.model.getReservarInstalaciones(fecha, instalacion);
	    List<Object[]> actividades = this.model.getReservarActividades(fecha, instalacion);

	    // Horarios disponibles
	    String[] horarios = { "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00" };

	    // Crear un Set para tener acceso rápido a las horas reservadas
	    Set<String> horariosReservados = new HashSet<>();
	    Map<String, String> reservadosPor = new HashMap<>();

	    // Añadir reservas de socios
	    for (Object[] reserva : reservas) {
	        if (reserva.length > 3 && reserva[0] != null && reserva[1] != null && reserva[4] != null) {
	            String horaInicio = reserva[0].toString();
	            String horaFin = reserva[1].toString();
	            String dniSocio = reserva[4].toString(); // El 'dni' del socio que hizo la reserva

	            // Añadir la hora de inicio
	            horariosReservados.add(horaInicio);
	            // Restar 1 a la hora de fin y agregarla
	            String horaFinMenosUno = calcularHoraAnterior(horaFin);
	            horariosReservados.add(horaFinMenosUno);

	            reservadosPor.put(horaInicio, "Socio");
	            reservadosPor.put(horaFinMenosUno, "Socio");
	        }
	    }

	    // Añadir actividades
	    for (Object[] actividad : actividades) {
	        if (actividad.length > 1 && actividad[0] != null && actividad[1] != null) {
	            String horaInicio = actividad[0].toString();
	            String nombreActividad = actividad[1].toString(); // El nombre de la actividad
	            horariosReservados.add(horaInicio); // Añadimos la actividad al conjunto de horarios reservados
	            reservadosPor.put(horaInicio, nombreActividad); // Guardar el nombre de la actividad
	        }
	    }

	    // Crear el modelo de la tabla
	    DefaultTableModel modelo1 = new DefaultTableModel();
	    modelo1.addColumn("Horario");
	    modelo1.addColumn("Estado");
	    modelo1.addColumn("Reservado por");

	    // Llenar la tabla con los horarios
	    for (String hora : horarios) {
	        String estado = "Disponible";
	        String reservadoPorUsuario = "N/A";

	        // Verificar si la hora está reservada
	        if (horariosReservados.contains(hora)) {
	            estado = "Reservado";
	            reservadoPorUsuario = reservadosPor.get(hora);
	        }

	        modelo1.addRow(new Object[] { hora, estado, reservadoPorUsuario });
	    }

	    // Establecer el modelo de la tabla con los datos actualizados
	    this.view.setTablaReservasModel(modelo1);
	}

	// Función auxiliar para calcular la hora anterior a la hora de fin
	private String calcularHoraAnterior(String horaFin) {
	    String[] parts = horaFin.split(":");
	    int hora = Integer.parseInt(parts[0]);
	    int minutos = Integer.parseInt(parts[1]);

	    // Si la hora es 00, la hora anterior será 23
	    if (hora == 0) {
	        hora = 23;
	    } else {
	        hora--; // Resta una hora
	    }

	    // Formatear la hora con el formato adecuado (HH:00)
	    return String.format("%02d:%02d", hora, minutos);
	}


/*
	
	public Double obtenerPrecioTotalReserva(int idSocio, String fecha, String nombreInstalacion) {
	    // Paso 1: Obtener el precio por hora de la instalación
	    Double precioPorHora = this.model.getPrecioHoraInstalacion(nombreInstalacion);
	    
	    // Paso 2: Obtener el número de horas reservadas para este socio en esta fecha
	    int horasReservadas = this.model.obtenerHorasReservadas(idSocio, fecha);

	    // Paso 3: Calcular el precio total multiplicando el precio por hora por el número de horas reservadas
	    Double precioTotal = precioPorHora * horasReservadas;

	    // Retornar el precio total
	    return precioTotal;
	}

*/
	
	
	

}
