package progetto.gruppo14.DB;

import progetto.gruppo14.SESSIONE_COOKIES.Sessione;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Objects;

//far partire DB con: java -jar %DERBY_HOME%/lib/derbyrun.jar server start -noSecurityManager
@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    String dbURL = "jdbc:derby://localhost:1527/UtentiDb";
    String user = "App";
    String password = "pw";
    Connection conn = null;
    public void init() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(dbURL, user, password);
        } catch (ClassNotFoundException | SQLException ignored) {}
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        String utenteJson = "";
        try {
            utenteJson = logIn(request);
        }
        catch (NullPointerException ex) {
            utenteJson ="ERRORE";
        }

        //prende elementi da JSON
        String[] elementi = utenteJson.split("[{}:,]");
        String isLogged = elementi[2];
        String username = elementi[6];
        String ruolo = elementi[8];

        HttpSession mysession = request.getSession(false); //parametro "false" serve a non creare una nuova sessione

        if(Objects.equals(utenteJson, "ERRORE") || isLogged.equals("false")){
            mysession.setAttribute("LoginFallito", true);
            request.getRequestDispatcher("/PAGINE/Login.jsp").forward(request, response);
        }
        else {
            if(mysession == null) {
                Sessione sessioneObject = new Sessione();
                sessioneObject.dealWithInvalidSession(request, response);
            }
            else {
                mysession.setAttribute("username", username);
                mysession.setAttribute("ruolo", ruolo);
                String areaPersonele = response.encodeURL("/PAGINE/AreaPersonale.jsp");
                request.getRequestDispatcher(areaPersonele).forward(request, response);
            }
        }
    }

    private String logIn(HttpServletRequest request) {
        String utenteJson = "{" +
                "\"loggato\":false," +
                "\"utente\":{" +
                "\"username\":\"\"," +
                "\"ruolo\":\"\"" +
                "}" +
                "}";
        try {
            Statement stmtUtente = conn.createStatement();
            String sql = "SELECT * FROM Utenti WHERE USERNAME='" + request.getParameter("username") + "' AND PASSWORD='" + request.getParameter("password") + "'";
            ResultSet utenteLoggato = stmtUtente.executeQuery(sql);
            if (utenteLoggato.next()){ //se username e password, esistono e coincidono, questo if-statement è true
                utenteJson = "{" +
                                "\"loggato\":true," +
                                "\"utente\":" +
                                "{" +
                                    "\"username\":\"" + utenteLoggato.getString("username") + "\"," +
                                    "\"ruolo\":\"" + utenteLoggato.getString("ruolo") + "\"" +
                                "}" +
                            "}";
            }
            utenteLoggato.close();
            stmtUtente.close();
        } catch (SQLException ignored) {}

        return utenteJson;
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