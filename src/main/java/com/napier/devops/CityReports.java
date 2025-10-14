package com.napier.devops;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityReports {

    // Connection to the database
    private final Connection con;

    // Constructor sets up the connection so we can use it in the methods below
    public CityReports(Connection con) {
        this.con = con;
    }

    // Gets the top N most populated cities in the world
    public List<City> getTopCities(int n) {
        List<City> cities = new ArrayList<>();

        // SQL query which selects city info and limits results to N
        String sql = "SELECT ID, Name, CountryCode, District, Population "
           + "FROM city "
           + "ORDER BY Population DESC "
           + "LIMIT ?";


        try (PreparedStatement ps = con.prepareStatement(sql)) {
            // Replace the ? in the SQL query with the number the user provides
            ps.setInt(1, n);

            // Execute the query and store the results
            try (ResultSet rs = ps.executeQuery()) {

                // Go through each row returned by the database
                while (rs.next()) {
                    // Create a new City object for each row
                    City c = new City();
                    c.setId(rs.getLong("ID"));
                    c.setName(rs.getString("Name"));
                    c.setCountryCode(rs.getString("CountryCode"));
                    c.setDistrict(rs.getString("District"));
                    c.setPopulation(rs.getLong("Population"));

                    // Add the City object to the list
                    cities.add(c);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error in getTopCities: " + e.getMessage());
        }

        // Return the list of cities
        return cities;
    }

    // Gets all cities in the world, ordered by population (largest to smallest)
    public List<City> getAllCities() {
        List<City> cities = new ArrayList<>();

        // SQL query that selects all cities and sorts them by population
        String sql = "SELECT ID, Name, CountryCode, District, Population "
           + "FROM city "
           + "ORDER BY Population DESC";


        try (
            // Create a Statement and run the query
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            // Go through each result and turn it into a City object
            while (rs.next()) {
                City c = new City();
                c.setId(rs.getLong("ID"));
                c.setName(rs.getString("Name"));
                c.setCountryCode(rs.getString("CountryCode"));
                c.setDistrict(rs.getString("District"));
                c.setPopulation(rs.getLong("Population"));
                cities.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error in getAllCities: " + e.getMessage());
        }

        // Return the full list of cities
        return cities;
    }

    // Prints out each city to the console 
    public void displayCities(List<City> cities) {
        for (City c : cities) {
            System.out.println(
                c.getName() + " | " +
                c.getCountryCode() + " | " +
                c.getDistrict() + " | " +
                c.getPopulation()
            );
        }
    }
}
