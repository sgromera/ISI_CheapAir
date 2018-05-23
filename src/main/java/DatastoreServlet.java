import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;

@SuppressWarnings("serial")
@WebServlet(
		name = "DatastoreDeployment",
		urlPatterns = {"/datastore"}
)
public class DatastoreServlet extends HttpServlet{
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		StoreAirports s = new StoreAirports();
		ArrayList<Airport> a = s.getAirports();
		
		if(a.size() > 0) response.getWriter().print(a.get(0).getCodigo());
		else response.getWriter().print("No hay aeropuertos\n");
		
		/*
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Entity e = new Entity("Person");
		e.setProperty("name", "Sergio");
		e.setProperty("surname", "Romera");
		
		datastore.put(e);
		*/
		response.getWriter().print("Datastore Complete\n");
	}
}
