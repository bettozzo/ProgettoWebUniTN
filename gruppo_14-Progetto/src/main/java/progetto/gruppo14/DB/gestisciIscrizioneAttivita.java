package progetto.gruppo14.DB;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


//far partire DB con: java -jar %DERBY_HOME%/lib/derbyrun.jar server start -noSecurityManager
@WebServlet(name = "gestisciIscrizioneAttivita", value = "/gestisciIscrizioneAttivita")
public class gestisciIscrizioneAttivita extends HttpServlet {
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
    protected void smistaRichiesta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        if(request.getParameter("iscrizione").equals("true")){
            iscrivi(request, response);
        }
        else
            disiscrivi(request, response);
    }

    private void iscrivi(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //prende username dalla sessione
        String username = ((String)request.getSession().getAttribute("username")).replaceAll("\"", "");
        String iscrittoConSuccesso = "{\"iscritto\":false}";

        try {
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO PARTECIPA VALUES('"+username+"', '"+request.getParameter("attivita")+"')";
            if(stmt.executeUpdate(sql) == 1) {//se viene aggiunto, ritorno che si è iscritto con successo
                iscrittoConSuccesso = "{\"iscritto\":true}";
            }
            stmt.close();
        }catch (SQLException | NullPointerException ignored) {}

        try (PrintWriter out = response.getWriter()) {
            out.println(iscrittoConSuccesso);
        }
    }

    private void disiscrivi(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //prende username dalla sessione
        String username = ((String)request.getSession().getAttribute("username")).replaceAll("\"", "");
        String iscrittoConSuccesso = "{\"disiscritto\":false}";

        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM PARTECIPA WHERE UTENTI_ID='"+username+"' AND ATTIVITA_ID='"+request.getParameter("attivita")+"'";
            if(stmt.executeUpdate(sql) == 1) {
                iscrittoConSuccesso = "{\"disiscritto\":true}";
            }
            stmt.close();

        }catch (SQLException | NullPointerException ignored) {}

        try (PrintWriter out = response.getWriter()) {
            out.println(iscrittoConSuccesso);
        }
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        smistaRichiesta(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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