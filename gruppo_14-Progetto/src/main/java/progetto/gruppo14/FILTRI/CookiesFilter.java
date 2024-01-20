package progetto.gruppo14.FILTRI;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.IOException;
import javax.servlet.ServletException;

import progetto.gruppo14.SESSIONE_COOKIES.CookieStuff;
import progetto.gruppo14.SESSIONE_COOKIES.Sessione;

//verificare se è già stato chiesto all'utente di accettare i cookies (tramite pop up)
@WebFilter(filterName = "CookiesFilter")
public class CookiesFilter implements Filter {
    protected FilterConfig filterconfig;

    public void init(FilterConfig config) throws ServletException {
        this.filterconfig = filterconfig;
    }
    public void destroy() {
        this.filterconfig = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest myRequest = (HttpServletRequest) request;
        HttpSession mySession = myRequest.getSession(false);

        if (mySession == null) {
            Sessione sessionObject = new Sessione();
            sessionObject.dealWithInvalidSession((HttpServletRequest) request, (HttpServletResponse) response);
        }
        else {
            CookieStuff cookieStuff = new CookieStuff();
            boolean isThereCookie = cookieStuff.isThereCookie(myRequest); // verifica se esiste gia un cookie nel browser

            if (isThereCookie) { //c'è gia un cookie, non serve chiedere perchè già accettato in passato
                mySession.setAttribute("DoPopUp", false);
            }
            chain.doFilter(request, response);
        }
    }
}

