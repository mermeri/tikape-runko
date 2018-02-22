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
import tikape.runko.domain.Ainesosa;
/**
 *
 * @author Meri
 */
public class AinesosaDao implements Dao<Ainesosa, Integer>{
    private Database database;
    
    public AinesosaDao(Database db) {
        this.database = db;
    }

    @Override
    public Ainesosa findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ainesosa WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Ainesosa o = new Ainesosa(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Ainesosa> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ainesosa");

        ResultSet rs = stmt.executeQuery();
        List<Ainesosa> ainesosat = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            ainesosat.add(new Ainesosa(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return ainesosat;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement stmt = c.prepareStatement("DELETE FROM Ainesosa WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        c.close();
    }

    @Override
    public Ainesosa saveOrUpdate(Ainesosa object) throws SQLException {
        if (object.getId() == null) {
            Connection c = database.getConnection();
            PreparedStatement stmt = c.prepareStatement("INSERT INTO Ainesosa(nimi) VALUES (?)");
            stmt.setString(1, object.getNimi());
        
            stmt.executeUpdate();
            stmt.close();
        
            stmt = c.prepareStatement("SELECT * FROM Ainesosa WHERE nimi = ?");
            stmt.setString(1, object.getNimi());
        
            ResultSet rs = stmt.executeQuery();
            rs.next();
        
            Ainesosa a = new Ainesosa(rs.getInt("id"), rs.getString("nimi"));
        
            stmt.close();
            rs.close();
            c.close();
        
            return a;
        } else {
            Connection c = database.getConnection();
            PreparedStatement stmt = c.prepareStatement("UPDATE Ainesosa SET nimi = ? WHERE id = ?");
            stmt.setString(1, object.getNimi());
            stmt.setInt(2, object.getId());
            
            stmt.executeUpdate();
            stmt.close();
            c.close();
            
            return object;
        }
    }
    
}
