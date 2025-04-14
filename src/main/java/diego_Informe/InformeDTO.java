package diego_Informe;

public class InformeDTO {
    private String nombreActividad;
    private int numeroEdicion;
    private int numeroInscripciones;
    private int cantidadSocios;
    private int cantidadNoSocios;

    public InformeDTO() {
        // Constructor vacío
    }

    public InformeDTO(String nombreActividad, int numeroEdicion, int numeroInscripciones, int cantidadSocios, int cantidadNoSocios) {
        this.nombreActividad = nombreActividad;
        this.numeroEdicion = numeroEdicion;
        this.numeroInscripciones = numeroInscripciones;
        this.cantidadSocios = cantidadSocios;
        this.cantidadNoSocios = cantidadNoSocios;
    }

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

    public int getNumeroInscripciones() {
        return numeroInscripciones;
    }

    public void setNumeroInscripciones(int numeroInscripciones) {
        this.numeroInscripciones = numeroInscripciones;
    }

    public int getCantidadSocios() {
        return cantidadSocios;
    }

    public void setCantidadSocios(int cantidadSocios) {
        this.cantidadSocios = cantidadSocios;
    }

    public int getCantidadNoSocios() {
        return cantidadNoSocios;
    }

    public void setCantidadNoSocios(int cantidadNoSocios) {
        this.cantidadNoSocios = cantidadNoSocios;
    }

    @Override
    public String toString() {
        return "InformeDTO{" +
                "nombreActividad='" + nombreActividad + '\'' +
                ", numeroEdicion=" + numeroEdicion +
                ", numeroInscripciones=" + numeroInscripciones +
                ", cantidadSocios=" + cantidadSocios +
                ", cantidadNoSocios=" + cantidadNoSocios +
                '}';
    }
}
