import java.util.Date;

public class Flight {
	private Date fechaSalida, fechaLlegada;
	private String codAirOrigen, codAirDestino;
	
	public Flight(Date fechaSalida, Date fechaLlegada) {
		this.fechaSalida = fechaSalida;
		this.fechaLlegada = fechaLlegada;
	}
	
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	
	public Date getFechaSalida() {
		return this.fechaSalida;
	}
	
	public void setFechaLlegada(Date fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}
	
	public Date getFechaLlegada() {
		return this.fechaLlegada;
	}
	
	public void setCodAirOrigen(String cod) {
		this.codAirOrigen = cod;
	}

	public void setCodAirDestino(String cod) {
		this.codAirDestino = cod;
	}

	public String getCodAirOrigen() {
		return this.codAirOrigen;
	}
	
	public String getCodAirDestino() {
		return this.codAirDestino;
	}
}
