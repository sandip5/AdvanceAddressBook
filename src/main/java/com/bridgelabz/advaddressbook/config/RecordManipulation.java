package com.bridgelabz.advaddressbook.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RecordManipulation {
    public void selectRecords() {
        try (Statement stat = Connection.con.createStatement();
             ResultSet result = stat.executeQuery("select * from person")) {
            while (result.next()) {
                System.out.println("id -->" + result.getInt(1));
                System.out.println("name -->" + result.getString(2));
                System.out.println("mobile -->" + result.getLong(3));
                System.out.println("city -->" + result.getString(4));
                System.out.println("state -->" + result.getString(5));
                System.out.println("zip -->" + result.getInt(6));
                System.out.println("\n================================\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean recordManipulation(String query) {
        boolean flag = false;
        try (Statement stat = Connection.con.createStatement()) {
            flag = stat.executeUpdate(query) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
