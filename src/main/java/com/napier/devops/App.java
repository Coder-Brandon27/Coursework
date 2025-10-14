package com.napier.devops;

import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        DatabaseConnector db = new DatabaseConnector();
        Connection con = db.connect();

        if (con != null) {
            // You can add queries here later
            db.close();
        } else {
            System.out.println("Connection failed after multiple attempts.");
        }
    }
}
