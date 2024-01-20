package progetto.gruppo14.FILTRI;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//gestire l'accesso alla area personale e login per utenti non autorizzati

@WebFilter(filterName = "LoginFilter")
public class LoginFilter implements Filter {
    protected FilterConfig filterconfig;

    public void init(FilterConfig config) throws ServletException {
        this.filterconfig = filterconfig;
    }
    public void destroy() {
        this.filterconfig = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest richiesta = (HttpServletRequest) request;
        HttpSession session = richiesta.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("username") != null);

        //richiesta.getRequestURI() ritorna l'uri della richiesta
        boolean isLoginRequest = richiesta.getRequestURI().contains("Login"); //sta cercando di accedere alla pagina login?
        boolean isPersonaleRequest = richiesta.getRequestURI().contains("AreaPersonale"); //sta cercando di accedere alla pagina area personale?

        if (isLoggedIn && isLoginRequest) {
            // l'utente è gia loggato e sta cercando di accedere nuovamente alla pagina login
            richiesta.getRequestDispatcher("/PAGINE/SomethingWentWrong.jsp").forward(request, response);
        }
        else if (!isLoggedIn && isPersonaleRequest) {
            // l'utente non è loggato ma sta cercando di accedere all'area personale
            richiesta.getRequestDispatcher("/PAGINE/SomethingWentWrong.jsp").forward(request, response);
        }
        else {
            chain.doFilter(request, response);
        }
    }
}
