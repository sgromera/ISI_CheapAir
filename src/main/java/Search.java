import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(
    name = "Search",
    urlPatterns = {"/search"}
)
public class Search extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    
	  /*
	   * Recopilamos los datos del formulario
	   * 
	   * */
    String [] origen = request.getParameterValues("origen");
    String [] destino = request.getParameterValues("destino");
    String [] fecha_ida = request.getParameterValues("fechaida");
    String [] fecha_vuelta = request.getParameterValues("fechavuelta");
   
    /*
     * Comprobamos que al menos el usuario haya introducido al menos un origen,
     * un destino y una fecha de ida
     * 
     * */
    if(origen.length > 0 && destino.length > 0 && fecha_ida.length > 0) {
	    // Adaptamos el origen del aeropuerto
	    Airport orig = new Airport(origen[0],"");
	    
	    // Adaptamos la fecha de ida
	    String[] aux = fecha_ida[0].split("-");
	    Date fechaIda = new Date(aux[0]+"/"+aux[1]+"/"+aux[2]);
	    
	    // Adaptamos destino del aeropuerto
	    Airport dest = new Airport(destino[0],"");
	   	    
	    
	    // Hago un scraping a la web de Norweigan y a la API de Ryanair
	    Scraper s;
	    Ryanair r;
	    
	    // Antes, comprobamos si el usuario introduce una fecha de vuelta
	    if(!fecha_vuelta[0].isEmpty()) {
	    	// Si ha introducido fecha de vuelta, construimos los Wrapper para buscar vuelos de ida y vuelta
		    aux = fecha_vuelta[0].split("-");
		    Date fechaVuelta = new Date(aux[0]+"/"+aux[1]+"/"+aux[2]);
	    	s = new Scraper(orig,dest,fechaIda,fechaVuelta);
	    	r = new Ryanair(orig.getCodigo(),dest.getCodigo(),fechaIda,fechaVuelta);
	    }
	    else {
	    	// Si no ha introducido fecha de vuelta, construimos los Wrapper para buscar vuelos de solo ida
	    	s = new Scraper(orig,dest,fechaIda);
	    	r = new Ryanair(orig.getCodigo(),dest.getCodigo(),fechaIda);
	    }
	    
	    TravelResult tr1, tr2;
	    
	    // Extraemos los datos del Wrapper de Norwegian
	    tr1 = s.scrap();
	    // Extraemos los datos del Wrapper de Ryanair
	    tr2 = r.search();
	    
	    // Integramos los resultados en un objeto
	    tr1.Merge(tr2);
	    // Ordenamos los vuelos por precio en orden ascendente
	    tr1.sortByPrice();
	    
	    // Comprobamos que se haya creado un objeto resultado
	    if(tr1 != null && !tr1.equals(null) ) {
	    	// Enviamos los datos al JSP
	    	request.setAttribute("travelResult",tr1);
	    	
	    	RequestDispatcher rd = request.getRequestDispatcher("/resultados.jsp");

	    	rd.forward(request, response);
	    }
	    
    }

  }
}