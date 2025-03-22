package diego_InscripcionSocios;

public class InscripcionDTO {
	private int id;
	private int socioId;
	private int actividadId;
	private String fechaInscripcion;

	public InscripcionDTO(int id, int socioId, int actividadId, String fechaInscripcion) {
		this.id = id;
		this.socioId = socioId;
		this.actividadId = actividadId;
		this.fechaInscripcion = fechaInscripcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSocioId() {
		return socioId;
	}

	public void setSocioId(int socioId) {
		this.socioId = socioId;
	}

	public int getActividadId() {
		return actividadId;
	}

	public void setActividadId(int actividadId) {
		this.actividadId = actividadId;
	}

	public String getFechaInscripcion() {
		return fechaInscripcion;
	}

	public void setFechaInscripcion(String fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

}
