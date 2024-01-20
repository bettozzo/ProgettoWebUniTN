package progetto.gruppo14.DB;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

//far partire DB con: java -jar %DERBY_HOME%/lib/derbyrun.jar server start -noSecurityManager
@WebServlet(name = "CancellazioneDatiUtenti", value = "/CancellazioneDatiUtenti")
public class CancellazioneDatiUtenti extends HttpServlet {
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

    protected void cancellaDati(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json");
        String output = "{\"cancellato\":false}";
        try {
            Statement stmt = conn.createStatement();

            String username = ((String)request.getSession().getAttribute("username")).replaceAll("\"", "");
            //Conto a quante attività l'utente partecipa per poi sapere se la richiesta è andata a buon fine
            String sql = "SELECT COUNT(*) FROM PARTECIPA WHERE UTENTI_ID = '"+username+"'";
            ResultSet result = stmt.executeQuery(sql);
            int countAttivitaIscritto = 0;
            if(result.next()){
                countAttivitaIscritto = result.getInt(1);
            }

            //Cancella l'utente prima dalla tabella delle Attività per evitare problemi di dependecies
            sql = "DELETE FROM PARTECIPA WHERE UTENTI_ID ='"+username+"'";
            boolean rimossoCorrettamenteAttivita = stmt.executeUpdate(sql) == countAttivitaIscritto;

            //Cancella l'utente dalla tabella Utenti
            sql = "DELETE FROM UTENTI WHERE USERNAME ='"+username+"'";
            boolean rimossoCorrettamenteUtenti = stmt.executeUpdate(sql) != 0;

            stmt.close();

            if(rimossoCorrettamenteAttivita && rimossoCorrettamenteUtenti){
                output = "{\"cancellato\":true}";
            }

        }catch (SQLException ignored) {}

        try (PrintWriter out = response.getWriter()) {
            out.println(output);

        } catch (IOException ignored) {

        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cancellaDati(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cancellaDati(request, response);
    }

    public void destroy() {
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}