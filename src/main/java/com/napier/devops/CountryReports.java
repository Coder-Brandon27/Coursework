package com.napier.devops;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryReports {

    // Connection to the database
    private final Connection con;

    // Constructor sets up the connection so we can use it in the methods below
    public CountryReports(Connection con) {
        this.con = con;
    }

    // Gets the top N most populated countries in a given continent
    public List<Country> getTopCountriesInContinent(String continent, int n) {
        List<Country> countries = new ArrayList<>();

        // SQL query that selects country info filtered by continent and limits results to N
        String sql = "SELECT Code, Name, Continent, Region, Population, Capital "
                   + "FROM country "
                   + "WHERE Continent = ? "
                   + "ORDER BY Population DESC "
                   + "LIMIT ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            // Replace the ? in the SQL query with the values the user provides
            ps.setString(1, continent);
            ps.setInt(2, n);

            // Execute the query and store the results
            try (ResultSet rs = ps.executeQuery()) {

                // Go through each row returned by the database
                while (rs.next()) {
                    // Create a new Country object for each row
                    Country c = new Country();
                    c.setCode(rs.getString("Code"));
                    c.setName(rs.getString("Name"));
                    c.setContinent(rs.getString("Continent"));
                    c.setRegion(rs.getString("Region"));
                    c.setPopulation(rs.getLong("Population"));
                    c.setCapital(rs.getInt("Capital"));

                    // Add the Country object to the list
                    countries.add(c);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error in getTopCountriesInContinent: " + e.getMessage());
        }

        // Return the list of countries
        return countries;
    }

    // Prints out each country to the console
    public void displayCountries(List<Country> countries) {
        for (Country c : countries) {
            System.out.println(
                c.getName() + " | " +
                c.getContinent() + " | " +
                c.getRegion() + " | " +
                c.getPopulation()
            );
        }
    }

    //user story 23
    public List<Country> RegionLargeToSmall(String n) {
        List<Country> countries = new ArrayList<>();
        String sql = "SELECT name, continent, region, population " +
                "FROM country " +
                "WHERE region = ? " +
                "ORDER BY population DESC";

        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, n);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Create a new country object for each row
                    Country c = new Country();
                    c.setName(rs.getString("Name"));
                    c.setContinent(rs.getString("Continent"));
                    c.setRegion(rs.getString("Region"));
                    c.setPopulation(rs.getLong("Population"));

                    // Add the City object to the list
                    countries.add(c);
                }
            }
        } catch (Exception e) {
            System.out.println("Error has occured while trying to load country. " + e.getMessage());
        }
        return countries;
    }

    //user story 24
    public List<Country> getTopNCountriesByPopulation(int n) {
        List<Country> countries = new ArrayList<>();
        String sql = "SELECT Name, Continent, Region, Population " +
                "FROM country " +
                "ORDER BY Population DESC " +
                "LIMIT ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, n);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Country c = new Country();
                    c.setName(rs.getString("Name"));
                    c.setContinent(rs.getString("Continent"));
                    c.setRegion(rs.getString("Region"));
                    c.setPopulation(rs.getInt("Population"));
                    countries.add(c);
                }
            }
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to load top N countries. " + e.getMessage());
        }

        return countries;
    }



    //user story 23
    public void displayCountiresByLargestToSmallest(List<Country> countries) {
        System.out.println("Name | Continent | Region | Population");
        System.out.println("--------------------------------------------------");
        for (Country c : countries) {
            System.out.println(
                    c.getName() + " | " +
                            c.getContinent() + " | " +
                            c.getRegion() + " | " +
                            c.getPopulation()
            );
        }
    }

    public void displayTopNPop(List<Country> countries) {
        System.out.println("Name | Continent | Region | Population");
        System.out.println("--------------------------------------------------");
        for (Country c : countries) {
            System.out.println(
                    c.getName() + " | " +
                            c.getContinent() + " | " +
                            c.getRegion() + " | " +
                            c.getPopulation()
            );
        }
    }

    //use case 26
    public List<Country> TopNCountriesByRegions(String region, int n) {
        List<Country> countries = new ArrayList<>();
        String sql = "SELECT Code, Name, Continent, Region, Population " +
                "FROM country " +
                "WHERE Region = ? " +
                "ORDER BY Population DESC " +
                "LIMIT ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, region);
            ps.setInt(2, n);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Country c = new Country();
                    c.setCode(rs.getString("Code"));
                    c.setName(rs.getString("Name"));
                    c.setContinent(rs.getString("Continent"));
                    c.setRegion(rs.getString("Region"));
                    c.setPopulation(rs.getInt("Population"));
                    countries.add(c);
                }
            }
        } catch (Exception e) {
            System.out.println("Error has occurred while trying to load top N countries by region. " + e.getMessage());
        }

        return countries;
    }

    public void displayTopNPopRegion(List<Country> countries) {
        System.out.println("Name | Continent | Region | Population");
        System.out.println("--------------------------------------------------");
        for (Country c : countries) {
            System.out.println(
                    c.getName() + " | " +
                            c.getContinent() + " | " +
                            c.getRegion() + " | " +
                            c.getPopulation()
            );
        }
    }



}


