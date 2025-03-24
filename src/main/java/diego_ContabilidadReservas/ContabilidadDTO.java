package diego_ContabilidadReservas;

public class ContabilidadDTO {
	private String nombre;
	private String dni;
	private String estado;
	private double montanteReserva;
	private double actividades;
	private double total;

	// Constructores

	public ContabilidadDTO() {
	};

	public ContabilidadDTO(String nombre, String dni, String estado, double montanteReserva, double actividades,
			double total) {
		this.nombre = nombre;
		this.dni = dni;
		this.estado = estado;
		this.montanteReserva = montanteReserva;
		this.actividades = actividades;
		this.total = total;
	}

	// Getters y Setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getMontanteReserva() {
		return montanteReserva;
	}

	public void setMontanteReserva(double montanteReserva) {
		this.montanteReserva = montanteReserva;
	}

	public double getActividades() {
		return actividades;
	}

	public void setActividades(double actividades) {
		this.actividades = actividades;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	// Método para obtener la representación en texto del DTO
	@Override
	public String toString() {
		return "Nombre: " + nombre + "\nDNI: " + dni + "\nEstado: " + estado + "\nMontante Reserva: " + montanteReserva
				+ "\nActividades: " + actividades + "\nTotal: " + total;
	}
}
