package com.ag.Jdbc;

import dnl.utils.text.table.TextTable;

import java.sql.*;
import java.util.Scanner;

public class Dbase {
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/db3";
    static final String USER = "postgres";
    static final String PASS = "su";
    static final String BYE_SCANNER = "bye";
    static final String SELECT = "SELECT";
    public static void main(String[] args) throws SQLException {
        Connection connection =null;
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement statement = connection.createStatement();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            if (BYE_SCANNER.equalsIgnoreCase(line.trim())) {
                break;
            }
            allocate(line, statement);
        }
        connection.close();
    }

    private static void  allocate (String line, Statement statement) throws SQLException {
        if (SELECT.equalsIgnoreCase(line.trim().substring(0,6))) {
            ResultSet resultSet = statement.executeQuery(line);
            printResultSet(resultSet);

        } else {
            System.out.println(statement.executeUpdate(line));
        }
    }

    private static void printResultSet(ResultSet resultSet) throws SQLException {
        int columnCount = resultSet.getMetaData().getColumnCount();
        int size = 3;///resultSet.getFetchSize();
        System.out.println(size);
        String[] columns = new String[columnCount];
        Object[][] data = new Object[size][columnCount];
        for (int i= 0 ; i< columnCount ; i++)   {
            columns[i] = resultSet.getMetaData().getColumnName(i+1);
        }
        int i =0;
        while (resultSet.next()) {
            for (int j =0 ; j<columnCount; j++) {
                Object obj= resultSet.getObject(columns[j]);
                data[i][j] = resultSet.getObject(columns[j]);
            }
            i++;
        }
        TextTable tt = new TextTable(columns,data);
        tt.printTable();
    }
}
