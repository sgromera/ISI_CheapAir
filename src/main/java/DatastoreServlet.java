import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		name = "DatastoreDeployment",
		urlPatterns = {"/datastore"}
)
public class DatastoreServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		
		StoreAirports sa = new StoreAirports();
		sa.StoreData();
		
		response.getWriter().print("Datastore Complete\n");
	}
}
