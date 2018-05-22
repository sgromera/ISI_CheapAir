import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Travel {
	private Date fechaSalida, fechaLlegada;
	private float precio;
	private String duracion; 
	private String url;
	private ArrayList<Flight> vuelos;
	private String escala;
	private String compania;
	private Airport origen,destino;
	
	/*
	 * Constructor usado si vamos a meter más de un vuelo
	 * Si usamos este constructor, los vuelos que vayamos a introducir
	 * deben ir ordenados por fecha de salida
	 * 
	 * */
	public Travel(String url, String compania) {
		this.fechaSalida = new Date();
		this.fechaLlegada = new Date();
		this.precio = 0;
		this.vuelos = new ArrayList<Flight>();
		this.url = url;
		this.compania = compania;
		this.origen = new Airport();
		this.destino = new Airport();
	}
	
	/*
	 * Constructor para un solo vuelo
	 * Si el viaje solo tiene un vuelo lo mejor es llamar a este contructor
	 * con el único vuelo como argumento
	 * */
	public Travel(Flight Vuelo, float precio, String url, String compania) {
		vuelos = new ArrayList<Flight>();
		vuelos.add(Vuelo);
		this.fechaSalida = Vuelo.getFechaSalida();
		this.fechaLlegada = Vuelo.getFechaLlegada();
		this.precio = precio;
		this.url = url;
		this.compania = compania;
		this.origen = Vuelo.getOrigen();
		this.destino = Vuelo.getDestino();
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
			this.origen = vuelo.getOrigen();
			this.destino = vuelo.getDestino();
		}
		else if(vuelo.getFechaSalida().after(this.fechaLlegada)) {
			this.vuelos.add(vuelo);
			this.fechaLlegada = vuelo.getFechaLlegada();
			this.destino = vuelo.getDestino();
		}
	}
	
	private String calcularDuracionViaje() {
		String diferencia = "";
		
		// Cojo las fechas
		Calendar finicio = Calendar.getInstance();
		finicio.setTime(this.fechaSalida);
        
		Calendar ffinal = Calendar.getInstance();
        ffinal.setTime(this.fechaLlegada);
        
        // Paso las fechas a milisegundos
        long milis_ini,milis_fin,diff;
        
        milis_ini = finicio.getTimeInMillis();
        milis_fin = ffinal.getTimeInMillis();
        diff = milis_fin - milis_ini;
        
        // calcular la diferencia en horas
        long diffHoras = (diff / (60 * 60 * 1000));
        
        long diffMinutos = (diff / (60 * 1000));
        long restominutos = diffMinutos%60;
        
        // Hago la diferencia
        diferencia = Long.toString(diffHoras) + " h " + Long.toString(restominutos) + " min";

        return diferencia;
	}
	
	
	public Date getFechaSalida() {
		return this.fechaSalida;
	}
	
	public Date getFechaLlegada() {
		return this.fechaLlegada;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	
	public String getDuracion() {
		duracion = this.calcularDuracionViaje();
		return this.duracion;
	}

	public ArrayList<Flight> getVuelos(){
		return this.vuelos;
	}
	
	public void setCompania(String compania) {
		this.compania = compania;
	}
	
	public String getCompania() {
		return this.compania;
	}
	
	public void setEscala(String escala) {
		this.escala = escala;
	}
	
	public String getEscala() {
		return this.escala;
	}
	
	public float getPrecio() {
		return this.precio;
	}

	public Airport getAirportOrigen() {
		return this.origen;
	}
	
	public Airport getAirportDestino() {
		return this.destino;
	}
}