package com.napier.devops;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PopulationReports {

    // Connection to the database
    private final Connection con;

    // Constructor sets up the connection so we can use it in the methods below
    public PopulationReports(Connection con) {
        this.con = con;
    }

    // Helper method to create a Population from a ResultSet row
    private Population createPopulationReport(ResultSet rs) throws SQLException {
        Population report = new Population();
        report.setName(rs.getString("Name"));
        report.setTotalPopulation(rs.getLong("TotalPopulation"));
        report.setUrbanPopulation(rs.getLong("UrbanPopulation"));
        report.setRuralPopulation(rs.getLong("RuralPopulation"));
        return report;
    }

    // Gets population breakdown for each continent (urban vs rural)

    public List<Population> getPopulationByContinent() {
        List<Population> reports = new ArrayList<>();

        // SQL query that calculates total, urban, and rural population per continent
        String sql = "SELECT " +
                "c.Continent AS Name, " +
                "SUM(c.Population) AS TotalPopulation, " +
                "COALESCE(SUM(city_pop.CityPopulation), 0) AS UrbanPopulation, " +
                "(SUM(c.Population) - COALESCE(SUM(city_pop.CityPopulation), 0)) AS RuralPopulation " +
                "FROM country c " +
                "LEFT JOIN (" +
                "    SELECT CountryCode, SUM(Population) AS CityPopulation " +
                "    FROM city " +
                "    GROUP BY CountryCode" +
                ") city_pop ON c.Code = city_pop.CountryCode " +
                "GROUP BY c.Continent " +
                "ORDER BY c.Continent ASC";

        try (
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            // Go through each result and turn it into a Population object
            while (rs.next()) {
                reports.add(createPopulationReport(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error in getPopulationByContinent: " + e.getMessage());
        }

        return reports;
    }

    // Gets population breakdown for each region (urban vs rural)
    public List<Population> getPopulationByRegion() {
        List<Population> reports = new ArrayList<>();

        // SQL query that calculates total, urban, and rural population per region
        String sql = "SELECT " +
                "c.Region AS Name, " +
                "SUM(c.Population) AS TotalPopulation, " +
                "COALESCE(SUM(city_pop.CityPopulation), 0) AS UrbanPopulation, " +
                "(SUM(c.Population) - COALESCE(SUM(city_pop.CityPopulation), 0)) AS RuralPopulation " +
                "FROM country c " +
                "LEFT JOIN (" +
                "    SELECT CountryCode, SUM(Population) AS CityPopulation " +
                "    FROM city " +
                "    GROUP BY CountryCode" +
                ") city_pop ON c.Code = city_pop.CountryCode " +
                "GROUP BY c.Region " +
                "ORDER BY c.Region ASC";

        try (
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            // Go through each result and turn it into a Population object
            while (rs.next()) {
                Population report = new Population();
                report.setName(rs.getString("Name"));
                report.setTotalPopulation(rs.getLong("TotalPopulation"));
                report.setUrbanPopulation(rs.getLong("UrbanPopulation"));
                report.setRuralPopulation(rs.getLong("RuralPopulation"));
                reports.add(report);
            }

        } catch (SQLException e) {
            System.out.println("Error in getPopulationByRegion: " + e.getMessage());
        }

        return reports;
    }

    // Gets population breakdown for each country (urban vs rural)
    public List<Population> getPopulationByCountry() {
        List<Population> reports = new ArrayList<>();

        // SQL query that calculates total, urban, and rural population per country
        String sql = "SELECT " +
                "c.Name AS Name, " +
                "c.Population AS TotalPopulation, " +
                "COALESCE(city_pop.CityPopulation, 0) AS UrbanPopulation, " +
                "(c.Population - COALESCE(city_pop.CityPopulation, 0)) AS RuralPopulation " +
                "FROM country c " +
                "LEFT JOIN (" +
                "    SELECT CountryCode, SUM(Population) AS CityPopulation " +
                "    FROM city " +
                "    GROUP BY CountryCode" +
                ") city_pop ON c.Code = city_pop.CountryCode " +
                "ORDER BY c.Name ASC";

        try (
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            // Go through each result and turn it into a Population object
            while (rs.next()) {
                Population report = new Population();
                report.setName(rs.getString("Name"));
                report.setTotalPopulation(rs.getLong("TotalPopulation"));
                report.setUrbanPopulation(rs.getLong("UrbanPopulation"));
                report.setRuralPopulation(rs.getLong("RuralPopulation"));
                reports.add(report);
            }

        } catch (SQLException e) {
            System.out.println("Error in getPopulationByCountry: " + e.getMessage());
        }

        return reports;
    }

    // Displays population reports with percentages
    public void displayPopulationReports(List<Population> reports, String reportType) {
        System.out.println("\n=== Population Report by " + reportType + " ===");
        System.out.println(String.format("%-40s | %-15s | %-20s | %-20s",
                reportType, "Total Pop.", "Urban Pop. (%)", "Rural Pop. (%)"));
        System.out.println("-".repeat(120));

        for (Population report : reports) {
            // Calculate percentages
            double urbanPercentage = (report.getTotalPopulation() > 0)
                    ? (report.getUrbanPopulation() * 100.0) / report.getTotalPopulation()
                    : 0.0;
            double ruralPercentage = (report.getTotalPopulation() > 0)
                    ? (report.getRuralPopulation() * 100.0) / report.getTotalPopulation()
                    : 0.0;

            System.out.println(String.format("%-40s | %,15d | %,15d (%.2f%%) | %,15d (%.2f%%)",
                    report.getName(),
                    report.getTotalPopulation(),
                    report.getUrbanPopulation(),
                    urbanPercentage,
                    report.getRuralPopulation(),
                    ruralPercentage
            ));
        }
        System.out.println();
    }

    // Gets the total population of the world
    public long getWorldPopulation() {
        long population = 0;

        String sql = "SELECT SUM(Population) AS WorldPopulation FROM country";

        try (
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            if (rs.next()) {
                population = rs.getLong("WorldPopulation");
            }

        } catch (SQLException e) {
            System.out.println("Error in getWorldPopulation: " + e.getMessage());
        }

        return population;
    }

    // Gets the total population of a continent
    public long getContinentPopulation(String continentName) {
        long population = 0;

        String sql = "SELECT SUM(Population) AS ContinentPopulation " +
                "FROM country " +
                "WHERE Continent = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, continentName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    population = rs.getLong("ContinentPopulation");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error in getContinentPopulation: " + e.getMessage());
        }

        return population;
    }

    // Gets the total population of a region
    public long getRegionPopulation(String regionName) {
        long population = 0;

        String sql = "SELECT SUM(Population) AS RegionPopulation " +
                "FROM country " +
                "WHERE Region = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, regionName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    population = rs.getLong("RegionPopulation");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error in getRegionPopulation: " + e.getMessage());
        }

        return population;
    }

    // Gets the total population of a country
    public long getCountryPopulation(String countryName) {
        long population = 0;

        String sql = "SELECT Population " +
                "FROM country " +
                "WHERE Name = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, countryName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    population = rs.getLong("Population");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error in getCountryPopulation: " + e.getMessage());
        }

        return population;
    }

    // Gets the total population of a district
    public long getDistrictPopulation(String districtName) {
        long population = 0;

        String sql = "SELECT SUM(Population) AS DistrictPopulation " +
                "FROM city " +
                "WHERE District = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, districtName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    population = rs.getLong("DistrictPopulation");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error in getDistrictPopulation: " + e.getMessage());
        }

        return population;
    }

    // Gets the population of a specific city
    public long getCityPopulation(String cityName) {
        long population = 0;

        String sql = "SELECT Population " +
                "FROM city " +
                "WHERE Name = ? " +
                "LIMIT 1";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cityName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    population = rs.getLong("Population");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error in getCityPopulation: " + e.getMessage());
        }

        return population;
    }

    // Displays a simple population value
    public void displayPopulation(String name, long population) {
        System.out.println("\nPopulation of " + name + ": " + String.format("%,d", population));
    }
}