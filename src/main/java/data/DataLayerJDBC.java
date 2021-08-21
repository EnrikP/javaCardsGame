package data;

import logica.*;
import logica.Spel;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 
 */
public class DataLayerJDBC {

    private String dbName;
    private final String login = "root";
    private final String pass = "azerty";
    private Connection con;

    public String getDbName() {
        return dbName;
    }

    public DataLayerJDBC(String dbName, boolean alternative) {
        this.dbName = dbName;
        if (alternative) {
            makeConnectionAlternative();
        } else {
            makeConnection();
        }
    }

    private void makeConnection() {
        try {
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"
                    + dbName + "?serverTimezone=UTC&allowMultiQueries=true", login, pass);

        } catch (SQLException ex) {
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void makeConnectionAlternative() {
        try {
            Properties connectionProps = new Properties();
            connectionProps.setProperty("user", this.login);
            connectionProps.setProperty("password", this.pass);
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"
                    + dbName + "?serverTimezone=UTC&allowMultiQueries=true", connectionProps);
        } catch (SQLException ex) {
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public List<Speler> getSpelerList() throws SQLException {
        Statement stmt = null;
        List<Speler> spelerList = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery("SELECT * FROM spelers");
            spelerList = new ArrayList<>();
            //rs.first();
            while (rs.next()) {
                int spel_id = rs.getInt("spel_id");
                int rol_id = rs.getInt("rol_id");
                String naam = rs.getString("naam");

                Speler speler = new Speler(spel_id,rol_id,naam);
                spelerList.add(speler);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return spelerList;
    }
    public List<Speler> getNewSpelers() throws SQLException {
        Statement stmt = null;
        List<Speler> spelerList = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery("SELECT * FROM spelers WHERE spel_id=(SELECT max(spel_id) FROM spelers);");
            spelerList = new ArrayList<>();
            //rs.first();
            while (rs.next()) {
                int spel_id = rs.getInt("spel_id");
                int rol_id = rs.getInt("rol_id");
                String naam = rs.getString("naam");

                Speler speler = new Speler(spel_id,rol_id,naam);
                spelerList.add(speler);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return spelerList;
    }
    public StadKleur toStadKleur(String kl) {
        switch(kl) {
            case "ROOD": return StadKleur.ROOD;
            case "BLAUW": return StadKleur.BLAUW;
            case "ZWART": return StadKleur.ZWART;
            case "GEEL": return StadKleur.GEEL;
        }
        throw new IllegalArgumentException("Error, geen geldig kleur stad. Fout met programma");
    }
    public List<Stad> getStedenList() throws SQLException {
        Statement stmt = null;
        List<Stad> stadList = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery("SELECT * FROM steden");
            stadList = new ArrayList<>();
            //rs.first();
            while (rs.next()) {
                int stad_id = rs.getInt("id");
                String naam = rs.getString("naam");
                StadKleur kleur = toStadKleur(rs.getString("kleur"));
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                Stad stad = new Stad(stad_id,naam,kleur,x,y);
                stadList.add(stad);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return stadList;
    }

    public List<Rol> getRollenList() throws SQLException {
        Statement stmt = null;
        List<Rol> rollenList = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery("SELECT * FROM rollen");
            rollenList = new ArrayList<>();
            //rs.first();
            while (rs.next()) {
                int rol_id = rs.getInt("id");
                String naam = rs.getString("naam");
                String beschrijving = rs.getString("beschrijving");
                Rol rol = new Rol(rol_id,naam,beschrijving);
                rollenList.add(rol);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return rollenList;
    }

    public List <String> getRollenNaamList() throws SQLException {
        Statement stmt = null;
        List<String> naamList = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery("SELECT * FROM rollen");
            naamList = new ArrayList<>();
            //rs.first();
            while (rs.next()) {
                String naam = rs.getString("naam");
                naamList.add(naam);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return naamList;
    }


    public void insertWithoutPreparedStatement(String user) throws SQLException {
        Statement stmt = null;
        try {
            stmt = this.con.createStatement();
            stmt.execute("INSERT INTO studenten (voornaam) VALUES('" + user + "')");
        } catch (SQLException ex) {
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }




    public List<String> getTables() throws SQLException {
        Statement stmt = null;
        List<String> tables = null;
        try {
            stmt = this.con.createStatement();

            ResultSet rs = stmt.executeQuery("SHOW TABLES");
            tables = new ArrayList<>();

            while (rs.next()) {
                String table = rs.getString(1);
                tables.add(table);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return tables;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }


    public void updateSpel(LocalDateTime einde, boolean gewonnen, int spelId) throws SQLException {
        PreparedStatement updateSpelen = null;
        try {
            updateSpelen = this.con.prepareStatement("UPDATE spellen SET einde = ?, gewonnen = ? WHERE id = ?");
            updateSpelen.setTimestamp(1, Timestamp.valueOf(einde));
            updateSpelen.setBoolean(2, gewonnen);
            updateSpelen.setInt(3, spelId);
            updateSpelen.executeUpdate();

            this.con.commit();


        } catch (SQLException ex) {
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
            if (this.con != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    con.rollback();
                } catch (SQLException excep) {
                    Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, excep);
                }
            }
        }finally {
            if (updateSpelen != null) updateSpelen.close();
        }

    }
    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public List<Spel> getSpellenList() throws SQLException {
        Statement stmt = null;
        List<Spel> spelList = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery("SELECT * FROM spellen");
            spelList = new ArrayList<>();
            //rs.first();
            while (rs.next()) {
                int rol_id = rs.getInt("id");
                LocalDateTime start = convertToLocalDateTimeViaInstant(rs.getDate("start"));
                LocalDateTime einde = convertToLocalDateTimeViaInstant(rs.getDate("einde"));
                boolean gewonnen = rs.getBoolean("gewonnen");
                Spel spel = new Spel(rol_id,start,einde,gewonnen);
                spelList.add(spel);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return spelList;
    }
    public void insertWithPreparedStatement(Speler speler) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = this.con.prepareStatement("INSERT INTO spelers(spel_id, rol_id, naam) VALUES (?,?,?)");
            stmt.setInt(1,speler.getspel_id());
            stmt.setInt(2, speler.getrol_id());
            stmt.setString(3, speler.getNaam());
            stmt.executeUpdate();
            this.con.commit();

        } catch (SQLException ex) {
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
    public void insertWithPreparedStatementSpelStart(Spel spel) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = this.con.prepareStatement("INSERT INTO spellen(start) VALUES (?)");
            stmt.setTimestamp(1, Timestamp.valueOf(spel.getStart()));
            stmt.executeUpdate();
            con.setAutoCommit(false);
            this.con.commit();

        } catch (SQLException ex) {
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
    public int getLastGeneratedKeySpellen() throws SQLException{
        Statement stmt = null;
        int id = 0;
        try {
            stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery("SELECT * FROM spellen");

            if (rs.last()){
                id = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return id;
    }
    public Stad getStadMetId(int id) throws SQLException {

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT * FROM steden WHERE ID = " + id;
            ResultSet rs = stmt.executeQuery(query);
            while(rs.first()) {
                int stad_id = rs.getInt("id");
                String naam = rs.getString("naam");
                StadKleur kleur = toStadKleur(rs.getString("kleur"));
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                Stad stad = new Stad(stad_id, naam, kleur, x, y);
                return stad;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        throw new IllegalArgumentException("Stad met dit ID niet gevonden.");
    }
    public Stad getStadMetNaam(String naam) throws SQLException {

        Statement stmt = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT * FROM steden WHERE naam = " +"'" + naam+"'";
            ResultSet rs = stmt.executeQuery(query);
            rs.first();
            int stad_id = rs.getInt("id");
            String n = rs.getString("naam");
            StadKleur kleur = toStadKleur(rs.getString("kleur"));
            int x = rs.getInt("x");
            int y = rs.getInt("y");
            return new Stad(stad_id, n, kleur, x, y);

        } catch (SQLException ex) {
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        throw new IllegalArgumentException("Stad met deze naam niet gevonden.");
    }
    public List<Connectie> getConnectiesList() throws SQLException {
        Statement stmt = null;
        List<Connectie> connectieList = null;
        try {
            stmt = this.con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery("SELECT * FROM connecties");
            connectieList = new ArrayList<>();
            //rs.first();
            while (rs.next()) {

                int stad1_id = rs.getInt("stad1_id");
                int stad2_id = rs.getInt("stad2_id");
                Stad stad1 = getStadMetId(stad1_id);
                Stad stad2 = getStadMetId(stad2_id);

                Connectie connectie = new Connectie(stad1, stad2);
                connectieList.add(connectie);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return connectieList;
    }

    public static void main(String[] args) {
        try {

            DataLayerJDBC dataLayer = new DataLayerJDBC("pandemie", true);

            List<Speler> spelerList = dataLayer.getSpelerList();
            System.out.println("de begin situatie");
            for (Speler s : spelerList) {
                System.out.println("\t"+s.getspel_id() + " " + s.getrol_id() + " " + s.getNaam());
            }
            List<Stad> stadList = dataLayer.getStedenList();
            for (Stad s : stadList) {
                System.out.println("\t"+s.getId() + " " + s.getNaam() + " " + s.getKleur() + " " + s.getX() + " " + s.getY());
            }
            List<Rol> rolList = dataLayer.getRollenList();
            for (Rol s : rolList) {
                System.out.println("\t"+s.getId() + " " + s.getNaam() + " " + s.getBeschrijving());
            }
            List<Spel> spelList = dataLayer.getSpellenList();
            int b = 0;
            for (Spel s : spelList) {
                b++;
                System.out.println("\t" + b + " " +s.getId() + " " + s.getStart() + " " + s.getEinde());
            }
            List<Connectie> connectieList = dataLayer.getConnectiesList();
            int j = 0;
            for (Connectie s : connectieList) {
                j++;
                System.out.println("\t" + j + " " + "Stad 1 : "+s.getStad1().getNaam() + "Cords(" +s.getStad1().getX() + "," + s.getStad1().getY()+")" + ", Stad 2:  " + s.getStad2().getNaam() + "Cords(" +s.getStad2().getX() + "," + s.getStad2().getY()+")");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


}
