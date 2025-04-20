package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

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
	    cargarActividadesEnTabla();
	    agregarListenerTablaActividades();
	}
	
	public void cargarActividadesEnTabla() {
	    List<Object[]> actividades = model.getDetalleActividades();

	    // Definir los nombres de las columnas
	    String[] columnas = {"Actividad", "Instalación", "Fecha Inicio", "Fecha Fin", 
	                         "Días", "Hora Inicio", "Hora Fin", "Coste Socio", "Coste No Socio"};

	    // Crear el modelo de la tabla
	    DefaultTableModel tableModel = new DefaultTableModel(columnas, 0); // 0 filas iniciales

	    // Rellenar el modelo con los datos
	    for (Object[] act : actividades) {
	        tableModel.addRow(act); // Cada act ya es un Object[] con los datos en orden
	    }

	    // Obtener la tabla desde la vista y establecer el nuevo modelo
	    JTable tabla = view.getTablaActividades();
	    tabla.setModel(tableModel);
	}

	
	
	private void agregarListenerTablaActividades() {
	    JTable tablaActividades = view.getTablaActividades();
	    JTable tablaDetalle = view.getTablaCalendario();
	    JTable tablaInscritos = view.getTablaInscritos(); // Asegúrate de tener este getter en la vista

	    tablaActividades.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	        @Override
	        public void valueChanged(ListSelectionEvent e) {
	            if (e.getValueIsAdjusting()) return;

	            int fila = tablaActividades.getSelectedRow();

	            if (fila >= 0) {
	                String nombreActividad = tablaActividades.getValueAt(fila, 0).toString();
	                int actividadId = model.getActividadIdPorNombre(nombreActividad);

	                // Cargar calendario/detalles
	                List<Object[]> detalle = model.getDetalleActividadPorDias(actividadId);
	                DefaultTableModel detalleModel = new DefaultTableModel(
	                    new String[] {"Instalación", "Día", "Fecha", "Hora Inicio", "Hora Fin"}, 0
	                );
	                for (Object[] filaDetalle : detalle) {
	                    detalleModel.addRow(filaDetalle);
	                }
	                tablaDetalle.setModel(detalleModel);

	                // Cargar inscritos
	                List<Object[]> inscritos = model.getInscritosActividad(actividadId);
	                DefaultTableModel inscritosModel = new DefaultTableModel(
	                    new String[] {"Nombre", "DNI", "Pagado"}, 0
	                );
	                for (Object[] filaInscrito : inscritos) {
	                    inscritosModel.addRow(filaInscrito);
	                }
	                tablaInscritos.setModel(inscritosModel);
	            }
	        }
	    });
	}


}
