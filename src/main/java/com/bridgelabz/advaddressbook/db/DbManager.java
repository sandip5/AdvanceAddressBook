package com.bridgelabz.advaddressbook.db;

import java.sql.*;

public class DbManager {
    private final String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/address_book";
    private String uid = "root";
    private String password = "toor";
    public void selectRecords() {
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, uid, password);
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("select * from person");
            while (result.next()) {
                System.out.println("Name -->" + result.getString(1));
                System.out.println("Student Name -->" + result.getString(2));
                System.out.println("Student Result -->" + result.getString(3));
                System.out.println("Student Marks -->" + result.getString(4));
            }
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean recordManipulation(String query){
        boolean flag = false;
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, uid, password);
            Statement stat = con.createStatement();
            flag = stat.executeUpdate(query)>0 ? true:false;
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
