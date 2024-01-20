package progetto.gruppo14;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


//servlet di partenza del sito: si occupa del urlencoding e ridirige l'utente alla pagina home
@WebServlet(name = "index", value = "/index")
public class index extends HttpServlet {

    public void dealWithRequest (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String homepage = response.encodeURL("/PAGINE/Home.jsp");
        request.getRequestDispatcher(homepage).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dealWithRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dealWithRequest(request, response);
    }
}
