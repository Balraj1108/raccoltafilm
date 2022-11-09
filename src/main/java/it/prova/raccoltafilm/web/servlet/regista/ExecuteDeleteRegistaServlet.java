package it.prova.raccoltafilm.web.servlet.regista;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.raccoltafilm.exceptions.RegistaAlmenoUnFilmException;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.service.RegistaService;

/**
 * Servlet implementation class ExecuteDeleteRegistaServlet
 */
@WebServlet("/admin/ExecuteDeleteRegistaServlet")
public class ExecuteDeleteRegistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	private RegistaService registaService;

	public ExecuteDeleteRegistaServlet() {
		this.registaService = MyServiceFactory.getRegistaServiceInstance();
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idFilmParam = request.getParameter("idRegista");

		if (!NumberUtils.isCreatable(idFilmParam)) {
			// qui ci andrebbe un messaggio nei file di log costruito ad hoc se fosse attivo
			request.setAttribute("errorMessage", "Attenzione si è verificato un provaerrore.");
			request.getRequestDispatcher("home").forward(request, response);
			return;
		}

		try {
			// novità rispetto al passato: abbiamo un overload di rimuovi che agisce per id
			// in questo modo spostiamo la logica di caricamento/rimozione nel service
			// usando la stessa finestra di transazione e non aprendo e chiudendo due volte
			// inoltre mi torna utile quando devo fare rimozioni eager
			registaService.rimuovi(Long.parseLong(idFilmParam));
		} catch (RegistaAlmenoUnFilmException e) {
			//request.setAttribute("errorMessage", "Attenzione si è verificato un regista ha figli.");
			request.getRequestDispatcher("ExecuteListRegistaServlet?operationResult=NON_HA_FIGLI").forward(request, response);
			return;
		} catch (Exception e) {
			// qui ci andrebbe un messaggio nei file di log costruito ad hoc se fosse attivo
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un erreerore.");
			request.getRequestDispatcher("home").forward(request, response);
			return;
		}

		response.sendRedirect("/raccoltafilm/ExecuteListRegistaServlet?operationResult=SUCCESS");
	}

}
