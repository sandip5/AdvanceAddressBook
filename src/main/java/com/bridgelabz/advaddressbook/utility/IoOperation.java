package com.bridgelabz.advaddressbook.utility;

import com.bridgelabz.advaddressbook.model.Person;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("ALL")
public class IoOperation {
    private String FILE_PATH_JSON =
            "C:\\Users\\aple\\IdeaProjects\\AdvanceAddressBook\\src\\main\\resources\\AddressBook.json";
    private String FILE_PATH_CSV =
            "C:\\Users\\aple\\IdeaProjects\\AdvanceAddressBook\\src\\main\\resources\\AddressBook.csv";
    private String FILE_PATH_GSON =
            "C:\\Users\\aple\\IdeaProjects\\AdvanceAddressBook\\src\\main\\resources\\AddressBookGson.json";

    public void writeJsonToFile(ArrayList<Person> persons) {
        JSONArray personList = new JSONArray();
        for (Person value : persons) {
            JSONObject personDetails = new JSONObject();
            personDetails.put("name", value.getName());
            personDetails.put("mobile", value.getMobile());
            personDetails.put("city", value.getCity());
            personDetails.put("state", value.getState());
            personDetails.put("zip", value.getZip());
            JSONObject personObject = new JSONObject();
            personObject.put("person", personDetails);
            personList.add(personObject);
        }
        try {
            FileWriter fileWriter = new FileWriter(FILE_PATH_JSON);
            fileWriter.append(personList.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Person> readFromJson() {
        JSONParser jsonParser = new JSONParser();
        ArrayList<Person> addressBookList = new ArrayList<>();
        try (FileReader reader = new FileReader(FILE_PATH_JSON)) {
            Object obj = jsonParser.parse(reader);
            JSONArray jsonList = (JSONArray) obj;
            jsonList.forEach(person -> addressBookList.add(parseAddressBook((JSONObject) person)));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return addressBookList;
    }

    private Person parseAddressBook(JSONObject personJson) {
        Person person = new Person();
        JSONObject personObj = (JSONObject) personJson.get("person");
        String personName = (String) personObj.get("name");
        person.setName(personName);
        Long mobile = (Long) personObj.get("mobile");
        person.setMobile(mobile);
        String city = (String) personObj.get("city");
        person.setCity(city);
        String state = (String) personObj.get("state");
        person.setState(state);
        Long zip = (Long) personObj.get("zip");
        person.setZip(zip.intValue());
        return person;
    }

    public ArrayList<Person> readFromCSV() {
        ArrayList<Person> addressBookList;
        try (Reader reader = Files.newBufferedReader(Paths.get(FILE_PATH_CSV))) {
            CsvToBean csvToBean = new CsvToBeanBuilder(reader).withType(Person.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            addressBookList = (ArrayList<Person>) csvToBean.parse();
            return addressBookList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeCsvToFile(ArrayList<Person> persons) {
        try (Writer writer = Files.newBufferedWriter(Paths.get(FILE_PATH_CSV))) {
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(persons);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Person> readFromGson() {
        ArrayList<Person> addressBookList = null;
        try {
            Person[] addressBookDetails = new Gson()
                    .fromJson(new FileReader(FILE_PATH_GSON), Person[].class);
            addressBookList = new ArrayList<>(Arrays.asList(addressBookDetails));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return addressBookList;
    }

    public void writeJsonFileUsingGson(ArrayList<Person> persons) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(persons);
        try (FileWriter writer = new FileWriter(FILE_PATH_GSON)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}