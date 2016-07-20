package com.mfrockola.classes;

import javax.swing.*;
import java.sql.*;

/**
 * Created by Angel C on 18/07/2016.
 */
public class SQLiteConsultor {

    String ruta;
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    public SQLiteConsultor() {
        String userHome = System.getProperty("user.dir");
        ruta = userHome+ "\\canciones_populares.s3db";
    }

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+ruta);
            statement = connection.createStatement();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public boolean insert(String sql) {
        boolean result = true;
        connect();
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            result = false;
            JOptionPane.showMessageDialog(null,e.getMessage());
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        return result;
    }

    public boolean delete() {
        boolean result = true;
        connect();
        try {
            statement.executeUpdate("DELETE FROM most_popular;");
        } catch (SQLException e) {
            result = false;
            JOptionPane.showMessageDialog(null,e.getMessage());
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        return result;
    }

    public boolean update(String sql) {
        boolean result = true;
        connect();

        try {
            statement.execute(sql);
        } catch (SQLException e) {
            result = false;
            JOptionPane.showMessageDialog(null,e.getMessage());
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        return result;
    }

    public ResultSet query(String sql) {
        connect();
        resultSet = null;
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return resultSet;
    }

    public void closeConnection() {
        try {
            if (connection!= null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
