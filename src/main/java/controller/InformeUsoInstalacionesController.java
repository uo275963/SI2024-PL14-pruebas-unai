package controller;


import java.io.File;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import model.InformeUsoInstalacionesModel;
import view.InformeUsoInstalacionesView;

public class InformeUsoInstalacionesController {
	private InformeUsoInstalacionesModel model;
	private InformeUsoInstalacionesView view;
	
	// Variables globales para guardar las fechas
		private String fechaMes;
		private String fechaMesAno;
		private String fechaCuatrimestre;
		private String fechaCuatrimestreAno;
		private String fechaAño;
		private String fechaInicio;
		private String fechaFinal;
	
	public InformeUsoInstalacionesController(InformeUsoInstalacionesModel m, InformeUsoInstalacionesView v) {
		this.model=m;
		this.view=v;
		initView();
	}
	public void initView(){
		view.getFrame().setVisible(true);
	}
	public void initController() {
		deshabilitarComponentes();
		view.getrBMes().addActionListener(e -> escogerPeriodo());
		view.getrBCuatrimestre().addActionListener(e -> escogerPeriodo());
		view.getrBAño().addActionListener(e -> escogerPeriodo());
		view.getrBPersonalizado().addActionListener(e -> escogerPeriodo());
		view.getcBCuatrimestreAño().addActionListener(e -> cargarCuatrimestres());
		view.getbInforme().addActionListener(e -> {
			// Llamamos a obtenerFechasComoString() para obtener las fechas de inicio y fin
		    obtenerFechasComoString();

		    // Verificamos si las fechas fueron correctamente obtenidas
		    if (fechaInicio != null && fechaFinal != null) {
		        // Si las fechas están definidas, llamamos a generarInformeSocios con las fechas
		        generarInformeInstalaciones(fechaInicio, fechaFinal);
		    } else {
		        // Si alguna de las fechas no está definida, mostramos un mensaje de error
		        System.out.println("Error: Las fechas de inicio y fin no están definidas correctamente.");
		    }
		});
	}
	
	private void deshabilitarComponentes() {
		view.getcBMesMes().setEnabled(false);
		view.getcBAñoMes().setEnabled(false);
		view.getcBCuatrimestreMes().setEnabled(false);
		view.getcBCuatrimestreAño().setEnabled(false);
		view.getcBAño().setEnabled(false);
		view.getcBPersonalizadoMesInicio().setEnabled(false);
		view.getcBPersonalizadoAñoInicio().setEnabled(false);
		view.getcBPersonalizadoMesFin().setEnabled(false);
		view.getcBPersonalizadoAñoFin().setEnabled(false);
	}
	
	private void escogerPeriodo() {
		deshabilitarComponentes();
		cargarAños();
		cargarMeses();
		cargarCuatrimestres();
		 // Solo cargar cuatrimestres si hay un año seleccionado
	    if (view.getrBCuatrimestre().isSelected()) {
	        String anioSeleccionado = (String) view.getcBCuatrimestreAño().getSelectedItem();
	        if (anioSeleccionado != null && !anioSeleccionado.isEmpty()) {
	            cargarCuatrimestres();  // Cargar cuatrimestres solo si hay año seleccionado
	        }
	    }
		if(view.getrBMes().isSelected()) {
			view.getcBMesMes().setEnabled(true);
			view.getcBAñoMes().setEnabled(true);
		}else if(view.getrBCuatrimestre().isSelected()){
			view.getcBCuatrimestreMes().setEnabled(true);
			view.getcBCuatrimestreAño().setEnabled(true);
		}else if(view.getrBAño().isSelected()) {
			view.getcBAño().setEnabled(true);
		}else if(view.getrBPersonalizado().isSelected()) {
			view.getcBPersonalizadoMesInicio().setEnabled(true);
			view.getcBPersonalizadoAñoInicio().setEnabled(true);
			view.getcBPersonalizadoMesFin().setEnabled(true);
			view.getcBPersonalizadoAñoFin().setEnabled(true);
		}
	}
	
