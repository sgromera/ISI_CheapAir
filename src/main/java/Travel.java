import java.util.ArrayList;
import java.util.Date;

public class Travel {
	private Date fechaSalida, fechaLlegada;
	float precio;
	int duracion; // duracion del viaje en minutos
	String url;
	ArrayList<Flight> vuelos;
	
	/*
	 * Constructor usado si vamos a meter más de un vuelo
	 * Si usamos este constructor, los vuelos que vayamos a introducir
	 * deben ir ordenados por fecha de salida
	 * 
	 * */
	public Travel() {
		fechaSalida = null;
		fechaLlegada = null;
		duracion = 0;
		vuelos = new ArrayList<Flight>();
	}
	
	/*
	 * Constructor para un solo vuelo
	 * Si el viaje solo tiene un vuelo lo mejor es llamar a este contructor
	 * con el único vuelo como argumento
	 * */
	public Travel(Flight Vuelo, float precio) {
		vuelos = new ArrayList<Flight>();
		this.fechaSalida = Vuelo.getFechaSalida();
		this.fechaLlegada = Vuelo.getFechaLlegada();
		this.duracion = Vuelo.getDuracion();
		this.precio = precio;
	}
	
	/*
	 * Añade un vuelo al viaje si es posterior a la fechaLlegada
	 * Si no existe ningún vuelo, establece fechaSalida y fechaLlegada conforme al vuelo
	 * Si existe un vuelo, comprueba que es posterior al vuelo anterior, si es así
	 * lo añade a la lista de vuelos y pospone la fechaLlegada a la del nuevo vuelo
	 * 
	 * */
	public void addFlight(Flight vuelo) {
		if(vuelos.size() == 0) {
			this.vuelos.add(vuelo);
			this.fechaSalida = vuelo.getFechaSalida();
			this.fechaLlegada = vuelo.getFechaLlegada();
			this.duracion = vuelo.getDuracion();			
		}
		else if(vuelo.getFechaSalida().after(this.fechaLlegada)) {
			this.vuelos.add(vuelo);
			this.fechaLlegada = vuelo.getFechaLlegada();
		}
	}
	
	public Date getFechaSalida() {
		return this.fechaSalida;
	}
	
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	
	public Date getFechaLlegada() {
		return this.fechaLlegada;
	}
	
	public void setFechaLlegada(Date fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	
	public ArrayList<Flight> getVuelos(){
		return this.vuelos;
	}
}
