package it.prova.raccoltafilm.web.servlet.film;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.raccoltafilm.model.Film;
import it.prova.raccoltafilm.service.FilmService;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.service.RegistaService;


@WebServlet("/PrepareUpdateFilmServlet")
public class PrepareUpdateFilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// injection del Service
	private FilmService filmService;
	private RegistaService registaService;

	public PrepareUpdateFilmServlet() {
		this.filmService = MyServiceFactory.getFilmServiceInstance();
		this.registaService = MyServiceFactory.getRegistaServiceInstance();
	}
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idFilmDaModificare = request.getParameter("idFilm");
		
		
		try {
			Film filmInstance = filmService.caricaSingoloElementoEager(Long.parseLong(idFilmDaModificare));
			
			request.setAttribute("registi_list_attribute", registaService.listAllElements());
			request.setAttribute("update_film_attr", filmInstance);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/film/update.jsp").forward(request, response);
		
	}


}
