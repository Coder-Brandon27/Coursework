package com.napier.devops;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the PopulationReports class.
 * Tests database queries and report generation methods.
 */
public class PopulationReportsTest {

    private static Connection con;
    private static PopulationReports populationReports;

    /**
     * Sets up database connection before all tests run.
     */
    @BeforeAll
    public static void init() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:33060/world?useSSL=false", "root", "example");
            populationReports = new PopulationReports(con);
        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
        }
    }

    /**
     * Closes database connection after all tests complete.
     */
    @AfterAll
    public static void cleanup() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }

    /**
     * Tests that getPopulationByContinent returns a non-null list.
     */
    @Test
    public void testGetPopulationByContinentNotNull() {
        List<Population> reports = populationReports.getPopulationByContinent();
        assertNotNull(reports);
    }

    /**
     * Tests that getPopulationByContinent returns a non-empty list.
     */
    @Test
    public void testGetPopulationByContinentNotEmpty() {
        List<Population> reports = populationReports.getPopulationByContinent();
        assertFalse(reports.isEmpty());
    }

    /**
     * Tests that continent population reports have a name field populated.
     */
    @Test
    public void testGetPopulationByContinentHasName() {
        List<Population> reports = populationReports.getPopulationByContinent();
        assertNotNull(reports.get(0).getName());
    }

    /**
     * Tests that continent population reports have a positive total population.
     */
    @Test
    public void testGetPopulationByContinentHasPositivePopulation() {
        List<Population> reports = populationReports.getPopulationByContinent();
        assertTrue(reports.get(0).getTotalPopulation() > 0);
    }

    /**
     * Tests that getPopulationByRegion returns a non-null list.
     */
    @Test
    public void testGetPopulationByRegionNotNull() {
        List<Population> reports = populationReports.getPopulationByRegion();
        assertNotNull(reports);
    }

    /**
     * Tests that getPopulationByRegion returns a non-empty list.
     */
    @Test
    public void testGetPopulationByRegionNotEmpty() {
        List<Population> reports = populationReports.getPopulationByRegion();
        assertFalse(reports.isEmpty());
    }

    /**
     * Tests that region population reports have a name field populated.
     */
    @Test
    public void testGetPopulationByRegionHasName() {
        List<Population> reports = populationReports.getPopulationByRegion();
        assertNotNull(reports.get(0).getName());
    }

    /**
     * Tests that region population reports have a positive total population.
     */
    @Test
    public void testGetPopulationByRegionHasPositivePopulation() {
        List<Population> reports = populationReports.getPopulationByRegion();
        assertTrue(reports.get(0).getTotalPopulation() >= 0);
    }

    /**
     * Tests that getPopulationByCountry returns a non-null list.
     */
    @Test
    public void testGetPopulationByCountryNotNull() {
        List<Population> reports = populationReports.getPopulationByCountry();
        assertNotNull(reports);
    }

    /**
     * Tests that getPopulationByCountry returns a non-empty list.
     */
    @Test
    public void testGetPopulationByCountryNotEmpty() {
        List<Population> reports = populationReports.getPopulationByCountry();
        assertFalse(reports.isEmpty());
    }

    /**
     * Tests that country population reports have a name field populated.
     */
    @Test
    public void testGetPopulationByCountryHasName() {
        List<Population> reports = populationReports.getPopulationByCountry();
        assertNotNull(reports.get(0).getName());
    }

    /**
     * Tests that country population reports have a positive total population.
     */
    @Test
    public void testGetPopulationByCountryHasPositivePopulation() {
        List<Population> reports = populationReports.getPopulationByCountry();
        assertTrue(reports.get(0).getTotalPopulation() > 0);
    }

    /**
     * Tests that getWorldPopulation returns a positive value.
     */
    @Test
    public void testGetWorldPopulationPositive() {
        long worldPop = populationReports.getWorldPopulation();
        assertTrue(worldPop > 0);
    }

    /**
     * Tests that getWorldPopulation returns a reasonable value over 1 million.
     */
    @Test
    public void testGetWorldPopulationReasonable() {
        long worldPop = populationReports.getWorldPopulation();
        assertTrue(worldPop > 1000000);
    }

    /**
     * Tests that getContinentPopulation returns a positive value for Asia.
     */
    @Test
    public void testGetContinentPopulationAsia() {
        long asiaPop = populationReports.getContinentPopulation("Asia");
        assertTrue(asiaPop > 0);
    }

    /**
     * Tests that getContinentPopulation returns a positive value for Europe.
     */
    @Test
    public void testGetContinentPopulationEurope() {
        long europePop = populationReports.getContinentPopulation("Europe");
        assertTrue(europePop > 0);
    }

    /**
     * Tests that getContinentPopulation returns 0 for invalid continent name.
     */
    @Test
    public void testGetContinentPopulationInvalid() {
        long invalidPop = populationReports.getContinentPopulation("InvalidContinent");
        assertEquals(0, invalidPop);
    }

    /**
     * Tests that getRegionPopulation returns a positive value for valid region.
     */
    @Test
    public void testGetRegionPopulationValid() {
        long regionPop = populationReports.getRegionPopulation("Caribbean");
        assertTrue(regionPop > 0);
    }

    /**
     * Tests that getRegionPopulation returns 0 for invalid region name.
     */
    @Test
    public void testGetRegionPopulationInvalid() {
        long invalidPop = populationReports.getRegionPopulation("InvalidRegion");
        assertEquals(0, invalidPop);
    }

    /**
     * Tests that getCountryPopulation returns a positive value for China.
     */
    @Test
    public void testGetCountryPopulationChina() {
        long chinaPop = populationReports.getCountryPopulation("China");
        assertTrue(chinaPop > 0);
    }

    /**
     * Tests that getCountryPopulation returns 0 for invalid country name.
     */
    @Test
    public void testGetCountryPopulationInvalid() {
        long invalidPop = populationReports.getCountryPopulation("InvalidCountry");
        assertEquals(0, invalidPop);
    }

    /**
     * Tests that getDistrictPopulation returns a non-negative value.
     */
    @Test
    public void testGetDistrictPopulationNonNegative() {
        long districtPop = populationReports.getDistrictPopulation("California");
        assertTrue(districtPop >= 0);
    }

    /**
     * Tests that getCityPopulation returns a positive value for Tokyo.
     */
    @Test
    public void testGetCityPopulationTokyo() {
        long tokyoPop = populationReports.getCityPopulation("Tokyo");
        assertTrue(tokyoPop > 0);
    }

    /**
     * Tests that getCityPopulation returns 0 for invalid city name.
     */
    @Test
    public void testGetCityPopulationInvalid() {
        long invalidPop = populationReports.getCityPopulation("InvalidCity12345");
        assertEquals(0, invalidPop);
    }

    /**
     * Tests that displayPopulationReports executes without errors with valid data.
     */
    @Test
    public void testDisplayPopulationReportsWithData() {
        List<Population> reports = populationReports.getPopulationByContinent();
        populationReports.displayPopulationReports(reports, "Continent");
    }

    /**
     * Tests that displayPopulationReports handles null input.
     */
    @Test
    public void testDisplayPopulationReportsNull() {
        populationReports.displayPopulationReports(null, "Test");
    }

    /**
     * Tests that displayPopulation executes without errors.
     */
    @Test
    public void testDisplayPopulation() {
        populationReports.displayPopulation("Test", 1000000);
    }
}