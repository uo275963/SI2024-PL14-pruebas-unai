package unai.inscribir_no_socio;

public class ListaActividadesDisplayDTO {
    private String nombre;
    private String desc;
    private String inst;
    private double precio_s;
    private double precio_n;
    private String periodo;
    private String finicio;
    private String ffin;
    private int aforoDisponible; // 
    private int listaEspera;    // 

    // Getters y setters existentes
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }

    public String getInst() { return inst; }
    public void setInst(String inst) { this.inst = inst; }

    public double getPrecio_s() { return precio_s; }
    public void setPrecio_s(double precio_s) { this.precio_s = precio_s; }

    public double getPrecio_n() { return precio_n; }
    public void setPrecio_n(double precio_n) { this.precio_n = precio_n; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public String getFinicio() { return finicio; }
    public void setFinicio(String finicio) { this.finicio = finicio; }

    public String getFfin() { return ffin; }
    public void setFfin(String ffin) { this.ffin = ffin; }
    
    // Nuevos getters y setters
    public int getAforoDisponible() { return aforoDisponible; }
    public void setAforoDisponible(int aforoDisponible) { this.aforoDisponible = aforoDisponible; }
    
    public int getListaEspera() { return listaEspera; }
    public void setListaEspera(int listaEspera) { this.listaEspera = listaEspera; }
}