	private void cargarAños() {
		// Lista de años
		String[]años = {"2024","2025"};
		
		// Establecer el modelo para el ComboBox de años
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>(años);
		view.getcBAñoMes().setModel(modelo);
		view.getcBCuatrimestreAño().setModel(modelo);
		view.getcBAño().setModel(modelo);
		view.getcBPersonalizadoAñoFin().setModel(modelo);
		view.getcBPersonalizadoAñoInicio().setModel(modelo);
	}
	
	private void cargarMeses() {
		String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo",
				"Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
		
		// Establecer el modelo para el combox de meses
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(meses);
		view.getcBMesMes().setModel(model);
		view.getcBCuatrimestreMes().setModel(model);
		view.getcBPersonalizadoMesInicio().setModel(model);
		view.getcBPersonalizadoMesFin().setModel(model);
	}
	
	private void cargarCuatrimestres() {
	    // Obtener el año seleccionado en el JComboBox
	    String anioSeleccionado = (String) view.getcBCuatrimestreAño().getSelectedItem();
	    if (anioSeleccionado != null && !anioSeleccionado.isEmpty()) {
	        int anio = Integer.parseInt(anioSeleccionado);

	        // Obtener los cuatrimestres correspondientes a ese año
	        List<Object[]> cuatrimestres = model.obtenerNombreCuatrimestres(anio);

	        // Crear el modelo para el JComboBox
	        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();

	        // Añadir los cuatrimestres al modelo
	        for (Object[] fila : cuatrimestres) {
	            if (fila[0] != null) {
	                modelo.addElement(fila[0].toString());
	            }
	        }

	        // Asignar el modelo al JComboBox de cuatrimestres
	        view.getcBCuatrimestreMes().setModel(modelo);
	    }
	}
	
	public void obtenerFechasComoString() {
		fechaMes = null;
		fechaCuatrimestre = null;
		fechaCuatrimestreAno = null;
		fechaAño = null;
		fechaInicio = null;
		fechaFinal = null;
		
		if(view.getrBMes().isSelected()) {
			int mes = view.getcBMesMes().getSelectedIndex() + 1;
			String año = view.getcBAñoMes().getSelectedItem().toString();
			// Fecha inicial será el 1ro del mes seleccionado
			
			fechaInicio = año + "-" + String.format("%02d", mes) + "-01";
			// Fecha final será el último día del mes
			int ultimoDia = obtenerUltimoDiaDelMes(mes, Integer.parseInt(año));
			fechaFinal = año + "-" + String.format("%02d", mes) + "-" + String.format("%02d", ultimoDia);
			System.out.println("Fecha Mes: " + fechaInicio + " hasta " + fechaFinal);
		}else if(view.getrBCuatrimestre().isSelected()) {
			String año = view.getcBCuatrimestreAño().getSelectedItem().toString();
			int anioInt = Integer.parseInt(año);
			String cuatrimestre = view.getcBCuatrimestreMes().getSelectedItem().toString();

			List<Object[]> fechas = model.obtenerFechasCuatrimestre(cuatrimestre, anioInt);

			if (!fechas.isEmpty()) {
				fechaInicio = fechas.get(0)[0].toString();
			    fechaFinal = fechas.get(0)[1].toString();
			    System.out.println("Fecha Cuatrimestre: " + fechaInicio + " hasta " + fechaFinal);
			} else {
			    System.out.println("Error: No se encontró el cuatrimestre en la base de datos.");
			}
		}else if(view.getrBAño().isSelected()) {
			// Año seleccionado, desde el 1 de enero hasta el 31 de diciembre
			fechaAño = view.getcBAño().getSelectedItem().toString();
			fechaInicio = fechaAño + "-01-01"; // 1ro de enero
			fechaFinal = fechaAño + "-12-31"; // 31 de diciembre
			System.out.println("Fecha Año: "+ fechaInicio + " hasta " + fechaFinal);
		}else if (view.getrBPersonalizado().isSelected()) {
			int mesInicio = view.getcBPersonalizadoMesInicio().getSelectedIndex() + 1;
			String añoInicio = view.getcBPersonalizadoAñoInicio().getSelectedItem().toString();
			fechaInicio = añoInicio +"-"+ String.format("%02d", mesInicio)+ "-01"; // Primer dia del mes de Inicio
			
			int mesFinal = view.getcBPersonalizadoMesFin().getSelectedIndex() + 1;
			String añoFinal = view.getcBPersonalizadoAñoFin().getSelectedItem().toString();
			// Ultimo dia del mes final
			int ultimoDiaFinal = obtenerUltimoDiaDelMes(mesFinal, Integer.parseInt(añoFinal));
			fechaFinal = añoFinal + "-" + String.format("%02d", mesFinal) + "-" + String.format("%02d", ultimoDiaFinal);	
		}
		 // Verificación de que la fecha final no es anterior a la fecha de inicio
        if (esFechaFinalAnterior(fechaInicio, fechaFinal)) {
            System.out.println("Error: La fecha final no puede ser anterior a la fecha de inicio.");
            // O podemos mostrar un mensaje en la interfaz
        } else {
            System.out.println("Fecha Desde: " + fechaInicio + " hasta " + fechaFinal);
        }
	}
	
