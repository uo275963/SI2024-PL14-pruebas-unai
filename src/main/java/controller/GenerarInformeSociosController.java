package controller;

import model.GenerarInformeSociosModel;
import view.GenerarInformeSociosView;

public class GenerarInformeSociosController {
	
	private GenerarInformeSociosModel model;
	private GenerarInformeSociosView view;
	
	
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
		
	}
	
	
	

}
