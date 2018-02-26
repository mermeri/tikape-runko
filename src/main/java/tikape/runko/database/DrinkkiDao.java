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
import tikape.runko.domain.Drinkki;

public class DrinkkiDao implements Dao<Drinkki, Integer> {

    private Database database;

    public DrinkkiDao(Database database) {
        this.database = database;
    }

    @Override
    public Drinkki findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Drinkki WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Drinkki o = new Drinkki(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Drinkki> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Drinkki");

        ResultSet rs = stmt.executeQuery();
        List<Drinkki> drinkit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            drinkit.add(new Drinkki(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return drinkit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement stmt = c.prepareStatement("DELETE FROM Drinkki WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        c.close();
    }
    
    @Override
    public Drinkki saveOrUpdate(Drinkki object) throws SQLException {
        
        if (object.getId() == null) {
            
            try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Drinkki(nimi) VALUES (?)");
            stmt.setString(1, object.getNimi());
            stmt.executeUpdate();
        }
            
            Connection c = database.getConnection();
        
            PreparedStatement stmt2 = c.prepareStatement("SELECT * FROM Drinkki WHERE nimi = ?");
            stmt2.setString(1, object.getNimi());
        
            ResultSet rs = stmt2.executeQuery();
            rs.next();
        
            Drinkki d = new Drinkki(rs.getInt("id"), rs.getString("nimi"));
        
            stmt2.close();
            rs.close();
            c.close();
        
            return d;
        } else {
            Connection c = database.getConnection();
            PreparedStatement stmt = c.prepareStatement("UPDATE Drinkki SET nimi = ? WHERE id = ?");
            stmt.setString(1, object.getNimi());
            stmt.setInt(2, object.getId());
            
            stmt.executeUpdate();
            stmt.close();
            c.close();
            
            return object;
        }
    }
}
