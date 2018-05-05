import java.util.Date;

public class Flight {
	private String code;
	private Date fechaSalida, fechaLlegada;
	private int duracion; //minutos de duración del vuelo
	String codVueloOrigen, codAirOrigen;
	String codVueloDestino, codAirDestino;
	
	public Flight(String code, Date fechaSalida, Date fechaLlegada) {
		this.code = code;
		this.fechaSalida = fechaSalida;
		this.fechaLlegada = fechaLlegada;
	}
	
	public void setCode(String code){
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
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
	
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	
	public int getDuracion() {
		return this.duracion;
	}
	
	public void setCodVueloOrigen(String cod) {
		this.codVueloOrigen = cod;
	}

	public void setCodVueloDestino(String cod) {
		this.codVueloDestino = cod;
	}

	public String getCodVueloOrigen() {
		return this.codVueloOrigen;
	}
	
	public String getCodVueloDestino() {
		return this.codVueloDestino;
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
