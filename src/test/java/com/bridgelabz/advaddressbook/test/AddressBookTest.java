package com.bridgelabz.advaddressbook.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

public class AddressBookTest {
    @Test
    public void givenAddressBookRestAssuredJsonFile_ReturnSameFileRecordAsResponseFromServer() {
        RestAssured.baseURI = "http://localhost:8000";
        Response response = RestAssured.get("/person/list");
        response.then().body("id", Matchers.hasItems(1, 2));
        response.then().body("name", Matchers.hasItems("Ranjeet Kapoor"));
    }

    @Test
    public void givenJsonFile_WhenPostPersonDetails_ShouldMatchAddedName() {
        RestAssured.baseURI = "http://localhost:8000";
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"name\": \"Ranjeet Kapoor\", " +
                        "\"city\": \"Bangalore\", " +
                        "\"state\": \"Karnatka\", " +
                        "\"zipCode\": \"123456\"," +
                        "\"phone\": \"1234567890\"}")
                .when()
                .post("/person/create");
        response.then().body("name", Matchers.is("Ranjeet Kapoor"));
    }

    @Test
    public void givenJsonFile_WhenUpdate_ShouldMatchAddedDetails() {
        RestAssured.baseURI = "http://localhost:8000";
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"name\": \"Ranjeet Kashyap\"," +
                        " \"city\": \"Bangalore\"," +
                        " \"state\": \"Karnatka\", " +
                        "\"zipCode\": \"123456\"," +
                        "\"phone\": \"1234567890\"}")
                .when()
                .put("/person/update/1");
        response.then().body("name", Matchers.is("Ranjeet Kashyap"));
    }

    @Test
    public void givenJsonFile_WhenDeleteUsingCurlOperation_ShouldNotHaveThatId() {
        RestAssured.baseURI = "http://localhost:8000";
        Response response = RestAssured.delete("/person/delete/2");
        response.then().body("id", Matchers.not(2));
    }
}