package diego_Informe;

public class InformeDTO {
    // Atributos para transportar los datos del informe
    private String nombreActividad;
    private int numeroEdicion;
    private int numeroInscritos;
    private int numeroSinPlaza;
    private double porcentajeSocios;
    private double porcentajeNoSocios;

    // Constructor vacío
    public InformeDTO() {
    }

    // Constructor parametrizado
    public InformeDTO(String nombreActividad, int numeroEdicion, int numeroInscritos, int numeroSinPlaza, double porcentajeSocios, double porcentajeNoSocios) {
        this.nombreActividad = nombreActividad;
        this.numeroEdicion = numeroEdicion;
        this.numeroInscritos = numeroInscritos;
        this.numeroSinPlaza = numeroSinPlaza;
        this.porcentajeSocios = porcentajeSocios;
        this.porcentajeNoSocios = porcentajeNoSocios;
    }

    // Getters y Setters

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public int getNumeroEdicion() {
        return numeroEdicion;
    }

    public void setNumeroEdicion(int numeroEdicion) {
        this.numeroEdicion = numeroEdicion;
    }

    public int getNumeroInscritos() {
        return numeroInscritos;
    }

    public void setNumeroInscritos(int numeroInscritos) {
        this.numeroInscritos = numeroInscritos;
    }

    public int getNumeroSinPlaza() {
        return numeroSinPlaza;
    }

    public void setNumeroSinPlaza(int numeroSinPlaza) {
        this.numeroSinPlaza = numeroSinPlaza;
    }

    public double getPorcentajeSocios() {
        return porcentajeSocios;
    }

    public void setPorcentajeSocios(double porcentajeSocios) {
        this.porcentajeSocios = porcentajeSocios;
    }

    public double getPorcentajeNoSocios() {
        return porcentajeNoSocios;
    }

    public void setPorcentajeNoSocios(double porcentajeNoSocios) {
        this.porcentajeNoSocios = porcentajeNoSocios;
    }
    
    // Método toString para facilitar la visualización de datos
    @Override
    public String toString() {
        return "InformeDTO{" +
                "nombreActividad='" + nombreActividad + '\'' +
                ", numeroEdicion=" + numeroEdicion +
                ", numeroInscritos=" + numeroInscritos +
                ", numeroSinPlaza=" + numeroSinPlaza +
                ", porcentajeSocios=" + porcentajeSocios +
                ", porcentajeNoSocios=" + porcentajeNoSocios +
                '}';
    }
}

