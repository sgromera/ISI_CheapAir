import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;

public class StoreAirports {
	private String source;
	private URL url;
	ArrayList<Airport> airports;
	DatastoreService datastore;
	
	public StoreAirports() {
		airports = new ArrayList<Airport>();
		source = "https://iatacodes.org/api/v6/airports?api_key=fa186435-fbe2-41ff-aaad-ee2f8c923c67";
		datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			url = new URL(source);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 	@brief		stores all data from source into cloud storage
	 * 
	 * */
	public void StoreData() {
		parseAirports();
		
		for(Airport a : this.airports) {
			store(a);
		}
	}
	
	/*
	 * 	@brief		search all airports from source and take it to this.airports
	 * 
	 * */
	private void parseAirports() {
		try {
			String data = readURL();
			
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(data);
			
			JSONArray  array = (JSONArray) obj.get("response");
			
			String code, name;
			for(Object o : array) {
				JSONObject obj2 = (JSONObject) o;
				code = (String) obj2.get("code");
				name = (String) obj2.get("name");
				Airport air = new Airport(code, name);
				
				this.airports.add(air);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * @brief	lee el contenido de la URL después de generarlo y lo devuelve como cadena de caracteres
	 * 
	 * @return	Devuelve el contenido de la URL en la clase como cadena de caracteres en String
	 * */
	private String readURL() throws IOException {
		String buffer, result = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(this.url.openStream()));
		
		while((buffer = in.readLine()) != null) {
			result += buffer;
		}
		in.close();
		
		return result;
	}
	
	/*
	 * 	@brief		store airports from this.airports to cloud storage
	 * 
	 * */
	private void store(Airport a) {
		Entity airport = new Entity("Airport");
		airport.setProperty("code", a.getCodigo());
		airport.setProperty("name", a.getNombre());
		
		datastore.put(airport);
	}
	
	public ArrayList<Airport> getAirports(){
		this.parseAirports();
		return this.airports;
	}
}
