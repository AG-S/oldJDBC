package com.ag.Jdbc;

import java.sql.SQLException;
import java.sql.Statement;

public class QueryExecute {
    protected Statement statement;
    protected String query;
    protected int countOperation;
    protected Operations operations;

    public QueryExecute(Statement statement,String query) {
        this.statement = statement;
        this.query=query;
    }

    void execute() throws SQLException {
        checkQuery();
        countOperation=statement.executeUpdate(query);

    }

    void display(){
        System.out.println(countOperation+operations.getMessage());
    }

    void checkQuery(){
        operations = Operations.getByName(query.trim().split(" ")[0]);

    }
}
