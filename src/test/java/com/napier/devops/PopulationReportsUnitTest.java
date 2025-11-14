package com.napier.devops;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JUnit4 tests for PopulationReports that avoid SQL and mocking.
 * These tests only cover methods that do not rely on a real database.
 */
public class PopulationReportsUnitTest {

    // Create PopulationReports with a null connection (unused in these tests)
    private final PopulationReports reports = new PopulationReports(null);

    /**
     * Ensures that displayPopulationReports accepts an empty list
     * without throwing an exception.
     */
    @Test
    public void testDisplayPopulationReportsEmptyList() {
        List<Population> list = new ArrayList<>();
        reports.displayPopulationReports(list, "Empty Test");
        assertTrue(true); // simple assertion to validate test reached this point
    }

    /**
     * Ensures displayPopulationReports accepts one Population object.
     */
    @Test
    public void testDisplayPopulationReportsSingleEntry() {
        List<Population> list = new ArrayList<>();

        Population p = new Population();
        p.setName("Europe");
        p.setTotalPopulation(1000);
        p.setUrbanPopulation(600);
        p.setRuralPopulation(400);

        list.add(p);

        reports.displayPopulationReports(list, "Continent");
        assertEquals(1, list.size());
    }

    /**
     * Ensures displayPopulationReports handles null safely.
     */
    @Test
    public void testDisplayPopulationReportsNull() {
        reports.displayPopulationReports(null, "Null Test");
        assertTrue(true);
    }

    /**
     * Ensures displayPopulation prints a population without error.
     */
    @Test
    public void testDisplayPopulation() {
        reports.displayPopulation("TestLand", 12345);
        assertTrue(true);
    }

    /**
     * Ensures that Population object fields store values correctly.
     */
    @Test
    public void testPopulationSettersAndGetters() {
        Population p = new Population();
        p.setName("TestRegion");
        p.setTotalPopulation(5000);
        p.setUrbanPopulation(3000);
        p.setRuralPopulation(2000);

        assertEquals("TestRegion", p.getName());
        assertEquals(5000, p.getTotalPopulation());
        assertEquals(3000, p.getUrbanPopulation());
        assertEquals(2000, p.getRuralPopulation());
    }
}
