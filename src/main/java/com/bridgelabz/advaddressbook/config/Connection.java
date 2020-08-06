package com.bridgelabz.advaddressbook.config;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    private static final String url = "jdbc:mysql://localhost:3306/address_book";
    private static final String uid = "root";
    private static final String password = "toor";
    public static java.sql.Connection con;

    static {
        try {
            con = DriverManager.getConnection(url, uid, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

