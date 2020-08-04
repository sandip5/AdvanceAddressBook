package com.bridgelabz.advaddressbook.services;

public interface IAddressBook {
    void addPerson();

    void editPerson();

    void deletePerson();

    void sortByName();

    void sortByCity();

    void sortByState();

    void sortByZip();

    void searchByCityAndState();

    void searchByCityOrState();
}
