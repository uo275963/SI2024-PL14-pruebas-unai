package diego_Actividad;

import java.util.Date;

public class ActividadDisplayDTO {

    // Declaramos variables
    private int id;
    private String nombre;
    private String descripcion;
    private int instalacion_id;
    private int aforo_maximo;
    private double coste_socio;
    private double coste_no_socio;
    private String fecha_inicio;
    private String fecha_fin;
    private String dias;
    private String hora_inicio;
    private String hora_fin;
    private int periodo_inscripcion_id;

    // Constructor
    public ActividadDisplayDTO() {}

    public ActividadDisplayDTO(int id, String nombre, String descripcion, int instalacion_id, int aforo_maximo,
                                double coste_socio, double coste_no_socio, String fecha_inicio, String fecha_fin, 
                                String dias, String hora_inicio, String hora_fin, int periodo_inscripcion_id) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.instalacion_id = instalacion_id;
        this.aforo_maximo = aforo_maximo;
        this.coste_socio = coste_socio;
        this.coste_no_socio = coste_no_socio;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.dias = dias;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.periodo_inscripcion_id = periodo_inscripcion_id;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getInstalacion_id() { return instalacion_id; }
    public void setInstalacion_id(int instalacion_id) { this.instalacion_id = instalacion_id; }

    public int getAforo_maximo() { return aforo_maximo; }
    public void setAforo_maximo(int aforo_maximo) { this.aforo_maximo = aforo_maximo; }

    public double getCoste_socio() { return coste_socio; }
    public void setCoste_socio(double coste_socio) { this.coste_socio = coste_socio; }

    public double getCoste_no_socio() { return coste_no_socio; }
    public void setCoste_no_socio(double coste_no_socio) { this.coste_no_socio = coste_no_socio; }

    public String getFecha_inicio() { return fecha_inicio; }
    public void setFecha_inicio(String fecha_inicio) { this.fecha_inicio = fecha_inicio; }

    public String getFecha_fin() { return fecha_fin; }
    public void setFecha_fin(String fecha_fin) { this.fecha_fin = fecha_fin; }

    public String getDias() { return dias; }
    public void setDias(String dias) { this.dias = dias; }

    public String getHora_inicio() { return hora_inicio; }
    public void setHora_inicio(String hora_inicio) { this.hora_inicio = hora_inicio; }

    public String getHora_fin() { return hora_fin; }
    public void setHora_fin(String hora_fin) { this.hora_fin = hora_fin; }

    public int getPeriodo_inscripcion_id() { return periodo_inscripcion_id; }
    public void setPeriodo_inscripcion_id(int periodo_inscripcion_id) { this.periodo_inscripcion_id = periodo_inscripcion_id; }
}
