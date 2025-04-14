package Services;

import Entites.Zone;
import Interfaces.InterfaceCRUD;
import Utils.MyDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;



public class ZoneService implements InterfaceCRUD<Zone> {
    Connection con;
    public ZoneService() {
        con= MyDB.getInstance().getCon();
    }

    @Override
    public void add(Zone zone)throws SQLException {
        String req = "INSERT INTO Zone (superficie_zone, nom_zone, localisation_zone) VALUES ('"
                + zone.getSuperficie_zone() + "', '"
                + zone.getNom_zone() + "', '"
                + zone.getLocalisation_zone() + "')";

        Statement st;
        try {
            st = con.createStatement();
            st.executeUpdate(req);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void update(Zone zone) {

    }

    @Override
    public void delete(Zone zone) {

    }

    @Override
    public List<Zone> find() {
        List<Zone> zones = new ArrayList<>();
        String req = "SELECT * FROM Zone";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Zone z = new Zone(
                        rs.getFloat("superficie_zone"),
                        rs.getString("nom_zone"),
                        rs.getString("localisation_zone")
                );
                zones.add(z);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return zones;
    }

}