	// Método para obtener el último día de un mes determinado
	private int obtenerUltimoDiaDelMes(int mes, int año) {
		   switch (mes) {
		       case 1: case 3: case 5: case 7: case 8: case 10: case 12:
		           return 31; // Meses con 31 días
		       case 4: case 6: case 9: case 11:
		            return 30; // Meses con 30 días
		       case 2:
		           // Comprobamos si es año bisiesto
		           if ((año % 4 == 0 && año % 100 != 0) || (año % 400 == 0)) {
		               return 29; // Febrero en año bisiesto
		           } else {
		               return 28; // Febrero en año no bisiesto
		           }
		       default:
		           return 0;
		   }
	}
	
	// Método para verificar si la fecha final es anterior a la fecha de inicio
		private boolean esFechaFinalAnterior(String fechaInicio, String fechaFinal) {
		    String[] inicioParts = fechaInicio.split("-");
		    String[] finalParts = fechaFinal.split("-");

		    // Compara año, mes y día
		    int añoInicio = Integer.parseInt(inicioParts[0]);
		    int mesInicio = Integer.parseInt(inicioParts[1]);
		    int diaInicio = Integer.parseInt(inicioParts[2]);

		    int añoFinal = Integer.parseInt(finalParts[0]);
		    int mesFinal = Integer.parseInt(finalParts[1]);
		    int diaFinal = Integer.parseInt(finalParts[2]);

		    // Verifica si la fecha final es anterior a la fecha de inicio
		    if (añoFinal < añoInicio) {
		        return true;
		    } else if (añoFinal == añoInicio) {
		        if (mesFinal < mesInicio) {
		            return true;
		        } else if (mesFinal == mesInicio) {
		            return diaFinal < diaInicio;
		        }
		    }
		    return false;
		}
		
		public void generarInformeInstalaciones(String fechaInicio, String fechaFinal) {
		    if (fechaInicio != null && fechaFinal != null) {
		        // Establecemos la ruta donde se guardará el informe
		        String rutaInforme = "src/main/resources/informes/InformeInstalaciones.txt";  // Ruta relativa a tu proyecto
		        File informeFile = new File(rutaInforme);

		        // Aseguramos que la carpeta exista, si no, la creamos
		        if (!informeFile.getParentFile().exists()) {
		            informeFile.getParentFile().mkdirs();  // Crea la carpeta 'informes' si no existe
		        }

		        // Llamamos al método del modelo para generar el informe y guardarlo en el archivo
		        model.generarInformeArchivo(fechaInicio, fechaFinal, informeFile.getAbsolutePath());
		        System.out.println("Generando informe con las fechas: " + fechaInicio + " hasta " + fechaFinal);
		    } else {
		        System.out.println("Error: Las fechas de inicio y fin no están establecidas correctamente.");
		    }
		 
		}
		
	
}


