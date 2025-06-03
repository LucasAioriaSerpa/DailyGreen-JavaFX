package org.dailygreen.dailygreen;

import java.sql.*;

public class SQLiteConnection {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    public SQLiteConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/DATABASE/db_dailygreen.sqlite");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.getResultSet();
    }
    public ResultSet executeQuery(String query) throws SQLException {
        statement.execute(query);
        return resultSet;
    }
}
