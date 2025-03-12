package diego_Actividad;

import java.util.Date;

public class ActividadDisplayDTO {

	// Declaramos variables
	private int id;
	private String nombre;
	private String descripcion;
	private int instalacion_id;
	private int aforoMaximo;
	private double costeSocio;
	private double costeNoSocio;
	private Date fechaInicio;
	private Date fechaFin;
	private String dias;
	private String horaInicio;
	private String horaFin;
	private int periodoInscripcionId;

	// Constructor
	public ActividadDisplayDTO() {
	}

	public ActividadDisplayDTO(int id, String nombre, String descripcion, int instalacion_id, int aforoMaximo,
			double costeSocio, double costeNoSocio, Date fechaInicio, Date fechaFin, String dias, String horaInicio,
			String horaFin, int periodoInscripcionId) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.instalacion_id = instalacion_id;
		this.aforoMaximo = aforoMaximo;
		this.costeSocio = costeSocio;
		this.costeNoSocio = costeNoSocio;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.dias = dias;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.periodoInscripcionId = periodoInscripcionId;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getInstalacionId() {
		return instalacion_id;
	}

	public void setInstalacionId(int instalacionId) {
		this.instalacion_id = instalacionId;
	}

	public int getAforoMaximo() {
		return aforoMaximo;
	}

	public void setAforoMaximo(int aforoMaximo) {
		this.aforoMaximo = aforoMaximo;
	}

	public double getCosteSocio() {
		return costeSocio;
	}

	public void setCosteSocio(double costeSocio) {
		this.costeSocio = costeSocio;
	}

	public double getCosteNoSocio() {
		return costeNoSocio;
	}

	public void setCosteNoSocio(double costeNoSocio) {
		this.costeNoSocio = costeNoSocio;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getDias() {
		return dias;
	}

	public void setDias(String dias) {
		this.dias = dias;
	}

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

	public int getPeriodoInscripcionId() {
		return periodoInscripcionId;
	}

	public void setPeriodoInscripcionId(int periodoInscripcionId) {
		this.periodoInscripcionId = periodoInscripcionId;
	}
}
