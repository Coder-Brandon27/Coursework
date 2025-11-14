package com.napier.devops;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityReports {

    // Database connection
    private final Connection con;

    // Save connection so all methods can use it
    public CityReports(Connection con) {
        this.con = con;
    }

    // Get top N most populated cities in the world
    public List<City> getTopCities(int n) {
        List<City> cities = new ArrayList<>();

        String sql = "SELECT ID, Name, CountryCode, District, Population "
                + "FROM city "
                + "ORDER BY Population DESC "
                + "LIMIT ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, n);

            try (ResultSet rs = ps.executeQuery()) {
                // Build each City object
                while (rs.next()) {
                    City c = new City();
                    c.setId(rs.getLong("ID"));
                    c.setName(rs.getString("Name"));
                    c.setCountryCode(rs.getString("CountryCode"));
                    c.setDistrict(rs.getString("District"));
                    c.setPopulation(rs.getLong("Population"));
                    cities.add(c);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error in getTopCities: " + e.getMessage());
        }

        return cities;
    }

    // Get all cities sorted by population
    public List<City> getAllCities() {
        List<City> cities = new ArrayList<>();

        String sql = "SELECT ID, Name, CountryCode, District, Population "
                + "FROM city "
                + "ORDER BY Population DESC";

        try (
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
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

        return cities;
    }

    // Print a list of cities safely
    public void displayCities(List<City> cities) {
        // If list is null, stop here
        if (cities == null) {
            System.out.println("No cities to display.");
            return;
        }

        // Print each city (skip null items)
        for (City c : cities) {
            if (c == null)
                continue;

            System.out.println(
                    c.getName() + " | " +
                    c.getCountryCode() + " | " +
                    c.getDistrict() + " | " +
                    c.getPopulation()
            );
        }
    }

    // Get cities grouped by district
    public List<City> getCitiesByDistrict() {
        List<City> districts = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT Name, District, Population "
                    + "FROM city "
                    + "ORDER BY District ASC, Population DESC";

            ResultSet rset = stmt.executeQuery(strSelect);

            while (rset.next()) {
                City c = new City();
                c.setName(rset.getString("Name"));
                c.setDistrict(rset.getString("District"));
                c.setPopulation(rset.getLong("Population"));
                districts.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error in getCitiesByDistrict: " + e.getMessage());
        }

        return districts;
    }

    // Print cities grouped by district
    public void displayCitiesByDistrictGrouped(List<City> cities) {
        String currentDistrict = "";

        for (City c : cities) {
            // New district header
            if (!c.getDistrict().equals(currentDistrict)) {
                currentDistrict = c.getDistrict();
                System.out.println("\nDistrict: " + currentDistrict);
            }

            System.out.println("  City: " + c.getName() + " | Population: " + c.getPopulation());
        }
    }

    // Get all cities in a country sorted by population
    public List<City> getCitiesByCountry(String countryName) {
        List<City> cities = new ArrayList<>();

        String sql = "SELECT city.ID, city.Name, city.CountryCode, city.District, city.Population " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Name = ? " +
                "ORDER BY city.Population DESC";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, countryName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    City c = new City();
                    c.setId(rs.getLong("ID"));
                    c.setName(rs.getString("Name"));
                    c.setCountryCode(rs.getString("CountryCode"));
                    c.setDistrict(rs.getString("District"));
                    c.setPopulation(rs.getLong("Population"));
                    cities.add(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in getCitiesByCountry: " + e.getMessage());
        }

        return cities;
    }

    // Print cities for a specific country
    public void displayCitiesByCountry(String countryName, List<City> cities) {
        System.out.println("\nCities in " + countryName + " (largest to smallest population):");

        for (City c : cities) {
            System.out.println(
                "  " + c.getName() + " | " +
                c.getDistrict() + " | Pop: " + c.getPopulation()
            );
        }
    }
}

