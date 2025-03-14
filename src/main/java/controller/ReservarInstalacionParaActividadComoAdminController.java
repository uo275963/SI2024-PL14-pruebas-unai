package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import giis.demo.util.Database;
import giis.demo.util.SwingUtil;
import model.ReservarInstalacionParaActividadComoAdminModel;
import view.ReservarInstalacionParaActividadComoAdminView;

public class ReservarInstalacionParaActividadComoAdminController {
	private ReservarInstalacionParaActividadComoAdminModel model;
	private ReservarInstalacionParaActividadComoAdminView view;
	
	public ReservarInstalacionParaActividadComoAdminController (ReservarInstalacionParaActividadComoAdminModel m, 
			ReservarInstalacionParaActividadComoAdminView v) {
		this.model=m;
		this.view=v;
		this.initView();
	}
	
	public void initView() {
		
		// Crear un modelo de tabla vacío 
 		DefaultTableModel instalaciones = new DefaultTableModel(new Object[] {"Instalaciones"}, 0);
 		DefaultTableModel actividades = new DefaultTableModel(new Object[] {"Actividades"}, 0);
		// Asignar el modelo vacío a la tabla en la vista


		
		this.meterInstalacionesyActividades();
		view.getFrame().setVisible(true);
		
		
	}
	
