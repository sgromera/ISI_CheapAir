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
          
    String [] origen = request.getParameterValues("origen");
    String [] destino = request.getParameterValues("destino");
    String [] fecha_ida = request.getParameterValues("fechaida");
    String [] fecha_vuelta = request.getParameterValues("fechavuelta");
   
    
    if(origen.length > 0 && destino.length > 0 && fecha_ida.length > 0) {
	    // Me dan el origen del aeropuerto
	    Airport orig = new Airport(origen[0],"");
	    String[] aux = fecha_ida[0].split("-");
	    Date fechaIda = new Date(aux[0]+"/"+aux[1]+"/"+aux[2]);
	    
	    // Me dan el destino del aeropuerto
	    Airport dest = new Airport(destino[0],"");
	   	    
	    
	    // Hago un scraping a la web de Norweigan y a la API de Ryanair
	    Scraper s;
	    Ryanair r;
	    if(!fecha_vuelta[0].isEmpty()) {
		    aux = fecha_vuelta[0].split("-");
		    Date fechaVuelta = new Date(aux[0]+"/"+aux[1]+"/"+aux[2]);
	    	s = new Scraper(orig,dest,fechaIda,fechaVuelta);
	    	r = new Ryanair(orig.getCodigo(),dest.getCodigo(),fechaIda,fechaVuelta);
	    }
	    else {
	    	s = new Scraper(orig,dest,fechaIda);
	    	r = new Ryanair(orig.getCodigo(),dest.getCodigo(),fechaIda);
	    }
	    
	    TravelResult tr1, tr2;
	    
	    tr1 = s.scrap();
	    tr2 = r.search();
	    
	    tr1.Merge(tr2);
	    tr1.sortByPrice();
	    
	    if(tr1 != null && !tr1.equals(null) ) {
	    	request.setAttribute("travelResult",tr1);
	    	request.setCharacterEncoding("UTF-8");
	    	
	    	RequestDispatcher rd = request.getRequestDispatcher("/resultados.jsp");

	    	rd.forward(request, response);
	    }
	    
    }

  }
}