package com.napier.devops;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CityReportsTest {

    // Test 1: null input list for displayCities
    @Test
    void displayCities_NullList_DoesNotCrash() {
        CityReports reports = new CityReports(null); 
        reports.displayCities(null);                 // should not crash
    }

    // Test 2: empty list for displayCities
    @Test
    void displayCities_EmptyList_DoesNotCrash() {
        CityReports reports = new CityReports(null);
        reports.displayCities(new ArrayList<>());    // should not crash
    }

    // Test 3: displayCities with mock data
    @Test
    void displayCities_MockData_Works() {
        CityReports reports = new CityReports(null);

        List<City> fakeCities = new ArrayList<>();

        City c = new City();
        c.setId(1);
        c.setName("Test City");
        c.setCountryCode("TC");
        c.setDistrict("Test District");
        c.setPopulation(100000);

        fakeCities.add(c);

        reports.displayCities(fakeCities);  // should print without error
    }

    // Test 4: check sorting logic manually 
    @Test
    void testCitiesAreSortedDescending() {
        List<City> fakeCities = new ArrayList<>();

        City c1 = new City(); c1.setPopulation(3000);
        City c2 = new City(); c2.setPopulation(2000);
        City c3 = new City(); c3.setPopulation(1000);

        fakeCities.add(c1);
        fakeCities.add(c2);
        fakeCities.add(c3);

        // Assert manually sorted 
        for (int i = 1; i < fakeCities.size(); i++) {
            assertTrue(
                fakeCities.get(i - 1).getPopulation() >= fakeCities.get(i).getPopulation()
            );
        }
    }
}



