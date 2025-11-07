package com.napier.devops;

import java.sql.Connection;

public class PopulationReports {

    // Connection to the database
    private final Connection con;

    // Constructor sets up the connection so we can use it in the methods below
    public PopulationReports(Connection con) {
        this.con = con;
    }


}
