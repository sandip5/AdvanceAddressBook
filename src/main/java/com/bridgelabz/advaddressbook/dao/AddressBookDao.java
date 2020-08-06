package com.bridgelabz.advaddressbook.dao;

import com.bridgelabz.advaddressbook.config.Connection;
import com.bridgelabz.advaddressbook.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AddressBookDao {
    public ArrayList<Person> getAllPerson() {
        ArrayList<Person> personList = null;
        try (Statement stat = Connection.con.createStatement();
             ResultSet result = stat.executeQuery("select * from person")) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }
}
