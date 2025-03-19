package unai.inscribir_socio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import giis.demo.util.Util;

public class InscribirSocioController {
    private InscribirSocioModel model;
    private InscribirSocioView view;

    public InscribirSocioController(InscribirSocioModel model, InscribirSocioView view) {
        this.model = model;
        this.view = view;
        this.initView();
    }

	public void initView() {
		//Inicializa la fecha de hoy a un valor que permitira mostrar carreras en diferentes fases 
		//y actualiza los datos de la vista
        cargarTablaSocios();
        cargarTablaActividadesValidas();
	    cargarPeriodosEnComboBox();
	    //actualizarFechasDesdePeriodo();
	    
	    /* 
	    String fecha = formatDate(view.fechaFin().getDate());
	    fecha = LocalDate.parse(fecha).plusDays(1).toString();
	    //System.out.print(fecha);
	    //System.out.println();
	    /* SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    try {
			Date date = formatter.parse(fecha);
		    view.fechaFin().setDate(date);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  */
	    
	    
		//Abre la ventana (sustituye al main generado por WindowBuilder)
		view.getFrame().setVisible(true); 

	}

	
    public void initController() {
        // Agrega los eventos a los componentes de la vista
        view.getListaPeriodo().addActionListener(e -> actualizarFechasDesdePeriodo());
    }

    
    /**
     * Carga la lista de socios en la tabla de la vista
     */
    private void cargarTablaSocios() {
        List<SocioDTO> socios = model.getListaSocios();

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Nombre", "DNI"});

        for (SocioDTO socio : socios) {
            tableModel.addRow(new Object[]{socio.getNombre(), socio.getDni()});
        }

        view.getTabSocios().setModel(tableModel);
        view.getTabSocios().revalidate();
        view.getTabSocios().repaint();
    }
    
    private void cargarTablaActividadesValidas() {
        List<ListaActividadesDisplayDTO> actividades = model.getListaActividadesValidas();

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Nombre", "Descripción", "Instalación", "Precio Socio", "Precio No Socio", "Periodo", "Inicio", "Fin"});

        for (ListaActividadesDisplayDTO actividad : actividades) {
            tableModel.addRow(new Object[]{
                actividad.getNombre(),
                actividad.getDesc(),
                actividad.getInst(),
                actividad.getPrecio_s(),
                actividad.getPrecio_n(),
                actividad.getPeriodo(),
                actividad.getFinicio(),
                actividad.getFfin()
            });
        }

        view.getTablaActividades().setModel(tableModel);
        view.getTablaActividades().revalidate();
        view.getTablaActividades().repaint();
    }


    private void cargarPeriodosEnComboBox() {
    	List<PeriodoDTO> periodos = model.getPeriodos();
    	view.getListaPeriodo().removeAllItems();
    	
    	// Agregar un valor por defecto que indique que no hay periodo seleccionado
        view.getListaPeriodo().addItem(""); // Esto agregará un elemento vacío al combo box
    	for (PeriodoDTO periodo : periodos) {
    		view.getListaPeriodo().addItem(periodo.getNombre());
    }
}
    private void actualizarFechasDesdePeriodo() {
        String nombrePeriodo = (String) view.getListaPeriodo().getSelectedItem();
        if (nombrePeriodo == null) return;

        List<PeriodoDTO> periodos = model.getPeriodos();
        for (PeriodoDTO periodo : periodos) {
            if (periodo.getNombre().equals(nombrePeriodo)) {
            	// Depuracion
            	//System.out.print(periodo.getFecha_inicio());
            	//System.out.println();
            	//System.out.print(periodo.getFecha_fin());
            	System.out.println();
                view.setFechaInicio(Util.isoStringToDate(periodo.getFecha_inicio()));
                view.setFechaFin(Util.isoStringToDate(periodo.getFecha_fin()));
                break;
            }
        }
    }
   
    

    /**
     * Método para obtener la lista de actividades en el periodo seleccionado
     */
    public void obtenerActividades() {
        Date fechaInicio = view.fechaInicio().getDate();
        Date fechaFin = view.fechaFin().getDate();
        
        String fechaInicioStr = formatDate(fechaInicio);
        String fechaFinStr = formatDate(fechaFin);

        if (fechaInicio == null || fechaFin == null) {
            JOptionPane.showMessageDialog(view.getFrame(), "Debe seleccionar ambas fechas", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<ListaActividadesDisplayDTO> actividades = model.getListaActividades(fechaInicioStr, fechaFinStr);
        cargarTablaActividades(actividades);
    }
    
    private void actualizarTabla() {
        Date fechaInicio = view.fechaInicio().getDate();
        Date fechaFin = view.fechaFin().getDate();

        if (fechaInicio == null || fechaFin == null) {
            return;
        }

        // Convertir las fechas a formato String (ISO)
        String fechaInicioStr = formatDate(fechaInicio);
        String fechaFinStr = formatDate(fechaFin);

        // Obtener las actividades desde el modelo
        List<ListaActividadesDisplayDTO> actividades = model.getListaActividades(fechaInicioStr, fechaFinStr);


        // Crear un nuevo modelo de tabla
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{
            "Nombre", "Descripción", "Instalación", "Precio Socio", "Precio No Socio", "Periodo", "Inicio", "Fin"
        });

        // Limpiar las filas anteriores de la tabla
        tableModel.setRowCount(0);

        // Llenar la tabla con las nuevas actividades
        for (ListaActividadesDisplayDTO actividad : actividades) {
            tableModel.addRow(new Object[]{
                actividad.getNombre(),
                actividad.getDesc(),
                actividad.getInst(),
                actividad.getPrecio_s(),
                actividad.getPrecio_n(),
                actividad.getPeriodo(),
                actividad.getFinicio(),
                actividad.getFfin()
            });
        }

        // Asignar el modelo actualizado a la tabla en la vista
        view.getTablaActividades().setModel(tableModel);

        // Forzar la actualización visual de la tabla
        view.getTablaActividades().revalidate();
        view.getTablaActividades().repaint();
    }


    /**
     * Carga las actividades en la tabla de la vista
     */
    private void cargarTablaActividades(List<ListaActividadesDisplayDTO> actividades) {
        DefaultTableModel model = (DefaultTableModel) view.getTablaActividades().getModel();
        model.setRowCount(0); // Limpiar tabla

        for (ListaActividadesDisplayDTO actividad : actividades) {
            model.addRow(new Object[]{
                actividad.getNombre(),
                actividad.getDesc(),
                actividad.getInst(),
                actividad.getPrecio_s(),
                actividad.getPrecio_n(),
                actividad.getPeriodo(),
                actividad.getFinicio(),
                actividad.getFfin()
            });
        }
    }

    

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return (date != null) ? sdf.format(date) : null;
    }
    
}
