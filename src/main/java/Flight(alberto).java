import java.util.Date;

public class Flight {
	private Date fechaSalida, fechaLlegada;
	private Airport origen, destino;
	private float precio;
	
	public Flight(Date fechaSalida, Date fechaLlegada, Airport origen, Airport destino, float precio) {
		this.fechaSalida = fechaSalida;
		this.fechaLlegada = fechaLlegada;
		this.origen = origen;
		this.destino = destino;
		this.precio = precio;
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
	
	public float getPrecio() {
		return this.precio;
	}
	
}
