package controller;

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
		initController();
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

}


