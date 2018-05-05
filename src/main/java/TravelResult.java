import java.util.ArrayList;

public class TravelResult {
	private ArrayList<Travel> viajes;
	
	public TravelResult() {
		this.viajes = new ArrayList<Travel>();
	}
	
	public void addTravel(Travel viaje) {
		viajes.add(viaje);
	}
	
	public ArrayList<Travel> getTravels(){
		return this.viajes;
	}
}
