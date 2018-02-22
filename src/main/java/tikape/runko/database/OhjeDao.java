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
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public List<Ohje> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ohje");

        ResultSet rs = stmt.executeQuery();
        List<Ohje> ohjeet = new ArrayList<>();
        while (rs.next()) {
            Integer drinkki_id = rs.getInt("drinkki_id");
            Integer ainesosa_id = rs.getInt("ainesosa_id");
            Integer jarjestys = rs.getInt("jarjestys");
            String maara = rs.getString("maara");
            String ohje = rs.getString("ohje");

            ohjeet.add(new Ohje(drinkki_id, ainesosa_id, jarjestys, maara, ohje));
        }

        rs.close();
        stmt.close();
        connection.close();

        return ohjeet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Ohje saveOrUpdate(Ohje object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
