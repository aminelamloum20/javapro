package services;

import Database.DbConnection;
import entities.Zone;
import Interfaces.InterfaceCRUD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class ZoneService implements InterfaceCRUD<Zone> {
    Connection con;
    public ZoneService() {
        con= DbConnection.getInstance().getConn();
    }

    @Override
    public void add(Zone zone) {
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
        String req = "UPDATE Zone SET superficie_zone = ?, localisation_zone = ? WHERE nom_zone = ?";
        try (PreparedStatement pst = con.prepareStatement(req)) {
            pst.setFloat(1, zone.getSuperficie_zone());
            pst.setString(2, zone.getLocalisation_zone());
            pst.setString(3, zone.getNom_zone()); // ← condition sur le nom
            int rowsUpdated = pst.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("✅ Zone mise à jour avec succès.");
            } else {
                System.out.println("⚠️ Aucune zone mise à jour (nom introuvable ?).");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur mise à jour zone : " + e.getMessage());
        }
    }



    @Override
    public void delete(Zone zone) {
        String req = "DELETE FROM Zone WHERE nom_zone = ?";
        try (PreparedStatement pst = con.prepareStatement(req)) {
            pst.setString(1, zone.getNom_zone());
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("🗑️ Zone supprimée avec succès.");
            } else {
                System.out.println("⚠️ Aucune zone supprimée (nom introuvable ?).");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur suppression zone : " + e.getMessage());
        }
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