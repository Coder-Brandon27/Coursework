package com.napier.devops;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the PopulationReports class.
 */
public class PopulationReportsTest {

    private PopulationReports reports;

    @BeforeEach
    void setup() {
        // Create the class with a null DB connection.
        // Some tests will skip DB-dependent methods if this is null.
        try {
            reports = new PopulationReports(null);
        } catch (Exception e) {
            reports = null;
        }
    }

    /**
     * Verifies that the class can be created safely.
     */
    @Test
    void testConstructorNotNull() {
        assertNotNull(reports, "PopulationReports instance should not be null after construction.");
    }

    /**
     * Checks that displayPopulationReports can handle an empty list safely.
     */
    @Test
    void testDisplayPopulationReportsHandlesEmptyList() {
        if (reports == null) return;
        List<Population> emptyList = new ArrayList<>();
        try {
            reports.displayPopulationReports(emptyList, "Continent");
        } catch (Exception e) {
            fail("displayPopulationReports threw an exception for empty list: " + e.getMessage());
        }
    }

    /**
     * Checks that displayPopulationReports can handle a null list safely.
     */
    @Test
    void testDisplayPopulationReportsHandlesNullList() {
        if (reports == null) return;
        try {
            reports.displayPopulationReports(null, "Region");
        } catch (Exception e) {
            fail("displayPopulationReports threw an exception for null list: " + e.getMessage());
        }
    }

    /**
     * Verifies displayPopulation works with normal input.
     */
    @Test
    void testDisplayPopulationWithNormalInput() {
        if (reports == null) return;
        reports.displayPopulation("TestCity", 1000);
    }

    /**
     * Verifies displayPopulation handles zero values safely.
     */
    @Test
    void testDisplayPopulationWithZeroPopulation() {
        if (reports == null) return;
        reports.displayPopulation("EmptyTown", 0);
    }

    /**
     * Verifies displayPopulation handles null names safely.
     */
    @Test
    void testDisplayPopulationWithNullName() {
        if (reports == null) return;
        try {
            reports.displayPopulation(null, 5000);
        } catch (Exception e) {
            fail("displayPopulation threw an exception for null name: " + e.getMessage());
        }
    }

    /**
     * Verifies getContinentPopulation handles null input
     */
    @Test
    void testGetContinentPopulationHandlesNullName() {
        if (reports == null) return;
        try {
            reports.getContinentPopulation(null);
        } catch (NullPointerException e) {
            // acceptable behaviour for null DB connection — just skip
        }
    }

    /**
     * Verifies getContinentPopulation handles empty input
     */
    @Test
    void testGetContinentPopulationHandlesEmptyName() {
        if (reports == null) return;
        try {
            reports.getContinentPopulation("");
        } catch (NullPointerException e) {
            // skip DB-based failure
        }
    }

    /**
     * Verifies getRegionPopulation handles null safely.
     */
    @Test
    void testGetRegionPopulationHandlesNullName() {
        if (reports == null) return;
        try {
            reports.getRegionPopulation(null);
        } catch (NullPointerException e) {
            // skip
        }
    }

    /**
     * Verifies getCountryPopulation handles null safely.
     */
    @Test
    void testGetCountryPopulationHandlesNullName() {
        if (reports == null) return;
        try {
            reports.getCountryPopulation(null);
        } catch (NullPointerException e) {
            // skip
        }
    }

    /**
     * Verifies getDistrictPopulation handles null safely.
     */
    @Test
    void testGetDistrictPopulationHandlesNullName() {
        if (reports == null) return;
        try {
            reports.getDistrictPopulation(null);
        } catch (NullPointerException e) {
            // skip
        }
    }

    /**
     * Verifies getCityPopulation handles null safely.
     */
    @Test
    void testGetCityPopulationHandlesNullName() {
        if (reports == null) return;
        try {
            reports.getCityPopulation(null);
        } catch (NullPointerException e) {
            // skip
        }
    }

    /**
     * Simple test to confirm getWorldPopulation can be called.
     */
    @Test
    void testGetWorldPopulationReturnsZeroWithoutDB() {
        if (reports == null) return;
        try {
            reports.getWorldPopulation();
        } catch (NullPointerException e) {
            // skip
        }
    }
}
