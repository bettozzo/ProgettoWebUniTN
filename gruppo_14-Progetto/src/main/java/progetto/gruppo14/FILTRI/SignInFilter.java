package progetto.gruppo14.FILTRI;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//gestire l'accesso alla pagina per il signIn e alla pagina di conferma per utenti non autorizzati

@WebFilter(filterName = "SignInFilter")
public class SignInFilter implements Filter {
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
        boolean isSignInRequest = richiesta.getRequestURI().contains("/SignIn");
        boolean isConfermaRequest = richiesta.getRequestURI().contains("/RegistrazioneConfermata");

        if(isLoggedIn && isSignInRequest){
            //l'utente è già loggato e vuole fare un sign in
            request.getRequestDispatcher("/PAGINE/SomethingWentWrong.jsp").forward(request, response);
        }
        else if (isConfermaRequest && request.getParameter("nome")==null) {
            //se l'utente sta cercando di accedere a Registrazione Confermata senza potere, significa che è arrivato qua
            //senza aver compilato il form di sign in, dunque 'nome' non esiste nella richiesta
            request.getRequestDispatcher("/PAGINE/SomethingWentWrong.jsp").forward(request, response);
        }
        else{
            chain.doFilter(request, response);
        }
    }
}
