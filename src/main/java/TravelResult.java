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
	
	public void sortByPrice() {
		// Ordeno los viajes de ida por precio
		ArrayList<Travel> t = this.viajesIda;
		for(int i=0;i<(t.size()-1);i++) {
			for(int j=i+1;j<t.size();j++) {
				if(t.get(i).getPrecio() > t.get(j).getPrecio()) {
					Travel travel = t.get(i);
					t.set(i, t.get(j));
					t.set(j, travel);
				}
			}
		}
		this.viajesIda = t;
	    
		// Ordeno los viajes de vuelta por precio
		t = this.viajesVuelta;
		for(int i=0;i<(t.size()-1);i++) {
			for(int j=i+1;j<t.size();j++) {
				if(t.get(i).getPrecio() > t.get(j).getPrecio()) {
					Travel travel = t.get(i);
					t.set(i, t.get(j));
					t.set(j, travel);
				}
			}
		}
		this.viajesVuelta = t;
	}
}
