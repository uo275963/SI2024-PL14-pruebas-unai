package diego_ContabilidadReservas;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ContabilidadController {

	private ContabilidadView view;
	private ContabilidadModel model;

	public ContabilidadController(ContabilidadView view, ContabilidadModel model) {
		this.view = view;
		this.model = model;
		this.initView();
	}

	public void initView() {
		cargarDatosTabla();
		view.getFrame().setVisible(true);
	}

	public void initController() {
		// Vincular botones con sus acciones
		this.view.getBtnCerrar().addActionListener(e -> cerrarVentana());
		this.view.getBtnGenerarDocumento().addActionListener(e -> generarDocumento());
	}

	// Método para cerrar la ventana
	private void cerrarVentana() {
		view.getFrame().dispose(); // Cierra la ventana
	}

	// Método para generar el documento TXT con los datos de los socios
	private void generarDocumento() {
		List<ContabilidadDTO> socios = model.obtenerSocios(); // Obtiene los datos de los socios

		// Si no hay socios, mostrar un mensaje de error
		if (socios.isEmpty()) {
			view.mostrarError("No hay socios para generar el documento.");
			return;
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("Socios.txt"))) {
			// Escribir cabecera del archivo
			writer.write("Nombre\tDNI\tEstado\tMontante Reserva\tActividades\tTotal\n");

			// Escribir los datos de cada socio
			for (ContabilidadDTO socio : socios) {
				writer.write(String.format("%s\t%s\t%s\t%.2f\t%.2f\t%.2f\n", socio.getNombre(), socio.getDni(),
						socio.getEstado(), socio.getMontanteReserva(), socio.getActividades(), socio.getTotal()));
			}

			view.mostrarMensaje("Documento generado exitosamente como 'socios.txt'.");

		} catch (IOException e) {
			view.mostrarError("Error al generar el documento.");
		}
	}

	// Método para cargar los datos de los socios en la tabla de la vista
	public void cargarDatosTabla() {
		List<ContabilidadDTO> socios = model.obtenerSocios(); // Obtiene los datos de los socios

		// Si no hay socios, mostrar un mensaje de error
		if (socios.isEmpty()) {
			view.mostrarError("No se encontraron datos de socios.");
			return;
		}

		// Datos de los socios para mostrar en la tabla
		Object[][] datos = new Object[socios.size()][6]; // 6 columnas para cada socio
		String[] columnas = { "Nombre", "DNI", "Estado", "Montante Reserva", "Actividades", "Total" };

		for (int i = 0; i < socios.size(); i++) {
			ContabilidadDTO socio = socios.get(i);
			datos[i][0] = socio.getNombre();
			datos[i][1] = socio.getDni();
			datos[i][2] = socio.getEstado();
			datos[i][3] = socio.getMontanteReserva();
			datos[i][4] = socio.getActividades();
			datos[i][5] = socio.getTotal();
		}

		// Cargar los datos en la tabla de la vista
		view.cargarDatosTabla(datos, columnas);
	}
}
