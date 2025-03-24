package diego_periodoInscripcion;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PeriodoDisplayDTO {

	// Declaramos variables
	private int id;
	private String nombre;
	private String fecha_inicio_socios;
	private String fecha_fin_socios;
	private String fecha_fin_no_socios;


	// Constructor
	public PeriodoDisplayDTO() {
	}

	public PeriodoDisplayDTO(int id, String nombre, String fecha_inicio_socios, String fecha_fin_socios, String fecha_fin_no_socios) {
		this.id = id;
		this.nombre = nombre;
		this.fecha_inicio_socios = fecha_inicio_socios;
		this.fecha_fin_socios = fecha_fin_socios;
		this.fecha_fin_no_socios = fecha_fin_no_socios;
	}

	// Getters y Setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFecha_inicio_socios() {
		return fecha_inicio_socios;
	}

	public void setFecha_inicio_socios(String fecha_inicio_socios) {
		this.fecha_inicio_socios = fecha_inicio_socios;
	}

	public String getFecha_fin_socios() {
		return fecha_fin_socios;
	}

	public void setFecha_fin_socios(String fecha_fin_socios) {
		this.fecha_fin_socios = fecha_fin_socios;

	}

	public String getFecha_fin_no_socios() {
		return fecha_fin_no_socios;
	}

	public void setFecha_fin_no_socios(String fecha_fin_no_socios) {
		this.fecha_fin_no_socios = fecha_fin_no_socios;
	}
}
