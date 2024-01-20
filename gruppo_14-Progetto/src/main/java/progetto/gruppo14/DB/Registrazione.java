package progetto.gruppo14.DB;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


//far partire DB con: java -jar %DERBY_HOME%/lib/derbyrun.jar server start -noSecurityManager
@WebServlet(name = "Registrazione", value = "/Registrazione")
public class    Registrazione extends HttpServlet {
    String dbURL = "jdbc:derby://localhost:1527/UtentiDb";
    String user = "App";
    String password = "pw";
    Connection conn = null;
    public void init() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(dbURL, user, password);
        } catch (ClassNotFoundException | SQLException ignored){}
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        boolean successo = false;
        try {
            //se non è presente nel DB, l'utente può registrarsi
            if(!presenteInDb(request.getParameter("username"))){
                //la funzione inserisciDati(HttpServletRequest) ritorna l'esito dell'inserimento (true/false)
                successo = inserisciDati(request);
            }
        }catch (NullPointerException ignored) {}

        if(successo){
            String conferma = response.encodeURL("/PAGINE/RegistrazioneConfermata.jsp");
            request.getRequestDispatcher(conferma).forward(request, response);
        }
        else{
            request.getSession().setAttribute("SigninFallito", true);
            String singin = response.encodeURL("/PAGINE/SignIn.jsp");
            request.getRequestDispatcher(singin).forward(request, response);
        }
    }

    private boolean inserisciDati(HttpServletRequest request) {
        boolean inseritoConSuccesso = false;
        try {
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO Utenti VALUES('"+
                    request.getParameter("nome")+"','"+
                    request.getParameter("cognome")+"','"+
                    request.getParameter("username")+"','"+
                    request.getParameter("password")+"','"+
                    request.getParameter("ruolo")+"','"+
                    request.getParameter("nascita")+"','"+
                    request.getParameter("mail")+"','"+
                    request.getParameter("telefono")+"'"+
                    ")";
            inseritoConSuccesso = stmt.executeUpdate(sql) == 1;
            stmt.close();
        } catch (SQLException ignored){}
        return inseritoConSuccesso;
    }

    private boolean presenteInDb(String username) {
        boolean isPresente = true;
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM UTENTI WHERE USERNAME='"+username+"'";
            ResultSet results = stmt.executeQuery(sql);
            isPresente=results.next();//if results.next() = true -> query ha prodotto dei risultati -> username già presente
            results.close();
            stmt.close();
        } catch (SQLException ignored){}
        return isPresente;
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    public void destroy() {
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}