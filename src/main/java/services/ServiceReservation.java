package services;

import Database.DbConnection;
import entities.Machine;
import entities.Reservation;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReservation {
    private final Connection conn;
    private final ServiceMachine serviceMachine = new ServiceMachine();
    private final UserService serviceUser = new UserService(); // you need to have this implemented

    public ServiceReservation() {
        this.conn = DbConnection.getInstance().getConn();
    }

    public void addReservation(Reservation r) {
        String sql = "INSERT INTO reservation (date_debut, date_fin, machine_id, client_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, r.getDateDebut());
            stmt.setTimestamp(2, r.getDateFin());
            stmt.setInt(3, r.getMachine().getId());
            stmt.setInt(4, r.getClient().getId());
            stmt.executeUpdate();
            System.out.println("✅ Reservation added.");
        } catch (SQLException e) {
            System.err.println("❌ Error adding reservation:");
            e.printStackTrace();
        }
    }

    public void deleteReservation(Reservation r) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, r.getId());
            stmt.executeUpdate();
            System.out.println("✅ Reservation deleted.");
        } catch (SQLException e) {
            System.err.println("❌ Error deleting reservation:");
            e.printStackTrace();
        }
    }

    public void editReservation(Reservation r) {
        String sql = "UPDATE reservation SET date_debut = ?, date_fin = ?, machine_id = ?, client_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, r.getDateDebut());
            stmt.setTimestamp(2, r.getDateFin());
            stmt.setInt(3, r.getMachine().getId());
            stmt.setInt(4, r.getClient().getId());
            stmt.setInt(5, r.getId());
            stmt.executeUpdate();
            System.out.println("✅ Reservation updated.");
        } catch (SQLException e) {
            System.err.println("❌ Error updating reservation:");
            e.printStackTrace();
        }
    }

    public Reservation getReservation(int id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Timestamp dateDebut = rs.getTimestamp("date_debut");
                Timestamp dateFin = rs.getTimestamp("date_fin");
                int machineId = rs.getInt("machine_id");
                int clientId = rs.getInt("client_id");

                Machine machine = serviceMachine.getMachine(machineId);
                User client = serviceUser.getUser(clientId);

                return new Reservation(id, dateDebut, dateFin, machine, client);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching reservation:");
            e.printStackTrace();
        }
        return null;
    }

    public List<Reservation> getReservations() {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM reservation";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                Timestamp dateDebut = rs.getTimestamp("date_debut");
                Timestamp dateFin = rs.getTimestamp("date_fin");
                int machineId = rs.getInt("machine_id");
                int clientId = rs.getInt("client_id");

                Machine machine = serviceMachine.getMachine(machineId);
                User client = serviceUser.getUser(clientId);

                // Defensive null-check: skip invalid foreign keys
                if (machine == null || client == null) {
                    System.err.println("⚠️ Skipping reservation ID " + id +
                            " (invalid machine_id: " + machineId + ", client_id: " + clientId + ")");
                    continue;
                }

                Reservation r = new Reservation(id, dateDebut, dateFin, machine, client);
                list.add(r);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error loading reservations:");
            e.printStackTrace();
        }
        return list;
    }
}
