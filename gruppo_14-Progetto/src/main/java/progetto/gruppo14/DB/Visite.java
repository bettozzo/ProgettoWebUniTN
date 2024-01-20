package progetto.gruppo14.DB;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

//far partire DB con: java -jar %DERBY_HOME%/lib/derbyrun.jar server start -noSecurityManager
@WebServlet(name = "Visite", value = "/Visite")
public class Visite extends HttpServlet {
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

        if(request.getParameter("mostra")!=null) {
            mostraVisite();
        }else {
            if (request.getParameter("reset") != null) {
                resetVisite();
            }
            else {
                aggiornaVisite(request.getParameter("pagina"), response);
            }
        }
    }

    private void mostraVisite(){
        String visite="{\"Sito\":-1," +
                "\"Home\":-1," +
                "\"ChiSiamo\":-1," +
                "\"Attivita\":-1," +
                "\"DescrizioneA1\":-1," +
                "\"DescrizioneA2\":-1," +
                "\"DescrizioneA3\":-1," +
                "\"Contatti\":-1," +
                "\"EmailConfermata\":-1," +
                "\"SignIn\":-1," +
                "\"RegistrazioneConfermata\":-1," +
                "\"LogIn\":-1," +
                "\"AreaPersonale\":-1" +
                "}";

        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM VISITE";
            ResultSet resVisite = stmt.executeQuery(sql);
            if(resVisite.next()){
                visite="{\"Sito\":"+resVisite.getInt("sito")+"," +
                        "\"Home\":"+resVisite.getInt("home")+"," +
                        "\"ChiSiamo\":"+resVisite.getInt("chisiamo")+"," +
                        "\"Attivita\":"+resVisite.getInt("attivita")+"," +
                        "\"DescrizioneA1\":"+resVisite.getInt("DescrizioneA1")+"," +
                        "\"DescrizioneA2\":"+resVisite.getInt("DescrizioneA2")+"," +
                        "\"DescrizioneA3\":"+resVisite.getInt("DescrizioneA3")+"," +
                        "\"Contatti\":"+resVisite.getInt("contatti")+"," +
                        "\"EmailConfermata\":"+resVisite.getInt("emailConfermata")+"," +
                        "\"SignIn\":"+resVisite.getInt("signin")+"," +
                        "\"RegistrazioneConfermata\":"+resVisite.getInt("registrazioneconfermata")+"," +
                        "\"Login\":"+resVisite.getInt("login")+"," +
                        "\"AreaPersonale\":"+resVisite.getInt("AreaPersonale")+
                        "}";
            }
            stmt.close();
        }catch (SQLException | NullPointerException ignored) {}
        //fixme scrittura file (anche in donazioni)
        writeToJson(visite);
    }
    private void resetVisite(){
        String visite="{\"Sito\":-1," +
                "\"Home\":-1," +
                "\"ChiSiamo\":-1," +
                "\"Attivita\":-1," +
                "\"DescrizioneA1\":-1," +
                "\"DescrizioneA2\":-1," +
                "\"DescrizioneA3\":-1," +
                "\"Contatti\":-1," +
                "\"EmailConfermata\":-1," +
                "\"SignIn\":-1," +
                "\"RegistrazioneConfermata\":-1," +
                "\"LogIn\":-1," +
                "\"AreaPersonale\":-1" +
                "}";
        try {
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM VISITE WHERE TRUE";//cancella tutti i valori
            stmt.executeUpdate(sql);
            sql = "INSERT INTO VISITE VALUES (0,0,0,0,0,0,0,0,0,0,0,0,0)";
            Statement aggiornaValori = conn.createStatement();
            aggiornaValori.executeUpdate(sql);
            visite = "{" +
                    "\"Sito\":0," +
                    "\"Home\":0," +
                    "\"ChiSiamo\":0," +
                    "\"Attivita\":0," +
                    "\"DescrizioneA1\":0," +
                    "\"DescrizioneA2\":0," +
                    "\"DescrizioneA3\":0," +
                    "\"Contatti\":0," +
                    "\"EmailConfermata\":0," +
                    "\"SignIn\":0," +
                    "\"RegistrazioneConfermata\":0," +
                    "\"LogIn\":0," +
                    "\"AreaPersonale\":0" +
                    "}";
            aggiornaValori.close();
            stmt.close();
        }catch (SQLException | NullPointerException ignored) {}
        //fixme scrittura file
        writeToJson(visite);
    }


    //Aggiorna visite totali al sito.
    //Viene chiamata quando si crea la sessione
    public void aggiornaVisiteSito(){
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(dbURL, user, password);

            Statement updateStmt = conn.createStatement();
            //Aumenta di 0.5 perché viene chiamata due volte. => in totale: aumenta di 1
            String sql="UPDATE VISITE SET sito=(SELECT sito FROM VISITE)+0.5";
            updateStmt.executeUpdate(sql);

            conn.close();
        } catch (SQLException | ClassNotFoundException ignored) {}
    }
    private void aggiornaVisite(String pagina, HttpServletResponse response) throws IOException {
        String visite = "{\"success\":false}";
        try {

            Statement updateStmt = conn.createStatement();
            String aggiornamentoVisite="UPDATE VISITE" +
                    " SET " + pagina + "=(SELECT " + pagina + " FROM VISITE)+1"; // uguale a: visitePagina = visitePagina+1
            if(updateStmt.executeUpdate(aggiornamentoVisite) == 1)//se aumenta le visite ritorna true
                visite = "{\"success\":true}";
            updateStmt.close();

        }catch (SQLException | NullPointerException ignored) {}

        try (PrintWriter out = response.getWriter()) {
            out.println(visite);
        }
    }
    protected void writeToJson(String datiJson){
        try {
            FileWriter myWriter = new FileWriter("src/main/webapp/visite.JSON");
            myWriter.write(datiJson);
            myWriter.close();
        } catch (IOException ignored) {}
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