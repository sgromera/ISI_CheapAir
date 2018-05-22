import java.util.ArrayList;

public class TravelResult {
	private ArrayList<Travel> viajesIda;
	private ArrayList<Travel> viajesVuelta;
	
	public TravelResult() {
		this.viajesIda = new ArrayList<Travel>();
		this.viajesVuelta = new ArrayList<Travel>();
	}
	
	public void addTravelIda(Travel viaje) {
		viajesIda.add(viaje);
	}
	
	public ArrayList<Travel> getTravelsIda(){
		return this.viajesIda;
	}
	
	public void addTravelVuelta(Travel viaje) {
		viajesVuelta.add(viaje);
	}
	
	public ArrayList<Travel> getTravelsVuelta(){
		return this.viajesVuelta;
	}
	
	public void Merge(TravelResult tr) {
		for(Travel t: tr.viajesIda) this.addTravelIda(t);
		for(Travel t: tr.viajesVuelta) this.addTravelVuelta(t);
	}
}
