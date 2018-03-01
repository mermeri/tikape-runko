/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.DisplayOhje;
import tikape.runko.domain.Ohje;
/**
 *
 * @author Meri
 */
public class OhjeDao implements Dao<Ohje, Integer> {
    private Database database;
    
    public OhjeDao(Database db) {
        this.database = db;
    }

    @Override
    public Ohje findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ohje WHERE drinkki_id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("raaka_aine_id");
        Integer jarjestys = rs.getInt("jarjestys");
        Integer maara = rs.getInt("maara");
        String ohje = rs.getString("ohje");

        Ohje o = new Ohje(key, id, jarjestys, maara, ohje);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }
    

    public List<Ohje> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ohje");

        ResultSet rs = stmt.executeQuery();
        List<Ohje> ohjeet = new ArrayList<>();
        while (rs.next()) {
            Integer drinkki_id = rs.getInt("drinkki_id");
            Integer ainesosa_id = rs.getInt("ainesosa_id");
            Integer jarjestys = rs.getInt("jarjestys");
            Integer maara = rs.getInt("maara");
            String ohje = rs.getString("ohje");

            ohjeet.add(new Ohje(drinkki_id, ainesosa_id, jarjestys, maara, ohje));
        }

        rs.close();
        stmt.close();
        connection.close();

        return ohjeet;
    }
    
    public List<Ohje> findAll(String sana) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ohje WHERE drinkki_id = " + sana);

        ResultSet rs = stmt.executeQuery();
        List<Ohje> ohjeet = new ArrayList<>();
        while (rs.next()) {
            Integer drinkki_id = rs.getInt("drinkki_id");
            Integer ainesosa_id = rs.getInt("raaka_aine_id");
            Integer jarjestys = rs.getInt("jarjestys");
            Integer maara = rs.getInt("maara");
            String ohje = rs.getString("ohje");

            ohjeet.add(new Ohje(drinkki_id, ainesosa_id, jarjestys, maara, ohje));
        }

        rs.close();
        stmt.close();
        connection.close();

        return ohjeet;
    }
    
    public List<String> findAllR(String sana) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT RaakaAine.nimi FROM Ohje, RaakaAine WHERE drinkki_id = " + sana + " AND RaakaAine.id = raaka_aine_id");

        ResultSet rs = stmt.executeQuery();
        List<String> ohjeet = new ArrayList<>();
        while (rs.next()) {
            String nimi = rs.getString("nimi");

            ohjeet.add(nimi);
        }

        rs.close();
        stmt.close();
        connection.close();

        return ohjeet;
    }
    
    public List<DisplayOhje> findAllO(String sana) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT RaakaAine.nimi, jarjestys, maara, ohje FROM Ohje, RaakaAine WHERE drinkki_id = " + sana + " AND RaakaAine.id = raaka_aine_id");

        ResultSet rs = stmt.executeQuery();
        List<DisplayOhje> ohjeet = new ArrayList<>();
        while (rs.next()) {
            String nimi = rs.getString("nimi");
            Integer jarjestys = rs.getInt("jarjestys");
            Integer maara = rs.getInt("maara");
            String ohje = rs.getString("ohje");
            
            ohjeet.add(new DisplayOhje(nimi, jarjestys, maara, ohje));
        }

        rs.close();
        stmt.close();
        connection.close();

        return ohjeet;
    }

    
    public void delete(Integer key) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement stmt = c.prepareStatement("DELETE * FROM Ohje WHERE drinkki.id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        c.close();
    }

    @Override
    public Ohje saveOrUpdate(Ohje object) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Ohje (raaka_aine_id, drinkki_id, jarjestys, maara, ohje) VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, object.getAinesosa_id());
            stmt.setInt(2, object.getDrinkki_id());
            stmt.setInt(3, object.getJarjestys());
            stmt.setInt(4, object.getMaara());
            stmt.setString(5, object.getOhje());
            
            stmt.executeUpdate();
        }

        return null;
    }
}
