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

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@WebServlet(
    name = "Search",
    urlPatterns = {"/search"}
)
public class Search extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
          
    String [] origen = request.getParameterValues("origen");
    String [] destino = request.getParameterValues("destino");
    String [] fecha_ida = request.getParameterValues("fechaida");
    String [] fecha_vuelta = request.getParameterValues("fecha_vuelta");
    
    if(origen.length > 0 && destino.length > 0 && fecha_ida.length > 0) {
	    // Me dan el origen del aeropuerto
	    // LLamo al data store
    	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Airport orig;
    	
    	String codeOrigen;
    	String[] aux = origen[0].split("[\\(\\)]");
    	if(aux.length >= 2) codeOrigen = aux[1];
    	else codeOrigen = origen[0];
	    Query q = new Query("Person").setFilter(new FilterPredicate("code", FilterOperator.EQUAL, codeOrigen));
	    
	    PreparedQuery pq = datastore.prepare(q);
	    Entity result = pq.asSingleEntity();
	    orig = new Airport((String) result.getProperty("code"), (String) result.getProperty("name"));
	    
	    // Me dan el destino del aeropuerto
	    // LLamo al data store
	    Airport dest;
	    
	    String codeDestino;
    	aux = destino[0].split("[\\(\\)]");
    	if(aux.length >= 2) codeDestino = aux[1];
    	else codeDestino = origen[0];
	    q = new Query("Person").setFilter(new FilterPredicate("code", FilterOperator.EQUAL, codeDestino));
	    
	    pq = datastore.prepare(q);
	    result = pq.asSingleEntity();
	    dest = new Airport((String) result.getProperty("code"), (String) result.getProperty("name"));
	    // Fecha ida
	    Date fechaIda = new Date(fecha_ida[0]);
	    
	    
	    // Hago un scraping a la web de Norweigan y a la API de Ryanair
	    Scraper s;
	    Ryanair r;
	    if(fecha_vuelta.length > 0) {
	    	Date fechaVuelta = new Date(fecha_vuelta[0]);
	    	s = new Scraper(orig,dest,fechaIda,fechaVuelta);
	    	r = new Ryanair(orig.getCodigo(),dest.getCodigo(),fechaIda,fechaVuelta);
	    }
	    else {
	    	s = new Scraper(orig,dest,fechaIda);
	    	r = new Ryanair(orig.getCodigo(),dest.getCodigo(),fechaIda);
	    }
	    
	    TravelResult tr1,tr2;
	    tr1 = s.scrap();
	    tr2 = r.search();
	    
	    tr1.Merge(tr2);
	    
	    // Ordeno los viajes de ida por precio 
	    Collections.sort(tr1.getTravelsIda(), new Comparator<Travel>() {
			@Override
		    public int compare(Travel t1, Travel t2) {
		        return Float.compare(t1.getPrecio(), t2.getPrecio());
		    }
		});
	    
	    // Ordeno los viajes de vuelta por precio 
	    Collections.sort(tr1.getTravelsVuelta(), new Comparator<Travel>() {
			@Override
		    public int compare(Travel t1, Travel t2) {
		        return Float.compare(t1.getPrecio(), t2.getPrecio());
		    }
		});
	    
	    if(tr1 != null && !tr1.equals(null) ) {
	    	request.setAttribute("travelResult",tr1);

	    	RequestDispatcher rd = request.getRequestDispatcher("/resultados.jsp");

	    	rd.forward(request, response);
	    }
	    
    }

  }
}