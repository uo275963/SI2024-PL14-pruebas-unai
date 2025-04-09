package diego_CancelarReserva;

public class CancelarDTO {
    private int usuario_id;
    private int actividad_id;
    private String nombre_usuario;
    private String nombre_actividad;
    private String nombre_instalacion;
    private String fecha_actividad;
    private String hora_actividad;

    // Getters y Setters
    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getActividad_id() {
        return actividad_id;
    }

    public void setActividad_id(int actividad_id) {
        this.actividad_id = actividad_id;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getNombre_actividad() {
        return nombre_actividad;
    }

    public void setNombre_actividad(String nombre_actividad) {
        this.nombre_actividad = nombre_actividad;
    }

    public String getNombre_instalacion() {
        return nombre_instalacion;
    }

    public void setNombre_instalacion(String nombre_instalacion) {
        this.nombre_instalacion = nombre_instalacion;
    }
    
    public String getFecha_actividad() {
        return fecha_actividad;
    }
    public void setFecha_actividad(String fecha_actividad) {
        this.fecha_actividad = fecha_actividad;
    }

    public String getHora_actividad() {
        return hora_actividad;
    }
    public void setHora_actividad(String hora_actividad) {
        this.hora_actividad = hora_actividad;
    }
}
