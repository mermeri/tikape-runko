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
import tikape.runko.domain.RaakaAine;
/**
 *
 * @author Meri
 */
public class RaakaAineDao implements Dao<RaakaAine, Integer>{
    private Database database;
    
    public RaakaAineDao(Database db) {
        this.database = db;
    }

    public RaakaAine findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM RaakaAine WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        RaakaAine o = new RaakaAine(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    public List<RaakaAine> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM RaakaAine");

        ResultSet rs = stmt.executeQuery();
        List<RaakaAine> ainesosat = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            ainesosat.add(new RaakaAine(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return ainesosat;
    }

    public void delete(Integer key) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement stmt = c.prepareStatement("DELETE FROM RaakaAine WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        c.close();
    }

    public RaakaAine saveOrUpdate(RaakaAine object) throws SQLException {
        RaakaAine findOne = findOne(object.getId());
        
        if (findOne == null) {
            
            Connection c = database.getConnection();
            PreparedStatement stmt = c.prepareStatement("INSERT INTO RaakaAine(nimi) VALUES (?)");
            stmt.setString(1, object.getNimi());
        
            stmt.executeUpdate();
            stmt.close();
        
            stmt = c.prepareStatement("SELECT * FROM RaakaAine WHERE nimi = ?");
            
            stmt.setString(1, object.getNimi());
            
            
            ResultSet rs = stmt.executeQuery();
            rs.next();
            
        
            RaakaAine a = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
        
            stmt.close();
            rs.close();
            c.close();
        
            return a;
        } else {
            Connection c = database.getConnection();
            PreparedStatement stmt = c.prepareStatement("UPDATE RaakaAine SET nimi = ? WHERE id = ?");
            stmt.setString(1, object.getNimi());
            stmt.setInt(2, object.getId());
            
            stmt.executeUpdate();
            stmt.close();
            c.close();
            
            return object;
        }
    }

    
    
}
