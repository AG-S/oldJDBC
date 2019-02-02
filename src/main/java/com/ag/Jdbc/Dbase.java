package com.ag.Jdbc;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Dbase {
    private static String db_url;
    private static String db_user;
    private static String db_pass = "su";
    static final String BYE_SCANNER = "bye";


    public static void main(String[] args) throws SQLException {
        try {
            readProperties();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        Connection connection =null;
        connection = DriverManager.getConnection(db_url, db_user, db_pass);
        Statement statement = connection.createStatement();
        Scanner scanner = new Scanner(System.in);
        System.out.println("to Exit type \"bye\"");
        while (true) {
            String line = scanner.nextLine();
            if (BYE_SCANNER.equalsIgnoreCase(line.trim())) {
                break;
            }
            QueryExecute query =new QueryExecute(statement,line);
            query.execute();
            query.display();
            query.writeToHTML();
        }
        connection.close();
    }
    private static void readProperties() throws IOException {
        FileInputStream fileInputStream =null;
        Properties property = new Properties();

        try {
            fileInputStream = new FileInputStream("src/main/resources/config.properties");
            property.load(fileInputStream);
            db_url = property.getProperty("db.host");
            db_user = property.getProperty("db.login");
            db_pass = property.getProperty("db.password");
        } catch (IOException e) {
            System.err.println("config file not found");
        } finally {
            fileInputStream.close();
        }
    }
}
