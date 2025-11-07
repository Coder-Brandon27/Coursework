package com.napier.devops;

public class Population {

    // The name of the continent/region/country
    private String name;

    // Total population
    private long totalPopulation;

    // Population living in cities
    private long urbanPopulation;

    // Population not living in cities
    private long ruralPopulation;

    // Default constructor
    public Population() {
    }

    // Constructor with all fields
    public Population(String name, long totalPopulation, long urbanPopulation, long ruralPopulation) {
        this.name = name;
        this.totalPopulation = totalPopulation;
        this.urbanPopulation = urbanPopulation;
        this.ruralPopulation = ruralPopulation;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalPopulation() {
        return totalPopulation;
    }

    public void setTotalPopulation(long totalPopulation) {
        this.totalPopulation = totalPopulation;
    }

    public long getUrbanPopulation() {
        return urbanPopulation;
    }

    public void setUrbanPopulation(long urbanPopulation) {
        this.urbanPopulation = urbanPopulation;
    }

    public long getRuralPopulation() {
        return ruralPopulation;
    }

    public void setRuralPopulation(long ruralPopulation) {
        this.ruralPopulation = ruralPopulation;
    }

    // Calculate urban percentage
    public double getUrbanPercentage() {
        if (totalPopulation == 0) return 0.0;
        return (urbanPopulation * 100.0) / totalPopulation;
    }

    // Calculate rural percentage
    public double getRuralPercentage() {
        if (totalPopulation == 0) return 0.0;
        return (ruralPopulation * 100.0) / totalPopulation;
    }

    @Override
    public String toString() {
        return "Population{" +
                "name='" + name + '\'' +
                ", totalPopulation=" + totalPopulation +
                ", urbanPopulation=" + urbanPopulation +
                ", ruralPopulation=" + ruralPopulation +
                '}';
    }
}