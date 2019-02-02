package com.ag.Jdbc;

import java.io.*;

public class WriterHTML {
    private static final String LINE_SEPARATOR =  System.getProperty("line.separator");
    private Data data;
    private String path = "htmltable.html";

    public WriterHTML(Data inputdata) {
        this.data = inputdata;
    }

    public void writeToFile() {
        File file = new File(path);
        try(
        FileOutputStream fileOutputStream= new FileOutputStream(file);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));)
        {
            writer.write(dataToString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String dataToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><head></head><body>");
        stringBuilder.append(LINE_SEPARATOR);
        stringBuilder.append("<table border=1 cellpadding=10 cellspacing=0>");
        stringBuilder.append(LINE_SEPARATOR);
        stringBuilder.append("<tr><th></th>");
        stringBuilder.append(LINE_SEPARATOR);
        for (String column : data.getColumns()) {
            stringBuilder.append("<th>");
            stringBuilder.append(column);
            stringBuilder.append("</th>");
            stringBuilder.append(LINE_SEPARATOR);
        }
        stringBuilder.append("</tr>");
        stringBuilder.append(LINE_SEPARATOR);
        int i = 0;
        for (Object[] arrayData: data.getData()){
            i++;
            stringBuilder.append("<tr><th>");
            stringBuilder.append(i);
            stringBuilder.append("</th>");
            stringBuilder.append(LINE_SEPARATOR);
            for (Object objectData : arrayData){
                stringBuilder.append("<td align=\"right\">");
                stringBuilder.append(objectData);
                stringBuilder.append("</td>");
                stringBuilder.append(LINE_SEPARATOR);
            }
        }
        stringBuilder.append("</table>");
        stringBuilder.append(LINE_SEPARATOR);
        stringBuilder.append("</body></html>");
        stringBuilder.append(LINE_SEPARATOR);
        return stringBuilder.toString();
    }
}
