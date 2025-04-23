package unai.inscribir_socio_usuario;

import java.sql.Date;
import java.sql.Time;

/**
 * DTO para mostrar la información de actividades en la vista de inscripción
 * Se adapta a los campos de la tabla ACTIVIDAD y consultas relacionadas
 */
public class ListaActividadesDisplayDTO {
    
    // Campos principales de la actividad
    private String nombre;
    private String desc;
    private String inst;
    private double precio_s;
    private double precio_n;
    private String periodo;
    private String finicio;
    private String ffin;
    private int aforo_maximo;
    private int inscritos;
    
    // Campos adicionales que pueden ser útiles
    private int id;
    private Time hora_inicio;
    private Time hora_fin;
    private String dias;
    
    /**
     * Constructor por defecto
     */
    public ListaActividadesDisplayDTO() {
    }
    
    /**
     * Constructor con parámetros principales
     */
    public ListaActividadesDisplayDTO(String nombre, String desc, String inst, double precio_s, 
                                    double precio_n, String periodo, String finicio, String ffin, 
                                    int aforo_maximo, int inscritos) {
        this.nombre = nombre;
        this.desc = desc;
        this.inst = inst;
        this.precio_s = precio_s;
        this.precio_n = precio_n;
        this.periodo = periodo;
        this.finicio = finicio;
        this.ffin = ffin;
        this.aforo_maximo = aforo_maximo;
        this.inscritos = inscritos;
    }
    
    /**
     * Constructor completo con todos los campos
     */
    public ListaActividadesDisplayDTO(int id, String nombre, String desc, String inst, double precio_s, 
                                    double precio_n, String periodo, String finicio, String ffin, 
                                    int aforo_maximo, int inscritos, Time hora_inicio, Time hora_fin, 
                                    String dias) {
        this.id = id;
        this.nombre = nombre;
        this.desc = desc;
        this.inst = inst;
        this.precio_s = precio_s;
        this.precio_n = precio_n;
        this.periodo = periodo;
        this.finicio = finicio;
        this.ffin = ffin;
        this.aforo_maximo = aforo_maximo;
        this.inscritos = inscritos;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.dias = dias;
    }
    
    // Getters y setters
    
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
    
    public String getDesc() {
        return desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public String getInst() {
        return inst;
    }
    
    public void setInst(String inst) {
        this.inst = inst;
    }
    
    public double getPrecio_s() {
        return precio_s;
    }
    
    public void setPrecio_s(double precio_s) {
        this.precio_s = precio_s;
    }
    
    public double getPrecio_n() {
        return precio_n;
    }
    
    public void setPrecio_n(double precio_n) {
        this.precio_n = precio_n;
    }
    
    public String getPeriodo() {
        return periodo;
    }
    
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
    public String getFinicio() {
        return finicio;
    }
    
    public void setFinicio(String finicio) {
        this.finicio = finicio;
    }
    
    public String getFfin() {
        return ffin;
    }
    
    public void setFfin(String ffin) {
        this.ffin = ffin;
    }
    
    public int getAforo_maximo() {
        return aforo_maximo;
    }
    
    public void setAforo_maximo(int aforo_maximo) {
        this.aforo_maximo = aforo_maximo;
    }
    
    public int getInscritos() {
        return inscritos;
    }
    
    public void setInscritos(int inscritos) {
        this.inscritos = inscritos;
    }
    
    public Time getHora_inicio() {
        return hora_inicio;
    }
    
    public void setHora_inicio(Time hora_inicio) {
        this.hora_inicio = hora_inicio;
    }
    
    public Time getHora_fin() {
        return hora_fin;
    }
    
    public void setHora_fin(Time hora_fin) {
        this.hora_fin = hora_fin;
    }
    
    public String getDias() {
        return dias;
    }
    
    public void setDias(String dias) {
        this.dias = dias;
    }
    
    /**
     * Devuelve el número de plazas disponibles para la actividad
     * @return Número de plazas disponibles
     */
    public int getPlazasDisponibles() {
        return aforo_maximo - inscritos;
    }
    
    /**
     * Comprueba si hay plazas disponibles para la actividad
     * @return true si hay plazas disponibles, false en caso contrario
     */
    public boolean hayPlazasDisponibles() {
        return getPlazasDisponibles() > 0;
    }
    
    @Override
    public String toString() {
        return "ListaActividadesDisplayDTO [nombre=" + nombre + ", descripción=" + desc + 
               ", instalación=" + inst + ", precio socio=" + precio_s + 
               ", precio no socio=" + precio_n + ", período=" + periodo + 
               ", fecha inicio=" + finicio + ", fecha fin=" + ffin + 
               ", aforo máximo=" + aforo_maximo + ", inscritos=" + inscritos + "]";
    }
}