	public void initController() {
		view.getBConfirmar().addActionListener(e -> SwingUtil.exceptionWrapper(()-> {
			
			
			cargarReservasEnTabla(view.getTFFecha().getText(),
					view.getTFHoraInicio().getText(),
					view.getTFHoraFin().getText());
			confirmarReserva();
		}));
		view.getbComprobar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> {
			comprobarFormatos(view.getTFFecha().getText(), view.getTFHoraInicio().getText(), view.getTFHoraFin().getText());
		}));
		
		
		view.getcBInstalaciones().addActionListener(e -> SwingUtil.exceptionWrapper(() -> {
			String seleccion = (String) view.getcBInstalaciones().getSelectedItem();
			view.getTFInstalacion().setText(seleccion);
		}));
		
		view.getCBActividades().addActionListener(e -> SwingUtil.exceptionWrapper(() -> {
			String seleccion = (String) view.getCBActividades().getSelectedItem();
			view.getTFActividad().setText(seleccion);
		}));
		
				
	}
	
	// MÉTODO PARA METER LAS INSTALACIONES Y LAS ACTIVIDADES DE LA BASE DE DATOS
	public void meterInstalacionesyActividades() {
		List<Object[]> list = this.model.getInstalaciones();
		List<Object[]> list2 = this.model.getActividades();
		
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>();
		DefaultComboBoxModel<String> modelo2 = new DefaultComboBoxModel<String>();
		
		for(Object[] fila: list) {
			if(fila.length > 0 && fila[0] !=null) {
				String nombreInstalacion = fila[0].toString();
				modelo.addElement(nombreInstalacion);
			}
		}
		for(Object[] fila: list2) {
			if(fila.length>0 && fila[0] !=null) {
				String nombreActividad = fila[0].toString();
				modelo2.addElement(nombreActividad);
			}
		}
		this.view.setcBInstalacionesModel(modelo);
		this.view.setcBActividadesModel(modelo2);
	}
	
	
	/**
	public void meterreserva() {
	    // Obtener los valores de los campos de texto y la selección de la tabla
	    String fecha = view.getTFFecha().getText();
	    String horaInicio = view.getTFHoraInicio().getText();
	    String horaFin = view.getTFHoraFin().getText();
	    String nombreInstalacion = view.getTFInstalacion().getText();
	    
	    // Obtener la actividad seleccionada de la tabla
	    int filaSeleccionada = view.gettActividades().getSelectedRow();
	    if (filaSeleccionada != -1) {
	        String actividad = view.gettActividades().getValueAt(filaSeleccionada, 0).toString();

	        // Verificamos si todos los campos están llenos
	        if (fecha.isEmpty() || horaInicio.isEmpty() || horaFin.isEmpty() || nombreInstalacion.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos antes de confirmar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
	            return;
	        }

	        boolean b = comprobarFormatos(fecha, horaInicio, horaFin);
	        if(!b) return;
	        
	        // Obtener el id de la instalación desde el nombre de la instalación
	        List<Object[]> instalaciones = model.getidInstalacion(); // Método en el modelo para obtener las instalaciones
	        int instalacionId = -1;
	        for (Object[] row : instalaciones) {
	            int id = (int) row[0]; // Nombre de la instalación en la base de datos
	            // Comparar con el nombre de la instalación que tienes en el campo de texto
	            String nombreinstalacion = view.getTFInstalacion().getText();
	            if (nombreinstalacion.equals(view.getTFInstalacion().getText())) {
	                instalacionId = id; // Convertir a int
	                break;
	            }
	        }

	        if (instalacionId == -1) {
	            JOptionPane.showMessageDialog(null, "La instalación seleccionada no existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	     
	        
	        // Valores fijos para usuario_id y pagado
	        int usuarioId = 3;  // Usuario administrador
	        boolean pagado = true;  // Pagado
	        
	        // Llamar al método para insertar la reserva
	        boolean exito = model.insertarReserva(3, instalacionId, fecha, horaInicio, horaFin,true);

	        if (exito) {
	            JOptionPane.showMessageDialog(null, "Reserva realizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(null, "Error al realizar la reserva.", "Error", JOptionPane.ERROR_MESSAGE);
	        }

	    } else {
	        JOptionPane.showMessageDialog(null, "Por favor, selecciona una actividad.", "Advertencia", JOptionPane.WARNING_MESSAGE);
	    }
	}

	*/
	public boolean comprobarFormatos(String dia, String horainicio, String horafin) {
		if(dia == null || dia.trim().isEmpty() || !dia.matches("\\d{4}-\\d{2}-\\d{2}")) {
			// Verificamos el formato YYYY-MM-DD
			JOptionPane.showMessageDialog(null, "Por favor, ingrese una fecha válida en forato YYYY-MM-DD.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
			
		}
		
		try {
			// Convertir la fecha a LocalDate y comparar con la fecha actual
		    LocalDate fechaIngresada = LocalDate.parse(dia);
		    LocalDate hoy = LocalDate.now();
		    if (fechaIngresada.isBefore(hoy)) {
		        JOptionPane.showMessageDialog(null, "La fecha no puede ser anterior al día de hoy.", "Error", JOptionPane.ERROR_MESSAGE);
		        return false;
		    }
		} catch (DateTimeParseException e) {
		        JOptionPane.showMessageDialog(null, "Fecha inválida.", "Error", JOptionPane.ERROR_MESSAGE);
		        return false;
		}
		// Expresión regular para validar formato HH:MM
	    String regexHora = "^([01]\\d|2[0-3]):([0-5]\\d)$";

	    if (horainicio == null || horafin == null || !horainicio.matches(regexHora) || !horafin.matches(regexHora)) {
	        JOptionPane.showMessageDialog(null, "Formato de hora incorrecto. Use HH:MM en formato de 24 horas.", "Error", JOptionPane.ERROR_MESSAGE);
	        return false;
	    }

	    try {
	        // Convertir las horas a LocalTime
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	        LocalTime inicio = LocalTime.parse(horainicio, formatter);
	        LocalTime fin = LocalTime.parse(horafin, formatter);

	        // Definir los límites de horario permitidos
	        LocalTime horaMinima = LocalTime.of(9, 0);  // 09:00
	        LocalTime horaMaxima = LocalTime.of(22, 0); // 22:00

	        // Verificar si las horas están dentro del rango permitido
	        if (inicio.isBefore(horaMinima) || inicio.isAfter(horaMaxima) || fin.isBefore(horaMinima) || fin.isAfter(horaMaxima)) {
	            JOptionPane.showMessageDialog(null, "Las horas deben estar entre 09:00 y 22:00.", "Error", JOptionPane.ERROR_MESSAGE);
	            return false;
	        }
	        
	        // Verificar que la hora fin sea al menos 1 hora después de la inicial
	        if (fin.isBefore(inicio.plusHours(1))) {
	            JOptionPane.showMessageDialog(null, "La hora de fin debe ser al menos 1 hora después de la inicial.", "Error", JOptionPane.ERROR_MESSAGE);
	            return false;
	        } else {
	            JOptionPane.showMessageDialog(null, "Fecha y horas válidas.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	        }
	    } catch (DateTimeParseException e) {
	        JOptionPane.showMessageDialog(null, "Error al analizar las horas.", "Error", JOptionPane.ERROR_MESSAGE);
	        return false;
	    }
	    return true;
	}
	
	/**
		//Funcion para obtener los nombres de las instalaciones y poder insertarlos en el combobox
		public void obtenerNombreInstalaciones() {

			// Obtener los resultados de la consulta (nombre y precio por hora)
			List<Object[]> l = this.model.getInstalaciones();

			DefaultTableModel modelo = (DefaultTableModel) this.view.getTablaReservasModel();
			modelo.setRowCount(0);
			
			// Iterar sobre los resultados
			for (Object[] fila : l) {
				if (fila.length > 0 && fila[0] != null) {
					// Aquí estamos tomando solo el nombre para mostrar en el ComboBox
					String nombreInstalacion = fila[0].toString();
					modelo.addRow(new Object[] {nombreInstalacion});
				}
			}

			this.view.setTablaReservasModel(modelo);

		}
		
		//Funcion para obtener los nombres de las actividades y poder insertarlos en la tabla
		public void obtenerNombreActividades() {

			// Obtener los resultados de la consulta (nombre y precio por hora)
			List<Object[]> l = this.model.getActividades();

			DefaultTableModel modelo = new DefaultTableModel();
			modelo.addColumn("Actividades");
					
			// Iterar sobre los resultados
			for (Object[] fila : l) {
				if (fila.length > 0 && fila[0] != null) {
					// Aquí estamos tomando solo el nombre para mostrar en el ComboBox
					String nombreActividad = fila[0].toString();
					modelo.addRow(new Object[] {nombreActividad});
				}
			}

			this.view.setTablaActividadesModel(modelo);

		}
				
		// Método para seleccionar la instalación
		public void seleccionarInstalacion() {
			int filaSeleccionada = view.gettInstalaciones().getSelectedRow();

				if (filaSeleccionada != -1) { // Verifica que haya una fila seleccionada
					DefaultTableModel model = (DefaultTableModel) view.gettInstalaciones().getModel();
					String nombreInstalacion = view.gettInstalaciones().getValueAt(filaSeleccionada, 0).toString();
				    view.setTFInstalacion(nombreInstalacion);
				    model.removeRow(filaSeleccionada); // Elimina la fila seleccionada de la tabla
				} else {
				     JOptionPane.showMessageDialog(null, "Por favor, selecciona una instalación primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
				}
		}
		
		// Método para quitar la instalación
		public void quitarInstalacion() {
			String instalacion = view.getTFInstalacion().getText().trim();
	        
	        if (!instalacion.isEmpty()) {
	            DefaultTableModel model = (DefaultTableModel) view.gettInstalaciones().getModel();
	            model.addRow(new Object[]{instalacion}); // Agrega la instalación de nuevo
	            
	           view.setTFInstalacion("");; // Limpia el JTextField
	        } else {
	            JOptionPane.showMessageDialog(null, "No hay instalación seleccionada para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
	        }
	    }

		*/
		public void cargarReservasEnTabla(String fecha, String horainicio, String horafin) {
		    
			
			
			List<Object[]> reservas = model.getReservas();  // Obtener las reservas desde el modelo

		    // Crear un arreglo bidimensional de objetos para el JTable
		    Object[][] data = new Object[reservas.size()][7];  // Suponiendo que hay 6 columnas en la tabla RESERVA_INSTALACION

		    for (int i = 0; i < reservas.size(); i++) {
		        Object[] reserva = reservas.get(i);
		        
		        
		        // Llenar el arreglo de datos con la información de cada reserva
		        data[i][0] = reserva[0];  // usuario_id
		        data[i][1] = reserva[1];  // instalacion_id
		        data[i][2] = reserva[2];  // fecha
		        data[i][3] = reserva[3];  // hora_inicio
		        data[i][4] = reserva[4];  // hora_fin
		        data[i][5] = reserva[5];  // pagado
		        data[i][6] = reserva[6];
		    }

		    // Definir los nombres de las columnas
		    String[] columnNames = {"Reserva id", "Usuario", "Instalación","Fecha", "Hora Inicio", "Hora Fin", "Pagado"};

		    // Crear un DefaultTableModel con los datos obtenidos
		    DefaultTableModel modelTable = new DefaultTableModel(data, columnNames);
		    
		    // Establecer el modelo al JTable en la vista
		    view.gettReservas().setModel(modelTable);
		}
		
		public void confirmarReserva() {
		    // Obtener los datos de la vista
		    String fecha = view.getTFFecha().getText();
		    String horaInicio = view.getTFHoraInicio().getText();
		    String horaFin = view.getTFHoraFin().getText();
		    String instalacion = (String) view.getcBInstalaciones().getSelectedItem();
		    boolean pagado = true;
		    int usuarioId = 3;
		    
		    // Obtener el ID de la instalación seleccionada
		    int instalacionId = model.getInstalacionId(instalacion);
		    
		    if(!comprobarFormatos(fecha, horaInicio, horaFin)) {
			   return;
		    }

		    // Intentar insertar la reserva
		    boolean exito = model.insertarReserva(usuarioId, instalacionId, fecha, horaInicio, horaFin, pagado);
		    
		    if (exito) {
		        JOptionPane.showMessageDialog(null, "Reserva realizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
		    } else {
		        JOptionPane.showMessageDialog(null, "La instalación ya está ocupada en ese horario.", "Error", JOptionPane.ERROR_MESSAGE);
		        List<Object[]> reservas = model.getReservasInstalacion(fecha, horaInicio, horaFin, instalacionId);
		        String usuarioReserva = (String) reservas.get(0)[1];
		        view.gettAConflictos().setText("La instalacion esta ya ocupada por "+ usuarioReserva);
		    }
		}
		
		
}
