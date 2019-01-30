package com.ag.Jdbc;

import dnl.utils.text.table.TextTable;

import java.sql.*;
import java.util.Scanner;

public class Dbase {
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/db3";
    static final String USER = "postgres";
    static final String PASS = "su";
    static final String BYE_SCANNER = "bye";


    public static void main(String[] args) throws SQLException {
        Connection connection =null;
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement statement = connection.createStatement();
        Scanner scanner = new Scanner(System.in);
        System.out.println("to Exit type \"bye\"");
        while (true) {
            String line = scanner.nextLine();
            if (BYE_SCANNER.equalsIgnoreCase(line.trim())) {
                break;
            }
            QueryExecute query =allocate(line, statement);
            query.execute();
            query.display();
        }
        connection.close();
    }

    private static QueryExecute  allocate (String line, Statement statement) throws SQLException {
        if (Operations.SELECT.getName().equalsIgnoreCase(line.trim().split(" ")[0])) {
            QuerySelect query = new QuerySelect(statement,line);
            return query;
        } else {
            QueryExecute query = new QueryExecute(statement,line);
            return query;

        }
    }
}
