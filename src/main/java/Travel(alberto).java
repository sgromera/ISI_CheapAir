import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Travel {	
	private ArrayList<Flight> vuelos = new ArrayList<Flight>();
	private Date fechaSalida, fechaLlegada;
	private String duracion;
	private float precio = 0;
	private String url;
	private String compania;
	private ArrayList<String> escalas = new ArrayList<String>();
	private Airport origen,destino;
	
	/*
	 * Constructor usado si vamos a meter más de un vuelo
	 * Si usamos este constructor, los vuelos que vayamos a introducir
	 * deben ir ordenados por fecha de salida
	 * 
	 * */
	public Travel(ArrayList<Flight> vuelos, String duracion, String url, String compania, ArrayList<String> escalas) {
		this.vuelos = vuelos;
		this.fechaSalida = vuelos.get(0).getFechaSalida();
		this.fechaLlegada = vuelos.get(vuelos.size()-1).getFechaLlegada();
		this.duracion = duracion;
		for(Flight v:vuelos) {
			precio += v.getPrecio();
		}
		this.url = url;
		this.compania = compania;
		this.escalas = escalas;
		this.origen = vuelos.get(0).getOrigen();
		this.destino = vuelos.get(vuelos.size()-1).getDestino();
	}
	
	/*
	 * Constructor para un solo vuelo
	 * Si el viaje solo tiene un vuelo lo mejor es llamar a este contructor
	 * con el único vuelo como argumento
	 * */
	public Travel(Flight vuelo, String duracion, String url, String compania, String escala) {
		this.vuelos.add(vuelo);
		this.fechaSalida = vuelo.getFechaSalida();
		this.fechaLlegada = vuelo.getFechaLlegada();
		this.duracion = duracion;
		this.precio = vuelo.getPrecio();
		this.url = url;
		this.compania = compania;
		this.escalas.add(escala);
		this.origen = vuelo.getOrigen();
		this.destino = vuelo.getDestino();
	}
	
	public String calcularDuracionViaje() {
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
	
	public ArrayList<Flight> getVuelos() {
		return this.vuelos;
	}
	
	public Date getFechaSalida() {
		return this.fechaSalida;
	}
	
	public Date getFechaLlegada() {
		return this.fechaLlegada;
	}
	
	public String getDuracion() {
		return this.duracion;
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
	
	public String getUrl() {
		return this.url;
	}
	
	public String getCompania() {
		return this.compania;
	}
	
	public ArrayList<String> getEscalas() {
		return this.escalas;
	}
	
}
