import java.util.ArrayList;

public class Airports {
	private ArrayList<Airport> aeropuertos;
	
	public Airports() {
		this.aeropuertos = new ArrayList<Airport>();
	}
	
	public void addAirport(Airport a) {
		aeropuertos.add(a);
	}
	
	public ArrayList<Airport> getAirports(){
		return this.aeropuertos;
	}
	
	public Airport getAirport(String cod) {
		for(Airport a: aeropuertos) {
			if(a.getCodigo() == cod) return a;
		}
		return null;
	}

}