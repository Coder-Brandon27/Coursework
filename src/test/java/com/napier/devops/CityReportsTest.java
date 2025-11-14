package com.napier.devops;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

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


    

}

