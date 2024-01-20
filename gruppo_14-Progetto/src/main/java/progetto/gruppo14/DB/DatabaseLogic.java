package progetto.gruppo14.DB;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

//far partire DB con: java -jar %DERBY_HOME%/lib/derbyrun.jar server start -noSecurityManager
public class DatabaseLogic {
    static Connection conn = null;
    public static void main(String[] args) {
        String dbURL = "jdbc:derby://localhost:1527/UtentiDb;";
        String user = "App";
        String password = "pw";
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(dbURL, user, password);

            initUtenti();
            initAttivita();
            initPartecipazioni();
            initFrasi();
            initVisite();
            initDonazioni();

            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                //se non si chiude la connessione prima (a causa di un errore), la si chiude ora
                if(!conn.isClosed())
                    conn.close();
            } catch (SQLException ignored) {}
        }
    }


    private static void initUtenti() {

        createTable("Utenti",
                "(" +
                        "Nome VARCHAR(32),"+
                        "Cognome VARCHAR(32),"+
                        "Username VARCHAR(32),"+
                        "Password VARCHAR(32),"+
                        "Ruolo VARCHAR(14),"+
                        "Nascita VARCHAR(11),"+
                        "Email VARCHAR(64),"+
                        "Telefono VARCHAR(10)," +
                        "PRIMARY KEY(USERNAME)"+
                        ")"
        );

        ArrayList<String> utenti = new ArrayList<>(10);

        //pattern: nome, cognome, username, pass, ruolo, dataNascita, mail, telefono
        utenti.add("('Admin','Admin','admin','14Adm1n!','admin','01-01-1970','admin@admin.it','000000000')");
        utenti.add("('Luca','Nervi','Sindacalista','S!4Gueva','aderente','13-07-1971','luca@nervi.it','332454365')");
        utenti.add("('Paolo','Bitta','Contratto','T0CTOsa!','aderente','25-07-1969','paolo@bitta.it','444444444')");
        utenti.add("('Augusto','DeMarinis','AugustoDema9','1234aDm!','simpatizzante','12-12-1949','augusto@deMarinis.it','987969568')");
        utenti.add("('Silvano','Rogi','Contabile','$TO0GoAS','aderente','28-09-1977','silvano@rogi.it','628318530')");
        utenti.add("('Guido','Geller','Geller','Tast1ng!','simpatizzante','09-03-1957','guido@geller.it','889999999')");
        utenti.add("('Alessandra','Costa','Alex','Rase4Ev$','aderente','16-02-1971','alex@costa.it','533212323')");
        utenti.add("('Giuseppe','LoCascio','Pippo','m4sVera?','simpatizzante','16-05-1965','giuseppe@loCascio.it','123211132')");
        utenti.add("('Patrizia','DImprozano','Patti','$TO0GoAS','aderente','13-04-1969','patrizia@dImporzano.it','123420952')");
        utenti.add("('Olmo','Ghesizzi','Surfer','0lmOGhz!','simpatizzante','25-01-1974','olmo@ghesizzi.it','112323414')");
        insertData("Utenti", utenti);
    }

    private static void initAttivita() {
        createTable("Attivita",
                "(" +
                        "Titolo VARCHAR(128)," +
                        "PRIMARY KEY(Titolo)" +
                        ")");

        ArrayList<String> attivita = new ArrayList<>(3);
        attivita.add("('Sanità')");
        attivita.add("('Sensibilizzazione')");
        attivita.add("('Didattica')");
        insertData("Attivita", attivita);
    }


    private static void initPartecipazioni() {
        createTable("Partecipa",
                "(" +
                        "utenti_id VARCHAR(32) REFERENCES Utenti(Username)," +
                        "attivita_id VARCHAR(128) REFERENCES Attivita(Titolo)," +
                        "PRIMARY KEY(utenti_id, attivita_id)" +
                        ")");

        ArrayList<String> partecipazioni = new ArrayList<>(12);
        partecipazioni.add("('Contabile', 'Sanità')");
        partecipazioni.add("('Contabile', 'Sensibilizzazione')");
        partecipazioni.add("('Contabile', 'Didattica')");
        partecipazioni.add("('Contratto', 'Sensibilizzazione')");
        partecipazioni.add("('Contratto', 'Sanità')");
        partecipazioni.add("('Sindacalista', 'Sensibilizzazione')");
        partecipazioni.add("('Alex', 'Sanità')");
        partecipazioni.add("('Alex', 'Sensibilizzazione')");
        partecipazioni.add("('Patti', 'Sanità')");
        partecipazioni.add("('Patti', 'Didattica')");
        partecipazioni.add("('Surfer', 'Sanità')");
        partecipazioni.add("('Pippo', 'Sensibilizzazione')");
        insertData("Partecipa", partecipazioni);
    }


    private static void initFrasi() {
        createTable("Frasi","(Frasi VARCHAR(512))");

        ArrayList<String> frasi = new ArrayList<>();
        //lettura del file "frasiDaInserire.txt"
        try {
            File fileFrasi = new File("./src/main/java/progetto/gruppo14/DB/frasiDaInserire.txt");
            Scanner scanner = new Scanner(fileFrasi);
            while (scanner.hasNextLine()) {
                String stringa = scanner.nextLine();
                if(!stringa.equals(""))//ignora righe vuote
                    frasi.add("'"+stringa+"'");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        insertData("Frasi", frasi);
    }

    private static void initVisite() {
        createTable("Visite",
                "(" +
                        "Sito FLOAT," +
                        "Home INT," +
                        "ChiSiamo INT," +
                        "Attivita INT," +
                        "DescrizioneA1 INT," +
                        "DescrizioneA2 INT," +
                        "DescrizioneA3 INT," +
                        "Contatti INT," +
                        "emailConfermata INT," +
                        "SignIn INT," +
                        "RegistrazioneConfermata INT," +
                        "Login INT," +
                        "AreaPersonale INT"+
                        ")");

        ArrayList<String> visite = new ArrayList<>(1);
        visite.add("(3000,800,500,2000,1600,800,2500,1100,2500,2000,3000,1000,2000)");
        insertData("Visite", visite);
    }

    private static void initDonazioni() {
        //mi sono permesso di assumere meno di 2147483647€ in donazioni al mese
        createTable("Donazioni",
                "(" +
                        "Gennaio INT, " +
                        "Febbraio INT, " +
                        "Marzo INT, " +
                        "Aprile INT, " +
                        "Maggio INT, " +
                        "Giugno INT, " +
                        "Luglio INT, " +
                        "Agosto INT, " +
                        "Settembre INT, " +
                        "Ottobre INT, " +
                        "Novembre INT, " +
                        "Dicembre INT " +
                        ")");
        ArrayList<String> donazioni = new ArrayList<>(1);
        donazioni.add("(2000,2000,3000,2000,2000,3000,3000,4000,2000,1000,3000,6000)");
        insertData("Donazioni", donazioni);
    }
    private static void createTable(String table, String colonne){
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE " + table + " " + colonne);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void insertData(String table, ArrayList<String> dati){
        try {
            Statement stmt = conn.createStatement();
            for (String sql : dati) {
                stmt.executeUpdate("INSERT INTO " + table + " VALUES "+sql);
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

