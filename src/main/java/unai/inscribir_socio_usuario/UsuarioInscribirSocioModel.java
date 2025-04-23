package unai.inscribir_socio_usuario;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import giis.demo.util.ApplicationException;
import giis.demo.util.Database;

public class UsuarioInscribirSocioModel {
    private static final String MSG_PERIODO_NO_VALIDO = "No estamos en un periodo de inscripción válido para socios";
    
    private Database db = new Database();
    private int usuarioId; // ID del socio logueado

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public int getUsuarioId() {
        return this.usuarioId;
    }
    
    // Verificar si un socio existe con el DNI y clave dados
    public Integer verificarCredencialesSocio(String dni, String clave) {
        String sql = "SELECT id FROM USUARIO WHERE dni = ? AND password = ? AND rol = 'SOCIO' AND estado = 'ACTIVO'";
        List<Object[]> resultado = db.executeQueryArray(sql, dni, clave);
        
        if (resultado != null && !resultado.isEmpty()) {
            Object[] fila = resultado.get(0);
            if (fila != null && fila.length > 0 && fila[0] != null) {
                try {
                    return Integer.parseInt(fila[0].toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    // Obtener información del socio logueado
    public SocioDTO getInfoSocio(int socioId) {
        String sql = "SELECT nombre, dni FROM USUARIO WHERE id = ? AND rol = 'SOCIO'";
        List<SocioDTO> socios = db.executeQueryPojo(SocioDTO.class, sql, socioId);
        
        if (socios != null && !socios.isEmpty()) {
            return socios.get(0);
        }
        return null;
    }
    
    // Obtener la lista de actividades disponibles para inscripción de socios
    public List<ListaActividadesDisplayDTO> getActividadesDisponibles() {
        // Obtener fecha actual
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaHoy = sdf.format(new Date());
        
        String sql = 
            "SELECT a.nombre AS nombre, " +
            "a.descripcion AS desc, " +
            "i.nombre AS inst, " +
            "a.coste_socio AS precio_s, " +
            "a.coste_no_socio AS precio_n, " +
            "p.nombre AS periodo, " +
            "a.fecha_inicio AS finicio, " +
            "a.fecha_fin AS ffin, " +
            "a.aforo_maximo, " +
            "(SELECT COUNT(*) FROM INSCRIPCION_ACTIVIDAD WHERE actividad_id = a.id) AS inscritos " +
            "FROM ACTIVIDAD a " +
            "JOIN INSTALACION i ON a.instalacion_id = i.id " +
            "LEFT JOIN PERIODO_INSCRIPCION p ON a.periodo_inscripcion_id = p.id " +
            "WHERE p.fecha_inicio_socios <= ? " +
            "AND p.fecha_fin_socios >= ? " +
            "ORDER BY a.nombre";
        
        return db.executeQueryPojo(ListaActividadesDisplayDTO.class, sql, fechaHoy, fechaHoy);
    }
    
    // Verificar si el socio ya está inscrito en una actividad
    public boolean estaSocioInscritoEnActividad(int socioId, int actividadId) {
        String sql = "SELECT COUNT(*) FROM INSCRIPCION_ACTIVIDAD WHERE usuario_id = ? AND actividad_id = ?";
        List<Object[]> resultado = db.executeQueryArray(sql, socioId, actividadId);

        if (resultado != null && !resultado.isEmpty()) {
            Object[] fila = resultado.get(0);
            if (fila != null && fila.length > 0) {
                try {
                    int count = Integer.parseInt(fila[0].toString());
                    return count > 0; // Retorna true si ya está inscrito
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    // Verificar si el socio ya está en lista de espera para una actividad
    public boolean estaSocioEnListaEspera(int socioId, int actividadId) {
        String sql = "SELECT COUNT(*) FROM LISTA_ESPERA WHERE usuario_id = ? AND actividad_id = ?";
        List<Object[]> resultado = db.executeQueryArray(sql, socioId, actividadId);

        if (resultado != null && !resultado.isEmpty()) {
            Object[] fila = resultado.get(0);
            if (fila != null && fila.length > 0) {
                try {
                    int count = Integer.parseInt(fila[0].toString());
                    return count > 0;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    // Obtener el ID de una actividad por su nombre
    public Integer getIdActividadPorNombre(String nombreActividad) {
        String sql = "SELECT id FROM ACTIVIDAD WHERE nombre = ?";
        List<Object[]> resultado = db.executeQueryArray(sql, nombreActividad);

        if (resultado != null && !resultado.isEmpty()) {
            Object[] fila = resultado.get(0);
            if (fila != null && fila.length > 0) {
                Object id = fila[0];
                if (id != null) {
                    try {
                        return Integer.parseInt(id.toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }
        return null;
    }
    
    // Verificar si una actividad tiene plazas disponibles
    public boolean hayPlazasDisponibles(int actividadId) {
        String sql = "SELECT a.aforo_maximo, (SELECT COUNT(*) FROM INSCRIPCION_ACTIVIDAD WHERE actividad_id = a.id) as inscritos " +
                     "FROM ACTIVIDAD a WHERE a.id = ?";
        List<Object[]> resultado = db.executeQueryArray(sql, actividadId);
        
        if (resultado != null && !resultado.isEmpty()) {
            Object[] fila = resultado.get(0);
            if (fila != null && fila.length > 1) {
                try {
                    int aforoMaximo = Integer.parseInt(fila[0].toString());
                    int inscritos = Integer.parseInt(fila[1].toString());
                    return inscritos < aforoMaximo;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    // Inscribir socio en actividad
    public void inscribirSocioEnActividad(int socioId, int actividadId) {
        // Verificar si estamos en el periodo de inscripción para socios
        if (!estamosEnPeriodoInscripcionSocios(actividadId)) {
            throw new ApplicationException(MSG_PERIODO_NO_VALIDO);
        }
        
        // Verificar si ya está inscrito
        if (estaSocioInscritoEnActividad(socioId, actividadId)) {
            throw new ApplicationException("Ya estás inscrito en esta actividad");
        }
        
        // Verificar si hay plazas disponibles
        if (hayPlazasDisponibles(actividadId)) {
            // Hay plazas, inscribir directamente
            String sql = "INSERT INTO INSCRIPCION_ACTIVIDAD (usuario_id, actividad_id, pagado) VALUES (?, ?, FALSE)";
            db.executeUpdate(sql, socioId, actividadId);
        } else {
            // No hay plazas, añadir a lista de espera si no está ya
            if (!estaSocioEnListaEspera(socioId, actividadId)) {
                // Obtener la última posición en la lista de espera para esta actividad
                int ultimaPosicion = obtenerUltimaPosicionListaEspera(actividadId);
                
                // Añadir a la lista de espera en la siguiente posición
                String sql = "INSERT INTO LISTA_ESPERA (usuario_id, actividad_id, posicion) VALUES (?, ?, ?)";
                db.executeUpdate(sql, socioId, actividadId, ultimaPosicion + 1);
            } else {
                throw new ApplicationException("Ya estás en la lista de espera para esta actividad");
            }
        }
    }
    
    // Comprobar si estamos en periodo de inscripción para socios
    private boolean estamosEnPeriodoInscripcionSocios(int actividadId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaHoy = sdf.format(new Date());
        
        String sql = "SELECT COUNT(*) FROM ACTIVIDAD a " +
                     "JOIN PERIODO_INSCRIPCION p ON a.periodo_inscripcion_id = p.id " +
                     "WHERE a.id = ? AND p.fecha_inicio_socios <= ? AND p.fecha_fin_socios >= ?";
        
        List<Object[]> resultado = db.executeQueryArray(sql, actividadId, fechaHoy, fechaHoy);
        
        if (resultado != null && !resultado.isEmpty()) {
            Object[] fila = resultado.get(0);
            if (fila != null && fila.length > 0) {
                try {
                    int count = Integer.parseInt(fila[0].toString());
                    return count > 0;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    // Obtener la última posición en la lista de espera para una actividad
    private int obtenerUltimaPosicionListaEspera(int actividadId) {
        String sql = "SELECT MAX(posicion) FROM LISTA_ESPERA WHERE actividad_id = ?";
        List<Object[]> resultado = db.executeQueryArray(sql, actividadId);
        
        if (resultado != null && !resultado.isEmpty()) {
            Object[] fila = resultado.get(0);
            if (fila != null && fila.length > 0 && fila[0] != null) {
                try {
                    return Integer.parseInt(fila[0].toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0; // Si no hay nadie en lista de espera, la posición será 0
    }
    
    // Método para procesar la lista de espera cuando se libere una plaza
    public void procesarListaEspera(int actividadId) {
        // Verificar si hay plazas disponibles
        if (!hayPlazasDisponibles(actividadId)) {
            return; // No hay plazas disponibles
        }
        
        // Obtener el primer usuario en la lista de espera
        String sqlPrimerUsuario = "SELECT usuario_id FROM LISTA_ESPERA WHERE actividad_id = ? ORDER BY posicion ASC LIMIT 1";
        List<Object[]> resultado = db.executeQueryArray(sqlPrimerUsuario, actividadId);
        
        if (resultado != null && !resultado.isEmpty()) {
            Object[] fila = resultado.get(0);
            if (fila != null && fila.length > 0 && fila[0] != null) {
                try {
                    int usuarioId = Integer.parseInt(fila[0].toString());
                    
                    // Inscribir al usuario en la actividad
                    String sqlInscribir = "INSERT INTO INSCRIPCION_ACTIVIDAD (usuario_id, actividad_id, pagado) VALUES (?, ?, FALSE)";
                    db.executeUpdate(sqlInscribir, usuarioId, actividadId);
                    
                    // Eliminar al usuario de la lista de espera
                    String sqlEliminar = "DELETE FROM LISTA_ESPERA WHERE usuario_id = ? AND actividad_id = ?";
                    db.executeUpdate(sqlEliminar, usuarioId, actividadId);
                    
                    // Actualizar posiciones en la lista de espera
                    String sqlActualizar = "UPDATE LISTA_ESPERA SET posicion = posicion - 1 WHERE actividad_id = ?";
                    db.executeUpdate(sqlActualizar, actividadId);
                    
                    // Procesar recursivamente para inscribir a más usuarios si hay más plazas
                    procesarListaEspera(actividadId);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // Obtener la posición del socio en la lista de espera
    public int getPosicionEnListaEspera(int socioId, int actividadId) {
        String sql = "SELECT posicion FROM LISTA_ESPERA WHERE usuario_id = ? AND actividad_id = ?";
        List<Object[]> resultado = db.executeQueryArray(sql, socioId, actividadId);
        
        if (resultado != null && !resultado.isEmpty()) {
            Object[] fila = resultado.get(0);
            if (fila != null && fila.length > 0 && fila[0] != null) {
                try {
                    return Integer.parseInt(fila[0].toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1; // No está en lista de espera
    }
    
    public int getNumeroEnListaEspera(int actividadId) {
        String sql = "SELECT COUNT(*) FROM LISTA_ESPERA WHERE actividad_id = ?";
        List<Object[]> resultado = db.executeQueryArray(sql, actividadId);
        
        if (resultado != null && !resultado.isEmpty()) {
            Object[] fila = resultado.get(0);
            if (fila != null && fila.length > 0 && fila[0] != null) {
                try {
                    return Integer.parseInt(fila[0].toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }
}