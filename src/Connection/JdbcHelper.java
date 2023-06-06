/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 *
 * @author User
 */
public class JdbcHelper {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/QuanLySinhVien";
    private static final String DATABASE_USER_NAME = "root";
    private static final String DATABASE_PASSWORD = "";

    public static Connection getConn() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER_NAME, DATABASE_PASSWORD);
    }

    public static boolean insert(String tableName, String[] columns, Object[] values) {
        boolean success = false;
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER_NAME, DATABASE_PASSWORD)) {
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("INSERT INTO ").append(tableName).append(" (");
            for (int i = 0; i < columns.length; i++) {
                sqlBuilder.append(columns[i]);
                if (i < columns.length - 1) {
                    sqlBuilder.append(", ");
                }
            }
            sqlBuilder.append(") VALUES (");
            for (int i = 0; i < values.length; i++) {
                sqlBuilder.append("?");
                if (i < values.length - 1) {
                    sqlBuilder.append(", ");
                }
            }
            sqlBuilder.append(")");

            String sql = sqlBuilder.toString();

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                for (int i = 0; i < values.length; i++) {
                    statement.setObject(i + 1, values[i]);
                }
                int rowsAffected = statement.executeUpdate();
                success = (rowsAffected > 0);
                System.out.println(success ? "Insertion successful." : "Insertion failed.");
            }
        } catch (SQLException e) {
            System.err.println("Error executing INSERT query: " + e.getMessage());
        }
        return success;
    }

    public static boolean update(String tableName, String[] columns, Object[] values, String condition) {
        boolean success = false;
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER_NAME, DATABASE_PASSWORD)) {
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("UPDATE ").append(tableName).append(" SET ");
            for (int i = 0; i < columns.length; i++) {
                sqlBuilder.append(columns[i]).append(" = ?");
                if (i < columns.length - 1) {
                    sqlBuilder.append(", ");
                }
            }
            sqlBuilder.append(" WHERE ").append(condition);

            String sql = sqlBuilder.toString();

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                for (int i = 0; i < values.length; i++) {
                    statement.setObject(i + 1, values[i]);
                }
                int rowsAffected = statement.executeUpdate();
                success = (rowsAffected > 0);
                System.out.println(success ? "Update successful." : "Update failed.");
            }
        } catch (SQLException e) {
            System.err.println("Error executing UPDATE query: " + e.getMessage());
        }
        return success;
    }

    public static boolean delete(String tableName, String condition) {
        boolean success = false;
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER_NAME, DATABASE_PASSWORD)) {
            String sql = "DELETE FROM " + tableName + " WHERE " + condition;

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                int rowsAffected = statement.executeUpdate();
                success = (rowsAffected > 0);
                System.out.println(success ? "Deletion successful." : "Deletion failed.");
            }
        } catch (SQLException e) {
            System.err.println("Error executing DELETE query: " + e.getMessage());
        }
        return success;
    }

    public static PreparedStatement preparedStatement(String sql, Object... args) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        PreparedStatement pstmt = null;
        if (sql.trim().startsWith("(")) {
            pstmt = connection.prepareCall(sql);
        } else {
            pstmt = connection.prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]);
        }
        return pstmt;
    }

    public static ResultSet select(String tableName, String[] columns, String condition) {
        ResultSet resultSet = null;
        try (Connection connection = getConn()) {
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT ");
            for (int i = 0; i < columns.length; i++) {
                sqlBuilder.append(columns[i]);
                if (i < columns.length - 1) {
                    sqlBuilder.append(", ");
                }
            }
            sqlBuilder.append(" FROM ").append(tableName);
            if (condition != null && !condition.isEmpty()) {
                sqlBuilder.append(" WHERE ").append(condition);
            }

            String sql = sqlBuilder.toString();

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                resultSet = statement.executeQuery();
            }
        } catch (SQLException e) {
            System.err.println("Error executing SELECT query: " + e.getMessage());
        }

        return resultSet;
    }

    //INSERT UPDATE
    public static void executeUpdate(String sql, Object... args) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = JdbcHelper.getConn();
            statement = preparedStatement(sql, args);
            statement.executeUpdate();
            System.out.println("Update executed successfully.");
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // Xử lý lỗi đóng statement (nếu cần)
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Xử lý lỗi đóng kết nối (nếu cần)
                }
            }
        }
    }

    public static void executeNonQuery(String sql, Object... args) {
        try {
            Connection connection = null;
            PreparedStatement statement = null;

            try {
                connection = JdbcHelper.getConn();
                statement = preparedStatement(sql, args);
                statement.executeUpdate();
                System.out.println("Non-query executed successfully.");
            } finally {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {

                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {

                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing non-query: " + e.getMessage());
        }
    }
    //SELECT
    public static ResultSet executeQuery(String sql, Object... args) {
        try {
            PreparedStatement stmt = preparedStatement(sql, args);
            return stmt.executeQuery();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
