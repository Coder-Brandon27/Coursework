package com.napier.devops;

import java.sql.Connection;
import java.util.List;

public class App {
    public static void main(String[] args) {
        DatabaseConnector db = new DatabaseConnector(); // new database instance
        Connection con ;
        if(args.length < 1){
            con = db.connect("localhost:33060", 30000);
        }else{
            con = db.connect(args[0], Integer.parseInt(args[1]));
        }
        if (con != null) {
            System.out.println("Connected to Database.");

            //City reports object to access city related queries
            CityReports reports = new CityReports(con);
            //USER STORY 1: Top N populated cities in the world
            int N = 10;
            List<City> topCities = reports.getTopCities(N);
            System.out.println("\nTop " + N + " most populated cities in the world:");
            reports.displayCities(topCities);

            //USER STORY 2: All cities in the world ordered by population (largest>smallest)
            //This query returns a lot of rows so I have limited it to the first 20 for readibility
            List<City> allCities = reports.getAllCities();
            System.out.println("\nFirst 20 from full city list (descending population):");
            int limit = Math.min(20, allCities.size());
            reports.displayCities(allCities.subList(0, limit));

            //user story 3:
            // Get the cities grouped by district
            List<City> cities = reports.getCitiesByDistrict();
            System.out.println("\n Cities in each district organised by largest to smallest population\n");
            // Display them grouped by district and displays
            reports.displayCitiesByDistrictGrouped(cities);

            //user story 4:
            //Top N populated countries in a continent
            CountryReports countryReportService = new CountryReports(con);
            String continent = "Asia";
            int countryN = 10;
            System.out.println("\nTop " + countryN + " most populated countries in " + continent + ":");
            countryReportService.displayCountries(
                    countryReportService.getTopCountriesInContinent(continent, countryN)
            );

            // user story 33: view all cities in Japan
            List<City> japanCities = reports.getCitiesByCountry("Japan");
            reports.displayCitiesByCountry("Japan", japanCities);

            // Create an instance of PopulationReports with the database connection
            PopulationReports popReports = new PopulationReports(con);

            //Get population breakdown by continent
            System.out.println("=== CONTINENT POPULATION REPORTS ===");
            List<Population> continentReports = popReports.getPopulationByContinent();
            popReports.displayPopulationReports(continentReports, "Continent");

            //Get population breakdown by region
            System.out.println("\n=== REGION POPULATION REPORTS ===");
            List<Population> regionReports = popReports.getPopulationByRegion();
            popReports.displayPopulationReports(regionReports, "Region");

            //Get population breakdown by country
            System.out.println("\n=== COUNTRY POPULATION REPORTS ===");
            List<Population> countryReports = popReports.getPopulationByCountry();
            popReports.displayPopulationReports(countryReports, "Country");

           //world population
            long worldPop = popReports.getWorldPopulation();
            popReports.displayPopulation("World", worldPop);

            //Get continent population
            long asiaPop = popReports.getContinentPopulation("Asia");
            popReports.displayPopulation("Asia", asiaPop);

            //Get region population
            long caribbeanPop = popReports.getRegionPopulation("Caribbean");
            popReports.displayPopulation("Caribbean", caribbeanPop);

            //Get country population
            long ukPop = popReports.getCountryPopulation("United Kingdom");
            popReports.displayPopulation("United Kingdom", ukPop);

            //Get district population
            long englandPop = popReports.getDistrictPopulation("England");
            popReports.displayPopulation("England District", englandPop);

            //Get city population
            long londonPop = popReports.getCityPopulation("London");
            popReports.displayPopulation("London", londonPop);

            //user story 23:
            // Gets the largest to smalled countries from selected region
            System.out.println("\n List of all countries in a region listed from highest population to lowest");
            List<Country> CountryLToS = countryReportService.RegionLargeToSmall("Western Europe");
            countryReportService.displayCountiresByLargestToSmallest(CountryLToS);

            //user story 24:
            // Lists the top n countries around the world by population
            System.out.println("\n Lists the top N countries around the world by population");
            List<Country> CountryTopNPop = countryReportService.getTopNCountriesByPopulation(5);
            countryReportService.displayTopNPop(CountryTopNPop);

            //user story 26:
            // Lists the top n countries by population of a select region
            System.out.println("\n Lists the top N countries around the world by population");
            List<Country> CountryTopNPopRegion = countryReportService.TopNCountriesByRegions("Western Europe", 5);
            countryReportService.displayTopNPopRegion(CountryTopNPopRegion);

            db.close();
        } else {
            System.out.println("Connection failed after multiple attempts.");
        }
    }
}


        
