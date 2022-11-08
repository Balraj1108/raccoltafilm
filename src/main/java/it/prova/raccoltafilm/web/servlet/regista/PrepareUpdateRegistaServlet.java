package it.prova.raccoltafilm.web.servlet.regista;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.raccoltafilm.model.Regista;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.service.RegistaService;


/**
 * Servlet implementation class PrepareUpdateRegistaServlet
 */
@WebServlet("/PrepareUpdateRegistaServlet")
public class PrepareUpdateRegistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// injection del Service
	private RegistaService registaService;

	public PrepareUpdateRegistaServlet() {
		this.registaService = MyServiceFactory.getRegistaServiceInstance();
	}
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idRegistaDaModificare = request.getParameter("idRegista");
		
		try {
			Regista registaInstance = registaService.caricaSingoloElemento(Long.parseLong(idRegistaDaModificare));
			
			request.setAttribute("update_regista_attr", registaInstance);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/regista/update.jsp").forward(request, response);
		
	}

}
