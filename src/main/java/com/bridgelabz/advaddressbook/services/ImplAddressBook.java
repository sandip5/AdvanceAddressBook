package com.bridgelabz.advaddressbook.services;

import com.bridgelabz.advaddressbook.config.CrudQuery;
import com.bridgelabz.advaddressbook.enums.EditType;
import com.bridgelabz.advaddressbook.model.Person;
import com.bridgelabz.advaddressbook.utility.IoOperation;
import com.bridgelabz.advaddressbook.utility.ValidateInput;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.util.Comparator.comparing;

public class ImplAddressBook implements IAddressBook {
    private ArrayList<Person> persons;
    private int indexOfPerson;
    private String personName;
    private long mobile;
    private int zip;
    private String state;
    private String city;

    public void setList(ArrayList<Person> addressBookList) {
        this.persons = new ArrayList<>(addressBookList);
    }

    public boolean patternValidation(String value, String regex) {
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(value).matches())
            return false;
        else {
            System.out.println("Enter Valid Input...");
            return true;
        }
    }

    public void printPersonDetails() {
        persons.forEach(System.out::println);
        CrudQuery crudQuery = new CrudQuery();
        crudQuery.selectData();
    }

    /**
     * Method for adding person
     */
    @Override
    public void addPerson() {
        Person person = new Person();
        ValidateInput validateInput = new ValidateInput();
        addPersonName(validateInput);
        person.setName(personName);
        addPersonMobileNumber(validateInput);
        person.setMobile(mobile);
        addCity(validateInput);
        person.setCity(city);
        addState(validateInput);
        person.setState(state);
        addZip(validateInput);
        person.setZip(zip);
        persons.add(person);
        CrudQuery crudQuery = new CrudQuery();
        crudQuery.insertData(person.getName(),
                person.getMobile(),
                person.getCity(),
                person.getState(),
                person.getZip());
        System.out.println("Person added");
    }

    private void addZip(ValidateInput validateInput) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter zip: ");
        try {
            this.zip = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Input Mismatch...");
            addZip(validateInput);
        }
        if (patternValidation(String.valueOf(zip), validateInput.getZIP_PATTERN())) {
            addZip(validateInput);
        }
    }

    private void addState(ValidateInput validateInput) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter state: ");
        this.state = scanner.nextLine();
        if (patternValidation(state, validateInput.getCOMMON_PATTERN())) {
            addState(validateInput);
        }
    }

    private void addCity(ValidateInput validateInput) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter city: ");
        this.city = scan.nextLine();
        if (patternValidation(city, validateInput.getCOMMON_PATTERN())) {
            addCity(validateInput);
        }
    }

    private void addPersonMobileNumber(ValidateInput validateInput) {
        System.out.println("Enter Mobile Number: ");
        Scanner scannerMobile = new Scanner(System.in);
        try {
            this.mobile = scannerMobile.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Input Mismatch...");
            addPersonMobileNumber(validateInput);
        }
        if (patternValidation(String.valueOf(mobile), validateInput.getPHONE_NUMBER_PATTERN())) {
            addPersonMobileNumber(validateInput);
        }
    }

    private void addPersonName(ValidateInput validateInput) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter person name: ");
        this.personName = scan.nextLine();
        if (patternValidation(personName, validateInput.getCOMMON_PATTERN())) {
            addPersonName(validateInput);
        } else {
            checkDuplicate(personName, validateInput);
        }
    }

    private void checkDuplicate(String personName, ValidateInput validateInput) {
        persons.stream().filter(value -> personName.equals(value.getName())).forEach(value -> {
            System.out.println("Duplicate Entries Not Allowed, Try Again... ");
            addPersonName(validateInput);
        });
    }

    @Override
    public void editPerson() {
        CrudQuery crudQuery = new CrudQuery();
        System.out.println("Enter Persons Person Name you want to edit:");
        Scanner scanner = new Scanner(System.in);
        String searchFirstName = scanner.nextLine();
        indexOfPerson = 0;
        boolean isFoundPerson = false;
        for (int i = 0; i < persons.size(); i++) {
            if (searchFirstName.equals(persons.get(i).getName())) {
                isFoundPerson = true;
                indexOfPerson = i;
                break;
            }
        }
        if (isFoundPerson) {
            boolean check = false;
            System.out.println("Select To Edit:\n 1. Mobile Number\n 2. City\n 3. State\n 4. Zip ");
            switch (scanner.nextInt()) {
                case 1:
                    System.out.println("enter new mobile number");
                    Long number = scanner.nextLong();
                    if (!patternValidation(String.valueOf(number), new ValidateInput().getPHONE_NUMBER_PATTERN())) {
                        persons.get(indexOfPerson).setMobile(number);
                        crudQuery.insertData(searchFirstName, EditType.EDIT_NUMBER, number);
                        check = true;
                    }
                    break;
                case 2:
                    Scanner scan = new Scanner(System.in);
                    System.out.println("enter new city name");
                    String city = scan.nextLine();
                    if (!patternValidation(city, new ValidateInput().getCOMMON_PATTERN())) {
                        persons.get(indexOfPerson).setCity(city);
                        crudQuery.insertData(searchFirstName, EditType.EDIT_CITY, city);
                        check = true;
                    }
                    break;
                case 3:
                    scan = new Scanner(System.in);
                    System.out.println("enter new state name");
                    String state = scan.nextLine();
                    if (!patternValidation(state, new ValidateInput().getCOMMON_PATTERN())) {
                        persons.get(indexOfPerson).setState(state);
                        crudQuery.insertData(searchFirstName, EditType.EDIT_STATE, state);
                        check = true;
                    }
                    break;
                case 4:
                    System.out.println("enter new zip");
                    int zip = scanner.nextInt();
                    if (!patternValidation(String.valueOf(zip), new ValidateInput().getZIP_PATTERN())) {
                        persons.get(indexOfPerson).setZip(zip);
                        crudQuery.insertData(searchFirstName, EditType.EDIT_ZIP, Long.valueOf(zip));
                        check = true;
                    }
                    break;
                default:
                    System.out.println("Invalid Selection, Try Again...");
                    editPerson();
            }
            System.out.println();
            if (!check) {
                System.out.println("Edit Not Completed... Enter Valid Input");
            } else {
                System.out.println("Edit completed");
            }
        } else
            System.out.println("No person found with this First Name");
    }

    @Override
    public void deletePerson() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Persons Name you want to delete:");
        String searchFirstName = scanner.nextLine();
        boolean isRemoved = persons.removeIf(person -> person.getName().equals(searchFirstName));
        if (!isRemoved) {
            System.out.println("Enter Correct Person Name... ");
        } else {
            System.out.println("Deleted Successfully...");
            CrudQuery crudQuery = new CrudQuery();
            crudQuery.deleteData(searchFirstName);
        }
    }

    @Override
    public void sortByName() {
        System.out.println("Sorting by Last name is selected");
        persons.sort(comparing(Person::getName));
        System.out.println("Sorting is completed to see the result select print option");
    }

    //Method for sorting persons list according to city
    public void sortByCity() {
        System.out.println("Sorting by City name is selected");
        persons.sort(comparing(Person::getCity));
        System.out.println("Sorting is completed to see the result select print option");
    }

    public void sortByState() {
        System.out.println("Sorting by state name is selected");
        persons.sort(comparing(Person::getState));
        System.out.println("Sorting is completed to see the result select print option");
    }

    public void sortByZip() {
        System.out.println("Sorting by zip");
        persons.sort(comparing(Person::getZip));
        System.out.println("Sorting is completed to see the result select print option");
    }

    public void searchByCityAndState() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter city:");
        String searchCity = scanner.next();
        System.out.println("Enter state:");
        String searchState = scanner.next();
        int indexOfPerson;
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getState().equals(searchState) && persons.get(i).getCity().equals(searchCity)) {
                indexOfPerson = i;
                System.out.println(persons.get(indexOfPerson).getName() + " " + persons.get(indexOfPerson)
                        + " " + persons.get(indexOfPerson).getCity() + " " + persons.get(indexOfPerson).getState() + " "
                        + persons.get(indexOfPerson).getZip() + " " + persons.get(indexOfPerson).getMobile());
            }
        }
    }

    public void searchByCityOrState() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type:\n 1 -> Search By City\n 2 -> Search By State");
        switch (scanner.nextInt()) {
            case 1:
                System.out.println("Enter city:");
                iteratingCityOrState();
                break;
            case 2:
                System.out.println("Enter state:");
                iteratingCityOrState();
                break;
            default:
                System.out.println("Invalid Selection, Try Again...");
                searchByCityOrState();
        }
    }

    private void iteratingCityOrState() {
        Scanner scanner = new Scanner(System.in);
        String elements = scanner.next();
        IntStream.range(0, persons.size())
                .filter(counters -> persons.get(counters).getState()
                        .equals(elements) || persons.get(counters)
                        .getCity().equals(elements)).forEach(counters -> {
            indexOfPerson = counters;
            System.out.println(persons.get(indexOfPerson).getName() + " " +
                    persons.get(indexOfPerson).getMobile() +
                    " " + persons.get(indexOfPerson).getCity() + " " +
                    persons.get(indexOfPerson).getState() + " "
                    + persons.get(indexOfPerson).getZip() + " ");
        });
    }

    public void writeFileUsingJsonSimple() {
        new IoOperation().writeJsonToFile(persons);
    }

    public void writeCSVFileUsingOpenCsv() {
        new IoOperation().writeCsvToFile(persons);
    }

    public void writeFileUsingGson() {
        new IoOperation().writeJsonFileUsingGson(persons);
    }
}