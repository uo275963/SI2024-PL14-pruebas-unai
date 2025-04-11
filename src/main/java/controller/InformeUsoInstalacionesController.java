package controller;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import model.InformeUsoInstalacionesModel;
import view.InformeUsoInstalacionesView;

public class InformeUsoInstalacionesController {
	private InformeUsoInstalacionesModel model;
	private InformeUsoInstalacionesView view;
	
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

}


