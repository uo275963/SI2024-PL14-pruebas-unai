package controller;

import model.CancelarActividadPlanificadaModel;
import view.CancelarActividadPlanificadaView;

public class CancelarActividadPlanificadaController {

	private CancelarActividadPlanificadaView view;
	private CancelarActividadPlanificadaModel model;

	public CancelarActividadPlanificadaController(CancelarActividadPlanificadaModel model,
			CancelarActividadPlanificadaView view) {
		super();
		this.view = view;
		this.model = model;
	}
	
	
	
	
	
	public void initView() {
		this.view.getFrame().setVisible(true);
		
	}
	
	
	public void initController() {
		this.initView();
		
	}

}
