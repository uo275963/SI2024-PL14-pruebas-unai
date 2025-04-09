package controller;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import model.DesapuntarSocioModel;
import view.DesapuntarSocioActividadView;

public class DesapuntarSocioActividadoController {
	private DesapuntarSocioModel model;
	private DesapuntarSocioActividadView view;
	
	public DesapuntarSocioActividadoController(DesapuntarSocioModel m, DesapuntarSocioActividadView v) {
		this.model=m;
		this.view=v;
		initView();
		initController();
	}
	
	public void initView() {
		view.getFrame().setVisible(true);
		this.meterActividades();
		
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("Socios");
		this.view.setTablaSociosModel(modelo);
	}
	
	public void initController() {
		view.getCbActividad().addActionListener(e -> {
			String actividadSeleccionada = (String) view.getCbActividad().getSelectedItem();
			if (actividadSeleccionada != null) {
				mostrarSociosActividad(actividadSeleccionada);
				
				view.getTaDNI().setText("");
				view.getTaPago().setText("");
			}
		});
		view.getCbSocio().addActionListener(e -> {
			String socioSeleccionado = (String) view.getCbSocio().getSelectedItem();
			String actividadSeleccionada = (String) view.getCbActividad().getSelectedItem();

			if (socioSeleccionado != null && actividadSeleccionada != null) {
				mostrarInfoSocio(socioSeleccionado, actividadSeleccionada);
			}
		});
		
		view.getbCerrar().addActionListener(e -> {
			view.getFrame().setVisible(false);
		});
		
		view.getbDesapuntar().addActionListener(e -> {
		    desapuntarSocioDeActividad();
		});

	}
	
	/**
	 *Método para meter las actividades en el combobox
	 */
	public void meterActividades() {
		List<Object[]> list = this.model.getActividades();
		
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>();
		
		for(Object[] fila: list) {
			if(fila.length > 0 && fila[0] !=null) {
				String nombreActividad = fila[0].toString();
				modelo.addElement(nombreActividad);
			}
		}
		this.view.setCbActividadesModel(modelo);
	}
	
	/**
	 * Método para insertar los usuarios que están inscritos a una actividad en específico
	 * @param El nombre de la actividad
	 */
	private void mostrarSociosActividad(String nombreActividad) {
		List<Object[]> socios = model.getSociosPorActividad(nombreActividad);

		// Rellenar tabla
		DefaultTableModel modeloTabla = (DefaultTableModel) view.getTablaSociosModel();
		modeloTabla.setRowCount(0);

		// Rellenar comboBox de usuarios
		DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<>();

		for (Object[] fila : socios) {
			if (fila.length > 0 && fila[0] != null) {
				String nombreSocio = fila[0].toString();
				modeloTabla.addRow(new Object[]{ nombreSocio });
				modeloCombo.addElement(nombreSocio);
			}
		}

		view.setCbSociosModel(modeloCombo);
	}

	
	private void mostrarInfoSocio(String nombreSocio, String nombreActividad) {
		Object[] datos = model.getDatosSocio(nombreSocio, nombreActividad);

		if (datos != null && datos.length == 2) {
			String dni = datos[0] != null ? datos[0].toString() : "";
			String pagado = (datos[1] != null && ((int) datos[1]) == 1) ? "Pagado" : "No pagado";

			view.getTaDNI().setText(dni);
			view.getTaPago().setText(pagado);
		} else {
			view.getTaDNI().setText("");
			view.getTaPago().setText("No encontrado");
		}
	}

	
	private void desapuntarSocioDeActividad() {
	    String actividad = (String) view.getCbActividad().getSelectedItem();
	    String socio = (String) view.getCbSocio().getSelectedItem();
	    String pago = view.getTaPago().getText();

	    if (actividad != null && socio != null) {
	        if (!confirmarDesapuntado(socio, actividad)) {
	            // Si el usuario pulsa "No", mostramos el mensaje y salimos del método
	            javax.swing.JOptionPane.showMessageDialog(
	                view.getFrame(),
	                "Solicitud cancelada.",
	                "Cancelado",
	                javax.swing.JOptionPane.INFORMATION_MESSAGE
	            );
	            return; // ⬅️ IMPORTANTE: Esto detiene la ejecución aquí
	        }

	        // Si llega aquí, significa que el usuario pulsó "Sí" y se desapunta
	        model.eliminarInscripcion(socio, actividad);

	        // Refrescamos tabla y combo
	        mostrarSociosActividad(actividad);

	        // Limpiar campos
	        view.getTaDNI().setText("");
	        view.getTaPago().setText("");
	        
	        if(pago.equals("Pagado"))
	        javax.swing.JOptionPane.showMessageDialog(
	            view.getFrame(),
	            "Socio desapuntado correctamente. Se devolverá el pago.",
	            "Éxito",
	            javax.swing.JOptionPane.INFORMATION_MESSAGE
	        );
	        else {
	        javax.swing.JOptionPane.showMessageDialog(
	    	    view.getFrame(),
	    	    "Socio desapuntado correctamente. Como no se ha pagado, se descontará simplemente del recibo final.",
	    	    "Éxito",
	    	    javax.swing.JOptionPane.INFORMATION_MESSAGE
	    	  );
	        }
	    }
	}

	

	private boolean confirmarDesapuntado(String socio, String actividad) {
	    int opcion = javax.swing.JOptionPane.showConfirmDialog(
	        view.getFrame(),
	        "¿Desea desapuntar a " + socio + " de " + actividad + "?",
	        "Confirmación",
	        javax.swing.JOptionPane.YES_NO_OPTION,
	        javax.swing.JOptionPane.QUESTION_MESSAGE
	    );

	    return opcion == javax.swing.JOptionPane.YES_OPTION;
	}

	


}
