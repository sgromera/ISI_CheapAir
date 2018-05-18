import java.util.Date;

public class Flight {
	private Date fechaSalida, fechaLlegada;
	private Airport origen, destino;
	
	public Flight(Date fechaSalida, Date fechaLlegada, Airport origen, Airport destino) {
		this.fechaSalida = fechaSalida;
		this.fechaLlegada = fechaLlegada;
		this.origen = origen;
		this.destino = destino;
	}
	
	public Date getFechaSalida() {
		return this.fechaSalida;
	}
	
	public Date getFechaLlegada() {
		return this.fechaLlegada;
	}
	
	public Airport getOrigen() {
		return this.origen;
	}
	
	public Airport getDestino() {
		return this.destino;
	}
	
}
