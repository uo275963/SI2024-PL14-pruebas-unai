package unai.ver_reservas;

import java.sql.Time;
import java.util.Date;

public class ReservaInstalacionDTO {
    private String fecha;  // Usa Date en lugar de java.sql.Date
    private String horaInicio;
    private String horaFin;
    private String nombreUsuario;
    private String nombreActividad;

    public String getNombreActividad() {
		return nombreActividad;
	}

	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}

	// Constructor con parámetros
    public ReservaInstalacionDTO(String fecha, String horaInicio, String horaFin, String nombreUsuario) {
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.nombreUsuario = nombreUsuario;
        
    }

    // Constructor sin parámetros (necesario para DBUtils)
    public ReservaInstalacionDTO() {
    }

    // Getter y Setter para 'fecha'
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }




    // Getters y Setters para los otros atributos

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
