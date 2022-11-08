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
import it.prova.raccoltafilm.utility.UtilityForm;


@WebServlet("/ExecuteUpdateFilmServlet")
public class ExecuteUpdateFilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	// injection del Service
	private FilmService filmService;
	private RegistaService registaService;

	public ExecuteUpdateFilmServlet() {
		this.filmService = MyServiceFactory.getFilmServiceInstance();
		this.registaService = MyServiceFactory.getRegistaServiceInstance();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// estraggo input
		String titoloParam = request.getParameter("titolo");
		String genereParam = request.getParameter("genere");
		String dataPubblicazioneParam = request.getParameter("dataPubblicazione");
		String minutiDurataParam = request.getParameter("minutiDurata");
		String registaIdParam = request.getParameter("regista.id");
		String idFilmParam = request.getParameter("idFilm");

		// preparo un bean (che mi serve sia per tornare in pagina
		// che per inserire) e faccio il binding dei parametri
		Film filmInstance = UtilityForm.createFilmFromParams(titoloParam, genereParam, minutiDurataParam,
				dataPubblicazioneParam, registaIdParam);
		filmInstance.setId(Long.parseLong(idFilmParam));

		try {
			// se la validazione non risulta ok
			if (!UtilityForm.validateFilmBean(filmInstance)) {
				request.setAttribute("update_film_attr", filmInstance);
				// questo mi serve per la select di registi in pagina
				request.setAttribute("registi_list_attribute", registaService.listAllElements());
				request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione");
				request.getRequestDispatcher("/film/update.jsp").forward(request, response);
				return;
			}

			// se sono qui i valori sono ok quindi posso creare l'oggetto da inserire
			// occupiamoci delle operazioni di business
			filmService.aggiorna(filmInstance);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/film/insert.jsp").forward(request, response);
			return;
		}

		// andiamo ai risultati
		// uso il sendRedirect con parametro per evitare il problema del double save on
		// refresh
		response.sendRedirect("ExecuteListFilmServlet?operationResult=SUCCESS");

	}

}
