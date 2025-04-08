package controller;

import model.GenerarInformeSociosModel;
import view.GenerarInformeSociosView;

public class GenerarInformeSociosController {
	
	private GenerarInformeSociosModel model;
	private GenerarInformeSociosView view;
	
	// Variables globales para guardar las fechas
		private String fechaMes;
		private String fechaEstacion;
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
		view.getRBMes().addActionListener(e -> escogerPeriodo());
		view.getRBEstaciones().addActionListener(e -> escogerPeriodo());
		view.getRBAño().addActionListener(e -> escogerPeriodo());
		view.getRBPeriodoPersonalizado().addActionListener(e -> escogerPeriodo());
		
	}
	
	public void escogerPeriodo() {
		// Desactiva todo primero por claridad
	    view.getCBEstaciones().setEnabled(false);
	    view.getCBEstacionesAño().setEnabled(false);
	    view.getCBMesInicio().setEnabled(false);
	    view.getCBAñoInicio().setEnabled(false);
	    view.getCBMesFinal().setEnabled(false);
	    view.getCBAñoFinal().setEnabled(false);
	    view.getCBAño().setEnabled(false);

	    // Activar solo los necesarios según el radio seleccionado
	    if (view.getRBMes().isSelected()) {
	        // Supongamos que hay un CBMes, si no hay, deberías crearlo como atributo
	        // view.getCBMes().setEnabled(true); // Este no es accesible directamente aún
	        // Activar año si se necesita
	        view.getCBAño().setEnabled(true);
	    } else if (view.getRBEstaciones().isSelected()) {
	        view.getCBEstaciones().setEnabled(true);
	        view.getCBEstacionesAño().setEnabled(true);
	    } else if (view.getRBAño().isSelected()) {
	        view.getCBAño().setEnabled(true);
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
	    fechaAño = null;
	    fechaInicio = null;
	    fechaFinal = null;

	    if (view.getRBMes().isSelected()) {
	        int mes = view.getCBMes().getSelectedIndex() + 1;
	        String año = view.getCBAño().getSelectedItem().toString();
	        fechaMes = año + "-" + String.format("%02d", mes);
	        System.out.println("Fecha Mes: " + fechaMes);

	    } else if (view.getRBEstaciones().isSelected()) {
	        String año = view.getCBEstacionesAño().getSelectedItem().toString();
	        String estacion = view.getCBEstaciones().getSelectedItem().toString();
	        fechaEstacion = año + "-" + estacion;
	        System.out.println("Fecha Estación: " + fechaEstacion);

	    } else if (view.getRBAño().isSelected()) {
	        fechaAño = view.getCBAño().getSelectedItem().toString();
	        System.out.println("Fecha Año: " + fechaAño);

	    } else if (view.getRBPeriodoPersonalizado().isSelected()) {
	        int mesInicio = view.getCBMesInicio().getSelectedIndex() + 1;
	        String añoInicio = view.getCBAñoInicio().getSelectedItem().toString();
	        fechaInicio = añoInicio + "-" + String.format("%02d", mesInicio);

	        int mesFinal = view.getCBMesFinal().getSelectedIndex() + 1;
	        String añoFinal = view.getCBAñoFinal().getSelectedItem().toString();
	        fechaFinal = añoFinal + "-" + String.format("%02d", mesFinal);

	        System.out.println("Fecha Desde: " + fechaInicio);
	        System.out.println("Fecha Hasta: " + fechaFinal);
	    }
	}
	
	
	
	public void GenerarInforme() {
		
	}


	
	
	

}
