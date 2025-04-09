package controller;

import java.io.File;

import javax.swing.DefaultComboBoxModel;

import model.GenerarInformeSociosModel;
import view.GenerarInformeSociosView;

public class GenerarInformeSociosController {
	
	private GenerarInformeSociosModel model;
	private GenerarInformeSociosView view;
	
	// Variables globales para guardar las fechas
		private String fechaMes;
		private String fechaMesAno;
		private String fechaEstacion;
		private String fechaEstacionAno;
		private String fechaAño;
		private String fechaInicio;
		private String fechaFinal;
	
	
	public GenerarInformeSociosController(GenerarInformeSociosModel model, GenerarInformeSociosView view) {
		super();
		this.model = model;
		this.view = view;
	}
	
	public void initView() {
		
		this.view.getFrame().setVisible(true);
	}
	
	public void initController() {
		this.initView();
	    deshabilitarComponentes();
	    cargarMeses();  // Llamamos al método que llena el ComboBox de meses
	    cargarEstaciones(); // Llenamos el ComboBox de estaciones
	    cargarAños();       // Llenamos el ComboBox de años con 2024 y 2025
		view.getRBMes().addActionListener(e -> escogerPeriodo());
		view.getRBEstaciones().addActionListener(e -> escogerPeriodo());
		view.getRBAño().addActionListener(e -> escogerPeriodo());
		view.getRBPeriodoPersonalizado().addActionListener(e -> escogerPeriodo());
		view.getBtnGenerarInforme().addActionListener(e -> {
		    // Llamamos a obtenerFechasComoString() para obtener las fechas de inicio y fin
		    obtenerFechasComoString();

		    // Verificamos si las fechas fueron correctamente obtenidas
		    if (fechaInicio != null && fechaFinal != null) {
		        // Si las fechas están definidas, llamamos a generarInformeSocios con las fechas
		        generarInformeSocios(fechaInicio, fechaFinal);
		    } else {
		        // Si alguna de las fechas no está definida, mostramos un mensaje de error
		        System.out.println("Error: Las fechas de inicio y fin no están definidas correctamente.");
		    }
		});
		
	}
	
	private void cargarAños() {
	    // Lista de años específicos (2024 y 2025)
	    String[] años = {"2024", "2025"};
	    
	    // Establecer el modelo para el ComboBox de años
	    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(años);
	    view.getCBAno().setModel(model);
	    view.getCBAñoInicio().setModel(model);
	    view.getCBMesAno().setModel(model);
	    view.getCBEstacionesAño().setModel(model);
	    view.getCBAñoInicio().setModel(model);
	    view.getCBAñoFinal().setModel(model);

	}
	
	private void cargarMeses() {
	    String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
	                      "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	    
	    // Establecer el modelo para el ComboBox de meses
	    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(meses);
	    view.getCBMes().setModel(model); 
	    view.getCBMesInicio().setModel(model);
	    view.getCBMesFinal().setModel(model);
	    
	}
	
