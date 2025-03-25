package diego_InscripcionSocios;

public class InscripcionDTO {
    private int usuarioId;        // ID del usuario que se inscribe
    private int actividadId;      // ID de la actividad a la que se inscribe
    private boolean pagado;       // Indica si la inscripción ha sido pagada

    // Constructor
    public InscripcionDTO() {};
    
    public InscripcionDTO(int usuarioId, int actividadId, boolean pagado) {
        this.usuarioId = usuarioId;
        this.actividadId = actividadId;
        this.pagado = pagado;
    }

    // Getters y Setters
    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getActividadId() {
        return actividadId;
    }

    public void setActividadId(int actividadId) {
        this.actividadId = actividadId;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    // Método toString para mostrar la inscripción
    @Override
    public String toString() {
        return "InscripcionDTO [usuarioId=" + usuarioId + ", actividadId=" + actividadId + ", pagado=" + pagado + "]";
    }
}
