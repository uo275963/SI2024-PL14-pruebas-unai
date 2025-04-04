package diego_CancelarReserva;

import java.time.LocalDate;

public class CancelarDTO {
    private int reservaId;
    private int usuarioId;
    private int instalacionId;
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private boolean pagado;

    /**
     * Constructor por defecto requerido por el mapeo de la base de datos.
     */
    public CancelarDTO() {
    }

    /**
     * Constructor completo para inicializar el DTO.
     * 
     * @param reservaId     Identificador único de la reserva.
     * @param usuarioId     Identificador del usuario que realizó la reserva.
     * @param instalacionId Identificador de la instalación reservada.
     * @param fecha         Fecha en la que se realizó la reserva.
     * @param horaInicio    Hora de inicio de la reserva.
     * @param horaFin       Hora de fin de la reserva.
     * @param pagado        Indica si la reserva ya fue pagada.
     */
    public CancelarDTO(int reservaId, int usuarioId, int instalacionId, String fecha, String horaInicio, String horaFin, boolean pagado) {
        this.reservaId = reservaId;
        this.usuarioId = usuarioId;
        this.instalacionId = instalacionId;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.pagado = pagado;
    }

    // Getters y Setters

    public int getReservaId() {
        return reservaId;
    }

    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getInstalacionId() {
        return instalacionId;
    }

    public void setInstalacionId(int instalacionId) {
        this.instalacionId = instalacionId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    /**
     * Método que retorna una representación en forma de cadena del objeto.
     * Esto es útil para mostrar la información de la reserva en la vista.
     */
    @Override
    public String toString() {
        return "Reserva " + reservaId + " - Fecha: " + fecha + " (" + horaInicio + " - " + horaFin + ")";
    }
}
