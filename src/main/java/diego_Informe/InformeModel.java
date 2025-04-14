package diego_Informe;

import java.util.ArrayList;
import java.util.List;

import giis.demo.util.ApplicationException;
import giis.demo.util.Database;

public class InformeModel {
    private Database db = new Database();

    /**
     * Genera un informe de actividades para un período determinado.
     * Para cada actividad se obtiene: 
     *  - nombre de la actividad, 
     *  - número de edición (orden incremental), 
     *  - número total de inscripciones, 
     *  - cantidad de inscripciones de socios y 
     *  - cantidad de inscripciones de no socios.
     *
     * @param nombrePeriodo El nombre del período de inscripción (por ejemplo, "Cuatrimestre 1")
     * @return Lista de objetos InformeDTO con la información requerida.
     */
    public List<InformeDTO> getInformePorPeriodo(String nombrePeriodo) {
        if (nombrePeriodo == null || nombrePeriodo.trim().isEmpty()) {
            throw new ApplicationException("Debe seleccionar un período válido.");
        }
        
        // Primero obtenemos el id del período de inscripción
        String sqlPeriodo = "SELECT id FROM PERIODO_INSCRIPCION WHERE nombre = ?";
        List<Integer> periodoIds = db.executeQueryPojo(Integer.class, sqlPeriodo, nombrePeriodo);
        if (periodoIds.isEmpty()) {
            throw new ApplicationException("No se encontró el período de inscripción: " + nombrePeriodo);
        }
        int periodoId = periodoIds.get(0);
        
        // Se obtienen las actividades correspondientes al período
        String sqlActividades = "SELECT id, nombre FROM ACTIVIDAD WHERE periodo_inscripcion_id = ?";
        // Utilizaremos executeQueryArray para obtener el id y el nombre (cada fila es un Object[] con [id, nombre])
        List<Object[]> actividades = db.executeQueryArray(sqlActividades, periodoId);
        
        List<InformeDTO> informes = new ArrayList<>();
        int edicion = 1; // Número de edición asignado de forma incremental
        
        // Para cada actividad se consultan los datos de inscripciones
        for (Object[] actividad : actividades) {
            int actividadId = ((Number) actividad[0]).intValue();
            String nombreActividad = actividad[1].toString();
            
            // Consultar número total de inscripciones para esta actividad
            String sqlTotalInscripciones = "SELECT COUNT(*) FROM INSCRIPCION_ACTIVIDAD WHERE actividad_id = ?";
            int numeroInscripciones = getCount(sqlTotalInscripciones, actividadId);
            
            // Consultar cantidad de inscripciones de socios
            String sqlSocios = "SELECT COUNT(*) FROM INSCRIPCION_ACTIVIDAD ia " +
                               "JOIN USUARIO u ON ia.usuario_id = u.id " +
                               "WHERE ia.actividad_id = ? AND u.rol = 'SOCIO'";
            int cantidadSocios = getCount(sqlSocios, actividadId);
            
            // Consultar cantidad de inscripciones de no socios
            String sqlNoSocios = "SELECT COUNT(*) FROM INSCRIPCION_ACTIVIDAD ia " +
                                 "JOIN USUARIO u ON ia.usuario_id = u.id " +
                                 "WHERE ia.actividad_id = ? AND u.rol = 'NO_SOCIO'";
            int cantidadNoSocios = getCount(sqlNoSocios, actividadId);
            
            // Crear un nuevo objeto InformeDTO y asignar los datos
            InformeDTO dto = new InformeDTO(nombreActividad, edicion, numeroInscripciones, cantidadSocios, cantidadNoSocios);
            informes.add(dto);
            edicion++;  // Incrementamos el número de edición para la siguiente actividad
        }
        
        return informes;
    }
    
    /**
     * Método auxiliar que ejecuta una consulta SQL que devuelve una única cifra (COUNT(*)) y lo transforma a entero.
     * 
     * @param sql La consulta SQL a ejecutar.
     * @param param El parámetro a pasar a la consulta.
     * @return Resultado entero de la consulta.
     */
    private int getCount(String sql, Object param) {
        List<Long> result = db.executeQueryPojo(Long.class, sql, param);
        if (result.isEmpty()) {
            return 0;
        }
        return result.get(0).intValue();
    }
}
