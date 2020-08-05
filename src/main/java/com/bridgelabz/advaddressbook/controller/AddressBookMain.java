package com.bridgelabz.advaddressbook.controller;
import com.bridgelabz.advaddressbook.db.BussLayer;
import com.bridgelabz.advaddressbook.db.DbManager;
import com.bridgelabz.advaddressbook.model.Person;
import com.bridgelabz.advaddressbook.services.ImplAddressBook;
import com.bridgelabz.advaddressbook.utility.IoOperation;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class AddressBookMain {
    public static void main(String[] args) {
        System.out.println(
                "Welcome to Address Book---> You Can Save Person Address Details In This Book...");
        AddressBookMain addressBookMain = new AddressBookMain();
        addressBookMain.openAddressBook();
    }

    public void openAddressBook() {
        Scanner scanner = new Scanner(System.in);
        ImplAddressBook util = new ImplAddressBook();
        ArrayList<Person> addressBookList;
        System.out.println("Select Option: \n1.Read File Using Simple Json" +
                "\n2.Read File Using OpenCSV" +
                "\n3.Read File Using Gson" +
                "\n4.Do Operation In Empty List" +
                "\n5.Do Operation With SQL Server");
        int select = scanner.nextInt();
        switch (select) {
            case 1:
                addressBookList = new IoOperation().readFromJson();
                break;
            case 2:
                addressBookList = new IoOperation().readFromCSV();
                break;
            case 3:
                addressBookList = new IoOperation().readFromGson();
                break;
            case 4:
                addressBookList = new ArrayList<>();
                break;
            case 5:
                addressBookList = new DbManager().getAllPerson();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + select);
        }
        util.setList(addressBookList);
        System.out.println("-----------------------Open Address Book-----------------------");
        AtomicBoolean close = new AtomicBoolean(false);
        while (!close.get()) {
            System.out.println(
                    "Select option: \n1.Enter 1, To Add Person Details In Address Book..." +
                            "\n2.Enter 2, To Print All Address From This Address Book..." +
                            "\n3.Enter 3, To Close Address Book..." +
                            "\n4.Enter 4, To Edit Person Details By Person Name In Address Book..." +
                            "\n5.Enter 5, To Delete Person Details By Person Name In Address Book..." +
                            "\n6.Enter 6, To Arrange Person Name By Ascending Order In Address Book... " +
                            "\n7.Enter 7, To Arrange Person's City By Ascending Order In Address Book..." +
                            "\n8.Enter 8, To Arrange Person's State By Ascending Order In Address Book..." +
                            "\n9.Enter 9, To Arrange Person's Zip By Ascending Order In Address Book..." +
                            "\n10.Enter 10, To Search Person Details By Giving City And State Both From Address Book..." +
                            "\n11.Enter 11, To Search Person Details By Giving City Or State Both From Address Book..." +
                            "\n12.Enter 12, To Write Address Book Details In JSON File Using Simple JSON" +
                            "\n13.Enter 13, To Write Address Book Details In CSV File Using Open CSV" +
                            "\n14.Enter 14, To Write Address Book Details In JSON File Using Gson");
            switch (scanner.nextInt()) {
                case 1:
                    util.addPerson();
                    break;
                case 2:
                    util.printPersonDetails();
                    break;
                case 3:
                    close.set(true);
                    break;
                case 4:
                    util.editPerson();
                    break;
                case 5:
                    util.deletePerson();
                    break;
                case 6:
                    util.sortByName();
                    break;
                case 7:
                    util.sortByCity();
                    break;
                case 8:
                    util.sortByState();
                    break;
                case 9:
                    util.sortByZip();
                    break;
                case 10:
                    util.searchByCityAndState();
                    break;
                case 11:
                    util.searchByCityOrState();
                    break;
                case 12:
                    util.writeFileUsingJsonSimple();
                    break;
                case 13:
                    util.writeCSVFileUsingOpenCsv();
                    break;
                case 14:
                    util.writeFileUsingGson();
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }
}