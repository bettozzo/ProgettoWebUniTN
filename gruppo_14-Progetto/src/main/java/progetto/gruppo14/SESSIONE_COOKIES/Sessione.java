package progetto.gruppo14.SESSIONE_COOKIES;

import progetto.gruppo14.DB.Visite;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


//classe che si occupa di creare la sessione, inizializzarne gli attributi,
// e porta ad una pagina di errore se la sessione non è piu valida
//per contare le visit al sito, nel momento in cui viene creata una sessione si chiama il db per incrementare i contatori

@WebServlet(name = "Sessione", value = "/Sessione")
public class Sessione {

    public void creaSessione(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession mySession = request.getSession(false);
        if(mySession == null){
            mySession = request.getSession();
            mySession.setAttribute("username", null);
            mySession.setAttribute("ruolo", null);
            mySession.setAttribute("DoPopUp", true);
            mySession.setAttribute("LoginFallito", false);
            mySession.setAttribute("SigninFallito", false);
            Visite visiteDB = new Visite();
            visiteDB.aggiornaVisiteSito();
        }
    }
    public void dealWithInvalidSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
       String errorPage = response.encodeURL("./PAGINE/SomethingWentWrong.jsp");
        request.getRequestDispatcher(errorPage).forward(request, response);
    }

}
