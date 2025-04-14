package com.example.projectjava;

import Database.DbConnection;
import entities.User;
import services.UserService;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection conn = DbConnection.getConnection();
        UserService userService = new UserService();

        // 1. ➕ Add user
        User newUser = new User("test@example.com", "123456", "ROLE_USER", "token123");
        userService.addUser(newUser);

        // 2. 🔍 Get user by ID
        User fetchedUser = userService.getUser(1); // replace with actual id
        if (fetchedUser != null) {
            System.out.println("Fetched: " + fetchedUser.getEmail() + ", Role: " + fetchedUser.getRole());
        }

        // 3. ✏️ Update user (example)
        if (fetchedUser != null) {
            fetchedUser.setRole("ROLE_ADMIN");
            userService.editUser(fetchedUser);
        }

        // 4. 📋 Get all users
        List<User> allUsers = userService.getAllUsers();
        System.out.println("---- All Users ----");
        for (User u : allUsers) {
            System.out.println(u.getId() + " | " + u.getEmail() + " | " + u.getRole());
        }

        // 5. 🗑️ Delete user
        if (fetchedUser != null) {
            userService.deleteUser(fetchedUser);
        }

    }

}
