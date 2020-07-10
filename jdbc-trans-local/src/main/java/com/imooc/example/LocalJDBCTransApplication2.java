package com.imooc.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class LocalJDBCTransApplication2 {

    private static final Logger log = LoggerFactory.getLogger("LocalJDBCTransApplication2");

    public static void main(String[] args) throws SQLException {

        Connection connection = getConnection();
        connection.setAutoCommit(false);

        String query = "select * from t_user where id = 1 for update";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        int superManAmount = 0;
        while (rs.next()) {
            String username = rs.getString(2);
            int amount = rs.getInt(3);
            log.info("{} has amount: {}", username, amount);
            if (username.equals("SuperMan")) {
                superManAmount = amount;
            }
        }

        String plusSQL = "update t_user\n" +
                "set amount = ? + 100\n" +
                "where username = ?";
        PreparedStatement plusPS = connection.prepareStatement(plusSQL);

        plusPS.setInt(1, superManAmount);
        plusPS.setString(2,"SuperMan");
        plusPS.executeUpdate();


        connection.commit();

        plusPS.close();
        connection.close();
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
