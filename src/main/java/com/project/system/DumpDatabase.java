package com.project.system;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;

/**
 * Класс для дампа данных из БД в виде sql запросов INSERT
 */
public class DumpDatabase {

    private Connection conn;
    private PrintWriter out;

    public static void main(String[] args) {
        try {
            DumpDatabase app = new DumpDatabase();
            String fileName = "src/main/resources/init_data.sql";
            System.out.println("Creating " + fileName);
            app.dumpDatabase(fileName);
            System.out.println("Done.");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public DumpDatabase() throws IOException,
            ClassNotFoundException, SQLException {
        // Get database connection from hibernate.properties.
        // Or hard-code your own JDBC connection if desired.
        InputStream in = getClass().getResourceAsStream(
                "/config/hibernate.properties");
        Properties properties = new Properties();
        properties.load(in);
        String driver = properties.getProperty(
                "hibernate.connection.driver_class");
        String url = properties.getProperty(
                "hibernate.connection.url");
        String user = properties.getProperty(
                "hibernate.connection.username");
        String password = properties.getProperty(
                "hibernate.connection.password");

        Class.forName(driver);
        conn = DriverManager.getConnection(url, user, password);
    }

    public void dumpDatabase(String fileName)
            throws FileNotFoundException, SQLException {
        out = new PrintWriter(fileName);
        listAll();
        out.close();
        conn.close();
    }

    public void listAll() throws SQLException {
        DatabaseMetaData metadata = conn.getMetaData();
        String[] types = {"TABLE"};
        ResultSet rs = metadata.getTables(
                null, null, null, types);
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            listTable(tableName);
        }
    }

    private void listTable(String tableName) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(
                "select * from " + tableName + " a");
        out.println("----" + tableName + "----");
        int rowNo = 0;
        ResultSet rs = statement.executeQuery();
        StringBuilder insertDataQuery = new StringBuilder();
        while (rs.next()) {

            if (rowNo == 0) {
                insertDataQuery.append(buildInsert(rs, tableName));
            }
            insertDataQuery.append(buildInsertValues(rs));
            if (!rs.isLast()) {
                insertDataQuery.append(", ");
            } else {
                insertDataQuery.append(";");
            }
            rowNo++;
        }
        out.println(insertDataQuery);
    }

    private String buildInsert(ResultSet rs, String tableName)
            throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        StringBuilder insertInfo = new StringBuilder("INSERT INTO " + tableName + " (");
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            if (i != 0) {
                insertInfo.append(", ");
            }
            int col = i + 1;
            insertInfo.append(metaData.getColumnName(col));
        }
        insertInfo.append(") VALUES");
        return insertInfo.toString();
    }

    private String buildInsertValues(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        StringBuilder insertValues = new StringBuilder(" (");
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            if (i != 0) {
                insertValues.append(", ");
            }
            String column = metaData.getColumnName(i + 1);
            String value = rs.getString(column);
            if (value != null) {
                insertValues.append("'").append(value).append("'");
            } else {
                insertValues.append(value);
            }
        }
        insertValues.append(")");
        return insertValues.toString();
    }

}