import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

import org.jsoup.*;
import org.jsoup.Connection.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
	private String url;
	private Airport origen;
	private Airport destino;
	private Date fechaIda;
	private Date fechaVuelta;
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
	public Scraper(Airport origen, Airport destino, Date fechaIda) {
		this.origen = origen;
		this.destino = destino;
		this.fechaIda = fechaIda;
		this.triptype = 1;
		
		int dia = fechaIda.getDate();
	    int mes = fechaIda.getMonth() + 1;
	    int anio = fechaIda.getYear() + 1900;
	    
	    String d,m;
	    if(dia < 10) d = "0" + Integer.toString(dia);
	    else d = Integer.toString(dia);
	    
	    if(mes < 10) m = "0" + Integer.toString(mes);
	    else m = Integer.toString(mes);
		
	    this.dia_ida = d;
	    this.mes_ida = m;
	    this.anio_ida = Integer.toString(anio);
	    
		url = "https://www.norwegian.com/es/ipc/availability/avaday?AdultCount=1&CurrencyCode=EUR&D_City=" + 
			  origen.getCodigo() + "&D_Day=" + this.dia_ida + "&D_Month=" + this.anio_ida + this.mes_ida + "&A_City=" + 
			  destino.getCodigo() + "&TripType=1";
	}
	
	/*
	 *  Constructor que monta la URL de los vuelos de ida y vuelta
	 */
	public Scraper(Airport origen, Airport destino, Date fechaIda, Date fechaVuelta) {
		this.origen = origen; 
		this.destino = destino;
		this.fechaIda = fechaIda;
		this.fechaVuelta = fechaVuelta;
		this.triptype = 2;
		
		int dia = fechaIda.getDate();
	    int mes = fechaIda.getMonth() + 1;
	    int anio = fechaIda.getYear() + 1900;
	    int dia_v = fechaIda.getDate();
	    int mes_v = fechaIda.getMonth() + 1;
	    int anio_v = fechaIda.getYear() + 1900;
	    
	    String d, dv, m, mv;
	    if(dia < 10) d = "0" + Integer.toString(dia);
	    else d = Integer.toString(dia);
	    
	    if(mes < 10) m = "0" + Integer.toString(mes);
	    else m = Integer.toString(mes);
	    
	    if(dia_v < 10) dv = "0" + Integer.toString(dia_v);
	    else dv = Integer.toString(dia_v);
	    
	    if(mes_v < 10) mv = "0" + Integer.toString(mes_v);
	    else mv = Integer.toString(mes_v);
	    
	    this.dia_ida = d;
	    this.mes_ida = m;
	    this.anio_ida = Integer.toString(anio);
	    this.dia_vuelta = dv;
	    this.mes_vuelta = mv;
	    this.anio_vuelta = Integer.toString(anio_v);
	    
		url = "https://www.norwegian.com/es/ipc/availability/avaday?AdultCount=1&CurrencyCode=EUR&D_City=" + 
			  origen.getCodigo() + "&D_Day=" + this.dia_ida + "&D_Month=" + this.anio_ida + this.mes_ida + "&A_City=" + 
			  destino.getCodigo() + "&R_Day=" + this.dia_vuelta + "&R_Month=" + this.anio_vuelta + this.mes_vuelta + "&TripType=2";
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
	public int getStatusConnectionCode() {
		
	    Response response = null;
		
	    try {
		response = Jsoup.connect(this.url).userAgent("").timeout(10000000).ignoreHttpErrors(true).execute();
	    } catch (IOException ex) {
		System.out.println("Excepción al obtener el Status Code: " + ex.getMessage());
	    }
	    if(response == null) return 401;
	    return response.statusCode();
	}
	
	
	/*
	 *	Esta función nos da el documento HTML de la página que pasamos como argumento 
	 * */
	public Document getHtmlDocument() {

	    Document doc = null;
		try {
		    doc = Jsoup.connect(this.url).userAgent("Mozilla").timeout(10000000).get();
		    } catch (IOException ex) {
			System.out.println("Excepci�n al obtener el HTML de la p�gina" + ex.getMessage());
		    }
	    return doc;
	}
	
	/*
	 *	Esta función hace web scraping a la página web de la compañía de aviones Norweigan y añade los viajes
	 *  al objeto TravelResult 
	 * */
	public TravelResult scrap() {

		TravelResult tr = new TravelResult();
		
		if(getStatusConnectionCode() == 200){
			
			//Document doc = getHtmlDocument();
			Document doc;
			try {
				doc = Jsoup.connect(this.url).userAgent("Mozilla").timeout(100000).get();
			} catch (IOException e) {
				e.printStackTrace();
				return tr;
			}
			
			Elements ida_vuelta = doc.getElementsByClass("avadaytable");
			
            boolean ida = true;
			boolean viajes_encontrados = true;
			
			// Viajes de ida o de vuelta
			for(Element iv: ida_vuelta) {

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
							if(p.size() > 0) { // Si no est� completo el vuelo
								viajes_encontrados = true;
								
								String precio = p.get(0).getElementsByAttributeValue("title","EUR").get(0).text().replace(",", ".");
								float price = Float.parseFloat(precio);
								
								// Fecha-Hora Salida
								String hora_salida = filas1.get(i).getElementsByClass("content emphasize").get(0).text();
								String fecha_salida;
								if(ida) fecha_salida = mes_ida + "/" + dia_ida + "/" + anio_ida + " " + hora_salida;
								else fecha_salida = mes_vuelta + "/" + dia_vuelta + "/" + anio_vuelta + " " + hora_salida;
								Date fechaSalida = new Date(fecha_salida);
								
								// Fecha-Hora Llegada
								String [] hora_llegada = filas1.get(i).getElementsByClass("content emphasize").get(1).text().split(" ");
								String fecha_llegada;
								if(ida) fecha_llegada = mes_ida + "/" + dia_ida + "/" + anio_ida + " " + hora_llegada[0];
								else fecha_llegada = mes_vuelta + "/" + dia_vuelta + "/" + anio_vuelta + " " + hora_llegada[0];
								Date fechaLlegada = new Date(fecha_llegada);
								if(filas1.get(i).getElementsByClass("offsetdays").size() > 0) {
									 Calendar cal = Calendar.getInstance();
									 cal.setTime(fechaLlegada);
					                 cal.add(Calendar.DATE, 1);
					                 fechaLlegada = cal.getTime();
								}
								
								String escala = filas1.get(i).getElementsByClass("content").get(2).text();
								String ciudad_origen,ciudad_destino;
								String esc = "";
								
								if(escala.contains("Directo")) {
									ciudad_origen = filas21.get(vuelos_directo).getElementsByClass("content").get(0).text();
									ciudad_destino = filas21.get(vuelos_directo).getElementsByClass("content").get(1).text();
									vuelos_directo++;
								}
								else {
									ciudad_origen = filas2.get(vuelos_escala).getElementsByClass("content").get(0).text();
									ciudad_destino = filas2.get(vuelos_escala).getElementsByClass("content").get(1).text();
									
									Elements lugar_escala_transito = filas3.get(vuelos_escala).getElementsByClass("tooltipclick TooltipBoxTransit");
									
									if(lugar_escala_transito.size() > 0 ) { // Escala de tr�nsito
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
								
								this.origen.setNombre(ciudad_origen);
								this.destino.setNombre(ciudad_destino);
								Flight f;
								if(ida) f = new Flight(fechaSalida,fechaLlegada,this.origen,this.destino);
								else f = new Flight(fechaSalida,fechaLlegada,this.destino,this.origen);
								Travel t = new Travel(f,price,this.url,"Norwegian");
								t.setEscala(esc);
								
								// A�ado el viaje como resultado
								if(ida) tr.addTravelIda(t);
								else tr.addTravelVuelta(t);
								
							}
						}
					}
				}
				
				ida = false;
			}
			
		}
		
		return tr;
		
	}	
				
		
}