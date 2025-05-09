package unai.inscribir_no_socio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
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

public class InscribirNoSocioController {
    private InscribirNoSocioModel model;
    private InscribirNoSocioView view;

    public InscribirNoSocioController(InscribirNoSocioModel model, InscribirNoSocioView view) {
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
   

        // Event listener para el botón "Realizar Inscripción"
    	view.getBotonInsc().addActionListener(e -> realizarInscripcion());
    }
    
    

    private void realizarInscripcion() {
        String dni = view.getDniField().getText();
        String nombre = view.getNombreField().getText();

        if (dni.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(view.getFrame(), "Por favor, completa todos los campos.");
            return;
        }

        // Verificar si el no socio existe en la base de datos
        Integer noSocioId = model.getIdSocioPorDNI(dni);

        // Si no existe, agregar al no socio a la base de datos
        if (noSocioId == null) {
            model.registrarNuevoNoSocio(dni, nombre);
            noSocioId = model.getIdSocioPorDNI(dni);
            
            if (noSocioId == null) {
                JOptionPane.showMessageDialog(view.getFrame(), "No se pudo registrar al no socio.");
                return;
            }
        }

        // Obtener la actividad seleccionada
        int row = view.getTablaActividades().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view.getFrame(), "Por favor, selecciona una actividad.");
            return;
        }
        
        String actividadNombre = (String) view.getTablaActividades().getValueAt(row, 0);
        int aforoDisponible = (int) view.getTablaActividades().getValueAt(row, 8); // Columna de aforo disponible
        
        Integer actividadId = model.getIdActividadPorNombre(actividadNombre);
        if (actividadId == null) {
            JOptionPane.showMessageDialog(view.getFrame(), "La actividad seleccionada no es válida.");
            return;
        }
        
        // Verificar si el no socio ya está inscrito
        if (model.estaNoSocioInscritoEnActividad(noSocioId, actividadId)) {
            JOptionPane.showMessageDialog(view.getFrame(), "El no socio ya está inscrito en esta actividad.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar si estamos dentro del periodo de inscripción para no socios
        if (!model.estaDentroPeriodoInscripcionNoSocios(actividadId)) {
            JOptionPane.showMessageDialog(view.getFrame(), "Fuera del periodo de inscripción para no socios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        

        // Obtener el aforo máximo y el número de inscritos
        int aforoMaximo = model.getAforoMaximoDeActividad(actividadId);
        int inscritos = model.getNumeroDeInscritos(actividadId);

        if (aforoMaximo == -1 || inscritos == -1) {
            JOptionPane.showMessageDialog(view.getFrame(), "Error al verificar la disponibilidad de plazas.");
            return;
        }

        // Verificar si ya está en lista de espera
        if (model.estaEnListaEspera(noSocioId, actividadId)) {
            int posicion = model.obtenerPosicionListaEspera(noSocioId, actividadId);
            JOptionPane.showMessageDialog(view.getFrame(), 
                "El no socio ya está en lista de espera para esta actividad. Posición: " + posicion, 
                "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Comprobar si hay plazas disponibles
        if (inscritos >= aforoMaximo) {
            // No hay plazas, agregar a lista de espera
            model.agregarAListaEspera(noSocioId, actividadId);
            int posicion = model.obtenerPosicionListaEspera(noSocioId, actividadId);
            JOptionPane.showMessageDialog(view.getFrame(), 
                "No hay plazas disponibles. Se ha añadido al no socio a la lista de espera en posición: " + posicion, 
                "Lista de Espera", JOptionPane.INFORMATION_MESSAGE);
            cargarTablaActividadesValidas();
            return;
        }

        // Hay plazas disponibles, inscribir al no socio
        try {
            model.inscribirNoSocioEnActividad(noSocioId, actividadId);
            
            // Obtener el coste para no socios y registrar el pago
            Double costeNoSocio = model.getCosteNoSocio(actividadId);
            model.registrarPago(noSocioId, actividadId, costeNoSocio);

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(view.getFrame(), "¡Inscripción realizada con éxito!");
            
            // Generar y mostrar recibo
            String nombreActividad = model.obtenerNombreActividad(actividadId);
            String nombreInstalacion = model.obtenerNombreInstalacion(actividadId);
            String fechaInscripcion = java.time.LocalDate.now().toString();

            // Crear y mostrar la ventana de recibo
            ReciboView recibo = new ReciboView(dni, nombre, costeNoSocio, nombreActividad, nombreInstalacion, fechaInscripcion);
            recibo.mostrar();
            
            // Recargar tablas para reflejar cambios
            cargarTablaActividadesValidas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Error al realizar la inscripción: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
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


    }
    
    private void cargarTablaActividadesValidas() {
        List<ListaActividadesDisplayDTO> actividades = model.getListaActividadesValidas();

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{
            "Nombre", "Descripción", "Instalación", "Precio Socio", "Precio No Socio", 
            "Periodo", "Inicio", "Fin", "Plazas disponibles", "En cola"
        });

        for (ListaActividadesDisplayDTO actividad : actividades) {
            tableModel.addRow(new Object[]{
                actividad.getNombre(),
                actividad.getDesc(),
                actividad.getInst(),
                actividad.getPrecio_s(),
                actividad.getPrecio_n(),
                actividad.getPeriodo(),
                actividad.getFinicio(),
                actividad.getFfin(),
                actividad.getAforoDisponible(),
                actividad.getListaEspera()
            });
        }

        view.getTablaActividades().setModel(tableModel);
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
                actividad.getFfin(),
                actividad.getAforoDisponible(),
                actividad.getListaEspera()
            });
        }
    }

    

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return (date != null) ? sdf.format(date) : null;
    }
    
}