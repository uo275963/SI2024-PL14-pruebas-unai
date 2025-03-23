package diego_InscripcionSocios;

import diego_InscripcionSocios.ActividadDisplayDTO;
import java.util.List;

public class InscripcionController {
    private InscripcionModel model;
    private InscripcionView view;
    
    public InscripcionController(InscripcionModel model, InscripcionView view) {
    	 this.model = model;
         this.view = view;
         this.initView();
    }
    
    public void initView() {
    	view.getFrame().setVisible(true);
    }

    /**
     * Obtiene las actividades disponibles para la inscripción.
     */
    public List<ActividadDisplayDTO> obtenerActividadesDisponibles() {
        return model.obtenerActividadesDisponibles();
    }

    /**
     * Verifica si una actividad tiene plazas disponibles.
     */
    public boolean verificarDisponibilidad(int actividadId) {
        return model.verificarDisponibilidad(actividadId);
    }

    /**
     * Inscribe a un usuario en una actividad.
     */
    public void inscribirUsuario(int usuarioId, int actividadId) {
        model.inscribirUsuario(usuarioId, actividadId);
    }
}
