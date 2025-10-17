package com.napier.devops;

import java.sql.Connection;
import java.util.List;

public class App {
    public static void main(String[] args) {
        DatabaseConnector db = new DatabaseConnector();
        Connection con = db.connect();

        if (con != null) {
            System.out.println("Connected to Database.");

            //City reports object to access city related queries
            CityReports reports = new CityReports(con);

            //USER STORY 1: Top N populated cities in the world
            int N = 10;
            List<City> topCities = reports.getTopCities(N);
            System.out.println("\nTop " + N + " most populated cities in the world:");
            reports.displayCities(topCities);

            //USER STORY 2: All cities in the world ordered by population (largest>smallest)
            //This query returns a lot of rows so I have limited it to the first 20 for readibility
            List<City> allCities = reports.getAllCities();
            System.out.println("\nFirst 20 from full city list (descending population):");
            int limit = Math.min(20, allCities.size());
            reports.displayCities(allCities.subList(0, limit));

            //user story 3:
            // Get the cities grouped by district
             List<City> cities = reports.getCitiesByDistrict();
             System.out.println("\n Cities in each district organised by largest to smallest population\n");
            // Display them grouped by district and displays 1st 40 for readablity
            reports.displayCitiesByDistrictGrouped(cities.subList(0, 40));
            db.close();
        } else {
            System.out.println("Connection failed after multiple attempts.");
        }
    }
}
