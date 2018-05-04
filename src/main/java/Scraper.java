import java.io.IOException;

import org.jsoup.*;
import org.jsoup.Connection.*;
import org.jsoup.nodes.Document;

public class Scraper {
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
	public static int getStatusConnectionCode(String url) {
		
	    Response response = null;
		
	    try {
		response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
	    } catch (IOException ex) {
		System.out.println("Excepción al obtener el Status Code: " + ex.getMessage());
	    }
	    return response.statusCode();
	}
	
	
	/*
	 *	Esta función nos da el documento HtML de la página que pasamos como argumento 
	 * */
	public static Document getHtmlDocument(String url) {

	    Document doc = null;
		try {
		    doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
		    } catch (IOException ex) {
			System.out.println("Excepción al obtener el HTML de la página" + ex.getMessage());
		    }
	    return doc;
	}
}
