package controller;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import model.ReservaAutomaticaModel;
import view.ReservaAutomaticaView;

public class ReservaAutomaticaController {
	private ReservaAutomaticaModel model;
	private ReservaAutomaticaView view;
	
	public ReservaAutomaticaController (ReservaAutomaticaModel m, ReservaAutomaticaView v) {
		this.model=m;
		this.view=v;
		this.initView();
	}
	
	public void initView() {
		view.getFrame().setVisible(true);
	}
	public void initController() {
		
	}
	
	public void meterInstalaciones() {
		List<Object[]> list = this.model.getActividades();
		
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>();
		
		for(Object[] fila: list) {
			if(fila.length > 0 && fila[0] !=null) {
				String nombreInstalacion = fila[0].toString();
				modelo.addElement(nombreInstalacion);
			}
		}
		this.view.setCbActividadesModel(modelo);
	}
	
}
