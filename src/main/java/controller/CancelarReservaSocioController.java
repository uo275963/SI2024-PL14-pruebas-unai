package controller;

import model.CancelarReservaSocioModel;
import view.CancelarReservaSocioView;

public class CancelarReservaSocioController {
	private CancelarReservaSocioModel model;
	private CancelarReservaSocioView view;
	
	public CancelarReservaSocioController(CancelarReservaSocioModel m, CancelarReservaSocioView v){
		this.model=m;
		this.view=v;
		initView();
	}
	
	public void initView() {
		
	}
	
	public void initController() {
		view.getFrame().setVisible(true);
	}

}
