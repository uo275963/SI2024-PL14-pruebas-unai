package diego_InscripcionSocios;

import diego_InscripcionSocios.ActividadDisplayDTO;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InscripcionView extends JFrame {
    private JTable table;
    private JButton btnInscribir;
    private InscripcionController controller;
    
    public InscripcionView() {
        controller = new InscripcionController();
        
        setTitle("Inscripción a Actividades");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Configurar tabla
        String[] columnNames = { "Nombre", "Descripción", "Aforo", "Coste Socio", "Coste No Socio", "Fechas" };
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Botón de inscripción
        btnInscribir = new JButton("Inscribirse");
        btnInscribir.addActionListener(e -> inscribirUsuario());
        add(btnInscribir, BorderLayout.SOUTH);
        
        // Cargar actividades
        cargarActividades();
    }

    private void cargarActividades() {
        List<ActividadDisplayDTO> actividades = controller.obtenerActividadesDisponibles();
        String[][] data = new String[actividades.size()][6];
        
        for (int i = 0; i < actividades.size(); i++) {
            ActividadDisplayDTO actividad = actividades.get(i);
            data[i][0] = actividad.getNombre();
            data[i][1] = actividad.getDescripcion();
            data[i][2] = String.valueOf(actividad.getAforo_maximo());
            data[i][3] = String.valueOf(actividad.getCoste_socio());
            data[i][4] = String.valueOf(actividad.getCoste_no_socio());
            data[i][5] = actividad.getFecha_inicio() + " - " + actividad.getFecha_fin();
        }
        
        table.setModel(new javax.swing.table.DefaultTableModel(data, new String[] { "Nombre", "Descripción", "Aforo", "Coste Socio", "Coste No Socio", "Fechas" }));
    }

    private void inscribirUsuario() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int actividadId = Integer.parseInt((String) table.getValueAt(selectedRow, 0));
            // Asumimos que el usuario está logueado, en este caso el id de usuario es un valor ficticio
            int usuarioId = 1; // Esto debe ser reemplazado por el id real del usuario
            controller.inscribirUsuario(usuarioId, actividadId);
            JOptionPane.showMessageDialog(this, "Inscripción realizada con éxito.");
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una actividad.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InscripcionView view = new InscripcionView();
            view.setVisible(true);
        });
    }
}
