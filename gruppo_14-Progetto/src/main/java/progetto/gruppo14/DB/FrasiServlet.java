package progetto.gruppo14.DB;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

//far partire DB con: java -jar %DERBY_HOME%/lib/derbyrun.jar server start -noSecurityManager
@WebServlet(name = "FrasiServlet", value = "/FrasiServlet")
public class FrasiServlet extends HttpServlet {
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

    //prende tutte le frasi presenti nel database e le restituisce in una stringa formattata: "<frase>", "<frase>", ...,
    protected void processRequest(HttpServletResponse response) throws IOException {
        String output = "";
        try {
            Statement getFrasiStmt = conn.createStatement();
            String sql = "SELECT * FROM FRASI";
            ResultSet frasiDb = getFrasiStmt.executeQuery(sql);
            while(frasiDb.next()) {
                output += "\""+frasiDb.getString(1)+"\"";
            }
            frasiDb.close();
            getFrasiStmt.close();
        }catch (SQLException | NullPointerException ignored) {}

        try (PrintWriter out = response.getWriter()) {
            out.println(output);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(response);
    }

    public void destroy() {
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}