package progetto.gruppo14.DB;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

//far partire DB con: java -jar %DERBY_HOME%/lib/derbyrun.jar server start -noSecurityManager
@WebServlet(name = "Donazioni", value = "/Donazioni")
public class Donazioni extends HttpServlet {
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

    private void smistaRichiesta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        if(request.getParameter("mese")!=null)
            aggiungiDonazione(request.getParameter("mese"), Integer.parseInt(request.getParameter("donazione")), response);
        else if(request.getParameter("reset")!=null)
            resetDonazioni();
    }

    private void resetDonazioni() {
        String donazioniReset = "{\"successo\":false}";
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM DONAZIONI WHERE TRUE";//cancella tutti i valori
            stmt.executeUpdate(sql);

            sql = "INSERT INTO DONAZIONI VALUES (0,0,0,0,0,0,0,0,0,0,0,0)";
            Statement aggiornaValori = conn.createStatement();
            aggiornaValori.executeUpdate(sql);
            donazioniReset = "{\"successo\":true}";
            aggiornaValori.close();
            stmt.close();
        } catch (SQLException | NullPointerException ignored) {}
        writeToJson(donazioniReset);
    }

    private void aggiungiDonazione(String mese, int totaleDonazione, HttpServletResponse response) throws IOException {
        String donazioneAggiunta = "{\"successo\":false}";
        try {
            Statement stmt = conn.createStatement();
            String sql ="UPDATE Donazioni" +
                    " SET " +mese+ "=(SELECT "+mese+" FROM DONAZIONI)+" + totaleDonazione;
            if(stmt.executeUpdate(sql) == 1) {
                donazioneAggiunta = "{\"successo\":true}";
            }
            stmt.close();
        }catch (SQLException | NullPointerException ignored) {}
        try (PrintWriter out = response.getWriter()) {
            out.println(donazioneAggiunta);
        }
    }

    private void returnAllDonazioni(HttpServletResponse response) throws IOException {
        String donazioniTotali ="{\"successo\":false," +
                "\"mese\":[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]" +
                "}";
        try {
            Statement stmt = conn.createStatement();
            String sql ="SELECT * FROM DONAZIONI";
            ResultSet result = stmt.executeQuery(sql);
            if(result.next()){
                donazioniTotali ="{\"successo\":true," +
                        "\"mese\":[" +
                        result.getInt(1)+ ", "+
                        result.getInt(2)+ ", "+
                        result.getInt(3)+ ", "+
                        result.getInt(4)+ ", "+
                        result.getInt(5)+ ", "+
                        result.getInt(6)+ ", "+
                        result.getInt(7)+ ", "+
                        result.getInt(8)+ ", "+
                        result.getInt(9)+ ", "+
                        result.getInt(10)+ ", "+
                        result.getInt(11)+ ", "+
                        result.getInt(12)+
                        "]" +
                        "}";
            }
            result.close();
            stmt.close();
        }catch (SQLException | NullPointerException ignored) {}

        writeToJson(donazioniTotali);

        try (PrintWriter out = response.getWriter()) {
            out.println(donazioniTotali);
        }
    }

    protected void writeToJson(String datiJson){
        try {
            FileWriter myWriter = new FileWriter("./JSON/donazioni.JSON");
            myWriter.write(datiJson);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("ERRORE SCRITTURA FILE");
            e.printStackTrace();
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