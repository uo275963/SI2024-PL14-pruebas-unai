package diego_CancelarReserva;

public class CancelarDTO {

    private int usuario_id;
    private String nombre_instalacion;
    private String fecha; // YYYY-MM-DD
    private String hora;  // HH:MM:SS
    
    public CancelarDTO(String nombre_instalacion, String fecha, String hora) {
		super();
		this.nombre_instalacion = nombre_instalacion;
		this.fecha = fecha;
		this.hora = hora;
	}

	public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getNombre_instalacion() {
        return nombre_instalacion;
    }

    public void setNombre_instalacion(String nombre_instalacion) {
        this.nombre_instalacion = nombre_instalacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
