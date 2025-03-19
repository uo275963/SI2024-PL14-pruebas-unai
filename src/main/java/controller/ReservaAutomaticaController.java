package controller;

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
}
