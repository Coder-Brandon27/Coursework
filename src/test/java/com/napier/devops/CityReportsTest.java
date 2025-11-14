package com.napier.devops;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CityReportsTest {

    static DatabaseConnector db;
    static CityReports cityReports;

    // Runs once before all tests
    @BeforeAll
    static void init() {
        db = new DatabaseConnector();
        Connection con = db.connect();          // connect to database
        assertNotNull(con);                     // make sure connection worked
        cityReports = new CityReports(con);     // create object to test
    }

    // Test 1: null country should return an empty list
    @Test
    void getCitiesByCountry_NullCountry_ReturnsEmptyList() {
        List<City> cities = cityReports.getCitiesByCountry(null);

        assertNotNull(cities);                  // method must not return null
        assertEquals(0, cities.size());         // should be empty
    }

    // Test 2: empty country string should return an empty list
    @Test
    void getCitiesByCountry_EmptyCountry_ReturnsEmptyList() {
        List<City> cities = cityReports.getCitiesByCountry("");

        assertNotNull(cities);             // method must not return null
        assertEquals(0, cities.size());    // should be empty
    }

    // Test 3: invalid country name should return an empty list
    @Test
    void getCitiesByCountry_InvalidCountry_ReturnsEmptyList() {
        List<City> cities = cityReports.getCitiesByCountry("Narnia");  // not a real country

        assertNotNull(cities);             // still must not return null
        assertEquals(0, cities.size());    // should be empty list
    }

    // Test 4: valid country should not return an empty list
    @Test
    void getCitiesByCountry_ValidCountry_ReturnsCities() {
        List<City> cities = cityReports.getCitiesByCountry("Japan");   // real country in world DB

        assertNotNull(cities);                 // must not be null
        assertFalse(cities.isEmpty());         // should have at least one city
    }

    // Test 5: cities should be sorted by population in descending order
    @Test
    void getCitiesByCountry_ValidCountry_IsSortedDescending() {
        List<City> cities = cityReports.getCitiesByCountry("Japan");

        assertNotNull(cities);
        assertTrue(cities.size() > 1);    // Must have more than one city to test sorting

        // Check that each city has a population <= the previous one
        for (int i = 1; i < cities.size(); i++) {
            long prev = cities.get(i - 1).getPopulation();
            long current = cities.get(i).getPopulation();
            assertTrue(prev >= current, "Cities are not sorted in descending order.");
        }
    }

    // Test 6: displayCities should handle null list without crashing
    @Test
    void displayCities_NullList_DoesNotThrow() {
        cityReports.displayCities(null);   // should not crash
    }
}


