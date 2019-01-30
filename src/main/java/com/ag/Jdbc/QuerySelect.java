package com.ag.Jdbc;

import dnl.utils.text.table.TextTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuerySelect extends QueryExecute {
    ResultSet resultSet;
    public QuerySelect(Statement statement, String query) {
        super(statement, query);
    }

    @Override
    void execute() throws SQLException {
        resultSet = statement.executeQuery(query);
        operations = Operations.getByName("select");
    }

    @Override
    void display(){
        try {
             TextTable textTable = buildDataForDisplaying();
             if (textTable==null) {
                 super.display();
             } else {
                 textTable.printTable();
             }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private TextTable buildDataForDisplaying() throws SQLException {
        int columnCount = resultSet.getMetaData().getColumnCount();
        // System.out.println(size);
        String[] columns = new String[columnCount];
        List<List<Object>> rowData= new ArrayList<>();
       //
        for (int i = 0; i < columnCount; i++) {
            columns[i] = resultSet.getMetaData().getColumnName(i + 1);
        }
        int i = 0;
        while (resultSet.next()) {
            rowData.add(new ArrayList<>());
            for (int j = 0; j < columnCount; j++) {
                rowData.get(i).add(resultSet.getObject(columns[j]));
            }
            i++;
        }
        int size = i;
        if (size==0) {
            return null;
        }
        Object[][] data = new Object[i][columnCount];
        for(i=0; i<rowData.size(); i++){
            for (int j=0; j<columnCount; j++ ) {
                data[i][j]=rowData.get(i).get(j);
            }
        }
        return new TextTable(columns, data);

    }


}
