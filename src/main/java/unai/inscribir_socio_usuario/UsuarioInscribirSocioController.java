package unai.inscribir_socio_usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import giis.demo.util.ApplicationException;

public class UsuarioInscribirSocioController {
    private UsuarioInscribirSocioModel model;
    private UsuarioInscribirSocioView view;
    private UsuarioLoginView loginView;

    public UsuarioInscribirSocioController(UsuarioInscribirSocioModel model, UsuarioInscribirSocioView view, UsuarioLoginView loginView) {
        this.model = model;
        this.view = view;
        this.loginView = loginView;
        this.initLoginView();
    }

    // Inicializa y muestra la vista de login
    public void initLoginView() {
        // Configura los eventos para la vista de login
        loginView.getBotonLogin().addActionListener(e -> verificarLogin());
        loginView.getBotonSalir().addActionListener(e -> cerrarVentana(loginView.getFrame()));
        
        // Muestra la ventana de login
        loginView.getFrame().setVisible(true);
    }

    // Verifica las credenciales del usuario
    private void verificarLogin() {
        String dni = loginView.getCampoDNI().getText();
        char[] password = loginView.getCampoPassword().getPassword();
        String clave = new String(password);
        
        // Validar campos vacíos
        if (dni.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(loginView.getFrame(), 
                    "Debe ingresar DNI y contraseña", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar credenciales
        Integer socioId = model.verificarCredencialesSocio(dni, clave);
        if (socioId != null) {
            // Credenciales correctas, guardar ID del socio y abrir la vista principal
            model.setUsuarioId(socioId);
            loginView.getFrame().setVisible(false);
            initMainView();
        } else {
            JOptionPane.showMessageDialog(loginView.getFrame(), 
                    "DNI o contraseña incorrectos o usuario no es socio activo", 
                    "Error de autenticación", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Inicializa la vista principal después del login exitoso
    private void initMainView() {
        // Obtener información del socio
        SocioDTO socio = model.getInfoSocio(model.getUsuarioId());
        if (socio != null) {
            view.setNombreUsuario(socio.getNombre());
        }
        
        // Cargar datos en la vista
        cargarActividadesDisponibles();
        
        // Configurar eventos
        view.getBotonInscribir().addActionListener(e -> inscribirEnActividad());
        view.getBotonSalir().addActionListener(e -> {
            JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(view.getBotonSalir());
            if (ventana != null) {
                ventana.dispose();  // Cierra la ventana
            }
        });
        // Mostrar la ventana principal
        view.getFrame().setVisible(true);
    }
    
    // Carga las actividades disponibles en la tabla
    private void cargarActividadesDisponibles() {
        List<ListaActividadesDisplayDTO> actividades = model.getActividadesDisponibles();
        
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        tableModel.setColumnIdentifiers(new Object[]{
            "Nombre", "Descripción", "Instalación", "Precio socio", "Precio no socio", 
            "Periodo", "Fecha inicio", "Fecha fin", "Plazas totales", "Plazas disponibles", "Estado"
        });
        
        for (ListaActividadesDisplayDTO actividad : actividades) {
            int actividadId = model.getIdActividadPorNombre(actividad.getNombre());
            int plazasDisponibles = actividad.getAforo_maximo() - actividad.getInscritos();
            
            String estado;
            if (model.estaSocioInscritoEnActividad(model.getUsuarioId(), actividadId)) {
                estado = "Ya inscrito";
            } else if (model.estaSocioEnListaEspera(model.getUsuarioId(), actividadId)) {
                int posicion = model.getPosicionEnListaEspera(model.getUsuarioId(), actividadId);
                estado = "En lista de espera (Pos: " + posicion + ")";
            } else if (plazasDisponibles > 0) {
                estado = "Disponible";
            } else {
                // Modificamos esta parte para mostrar cuántos usuarios hay en lista de espera
                int enListaEspera = model.getNumeroEnListaEspera(actividadId);
                estado = "Aforo completo";
                estado += " (" + enListaEspera + " en espera)";

            }
            
            tableModel.addRow(new Object[]{
                actividad.getNombre(),
                actividad.getDesc(),
                actividad.getInst(),
                actividad.getPrecio_s(),
                actividad.getPrecio_n(),
                actividad.getPeriodo(),
                actividad.getFinicio(),
                actividad.getFfin(),
                actividad.getAforo_maximo(),
                plazasDisponibles,
                estado
            });
        }
        
        view.getTablaActividades().setModel(tableModel);
        view.getTablaActividades().getColumnModel().getColumn(10).setPreferredWidth(200);
        view.getTablaActividades().getColumnModel().getColumn(10).setMinWidth(150);
        view.getTablaActividades().setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        
        view.getTablaActividades().revalidate();
        view.getTablaActividades().repaint();
    }
    
    // Maneja la inscripción a una actividad
    private void inscribirEnActividad() {
        int filaSeleccionada = view.getTablaActividades().getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(view.getFrame(), 
                    "Debe seleccionar una actividad", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String nombreActividad = view.getTablaActividades().getValueAt(filaSeleccionada, 0).toString();
        Integer actividadId = model.getIdActividadPorNombre(nombreActividad);
        
        if (actividadId == null) {
            JOptionPane.showMessageDialog(view.getFrame(), 
                    "Error al identificar la actividad", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar si ya está inscrito o en lista de espera
        if (model.estaSocioInscritoEnActividad(model.getUsuarioId(), actividadId)) {
            JOptionPane.showMessageDialog(view.getFrame(), 
                    "Ya estás inscrito en esta actividad", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (model.estaSocioEnListaEspera(model.getUsuarioId(), actividadId)) {
            int posicion = model.getPosicionEnListaEspera(model.getUsuarioId(), actividadId);
            JOptionPane.showMessageDialog(view.getFrame(), 
                    "Ya estás en lista de espera para esta actividad (Posición: " + posicion + ")", 
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Confirmar inscripción
        int plazasDisponibles = Integer.parseInt(view.getTablaActividades().getValueAt(filaSeleccionada, 9).toString());
        
        String mensaje;
        if (plazasDisponibles > 0) {
            mensaje = "¿Deseas inscribirte en la actividad '" + nombreActividad + "'?";
        } else {
            mensaje = "No hay plazas disponibles para esta actividad. ¿Deseas añadirte a la lista de espera?";
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(view.getFrame(), mensaje, 
                "Confirmar inscripción", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                // Intentar inscribir
                model.inscribirSocioEnActividad(model.getUsuarioId(), actividadId);
                
                // Mostrar mensaje de éxito
                if (plazasDisponibles > 0) {
                    JOptionPane.showMessageDialog(view.getFrame(), 
                            "Te has inscrito correctamente en la actividad", 
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(view.getFrame(), 
                            "Has sido añadido a la lista de espera para esta actividad", 
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                }
                
                // Actualizar la tabla
                cargarActividadesDisponibles();
                
            } catch (ApplicationException e) {
                JOptionPane.showMessageDialog(view.getFrame(), 
                        "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Cierra la ventana actual
    private void cerrarVentana(JFrame ventana) {
        ventana.dispose();
    }
    
    // Cierra toda la aplicación
    private void cerrarAplicacion() {
        view.getFrame().dispose();
        System.exit(0);
    }
}
