package com.example.pijihene;

import DB.DbConnection;
import entities.Machine;
import entities.Reservation;
import entities.User;
import services.ServiceMachine;
import services.ServiceReservation;
import services.UserService;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ServiceReservation serviceReservation = new ServiceReservation();
        ServiceMachine serviceMachine = new ServiceMachine();
        UserService userService = new UserService();

        // 👤 Get any existing user
        User user = userService.getUser(1); // Make sure ID 1 exists
        if (user == null) {
            System.out.println("❌ User with ID 1 not found.");
            return;
        }

        // 🚜 Get any existing machine
        Machine machine = serviceMachine.getMachine(33); // Make sure ID 1 exists
        if (machine == null) {
            System.out.println("❌ Machine with ID 1 not found.");
            return;
        }

        // 🕒 Create reservation time range
        Timestamp start = Timestamp.valueOf(LocalDateTime.now().plusDays(1));
        Timestamp end = Timestamp.valueOf(LocalDateTime.now().plusDays(5));

        // 📦 Create new reservation
        Reservation reservation = new Reservation(start, end, machine, user);
        serviceReservation.addReservation(reservation);

        // 📥 Fetch and display all reservations
        List<Reservation> reservations = serviceReservation.getReservations();
        System.out.println("📋 All Reservations:");
        for (Reservation r : reservations) {
            System.out.println("ID: " + r.getId()
                    + " | Machine: " + r.getMachine().getId()
                    + " | User: " + r.getClient().getId()
                    + " | From: " + r.getDateDebut()
                    + " | To: " + r.getDateFin());
        }

        // 🧪 Get specific reservation
        if (!reservations.isEmpty()) {
            int firstId = reservations.get(0).getId();
            Reservation fetched = serviceReservation.getReservation(firstId);
            if (fetched != null) {
                System.out.println("🔍 Fetched reservation with ID " + firstId + ":");
                System.out.println("Machine ID: " + fetched.getMachine().getId());
                System.out.println("User ID: " + fetched.getClient().getId());
            }

            // ✏️ Edit reservation
            fetched.setDateFin(Timestamp.valueOf(LocalDateTime.now().plusDays(7)));
            serviceReservation.editReservation(fetched);

            // ❌ Delete reservation
            serviceReservation.deleteReservation(fetched);
        }

    }
}
