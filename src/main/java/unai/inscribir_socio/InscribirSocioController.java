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

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
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
    	view.getBotonInsc().addActionListener(e -> inscribirSocioEnActividad());
    	view.getBotonVolver().addActionListener(e -> {
              // Aquí puedes cerrar la ventana desde el controlador
              JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(view.getBotonVolver());
              if (ventana != null) {
                  ventana.dispose();  // Cierra la ventana
              }
          });

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
    
    private void inscribirSocioEnActividad() {
        int socioFila = view.getTabSocios().getSelectedRow();
        int actividadFila = view.getTablaActividades().getSelectedRow();

        if (socioFila == -1 || actividadFila == -1) {
            JOptionPane.showMessageDialog(view.getFrame(), "Seleccione un socio y una actividad", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

       
        
       // Obtener el ID del socio seleccionado en la tabla
        int socioId = model.getIdSocioPorDNI(view.getTabSocios().getValueAt(view.getTabSocios().getSelectedRow(), 1).toString());

        // Obtener el ID de la actividad seleccionada en la tabla
        int actividadId = model.getIdActividadPorNombre(view.getTablaActividades().getValueAt(view.getTablaActividades().getSelectedRow(), 0).toString());


        // Obtener el aforo máximo y el número de inscritos
        int aforoMaximo = model.getAforoMaximoDeActividad(actividadId);
        int inscritos = model.getNumeroDeInscritos(actividadId);

        if (aforoMaximo == -1 || inscritos == -1) {
            JOptionPane.showMessageDialog(view.getFrame(), "Error al verificar la disponibilidad de plazas.");
            return;
        }

        // Comprobar si hay plazas disponibles
        if (inscritos >= aforoMaximo) {
            JOptionPane.showMessageDialog(view.getFrame(), "No hay plazas disponibles para esta actividad.");
            return;
        }
        
        
        // Llamar al método para inscribir al socio
        model.inscribirSocioEnActividad(socioId, actividadId);


        try {
            model.inscribirSocioEnActividad(socioId, actividadId);
            JOptionPane.showMessageDialog(view.getFrame(), "Inscripción realizada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Error al inscribir: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
