import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

import org.jsoup.*;
import org.jsoup.Connection.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
	public static String url = "";
	private String nadultos;
	private Airport origen;
	private Airport destino;
	private String dia_ida;
	private String mes_ida;
	private String anio_ida;
	private String dia_vuelta; 
	private String mes_vuelta;
	private String anio_vuelta;
	private int triptype;
	
	/*
	 *  Constructor que monta la URL de los vuelos de solo ida
	 */
	public Scraper(String nadultos, String codigoOrigen, String codigoDestino, String dia_ida,
			String mes_ida, String anio_ida) {
		this.nadultos = nadultos;
		this.origen = Airport.getAirport(codigoOrigen);
		this.destino = Airport.getAirport(codigoDestino);
		this.dia_ida = dia_ida;
		this.mes_ida = mes_ida;
		this.anio_ida = anio_ida;
		this.triptype = 1;
		
		url = "https://www.norwegian.com/es/ipc/availability/avaday?AdultCount=" + nadultos + 
		      "&CurrencyCode=EUR&D_City=" + origen.getCodigo() + "&D_Day=" + dia_ida + "&D_Month=" 
			  + anio_ida + mes_ida + "&A_City=" + codigoDestino + "&TripType=1";
	}
	
	/*
	 *  Constructor que monta la URL de los vuelos de ida y vuelta
	 */
	public Scraper(String nadultos, String codigoOrigen, String codigoDestino, String dia_ida,
					String mes_ida, String anio_ida, String dia_vuelta, String mes_vuelta, String anio_vuelta) {
		this.nadultos = nadultos;
		this.origen = a.getAirport(codigoOrigen); // Aqui llamaría a un objeto de la clase Airports
		this.destino = a.getAirport(codigoDestino);
		this.dia_ida = dia_ida;
		this.mes_ida = mes_ida;
		this.anio_ida = anio_ida;
	    this.dia_vuelta = dia_vuelta; 
		this.mes_vuelta = mes_vuelta;
		this.anio_vuelta = mes_vuelta;
		this.triptype = 2;
		
		url = "https://www.norwegian.com/es/ipc/availability/avaday?AdultCount=" + nadultos + 
		      "&CurrencyCode=EUR&D_City=" + origen.getCodigo() + "&D_Day=" + dia_ida + "&D_Month=" 
			  + anio_ida + mes_ida + "&A_City=" + destino.getCodigo() + "&R_Day=" + dia_vuelta + "&R_Month=" +
		      anio_vuelta + mes_vuelta + "&TripType=2";
	}
	
	/*
	 * 		Esta función devuelve el código de estado de la petición que enviamos a la página web
	 * 
	 * 		Status:
	 *  
	 * 		200 OK			300 Multiple Choices
	 * 		301 Moved Permanently	305 Use Proxy
	 * 		400 Bad Request		403 Forbidden
 	 * 		404 Not Found		500 Internal Server Error
 	 * 		502 Bad Gateway		503 Service Unavailable
	 * */
	public static int getStatusConnectionCode() {
		
	    Response response = null;
		
	    try {
		response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
	    } catch (IOException ex) {
		System.out.println("Excepción al obtener el Status Code: " + ex.getMessage());
	    }
	    return response.statusCode();
	}
	
	
	/*
	 *	Esta función nos da el documento HTML de la página que pasamos como argumento 
	 * */
	public static Document getHtmlDocument() {

	    Document doc = null;
		try {
		    doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
		    } catch (IOException ex) {
			System.out.println("Excepción al obtener el HTML de la pÃ¡gina" + ex.getMessage());
		    }
	    return doc;
	}
	
	/*
	 *	Esta función hace web scraping a la página web de la compañia de aviones Norweigan y añade los viajes
	 *  al objeto TravelResult 
	 * */
	public TravelResult scrap() throws IOException {

		TravelResult tr = new TravelResult();
		
		if(getStatusConnectionCode() == 200) {
			
			Document doc = getHtmlDocument();
			
			Elements ida_vuelta = doc.getElementsByClass("avadaytable");
			
            boolean ida = true;
			boolean viajes_encontrados = true;
			
			// Viajes de ida o de vuelta
			for(Element iv: ida_vuelta) {
				
				ArrayList<Travel> list = new ArrayList<Travel>();

				if(viajes_encontrados) {
					viajes_encontrados = false;
					
					for(int j=0;j<2;j++) {
						Elements filas1, filas2, filas21, filas3;
	
						if(j%2 == 0) { // Viajes impares
							filas1 = iv.getElementsByClass("oddrow rowinfo1 ");
							filas2 = iv.getElementsByClass("oddrow rowinfo2");
							filas21 = iv.getElementsByClass("oddrow rowinfo2 lastrow");
							filas3 = iv.getElementsByClass("oddrow lastrow");
						}
						else { // Viajes pares
							filas1 = iv.getElementsByClass("evenrow rowinfo1 ");
							filas2 = iv.getElementsByClass("evenrow rowinfo2");
							filas21 = iv.getElementsByClass("evenrow rowinfo2 lastrow");
							filas3 = iv.getElementsByClass("evenrow lastrow");
						}
						
						int vuelos_directo = 0, vuelos_escala = 0;
						
						for(int i=0;i<filas1.size();i++) {
							// Precio
							Elements p = filas1.get(i).getElementsByClass("fareselect standardlowfare");
							if(p.size() > 0) { // Si no está completo el vuelo
								viajes_encontrados = true;
								
								String precio = p.get(0).getElementsByAttributeValue("title","EUR").get(0).text().replace(",", ".");
								float price = Float.parseFloat(precio);
								
								// Fecha-Hora Salida
								String hora_salida = filas1.get(i).getElementsByClass("content emphasize").get(0).text();
								String fecha_salida;
								if(ida) fecha_salida = dia_ida + "/" + mes_ida + "/" + anio_ida + " " + hora_salida;
								else fecha_salida = dia_vuelta + "/" + mes_vuelta + "/" + anio_vuelta + " " + hora_salida;
								Date fechaSalida = new Date(fecha_salida);
								
								// Fecha-Hora Llegada
								String [] hora_llegada = filas1.get(i).getElementsByClass("content emphasize").get(1).text().split(" ");
								String fecha_llegada;
								if(ida) fecha_llegada = dia_ida + "/" + mes_ida + "/" + anio_ida + " " + hora_llegada[0];
								else fecha_llegada = dia_vuelta + "/" + mes_vuelta + "/" + anio_vuelta + " " + hora_llegada[0];
								Date fechaLlegada = new Date(fecha_llegada);
								if(filas1.get(i).getElementsByClass("offsetdays").size() > 0) {
									 Calendar cal = Calendar.getInstance(); 
					                 cal.setTime(fechaLlegada);
					                 cal.add(Calendar.DATE, 1);
					                 fechaLlegada = cal.getTime();
								}
								
								String escala = filas1.get(i).getElementsByClass("content").get(2).text();
								String [] duracion;
								String ciudad_origen,ciudad_destino;
								String esc = "";
								
								if(escala.contains("Directo")) {
									ciudad_origen = filas21.get(vuelos_directo).getElementsByClass("content").get(0).text();
									ciudad_destino = filas21.get(vuelos_directo).getElementsByClass("content").get(1).text();
									duracion = filas21.get(vuelos_directo).getElementsByClass("content").get(2).text().split(":");
									vuelos_directo++;
								}
								else {
									ciudad_origen = filas2.get(vuelos_escala).getElementsByClass("content").get(0).text();
									ciudad_destino = filas2.get(vuelos_escala).getElementsByClass("content").get(1).text();
									duracion = filas2.get(vuelos_escala).getElementsByClass("content").get(2).text().split(":");
									
									Elements lugar_escala_transito = filas3.get(vuelos_escala).getElementsByClass("tooltipclick TooltipBoxTransit");
									
									if(lugar_escala_transito.size() > 0 ) { // Escala de tránsito
										esc = lugar_escala_transito.get(0).text();
									}
									else { // Escala de noche
										Elements lugar_escala_noche = filas3.get(vuelos_escala).getElementsByClass("tooltipclick TooltipBoxNightstop");
										
										if(lugar_escala_noche.size() > 0) { // Escala de noche
											esc = lugar_escala_noche.get(0).text();
										}
									}
									
									vuelos_escala++;
								}
								
								Flight f = new Flight(fechaSalida,fechaLlegada,this.origen,this.destino,price);
								Travel t = new Travel(f,duracion[1],this.url,"Norweigan",esc);
								list.add(t);
								
							}
						}
					}
				}
				
				// Si hay viajes los añado
				if(ida) {
					for(Travel tra : list) {
						tr.addTravelIda(tra);
					}
				}
				else {
					for(Travel tra : list) {
						tr.addTravelVuelta(tra);
					}
				}
				
				ida = false;
			}
			
		}
		
		return tr;
		
	}	
				
		
}
