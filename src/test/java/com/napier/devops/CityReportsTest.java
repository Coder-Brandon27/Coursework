package com.napier.devops;

import org.junit.jupiter.api.*;
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
        Connection con = db.connect("localhost:33060", 30000);

        // If database is not running (GitHub Actions), skip tests
        if (con == null) {
            System.out.println("Database not available – skipping CityReports tests.");
            return;
        }

        cityReports = new CityReports(con);
    }

    // helper: skip test if DB was not connected
    private void assumeDbConnected() {
        Assumptions.assumeTrue(cityReports != null);
    }

    // Test 1: null country
    @Test
    void getCitiesByCountry_NullCountry_ReturnsEmptyList() {
        assumeDbConnected();
        List<City> cities = cityReports.getCitiesByCountry(null);
        assertNotNull(cities);
        assertEquals(0, cities.size());
    }

    // Test 2: empty string
    @Test
    void getCitiesByCountry_EmptyCountry_ReturnsEmptyList() {
        assumeDbConnected();
        List<City> cities = cityReports.getCitiesByCountry("");
        assertNotNull(cities);
        assertEquals(0, cities.size());
    }

    // Test 3: invalid country
    @Test
    void getCitiesByCountry_InvalidCountry_ReturnsEmptyList() {
        assumeDbConnected();
        List<City> cities = cityReports.getCitiesByCountry("Narnia");
        assertNotNull(cities);
        assertEquals(0, cities.size());
    }

    // Test 4: valid country should return cities
    @Test
    void getCitiesByCountry_ValidCountry_ReturnsCities() {
        assumeDbConnected();
        List<City> cities = cityReports.getCitiesByCountry("Japan");
        assertNotNull(cities);
        assertFalse(cities.isEmpty());
    }

    // Test 5: ensure sorted descending by population
    @Test
    void getCitiesByCountry_ValidCountry_IsSortedDescending() {
        assumeDbConnected();
        List<City> cities = cityReports.getCitiesByCountry("Japan");

        assertNotNull(cities);
        assertTrue(cities.size() > 1);

        for (int i = 1; i < cities.size(); i++) {
            long prev = cities.get(i - 1).getPopulation();
            long current = cities.get(i).getPopulation();
            assertTrue(prev >= current, "Cities are not sorted in descending order.");
        }
    }

    // Test 6: displayCities should handle null list
    @Test
    void displayCities_NullList_DoesNotThrow() {
        assumeDbConnected();
        cityReports.displayCities(null); // should not crash
    }

    // Test 7: displayCities should handle empty list
    @Test
    void displayCities_EmptyList_DoesNotThrow() {
        assumeDbConnected();
        List<City> empty = new ArrayList<>();
        cityReports.displayCities(empty);
    }

    // Test 8: displayCities should skip null items inside list
    @Test
    void displayCities_ListWithNull_DoesNotThrow() {
        assumeDbConnected();
        List<City> list = new ArrayList<>();
        list.add(null);
        cityReports.displayCities(list); // should not crash
    }

    // Test 9: displayCities with valid data
    @Test
    void displayCities_ValidCity_PrintsCorrectly() {
        assumeDbConnected();

        City c = new City();
        c.setName("Tokyo");
        c.setCountryCode("JPN");
        c.setDistrict("Kanto");
        c.setPopulation(9000000);

        List<City> cities = new ArrayList<>();
        cities.add(c);

        cityReports.displayCities(cities); // should print without error
    }
}


