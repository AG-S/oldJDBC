package com.ag.Jdbc;

import dnl.utils.text.table.TextTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueryExecute {
    private Statement statement;
    private String query;
    private int countOperation;
    private Operations operations;
    private boolean selectQuery;
    private ResultSet resultSet;
    private TextTable textTable;
    private Data data;


    public QueryExecute(Statement statement,String query) {
        this.statement = statement;
        this.query=query;
    }

    public void execute()  {
        checkQuery();
        if (selectQuery) {
            try {
                resultSet = statement.executeQuery(query);
                data = new Data();
                defineColumns(data);
                defineData(data);
                textTable = new TextTable(data.getColumns(), data.getData());
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException() ;
            } finally {
                try {
                    resultSet.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                countOperation = statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException() ;
            }
        }

    }

    public void display(){
        if (countOperation ==0 || !selectQuery) {
            System.out.println(countOperation+operations.getMessage());
        } else {
            textTable.printTable();
        }

    }

    private void checkQuery(){
        operations = Operations.getByName(query.trim().split(" ")[0]);
        if (Operations.SELECT.getName().equalsIgnoreCase(query.trim().split(" ")[0])) {
            selectQuery = true;
        }

    }

    public void writeToHTML() {
        if (selectQuery) {
            WriterHTML writerHTML =new WriterHTML(data);
            writerHTML.writeToFile();
        }

    }

    private void defineColumns(Data data){
        try {
            int columnCount = resultSet.getMetaData().getColumnCount();
            String[] columns = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columns[i] = resultSet.getMetaData().getColumnName(i + 1);
            }
            data.setColumns(columns);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    private void defineData(Data data) {
        Object[][] parsedData =null;
        int columnCount = data.getColumns().length;
        List<List<Object>> rowData = new ArrayList<>();
        int i = 0;
        try {
            while (resultSet.next()) {
                rowData.add(new ArrayList<>());
                for (int j = 0; j < columnCount; j++) {
                    rowData.get(i).add(resultSet.getObject(data.getColumns()[j]));
                }
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        countOperation = i;
        if (countOperation != 0) {
           parsedData = new Object[i][columnCount];
            for (i = 0; i < rowData.size(); i++) {
                for (int j = 0; j < columnCount; j++) {
                    parsedData[i][j] = rowData.get(i).get(j);
                }
            }
        }
        data.setData(parsedData);
    }
}
