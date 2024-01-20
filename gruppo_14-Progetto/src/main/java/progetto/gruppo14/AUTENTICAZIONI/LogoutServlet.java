package progetto.gruppo14.AUTENTICAZIONI;

import progetto.gruppo14.SESSIONE_COOKIES.Sessione;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

//la servlet si occupa di eseguire il logout dell'utente (ovvero renderlo nuovamente anonimo nella sessione) e reindirizzarlo alla home
@WebServlet(name = "LogoutServlet", value = "/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    protected void dealwithRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession mysession = request.getSession(false);

        if(mysession==null){
            Sessione sessioneObj = new Sessione();
            sessioneObj.dealWithInvalidSession(request, response);
        }
        else{
            mysession.setAttribute("username", null);
            String home = response.encodeURL("./PAGINE/Home.jsp");
            request.getRequestDispatcher(home).forward(request, response);
        }
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dealwithRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dealwithRequest(request, response);
    }
}
