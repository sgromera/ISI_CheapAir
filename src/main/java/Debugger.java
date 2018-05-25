import java.io.IOException;
import java.util.Date;

public class Debugger {

	public static void main(String args[]){
		String[] origen = new String[1];
		String[] destino = new String[1];
		String[] fecha_ida = new String[1];
		String[] fecha_vuelta = new String[1];
		
		// Parámetros de la búsqueda//
		//////////////////////////////
		origen[0] = "AGP"; // Código IATA de aeropuerto (3 caracteres)
		destino[0] = "ATH"; // Código IATA de aeropuerto (3 caracteres)
		fecha_ida[0] = "2018-05-26"; // Fecha de ida YYYY-mm-dd
		fecha_vuelta[0] = "2018-05-30"; // Fecha de vuelta YYYY-mm-dd (opcional)
		//////////////////////////////
		
	    if(origen.length > 0 && destino.length > 0 && fecha_ida.length > 0) {
		    // Me dan el origen del aeropuerto
		    Airport orig = new Airport(origen[0],"");
		    String[] aux = fecha_ida[0].split("-");
		    Date fechaIda = new Date(aux[0]+"/"+aux[1]+"/"+aux[2]);
		    
		    // Me dan el destino del aeropuerto
		    Airport dest = new Airport(destino[0],"");
		   	    
		    
		    // Hago un scraping a la web de Norweigan y a la API de Ryanair
		    Scraper s;
		    Ryanair r;
		    if(!fecha_vuelta[0].isEmpty()) {
			    aux = fecha_vuelta[0].split("-");
			    Date fechaVuelta = new Date(aux[0]+"/"+aux[1]+"/"+aux[2]);
		    	s = new Scraper(orig,dest,fechaIda,fechaVuelta);
		    	r = new Ryanair(orig.getCodigo(),dest.getCodigo(),fechaIda,fechaVuelta);
		    }
		    else {
		    	s = new Scraper(orig,dest,fechaIda);
		    	r = new Ryanair(orig.getCodigo(),dest.getCodigo(),fechaIda);
		    }
		    
		    TravelResult tr1, tr2;
		    
			tr1 = s.scrap();
		    tr2 = r.search();
		    
		    tr1.Merge(tr2);
		    System.out.println("Ida:");
		    for(Travel t: tr1.getTravelsIda()) {
		    	System.out.println(printTravel(t));
		    }
		    System.out.println("\nVuelta:");
		    for(Travel t: tr1.getTravelsVuelta()) {
		    	System.out.println(printTravel(t));
		    }
		    
		    System.out.println("finished!");
	    }
	    
	    
	}
	
	private static String printTravel(Travel viaje) {
		String result = "";
		
		result += viaje.getFechaSalida().toString() + " ";
		result += viaje.getOrigen().getNombre() + " - ";
		result += viaje.getDestino().getNombre();
		result += " " + viaje.getFechaLlegada().toString() + "\n";
		result += viaje.getCompania() + "\n";
		result += "Duración: " + viaje.getDuracion() + "\n";
		if(!viaje.getEscala().isEmpty()) result += viaje.getEscala() + "\n";
		result += "\n";
		return result;
	}
}