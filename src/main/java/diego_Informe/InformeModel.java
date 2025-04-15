package diego_Informe;

import giis.demo.util.Database;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InformeModel {
    // Instancia para acceder a la base de datos. 
    // Se asume que la clase Database extiende de DbUtil y define el método getUrl().
    private Database db = new Database();

    /**
     * Obtiene la lista de informes (InformeDTO) a partir del período seleccionado.
     * Realiza la consulta a las tablas ACTIVIDAD, INSCRIPCION_ACTIVIDAD y USUARIO.
     * 
     * Se obtienen las siguientes columnas:
     *  - nombreActividad: el nombre de la actividad (alias de a.nombre).
     *  - aforoMaximo: el aforo máximo de la actividad.
     *  - inscritos: total de inscripciones en la actividad.
     *  - socios: número de inscripciones de usuarios con rol 'SOCIO'.
     *  - noSocios: número de inscripciones de usuarios con rol 'NO_SOCIO'.
     * 
     * Sobre estos valores se calculan:
     *  - numeroSinPlaza: si inscritos > aforoMaximo, la diferencia; en otro caso, 0.
     *  - porcentajeSocios y porcentajeNoSocios: porcentajes sobre el total de inscritos.
     *  - Se asume el número de edición en 1 (dado que no disponemos de otro dato).
     * 
     * @param fechaInicio La fecha de inicio del período (formato "yyyy-MM-dd")
     * @param fechaFin La fecha fin del período (formato "yyyy-MM-dd")
     * @return Lista de InformeDTO con los datos procesados.
     */
    public List<InformeDTO> obtenerInformesPorPeriodo(String fechaInicio, String fechaFin) {
        List<InformeDTO> listaInforme = new ArrayList<>();

        String sql = "SELECT " +
                "a.nombre AS nombreActividad, " +
                "a.aforo_maximo AS aforoMaximo, " +
                "COUNT(ia.usuario_id) AS inscritos, " +
                "SUM(CASE WHEN u.rol = 'SOCIO' THEN 1 ELSE 0 END) AS socios, " +
                "SUM(CASE WHEN u.rol = 'NO_SOCIO' THEN 1 ELSE 0 END) AS noSocios " +
                "FROM ACTIVIDAD a " +
                "LEFT JOIN INSCRIPCION_ACTIVIDAD ia ON a.id = ia.actividad_id " +
                "LEFT JOIN USUARIO u ON ia.usuario_id = u.id " +
                "WHERE a.fecha_inicio >= ? AND a.fecha_fin <= ? " +
                "GROUP BY a.id, a.nombre, a.aforo_maximo";


        // Ejecuta la consulta utilizando el método executeQueryMap de DbUtil
        List<Map<String, Object>> resultados = db.executeQueryMap(sql, fechaInicio, fechaFin);

        for (Map<String, Object> fila : resultados) {
            String nombreActividad = (String) fila.get("nombreActividad");
            int aforoMaximo = ((Number) fila.get("aforoMaximo")).intValue();
            int inscritos = ((Number) fila.get("inscritos")).intValue();
            int socios = ((Number) fila.get("socios")).intValue();
            int noSocios = ((Number) fila.get("noSocios")).intValue();

            int numeroSinPlaza = (inscritos > aforoMaximo) ? (inscritos - aforoMaximo) : 0;
            double porcentajeSocios = (inscritos > 0) ? (socios * 100.0 / inscritos) : 0;
            double porcentajeNoSocios = (inscritos > 0) ? (noSocios * 100.0 / inscritos) : 0;
            
            // Se asume el número de edición como 1.
            InformeDTO dto = new InformeDTO(nombreActividad, 1, inscritos, numeroSinPlaza, porcentajeSocios, porcentajeNoSocios);
            listaInforme.add(dto);
        }
        
        return listaInforme;
    }
}
