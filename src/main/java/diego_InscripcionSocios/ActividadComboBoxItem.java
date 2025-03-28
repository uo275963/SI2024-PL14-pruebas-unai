package diego_InscripcionSocios;

public class ActividadComboBoxItem {
    private int id;
    private String nombre;

    public ActividadComboBoxItem(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        // Este método define cómo se mostrará el elemento en el ComboBox
        return id + " - " + nombre;
    }
}
