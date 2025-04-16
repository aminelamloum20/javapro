package services;

import Database.DbConnection;
import entities.Grange;
import Interfaces.InterfaceCRUD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GrangeService implements InterfaceCRUD<Grange> {
    Connection con;

    public GrangeService() {
        con = DbConnection.getInstance().getConn();
    }

    @Override
    public void add(Grange grange)  {
        String req = "INSERT INTO grange (type_grange, capacite) VALUES ('"
                + grange.getType_grange() + "', '"
                + grange.getCapacite() + "')";

        Statement st;
        try {
            st = con.createStatement();
            st.executeUpdate(req);
        } catch (SQLException e) {
            System.out.println("❌ Erreur ajout grange : " + e.getMessage());
        }
    }


    @Override
    public void update(Grange grange) {

    }

    @Override
    public void delete(Grange grange) {

    }

    @Override
    public List<Grange> find() {
        List<Grange> granges = new ArrayList<>();
        String req = "SELECT * FROM grange";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Grange g = new Grange();
                g.setType_grange(rs.getString("type_grange"));
                g.setCapacite(rs.getFloat("capacite"));
                granges.add(g);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur récupération granges : " + e.getMessage());
        }
        return granges;
    }
}
