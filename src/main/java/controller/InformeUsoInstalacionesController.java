package controller;

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
		
	}

}


