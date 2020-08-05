package com.bridgelabz.advaddressbook.db;

import com.bridgelabz.advaddressbook.model.Person;

import java.sql.*;
import java.util.ArrayList;

public class DbManager {
    private final String driver = "com.mysql.cj.jdbc.Driver";
    private final String url = "jdbc:mysql://localhost:3306/address_book";
    private final String uid = "root";
    private final String password = "toor";

    public void selectRecords() {
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, uid, password);
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("select * from person");
            while (result.next()) {
                System.out.println("id -->" + result.getInt(1));
                System.out.println("name -->" + result.getString(2));
                System.out.println("mobile -->" + result.getLong(3));
                System.out.println("city -->" + result.getString(4));
                System.out.println("state -->" + result.getString(5));
                System.out.println("zip -->" + result.getInt(6));
            }
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean recordManipulation(String query) {
        boolean flag = false;
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, uid, password);
            Statement stat = con.createStatement();
            flag = stat.executeUpdate(query) > 0;
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public ArrayList<Person> getAllPerson() {
        ArrayList<Person> personList = null;
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, uid, password);
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("select * from person");
            personList = new ArrayList<>();
            while (result.next()) {
                Person person = new Person();
                person.setName(result.getString(2));
                person.setMobile(result.getLong(3));
                person.setCity(result.getString(4));
                person.setState(result.getString(5));
                person.setZip(result.getInt(6));
                personList.add(person);
            }
            return personList;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }
}

