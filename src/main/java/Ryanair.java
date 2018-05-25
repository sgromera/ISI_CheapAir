import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import org.json.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class Ryanair {
	private URL url;
	private String apiSource;
	private String departureAirport, arrivalAirport;
	private Date outboundDateFrom, outboundDateTo, inboundDateFrom, inboundDateTo;
	private String language, market;
	boolean IdayVuelta;
	
	/*
	 * @brief	Constructor de la clase que establece unos valores iniciales de búsqueda para solo Ida
	 * 
	 * @args	departureAirport 	código IATA del aeropuerto de salida
	 *			arrivalAirport		código IATA del aeropuerto de llegada
	 *			outboundDateFrom	intervalo inferior de la fecha de ida
	 *			outboundDateTo 		intervalo superior de la fecha de ida
	 * */
	public Ryanair(String departureAirport, String arrivalAirport, Date outboundDate) {
		this.apiSource = "https://api.ryanair.com/farefinder/3/oneWayFares";		
		this.IdayVuelta = false;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.outboundDateFrom = outboundDate;
		this.outboundDateTo = outboundDate;
		this.language = "es";
		this. market = "es-es";
	}
	
	/*
	 * @brief	Constructor de la clase que establece unos valores iniciales de búsqueda para Ida y Vuelta
	 * 
	 * @args	departureAirport 	código IATA del aeropuerto de salida
	 *			arrivalAirport		código IATA del aeropuerto de llegada
	 *			outboundDateFrom	intervalo inferior de la fecha de ida
	 *			inboundDateFrom		intervalo inferior de la fecha de vuelta
	 * */
	public Ryanair(String departureAirport, String arrivalAirport, Date outboundDate, Date inboundDate) {
		this.apiSource = "https://api.ryanair.com/farefinder/3/roundTripFares";		
		this.IdayVuelta = true;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.outboundDateFrom = outboundDate;
		this.outboundDateTo = outboundDate;
		this.inboundDateFrom = inboundDate;
		this.inboundDateTo = inboundDate;
		this.language = "es";
		this. market = "es-es";
	}
	
	/*
	 * @brief	genera la url a partir de los parámetros que hemos establecido
	 * 
	 * */
	private void generateURL() throws MalformedURLException {
		String newUrl = this.apiSource;
		newUrl += "?arrivalAirportIataCode=" + this.arrivalAirport;
		newUrl += "&departureAirportIataCode=" + this.departureAirport;
		newUrl += "&outboundDepartureDateFrom=" + this.getDateToString(this.outboundDateFrom);
		newUrl += "&outboundDepartureDateTo=" + this.getDateToString(this.outboundDateTo);
		newUrl += "&language=" + this.language;
		newUrl += "&market=" + this.market;
		
		if(this.IdayVuelta) {
			newUrl += "&inboundDepartureDateFrom=" + this.getDateToString(this.inboundDateFrom);
			newUrl += "&inboundDepartureDateTo=" + this.getDateToString(this.inboundDateTo);
		}
		
		this.url = new URL(newUrl);
	}
	
	/*
	 * @brief	Transforma la fecha en una cadena de caracteres con formato válido para la API Ryanair yyyy-MM-dd
	 * 
	 * @args	date fecha que se quiere transformar
	 * 
	 * @return	cadena de caracteres que corresponde a la fecha transformada
	 * */
	private String getDateToString(Date date) {
		int year = date.getYear() + 1900;
		int month = date.getMonth() + 1;
		int day = date.getDate();
		String mes, dia;
		if(month < 10) mes = "0" + Integer.toString(month);
		else mes = Integer.toString(month);
		
		if(day < 10) dia = "0" + Integer.toString(day);
		else dia = Integer.toString(day);
		
		return Integer.toString(year) + "-" + mes + "-" + dia;
	}
	
	/*
	 * @brief	lee el contenido de la URL después de generarlo y lo devuelve como cadena de caracteres
	 * 
	 * @return	Devuelve el contenido de la URL en la clase como cadena de caracteres en String
	 * */
	private String readURL() throws IOException {
		generateURL();
		String buffer, result = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		
		while((buffer = in.readLine()) != null) {
			result += buffer;
		}
		in.close();
		
		return result;
	}
	
	/*
	 * @brief	busca los vuelos para una búsqueda de solo ida
	 * 
	 * @return	Devuelve un objeto TravelResult con todos los viajes que se han encontrado
	 * */
	private TravelResult searchIda() {
		TravelResult tr = new TravelResult();
		
		try {
			// Obtenemos los datos de la API
			String APIdata = readURL();

			// Inicializmos el parser para leer el JSON
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(APIdata);
			
			//Obtenemos los vuelos
			JSONArray  array = (JSONArray) obj.get("fares");
			
			// Iteramos para todos los viajes que obtenemos en el JSON
			for(Object travel : array) {
				//para cada viaje obtenemos su vuelo y lo itnroducimos en el Travel Result
				Travel viaje = getOutbound( (JSONObject) travel);
				tr.addTravelIda(viaje);
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		return tr;
	}
	
	/*
	 * @brief	Encuentra los vuelos de salida del objeto JSON dado como argumento
	 * 
	 * @args	travel	Objeto JSON que contiene un viaje encontrado por la API
	 * 
	 * @return	devuelve un objeto Travel en el que se ha integrado la información encontrada por la API
	 * */
	private Travel getOutbound(JSONObject travel) {
		JSONObject obj = (JSONObject) travel.get("outbound");
		
		Travel t = getTravel(obj);
		
		return t;
	}
	
	private Travel getInbound(JSONObject travel) {
		JSONObject obj = (JSONObject) travel.get("inbound");
		
		Travel t = getTravel(obj);
		
		return t;
	}
	
	private Travel getTravel(JSONObject obj) {
		Date fechaSalida, fechaLlegada;
		String codAirOrigen, codAirDestino, nameOrigen, countryNameOrigen, nameDestino, countryNameDestino, aux;
		String[] auxdate, auxtime, auxdate2;
		float precio = 0;
		double price;
		
		JSONObject pointer = (JSONObject) obj.get("arrivalAirport");
		codAirDestino = (String) pointer.get("iataCode");
		aux = (String) obj.get("departureDate");
		auxdate = aux.split("T");
		auxdate2 = auxdate[0].split("-");
		auxtime = auxdate[1].split(":");
		fechaSalida = new Date(auxdate2[0]+"/"+auxdate2[1]+"/"+auxdate2[2]+" "+auxtime[0]+":"+auxtime[1]);
		aux = (String) obj.get("arrivalDate");
		auxdate = aux.split("T");
		auxdate2 = auxdate[0].split("-");
		auxtime = auxdate[1].split(":");
		fechaLlegada = new Date(auxdate2[0]+"/"+auxdate2[1]+"/"+auxdate2[2]+" "+auxtime[0]+":"+auxtime[1]);
		nameDestino = (String) pointer.get("name");
		countryNameDestino = (String) pointer.get("countryName");
		
		pointer = (JSONObject) obj.get("departureAirport");
		codAirOrigen = (String) pointer.get("iataCode");
		nameOrigen = (String) pointer.get("name");
		countryNameOrigen = (String) pointer.get("countryName");
		
		pointer = (JSONObject) obj.get("price");
		price = (double) pointer.get("value");
		precio = (float) price;
		
		Airport origen = new Airport(codAirOrigen, nameOrigen, countryNameOrigen);
		Airport destino = new Airport(codAirDestino, nameDestino, countryNameDestino);
		Flight f = new Flight(fechaSalida, fechaLlegada, origen, destino);
		
		Travel t = new Travel(f, precio, "https://www.ryanair.com", "Ryanair");
		t.setEscala("");
		
		return t;
	}
	
	private TravelResult searchIdayVuelta() {
		TravelResult tr = new TravelResult();
		
		try {
			// Obtenemos los datos de la API
			String APIdata = readURL();

			// Inicializmos el parser para leer el JSON
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(APIdata);
			
			//Obtenemos los vuelos
			JSONArray  array = (JSONArray) obj.get("fares");
			
			// Iteramos para todos los viajes que obtenemos en el JSON
			for(Object travel : array) {
				//para cada viaje obtenemos su vuelo y lo itnroducimos en el Travel Result
				Travel viaje = getOutbound( (JSONObject) travel);
				tr.addTravelIda(viaje);
				viaje = getInbound( (JSONObject) travel );
				tr.addTravelVuelta(viaje);
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		return tr;
	}
	
	/*
	 * @brief Busca los vuelos según los parámetros establecidos en el constructor
	 * 
	 * */
	public TravelResult search() {
		// Se extraen los vuelos
		TravelResult tr = new TravelResult();
		if(this.IdayVuelta)
			tr = searchIdayVuelta();
		else 
			tr = searchIda();

		
		return tr;
	}
}
