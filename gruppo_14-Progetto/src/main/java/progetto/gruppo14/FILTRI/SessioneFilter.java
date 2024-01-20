package progetto.gruppo14.FILTRI;

import progetto.gruppo14.SESSIONE_COOKIES.Sessione;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//chiama il metodo creaSessione() della classe Sessione che, se non esiste già, crea una nuova sessione
//se per qualche motivo la creazione della sessione non dovesse andare a buon fine, chiama il metodo dealWithInvalidSession() della classe Sessione

@WebFilter(filterName = "SessioneFilter")
public class SessioneFilter implements Filter {
    protected FilterConfig filterconfig;

    public void init(FilterConfig config) throws ServletException {
        this.filterconfig = filterconfig;
    }
    public void destroy() {
        this.filterconfig = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        Sessione sessionObject = new Sessione();
        sessionObject.creaSessione((HttpServletRequest) request,(HttpServletResponse) response);
        HttpSession currentSession = ((HttpServletRequest) request).getSession(false);

        if(currentSession == null){
            sessionObject.dealWithInvalidSession((HttpServletRequest)request, (HttpServletResponse) response);
        }
        else {
            chain.doFilter(request, response);
        }
    }
}
