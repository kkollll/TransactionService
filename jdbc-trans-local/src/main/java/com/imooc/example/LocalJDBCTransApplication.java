package com.imooc.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LocalJDBCTransApplication {

    private static final Logger log = LoggerFactory.getLogger("LocalJDBCTransApplication");

    public static void main(String[] args) throws SQLException {

        Connection connection = getConnection();
        connection.setAutoCommit(false);

        String plusSQL = "update t_user\n" +
                "set amount = amount + 100\n" +
                "where username = ?";
        PreparedStatement plusPS = connection.prepareStatement(plusSQL);

        String minusSQL = "update t_user\n" +
                "set amount = amount - 100\n" +
                "where username = ?";
        PreparedStatement minusPS = connection.prepareStatement(minusSQL);

        plusPS.setString(1,"SuperMan");
        plusPS.executeUpdate();

        simulateERROR();

        minusPS.setString(1,"BatMan");
        minusPS.executeUpdate();

        connection.commit();

        plusPS.close();
        minusPS.close();
        connection.close();
    }

    private static void simulateERROR() throws SQLException {
        throw new SQLException("Some error");
    }

    private static Connection getConnection() throws SQLException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/dist_tran_course?serverTimezone=UTC";
        String username = "root";
        String password = "123";

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }
        return DriverManager.getConnection(url, username, password);
    }
}
