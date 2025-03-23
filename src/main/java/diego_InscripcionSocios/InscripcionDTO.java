package diego_InscripcionSocios;

public class InscripcionDTO {
    private int id;
    private int usuarioId;
    private int actividadId;
    private boolean pagado;

    public InscripcionDTO() {}

    public InscripcionDTO(int id, int usuarioId, int actividadId, boolean pagado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.actividadId = actividadId;
        this.pagado = pagado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "InscripcionDTO{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", actividadId=" + actividadId +
                ", pagado=" + pagado +
                '}';
    }
}