package com.napier.devops;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryReport {

    private final Connection con;

    public CountryReport(Connection con) {this.con = con;}

    public List<Country> RegionLargeToSmall(String n) {
        List<Country> countries = new ArrayList<>();

        String sql = "SELECT code, name, population"
                + "FROM country "
                + "WHERE region = ?"
                + "ORDER BY population DESC ";


        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, n);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    // Create a new City object for each row
                    Country c = new Country();
                    c.setCode(rs.getString("Code"));
                    c.setName(rs.getString("Name"));
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


    public void displayCountiresByLargestToSmallest(List<Country> countries) {
        for (Country c : countries) {
            System.out.println(
                            c.getCode() + " | " +
                            c.getName() + " | " +
                            c.getPopulation()
            );
        }
    }





}
