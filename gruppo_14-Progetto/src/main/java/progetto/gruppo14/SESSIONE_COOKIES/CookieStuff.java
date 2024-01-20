package progetto.gruppo14.SESSIONE_COOKIES;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


//gestisce i cookies, ha a disposizione una funzione per controllare se siano già presente un cookie nel browser
//e gestisce le richieste per creare un nuovo cookie

@WebServlet(name = "CreaCookie", value = "/CreaCookie")
public class CookieStuff extends HttpServlet {
    public boolean isThereCookie(HttpServletRequest request){
        boolean res = false;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie c : cookies) {
                String name = c.getName();
                if (name.equals("Tum4World")) {
                    c.setMaxAge(60*60*24*30); //cosi non muore
                    res = true;
                }
            }
        }
        return res;
    }
    public void createCookie(HttpServletResponse response){
        Cookie myCookie = new Cookie("Tum4World", "Tum4World");
        myCookie.setMaxAge(60*60*24*30); //un mese
        response.addCookie(myCookie);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        createCookie(response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        createCookie(response);
    }
}
