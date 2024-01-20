package progetto.gruppo14.DB;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

//far partire DB con: java -jar %DERBY_HOME%/lib/derbyrun.jar server start -noSecurityManager
@WebServlet(name = "RetrieveUtentiRuolo", value = "/RetrieveUtentiRuolo")
public class RetrieveUtentiRuolo extends HttpServlet {
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

    protected void smistaRichiesta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getParameter("singolo")!=null){
            getSingoloUtente((((String)request.getSession().getAttribute("username")).replaceAll("\"", "")), response);
        }else{
            getAllUtenti(request.getParameter("ruolo"), response);
        }
    }
    private void getSingoloUtente(String username, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String output = "{" +
                "\"nome\":\"\"," +
                "\"cognome\":\"\"," +
                "\"username\":\"\"," +
                "\"ruolo\":\"\"," +
                "\"nascita\":\"\"," +
                "\"email\":\"\"," +
                "\"telefono\":\"\"," +
                "\"attivita\":[]"+
                "}";
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM UTENTI WHERE USERNAME='" + username +"'";
            ResultSet resultUtenti = stmt.executeQuery(sql);
            StringBuilder attivita = new StringBuilder();
            if(resultUtenti.next()) {
                //se utente ha fatto il login, prendo dal DB le attività a cui è iscritto
                sql = "SELECT A.TITOLO FROM UTENTI U, ATTIVITA A, PARTECIPA P WHERE P.UTENTI_ID = U.USERNAME AND P.ATTIVITA_ID = A.TITOLO AND U.USERNAME = '"+username+"'";

                Statement stmtAttivita = conn.createStatement();
                ResultSet attivitaUtente = stmtAttivita.executeQuery(sql);
                while(attivitaUtente.next()){
                    //"attivita" è una stringa con formato: "<titolo>","<titolo>",...,
                    attivita.append("\"").append(attivitaUtente.getString("titolo")).append("\",");
                }
                if(attivita.length() != 0)//se utente è iscritto a qualche attività
                    attivita.deleteCharAt(attivita.length()-1);//cancella ultima virgola
                attivitaUtente.close();
                stmtAttivita.close();

                output = "{" +
                        "\"nome\":\""+resultUtenti.getString("nome")+"\"," +
                        "\"cognome\":\""+resultUtenti.getString("cognome")+"\"," +
                        "\"username\":\""+resultUtenti.getString("username")+"\"," +
                        "\"ruolo\":\""+resultUtenti.getString("ruolo")+"\"," +
                        "\"nascita\":\""+resultUtenti.getString("nascita")+"\"," +
                        "\"email\":\""+resultUtenti.getString("email")+"\"," +
                        "\"telefono\":\""+resultUtenti.getString("telefono")+"\"," +
                        "\"attivita\":["+attivita+"]"+
                        "}";
            }
            resultUtenti.close();
            stmt.close();
        }catch (SQLException ignored) {}

        try (PrintWriter out = response.getWriter()) {
            out.println(output);
        }
    }
    protected void getAllUtenti(String ruolo, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String output = "{\"utenti\":[]}";
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM UTENTI";
            if(!ruolo.equals("tutti"))
                sql += " WHERE RUOLO='" + ruolo+"'";

            ResultSet resultUtenti = stmt.executeQuery(sql);
            StringBuilder utenti = new StringBuilder();
            while(resultUtenti.next()){

                String singoloJson = "{" +
                                            "\"nome\":\""+resultUtenti.getString("nome")+"\"," +
                                            "\"cognome\":\""+resultUtenti.getString("cognome")+"\"," +
                                            "\"username\":\""+resultUtenti.getString("username")+"\"," +
                                            "\"nascita\":\""+resultUtenti.getString("nascita")+"\"," +
                                            "\"email\":\""+resultUtenti.getString("email")+"\"," +
                                            "\"telefono\":\""+resultUtenti.getString("telefono")+"\""+
                                        "},";
                utenti.append(singoloJson);
            }

            if(utenti.length() != 0)//se ci sono degli utenti
                utenti.deleteCharAt(utenti.length()-1); //cancella ultima virgola

            resultUtenti.close();
            stmt.close();
            output = "{\"utenti\":["+utenti+"]}";

        }catch (SQLException ignored) {}

        try (PrintWriter out = response.getWriter()) {
            out.println(output);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        smistaRichiesta(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        smistaRichiesta(request, response);
    }

    public void destroy() {
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}