	private void cargarEstaciones() {
	    // Lista de estaciones (puedes cargarla dinámicamente desde una base de datos si es necesario)
	    String[] estaciones = {"Primavera", "Verano", "Otoño", "Invierno"};
	    
	    // Establecer el modelo para el ComboBox de estaciones
	    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(estaciones);
	    view.getCBEstaciones().setModel(model);  // Asegúrate de que el ComboBox tiene el nombre correcto (CBEstaciones)
	}
	
	
	private void deshabilitarComponentes() {
	    // Desactiva todos los componentes relevantes
	    view.getCBMes().setEnabled(false);
	    view.getCBMesAno().setEnabled(false);
	    view.getCBEstaciones().setEnabled(false);
	    view.getCBEstacionesAño().setEnabled(false);
	    view.getCBMesInicio().setEnabled(false);
	    view.getCBAñoInicio().setEnabled(false);
	    view.getCBMesFinal().setEnabled(false);
	    view.getCBAñoFinal().setEnabled(false);
	    view.getCBAno().setEnabled(false);
	}

	
	public void escogerPeriodo() {
		// Desactiva todo primero por claridad
	    deshabilitarComponentes();

	    // Activar solo los necesarios según el radio seleccionado
	    if (view.getRBMes().isSelected()) {
	        view.getCBMes().setEnabled(true);
	        view.getCBMesAno().setEnabled(true);
	    } else if (view.getRBEstaciones().isSelected()) {
	        view.getCBEstaciones().setEnabled(true);
	        view.getCBEstacionesAño().setEnabled(true);
	    } else if (view.getRBAño().isSelected()) {
	        view.getCBAno().setEnabled(true);
	    } else if (view.getRBPeriodoPersonalizado().isSelected()) {
	        view.getCBMesInicio().setEnabled(true);
	        view.getCBAñoInicio().setEnabled(true);
	        view.getCBMesFinal().setEnabled(true);
	        view.getCBAñoFinal().setEnabled(true);
	    }
		
		
	}
	
	
	public void obtenerFechasComoString() {
	    // Limpia las anteriores por si se cambia de opción
	    fechaMes = null;
	    fechaEstacion = null;
	    fechaEstacionAno = null;
	    fechaAño = null;
	    fechaInicio = null;
	    fechaFinal = null;

	    if (view.getRBMes().isSelected()) {
	        int mes = view.getCBMes().getSelectedIndex() + 1;
	        String año = view.getCBMesAno().getSelectedItem().toString();
	        // Fecha inicial será el 1ro del mes seleccionado
	        fechaInicio = año + "-" + String.format("%02d", mes) + "-01";
	        // Fecha final será el último día del mes
	        int ultimoDia = obtenerUltimoDiaDelMes(mes, Integer.parseInt(año));
	        fechaFinal = año + "-" + String.format("%02d", mes) + "-" + String.format("%02d", ultimoDia);
	        System.out.println("Fecha Mes: " + fechaInicio + " hasta " + fechaFinal);

	    } else if (view.getRBEstaciones().isSelected()) {
	        String año = view.getCBEstacionesAño().getSelectedItem().toString();
	        String estacion = view.getCBEstaciones().getSelectedItem().toString();

	        // Aquí definimos las fechas de inicio y fin para cada estación
	        if (estacion.equals("Primavera")) {
	            fechaInicio = año + "-03-21"; // 21 de marzo
	            fechaFinal = año + "-06-20"; // 20 de junio
	        } else if (estacion.equals("Verano")) {
	            fechaInicio = año + "-06-21"; // 21 de junio
	            fechaFinal = año + "-09-20"; // 20 de septiembre
	        } else if (estacion.equals("Otoño")) {
	            fechaInicio = año + "-09-21"; // 21 de septiembre
	            fechaFinal = año + "-12-20"; // 20 de diciembre
	        } else if (estacion.equals("Invierno")) {
	            fechaInicio = año + "-12-21"; // 21 de diciembre
	            fechaFinal = (Integer.parseInt(año) + 1) + "-03-20"; // 20 de marzo del siguiente año
	        }

	        System.out.println("Fecha Estación: " + fechaInicio + " hasta " + fechaFinal);

	    } else if (view.getRBAño().isSelected()) {
	        // Año seleccionado, desde el 1 de enero hasta el 31 de diciembre
	        fechaAño = view.getCBAno().getSelectedItem().toString();
	        fechaInicio = fechaAño + "-01-01"; // 1ro de enero
	        fechaFinal = fechaAño + "-12-31"; // 31 de diciembre
	        System.out.println("Fecha Año: " + fechaInicio + " hasta " + fechaFinal);

	    } else if (view.getRBPeriodoPersonalizado().isSelected()) {
	        int mesInicio = view.getCBMesInicio().getSelectedIndex() + 1;
	        String añoInicio = view.getCBAñoInicio().getSelectedItem().toString();
	        fechaInicio = añoInicio + "-" + String.format("%02d", mesInicio) + "-01"; // Primer día del mes de inicio

	        int mesFinal = view.getCBMesFinal().getSelectedIndex() + 1;
	        String añoFinal = view.getCBAñoFinal().getSelectedItem().toString();
	        // Último día del mes final
	        int ultimoDiaFinal = obtenerUltimoDiaDelMes(mesFinal, Integer.parseInt(añoFinal));
	        fechaFinal = añoFinal + "-" + String.format("%02d", mesFinal) + "-" + String.format("%02d", ultimoDiaFinal);

	        // Verificación de que la fecha final no es anterior a la fecha de inicio
	        if (esFechaFinalAnterior(fechaInicio, fechaFinal)) {
	            System.out.println("Error: La fecha final no puede ser anterior a la fecha de inicio.");
	            // O podemos mostrar un mensaje en la interfaz
	        } else {
	            System.out.println("Fecha Desde: " + fechaInicio + " hasta " + fechaFinal);
	        }
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


	
	public void generarInformeSocios(String fechaInicio, String fechaFinal) {
	    if (fechaInicio != null && fechaFinal != null) {
	        // Establecemos la ruta donde se guardará el informe
	        String rutaInforme = "src/main/resources/informes/InformeSocios.txt";  // Ruta relativa a tu proyecto
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
